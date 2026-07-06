package com.demo.ecommerce.application.service.sale;

import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.SeedSalesResponse;
import com.demo.ecommerce.infrastructure.output.persistence.adapter.brand.SpringDataBrandRepository;
import com.demo.ecommerce.infrastructure.output.persistence.adapter.category.SpringDataCategoryRepository;
import com.demo.ecommerce.infrastructure.output.persistence.adapter.product.SpringDataProductRepository;
import com.demo.ecommerce.infrastructure.output.persistence.adapter.sale.SpringDataSaleRepository;
import com.demo.ecommerce.infrastructure.output.persistence.adapter.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SaleSeedService {

    private static final int DEFAULT_SALES = 120;
    private static final String SEED_PLACEHOLDER = "120 /* seed.sales.count */";

    private final DataSource dataSource;
    private final ResourceLoader resourceLoader;
    private final SpringDataSaleRepository saleRepository;
    private final SpringDataProductRepository productRepository;
    private final SpringDataBrandRepository brandRepository;
    private final SpringDataCategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public SeedSalesResponse seed(Integer requestedSales) {
        int targetSales = requestedSales == null || requestedSales < 1 ? DEFAULT_SALES : requestedSales;
        executeSeedScript(targetSales);

        LocalDateTime windowEnd = LocalDateTime.now();
        LocalDateTime windowStart = windowEnd.minusDays(7);

        return new SeedSalesResponse(
                Math.toIntExact(categoryRepository.count()),
                Math.toIntExact(brandRepository.count()),
                Math.toIntExact(productRepository.count()),
                Math.toIntExact(userRepository.count()),
                targetSales,
                Math.toIntExact(saleRepository.count()),
                windowStart.toString(),
                windowEnd.toString()
        );
    }

    private void executeSeedScript(int targetSales) {
        try {
            Resource seedResource = resourceLoader.getResource("classpath:data.sql");
            String script = seedResource.getContentAsString(StandardCharsets.UTF_8)
                    .replace(SEED_PLACEHOLDER, String.valueOf(targetSales));

            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(false, false, StandardCharsets.UTF_8.name(), new ByteArrayResource(script.getBytes(StandardCharsets.UTF_8)));
            DatabasePopulatorUtils.execute(populator, dataSource);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read data.sql for seed execution.", exception);
        }
    }
}

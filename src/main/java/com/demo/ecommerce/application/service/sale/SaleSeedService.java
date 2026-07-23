package com.demo.ecommerce.application.service.sale;

import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.SeedSalesResponse;
import com.demo.ecommerce.infrastructure.output.persistence.adapter.brand.SpringDataBrandRepository;
import com.demo.ecommerce.infrastructure.output.persistence.adapter.category.SpringDataCategoryRepository;
import com.demo.ecommerce.infrastructure.output.persistence.adapter.product.SpringDataProductRepository;
import com.demo.ecommerce.infrastructure.output.persistence.adapter.sale.SpringDataSaleRepository;
import com.demo.ecommerce.infrastructure.output.persistence.adapter.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SaleSeedService {

    private final DataSource dataSource;
    private final SpringDataSaleRepository saleRepository;
    private final SpringDataProductRepository productRepository;
    private final SpringDataBrandRepository brandRepository;
    private final SpringDataCategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public SeedSalesResponse seed() {
        executeSeedScript();

        LocalDateTime windowEnd = LocalDateTime.now();
        LocalDateTime windowStart = windowEnd.minusDays(7);

        return new SeedSalesResponse(
                Math.toIntExact(categoryRepository.count()),
                Math.toIntExact(brandRepository.count()),
                Math.toIntExact(productRepository.count()),
                Math.toIntExact(userRepository.count()),
                Math.toIntExact(saleRepository.count()),
                windowStart.toString(),
                windowEnd.toString()
        );
    }

    private void executeSeedScript() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                false,
                false,
                "UTF-8",
                new ClassPathResource("data.sql")
        );
        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}

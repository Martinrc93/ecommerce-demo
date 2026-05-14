package com.demo.ecommerce.application.service.sale;

import com.demo.ecommerce.application.port.in.sale.command.CreateSaleCommand;
import com.demo.ecommerce.application.port.in.sale.command.Item;
import com.demo.ecommerce.application.port.in.sale.usecase.CreateSaleUseCase;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.application.port.out.SaleRepositoryPort;
import com.demo.ecommerce.domain.exception.product.ProductIdNotFoundException;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreateSaleService implements CreateSaleUseCase {

    private final SaleRepositoryPort repository;
    private final ProductRepositoryPort productRepository;

    @Override
    @Transactional
    public Sale save(CreateSaleCommand command) {

        Sale sale = Sale.create(command.userId(), BigDecimal.ZERO);

        List<Long> productIds = command.items().stream()
                .map(Item::productId)
                .toList();


        Map<Long, Product> productMap = productRepository.findAllByIdsWithPessimisticLock(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        for (Item item : command.items()) {
            if (!productMap.containsKey(item.productId())) {
                throw new ProductIdNotFoundException(item.productId());
            }
        }

        List<Product> productsToSave = new ArrayList<>();

        for (Item item : command.items()) {
            Product product = productMap.get(item.productId());

            product.updateStock(item.quantity());
            productsToSave.add(product);

            SaleDetail saleDetail = SaleDetail.create(
                    product.getId(),
                    item.quantity(),
                    product.getPrice(),
                    BigDecimal.ZERO
            );
            sale.addSaleDetail(saleDetail);
        }

        productRepository.saveAll(productsToSave);
        return repository.save(sale);
    }

}

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

@Service
@AllArgsConstructor
public class CreateSaleService implements CreateSaleUseCase {

    private final SaleRepositoryPort repository;
    private final ProductRepositoryPort productRepository;

    @Override
    @Transactional
    public Sale save(CreateSaleCommand command) {

        Sale sale = Sale.create(command.userId(), BigDecimal.ZERO);

        for (Item item : command.items()){
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(()-> new ProductIdNotFoundException(item.productId()));
            product.updateStock(item.quantity());
            productRepository.save(product);
            SaleDetail saleDetail = SaleDetail.create(product.getId(),item.quantity(),product.getPrice(),BigDecimal.ZERO);
            sale.addSaleDetail(saleDetail);
        }

        return repository.save(sale);
    }

    @Override
    @Transactional
    public SaleDetail save(Item command) {
        return null;
    }
}

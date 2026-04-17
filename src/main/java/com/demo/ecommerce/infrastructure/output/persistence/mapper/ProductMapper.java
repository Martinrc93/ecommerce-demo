package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.infrastructure.output.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "productDetail.name", target = "name")
    @Mapping(source = "productDetail.description", target = "description")
    @Mapping(source = "productDetail.brand", target = "brand")
    @Mapping(source = "productDetail.category", target = "category")
    @Mapping(source = "price.money", target = "price")
    @Mapping(source = "productAvailability.stock.stock", target = "stock")
    @Mapping(source = "productAvailability.active", target = "active")
    ProductEntity toEntity(Product product);

    default Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        return Product.reconstitute(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getBrand(),
                entity.getCategory(),
                entity.getPrice(),
                entity.getStock(),
                entity.isActive()
        );
    }
}

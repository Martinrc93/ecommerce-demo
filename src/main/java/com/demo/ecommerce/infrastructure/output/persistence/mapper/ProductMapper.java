package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.infrastructure.output.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "productDetail.name", target = "name")
    @Mapping(source = "productDetail.description", target = "description")
    @Mapping(source = "productDetail.brand.name", target = "brand.name")
    @Mapping(source = "productDetail.brand.id", target = "brand.id")
    @Mapping(source = "productDetail.category.name", target = "category.name")
    @Mapping(source = "productDetail.category.id", target = "category.id")
    @Mapping(source = "price.money", target = "price")
    @Mapping(source = "productAvailability.stock.stock", target = "stock")
    @Mapping(source = "productAvailability.active", target = "active")
    ProductEntity toEntity(Product product);

    default Product toDomain(ProductEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Brand brand = entity.getBrand() != null ? Brand.of(entity.getBrand().getId(), entity.getBrand().getName()) : null;
        Category category = entity.getCategory() != null ? Category.of(entity.getCategory().getId(), entity.getCategory().getName()) : null;
        
        return Product.reconstitute(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                brand,
                category,
                entity.getPrice(),
                entity.getStock(),
                entity.isActive()
        );
    }
}

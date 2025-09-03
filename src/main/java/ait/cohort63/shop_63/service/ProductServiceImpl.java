package ait.cohort63.shop_63.service;

import ait.cohort63.shop_63.model.entity.Product;
import ait.cohort63.shop_63.repository.interfaces.ProductRepository;
import ait.cohort63.shop_63.service.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository productRepository;
    
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public Product saveProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        
        // Устанавливаем продукт как активный по умолчанию
        product.setActive(true);
        
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllActiveProducts() {
        return List.of();
    }

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    @Override
    public Product deleteProductById(Long id) {
        return null;
    }

    @Override
    public Product deleteProductByTitle(String title) {
        return null;
    }

    @Override
    public Product restoreProductById(Long id) {
        return null;
    }

    @Override
    public long getProductCount() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }

    @Override
    public BigDecimal getAveragePrice() {
        return null;
    }
}

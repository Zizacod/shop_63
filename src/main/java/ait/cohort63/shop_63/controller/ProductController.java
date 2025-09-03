package ait.cohort63.shop_63.controller;

import ait.cohort63.shop_63.model.entity.Product;
import ait.cohort63.shop_63.service.interfaces.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);

    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {

        return null;
    }

    @GetMapping
    public List<Product> getAll() {
        return List.of();
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        return product;
    }

    @DeleteMapping("/{productId}")
    public Product remove(@PathVariable("productId") Long id) {

        return new Product();
    }
}

package ait.cohort63.shop_63.repository;

import ait.cohort63.shop_63.model.entity.Product;
import ait.cohort63.shop_63.repository.interfaces.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.cfg.Configuration;

import java.math.BigDecimal;
import java.util.List;

public class ProductRepositoryHibernate implements ProductRepository {

    private EntityManager entityManager;

    public ProductRepositoryHibernate() {
        entityManager = new Configuration()
                .configure("hibernate/postgres.cfg.xml")
                .buildSessionFactory().createEntityManager();
    }

    @Override
    public Product save(Product product) {

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException(e);
        }
        return product;
    }


    @Override
    public List<Product> getAllActiveProducts() {
        return entityManager.createQuery("from Product p where p.active = true", Product.class).getResultList();
    }

    @Override
    public Product getProductById(Long id) {
        Product foundProduct = entityManager.find(Product.class, id);
        return foundProduct;
    }

    @Override
    public Product updateProduct(Long id, Product dataForUpdate) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Product foundProduct = entityManager.find(Product.class, id);
            if (foundProduct == null) {
                throw new IllegalArgumentException("Product with id " + id + " not found");
            }
            
            if (dataForUpdate.getTitle() != null) {
                foundProduct.setTitle(dataForUpdate.getTitle());
            }
            if (dataForUpdate.getPrice() != null) {
                foundProduct.setPrice(dataForUpdate.getPrice());
            }
            foundProduct.setActive(dataForUpdate.isActive());
            
            transaction.commit();
            return foundProduct;

        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to update product with id " + id, e);
        }
    }


    
    @Override
    public Product deleteProductById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Product foundProduct = entityManager.find(Product.class, id);
            if (foundProduct == null) {
                throw new IllegalArgumentException("Product not found: " + id);
            }
            // Софт удаление - помечаем как неактивный
            foundProduct.setActive(false);
            transaction.commit();
            return foundProduct;
            
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to delete product with id: " + id, e);
        }
    }

    @Override
    public Product deleteProductByTitle(String title) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            // Используем HQL запрос для поиска по title среди активных продуктов
            Product foundProduct = entityManager
                .createQuery("from Product p where p.title = :title and p.active = true", Product.class)
                .setParameter("title", title)
                .getSingleResult();
            
            // Софт удаление - помечаем как неактивный
            foundProduct.setActive(false);
            transaction.commit();
            return foundProduct;
        } catch (jakarta.persistence.NoResultException e) {
            if (transaction.isActive()) transaction.rollback();
            throw new IllegalArgumentException("Active product not found: " + title);
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to delete product: " + title, e);
        }
    }

    @Override
    public Product restoreProductById(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            Product foundProduct = entityManager.find(Product.class, id);
            if (foundProduct == null) {
                throw new IllegalArgumentException("Product with id " + id + " not found");
            }
            
            // Проверяем, что продукт действительно "удален" (неактивен)
            if (foundProduct.isActive()) {
                throw new IllegalStateException("Product with id " + id + " is already active");
            }
            
            // Восстанавливаем продукт - делаем активным
            foundProduct.setActive(true);
            transaction.commit();
            return foundProduct;
            
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Failed to restore product with id: " + id, e);
        }
    }

    @Override
    public long getProductCount() {
        try {
            Long count = entityManager
                .createQuery("select count(p) from Product p where p.active = true", Long.class)
                .getSingleResult();
            return count != null ? count : 0L;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product count", e);
        }
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

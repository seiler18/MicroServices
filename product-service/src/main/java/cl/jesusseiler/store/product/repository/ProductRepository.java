package cl.jesusseiler.store.product.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.jesusseiler.store.product.entity.Category;
import cl.jesusseiler.store.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
        
    public List<Product> findByCategory(Category category);

}

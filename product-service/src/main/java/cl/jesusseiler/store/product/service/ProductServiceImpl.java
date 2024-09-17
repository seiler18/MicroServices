package cl.jesusseiler.store.product.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.jesusseiler.store.product.entity.Product;
import cl.jesusseiler.store.product.entity.Category;
import cl.jesusseiler.store.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    
    @Override
    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {    
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        product.setStatus("CREATED");
        product.setCreatedAt(new Date());
        
        return productRepository.save(product);
    }

    //REVISAR ESTE PENDIENTE REVISAR VIDEO 5
    @Override
    public Product updateProduct(Product product) {
        Product productDB = getProduct(product.getId());
        if (productDB == null) {
            return null;
        }
        productDB.setName(product.getName());
        productDB.setDescription(product.getDescription());
        productDB.setCategory(product.getCategory());
        productDB.setPrice(product.getPrice());
        return productRepository.save(productDB);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDB = getProduct(id);
        if (productDB == null) {
            return null;
        }
        productDB.setStatus("DELETED");
        return productRepository.save(productDB);
    }


    //REVISAR ESTE PENDIENTE REVISAR VIDEO 5
    @Override
    public Product updateStock(Long id, Double quantity) {
        Product productDB = getProduct(id);
        if (productDB == null) {
            return null;
        }
        //esto tiene otro formato - REVISAR VIDEO 5
        Double stock = productDB.getStock();
        productDB.setStock(stock + quantity);
        return productRepository.save(productDB);
    }

    
    
    
}

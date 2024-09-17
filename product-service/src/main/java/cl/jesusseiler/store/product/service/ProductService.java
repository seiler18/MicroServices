package cl.jesusseiler.store.product.service;

import java.util.List;

import cl.jesusseiler.store.product.entity.Product;
import cl.jesusseiler.store.product.entity.Category;

public interface ProductService {

    //listar todos los productos
    public List<Product> listAllProduct();
    //listar producto
    public Product getProduct(Long id);
    //crear producto
    public Product createProduct(Product product);
    //actualizar producto
    public Product updateProduct(Product product);
    //eliminar producto
    public Product deleteProduct(Long id);
    //listar productos por categoria
    public List<Product> findByCategory(Category category);
    //actualizar stock
    public Product updateStock(Long id, Double quantity);


    

}

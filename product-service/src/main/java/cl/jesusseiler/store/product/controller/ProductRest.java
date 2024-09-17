package cl.jesusseiler.store.product.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import cl.jesusseiler.store.product.entity.Product;
import cl.jesusseiler.store.product.entity.Category;
import cl.jesusseiler.store.product.service.ProductService;
import jakarta.validation.Valid;

@RestController
@Slf4j
@RequestMapping(value = "/products")
public class ProductRest {
    @Autowired
    private ProductService productService;

    // Listar todos los productos y filtrar por categoria
    @GetMapping
    public ResponseEntity<List<Product>> listProducts(
            @RequestParam(value = "categoryId", required = false) Long categoryId) {
        List<Product> products = productService.listAllProduct();
        if (categoryId == null) {
            products = productService.listAllProduct();
            if (products.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        } else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if (products.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
        }
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        log.info("Listando productos : {}", products);
        return ResponseEntity.ok(products);
    }

    // Listar un producto por id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("Producto encontrado : {}", product);
        return ResponseEntity.ok(product);
    }

    // Crear un producto
    @SuppressWarnings("null")
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result)
            throws MethodArgumentNotValidException {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(null, result); // Lanza la excepción para ser manejada por el
                                                                     // GlobalExceptionHandler
        }
        // validar si el producto ya existe en la base de datos
        if (productService.getProduct(product.getId()) != null) {
            log.warn("El producto con id {} ya existe en la base de datos", product.getId());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Product productCreate = productService.createProduct(product);
        log.info("Producto creado con éxito : {}", productCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    // Actualizar un producto
    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        product.setId(id);
        Product productDB = productService.updateProduct(product);
        if (productDB == null) {
            log.warn("El producto con id {} no existe en la base de datos", id);
            return ResponseEntity.notFound().build();
        }
        log.info("El producto con id {} ha sido actualizado", id);
        return ResponseEntity.ok(productDB);
    }

    // Eliminar un producto
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        Product product = productService.deleteProduct(id);
        if (product == null) {
            log.warn("El producto con id {} no existe en la base de datos", id);
            return ResponseEntity.notFound().build();
        }
        log.info("El producto con id {} ha sido eliminado", id);
        return ResponseEntity.ok(product);
    }

    // Actualizar el stock de un producto
    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable("id") Long id,
            @RequestParam(value = "quantity", required = true) Double quantity) {
        Product product = productService.updateStock(id, quantity);
        if (product == null) {
            log.warn("El producto con id {} no existe en la base de datos", id);
            return ResponseEntity.notFound().build();
        }
        log.info("El stock del producto con id {} ha sido actualizado", id);
        return ResponseEntity.ok(product);
    }

}

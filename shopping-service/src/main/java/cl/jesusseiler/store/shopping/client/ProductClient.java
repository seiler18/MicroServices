package cl.jesusseiler.store.shopping.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import cl.jesusseiler.store.shopping.model.Product;

@FeignClient(name = "product-service" , path = "/products")
public interface ProductClient {

    // Listar un producto por id
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id);

    // Actualizar el stock de un producto
    @GetMapping(value = "/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable("id") Long id,
            @RequestParam(value = "quantity", required = true) Double quantity);

}

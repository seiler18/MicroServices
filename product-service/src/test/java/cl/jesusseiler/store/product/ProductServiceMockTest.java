package cl.jesusseiler.store.product;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import cl.jesusseiler.store.product.entity.Category;
import cl.jesusseiler.store.product.entity.Product;
import cl.jesusseiler.store.product.repository.ProductRepository;
import cl.jesusseiler.store.product.service.ProductService;
import cl.jesusseiler.store.product.service.ProductServiceImpl;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productRepository);
        Product computer = Product.builder()
            .id(1L)
            .name("computer")
            .category(Category.builder().id(1L).build())
            .price(Double.parseDouble("12.5"))
            .stock(Double.parseDouble("5"))
            .build();

        Mockito.when(productRepository.findById(1L))
            .thenReturn(Optional.of(computer));
        
        Mockito.when(productRepository.save(computer)).thenReturn(computer);
    }

    @Test
    public void whenValidGetID_thenReturnProduct() {
        Product found = productService.getProduct(1L);
        Assertions.assertThat(found.getId()).isEqualTo(1L);
        Assertions.assertThat(found.getName()).isEqualTo("computer");

    }
    
    @Test
    public void whenValidUpdateStock_thenReturnNewStock() {
        Product newStock = productService.updateStock(1L, Double.parseDouble("8"));
        Assertions.assertThat(newStock.getStock()).isEqualTo(13);
        Mockito.verify(productRepository).save(newStock);
    }

    @Test
    public void whenValidDelete_thenReturnProduct() {
        Product deletedProduct = productService.deleteProduct(1L);
        Assertions.assertThat(deletedProduct.getStatus()).isEqualTo("DELETED");
        Mockito.verify(productRepository).save(deletedProduct);
    }
    
}

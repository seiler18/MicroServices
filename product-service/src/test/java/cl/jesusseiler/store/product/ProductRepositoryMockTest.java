package cl.jesusseiler.store.product;

import java.util.Date;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import cl.jesusseiler.store.product.entity.Category;
import cl.jesusseiler.store.product.entity.Product;
import cl.jesusseiler.store.product.repository.ProductRepository;

@DataJpaTest
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindCategory_thenReturnListProduct() {
        Product product01 = Product.builder()
                .name("computer")
                .category(Category.builder().id(1L).build())
                .description("muy chulo")
                .stock(Double.parseDouble("10"))
                .price(Double.parseDouble("1000"))
                .status("Created")
                .createdAt(new Date()).build();
                productRepository.save(product01);

                List<Product> founds = productRepository.findByCategory(product01.getCategory());


                Assertions.assertThat(founds.size()).isEqualTo(3);

    }
}

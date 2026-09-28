package br.edu.utfpr.pb.pw44s.ecommerceserver;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.ProductDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Category;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Product;
import br.edu.utfpr.pb.pw44s.ecommerceserver.repository.CategoryRepository;
import br.edu.utfpr.pb.pw44s.ecommerceserver.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class ProductControllerTest {
    private static final String API_PRODUCT = "/products";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void cleanup() { // Apaga os produtos e categorias antes de cada teste
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void getProducts_whenProductExists_receiveProduct() { // Testa a Listagem com Produtos Cadastrados
        Category category = createValidCategory(); // Categoria válida
        createValidProduct(category); // Produto válido
        ResponseEntity<ProductDTO[]> response = restTemplate.getForEntity(API_PRODUCT, ProductDTO[].class); // Get executado
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); // Deve retornar 200 OK
        assertThat(response.getBody()).hasSize(1); // Deve retornar 1 produto.
    }

    @Test
    public void getProductById_whenProductExists_receiveProduct() { // Testa a Busca de Produto por ID
        Category category = createValidCategory(); // Categoria válida
        Product product = createValidProduct(category); // Produto válido
        ResponseEntity<ProductDTO> response = restTemplate.getForEntity(
                API_PRODUCT + "/" + product.getId(), ProductDTO.class); // Get executado
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); // Deve retornar 200 OK
        assertThat(response.getBody()).isNotNull(); // Deve retornar um produto
        assertThat(response.getBody().getId()).isEqualTo(product.getId()); // Deve retornar o produto buscado
    }

    @Test
    public void getProductsByCategory_whenCategoryExists_receiveProductsFromCategory() { // Testa o Filtro por Categoria
        Category category1 = createValidCategory(); // Primeira categoria válida
        Product product1 = createValidProduct(category1); // Produto válido da primeira categoria

        Category category2 = createValidCategory(); // Segunda categoria válida
        createValidProduct(category2); // Produto válido da segunda categoria

        ResponseEntity<ProductDTO[]> response = restTemplate.getForEntity(API_PRODUCT + "/category/" +
                category1.getId(), ProductDTO[].class); // Get executado

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK); // Deve retornar 200 OK
        assertThat(response.getBody()).isNotNull(); // Deve retornar a lista de produtos da categoria
        assertThat(response.getBody()).hasSize(1); // Deve retornar somente um produto na categoria
        assertThat(response.getBody()[0].getId()).isEqualTo(product1.getId()); // Deve retornar o produto da categoria
    }

    private Category createValidCategory() { // Cria uma categoria válida para utilizar nos testes
        Category category = Category.builder().name("Categoria Teste").build();
        return categoryRepository.save(category);
    }

    private Product createValidProduct(Category category) { // Cria um produto válido para utilizar nos testes
        Product product = Product.builder().name("Produto Teste").description("Descrição do produto.")
                .price(new BigDecimal("75.90")).imageUrl("https://teste.jpg").category(category).build();
        return productRepository.save(product);
    }
}

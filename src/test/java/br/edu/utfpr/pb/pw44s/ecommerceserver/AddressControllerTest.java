package br.edu.utfpr.pb.pw44s.ecommerceserver;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.AddressDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.AuthRequestDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.UserRequestDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.repository.AddressRepository;
import br.edu.utfpr.pb.pw44s.ecommerceserver.repository.UserRepository;
import br.edu.utfpr.pb.pw44s.ecommerceserver.security.dto.AuthResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class AddressControllerTest {
    private static final String API_ADDRESS = "/addresses";
    private static final String API_USER = "/users";
    private static final String API_LOGIN = "/login";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanup() { // Apaga os endereços e usuários antes de cada teste
        addressRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void postAddress_whenAddressIsValid_receiveCreated() { // Testa a criação de endereço
        createValidUser(); // Cadastra um usuário válido
        String token = loginToken(); // Autentica o usuário e obtém o token

        AddressDTO address = createValidAddress(); // Cria um endereço válido

        HttpHeaders headers = new HttpHeaders(); // Cria os headers da requisição
        headers.setBearerAuth(token); // Adiciona o token no header de autenticação

        HttpEntity<AddressDTO> request = new HttpEntity<>(address, headers); // Junta Endereço + (Header+Token)

        ResponseEntity<AddressDTO> response = restTemplate.exchange(API_ADDRESS,
                HttpMethod.POST, request, AddressDTO.class); // POST Address Executado
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED); // Deve retornar 201 CREATED
    }

    private void createValidUser() { // Cadastra um usuário válido para utilizar nos testes
        UserRequestDTO user = UserRequestDTO.builder()
                .name("Usuário Teste").email("teste@email.com").password("Teste123").build();
        restTemplate.postForEntity(API_USER, user, Object.class); // Post executado
    }

    private String loginToken() { // Autentica um usuário e retorna o token para utilizar nos testes
        AuthRequestDTO request = AuthRequestDTO.builder()
                .email("teste@email.com").password("Teste123").build(); // Passa credenciais válidas
        ResponseEntity<AuthResponseDTO> response = restTemplate.postForEntity(
                API_LOGIN, request, AuthResponseDTO.class); // POST Login Executado
        assertThat(response.getBody()).isNotNull();  // Deve retornar uma resposta
        return response.getBody().getToken(); // Retorna um token
    }

    private AddressDTO createValidAddress() { // Cria um endereço válido para utilizar nos testes
        return AddressDTO.builder().street("Rua Teste").number("12").complement("Complemento")
                .neighborhood("Bairro Teste").city("Cidade Teste").state("PR").zipCode("85000-000").build();
    }
}
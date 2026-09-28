package br.edu.utfpr.pb.pw44s.ecommerceserver;

import br.edu.utfpr.pb.pw44s.ecommerceserver.dto.UserRequestDTO;
import br.edu.utfpr.pb.pw44s.ecommerceserver.model.User;
import br.edu.utfpr.pb.pw44s.ecommerceserver.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestRestTemplate
public class UserControllerTest {
    private static final String API_USER = "/users";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void cleanup() { // Apaga usuários antes de cada teste
        userRepository.deleteAll();
    }

    @Test
    public void postUser_whenUserIsValid_receiveCreated() { // Testa a Criação de Usuário
        UserRequestDTO user = createValidUser(); // Usuário válido
        ResponseEntity<Object> response = restTemplate.postForEntity(API_USER, user, Object.class); // Post executado
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED); // Deve retornar 201 CREATED
    }

    @Test
    public void postUser_whenUserIsValid_userSavedToDB() { // Testa a Persistência no BD
        UserRequestDTO user = createValidUser(); // Usuário válido
        restTemplate.postForEntity(API_USER, user, Object.class); // Post executado
        assertThat(userRepository.count()).isEqualTo(1); // Deve existir 1 usuário no BD
    }

    @Test
    public void postUser_whenUserIsValid_passwordIsHashedInDB() { // Testa a Criptografia da Senha
        UserRequestDTO user = createValidUser(); // Usuário válido
        restTemplate.postForEntity(API_USER, user, Object.class); // Post executado
        User userDB = userRepository.findAll().getFirst(); // Busca o usuário salvo no BD
        assertThat(userDB.getPassword()).isNotEqualTo(user.getPassword()); // Senha salva deve ser diferente da original
    }

    @Test
    public void postUser_whenUserPasswordIsInvalid_receiveBadRequest() { // Testa o @Pattern da Senha
        UserRequestDTO user = createValidUser();  // Usuário válido
        user.setPassword("senhateste");
        ResponseEntity<Object> response = restTemplate.postForEntity(API_USER, user, Object.class); // Post executado
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST); // Deve retornar 400 BAD REQUEST
    }

    private UserRequestDTO createValidUser() { // Cria um usuário válido para utilizar nos testes
        return UserRequestDTO.builder().name("Usuário Teste").email("teste@email.com").password("Teste123").build();
    }
}
package br.edu.utfpr.pb.pw44s.ecommerceserver.repository;

import br.edu.utfpr.pb.pw44s.ecommerceserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
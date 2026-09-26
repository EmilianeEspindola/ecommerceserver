package br.edu.utfpr.pb.pw44s.ecommerceserver.repository;

import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
    Address findByIdAndUserId(Long id, Long userId);
}
package br.edu.utfpr.pb.pw44s.ecommerceserver.repository;

import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId); // Busca todos os pedidos de um usuário
    Page<Order> findByUserId(Long userId, Pageable pageable); // Busca todos os pedidos de um usuário de forma paginada
    Order findByIdAndUserId(Long id, Long userId); // Busca um pedido específico de um usuário
}
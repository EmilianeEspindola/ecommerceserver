package br.edu.utfpr.pb.pw44s.ecommerceserver.service;

import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Order save(Order order);
    List<Order> findByUserId(Long userId);
    Page<Order> findByUserId(Long userId, Pageable pageable);
    Order findByIdAndUserId(Long id, Long userId);
}
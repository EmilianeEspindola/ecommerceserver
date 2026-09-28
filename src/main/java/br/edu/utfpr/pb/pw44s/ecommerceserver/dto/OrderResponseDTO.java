package br.edu.utfpr.pb.pw44s.ecommerceserver.dto;

import br.edu.utfpr.pb.pw44s.ecommerceserver.model.OrderAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;

    private LocalDateTime date;

    private OrderAddress address;

    private List<OrderItemResponseDTO> items;

    private BigDecimal total;
}
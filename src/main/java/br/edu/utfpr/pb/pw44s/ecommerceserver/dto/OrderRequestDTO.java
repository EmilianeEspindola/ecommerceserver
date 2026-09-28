package br.edu.utfpr.pb.pw44s.ecommerceserver.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    @NotNull
    private Long addressId;

    @NotEmpty // Não permite criar um pedido sem itens
    @Valid // Não permite adicionar itens com quantidade zerada
    private List<OrderItemRequestDTO> items;
}
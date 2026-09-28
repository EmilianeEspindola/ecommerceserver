package br.edu.utfpr.pb.pw44s.ecommerceserver.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDTO {
    @NotNull
    private Long productId;

    @NotNull
    @Positive // Impede de adicionar um item com quantidade zerada
    private Integer quantity;
}
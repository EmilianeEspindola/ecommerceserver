package br.edu.utfpr.pb.pw44s.ecommerceserver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private Long id; // Necessário para identificar no caso da existência de mais de um endereço por usuário.

    @NotNull
    private String street;

    @NotNull
    private String number;

    private String complement;

    @NotNull
    private String neighborhood;

    @NotBlank
    private String city;

    @NotBlank
    @Size(min = 2, max = 2)
    private String state;

    @NotNull
    @Pattern(regexp = "^\\d{5}-\\d{3}$")
    private String zipCode;
}
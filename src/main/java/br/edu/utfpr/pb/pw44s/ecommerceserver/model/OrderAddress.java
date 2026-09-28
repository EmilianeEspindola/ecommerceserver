package br.edu.utfpr.pb.pw44s.ecommerceserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderAddress { // Faz uma cópia do endereço escolhido na hora para não perder histórico
    @Column(name = "address_street", nullable = false)
    private String street;

    @Column(name = "address_number", nullable = false)
    private String number;

    @Column(name = "address_complement")
    private String complement;

    @Column(name = "address_neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "address_city", nullable = false)
    private String city;

    @Column(name = "address_state", nullable = false)
    private String state;

    @Column(name = "address_zip_code", nullable = false)
    private String zipCode;
}
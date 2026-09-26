package br.edu.utfpr.pb.pw44s.ecommerceserver.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "tb_address")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String street;

    @NotNull
    @Column(nullable = false)
    private String number;

    private String complement;

    @NotNull
    @Column(nullable = false)
    private String neighborhood;

    @NotNull
    @Column(nullable = false)
    private String city;

    @NotNull
    @Column(nullable = false)
    private String state;

    @NotNull
    @Column(nullable = false)
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
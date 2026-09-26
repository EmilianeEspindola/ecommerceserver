package br.edu.utfpr.pb.pw44s.ecommerceserver.dto;

import lombok.Data;

@Data
public class AuthRequestDTO {
    private String email;
    private String password;
}
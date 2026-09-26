package br.edu.utfpr.pb.pw44s.ecommerceserver.security;

public class SecurityConstants {
    public static final String SECRET = "utfpr"; // Utilizado para gerar o token
    public static final long EXPIRATION_TIME = 86400000; // 1 Dia = 60*60*24*1000
    public static final String TOKEN_PREFIX = "Bearer "; // Tipo de autenticação
    public static final String HEADER_STRING = "Authorization"; // Header a ser informado ao servidor com o token
}
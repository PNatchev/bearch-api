package io.bearch.webapi.config.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "auth")
public class SecurityProperties {
    private String user;
    private String password;
    private RSAPublicKey rsaPublicKey;
    private RSAPrivateKey rsaPrivateKey;
}

package com.example.authentication.jwt.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * @author Gary Cheng
 */
@Slf4j
public class Base64Test {
    public static void main(String[] args) {
        String secretKey = "3778214125442A472D4B6150645367566B58703273357638792F423F4528482B";
        String encoded = Encoders.BASE64.encode(secretKey.getBytes());
        log.debug(encoded);
        byte[] keyBytes = Decoders.BASE64.decode(encoded);
        log.debug(new String(keyBytes));
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}

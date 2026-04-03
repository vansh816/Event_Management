package net.engineeringdigest.journalApp.service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    // ✅ Fixed secret key - yeh kabhi nahi badlega
    private String secretkey = "bXlTdXBlclNlY3JldEtleUZvckpXVFRva2VuMTIzNDU2Nzg=";
    //                          ^ apna khud ka base64 key daal do

    // Constructor ki zaroorat nahi ab

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(getkey())
                .compact();
    }

    private Key getkey() {
        byte[] keybytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keybytes);
    }
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getkey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String email = extractEmail(token);
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getkey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return email.equals(userDetails.getUsername()) && !expiration.before(new Date());
    }
}

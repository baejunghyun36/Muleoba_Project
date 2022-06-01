package project.muleoba.token;

import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private static final String SECRET_KEY = "qwerjaskldjaslkdjaskldjaslkdjaslkjdlasjdkasdjkas";

    // 로그인 서비스
    public static String createToken(Long uID, long expTime) {

        if ( expTime <= 0 ) {
            throw new RuntimeException("토큰 시간만료");
        }

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        byte[] secretkeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretkeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder()
                .setSubject(String.valueOf(uID))
                .signWith(signingKey, signatureAlgorithm)
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .compact();
    }

    // 토근 해석
    public static Long getUID(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

}

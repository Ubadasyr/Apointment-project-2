package com.servicesproject.project.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
@Component
public class TokenUtil {

@Value("${token.expiration}")
 private  long Token_Ex=3000;

@Value("${token.secret}")
private String Token_Secret;
    public String generateToken(UserDetails userDetails){

        Map<String,Object> claims=new HashMap<>();
        System.out.println(userDetails.getUsername());
        claims.put("sub",userDetails.getUsername());
        claims.put("created",new Date());

        return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS256,Token_Secret).compact();
    }

    private Date generateExpirationDate() {
return new Date(System.currentTimeMillis() + Token_Ex * 1000);
    }


    public String getUserName(String token){
        try{

            Claims claim =Jwts.parser().setSigningKey(Token_Secret).parseClaimsJws(token).getBody();
            System.out.println(claim);
            return  claim.getSubject();
        }catch (Exception e){return null;}
    }


    public boolean isValid(String token, UserDetails userDetails) {
        String username = getUserName(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        try {
            Claims claim = Jwts.parser().setSigningKey(Token_Secret).parseClaimsJws(token).getBody();
            Date ex = claim.getExpiration();
            return ex.before(new Date());
        }
        catch (Exception e){return false;}

    }
}

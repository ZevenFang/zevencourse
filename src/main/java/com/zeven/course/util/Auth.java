package com.zeven.course.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangf on 2016/5/21.
 */
public class Auth {

    public static final Key key = MacProvider.generateKey();
    public static final long expire = 7200000; //2 hours

    public static void main (String [] args) {
        Map<String,Object> claims = new HashMap<String, Object>();
        claims.put("id",1);
        claims.put("name","zeven");
        claims.put("role","admin");
        String s = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, key).compact();
        System.out.println(s);
        Claims result;
        try {
            result = Jwts.parser().setSigningKey(key).parseClaimsJws(s).getBody();
            System.out.println(result.get("id")+"|"+result.get("name")+"|"+result.get("role"));
        } catch (SignatureException e){
            System.out.println("401 No Authentication");
        }
    }
}

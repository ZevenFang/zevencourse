package com.zeven.course.interceptor;

import com.zeven.course.util.Auth;
import com.zeven.course.util.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fangf on 2016/5/21.
 */
public class AccessInterceptor extends CrossDomainInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (!uri.startsWith("/Access")) {
                Token token = new Token(request.getHeader("Authorization"));
                if (token.getErr()==Token.ExpiredJwtError){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            "Authentication Failed: 认证过期");
                    return true;
                }
                if (token.getErr()==Token.SignatureError){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            "Authentication Failed: 非法认证");
                    return true;
                }
                boolean isAdmin = !token.getRole().equals("admin")&&uri.startsWith("/Admin");
                boolean isTeacher = !token.getRole().equals("teacher")&&uri.startsWith("/Teacher");
                boolean isStudent = !token.getRole().equals("student")&&uri.startsWith("/Student");
                if (isAdmin||isTeacher||isStudent)
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            "Authentication Failed: 无权访问");
        }
        return true;
    }
}

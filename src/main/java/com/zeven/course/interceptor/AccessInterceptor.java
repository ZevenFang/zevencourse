package com.zeven.course.interceptor;

import com.zeven.course.util.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fangf on 2016/5/21.
 * OAuth权限认证
 */
public class AccessInterceptor extends CrossDomainInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getContextPath();
        String uri = request.getRequestURI();
        if (!uri.startsWith(path+"/Access")) {
                Token token = new Token(request.getHeader("Authorization"));
                if (token.getErr()==Token.ExpiredJwtError){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            "Authentication Failed: 认证过期");
                    return false;
                }
                if (token.getErr()==Token.SignatureError){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            "Authentication Failed: 非法认证");
                    return false;
                }
                boolean isAdmin = !token.getRole().equals("admin")&&uri.startsWith(path+"/Admin");
                boolean isTeacher = !token.getRole().equals("teacher")&&uri.startsWith(path+"/Teacher");
                boolean isStudent = !token.getRole().equals("student")&&uri.startsWith(path+"/Student");
                if (isAdmin||isTeacher||isStudent) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                            "Authentication Failed: 无权访问");
                    return false;
                }
        }
        return true;
    }
}

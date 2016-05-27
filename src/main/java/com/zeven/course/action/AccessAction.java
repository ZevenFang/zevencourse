package com.zeven.course.action;

import com.zeven.course.model.Student;
import com.zeven.course.model.Teacher;
import com.zeven.course.service.StudentService;
import com.zeven.course.service.TeacherService;
import com.zeven.course.util.Auth;
import com.zeven.course.util.MD5Encoder;
import com.zeven.course.util.Message;
import com.zeven.course.util.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fangf on 2016/5/20.
 */
@RequestMapping("/Access")
@Controller
public class AccessAction {

    private StudentService sService = new StudentService();
    private TeacherService tService = new TeacherService();

    @ResponseBody
    @RequestMapping("signin")
    public Message signin(@RequestParam String name,@RequestParam String password,@RequestParam String role){
        Message message = new Message(1,"ok");
        Map<String,Object> claims = new HashMap<String, Object>();
        if (role.equals("student")){//学生账号
            Student student = sService.login(name,password);
            if (student==null)
                return new Message(-1,"用户名或密码错误");
            claims.put("id",student.getId());
            claims.put("name",student.getName());
            claims.put("role","student");
        } else if (role.equals("teacher")){//教师账号
            Teacher teacher = tService.login(name,password);
            if (teacher==null)
                return new Message(-1,"用户名或密码错误");
            claims.put("id",teacher.getId());
            claims.put("name",teacher.getName());
            claims.put("role","teacher");
        } else if (role.equals("admin")){//管理员账号
            Teacher teacher = tService.login(name,password);
            if (teacher==null)
                return new Message(-1,"用户名或密码错误");
            if (teacher.getIsAdmin()==0)
                return new Message(-2,"非管理员账号");
            claims.put("id",teacher.getId());
            claims.put("name",teacher.getName());
            claims.put("role","admin");
        }
        String s = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, Auth.key).setExpiration(new Date(System.currentTimeMillis()+Auth.expire)).compact();
        claims.put("token",s);
        claims.remove("exp");
        message.setData(claims);
        return message;
    }

    @ResponseBody
    @RequestMapping("userInfo")
    public Message userInfo(@RequestHeader("Authorization") String token){
        Token t = new Token(token);
        int id = t.getId();
        String role = t.getRole();
        if (role.equals("student"))
            return new Message(1,"ok",sService.findById(id));
        else if (role.equals("teacher")||role.equals("admin"))
            return new Message(1,"ok",tService.findById(id));
        else
            return new Message(-1,"用户身份不符");
    }

    @ResponseBody
    @RequestMapping("changePwd")
    public Message changePwd(@RequestParam String oldPwd, @RequestParam String newPwd, @RequestHeader("Authorization") String token){
        Token t = new Token(token);
        int id = t.getId();
        String role = t.getRole();
        if (role.equals("student")) {
            Student student = sService.findById(id);
            if (!student.getPassword().equals(MD5Encoder.encode(oldPwd)))
                return new Message(-2, "原密码错误");
            student.setPassword(MD5Encoder.encode(newPwd));
            sService.update(student);
        } else if (role.equals("teacher") || role.equals("admin")) {
            Teacher teacher = tService.findById(id);
            if (!teacher.getPassword().equals(MD5Encoder.encode(oldPwd)))
                return new Message(-2, "原密码错误");
            teacher.setPassword(MD5Encoder.encode(newPwd));
            tService.update(teacher);
        }
        return new Message(1,"ok");
    }

}

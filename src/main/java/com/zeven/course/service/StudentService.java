package com.zeven.course.service;

import com.zeven.course.dao.DaoSupportImpl;
import com.zeven.course.model.Student;
import com.zeven.course.util.MD5Encoder;
import com.zeven.course.util.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fangf on 2016/5/20.
 */
@Component
public class StudentService extends DaoSupportImpl<Student> {

    @Resource
    private CourseService courseService;

    public Student login(String sno, String password){
        List students = getSession().createQuery("FROM Student WHERE sno = (:sno)")
                .setParameter("sno",sno).list();
        Student student = students.size()==0?null: (Student) students.get(0);
        if (student!=null)
            return student.getPassword().equals(MD5Encoder.encode(password))?student:null;
        else return null;
    }

    public void deleteCascade(Integer[] ids){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        session.createQuery("DELETE FROM CourseStudent WHERE sid in (:ids)")
                .setParameterList("ids",ids).executeUpdate();
        session.createQuery(
                "DELETE FROM Student WHERE id IN (:ids)")
                .setParameterList("ids", ids).executeUpdate();
        tx.commit();

    }

    public Map<String,Object> findStudentsByCid(int id){
        Map<String,Object> data = new HashMap<String, Object>();
        List students = getSession().createQuery("FROM Student WHERE id in (SELECT sid FROM CourseStudent WHERE cid = (:cid))")
                .setParameter("cid",id).list();
        data.put("course",courseService.findById(id));
        data.put("students",students);

        return data;
    }

}

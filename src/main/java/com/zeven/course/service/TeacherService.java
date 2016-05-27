package com.zeven.course.service;

import com.zeven.course.dao.DaoSupportImpl;
import com.zeven.course.model.Teacher;
import com.zeven.course.util.MD5Encoder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by fangf on 2016/5/20.
 */
@Component
public class TeacherService extends DaoSupportImpl<Teacher> {

    public Teacher login(String tno, String password){
        List teachers = getSession().createQuery("FROM Teacher WHERE tno = (:tno)")
                .setParameter("tno",tno).list();
        Teacher teacher = teachers.size()==0?null: (Teacher) teachers.get(0);
        if (teacher != null)
            return teacher.getPassword().equals(MD5Encoder.encode(password))?teacher:null;
        else return null;
    }

    public void deleteCascade(Integer[] ids){
        Session session = getSession();
        Transaction tx = getSession().beginTransaction();
        session.createQuery("DELETE FROM TeacherCourse WHERE tid in (:ids)")
                .setParameterList("ids",ids).executeUpdate();
        session.createQuery(
                "DELETE FROM Teacher WHERE id IN (:ids)")
                .setParameterList("ids", ids).executeUpdate();
        tx.commit();

    }

    public List findAllWithoutAdmin(){
        return getSession().createQuery("FROM Teacher WHERE isAdmin != 1").list();
    }

}

package com.zeven.course.service;

import com.zeven.course.common.TeacherCommon;
import com.zeven.course.dao.DaoSupportImpl;
import com.zeven.course.model.Course;
import com.zeven.course.model.Teacher;
import com.zeven.course.model.TeacherCourse;
import com.zeven.course.util.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fangf on 2016/5/22.
 */
public class TeacherCourseService extends DaoSupportImpl<TeacherCourse> {

    private CourseService courseService = new CourseService();

    public Message selectCourses(int userID, Integer[] ids){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        if (session.createQuery("FROM TeacherCourse WHERE tid = (:tid) and cid in (:cid)")
                .setParameter("tid",userID)
                .setParameterList("cid",ids).list().size()>0) {
            return new Message(-1, "请查看是否选过选中的课程。");
        }
        for (int i :ids){
            TeacherCourse tc = new TeacherCourse();
            tc.setTid(userID);
            tc.setCid(i);
            session.save(tc);
        }
        tx.commit();
        closeSession();
        return new Message(1,"ok");
    }

    public Message unselectCourses(int userID, Integer[] ids) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        int rows = session.createQuery("DELETE FROM TeacherCourse WHERE tid = (:tid) and cid in (:cid)")
                .setParameter("tid", userID)
                .setParameterList("cid", ids).executeUpdate();
        tx.commit();
        closeSession();
        return rows > 0 ? new Message(1, "ok") : new Message(-1, "删除失败");
    }

    public Map<String,List> getCoursesWithTeacher(){
        Map<String,List> data = new HashMap<String, List>();
        List<Course> courses = getSession().createQuery("FROM Course WHERE id in (SELECT DISTINCT cid FROM TeacherCourse)").list();
        data.put("courses",courses);
        Integer[] cids = new Integer[courses.size()];
        for (int i=0;i<courses.size();i++)
            cids[i] = courses.get(i).getId();
        List<Teacher> teachers;
        if (cids.length==0) teachers = new ArrayList<Teacher>();
        else teachers = getSession().createQuery("FROM Teacher WHERE id in (SELECT DISTINCT tid FROM TeacherCourse WHERE cid in (:cid))")
                .setParameterList("cid",cids).list();
        data.put("teachers", TeacherCommon.teacherList(teachers));
        data.put("tc",findAll());
        closeSession();
        return data;
    }

}

package com.zeven.course.service;

import com.zeven.course.dao.DaoSupportImpl;
import com.zeven.course.model.CourseStudent;
import com.zeven.course.model.TeacherCourse;
import com.zeven.course.util.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fangf on 2016/5/23.
 */
public class CourseStudentService extends DaoSupportImpl<CourseStudent> {

    public Message selectCourses(int userID, Integer[] ids){
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        if (session.createQuery("FROM CourseStudent WHERE sid = (:sid) and cid in (:cid)")
                .setParameter("sid",userID)
                .setParameterList("cid",ids).list().size()>0) {
            return new Message(-1, "请查看是否选过选中的课程。");
        }
        for (int i :ids){
            CourseStudent tc = new CourseStudent();
            tc.setSid(userID);
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
        int rows = session.createQuery("DELETE FROM CourseStudent WHERE sid = (:sid) and cid in (:cid)")
                .setParameter("sid", userID)
                .setParameterList("cid", ids).executeUpdate();
        tx.commit();
        closeSession();
        return rows > 0 ? new Message(1, "ok") : new Message(-1, "删除失败");
    }

    public List getGradeByCid(int id) {
        return getSession().createQuery("FROM CourseStudent WHERE cid = (:cid)")
                .setParameter("cid",id).list();
    }

    public Message recordGrade(int tid, int cid, int sid, double grade){
        Session session = getSession();
        if(session.createQuery("FROM TeacherCourse WHERE tid = (:tid) and cid = (:cid)")
                .setParameter("tid",tid)
                .setParameter("cid",cid).list().size()==0)
            return new Message(-1,"您非此课程的授课教师，无法录入成绩");
        Transaction tx = session.beginTransaction();
        int row = session.createQuery("UPDATE CourseStudent SET grade = (:grade) WHERE cid = (:cid) and sid = (:sid)")
                .setParameter("grade",grade)
                .setParameter("cid",cid)
                .setParameter("sid",sid)
                .executeUpdate();
        tx.commit();
        return row>0?new Message(1,"ok"):new Message(-2,"录入成绩失败");
    }

    public Map<String,List> queryGrade(int sid){
        Map<String,List> data = new HashMap<String, List>();
        Session session = getSession();
        List grades = session.createQuery("FROM CourseStudent WHERE sid = (:sid) and grade != 0")
                .setParameter("sid",sid).list();
        Integer[] cids = new Integer[grades.size()];
        for (int i=0;i<grades.size();i++){
            CourseStudent cs = (CourseStudent)grades.get(i);
            cids[i] = cs.getCid();
        }
        List courses;
        if (cids.length==0) courses = new ArrayList();
        else courses = session.createQuery("FROM Course WHERE id in (:cid)")
                .setParameterList("cid",cids).list();
        data.put("grades",grades);
        data.put("courses",courses);
        return data;
    }

}

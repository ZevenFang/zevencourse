package com.zeven.course.model;

import javax.persistence.*;

/**
 * Created by fangf on 2016/5/21.
 */
@Entity
@Table(name = "teacher_course", schema = "zevencourse", catalog = "")
@IdClass(TeacherCoursePK.class)
public class TeacherCourse {
    private int tid;
    private int cid;

    @Id
    @Column(name = "tid", nullable = false)
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    @Id
    @Column(name = "cid", nullable = false)
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeacherCourse that = (TeacherCourse) o;

        if (tid != that.tid) return false;
        if (cid != that.cid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tid;
        result = 31 * result + cid;
        return result;
    }
}

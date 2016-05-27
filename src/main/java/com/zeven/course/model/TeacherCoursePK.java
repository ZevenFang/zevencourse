package com.zeven.course.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by fangf on 2016/5/21.
 */
public class TeacherCoursePK implements Serializable {
    private int tid;
    private int cid;

    @Column(name = "tid", nullable = false)
    @Id
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    @Column(name = "cid", nullable = false)
    @Id
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

        TeacherCoursePK that = (TeacherCoursePK) o;

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

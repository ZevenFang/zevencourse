package com.zeven.course.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by fangf on 2016/5/21.
 */
public class CourseStudentPK implements Serializable {
    private int cid;
    private int sid;

    @Column(name = "cid", nullable = false)
    @Id
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Column(name = "sid", nullable = false)
    @Id
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseStudentPK that = (CourseStudentPK) o;

        if (cid != that.cid) return false;
        if (sid != that.sid) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = cid;
        result = 31 * result + sid;
        return result;
    }
}

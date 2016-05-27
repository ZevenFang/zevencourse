package com.zeven.course.model;

import javax.persistence.*;

/**
 * Created by fangf on 2016/5/21.
 */
@Entity
@Table(name = "course_student", schema = "zevencourse", catalog = "")
@IdClass(CourseStudentPK.class)
public class CourseStudent {
    private int cid;
    private int sid;
    private double grade;

    @Id
    @Column(name = "cid", nullable = false)
    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Id
    @Column(name = "sid", nullable = false)
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Basic
    @Column(name = "grade", precision = 1)
    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseStudent that = (CourseStudent) o;

        if (cid != that.cid) return false;
        if (sid != that.sid) return false;
        if (Double.compare(that.grade, grade) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = cid;
        result = 31 * result + sid;
        temp = Double.doubleToLongBits(grade);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

package com.zeven.course.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by fangf on 2016/5/21.
 */
@Entity
public class Course {
    private int id;
    private String cno;
    private String name;
    private byte weekStart;
    private byte weekEnd;
    private double credit;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cno", nullable = false, length = 45)
    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "week_start", nullable = false)
    public byte getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(byte weekStart) {
        this.weekStart = weekStart;
    }

    @Basic
    @Column(name = "week_end", nullable = false)
    public byte getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(byte weekEnd) {
        this.weekEnd = weekEnd;
    }

    @Basic
    @Column(name = "credit", nullable = false, precision = 1)
    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (weekStart != course.weekStart) return false;
        if (weekEnd != course.weekEnd) return false;
        if (Double.compare(course.credit, credit) != 0) return false;
        if (cno != null ? !cno.equals(course.cno) : course.cno != null) return false;
        if (name != null ? !name.equals(course.name) : course.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (cno != null ? cno.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) weekStart;
        result = 31 * result + (int) weekEnd;
        temp = Double.doubleToLongBits(credit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

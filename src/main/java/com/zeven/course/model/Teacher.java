package com.zeven.course.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by fangf on 2016/5/21.
 */
@Entity
public class Teacher {
    private int id;
    private String tno;
    private String password;
    private String name;
    private byte gender;
    private String notes;
    private byte isAdmin;
    private int deptId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "tno", nullable = false, length = 45)
    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
    @Column(name = "gender", nullable = false)
    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "notes", nullable = true, length = 255)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "is_admin", nullable = false)
    public byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Basic
    @Column(name = "dept_id", nullable = false)
    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;

        if (id != teacher.id) return false;
        if (gender != teacher.gender) return false;
        if (isAdmin != teacher.isAdmin) return false;
        if (deptId != teacher.deptId) return false;
        if (tno != null ? !tno.equals(teacher.tno) : teacher.tno != null) return false;
        if (password != null ? !password.equals(teacher.password) : teacher.password != null) return false;
        if (name != null ? !name.equals(teacher.name) : teacher.name != null) return false;
        if (notes != null ? !notes.equals(teacher.notes) : teacher.notes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tno != null ? tno.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) gender;
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (int) isAdmin;
        result = 31 * result + deptId;
        return result;
    }
}

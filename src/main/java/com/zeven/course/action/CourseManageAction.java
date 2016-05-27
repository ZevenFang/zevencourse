package com.zeven.course.action;

import com.zeven.course.model.Course;
import com.zeven.course.service.CourseService;
import com.zeven.course.util.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by fangf on 2016/5/19.
 */
@RequestMapping("/Admin/course")
@Controller
public class CourseManageAction {

    @Resource
    private CourseService service;

    @ResponseBody
    @RequestMapping("/getAll")
    public Message getAll(){
        Message msg = new Message(1,"ok");
        msg.setData(service.findAll());
        return msg;
    }

    @ResponseBody
    @RequestMapping("/add")
    public Message add(@RequestParam String cno, @RequestParam String name, @RequestParam Byte weekStart, @RequestParam Byte weekEnd, @RequestParam Double credit){
        if (service.isFieldExisted("cno",cno))
            return new Message(-1,"已存在课程号“"+cno+"”");
        if (service.isFieldExisted("name",name))
            return new Message(-1,"已存在课程“"+name+"”");
        Course course = new Course();
        course.setCno(cno);
        course.setName(name);
        course.setWeekStart(weekStart);
        course.setWeekEnd(weekEnd);
        course.setCredit(credit);
        service.save(course);
        return new Message(1,"ok");
    }

    @ResponseBody
    @RequestMapping("/del")
    public Message del(@RequestParam("ids[]") Integer[] ids){
        service.deleteByIds(ids);
        return new Message(1,"ok");
    }

    @ResponseBody
    @RequestMapping("/upd")
    public Message upd(@RequestParam int id, String cno, String name, Byte weekStart, Byte weekEnd, Double credit) {
        Course course = service.findById(id);
        if (cno != null && !course.getCno().equals(cno) && service.isFieldExisted("cno", cno))
            return new Message(-1, "已存在课程号“" + name + "”");
        if (!course.getName().equals(name) && service.isFieldExisted("name", name))
            return new Message(-1, "已存在课程“" + name + "”");
        if (cno != null) course.setCno(cno);
        if (name != null) course.setName(name);
        if (weekStart != null) course.setWeekStart(weekStart);
        if (weekEnd != null) course.setWeekEnd(weekEnd);
        if (credit != null) course.setCredit(credit);
        service.update(course);
        return new Message(1, "ok");
    }

}
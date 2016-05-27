package com.zeven.course.action;

import com.zeven.course.service.*;
import com.zeven.course.util.Auth;
import com.zeven.course.util.Message;
import com.zeven.course.util.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by fangf on 2016/5/22.
 */
@RequestMapping("/Teacher")
@Controller
public class TeacherAction {

    @Resource
    private CourseService courseService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherCourseService tcService;
    @Resource
    private ClassService classService;
    @Resource
    private CourseStudentService csService;

    @ResponseBody
    @RequestMapping("/course/getAll")
    public Message getAllCourse(){
        return new Message(1,"ok",courseService.getAvailableCourses());
    }

    @ResponseBody
    @RequestMapping("/course/selectCourses")
    public Message selectCourses(@RequestParam("ids[]") Integer[] ids, @RequestHeader("Authorization") String token){
        int userID = new Token(token).getId();
        return tcService.selectCourses(userID, ids);
    }

    @ResponseBody
    @RequestMapping("/course/unselectCourses")
    public Message unselectCourses(@RequestParam("ids[]") Integer[] ids, @RequestHeader("Authorization") String token){
        int userID = new Token(token).getId();
        return tcService.unselectCourses(userID, ids);
    }

    @ResponseBody
    @RequestMapping("/course/selectedCourses")
    public Message selectCourses(@RequestHeader("Authorization") String token){
        int id = new Token(token).getId();
        return new Message(1,"ok",courseService.findCoursesByTid(id));
    }

    @ResponseBody
    @RequestMapping("/grade/findStudentsByCid")
    public Message findStudentsByCid(@RequestParam int id){
        return new Message(1,"ok",studentService.findStudentsByCid(id));
    }

    @ResponseBody
    @RequestMapping("/class/getAll")
    public Message getAll(){
        return new Message(1,"ok",classService.findAll());
    }

    @ResponseBody
    @RequestMapping("/class/getGradeByCid")
    public Message getGradeByCid(@RequestParam int id){
        return new Message(1,"ok",csService.getGradeByCid(id));
    }

    @ResponseBody
    @RequestMapping("/grade/recordGrade")
    public Message recordGrade(@RequestParam int cid, @RequestParam int sid, @RequestParam double grade, @RequestHeader("Authorization") String token){
        return csService.recordGrade(new Token(token).getId(),cid,sid,grade);
    }

}

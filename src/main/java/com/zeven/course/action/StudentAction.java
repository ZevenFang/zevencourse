package com.zeven.course.action;

import com.zeven.course.model.CourseStudent;
import com.zeven.course.service.CourseService;
import com.zeven.course.service.CourseStudentService;
import com.zeven.course.service.StudentService;
import com.zeven.course.service.TeacherCourseService;
import com.zeven.course.util.Message;
import com.zeven.course.util.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fangf on 2016/5/22.
 */
@RequestMapping("/Student")
@Controller
public class StudentAction {

    private CourseService courseService = new CourseService();
    private TeacherCourseService tcService = new TeacherCourseService();
    private CourseStudentService csService = new CourseStudentService();

    @ResponseBody
    @RequestMapping("/course/getAll")
    public Message getAllCourse(){
        return new Message(1,"ok",tcService.getCoursesWithTeacher());
    }

    @ResponseBody
    @RequestMapping("/course/selectCourses")
    public Message selectCourses(@RequestParam("ids[]") Integer[] ids, @RequestHeader("Authorization") String token){
        int userID = new Token(token).getId();
        return csService.selectCourses(userID, ids);
    }

    @ResponseBody
    @RequestMapping("/course/unselectCourses")
    public Message unselectCourses(@RequestParam("ids[]") Integer[] ids, @RequestHeader("Authorization") String token){
        int userID = new Token(token).getId();
        return csService.unselectCourses(userID, ids);
    }

    @ResponseBody
    @RequestMapping("/course/selectedCourses")
    public Message selectCourses(@RequestHeader("Authorization") String token){
        int id = new Token(token).getId();
        return new Message(1,"ok",courseService.findCoursesBySid(id));
    }

    @ResponseBody
    @RequestMapping("/grade/queryGrade")
    public Message queryGrade(@RequestHeader("Authorization") String token){
        int id = new Token(token).getId();
        return new Message(1,"ok", csService.queryGrade(id));
    }


}

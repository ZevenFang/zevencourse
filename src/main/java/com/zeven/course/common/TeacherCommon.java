package com.zeven.course.common;

import com.zeven.course.model.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fangf on 2016/5/23.
 */
public class TeacherCommon {

    public static List<Map<String, Object>> teacherList(List<Teacher> teachers){
        List<Map<String, Object>> teacherList = new ArrayList<Map<String, Object>>();
        for (Teacher t:teachers){
            Map<String,Object> teacherMap = new HashMap<String, Object>();
            teacherMap.put("id",t.getId());
            teacherMap.put("name",t.getName());
            teacherList.add(teacherMap);
        }
        return teacherList;
    }

}

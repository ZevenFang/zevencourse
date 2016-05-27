package com.zeven.course.action;

import com.zeven.course.model.Clazz;
import com.zeven.course.service.ClassService;
import com.zeven.course.util.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fangf on 2016/5/20.
 */
@RequestMapping("/Admin/class")
@Controller
public class ClassManageAction {

    private ClassService service = new ClassService();

    @ResponseBody
    @RequestMapping("/getAll")
    public Message getAll(){
        return new Message(1,"ok",service.findAll());
    }

    @ResponseBody
    @RequestMapping("/add")
    public Message add(@RequestParam String name, @RequestParam int deptId){
        if (service.isFieldExisted("name",name))
            return new Message(-1,"已存在“"+name+"”");
        Clazz clazz = new Clazz();
        clazz.setName(name);
        clazz.setDeptId(deptId);
        service.save(clazz);
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
    public Message upd(@RequestParam int id,@RequestParam String name,@RequestParam int deptId){
        Clazz clazz = service.findById(id);
        if (!clazz.getName().equals(name)&&service.isFieldExisted("name",name))
            return new Message(-1,"已存在“"+name+"”");
        clazz.setName(name);
        clazz.setDeptId(deptId);
        service.update(clazz);
        return new Message(1,"ok");
    }

}

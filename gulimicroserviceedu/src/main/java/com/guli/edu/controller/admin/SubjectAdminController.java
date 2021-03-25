package com.guli.edu.controller.admin;

import com.guli.common.vo.R;
import com.guli.edu.entity.Subject;
import com.guli.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Api(description="课程分类管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/subject")
public class SubjectAdminController {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("import")
    public R addUser(
        @ApiParam(name = "file", value = "Excel文件", required = true)
        @RequestParam("file") MultipartFile file) throws Exception {
        List<String> msg = subjectService.batchImport(file);
        if(msg.size() == 0){
            return R.ok().message("批量导入成功");
        }else{
            return R.error().message("部分数据导入失败").data("messageList", msg);
        }
    }

    //获取所有的课程分类
    @ApiOperation(value = "获取所有的课程分类")
    @GetMapping
    public R getUser(){
        List<Subject> list = subjectService.list(null);
        return R.ok().data("list",list);
    }

}
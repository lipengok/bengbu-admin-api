package com.guli.edu.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.common.vo.R;
import com.guli.edu.entity.Teacher;
import com.guli.edu.query.TeacherQuery;
import com.guli.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description="讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin//解决跨域问题的注解（端口号不一致的问题）
public class TeacherAdminController {
    @Autowired
    private TeacherService teacherService;
    //模拟登陆
    @ApiOperation(value = "模拟登陆")
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin-token");
    }
    @ApiOperation(value = "模拟登陆信息")
    @GetMapping("info")
    //{"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}
    public R info(){
        return R.ok().data("roles","[admin]").data("introduction","I am a super administrator").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").data("name","Super Admin");
    }
    //获取所有讲师列表
    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public R list(){
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }
    //逻辑删除
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeById(
        @ApiParam(name = "id", value = "讲师ID", required = true)
        @PathVariable String id){
        teacherService.removeById(id);
        return R.ok();
    }
    //逻辑删除id数组
    @ApiOperation(value = "根据ID数组删除讲师")
    @PostMapping("ids")
    public R removeByIds(
            @ApiParam(name = "ids", value = "讲师ID数组", required = true)
            @RequestBody String[] ids){
        for (int i = 0; i <ids.length ; i++) {
            teacherService.removeById(ids[i]);
        }
        return R.ok();
    }
    //分页讲师列表
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{page}/{limit}")
    public R pageList(
        @ApiParam(name = "page", value = "当前页码", required = true)
        @PathVariable Long page,
        @ApiParam(name = "limit", value = "每页记录数", required = true)
        @PathVariable Long limit){
        Page<Teacher> pageParam = new Page<>(page, limit);
        teacherService.page(pageParam, null);
        List<Teacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }
    //分页讲师列表
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("MoreCondtionPageList/{page}/{limit}")
    public R pageQuery(
        @ApiParam(name = "page", value = "当前页码", required = true)
        @PathVariable Long page,
        @ApiParam(name = "limit", value = "每页记录数", required = true)
        @PathVariable Long limit,
        @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
        TeacherQuery teacherQuery){
        if(page <= 0 || limit <= 0){
            //throw new GuliException(21003, "参数不正确1");
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }
        Page<Teacher> pageParam = new Page<>(page, limit);
        teacherService.pageQuery(pageParam, teacherQuery);
        List<Teacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }
    //新增讲师
    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R save(
        @ApiParam(name = "teacher", value = "讲师对象", required = true)
        @RequestBody Teacher teacher){
        teacherService.save(teacher);
        return R.ok();
    }
    //根据ID查询讲师
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(
        @ApiParam(name = "id", value = "讲师ID", required = true)
        @PathVariable String id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }
    //根据ID修改讲师
    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("{id}")
    public R updateById(
        @ApiParam(name = "id", value = "讲师ID", required = true)
        @PathVariable String id,
        @ApiParam(name = "teacher", value = "讲师对象", required = true)
        @RequestBody Teacher teacher){
        teacher.setId(id);
        teacherService.updateById(teacher);
        return R.ok();
    }
}

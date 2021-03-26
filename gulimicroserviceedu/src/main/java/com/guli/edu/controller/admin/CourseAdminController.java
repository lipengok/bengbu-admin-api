package com.guli.edu.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.vo.R;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseInfoForm;
import com.guli.edu.query.CourseQuery;
import com.guli.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Api(description="课程管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/course")
public class CourseAdminController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
@ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
        @RequestBody CourseInfoForm courseInfoForm){
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        if(!StringUtils.isEmpty(courseId)){
            return R.ok().data("courseId", courseId);
        }else{
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "课程封面上传")
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        //产生唯一的图片地址
        Date date=new Date();
        String lastFileName=date.getTime()+fileName;
        String property = System.getProperty("user.dir");
        String filePath = property + "\\gulimicroserviceedu\\src\\main\\resources\\static\\img\\course";
        System.out.println(filePath);
        courseService.uploadCourse(file.getBytes(),filePath,lastFileName);
        return R.ok().data("cover",filePath+"\\"+lastFileName);
    }

    @ApiOperation(value = "获取课程封面图片路径")
    @GetMapping("/getImgPathById/{id}")
    public R getImgPathById(@PathVariable String id) throws IOException {
        Course course=courseService.getOne(new QueryWrapper<Course>().eq("id",id));
        String filePath=course.getCover();
        return R.ok().data("file",filePath);
    }

    @ApiOperation(value = "获取课程封面图片")
    @GetMapping("/getImgById/{id}")
    public R getImgById(@PathVariable String id) throws IOException {
        Course course=courseService.getOne(new QueryWrapper<Course>().eq("id",id));
        String filePath=course.getCover();
        File file=new File(filePath);
        FileInputStream input=new FileInputStream(file);
        input.close();
        return R.ok().data("file",input.toString());
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("course-info/{id}")
    public R getById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id){
        CourseInfoForm courseInfoForm = courseService.getCourseInfoFormById(id);
        return R.ok().data("item", courseInfoForm);
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
@ApiParam(name = "page", value = "当前页码", required = true)
        @PathVariable Long page,
@ApiParam(name = "limit", value = "每页记录数", required = true)
        @PathVariable Long limit,
@ApiParam(name = "courseQuery", value = "查询对象", required = false)
        CourseQuery courseQuery){
        Page<Course> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<Course> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
@ApiParam(name = "id", value = "课程ID", required = true)
        @PathVariable String id){
        boolean result = courseService.removeByCourseId(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}

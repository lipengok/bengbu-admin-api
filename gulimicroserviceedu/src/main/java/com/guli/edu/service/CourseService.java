package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.CourseInfoForm;
import com.guli.edu.query.CourseQuery;

import java.io.IOException;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Helen
 * @since 2021-01-10
 */
public interface CourseService extends IService<Course> {
    /**
     * 保存课程和课程详情信息
     * @param courseInfoForm
     * @return 新生成的课程id
     */
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    void uploadCourse(byte[] file, String filePath, String fileName) throws IOException;

    CourseInfoForm getCourseInfoFormById(String id);

    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    boolean removeByCourseId(String courseId);
    }

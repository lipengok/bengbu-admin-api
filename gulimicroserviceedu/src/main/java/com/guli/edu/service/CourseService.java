package com.guli.edu.service;

import com.guli.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.CourseInfoForm;

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
}

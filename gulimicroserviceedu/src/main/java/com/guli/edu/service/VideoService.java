package com.guli.edu.service;

import com.guli.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Helen
 * @since 2021-01-10
 */
public interface VideoService extends IService<Video> {

    boolean removeByCourseId(String courseId);

    boolean getCountByChapterId(String chapterId);
}

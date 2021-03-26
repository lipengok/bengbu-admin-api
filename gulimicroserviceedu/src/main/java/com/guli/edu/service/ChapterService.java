package com.guli.edu.service;

import com.guli.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.entity.vo.ChapterVo;

import java.util.ArrayList;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Helen
 * @since 2021-01-10
 */
public interface ChapterService extends IService<Chapter> {
    ArrayList<ChapterVo> nestedList(String courseId);

    boolean removeChapterById(String id);
}

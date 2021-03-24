package com.guli.edu.service;

import com.guli.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Helen
 * @since 2021-01-10
 */
public interface SubjectService extends IService<Subject> {
    List<String> batchImport(MultipartFile file);
}

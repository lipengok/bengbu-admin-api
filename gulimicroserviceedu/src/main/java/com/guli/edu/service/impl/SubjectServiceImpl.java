package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.edu.entity.Subject;
import com.guli.edu.mapper.SubjectMapper;
import com.guli.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.util.ExcelImportHSSFUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2021-01-10
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    @Transactional
    @Override
    public List<String> batchImport(MultipartFile file) {
        List<String> msg = new ArrayList<>();
        try {
            ExcelImportHSSFUtil excelHSSFUtil = new ExcelImportHSSFUtil(file.getInputStream());
            Sheet sheet = excelHSSFUtil.getSheet();
            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount <= 1) {
                msg.add("请填写数据");
                return msg;
            }
            for (int rowNum = 1; rowNum < rowCount; rowNum++) {
                Row rowData = sheet.getRow(rowNum);
                if (rowData != null) {// 行不为空
                    //一级分类名称
                    String levelOneValue = "";
                    Cell levelOneCell = rowData.getCell(0);
                    if(levelOneCell != null){
                        //社值为1，保证导入数据无差别
                        levelOneValue = excelHSSFUtil.getCellValue(levelOneCell,1);
                        if (StringUtils.isEmpty(levelOneValue)) {
                            msg.add("第" + rowNum + "行一级分类为空");
                            continue;
                        }
                    }
                    Subject subject = this.getByTitle(levelOneValue);
                    Subject subjectLevelOne = null;
                    String parentId = null;
                    if(subject == null){//创建一级分类
                        subjectLevelOne = new Subject();
                        subjectLevelOne.setTitle(levelOneValue);
                        subjectLevelOne.setSort(0);
                        baseMapper.insert(subjectLevelOne);//添加
                        parentId = subjectLevelOne.getId();
                    }else{
                        parentId = subject.getId();
                    }
                    //二级分类名称
                    String levelTwoValue = "";
                    Cell levelTwoCell = rowData.getCell(1);
                    if(levelTwoCell != null){
                        levelTwoValue = excelHSSFUtil.getCellValue(levelTwoCell,1);
                        if (StringUtils.isEmpty(levelTwoValue)) {
                            msg.add("第" + rowNum + "行二级分类为空");
                            continue;
                        }
                    }
                    Subject subjectSub = this.getSubByTitle(levelTwoValue, parentId);
                    Subject subjectLevelTwo = null;
                    if(subjectSub == null){//创建二级分类
                        subjectLevelTwo = new Subject();
                        subjectLevelTwo.setTitle(levelTwoValue);
                        subjectLevelTwo.setParentId(parentId);
                        subjectLevelTwo.setSort(0);
                        baseMapper.insert(subjectLevelTwo);//添加
                    }
                }
            }
        }catch (Exception e){
            //EXCEL_DATA_ERROR(false, 21005, "Excel数据导入错误");
            System.out.println(msg);
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
        return msg;
    }


/**
 * 根据分类名称查询这个一级分类中否存在
 * @param title
 * @return
 */
    private Subject getByTitle(String title) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", "0");
        return baseMapper.selectOne(queryWrapper);
    }
/**
 * 根据分类名称和父id查询这个二级分类中否存在
 * @param title
 * @return
 */
    private Subject getSubByTitle(String title, String parentId) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        return baseMapper.selectOne(queryWrapper);
    }
}

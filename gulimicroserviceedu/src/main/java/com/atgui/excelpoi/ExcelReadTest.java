package com.atgui.excelpoi;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

public class ExcelReadTest {
    //读取文件的标题
    @Test
    public void testRead03() throws Exception{
        InputStream is = new FileInputStream("E:/test/excel-poi/excel-03.xls");
        Workbook workbook = new HSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        // 读取第一行第一列
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);
        // 输出单元内容
        System.out.println(cell.getStringCellValue());
        // 操作结束，关闭文件
        is.close();
    }


    //读取表格里面的所有的内容
    @Test
    public void testCellType() throws Exception {
        InputStream is = new FileInputStream("E:/test/excel-poi/excel-03.xls");
        Workbook workbook = new HSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        // 读取标题所有内容
        Row rowTitle = sheet.getRow(0);
        if (rowTitle != null) {// 行不为空
            // 读取cell
            int cellCount = rowTitle.getPhysicalNumberOfCells();
            for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                Cell cell = rowTitle.getCell(cellNum);
                if (cell != null) {
                    int cellType = cell.getCellType();
                    String cellValue = cell.getStringCellValue();
                    System.out.print(cellValue + "|");
                }
            }
            System.out.println();
        }
        // 读取商品列表数据
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {// 行不为空
                // 读取cell
                int cellCount = rowTitle.getPhysicalNumberOfCells();
                for (int cellNum = 0; cellNum < cellCount; cellNum++) {
                    System.out.print("【" + (rowNum + 1) + "-" + (cellNum + 1) + "】");
                    Cell cell = rowData.getCell(cellNum);
                    if (cell != null) {
                        int cellType = cell.getCellType();
                        //判断单元格数据类型
                        String cellValue = "";
                        switch (cellType) {
                            case HSSFCell.CELL_TYPE_STRING://字符串
                                System.out.print("【STRING】");
                                cellValue = cell.getStringCellValue();
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN://布尔
                                System.out.print("【BOOLEAN】");
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_BLANK://空
                                System.out.print("【BLANK】");
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                System.out.print("【NUMERIC】");
                                //cellValue = String.valueOf(cell.getNumericCellValue());
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {//日期
                                    System.out.print("【日期】");
                                    Date date = cell.getDateCellValue();
                                    cellValue = new DateTime(date).toString("yyyy-MM-dd");
                                } else {
                                    // 不是日期格式，则防止当数字过长时以科学计数法显示
                                    System.out.print("【转换成字符串】");
                                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cellValue = cell.toString();
                                }
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                System.out.print("【数据类型错误】");
                                break;
                        }
                        System.out.println(cellValue);
                    }
                }
            }
        }
        is.close();
    }
}
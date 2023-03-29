package com.cnce.common.utils;


import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 解析Excel工具类
 * @param <T>
 */
public class ExcelAnalysis<T> {

    public static List<Map> readExcel(List<String> objList, InputStream is, String fileName) {
        List<Map> list = new ArrayList<>();
        Workbook workbook = createWorkbookByExcelType(is, fileName);

        Sheet sheet = workbook.getSheetAt(0);
        Row row = null;
        // 获取最大行数
        int maxRowNum = sheet.getLastRowNum();
        // 获取第一行
        row = sheet.getRow(0);
        // 获取最大列数
        int maxColNum = row.getLastCellNum();

        // 循环遍历excel表格，把每条数据封装成 map集合，再放入list集合中
        for (int i = 0; i <= maxRowNum; i++) {
            Map<String, String> map = new HashMap<>();
            row = sheet.getRow(i);
            if (row != null){
                for (int j = 0; j < maxColNum; j++) {
                    String cellData = (String)getCellValue(workbook, row.getCell(j));
                    map.put(objList.get(j), cellData);
                }
                list.add(map);
            }
        }
        return list;
    }


    //根据传入的文件名后缀判断是xls还是xlsx
    private static Workbook createWorkbookByExcelType(InputStream inputStream,String fileName){
        Workbook wb = null;
        if(fileName == null){
            return null;
        }
        String extString = fileName.substring(fileName.lastIndexOf("."));
        InputStream is = null;
        try {
            is = inputStream;
            if(".xls".equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return wb;
    }


    private static Object getCellValue(Workbook wb, Cell cell) {
        Object columnValue = null;
        if (cell != null) {
            DecimalFormat df = new DecimalFormat("0");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DecimalFormat nf = new DecimalFormat("0.00");
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    columnValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = df.format(cell.getNumericCellValue());
                    } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                        columnValue = nf.format(cell.getNumericCellValue());
                    } else {
                        columnValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    columnValue = cell.getBooleanCellValue();
                    break;
                case Cell.CELL_TYPE_BLANK:
                    columnValue = "";
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    // 格式单元格
                    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                    evaluator.evaluateFormulaCell(cell);
                    CellValue cellValue = evaluator.evaluate(cell);
                    columnValue = cellValue.getNumberValue();
                    break;
                default:
                    columnValue = cell.toString();
            }
        }
        return columnValue;
    }

}


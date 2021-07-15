package com.isechome.ecommerce.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@Component
public class ImportExcel {

    private static Calendar calendar = Calendar.getInstance();

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
    private static SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy/MM/dd");


    //解析excel文件
    public static HashMap<String, ArrayList<String[]>> analysisFile(MultipartFile file) throws IOException {
        HashMap<String, ArrayList<String[]>> hashMap = new HashMap<>();
        //获取workbook对象
        Workbook workbook = null;
        String filename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        //根据后缀名是否excel文件
        if(filename.endsWith("xls")){
            //2003
            workbook = new HSSFWorkbook(inputStream);
        }else if(filename.endsWith("xlsx")){
            //2007
            workbook = new XSSFWorkbook(inputStream);
        }

        //创建对象，把每一行作为一个String数组，所以数组存到集合中
        ArrayList<String[]> arrayList = new ArrayList<>();
        if(workbook != null){
            //循环sheet,现在是单sheet
            int sheetNum = 0;
            // for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获取第一个sheet
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if(sheet == null){
                    hashMap.put("文件sheet为空!",arrayList);
                    return hashMap;
                }
                //获取当前sheet开始行和结束行
                int firstRowNum = sheet.getFirstRowNum();
                int lastRowNum = sheet.getLastRowNum();
                //循环开始，除了前两行
                for(int rowNum = firstRowNum ;rowNum <= lastRowNum;rowNum++){
                    //获取当前行
                    Row row = sheet.getRow(rowNum);
                    //获取当前行的开始列和结束列
                    short firstCellNum = row.getFirstCellNum();
                    short lastCellNum = row.getLastCellNum();

                    //获取总行数
                    int lastCellNum2 = row.getPhysicalNumberOfCells();
                    String[] strings = new String[lastCellNum2];
                    //循环当前行
                    for(int cellNum = firstCellNum;cellNum < lastCellNum;cellNum++){
                        Cell cell = row.getCell(cellNum);
                        if( cell == null || "".equals(cell) || cell.getCellType()== Cell.CELL_TYPE_BLANK ){
                            hashMap.put("第"+(rowNum+1)+"行,第"+(cellNum+1)+"列为空",arrayList);
                            //return hashMap;
                        }
                        String  cellValue = "";
                        cellValue = getCellValue(cell);
                        strings[cellNum] = cellValue;
                    }
                    arrayList.add(strings);

                }
            //}
        }
        inputStream.close();
        hashMap.put("OK",arrayList);
        return hashMap;
    }

    //把每一个cell转换为string
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字转换成string，防止12.0这种情况
        if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
            cell.setCellType(cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: //数字0
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串1
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                //cellValue = String.valueOf(cell.getCellFormula());
                try {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    cellValue = String.valueOf(cell.getRichStringCellValue());
                }
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    //判断row是否为空
    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    //检查文件类型
    public static Boolean checkFile(MultipartFile file){
        //检查文件是否为空
        boolean empty = file.isEmpty();
        if(empty || file == null){
            return  false;
        }
        //检查文件是否是excel类型文件
        String filename = file.getOriginalFilename();
        if(!filename.endsWith("xls") && !filename.endsWith("xlsx")){
            return false;
        }
        return true;
    }

    //转换excel导入之后时间变为数字,月时间
    public static String getCorrectMonth(int i){
        calendar.set(1900,0,1);
        calendar.add(calendar.DATE,i);
        Date time = calendar.getTime();
        String s = simpleDateFormat.format(time);
        return s;
    }

    //转换excel导入之后时间变为数字,年月日时间
    public static String getCorrectDay(int i){
        calendar.set(1900,0,-1,0,0,0);
        calendar.add(calendar.DATE,i);
        Date time = calendar.getTime();
        String s = simpleDateFormat3.format(time);
        return s;
    }

    //获取当前时间的字符串
    public static String getNowDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }


    //文件读取到指定的位置
    public String saveFile(MultipartFile file) throws IOException {
        MultipartFile update = file;
        //文件中参数名字
        String name = update.getName();
        //文件名字
        String originalFilename = update.getOriginalFilename();
        //是否为空
        boolean empty = update.isEmpty();
        //传输文件到指定路径中
        String path = "F://LDJS/boco/uploading/"+originalFilename;
        update.transferTo(new File(path));
        //文件类型
        String contentType = update.getContentType();
        InputStream inputStream = update.getInputStream();
        inputStream.close();
        //是否存在此路径
        boolean path1 = new File(path).exists();
        if(path1){
            return "OK";
        }else{
            return "导入文件失败";
        }

    }

    //显示时间，把数字转换成时间类型的
    public static String getExcelDate(Cell cell){
        Date dateCellValue = cell.getDateCellValue();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(dateCellValue);
        return format;
    }


    public static String getDetailDate(String date){
        int dayNum = (int)Double.parseDouble(date);

        String s1 = "0."+ date.split("\\.")[1];
        String hour = Double.parseDouble(s1)*24 +"";
        int hourNum = Integer.parseInt(hour.split("\\.")[0]);

        String s2 = "0."+ hour.split("\\.")[1];
        String minte = Double.parseDouble(s2)*60 +"";
        int minteNum = Integer.parseInt(minte.split("\\.")[0]);

        String s3 = "0."+ minte.split("\\.")[1];
        String second = Double.parseDouble(s3)*60 +"";
        int secondNum = Integer.parseInt(second.split("\\.")[0]);
        calendar.set(1900,0,-1,0,0,0);
        calendar.add(calendar.DATE,dayNum);
        calendar.add(calendar.HOUR,hourNum);
        calendar.add(calendar.MINUTE,minteNum);
        calendar.add(calendar.SECOND,secondNum);
        Date time = calendar.getTime();
        String s = simpleDateFormat2.format(time);
        return s;
    }

    //检查是否是数字
    public static Boolean checkWhetherNumber(String str){
        try {
            BigDecimal bigDecimal = new BigDecimal(str);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    //检查是不是时间类型
    public static Boolean checkWhetherDate(String str){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(str);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    //检查是不是时间类型
    public static Boolean checkWhetherDate2(String str){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(str);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    //检查是不是月的时间类型
    public static Boolean checkWhetherMonth(String str){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");
            simpleDateFormat.setLenient(false);
            simpleDateFormat.parse(str);
        }catch (Exception e){
            return false;
        }
        return true;
    }




}
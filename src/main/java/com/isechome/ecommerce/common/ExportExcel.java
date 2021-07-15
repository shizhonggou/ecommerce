/*
 * @Author: xiangbin
 * @Date: 2021-04-29 17:21:22
 * @LastEditTime: 2021-04-29 18:07:36
 * @FilePath: \ess\src\main\java\cn\steelhome\ess\common\ExportExcel.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce.common;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ExportExcel  {
     // 显示的导出表的标题
  private String title;
  // 导出表的列名
  private String[] rowName;
  private List<Object[]> dataList = new ArrayList<Object[]>();

  // 构造函数，传入要导出的数据
  public ExportExcel(String title, String[] rowName, List<Object[]> dataList) {
    this.dataList = dataList;
    this.rowName = rowName;
    this.title = title;
  }

  // 导出数据
  public void export(OutputStream out) throws Exception {
    try {
      HSSFWorkbook workbook = new HSSFWorkbook();
      HSSFSheet sheet = workbook.createSheet(title);

      // 产生表格标题行
      HSSFRow rowm = sheet.createRow(0);
      HSSFCell cellTitle = rowm.createCell(0);
      
      
      //sheet样式定义【】
      HSSFCellStyle columnTopStyle=this.getColumnTopStyle(workbook);
      HSSFCellStyle style=this.getStyle(workbook);
      sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
      cellTitle.setCellStyle(columnTopStyle);
      cellTitle.setCellValue(title);

      // 定义所需列数
      int columnNum = rowName.length;
      HSSFRow rowRowName = sheet.createRow(2);

      // 将列头设置到sheet的单元格中
      for (int n = 0; n < columnNum; n++) {
        HSSFCell cellRowName = rowRowName.createCell(n);
        cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);
        HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
        cellRowName.setCellValue(text);
        cellRowName.setCellStyle(columnTopStyle);

      }
      // 将查询到的数据设置到sheet对应的单元格中
      for (int i = 0; i < dataList.size(); i++) {
        Object[] obj = dataList.get(i);// 遍历每个对象
        HSSFRow row = sheet.createRow(i + 3);// 创建所需的行数

        for (int j = 0; j < obj.length; j++) {
          HSSFCell cell = null;
          cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
          cell.setCellValue(obj[j].toString());
          // if (j == 0) {
          //   cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
          //   cell.setCellValue(i + 1);
          // } else {
          //   cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
          //   if (!"".equals(obj[j]) && obj[j] != null) {
          //     cell.setCellValue(obj[j].toString());
          //   }
          // }
          //cell.setCellStyle(style);

        }

      }

      // 让列宽随着导出的列长自动适应
      for (int colNum = 0; colNum < columnNum; colNum++) {
        int columnWidth = sheet.getColumnWidth(colNum) / 256;
        for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
          HSSFRow currentRow;
          if (sheet.getRow(rowNum) == null) {
            currentRow = sheet.createRow(rowNum);
          } else {
            currentRow = sheet.getRow(rowNum);
          }
          if (currentRow.getCell(colNum) != null) {
            HSSFCell currentCell = currentRow.getCell(colNum);
            if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
              int length = currentCell.getStringCellValue().getBytes().length;
              if (columnWidth < length) {
                columnWidth = length;
              }
            }
          }
        }
        if (colNum == 0) {
          sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
        } else {
          sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
        }
      }

      if (workbook != null) {
        try {

          workbook.write(out);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }

    } catch (Exception e) {

    }
  }
  /*
   * 列头单元格样式
   */
  public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
    // 设置字体
    HSSFFont font = workbook.createFont();

    // 设置字体大小
    font.setFontHeightInPoints((short) 11);
    // 字体加粗
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    // 设置字体名字
    font.setFontName("Courier New");
    // 设置样式
    HSSFCellStyle style = workbook.createCellStyle();
    // 设置低边框
    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    // 设置低边框颜色
    style.setBottomBorderColor(HSSFColor.BLACK.index);
    // 设置右边框
    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    // 设置顶边框
    style.setTopBorderColor(HSSFColor.BLACK.index);
    // 设置顶边框颜色
    style.setTopBorderColor(HSSFColor.BLACK.index);
    // 在样式中应用设置的字体
    style.setFont(font);
    // 设置自动换行
    style.setWrapText(false);
    // 设置水平对齐的样式为居中对齐；
    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    return style;

  }

  public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
    // 设置字体
    HSSFFont font = workbook.createFont();
    // 设置字体大小
    font.setFontHeightInPoints((short) 10);
    // 字体加粗
    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    // 设置字体名字
    font.setFontName("Courier New");
    // 设置样式;
    HSSFCellStyle style = workbook.createCellStyle();
    // 设置底边框;
    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
    // 设置底边框颜色;
    style.setBottomBorderColor(HSSFColor.BLACK.index);
    // 设置左边框;
    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    // 设置左边框颜色;
    style.setLeftBorderColor(HSSFColor.BLACK.index);
    // 设置右边框;
    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
    // 设置右边框颜色;
    style.setRightBorderColor(HSSFColor.BLACK.index);
    // 设置顶边框;
    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
    // 设置顶边框颜色;
    style.setTopBorderColor(HSSFColor.BLACK.index);
    // 在样式用应用设置的字体;
    style.setFont(font);
    // 设置自动换行;
    style.setWrapText(false);
    // 设置水平对齐的样式为居中对齐;
    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    // 设置垂直对齐的样式为居中对齐;
    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    return style;
  }
 
}

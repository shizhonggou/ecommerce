/*
 * @Author: shitaodi
 * @Date: 2021-09-02 16:29:25
 * @LastEditTime: 2021-09-02 16:30:36
 * @FilePath: \e-commerce\src\test\java\com\isechome\ecommerce\TestPdf2.java
 * @Description: 
 * @Copyright: © 2021, SteelHome. All rights reserved.
 */
package com.isechome.ecommerce;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: pdf
 * @description: testPdf
 * @author: Mr. shizg
 * @create: 2021-08-18 14:52
 **/
class TestPdf2 {
    public static void main(String[] args) throws Exception {

        TestPdf2 pdf = new TestPdf2();
        String filename = "G:/shizg/temp/testTable3.pdf";
        pdf.createPDF(filename);
        System.out.println("打印完成");

    }

    public static PdfPTable createTable(PdfWriter writer) throws DocumentException, IOException{
        PdfPTable table = new PdfPTable(2);//生成一个两列的表格
        PdfPCell cell;
        int size = 20;
        cell = new PdfPCell(new Phrase("one"));
        cell.setFixedHeight(size);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("two"));
        cell.setFixedHeight(size);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("three"));
        cell.setFixedHeight(size);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("four"));
        cell.setFixedHeight(size);
        table.addCell(cell);
        return table;
    }

    public void createPDF(String filename) throws IOException {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.addTitle("example of PDF");
            document.open();
            PdfPTable table = createTable(writer);
            document.add(table);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}

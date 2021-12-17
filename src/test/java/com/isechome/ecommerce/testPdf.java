package com.isechome.ecommerce;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
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
class TestPdf {
    public static void main(String[] args) throws Exception {

        TestPdf pdf = new TestPdf();
        String filename = "G:/shizg/temp/testTable3.pdf";
        pdf.createPDF(filename);
        System.out.println("打印完成");

    }
    public void createPDF(String filename) throws IOException {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.addTitle("example of PDF");
            document.open();
            document.add(new Paragraph("Hello World!"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}

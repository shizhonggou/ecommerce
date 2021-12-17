package com.isechome.ecommerce.util;

import com.isechome.ecommerce.entity.*;
import com.itextpdf.text.Element;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import com.isechome.ecommerce.entity.ScShelfResource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.hutool.core.date.DateUtil;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.List;

/**
 * @program: pdf
 * @description: 生成pdf
 * @author: Mr. shizg
 * @create: 2021-08-18 16:42
 **/
public class PdfUtil {

    public void createPDF(Document document, PdfWriter writer, Map<String, Object> model) throws IOException {

        try {

            document.addAuthor("scurry");
            document.addSubject("product sheet.");
            document.addKeywords("product.");
            document.open();

            if (Integer.parseInt(model.get("type").toString()) == 1) {
                document.addTitle("提货单");
                PdfPTable table1 = createTable1(writer, model);
                document.add(table1);
                PdfPTable table = createTable(writer, model);
                document.add(table);
                PdfPTable table2 = createTable2(writer, model);
                document.add(table2);
            } else if (Integer.parseInt(model.get("type").toString()) == 2) {
                document.addTitle("合同");
                PdfPTable table3 = createTable3(writer, model);
                document.add(table3);
                PdfPTable table4 = createTable4(writer, model);
                document.add(table4);
                PdfPTable table5 = createTable5(writer, model);
                document.add(table5);
                PdfPTable table6 = createTable6(writer, model);
                document.add(table6);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public static PdfPTable createTable6(PdfWriter writer, Map<String, Object> model)
            throws IOException, DocumentException {
        PdfPTable table = new PdfPTable(new float[] { 20.0f, 180.0f, 100.0f, 20.0f, 180.0f, 100.0f });// 生成一个两列的表格
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setTotalWidth(789.0f);
        table.setLockedWidth(true);
        PdfPCell cell;
        int size = 18;
        // BaseFont bfOne = BaseFont.createFont("/usr/local/www/e-commerce/simsun.ttf",
        // BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        BaseFont bfOne = BaseFont.createFont("simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        Font font = new Font(bfOne, 10, 0, Color.decode("#D6565A"));
        Font font2 = new Font(bfOne, 12, Font.BOLD, Color.decode("#D6565A"));
        Font font3 = new Font(bfOne, 10, Font.BOLD, Color.decode("#D6565A"));
        Font font6 = new Font(bfOne, 10, Font.BOLD, Color.decode("#505050"));
        ScOrderListBase scOrderListBase = (ScOrderListBase) model.get("orderlistbase");
        SysCompany pur_company = (SysCompany) model.get("pur_company");

        cell = new PdfPCell(new Phrase("收货单位", font2));
        cell.setRowspan(4);
        cell.setFixedHeight(size * 4);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p1 = new Paragraph();
        p1.add(new Chunk("全称：", font3));
        p1.add(new Chunk(pur_company.getComname(), font6));
        cell = new PdfPCell(p1);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p2 = new Paragraph();
        p2.add(new Chunk("编码：", font3));
        p2.add(new Chunk(pur_company.getMgId() == null ? "" : pur_company.getMgId(), font6));
        cell = new PdfPCell(p2);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("付款单位", font2));

        cell.setRowspan(5);
        cell.setFixedHeight(size * 5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p3 = new Paragraph();
        p3.add(new Chunk("全称：", font3));
        p3.add(new Chunk(pur_company.getComname(), font6));
        cell = new PdfPCell(p3);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p4 = new Paragraph();
        p4.add(new Chunk("编码：", font3));
        p4.add(new Chunk(pur_company.getMgId() == null ? "" : pur_company.getMgId(), font6));
        cell = new PdfPCell(p4);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p5 = new Paragraph();
        p5.add(new Chunk("地址：", font3));
        p5.add(new Chunk(pur_company.getAddress(), font6));
        cell = new PdfPCell(p5);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p6 = new Paragraph();
        p6.add(new Chunk("邮编：", font3));
        p6.add(new Chunk(pur_company.getPostcode(), font6));
        cell = new PdfPCell(p6);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p7 = new Paragraph();
        p7.add(new Chunk("地址：", font3));
        p7.add(new Chunk(pur_company.getAddress(), font6));
        cell = new PdfPCell(p7);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p8 = new Paragraph();
        p8.add(new Chunk("邮编：", font3));
        p8.add(new Chunk(pur_company.getPostcode(), font6));
        cell = new PdfPCell(p8);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p9 = new Paragraph();
        p9.add(new Chunk("到站(港)：", font3));
        p9.add(new Chunk(scOrderListBase.getDestination() == null ? "自提" : scOrderListBase.getDestination(), font6));
        cell = new PdfPCell(p9);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p10 = new Paragraph();
        p10.add(new Chunk("电话：", font3));
        p10.add(new Chunk(pur_company.getContacttel(), font6));
        cell = new PdfPCell(p10);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p11 = new Paragraph();
        p11.add(new Chunk("账户：", font3));
        p11.add(new Chunk(pur_company.getBankaccount(), font6));
        cell = new PdfPCell(p11);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p12 = new Paragraph();
        p12.add(new Chunk("电话：", font3));
        p12.add(new Chunk(pur_company.getContacttel(), font6));
        cell = new PdfPCell(p12);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p13 = new Paragraph();
        p13.add(new Chunk("专用线：", font3));
        p13.add(new Chunk(scOrderListBase.getScOrderListDetail().get(0).getScShelfResource().getWarehouse(), font6));
        cell = new PdfPCell(p13);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p14 = new Paragraph();
        p14.add(new Chunk("生产厂：", font3));
        p14.add(new Chunk("销售公司工厂", font6));
        cell = new PdfPCell(p14);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p15 = new Paragraph();
        p15.add(new Chunk("开户行：", font3));
        p15.add(new Chunk(pur_company.getBanktype(), font6));
        cell = new PdfPCell(p15);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p16 = new Paragraph();
        p16.add(new Chunk("传真：", font3));
        p16.add(new Chunk(pur_company.getContactfax(), font6));
        cell = new PdfPCell(p16);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("补充说明：", font));
        cell.setColspan(3);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p17 = new Paragraph();
        p17.add(new Chunk("纳税人登记号：", font3));
        p17.add(new Chunk(pur_company.getTaxno(), font6));
        cell = new PdfPCell(p17);
        cell.setColspan(2);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p18 = new Paragraph();
        p18.add(new Chunk("打印日期：", font3));
        p18.add(new Chunk(DateUtil.today(), font6));
        cell = new PdfPCell(p18);
        cell.setColspan(6);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
        return table;
    }

    public static PdfPTable createTable5(PdfWriter writer, Map<String, Object> model)
            throws IOException, DocumentException {
        PdfPTable table = new PdfPTable(new float[] { 20.0f, 70.0f, 70.0f, 70.0f, 70.0f, 70.0f, 70.0f, 160.0f });// 生成一个两列的表格
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setTotalWidth(789.0f);
        table.setLockedWidth(true);
        PdfPCell cell;
        int size = 16;

        // BaseFont bfOne = BaseFont.createFont("/usr/local/www/e-commerce/simsun.ttf",
        // BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        BaseFont bfOne = BaseFont.createFont("simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfOne, 10, 0, Color.decode("#D6565A"));
        ScOrderListBase scOrderListBase = (ScOrderListBase) model.get("orderlistbase");

        cell = new PdfPCell(new Phrase("序号", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("品 名", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("规 格", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("物料号", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("价格(元/吨)", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("订货数量(吨)", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("金 额(元)", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("说 明", font));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        int num = 1;
        Double num_sum = (double) 0;
        Double money_sum = (double) 0;
        for (int i = 0; i < 11; i++) {
            cell = new PdfPCell(new Phrase(String.valueOf(i == 10 ? "合计" : num), font));
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorderColor(Color.decode("#D6565A"));
            table.addCell(cell);

            ScOrderListDetail detail = null;
            if (i <= scOrderListBase.getScOrderListDetail().size() - 1) {
                detail = scOrderListBase.getScOrderListDetail().get(i);
            }

            if (detail != null) {
                num_sum = add(num_sum, detail.getNum());
                money_sum = add(money_sum, mul(detail.getNum(), detail.getPrice()));
            }

            cell = new PdfPCell(new Phrase(detail == null ? "" : detail.getScShelfResource().getVarietyName(), font));
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorderColor(Color.decode("#D6565A"));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(detail == null ? "" : detail.getScShelfResource().getSpecification(), font));
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorderColor(Color.decode("#D6565A"));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(detail == null ? "" : detail.getScShelfResource().getMaterial(), font));
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorderColor(Color.decode("#D6565A"));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(detail == null ? "" : detail.getPrice().toString(), font));
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorderColor(Color.decode("#D6565A"));
            table.addCell(cell);

            if (i == 10) {
                cell = new PdfPCell(new Phrase(num_sum.toString(), font));
            } else {
                cell = new PdfPCell(new Phrase(detail == null ? "" : detail.getNum().toString(), font));
            }
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorderColor(Color.decode("#D6565A"));
            table.addCell(cell);

            if (i == 10) {
                cell = new PdfPCell(new Phrase(money_sum.toString(), font));
            } else {
                cell = new PdfPCell(new Phrase(
                        detail == null ? "" : String.valueOf(mul(detail.getNum(), detail.getPrice())), font));
            }
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorderColor(Color.decode("#D6565A"));
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", font));
            cell.setFixedHeight(size);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorderColor(Color.decode("#D6565A"));
            table.addCell(cell);
            num++;
        }
        return table;
    }

    public static PdfPTable createTable3(PdfWriter writer, Map<String, Object> model)
            throws IOException, DocumentException {
        PdfPTable table = new PdfPTable(new float[] { 20.0f, 70.0f, 50.0f, 160.0f, 80.0f, 50.0f });// 生成一个两列的表格
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setTotalWidth(789.0f);
        table.setLockedWidth(true);
        PdfPCell cell;
        int size = 20;
        // BaseFont bfOne = BaseFont.createFont("/usr/local/www/e-commerce/simsun.ttf",
        // BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        BaseFont bfOne = BaseFont.createFont("simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font1 = new Font(bfOne, 18, 1, Color.decode("#D6565A"));
        Font font2 = new Font(bfOne, 12, Font.BOLD, Color.decode("#D6565A"));
        Font font3 = new Font(bfOne, 10, Font.BOLD, Color.decode("#D6565A"));
        Font font4 = new Font(bfOne, 16, Font.BOLD, Color.decode("#D6565A"));
        Font font5 = new Font(bfOne, 9, 1, Color.decode("#D6565A"));
        Font font6 = new Font(bfOne, 10, Font.BOLD, Color.decode("#505050"));
        ScOrderListBase scOrderListBase = (ScOrderListBase) model.get("orderlistbase");
        SysCompany pt_company = (SysCompany) model.get("pt_company");
        SysCompany pur_company = (SysCompany) model.get("pur_company");

        cell = new PdfPCell(new Phrase(pt_company.getComname(), font1));
        cell.setColspan(6);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("签订", font2));
        cell.setRowspan(2);
        cell.setFixedHeight(size * 2);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMM");

        Paragraph p = new Paragraph();
        p.add(new Chunk("日期：", font3));
        p.add(new Chunk(sdf2.format(scOrderListBase.getCreatTime()), font6));
        cell = new PdfPCell(p);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", font3));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(sdf.format(scOrderListBase.getCreatTime()) + "年产品销售合同", font4));
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("合同号：", font3));
        cell.setColspan(2);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p1 = new Paragraph();
        p1.add(new Chunk("地点：", font3));
        p1.add(new Chunk(pt_company.getCity(), font6));
        cell = new PdfPCell(p1);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p2 = new Paragraph();
        p2.add(new Chunk("有效期：", font3));
        p2.add(new Chunk(sdf3.format(scOrderListBase.getCreatTime()), font6));
        cell = new PdfPCell(p2);
        cell.setFixedHeight(size);
        cell.setBorder(Rectangle.TOP);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("履约地：" + pt_company.getCity(), font3));
        cell.setFixedHeight(size);
        cell.setBorder(Rectangle.TOP);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p3 = new Paragraph();
        p3.add(new Chunk("分销渠道：", font3));
        p3.add(new Chunk("厂内直发(现货中心)", font6));
        cell = new PdfPCell(p3);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p4 = new Paragraph();
        p4.add(new Chunk("交货月份：", font3));
        p4.add(new Chunk(sdf3.format(scOrderListBase.getCreatTime()), font6));
        cell = new PdfPCell(p4);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);
        return table;
    }

    public static PdfPTable createTable4(PdfWriter writer, Map<String, Object> model)
            throws IOException, DocumentException {
        PdfPTable table = new PdfPTable(new float[] { 290.0f, 10.0f, 100.0f, 100.0f });// 生成一个两列的表格
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setTotalWidth(789.0f);
        table.setLockedWidth(true);
        PdfPCell cell;
        int size = 20;
        // BaseFont bfOne = BaseFont.createFont("/usr/local/www/e-commerce/simsun.ttf",
        // BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        BaseFont bfOne = BaseFont.createFont("simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfOne, 10, 0, Color.decode("#D6565A"));
        Font font3 = new Font(bfOne, 10, Font.BOLD, Color.decode("#D6565A"));
        Font font6 = new Font(bfOne, 10, Font.BOLD, Color.decode("#505050"));
        SysCompany pt_company = (SysCompany) model.get("pt_company");
        SysCompany pur_company = (SysCompany) model.get("pur_company");

        Paragraph p = new Paragraph();
        p.setLeading(100f, 100f);
        p.setAlignment(Element.ALIGN_JUSTIFIED);
        p.setSpacingAfter(50);
        p.setSpacingBefore(50);

        Chunk chunk = new Chunk("合同条款内容：\n   1、交货、运输方式和费用负担：卖方代办运输，买方承担运输费用。凡合同配载不足额定车载量也可装运，"
                + "\n空载费、运杂费均由买方承担。代办非铁路运输方式，买卖双方另行签订代办运输协议。" + "\n    2、检验方式及检验期限：由合同规定的收货方按卖货方质量证明书验收，如发生计量和质量问题需在货到"
                + "\n收货单位后10天内向卖方提出详细书面异议资料，并保存原货物，方可受理。" + "\n    3、合同变更：变更合同事宜，买方须在交货期前35天提出协商。"
                + "\n    4、买方如延付、拒付货款，卖方有权解除本合同并要求买方承担由此给卖方造成的一切损失。" + "\n    5、价格：价格为不含税价，增值税按国家税法规定另收，合同金额为不含税金额。   "
                + "\n    6、解决合同纠纷方式：发生争议，买卖双方协商解决，协商不成，向卖方所在地人民法院起诉。 " + "\n    7、违约责任：按《中华人民共和国合同法》及有关规定执行。"
                + "\n    8、结算方式及付款期限：除买卖双方另有约定外，买方须全额支付预付款。", font);

        p.add(chunk);
        cell = new PdfPCell(p);
        cell.setRowspan(8);
        cell.setFixedHeight(size * 6);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("卖方单位", font3));
        cell.setRowspan(4);
        cell.setFixedHeight(size);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("名称：" + pt_company.getComname(), font3));
        cell.setColspan(2);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(
                new Phrase("地址：" + pt_company.getAddress() + "       邮编：" + pt_company.getPostcode(), font));
        cell.setColspan(2);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("传真：" + pt_company.getContactfax(), font));
        cell.setColspan(2);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(
                "销售服务中心电话：" + pt_company.getContacttel() + "        卖方代理人：" + pt_company.getLinkman(), font3));
        cell.setFixedHeight(size);
        cell.setColspan(2);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("买方单位", font3));
        cell.setRowspan(4);
        cell.setFixedHeight(size * 4);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p1 = new Paragraph();
        p1.add(new Chunk("名称：", font3));
        p1.add(new Chunk(pur_company.getComname(), font6));
        cell = new PdfPCell(p1);
        cell.setColspan(2);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p2 = new Paragraph();
        p2.add(new Chunk("邮编：", font3));
        p2.add(new Chunk(pur_company.getPostcode(), font6));
        cell = new PdfPCell(p2);
        cell.setColspan(2);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p3 = new Paragraph();
        p3.add(new Chunk("地址：", font3));
        p3.add(new Chunk(pur_company.getAddress(), font6));
        cell = new PdfPCell(p3);
        cell.setColspan(2);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);

        Paragraph p4 = new Paragraph();
        p4.add(new Chunk("代理人：", font3));
        p4.add(new Chunk(pur_company.getLinkman(), font6));
        cell = new PdfPCell(p4);
        cell.setColspan(2);
        cell.setFixedHeight(size);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(Color.decode("#D6565A"));
        table.addCell(cell);
        return table;
    }

    public static PdfPTable createTable(PdfWriter writer, Map<String, Object> model)
            throws IOException, DocumentException {
        PdfPTable table = new PdfPTable(new float[] { 150, 80, 80, 80, 80, 80, 80, 80, 100 });// 生成一个两列的表格
        table.setTotalWidth(750.0f);
        table.setLockedWidth(true);

        String chineseNum = (String) model.get("chineseNum");
        PdfPCell cell;
        int size = 20;
        // BaseFont bfOne = BaseFont.createFont("/usr/local/www/e-commerce/simsun.ttf",
        // BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        BaseFont bfOne = BaseFont.createFont("simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfOne, 11, Font.BOLD);
        Font font1 = new Font(bfOne, 11);

        ScOrderListBase scOrderListBase = (ScOrderListBase) model.get("sheet");
        List<ScShelfResource> resourceList = (List<ScShelfResource>) model.get("resourceList");
        List<LogisticsPurchase> purchaseList = (List<LogisticsPurchase>) model.get("purchaseList");

        cell = new PdfPCell(new Phrase("资源编号", font));
        cell.setRowspan(2);
        cell.setFixedHeight(size * 2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("品名", font));
        cell.setRowspan(2);
        cell.setFixedHeight(size * 2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("牌号", font));
        cell.setRowspan(2);
        cell.setFixedHeight(size * 2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("规格", font));
        cell.setRowspan(2);
        cell.setFixedHeight(size * 2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("产地", font));
        cell.setRowspan(2);
        cell.setFixedHeight(size * 2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("数量（吨）", font));

        cell.setColspan(2);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("批次号", font));
        cell.setRowspan(2);
        cell.setFixedHeight(size * 2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("备注", font));
        cell.setRowspan(2);
        cell.setFixedHeight(size * 2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("应发", font));
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("实发", font));
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        for (int i = 0; i < resourceList.size(); i++) {
            cell = new PdfPCell(new Phrase(purchaseList.get(i).getExtractId(), font1));
            cell.setFixedHeight(size);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(resourceList.get(i).getVarietyName(), font1));
            cell.setFixedHeight(size);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(resourceList.get(i).getMaterial(), font1));
            cell.setFixedHeight(size);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(resourceList.get(i).getSpecification(), font1));
            cell.setFixedHeight(size);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(resourceList.get(i).getFactory(), font1));
            cell.setFixedHeight(size);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            String num = String.valueOf(purchaseList.get(i).getNum());
            cell = new PdfPCell(new Phrase(num, font1));
            cell.setFixedHeight(size);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            String actualnum = String.valueOf(purchaseList.get(i).getActualNum() != null
                    ? (purchaseList.get(i).getActualNum() > 0 ? purchaseList.get(i).getActualNum() : "")
                    : "");
            cell = new PdfPCell(new Phrase(actualnum, font1));
            cell.setFixedHeight(size);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", font1));
            cell.setFixedHeight(size);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("", font1));
            cell.setFixedHeight(size);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
        }

        cell = new PdfPCell(new Phrase("应发吨位合计(大写)：", font1));
        cell.setColspan(4);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(chineseNum, font1));
        cell.setColspan(5);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        return table;
    }

    public static PdfPTable createTable2(PdfWriter writer, Map<String, Object> model)
            throws IOException, DocumentException {
        PdfPTable table = new PdfPTable(new float[] { 320, 50, 180 });// 生成一个两列的表格

        table.setTotalWidth(750.0f);
        table.setLockedWidth(true);

        PdfPCell cell;
        int size = 80;
        Font font = new Font(BaseFont.createFont("simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 10);
        Font font2 = new Font(BaseFont.createFont("simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED), 12);

        String username = (String) model.get("username");

        cell = new PdfPCell(new Phrase("", font));
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(30);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(
                "说明:\n    1、按品名、牌号、规格、数量和批次发货，不得错发、超发。\n    2、从开单之日起，限 日内提货，逾期或隔月此单无效。\n    3、加盖产品自提单专用章后，方能发货，提单中打印内容一经涂改，此单无效。",
                font));
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("说明", font));
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", font));
        cell.setFixedHeight(size);

        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("制单：", font2));
        cell.setFixedHeight(40);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("提货人：                  发货人：", font2));
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(40);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        return table;

    }

    public static PdfPTable createTable1(PdfWriter writer, Map<String, Object> model)
            throws IOException, DocumentException {
        PdfPTable table = new PdfPTable(new float[] { 200.0f, 220.0f, 110.0f });// 生成一个两列的表格
        table.setTotalWidth(750.0f);
        table.setLockedWidth(true);
        PdfPCell cell;
        int size = 20;
        // BaseFont bfOne = BaseFont.createFont("/usr/local/www/e-commerce/simsun.ttf",
        // BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        BaseFont bfOne = BaseFont.createFont("simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        Font font = new Font(bfOne, 8, Font.NORMAL);
        Font font1 = new Font(bfOne, 16);
        Font font2 = new Font(bfOne, 20, Font.BOLD);
        Font font3 = new Font(bfOne, 12);
        ScOrderListBase scOrderListBase = (ScOrderListBase) model.get("sheet");
        List<LogisticsPurchase> purchaseList = (List<LogisticsPurchase>) model.get("purchaseList");
        String sold_company = (String) model.get("sold_company");
        String purchase_company = (String) model.get("purchase_company");

        cell = new PdfPCell(new Paragraph(sold_company, font1));
        cell.setPaddingLeft(110);
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        System.out.println("3");
        cell = new PdfPCell(new Phrase("质量记录", font));
        cell.setBorder(Rectangle.NO_BORDER);

        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        Paragraph p1 = new Paragraph();
        p1.add(new Chunk("提货单号：", font));
        p1.add(new Chunk(purchaseList.get(0).getExtractId(), font3));
        cell = new PdfPCell(p1);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("", font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("编号：", font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("收货单位：" + purchase_company, font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("产品提货单", font2));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setPaddingLeft(20);
        cell.setFixedHeight(size);
        cell.setRowspan(2);
        table.addCell(cell);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cell = new PdfPCell(new Phrase("开单日期：" + sdf.format(scOrderListBase.getCreatTime()), font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("订单号：" + scOrderListBase.getOrderId(), font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("库存地点：" + purchaseList.get(0).getDepository(), font));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        return table;
    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

}

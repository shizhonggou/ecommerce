package com.isechome.ecommerce.util;


import com.isechome.ecommerce.entity.ScOrderListDetail;
import com.isechome.ecommerce.entity.ScShelfResource;
// import com.itextpdf.*;
// import com.itextpdf.text.pdf.*;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
// import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.DocumentException;

import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
// import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;

// import org.springframework.core.io.ClassPathResource;

// import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.List;

/**
 * @program: pdf
 * @description: 生成pdf
 * @author: Mr. shizg
 * @create: 2021-08-18 16:42
 **/
public class orderDetailPdf {
    public void createPDF( Document document,PdfWriter writer,Map<String, Object> model) throws IOException {
        try {

            document.addTitle("sheet of product");
            document.addAuthor("scurry");
            document.addSubject("product sheet.");
            document.addKeywords("product.");
            document.open();
            // PdfPTable table1 = createTable(writer,model);
            // document.add(table1);

           generatePDF(document,writer,model);
            // try {
            //     generatePDF(document,writer,model);
            // } catch (Exception e) {
            //     // TODO Auto-generated catch block
            //     e.printStackTrace();
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            document.close();
        }
    }



    // 生成PDF文件
    public void generatePDF(Document document,PdfWriter writer,Map<String,Object> model) throws Exception {

        int j = 0;
        DecimalFormat    df   = new DecimalFormat("######0.00");  

        Date kpdate=(Date)model.get("kpdate");
        String sdf = new SimpleDateFormat("yyyy-MM-dd").format(kpdate);
       
       // cell = new PdfPCell(new Phrase("填开日期"+ sdf.format(kpdate), font2));
        List<ScOrderListDetail> dataList=(List<ScOrderListDetail>) model.get("sheet");
        //分批处理
        if (null != dataList && dataList.size() > 0) {
            Double SettlementMoneySum=0.0;
            Double shuiMoneySum=0.0;
            for (ScOrderListDetail scOrderListDetail : dataList) {
                SettlementMoneySum+=scOrderListDetail.getSettlementMoney();
                //shuiMoneySum+=scOrderListDetail.getSettlementMoney()*0.13;
            }
            shuiMoneySum=SettlementMoneySum*0.13;
          
            int pointsDataLimit = 20;//限制条数(每页显示条数)
            Integer size = dataList.size();
            //判断是否有必要分批
            if (pointsDataLimit < size) {
                 part = size / pointsDataLimit;//分批数（分几页）
                 System.out.println("分页"+part);
                for (int i = 0; i < part; i++) {
                    j++;
                    document.newPage();
                    /* 引入头部 */
                    PdfPTable headertab=  header(writer,model,document,j);
                    document.add(headertab);
                    //todo 表格内容填充
                    PdfPTable table = tbPublic();
                    List<ScOrderListDetail> listPage = dataList.subList(0, pointsDataLimit);
                    Integer key=0;
                    Double SettlementMoney=0.0;
                    Double shuiMoney=0.0;
                  
                    for (ScOrderListDetail scOrderListDetail : listPage) {
                        key++;
                        SettlementMoney+=scOrderListDetail.getSettlementMoney();
                       // shuiMoney+=scOrderListDetail.getSettlementMoney()*0.13;
                        table= tableDetail(table,scOrderListDetail,key);
                       
                    }
                    shuiMoney=SettlementMoney*0.13;
                    //剔除
                    dataList.subList(0, pointsDataLimit).clear();
                    tbPublicfooter(table,String.valueOf(SettlementMoney),df.format(shuiMoney),String.valueOf(SettlementMoneySum),df.format(shuiMoneySum));
                    document.add(table); //表格数据
                    /* 引入底部 */
                    footer(document,sdf);
                    //.onEndPage(writer,document); //当前页码
                    
                }
            } else {
                part=1;
                j=1;
                List<ScOrderListDetail> listPage = dataList.subList(0, dataList.size());
                document.newPage();
                /* 引入头部 */
                PdfPTable headertab=  header(writer,model,document,j);
                document.add(headertab);
                PdfPTable table = tbPublic();
              //  System.out.println("table初始化"+table);
                Integer key=0;
                Double SettlementMoney=0.0;
                Double shuiMoney=0.0;
                for (ScOrderListDetail scOrderListDetail : listPage) {
                    key++;
                    SettlementMoney+=scOrderListDetail.getSettlementMoney();
                  //  shuiMoney+=scOrderListDetail.getSettlementMoney()*0.13;
                    table= tableDetail(table,scOrderListDetail,key);
                    
                }
                shuiMoney=SettlementMoney*0.13;
                tbPublicfooter(table,String.valueOf(SettlementMoney),df.format(shuiMoney),String.valueOf(SettlementMoneySum),df.format(shuiMoneySum));
                document.add(table); //表格数据
                /* 引入底部 */
                footer(document,sdf);
                newPdfUtil.onEndPage(writer,document); //当前页码
            }
        } else {
            System.out.println("没有数据!!!"); // 此处根据自己的业务调整
        }

    }

    /* 表格头部公共的一行 */
    public PdfPTable tbPublic(){
        PdfPTable table_new = newPdfUtil.createTable(new float[] { 50, 200, 80, 50, 80, 80, 80, 40,100 });
        table_new.addCell(newPdfUtil.createCell("序号", textfont_10));
        table_new.addCell(newPdfUtil.createCell("货物（劳务）名称", textfont_10));
        table_new.addCell(newPdfUtil.createCell("规格型号", textfont_10));
        table_new.addCell(newPdfUtil.createCell("单位", textfont_10));
        table_new.addCell(newPdfUtil.createCell("数量", textfont_10));
        table_new.addCell(newPdfUtil.createCell("单价", textfont_10));
        table_new.addCell(newPdfUtil.createCell("金额", textfont_10));
        table_new.addCell(newPdfUtil.createCell("税率", textfont_10));
        table_new.addCell(newPdfUtil.createCell("税额", textfont_10));
        return table_new;
    }
    /* 表格底部公共的行 */
    public PdfPTable tbPublicfooter(PdfPTable table,String SettlementMoney,String shuiMoney,String SettlementMoneySum,String shuiMoneySum){
        table.addCell(newPdfUtil.createCell("小计", textfont_10,Element.ALIGN_LEFT));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell(SettlementMoney, textfont_10,Element.ALIGN_RIGHT));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell(shuiMoney, textfont_10,Element.ALIGN_RIGHT));
        table.addCell(newPdfUtil.createCell("总计", textfont_10,Element.ALIGN_LEFT));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell(SettlementMoneySum, textfont_10,Element.ALIGN_RIGHT));
        table.addCell(newPdfUtil.createCell("", textfont_10));
        table.addCell(newPdfUtil.createCell(shuiMoneySum, textfont_10,Element.ALIGN_RIGHT));
        table.addCell(newPdfUtil.createCell("备注", textfont_10,Element.ALIGN_LEFT));
        PdfPCell cell=newPdfUtil.createCell("", textfont_10);
        cell.setColspan(8);
        table.addCell(cell);
        return table;
    }

    /* 底部公共部分 */
    public void footer(Document document, String sdf) throws DocumentException {
        //表格下的落款——xxxxxx、打印时间
        PdfPTable table_3 = newPdfUtil.createTable(new float[] { 75, 325});
        table_3.addCell(newPdfUtil.createCell("销售方（章）：", textfont_10, Element.ALIGN_LEFT, 1, false));
        table_3.addCell(newPdfUtil.createCell("填开日期： "+sdf, textfont_10, Element.ALIGN_RIGHT, 1, false));
        document.add(table_3); //表格下面的落款
    }

    /*表格详细部分 */
    public PdfPTable tableDetail(PdfPTable table,ScOrderListDetail scOrderListDetail,Integer sod){
        ScShelfResource resource=scOrderListDetail.getScShelfResource();
        table.addCell(newPdfUtil.createCell(String.valueOf(sod), textfont_10, Element.ALIGN_LEFT));
        table.addCell(newPdfUtil.createCell( "*"+resource.getVarietyName()+resource.getMaterial(), textfont_10, Element.ALIGN_LEFT));
        table.addCell(newPdfUtil.createCell(resource.getMaterial()+resource.getSpecification(), textfont_10, Element.ALIGN_LEFT));
        table.addCell(newPdfUtil.createCell("吨", textfont_10, Element.ALIGN_LEFT));
        String num = String.valueOf(scOrderListDetail.getSettlementWeight());
        table.addCell(newPdfUtil.createCell(num, textfont_10, Element.ALIGN_RIGHT));
        // table.addCell(newPdfUtil.createCell(resource.getSpecification(), textfont_10, Element.ALIGN_LEFT));
        Double price=(double) (scOrderListDetail.getSettlementMoney()/scOrderListDetail.getSettlementWeight());
        DecimalFormat    df1   = new DecimalFormat("######0.000000");  
       // String actualnum = String.valueOf(price);
        table.addCell(newPdfUtil.createCell(df1.format(price), textfont_10, Element.ALIGN_RIGHT));
        String SettlementMoney=String.valueOf(scOrderListDetail.getSettlementMoney());
        table.addCell(newPdfUtil.createCell(SettlementMoney, textfont_10, Element.ALIGN_RIGHT));
        table.addCell(newPdfUtil.createCell("13%", textfont_10, Element.ALIGN_RIGHT));
        DecimalFormat    df   = new DecimalFormat("######0.00");  
        String shuiMoney=String.valueOf(df.format(scOrderListDetail.getSettlementMoney()*0.13));
        table.addCell(newPdfUtil.createCell(shuiMoney, textfont_10, Element.ALIGN_RIGHT));
        return table;
    }
        
  
    public static PdfPTable header(PdfWriter writer,Map<String, Object> model,Document document,int page) throws IOException, DocumentException {
        PdfPTable table = new PdfPTable(new float[] {200.0f, 220.0f, 110.0f});//生成一个两列的表格
        table.setTotalWidth(750.0f);
        table.setLockedWidth(true);
        PdfPCell cell;
        int size = 20;
       
    
        BaseFont bfOne = BaseFont.createFont("simsun.ttf",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);


       // Font font = new Font( bfOne, 8, Font.NORMAL );
        Font font1 = new Font( bfOne, 16, Font.BOLD );
    
      
      //  String sold_company = (String) model.get("sold_company");
        String sold_company="销售货物或提供应税劳务清单";
        String purchase_company = (String) model.get("purchase_company");
        String pmid_company=(String) model.get("pmid_company");
        String invoice_num=(String) model.get("invoice_num");
        String invoice_code=(String) model.get("invoice_code");
       

        cell=new PdfPCell(new Phrase(sold_company,font1));
       
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

      
       
        cell = new PdfPCell(new Phrase("购买方名称：" + purchase_company, textfont_10));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

      

       


        cell = new PdfPCell(new Phrase("销售方名称：" + pmid_company, textfont_10));
        cell.setColspan(3);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

      

        cell = new PdfPCell(new Phrase("所属增值税发票代码：" + invoice_code, textfont_10));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("号码：" + invoice_num, textfont_10));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);

        String text ="共"+part+"页第" + page + "页";
        
        cell = new PdfPCell(new Phrase(text, textfont_10));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setFixedHeight(size);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);


        return table;
    }

   
   


    
// 定义全局的字体静态变量
    // private static Font titlefont_16;
    // private static Font titlefontnormal_16;
    // private static Font headfont_14;
    // private static Font headfontnormal_14;
    // private static Font headfont_12;
    private static Font headfontnormal_12;
  //  private static Font keyfont_10;
    private static Font textfont_10;
    private static int part;
   // private static Font underlinefont_10;

    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese =BaseFont.createFont("simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            // titlefont_16 = new Font(bfChinese, 16, Font.BOLD);
            // headfont_14 = new Font(bfChinese, 14, Font.BOLD);
            // headfont_12 = new Font(bfChinese, 12, Font.BOLD);
            // keyfont_10 = new Font(bfChinese, 10, Font.BOLD);

            // titlefontnormal_16 = new Font(bfChinese, 16, Font.NORMAL);
            // headfontnormal_14 = new Font(bfChinese, 14, Font.NORMAL);
            headfontnormal_12 = new Font(bfChinese, 12);
            textfont_10 = new Font(bfChinese, 10);
           // underlinefont_10 = new Font(bfChinese, 10, Font.UNDERLINE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/************************* 全局变量 end *************************/

}

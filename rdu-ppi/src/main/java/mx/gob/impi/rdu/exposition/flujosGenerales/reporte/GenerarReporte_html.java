/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales.reporte;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.impi.rdu.dto.*;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;
import mx.gob.impi.rdu.persistence.model.Reivindicacion;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.Util;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.log4j.Logger;
import org.apache.pdfbox.util.PDFImageWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.FontFactory;

import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.Element;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.html.simpleparser.StyleSheet;

import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.awt.Color;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.itextpdf.text.Element;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.pipeline.WritableElement;

import com.itextpdf.text.pdf.parser.FilteredTextRenderListener;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.parser.RegionTextRenderFilter;
import com.itextpdf.text.pdf.parser.RenderFilter;

import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PRStream;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


/**
 *
 * @author cesar
 */
public class GenerarReporte_html {

    Logger log = Logger.getLogger(GenerarReporte.class);

  // itextpdf-5.4.1.jar  http://sourceforge.net/projects/itext/files/iText/txtHTML
  public void htmltoPDF(String txtHTML) throws DocumentException {
    try {
      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      Date fecha=new Date();
      String strElemento = "";
      BaseFont dejavusans = BaseFont.createFont(request.getRealPath("") + "/content/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
      Document document = new Document(PageSize.LETTER);
      ByteArrayOutputStream baosArchivo =new ByteArrayOutputStream();
//      PdfWriter.getInstance(document, new FileOutputStream("/opt/rptGenerado"+ fecha.getDate()+"_" + fecha.getHours()+"_"+fecha.getMinutes()+".pdf"));
      PdfWriter.getInstance(document, baosArchivo);
      document.addAuthor("IMPI");
      document.addCreator("Real's HowTo");
      document.addSubject("Thanks for your support");
      document.addCreationDate();
      document.addTitle("Modelo Industrial de Pruebas");
      document.setMargins(87, 87, 87, 87);
      document.open();
      FontFactory.register(request.getRealPath("") + "/content/fonts/DejaVuSans.ttf");
      StyleSheet estilo = new StyleSheet();
      estilo.loadTagStyle("li", "leading", "15");
      estilo.loadTagStyle("ul", "indent", "10");
      estilo.loadTagStyle("ol", "indent", "10");
      estilo.loadStyle("Font", "face", "dejavusans");
      estilo.loadStyle("Font", "font-size", "8px");
      String str = "<h3 align='center' >"+ "Modelo Industrial de Pruebas" +"</h3>"+  txtHTML;
      
      HashMap<String,Object> providers = new HashMap<String, Object>();
      rduFontProvider dfp = new rduFontProvider(request.getRealPath("") + "/content/fonts/DejaVuSans.ttf");
      providers.put(HTMLWorker.FONT_PROVIDER, dfp); 
//      List<Element> htmlObjs = HTMLWorker.parseToList(new StringReader(str),estilo,providers);
//      for (Element element : htmlObjs){
//            document.add(element);
//      }
//      document.close();
//      
      HTMLWorker htmlWorker = new HTMLWorker(document);
      htmlWorker.setStyleSheet(estilo);
     // htmlWorker.setProviders(providers);
//      String str = "<h3 align='center' >"+ "Modelo Industrial de Pruebas" +"</h3>"+  txtHTML;
      htmlWorker.parse(new StringReader(str));
      document.close();
      
      FileOutputStream fos = new FileOutputStream (new File("/opt/rptGenerado2"+ fecha.getDate()+"_" + fecha.getHours()+"_"+fecha.getMinutes()+".pdf")); 
      ByteArrayOutputStream baos = agregaNumPaginas(baosArchivo);
    // Put data in your baos
      baos.writeTo(fos);
      System.out.println("Done");
      }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
    
    public void xhtmltoPDF(String txtHTML) throws DocumentException {
    try {
      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      Date fecha=new Date();
      String strElemento = "";
     // BaseFont dejavusans = BaseFont.createFont(request.getRealPath("") + "/content/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
      Document document = new Document(PageSize.LETTER);
      ByteArrayOutputStream baosArchivo =new ByteArrayOutputStream();
      PdfWriter writer =PdfWriter.getInstance(document, baosArchivo);
      document.addAuthor("IMPI");
      document.addCreator("Real's HowTo");
      document.addSubject("Thanks for your support");
      document.addCreationDate();
      document.addTitle("Modelo Industrial de Pruebas");
      document.setMargins(87, 87, 87, 87);
   //   document.open();

      String str = "<h3 align='center' >"+ "Modelo Industrial de Pruebas" +"</h3>"+  txtHTML;
      XMLWorkerHelper worker=XMLWorkerHelper.getInstance();
      //PdfWriter writer = PdfWriter.getInstance(document,new FileOutputStream("results/loremipsum.pdf"));
      document.open();
      worker.parseXHtml(writer,document,new StringReader(str)) ;
      document.close();
      
      FileOutputStream fos = new FileOutputStream (new File("/opt/rptGeneradoXML"+ fecha.getDate()+"_" + fecha.getHours()+"_"+fecha.getMinutes()+".pdf")); 
      ByteArrayOutputStream baos = agregaNumPaginas(baosArchivo);
    // Put data in your baos
      baos.writeTo(fos);
      System.out.println("Done");
      }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

    public String leePDF() throws UnsupportedEncodingException
    { String txtPDF="";
      try{      
          
        String cadena="/media/WindowsD/resultado"+ new Date().getMinutes()+".txt";
        PdfReader reader1 = new PdfReader("/media/WindowsD/Resumen1.pdf");
        PrintWriter salida = new PrintWriter(new FileOutputStream(cadena));
        Rectangle rect = new Rectangle(600, 850);
        RenderFilter filter = new RegionTextRenderFilter(rect);
        TextExtractionStrategy strategy1;
        for (int i = 1; i <= reader1.getNumberOfPages(); i++) {
            strategy1 = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
            //out.println(PdfTextExtractor.getTextFromPage(reader1, i, strategy1));
            txtPDF+=PdfTextExtractor.getTextFromPage(reader1, i, strategy1);
        }
        salida.println(txtPDF);
        salida.flush();
        salida.close();
        reader1.close();
        
        PdfReader reader;

        File file = new File("/media/WindowsD/Resumen1.pdf");
        reader = new PdfReader(file.getAbsolutePath());
        for (int i = 0; i < reader.getXrefSize(); i++) {
            PdfObject pdfobj = reader.getPdfObject(i);
            if (pdfobj == null || !pdfobj.isStream()) {
                continue;
            }
            PdfStream stream = (PdfStream) pdfobj;
            PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
            if (pdfsubtype != null && pdfsubtype.toString().equals(PdfName.IMAGE.toString())) {
                byte[] img = PdfReader.getStreamBytesRaw((PRStream) stream);
                FileOutputStream out = new FileOutputStream(new File(file.getParentFile(), "/media/WindowsD/" + String.format("%1$05d", i) + ".jpg"));
                out.write(img);
                out.flush();
                out.close();
            }
        }
         
        
            //rptGeneradoXML10_12_52.pdf SC-RDU-2014(1).pdf
//            PdfReader reader = new PdfReader("/media/WindowsD/Resumen.pdf");
          
//            System.out.println("This PDF has "+reader.getNumberOfPages()+" pages.");
//            for (int i=1;i<=reader.getNumberOfPages();i++){
//             txtPDF += PdfTextExtractor.getTextFromPage(reader, i);
//            }
            
//            PdfReader reader = new PdfReader("/media/WindowsD/Resumen.pdf");
//            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
//            TextExtractionStrategy strategy;
//            //reader.selectPages(txtPDF);
//            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
//                strategy = parser.processContent(i, new SimpleTextExtractionStrategy()); 
//                txtPDF+=strategy.getResultantText()+"/n";
//            }
 
        } catch (IOException e) {
            e.printStackTrace();
        }
      String cad=new String(txtPDF.getBytes(),"UTF-8");
      txtPDF=cad;
      return txtPDF;
    }

    public void extractImages(String filename, String destination)
            throws IOException, DocumentException {        
        System.out.println("Processing PDF at " + filename);
        System.out.println("Saving images to " + destination);

        PdfReader reader = new PdfReader(filename);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        ImageRenderListener listener = new ImageRenderListener(destination + "/Img%s.%s");
        for (int i = 1; i <= reader.getNumberOfPages(); i++) {
            parser.processContent(i, listener);
        }
        reader.close();
    }
  
  
    public class FooterPiePaginaiText extends  PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(writer.getPageNumber()), 200,20,0);
        }
    }
    public ByteArrayOutputStream agregaNumPaginas(ByteArrayOutputStream baosReporteMarcasPdf) {
        ByteArrayOutputStream baosReporteFinal = new ByteArrayOutputStream();
        ByteArrayOutputStream baosReporte = new ByteArrayOutputStream();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            BaseFont dejavusans = BaseFont.createFont(request.getRealPath("") + "/content/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Document document = new Document();
            document.setPageSize(PageSize.LETTER);
            //Se crea un reader para el PDF
            PdfReader reader = new PdfReader(baosReporteMarcasPdf.toByteArray());
            //obtenemos el número de páginas del archivo
            int total = reader.getNumberOfPages();
            PdfWriter pdfwriter = PdfWriter.getInstance(document, baosReporteFinal);
            PdfImportedPage pdfimportedpage = null;
            document.open();
            //Declaro el PdfContentByte obteniendo el Direct Content del PDF.
            PdfContentByte pdfcontentbyte = pdfwriter.getDirectContent();
            for (int j = 1; j <= total; j++) {
                PdfTemplate template = pdfcontentbyte.createTemplate(50, 50);
                template.beginText();
                //Inicializamos los valores para el templete
                //Se define el tipo de letra, color y tamaño
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                template.setFontAndSize(bf, 8);

                template.setTextMatrix(0, 0);
                String np=String.valueOf(j);
                //Se define el texto que se agregara como marca de agualowagie
                template.showText(np);
                template.endText();
                document.newPage();
                
                pdfimportedpage = pdfwriter.getImportedPage(reader, j);
                pdfcontentbyte.addTemplate(pdfimportedpage, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, -10F);
                if (j<10){
                    pdfcontentbyte.addTemplate( template,300, 50);
                }else if (j<100){
                    pdfcontentbyte.addTemplate( template,298, 50);
                }else if (j<1000){
                    pdfcontentbyte.addTemplate( template,296, 50);
                }
            }
            document.close();
            return baosReporteFinal;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
     private void ponerNumeroPagina(PdfContentByte pdfContentByte,int numeroPagina) {
        
            PdfPTable tablaNumeracion = new PdfPTable(1);
            PdfPCell celda = null;
            celda.setBorder(0);
            tablaNumeracion.addCell(celda);
            tablaNumeracion.setTotalWidth(50f);

                if (numeroPagina < 10) {
                    tablaNumeracion.writeSelectedRows(0, -1, 342.5f, 55.5f, pdfContentByte);
                } else if (numeroPagina < 100) {
                    tablaNumeracion.writeSelectedRows(0, -1, 341f, 55.5f, pdfContentByte);
                } else if (numeroPagina < 1000) {
                    tablaNumeracion.writeSelectedRows(0, -1, 339.5f, 55.5f, pdfContentByte);
                } else if (numeroPagina < 10000) {
                    tablaNumeracion.writeSelectedRows(0, -1, 337f, 55.5f, pdfContentByte);
                } else {
                    tablaNumeracion.writeSelectedRows(0, -1, 336f, 55.5f, pdfContentByte);
                }
    }
    
}
  
//      ArrayList<Element> htmlObjs = HTMLWorker.parseToList(new StringReader(str), estilo);
//      for(Element elemento: htmlObjs)
//      {
////           document.add(elemento);
////           elemento=new Chunk(strElemento, new Font(dejavusans, 8));
//            Paragraph parrafo = new Paragraph();
//            parrafo.setFont(fontDejavuSans);
//            parrafo.add(elemento);
//            document.add(parrafo);
//      }
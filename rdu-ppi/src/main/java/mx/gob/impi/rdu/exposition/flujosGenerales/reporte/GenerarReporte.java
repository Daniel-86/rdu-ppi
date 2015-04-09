/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales.reporte;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.GrayColor;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;
import com.lowagie.text.Paragraph;
import com.lowagie.text.FontFactory;

//import com.itextpdf.text.html.simpleparser.HTMLWorker;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.DocumentException;


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

/**
 *
 * @author cesar
 */
public class GenerarReporte {

    Logger log = Logger.getLogger(GenerarReporte.class);

    public ByteArrayOutputStream generarDibujosEnPdf(String pathFile, Object report) {
        ByteArrayOutputStream streamReporteGenerado = new ByteArrayOutputStream();
        try {
            JasperPrint jasperPrint = null;
            if (report instanceof TramitePatente) {
                TramitePatente tramite = (TramitePatente) report;
                if (tramite != null && tramite.getImagenes() != null && tramite.getImagenes().size() > 0) {
                    List<ReporteDibujo> dibujos = new ArrayList<ReporteDibujo>();
                    for (ImagenDibujo imagenDibujo : tramite.getImagenes()) {
//                        dibujos.add(new ReporteDibujo(new ByteArrayInputStream(FileServicesUtil.computeSize(imagenDibujo.getArchivo(), 485, 673).toByteArray()), imagenDibujo.getOrden().toString()));
                        dibujos.add(new ReporteDibujo(new ByteArrayInputStream(imagenDibujo.getArchivo()), imagenDibujo.getOrden().toString()));
                    }
                    jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(dibujos));
                    JRExporter jasperExport = new JRPdfExporter();
                    jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                    jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jasperExport.exportReport();
                    //this.writePdf(streamReporteGenerado, "/home/cesar/Desktop/reporte_dibujo.pdf");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streamReporteGenerado;
    }

    public ByteArrayOutputStream generarReivindicacionesEnPdf(String pathFile, Object report, int Paginas) {
        ByteArrayOutputStream streamReporteGenerado = new ByteArrayOutputStream();
        try {
            JasperPrint jasperPrint = null;
            if (report instanceof TramitePatente) {
                TramitePatente tramite = (TramitePatente) report;
                if (tramite != null && tramite.getReivindicaciones() != null && tramite.getReivindicaciones().size() > 0) {
                    List<ReporteReivindicacion> reivindicaciones = new ArrayList<ReporteReivindicacion>();
                    reivindicaciones.add(new ReporteReivindicacion(formatearReivindicaciones(tramite.getReivindicaciones()),Paginas));
                    jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(reivindicaciones));
                    JRExporter jasperExport = new JRPdfExporter();
                    jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                    jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jasperExport.exportReport();
                    //this.writePdf(streamReporteGenerado, "/home/cesar/Desktop/reporte_reivindicaciones.pdf");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streamReporteGenerado;
    }
    
    public ByteArrayOutputStream generarReivindicacionEnPdf(String pathFile, Object report, int Paginas) {
        ByteArrayOutputStream streamReporteGenerado = new ByteArrayOutputStream();
        try {
            JasperPrint jasperPrint = null;
            if (report instanceof ReporteReivindicacion) {
               // TramitePatente tramite = (TramitePatente) report;
                ReporteReivindicacion reivindicacionDto =(ReporteReivindicacion) report;
                if (reivindicacionDto != null ) {
                    List<ReporteReivindicacion> reivindicacion = new ArrayList<ReporteReivindicacion>();
                    reivindicacion.add(new ReporteReivindicacion(reivindicacionDto.getDescripcion(),Paginas));
                    jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(reivindicacion));
                    JRExporter jasperExport = new JRPdfExporter();
                    jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                    jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jasperExport.exportReport();
                    //this.writePdf(streamReporteGenerado, "/home/cesar/Desktop/reporte_reivindicaciones.pdf");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streamReporteGenerado;
    }
        
    public ByteArrayOutputStream generarResumenEnPdf(String pathFile, Object report, int Paginas) {
        ByteArrayOutputStream streamReporteGenerado = new ByteArrayOutputStream();
        try {
            JasperPrint jasperPrint = null;
            if (report instanceof ReporteResumen) {
               // TramitePatente tramite = (TramitePatente) report;
                ReporteResumen resumenDto =(ReporteResumen) report;
                if (resumenDto != null ) {
                    List<ReporteResumen> resumen = new ArrayList<ReporteResumen>();
                    resumen.add(new ReporteResumen(resumenDto.getDescripcion(),Paginas));
                    jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(resumen));
                    JRExporter jasperExport = new JRPdfExporter();
                    jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                    jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jasperExport.exportReport();
                    //this.writePdf(streamReporteGenerado, "/home/cesar/Desktop/reporte_reivindicaciones.pdf");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streamReporteGenerado;
    }

    public ByteArrayOutputStream generarDescripcionEnPdf(String pathFile, Object report) {
        ByteArrayOutputStream streamReporteGenerado = new ByteArrayOutputStream();
        try {
            JasperPrint jasperPrint = null;
            if (report instanceof ReporteDescripcionDto) {
                //ReporteDescripcionDto descripcionDto
                ReporteDescripcionDto descripcionDto = (ReporteDescripcionDto) report;
                if (descripcionDto != null) {
//                    List<ReporteReivindicacion> reivindicaciones = new ArrayList<ReporteReivindicacion>();
                    List<ReporteDescripcionDto> listaDescripcion = new ArrayList<ReporteDescripcionDto>();
//                    reivindicaciones.add(new ReporteReivindicacion(formatearReivindicaciones(tramite.getReivindicaciones())));
                    
//                    listaDescripcion.add(new ReporteDescripcionDto(formatearDescripcion(descripcionDto), descripcionDto.getDesReivindicaciones()));
                    listaDescripcion.add(new ReporteDescripcionDto(descripcionDto.getPreambulo(), "", "", descripcionDto.getTextoAdicional()
                            , descripcionDto.getDesReivindicaciones(), descripcionDto.getTituloDescripcion(),descripcionDto.getPaginas() ));
                    
                    jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(listaDescripcion));
                    JRExporter jasperExport = new JRPdfExporter();
                    jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                    jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jasperExport.exportReport();
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streamReporteGenerado;
    }

    public static String formatearReivindicaciones(List<Reivindicacion> reivindicaciones) {
        String result = "";
        for (Reivindicacion reivindicacion : reivindicaciones) {
            result += reivindicacion.getOrden() + ". " + reivindicacion.getDescripcion() + "\n\n";
        }
        return result;
    }

    public ByteArrayOutputStream generarHojaDescuentoPdf(String pathFile, Object report) {
        ByteArrayOutputStream streamReporteGenerado = new ByteArrayOutputStream();
        try {
            JasperPrint jasperPrint = null;
                hojaDescuento descuento = (hojaDescuento) report;
                    List<hojaDescuento> listaDescuento = new ArrayList<>();
                    listaDescuento.add(datosHojaDescuento(descuento));
                    jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(listaDescuento));
                    JRExporter jasperExport = new JRPdfExporter();
                    jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                    jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    jasperExport.exportReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streamReporteGenerado;
    }

    public static String formatearDescripcion(ReporteDescripcionDto descripcionDto) {
        String result = "";
        
        if(descripcionDto.getPreambulo()!= null && !descripcionDto.getPreambulo().equals(""))
            result += descripcionDto.getPreambulo() + "\n\n";
        
        if(descripcionDto.getDescripcionVistas()!= null && !descripcionDto.getDescripcionVistas().equals(""))
            result += descripcionDto.getDescripcionVistas() + "\n\n";
        
        if(descripcionDto.getTextoAdicional()!= null && !descripcionDto.getTextoAdicional().equals(""))
            result += descripcionDto.getTextoAdicional()+ "\n\n";
        
        return result;
    }
    
    public static hojaDescuento datosHojaDescuento(hojaDescuento datos){
        hojaDescuento hojaDescuento = new hojaDescuento();
        try {
            hojaDescuento.setFecha(new Date());
            
            if(datos.getFlag() == Constantes.FIRST){
                hojaDescuento.setAnio("");
                hojaDescuento.setMes("");
                hojaDescuento.setDia("");
            }else{
                String fechaActual = Util.formatearFecha(new Date(),"dd/MMM/yyyy");
                String[] fechaSeparada = fechaActual.split ("/");
                hojaDescuento.setAnio(datos.getAnio());
                hojaDescuento.setMes(datos.getMes());
                hojaDescuento.setDia(datos.getDia());
            }
            
            hojaDescuento.setNumSolicitud("");
            hojaDescuento.setTipo(datos.getTipo());
            hojaDescuento.setNombre(datos.getNombre());
            hojaDescuento.setNombreApoderado(datos.getNombreApoderado());
            hojaDescuento.setTipoSolicitud(datos.getTipoSolicitud());
            hojaDescuento.setImgFirmaImpi(datos.getImgFirmaImpi());
            hojaDescuento.setNombreFirmante(datos.getNombreFirmante());
            hojaDescuento.setSelloSolicitante(datos.getSelloSolicitante());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hojaDescuento;
    }

    public ByteArrayOutputStream generaRepporte(String pathFile, Object reporte) {
        if (log.isInfoEnabled()) {
            log.info("**********************Cargando el archivo*************   " + pathFile);
        }
        ByteArrayOutputStream streamReporteGenerado = new ByteArrayOutputStream();
        try {
            JasperPrint jasperPrint = null;
            if (reporte instanceof ReporteDisenoIndustrialDto) {
                ReporteDisenoIndustrialDto reporteDisenoIndustrialDto = (ReporteDisenoIndustrialDto) reporte;
                List<ReporteDisenoIndustrialDto> disenoIndustrial = new ArrayList<ReporteDisenoIndustrialDto>();
                disenoIndustrial.add(reporteDisenoIndustrialDto);
                jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(disenoIndustrial));
                JRExporter jasperExport = new JRPdfExporter();
                jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jasperExport.exportReport();
                boolean apodAnex = reporteDisenoIndustrialDto.getNombresApodAnexo() == null ? true : reporteDisenoIndustrialDto.getNombresApodAnexo().equals("");
                boolean invAnex = reporteDisenoIndustrialDto.getNombresInvAnexo() == null ? true : reporteDisenoIndustrialDto.getNombresInvAnexo().equals("");
                boolean persAnex = reporteDisenoIndustrialDto.getNombresPersNotAnexo() == null ? true : reporteDisenoIndustrialDto.getNombresPersNotAnexo().equals("");
                boolean solAnex = reporteDisenoIndustrialDto.getNombresSolAnexo() == null ? true : reporteDisenoIndustrialDto.getNombresSolAnexo().equals("");
                boolean priorAnex = reporteDisenoIndustrialDto.getPaisPriorAnexo() == null ? true : reporteDisenoIndustrialDto.getPaisPriorAnexo().equals("");
                if (apodAnex && invAnex && persAnex && solAnex && priorAnex) {
                    streamReporteGenerado = this.eliminarPagina(streamReporteGenerado, 2);
                }
            }
            if (reporte instanceof ReportePromocionesDto) {
                log.info("  **   DENTRO DE reporte instanceof ReportePromociones==>");
                ReportePromocionesDto reportePromociones = (ReportePromocionesDto) reporte; 
                List<ReportePromocionesDto> repoPromociones = new ArrayList<ReportePromocionesDto>();
                repoPromociones.add(reportePromociones);
                jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(repoPromociones));
                JRExporter jasperExport = new JRPdfExporter();
                jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jasperExport.exportReport();                
            }

            if (reporte instanceof ReporteTransRegDto) {
                log.info("  **   DENTRO DE reporte instanceof ReporteTransRegDto==>");
                ReporteTransRegDto reporteTransRegDto = (ReporteTransRegDto) reporte;
                List<ReporteTransRegDto> transReg = new ArrayList<ReporteTransRegDto>();
                transReg.add(reporteTransRegDto);
                jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(transReg));
                JRExporter jasperExport = new JRPdfExporter();
                jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jasperExport.exportReport();
            }


            if (reporte instanceof ReporteCambioDomicilioDto) {
                log.info("  **   DENTRO DE reporte instanceof reporteCambioDomicilioDto==>");
                ReporteCambioDomicilioDto reporteCambioDomicilioDto = (ReporteCambioDomicilioDto) reporte;
                List<ReporteCambioDomicilioDto> transReg = new ArrayList<ReporteCambioDomicilioDto>();
                transReg.add(reporteCambioDomicilioDto);
                jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(transReg));
                JRExporter jasperExport = new JRPdfExporter();
                jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jasperExport.exportReport();
            }
            if (reporte instanceof ReporteInscripcionLicenciaFranquiciaDto) {
                ReporteInscripcionLicenciaFranquiciaDto reporteInscripcionLicenciaFranquiciaDto = (ReporteInscripcionLicenciaFranquiciaDto) reporte;
                List<ReporteInscripcionLicenciaFranquiciaDto> inscripcionLicFran = new ArrayList<ReporteInscripcionLicenciaFranquiciaDto>();
                inscripcionLicFran.add(reporteInscripcionLicenciaFranquiciaDto);
                jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(inscripcionLicFran));
                JRExporter jasperExport = new JRPdfExporter();
                jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jasperExport.exportReport();
                //Falta condicion
                streamReporteGenerado = this.eliminarPagina(streamReporteGenerado, 3);
            }

            if (reporte instanceof ReporteRenovacionDto) {
                ReporteRenovacionDto reporteRenovacionDto = (ReporteRenovacionDto) reporte;
                List<ReporteRenovacionDto> renovacion = new ArrayList<ReporteRenovacionDto>();
                renovacion.add(reporteRenovacionDto);
                jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(renovacion));
                JRExporter jasperExport = new JRPdfExporter();
                jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jasperExport.exportReport();
                //Falta condicion
                //streamReporteGenerado = this.eliminarPagina(streamReporteGenerado, 2);
            }
            
            if (reporte instanceof ReporteNotificacionAcuseDto) {
                ReporteNotificacionAcuseDto reporteRenovacionDto = (ReporteNotificacionAcuseDto) reporte;
                List<ReporteNotificacionAcuseDto> renovacion = new ArrayList<ReporteNotificacionAcuseDto>();
                renovacion.add(reporteRenovacionDto);
                jasperPrint = JasperFillManager.fillReport(pathFile, null, new JRBeanCollectionDataSource(renovacion));
                JRExporter jasperExport = new JRPdfExporter();
                jasperExport.setParameter(JRExporterParameter.OUTPUT_STREAM, streamReporteGenerado);
                jasperExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jasperExport.exportReport();
                //Falta condicion
                //streamReporteGenerado = this.eliminarPagina(streamReporteGenerado, 2);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return streamReporteGenerado;
    }

    public ByteArrayOutputStream concatenarPdfs(ByteArrayOutputStream baosReporteMarcasPdf, List<byte[]> anexosPdf) {
        ByteArrayOutputStream baosReporteFinal = new ByteArrayOutputStream();
        try {
            PdfCopyFields pdfCopyFields = new PdfCopyFields(baosReporteFinal);
            pdfCopyFields.addDocument(new PdfReader(baosReporteMarcasPdf.toByteArray()));
            for (byte[] anexoPdf : anexosPdf) {
                PdfReader pdfReader = new PdfReader(anexoPdf);
                pdfCopyFields.addDocument(pdfReader);
            }
            pdfCopyFields.close();
            return baosReporteFinal;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ByteArrayOutputStream crearMarcaAgua(ByteArrayOutputStream baosReporteMarcasPdf) {
        ByteArrayOutputStream baosReporteFinal = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            document.setPageSize(new Rectangle(612, 1008));
            //Se crea un reader para el PDF
            PdfReader reader = new PdfReader(baosReporteMarcasPdf.toByteArray());
            //obtenemos el número de páginas del archivo
            int total = reader.getNumberOfPages();
            PdfWriter pdfwriter = PdfWriter.getInstance(document, baosReporteFinal);
            PdfImportedPage pdfimportedpage = null;
            //Image image = Image.getInstance("/home/cesar/Downloads/images.jpeg");
            document.open();
            //Declaro el PdfContentByte obteniendo el Direct Content del PDF.
            PdfContentByte pdfcontentbyte = pdfwriter.getDirectContent();
            PdfTemplate template = pdfcontentbyte.createTemplate(700, 300);
            for (int j = 1; j <= total; j++) {
                template.beginText();
                //Inicializamos los valores para el templete
                //Se define el tipo de letra, color y tamaño
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                template.setColorFill(new Color(238, 238, 238));
                template.setFontAndSize(bf, 20);

                template.setTextMatrix(0, 0);
                //Se define el texto que se agregara como marca de agua
                template.showText("VISTA PREVIA");
                template.endText();
                //Se asigna el templete
                //Se asignan los valores para el texto de marca de agua
                // Se asigna el grado de inclinacion y la posicion donde aparecerá el texto
                //Se agregan las paginas al documento PDF
                document.newPage();
                pdfimportedpage = pdfwriter.getImportedPage(reader, j);
                pdfcontentbyte.addTemplate(pdfimportedpage, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, -10F);
                //Se asigna la posición de la imágen
                //image.setAbsolutePosition(100, 100);
                //Se asigna la imágen al PDF
                //pdfcontentbyte.addImage(image);
                pdfcontentbyte.addTemplate(template, 1, 1, -1, 1, 250, 500);
            }
            document.close();
            return baosReporteFinal;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public byte[] crearMarcaAguaVistaPrevia(byte[] archivoPDF) {
        ByteArrayOutputStream baosReporteFinal = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfImportedPage pdfimportedpage = null;
            //Se crea un reader para el PDF
            PdfReader reader = new PdfReader(archivoPDF);

            //Obtenemos el número de páginas del archivo 
            PdfWriter pdfwriter = PdfWriter.getInstance(document, baosReporteFinal);
            int total = reader.getNumberOfPages();

            //Se abre el archivo
            document.open();

            //Declaro el PdfContentByte obteniendo el Direct Content del PDF.
            PdfContentByte pdfcontentbyte = pdfwriter.getDirectContent();
            //TEMPLATE PARA TEXTO VISTA PREVIA
            //TEMPLATES PARA LEYENDA A MOSTRA
            PdfTemplate template = pdfcontentbyte.createTemplate(300, 150);
            PdfTemplate template2 = pdfcontentbyte.createTemplate(300, 100);
            PdfTemplate template3 = pdfcontentbyte.createTemplate(350, 100);


            //ESTILO DE FUENTE
            BaseFont bf = BaseFont.createFont(
                    BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

            //FOR PARA AGREGAR MARCA DE AGUA A TODAS LAS PAGINAS DEL PDF
            for (int j = 1; j <= total; j++) {

                //Se crea opacidad
                PdfGState gstate = new PdfGState();
                gstate.setFillOpacity(0.2f);
                gstate.setStrokeOpacity(0.2f);

                //Crear pagina con tamaño de hoja original
                Rectangle pageSize = reader.getPageSizeWithRotation(j);
                document.setPageSize(pageSize);

                //BLOQUE DE TEMPLATE PARA LABEL DE VISTA PREVIA
                template.beginText();
                template.setColorFill(Color.LIGHT_GRAY);
                template.setGState(gstate);
                template.setFontAndSize(bf, 40);
                template.setTextMatrix(0, 0);
                template.showText("VISTA PREVIA");
                template.endText();

                //BLOQUE DE TEMPLATE PARA LEYENDA
                template2.beginText();
                template2.setColorFill(Color.LIGHT_GRAY);
                template2.setGState(gstate);
                template2.setTextMatrix(0, 0);
                template2.setFontAndSize(bf, 14);
                template2.showText("DOCUMENTO NO OFICIAL PARA EL");
                template2.endText();

                //BLOQUE DE TEMPLATE PARA LEYENDA
                template3.beginText();
                template3.setColorFill(Color.LIGHT_GRAY);
                template3.setGState(gstate);
                template3.setTextMatrix(0, 0);
                template3.setFontAndSize(bf, 14);
                template3.showText("INSTITUTO MEXICANO DE PROPIEDAD INDUSTRIAL");
                template3.endText();

                document.newPage();
                pdfimportedpage = pdfwriter.getImportedPage(reader, j);

                //AGREGAR LOS TEMPLATES
                
                //Obtener tamaño vertical de la hoja para alineacion
                float urY = pageSize.getHeight() / 2;                
                //LOS ULTIMOS DOS VALORES INDICAN A X y Y RESPECTIVAMENTE
                pdfcontentbyte.addTemplate(pdfimportedpage, 0.0F, -10.0F);
                pdfcontentbyte.addTemplate(template, 1, 1, -1, 1, 150, (urY - 100));
                pdfcontentbyte.addTemplate(template2, 1, 1, -1, 1, 200, (urY - 120));
                pdfcontentbyte.addTemplate(template3, 1, 1, -1, 1, 170, (urY - 190));
            }
            document.close();

            return baosReporteFinal.toByteArray();
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int getNumberPages(ByteArrayOutputStream pdfArray) throws Exception {
        Document document = new Document();
        document.setPageSize(new Rectangle(612, 1008));
        PdfReader reader = new PdfReader(pdfArray.toByteArray());
        return reader.getNumberOfPages();
    }

    public ByteArrayOutputStream eliminarPagina(ByteArrayOutputStream baosReporteMarcasPdf, int numeroPagina) {
        ByteArrayOutputStream baosReporteFinal = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            document.setPageSize(new Rectangle(612, 1008));
            //Se crea un reader para el PDF
            PdfReader reader = new PdfReader(baosReporteMarcasPdf.toByteArray());
            //obtenemos el número de páginas del archivo
            int total = reader.getNumberOfPages();
            PdfWriter pdfwriter = PdfWriter.getInstance(document, baosReporteFinal);
            PdfImportedPage pdfimportedpage = null;
            //Image image = Image.getInstance("/home/cesar/Downloads/images.jpeg");
            document.open();
            //Declaro el PdfContentByte obteniendo el Direct Content del PDF.
            PdfContentByte pdfcontentbyte = pdfwriter.getDirectContent();
            for (int j = 1; j <= total; j++) {
                //Se asigna el templete
                //Se asignan los valores para el texto de marca de agua
                // Se asigna el grado de inclinacion y la posicion donde aparecerá el texto
                //Se agregan las paginas al documento PDF
                if (j != numeroPagina) {
                    document.newPage();
                    pdfimportedpage = pdfwriter.getImportedPage(reader, j);
                    pdfcontentbyte.addTemplate(pdfimportedpage, 1.0F, 0.0F, 0.0F, 1.0F, 0.0F, -10F);
//                    HeaderFooter footer = new HeaderFooter(new Phrase("Pagina " + j + " de " + total), false);
//                    footer.setBorder(Rectangle.TOP);
//                    document.setFooter(footer);
                }
                //Se asigna la posición de la imágen
                //image.setAbsolutePosition(100, 100);
                //Se asigna la imágen al PDF
                //pdfcontentbyte.addImage(image);                
            }
            document.close();
            return baosReporteFinal;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //Escribir a disco el objeto ByteArrayOutputStream
    public static void writePdf(ByteArrayOutputStream baos, String path) {
        File file = new File(path);
        FileOutputStream foStream;
        try {
            foStream = new FileOutputStream(file);
            ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            //Writes a byte to the byte array output stream.
            oStream.write(baos.toByteArray());
            oStream.writeTo(foStream);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //APLICAR BOOKMARKS
    public ByteArrayOutputStream concatenarPdfsWithBookMarks(List<ReportesDto> listaReportes, boolean vistaPrevia) {
        Document document = new Document();
        ByteArrayOutputStream baos = null;
        PdfCopy copy = null;

        try {
            baos = new ByteArrayOutputStream();
            copy = new PdfCopy(document, baos);
            document.open();

            PdfReader reader;
            int page_offset = 0;
            int n;
            ArrayList<HashMap<String, Object>> bookmarks = new ArrayList<HashMap<String, Object>>();
            List<HashMap<String, Object>> tmp;
            for (int i = 0; i < listaReportes.size(); i++) {
                //Si es vista previa crear marca de agua
                if (vistaPrevia) {
                    reader = new PdfReader(crearMarcaAguaVistaPrevia(listaReportes.get(i).getReporteBytes()));
                } else {
                    reader = new PdfReader(listaReportes.get(i).getReporteBytes());
                }

                tmp = SimpleBookmark.getBookmark(reader);
                SimpleBookmark.shiftPageNumbers(tmp, page_offset, null);
                if (tmp != null) {
                    bookmarks.addAll(tmp);
                } else if (page_offset == 0 && vistaPrevia==false ) {
                    HashMap<String, Object> kid = new HashMap<String, Object>();
                    bookmarks.add(kid);
                    kid.put("Title", "ACUSE DE RECIBO");// "SOLICITUD");
                    kid.put("Action", "GoTo");
                    kid.put("Page", String.format("%d Fit", page_offset + 1));
                } else if (page_offset == 0 && vistaPrevia==true ) {
                    HashMap<String, Object> kid = new HashMap<String, Object>();
                    bookmarks.add(kid);
                    kid.put("Title", "SOLICITUD");// "SOLICITUD");
                    kid.put("Action", "GoTo");
                    kid.put("Page", String.format("%d Fit", page_offset + 1)); 
                }else if (page_offset != 0) {
                    //if(listaReportes.get(i).getNombreBookMark()=="DESCRIPCION" && listaReportes.get(i+1).getNombreBookMark()=="MEMORIA TÉCNICA")
                    if(listaReportes.get(i).getNombreBookMark()=="DESCRIPCION" && listaReportes.get(i+1).getNombreBookMark()=="REIVINDICACION")    
                    {   
//                        if ((i+2)<listaReportes.size()){
//                            if(listaReportes.get(i+2).getNombreBookMark()=="DOCUMENTO RESUMEN"){
//                                HashMap<String, Object> kid = new HashMap<String, Object>();
//                                bookmarks.add(kid);
//                                kid.put("Title", "MEMORIA TECNICA");
//                                kid.put("Action", "GoTo");
//                                kid.put("Page", String.format("%d Fit", page_offset + 1));
//                                n = reader.getNumberOfPages();
//                                page_offset += n;
//                                for (int page = 0; page < n;) {
//                                    copy.addPage(copy.getImportedPage(reader, ++page));
//                                }
//                                if (vistaPrevia) {
//                                    reader = new PdfReader(crearMarcaAguaVistaPrevia(listaReportes.get(i+1).getReporteBytes()));
//                                } else {
//                                    reader = new PdfReader(listaReportes.get(i+1).getReporteBytes());
//                                }
//                                n = reader.getNumberOfPages();
//                                page_offset += n;
//                                for (int page = 0; page < n;) {
//                                    copy.addPage(copy.getImportedPage(reader, ++page));
//                                }
//                                if (vistaPrevia) {
//                                    reader = new PdfReader(crearMarcaAguaVistaPrevia(listaReportes.get(i+2).getReporteBytes()));
//                                } else {
//                                    reader = new PdfReader(listaReportes.get(i+2).getReporteBytes());
//                                }
//                                i+=2;                                
//                            }else{
//                                HashMap<String, Object> kid = new HashMap<String, Object>();
//                                bookmarks.add(kid);
//                                kid.put("Title", "MEMORIA TECNICA");
//                                kid.put("Action", "GoTo");
//                                kid.put("Page", String.format("%d Fit", page_offset + 1));
//                                n = reader.getNumberOfPages();
//                                page_offset += n;
//                                for (int page = 0; page < n;) {
//                                    copy.addPage(copy.getImportedPage(reader, ++page));
//                                }
//                                if (vistaPrevia) {
//                                    reader = new PdfReader(crearMarcaAguaVistaPrevia(listaReportes.get(i+1).getReporteBytes()));
//                                } else {
//                                    reader = new PdfReader(listaReportes.get(i+1).getReporteBytes());
//                                }
//                                i++;
//                            }
//                        }else{
                            HashMap<String, Object> kid = new HashMap<String, Object>();
                            bookmarks.add(kid);
                            kid.put("Title", "MEMORIA TECNICA");
                            kid.put("Action", "GoTo");
                            kid.put("Page", String.format("%d Fit", page_offset + 1));
                            n = reader.getNumberOfPages();
                            page_offset += n;
                            for (int page = 0; page < n;) {
                                copy.addPage(copy.getImportedPage(reader, ++page));
                            }
                            if (vistaPrevia) {
                                reader = new PdfReader(crearMarcaAguaVistaPrevia(listaReportes.get(i+1).getReporteBytes()));
                            } else {
                                reader = new PdfReader(listaReportes.get(i+1).getReporteBytes());
                            }
                            i++;
//                        }
                    }else if(listaReportes.get(i).getNombreBookMark()=="TRAD. DESCRIPCION"){
                            if ((i+1)<listaReportes.size()){
                                if (listaReportes.get(i+1).getNombreBookMark()=="TRAD. REIVINDICACION"){
//                                    if ((i+2)<listaReportes.size()){
//                                        if (listaReportes.get(i+1).getNombreBookMark()=="TRAD. RESUMEN"){
//                                            HashMap<String, Object> kid = new HashMap<String, Object>();
//                                            bookmarks.add(kid);
//                                            kid.put("Title", "TRADUCCION DE MEMORIA TECNICA");
//                                            kid.put("Action", "GoTo");
//                                            kid.put("Page", String.format("%d Fit", page_offset + 1));
//                                            n = reader.getNumberOfPages();
//                                            page_offset += n;
//                                            for (int page = 0; page < n;) {
//                                            //   reader.setViewerPreferences(new PdfViewerPreferencesImp());
//                                                copy.addPage(copy.getImportedPage(reader, ++page));
//                                            }
//                                            if (vistaPrevia) {
//                                                reader = new PdfReader(crearMarcaAguaVistaPrevia(listaReportes.get(i+1).getReporteBytes()));
//                                            } else {
//                                                reader = new PdfReader(listaReportes.get(i+1).getReporteBytes());
//                                            }
//                                            n = reader.getNumberOfPages();
//                                            page_offset += n;
//                                            for (int page = 0; page < n;) {
//                                                copy.addPage(copy.getImportedPage(reader, ++page));
//                                            }
//                                            if (vistaPrevia) {
//                                                reader = new PdfReader(crearMarcaAguaVistaPrevia(listaReportes.get(i+2).getReporteBytes()));
//                                            } else {
//                                                reader = new PdfReader(listaReportes.get(i+2).getReporteBytes());
//                                            }                                            
//                                            i+=2;                                                                                          
//                                        }else{
//                                            HashMap<String, Object> kid = new HashMap<String, Object>();
//                                            bookmarks.add(kid);
//                                            kid.put("Title", "TRADUCCION DE MEMORIA TECNICA");
//                                            kid.put("Action", "GoTo");
//                                            kid.put("Page", String.format("%d Fit", page_offset + 1));
//                                            n = reader.getNumberOfPages();
//                                            page_offset += n;
//                                            for (int page = 0; page < n;) {
//                                            //   reader.setViewerPreferences(new PdfViewerPreferencesImp());
//                                                copy.addPage(copy.getImportedPage(reader, ++page));
//                                            }
//                                            if (vistaPrevia) {
//                                                reader = new PdfReader(crearMarcaAguaVistaPrevia(listaReportes.get(i+1).getReporteBytes()));
//                                            } else {
//                                                reader = new PdfReader(listaReportes.get(i+1).getReporteBytes());
//                                            }
//                                            i++;                                             
//                                        }
//                                    }else{
                                        HashMap<String, Object> kid = new HashMap<String, Object>();
                                        bookmarks.add(kid);
                                        kid.put("Title", "TRADUCCION DE MEMORIA TECNICA");
                                        kid.put("Action", "GoTo");
                                        kid.put("Page", String.format("%d Fit", page_offset + 1));
                                        n = reader.getNumberOfPages();
                                        page_offset += n;
                                        for (int page = 0; page < n;) {
                                            copy.addPage(copy.getImportedPage(reader, ++page));
                                        }
                                        if (vistaPrevia) {
                                            reader = new PdfReader(crearMarcaAguaVistaPrevia(listaReportes.get(i+1).getReporteBytes()));
                                        } else {
                                            reader = new PdfReader(listaReportes.get(i+1).getReporteBytes());
                                        }
                                        i++; 
//                                    }
                                }
                            }else{
                                    HashMap<String, Object> kid = new HashMap<String, Object>();
                                    bookmarks.add(kid);
                                    kid.put("Title", "TRADUCCION DE MEMORIA TECNICA");
                                    kid.put("Action", "GoTo");
                                    kid.put("Page", String.format("%d Fit", page_offset + 1));
                            }
                    }else if(listaReportes.get(i).getNombreBookMark()=="TRAD. REIVINDICACION"){
                        HashMap<String, Object> kid = new HashMap<String, Object>();
                        bookmarks.add(kid);
                        kid.put("Title", "TRADUCCION DE MEMORIA TECNICA");
                        kid.put("Action", "GoTo");
                        kid.put("Page", String.format("%d Fit", page_offset + 1));
                    }else
                    {
                        HashMap<String, Object> kid = new HashMap<String, Object>();
                        bookmarks.add(kid);
                        kid.put("Title", listaReportes.get(i).getNombreBookMark());
                        kid.put("Action", "GoTo");
                        kid.put("Page", String.format("%d Fit", page_offset + 1));
                    }
                }
                n = reader.getNumberOfPages();
                page_offset += n;

                for (int page = 0; page < n;) {
                    //   reader.setViewerPreferences(new PdfViewerPreferencesImp());
                    copy.addPage(copy.getImportedPage(reader, ++page));
                }
            }
            if (bookmarks != null && !bookmarks.isEmpty()) {
                copy.setOutlines(bookmarks);
            }
            document.close();
        } catch (DocumentException de) {
            de.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();           
        }
        return baos != null ? baos : null;
    }
    
      public byte[] agregarNumSolicitudReporte(byte[] byteArray, String numSolicitud)
    {
       ByteArrayOutputStream baosReporteFinal = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfImportedPage pdfimportedpage = null;
            //Se crea un reader para el PDF
            PdfReader reader = new PdfReader(byteArray);

            //Obtenemos el número de páginas del archivo 
            PdfWriter pdfwriter = PdfWriter.getInstance(document, baosReporteFinal);
            int total = reader.getNumberOfPages();

            //Se abre el archivo
            document.open();

            //Declaro el PdfContentByte obteniendo el Direct Content del PDF.
            PdfContentByte pdfcontentbyte = pdfwriter.getDirectContent();
            //TEMPLATE PARA TEXTO VISTA PREVIA
            //TEMPLATES PARA LEYENDA A MOSTRA
            PdfTemplate template = pdfcontentbyte.createTemplate(300, 150);
           


            //ESTILO DE FUENTE
            BaseFont bf = BaseFont.createFont(
                    BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

            //FOR PARA AGREGAR MARCA DE AGUA A TODAS LAS PAGINAS DEL PDF
            for (int j = 1; j <= total; j++) {

                //Crear pagina con tamaño de hoja original
                Rectangle pageSize = reader.getPageSizeWithRotation(j);
                document.setPageSize(pageSize);

                //BLOQUE DE TEMPLATE PARA LABEL DE VISTA PREVIA
                template.beginText();
                template.setColorFill(Color.black);
                template.setFontAndSize(bf, 3);
                template.setTextMatrix(0, 0);
                template.showText(numSolicitud);
                template.endText();               

                document.newPage();
                pdfimportedpage = pdfwriter.getImportedPage(reader, j);
                
                //Obtener tamaño vertical de la hoja para alineacion
                float urX = pageSize.getWidth() - 15;     
                float urY = pageSize.getHeight()- 40;   
                //LOS ULTIMOS DOS VALORES INDICAN A X y Y RESPECTIVAMENTE
                pdfcontentbyte.addTemplate(pdfimportedpage, 0.0F, -10.0F);
               pdfcontentbyte.addTemplate(template, 0, -4, 4,  0, urX, urY);             
            }
            document.close();
           
        } catch (IOException | DocumentException ex) {
            ex.printStackTrace();
           
        }
        return baosReporteFinal.toByteArray();
    
    
    }

  // itextpdf-5.4.1.jar  http://sourceforge.net/projects/itext/files/iText/
  public void htmltoPDF(String txtHTML) throws DocumentException {
    try {
      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
      Date fecha=new Date();
      String strElemento = "";
      BaseFont dejavusans = BaseFont.createFont(request.getRealPath("") + "/content/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
      Font fontDejavuSans = new Font(BaseFont.createFont(request.getRealPath("") + "/content/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, 
      BaseFont.EMBEDDED), 8, Font.NORMAL, new Color(0, 0, 0));
      fontDejavuSans.setSize(8);
      Document document = new Document(PageSize.LETTER);
      HeaderFooter footer = new HeaderFooter(new Phrase("This is page "), new Phrase("."));
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
      
//      HashMap<String,Object> providers = new HashMap<String, Object>();
//      rduFontProvider dfp = new rduFontProvider(request.getRealPath("") + "/content/fonts/DejaVuSans.ttf");
//      providers.put(HTMLWorker.FONT_PROVIDER, dfp); 
//      List<Element> htmlObjs = HTMLWorker.parseToList(new StringReader(str),null,providers);
//      for (Element element : htmlObjs){
//            document.add(element);
//      }
      //HTMLWorker htmlWorker = new HTMLWorker(document);
//        htmlWorker.setProviders(providers);
//      ArrayList<Element> htmlObjs = HTMLWorker.parseToList(new StringReader(str), estilo);
//      for (Element element : htmlObjs) {
//            
//            document.add(element);
//        }
//        for (int k = 0; k < htmlObjs.size(); ++k) {
//           // strElemento = htmlObjs.get(k).toString();
////      Element elemento;
//            Paragraph parrafo = new Paragraph();
//            parrafo.setFont(fontDejavuSans);
//            parrafo.add(htmlObjs.get(k));
//            document.add(parrafo);
//        }
//      elemento=new Chunk(strElemento, new Font(dejavusans, 8));
////      document.add(new Chunk(strElemento, new Font(dejavusans, 8)));
//      document.add(elemento);
//      }              
      HTMLWorker htmlWorker = new HTMLWorker(document);
      htmlWorker.setStyleSheet(estilo);
      
//      String str = "<h3 align='center' >"+ "Modelo Industrial de Pruebas" +"</h3>"+  txtHTML;
      htmlWorker.parse(new StringReader(str));
      document.close();
      
      FileOutputStream fos = new FileOutputStream (new File("/opt/rptGenerado"+ fecha.getDate()+"_" + fecha.getHours()+"_"+fecha.getMinutes()+".pdf")); 
      ByteArrayOutputStream baos = agregaNumPaginas(baosArchivo);
    // Put data in your baos
      baos.writeTo(fos);
      System.out.println("Done");
      }
    catch (Exception e) {
      e.printStackTrace();
    }
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
                //Se define el texto que se agregara como marca de agua
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
     //       Chunk ck = new Chunk(String.valueOf(numeroPagina), new Font(arial, 10, Font.NORMAL, Color.BLACK));
//            celda = new PdfPCell(new Phrase(ck));
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

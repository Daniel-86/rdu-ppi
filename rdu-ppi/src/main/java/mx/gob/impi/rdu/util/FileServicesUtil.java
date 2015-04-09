package mx.gob.impi.rdu.util;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.exceptions.BadPasswordException;
import com.lowagie.text.exceptions.InvalidPdfException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import mx.gob.impi.rdu.firma.exception.FileException;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mx.gob.impi.rdu.dto.ImageDataDto;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

/**
 * Clase que implementa los servicios para acceso a archivos.
 *
 * @version 0.1
 * @author INFOTEC
 *
 */
public class FileServicesUtil {

    public static byte[] getDataByteArray(String filePath) throws FileException {

        byte[] result = (byte[]) null;
        File file = new File(filePath);
        if ((filePath == null) || (file == null) || (!file.exists())) {
            throw new FileException("No existe el archivo especificado: \""
                    + filePath + "\".");
        }

        DataInputStream input = null;
        try {
            input = new DataInputStream(new FileInputStream(file));
            result = new byte[(int) file.length()];
            input.readFully(result);

        } catch (FileNotFoundException fnfe) {
            throw new FileException("Archivo no encontrado: \"" + file + "\".",
                    fnfe);
        } catch (IOException ioe) {
            throw new FileException("No ha sido posible leer el archivo: \""
                    + file + "\".", ioe);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    throw new FileException(
                            "No ha sido posible cerrar el archivo: \"" + file
                            + "\".");
                }
            }
        }
        return result;

    }

    public static void createDocxFile(String path, int fontSize, boolean bold, boolean italic, String text) {
        XWPFDocument document = new XWPFDocument();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        createParagraph(document, ParagraphAlignment.BOTH, fontSize, bold, italic, text);
        try {
            document.write(baos);
            baos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ByteArrayOutputStream createDocFile(String title, String content) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        try {
            RtfWriter2 pdf = RtfWriter2.getInstance(document,
                    baos);
            //new FileOutputStream("/home/cesar/text.rtf"));
            HeaderFooter header = new HeaderFooter(
                    new Phrase(title, new RtfFont("Arial", 10F, Font.BOLD)), false);
            document.open();
            header.setAlignment(Element.ALIGN_CENTER);
//            HeaderFooter footer = new HeaderFooter(
//                    new Phrase("Welcome to Technofriends "), new Phrase("."));
//            footer.setAlignment(Element.ALIGN_CENTER);
            //document.setFooter(footer);
            document.setHeader(header);
            Paragraph para = new Paragraph(content, new RtfFont("Arial", 10F));
            document.add(para);
//            document.add(new Paragraph("Subscribe to Technofriends at http://technofriends.in/feed"));
        } catch (DocumentException docex) {
            docex.printStackTrace();
        } catch (Exception docex) {
            docex.printStackTrace();
        }
        document.close();
        //GenerarReporte.writePdf(baos, "/home/cesar/archivoword.doc");
        return baos;
    }

    public static void createParagraph(XWPFDocument document, ParagraphAlignment align, int fontSize, boolean bold, boolean italic, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(align);
        //paragraphOne.setBorderBottom(Borders.SINGLE);
        //paragraphOne.setBorderTop(Borders.SINGLE);
        //paragraphOne.setBorderRight(Borders.SINGLE);
        //paragraphOne.setBorderLeft(Borders.SINGLE);
        //paragraphOne.setBorderBetween(Borders.SINGLE);
        XWPFRun paragraphRun = paragraph.createRun();
        paragraphRun.setBold(bold);
        paragraphRun.setItalic(italic);
        paragraphRun.setText(text);
        paragraphRun.setFontSize(fontSize);
        paragraphRun.addBreak();
    }

    public static ByteArrayOutputStream computeSize(byte[] buff, final int MAX_WIDTH, final int MAX_HEIGHT) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedImage bufferedImage = null;
        double width = 0;
        double height = 0;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(buff));
            width = bufferedImage.getWidth();
            height = bufferedImage.getHeight();
            if (width > MAX_WIDTH) {
                height = MAX_WIDTH * (height / width);
                width = MAX_WIDTH;
            }
            if (height > MAX_HEIGHT) {
                width = MAX_HEIGHT * ((width / height));
                height = MAX_HEIGHT;
            }
            baos = scaleImage(bufferedImage, (int) width, (int) height);
        } catch (IOException ex) {
            ex.printStackTrace();
            baos = null;
        }
        return baos;
    }

    public static ByteArrayOutputStream scaleImage(BufferedImage img, int width, int height /*, Color background*/) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int imgWidth = img.getWidth();
        int imgHeight = img.getHeight();
        if (imgWidth * height < imgHeight * width) {
            width = imgWidth * height / imgHeight;
        } else {
            height = imgHeight * width / imgWidth;
        }
        BufferedImage newImage = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = newImage.createGraphics();
        try {
            /*
             g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
             RenderingHints.VALUE_INTERPOLATION_BICUBIC);
             * */
            //g.setBackground(background);
            g.clearRect(0, 0, width, height);
            g.drawImage(img, 0, 0, width, height, null);
            ImageIO.write(newImage, "jpg", baos);
//            ImageIO.write(newImage, "jpg", new FileOutputStream(path + "archivo_modificado.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
            baos = null;
        } finally {
            g.dispose();
        }
        return baos;
    }

    public static boolean checkSizeImage(byte[] buff, int minHeight, int minWidth, int maxHeight, int maxWidth) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(buff));
            if (image.getHeight() <= maxHeight && image.getHeight() >= minHeight && image.getWidth() <= maxWidth
                    || image.getWidth() <= maxWidth && image.getWidth() >= minWidth && image.getHeight() <= maxHeight) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean checkSizeImage(byte[] buff, int min, int max) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(buff));
//            System.out.println("Ancho: " + image.getWidth() + "\nAlto: " + image.getHeight());
            if (image.getHeight() >= min && image.getHeight() <= max && image.getWidth() >= min && image.getWidth() <= max) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean checkSizeImagePat(byte[] buff, int maxHeight, int maxWidth) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(buff));
//            System.out.println("Ancho: " + image.getWidth() + "\nAlto: " + image.getHeight());
            if (image.getHeight() <= maxHeight && image.getWidth() <= maxWidth) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

//    public static void main(String[] args) throws Exception {
//        URL yahoo = new URL("https://eservicios.impi.gob.mx/seimpi/GenPagoFEPSPdf?recuperacion=true&folios_feps=10010994449");
//        byte[] buffer = getBytes(yahoo.openStream());
//        System.out.println("Tamaño: " + buffer.length);
//        writePdf(buffer, "/home/oracle/archivo.pdf");
//    }
    public static byte[] getBytes(InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] buf;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        while ((len = is.read(buf, 0, size)) != -1) {
            bos.write(buf, 0, len);
        }
        buf = bos.toByteArray();
        return buf;
    }

    public static boolean validarArchivoProtegido(byte[] buffer) throws IOException {
        Document document = new Document();
        document.setPageSize(new Rectangle(612, 1008));
        PdfReader reader;
        try {
            reader = new PdfReader(buffer);
            reader.getNumberOfPages();
            return reader.isEncrypted();
        } catch (BadPasswordException bpe) {
            return true;
        } catch (InvalidPdfException ipe) {
            return true;
        }
    }

    public static boolean checkMimeType(String fileName, byte[] file) throws IOException, IllegalArgumentException {
        TikaConfig config = TikaConfig.getDefaultConfig();
        Detector detector = config.getDetector();
        TikaInputStream stream = TikaInputStream.get(file);
        Metadata metadata = new Metadata();
        MediaType mediaType = null;
        mediaType = detector.detect(stream, metadata);
        String mimeTypeContenidoArchivo = mediaType.toString();
        //fin: Anexos, identificacion de archivos renombrados

        MimeType mimeTypeNombreArchivo = getMimeType(getExtension(fileName));
        //String mimeTypeContenidoArchivo=mimeTypeNombreArchivo.getMime();
        if (mimeTypeContenidoArchivo.equals(mimeTypeNombreArchivo.getMime())) {
            return true;
        } else {
            return false;
        }
    }

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    public static MimeType getMimeType(String extension) throws IllegalArgumentException {
        MimeType[] valores = MimeType.values();
        for (MimeType mimeType : valores) {
            if (mimeType.getExtension().equals(extension)) {
                return mimeType;
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("La extension '").append(extension);
        sb.append("' no esta actualmente soportada como en el enum: ");
        sb.append(MimeType.class.toString());
        sb.append(" Revise el enum y el catalogo en BD.");
        throw new IllegalArgumentException(sb.toString());
    }

    public static boolean validaDPI(byte[] bytes) throws IOException {

        FileInputStream fis;
        ImageDataDto imageDataDto = null;
        try {
            InputStream in = new ByteArrayInputStream(bytes);

//            fis = new FileInputStream(nameArchivo);
            BufferedImage bufferedImage = ImageIO.read(in);


            float inche = (float) 2.54;
            float eqPxtoCm = (float) 0.026458333;

            if (bufferedImage != null) {
                imageDataDto = new ImageDataDto();
                //Insertar valores en PX
                imageDataDto.setAnchoPx(bufferedImage.getWidth());
                imageDataDto.setAltoPx(bufferedImage.getHeight());

                //Insertar calculos para valores en CM
                imageDataDto.setAnchoCm(imageDataDto.getAnchoPx() * eqPxtoCm);
                imageDataDto.setAltoCm(imageDataDto.getAltoPx() * eqPxtoCm);

                //Insertar los calculos para el DPI
                imageDataDto.setAnchoDpi(imageDataDto.getAnchoPx() * inche / imageDataDto.getAnchoCm());
                imageDataDto.setAltoDpi(imageDataDto.getAltoPx() * inche / imageDataDto.getAltoCm());

                //Validar requerimientos de DPI maximo 4000
                if (imageDataDto.getAnchoDpi() <= 4000) {

                    //Cumple con el requerimiento de dpi.
                    imageDataDto.setCumpleReq(true);
                } else {
                    //No cumple con el requerimiento de dpi.
                    imageDataDto.setCumpleReq(false);
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileServicesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Retorna el objeto lleno de los valores de la imagen
        //y la bandera que valida la restriccion de DPI.
        return imageDataDto.isCumpleReq();
    }

    public static boolean validaGifAnimado(byte[] bs) {
        StringBuffer stringImagen = new StringBuffer();
        StringBuffer patron = new StringBuffer();

        for (int i = 0; i < bs.length; i++) {
            stringImagen.append((int) bs[i]);
        }
        for (int i = 0; i < Constantes.bytesGifAnimado.length; i++) {
            patron.append((int) Constantes.bytesGifAnimado[i]);
        }
        
        if(stringImagen.toString().indexOf(patron.toString()) != -1)
        {
            return false;
        }
        return true;
    }
}

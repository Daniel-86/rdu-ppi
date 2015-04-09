package mx.gob.impi.rdu.util;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfReader;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Collections;
import java.util.List;
import mx.gob.impi.rdu.dto.DomicilioDto;
import mx.gob.impi.rdu.dto.FiltroTablero;
import mx.gob.impi.rdu.dto.TipoCriterioDto;
import mx.gob.impi.rdu.dto.TipoTramiteDto;
import mx.gob.impi.rdu.persistence.model.Domicilio;
import mx.gob.impi.rdu.persistence.model.Persona;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import org.apache.log4j.Logger;

/**
 * ***************************************************************************
 */
/*
 * Name: Infotec /* Date: 06-jun-2012 12:54:17 /* Description: Utilidades para
 * el sistema
 */
/**
 * ***************************************************************************
 */
public class Util {

    public static final String FORMATODDMMYYYY = "dd/MM/yyyy";
    public static final String FORMATODDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMATOYYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";
    public static final String FORMATO_EVENTO = "dd/MM/yyyy HH:mm";
    public static final int ASCENDENTE = 0;
    public static final int DESCENDENTE = 1;
    public static Logger lger = Logger.getLogger("rdu-model_Util");

    /**
     * Obtiene la fecha con el formato establecido en el diccionario
     *
     * @param valor
     * @return
     */
    public static Date parsearFecha(String valor) {
        Date fecha = null;
        String formato = "";
        try {
            formato = valor.charAt(2) == '/' ? "dd/MM/yyyy"
                    : valor.charAt(2) == '-' ? "dd-MM-yyyy"
                    : valor.charAt(4) == '/' ? "yyyy/MM/dd" : valor.charAt(4) == '-' ? "yyyy-MM-dd" : "";
            fecha = parsearFecha(valor, formato);

        } catch (Exception e) {
            fecha = null;
        }

        return fecha;
    }

    /**
     * Obtiene la fecha con el formato indicado
     *
     * @param valor
     * @param formato
     * @return
     */
    public static Date parsearFecha(String valor, String formato) {

        Date fecha = null;
        SimpleDateFormat formateador = new SimpleDateFormat(formato);
        try {
            fecha = formateador.parse(valor);
        } catch (ParseException e) {
            fecha = null;
        }
        return fecha;
    }

    /**
     * Asigna el formato indicado a la fecha
     *
     * @param date
     * @param formato
     * @return
     */
    public static String formatearFecha(Date date, String formato) {

        String fecha = null;
        SimpleDateFormat formateador = new SimpleDateFormat(formato);
        if (date == null) {
            fecha = "\\N";
        } else {
            fecha = formateador.format(date);
        }
        return fecha;
    }

    /**
     * El formato por default para la fecha
     *
     * @param date
     * @return
     */
    public static String formatearFecha(Date date) {

        String fecha = null;
        SimpleDateFormat formateador = new SimpleDateFormat(FORMATODDMMYYYY);
        fecha = formateador.format(date);
        return fecha;
    }

    /**
     * Obtiene la fecha con el formato dd/MM/yyyy HH24:mm
     *
     * @param date
     * @return
     */
    public static String formatearFechaEvento(Date date) {

        String fecha = null;
        SimpleDateFormat formateador = new SimpleDateFormat(FORMATO_EVENTO);
        fecha = formateador.format(date);
        return fecha;
    }

    /**
     * Asigna un timestamp a una fecha con el formato YYYY-MM-DD HH24:MI
     *
     * @param date
     * @return
     */
    public static Date formatearFechaToEvento(String valor) {
        Date fecha = null;
        SimpleDateFormat formateador = new SimpleDateFormat(FORMATO_EVENTO);
        try {
            fecha = formateador.parse(valor);
        } catch (ParseException e) {
            fecha = null;
        }
        return fecha;
    }

    /**
     * La fecha a partir del no. de dias indicado
     *
     * @param date
     * @param dias
     * @return
     */
    public static Date restarDias(Date date, int dias) {
        Calendar fecha = Calendar.getInstance(); // obtiene la fecha actual
        fecha.add(Calendar.DATE, -dias); // incrementa en 30 dï¿œas la fecha
        // actual.
        return fecha.getTime();
    }

    /**
     * Restar N horas ala fecha indicada
     *
     * @param date
     * @param dias
     * @return
     */
    public static Date restarHoras(Date date, int hora) {
        Calendar fecha = Calendar.getInstance(); // obtiene la fecha actual
        fecha.setTime(date);
        fecha.add(Calendar.HOUR, -hora); // incrementa en 30 dias la fecha
        // actual.
        return fecha.getTime();
    }

    /**
     * Obtiene la hora del dia respecto a la fecha indicada
     *
     * @param date
     * @return
     */
    public String getHumanHour(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        String hourOfDay = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        return hourOfDay;
    }

    /**
     * Obtiene la hora del dia respecto a la epoca
     *
     * @param epoch
     * @return
     */
    public String getHumanHour(Long epoch) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTimeInMillis(epoch);
        String hourOfDay = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        return hourOfDay;
    }

    /**
     * La fecha actual del sistema
     *
     * @param format
     * @return
     */
    public String getDateTime(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Obtiene el no. de minutes entre una fecha y otra
     *
     * @param oldTime
     * @param currentTime
     * @return
     */
    public Long compareTimesByMinutes(Timestamp oldTime, Timestamp currentTime) {
        Long difmiliSeconds = currentTime.getTime() - oldTime.getTime(); // obtiene el no. de milisegundos
        difmiliSeconds = (difmiliSeconds / (1000 * 60));// la diferencia en minutos
        return difmiliSeconds;
    }

    /**
     * Metodo creado para obtener las horas que faltan para llegar a la hora
     * meta y hora limite de captura de una encuesta
     *
     * @param oldTime
     * @param currentTime
     * @return
     */
    public String getDifferentialFromDay(Timestamp oldTime, Timestamp currentTime) {
        String differentialHumanHour = "";
        try {
            Long differential = currentTime.getTime() - oldTime.getTime();

            if (differential > Constantes.FIRST) {
                Long hour = differential / (60 * 60 * 1000);
                Long minutes = differential / (60 * 1000);
                Long seconds = differential / 1000;

                Long minutesDiff = hour.equals(Constantes.FIRST_LONG) ? (minutes % 60) : (minutes % (hour * 60));
                Long secondsDiff = minutes.equals(Constantes.FIRST_LONG) ? (seconds % 60) : (seconds % (minutes * 60));

                differentialHumanHour = hour + ":" + minutesDiff + ":" + secondsDiff;
            } else {
                differentialHumanHour = "00:00:00";
            }
        } catch (Exception e) {
        }
        return differentialHumanHour;
    }

    /**
     * Asigna el formato indicado a la fecha
     *
     * @param date
     * @param formato
     * @return
     */
    public static boolean compareTimesByString(Date date, String formato, String fechaExpediente) {   
        boolean flag = true;
        
        SimpleDateFormat formateador = new SimpleDateFormat(formato);
        String fechaFormato = formateador.format(date);

        String fecha[] = fechaFormato.split("/");
        if (fechaExpediente.equals(fecha[2])) 
            flag = false;
        else
            flag = true;
            
        return flag;
    }
    
    
    public Date getFirstDayYear(Date date) {
        Date fecha = null;
        
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        
        calendar.add(calendar.DAY_OF_MONTH, Constantes.INIT);
        calendar.add(calendar.DAY_OF_MONTH, Constantes.INIT);
        calendar.add(calendar.YEAR, calendar.get(Calendar.YEAR));
        
        fecha = calendar.getTime();
        
        return fecha;
    }

    public String extractExtension(String fileName) {
        String extension = null;
        extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        return extension;
    }

    public MimeType extractMime(String extension) {
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
    /*
     * Funcion para calcular el ancho de una cadena dependiendo de su fuente
     *
     */

    public static int fontSize(String string, Font font) {
        BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
        FontMetrics fm = bi.getGraphics().getFontMetrics(font);
        return fm.stringWidth(string);
    }

    public static int positionSplit(String string, Font font, int length) {
        int position = 0;
        int width = 0;
        BufferedImage bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
        FontMetrics fm = bi.getGraphics().getFontMetrics(font);
        for (int i = 0; i <= string.length(); i++) {
            width = fm.stringWidth(string.substring(0, i));
            //System.out.println("Ancho: " + width + "Maximo: " + longitud);
            if (width > length) {
                position = i - 1;
                break;
            }
        }
        return position;
    }

    public static String reemplazaAcentos(String p1) {

        p1 = p1.replaceAll("[\u00E8\u00E9\u00EA\u00EB]", "e");
        p1 = p1.replaceAll("[\u00F9\u00FA\u00FC\u00FB]", "u");
        p1 = p1.replaceAll("[\u00EF\u00EE\u00EC\u00ED]", "i");
        p1 = p1.replaceAll("[\u00E0\u00E2\u00E1\u00E4]", "a");
        p1 = p1.replaceAll("[\u00F2\u00F3\u00F6\u00F4]", "o");
        p1 = p1.replaceAll("[\u00C0\u00C1\u00C2\u00C4]", "A");
        p1 = p1.replaceAll("[\u00C8\u00C9\u00CB\u00CA]", "E");
        p1 = p1.replaceAll("[\u00CE\u00CD\u00CF\u00EE]", "I");
        p1 = p1.replaceAll("[\u00D2\u00D3\u00D6\u00D4]", "O");
        p1 = p1.replaceAll("[\u00D9\u00DA\u00DC\u00DB]", "U");
        //p1 = p1.replaceAll("[\u00D1]", "N");
        //p1 = p1.replaceAll("[\u00F1]", "n");
        //p1 = p1.replaceAll("[\\s]", "");


        return p1;
    }

    public static String getRows(String string, Font font, int count, int numRows) {
        String rows[] = new String[numRows];
        String result = "";
        for (int i = 0; i < numRows; i++) {
            if (fontSize(string, font) > count) {
                //System.out.println("Tamaño: " + fontSize(string, font));
                //System.out.println("Position:"+positionSplit(string, font, count));
                rows[i] = string.substring(0, getRow(string, positionSplit(string, font, count)));
                //System.out.println(rows[i]);
                string = string.substring(getRow(string, positionSplit(string, font, count)));
                result += rows[i];
            } else {
                rows[i] = string.substring(0, string.length());
                result += rows[i];
                break;
            }
        }
        return result;
    }

    public static <T> Iterable<T> checkListNull(Iterable<T> iterable) {
        return iterable == null ? Collections.<T>emptyList() : iterable;
    }

    public static String getDigest(byte[] strAttributes) {
        String digestivo = null;
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("SHA-1");
            byte[] digest = messageDigest.digest(strAttributes);
            digestivo = encoder.encode(digest);  //com.advantage.security.utils.Base64Utils.base64Encode(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return digestivo;
    }

    public static String nPosiciones(String value, int posiciones) {
       
        for (int i = value.length(); i < posiciones; i++) {
            value = "0" + value;
        }
        return value;
    }

    public static int getRow(String string, int length) {
        int index = 0;
        for (int i = length; i >= 0; i--) {
            if (string.charAt(i) == ' ') {
                index = string.substring(0, i).length();
                break;
            }
        }
        return index;
    }

    public static List<TipoTramiteDto> geTiposTramite() {
        List<TipoTramiteDto> retFiltrosTipoTramite = new ArrayList();

        retFiltrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite(), TipoTramiteEnum.SOL_PATENTES.getDescripcion()));
        //retFiltrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite(), TipoTramiteEnum.PROM_PATENTES.getDescripcion()));
        retFiltrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.SOL_SIT.getIdTipoTramite(), TipoTramiteEnum.SOL_SIT.getDescripcion()));
        return retFiltrosTipoTramite;
    }

    public static List<TipoTramiteDto> geTiposTramite(String area) {

        List<TipoTramiteDto> retFiltrosTipoTramite = new ArrayList();

        if (area.equals("4")) {
            retFiltrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite(), TipoTramiteEnum.SOL_PATENTES.getDescripcion()));
            retFiltrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite(), TipoTramiteEnum.PROM_PATENTES.getDescripcion()));
        }
        if (area.equals("20")) {
            retFiltrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.SOL_SIT.getIdTipoTramite(), TipoTramiteEnum.SOL_SIT.getDescripcion()));
            }
        return retFiltrosTipoTramite;
    }

    public static List<TipoTramiteDto> geTiposTramiteTipoTableros(int pTipoTableros) {
        List<TipoTramiteDto> retFiltrosTipoTramite = new ArrayList();

        if (pTipoTableros == 0) {
            retFiltrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.SOL_SIT.getIdTipoTramite(), TipoTramiteEnum.SOL_SIT.getDescripcion()));
            retFiltrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite(), TipoTramiteEnum.SOL_PATENTES.getDescripcion()));
            //retFiltrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite(), TipoTramiteEnum.PROM_PATENTES.getDescripcion()));
        }
        return retFiltrosTipoTramite;
    }

    public static List<FiltroTablero> geFiltrosExtra() {
        List<FiltroTablero> filtrosTablero = new ArrayList();
        filtrosTablero.add(new FiltroTablero(FiltroExtraEnum.TODOS.getIdFiltroExtra(), FiltroExtraEnum.TODOS.getDescripcion()));
        filtrosTablero.add(new FiltroTablero(FiltroExtraEnum.ULTIMA_SEMANA.getIdFiltroExtra(), FiltroExtraEnum.ULTIMA_SEMANA.getDescripcion()));
        filtrosTablero.add(new FiltroTablero(FiltroExtraEnum.ULTIMO_MES.getIdFiltroExtra(), FiltroExtraEnum.ULTIMO_MES.getDescripcion()));
        filtrosTablero.add(new FiltroTablero(FiltroExtraEnum.RANGO.getIdFiltroExtra(), FiltroExtraEnum.RANGO.getDescripcion()));
        return filtrosTablero;
    }

    public static List<TipoCriterioDto> getTiposCriterio() {
        List<TipoCriterioDto> retFilTipFec = new ArrayList();
        retFilTipFec.add(new TipoCriterioDto(TipoCriterioEnum.TODO.getIdTipoFecha(), TipoCriterioEnum.TODO.getDescripcion()));
        retFilTipFec.add(new TipoCriterioDto(TipoCriterioEnum.ULTIMA_SEMANA.getIdTipoFecha(), TipoCriterioEnum.ULTIMA_SEMANA.getDescripcion()));
        retFilTipFec.add(new TipoCriterioDto(TipoCriterioEnum.ULTIMO_MES.getIdTipoFecha(), TipoCriterioEnum.ULTIMO_MES.getDescripcion()));
        retFilTipFec.add(new TipoCriterioDto(TipoCriterioEnum.POR_RANGO.getIdTipoFecha(), TipoCriterioEnum.POR_RANGO.getDescripcion()));
        return retFilTipFec;
    }

    public static String encodeObject(Object obj) throws Exception {
        try {

            BASE64Encoder enc = new BASE64Encoder();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            byte[] byter = bos.toByteArray();
            out.close();
            bos.close();

            return enc.encode(byter);
        } catch (Exception ex) {
            //TODO: Handle the exception
            ex.printStackTrace();
        }

        return "";
    }

    public static Object decodeObject(String objectString) {
        try {
            BASE64Decoder dec = new BASE64Decoder();
            byte[] bs = dec.decodeBuffer(objectString);

            ByteArrayInputStream bis = new ByteArrayInputStream(bs);
            ObjectInput in = new ObjectInputStream(bis);
            Object obj = in.readObject();
            bis.close();
            in.close();
            return obj;

        } catch (Exception e) {
            lger.error("ERROR: AL DECODIFICAR >>> " + e.getLocalizedMessage() + "   >>>>  " + e.getMessage(), e);
            return null;
        }
    }

    public static Object decodeByteSigned(String objectString) {
        try {
            BASE64Decoder dec = new BASE64Decoder();
            byte[] bs = dec.decodeBuffer(objectString);

            ByteArrayInputStream bis = new ByteArrayInputStream(bs);
            ObjectInput in = new ObjectInputStream(bis);
            Object obj = in.readObject();
            bis.close();
            in.close();
            return obj;

        } catch (Exception e) {
            return null;
        }
    }

    public static File writeTempFile(InputStream fileToWrite, String name, String extension) {
        File toWrite = null;
        FileOutputStream writer = null;

        int DEFAULT_CHUNK_SIZE = 1024;
        byte[] chunk = new byte[DEFAULT_CHUNK_SIZE];

        try {
            toWrite = File.createTempFile(name, extension);
            writer = new FileOutputStream(toWrite);

            if (fileToWrite.available() > 0) {
                while ((fileToWrite.read(chunk)) != -1) {
                    writer.write(chunk);
                } // while
            } // if

            // Se indica que se borre el fichero al terminar el programa
            toWrite.deleteOnExit();

            writer.flush();
            writer.close();
            fileToWrite.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return toWrite;
    }

    public static DomicilioDto recuperaDomicilioDto(Domicilio pDomicilio) {
        DomicilioDto vDomicilioDto = new DomicilioDto();

        try {

            if (pDomicilio.getCalle() != null) {
                vDomicilioDto.setCalle(pDomicilio.getCalle());
            }
            if (pDomicilio.getCodigopostal() != null) {
                vDomicilioDto.setCodigopostal(pDomicilio.getCodigopostal());
            }
            if (pDomicilio.getEntidad() != null) {
                vDomicilioDto.setEntidad(pDomicilio.getEntidad());
            }
            if (pDomicilio.getIdDomicilio() != null) {
                vDomicilioDto.setIdDomicilio(pDomicilio.getIdDomicilio());
            }
            if (pDomicilio.getIdEntidad() != null) {
                vDomicilioDto.setIdEntidad(pDomicilio.getIdEntidad());
            } else {
                vDomicilioDto.setIdEntidad("9");
            }

            if (pDomicilio.getIdPais() != null) {
                vDomicilioDto.setIdPais(pDomicilio.getIdPais());
            }
            if (pDomicilio.getColonia() != null) {
                vDomicilioDto.setColonia(pDomicilio.getColonia());
            }
            if (pDomicilio.getPoblacion() != null) {
                vDomicilioDto.setPoblacion(pDomicilio.getPoblacion());
            }

            return vDomicilioDto;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

    }

    public static boolean aplicaDescuento(List<Persona> solicitantes) {
        boolean isOff = false;

        for (Persona psn : solicitantes) {
            if (psn != null && psn.getDescuento() != null && psn.getDescuento() > 0) {
                if (psn.getIdTipoSolicitante() == Constantes.SOL_EMPRESA_GRANDE | psn.getIdTipoSolicitante() == Constantes.SOL_EMPRESA_MED | psn.getIdTipoSolicitante() == Constantes.SOL_PERSONA_MORAL) {
                    isOff = false;
                    break;
                } else {
                    isOff = true;
                }
            } else {
                isOff = false;
                break;
            }
        }

        return isOff;

    }

    public static long getNumberPages(byte[] pdfArray) throws Exception {
        Document document = new Document();
        document.setPageSize(new Rectangle(612, 1008));
        PdfReader reader = new PdfReader(pdfArray);
        return reader.getNumberOfPages();
    }

    /**
     * Converts a int value into a byte array.
     *
     * @param value int value to be converted
     * @return byte array containing the int value
     */
    public static byte[] intToByteArray(int value) {
        byte[] data = new byte[4];

        // int -> byte[]
        for (int i = 0; i < 4; ++i) {
            int shift = i << 3; // i * 8
            data[3 - i] = (byte) ((value & (0xff << shift)) >>> shift);
        }
        return data;
    }

    /**
     * Converts a byte array to an int value.
     *
     * @param data byte array to be converted
     * @return int value of the byte array
     */
    public static int byteArrayToInt(byte[] data) {
        // byte[] -> int
        int number = 0;
        for (int i = 0; i < 4; ++i) {
            number |= (data[3 - i] & 0xff) << (i << 3);
        }
        return number;
    }

    /**
     * Concatenates two byte arrays and returns the resulting byte array.
     *
     * @param a first byte array
     * @param b second byte array
     * @return byte array containing first and second byte array
     */
    public byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);

        return c;
    }

    public static String hCodePatentes(String folio, Date fecha) {
        return null;
    }

    public static String convertStringToHex(String string) {
        //<td><input type="button"  onclick="insertCharacter(this.value);" value="&#032;"/></td>
        String result = "";
        char[] characters = string.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            if (i < characters.length - 1) {
                result += "\\u" + zeroPad(Integer.toHexString((int) characters[i]), 4) + ",";
            } else {
                result += "\\u" + zeroPad(Integer.toHexString((int) characters[i]), 4);
            }
        }
        return result;
    }

    public static String zeroPad(String value, int width) {
        String result = "";
        if (value.length() < width) {
            for (int i = 0; i < width - value.length(); i++) {
                result += "0";
            }
        }
        return result + value;
    }

    public List<String> generarRangoAnios(int anioMinimoPromociones) {
        Calendar fchActual = Calendar.getInstance();
        List<String> listAnios = new ArrayList<String>();

        fchActual.setTime(new Date());

        for (Integer i = anioMinimoPromociones; i <= fchActual.get(Calendar.YEAR); i++) {
            listAnios.add(i.toString());
        }

        return listAnios;
    }

    public String recuperarNombreAnexo(Long tipoAnexo) {

        switch (tipoAnexo.intValue()) {
            case 1:
                return "DOC. PODER";
            case 2:
                return "DOC. PRIORIDAD";
            case 3:
                return "DOC. LEGAL";
            case 4:
                return "CONTRATO";
            case 5:
                return "DOC. CONTVO";
            case 6:
                return "REGLAS USO";
            case 7:
                return "OTROS";
            case 8:
                return "DOC . RPG";
            case 9:
                return "FORMATO SOLICITUD";
            case 10:
                return "FE ECHOS";
            case 15:
                return "DESCRIPCION";
            case 16:
                return "REIVINDICACION";
                //return "MEMORIA TÉCNICA";
            case 17:
                return "COMPROBANTE PAGO";  
            case 19:
                return "ACREDITA PERSONALIDAD";
            case 20:
                return "CESION DERECHOS";
            case 21:
                return "DIV PREVIA";
            case 22:
                return "PRIORIDAD";
            case 24:
                return "NOTIFICACION";
            case 27:
                return "TRADUCCION";
            case 29:
                return "CONST MAT BIOL";
            case 41:
                return "HOJA DE DESCUENTO";
            case 43:
                return "DOCUMENTO RESUMEN";
            case 44:
                return "ACUSE DE RECIBO";
            case 45:
                return "TRAD. DESCRIPCION";
            case 46:
                return "TRAD. REIVINDICACION"; 
            case 47:
                return "TRAD. RESUMEN";
            case 48:
                return "CARTA PODER SIMPLE";
            case 49:	
                return "CONSTANCIA RGP";
            case 50:   
                return "PODER NOTARIAL";
            case 51:	
                return "ACTA CONSTITUTIVA";
            case 52:	
                return "OTRO";
        }
        return null;
    }

    public String parseNombreAnexo(String nombreAnexo) {
        int i = 0;
        for (i = nombreAnexo.length() - 1; i >= 0; i--) {
            if (nombreAnexo.charAt(i) == File.separatorChar) {
                break;
            }
        }
        return nombreAnexo.substring(i + 1, nombreAnexo.length());
    }
    
    public boolean fueraRangoFechas(Date fecha, int numMeses)
    {
        Calendar fechaFin = Calendar.getInstance();
        Calendar fechaSeleccionada = Calendar.getInstance();
        Calendar fechaInicio = Calendar.getInstance();
                
        fechaInicio.setTime(new Date());
        fechaInicio.add(Calendar.MONTH, -numMeses);
        fechaInicio.add(Calendar.DAY_OF_YEAR, -5);
        
        fechaFin.setTime(new Date());
        
        fechaSeleccionada.setTime(fecha);
        
        if(fechaSeleccionada.compareTo(fechaInicio) >= 0 && 
                fechaFin.compareTo(fechaSeleccionada) >= 0)
        {
           return false;
        }
        
        return true;
    }
}

package mx.gob.impi.rdu.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.impi.rdu.exposition.SesionRDU;

import javax.servlet.ServletException;

import org.primefaces.context.RequestContext;
import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import mx.gob.impi.rdu.dto.ApoderadoDto;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.persistence.model.CatArea;
import mx.gob.impi.rdu.persistence.model.DocumentoArticulo;
import mx.gob.impi.rdu.persistence.model.Usuario;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;

import java.io.ByteArrayOutputStream;

public class ContextUtils {

    public static final String CURRENT_LOGGING_USER = "intendedUserToLogin_FROM THIS APPLICATION";
    public static final Map<String, Object> countries;
    //private static final String ENCODING = "ISO-8859-1";
    private static final String ENCODING = "UTF-8";

    static {
        countries = new LinkedHashMap<String, Object>();
        countries.put("English", new java.util.Locale("en", "US")); //label, value
        countries.put("French", new java.util.Locale("fr", "CA"));
        countries.put("Spanish", new java.util.Locale("es", "MX"));
        //countries.put("Chinese", Locale.SIMPLIFIED_CHINESE); // tambien sirve !!!
        countries.put("Chinese", new java.util.Locale("zh", "CN"));
    }

    public static void setNewLocal(String newLocaleValue) {
        for (Map.Entry<String, Object> entry : countries.entrySet()) {
            if (entry.getValue().toString().equals(newLocaleValue)) {
                FacesContext.getCurrentInstance().
                        getViewRoot().
                        setLocale((Locale) entry.getValue());
            }
        }
    }

    public static ResourceBundle getResourceBundle(String name) {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale lang = context.getViewRoot().getLocale();
        lang = new java.util.Locale("zh", "CN");
        return ResourceBundle.getBundle(name, lang);
    }

    public static void createGlobalFacesMessage(String summary, String detail) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
        context.addMessage(null, fm);
    }

    public static void addCallBackParam(boolean exito) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.addCallbackParam("exito", exito);
    }

    public static int getIntParam(String name) {
        Object obj = getParameter(name);
        if (obj == null) {
            return 0;
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) ContextUtils.getContext().getRequest();
    }

    public static HttpSession getSession() {
        return (HttpSession) (getContext().getSession(true));
    }

    public static Object getSessionMapObject(String name) {
        return getSessionMap().get(name);
    }

    private static Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public static Object getValueFromSessionMap(String key) {
        return getSessionMap().get(key);
    }

    public static void setValueToSessionMap(String key, Object obj) {
        getSessionMap().put(key, obj);
    }

    public static String getParameter(String name) {
        Map<String, String> requestMap = getContext().getRequestParameterMap();
        return requestMap.get(name);
    }

    public static ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    public static void addMsgInfo(String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    public static void addMsgWarn(String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail));
    }

    public static void addMsgError(String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
    }

    public static void addMsgFatal(String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, detail));
    }
    public static final String FORMATO_DDMMYYYY = "dd/MM/yyyy";
    public static final String FORMATO_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMATO_YYYYMMDD_HHMMSS = "yyyyMMdd_HHmmss";

    public static final String formatearFecha(Date date, String formato) {
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
     * Verifica la sesion web para obtener la info del usuario.
     *
     * @return
     */
    public static SesionRDU obtenerSesionUsuario() {
        SesionRDU sesionWeb = null;
        try {
            /*
            HttpServletRequest rqs=getRequest();
            String encXmlPromovente = rqs.getParameter("PORTAL_XMLPromovente");
             */
            boolean valVacio = true;
            FacesContext context = FacesContext.getCurrentInstance();
            HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
            sesionWeb = (SesionRDU) httpSession.getAttribute(SesionRDU.class.getName());

            if (null != sesionWeb) {
                
                valVacio = sesionWeb.getUsuario() == null ? true : sesionWeb.getUsuario().getIdUsuario() == null ? true : false;
                valVacio = sesionWeb.getPromovente() == null ? true : sesionWeb.getPromovente().getId_promovente() == 0 ? true : false;
                sesionWeb = valVacio == true ? null : sesionWeb;


            }



        } catch (Exception e) {
            System.out.println( "No existe la sesión: " + e.getMessage() );

        }
        return sesionWeb;
    }

    /**
     * Cierra la sesi�n de un usuario
     */
    public static void cerrarSesion() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(false);
        httpSession.removeAttribute(SesionRDU.class.getName());

    }

    /**
     * Crea una sesion para el usuario
     * @param prmUsrSeg
     */
    public static void crearSesionUsuario(Usuario prmUsr, PromoventeDto prmPromovente) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        SesionRDU sesionWeb = new SesionRDU();
        sesionWeb.setUsuario(prmUsr);
        sesionWeb.setPromovente(prmPromovente);
        httpSession.setAttribute(SesionRDU.class.getName(), sesionWeb);

    }

    public static void crearSesionRdu(SesionRDU sesionWeb) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession httpSession = (HttpSession) context.getExternalContext().getSession(true);
        httpSession.setAttribute(SesionRDU.class.getName(), sesionWeb);

    }

    public static PromoventeDto crearPromoventeDeXML() {
        //Creamos una instancia de XStream
        XStream xstream = new XStream();
        PromoventeDto retPromovente = new PromoventeDto();


        try {
            FacesContext context = FacesContext.getCurrentInstance();
            File reportFile = new File(context.getExternalContext().getRealPath("") + "/intercambio.xml");
            retPromovente = (PromoventeDto) xstream.fromXML(new FileInputStream(reportFile));

            //System.out.println(retPromovente);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return retPromovente;
    }

    /**
     * Genera una cadena XML para enviar a PASE y poder crear un folio FEPS
     * que represente el pago de los articulos que corresponden al documento
     * que se encuentra en el contexto.
     */
    public static String XMLTramiteFEPS() {

        OutputStream os = new ByteArrayOutputStream();
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(os, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Genera los datos del promovente
        PromoventeDto promovente = datosPromovente();

        // Genera las tarifas
        List<DocumentoArticulo> listArea = datosTramite();

        XStream xstream = new XStream();
        xstream.toXML(promovente, osw);
        String header = "<?xml version=\"1.0\" encoding=\"" + osw.getEncoding() + "\"?>\n";

        xstream.toXML(listArea, osw);
        String articulos = osw.getEncoding();

        //String header =
        StringBuffer xml = new StringBuffer();

        xml.append(header);
        xml.append(articulos);
        xml.append(os.toString());

        return (String) xml.toString();

    }

    /*Obtiene los datos del promovente utilizando los datos de session de.*/
    public static PromoventeDto datosPromovente() {

        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        PromoventeDto promovente = new PromoventeDto();
        PromoventeDto sesionPromovente = obtSession.getPromovente();

        promovente.setNombre(sesionPromovente.getNombre());
        promovente.setApaterno(sesionPromovente.getApaterno());
        promovente.setAmaterno(sesionPromovente.getAmaterno());
        promovente.setLogin(sesionPromovente.getLogin());
        promovente.setPassword(sesionPromovente.getPassword());
        promovente.setAmaterno(sesionPromovente.getAmaterno());
        promovente.setLogin(sesionPromovente.getLogin());
        promovente.setPassword(sesionPromovente.getPassword());
        promovente.setEmail(sesionPromovente.getEmail());
        promovente.setRfc(sesionPromovente.getRfc());
        promovente.setCalle_numero(sesionPromovente.getCalle_numero());
        promovente.setNumero_exterior(sesionPromovente.getNumero_exterior());
        promovente.setNumero_interior(sesionPromovente.getNumero_interior());
        promovente.setColonia(sesionPromovente.getColonia());
        promovente.setCodigo_postal(sesionPromovente.getCodigo_postal());
        promovente.setTelefono(sesionPromovente.getTelefono());
        promovente.setFax(sesionPromovente.getFax());
        promovente.setRazon_social(sesionPromovente.getRazon_social());
        promovente.setFecha_registro(sesionPromovente.getFecha_registro());
        promovente.setFecha_activacion(sesionPromovente.getFecha_activacion());
        promovente.setHabilitado(sesionPromovente.getHabilitado());
        promovente.setId_perfil(sesionPromovente.getId_perfil());
        promovente.setDescPerfil(sesionPromovente.getDescPerfil());
        promovente.setId_promovente(sesionPromovente.getId_promovente());
        promovente.setId_estado(sesionPromovente.getId_estado());
        promovente.setDescEstado(sesionPromovente.getDescEstado());
        promovente.setId_municipio(sesionPromovente.getId_municipio());
        promovente.setDescMunicipio(sesionPromovente.getDescMunicipio());
        promovente.setTipo_persona(sesionPromovente.getTipo_persona());
        promovente.setDescTipoPromovente(sesionPromovente.getDescTipoPromovente());
        promovente.setCargo(sesionPromovente.getCargo());
        promovente.setHabilita_marcanet(sesionPromovente.getHabilita_marcanet());

        return promovente;
    }

    public static List<DocumentoArticulo> datosTramite() {
        List<CatArea> listArea = new ArrayList();
        List<DocumentoArticulo> listDocumentoArt = new ArrayList();
        DocumentoArticulo documentoArticulo = new DocumentoArticulo();
        DocumentoArticulo docuArticulo = new DocumentoArticulo();

        documentoArticulo.setIdArea(1);
        documentoArticulo.setIdTipoSolicitud(1);
        documentoArticulo.setIdTipoDocumento(1);
        documentoArticulo.setArticuloPago("14");
        documentoArticulo.setSentido("E");
        documentoArticulo.setInciso("a");
        documentoArticulo.setCantidad((short) 1);
        documentoArticulo.setIndPromocion((short) 0);

        docuArticulo.setIdArea(1);
        docuArticulo.setIdTipoSolicitud(1);
        docuArticulo.setIdTipoDocumento(1);
        docuArticulo.setArticuloPago("28");
        docuArticulo.setSentido("E");
        docuArticulo.setInciso("");
        docuArticulo.setCantidad((short) 1);
        docuArticulo.setIndPromocion((short) 1);

        listDocumentoArt.add(documentoArticulo);
        listDocumentoArt.add(docuArticulo);


//        for(int i=1; i<3; i++){
//            CatArea area = new CatArea();
//            area.setDescripcion("LAURA"+i);
//            area.setIdArea(i);
//            area.setIndActivo(i);
//
//            listArea.add(area);
//        }

        return listDocumentoArt;
    }


    /*
     * En este metodo se genera la sesion mediante el parametro XML que proporciona PASE a RDU
     */
    public static void leerParametro() throws IOException, ServletException {
        String encXmlPromovente = "";
        String msgAviso = "";
        try {

            HttpServletRequest rqs = getRequest();
            if (rqs == null) {
                msgAviso = "HttpServletRequest vacio, no se pudo generar la seguridad";
            } else {
                encXmlPromovente = rqs.getParameter("PORTAL_XMLPromovente");
                //QUITAR cuando se implemente con el PASE
                encXmlPromovente = "TMPPASE";
                if (encXmlPromovente == null) {
                    msgAviso = "PORTAL_XMLPromovente vacio, no se pudo generar la seguridad";
                } else {
                    SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
                    if (obtSession == null) {
                        PromoventeDto obtPromovente = new PromoventeDto();
                        Usuario nvoUsuario = new Usuario();
                        obtPromovente = crearPromoventeDeXML();
                        crearSesionUsuario(nvoUsuario, obtPromovente);
                        msgAviso = "sesion creada";
                    } else {
                        msgAviso = "no se creo la sesion y no se implemento la seguridad";
                    }

                }
            }

            System.out.println("--->ContextUtils.leerParametro: " + msgAviso);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * En este metodo regresa un apoderado mediante el parametro XML que proporciona PASE a RDU
     */
    public static ApoderadoDto obtenerApoderadoDeSesion(int idApoderado) {
        ApoderadoDto retApoderado = new ApoderadoDto();
        SesionRDU obtSession = obtenerSesionUsuario();
        if (null != obtSession) {
            if (null != obtSession.getPromovente()) {
                int idPromoventeSesion = 0;
                idPromoventeSesion = obtSession.getPromovente().getId_promovente();
                if (idApoderado == idPromoventeSesion) {
                    ApoderadoDto oApoderadoP = new ApoderadoDto();
                    oApoderadoP.setNombre(obtSession.getPromovente().getNombre());
                    oApoderadoP.setApaterno(obtSession.getPromovente().getApaterno());
                    oApoderadoP.setAmaterno(obtSession.getPromovente().getAmaterno());
                    oApoderadoP.setEmail(obtSession.getPromovente().getEmail());
                    retApoderado = oApoderadoP;
                } else {

                    if (null != obtSession.getPromovente().getApoderados()) {
                        for (Iterator iter = obtSession.getPromovente().getApoderados().iterator(); iter.hasNext();) {
                            ApoderadoDto oApoderado = (ApoderadoDto) iter.next();
                            if (oApoderado.getId_promovente() == idApoderado) {
                                retApoderado = oApoderado;
                                break;
                            }
                        }
                    }
                }
            }

        }
        return retApoderado;
    }

    public static List<Integer> obtenerUsuariosId() {
        List<Integer> retUsuariosId = new ArrayList();
        SesionRDU obtSession = obtenerSesionUsuario();
        if (null != obtSession) {
            if (null != obtSession.getPromovente()) {
                // se agrega al promovente
                retUsuariosId.add(obtSession.getPromovente().getId_promovente());
                //se agregan los adicionales
                if (null != obtSession.getPromovente().getApoderados()) {
                    for (Iterator iter = obtSession.getPromovente().getApoderados().iterator(); iter.hasNext();) {
                        ApoderadoDto oApoderado = (ApoderadoDto) iter.next();
                        retUsuariosId.add(oApoderado.getId_promovente());
                    }
                }
            }
        }
        return retUsuariosId;
    }

    public static boolean checkMimeType(InputStream is, String extArchivo, String extValida) {
        Util utilArch = new Util();
        TikaConfig config = TikaConfig.getDefaultConfig();
        Detector detector = config.getDetector();
        TikaInputStream stream = TikaInputStream.get(is);
        Metadata metadata = new Metadata();
        MediaType mediaType = null;
        try {
            mediaType = detector.detect(stream, metadata);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String mimeTypeContenidoArchivo = mediaType.toString();
        //fin: Anexos, identificacion de archivos renombrados
        //String mimeTypeContenidoArchivo=mimeTypeNombreArchivo.getMime();
        if (mimeTypeContenidoArchivo.equals("image/gif")
                || mimeTypeContenidoArchivo.equals("image/jpeg")
                || mimeTypeContenidoArchivo.equals("image/tiff")) {
            return true;
        } else {
            return false;
        }
    }
}

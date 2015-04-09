package mx.gob.impi.rdu.exposition.anexos;

import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.persistence.model.Pago;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.DataEncoding;
import mx.gob.impi.rdu.util.DataEncodingB64Impl;
import mx.gob.impi.rdu.util.JndiPropertiesUtils;
import mx.gob.impi.rdu.util.NodeElementRecord;
import org.apache.log4j.Logger;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author winter
 */
@ManagedBean(name = "salirMB")
@ViewScoped
@SuppressWarnings("serial")
public class SalirMB implements Serializable{
    private  Logger logger = Logger.getLogger(this.getClass());

    /** Properties bundle para datos de XML */
    private static final String BUNDLE_XML_STRUCTURE_PATH = "mx.gob.impi.rdu.i18n.xmltramite";
    /** Archivo Properties que contiene los parametros con pase*/
    private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.parametros";

    /** Variable bundle de recursos de XML tags */
    final ResourceBundle bundleXML = ResourceBundle.getBundle(BUNDLE_XML_STRUCTURE_PATH);
    final ResourceBundle bundleParametros = ResourceBundle.getBundle(BUNDLE_PARAMETROS);

    /** Constante para determinar la p�gina a enviar cuando se pretende redirigir al PASE sin realizar el c�lculo de tarifa  */
    public static final String REDIRECCIONAMIENTO_PASE_MENSAJE_PAGAR_FOLIO = "paseRegresarMuestraMensajePagar";
    public static final String REDIRECCIONAMIENTO_PASE_MENSAJE_PAG_PRPAL = "paseRegresarMuestraMensajePrincipal";
    
    private String url;
    private String action;
    private String cadena;
    private String sMensajeMostrarPagPrpal;
    private Integer idTramiteSeleccionado=0;
    
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    public Integer getIdTramiteSeleccionado() {
        return idTramiteSeleccionado;
    }

    public void setIdTramiteSeleccionado(Integer idTramiteSeleccionado) {
        this.idTramiteSeleccionado = idTramiteSeleccionado;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @PostConstruct
    public void init() throws Exception {
        logger.info(" ==> DENTRO DEL INIT()");


        PromoventeDto promovente=new PromoventeDto();
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        
        if (null != obtSession) {
                        logger.info("  ** getIdMenu() ==> "+obtSession.getIdMenu()+
                    " getIdTramite ==> "+obtSession.getIdTramite()+
                    " IdTipoTramite ==> "+obtSession.getIdTipoTramite());

             List<Pago> tramiteFeps = new ArrayList<Pago>();
             tramiteFeps = this.flujosgralesViewService.buscarPago(obtSession.getIdTramite());


            if (obtSession.getIdMenu() == 2 && obtSession.getIdTramite() > 0) {
                promovente=obtSession.getPromovente();
//                action=bundleParametros.getString("action.regresaPagPrpal.url")+tramiteFeps.get(Constantes.FIRST).getFoliopago();
                action=bundleParametros.getString("action.regresaPagPrpal.url")+tramiteFeps.get(Constantes.FIRST).getFoliopago();
            }else if(obtSession.getIdMenu() == 5) {
                promovente=obtSession.getPromovente();

                //Genera el mensaje donde indica al usuario que debera pagar un folio determinado
                sMensajeMostrarPagPrpal = "" + bundleParametros.getString("pase.mensajegeneral.enviodesderdu");
//                action=bundleParametros.getString("action.salir.url");
                action=(String) JndiPropertiesUtils.getProperty("action.salir.url");

            }
            else if(obtSession.getIdMenu() == 58) {
                promovente=obtSession.getPromovente();
                sMensajeMostrarPagPrpal = "" + bundleParametros.getString("pase.mensajegeneral.enviodesderdu");
                action=(String) JndiPropertiesUtils.getProperty("action.salir.urlPatentes");
            }
            else if(obtSession.getIdMenu() == 0) {
                promovente=obtSession.getPromovente();

                List<Pago> listPagos = this.flujosgralesViewService.buscarPago(obtSession.getIdTramite());

                logger.info("************ RESPUESTA getIdPago: " +listPagos.get(0).getIdPago()+
                            " getFoliopago: " +listPagos.get(0).getFoliopago());

                //Genera el mensaje donde indica al usuario que debera pagar un folio determinado
                sMensajeMostrarPagPrpal = "" + bundleParametros.getString("pase.pagar.folio")+" "+listPagos.get(0).getFoliopago();
//                action=bundleParametros.getString("action.regresaPagPrpal.url")+tramiteFeps.get(Constantes.FIRST).getFoliopago();
                action=(String) JndiPropertiesUtils.getProperty("action.regresaPagPrpal.url")+tramiteFeps.get(Constantes.FIRST).getFoliopago();
            }
        }

        logger.info(" ** LIGA==>  "+action);

        //Numero maximo de tags correspondiente a la informacion del promovente  y tarifas
        final Integer max_data_tag = new Integer(bundleXML.getString("rdu.pase.xml.max_data_tag"));

        // Genera el Node Element para preparar el XML
        // Genera los datos del promovente
        final ArrayList<NodeElementRecord> datosPromovente = creaArregloDatosPromovente(promovente);
                
        try {
            // Crea la instancia para XML
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            final Document document = documentBuilder.newDocument();

            // Agrega el elemento ROOT del documento
            final Element rootElement = document.createElement(bundleXML.getString("rdu.pase.xml.tagroot"));

            // Crea el tag de datos de promovente
            final Comment comment1 = document.createComment(bundleXML.getString("rdu.pase.xml.comment1"));
            rootElement.appendChild(comment1);
            final Element promoventeElement = document.createElement(bundleXML.getString("rdu.pase.xml.tag0"));

            // Agrega los elementos hijo del promovente
            for (int idx_i = 0; idx_i < max_data_tag; idx_i++) {
                final NodeElementRecord ner = datosPromovente.get(idx_i);
                final String val1 = ner.getNodename();
                final String val2 = ner.getNodetext();
                final Element em = document.createElement(val1);
                em.appendChild(document.createTextNode(val2));
                promoventeElement.appendChild(em);
            }
            rootElement.appendChild(promoventeElement);

            final Comment comment3 = document.createComment(bundleXML.getString("rdu.pase.xml.comment3"));
            //Comentario del Mensaje a mostrar al promovente
            final Comment commentMsjParaPromovente = document.createComment(bundleXML.getString("rdu.pase.xml.comment6"));
            final Element eMsjParaPromovente = document.createElement(bundleXML.getString("rdu.pase.xml.tagMensajePromovente"));
            //Coloca el mensaje a mostrar al promovente
            eMsjParaPromovente.appendChild(document.createTextNode(sMensajeMostrarPagPrpal));

            rootElement.appendChild(comment3);
            rootElement.appendChild(commentMsjParaPromovente);
            rootElement.appendChild(eMsjParaPromovente);

            //Agrega los tags creados
            document.appendChild(rootElement);

            // Transforma el documento a tipo DOM
            final Writer wr = new StringWriter();
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, bundleXML.getString("rdu.pase.xml.encoding"));
            final DOMSource source = new DOMSource(document);
            final StreamResult result = new StreamResult(wr);
            transformer.transform(source, result);
            
            cadena = result.getWriter().toString();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }  catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        DataEncoding de = DataEncodingB64Impl.getInstance();

        logger.info("cadenaXML  ==>"+cadena);

        url=de.encodeData(cadena).toString();
        
//        if(null != obtSession && obtSession.getIdMenu() == 57) {
////            promovente=obtSession.getPromovente();
////            tramite.setIdTramite(obtSession.getIdTramite());
////            sMensajeMostrarPagPrpal = "" + bundleParametros.getString("pase.mensajegeneral.enviodesderdu");
//            action=(String) JndiPropertiesUtils.getProperty("action.salir.urlMarcas");
//        }
//        if(null != obtSession && obtSession.getIdMenu() == 58) {
//            action=(String) JndiPropertiesUtils.getProperty("action.salir.urlPatentes");
//        }
        
        //Se cierra sesion en el RDU
        if(obtSession.getIdMenu() == 5 || obtSession.getIdMenu() == 57 || obtSession.getIdMenu() == 58) {
            ContextUtils.cerrarSesion();
        }
        
    }


    private ArrayList<NodeElementRecord> creaArregloDatosPromovente(PromoventeDto promovente){
        // Crea variables
        final ArrayList<NodeElementRecord> datosPromovente = new ArrayList<NodeElementRecord>();
        logger.debug("  ==>DENTRO DE creaArregloDatosPromovente==>");

        // id_promovente
        NodeElementRecord ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag104"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag104"));
        ner.setNodetext(((Integer) promovente.getId_promovente()).toString());
        datosPromovente.add(ner);

        // nombre
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag105"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag105"));
        ner.setNodetext(promovente.getNombre());
        datosPromovente.add(ner);

        // apaterno
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag106"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag106"));
        ner.setNodetext(promovente.getApaterno());
        datosPromovente.add(ner);

        // amaterno
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag107"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag107"));
        ner.setNodetext(promovente.getAmaterno());
        datosPromovente.add(ner);

        // login
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag200"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag200"));
        ner.setNodetext(promovente.getLogin());
        datosPromovente.add(ner);

        // password
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag201"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag201"));
        ner.setNodetext(promovente.getPassword());
        datosPromovente.add(ner);

        // email
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag118"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag118"));
        ner.setNodetext(promovente.getEmail());
        datosPromovente.add(ner);

        // rfc
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag119"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag119"));
        ner.setNodetext(promovente.getRfc());
        datosPromovente.add(ner);

        // calle_numero
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag108"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag108"));
        ner.setNodetext(promovente.getCalle_numero() + " " + promovente.getNumero_exterior() + " " + promovente.getNumero_interior());
        datosPromovente.add(ner);

        // numero_exterior
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag109"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag109"));
        ner.setNodetext(promovente.getNumero_exterior());
        datosPromovente.add(ner);

        // numero_interior
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag110"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag110"));
        ner.setNodetext(promovente.getNumero_interior());
        datosPromovente.add(ner);

        // colonia
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag111"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag111"));
        ner.setNodetext(promovente.getColonia());
        datosPromovente.add(ner);

        // codigo_postal
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag112"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag112"));
        ner.setNodetext(promovente.getCodigo_postal());
        datosPromovente.add(ner);

        // telefono
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag116"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag116"));
        ner.setNodetext(promovente.getTelefono());
        datosPromovente.add(ner);

        // fax
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag117"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag117"));
        ner.setNodetext(promovente.getFax());
        datosPromovente.add(ner);

        // razon_social
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag202"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag202"));
        ner.setNodetext(promovente.getRazon_social());
        datosPromovente.add(ner);

        // fecha_registro
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag203"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag203"));
        ner.setNodetext(promovente.getFecha_registro());
        datosPromovente.add(ner);

        // fecha_activacion
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag204"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag204"));
        ner.setNodetext(promovente.getFecha_activacion());
        datosPromovente.add(ner);

        // habilitado
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag205"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag205"));
        ner.setNodetext(String.valueOf(promovente.getHabilitado()));
        datosPromovente.add(ner);

        // id_perfil
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag206"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag206"));
        ner.setNodetext(String.valueOf(promovente.getId_perfil()));
        datosPromovente.add(ner);

        // descPerfil
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag207"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag207"));
        ner.setNodetext(promovente.getDescPerfil());
        datosPromovente.add(ner);

        // id_promovente_padre
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag208"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag208"));
        ner.setNodetext(String.valueOf(promovente.getId_promovente_padre()));
        datosPromovente.add(ner);

        // id_estado
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag114"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag114"));
        ner.setNodetext(String.valueOf(promovente.getId_estado()));
        datosPromovente.add(ner);

        // descEstado
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag209"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag209"));
        ner.setNodetext(promovente.getDescEstado());
        datosPromovente.add(ner);

        // id_municipio
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag115"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag115"));
        ner.setNodetext(String.valueOf(promovente.getId_municipio()));
        datosPromovente.add(ner);

        // descMunicipio
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag210"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag210"));
        ner.setNodetext(promovente.getDescMunicipio());
        datosPromovente.add(ner);

        // tipo_persona
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag211"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag211"));
        ner.setNodetext(String.valueOf(promovente.getTipo_persona()));
        datosPromovente.add(ner);

        // descTipoPromovente
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag212"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag212"));
        ner.setNodetext(promovente.getDescTipoPromovente());
        datosPromovente.add(ner);

        // cargo
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag213"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag213"));
        ner.setNodetext(promovente.getCargo());
        datosPromovente.add(ner);

        // habilita_marcanet
        ner = new NodeElementRecord();
        ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag214"));
        ner.setNodename(bundleXML.getString("rdu.pase.xml.tag214"));
        ner.setNodetext(String.valueOf(promovente.getHabilita_marcanet()));
        datosPromovente.add(ner);

        return datosPromovente;
    }

}
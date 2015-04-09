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

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.persistence.model.*;
import mx.gob.impi.rdu.service.CatalogosViewServiceImpl;
import mx.gob.impi.rdu.service.PatentesViewServiceImpl;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.DataEncoding;
import mx.gob.impi.rdu.util.DataEncodingB64Impl;
import mx.gob.impi.rdu.util.JndiPropertiesUtils;
import mx.gob.impi.rdu.util.NodeElementRecord;
import mx.gob.impi.rdu.util.TipoTramiteEnum;
import org.apache.log4j.Logger;

/**
 *
 * @author winter
 */
@ManagedBean(name = "redireccionarMB")
@ViewScoped
@SuppressWarnings("serial")
public class RedireccionarMB implements Serializable {

    /**
     * Properties bundle para datos de XML
     */
    private static final String BUNDLE_XML_STRUCTURE_PATH = "mx.gob.impi.rdu.i18n.xmltramite";
    /**
     * Archivo Properties que contiene los parametros con pase
     */
    private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.parametros";
    /**
     * Variable bundle de recursos de XML tags
     */
    final ResourceBundle bundleXML = ResourceBundle.getBundle(BUNDLE_XML_STRUCTURE_PATH);
    final ResourceBundle bundleParametros = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
    private Logger logger = Logger.getLogger(this.getClass());
    private String url;
    private String action;
    private String cadena;
    private int areaXml;
    private int isOff = 0;
    //variable que indica a que formulario se va a dirigir del PASE
    private String tipoformulario = "";
    @ManagedProperty(value = "#{catalogosViewService}")
    private CatalogosViewServiceImpl catalogosViewService;   
    @ManagedProperty(value = "#{patentesViewService}")
    private PatentesViewServiceImpl patentesViewService;   

    public void setCatalogosViewService(CatalogosViewServiceImpl catalogosViewService) {
        this.catalogosViewService = catalogosViewService;
    }   

    public void setPatentesViewService(PatentesViewServiceImpl patentesViewService) {
        this.patentesViewService = patentesViewService;
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

    public String getTipoformulario() {
        return tipoformulario;
    }

    public void setTipoformulario(String tipoformulario) {
        this.tipoformulario = tipoformulario;
    }

    @PostConstruct
    public void init() throws Exception {
        logger.info(" ==> DENTRO DEL INIT()");
//        action = bundleParametros.getString("action.calculoTarifasRDU");
        action = (String) JndiPropertiesUtils.getProperty("action.calculoTarifasRDU");
        boolean hayTramite = false;
        PromoventeDto promovente = new PromoventeDto();
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();

        if (null != obtSession) {
            logger.info("  ** getIdMenu() ==> " + obtSession.getIdMenu()
                    + " getIdTramite ==> " + obtSession.getIdTramite()
                    + " IdTipoTramite ==> " + obtSession.getIdTipoTramite());
           if (obtSession.getIdTipoTramite() == TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite()) {
                logger.info("   **  PERTENECE A PATENTES DENTRO DEL PASE ==>2");
                areaXml = TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite();
            }else if (obtSession.getIdTipoTramite() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite()) {
                logger.info("   **  PERTENECE A SIT DENTRO DEL PASE ==>20");
                areaXml = TipoTramiteEnum.SOL_SIT.getIdTipoTramite();
            }else if(obtSession.getIdTipoTramite() == TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite()){
                logger.info("   **  PERTENECE A PROMOCIONES DENTRO DEL PASE ==>????");
                areaXml = TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite();
            }
           
            if (obtSession.getIdMenu() == 8 && obtSession.getIdTramite() > 0) {
                promovente = obtSession.getPromovente();
                hayTramite = true;
            } else if ((obtSession.getIdMenu() == 1 || obtSession.getIdMenu() == 2 || obtSession.getIdMenu() == 9
                    || obtSession.getIdMenu() == 14 || obtSession.getIdMenu() == 15
                    || obtSession.getIdMenu() == 40
                    || obtSession.getIdMenu() == 41
                    || obtSession.getIdMenu() == 43) && obtSession.getIdTramite() > 0) {
                promovente = obtSession.getPromovente();
                hayTramite = true;
            }else if ((obtSession.getIdMenu() == 21 || obtSession.getIdMenu() == 20 ) && obtSession.getIdTramite() > 0) {
                promovente = obtSession.getPromovente();
                hayTramite = true;
            }
        }
        if (hayTramite) {
            //Numero maximo de tags correspondiente a la informacion del promovente  y tarifas
            final Integer max_data_tag = new Integer(bundleXML.getString("rdu.pase.xml.max_data_tag"));
            final Integer max_tarifas_tag = new Integer(bundleXML.getString("rdu.pase.xml.max_tarifas_tag"));

            // Genera el Node Element para preparar el XML
            // Genera los datos del promovente

            final ArrayList<NodeElementRecord> datosPromovente = creaArregloDatosPromovente(promovente);


            // Genera las tarifas
            logger.info(" ***** Genera las tarifas  ==>  ");
            final ArrayList<NodeElementRecord> datosTarifa = creaArregloDatosTarifa(obtSession.getIdTramite(), areaXml);

            try {
                // Crea la instancia para XML
                final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                final Document document = documentBuilder.newDocument();

                // Agrega el elemento ROOT del documento
                logger.info(" ***** Agrega el elemento ROOT del documento  ==>  ");
                final Element rootElement = document.createElement(bundleXML.getString("rdu.pase.xml.tagroot"));

                // Crea el tag de datos de promovente
                logger.info(" ***** Crea el tag de datos de promovente  ==>  ");
                final Comment comment1 = document.createComment(bundleXML.getString("rdu.pase.xml.comment1"));
                rootElement.appendChild(comment1);
                final Element promoventeElement = document.createElement(bundleXML.getString("rdu.pase.xml.tag0"));

                // Agrega los elementos hijo del promovente
                logger.info(" ***** Agrega los elementos hijo del promovente  ==>  ");
                for (int idx_i = 0; idx_i < max_data_tag; idx_i++) {
                    final NodeElementRecord ner = datosPromovente.get(idx_i);
                    final String val1 = ner.getNodename();
                    final String val2 = ner.getNodetext();
                    final Element em = document.createElement(val1);
                    em.appendChild(document.createTextNode(val2));
                    promoventeElement.appendChild(em);
                }
                rootElement.appendChild(promoventeElement);

                final Comment comment2 = document.createComment(bundleXML.getString("rdu.pase.xml.comment2"));
                final Comment comment3 = document.createComment(bundleXML.getString("rdu.pase.xml.comment3"));
                final Comment comment4 = document.createComment(bundleXML.getString("rdu.pase.xml.comment4"));
                final Element idDoc = document.createElement(bundleXML.getString("rdu.pase.xml.tag1"));

                idDoc.appendChild(document.createTextNode(String.valueOf(obtSession.getIdTramite())));
                final Element area = document.createElement(bundleXML.getString("rdu.pase.xml.tag3"));
                //si es solicitud de patentes y aplica descuento
                if (areaXml == TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite() & (isOff > 0)) {
                    area.appendChild(document.createTextNode(String.valueOf(areaXml) + isOff + ""));
                }else if (areaXml == TipoTramiteEnum.SOL_SIT.getIdTipoTramite() & (isOff > 0)) {
                    area.appendChild(document.createTextNode(String.valueOf(areaXml) + isOff + ""));
                } else {
                    area.appendChild(document.createTextNode(String.valueOf(areaXml)));
                }


                final Element areaSubgrupo = document.createElement(bundleXML.getString("rdu.pase.xml.tag4"));

                //SIEMPRE PASA COMO NULL
                areaSubgrupo.appendChild(document.createTextNode(""));

                /**
                 * Si no cuenta con tarifas, se asigna el valor acordado con
                 * PASE para el SubGrupo, si subgrupo tiene este valor: PASE
                 * valida el area si subgrupo no tiene este valor: PASE valida
                 * las tarifas *
                 */
                if (datosTarifa.isEmpty()) {
                    logger.debug("  ==> dentro del if Si no cuenta con tarifas, se asigna el valor acordado con PASE para el SubGrupo,");
                    int subgrupoinvalido = 777777;

                    areaSubgrupo.appendChild(document.createTextNode(String.valueOf(subgrupoinvalido)));
                } else {
                    areaSubgrupo.appendChild(document.createTextNode(""));
                }

                rootElement.appendChild(comment2);
                rootElement.appendChild(comment3);
                rootElement.appendChild(idDoc);
                rootElement.appendChild(comment4);
                rootElement.appendChild(area);
                rootElement.appendChild(areaSubgrupo);

                // Crea los articulos para las tarifas
                logger.info(" ***** Crea los articulos para las tarifas  ==>  ");
                final Comment comment5 = document.createComment(bundleXML.getString("rdu.pase.xml.comment5"));
                rootElement.appendChild(comment5);
                final Element tarifas = document.createElement(bundleXML.getString("rdu.pase.xml.tag5"));
                logger.info(" ***** datosTarifa.size()  ==>  " + datosTarifa.size());
                final int maxLoop = datosTarifa.size();

                for (int idx_j = 0; idx_j < maxLoop; idx_j++) {
                    final Element tarifa = document.createElement(bundleXML.getString("rdu.pase.xml.tag6"));

                    // Agrega el tag articulo
                    logger.info(" ***** Agrega el tag articulo  ==>  ");
                    final Element articulo = document.createElement(datosTarifa.get(idx_j).getNodedescription());
                    articulo.appendChild(document.createTextNode(datosTarifa.get(idx_j).getNodetext()));
                    tarifa.appendChild(articulo);

                    // Agrega el tag inciso
                    logger.info(" ***** Agrega el tag inciso  ==>  ");
                    final Element inciso = document.createElement(datosTarifa.get(idx_j + 1).getNodedescription());
                    inciso.appendChild(document.createTextNode(datosTarifa.get(idx_j + 1).getNodetext()));
                    tarifa.appendChild(inciso);

                    // Verifica la prioridad y agrega el tag ind_prioridad
                    if ((new Integer(datosTarifa.get(idx_j + 3).getNodetext())) > 0) {
                        Integer nuevaCantidad = Integer.valueOf(-1);

                        try {
                            if (areaXml == TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite()) {
                                TramitePatente datosTramitePatente = patentesViewService.obtenerTramitePatenteById(obtSession.getIdTramite());
                                logger.info("****==> NUMERO DE PRIORIDADES    ==>" + datosTramitePatente.getPrioridades().size());

                                if (datosTramitePatente.getPrioridades().size() > 0) {
                                    nuevaCantidad = datosTramitePatente.getPrioridades().size();
                                } else {
                                    nuevaCantidad = 0;
                                }
                            }else if (areaXml == TipoTramiteEnum.SOL_SIT.getIdTipoTramite()) {
                                TramitePatente datosTramitePatente = patentesViewService.obtenerTramitePatenteById(obtSession.getIdTramite());
                                logger.info("****==> NUMERO DE PRIORIDADES    ==>" + datosTramitePatente.getPrioridades().size());

                                if (datosTramitePatente.getPrioridades().size() > 0) {
                                    nuevaCantidad = datosTramitePatente.getPrioridades().size();
                                } else {
                                    nuevaCantidad = 0;
                                }
                            }else{
                                nuevaCantidad = new Integer(datosTarifa.get(idx_j + 2).getNodetext());
                            }
                            logger.info(" ***** nuevaCantidad  ==>  " + nuevaCantidad);


                        } catch (Exception e) {
                            logger.error("Error al crear el XML TransformerException: " + e.getClass() + " - " + e.getMessage() + "");
                            //                        e.printStackTrace();
                            nuevaCantidad = new Integer(datosTarifa.get(idx_j + 2).getNodetext());
                        }
                        final Element cantidad = document.createElement(datosTarifa.get(idx_j + 2).getNodedescription());
                        cantidad.appendChild(document.createTextNode(nuevaCantidad.toString()));
                        tarifa.appendChild(cantidad);
                    } else {
                        final Element cantidad = document.createElement(datosTarifa.get(idx_j + 2).getNodedescription());
                        cantidad.appendChild(document.createTextNode(datosTarifa.get(idx_j + 2).getNodetext()));
                        tarifa.appendChild(cantidad);
                    }
                    final Element indPrioridad = document.createElement(datosTarifa.get(idx_j + 3).getNodedescription()); // Agrega el tag ind_prioridad
                    indPrioridad.appendChild(document.createTextNode(datosTarifa.get(idx_j + 3).getNodetext()));
                    tarifa.appendChild(indPrioridad);
                    tarifas.appendChild(tarifa);
                    idx_j += max_tarifas_tag;
                }
                //Agrega los tags TARIFAS
                logger.info(" ***** Agrega los tags TARIFAS  ==>  ");
                rootElement.appendChild(tarifas);

                //Agrega los tags creados
                document.appendChild(rootElement);
                // Transforma el documento a tipo DOM
                logger.info(" ***** Transforma el documento a tipo DOM  ==>  ");
                final Writer wr = new StringWriter();
                final TransformerFactory transformerFactory = TransformerFactory.newInstance();
                final Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.ENCODING, bundleXML.getString("rdu.pase.xml.encoding"));
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                final DOMSource source = new DOMSource(document);
                final StreamResult result = new StreamResult(wr);
                transformer.transform(source, result);
                logger.info(" ***** CREA CADENA  ==>  ");
                cadena = result.getWriter().toString();

            } catch (ParserConfigurationException e) {
                logger.error("Error al crear el XML ParserConfigurationException: " + e.getClass() + " - " + e.getMessage() + "");
            } catch (TransformerConfigurationException e) {
                logger.error("Error al crear el XML TransformerConfigurationException: " + e.getClass() + " - " + e.getMessage() + "");
            } catch (TransformerException e) {
                logger.error("Error al crear el XML TransformerException: " + e.getClass() + " - " + e.getMessage() + "");
            }

            logger.info("cadenaXML    ==>" + cadena);
            
            System.out.println(cadena);

            DataEncoding de = DataEncodingB64Impl.getInstance();
            url = de.encodeData(cadena).toString();
            
            if (null != obtSession) {
                obtSession.setIdTramite(0);
            }
        } else {
            throw new AssertionError("El portal de Pago y servicios se encuentra ocupado por el momento, vuelva a intentar desde solicitudes en preparación.");
        }
    }

    private ArrayList<NodeElementRecord> creaArregloDatosPromovente(PromoventeDto promovente) {
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
        //ner.setNodetext(promovente.getCalle_numero() + " " + promovente.getNumero_exterior() + " " + promovente.getNumero_interior());
        ner.setNodetext(promovente.getCalle_numero());
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

    private ArrayList<NodeElementRecord> creaArregloDatosTarifa(Long idTramite, int idArea) throws Exception {
        
        System.out.println("--------> en creaArregloDatosTarifa()... ");
        System.out.println("---------> idTramite: "+idTramite);
        System.out.println("----------> idArea: "+idArea);
        DocumentoArticulo documentoArticulo = new DocumentoArticulo();
        List<DocumentoArticulo> consultaTarifa = null;
        TramitePatente tramitePatente = new TramitePatente();
        TramitePromocionMarca tramitePromocionMarca = new TramitePromocionMarca();
        ArrayList<NodeElementRecord> respuesta = new ArrayList<NodeElementRecord>();
        
        int cantidad = 0;

         if (idArea == TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite()) {
            logger.info("   **  creaArregloDatosTarifa:PERTENECE A PATENTES DENTRO DEL PASE ==>2");
            tramitePatente = patentesViewService.selectTramite(idTramite);

            List<Persona> personas = patentesViewService.selectSolicitanteTramitePatente(idTramite);


            for (Persona psn : personas) {
                if (psn != null && psn.getDescuento() != null && psn.getDescuento() > 0) {
                    if (psn.getIdTipoSolicitante() == Constantes.SOL_EMPRESA_GRANDE | psn.getIdTipoSolicitante() == Constantes.SOL_EMPRESA_MED | psn.getIdTipoSolicitante() == Constantes.SOL_PERSONA_MORAL) {
                        //isOff=false;
                        isOff = 0;
                        break;
                    } else {
                        //isOff=true;                                        
                        isOff = psn.getIdTipoSolicitante().intValue();
                    }

                } else {
                    //isOff=false;
                    isOff = 0;
                    break;
                }
            }

            areaXml = TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite();

            logger.info("  **==> IDTRAMITE  " + tramitePatente.getIdTramitePatente()
                    + "  IdSubtiposolicitud  " + tramitePatente.getIdSubtipoSolicitud());

            // Configura el parametro a enviar
            documentoArticulo.setIdArea(Integer.valueOf(Constantes.AREA_PATENTE.toString()));

            documentoArticulo.setIdTipoSolicitud(Integer.valueOf(tramitePatente.getIdSubtipoSolicitud().toString()));
            documentoArticulo.setIdTipoDocumento(1);
            logger.info("  **==> IConfigura el parametro a enviar PATENTES  ");
        
        
            // =============================== PROMOCIONES ======================================
        }else if (idArea == TipoTramiteEnum.SOL_SIT.getIdTipoTramite()) {
            logger.info("   **  creaArregloDatosTarifa:PERTENECE A SIT DENTRO DEL PASE ==>20");
            tramitePatente = patentesViewService.selectTramite(idTramite);

            List<Persona> personas = patentesViewService.selectSolicitanteTramitePatente(idTramite);


            for (Persona psn : personas) {
                if (psn != null && psn.getDescuento() != null && psn.getDescuento() > 0) {
                    if (psn.getIdTipoSolicitante() == Constantes.SOL_EMPRESA_GRANDE | psn.getIdTipoSolicitante() == Constantes.SOL_EMPRESA_MED | psn.getIdTipoSolicitante() == Constantes.SOL_PERSONA_MORAL) {
                        //isOff=false;
                        isOff = 0;
                        break;
                    } else {
                        //isOff=true;                                        
                        isOff = psn.getIdTipoSolicitante().intValue();
                    }

                } else {
                    //isOff=false;
                    isOff = 0;
                    break;
                }
            }

            areaXml = TipoTramiteEnum.SOL_SIT.getIdTipoTramite();

            logger.info("  **==> IDTRAMITE  " + tramitePatente.getIdTramitePatente()
                    + "  IdSubtiposolicitud  " + tramitePatente.getIdSubtipoSolicitud());

            // Configura el parametro a enviar
            documentoArticulo.setIdArea(Integer.valueOf(Constantes.AREA_SIT.toString()));
            String addTipoSol="";
            if((tramitePatente.getIdSubtipoSolicitud()==28 || tramitePatente.getIdSubtipoSolicitud()==29) && tramitePatente.getCobertura().equals("2"))  addTipoSol=tramitePatente.getCobertura();
            documentoArticulo.setIdTipoSolicitud(Integer.valueOf(tramitePatente.getIdSubtipoSolicitud().toString()+addTipoSol));
            documentoArticulo.setIdTipoDocumento(1);
            logger.info("  **==> IConfigura el parametro a enviar SIT  ");
        
        
            // =============================== PROMOCIONES ======================================
        }else if (idArea == TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite()) {
            System.out.println("-------------> Creando parámetros de búsqueda para documentoArtículo de promociones...");
            SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
            if (obtSession.getDescuento() == 1 && obtSession.getTipoSolicitante() > 0) {
                areaXml = Integer.parseInt("4" + obtSession.getTipoSolicitante());
            } else {
                areaXml = TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite();
            }
            
            // Configura el parametro a enviar
            documentoArticulo.setIdArea(Integer.valueOf(Constantes.AREA_PATENTE.toString()));

            documentoArticulo.setIdTipoSolicitud(obtSession.getSubTipo()); // El tipo de la promoción
            documentoArticulo.setIdTipoDocumento(1);
        }
        
        try {
            consultaTarifa = catalogosViewService.cosultarDocumentoArticulo(documentoArticulo);
            logger.info(" ** consultaTarifa==>  " + consultaTarifa.size());
          
            for (DocumentoArticulo documento : consultaTarifa) {
                if (documento != null) {
                    // Articulo
                    NodeElementRecord ner = new NodeElementRecord();
                    ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag7"));
                    ner.setNodename(bundleXML.getString("rdu.pase.xml.tag7"));
                    ner.setNodetext(documento.getArticuloPago());
                    respuesta.add(ner);

                    // Inciso
                    ner = new NodeElementRecord();
                    ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag8"));
                    ner.setNodename(bundleXML.getString("rdu.pase.xml.tag8"));
                    //se agrega la validacion de PCT
                    if (documento.getIdTipoSolicitud() == 11 && documento.getArticuloPago().equals("9") ) {
                         if (tramitePatente.getFaseSolPCT() != null && tramitePatente.getFaseSolPCT().equals("Capitulo 2")){
                             ner.setNodetext(documento.getInciso());
                             if (documento.getInciso().equals("c")){
                                documento.setCantidad(new Short("1"));
                                //cantidad=1;
                             }else {
                                documento.setCantidad(new Short("0"));
                                //cantidad=0; 
                             }    
                         }else{
                             ner.setNodetext(documento.getInciso());
                             if (documento.getInciso().equals("b")){
                                documento.setCantidad(new Short("1")); 
                             //   cantidad=1;
                             }else {
                                documento.setCantidad(new Short("0")); 
                               // cantidad=0; 
                             } 
                         }    
                    } else {
                        ner.setNodetext(documento.getInciso());
                    }
                    //se comenta para probar PCT
//                    if (documento.getIdTipoSolicitud() == 27 && (tramitePatente.getFaseSolPCT() != null && tramitePatente.getFaseSolPCT().equals("Capitulo 2"))) {
//                        ner.setNodetext("c");
//                    } else {
//                        ner.setNodetext(documento.getInciso());
//                    }

                    respuesta.add(ner);

                    String valorCantidad = "";

                    if (cantidad != 0) {
                        valorCantidad = String.valueOf(cantidad);
                    } else {
                        System.out.println("----------> documento.getCantidad(): "+documento.getCantidad());
                        valorCantidad = (documento.getCantidad()).toString();
                    }

                    ner = new NodeElementRecord();
                    ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag9"));
                    ner.setNodename(bundleXML.getString("rdu.pase.xml.tag9"));
                    ner.setNodetext(valorCantidad);
                    respuesta.add(ner);

                    // Indicador de prioridad
                    ner = new NodeElementRecord();
                    ner.setNodedescription(bundleXML.getString("rdu.pase.xml.tag10"));
                    ner.setNodename(bundleXML.getString("rdu.pase.xml.tag10"));
                    ner.setNodetext((documento.getIndPromocion()).toString());
                    respuesta.add(ner);
                }
            }

        } catch (Exception exception) {
            logger.error("Error al crear el arreglo de tarifa: " + exception.getClass() + " - " + exception.getMessage());
            throw exception;
        }

        return respuesta;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.firma;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import mx.gob.impi.rdu.dto.ApoderadoDto;
import mx.gob.impi.rdu.dto.FirmaDto;
import mx.gob.impi.rdu.persistence.model.Notificacion;
import mx.gob.impi.rdu.util.BundleUtils;
import mx.gob.impi.rdu.util.Util;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author
 */
public class AnexoNotificacionXml {

    private Document document;
    private Element documento;
    private Notificacion tramiteDto;
    private ApoderadoDto apoderado;
    private FirmaDto firmaDto;
    private Logger logger = Logger.getLogger(this.getClass());
    public static final String XML_MAIN_HEADER = "documento_electronico";
    public static final String XML_SUB_MAIN = "documento";
    public static final String XML_AREA = "40";
    public final static String XML_TIPO_DOCUMENTO = "XML";
    public static final String XML_TIPO_SOLICITUD = "1";
    public static final String XML_TIPO_SOLICITANTE = "1";
    public static final String XML_TIPO_CLASE = "10";
    public static final String CLASE = "5";

    public AnexoNotificacionXml() {
        super();
    }

    public AnexoNotificacionXml(Notificacion tramite, FirmaDto firma) {
        this.tramiteDto = tramite;
        this.firmaDto = firma;
    }

    public byte[] getDocumentoXml() {

        byte[] xmlByte = null;
        try {

            this.generaXml();
            xmlByte = this.exportToByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error al convertir archivo xml... " + e.getMessage(), e);
        }

        return xmlByte;
    }

    public void generaXml() throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        document = db.newDocument();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Element docRoot = document.createElement(XML_MAIN_HEADER);
        document.appendChild(docRoot);

        documento = document.createElement(XML_SUB_MAIN);
        docRoot.appendChild(documento);

        this.creaMainInfo();//DONE
        

        this.creaAnexos();       

        this.creaCadenas();

        this.creaFirmas();

    }

    public byte[] exportToByteArray() throws Exception {
        Source source = new DOMSource(this.document);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(out);
        TransformerFactory fact = TransformerFactory.newInstance();
        Transformer trans = fact.newTransformer();
        trans.transform(source, result);

        return out.toByteArray();

    }


    private void creaCadenas() {

        Element cadenas = document.createElement("cadenas");
        documento.appendChild(cadenas);

        Element cad = document.createElement("cadena");
        cadenas.appendChild(cad);

        Element tipo = document.createElement("tipo");
        tipo.appendChild(document.createTextNode("impi"));
        cad.appendChild(tipo);

        Element desc = document.createElement("descripcion");
        desc.appendChild(document.createTextNode(this.firmaDto.getCadenaImpi() + ""));
        cad.appendChild(desc);


       

    }

    private void creaFirmas() {

        Element firmas = document.createElement("firmas");
        documento.appendChild(firmas);

        Element firma = document.createElement("firma");
        firmas.appendChild(firma);

        Element tipo = document.createElement("tipo");
        tipo.appendChild(document.createTextNode("impi"));
        firma.appendChild(tipo);

        Element desc = document.createElement("descripcion");
        desc.appendChild(document.createTextNode(this.firmaDto.getFirmaImpi() + ""));
        firma.appendChild(desc);
    

    }

    private void creaAnexos() {

        Element anexos = document.createElement("anexos");
        documento.appendChild(anexos);

        
            Element anexo = document.createElement("anexo");
            anexos.appendChild(anexo);

            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(tramiteDto.getArchivoNombre() + ""));
            anexo.appendChild(id);

            Element tipo_anexo = document.createElement("tipo_anexo");
            tipo_anexo.appendChild(document.createTextNode("notificacion"));
            anexo.appendChild(tipo_anexo);

            Element descripcion = document.createElement("descripcion");
            descripcion.appendChild(document.createTextNode(tramiteDto.getDenominacion() + ""));
            anexo.appendChild(descripcion);

            Element firma = document.createElement("firma");
            firma.appendChild(document.createTextNode(Util.getDigest(tramiteDto.getArchivo()) + ""));
            anexo.appendChild(firma);

        








    }





    
    
      
      
     
     
    
    

    private void creaMainInfo() {


        Element area = document.createElement("area");
        area.appendChild(document.createTextNode(XML_AREA)); //***************      
        documento.appendChild(area);

        Element tipoDocumento = document.createElement("tipo_documento");
        tipoDocumento.appendChild(document.createTextNode(XML_TIPO_DOCUMENTO));       //*****
        documento.appendChild(tipoDocumento);

        Element tipoSolicitud = document.createElement("tipo_solicitud");
        tipoSolicitud.appendChild(document.createTextNode("NOTIFICACION" + ""));       //*****
        documento.appendChild(tipoSolicitud);

        Element folio = document.createElement("folio");
        folio.appendChild(document.createTextNode(this.firmaDto.getFolioId() + ""));       //*****
        documento.appendChild(folio);

        Element expediente = document.createElement("expediente");
        expediente.appendChild(document.createTextNode(this.firmaDto.getClaveExpediente()!=null ? this.firmaDto.getClaveExpediente()+ "" :  firmaDto.getExpediente()!=null?firmaDto.getExpediente().toString() +"" : ""   ));       //*****
        documento.appendChild(expediente);

        Element fecha_presentacion = document.createElement("fecha_presentacion");
        fecha_presentacion.appendChild(document.createTextNode(Util.formatearFecha(tramiteDto.getFechaCarga(), BundleUtils.getResource("ddMMyyyy.hhmmss")) + ""));       //*****
        documento.appendChild(fecha_presentacion);

        //<ind_aplica_descuento_bajo_protesta>S</ind_aplica_descuento_bajo_protesta>
        boolean isOff = false;

        
        

    }
}

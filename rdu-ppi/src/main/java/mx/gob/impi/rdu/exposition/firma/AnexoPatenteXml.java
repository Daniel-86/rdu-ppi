/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.firma;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;
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
import mx.gob.impi.rdu.dto.PersonaDto;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.dto.TramiteDto;
import mx.gob.impi.rdu.persistence.model.*;
import mx.gob.impi.rdu.util.BundleUtils;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.EnumSubTipoPatente;
import mx.gob.impi.rdu.util.Util;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author
 */
public class AnexoPatenteXml {

    private Document document;
    private Element documento;
    private TramitePatente tramiteDto;
    private ApoderadoDto apoderado;
    private PromoventeDto promovente;
    private FirmaDto firmaDto;
    private Logger logger = Logger.getLogger(this.getClass());
    public static final String XML_MAIN_HEADER = "documento_electronico";
    public static final String XML_SUB_MAIN = "documento";
    public static final String XML_AREA = "4";
    public final static String XML_TIPO_DOCUMENTO = "XML";
    public static final String XML_TIPO_SOLICITUD = "1";
    public static final String XML_TIPO_SOLICITANTE = "1";
    public static final String XML_TIPO_CLASE = "10";
    public static final String CLASE = "5";

    public AnexoPatenteXml() {
        super();
    }

    public AnexoPatenteXml(TramitePatente tramite, FirmaDto firma, PromoventeDto promo) {
        this.tramiteDto = tramite;
        this.firmaDto = firma;
        this.promovente=promo;
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

        this.creaSolPatente();//+

        this.creaAnexos();

        this.creaFoliosPago();

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

    private void creaFoliosPago() {
        
        Element foliosPago = document.createElement("folios_pago");
        documento.appendChild(foliosPago);

        Element folio = document.createElement("folio");
        foliosPago.appendChild(folio);

        Element folioId = document.createElement("folio");
        folioId.appendChild(document.createTextNode(this.firmaDto.getPago().getFoliopago() + ""));
        folio.appendChild(folioId);

        Element fechaPago = document.createElement("fecha_pago");
        fechaPago.appendChild(document.createTextNode(  this.firmaDto.getPago().getFechapago()==null?"":  Util.formatearFecha(this.firmaDto.getPago().getFechapago(), Util.FORMATODDMMYYYYHHMMSS)  + ""));
        folio.appendChild(fechaPago);

        Element monto = document.createElement("monto");
        monto.appendChild(document.createTextNode(this.firmaDto.getPago().getTotal().toString() + ""));
        folio.appendChild(monto);

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


        Element cad2 = document.createElement("cadena");
        cadenas.appendChild(cad2);

        Element tipo2 = document.createElement("tipo");
        tipo2.appendChild(document.createTextNode("solicitante"));
        cad2.appendChild(tipo2);

        Element desc2 = document.createElement("descripcion");
        desc2.appendChild(document.createTextNode(this.firmaDto.getCadenaSolicitante() + ""));
        cad2.appendChild(desc2);

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


        Element firma2 = document.createElement("firma");
        firmas.appendChild(firma2);

        Element tipo2 = document.createElement("tipo");
        tipo2.appendChild(document.createTextNode("solicitante"));
        firma2.appendChild(tipo2);

        Element desc2 = document.createElement("descripcion");
        desc2.appendChild(document.createTextNode(this.firmaDto.getFirmaSolicitante() + ""));
        firma2.appendChild(desc2);

    }

    private void creaAnexos() {

        Element anexos = document.createElement("anexos");
        documento.appendChild(anexos);

        for (Anexos anex : this.tramiteDto.getAnexos()) {
            Element anexo = document.createElement("anexo");
            anexos.appendChild(anexo);

            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(anex.getIdAnexo().toString() + ""));
            anexo.appendChild(id);

            Element tipo_anexo = document.createElement("tipo_anexo");
            tipo_anexo.appendChild(document.createTextNode(anex.getIdTipoanexo().toString() + ""));
            anexo.appendChild(tipo_anexo);

            Element descripcion = document.createElement("descripcion");
            descripcion.appendChild(document.createTextNode(anex.getNombreArchivo() + ""));
            anexo.appendChild(descripcion);

            Element firma = document.createElement("firma");
            firma.appendChild(document.createTextNode(Util.getDigest(anex.getArchivoAnexo()) + ""));
            anexo.appendChild(firma);
            //Si se tienen archivos de traducción
            if (anex.getOtroIdioma()==2 && anex.getArchivoTrad()!=null){
//                Element anexoTrad = document.createElement("anexo");
//                anexos.appendChild(anexoTrad);
//
//                Element idTrad = document.createElement("id");
//                idTrad.appendChild(document.createTextNode(anex.getIdAnexo().toString() + ""));
//                anexo.appendChild(idTrad);

                Element tipo_anexoTrad = document.createElement("tipo_anexo");
                tipo_anexoTrad.appendChild(document.createTextNode(anex.getIdTipoanexoTrad().toString() + ""));
                anexo.appendChild(tipo_anexoTrad);

                Element descripcionTrad = document.createElement("descripcion");
                descripcionTrad.appendChild(document.createTextNode(anex.getNombreTrad() + ""));
                anexo.appendChild(descripcionTrad);

                Element firmaTrad = document.createElement("firma");
                firmaTrad.appendChild(document.createTextNode(Util.getDigest(anex.getArchivoTrad()) + ""));
                anexo.appendChild(firmaTrad);    
            }
        } 
    }

    private void creaSolPatente() {

        Element solPatente = document.createElement("sol_patente");


        //solMarca.appendChild(document.createTextNode("1"));       //*****
        documento.appendChild(solPatente);


        Element tipoSubtipo = document.createElement("tipo_subtipo");
        tipoSubtipo.appendChild(document.createTextNode(this.tramiteDto.getSubTipoSol().getDescripcion() + "")); //***************      
        solPatente.appendChild(tipoSubtipo);


        Element fechaDivulgacion = document.createElement("fecha_divulgacion_previa");
        fechaDivulgacion.appendChild(document.createTextNode(this.tramiteDto.getFechaDivPrevia() == null ? "" : Util.formatearFecha(this.tramiteDto.getFechaDivPrevia(), Util.FORMATODDMMYYYY)   + "")); //***************      
        solPatente.appendChild(fechaDivulgacion);

        Element tituloInvencion = document.createElement("titulo_invencion");
        tituloInvencion.appendChild(document.createTextNode(this.tramiteDto.getInvencion() == null ? "" : this.tramiteDto.getSubTipoSol().getDescripcion() + " " + this.tramiteDto.getInvencion() + "")); //***************      
        solPatente.appendChild(tituloInvencion);
        boolean esInventor = false;
        
        //VALIDACION YA QUE EN PROMOCIONES NO SE HA ESTABLECIDO COMO RECUPERAR LOS SOLICITANTES
        if(this.tramiteDto.getSolicitantes() != null && !this.tramiteDto.getSolicitantes().isEmpty()){
            for (Persona psn : this.tramiteDto.getSolicitantes()) {
                if (psn.getInventor() != null && psn.getInventor() > 0) {
                    esInventor = true;
                    break;
                }
            }            
        }

        Element solInventor = document.createElement("ind_solicitante_inventor");
        solInventor.appendChild(document.createTextNode(esInventor ? "S" : "N")); //***************      
        solPatente.appendChild(solInventor);


        Element observacion = document.createElement("observacion");
        observacion.appendChild(document.createTextNode(this.tramiteDto.getObservaciones() == null ? "" : this.tramiteDto.getObservaciones() + "")); //***************      
        solPatente.appendChild(observacion);

        if(this.tramiteDto.getSolicitantes() != null){
            Element solicitantes = document.createElement("solicitantes");
            this.creaSolicitante(solicitantes, "solicitante", this.tramiteDto.getSolicitantes());
            solPatente.appendChild(solicitantes);
        }
        
        if(this.tramiteDto.getInventores() != null){
            Element inventores = document.createElement("inventores");
            this.creaInventores(inventores, "inventor", this.tramiteDto.getInventores());
            solPatente.appendChild(inventores);
        }
        
        if(this.tramiteDto.getApoderados() != null){
            Element apoderados = document.createElement("apoderados");
            this.creaApoderados(apoderados, "apoderado", this.tramiteDto.getApoderados());
            solPatente.appendChild(apoderados);
        }
        
        if(this.tramiteDto.getPersonasNot() != null){
            Element notificaciones = document.createElement("personas_oir_recibir_notificacion");
            this.creaPersonasNotificaciones(notificaciones, "persona_oir_recibir_notificacion", this.tramiteDto.getPersonasNot());
            solPatente.appendChild(notificaciones);
        }
        
        Element firmante = document.createElement("firmante");
        this.creaFirmante(firmante);  /////  pendiente --------------************---
        solPatente.appendChild(firmante);
        
        Element domicilioNot = document.createElement("direccion_notificacion");
        this.creaDomicilioNotificacion(domicilioNot);  /////  pendiente --------------************---
        solPatente.appendChild(domicilioNot);
        
         Element divisional = document.createElement("divisional_solicitud");
        this.creaDivisionalSolicitud(divisional);  /////  pendiente --------------************---
        solPatente.appendChild(divisional);
        
        if(this.tramiteDto.getPrioridades() != null){
            Element prioridades = document.createElement("prioridades");
            this.creaPrioridadess(prioridades, this.tramiteDto.getPrioridades());  /////  pendiente --------------************---
            solPatente.appendChild(prioridades);            
        }
        
        if(this.tramiteDto.getReivindicaciones() != null){
            Element reivindicaciones = document.createElement("reivindicaciones");
            this.creaReinvidicaciones(reivindicaciones, this.tramiteDto.getReivindicaciones());  /////  pendiente --------------************---
            solPatente.appendChild(reivindicaciones);            
        }
        
        if(this.tramiteDto.getImagenes() != null){
            Element dibujos = document.createElement("dibujos");
            this.creaDibujos(dibujos, this.tramiteDto.getImagenes());  /////  pendiente --------------************---
            solPatente.appendChild(dibujos);
        }
        

//        Element signoDistintivo = document.createElement("invencion");
//        signoDistintivo.appendChild(document.createTextNode(this.tramiteDto.getInvencion() == null ? "" : this.tramiteDto.getInvencion() + ""));
//        solPatente.appendChild(signoDistintivo);
//
//        Element fechaPrimerUso = document.createElement("fecha_divulgacion");

//        fechaPrimerUso.appendChild(document.createTextNode(this.tramiteDto.getFechaDivulgacion() == null ? "" : (new SimpleDateFormat("dd/MM/yyyy")).format(this.tramiteDto.getFechaDivulgacion()) + ""));
//        solMarca.appendChild(fechaPrimerUso);

//        Element noSeHaUsado = document.createElement("no_se_ha_usado");
//        noSeHaUsado.appendChild(document.createTextNode(this.tramiteDto. getNousadofecha()==1?"true":"false"));
//        solMarca.appendChild(noSeHaUsado);

//        Element indMarcaColectiva = document.createElement("ind_marca_colectiva");
//        indMarcaColectiva.appendChild(document.createTextNode(tramiteDto.getMarcacolectiva()==null? "": tramiteDto.getMarcacolectiva().toString()+""));
//        solMarca.appendChild(indMarcaColectiva);


//        Element tipoClase = document.createElement("tipo_clase");
//        tipoClase.appendChild(document.createTextNode(tramiteDto.get getTipoClaseSeleccionadaDto().getTipoClase()+""));
//        solMarca.appendChild(tipoClase);

//        Element clase = document.createElement("clase");
//        clase.appendChild(document.createTextNode(new Long(tramiteDto.getTipoClaseSeleccionadaDto().getIdClase()).toString()+""));
//        solMarca.appendChild(clase);

//        Element productosOServicios = document.createElement("resumen");
//        productosOServicios.appendChild(document.createTextNode(tramiteDto.getResumen() == null ? "" : tramiteDto.getResumen() + ""));
//        solMarca.appendChild(productosOServicios);

//        Element metodoSeleccion = document.createElement("metodo_seleccion");
//        metodoSeleccion.appendChild(document.createTextNode(tramiteDto.getTipoClaseSeleccionadaDto().getIdFormaseleccionclase()==null?"":  tramiteDto.getTipoClaseSeleccionadaDto().getIdFormaseleccionclase().toString()+""));//********************************************
//        solMarca.appendChild(metodoSeleccion);

//        Element ubicacionEstablecimientos = document.createElement("ubicacion_establecimientos");
//        this.creaUbicacionEstablecimientos(ubicacionEstablecimientos);
//        solMarca.appendChild(ubicacionEstablecimientos);

        //Element detClases = document.createElement("detClases");
        //detClases.appendChild(document.createTextNode(tramiteDto.getProductoServicio()==null?"" :tramiteDto.getProductoServicio()));//************************************************
        //solMarca.appendChild(detClases);

//        Element prioridades = document.createElement("prioridades");
//        this.creaPrioridades(prioridades); // here i go
//        solMarca.appendChild(prioridades);
//
//        Element leyendasFigurasNoReservables = document.createElement("leyendas_figuras_no_reservables");
//        leyendasFigurasNoReservables.appendChild(document.createTextNode(tramiteDto.getObservaciones() == null ? "" : tramiteDto.getObservaciones() + ""));
//        solMarca.appendChild(leyendasFigurasNoReservables);





    }

    private void creaPrioridades(Element prioridades) {

        for (Prioridad prior : Util.checkListNull(tramiteDto.getPrioridades())) {
            Element prioridad = document.createElement("prioridad");

            Element solicitudInternacional = document.createElement("solicitudInternacional");
            solicitudInternacional.appendChild(document.createTextNode(prior.getNumeroExpediente() == null ? "" : prior.getNumeroExpediente() + ""));
            prioridad.appendChild(solicitudInternacional);

            Element pais = document.createElement("pais");
            pais.appendChild(document.createTextNode(prior.getNombrePais() == null ? "" : prior.getNombrePais()));
            prioridad.appendChild(pais);

            Element paisDesc = document.createElement("pais_desc");
            paisDesc.appendChild(document.createTextNode(prior.getNombrePais() == null ? "" : prior.getNombrePais()));
            prioridad.appendChild(paisDesc);


            Element fechaPresentacion = document.createElement("fecha_presentacion");
            fechaPresentacion.appendChild(document.createTextNode(prior.getFechaPresentacionExt() == null ? "" : new SimpleDateFormat(BundleUtils.getResource("ddMMyyyy")).format(prior.getFechaPresentacionExt()) + ""));
            prioridad.appendChild(fechaPresentacion);

            prioridades.appendChild(prioridad);


        }


    }

    private void creaUbicacionEstablecimientos(Element ubicacionEstablecimientos) {

        Element establecimiento = document.createElement("establecimiento");

        Element calle = document.createElement("calle");
        Element colonia = document.createElement("colonia");
        Element cp = document.createElement("cp");
        Element municipio = document.createElement("municipio");
        Element estado = document.createElement("estado");
        Element pais = document.createElement("pais");
        Element paisDesc = document.createElement("pais_desc");

//        if(  (this.tramiteDto.get getEstablecimientoXTramiteDto() != null) && (this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto() != null)    ){
//            calle.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getCalle()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getCalle())); //***************   
//            colonia.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getColonia()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getColonia())); //***************      
//            cp.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getCodigopostal()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getCodigopostal())); //***************      
//        
//            if(tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getIdPais()!=null ){
//                if(tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getIdPais().longValue()  == Constantes.ID_PAIS_MEXICO.longValue()){
//                    municipio.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPoblacion()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPoblacion()+"")); //***************     
//                    estado.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getEntidad().getNombre()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getEntidad().getNombre()+"")); //***************      
//                }else{                
//                    municipio.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPoblacion()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPoblacion()+"")); //***************     
//                    estado.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getIdEntidad()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getIdEntidad()+"")); //***************                      
//                }
//            }
//            
//            
//            pais.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto()==null?"":  this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto().getClavePais() ==null?"" :this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto().getClavePais() )); //***************   
//            paisDesc.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto()==null?"" : this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto().getNombre()==null?"" :this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto().getNombre()   )); //***************      
//            
//            
//            
//        }else{        
        calle.appendChild(document.createTextNode("")); //***************      
        colonia.appendChild(document.createTextNode("")); //***************      
        cp.appendChild(document.createTextNode("")); //***************      
        municipio.appendChild(document.createTextNode("")); //***************      
        estado.appendChild(document.createTextNode("")); //***************      
        pais.appendChild(document.createTextNode("")); //***************      
        paisDesc.appendChild(document.createTextNode("")); //***************      



//        }       

        establecimiento.appendChild(calle);
        establecimiento.appendChild(colonia);
        establecimiento.appendChild(cp);
        establecimiento.appendChild(municipio);
        establecimiento.appendChild(estado);
        establecimiento.appendChild(pais);
        establecimiento.appendChild(paisDesc);
        ubicacionEstablecimientos.appendChild(establecimiento);

    }

    private void creaFirmante(Element firmante) { // pendiente *************************



        Element tipo = document.createElement("tipo");
        tipo.appendChild(document.createTextNode("firmante"));
        firmante.appendChild(tipo);

        Element nombre = document.createElement("nombre");
        nombre.appendChild(document.createTextNode(firmaDto.getNombreFirmante()));//  tramiteDto. getNombreFirmante()
        firmante.appendChild(nombre);

//        Element nacionalidad = document.createElement("nacionalidad");
//        Element nacionalidad_desc = document.createElement("nacionalidad_desc");
//        Element calle = document.createElement("calle");
//        Element colonia = document.createElement("colonia");
//        Element codigo_postal = document.createElement("cp");
//        Element municipio = document.createElement("municipio");
//        Element estado = document.createElement("estado");
//        //Element pais_desc = document.createElement("pais_desc");
//        Element telefono = document.createElement("telefono");
//        Element fax = document.createElement("fax");
//        Element email = document.createElement("email");
//        
//        
//        
//        
//        //nacionalidad.appendChild(document.createTextNode(this.apoderado.getDescEstado()+ ""));
//
//         if(   this.promovente!=null  ){       
//            
//            
//           // nacionalidad.appendChild(document.createTextNode(this.promovente.get);
//            
//            
//            
//            //nacionalidad_desc.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto()==null ? "" :  this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto().getNacionalidad() ==null ? "" : this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto().getNacionalidad()));
//            calle.appendChild(document.createTextNode(  this.promovente.getCalle_numero()==null?"": this.promovente.getCalle_numero()+""));
//            colonia.appendChild(document.createTextNode(this.promovente.getColonia()==null?"":this.promovente.getColonia()+""));
//            municipio.appendChild(document.createTextNode(this.promovente.getDescMunicipio()==null? "":this.promovente.getDescMunicipio() +""));
//            estado.appendChild(document.createTextNode(this.promovente.getDescEstado()==null ? "":this.promovente.getDescEstado()+""));
//            //pais_desc.appendChild(document.createTextNode(this.promovente.get+"" ));
//             telefono.appendChild(document.createTextNode(this.promovente.getTelefono()==null?"":this.promovente.getTelefono() +""));
//             email.appendChild(document.createTextNode(this.promovente.getEmail()==null?"":this.promovente.getEmail() +""));
//             
//            codigo_postal.appendChild(document.createTextNode( this.promovente.getCodigo_postal()==null?"": this.promovente.getCodigo_postal() +"")); 
//             
//             
//        }else{
////        nacionalidad.appendChild(document.createTextNode(""));
////        nacionalidad_desc.appendChild(document.createTextNode(""));
//        calle.appendChild(document.createTextNode(""));
//        colonia.appendChild(document.createTextNode(""));
//         codigo_postal.appendChild(document.createTextNode(""));
//        municipio.appendChild(document.createTextNode(""));
//        estado.appendChild(document.createTextNode(""));
////        pais_desc.appendChild(document.createTextNode(""));
//        telefono.appendChild(document.createTextNode(""));
//        fax.appendChild(document.createTextNode(""));
//        email.appendChild(document.createTextNode(""));
//       
//       }
//
////        firmante.appendChild(nacionalidad);
////        firmante.appendChild(nacionalidad_desc);
//        firmante.appendChild(calle);
//        firmante.appendChild(colonia);
//        firmante.appendChild(codigo_postal);
//        firmante.appendChild(municipio);
//        firmante.appendChild(estado);
////        firmante.appendChild(pais_desc);
//        firmante.appendChild(telefono);
//        firmante.appendChild(fax);
//        firmante.appendChild(email);
//        
//
////        Element tipo_persona = document.createElement("tipo_persona");
////        tipo_persona.appendChild(document.createTextNode(this.apoderado.getTipo_persona()+""));
////        firmante.appendChild(tipo_persona);

    }
    private void creaDomicilioNotificacion(Element domNotificacion) { // pendiente *************************
       
        Element calle = document.createElement("calle");
        Element colonia = document.createElement("colonia");
        Element codigo_postal = document.createElement("cp");
        Element municipio = document.createElement("municipio");
        Element estado = document.createElement("estado");
        Element telefono = document.createElement("telefono");
        Element fax = document.createElement("fax");
        Element email = document.createElement("email");
        if( this.promovente!=null  ){       
            calle.appendChild(document.createTextNode(  this.promovente.getCalle_numero()==null?"": this.promovente.getCalle_numero()+""));
            colonia.appendChild(document.createTextNode(this.promovente.getColonia()==null?"":this.promovente.getColonia()+""));
            municipio.appendChild(document.createTextNode(this.promovente.getDescMunicipio()==null? "":this.promovente.getDescMunicipio() +""));
            estado.appendChild(document.createTextNode(this.promovente.getDescEstado()==null ? "":this.promovente.getDescEstado()+""));
            telefono.appendChild(document.createTextNode(this.promovente.getTelefono()==null?"":this.promovente.getTelefono() +""));
            email.appendChild(document.createTextNode(this.promovente.getEmail()==null?"":this.promovente.getEmail() +""));
            codigo_postal.appendChild(document.createTextNode( this.promovente.getCodigo_postal()==null?"": this.promovente.getCodigo_postal() +"")); 

        }else{
            calle.appendChild(document.createTextNode(""));
            colonia.appendChild(document.createTextNode(""));
            codigo_postal.appendChild(document.createTextNode(""));
            municipio.appendChild(document.createTextNode(""));
            estado.appendChild(document.createTextNode(""));
            telefono.appendChild(document.createTextNode(""));
            fax.appendChild(document.createTextNode(""));
            email.appendChild(document.createTextNode(""));
       }

        domNotificacion.appendChild(calle);
        domNotificacion.appendChild(colonia);
        domNotificacion.appendChild(codigo_postal);
        domNotificacion.appendChild(municipio);
        domNotificacion.appendChild(estado);
        domNotificacion.appendChild(telefono);
        domNotificacion.appendChild(fax);
        domNotificacion.appendChild(email);
        
    }

    private void creaSolicitante(Element solicitantes, String tipoPersona, List<Persona> personas) {

        for (Persona solDto : personas) {


            Element interesado = document.createElement(tipoPersona);

            Element tipo = document.createElement("tipo_persona");
            tipo.appendChild(document.createTextNode(solDto.getTipoPersona().getDescripcion().substring(0, 1)  ));
            interesado.appendChild(tipo);
            
            Element tipoDesc = document.createElement("tipo_persona_desc");
            tipoDesc.appendChild(document.createTextNode(solDto.getTipoPersona().getDescripcion()));
            interesado.appendChild(tipoDesc);
            
            Element tipoSolicitante = document.createElement("tipo_solicitante");
            tipoSolicitante.appendChild(document.createTextNode(solDto.getTipoSolicitante().getDescripcion()    ));
            interesado.appendChild(tipoSolicitante);
            
            Element indPrincipal = document.createElement("ind_solicitante_principal");
            indPrincipal.appendChild(document.createTextNode(  (solDto.getPrincipal() != null && solDto.getPrincipal() >0 )? "S" :"N"));
            interesado.appendChild(indPrincipal);
            
            
            Element nombre = document.createElement("nombre");
            String nombrecompleto=solDto.getNombrecompleto() + " " + (solDto.getPrimerApellido() == null ? "" : solDto.getPrimerApellido()) + " " + (solDto.getSegundoApellido() == null ? "" : solDto.getSegundoApellido()); 
            nombre.appendChild(document.createTextNode(solDto.getNombrecompleto() + " " + (solDto.getPrimerApellido() == null ? "" : solDto.getPrimerApellido()) + " " + (solDto.getSegundoApellido() == null ? "" : solDto.getSegundoApellido()) + ""));
            interesado.appendChild(nombre);

            Element nacionalidad = document.createElement("nacionalidad");
            nacionalidad.appendChild(document.createTextNode(solDto.getNacionalidad().getClavePais() == null ? "" : solDto.getNacionalidad().getClavePais() + "")); //***************      
            interesado.appendChild(nacionalidad);

            Element nacionalidadDesc = document.createElement("nacionalidad_desc");
            nacionalidadDesc.appendChild(document.createTextNode(solDto.getNacionalidad().getNacionalidad() == null ? "" : solDto.getNacionalidad().getNacionalidad() + "")); //***************      
            interesado.appendChild(nacionalidadDesc);

            Element calle = document.createElement("calle");
            calle.appendChild(document.createTextNode(solDto.getDomicilioObj().getCalle() == null ? "" : solDto.getDomicilioObj().getCalle() + " " + (solDto.getDomicilioObj().getNumExt()==null?"":solDto.getDomicilioObj().getNumExt()) + " " + (solDto.getDomicilioObj().getNumInt()==null?"":solDto.getDomicilioObj().getNumInt()) + "")); //***************      
            interesado.appendChild(calle);

            Element colonia = document.createElement("colonia");
            colonia.appendChild(document.createTextNode(solDto.getDomicilioObj().getColonia() == null ? "" : solDto.getDomicilioObj().getColonia() + "")); //***************      
            interesado.appendChild(colonia);

            Element cp = document.createElement("cp");
            cp.appendChild(document.createTextNode(solDto.getDomicilioObj().getCodigopostal() == null ? "" : solDto.getDomicilioObj().getCodigopostal() + "")); //***************      
            interesado.appendChild(cp);

            Element municipio = document.createElement("municipio");
            municipio.appendChild(document.createTextNode(solDto.getDomicilioObj().getPoblacion() == null ? "" : solDto.getDomicilioObj().getPoblacion() + "")); //***************      
            interesado.appendChild(municipio);

            Element estado = document.createElement("estado");
            if(solDto.getDomicilioObj().getIdPais()  != null && solDto.getDomicilioObj().getIdPais()  == Constantes.ID_PAIS_MEXICO){
            estado.appendChild(document.createTextNode(solDto.getDomicilioObj().getEntidad().getNombre() == null ? "" : solDto.getDomicilioObj().getEntidad().getNombre() + "")); //***************      
            }else{
            estado.appendChild(document.createTextNode(solDto.getDomicilioObj().getIdEntidad() == null ? "" : solDto.getDomicilioObj().getIdEntidad() + "")); //***************      
            }
            interesado.appendChild(estado);

            Element pais = document.createElement("pais");
            pais.appendChild(document.createTextNode(solDto.getDomicilioObj().getPais().getClavePais() == null ? "" : solDto.getDomicilioObj().getPais().getClavePais() + "")); //***************      
            interesado.appendChild(pais);

            Element pais_desc = document.createElement("pais_desc");
            pais_desc.appendChild(document.createTextNode(solDto.getDomicilioObj().getPais().getNombre() == null ? "" : solDto.getDomicilioObj().getPais().getNombre() + "")); //***************      
            interesado.appendChild(pais_desc);
            
            Element telefono = document.createElement("telefono");
            telefono.appendChild(document.createTextNode(solDto.getDatosContacto() == null ? "" : solDto.getDatosContacto().getTelefono() == null ? "" : solDto.getDatosContacto().getTelefono() + " " + (solDto.getDatosContacto().getTelefonoExt()==null?"":solDto.getDatosContacto().getTelefonoExt()) + "")); //***************      
            interesado.appendChild(telefono);

            Element fax = document.createElement("fax");
            fax.appendChild(document.createTextNode(solDto.getDatosContacto() == null ? "" : solDto.getDatosContacto().getFax() == null ? "" : solDto.getDatosContacto().getFax()+ "")); //***************      
            interesado.appendChild(fax);
           
            
            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(solDto.getDatosContacto() == null ? "" : solDto.getDatosContacto().getCorreoelectronico() == null ? "" : solDto.getDatosContacto().getCorreoelectronico() + "")); //***************      
            interesado.appendChild(email);


//        Element tipo_persona= document.createElement("tipo_persona");        
//        tipo_persona.appendChild(document.createTextNode(solDto.getTipoPersonaDto().getDescripcion()+"")); //***************      
//        interesado.appendChild(tipo_persona);


            solicitantes.appendChild(interesado);

        }

    }
    
     private void creaInventores(Element solicitantes, String tipoPersona, List<Persona> personas) {

        for (Persona solDto : personas) {


            Element inventor = document.createElement(tipoPersona);

            Element nombre = document.createElement("nombre");
            nombre.appendChild(document.createTextNode(solDto.getNombrecompleto()==null ? "" :solDto.getNombrecompleto()+ " " + (solDto.getPrimerApellido() == null ? "" : solDto.getPrimerApellido()) + " " + (solDto.getSegundoApellido() == null ? "" : solDto.getSegundoApellido()) + ""));
            inventor.appendChild(nombre);
            
          

            Element nacionalidad = document.createElement("nacionalidad");
            nacionalidad.appendChild(document.createTextNode(solDto.getNacionalidad().getClavePais() == null ? "" : solDto.getNacionalidad().getClavePais() + "")); //***************      
            inventor.appendChild(nacionalidad);

            Element nacionalidadDesc = document.createElement("nacionalidad_desc");
            nacionalidadDesc.appendChild(document.createTextNode(solDto.getNacionalidad().getNacionalidad() == null ? "" : solDto.getNacionalidad().getNacionalidad() + "")); //***************      
            inventor.appendChild(nacionalidadDesc);

            Element calle = document.createElement("calle");
            calle.appendChild(document.createTextNode(solDto.getDomicilioObj().getCalle() == null ? "" : solDto.getDomicilioObj().getCalle() + " " + (solDto.getDomicilioObj().getNumExt()==null?"":solDto.getDomicilioObj().getNumExt()) + " " + (solDto.getDomicilioObj().getNumInt()==null?"":solDto.getDomicilioObj().getNumInt()) + "")); //***************      
            inventor.appendChild(calle);

            Element colonia = document.createElement("colonia");
            colonia.appendChild(document.createTextNode(solDto.getDomicilioObj().getColonia() == null ? "" : solDto.getDomicilioObj().getColonia() + "")); //***************      
            inventor.appendChild(colonia);

            Element cp = document.createElement("cp");
            cp.appendChild(document.createTextNode(solDto.getDomicilioObj().getCodigopostal() == null ? "" : solDto.getDomicilioObj().getCodigopostal() + "")); //***************      
            inventor.appendChild(cp);

            Element municipio = document.createElement("municipio");
            municipio.appendChild(document.createTextNode(solDto.getDomicilioObj().getPoblacion() == null ? "" : solDto.getDomicilioObj().getPoblacion() + "")); //***************      
            inventor.appendChild(municipio);

            Element estado = document.createElement("estado");
            if(solDto.getDomicilioObj().getIdPais()  != null && solDto.getDomicilioObj().getIdPais()  == Constantes.ID_PAIS_MEXICO){
                estado.appendChild(document.createTextNode(solDto.getDomicilioObj().getEntidad().getNombre() == null ? "" : solDto.getDomicilioObj().getEntidad().getNombre() + "")); //***************      
            }else{
                estado.appendChild(document.createTextNode(solDto.getDomicilioObj().getIdEntidad() == null ? "" : solDto.getDomicilioObj().getIdEntidad()  + "")); //***************      
            }
            inventor.appendChild(estado);

            Element pais = document.createElement("pais");
            pais.appendChild(document.createTextNode(solDto.getDomicilioObj().getPais().getClavePais() == null ? "" : solDto.getDomicilioObj().getPais().getClavePais() + "")); //***************      
            inventor.appendChild(pais);

            Element pais_desc = document.createElement("pais_desc");
            pais_desc.appendChild(document.createTextNode(solDto.getDomicilioObj().getPais().getNombre() == null ? "" : solDto.getDomicilioObj().getPais().getNombre() + "")); //***************      
            inventor.appendChild(pais_desc);

            Element telefono = document.createElement("telefono");
            telefono.appendChild(document.createTextNode(solDto.getDatosContacto() == null ? "" : solDto.getDatosContacto().getTelefono() == null ? "" : solDto.getDatosContacto().getTelefono() + " " + (solDto.getDatosContacto().getTelefonoExt()==null?"":solDto.getDatosContacto().getTelefonoExt()) + "")); //***************      
            inventor.appendChild(telefono);

            Element fax = document.createElement("fax");
            fax.appendChild(document.createTextNode(solDto.getDatosContacto() == null ? "" : solDto.getDatosContacto().getFax() == null ? "" : solDto.getDatosContacto().getFax()+ "")); //***************      
            inventor.appendChild(fax);
           
            
            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(solDto.getDatosContacto() == null ? "" : solDto.getDatosContacto().getCorreoelectronico() == null ? "" : solDto.getDatosContacto().getCorreoelectronico() + "")); //***************      
            inventor.appendChild(email);


//        Element tipo_persona= document.createElement("tipo_persona");        
//        tipo_persona.appendChild(document.createTextNode(solDto.getTipoPersonaDto().getDescripcion()+"")); //***************      
//        interesado.appendChild(tipo_persona);


            solicitantes.appendChild(inventor);

        }

    }
     
     private void creaApoderados(Element solicitantes, String tipoPersona, List<Persona> personas) {

        for (Persona solDto : personas) {


            Element apodera = document.createElement(tipoPersona);

            
            //<ind_apoderado_principal>S</ind_apoderado_principal>
            
            Element indPrincipal = document.createElement("ind_apoderado_principal");
            indPrincipal.appendChild( document.createTextNode(   ( solDto.getPrincipal()  != null &&  solDto.getPrincipal()>0 ) ? "S":"N"    ));
            apodera.appendChild(indPrincipal);                     
           
            Element nombre = document.createElement("nombre");
            nombre.appendChild(document.createTextNode(solDto.getNombrecompleto()==null ? "" :solDto.getNombrecompleto()+ " " + (solDto.getPrimerApellido() == null ? "" : solDto.getPrimerApellido()) + " " + (solDto.getSegundoApellido() == null ? "" : solDto.getSegundoApellido()) + ""));
            apodera.appendChild(nombre);            
          
            Element rgp = document.createElement("rgp");
            rgp.appendChild(document.createTextNode(solDto.getRgp()==null ? "" :solDto.getRgp()+"" ));
            apodera.appendChild(rgp);            

//            Element calle = document.createElement("calle");
//            calle.appendChild(document.createTextNode(this.tramiteDto.getDomicilioObj().getCalle() == null ? "" : this.tramiteDto.getDomicilioObj().getCalle() + " " + (this.tramiteDto.getDomicilioObj().getNumExt()==null?"":this.tramiteDto.getDomicilioObj().getNumExt()) + " " + (this.tramiteDto.getDomicilioObj().getNumInt()==null?"":this.tramiteDto.getDomicilioObj().getNumInt()) + "")); //***************      
////            calle.appendChild(document.createTextNode(solDto.getDomicilioObj().getCalle() == null ? "" : solDto.getDomicilioObj().getCalle() + " " + (solDto.getDomicilioObj().getNumExt()==null?"":solDto.getDomicilioObj().getNumExt()) + " " + (solDto.getDomicilioObj().getNumInt()==null?"":solDto.getDomicilioObj().getNumInt()) + "")); //***************      
//            apodera.appendChild(calle);
//
//            Element colonia = document.createElement("colonia");
//            
//            colonia.appendChild(document.createTextNode(this.tramiteDto.getDomicilioObj().getColonia() == null ? "" : this.tramiteDto.getDomicilioObj().getColonia() + ""));                    
////            colonia.appendChild(document.createTextNode(solDto.getDomicilioObj().getColonia() == null ? "" : solDto.getDomicilioObj().getColonia() + "")); //***************      
//            apodera.appendChild(colonia);
//
//            Element cp = document.createElement("cp");
//            cp.appendChild(document.createTextNode(this.tramiteDto.getDomicilioObj().getCodigopostal() == null ? "" : this.tramiteDto.getDomicilioObj().getCodigopostal() + "")); //***************                  
////            cp.appendChild(document.createTextNode(solDto.getDomicilioObj().getCodigopostal() == null ? "" : solDto.getDomicilioObj().getCodigopostal() + "")); //***************      
//            apodera.appendChild(cp);
//
//            Element municipio = document.createElement("municipio");
//            municipio.appendChild(document.createTextNode(this.tramiteDto.getDomicilioObj().getPoblacion() == null ? "" : this.tramiteDto.getDomicilioObj().getPoblacion() + "")); //***************                  
////            municipio.appendChild(document.createTextNode(solDto.getDomicilioObj().getPoblacion() == null ? "" : solDto.getDomicilioObj().getPoblacion() + "")); //***************      
//            apodera.appendChild(municipio);
//
//            Element estado = document.createElement("estado");
//            if(this.tramiteDto.getDomicilioObj().getIdPais()  != null && this.tramiteDto.getDomicilioObj().getIdPais()  == Constantes.ID_PAIS_MEXICO){
//            estado.appendChild(document.createTextNode(this.tramiteDto.getDomicilioObj().getEntidad().getNombre() == null ? "" : this.tramiteDto.getDomicilioObj().getEntidad().getNombre() + "")); //***************      
//            }else{
//            estado.appendChild(document.createTextNode(this.tramiteDto.getDomicilioObj().getIdEntidad() == null ? "" : this.tramiteDto.getDomicilioObj().getIdEntidad() + "")); //***************      
//            }            
////            if(solDto.getDomicilioObj().getIdPais()  != null && solDto.getDomicilioObj().getIdPais()  == Constantes.ID_PAIS_MEXICO){
////            estado.appendChild(document.createTextNode(solDto.getDomicilioObj().getEntidad().getNombre() == null ? "" : solDto.getDomicilioObj().getEntidad().getNombre() + "")); //***************      
////            }else{
////            estado.appendChild(document.createTextNode(solDto.getDomicilioObj().getIdEntidad() == null ? "" : solDto.getDomicilioObj().getIdEntidad() + "")); //***************      
////            }
//            apodera.appendChild(estado);
//
//            Element pais = document.createElement("pais");
//            pais.appendChild(document.createTextNode(this.tramiteDto.getDomicilioObj().getPais().getClavePais() == null ? "" : this.tramiteDto.getDomicilioObj().getPais().getClavePais() + "")); //***************                  
////            pais.appendChild(document.createTextNode(solDto.getDomicilioObj().getPais().getClavePais() == null ? "" : solDto.getDomicilioObj().getPais().getClavePais() + "")); //***************      
//            apodera.appendChild(pais);
//
//            Element pais_desc = document.createElement("pais_desc");
//            pais_desc.appendChild(document.createTextNode(this.tramiteDto.getDomicilioObj().getPais().getNombre() == null ? "" : this.tramiteDto.getDomicilioObj().getPais().getNombre() + "")); //***************                  
////            pais_desc.appendChild(document.createTextNode(solDto.getDomicilioObj().getPais().getNombre() == null ? "" : solDto.getDomicilioObj().getPais().getNombre() + "")); //***************      
//            apodera.appendChild(pais_desc);
//
//            Element telefono = document.createElement("telefono");
//            telefono.appendChild(document.createTextNode(this.tramiteDto.getDatosContacto() == null ? "" : this.tramiteDto.getDatosContacto().getTelefono() == null ? "" : this.tramiteDto.getDatosContacto().getTelefono() + " " + (this.tramiteDto.getDatosContacto().getTelefonoExt()==null?"":this.tramiteDto.getDatosContacto().getTelefonoExt()) + "")); //***************                  
////            telefono.appendChild(document.createTextNode(solDto.getDatosContacto() == null ? "" : solDto.getDatosContacto().getTelefono() == null ? "" : solDto.getDatosContacto().getTelefono() + " " + (solDto.getDatosContacto().getTelefonoExt()==null?"":solDto.getDatosContacto().getTelefonoExt()) + "")); //***************      
//            apodera.appendChild(telefono);
//
//            Element fax = document.createElement("fax");
//            fax.appendChild(document.createTextNode(this.tramiteDto.getDatosContacto() == null ? "" : this.tramiteDto.getDatosContacto().getFax() == null ? "" : this.tramiteDto.getDatosContacto().getFax()+ "")); //***************                  
////            fax.appendChild(document.createTextNode(solDto.getDatosContacto() == null ? "" : solDto.getDatosContacto().getFax() == null ? "" : solDto.getDatosContacto().getFax()+ "")); //***************      
//            apodera.appendChild(fax);
//           
//            
//            Element email = document.createElement("email");
//            email.appendChild(document.createTextNode(this.tramiteDto.getDatosContacto() == null ? "" : this.tramiteDto.getDatosContacto().getCorreoelectronico() == null ? "" : this.tramiteDto.getDatosContacto().getCorreoelectronico() + "")); //***************                  
////            email.appendChild(document.createTextNode(solDto.getDatosContacto() == null ? "" : solDto.getDatosContacto().getCorreoelectronico() == null ? "" : solDto.getDatosContacto().getCorreoelectronico() + "")); //***************      
//            apodera.appendChild(email);


//        Element tipo_persona= document.createElement("tipo_persona");        
//        tipo_persona.appendChild(document.createTextNode(solDto.getTipoPersonaDto().getDescripcion()+"")); //***************      
//        interesado.appendChild(tipo_persona);


            solicitantes.appendChild(apodera);

        }

    }
     
    private void creaPersonasNotificaciones(Element solicitantes, String tipoPersona, List<Persona> personas) {

        for (Persona solDto : personas) {


            Element notificacion = document.createElement(tipoPersona);

            
            //<ind_apoderado_principal>S</ind_apoderado_principal>
            
            Element nombre = document.createElement("nombre");
            nombre.appendChild( document.createTextNode( solDto.getNombrecompleto()==null ? "":  solDto.getNombrecompleto()   ));
            notificacion.appendChild(nombre);                     
                 


            solicitantes.appendChild(notificacion);

        }

    }
    
    
      private void creaDivisionalSolicitud(Element divisional) {


        //<ind_apoderado_principal>S</ind_apoderado_principal>

        Element numero = document.createElement("numero");
        numero.appendChild(document.createTextNode(this.tramiteDto.getExpDivisional() == null ? "" : tramiteDto.getExpDivisional() + ""));
        divisional.appendChild(numero);
        String tmpExp = null;
        String idTipoDivisional = this.tramiteDto.getExpDivisional()==null ? "": this.tramiteDto.getExpDivisional().substring(3, 4);

//                    if(this.tramiteDto.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.CIRCUITO_INTEGRADO.getIdSubTipoPatente()){
//                        tmpExp = EnumSubTipoPatente.CIRCUITO_INTEGRADO.getDescripcion();
//                    }else if(this.tramiteDto.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.DISENO_INDUSTRIAL.getIdSubTipoPatente()){
//                        tmpExp = EnumSubTipoPatente.DISENO_INDUSTRIAL.getDescripcion();
//                    }else if(this.tramiteDto.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.MODELO_UTILIDAD.getIdSubTipoPatente()){
//                        tmpExp = EnumSubTipoPatente.MODELO_UTILIDAD.getDescripcion();
//                    }else if(this.tramiteDto.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.PATENTE.getIdSubTipoPatente()){
//                        tmpExp = EnumSubTipoPatente.PATENTE.getDescripcion();
//                    }
        if (idTipoDivisional.equals(EnumSubTipoPatente.CIRCUITO_INTEGRADO.getCode())) {
            tmpExp = EnumSubTipoPatente.CIRCUITO_INTEGRADO.getDescripcion();
        } else if (idTipoDivisional.equals(EnumSubTipoPatente.DISENO_INDUSTRIAL.getCode())) {
            tmpExp = EnumSubTipoPatente.DISENO_INDUSTRIAL.getDescripcion();
        } else if (idTipoDivisional.equals(EnumSubTipoPatente.MODELO_UTILIDAD.getCode())) {
            tmpExp = EnumSubTipoPatente.MODELO_UTILIDAD.getDescripcion();
        } else if (idTipoDivisional.equals(EnumSubTipoPatente.PATENTE.getCode())) {
            tmpExp = EnumSubTipoPatente.PATENTE.getDescripcion();
        }


        Element figuraJuridica = document.createElement("figura_juridica");
        figuraJuridica.appendChild(document.createTextNode(tmpExp == null ? "" : tmpExp + ""));
        divisional.appendChild(figuraJuridica);


        Element fechaPresentacion = document.createElement("fecha_presentacion");
        fechaPresentacion.appendChild(document.createTextNode(this.tramiteDto.getFechaPresentacionExp() == null ? "" : Util.formatearFecha(this.tramiteDto.getFechaPresentacionExp(), Util.FORMATODDMMYYYY) + ""));
        divisional.appendChild(fechaPresentacion);



    }
      
      
        private void creaPrioridadess(Element prioridades,  List<Prioridad> prioridads) {

        for (Prioridad prior : prioridads) {

            
            Element prioridad = document.createElement("prioridad");

            
            //<ind_apoderado_principal>S</ind_apoderado_principal>
            
            Element numeroSerie = document.createElement("numero_serie");
            numeroSerie.appendChild( document.createTextNode(   prior.getNumeroExpediente()==null?"":    prior.getNumeroExpediente() +""   ));
            prioridad.appendChild(numeroSerie);                     
            
            
            Element pais = document.createElement("codigo_pais");
            pais.appendChild( document.createTextNode(   prior.getCodigoPais() ==null?"":    prior.getCodigoPais() +""   ));
            prioridad.appendChild(pais);     
           
            Element pais_desc = document.createElement("nombre_pais");
            pais_desc.appendChild( document.createTextNode(   prior.getNombrePais() ==null?"":    prior.getNombrePais() +""   ));
            prioridad.appendChild(pais_desc);     
           
            
            
            Element fechaPresentacion = document.createElement("fecha_presentacion");
            fechaPresentacion.appendChild( document.createTextNode(   prior.getFechaPresentacionExt() ==null? "":  Util.formatearFecha(prior.getFechaPresentacionExt() , Util.FORMATODDMMYYYY)  +""   ));
            prioridad.appendChild(fechaPresentacion);     
            
            
        

            prioridades.appendChild(prioridad);

        }

    }
     
        
        private void creaReinvidicaciones(Element eReivindicaciones,  List<Reivindicacion> reivindicaciones) {

            String chainHashReivindicacion = "";
            String chainHashesReivindicaciones = "";
            
        for (Reivindicacion rein : reivindicaciones) {
            
            Element reivindicacion = document.createElement("reivindicacion");
            
            Element numeroSerie = document.createElement("numero");
            numeroSerie.appendChild( document.createTextNode(   rein.getOrden()==null?"":    rein.getOrden() +""   ));
            reivindicacion.appendChild(numeroSerie);
            
            Element descripcion = document.createElement("descripcion");
            descripcion.appendChild( document.createTextNode(   rein.getDescripcion()==null?"":    rein.getDescripcion() +""   ));
            reivindicacion.appendChild(descripcion);
            
            Element firma = document.createElement("firma");
            chainHashReivindicacion = rein.getDescripcion()==null?"":    Util.getDigest(rein.getDescripcion().getBytes());
            firma.appendChild( document.createTextNode( chainHashReivindicacion ));
            chainHashesReivindicaciones += chainHashReivindicacion;
            reivindicacion.appendChild(firma);
           
            eReivindicaciones.appendChild(reivindicacion);

        }
        Element firmas = document.createElement("firmas");
        firmas.appendChild( document.createTextNode( chainHashesReivindicaciones.equals("") ? "" : Util.getDigest(chainHashesReivindicaciones.getBytes())) );
        eReivindicaciones.appendChild(firmas);

    }
 
    private void creaDibujos(Element dibujos, List<ImagenDibujo> imagenes) {

        String chainFirmaImagen = "";
        String chainFirmasImagenes = "";

        for (ImagenDibujo img : imagenes) {

            Element dibujo = document.createElement("dibujo");

            //<ind_apoderado_principal>S</ind_apoderado_principal>

            Element numero = document.createElement("numero");
            numero.appendChild(document.createTextNode(img.getOrden() == null ? "" : img.getOrden() + ""));
            dibujo.appendChild(numero);

            Element descripcion = document.createElement("descripcion");
            descripcion.appendChild(document.createTextNode(img.getArchivo() == null ? "" : img.getArchivo().toString() + ""));
            dibujo.appendChild(descripcion);

            Element firma = document.createElement("firma");
            chainFirmaImagen = img.getArchivo()== null ? "" : Util.getDigest(img.getArchivo());
            //firma.appendChild(document.createTextNode(Util.getDigest(img.getArchivo()) + ""));
            firma.appendChild(document.createTextNode(chainFirmaImagen));
            dibujo.appendChild(firma);
            
            chainFirmasImagenes += chainFirmaImagen;

            dibujos.appendChild(dibujo);

        }
        
        Element firmas = document.createElement("firmas");
        firmas.appendChild(document.createTextNode(chainFirmasImagenes.equals("") ? "" : Util.getDigest(chainFirmasImagenes.getBytes())));
        dibujos.appendChild(firmas);

    }
    
    

    private void creaMainInfo() {


        Element area = document.createElement("area");
        area.appendChild(document.createTextNode(XML_AREA)); //***************      
        documento.appendChild(area);

        Element tipoDocumento = document.createElement("tipo_documento");
        tipoDocumento.appendChild(document.createTextNode(XML_TIPO_DOCUMENTO));       //*****
        documento.appendChild(tipoDocumento);

        Element tipoSolicitud = document.createElement("tipo_solicitud");
        tipoSolicitud.appendChild(document.createTextNode(tramiteDto.getTipoSol().getDescripcion()+ ""));       //*****
        documento.appendChild(tipoSolicitud);
        
        Element expediente = document.createElement("expediente");
        expediente.appendChild(document.createTextNode(this.firmaDto.getExpedienteId().toString() + ""));       //*****
        documento.appendChild(expediente);

        Element folio = document.createElement("folio");
        folio.appendChild(document.createTextNode(this.firmaDto.getFolioId() + ""));       //*****
        documento.appendChild(folio);
        
        Element fecha_presentacion = document.createElement("fecha_presentacion");
        fecha_presentacion.appendChild(document.createTextNode(Util.formatearFecha(tramiteDto.getSysDate(), BundleUtils.getResource("ddMMyyyy.hhmmss")) + ""));       //*****
        documento.appendChild(fecha_presentacion);

        //<ind_aplica_descuento_bajo_protesta>S</ind_aplica_descuento_bajo_protesta>
        boolean isOff = false;

        //VALIDACION YA QUE EN PROMOCIONES NO SE HA ESTABLECIDO COMO RECUPERAR LOS SOLICITANTES
        if(this.tramiteDto.getSolicitantes() != null && !this.tramiteDto.getSolicitantes().isEmpty()){
            for (Persona psn : this.tramiteDto.getSolicitantes()) {
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
        }
        

        Element aplicaDescuento = document.createElement("ind_aplica_descuento_bajo_protesta");
        aplicaDescuento.appendChild(document.createTextNode(isOff?"S" :"N"));       //*****
        documento.appendChild(aplicaDescuento);


    }
}

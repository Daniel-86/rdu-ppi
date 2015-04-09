/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.firma;
    

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import mx.gob.impi.rdu.dto.ApoderadoDto;
import mx.gob.impi.rdu.dto.PersonaDto;
import mx.gob.impi.rdu.dto.TramiteDto;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.util.BundleUtils;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.Util;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author
 */
public class AnexoFirmanteXml {
    
     private Document document;
     private Element documento;
     private TramiteDto tramiteDto;
     private ApoderadoDto apoderado;
     private Logger logger = Logger.getLogger(this.getClass());

     public static final String XML_MAIN_HEADER="documento_electronico";
     public static final String XML_SUB_MAIN="documento";
     public static final String XML_AREA="8";
     public final static String XML_TIPO_DOCUMENTO="XML";
     public static final String XML_TIPO_SOLICITUD="1";
     public static final String XML_TIPO_SOLICITANTE="1";
     public static final String XML_TIPO_CLASE="10";
     public static final String CLASE="5";
     
    public AnexoFirmanteXml() {
        super();
    }

     
     
    public AnexoFirmanteXml(TramiteDto tramite, ApoderadoDto apoderado ) {
        this.tramiteDto = tramite;
        this.apoderado = apoderado;
    }
    
     
     
     
     public byte[] getDocumentoXml()   {
    
        byte[]  xmlByte = null;        
          try {
            
            this.generaXml();           
            xmlByte =this.exportToByteArray();
    
          } catch (Exception e) {
              e.printStackTrace();
              logger.error("Error al convertir archivo xml... "+e.getMessage() , e);
          }
            
          return xmlByte;    
    }
    
    
    public void generaXml() throws Exception
    {
         
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         DocumentBuilder db = dbf.newDocumentBuilder();
         document = db.newDocument();
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Element docRoot = document.createElement(XML_MAIN_HEADER);
        document.appendChild(docRoot);
        
        documento = document.createElement(XML_SUB_MAIN);        
        docRoot.appendChild(documento);
                
        this.creaMainInfo();//DONE
        
        this.creaSolMarca();
        
        this.creaAnexos();
        
        this.creaFoliosPago();
        
        this.creaCadenas();
        
        this.creaFirmas();
   
    }
    
    public byte[] exportToByteArray() throws Exception
   {
          Source source = new DOMSource(this.document);  
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          StringWriter stringWriter = new StringWriter();
         Result result = new StreamResult(out);
         TransformerFactory fact = TransformerFactory.newInstance();
         Transformer trans = fact.newTransformer();
         trans.transform(source, result);
         
         return out.toByteArray();
         
                    
   }
    
    
    private void creaFoliosPago(){
        
        Element foliosPago = document.createElement("folios_pago");
        documento.appendChild(foliosPago);
        
        Element folio = document.createElement("folio");
        foliosPago.appendChild(folio);
        
        Element folioId = document.createElement("folio");
        folioId.appendChild(document.createTextNode(this.tramiteDto.getPagoDto().getFoliopago()+"" ));
        folio.appendChild(folioId);
                
        Element fechaPago = document.createElement("fecha_pago");              
        fechaPago.appendChild(document.createTextNode((new SimpleDateFormat("dd/MM/yyyy")).format(this.tramiteDto.getPagoDto().getFechapago())+""));
        folio.appendChild(fechaPago);
        
        
        Element monto = document.createElement("monto");
        monto.appendChild(document.createTextNode(this.tramiteDto.getPagoDto().getTotal().toString()+ ""));
        folio.appendChild(monto);
        
        
    }
    
       private void creaCadenas(){
        
        Element cadenas = document.createElement("cadenas");
        documento.appendChild(cadenas);
        
        Element cad = document.createElement("cadena");
        cadenas.appendChild(cad);
        
        Element tipo = document.createElement("tipo");
        tipo.appendChild(document.createTextNode("impi" ));
        cad.appendChild(tipo);
                
        Element desc = document.createElement("descripcion");              
        desc.appendChild(document.createTextNode(tramiteDto.getFirma().getCadenaImpi() +""));
        cad.appendChild(desc);
        
        
         Element cad2 = document.createElement("cadena");
         cadenas.appendChild(cad2);
        
        Element tipo2 = document.createElement("tipo");
        tipo2.appendChild(document.createTextNode("solicitante" ));
        cad2.appendChild(tipo2);
                
        Element desc2 = document.createElement("descripcion");              
        desc2.appendChild(document.createTextNode(tramiteDto.getFirma().getCadenaSolicitante() +""));
        cad2.appendChild(desc2);       
        
    }
    
       
      private void creaFirmas(){
        
        Element firmas = document.createElement("firmas");
        documento.appendChild(firmas);
        
        Element firma = document.createElement("firma");
        firmas.appendChild(firma);
        
        Element tipo = document.createElement("tipo");
        tipo.appendChild(document.createTextNode("impi" ));
        firma.appendChild(tipo);
                
        Element desc = document.createElement("descripcion");              
        desc.appendChild(document.createTextNode(tramiteDto.getFirma().getFirmaImpi()+""));
        firma.appendChild(desc);
        
        
         Element firma2 = document.createElement("firma");
         firmas.appendChild(firma2);
        
        Element tipo2 = document.createElement("tipo");
        tipo2.appendChild(document.createTextNode("solicitante" ));
        firma2.appendChild(tipo2);
                
        Element desc2 = document.createElement("descripcion");              
        desc2.appendChild(document.createTextNode(tramiteDto.getFirma().getFirmaSolicitante() +""));
        firma2.appendChild(desc2);       
        
    }   
       
    
    private void creaAnexos(){
        
         Element anexos = document.createElement("anexos");        
        documento.appendChild(anexos);
        
        for(Anexos anex : this.tramiteDto.getAnexos())
        {
        Element anexo = document.createElement("anexo");                        
        anexos.appendChild(anexo);
      
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(anex.getIdAnexo().toString()+""));
        anexo.appendChild(id);
        
        Element tipo_anexo = document.createElement("tipo_anexo");
        tipo_anexo.appendChild(document.createTextNode(anex.getIdTipoanexo().toString()+""));
        anexo.appendChild(tipo_anexo);
        
        Element descripcion = document.createElement("descripcion");
        descripcion.appendChild(document.createTextNode(anex.getNombreArchivo()+""));
        anexo.appendChild(descripcion);
        
        Element firma = document.createElement("firma");
        firma.appendChild(document.createTextNode(Util.getDigest(anex.getArchivoAnexo())+""));
        anexo.appendChild(firma);
        
        }
        
       
        
        
        
              
 
        
    }
    
    private void creaSolMarca()
    {
        
        Element solMarca = document.createElement("sol_marca");        
       
        
     //   solMarca.appendChild(document.createTextNode("1"));       //*****
        documento.appendChild(solMarca);
        
        
        Element tipoSubtipo = document.createElement("tipo_subtipo");        
        tipoSubtipo.appendChild(document.createTextNode(this.tramiteDto.getSubTipoSolicitudDto().getIdTiposolicitud().toString()+"")); //***************      
        solMarca.appendChild(tipoSubtipo);

        Element solicitantes = document.createElement("solicitantes");                        
        this.creaSolicitante(solicitantes);
        solMarca.appendChild(solicitantes);
                        
        Element firmante = document.createElement("firmante");
        this.creaFirmante(firmante);  /////  pendiente --------------************---
        solMarca.appendChild(firmante);
        
        Element signoDistintivo = document.createElement("signo_distintivo");
        signoDistintivo.appendChild(document.createTextNode(this.tramiteDto.getDescripcionsignodistintivo()==null?"":this.tramiteDto.getDescripcionsignodistintivo()+""));
        solMarca.appendChild(signoDistintivo);
        
        Element fechaPrimerUso = document.createElement("fecha_primer_uso");
        
        fechaPrimerUso.appendChild(document.createTextNode(this.tramiteDto.getFechaprimeruso()==null?"":(new SimpleDateFormat("dd/MM/yyyy")).format(this.tramiteDto.getFechaprimeruso()) +""));
        solMarca.appendChild(fechaPrimerUso);
        
        Element noSeHaUsado = document.createElement("no_se_ha_usado");
        noSeHaUsado.appendChild(document.createTextNode(this.tramiteDto.getNousadofecha()==1?"true":"false"));
        solMarca.appendChild(noSeHaUsado);
        
        Element indMarcaColectiva = document.createElement("ind_marca_colectiva");
        indMarcaColectiva.appendChild(document.createTextNode(tramiteDto.getMarcacolectiva()==null? "": tramiteDto.getMarcacolectiva().toString()+""));
        solMarca.appendChild(indMarcaColectiva);
        
        
        Element tipoClase = document.createElement("tipo_clase");
        tipoClase.appendChild(document.createTextNode(tramiteDto.getTipoClaseSeleccionadaDto().getTipoClase()+""));
        solMarca.appendChild(tipoClase);
        
        Element clase = document.createElement("clase");
        clase.appendChild(document.createTextNode(new Long(tramiteDto.getTipoClaseSeleccionadaDto().getIdClase()).toString()+""));
        solMarca.appendChild(clase);
        
        Element productosOServicios = document.createElement("productos_o_servicios");
        productosOServicios.appendChild(document.createTextNode(tramiteDto.getProductoServicio()==null?"":tramiteDto.getProductoServicio()+""));
        solMarca.appendChild(productosOServicios);
        
        Element metodoSeleccion = document.createElement("metodo_seleccion");
        metodoSeleccion.appendChild(document.createTextNode(tramiteDto.getTipoClaseSeleccionadaDto().getIdFormaseleccionclase()==null?"":  tramiteDto.getTipoClaseSeleccionadaDto().getIdFormaseleccionclase().toString()+""));//********************************************
        solMarca.appendChild(metodoSeleccion);
        
        Element ubicacionEstablecimientos = document.createElement("ubicacion_establecimientos");
        this.creaUbicacionEstablecimientos(ubicacionEstablecimientos);
        solMarca.appendChild(ubicacionEstablecimientos);
                
        //Element detClases = document.createElement("detClases");
        //detClases.appendChild(document.createTextNode(tramiteDto.getProductoServicio()==null?"" :tramiteDto.getProductoServicio()));//************************************************
        //solMarca.appendChild(detClases);
        
        Element prioridades = document.createElement("prioridades");
        this.creaPrioridades(prioridades); // here i go
        solMarca.appendChild(prioridades);
        
        Element leyendasFigurasNoReservables = document.createElement("leyendas_figuras_no_reservables");
        leyendasFigurasNoReservables.appendChild(document.createTextNode(tramiteDto.getLeyendasfigurasreservables()==null?"":tramiteDto.getLeyendasfigurasreservables()+""));
        solMarca.appendChild(leyendasFigurasNoReservables);
        
        
       
        
        
    }
    
    private void creaPrioridades(Element prioridades){
        Element prioridad = document.createElement("prioridad");
        
        Element solicitudInternacional = document.createElement("solicitudInternacional");
        solicitudInternacional.appendChild(document.createTextNode(tramiteDto.getNumerorefextranjero()==null ?"":tramiteDto.getNumerorefextranjero() +""));
        prioridad.appendChild(solicitudInternacional);
        
        Element pais = document.createElement("pais");
        pais.appendChild(document.createTextNode(tramiteDto.getPaisPrioridadDto()==null?"": tramiteDto.getPaisPrioridadDto().getClavePais() ==null?"":tramiteDto.getPaisPrioridadDto().getClavePais()));
        prioridad.appendChild(pais);
        
        Element paisDesc = document.createElement("pais_desc");
        paisDesc.appendChild(document.createTextNode(tramiteDto.getPaisPrioridadDto()==null ?"":tramiteDto.getPaisPrioridadDto().getNombre()==null?"":tramiteDto.getPaisPrioridadDto().getNombre()));
        prioridad.appendChild(paisDesc);
        
        
        Element fechaPresentacion = document.createElement("fecha_presentacion");
        fechaPresentacion.appendChild(document.createTextNode(tramiteDto.getFechapresentacionextranjero()==null?"": new SimpleDateFormat(BundleUtils.getResource("ddMMyyyy")).format(tramiteDto.getFechapresentacionextranjero()) +""));
        prioridad.appendChild(fechaPresentacion);
        
        prioridades.appendChild(prioridad);
        
        
        
        
    
    }
    
    private void creaUbicacionEstablecimientos(Element ubicacionEstablecimientos)
    {       
        
        Element establecimiento = document.createElement("establecimiento");
               
        Element calle = document.createElement("calle");     
        Element colonia = document.createElement("colonia");   
        Element cp = document.createElement("cp");   
        Element municipio = document.createElement("municipio");      
        Element estado = document.createElement("estado"); 
        Element pais = document.createElement("pais");        
        Element paisDesc = document.createElement("pais_desc");        
        
        if(  (this.tramiteDto.getEstablecimientoXTramiteDto() != null) && (this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto() != null)    ){
            calle.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getCalle()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getCalle())); //***************   
            colonia.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getColonia()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getColonia())); //***************      
            cp.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getCodigopostal()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getCodigopostal())); //***************      
        
            if(tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getIdPais()!=null ){
                if(tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getIdPais().longValue()  == Constantes.ID_PAIS_MEXICO.longValue()){
                    municipio.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPoblacion()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPoblacion()+"")); //***************     
                    estado.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getEntidad().getNombre()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getEntidad().getNombre()+"")); //***************      
                }else{                
                    municipio.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPoblacion()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPoblacion()+"")); //***************     
                    estado.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getIdEntidad()==null?"":this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getIdEntidad()+"")); //***************                      
                }
            }
            
            
            pais.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto()==null?"":  this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto().getClavePais() ==null?"" :this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto().getClavePais() )); //***************   
            paisDesc.appendChild(document.createTextNode(this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto()==null?"" : this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto().getNombre()==null?"" :this.tramiteDto.getEstablecimientoXTramiteDto().getDomicilioDto().getPaisDto().getNombre()   )); //***************      
            
            
            
        }else{        
            calle.appendChild(document.createTextNode("")); //***************      
            colonia.appendChild(document.createTextNode("")); //***************      
            cp.appendChild(document.createTextNode("")); //***************      
            municipio.appendChild(document.createTextNode("")); //***************      
            estado.appendChild(document.createTextNode("")); //***************      
            pais.appendChild(document.createTextNode("")); //***************      
            paisDesc.appendChild(document.createTextNode("")); //***************      
            
            
            
        }       
        
        establecimiento.appendChild(calle);        
        establecimiento.appendChild(colonia);
        establecimiento.appendChild(cp);
        establecimiento.appendChild(municipio);
        establecimiento.appendChild(estado);
        establecimiento.appendChild(pais);       
        establecimiento.appendChild(paisDesc);        
        ubicacionEstablecimientos.appendChild(establecimiento);       
        
    }
    
    private void creaFirmante(Element firmante){
        
        
        
        Element tipo = document.createElement("tipo");
        tipo.appendChild(document.createTextNode("firmante"));
        firmante.appendChild(tipo);
        
        Element nombre = document.createElement("nombre");
        nombre.appendChild(document.createTextNode(tramiteDto.getNombreFirmante()));
        firmante.appendChild(nombre);
        
        Element nacionalidad = document.createElement("nacionalidad");
        Element nacionalidad_desc = document.createElement("nacionalidad_desc");
        Element calle = document.createElement("calle");
        Element colonia = document.createElement("colonia");
        Element municipio = document.createElement("municipio");
        Element estado = document.createElement("estado");
        Element pais_desc = document.createElement("pais_desc");
        Element telefono = document.createElement("telefono");
         Element email = document.createElement("email");
         Element codigo_postal = document.createElement("cp");
        //nacionalidad.appendChild(document.createTextNode(this.apoderado.getDescEstado()+ ""));
        if(   (this.tramiteDto.getDomicilioNotificacionDto() !=null) &&  (this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto() !=null)   ){       
            
            
            nacionalidad.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto() ==null?""  :  this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto().getClavePais()==null ?"" :this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto().getClavePais() ));
            
            
            
            nacionalidad_desc.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto()==null ? "" :  this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto().getNacionalidad() ==null ? "" : this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto().getNacionalidad()));
            calle.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getCalle()==null ?"" :this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getCalle()+""));
            colonia.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getColonia() == null?"": this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getColonia()+""));
            municipio.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPoblacion() ==null?"": this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPoblacion()+""));
            estado.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getEntidad()==null?"":  this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getEntidad().getNombre()==null?"": this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getEntidad().getNombre()+""));
            pais_desc.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto()==null?"" : this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto().getNombre() == null ? "":  this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getPaisDto().getNombre()+"" ));
             telefono.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDatosContactoDto() ==null ? ""  :  this.tramiteDto.getDomicilioNotificacionDto().getDatosContactoDto().getTelefono()==null ?"" :this.tramiteDto.getDomicilioNotificacionDto().getDatosContactoDto().getTelefono() +""));
             email.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDatosContactoDto()==null?"" :  this.tramiteDto.getDomicilioNotificacionDto().getDatosContactoDto().getCorreoelectronico()==null?""  :this.tramiteDto.getDomicilioNotificacionDto().getDatosContactoDto().getCorreoelectronico() +""));
             
            codigo_postal.appendChild(document.createTextNode(this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto()==null?"" :  this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getCodigopostal()  ==null?""  :this.tramiteDto.getDomicilioNotificacionDto().getDomicilioDto().getCodigopostal() +"")); 
             
             
        }else{
            nacionalidad.appendChild(document.createTextNode(""));
            nacionalidad_desc.appendChild(document.createTextNode(""));
            calle.appendChild(document.createTextNode(""));
            colonia.appendChild(document.createTextNode(""));
            municipio.appendChild(document.createTextNode(""));
            estado.appendChild(document.createTextNode(""));
            pais_desc.appendChild(document.createTextNode(""));
             telefono.appendChild(document.createTextNode(""));
             email.appendChild(document.createTextNode(""));
             codigo_postal.appendChild(document.createTextNode(""));
        }
        
        firmante.appendChild(nacionalidad);      
        firmante.appendChild(nacionalidad_desc);
        firmante.appendChild(calle);       
        firmante.appendChild(colonia);
        firmante.appendChild(municipio);
        firmante.appendChild(estado);        
        firmante.appendChild(pais_desc);
        firmante.appendChild(telefono);
        firmante.appendChild(email);
        firmante.appendChild(codigo_postal);
        
//        Element tipo_persona = document.createElement("tipo_persona");
//        tipo_persona.appendChild(document.createTextNode(this.apoderado.getTipo_persona()+""));
//        firmante.appendChild(tipo_persona);
        
        
       
       


        
    }
    
    private void creaSolicitante(Element solicitantes){
    
        for(PersonaDto solDto : tramiteDto.getSolicitantesDto()){
           
        
        Element interesado = document.createElement("interesado");        
        
        Element tipo = document.createElement("tipo");        
        tipo.appendChild(document.createTextNode("interesado")); 
        interesado.appendChild(tipo);

        Element nombre = document.createElement("nombre");        
        nombre.appendChild(document.createTextNode(solDto.getNombrecompleto()+"")); 
        interesado.appendChild(nombre);

        Element nacionalidad = document.createElement("nacionalidad");        
        nacionalidad.appendChild(document.createTextNode(solDto.getPaisDto().getClavePais() ==null ? "" :solDto.getPaisDto().getClavePais()+"")); //***************      
        interesado.appendChild(nacionalidad);
        
        Element nacionalidadDesc= document.createElement("nacionalidad_desc");        
        nacionalidadDesc.appendChild(document.createTextNode(solDto.getPaisDto().getNacionalidad() ==null ?"":solDto.getPaisDto().getNacionalidad()  + "")); //***************      
        interesado.appendChild(nacionalidadDesc);
        
        Element calle= document.createElement("calle");        
        calle.appendChild(document.createTextNode(solDto.getDomicilioDto().getCalle()==null? "" : solDto.getDomicilioDto().getCalle()+"")); //***************      
        interesado.appendChild(calle);
        
        Element colonia= document.createElement("colonia");        
        colonia.appendChild(document.createTextNode(solDto.getDomicilioDto().getColonia()==null?"": solDto.getDomicilioDto().getColonia()+"")); //***************      
        interesado.appendChild(colonia);
        
        Element cp= document.createElement("cp");        
        cp.appendChild(document.createTextNode(solDto.getDomicilioDto().getCodigopostal()==null?  "" : solDto.getDomicilioDto().getCodigopostal()+"")); //***************      
        interesado.appendChild(cp);
        
        Element municipio= document.createElement("municipio");        
        municipio.appendChild(document.createTextNode(solDto.getDomicilioDto().getPoblacion()==null?"" :solDto.getDomicilioDto().getPoblacion()+"")); //***************      
        interesado.appendChild(municipio);
        
        Element estado= document.createElement("estado");                
        estado.appendChild(document.createTextNode(solDto.getDomicilioDto().getEntidad().getNombre()==null? "": solDto.getDomicilioDto().getEntidad().getNombre()+"")); //***************      
        interesado.appendChild(estado);
         
        Element pais= document.createElement("pais");        
        pais.appendChild(document.createTextNode(solDto.getPaisDto().getClavePais()==null? "":  solDto.getPaisDto().getClavePais()+"")); //***************      
        interesado.appendChild(pais);

        Element pais_desc= document.createElement("pais_desc");        
        pais_desc.appendChild(document.createTextNode(solDto.getPaisDto().getNombre()==null?"":  solDto.getPaisDto().getNombre()+"")); //***************      
        interesado.appendChild(pais_desc);
        
        Element telefono= document.createElement("telefono");        
        telefono.appendChild(document.createTextNode(solDto.getDatosContactoDto()  ==null?"":  solDto.getDatosContactoDto().getTelefono()==null? "": solDto.getDatosContactoDto().getTelefono() +"")); //***************      
        interesado.appendChild(telefono);
                
        Element email= document.createElement("email");        
        email.appendChild(document.createTextNode(solDto.getDatosContactoDto()  ==null?"":  solDto.getDatosContactoDto().getCorreoelectronico()==null? "": solDto.getDatosContactoDto().getCorreoelectronico() +"")); //***************      
        interesado.appendChild(email);
        
        
//        Element tipo_persona= document.createElement("tipo_persona");        
//        tipo_persona.appendChild(document.createTextNode(solDto.getTipoPersonaDto().getDescripcion()+"")); //***************      
//        interesado.appendChild(tipo_persona);
        
        
        solicitantes.appendChild(interesado);
          
        }
        
    }
            
    
    private void creaMainInfo()
    {       
    
    
        Element area = document.createElement("area");        
        area.appendChild(document.createTextNode(XML_AREA)); //***************      
        documento.appendChild(area);

        Element tipoDocumento = document.createElement("tipo_documento");        
        tipoDocumento.appendChild(document.createTextNode(XML_TIPO_DOCUMENTO));       //*****
        documento.appendChild(tipoDocumento);

        Element tipoSolicitud = document.createElement("tipo_solicitud");        
        tipoSolicitud.appendChild(document.createTextNode(tramiteDto.getSubTipoSolicitudDto().getDescripcion()+""));       //*****
        documento.appendChild(tipoSolicitud);

        Element folio = document.createElement("folio");        
        folio.appendChild(document.createTextNode(tramiteDto.getFirma().getFolio().split("/")[3]+ "" ));       //*****
        documento.appendChild(folio);

        Element expediente = document.createElement("expediente");        
        expediente.appendChild(document.createTextNode(tramiteDto.getFirma().getExpediente().toString() + ""));       //*****
        documento.appendChild(expediente);

        Element fecha_presentacion = document.createElement("fecha_presentacion");        
        fecha_presentacion.appendChild(document.createTextNode(Util.formatearFecha(tramiteDto.getFechaSysdate(), BundleUtils.getResource("ddMMyyyy.hhmmss"))+""));       //*****
        documento.appendChild(fecha_presentacion);
        
        
        
    }
    
    
    
}

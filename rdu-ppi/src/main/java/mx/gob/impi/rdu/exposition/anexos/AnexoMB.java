/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.anexos;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.persistence.model.Tramite;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.service.CatalogosViewServiceImpl;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import mx.gob.impi.rdu.util.Util;
import mx.gob.impi.rdu.util.MimeType;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import javax.faces.application.FacesMessage;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import com.philvarner.clamavj.ClamScan;
import com.philvarner.clamavj.ScanResult;
import java.io.ByteArrayOutputStream;
import java.util.ResourceBundle;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javax.servlet.http.HttpSession;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.exposition.form.CapturaSolicitudForm;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.util.AnexoComparator;
import mx.gob.impi.rdu.util.AnexoComparatorDos;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.FileServicesUtil;
import mx.gob.impi.rdu.util.TipoTramiteEnum;
//ini: Anexos, identificacion de archivos renombrados
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.primefaces.context.RequestContext;
import org.xml.sax.SAXException;
//fin: Anexos, identificacion de archivos renombrados

/**
 *
 * @author Infotec
 */
@ManagedBean(name = "anexoMB")
@ViewScoped
@SuppressWarnings("serial")
public class AnexoMB implements Serializable {

    private Logger logger = Logger.getLogger(this.getClass());
    private List<AnexosViewDto> anexoDto;
    List<Anexos> anexosDB;
    private Tramite tramite;
    private TramitePatente tramitePatente;
    private SolicitudPreparacionDto tramiteSolicitud;
    
    
    private AnexosViewDto anexoSelected;
    private StreamedContent fileDownload;
    private boolean declaracionAnexo;
    private Integer mostrarHerramientas;
    private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.parametros";
    final ResourceBundle bundleParametros = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
    @ManagedProperty(value = "#{catalogosViewService}")
    private CatalogosViewServiceImpl catalogosViewService;
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private boolean esPromocionPatente;
    private CapturaSolicitudForm forma = new CapturaSolicitudForm();
    private Boolean vistaPreviaAnexos = false;

    public void setCatalogosViewService(CatalogosViewServiceImpl catalogosViewService) {
        this.catalogosViewService = catalogosViewService;
    }

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    @PostConstruct
    public void init() {
        if (session.getAttribute("cabecero") instanceof SolicitudPreparacionDto) {
            this.tramiteSolicitud = (SolicitudPreparacionDto) session.getAttribute("cabecero");
            if (tramiteSolicitud != null) {
                setTramiteSolicitud(tramiteSolicitud);
            }
            mostrarHerramientas = (Integer) session.getAttribute("mostraHerramientas");
        } else if (session.getAttribute("cabecero") instanceof TramitePatente) {
            this.tramitePatente = (TramitePatente) session.getAttribute("cabecero");
            if (tramitePatente != null) {
                setTramitePatente(tramitePatente);
            }
            mostrarHerramientas = (Integer) session.getAttribute("mostraHerramientas");
        }
    }

    public AnexosViewDto getAnexoSelected() {
        return anexoSelected;
    }

    public void setAnexoSelected(AnexosViewDto anexoSelected) {
        this.anexoSelected = anexoSelected;
    }

    public List<AnexosViewDto> getAnexoDto() {
        return anexoDto;
    }

    public void setAnexoDto(List<AnexosViewDto> anexoDto) {
        this.anexoDto = anexoDto;
    }

    public List<Anexos> getAnexosDB() {
        return anexosDB;
    }

    public void setAnexosDB(List<Anexos> anexosDB) {
        this.anexosDB = anexosDB;
    }

    public SolicitudPreparacionDto getTramiteSolicitud() {
        return tramiteSolicitud;
    }

    public void setTramiteSolicitud(SolicitudPreparacionDto tramiteSolicitud) {
        this.tramiteSolicitud = tramiteSolicitud;
        this.declaracionAnexo = false;
        AnexosViewDto anexosViewDto = new AnexosViewDto();
        try {
            logger.info("valor de IdTramite:   "+this.tramiteSolicitud.getIdTramite());
            anexosViewDto.setIdSubtiposolicitud(this.tramiteSolicitud.getIdSubtiposolicitud());
            anexosViewDto.setIdTramite(this.tramiteSolicitud.getIdTramite());
            anexosViewDto.setIdTipomarca(0);
            
            // Si trae id de promoción es promoción de patente
            if (tramiteSolicitud.getIdPromocion() != null) {
                esPromocionPatente = true;
            }

            logger.info("**** anexosViewDto:   "+anexosViewDto);
            anexoDto = catalogosViewService.CosultaAnexosDto(anexosViewDto);
            logger.info("**** Tamano de la lista anexoDto:   "+anexoDto.size());
            //Se recuperan los anexos que se encuentran guardaos en BD
            logger.info("**** anexosViewDto.getIdTramite():   "+anexosViewDto.getIdTramite());
            anexosDB =  flujosgralesViewService.obtenerAnexosByTramite(anexosViewDto.getIdTramite());  
            logger.info("**** Tamano de la lista anexoDto:   "+anexosDB.size());          
            
            //Se recorre la lista anexos en BD y los comparar contra los anexos del tramite
            uno : for(Anexos listaAnexoDb : anexosDB){
                int eliminar = 0;
                long idTipoanexo = listaAnexoDb.getIdTipoanexo();
                
                dos : for(AnexosViewDto listaAnexosViewDto : anexoDto){
                    logger.info("******   listaAnexosViewDto.getIdTipoanexo()   "+listaAnexosViewDto.getIdTipoanexo());
                    logger.info("******   idTipoanexo   "+idTipoanexo);
                    if(listaAnexosViewDto.getIdTipoanexo()==idTipoanexo){
                        eliminar=1;
                        break dos;
                    }                    
                }
                
                if(eliminar==0){
                    int anexosEliminados = flujosgralesViewService.deleteAnexosByIds(listaAnexoDb.getIdAnexo());
                    logger.info("Se elimino el anexo con id: "+listaAnexoDb.getIdAnexo()+" nombre archivo "+listaAnexoDb.getNombreArchivo()
                            +" Correspondiente al tramite: "+listaAnexoDb.getIdTramite()+" y tramite patente  "+listaAnexoDb.getIdTramitePatente()+
                            " Registros Eliminados  "+anexosEliminados);
                }
            }

        } catch (Exception e) {
            System.out.println("  ==> ERROR:  " + e.getMessage());
        }
        //session.removeAttribute("cabecero");

    }

    public Tramite getTramite() {
        return tramite;
    }

    public TramitePatente getTramitePatente() {
        return tramitePatente;
    }

    public void setTramitePatente(TramitePatente tramitePatente) {
        this.tramitePatente = tramitePatente;
        this.declaracionAnexo = false;
        int identicos=0;
        AnexosViewDto anexosViewDto = new AnexosViewDto();
        try {
            if (tramitePatente.getPrioridades() != null && tramitePatente.getPrioridades().isEmpty() == false) {
                anexosViewDto.setPrioridad(Constantes.ANEXO_PRIORIDAD_PAT.intValue());
            }

            if (tramitePatente.getFechaDivPrevia() != null) {
                anexosViewDto.setComprobacionFechaDiv(Constantes.ANEXO_DIV_PREVIA_PAT.intValue());
            }

            if (tramitePatente.getPersonasNot() != null && tramitePatente.getPersonasNot().isEmpty() == false) {
                anexosViewDto.setNotificaciones(Constantes.ANEXO_NOTIFICACION_PAT.intValue());
            }

//            if (tramitePatente.getApoderados() != null && tramitePatente.getApoderados().isEmpty() == false) {
//                anexosViewDto.setDoctoAcreditaPersonalidad(Constantes.ANEXO_ACREDITA_PERSONALIDAD.intValue());
//            }
        /// Validar regla Cesión de Derechos
            if (tramitePatente.getSolicitantes() != null && tramitePatente.getSolicitantes().isEmpty() == false) {
                if (tramitePatente.getInventores()!= null && tramitePatente.getInventores().isEmpty() == false) {
                    if (tramitePatente.getSolicitantes().size() > tramitePatente.getInventores().size()){
                        anexosViewDto.setCesionDerechos(Constantes.ANEXO_CESION_DERECHOS.intValue());    
                    }else{                    
                        for (int i=0;i< tramitePatente.getInventores().size();i++)
                        {
                            for (int j=0; j< tramitePatente.getSolicitantes().size();j++)
                            {
                                if(tramitePatente.getInventores().get(i).getNombreConcatenado().toUpperCase().equals(tramitePatente.getSolicitantes().get(j).getNombreConcatenado().toUpperCase()))
                                {            
                                  identicos+=1;
                                  break;
                                }
                            }
                        }               
                        if (tramitePatente.getInventores().size() > identicos){
                            anexosViewDto.setCesionDerechos(Constantes.ANEXO_CESION_DERECHOS.intValue());    
                        } 
                    }
                }
            }


            anexosViewDto.setIdSubtiposolicitud(this.tramitePatente.getSubTipoSol().getIdSubtiposolicitud());
            anexosViewDto.setIdTramitePatente(this.tramitePatente.getIdTramitePatente());
            anexosViewDto.setIdTipomarca(0);
            anexoDto = catalogosViewService.ConsultaAnexosDtoPatentes(anexosViewDto);

//            int anexoApoderados = getAnexoApoderado(anexoDto);
//            if (this.tramitePatente.getApoderados() != null && anexoApoderados == 0 ) {
//                    AnexosViewDto anexoDbApoderado = new AnexosViewDto();
//                    anexoDbApoderado.setIdTipoanexo(Constantes.ANEXO_ACREDITA_PERSONALIDAD);
//                    anexoDbApoderado = catalogosViewService.selectAnexoDynamic(anexoDbApoderado);
//                    
//                    AnexosViewDto anexoView = new AnexosViewDto();                    
//                    anexoView.setIdTipoanexo(Constantes.ANEXO_ACREDITA_PERSONALIDAD);
//                    anexoView.setIdSubtiposolicitud(tramitePatente.getSubTipoSol().getIdSubtiposolicitud().longValue());//(10L); 
//                    anexoView.setOrden(new Short("5"));
//                    anexoView.setDescripcion("DOCUMENTO QUE ACREDITA LA PERSONALIDAD");
//                    anexoView.setVisiblexcarga(new Short("1"));
//                    anexoView.setExtension("pdf");
//                    anexoView.setCargado(false);
//                    
////                    if(anexoDbApoderado!=null){
////                        anexoView.setIdAnexo(anexoDbApoderado.getIdAnexo());
////                        anexoView.setIdTramitePatente(anexoDbApoderado.getIdTramitePatente());
////                        anexoView.setArchivoAnexo(anexoDbApoderado.getArchivoAnexo());
////                        anexoView.setNombreArchivo(anexoDbApoderado.getNombreArchivo());
////                        anexoView.setCargado(true);
////                    }
//                    anexoDto.add(anexoView);
//                }
//            
//            int anexoSolicitante = getAnexoSolicitante(anexoDto);
//            if (this.tramitePatente.getApoderados() != null && anexoSolicitante == 0 ) {
//                    AnexosViewDto anexoDbSolicitante = new AnexosViewDto();
//                    anexoDbSolicitante.setIdTipoanexo(Constantes.ANEXO_CESION_DERECHOS);
//                    anexoDbSolicitante = catalogosViewService.selectAnexoDynamic(anexoDbSolicitante);
//                    
//                    AnexosViewDto anexoView = new AnexosViewDto();                    
//                    anexoView.setIdTipoanexo(Constantes.ANEXO_CESION_DERECHOS);
//                    anexoView.setIdSubtiposolicitud(tramitePatente.getSubTipoSol().getIdSubtiposolicitud().longValue());
//                    anexoView.setOrden(new Short("6"));
//                    anexoView.setDescripcion("CESIÓN DE DERECHOS");
//                    anexoView.setVisiblexcarga(new Short("1"));
//                    anexoView.setExtension("pdf");
//                    anexoView.setCargado(false);
//                    
////                    if(anexoDbSolicitante!=null){
////                        anexoView.setIdAnexo(anexoDbSolicitante.getIdAnexo());
////                        anexoView.setIdTramitePatente(anexoDbSolicitante.getIdTramitePatente());
////                        anexoView.setArchivoAnexo(anexoDbSolicitante.getArchivoAnexo());
////                        anexoView.setNombreArchivo(anexoDbSolicitante.getNombreArchivo());
////                        anexoView.setCargado(true);
////                    }
//                    anexoDto.add(anexoView);
//                }
            
            int numPriorGuardadas = getNumeroAnexosPrioridad(anexoDto);
            if (this.tramitePatente.getPrioridades() != null && numPriorGuardadas < this.tramitePatente.getPrioridades().size()) {
                for (int i = 0; i < (tramitePatente.getPrioridades().size() - numPriorGuardadas); i++) {
                    String nombrePais = tramitePatente.getPrioridades().get(i).getNombrePais();
                    Long idPrioridad = tramitePatente.getPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbPrioridad = new AnexosViewDto();
                    AnexosViewDto anexoDbTraduccion = new AnexosViewDto();
                                        
                    anexoDbPrioridad.setIdPrioridad(idPrioridad);
                    anexoDbPrioridad.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);
                    anexoDbPrioridad = catalogosViewService.selectAnexoDynamic(anexoDbPrioridad);
                    
                    AnexosViewDto anexoView = new AnexosViewDto();                    
                    anexoView.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);
                    anexoView.setIdSubtiposolicitud(tramitePatente.getSubTipoSol().getIdSubtiposolicitud().longValue());
                    anexoView.setOrden(new Short("8"));//8
                    anexoView.setDescripcion("DOCUMENTO DE PRIORIDAD ("+nombrePais+")");
                    anexoView.setVisiblexcarga(new Short("1"));
                    anexoView.setExtension("pdf");
                    anexoView.setCargado(false);
                    anexoView.setIdPrioridad(idPrioridad);
                    
                    if(anexoDbPrioridad!=null){
                        anexoView.setIdAnexo(anexoDbPrioridad.getIdAnexo());
                        anexoView.setIdTramitePatente(anexoDbPrioridad.getIdTramitePatente());
                        anexoView.setArchivoAnexo(anexoDbPrioridad.getArchivoAnexo());
                        anexoView.setNombreArchivo(anexoDbPrioridad.getNombreArchivo());
                        anexoView.setCargado(true);
                    }
                    anexoDto.add(anexoView);
                                        
                    anexoDbTraduccion.setIdPrioridad(idPrioridad);
                    anexoDbTraduccion.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                    anexoDbTraduccion = catalogosViewService.selectAnexoDynamic(anexoDbTraduccion);
                    
                    AnexosViewDto traduccionView = new AnexosViewDto();                    
                    traduccionView.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                    traduccionView.setIdSubtiposolicitud(tramitePatente.getSubTipoSol().getIdSubtiposolicitud().longValue());
                    traduccionView.setOrden(new Short("8"));//8
                    traduccionView.setDescripcion("TRADUCCION DE PRIORIDAD ("+nombrePais+")");
                    traduccionView.setVisiblexcarga(new Short("1"));
                    traduccionView.setExtension("pdf");
                    traduccionView.setCargado(false);
                    traduccionView.setIdPrioridad(idPrioridad);
                    
                    if(anexoDbTraduccion!=null){
                        traduccionView.setIdAnexo(anexoDbTraduccion.getIdAnexo());
                        traduccionView.setIdTramitePatente(anexoDbTraduccion.getIdTramitePatente());
                        traduccionView.setArchivoAnexo(anexoDbTraduccion.getArchivoAnexo());
                        traduccionView.setNombreArchivo(anexoDbTraduccion.getNombreArchivo());
                        traduccionView.setCargado(true);
                    }   
                    anexoDto.add(traduccionView);
                }
            }
//            //Anexo otros documentos
//            int anexoOtros =getAnexoOtros(anexoDto);
//            if (anexoOtros==0){
//                
//                AnexosViewDto anexoDbOtros = new AnexosViewDto();
//                anexoDbOtros.setIdTipoanexo(Constantes.ANEXO_OTROS);
//                anexoDbOtros = catalogosViewService.selectAnexoDynamic(anexoDbOtros);
//                
//                AnexosViewDto otrosView = new AnexosViewDto();                    
//                otrosView.setIdTipoanexo(Constantes.ANEXO_OTROS);
//                otrosView.setIdSubtiposolicitud(tramitePatente.getSubTipoSol().getIdSubtiposolicitud().longValue());
//                otrosView.setOrden(new Short("25"));
//                otrosView.setDescripcion("OTROS");
//                otrosView.setVisiblexcarga(new Short("1"));
//                otrosView.setExtension("pdf");
//                otrosView.setCargado(false);   
////            if(anexoDbOtros!=null && anexoDbOtros.getIdAnexo()!=0) {
////                otrosView.setIdAnexo(anexoDbOtros.getIdAnexo());
////                otrosView.setIdTramitePatente(anexoDbOtros.getIdTramitePatente());
////                otrosView.setArchivoAnexo(anexoDbOtros.getArchivoAnexo());
////                otrosView.setNombreArchivo(anexoDbOtros.getNombreArchivo());
////                otrosView.setCargado(true);
////            }   
//                anexoDto.add(otrosView);
//            }
//            
            //fin anexo otros documentos
            /**
             * Reodenamiento por la anexacion de prioridades
             */
            Collections.sort(anexoDto, new AnexoComparator());  
//          Quitar anexos
            for (int i=0;i< anexoDto.size();i++)
            {
               if (anexoDto.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_DESCRIPCION_PATENTE.longValue())
               {
                   anexoDto.remove(i);
                   i--;
               }else if(anexoDto.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_DIV_PREVIA_PAT.longValue())
               {
                   anexoDto.remove(i);
                   i--;
               }else if(anexoDto.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_REIVINDICACIONES_PATENTE.longValue())
               {
                   anexoDto.remove(i);
                   i--;
               }else if(anexoDto.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_RESUMEN_MEMORIA.longValue())
               {
                   anexoDto.remove(i);
                   i--;
               }else if(anexoDto.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_CARTA_PODER.longValue())
               {
                   anexoDto.remove(i);
                   i--;
               }else if(anexoDto.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_CONSTANCIA_RGP.longValue())
               {
                   anexoDto.remove(i);
                   i--;
               }else if(anexoDto.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_PODER_NOTARIAL.longValue())
               {
                   anexoDto.remove(i);
                   i--;
               }else if(anexoDto.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_ACTA_CONSTITUTIVA.longValue())
               {
                   anexoDto.remove(i);
                   i--;
               }else if(anexoDto.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_OTRO.longValue())
               {
                   anexoDto.remove(i);
                   i--;
               }       
            }
              
            indexacion(anexoDto);
            Collections.sort(anexoDto, new AnexoComparatorDos());
            
             
//            if (anexosViewDto.getDoctoAcreditaPersonalidad() != null && anexosViewDto.getDoctoAcreditaPersonalidad().intValue() == Constantes.ANEXO_ACREDITA_PERSONALIDAD.intValue()) {
//                ponerAnexoObligatorio(anexoDto, Constantes.ANEXO_ACREDITA_PERSONALIDAD);
//            }

            if (anexosViewDto.getComprobacionFechaDiv() != null && anexosViewDto.getComprobacionFechaDiv().intValue() == Constantes.ANEXO_DIV_PREVIA_PAT.intValue()) {
                ponerAnexoObligatorio(anexoDto, Constantes.ANEXO_DIV_PREVIA_PAT);
            }
//            if (anexosViewDto.getNotificaciones() != null && anexosViewDto.getNotificaciones().intValue() == Constantes.ANEXO_NOTIFICACION_PAT.intValue()) {
//                ponerAnexoObligatorio(anexoDto, Constantes.ANEXO_NOTIFICACION_PAT);
//            }
            if (tramitePatente.isMaterial_biologico()) {
                ponerAnexoObligatorio(anexoDto, Constantes.ANEXO_CONST_MAT_BIOL);
            }

            tramitePatente.setLstAnexosDocumento(anexoDto);
        } catch (Exception e) {
            System.out.println("  ==> ERROR:  " + e.getMessage());
        }
       // session.removeAttribute("cabecero");
    }

    public void indexacion(List<AnexosViewDto> anexosDto) {
        int i = 0;
        for (AnexosViewDto anexoViewDto : anexosDto) {
            anexoViewDto.setIndex(i);
            i++;
        }
    }

    public int getNumeroAnexosPrioridad(List<AnexosViewDto> anexoDto) {
        int i = 0;
        for (AnexosViewDto anexoViewDto : anexoDto) {
            if (anexoViewDto.getIdTipoanexo().intValue() == Constantes.ANEXO_PRIORIDAD_PAT.intValue()) {
                i++;
            }
        }
        return i;
    }
    
    public int getAnexoApoderado(List<AnexosViewDto> anexoDto) {
        int i = 0;
        for (AnexosViewDto anexoViewDto : anexoDto) {
            if (anexoViewDto.getIdTipoanexo().intValue() == Constantes.ANEXO_ACREDITA_PERSONALIDAD.intValue()) {
                i++;
            }
        }
        return i;
    }

    public int getAnexoSolicitante(List<AnexosViewDto> anexoDto) {
        int i = 0;
        for (AnexosViewDto anexoViewDto : anexoDto) {
            if (anexoViewDto.getIdTipoanexo().intValue() == Constantes.ANEXO_CESION_DERECHOS.intValue()) {
                i++;
            }
        }
        return i;
    }
    
    public int getAnexoOtros(List<AnexosViewDto> anexoDto) {
        int i = 0;
        for (AnexosViewDto anexoViewDto : anexoDto) {
            if (anexoViewDto.getIdTipoanexo().intValue() == 25) {
                i++;
            }
        }
        return i;
    }
    public void ponerAnexoObligatorio(List<AnexosViewDto> anexos, Long idAnexo) {
        for (AnexosViewDto anexo : anexos) {
            if (anexo.getIdTipoanexo().intValue() == idAnexo.intValue()) {
                anexo.setObligatorio((short) 1);
                break;
            }
        }
    }

//    public StreamedContent getFileDownload() {
//        StreamedContent file = null;
//        InputStream stream = null;
//        Util utilArch = new Util();
//
//        if (null != anexoSelected) {
//            if (null != anexoSelected.getArchivoAnexo()) {
//                stream = new ByteArrayInputStream(anexoSelected.getArchivoAnexo());
//                MimeType mType = utilArch.extractMime(anexoSelected.getExtension());
//                String sMime = mType.getMime();
//                String sNmDownload = anexoSelected.getNombreArchivo();
//                file = new DefaultStreamedContent(stream, sMime, sNmDownload);
//            }
//        }
//        return file;
//    }
    public String getFileDownload() throws IOException {      
        ByteArrayOutputStream outStream=null;
        forma.setRenderedVistaPrevia(true);
        session.removeAttribute("reporteStream");       
        Util utilArch = new Util();
        if (null != anexoSelected) {
            if (null != anexoSelected.getArchivoAnexo()) {
                outStream = new ByteArrayOutputStream();
                outStream.write(anexoSelected.getArchivoAnexo());
                session.setAttribute("reporteStream", outStream);
               vistaPreviaAnexos = true;
            }
        }
        return null;
    }

    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }

    public boolean isDeclaracionAnexo() {
        return declaracionAnexo;
    }

    public void setDeclaracionAnexo(boolean declaracionAnexo) {
        this.declaracionAnexo = declaracionAnexo;
    }

    public void uploadAnexo(FileUploadEvent event) throws IOException, SAXException, TikaException {
        //logger.info("==> DENTRO DE Anexo.uploadAnexo()");
        String sMsgUpload = "";
        Boolean pasaDeteccionVirus = true;

        try {
            UploadedFile file = event.getFile();
            String fileName = file.getFileName();

            //parametros para deteccion de virus en los archivos
            String aplicaDeteccionVirus = bundleParametros.getString("anexo.antivirus.aplica");
            if (aplicaDeteccionVirus.equals("1")) {
                pasaDeteccionVirus = this.deteccionVrus(file);
                if (pasaDeteccionVirus == false) {
                    sMsgUpload = "No se cargo su archivo, contiene virus.";
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", sMsgUpload);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return;
                }
            }
            logger.info(".....paso validacion de virus");
            Util utilArch = new Util();
            String extArchivo = utilArch.extractExtension(fileName);
            extArchivo = extArchivo.toLowerCase();
            if ( extArchivo.equals("gif") ){
                if( file.getContents().length > 100000 ) {
                    sMsgUpload = "Su archivo mide " + file.getContents().length + " bytes, esta permitido hasta 100kb.";
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", sMsgUpload);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return;
                }
//                else if (!FileServicesUtil.checkSizeImage(file.getContents(), 198, 283)) {
//                    sMsgUpload = "El archivo [" + fileName + "] no cumple con la dimensiones adecuadas";
//                    System.out.println(sMsgUpload);
//
//                    this.anexoSelected = null;
//                }
            }

            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.INI" );
            if (null != this.anexoSelected) {

                //ini: Anexos, identificacion de archivos renombrados
                TikaConfig config = TikaConfig.getDefaultConfig();
                Detector detector = config.getDetector();
                TikaInputStream stream = TikaInputStream.get(file.getInputstream());
                Metadata metadata = new Metadata();
                MediaType mediaType = null;
                mediaType = detector.detect(stream, metadata);
                String mimeTypeContenidoArchivo = mediaType.toString();
                //fin: Anexos, identificacion de archivos renombrados

                MimeType mimeTypeNombreArchivo = utilArch.extractMime(extArchivo);
                //String mimeTypeContenidoArchivo=mimeTypeNombreArchivo.getMime();                
                if (mimeTypeContenidoArchivo.equals(mimeTypeNombreArchivo.getMime())) {
                    if (this.anexoSelected.getExtension().equals(extArchivo)) {
                        if (extArchivo.equals("gif")) {
                            this.anexoSelected.setNombreArchivo(fileName);
                            this.anexoSelected.setExtension(extArchivo);
                            this.anexoSelected.setArchivoAnexo(file.getContents());
                            this.anexoSelected.setCargado(Boolean.TRUE);

//                            this.anexoDto.remove(this.anexoSelected);
//                            this.anexoDto.add(this.anexoSelected);

                            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.FIN" );
                            sMsgUpload = "Se cargo el archivo [" + fileName + "] asociado al anexo " + this.anexoSelected.getDescripcion();
                        } else if (!extArchivo.equals("gif") && !FileServicesUtil.validarArchivoProtegido(file.getContents())) {
                            this.anexoSelected.setNombreArchivo(fileName);
                            this.anexoSelected.setExtension(extArchivo);
                            this.anexoSelected.setArchivoAnexo(file.getContents());
                            this.anexoSelected.setCargado(Boolean.TRUE);
//                            if (this.anexoSelected.getIdTipoanexo().longValue()== Constantes.ANEXO_CESION_DERECHOS.longValue())
//                            {
//                                this.anexoSelected.setIdSolicitante(tramitePatente.getIdUsuarioFirmante());
//                            }

//                            this.anexoDto.remove(this.anexoSelected);
//                            this.anexoDto.add(this.anexoSelected);

                            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.FIN" );
                            actualizaTramitePatente();
                            sMsgUpload = "Se cargo el archivo [" + fileName + "] asociado al anexo " + this.anexoSelected.getDescripcion();
                        } else {
                            sMsgUpload = "[" + fileName + "] no se cargó ya que está protegido o dañado";
                        }
                    } else {
                        sMsgUpload = "[" + fileName + "] no se cargó ya que el tipo de archivo no corresponde al anexo " + this.anexoSelected.getDescripcion();
                        //sMsgUpload="No se cargo el archivo ya que el tipo no corresponde  "+fileName;
                    }
                } else {
                    sMsgUpload = "[" + fileName + "] no se cargó ya que el archivo fue renombrado";
                }

            } else {
                sMsgUpload = "No se cargo el archivo. " + sMsgUpload;
            }

            RequestContext context = RequestContext.getCurrentInstance();

            context.execute("singlesubirDialog.hide();");

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo:", sMsgUpload);
            FacesContext.getCurrentInstance().addMessage(null, message);



        } catch (IOException ex) {
            logger.fatal("Informacion de Error: -- " + ex + " -- ubicado en: " + ex.getLocalizedMessage());
        }



    }

    public boolean deteccionVrus(UploadedFile prmFile) throws IOException {
        boolean resulDet = true;
        try {
            String hostDeteccionVirus = bundleParametros.getString("anexo.antivirus.host");
            int ptoDeteccionVirus = Integer.parseInt(bundleParametros.getString("anexo.antivirus.pruerto"));

            ClamScan cs = new ClamScan();
            cs.setHost(hostDeteccionVirus);
            cs.setPort(ptoDeteccionVirus);
            cs.setTimeout(100);
            ScanResult sr = cs.scan(prmFile.getContents());
            //se intenta dos veces ya que a la primera siempre falla
            sr = cs.scan(prmFile.getContents());
            resulDet = sr.getStatus() == sr.getStatus().PASSED ? true : false;
            logger.info(".....verificacion de virus en anexo>>> result:" + sr.getResult() + " estatus: " + sr.getStatus());
            /*
             System.out.println("Resultado = {" + sr.getResult() + "}");
             System.out.println("Resultado = {" + sr.getStatus() + "}");
             System.out.println("Resultado = {" + sr.getSignature() + "}");
             */
        } catch (FileNotFoundException ex) {
            logger.fatal("Informacion de Error: -- " + ex + " -- ubicado en: " + ex.getLocalizedMessage());
        }

        return resulDet;
    }

    public void actualizaTramite() {
        Anexos nvoAnexo;
        List<Anexos> nvoAnexos = new ArrayList();
        List<AnexosViewDto> nvosAnexosDto = new ArrayList();
        if (null != this.anexoDto && this.tramiteSolicitud != null) {

            for (Iterator iter = this.anexoDto.iterator(); iter.hasNext();) {
                AnexosViewDto oAnxoViewDto = (AnexosViewDto) iter.next();
                nvoAnexo = new Anexos();
                if (oAnxoViewDto.getCargado()) {
                    if (oAnxoViewDto.getIdTramite() > 0) {
                        nvoAnexo.setIdTramite(oAnxoViewDto.getIdTramite());
                    }
                    if (oAnxoViewDto.getIdAnexo() > 0) {
                        nvoAnexo.setIdAnexo(oAnxoViewDto.getIdAnexo());
                    }

                    nvoAnexo.setIdTipoanexo(oAnxoViewDto.getIdTipoanexo());
                    nvoAnexo.setArchivoAnexo(oAnxoViewDto.getArchivoAnexo());
                    nvoAnexo.setExtension(oAnxoViewDto.getExtension());
                    nvoAnexo.setNombreArchivo(oAnxoViewDto.getNombreArchivo());

                    nvoAnexos.add(nvoAnexo);

                    oAnxoViewDto.setIdTramite(this.tramiteSolicitud.getIdTramite());

                    if(esPromocionPatente){
                        oAnxoViewDto.setIdTramitePromocionMarcas(this.tramiteSolicitud.getIdTramite());
                    }
                    nvosAnexosDto.add(oAnxoViewDto);
                }
                oAnxoViewDto.setIdTipoTramite(this.tramiteSolicitud.getIdTipoTramite());
            }
            /*
             if (nvoAnexos.size() > 0) {
             this.tramite.setAnexos(nvoAnexos);
             }
             */
            if (nvosAnexosDto.size() > 0) {
                int res = this.flujosgralesViewService.insertarAnexosDtos(nvosAnexosDto);
                //logger.info("slio de anexosDTO");
            }


        }
    }

    public void actualizaTramitePatente() {
       
//        if (session.getAttribute("cabecero") instanceof TramitePatente) {
//            this.tramitePatente = (TramitePatente) session.getAttribute("cabecero");
//        }   
        Anexos nvoAnexo;
        List<Anexos> nvoAnexos = new ArrayList();
        List<AnexosViewDto> nvosAnexosDto = new ArrayList();
        if (null != this.anexoDto && this.tramitePatente != null) {
            
            for (Iterator iter = this.anexoDto.iterator(); iter.hasNext();) {
                AnexosViewDto oAnxoViewDto = (AnexosViewDto) iter.next();
                nvoAnexo = new Anexos();
                if (oAnxoViewDto.getCargado()) {
                    if (oAnxoViewDto.getIdTramitePatente() > 0) {
                        nvoAnexo.setIdTramitePatente(oAnxoViewDto.getIdTramitePatente());
                    }
                    if (oAnxoViewDto.getIdAnexo() > 0) {
                        nvoAnexo.setIdAnexo(oAnxoViewDto.getIdAnexo());
                    }

                    nvoAnexo.setIdTipoanexo(oAnxoViewDto.getIdTipoanexo());
                    nvoAnexo.setArchivoAnexo(oAnxoViewDto.getArchivoAnexo());
                    nvoAnexo.setExtension(oAnxoViewDto.getExtension());
                    nvoAnexo.setNombreArchivo(oAnxoViewDto.getNombreArchivo());

                    nvoAnexos.add(nvoAnexo);

                    oAnxoViewDto.setIdTramitePatente(this.tramitePatente.getIdTramitePatente());
                    nvosAnexosDto.add(oAnxoViewDto);
                }

            }
            if (nvoAnexos.size() > 0) {
                this.tramitePatente.setAnexos(nvoAnexos);
            }
            if (nvosAnexosDto.size() > 0) {
                int res = this.flujosgralesViewService.insertarAnexosDtosPatentes(anexoDto);
                //logger.info("slio de anexosDTO");
                setTramitePatente(tramitePatente);
            }


        }
    }

    public void test() throws IOException {
        logger.info("  ==>  INFO TEST");
        FacesContext.getCurrentInstance().getExternalContext().redirect("redireccionar.faces");
    }

    public Integer getMostrarHerramientas() {
        return mostrarHerramientas;
    }

    public void setMostrarHerramientas(Integer mostrarHerramientas) {
        this.mostrarHerramientas = mostrarHerramientas;
    }
       
    public Boolean getVistaPreviaAnexos() {
        return vistaPreviaAnexos;
    }

    public void setVistaPreviaAnexos(Boolean vistaPreviaAnexos) {
        this.vistaPreviaAnexos = vistaPreviaAnexos;
    }
    
}

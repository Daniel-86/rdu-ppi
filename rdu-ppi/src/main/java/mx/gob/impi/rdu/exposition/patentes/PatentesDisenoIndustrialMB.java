/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.patentes;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.philvarner.clamavj.ClamScan;
import com.philvarner.clamavj.ScanResult;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.impi.rdu.dataModel.PersonaDM;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.dto.ApoderadoDto;
import mx.gob.impi.rdu.dto.DomicilioDto;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.dto.ReporteDisenoIndustrialDto;
import mx.gob.impi.rdu.dto.ReportesDto;
import mx.gob.impi.rdu.dto.hojaDescuento;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.exposition.flujosGenerales.helper.IncludeDomicilio;
import mx.gob.impi.rdu.exposition.flujosGenerales.helper.IncludeFirmante;
import mx.gob.impi.rdu.exposition.flujosGenerales.reporte.ConvertPDFToXML;
import mx.gob.impi.rdu.exposition.flujosGenerales.reporte.GenerarReporte;
import mx.gob.impi.rdu.exposition.flujosGenerales.reporte.GenerarReporte_html;
import mx.gob.impi.rdu.exposition.form.CapturaSolicitudForm;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.CatAnexos;
import mx.gob.impi.rdu.persistence.model.CatCapitulos;
import mx.gob.impi.rdu.persistence.model.CatSubtiposolicitud;
import mx.gob.impi.rdu.persistence.model.CatTipoSolicitante;
import mx.gob.impi.rdu.persistence.model.CatTipopersona;
import mx.gob.impi.rdu.persistence.model.CatTiposolicitud;
import mx.gob.impi.rdu.persistence.model.CodigosPostales;
import mx.gob.impi.rdu.persistence.model.Datoscontacto;
import mx.gob.impi.rdu.persistence.model.Domicilio;
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;
import mx.gob.impi.rdu.persistence.model.Pais;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.Prioridad;
import mx.gob.impi.rdu.persistence.model.Reivindicacion;
import mx.gob.impi.rdu.persistence.model.TipoPublicacionPct;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.service.CatalogosViewServiceImpl;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.service.PatentesViewServiceImpl;
import mx.gob.impi.rdu.util.*;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPct;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPctMU;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import org.apache.log4j.Logger;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.BeanUtils;
import org.xml.sax.SAXException;

@ManagedBean(name = "patentesDisIndMB")
@ViewScoped
/**
 *
 * @author adriana
 */
public class PatentesDisenoIndustrialMB implements Serializable {
    
    private int posicionActualwe;
    private int posicionActualPrueba;
    private boolean banderaEditar;
    private int numElemto;
    private Logger log = Logger.getLogger(this.getClass());
    private CatTiposolicitud tipoSol = new CatTiposolicitud();
    private List<CatTiposolicitud> listTiposSol = Collections.emptyList();
    private CatSubtiposolicitud subtipo = new CatSubtiposolicitud();
    private List<CatSubtiposolicitud> listSubtiposSol = Collections.emptyList();
    private boolean inventor;
    private boolean verPCT;
    private boolean habilitarPct;
    private Persona solicitanteDatos = new Persona();
    private String nombreSolicitante = "";
    private String nombreInventor = "";
    private Prioridad replica;
    private Persona inventorDatos = new Persona();
    private Persona apoderadoDatos = new Persona();
    private boolean editarSolicitante;
    private boolean editarInventor;
    private boolean editarPrioridad;
    //Todos los solicitantes son personas fisicas.
    private boolean personasFisicasSol = true;
    private Boolean editarApoderado = false;
    private Boolean editarPersonaNot = false;
    private boolean editarAnexoMT=false;
    private boolean editarAnexoApoderado=false;
    private Integer agregarPrioridad;
    private boolean verPrioridad;
    private boolean verPreguntaPrioridad;
    private boolean habilitarResumen;
    private boolean habilitarBusquedaPct;
    private int idProvPrioridad;
    private Persona promovente = new Persona();
    IncludeFirmante apoderadoInclude = new IncludeFirmante();
    IncludeFirmante personaNotInclude = new IncludeFirmante();
    private Persona persoNotDatos = new Persona();
    private Pais nacionalidadSolCombo = new Pais();
    private Pais nacionalidadInvCombo = new Pais();
    private List<Persona> listaSolicitantes = new ArrayList<Persona>();
    private List<Persona> listaInventores = new ArrayList<Persona>();
    private String flujoDescripcion;
    private String mensajeDescripcionVistas;
    private AnexosViewDto anexoArcDivPre;
    private AnexosViewDto anexoDescripcion;
    private AnexosViewDto anexoReivindicacion;
    private AnexosViewDto anexoResumen;
    private HashMap<Long, String> mapaSubtipoSolicitud = new HashMap<Long, String>();
    private HashMap<String, String> mapaEntidadesFederativas = new HashMap<String, String>();
    private boolean revisionFinalizaCaptura = false;
    private boolean protestaFinalizaCaptura = false;
    private boolean completaFinalizaCaptura = false;
    private boolean fechaValida = false;
    private boolean docDivPrevia;
    /*
     * private List<Persona> listaApoderados = new ArrayList<Persona>(); private
     * List<Persona> listaPersonasNot = new ArrayList<Persona>();
     *
     */
    private Persona selectedApoderado = new Persona();
    private Persona selectedPersonaNot = new Persona();
    private Persona selectedSolicitante = new Persona();
    private PersonaDM apoderadoModel;
    private PersonaDM solicitanteModel;
    private List<CatTipopersona> tiposPersona = Collections.emptyList();
    private List<CatTipopersona> tiposPersonaApoderados = Collections.emptyList();
    private List<CatTipopersona> tiposPersonaInventor = Collections.emptyList();
    private List<Pais> nacionalidades = Collections.emptyList();
    private List<CatTipoSolicitante> tiposSolicitantes = Collections.emptyList();
    IncludeDomicilio domicilioSolicitante = new IncludeDomicilio();
    IncludeDomicilio domicilioInventor = new IncludeDomicilio();
    IncludeDomicilio domicilioContacto = new IncludeDomicilio();
    private Prioridad datosPrioridad = new Prioridad();
    private CatAnexos datosAnexos = new CatAnexos();
    private CatAnexos datosAnexosApoderado = new CatAnexos();

    private Integer agregarprioridad;
    private List<Prioridad> listaPrioridades = new ArrayList<Prioridad>();
    private List<Pais> paisesPrioridad = Collections.emptyList();
    private List<Pais> paises = Collections.emptyList();
    private List<EntidadFederativa> entidadesFederativas = Collections.emptyList();
    private List<String> errores;
    private List<CatAnexos> anexosMemoria= Collections.emptyList();
    private List<CatAnexos> anexosApoderado= Collections.emptyList();
    private boolean verErrores;
    private long idTramite;
    private boolean esinventor;
    private boolean aplicaDescuento;
    private boolean marcarSolicitanteAutomatico;
    private boolean marcarApoderadoAutomatico;
    private boolean copiarInventorSolicitante;
    private boolean materiaBiologico;
    private boolean pubAnticipada;
    public boolean verFaseInt;
    private boolean editarImagenDibujo = false;
    private boolean editarReivindicacion = false;
    private Integer posicionImagenDibujo = null;
    private Integer posicionReivindicacion = null;
    private List<CatCapitulos> listCapitulos;
    private TipoPublicacionPct tipoPublicacionPct = new TipoPublicacionPct();
    private List<TipoPublicacionPct> tiposPublicacionPct = new ArrayList<TipoPublicacionPct>();
    private TramitePatente cabeceroTramite = new TramitePatente();
    private String expDivisional2;
    public List<Solicitud> solicitudesSgapatTable = Collections.emptyList();
    public Solicitud solSeleccionada;
    private List<AnexosViewDto> anexoDto;
    private List<AnexosViewDto> lstAnexosDto= new ArrayList<AnexosViewDto>();
    private List<AnexosViewDto> lstAnexosApoderado= new ArrayList<AnexosViewDto>();

    private TramitePatente tramitePat = new TramitePatente();
    private UploadedFile file;
    private String reivindicacion = new String();
    private String part1NumeroSolPct;
    private String part2NumeroSolPct;
    private String part1NumPubPct;
    private String part2NumPubPct;
    private DatosSolicitudPct datosSolicitudPct = new DatosSolicitudPct();
   // private DatosSolicitudPctMU datosPCTMU = new DatosSolicitudPctMU();
    private List<DatosSolicitudPctMU> lstDatosPCTMU = new ArrayList<DatosSolicitudPctMU>();
    @ManagedProperty(value = "#{catalogosViewService}")
    private CatalogosViewServiceImpl catalogosViewService;
    @ManagedProperty(value = "#{patentesViewService}")
    private PatentesViewServiceImpl patentesViewService;
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.CapturaSolPatentes";
    private final ResourceBundle bundleParametrosPatentes = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    //Se agrego el campo para no mostrar la vista previa al durante la captura del tramite
    private Boolean vistaPrevia = false;
    private Boolean reivindicacionFlag = true;
//    private boolean aceptarTerminos;
    private CapturaSolicitudForm forma = new CapturaSolicitudForm();
    private int indicadorFecha;
    private ImagenDibujo dibujoSeleccionado;
    private StreamedContent imagenMostrar;
    private String msgMostrarFchPrioridad;
    private String disabledEditarPri = "false";
    private String disabledEliminar = "false";
    private String firmante;
    private String sello;
    private int contador=0;
    private int nPaginas=0;
    private int nPaginasTrad=0;
    private long idtramite; 
    private int idAnexoMemoria;
    
    private int nPagMemyReiv=0;
    private int nPagMemyReivTrad=0;
    //////////////////
    private AnexosViewDto anexoSelected = new AnexosViewDto();
    private AnexosViewDto anexoSelectedTrad = new AnexosViewDto();
    private AnexosViewDto anexoSelectedApoderado = new AnexosViewDto();
    private String otroDocumento;

    public AnexosViewDto anexosViewDto = new AnexosViewDto();
    private StreamedContent fileDownload;
    private StreamedContent fileDownloadTrad;
    private StreamedContent fileDownloadApoderado;
    private boolean editarAnexo;
    private Logger logger = Logger.getLogger(this.getClass());
    private static final String BUNDLEPARAMETROS = "mx.gob.impi.rdu.i18n.parametros";
    final ResourceBundle bundleParametros = ResourceBundle.getBundle(BUNDLEPARAMETROS);
    //-----------------
    private AnexosViewDto anexoSeleccionado = new AnexosViewDto();
    private List<AnexosViewDto> lstAnexos= new ArrayList<AnexosViewDto>();
    private StreamedContent fileDownloadAnexo;
    
    private AccordionPanel panel;
    private String acordionIndex="false";
    private String txtEditor;
    private String txtEditor2;
    //////////////////

    
    //IJZA Se agrega variable que modifique el radio boton de la cobertura en caso de ser Estado de la Tecnica solo pintara internacional ya que
    // para ese solo aplicara la cobertura Internacional 19/02/2015
    private boolean modificaRadioBotonCoverNacioInter;
    private boolean modificaRadioBotonCoverInter;

    private boolean habilitaDeshabilitaComboTipPer;


    


    @PostConstruct
    public void init() {
        
        habilitaDeshabilitaComboTipPer = true;
        
    
        
        if (log.isInfoEnabled()) {
            log.info("<------------Entrando al metodo PatentesDisenoIndustrialMB.init()---------------------->");
        }

        llenarTiposPublicacionPct();
        paises = catalogosViewService.consultarPaises(new Pais(Constantes.AREA_MARCAS, true));
        nacionalidades = catalogosViewService.consultarNacionalidades(new Pais(Constantes.AREA_MARCAS, true));
        entidadesFederativas = catalogosViewService.ConsultarEntidadesFederativas();
//        paisesPrioridad = catalogosViewService.consultarPaises(new Pais(Constantes.AREA_MARCAS, true, true));
        paisesPrioridad = catalogosViewService.consultarPaises(new Pais(Constantes.AREA_MARCAS,true,true));
        anexosMemoria = catalogosViewService.consultarAnexos();
        anexosApoderado= catalogosViewService.consultarAnexosApoderado();

        Map<String, Object> listaIdiomas = catalogosViewService.consultarIdiomas();

        forma.setIdiomas(listaIdiomas);
        forma.setIdiomasReivindicacion(listaIdiomas);

//        for (int i = 0; i < paisesPrioridad.size(); i++) {
//            Pais elem = (Pais) paisesPrioridad.get(i);
//            if (elem.getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
//                paisesPrioridad.remove(i);
//                break;
//            }
//        }
        idProvPrioridad = 0;
        CatTipoSolicitante criterio = new CatTipoSolicitante();
        /*
         * initDomicilio(1); initDatosSolicitante(); //initDomicilio(2);
         * paisCombo.setNombre(Constantes.NOMBRE_PAIS);
         *
         * domicilio.setIdPais(paisCombo.getIdPais());
         * domicilio.setNombrePais(paisCombo.getNombre());
         */

        //------------------------------Init domicilio firmante
        domicilioSolicitante.initDomicilio(paises, entidadesFederativas, flujosgralesViewService);
        domicilioInventor.initDomicilio(paises, entidadesFederativas, flujosgralesViewService);
        domicilioContacto.initDomicilio(paises, entidadesFederativas, flujosgralesViewService);
        /*Se vuelven a cargar los catálogos después de elegir un código postal. Debido que se pierde los valores al recargar la pagina*/
        if (!FacesContext.getCurrentInstance().isPostback()) {
            this.paises = catalogosViewService.consultarPaises(new Pais(Constantes.AREA_MARCAS, true));
            this.nacionalidades = catalogosViewService.consultarNacionalidades(new Pais(Constantes.AREA_MARCAS, true));
        }

        try {

            criterio.setIndActivo(Constantes.EXISTE);
            tiposSolicitantes = catalogosViewService.consultarTiposSolicitanteXCriterio(criterio);
            tiposPersona = catalogosViewService.consultarTiposPersona(null);
            tiposPersonaApoderados = catalogosViewService.consultarTiposPersona(null);
            CatTipopersona tipoPersonaCrit = new CatTipopersona();
            tipoPersonaCrit.setIndActivo(new Short("1"));
            tipoPersonaCrit.setIdTipopersona(new Short("1"));
            tiposPersonaInventor = catalogosViewService.consultarTiposPersona(tipoPersonaCrit);
//            criterio.setIndActivo(Constantes.EXISTE);
            copiarInventorSolicitante = true;
            //inicializar solicitante
            inicializarPersona(1);
            //inicializar Inventor
            inicializarPersona(2);
          
            //Obtener los tipos de solicitudes
            CatTiposolicitud criterioTipoSol = new CatTiposolicitud();
            CatSubtiposolicitud criterioSubTipoSol = new CatSubtiposolicitud();
            criterioSubTipoSol.setIndActivo(new Short("1"));
            criterioTipoSol.setIndActivo(new Short("1"));
            criterioTipoSol.setIdArea(Constantes.AREA_SIT.intValue());
            listTiposSol = catalogosViewService.getTiposSolicitudesByCriterio(criterioTipoSol);
            listTiposSol=ordenaTipoSolicitud(listTiposSol);
            tramitePat.setTipoSol(listTiposSol.get(0));
            criterioSubTipoSol.setIdTiposolicitud(tramitePat.getTipoSol().getIdTiposolicitud());
            listSubtiposSol = catalogosViewService.getSubtiposSolByCriterio(criterioSubTipoSol);
            llenarMapaSubTipoSolicitudes();
            llenarMapaEntidadesFederativas();
            verFaseInt = false;
            verPreguntaPrioridad = true;
            forma.setValorTipoDesc(Constantes.INIT);
            forma.setRenderedOtroDoc(false);
            forma.setEsDesabilitadoPDF(true);
             aceptarDescripcionOk();
            forma.setCapPCT("1");

            //ini gip
            SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
            promovente = obtenerPromovente(obtSession);
            if (null != obtSession) {
                if (obtSession.getIdMenu() == 2 && obtSession.getIdTramite() > 0) {
                    idTramite = obtSession.getIdTramite();
                    patentesViewService.recuperarTramite(this);
                    flujosgralesViewService.configfechaPresDiv(this);
                    
                    recuperaCobertura(tramitePat.getIdSubtipoSolicitud().intValue());
                    
                     
                    if (tramitePat.getApoderados().isEmpty()){
                        this.lstAnexosApoderado.clear();
                        borrarPdfanexosApoderado();
                    }                                 
                    if(tramitePat.getFechaDivulgacion() != null){
                        
                       docDivPrevia = true; 
                       
                        Anexos anexoDivPrevia = new Anexos();
                        anexoDivPrevia.setIdTipoanexo(Constantes.ANEXO_DIV_PREVIA_PAT);
                        anexoDivPrevia.setIdTramitePatente(idTramite);

                        anexoDivPrevia = flujosgralesViewService.obtenerAnexosDynamic(anexoDivPrevia);
                        if (anexoDivPrevia != null && anexoDivPrevia.getNombreArchivo() != null) {
                            forma.setRutaArcDivPre(anexoDivPrevia.getNombreArchivo());
                           // forma.setTipoDescripcion(Constantes.SECOND.toString());
                        }
                        
                    }
                    actualizaAnexosMemoria();
                    actualizaAnexosApoderado();
                    anexosTramitePatente();
//                    anexosViewDto.setIdSubtiposolicitud(this.tramitePat.getSubTipoSol().getIdSubtiposolicitud());
//                    anexosViewDto.setIdTramitePatente(this.tramitePat.getIdTramitePatente());
//                    anexosViewDto.setIdTipomarca(0);
//                    lstAnexosDto = catalogosViewService.ConsultaAnexosDtoPatentes(anexosViewDto);
//                    for(int j=0;j<lstAnexosDto.size();j++)
//                    {
//                       if (lstAnexosDto.get(j).getTxtAnexo()==null && lstAnexosDto.get(j).getNombreArchivo()==null){
//                           lstAnexosDto.remove(j);
//                           j--;
//                        }  
//                    }
//                    if (tramitePat.getPreambulo() != null) {
//                        forma.setTipoDescripcion(Constantes.INIT.toString());
//                        forma.setRenderedCapturatexto(false);
//                    } else {
//                        Anexos anexoDescripcion = new Anexos();
//                        anexoDescripcion.setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);
//                        anexoDescripcion.setIdTramitePatente(idTramite);
//                        tramitePat.setPreambulo("Agregue aqui la descripción");
//                        anexoDescripcion = flujosgralesViewService.obtenerAnexosDynamic(anexoDescripcion);
//                        if (anexoDescripcion != null && anexoDescripcion.getNombreArchivo() != null) {
//                            if(anexoDescripcion.getNombreArchivo()==null){
//                                forma.setDescText("1");                                
//                            }else{
//                                forma.setRutaDesc(anexoDescripcion.getNombreArchivo());
//                                forma.setTipoDescripcion(Constantes.SECOND.toString());
//                                forma.setRenderedCargarPdf(false);
//                                forma.setDescPdf(Constantes.SECOND.toString());
//                                forma.setDescText("-1");
//                            }   
//                            AnexosViewDto anexoDescripcionBD = new AnexosViewDto();
//                            anexoDescripcionBD.setIdTipoanexo(anexoDescripcion.getIdTipoanexo());
//                            //anexoDescripcionBD.setIdTramite(anexoDescripcion.getIdTramite());
//                            anexoDescripcionBD.setArchivoAnexo(anexoDescripcion.getArchivoAnexo());
//                            anexoDescripcionBD.setExtension(anexoDescripcion.getExtension());
//                            anexoDescripcionBD.setNombreArchivo(anexoDescripcion.getNombreArchivo());
//                            anexoDescripcionBD.setIdTramitePatente(anexoDescripcion.getIdTramitePatente());
//                            
//                            forma.setAnexoDescripcionMe(anexoDescripcionBD);
//                        }
//                    }
                    
                    if(forma.getResuText() != null) {
                        forma.setRenderedResuText(true);
                        forma.setResuText(Constantes.INIT.toString());                        
                    }else{
                        Anexos anexoResumen = new Anexos();
                        anexoResumen.setIdTipoanexo(Constantes.ANEXO_RESUMEN_MEMORIA);
                        anexoResumen.setIdTramitePatente(idTramite);

                        anexoResumen = flujosgralesViewService.obtenerAnexosDynamic(anexoResumen);
                        if (anexoResumen != null && anexoResumen.getNombreArchivo() != null) {
                            forma.setRutaResum(anexoResumen.getNombreArchivo());
                            forma.setRenderedResuPdf(false);
                            forma.setResuPdf(Constantes.SECOND.toString());
                            
                            AnexosViewDto anexoResumenBD = new AnexosViewDto();
                            anexoResumenBD.setIdTipoanexo(anexoResumen.getIdTipoanexo());
//                            anexoResumenBD.setIdTramite(anexoResumen.getIdTramite());
                            anexoResumenBD.setArchivoAnexo(anexoResumen.getArchivoAnexo());
                            anexoResumenBD.setExtension(anexoResumen.getExtension());
                            anexoResumenBD.setNombreArchivo(anexoResumen.getNombreArchivo());
                            anexoResumenBD.setIdTramitePatente(anexoResumen.getIdTramitePatente());
                            
                            forma.setAnexoResumen(anexoResumenBD);
                        }
                    }

                    session.removeAttribute("imagenes");
                    session.setAttribute("imagenes", tramitePat.getImagenes());

                    habilitarOpcionesSubTipoSol(0);
                    habilitarPCT(0);
                    apoderadoModel = new PersonaDM(tramitePat.getApoderados());
                    solicitanteModel = new PersonaDM(tramitePat.getSolicitantes());
                    apoderadoInclude = convertirPersonaToIncludeFirmante(promovente);
                    if (obtSession.getCopiarTramite() != null) {
                        idTramite = 0;
                        obtSession.setCopiarTramite(null);
                    }

                } else {
                    inicializaTramite();
                    flujosgralesViewService.configfechaPresDiv(this);
                    if(obtSession.getIdMenu()==17){
                        tramitePat.setTipoSol(listTiposSol.get(1));
                    }else if(obtSession.getIdMenu()==18){
                        tramitePat.setTipoSol(listTiposSol.get(0));
                    }else if(obtSession.getIdMenu()==20){
                        tramitePat.setTipoSol(listTiposSol.get(0));
                    }else if(obtSession.getIdMenu()==21){
                        tramitePat.setTipoSol(listTiposSol.get(1));
                    }
                    if (tramitePat.getTipoSol() != null && tramitePat.getTipoSol().getIdTiposolicitud() != null
                            &&  (tramitePat.getTipoSol().getIdTiposolicitud().longValue() == Constantes.TIPO_SOL_MODELO_UTILIDAD
                            ||   tramitePat.getTipoSol().getIdTiposolicitud().longValue() == Constantes.TIPO_SOL_PATENTE) ){
                        tramitePat.setInvencion("");
                        habilitarOpcionesSubTipoSol(0);
                    }else{
                        habilitarOpcionesSubTipoSol(0);
                    }
                        
                    

                    tramitePat.setApoderados(new ArrayList<Persona>());
                    tramitePat.setPersonasNot(new ArrayList<Persona>());
                    tramitePat.setSolicitantes(new ArrayList<Persona>());
                    apoderadoModel = new PersonaDM(tramitePat.getApoderados());
                    solicitanteModel = new PersonaDM(new ArrayList<Persona>());
                    apoderadoInclude.init(paises, entidadesFederativas, flujosgralesViewService, tiposPersonaApoderados, nacionalidades);
                    apoderadoInclude.setTipoPersona(new CatTipopersona());
                    apoderadoInclude.getTipoPersona().setIdTipopersona(new Short("1"));
//                    if (tramitePat.getPreambulo() !=null && !tramitePat.getPreambulo().equals("")){
//                      tramitePat.setPreambulo("Agregue aqui la descripción");
//                    } 
                    tramitePat.setIdUsuarioFirmante(Long.parseLong(apoderadoInclude.getApoderado().getId_promovente() + ""));
                    //agregarPersona(apoderadoInclude, tramitePat.getApoderados(), false);
                    apoderadoInclude = convertirPersonaToIncludeFirmante(promovente);
                /////////////////////////////////
                    if (apoderadoInclude.getIncludeDomicilio()!=null){
                        this.setDomicilioContacto(apoderadoInclude.getIncludeDomicilio());
                    }
                               /////////////////////////////////
                    agregarApoderado();
                    //BeanUtils.copyProperties(tramitePat.getApoderados().get(0), promovente);
                }
                apoderadoInclude = limpiarIncludeFirmante();
                personaNotInclude = limpiarIncludeFirmante();
                //se comenta para mostrar la direccion y domicilio del apoderado principal
                
                apoderadoInclude.getIncludeDomicilio().getEntidadFederativaCombo().setIdEntidadFederativa(0);
                personaNotInclude.getIncludeDomicilio().getEntidadFederativaCombo().setIdEntidadFederativa(0);
                apoderadoInclude.getNacionalidad().setIdPais(Constantes.ID_PAIS_MEXICO);
                personaNotInclude.getNacionalidad().setIdPais(Constantes.ID_PAIS_MEXICO);

                for (Persona persona : tramitePat.getApoderados()) {
                    if (persona.getPrincipal() != null) {
                        selectedApoderado = persona;
                        break;
                    }
                }
//                for (Persona persona : tramitePat.getPersonasNot()) {
//                    if (persona.getPrincipal() != null) {
//                        selectedApoderado = persona;
//                        break;
//                    }
//                }

                for (Persona persona : tramitePat.getSolicitantes()) {
                    if (persona.getPrincipal() != null) {
                        selectedSolicitante = persona;
                        break;
                    }
                }

            }

           crearCabeceroTramite();
//           if (this.panel!=null){
//            this.panel.setActiveIndex("-1");
//           }     
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<CatTiposolicitud> ordenaTipoSolicitud(List<CatTiposolicitud> listTipoSol )
    {
        CatTiposolicitud anexoAux;
         for (int i=0;i<listTipoSol.size()-1;i++)
         {            
            for(int j=i+1;j<listTipoSol.size();j++)
            {
                if (listTipoSol.get(j).getIdTiposolicitud()< listTipoSol.get(i).getIdTiposolicitud())
                {
                    anexoAux=listTipoSol.get(i);
                    listTipoSol.set(i,listTipoSol.get(j));
                    listTipoSol.set(j, anexoAux);
                }    
                
            }
        }
        return listTipoSol;
    }
    
    public void llenarMapaSubTipoSolicitudes() {
        if (listSubtiposSol != null && listSubtiposSol.size() > 0) {
            for (CatSubtiposolicitud subtiposolicitud : listSubtiposSol) {
                mapaSubtipoSolicitud.put(subtiposolicitud.getIdSubtiposolicitud(),
                        subtiposolicitud.getDescripcion());
            }
        }

    }

    public void llenarMapaEntidadesFederativas() {
        if (entidadesFederativas != null && entidadesFederativas.size() > 0) {
            for (EntidadFederativa entidad : entidadesFederativas) {
                mapaEntidadesFederativas.put(entidad.getIdEntidadFederativa().toString(),
                        entidad.getNombre());
            }
        }

    }

//Falta afinar esta parte para cuando se viene de doc. en preparacion
    public void llenarTiposPublicacionPct() {
        tiposPublicacionPct.add(new TipoPublicacionPct(1, "A1"));
        tiposPublicacionPct.add(new TipoPublicacionPct(2, "A2"));
        tiposPublicacionPct.add(new TipoPublicacionPct(3, "A3"));
        tiposPublicacionPct.add(new TipoPublicacionPct(4, "A4"));
        tiposPublicacionPct.add(new TipoPublicacionPct(5, "A5"));
        tiposPublicacionPct.add(new TipoPublicacionPct(6, "A6"));
        tiposPublicacionPct.add(new TipoPublicacionPct(7, "A7"));
        tiposPublicacionPct.add(new TipoPublicacionPct(8, "A8"));
        tiposPublicacionPct.add(new TipoPublicacionPct(9, "A9"));
    }

    public Persona obtenerPromovente(SesionRDU obtSession) {
        Persona prom = new Persona();
        PromoventeDto promoventeDto = null;
        if (obtSession != null) {
            promoventeDto = obtSession.getPromovente();
            Domicilio domicilio = new Domicilio();
            Datoscontacto datosContacto = new Datoscontacto();
            prom.setDomicilioObj(domicilio);
            prom.setDatosContacto(datosContacto);
            domicilio.setPais(new Pais());
            prom.setTipoPersona(new CatTipopersona());
            prom.setIdSolicitante((long) promoventeDto.getId_promovente());
            prom.setIdUsuarioFirmante((long) promoventeDto.getId_promovente());
            prom.setNombrecompleto(promoventeDto.getNombre().toUpperCase() + " " + promoventeDto.getApaterno().toUpperCase() + " " + promoventeDto.getAmaterno().toUpperCase());
            prom.setNombrecompletotmp(promoventeDto.getNombre());
            prom.setPrimerApellido(promoventeDto.getApaterno());
            prom.setSegundoApellido(promoventeDto.getAmaterno());
            domicilio.setIdPais(Constantes.ID_PAIS);
            domicilio.getPais().setIdPais(Constantes.ID_PAIS);
            domicilio.getPais().setNombre(Constantes.NOMBRE_PAIS);
            domicilio.getPais().setNacionalidad(Constantes.NOMBRE_NACIONALIDAD);
            prom.setNacionalidad(domicilio.getPais());
            domicilio.setIdEntidad(promoventeDto.getId_estado() + "");
            domicilio.getEntidad().setIdEntidadFederativa(promoventeDto.getId_estado());
            domicilio.getEntidad().setNombre(promoventeDto.getDescEstado().toUpperCase());
            prom.getTipoPersona().setIdTipopersona((short) promoventeDto.getTipo_persona());
            domicilio.setCodigopostal(promoventeDto.getCodigo_postal().toString());
            domicilio.setPoblacion(promoventeDto.getDescMunicipio().toUpperCase());
            domicilio.setColonia(promoventeDto.getColonia().toUpperCase());
            domicilio.setCalle(promoventeDto.getCalle_numero().toUpperCase()
                    + (promoventeDto.getNumero_exterior() != null
                    ? promoventeDto.getNumero_exterior().length() > 0
                    ? " " + promoventeDto.getNumero_exterior() : "" : "")
                    + (promoventeDto.getNumero_interior() != null
                    ? promoventeDto.getNumero_interior().length() > 0
                    ? " " + promoventeDto.getNumero_interior() : "" : ""));
            datosContacto.setTelefono(promoventeDto.getTelefono().toUpperCase());
            datosContacto.setCorreoelectronico(promoventeDto.getEmail());
            datosContacto.setFax(promoventeDto.getFax());
        }
        return prom;
    }

    public void agregarApoderado() {
   // if (tramitePat.getApoderados().size() == 0){  
        if (apoderadoInclude.getApoderado().getNombreConcatenado()==null){
            apoderadoInclude.getApoderado().setNombreConcatenado(apoderadoInclude.getApoderado().getNombre() + " " + apoderadoInclude.getApoderado().getApaterno() + " " + apoderadoInclude.getApoderado().getAmaterno());            
        }
        if (this.getPromovente().getNombreConcatenado().toUpperCase().equals(apoderadoInclude.getApoderado().getNombreConcatenado().toUpperCase())){
       // if (tramitePat.getApoderados().get(0).getNombreConcatenado().toUpperCase().equals(this.getPromovente().getNombreConcatenado().toUpperCase())){
            if (agregarPersona(apoderadoInclude, tramitePat.getApoderados(), editarApoderado)) {
                apoderadoInclude = limpiarIncludeFirmante();
                reglaSolicitanteApoderado();
                if (tramitePat.getApoderados().size() == 1 && !marcarApoderadoAutomatico) {
                    tramitePat.getApoderados().get(0).setPrincipal(new Short("1"));
                    selectedApoderado = tramitePat.getApoderados().get(0);
                } else if (tramitePat.getApoderados().size() >= 2 && editarApoderado == false && !marcarApoderadoAutomatico) {
                    //selectedApoderado = new Persona();
                    log.info("PRINCIPAL   "+tramitePat.getApoderados().get(0).getPrincipal());
                    log.info("PRINCIPAL   "+tramitePat.getApoderados().get(0).getIdUsuarioFirmante());
//                    tramitePat.getApoderados().get(0).setPrincipal(null);
                }   
                editarApoderado = false;
            }           
        }else{if (agregarPersonaSinDomicilio(apoderadoInclude, tramitePat.getApoderados(), editarApoderado)) {
                apoderadoInclude = limpiarIncludeFirmante();
                reglaSolicitanteApoderado();
                if (tramitePat.getApoderados().size() == 1 && !marcarApoderadoAutomatico) {
                    tramitePat.getApoderados().get(0).setPrincipal(new Short("1"));
                    selectedApoderado = tramitePat.getApoderados().get(0);
                } else if (tramitePat.getApoderados().size() >= 2 && editarApoderado == false && !marcarApoderadoAutomatico) {
                    //selectedApoderado = new Persona();
                    log.info("PRINCIPAL   "+tramitePat.getApoderados().get(0).getPrincipal());
                    log.info("PRINCIPAL   "+tramitePat.getApoderados().get(0).getIdUsuarioFirmante());
//                tramitePat.getApoderados().get(0).setPrincipal(null);
            }
                editarApoderado = false;
            }
        }   
            
            
//    }else{  if (agregarPersonaSinDomicilio(apoderadoInclude, tramitePat.getApoderados(), editarApoderado)) {
//                apoderadoInclude = limpiarIncludeFirmante();
//                reglaSolicitanteApoderado();
//                if (tramitePat.getApoderados().size() == 1 && !marcarApoderadoAutomatico) {
//                    tramitePat.getApoderados().get(0).setPrincipal(new Short("1"));
//                    selectedApoderado = tramitePat.getApoderados().get(0);
//                } else if (tramitePat.getApoderados().size() >= 2 && editarApoderado == false && !marcarApoderadoAutomatico) {
//                    //selectedApoderado = new Persona();
//                    log.info("PRINCIPAL   "+tramitePat.getApoderados().get(0).getPrincipal());
//                    log.info("PRINCIPAL   "+tramitePat.getApoderados().get(0).getIdUsuarioFirmante());
////                tramitePat.getApoderados().get(0).setPrincipal(null);
//            }
//                editarApoderado = false;
//            }
//        }    
    }
    

    public void cancelarApoderado() {
        apoderadoInclude = limpiarIncludeFirmante();
        editarApoderado = false;
    }

    public void eliminarApoderado(Persona pPersona) {
        editarApoderado = false;
        tramitePat.getApoderados().remove(pPersona);
        apoderadoInclude = limpiarIncludeFirmante();
        if (pPersona.equals(selectedApoderado)) {
            selectedApoderado = null;
            marcarApoderadoAutomatico = false;
        }
        reglaSolicitanteApoderado();
        if (tramitePat.getApoderados().size() == 1 && !marcarApoderadoAutomatico) {
            tramitePat.getApoderados().get(0).setPrincipal(new Short("1"));
            selectedApoderado = tramitePat.getApoderados().get(0);
        }

    }

    public void editarApoderado(Persona pPersona) {
        editarApoderado = true;
//        apoderadoInclude = convertirPersonaToIncludeFirmante(pPersona);

        apoderadoInclude = convertirPersonaNotToIncludeFirmante(pPersona);
    }

    public void agregarPersonaNot() {
        if (agregarPersonaSinDomicilio(personaNotInclude, tramitePat.getPersonasNot(), editarPersonaNot)) {
            personaNotInclude = limpiarIncludeFirmante();
            editarPersonaNot = false;
        }
    }

    public void cancelarPersonaNot() {
        personaNotInclude = limpiarIncludeFirmante();
        this.editarPersonaNot = false;

    }

  //IJZA se comentan metodos eliminarPersonaNot y editarPersonaNot 23/02/2015

//    public void eliminarPersonaNot(Persona pPersona) {
//        editarPersonaNot = false;
//        tramitePat.getPersonasNot().remove(pPersona);
//        personaNotInclude = limpiarIncludeFirmante();
//    }
//
//    public void editarPersonaNot(Persona pPersona) {
//        editarPersonaNot = true;
//        personaNotInclude = convertirPersonaNotToIncludeFirmante(pPersona);
//    }

    public void onRowSelect_Solicitantes(SelectEvent event) {

        habilitarPrincipal(event, listaSolicitantes);
        marcarSolicitanteAutomatico = false;
    }

    public void onRowSelect_Apoderados(SelectEvent event) {
        habilitarPrincipal(event, tramitePat.getApoderados());//24925199 24925199 17688329

        marcarApoderadoAutomatico = true;
    }

    public void habilitarPrincipal(SelectEvent event, List<Persona> personas) {
        for (Persona persona : personas) {
            persona.setPrincipal(null);
        }
        ((Persona) event.getObject()).setPrincipal(new Short("1"));
    }

     public IncludeFirmante limpiarIncludeFirmante() {
        IncludeFirmante includeFirmante = new IncludeFirmante();
        includeFirmante.init(paises, entidadesFederativas, flujosgralesViewService, tiposPersonaApoderados, nacionalidades);
        includeFirmante.setApoderado(new ApoderadoDto());
        includeFirmante.getTipoPersona().setIdTipopersona(new Short("1"));
        includeFirmante.getIncludeDomicilio().setDatosContacto(new Datoscontacto());
        includeFirmante.getIncludeDomicilio().setDomicilio(new DomicilioDto());
        return includeFirmante;
    }

    public IncludeFirmante convertirPersonaToIncludeFirmante(Persona pPersona) {
        IncludeFirmante includeFirmante = new IncludeFirmante();
        includeFirmante.setIncludeDomicilio(new IncludeDomicilio());
        includeFirmante.setApoderado(new ApoderadoDto());
        DomicilioDto domicilio = new DomicilioDto();
        includeFirmante.setTipoPersona(pPersona.getTipoPersona());
        includeFirmante.init(paises, entidadesFederativas, flujosgralesViewService, tiposPersonaApoderados, nacionalidades);
        BeanUtils.copyProperties(pPersona.getDomicilioObj(), domicilio);
        includeFirmante.getIncludeDomicilio().setDomicilio(domicilio);
        includeFirmante.getIncludeDomicilio().getDomicilio().setIdPais(pPersona.getDomicilioObj().getIdPais());
        includeFirmante.getIncludeDomicilio().setPaisCombo(pPersona.getDomicilioObj().getPais());
        includeFirmante.getIncludeDomicilio().setEntidadFederativaCombo(pPersona.getDomicilioObj().getEntidad());
        includeFirmante.getIncludeDomicilio().getDomicilio().setNombreEntidad(pPersona.getDomicilioObj().getIdEntidad());
        includeFirmante.getIncludeDomicilio().setDatosContacto(pPersona.getDatosContacto());
        includeFirmante.getApoderado().setRgp(pPersona.getRgp());
//        includeFirmante.getApoderado().setRfc(pPersona.getRfc());
       
       // includeFirmante.getApoderado().setNombre(pPersona.getNombrecompleto());
        if (pPersona.getTipoPersona().getIdTipopersona()==1 && promovente.getPrimerApellido()!=null)
        {
            String[] palabras = pPersona.getNombrecompletotmp().split(" ");
            String nombreFormato = new String();
            for (int i=0;i<palabras.length;i++)
            {
                nombreFormato= nombreFormato + palabras[i].substring(0,1).toUpperCase()+ palabras[i].substring(1,palabras[i].length()).toLowerCase() + " ";
            }
            
            includeFirmante.getApoderado().setNombre(nombreFormato.trim());
            includeFirmante.getApoderado().setNombreTmp(nombreFormato.trim());
            includeFirmante.getApoderado().setNombreConcatenado(nombreFormato.trim() + " " + pPersona.getPrimerApellido() + " " + pPersona.getSegundoApellido());
            this.promovente.setNombreConcatenado(nombreFormato.trim() + " " + pPersona.getPrimerApellido() + " " + pPersona.getSegundoApellido());
        }else
        {
         includeFirmante.getApoderado().setNombre(pPersona.getNombrecompleto());
        }
        includeFirmante.getApoderado().setIdPersona(pPersona.getIdSolicitante());
//        includeFirmante.getNacionalidad().setIdPais(pPersona.getIdNacionalidad());
        includeFirmante.setPrincipal(pPersona.getPrincipal());
//        if (pPersona.getNacionalidad() != null) {
//            includeFirmante.setNacionalidad(pPersona.getNacionalidad());
//        }

        //includeFirmante.getApoderado().setPrimerApellido(pPersona.getPrimerApellido());
        //includeFirmante.getApoderado().setSegundoApellido(pPersona.getSegundoApellido());

        includeFirmante.getIncludeDomicilio().getEntidadFederativaCombo().setIdEntidadFederativa(Integer.parseInt(domicilio.getIdEntidad()));
        return includeFirmante;
    }

    public IncludeFirmante convertirPersonaNotToIncludeFirmante(Persona pPersona) {
        IncludeFirmante includeFirmante = new IncludeFirmante();
        includeFirmante.init(paises, entidadesFederativas, flujosgralesViewService, tiposPersonaApoderados, nacionalidades);
        includeFirmante.setApoderado(new ApoderadoDto());
        includeFirmante.setTipoPersona(pPersona.getTipoPersona());
        includeFirmante.getApoderado().setNombre(pPersona.getNombrecompleto());
        includeFirmante.getApoderado().setIdPersona(pPersona.getIdSolicitante());
        includeFirmante.getApoderado().setPrimerApellido(pPersona.getPrimerApellido());
        includeFirmante.getApoderado().setSegundoApellido(pPersona.getSegundoApellido());
        includeFirmante.getApoderado().setEsMandatorio(pPersona.getEsMandatorio());
        return includeFirmante;
    }

    public boolean agregarPersona(IncludeFirmante includeFirmante, List<Persona> personas, Boolean editar) {
        boolean insertar = true;
        String msgAviso = "";
        IncludeFirmante includePromovente;
        if (includeFirmante.getTipoPersona().getIdTipopersona().intValue() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mmensaje.error.tipo.persona") + "||";
        }

        Persona personaaux = new Persona();

        String nombreCompleto = "";
        nombreCompleto += includeFirmante.getApoderado().getNombre().trim();
        if (includeFirmante.getApoderado().getPrimerApellido() != null && !includeFirmante.getApoderado().getPrimerApellido().equals("")) {
            nombreCompleto += " " + includeFirmante.getApoderado().getPrimerApellido().trim();
        }
        if (includeFirmante.getApoderado().getSegundoApellido() != null && !includeFirmante.getApoderado().getSegundoApellido().equals("")) {
            nombreCompleto += " " + includeFirmante.getApoderado().getSegundoApellido().trim();
        }

//        personaaux.setNombrecompletotmp(includeFirmante.getApoderado().getNombre().trim());
        personaaux.setNombrecompletotmp(nombreCompleto);
        personaaux.setNombrecompletotmp(Util.reemplazaAcentos(personaaux.getNombrecompletotmp()));

        if (!editar) {
            Collections.sort(personas, new ComparatorPersona());


            if (Collections.binarySearch(personas, personaaux, new ComparatorPersona()) >= 0) {
                msgAviso += "NOMBRE REPETIDO" + "||";
                insertar = false;
            }
        }

        includeFirmante.getApoderado().setNombreConcatenado(nombreCompleto);

        if (includeFirmante.getApoderado().getNombre() == null || includeFirmante.getApoderado().getNombre().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.nombre") + "||";
        } else {
            if (Pattern.matches(Constantes.pattern_nombre_personas, includeFirmante.getApoderado().getNombre()) == false) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.nombre.apoderado.caracteres") + "||";
                insertar = false;
            }
//            if (includeFirmante.getApoderado().getRgp()!= null && includeFirmante.getApoderado().getRgp().replaceAll("\\s", "").length() > 0) {
            if (includeFirmante.getApoderado().getRgp() != null) {
                includeFirmante.getApoderado().setRgp(includeFirmante.getApoderado().getRgp().replaceAll("\\s", ""));
                if (includeFirmante.getApoderado().getRgp().length() > 0 && Pattern.matches(Constantes.pattern_apoderado_rgp, includeFirmante.getApoderado().getRgp()) == false) {
                    msgAviso += bundleParametrosPatentes.getString("mensaje.error.rgp.apoderado.caracteres") + "||";
                    insertar = false;
                }
            }
        }
        
        if (!promovente.getNombrecompleto().equals(nombreCompleto) && (includeFirmante.getApoderado().getPrimerApellido() == null 
                || includeFirmante.getApoderado().getPrimerApellido().length() == 0)) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.apoderado.primerApellido") + "||";
        }


        if (includeFirmante.getIncludeDomicilio().paisCombo.getIdPais() != null && includeFirmante.getIncludeDomicilio().paisCombo.getIdPais().intValue() == Constantes.ID_PAIS.intValue()) {
            if (includeFirmante.getIncludeDomicilio().domicilio.getColonia() == null || includeFirmante.getIncludeDomicilio().domicilio.getColonia().length() == 0) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.colonia") + "||";
            }
        } else {
//            if (includeFirmante.getIncludeDomicilio().domicilio.getNombreEntidad() == null || includeFirmante.getIncludeDomicilio().domicilio.getNombreEntidad().length() == 0) {
            if (includeFirmante.getIncludeDomicilio().domicilio.getIdEntidad() == null
                    || includeFirmante.getIncludeDomicilio().domicilio.getIdEntidad().equals(String.valueOf(Constantes.FIRST))) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.entidad") + "||";
            }
        }

        if (includeFirmante.getIncludeDomicilio().domicilio.getCodigopostal() == null || includeFirmante.getIncludeDomicilio().domicilio.getCodigopostal().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.cp") + "||";
        }

        if (includeFirmante.getIncludeDomicilio().domicilio.getCalle() == null || includeFirmante.getIncludeDomicilio().domicilio.getCalle().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.calle") + "||";
        } else {
            if (Pattern.matches(Constantes.pattern_direccionesPatente, includeFirmante.getIncludeDomicilio().domicilio.getCalle()) == false) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.caracteres") + "||";
            }
            if (includeFirmante.getIncludeDomicilio().domicilio.getCalle().length() > Integer.parseInt(bundleParametrosPatentes.getString("domicilio.CalleNumero.maxlength"))) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.max") + "||";
            }
        }

        /*Se verifica que exista un nombre esto que al inicial una solicitud existe la posibilidad de que cargue la 
         informacion del usuario de session de la cual no contiene la calle e imprime el error de la num. Ext*/

        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        Persona promoventeTmp = obtenerPromovente(obtSession); 
        
        if (promoventeTmp.getNombrecompleto() != null && promoventeTmp.getNombrecompleto().length() > 0
                && !promoventeTmp.getNombrecompleto().equals(includeFirmante.getApoderado().getNombreConcatenado().toUpperCase())
                && (includeFirmante.getIncludeDomicilio().domicilio.getNumExt() == null || includeFirmante.getIncludeDomicilio().domicilio.getNumExt().length() == 0)) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.numExt") + "||";
        }

        if (promoventeTmp.getNombrecompleto().toLowerCase().equals(includeFirmante.getApoderado().getNombreConcatenado().toLowerCase())
                && (listaSolicitantes.size() == Constantes.INIT
                || solicitanteModel.getRowCount() == Constantes.INIT || forma.getSolicitanteModelTmp().getRowCount() == Constantes.INIT)) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.solicitanteUnico") + "||";
        }

        if (includeFirmante.getIncludeDomicilio().datosContacto.getCorreoelectronico() != null
                ? (includeFirmante.getIncludeDomicilio().datosContacto.getCorreoelectronico().length() == 0) : false) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.correo") + "||";
        }
        if (includeFirmante.getIncludeDomicilio().datosContacto.getCorreoelectronico() != null ? includeFirmante.getIncludeDomicilio().datosContacto.getCorreoelectronico().length() > 0 ? !Pattern.matches(Constantes.PATTERN_EMAIL, includeFirmante.getIncludeDomicilio().datosContacto.getCorreoelectronico()) : false : false) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.validar.correo") + "||";
        }
        if (insertar) {
            Persona persona = new Persona();
            Domicilio domicilio = new Domicilio();
            Datoscontacto datosContacto = new Datoscontacto();
            persona.setIdSolicitante(includeFirmante.getApoderado().getIdPersona());
            persona.setNombrecompleto(includeFirmante.getApoderado().getNombre());
            persona.setNombrecompletotmp(Util.reemplazaAcentos(includeFirmante.getApoderado().getNombre()));
            persona.setTipoPersona(new CatTipopersona());
            persona.getTipoPersona().setIdTipopersona(new Short("1"));
            persona.setIdNacionalidad(includeFirmante.getIncludeDomicilio().getPaisCombo().getIdPais());
            persona.setIdTipopersona(persona.getTipoPersona().getIdTipopersona());
            persona.setIdNacionalidad(includeFirmante.getNacionalidad().getIdPais());

            persona.setPrimerApellido(includeFirmante.getApoderado().getPrimerApellido());
            persona.setSegundoApellido(includeFirmante.getApoderado().getSegundoApellido());
            persona.setEsMandatorio(includeFirmante.getApoderado().getEsMandatorio());

            persona.setNombreConcatenado(nombreCompleto);

//            if (includeFirmante.getNacionalidad() != null) {
//                persona.setNacionalidad(includeFirmante.getNacionalidad());
//                this.overrideNombreNacionalidad(persona.getNacionalidad());
//            }

            //Se agrego validacion: ya que es un campo no obligatorio con lo tal puede llegar vacio
            if (includeFirmante.getApoderado().getRgp() != null) {
                persona.setRgp(includeFirmante.getApoderado().getRgp().replaceAll("[\n\r\\s]", ""));
            }

//            persona.setRfc(includeFirmante.getApoderado().getRfc());
            persona.setPrincipal(includeFirmante.getPrincipal());
            domicilio.setIdPais(includeFirmante.getIncludeDomicilio().getPaisCombo().getIdPais());
            domicilio.setPais(includeFirmante.getIncludeDomicilio().getPaisCombo());
            domicilio.setCodigopostal(includeFirmante.getIncludeDomicilio().getDomicilio().getCodigopostal());

            domicilio.setNumExt(includeFirmante.getIncludeDomicilio().getDomicilio().getNumExt());
            domicilio.setNumInt(includeFirmante.getIncludeDomicilio().getDomicilio().getNumInt());

            if (domicilio.getIdPais() != null
                    && domicilio.getIdPais().longValue() == Constantes.ID_PAIS_MEXICO) {
                domicilio.setIdEntidad(includeFirmante.getIncludeDomicilio().getEntidadFederativaCombo().getIdEntidadFederativa().toString());
                includeFirmante.getIncludeDomicilio().overrideNombreEntidadFederativa();
                domicilio.setEntidad(includeFirmante.getIncludeDomicilio().getEntidadFederativaCombo());
            } else if (includeFirmante.getIncludeDomicilio().getPaisApoderado() != null
                    && includeFirmante.getIncludeDomicilio().getPaisApoderado().getIdPais() > 0) {
                Pais paisApoderado = new Pais();
                paisApoderado.setIdPais(Constantes.ID_PAIS);
                paisApoderado.setNombre(Constantes.NOMBRE_PAIS);

                domicilio.setIdPais(Constantes.ID_PAIS);
                domicilio.setPais(paisApoderado);
                domicilio.setIdEntidad(includeFirmante.getIncludeDomicilio().getEntidadFederativaCombo().getIdEntidadFederativa().toString());
                domicilio.getEntidad().setNombre(mapaEntidadesFederativas.get(
                        includeFirmante.getIncludeDomicilio().getEntidadFederativaCombo().getIdEntidadFederativa().toString()));

            } else {
                domicilio.setIdEntidad(includeFirmante.getIncludeDomicilio().getDomicilio().getNombreEntidad());
                domicilio.getEntidad().setNombre(includeFirmante.getIncludeDomicilio().getDomicilio().getNombreEntidad());
            }
            domicilio.setPoblacion(includeFirmante.getIncludeDomicilio().getDomicilio().getPoblacion());
            domicilio.setColonia(includeFirmante.getIncludeDomicilio().getDomicilio().getColonia());
            domicilio.setCalle(includeFirmante.getIncludeDomicilio().getDomicilio().getCalle());
            datosContacto.setCorreoelectronico(includeFirmante.getIncludeDomicilio().getDatosContacto().getCorreoelectronico());
            datosContacto.setTelefono(includeFirmante.getIncludeDomicilio().getDatosContacto().getTelefono());
            datosContacto.setFax(includeFirmante.getIncludeDomicilio().getDatosContacto().getFax());
            datosContacto.setTelefonoExt(includeFirmante.getIncludeDomicilio().getDatosContacto().getTelefonoExt());
            persona.setDomicilioObj(domicilio);
            persona.setDatosContacto(datosContacto);


            if (editar) {
                Integer index = buscarPersonaById(persona, personas);
                if (index != null) {
                    personas.remove(index.intValue());
                    //+
                    Collections.sort(personas, new ComparatorPersona());

                    if (Collections.binarySearch(personas, personaaux, new ComparatorPersona()) >= 0) {
                        msgAviso += "NOMBRE REPETIDO" + "||";
                        insertar = false;
                    } else {
                        //+
                        personas.add(index.intValue(), persona);
                    }
                } else {
                    persona.setIdSolicitante(new Integer(persona.hashCode()).longValue());
                    personas.add(persona);
                }
            } else {
                if (includeFirmante.getApoderado().getId_promovente() == tramitePat.getIdUsuarioFirmante().intValue()) {
                    persona.setIdUsuarioFirmante(tramitePat.getIdUsuarioFirmante());
                }else{
                    if (personas.size()==0){
                       if(promoventeTmp.getNombrecompleto().equals(tramitePat.getSolicitantes().get(0).getNombreConcatenado().toUpperCase())){
                            if (promoventeTmp.getTipoPersona().getIdTipopersona()==1 && promoventeTmp.getPrimerApellido()!=null)
                            {   ////////////agregado
                                includePromovente= convertirPersonaToIncludeFirmante(promoventeTmp);
                                if (includePromovente.getIncludeDomicilio()!=null){
                                    this.setDomicilioContacto(includePromovente.getIncludeDomicilio());
                                }
                                ///////
                                String[] palabras = promoventeTmp.getNombrecompletotmp().split(" ");
                                String nombreFormato = new String();
                                for (int i=0;i<palabras.length;i++)
                                {
                                    nombreFormato= nombreFormato + palabras[i].substring(0,1).toUpperCase()+ palabras[i].substring(1,palabras[i].length()).toLowerCase() + " ";
                                }
                                promoventeTmp.setNombreConcatenado(nombreFormato.trim() + " " + promoventeTmp.getPrimerApellido() + " " + promoventeTmp.getSegundoApellido());
                                promoventeTmp.setNombrecompleto(nombreFormato.trim());
                                promoventeTmp.setNombrecompletotmp(nombreFormato.trim());  
                            }else
                                {
                                    promoventeTmp.setNombreConcatenado(promoventeTmp.getNombrecompleto());
                                }
                            promoventeTmp.setPrincipal(new Short("1"));
                            personas.add(promoventeTmp);
                       } 
                    }
                }
                    
                persona.setIdSolicitante(new Integer(persona.hashCode()).longValue());
                personas.add(persona);
            }
        }
        if (!insertar) {
            //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgAviso, msgAviso);
            //FacesContext.getCurrentInstance().addMessage(null, message);
            convierteListaErrores(msgAviso);
            verErrores = true;
        } else {
            verErrores = false;
        }

        return insertar;
    }
public String agregarDomicilioIni(IncludeDomicilio includeDomicilio) {
        boolean insertar = true;
        int errores=0;
        String msgAviso = "";
        if (includeDomicilio.paisCombo.getIdPais() != null && includeDomicilio.paisCombo.getIdPais().intValue() == Constantes.ID_PAIS.intValue()) {
            if (includeDomicilio.domicilio.getColonia() == null || includeDomicilio.domicilio.getColonia().length() == 0) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.colonia")+"(Domicilio de contacto)" + "||";
                errores++;
            }
        } else {
               if (includeDomicilio.domicilio.getIdEntidad() == null
                    || includeDomicilio.domicilio.getIdEntidad().equals(String.valueOf(Constantes.FIRST))) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.entidad") +"(Domicilio de contacto)"+ "||";
                errores++;
            }
        }

        if (includeDomicilio.domicilio.getCodigopostal() == null || includeDomicilio.domicilio.getCodigopostal().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.cp")+"(Domicilio de contacto)" + "||";
            errores++;
        }
        
        if (includeDomicilio.domicilio.getCalle() == null || includeDomicilio.domicilio.getCalle().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.calle") +"(Domicilio de contacto)" + "||";
            errores++;
        } else {
            if (Pattern.matches(Constantes.pattern_direccionesPatente, includeDomicilio.domicilio.getCalle()) == false) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.caracteres") +"(Domicilio de contacto)"+ "||";
                errores++;
            }
            if (includeDomicilio.domicilio.getCalle().length() > Integer.parseInt(bundleParametrosPatentes.getString("domicilio.CalleNumero.maxlength"))) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.max")+"(Domicilio de contacto)" + "||";
                errores++;
            }
        }
        
        if(errores==3)
        {
            msgAviso=bundleParametrosPatentes.getString("mensaje.error.apoderado.domicilio")  + "||";
        }   
        if (insertar) {
            Domicilio domicilio = new Domicilio();
            Datoscontacto datosContacto = new Datoscontacto();
            EntidadFederativa entidadContacto= new EntidadFederativa();
            domicilio.setIdPais(includeDomicilio.getPaisCombo().getIdPais());
            domicilio.setPais(includeDomicilio.getPaisCombo());
            domicilio.setCodigopostal(includeDomicilio.getDomicilio().getCodigopostal());

            domicilio.setNumExt(includeDomicilio.getDomicilio().getNumExt());
            domicilio.setNumInt(includeDomicilio.getDomicilio().getNumInt());

            if (domicilio.getIdPais() != null
                    && domicilio.getIdPais().longValue() == Constantes.ID_PAIS_MEXICO) {
                domicilio.setIdEntidad(includeDomicilio.getEntidadFederativaCombo().getIdEntidadFederativa().toString());
                includeDomicilio.overrideNombreEntidadFederativa();
                domicilio.setEntidad(includeDomicilio.getEntidadFederativaCombo());
            } else if (includeDomicilio.getPaisApoderado() != null
                    && includeDomicilio.getPaisApoderado().getIdPais() > 0) {
                Pais paisApoderado = new Pais();
                paisApoderado.setIdPais(Constantes.ID_PAIS);
                paisApoderado.setNombre(Constantes.NOMBRE_PAIS);

                domicilio.setIdPais(Constantes.ID_PAIS);
                domicilio.setPais(paisApoderado);
                domicilio.setIdEntidad(includeDomicilio.getEntidadFederativaCombo().getIdEntidadFederativa().toString());
                domicilio.getEntidad().setNombre(mapaEntidadesFederativas.get(
                        includeDomicilio.getEntidadFederativaCombo().getIdEntidadFederativa().toString()));

            } else {
                domicilio.setIdEntidad(includeDomicilio.getDomicilio().getNombreEntidad());
                domicilio.getEntidad().setNombre(includeDomicilio.getDomicilio().getNombreEntidad());
            }
            
            if (domicilio.getPais().getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                entidadContacto.setIdEntidadFederativa(includeDomicilio.entidadFederativaCombo.getIdEntidadFederativa());
                entidadContacto.setNombre(includeDomicilio.entidadFederativaCombo.getNombre());

            } else {
                entidadContacto.setNombre(includeDomicilio.domicilio.getNombreEntidad());

            }

            domicilio.setEntidad(entidadContacto);
            domicilio.setPoblacion(includeDomicilio.getDomicilio().getPoblacion());
            domicilio.setColonia(includeDomicilio.getDomicilio().getColonia());
            domicilio.setCalle(includeDomicilio.getDomicilio().getCalle());
            datosContacto.setCorreoelectronico(includeDomicilio.getDatosContacto().getCorreoelectronico());
            datosContacto.setTelefono(includeDomicilio.getDatosContacto().getTelefono());
            datosContacto.setFax(includeDomicilio.getDatosContacto().getFax());
            datosContacto.setTelefonoExt(includeDomicilio.getDatosContacto().getTelefonoExt());
            tramitePat.setDomicilioObj(domicilio);
            tramitePat.setDatosContacto(datosContacto);

        }
               
        return msgAviso;
    }
    public String agregarDomicilioContacto(IncludeDomicilio includeDomicilio) {
        boolean insertar = true;
        int errores=0;
        String msgAviso = "";
        if (includeDomicilio.paisCombo.getIdPais() != null && includeDomicilio.paisCombo.getIdPais().intValue() == Constantes.ID_PAIS.intValue()) {
            if (includeDomicilio.domicilio.getColonia() == null || includeDomicilio.domicilio.getColonia().length() == 0) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.colonia")+"(Domicilio de contacto)" + "||";
                errores++;
            }
        } else {
               if (includeDomicilio.domicilio.getIdEntidad() == null
                    || includeDomicilio.domicilio.getIdEntidad().equals(String.valueOf(Constantes.FIRST))) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.entidad") +"(Domicilio de contacto)"+ "||";
                errores++;
            }
        }

        if (includeDomicilio.domicilio.getCodigopostal() == null || includeDomicilio.domicilio.getCodigopostal().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.cp")+"(Domicilio de contacto)" + "||";
            errores++;
        }

        if (includeDomicilio.domicilio.getCalle() == null || includeDomicilio.domicilio.getCalle().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.calle") +"(Domicilio de contacto)" + "||";
            errores++;
        } else {
            if (Pattern.matches(Constantes.pattern_direccionesPatente, includeDomicilio.domicilio.getCalle()) == false) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.caracteres") +"(Domicilio de contacto)"+ "||";
                errores++;
            }
            if (includeDomicilio.domicilio.getCalle().length() > Integer.parseInt(bundleParametrosPatentes.getString("domicilio.CalleNumero.maxlength"))) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.max")+"(Domicilio de contacto)" + "||";
                errores++;
            }
        }

        if (includeDomicilio.domicilio.getNumExt() == null || includeDomicilio.domicilio.getNumExt().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.numExt")+"(Domicilio de contacto)"  + "||";
            errores++;
        }

        if (includeDomicilio.datosContacto.getCorreoelectronico() != null
                ? (includeDomicilio.datosContacto.getCorreoelectronico().length() == 0) : false) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.correo")+"(Domicilio de contacto)"  + "||";
            errores++;
        }
        if (includeDomicilio.datosContacto.getCorreoelectronico() != null ? includeDomicilio.datosContacto.getCorreoelectronico().length() > 0 ? !Pattern.matches(Constantes.PATTERN_EMAIL, includeDomicilio.datosContacto.getCorreoelectronico()) : false : false) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.validar.correo")+"(Domicilio de contacto)"  + "||";
            errores++;
        }
        if(errores==5)
        {
            msgAviso=bundleParametrosPatentes.getString("mensaje.error.apoderado.domicilio")  + "||";
        }   
        if (insertar) {
            Domicilio domicilio = new Domicilio();
            Datoscontacto datosContacto = new Datoscontacto();
            EntidadFederativa entidadContacto= new EntidadFederativa();
            domicilio.setIdPais(includeDomicilio.getPaisCombo().getIdPais());
            domicilio.setPais(includeDomicilio.getPaisCombo());
            domicilio.setCodigopostal(includeDomicilio.getDomicilio().getCodigopostal());

            domicilio.setNumExt(includeDomicilio.getDomicilio().getNumExt());
            domicilio.setNumInt(includeDomicilio.getDomicilio().getNumInt());

            if (domicilio.getIdPais() != null
                    && domicilio.getIdPais().longValue() == Constantes.ID_PAIS_MEXICO) {
                domicilio.setIdEntidad(includeDomicilio.getEntidadFederativaCombo().getIdEntidadFederativa().toString());
                includeDomicilio.overrideNombreEntidadFederativa();
                domicilio.setEntidad(includeDomicilio.getEntidadFederativaCombo());
            } else if (includeDomicilio.getPaisApoderado() != null
                    && includeDomicilio.getPaisApoderado().getIdPais() > 0) {
                Pais paisApoderado = new Pais();
                paisApoderado.setIdPais(Constantes.ID_PAIS);
                paisApoderado.setNombre(Constantes.NOMBRE_PAIS);

                domicilio.setIdPais(Constantes.ID_PAIS);
                domicilio.setPais(paisApoderado);
                domicilio.setIdEntidad(includeDomicilio.getEntidadFederativaCombo().getIdEntidadFederativa().toString());
                domicilio.getEntidad().setNombre(mapaEntidadesFederativas.get(
                        includeDomicilio.getEntidadFederativaCombo().getIdEntidadFederativa().toString()));

            } else {
                domicilio.setIdEntidad(includeDomicilio.getDomicilio().getNombreEntidad());
                domicilio.getEntidad().setNombre(includeDomicilio.getDomicilio().getNombreEntidad());
            }
            
            if (domicilio.getPais().getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                entidadContacto.setIdEntidadFederativa(includeDomicilio.entidadFederativaCombo.getIdEntidadFederativa());
                entidadContacto.setNombre(includeDomicilio.entidadFederativaCombo.getNombre());

            } else {
                entidadContacto.setNombre(includeDomicilio.domicilio.getNombreEntidad());

            }

            domicilio.setEntidad(entidadContacto);
            domicilio.setPoblacion(includeDomicilio.getDomicilio().getPoblacion());
            domicilio.setColonia(includeDomicilio.getDomicilio().getColonia());
            domicilio.setCalle(includeDomicilio.getDomicilio().getCalle());
            datosContacto.setCorreoelectronico(includeDomicilio.getDatosContacto().getCorreoelectronico());
            datosContacto.setTelefono(includeDomicilio.getDatosContacto().getTelefono());
            datosContacto.setFax(includeDomicilio.getDatosContacto().getFax());
            datosContacto.setTelefonoExt(includeDomicilio.getDatosContacto().getTelefonoExt());
            tramitePat.setDomicilioObj(domicilio);
            tramitePat.setDatosContacto(datosContacto);

        }
               
        return msgAviso;
    }
    
    public boolean agregarPersonaSinDomicilio(IncludeFirmante includeFirmante, List<Persona> personas, Boolean editar) {
        boolean insertar = true;
        String msgAviso = "";
        IncludeFirmante includePromovente;
        if (includeFirmante.getTipoPersona().getIdTipopersona().intValue() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mmensaje.error.tipo.persona") + "||";
        }

        Persona paux = new Persona();

        String primerApellido = "";
        String segundoApellido = "";

        primerApellido = includeFirmante.getApoderado().getPrimerApellido().trim() == null ? "" : includeFirmante.getApoderado().getPrimerApellido().trim();
        segundoApellido = includeFirmante.getApoderado().getSegundoApellido().trim() == null ? "" : includeFirmante.getApoderado().getSegundoApellido().trim();

        String nombreCompletoAux = includeFirmante.getApoderado().getNombre().trim() + " " + primerApellido + " " + segundoApellido;

//        paux.setNombrecompletotmp(includeFirmante.getApoderado().getNombre().trim());
        paux.setNombrecompletotmp(nombreCompletoAux);
        paux.setNombrecompletotmp(Util.reemplazaAcentos(paux.getNombrecompletotmp()));

        if (!editar) {
            Collections.sort(personas, new ComparatorPersona());


            if (Collections.binarySearch(personas, paux, new ComparatorPersona()) >= 0) {
                msgAviso += "NOMBRE REPETIDO" + "||";
                insertar = false;
            }
        }

        if (includeFirmante.getApoderado().getNombre() == null || includeFirmante.getApoderado().getNombre().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.nombre") + "||";
        } else {
            if (Pattern.matches(Constantes.pattern_nombre_personas, includeFirmante.getApoderado().getNombre()) == false) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.nombre.notificar.caracteres") + "||";
                insertar = false;
            }
            if (includeFirmante.getApoderado().getRgp() != null) {
                includeFirmante.getApoderado().setRgp(includeFirmante.getApoderado().getRgp().replaceAll("\\s", ""));
                if (includeFirmante.getApoderado().getRgp().length() > 0 && Pattern.matches(Constantes.pattern_apoderado_rgp, includeFirmante.getApoderado().getRgp()) == false) {
                    msgAviso += bundleParametrosPatentes.getString("mensaje.error.rgp.apoderado.caracteres") + "||";
                    insertar = false;
                }
            }
        }
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        Persona promoventeTmp = obtenerPromovente(obtSession);
        if (insertar) {
            Persona persona = new Persona();
            persona.setIdSolicitante(includeFirmante.getApoderado().getIdPersona());
            persona.setNombrecompleto(includeFirmante.getApoderado().getNombre());
            persona.setNombrecompletotmp(Util.reemplazaAcentos(includeFirmante.getApoderado().getNombre()));
            persona.setTipoPersona(new CatTipopersona());
            persona.getTipoPersona().setIdTipopersona(new Short("1"));
            persona.setIdTipopersona(persona.getTipoPersona().getIdTipopersona());
            persona.setNombreConcatenado(nombreCompletoAux);
             //Se agrego validacion: ya que es un campo no obligatorio con lo tal puede llegar vacio
            if (includeFirmante.getApoderado().getRgp() != null) {
                persona.setRgp(includeFirmante.getApoderado().getRgp().replaceAll("[\n\r\\s]", ""));
            }
            persona.setPrimerApellido(includeFirmante.getApoderado().getPrimerApellido());
            persona.setSegundoApellido(includeFirmante.getApoderado().getSegundoApellido());
            persona.setEsMandatorio(includeFirmante.getApoderado().getEsMandatorio());

            if (editar) {
                Integer index = buscarPersonaById(persona, personas);
                if (index != null) {
                    personas.remove(index.intValue());
                    Collections.sort(personas, new ComparatorPersona());


                    if (Collections.binarySearch(personas, paux, new ComparatorPersona()) >= 0) {
                        msgAviso += "NOMBRE REPETIDO" + "||";
                        insertar = false;
                    } else {
                        personas.add(index.intValue(), persona);
                    }


                } else {
                    persona.setIdSolicitante(new Integer(persona.hashCode()).longValue());
                    personas.add(persona);
                }
            } else {
                 if (includeFirmante.getApoderado().getId_promovente() == tramitePat.getIdUsuarioFirmante().intValue()) {
                    persona.setIdUsuarioFirmante(tramitePat.getIdUsuarioFirmante());
                }else{
                    if (personas.size()==0){
                       if(promoventeTmp.getNombrecompleto().equals(this.getListaSolicitantes().get(0).getNombreConcatenado().toUpperCase())){
                            if (promoventeTmp.getTipoPersona().getIdTipopersona()==1 && promoventeTmp.getPrimerApellido()!=null)
                            {   ////////////agregado
                                includePromovente= convertirPersonaToIncludeFirmante(promoventeTmp);
                                if (includePromovente.getIncludeDomicilio()!=null){
                                    this.setDomicilioContacto(includePromovente.getIncludeDomicilio());
                                }
                                ///////
                                String[] palabras = promoventeTmp.getNombrecompletotmp().split(" ");
                                String nombreFormato = new String();
                                for (int i=0;i<palabras.length;i++)
                                {
                                    nombreFormato= nombreFormato + palabras[i].substring(0,1).toUpperCase()+ palabras[i].substring(1,palabras[i].length()).toLowerCase() + " ";
                                }
                                promoventeTmp.setNombreConcatenado(nombreFormato.trim() + " " + promoventeTmp.getPrimerApellido() + " " + promoventeTmp.getSegundoApellido());
                                promoventeTmp.setNombrecompleto(nombreFormato.trim());
                                promoventeTmp.setNombrecompletotmp(nombreFormato.trim());  
                            }else
                                {
                                    promoventeTmp.setNombreConcatenado(promoventeTmp.getNombrecompleto());
                                }
                            promoventeTmp.setPrincipal(new Short("1"));
                            personas.add(promoventeTmp);
                       } 
                    }
                }
//                if (includeFirmante.getApoderado().getId_promovente() == tramitePat.getIdUsuarioFirmante().intValue()) {
//                    persona.setIdUsuarioFirmante(tramitePat.getIdUsuarioFirmante());
//                }
                persona.setIdSolicitante(new Integer(persona.hashCode()).longValue());
                personas.add(persona);
            }
        }
        if (!insertar) {
            //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgAviso, msgAviso);
            //FacesContext.getCurrentInstance().addMessage(null, message);
            convierteListaErrores(msgAviso);
            verErrores = true;
        } else {
            verErrores = false;
        }

        return insertar;
    }

    public void overrideNombreNacionalidad(Pais pNacionalidad) {
        for (Pais nacionalidadTemp : nacionalidades) {
            if (nacionalidadTemp.getIdPais().intValue() == pNacionalidad.getIdPais().intValue()) {
                pNacionalidad.setNacionalidad(nacionalidadTemp.getNacionalidad());
                break;
            }
        }
    }

    public Integer buscarPersonaById(Persona pPersona, List<Persona> pPersonas) {
        Integer result = null;
        for (int i = 0; i < pPersonas.size(); i++) {
            if (pPersonas.get(i).getIdSolicitante().longValue() == pPersona.getIdSolicitante().longValue()) {
                result = i;
                break;
            }
        }
        return result;
    }
    
        public void crearAnexoHojaDescuento(Integer flagFechas, Persona solicitante,String firmante,String SelloFirmante) {

        GenerarReporte generarReporte = new GenerarReporte();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        List<Persona> personaApoderado = new ArrayList<Persona>();
        for (Persona perApoderado : apoderadoModel) {
            if (perApoderado.getPrincipal() != null && perApoderado.getPrincipal() == 1) {
                personaApoderado.add(perApoderado);
                break;
            }
        }

        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();

//        String usuarioPASE = obtSession.getUsuario().getNombre() + " "
//                + obtSession.getUsuario().getApellidoPaterno() + " "
//                + obtSession.getUsuario().getApellidoMaterno();
        String usuarioPASE="";
        if (personaApoderado.size()>0)
        {
            usuarioPASE = personaApoderado.get(Constantes.FIRST).getNombreConcatenado();
        }else
        {
            usuarioPASE = obtSession.getUsuario().getNombre() + " "
                + obtSession.getUsuario().getApellidoPaterno() + " "
                + obtSession.getUsuario().getApellidoMaterno();
        }
        
        //String usuarioPASE = personaApoderado.get(Constantes.FIRST).getNombreConcatenado();
        
        String primerApellido = "";
        String segundoApellido = "";
        
        primerApellido = solicitante.getPrimerApellido() == null ? "" : solicitante.getPrimerApellido().trim();
        segundoApellido = solicitante.getSegundoApellido() == null ? "" : solicitante.getSegundoApellido().trim();

        String nombreSolicitante = solicitante.getNombrecompleto() + " " + primerApellido + " " + segundoApellido;
        
        Persona personaSolicitante = new Persona();
        personaSolicitante.setNombrecompleto(nombreSolicitante);
        
        if (personaApoderado.size()>0)
        {
        Collections.sort(personaApoderado, new ComparatorPersona());
            if (Collections.binarySearch(personaApoderado, personaSolicitante, new ComparatorPersona()) < 0)
                usuarioPASE+=" por poder de\n"+nombreSolicitante;
//                usuarioPASE+=nombreSolicitante;
//            }else
//                usuarioPASE+=" De poder\n"+nombreSolicitante;
            
//        if(personaApoderado.get(numElemto).getNombrecompleto())
        
//        usuarioPASE+=" De poder\n"+nombreSolicitante;
        }
        hojaDescuento hojaDescuento = new hojaDescuento();
        hojaDescuento.setTipo(solicitante.getTipoSolicitante().getIdTipoSolicitante().intValue());
        hojaDescuento.setNombre(usuarioPASE);

        hojaDescuento.setNombreApoderado("");
        if (tramitePat.getTipoSol().getIdTiposolicitud().intValue() == Constantes.TIPO_SOL_BUSQUEDA_SIT )
        {
            hojaDescuento.setTipoSolicitud(Constantes.TIPO_SOLICITUD_BU);
        }else if (tramitePat.getTipoSol().getIdTiposolicitud().intValue() == Constantes.TIPO_SOL_VIGILANCIA_SIT )
        {
            hojaDescuento.setTipoSolicitud(Constantes.TIPO_SOLICITUD_VI);
        }else
        {
            hojaDescuento.setTipoSolicitud(Constantes.TIPO_SOLICITUD_BU);
        }
        
        hojaDescuento.setFlag(flagFechas);


        hojaDescuento.setImgFirmaImpi(request.getRealPath("") + "/content/imagenes/firma_impi.png");
        //
        hojaDescuento.setNombreFirmante(firmante);
        hojaDescuento.setSelloSolicitante(SelloFirmante);
        //
        ByteArrayOutputStream byt = generarReporte.generarHojaDescuentoPdf(request.getRealPath("")
                + "/content/reportes/impi_Hoja_descuento.jasper", hojaDescuento);

        byte[] archivoAnexo = byt.toByteArray();

        List<AnexosViewDto> anexoDescuento = new ArrayList<AnexosViewDto>();

        AnexosViewDto nvoAnexo = new AnexosViewDto();
        nvoAnexo.setArchivoAnexo(archivoAnexo);//                usuarioPASE+=nombreSolicitante;
//            }else
//                usuarioPASE+=" De poder\n"+nombreSolicitante;
            
//        if(personaApoderado.get(numElemto).getNombrecompleto())
        
//        usuarioPASE+=" De poder\n"+nombreSolicitante;
        nvoAnexo.setExtension("pdf");
        nvoAnexo.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
        nvoAnexo.setIdTramitePatente(tramitePat.getIdTramitePatente());
        nvoAnexo.setNombreArchivo("hojaDescuento.pdf");
        nvoAnexo.setCargado(true);

        anexoDescuento.add(nvoAnexo);

        int res = this.flujosgralesViewService.insertarAnexosDtosPatentes(anexoDescuento);
    }

    public void valueChangeEvent_tipoSolicitante(int tipoSolicitante) {
        if (log.isInfoEnabled()) {

            log.info(solicitanteDatos.getTipoSolicitante().getIdTipoSolicitante());
            log.info(tiposSolicitantes.size());
        }
        if(editarSolicitante)
        {
           this.solicitanteDatos.setNombrecompleto(null);
           this.solicitanteDatos.setPrimerApellido(null);
           this.solicitanteDatos.setSegundoApellido(null);
        }
        CatTipopersona criteriotipoPer = new CatTipopersona();
        criteriotipoPer.setIndActivo(new Short("1"));

        log.info("solicitanteDatos.isAplicarDescuento()    " + solicitanteDatos.isAplicarDescuento());

        if (solicitanteDatos.isAplicarDescuento()) {
            log.info("Elimina el anexo");
            Anexos anexo = new Anexos();
            anexo.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
            anexo.setIdTramitePatente(tramitePat.getIdTramitePatente());
            int res = this.flujosgralesViewService.deleteByTypeAnexo(anexo);
        }

        for (int i = 0; i < tiposSolicitantes.size(); i++) {
            if (tiposSolicitantes.get(i).getIdTipoSolicitante().intValue() == solicitanteDatos.getTipoSolicitante().getIdTipoSolicitante().intValue()) {
                solicitanteDatos.getTipoPersona().setIdTipopersona(tiposSolicitantes.get(i).getIdTipoPersona().shortValue());
                criteriotipoPer.setIdTipopersona(tiposSolicitantes.get(i).getIdTipoPersona().shortValue());
                solicitanteDatos.getTipoSolicitante().setIdTipoPersona(tiposSolicitantes.get(i).getIdTipoPersona().intValue());
                if (tiposSolicitantes.get(i).getAplicaDescuento().intValue() == 1) {
                    aplicaDescuento = true;
                } else {
                    aplicaDescuento = false;
                }
                if (solicitanteDatos.getTipoSolicitante().getIdTipoSolicitante() != null) {
                    if (solicitanteDatos.getTipoSolicitante().getIdTipoSolicitante().longValue() == 7L) {
                        esinventor = true;
                    } else {
                        inventor = false;
                        esinventor = false;
                    }
                }
                solicitanteDatos.setAplicarDescuento(false);
                //override de tipo persona
                solicitanteDatos.getTipoSolicitante().setDescripcion(tiposSolicitantes.get(i).getDescripcion());
                break;

            }
        }
        

        tiposPersona = catalogosViewService.consultarTiposPersona(criteriotipoPer);
        
   
        patentesViewService.habilitarSolicitante(this);

        
        habilitaComboTipoPer(tipoSolicitante);

    }

    public void habilitarTiposPersona() throws Exception {
        if (solicitanteDatos.getTipoSolicitante().getIdTipoSolicitante() != null) {
            CatTipopersona criteriotipoPer = new CatTipopersona();
            criteriotipoPer.setIndActivo(new Short("1"));

            for (int i = 0; i < tiposSolicitantes.size(); i++) {
                if (tiposSolicitantes.get(i).getIdTipoSolicitante().intValue() == solicitanteDatos.getTipoSolicitante().getIdTipoSolicitante().intValue()) {
                    criteriotipoPer.setIdTipopersona(tiposSolicitantes.get(i).getIdTipoPersona().shortValue());
                    solicitanteDatos.setIdTipoSolicitante(solicitanteDatos.getTipoSolicitante().getIdTipoSolicitante());
                    if (tiposSolicitantes.get(i).getAplicaDescuento().intValue() == 1) {
                        aplicaDescuento = true;
                    } else {
                        aplicaDescuento = false;
                        solicitanteDatos.setAplicarDescuento(false);
                    }

                    if (solicitanteDatos.getTipoSolicitante().getIdTipoSolicitante().longValue() == 7L) {
                        esinventor = true;
                    } else {
                        esinventor = false;
                    }

                    //override de tipo persona
                    solicitanteDatos.getTipoSolicitante().setDescripcion(tiposSolicitantes.get(i).getDescripcion());
                    break;

                }
            }
            tiposPersona = catalogosViewService.consultarTiposPersona(criteriotipoPer);
            CatTipoSolicitante criterio = new CatTipoSolicitante();
            criterio.setIndActivo(1);
            tiposSolicitantes = catalogosViewService.consultarTiposSolicitanteXCriterio(criterio);
        }
    }

    public void habilitarOpcionesSubTipoSol(int procedencia) throws Exception {


        CatSubtiposolicitud criterio = new CatSubtiposolicitud();
        criterio.setIndActivo(new Short("1"));
        criterio.setIdTiposolicitud(tramitePat.getTipoSol().getIdTiposolicitud());
        listSubtiposSol = catalogosViewService.getSubtiposSolByCriterio(criterio);
        
        materiaBiologico = false;
        pubAnticipada = false;
        habilitarResumen = false;
        verPCT = false;
        verFaseInt = false;

        if (procedencia == 1) {
            tramitePat.setMaterial_biologico(false);
            inicializaSolPCT();
            limpiarPct();

            listaPrioridades = new ArrayList<Prioridad>();
            tramitePat.setPrioridades(new ArrayList<Prioridad>());
        }

        if (tramitePat.getTipoSol().getIdTiposolicitud().longValue() == Constantes.TIPO_SOL_DISENO.longValue()
                || tramitePat.getTipoSol().getIdTiposolicitud().longValue() == Constantes.TIPO_SOL_ESQUEMA_TRAZADO.longValue()) {
            //habilitarResumen = false;
            forma.setRenderedDisenoInd(true);
        } else {
            //habilitarResumen = true;
            if (tramitePat.getTipoSol().getIdTiposolicitud().longValue() == Constantes.TIPO_SOL_PATENTE) {
                materiaBiologico = true;
            }
            forma.setRenderedDisenoInd(false);
        }
           if (tramitePat.getTipoSol().getIdTiposolicitud()!= 0){
               if (anexosMemoria.size()<3){
                    anexosMemoria = catalogosViewService.consultarAnexos(); 
               }
               if(tramitePat.getTipoSol().getIdTiposolicitud().doubleValue()== Constantes.SOL_DISENO_INDUSTRIAL.doubleValue())
               {
                   for(int i=0; i< anexosMemoria.size();i++){
                       if (anexosMemoria.get(i).getIdTipoanexo().doubleValue()== Constantes.ANEXO_RESUMEN_MEMORIA)
                       {
                          anexosMemoria.remove(i);
                          break;
                       }
                   }
                       
               }  
           }
        overrideTipoSolicitud();
    }

    public void limpiarPct() {
        tramitePat.setFechaSolPCT(null);
        tramitePat.setFechaPubPCT(null);
        tramitePat.setNumPubPCT(null);
        tramitePat.setTipoPubPCT(null);
        tipoPublicacionPct.setIdTipoPublicacionPct(null);
        habilitarPct = false;
    }

    public void overrideTipoSolicitud() {
        for (int u = 0; u < listTiposSol.size(); u++) {
            CatTiposolicitud elemento = (CatTiposolicitud) listTiposSol.get(u);

            if (elemento.getIdTiposolicitud().intValue() == tramitePat.getTipoSol().getIdTiposolicitud().intValue()) {
                tramitePat.getTipoSol().setDescripcion(elemento.getDescripcion());
                break;
            }
        }
    }

  
      //IJZA Se realiza función que recupera el valor de la cobertura cuando se entra desde solicitudes en preparación  
  
    public void recuperaCobertura(int idSubTipoSolicitud) throws Exception {

        modificaRadioBotonCoverInter = false;
        modificaRadioBotonCoverNacioInter = false;
        if (idSubTipoSolicitud == 28 || idSubTipoSolicitud == 29) {
            tramitePat.setCobertura(tramitePat.getCobertura());
            modificaRadioBotonCoverNacioInter = true;
            modificaRadioBotonCoverInter = false;
        } else if (idSubTipoSolicitud == 30) {
            tramitePat.setCobertura(tramitePat.getCobertura());
            modificaRadioBotonCoverNacioInter = false;
            modificaRadioBotonCoverInter = true;
        } else {
            modificaRadioBotonCoverNacioInter = false;
            modificaRadioBotonCoverInter = false;

        }

      
    }
    
    //IJZA Se agrega función cambiarCobertura para realizar el cambio del radio boton dependiendo del subtipo de solicitud
    public void cambiarCobertura(int idSubTipoSolicitud) throws Exception {

        modificaRadioBotonCoverInter = false;
        modificaRadioBotonCoverNacioInter = false;
        if (idSubTipoSolicitud == 28 || idSubTipoSolicitud == 29) {
            tramitePat.setCobertura(null);
            modificaRadioBotonCoverNacioInter = true;
            modificaRadioBotonCoverInter = false;
        } else if (idSubTipoSolicitud == 30) {
            tramitePat.setCobertura("2");
            modificaRadioBotonCoverNacioInter = false;
            modificaRadioBotonCoverInter = true;
        } else {
            modificaRadioBotonCoverNacioInter = false;
            modificaRadioBotonCoverInter = false;
        
        }

      
    }
    
    
    
    
    public void habilitaComboTipoPer(int tipSolId) {

        if (tipSolId == 9) {

            habilitaDeshabilitaComboTipPer = false;

        } else {

            habilitaDeshabilitaComboTipPer = true;

        }

    }

    
    
    public void habilitarPCT(int procedencia) throws Exception {

        verFaseInt = false;
        pubAnticipada = false;
        if (procedencia == 1) {
            tramitePat.setPub_anticipada(false);
            inicializaSolPCT();
        }


        if (tramitePat.getSubTipoSol() != null && tramitePat.getSubTipoSol().getIdSubtiposolicitud() != null && tramitePat.getSubTipoSol().getIdSubtiposolicitud().intValue() != 0) {
            if (tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_MODELO_UTILIDAD.longValue()
                    || tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_PATENTE.longValue()) {
                verPCT = true;
                this.setHabilitarPct(true);
///se descomenta para capitulos
                if (tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_PATENTE || tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_MODELO_UTILIDAD) {
                    //listCapitulos = catalogosViewService.getAllCapitulos();
                    if (procedencia == 1) {
                        //tramitePat.setFaseInternacional(new Integer(1));
                        //tramitePat.setFaseSolPCT("Capitulo 1");
                        forma.setCapPCT("1");
                    }
                    if (this.getIdTramite()>0){
                        forma.setCapPCT(tramitePat.getFaseInternacional().toString());
                    }
                   // verFaseInt = true;
                }
            } else {

                if (tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_NORMAL_PATENTE.longValue()) {
                    pubAnticipada = true;
                }
                verPCT = false;
                this.setHabilitarPct(false);
            }
        }

        //ASIGNAR DESCRIPCION AL SUBTIPO
        llenarMapaSubTipoSolicitudes();
        tramitePat.getSubTipoSol().setDescripcion(mapaSubtipoSolicitud.get(
                tramitePat.getSubTipoSol().getIdSubtiposolicitud()));

    }

    public void asignarFaseInternacional() {
        if (tramitePat.getFaseInternacional() != null && tramitePat.getFaseInternacional().intValue() != 0) {
            for (int y = 0; y < listCapitulos.size(); y++) {
                CatCapitulos elemen = (CatCapitulos) listCapitulos.get(y);
                if (elemen.getIdCapitulo().intValue() == tramitePat.getFaseInternacional().intValue()) {
                    tramitePat.setFaseSolPCT(elemen.getDescripcion());
                }
            }
        }
    }

    public void agregarSolicitantes() {
        //^([a-zA-Z0-9_\-\.]+)@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$  email
        log.info("agregando solicitantes");

        Persona elemento = new Persona();
        Datoscontacto datosCont = new Datoscontacto();
        Domicilio dom = new Domicilio();
        EntidadFederativa ent = new EntidadFederativa();
        Pais paisSolicitante = new Pais();
        EntidadFederativa entSolicitante = new EntidadFederativa();
        boolean insertar = true;
        String msgAviso = "";
        boolean agregarAInventor = false;
        
        if (solicitanteDatos.getTipoSolicitante() == null || solicitanteDatos.getTipoSolicitante().getIdTipoSolicitante() == null || solicitanteDatos.getTipoSolicitante().getIdTipoSolicitante().longValue() == 0) {


            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.tipo.solicitante") + "||";

        }
//        if (solicitanteDatos.getTipoPersona().getIdTipopersona().intValue() == 0) {
//            insertar = false;
//            msgAviso += bundleParametrosPatentes.getString("mmensaje.error.tipo.persona") + "||";
//        }


        this.solicitanteDatos.setNombrecompletotmp(this.solicitanteDatos.getNombrecompleto().trim());

        if (solicitanteDatos.getNombrecompleto() == null || solicitanteDatos.getNombrecompleto().length() == 0) {

            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.nombre") + "||";
        } else {
            if (!Pattern.matches(Constantes.pattern_nombre_personas, solicitanteDatos.getNombrecompleto())) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.nombre.caracteres") + "||";
                insertar = false;
            }
        }

        String primerApellido = "";
        String segundoApellido = "";

        primerApellido = this.solicitanteDatos.getPrimerApellido() == null ? "" : this.solicitanteDatos.getPrimerApellido().trim();
        segundoApellido = this.solicitanteDatos.getSegundoApellido() == null ? "" : this.solicitanteDatos.getSegundoApellido().trim();

        String nombreConcatenado = solicitanteDatos.getNombrecompleto() + " " + primerApellido + " " + segundoApellido;


        this.solicitanteDatos.setNombrecompletotmp(Util.reemplazaAcentos(nombreConcatenado));

        String nombre = "";
        nombre = this.solicitanteDatos.getNombrecompleto() == null ? null : this.solicitanteDatos.getNombrecompleto().trim();
        log.info("nombre   " + nombre);

        if (solicitanteDatos.getTipoPersona().getIdTipopersona() == Constantes.INIT_SHORT
                && solicitanteDatos.getPrimerApellido() == null) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.primerApellido") + "||";
        }

        //managedBean.solicitanteDatos.nacionalidad.idPais
        

        //IJZA se quita validacion de Nacionalidad ya que el campo se va a quitar de la solapa de solicitud por petición del usuario 25/02/2015
        
//        if (solicitanteDatos.getNacionalidad() == null || solicitanteDatos.getNacionalidad().getIdPais() == 0) {
//            insertar = false;
//            msgAviso += bundleParametrosPatentes.getString("mensaje.error.solicitante.nacionalidad") + "||";
//        }

        if (domicilioSolicitante.domicilio.getCodigopostal() == null || domicilioSolicitante.domicilio.getCodigopostal().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.cp") + "||";
        }

        if (solicitanteDatos.getTipoPersona().getIdTipopersona() == Constantes.INIT_SHORT
                && (solicitanteDatos.getPrimerApellido() == null || solicitanteDatos.getPrimerApellido().length() == 0)) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.primerApellido") + "||";
        }

        if (!editarSolicitante) {
            Collections.sort(this.listaSolicitantes, new ComparatorPersona());


            if (Collections.binarySearch(this.listaSolicitantes, this.solicitanteDatos, new ComparatorPersona()) >= 0) {
                msgAviso += "NOMBRE REPETIDO" + "||";
                insertar = false;
            }
        }


        if (domicilioSolicitante.paisCombo.getIdPais() != null && domicilioSolicitante.paisCombo.getIdPais().intValue() == Constantes.ID_PAIS.intValue()) {
            if (domicilioSolicitante.domicilio.getColonia() == null || domicilioSolicitante.domicilio.getColonia().length() == 0) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.colonia") + "||";
            }
        } else {
            if (domicilioSolicitante.paisCombo.getIdPais().intValue() == Constantes.FIRST) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.paisResidencia.solicitante") + "||";                
            }
            
            
            if (domicilioSolicitante.domicilio.getNombreEntidad() == null || domicilioSolicitante.domicilio.getNombreEntidad().length() == 0) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.entidadSolicitante") + "||";
            }
        }

        if (domicilioSolicitante.domicilio.getCodigopostal() == null || domicilioSolicitante.domicilio.getCodigopostal().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.cp") + "||";

        }

        if (domicilioSolicitante.domicilio.getCalle() == null || domicilioSolicitante.domicilio.getCalle().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.calle") + "||";

        } else {
            if (Pattern.matches(Constantes.pattern_direccionesPatente, domicilioSolicitante.getDomicilio().getCalle()) == false) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.caracteres") + "||";
            }
            if (domicilioSolicitante.domicilio.getCalle().length() > Integer.parseInt(bundleParametrosPatentes.getString("domicilio.CalleNumero.maxlength"))) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.max") + "||";
            }
        }

        if (domicilioSolicitante.domicilio.getNumExt() == null || domicilioSolicitante.domicilio.getNumExt().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.numExt") + "||";
        }
//      Validación Correo cuando no existen apoderados
//        if (tramitePat.getApoderados().isEmpty()) {
//            if (domicilioSolicitante.datosContacto.getCorreoelectronico() != null
//                    ? !(domicilioSolicitante.datosContacto.getCorreoelectronico().length() > 0) : !(false)) {
//                insertar = false;
//                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.correo") + "||";
//            }
//        }

        if (domicilioSolicitante.datosContacto.getCorreoelectronico() != null ? domicilioSolicitante.datosContacto.getCorreoelectronico().length() > 0 ? !Pattern.matches(Constantes.PATTERN_EMAIL, domicilioSolicitante.datosContacto.getCorreoelectronico()) : false : false) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.validar.correo") + "||";
        }



        if (insertar) {
            dom.setIdPais(domicilioSolicitante.paisCombo.getIdPais());


            if (domicilioSolicitante.paisCombo.getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                dom.setIdEntidad(domicilioSolicitante.entidadFederativaCombo.getIdEntidadFederativa().toString());
                overrideNombreEntidadFederativa(1);
                ent.setNombre(domicilioSolicitante.entidadFederativaCombo.getNombre());
                dom.setEntidad(ent);
            }
            dom.setPoblacion(domicilioSolicitante.domicilio.getPoblacion());
            dom.setCodigopostal(domicilioSolicitante.domicilio.getCodigopostal());
//            dom.setCalle(domicilioSolicitante.domicilio.getCalle().substring(0, Integer.parseInt(bundleParametrosPatentes.getString("domicilio.CalleNumero.maxlength"))));
            dom.setCalle(domicilioSolicitante.domicilio.getCalle());
            dom.setColonia(domicilioSolicitante.domicilio.getColonia());
            dom.setNumExt(domicilioSolicitante.domicilio.getNumExt());
            dom.setNumInt(domicilioSolicitante.domicilio.getNumInt());


            paisSolicitante.setIdPais(domicilioSolicitante.paisCombo.getIdPais());
            domicilioSolicitante.overrideNombrePais();
            paisSolicitante.setNombre(domicilioSolicitante.paisCombo.getNombre());
            dom.setPais(paisSolicitante);


            if (dom.getPais().getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                entSolicitante.setIdEntidadFederativa(domicilioSolicitante.entidadFederativaCombo.getIdEntidadFederativa());
                entSolicitante.setNombre(domicilioSolicitante.entidadFederativaCombo.getNombre());

            } else {
                entSolicitante.setNombre(domicilioSolicitante.domicilio.getNombreEntidad());

            }

            dom.setEntidad(entSolicitante);
            elemento.setDomicilioObj(dom);


            //datos de contacto

            datosCont.setCorreoelectronico(domicilioSolicitante.datosContacto.getCorreoelectronico());
            datosCont.setTelefono(domicilioSolicitante.datosContacto.getTelefono());
            datosCont.setFax(domicilioSolicitante.datosContacto.getFax());
            datosCont.setTelefonoExt(domicilioSolicitante.datosContacto.getTelefonoExt());
            elemento.setDatosContacto(datosCont);

   //IJZA se quita funcion overrideNombrePaisNacionalidad() ya que el solicitante se quito el campo nacionalidad 25/02/2015
   //         overrideNombrePaisNacionalidad();

            //Construir solicitante

            elemento.setTipoSolicitante(solicitanteDatos.getTipoSolicitante());
            elemento.setTipoPersona(solicitanteDatos.getTipoPersona());
            elemento.setNombrecompleto(solicitanteDatos.getNombrecompleto());
            elemento.setIdNacionalidad(solicitanteDatos.getPais().getIdPais());
            elemento.setIdSolicitante(solicitanteDatos.getIdSolicitante());
            elemento.setAplicarDescuento(solicitanteDatos.isAplicarDescuento());
            elemento.setNombrecompletotmp(solicitanteDatos.getNombrecompletotmp());
            elemento.setRfc(solicitanteDatos.getRfc());
            elemento.setNacionalidad(solicitanteDatos.getNacionalidad());
            elemento.setPrincipal(solicitanteDatos.getPrincipal());

            elemento.setPrimerApellido(solicitanteDatos.getPrimerApellido());
            elemento.setSegundoApellido(solicitanteDatos.getSegundoApellido());

            elemento.setNombreConcatenado(nombreConcatenado);

            if (inventor) {
                elemento.setInventor(new Short("1"));
                agregarAInventor = true;
            } else {
                elemento.setInventor(new Short("0"));

                if (elemento.getIdSolicitante() != null) {
                    this.listaInventores = eliminarInventoresSolicitante(elemento.getIdSolicitante(),
                            this.listaInventores);
                }
            }

            if (!editarSolicitante) {
                elemento.setIdSolicitante(new Integer(elemento.hashCode()).longValue());
                listaSolicitantes.add(elemento);
                //listaSolicitantesTmp.add(elemento);//+
                if (agregarAInventor == true) {
                    listaInventores.add(elemento);
                }

            } else {
                elemento.setNombrecompleto(solicitanteDatos.getNombrecompleto());
                elemento.setNombrecompletotmp(solicitanteDatos.getNombrecompletotmp());
                //solicitanteDatos.setNombrecompleto("");

                if (agregarAInventor == true && copiarInventorSolicitante == true) {
                    listaInventores.add(elemento);
                }


                for (int i = 0; i < listaSolicitantes.size(); i++) {
                    Persona elementoLista = (Persona) listaSolicitantes.get(i);
                    if (elemento.getIdSolicitante().intValue() == 0) {

                        if (elementoLista.getNombrecompleto().equals(nombreSolicitante) == true) {
                            nombreSolicitante = "";
                            listaSolicitantes.remove(i);
                            Collections.sort(this.listaSolicitantes, new ComparatorPersona());


                            if (Collections.binarySearch(this.listaSolicitantes, this.solicitanteDatos, new ComparatorPersona()) >= 0) {
                                msgAviso += "NOMBRE REPETIDO" + "||";
                                insertar = false;
                                break;
                            }
                            listaSolicitantes.add(i, elemento);

                            break;
                        }
                    } else {

                        if (elementoLista.getIdSolicitante().longValue() == elemento.getIdSolicitante().longValue()) {
                            listaSolicitantes.remove(i);
                            Collections.sort(this.listaSolicitantes, new ComparatorPersona());


                            if (Collections.binarySearch(this.listaSolicitantes, this.solicitanteDatos, new ComparatorPersona()) >= 0) {
                                msgAviso += "NOMBRE REPETIDO" + "||";
                                insertar = false;
                                break;
                            }

                            listaSolicitantes.add(i, elemento);

                            if (agregarAInventor == true && copiarInventorSolicitante == false) {
                                reemplazarInventor(elemento, true);
                            }

                        }
                    }
                }
                editarSolicitante = false;
            }

            if (listaSolicitantes.size() == 1) {
                listaSolicitantes.get(0).setPrincipal(new Short("1"));
                selectedSolicitante = listaSolicitantes.get(0);
                marcarSolicitanteAutomatico = true;
            } else {
                
                for (Persona persona : tramitePat.getApoderados()) {
                    if (persona.getPrincipal() != null) {
                        selectedApoderado = persona;
                        break;
                    }
                }
                
                for (Persona persona : tramitePat.getSolicitantes()) {
                    if (persona.getPrincipal() != null) {
                        selectedSolicitante = persona;
                        break;
                    }
                }
           
//                if (marcarSolicitanteAutomatico == true) {
//                    listaSolicitantes.get(0).setPrincipal(null);
//                    selectedSolicitante = new Persona();
//
//                }
            }

            if (msgAviso != null && msgAviso.length() == 0) {
                solicitanteModel = new PersonaDM((listaSolicitantes));
                domicilioSolicitante.setDomicilio(new DomicilioDto());
//                domicilioSolicitante.paisCombo.setIdPais(Constantes.ID_PAIS);
                domicilioSolicitante.setPaisCombo(new Pais());
                domicilioSolicitante.setCodigosPostalesTable(new ArrayList<CodigosPostales>());
                domicilioSolicitante.entidadFederativaCombo.setIdEntidadFederativa(0);
                inicializarPersona(1);
                domicilioSolicitante.setDatosContacto(new Datoscontacto());
                reglaSolicitanteApoderado();
            }
            //SE CREA TEMPORAL
            forma.setSolicitanteModelTmp(solicitanteModel);

        }

        if (!insertar) {
            //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgAviso, msgAviso);
            //FacesContext.getCurrentInstance().addMessage(null, message);
            convierteListaErrores(msgAviso);
            verErrores = true;
        } else {
            verErrores = false;
        }

    }

    /**
     * Metodo que elimina el inventor agregado automaticamente al crear un
     * solicitante, en caso de que sea "Moral" se debera eliminar.
     *
     * @param idSolicitante
     * @param listaInventores
     * @return listInventores de tipo PERSONA
     */
    public List<Persona> eliminarInventoresSolicitante(Long idSolicitante, List<Persona> listaInventores) {

        List<Persona> listInventores = new ArrayList<Persona>();

        /**
         * Checar dentro de la lista por numero de solicitante al modificar para
         * eliminar el inventor agregado automaticamente.
         */
        if (listaInventores != null && listaInventores.size() > 0) {
            /**
             * Si el solicitante esta vacio significa que entra la primera vez
             * por lo tanto no se buscara en la lista.
             */
            if (idSolicitante != null) {
                /**
                 * Recorrer la lista para encontrar a la Persona registrada.
                 */
                for (Persona persona : listaInventores) {
                    /**
                     * Se arma una nueva lista solo con las personas que no
                     * coincida el idSolicitante (Regla de negocio).
                     */
                    if (persona.getIdSolicitante().compareTo(idSolicitante) != 0) {
                        listInventores.add(persona);
                    }
                }
            }
        }
        return listInventores;
    }

    public void cancelarSolicitantes() {
        solicitanteModel = new PersonaDM((listaSolicitantes));
        domicilioSolicitante.setDomicilio(new DomicilioDto());
        domicilioSolicitante.paisCombo.setIdPais(new Long(0));
        domicilioSolicitante.entidadFederativaCombo.setIdEntidadFederativa(0);
        inicializarPersona(1);
        domicilioSolicitante.setDatosContacto(new Datoscontacto());
        reglaSolicitanteApoderado();
        this.editarSolicitante = false;
    }

    //IJZA se comenta metodo se sustituye por el de abajo 20/02/2015
    
//    public void overrideNombrePaisNacionalidad(int procedencia) {
//        //Bandera==true: para terminar el ciclo, esto debido que terminaba el ciclo hasta la 2 vez.
//        boolean flag = false;
//        for (Pais pais : paises) {
//            switch (procedencia) {
//                case 1:
//                    if (solicitanteDatos.getNacionalidad().getIdPais().intValue() == pais.getIdPais().intValue()) {
//                        solicitanteDatos.setNacionalidad(pais);
//                        break;
//                    }
//                    break;
//                case 2:
//                    if (inventorDatos.getNacionalidad().getIdPais().intValue() == pais.getIdPais().intValue()) {
//                        inventorDatos.setNacionalidad(pais);
//                        flag = true;
//                    }
//                    break;
//            }
//            if (flag == true) {
//                break;
//            }
//        }
//    }
    
     
    
//IJZA se cambia funcionalidad del metodo overrideNombrePaisNacionalidad ya que para el SIT no tiene inventores 20/02/2015   
    
    public void overrideNombrePaisNacionalidad() {
          boolean flag = false;
        for (Pais pais : paises) {

            if (solicitanteDatos.getNacionalidad().getIdPais().intValue() == pais.getIdPais().intValue()) {
                solicitanteDatos.setNacionalidad(pais);
               flag = true;
            }
               if (flag == true) {
                break;
            }
        }
    }   

    public void overrideNombreEntidadFederativa(int procedencia) {
        if (log.isInfoEnabled()) {
            log.info("***********+overrideNombreEntidadFederativa************");
        }
        for (EntidadFederativa entidadFederativa : entidadesFederativas) {

            switch (procedencia) {
                case 1:
                    if (entidadFederativa.getIdEntidadFederativa().intValue() == domicilioSolicitante.entidadFederativaCombo.getIdEntidadFederativa().intValue()) {
                        if (log.isInfoEnabled()) {
                            log.info("idEntidadFederativa: " + entidadFederativa.getIdEntidadFederativa());
                            log.info("Nombre : " + entidadFederativa.getNombre());
                        }
                        domicilioSolicitante.entidadFederativaCombo.setNombre(entidadFederativa.getNombre());
                        break;
                    }
                    break;
                case 2:
                    if (entidadFederativa.getIdEntidadFederativa().intValue() == domicilioInventor.entidadFederativaCombo.getIdEntidadFederativa().intValue()) {
                        if (log.isInfoEnabled()) {
                            log.info("idEntidadFederativa Est: " + entidadFederativa.getIdEntidadFederativa());
                            log.info("Nombre Est: " + entidadFederativa.getNombre());
                        }
                        domicilioInventor.entidadFederativaCombo.setNombre(entidadFederativa.getNombre());
                        break;
                    }
                    break;
            }

        }
    }

    public void editarSolicitante(Persona sol) {

        boolean encontrado = false;
        try {
            for (int i = 0; i < listaSolicitantes.size(); i++) {
                Persona elemento = (Persona) listaSolicitantes.get(i);
                if (sol.getIdSolicitante() == null) {
                    sol.setIdSolicitante(new Long(0));
                }

                if (sol.getIdSolicitante() == 0) {
                    if (elemento.getNombrecompleto().equals(sol.getNombrecompleto())) {
                        encontrado = true;
                        nombreSolicitante = elemento.getNombrecompleto();
                    }

                } else {

                    if (sol.getIdSolicitante() == elemento.getIdSolicitante()) {
                        encontrado = true;
                    }
                }
                if (encontrado) {
                    domicilioSolicitante.domicilio.setCalle(elemento.getDomicilioObj().getCalle());
                    domicilioSolicitante.domicilio.setColonia(elemento.getDomicilioObj().getColonia());
                    domicilioSolicitante.domicilio.setCodigopostal(elemento.getDomicilioObj().getCodigopostal());
                    domicilioSolicitante.domicilio.setPoblacion(elemento.getDomicilioObj().getPoblacion());
                    domicilioSolicitante.domicilio.setNumExt(elemento.getDomicilioObj().getNumExt());
                    domicilioSolicitante.domicilio.setNumInt(elemento.getDomicilioObj().getNumInt());
                    domicilioSolicitante.initDomicilio(paises, entidadesFederativas, flujosgralesViewService);
                    inicializarPersona(1);
                    solicitanteDatos.setNombrecompleto(elemento.getNombrecompleto());
                    solicitanteDatos.setRfc(elemento.getRfc());
                    
                    log.info("TIPO PERSONA    "+elemento.getTipoPersona().getIdTipopersona());
                    log.info("TIPO SOLICITANTE (solicitanteDatos)  "+listaSolicitantes.get(i).getTipoSolicitante().getIdTipoSolicitante()
                            +"    ID TIPO SOLICITANTE "+elemento.getIdTipoSolicitante());
                    
                    Long idTipoSolicitante = 0L;
                    
                    if(elemento.getIdTipoSolicitante()== null)
                        idTipoSolicitante = listaSolicitantes.get(i).getTipoSolicitante().getIdTipoSolicitante();
                    else
                        idTipoSolicitante = elemento.getIdTipoSolicitante();
                    
//                    if(elemento.getIdTipoSolicitante()!= null
//                            && elemento.getTipoSolicitante().getIdTipoSolicitante().intValue() != elemento.getIdTipoSolicitante())
//                        elemento.getTipoSolicitante().setIdTipoSolicitante(elemento.getIdTipoSolicitante());
                    
                    if(idTipoSolicitante > 0)
                        elemento.getTipoSolicitante().setIdTipoSolicitante(idTipoSolicitante);
                    
                    solicitanteDatos.setTipoSolicitante(elemento.getTipoSolicitante());
                    solicitanteDatos.getNacionalidad().setIdPais(elemento.getNacionalidad().getIdPais());
                    
//                    log.info("TIPO PERSONA    "+elemento.getTipoPersona().getIdTipopersona());
//                    log.info("TIPO SOLICITANTE   "+solicitanteDatos.getTipoSolicitante().getDescripcion());
                    
                    if(elemento.getTipoPersona().getDescripcion() != null
                            && elemento.getTipoPersona().getDescripcion().equals("FÍSICA"))
                        elemento.getTipoPersona().setIdTipopersona(Constantes.INIT_SHORT);
                    
                    
                    solicitanteDatos.setTipoPersona(elemento.getTipoPersona());
                    
                    log.info("TIPO PERSONA    "+elemento.getTipoPersona().getIdTipopersona());
                    log.info("TIPO SOLICITANTE   "+solicitanteDatos.getTipoSolicitante().getDescripcion());

                    solicitanteDatos.setPrimerApellido(elemento.getPrimerApellido());
                    solicitanteDatos.setSegundoApellido(elemento.getSegundoApellido());

                    if (elemento.isAplicarDescuento() == true) {
                        solicitanteDatos.setAplicarDescuento(true);
                    }

                    solicitanteDatos.setInventor(elemento.getInventor());
                    if (elemento.getInventor().intValue() == 1) {
                        inventor = true;
                        copiarInventorSolicitante = false;
                    } else {
                        inventor = false;
                        copiarInventorSolicitante = true;
                    }
                    
                    log.info("TIPO PERSONA    "+elemento.getTipoPersona().getIdTipopersona());
                    log.info("TIPO SOLICITANTE   "+solicitanteDatos.getTipoSolicitante().getDescripcion());

                    solicitanteDatos.setPrincipal(elemento.getPrincipal());


                    domicilioSolicitante.paisCombo.setIdPais(elemento.getDomicilioObj().getPais().getIdPais());
                    if (elemento.getDatosContacto() != null) {
                        domicilioSolicitante.datosContacto.setCorreoelectronico(elemento.getDatosContacto().getCorreoelectronico());
                        domicilioSolicitante.datosContacto.setTelefono(elemento.getDatosContacto().getTelefono());
                        domicilioSolicitante.datosContacto.setFax(elemento.getDatosContacto().getFax());
                        domicilioSolicitante.datosContacto.setTelefonoExt(elemento.getDatosContacto().getTelefonoExt());
                    }
                    
                    log.info("TIPO PERSONA    "+elemento.getTipoPersona().getIdTipopersona());
                    log.info("TIPO SOLICITANTE   "+solicitanteDatos.getTipoSolicitante().getDescripcion());

                    solicitanteDatos.setIdSolicitante(elemento.getIdSolicitante());

                    if (domicilioSolicitante.paisCombo.getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                        domicilioSolicitante.entidadFederativaCombo.setIdEntidadFederativa(elemento.getDomicilioObj().getEntidad().getIdEntidadFederativa());
                    } else {
                        domicilioSolicitante.domicilio.setNombreEntidad(elemento.getDomicilioObj().getEntidad().getNombre());
                    }
                    editarSolicitante = true;
                    
                    log.info("TIPO PERSONA    "+elemento.getTipoPersona().getIdTipopersona());
                    log.info("TIPO SOLICITANTE   "+solicitanteDatos.getTipoSolicitante().getDescripcion());
                    habilitarTiposPersona();      
                    
                    log.info("TIPO PERSONA    "+elemento.getTipoPersona().getIdTipopersona());
                    log.info("TIPO SOLICITANTE   "+solicitanteDatos.getTipoSolicitante().getDescripcion());              
                    
                    patentesViewService.habilitarSolicitante(this);
                    break;
                }


            }
        } catch (Exception e) {
            log.fatal(e.getMessage());
        }

    }

    public void eliminarSolicitante(Persona sol) {

        String msg = "";
        boolean eliminar = true;

        if (eliminar == true) {

            verErrores = false;
            for (int i = 0; i < listaSolicitantes.size(); i++) {
                Persona elemento = (Persona) listaSolicitantes.get(i);
                if (sol.getIdSolicitante() == null) {
                    sol.setIdSolicitante(new Long(0));
                }

                if (sol.getIdSolicitante() == 0) {
                    if (elemento.getNombrecompleto().equals(sol.getNombrecompleto())) {
                        listaSolicitantes.remove(i);
                    }

                } else {

                    if (sol.getIdSolicitante() == elemento.getIdSolicitante()) {

                        listaSolicitantes.remove(i);
                        //tiene copia en inventores
                        if (sol.getInventor() != null && sol.getInventor().intValue() == 1) {
                            reemplazarInventor(sol, false);

                        }
                    }
                }
            }
            reglaSolicitanteApoderado();

        } else {
            convierteListaErrores(msg);
            verErrores = true;
        }
        if (listaSolicitantes.size() == 1) {
            listaSolicitantes.get(0).setPrincipal(new Short("1"));
            selectedSolicitante = listaSolicitantes.get(0);
            marcarSolicitanteAutomatico = true;
        }
    }

    public void reglaSolicitanteApoderado() {///checar apoderado agregar validacion 
        if (listaSolicitantes != null && listaSolicitantes.size() == 1
                && listaSolicitantes.get(0).getNombreConcatenado().trim().toUpperCase().equals(promovente.getNombreConcatenado().trim().toUpperCase())) {
            if (!tramitePat.getApoderados().isEmpty() && promovente.getIdUsuarioFirmante().equals(tramitePat.getApoderados().get(0).getIdUsuarioFirmante())) {
                if (tramitePat.getApoderados().size()==2){
                    selectedApoderado = tramitePat.getApoderados().get(0); 
                }else
                {    
                    tramitePat.getApoderados().remove(0);
                    this.setDomicilioContacto(null);
                    this.domicilioContacto=new IncludeDomicilio();
                    domicilioContacto.initDomicilio(paises, entidadesFederativas, flujosgralesViewService);
                    //tramitePat.setDomicilioObj(null);
                    
                    //Marcar el apoderado como principal cuando es unico
                    if (tramitePat.getApoderados().size() == 1) 
                    {
                        tramitePat.getApoderados().get(0).setPrincipal(new Short("1"));
                        selectedApoderado = tramitePat.getApoderados().get(0);
                    }
                }    
            }
        } else if (tramitePat.getApoderados().isEmpty()) {
            if (promovente.getTipoPersona().getIdTipopersona()==1 && promovente.getPrimerApellido()!=null)
            {
            String[] palabras = promovente.getNombrecompletotmp().split(" ");
            String nombreFormato = new String();
            for (int i=0;i<palabras.length;i++)
            {
                nombreFormato= nombreFormato + palabras[i].substring(0,1).toUpperCase()+ palabras[i].substring(1,palabras[i].length()).toLowerCase() + " ";
            }
                promovente.setNombreConcatenado(nombreFormato.trim() + " " + promovente.getPrimerApellido() + " " + promovente.getSegundoApellido());
                promovente.setNombrecompleto(nombreFormato.trim());
                promovente.setNombrecompletotmp(nombreFormato.trim());  
                //includeFirmante.getApoderado().setNombre(nombreFormato);
            }else
            {
                promovente.setNombreConcatenado(promovente.getNombrecompleto());
            }
            //promovente.setNombreConcatenado(promovente.getNombrecompleto());
            IncludeFirmante includePromovente;
            includePromovente= convertirPersonaToIncludeFirmante(promovente);
            if (includePromovente.getIncludeDomicilio()!=null){
               this.setDomicilioContacto(includePromovente.getIncludeDomicilio());
            }
            tramitePat.getApoderados().add(promovente);
            //Marcar el apoderado como principal cuando es unico
            if (tramitePat.getApoderados().size() == 1) {
                tramitePat.getApoderados().get(0).setPrincipal(new Short("1"));
                selectedApoderado = tramitePat.getApoderados().get(0);
            }
            //promovente.setPrincipal(null);
        } 
//        else if (!promovente.getIdUsuarioFirmante().equals(tramitePat.getApoderados().get(0).getIdUsuarioFirmante())) {
//            if (promovente.getTipoPersona().getIdTipopersona()==1 && promovente.getPrimerApellido()!=null)
//            {
//            String[] palabras = promovente.getNombrecompletotmp().split(" ");
//            String nombreFormato = new String();
//            for (int i=0;i<palabras.length;i++)
//            {
//                nombreFormato= nombreFormato + palabras[i].substring(0,1).toUpperCase()+ palabras[i].substring(1,palabras[i].length()).toLowerCase() + " ";
//            }
//                promovente.setNombreConcatenado(nombreFormato.trim() + " " + promovente.getPrimerApellido() + " " + promovente.getSegundoApellido());
//                promovente.setNombrecompleto(nombreFormato.trim());
//                promovente.setNombrecompletotmp(nombreFormato.trim());  
//                //includeFirmante.getApoderado().setNombre(nombreFormato);
//            }else
//            {
//                promovente.setNombreConcatenado(promovente.getNombrecompleto());
//            }
//            tramitePat.getApoderados().add(0, promovente);
//        }
        if (tramitePat.getApoderados().isEmpty()){
           //this.anexosApoderado.clear();
           this.lstAnexosApoderado.clear();
           if(tramitePat.getIdTramitePatente() != null && tramitePat.getIdTramitePatente() > 0){
               borrarPdfanexosApoderado();
            }
        }
            
    }

    public void agregarInventor() {
        
        
        if (banderaEditar == true) {

            posicionActualwe = Integer.parseInt(session.getAttribute("posicion").toString());
            
        }

        Persona elemento = new Persona();
        Datoscontacto datosCont = new Datoscontacto();
        Domicilio dom = new Domicilio();
        EntidadFederativa ent = new EntidadFederativa();
        Pais paisInventor = new Pais();
        EntidadFederativa entInventor = new EntidadFederativa();
        boolean insertar = true;
        String msgAviso = "";

        if (inventorDatos.getTipoPersona().getIdTipopersona().intValue() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mmensaje.error.tipo.persona") + "||";
        }
        this.inventorDatos.setNombrecompletotmp(this.inventorDatos.getNombrecompleto().trim());


        if (inventorDatos.getNombrecompleto() == null || inventorDatos.getNombrecompleto().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.nombre") + "||";
        } else {
            if (Pattern.matches(Constantes.pattern_nombre_personas, inventorDatos.getNombrecompleto()) == false) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.nombre.inventor.caracteres") + "||";
                insertar = false;
            }
        }
        
        if (inventorDatos.getPrimerApellido() == null  || inventorDatos.getPrimerApellido().equals("")) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.inventor.primerApellido") + "||";
        }
        
        
        if (inventorDatos.getNacionalidad().getIdPais() == 0) {
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.inventor.nacionalidad");
            insertar = false;
        }

        String primerApellido = "";
        String segundoApellido = "";

        primerApellido = this.inventorDatos.getPrimerApellido() == null ? "" : this.inventorDatos.getPrimerApellido().trim();
        segundoApellido = this.inventorDatos.getSegundoApellido() == null ? "" : this.inventorDatos.getSegundoApellido().trim();

        String nombreConcatenado = inventorDatos.getNombrecompleto() + " " + primerApellido + " " + segundoApellido;

        this.inventorDatos.setNombrecompletotmp(Util.reemplazaAcentos(nombreConcatenado));


        if (!editarInventor) {
            Collections.sort(this.listaInventores, new ComparatorPersona());


            if (Collections.binarySearch(this.listaInventores, this.inventorDatos, new ComparatorPersona()) >= 0) {
                msgAviso += "NOMBRE REPETIDO" + "||";
                insertar = false;
            }
        }

        if (domicilioInventor.paisCombo.getIdPais() != null && domicilioInventor.paisCombo.getIdPais().intValue() == Constantes.ID_PAIS.intValue()) {
            if (domicilioInventor.domicilio.getColonia() == null || domicilioInventor.domicilio.getColonia().length() == 0) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.colonia") + "||";
            }
        } else {
            if (domicilioInventor.paisCombo.getIdPais().intValue() == Constantes.FIRST) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.paisResidencia") + "||";            
            }
            
            if (domicilioInventor.domicilio.getNombreEntidad() == null || domicilioInventor.domicilio.getNombreEntidad().length() == 0) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.entidadInventor") + "||";
            }
        }

        if (domicilioInventor.domicilio.getCodigopostal() == null || domicilioInventor.domicilio.getCodigopostal().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.cp") + "||";

        }

        if (domicilioInventor.domicilio.getCalle() == null || domicilioInventor.domicilio.getCalle().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.calle") + "||";

        } else {
            if (Pattern.matches(Constantes.pattern_direccionesPatente, domicilioInventor.getDomicilio().getCalle()) == false) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.caracteres") + "||";
            }
            if (domicilioInventor.domicilio.getCalle().length() > Integer.parseInt(bundleParametrosPatentes.getString("domicilio.CalleNumero.maxlength"))) {
                insertar = false;
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.max") + "||";
            }
        }

        if (domicilioInventor.domicilio.getNumExt() == null || domicilioInventor.domicilio.getNumExt().length() == 0) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.calle.numExt") + "||";
        }

        if (domicilioInventor.datosContacto.getCorreoelectronico() != null ? domicilioInventor.datosContacto.getCorreoelectronico().length() > 0 ? !Pattern.matches(Constantes.PATTERN_EMAIL, domicilioInventor.datosContacto.getCorreoelectronico()) : false : false) {
            insertar = false;
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.validar.correo") + "||";
        }
        if (insertar) {
            dom.setIdPais(domicilioInventor.paisCombo.getIdPais());

            if (domicilioInventor.paisCombo.getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                dom.setIdEntidad(domicilioInventor.entidadFederativaCombo.getIdEntidadFederativa().toString());
                overrideNombreEntidadFederativa(2);
                ent.setNombre(domicilioInventor.entidadFederativaCombo.getNombre());
                dom.setEntidad(ent);
            }
            dom.setPoblacion(domicilioInventor.domicilio.getPoblacion());
            dom.setCodigopostal(domicilioInventor.domicilio.getCodigopostal());
            dom.setCalle(domicilioInventor.domicilio.getCalle());
            dom.setColonia(domicilioInventor.domicilio.getColonia());
            dom.setNumExt(domicilioInventor.domicilio.getNumExt());
            dom.setNumInt(domicilioInventor.domicilio.getNumInt());

            paisInventor.setIdPais(domicilioInventor.paisCombo.getIdPais());
            domicilioInventor.overrideNombrePais();
            paisInventor.setNombre(domicilioInventor.paisCombo.getNombre());
            dom.setPais(paisInventor);


            if (dom.getPais().getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                entInventor.setIdEntidadFederativa(domicilioInventor.entidadFederativaCombo.getIdEntidadFederativa());
                entInventor.setNombre(domicilioInventor.entidadFederativaCombo.getNombre());

            } else {
                entInventor.setNombre(domicilioInventor.domicilio.getNombreEntidad());

            }

            dom.setEntidad(entInventor);
            elemento.setDomicilioObj(dom);

            //datos de contacto

            datosCont.setCorreoelectronico(domicilioInventor.datosContacto.getCorreoelectronico());
            datosCont.setTelefono(domicilioInventor.datosContacto.getTelefono());
            datosCont.setFax(domicilioInventor.datosContacto.getFax());
            datosCont.setTelefonoExt(domicilioInventor.datosContacto.getTelefonoExt());
            elemento.setDatosContacto(datosCont);

            /*Vuelve a cargar el catalogo ya que se pierde la información. Al querer agregar un inventor 
             * con nacionalidad Mexicana después de agregar un inventor con nacionalidad extranjera*/
            if (inventorDatos.getNacionalidad().getIdPais().intValue() == Constantes.ID_PAIS.intValue()) {
                this.paises = catalogosViewService.consultarPaises(new Pais(Constantes.AREA_MARCAS, true));
            }

            //Construir solicitante

            elemento.setTipoPersona(inventorDatos.getTipoPersona());
            elemento.setNombrecompleto(inventorDatos.getNombrecompleto());
            elemento.setNombrecompletotmp(inventorDatos.getNombrecompletotmp());
            elemento.setIdNacionalidad(inventorDatos.getPais().getIdPais());
            elemento.setIdSolicitante(inventorDatos.getIdSolicitante());
            elemento.setRfc(inventorDatos.getRfc());
            elemento.setNacionalidad(inventorDatos.getNacionalidad());
            elemento.setPrimerApellido(inventorDatos.getPrimerApellido());
            elemento.setSegundoApellido(inventorDatos.getSegundoApellido());

            elemento.setNombreConcatenado(nombreConcatenado);

            if (inventorDatos.getInventor() != null) {
                if (inventorDatos.getInventor().intValue() == 1) {
                    elemento.setInventor(inventorDatos.getInventor());
                    elemento.setTipoSolicitante(inventorDatos.getTipoSolicitante());
                    elemento.setDescuento(inventorDatos.getDescuento());
                    elemento.setPrincipal(inventorDatos.getPrincipal());
                    elemento.setPrimerApellido(inventorDatos.getPrimerApellido());
                    elemento.setSegundoApellido(inventorDatos.getSegundoApellido());
                }
            }

            if (!editarInventor) {
                elemento.setIdSolicitante(new Integer(elemento.hashCode()).longValue());
                listaInventores.add(elemento);
            } else {
                elemento.setNombrecompleto(inventorDatos.getNombrecompleto());
                elemento.setNombrecompletotmp(inventorDatos.getNombrecompletotmp());
                // inventorDatos.setNombrecompleto("");

                for (int i = 0; i < listaInventores.size(); i++) {
                    Persona elementoLista = (Persona) listaInventores.get(i);
                    if (elemento.getIdSolicitante().longValue() == 0) {

                        if (elementoLista.getNombrecompleto().equals(nombreInventor) == true) {
                            //nombreInventor = "";
                            listaInventores.remove(i);
                            Collections.sort(this.listaInventores, new ComparatorPersona());


                            if (Collections.binarySearch(this.listaInventores, this.inventorDatos, new ComparatorPersona()) >= 0) {
                                msgAviso += "NOMBRE REPETIDO" + "||";
                                verErrores = true;
                                insertar = false;
                                break;
                            }

                            listaInventores.add(i, elemento);
                              
                            numElemto=listaInventores.size()-1;                       
                            
                            
                            break;
                        }
                    } else {

                        if (elementoLista.getIdSolicitante().longValue() == elemento.getIdSolicitante().longValue()) {
                            listaInventores.remove(i);
                            Collections.sort(this.listaInventores, new ComparatorPersona());


                            if (Collections.binarySearch(this.listaInventores, this.inventorDatos, new ComparatorPersona()) >= 0) {
                                msgAviso += "NOMBRE REPETIDO" + "||";
                                verErrores = true;
                                insertar = false;
                                break;
                            }
                            listaInventores.add(elemento);
                 
                            numElemto=listaInventores.size()-1;
                       
                            
                            if (elemento.getInventor() != null) {
                                if (elemento.getInventor().intValue() == 1) {
                                    reemplazarSolicitante(elemento, true);
                                }
                            }
                        }
                    }
                }
                editarInventor = false;
            }


            if (msgAviso != null && msgAviso.length() == 0) {
                domicilioInventor.setDomicilio(new DomicilioDto());
//                domicilioInventor.paisCombo.setIdPais(Constantes.ID_PAIS);
                domicilioInventor.setPaisCombo(new Pais());
                domicilioInventor.setCodigosPostalesTable(new ArrayList<CodigosPostales>());
                domicilioInventor.entidadFederativaCombo.setIdEntidadFederativa(0);
                inicializarPersona(2);
                domicilioInventor.setDatosContacto(new Datoscontacto());
                verPrioridad = false;
            }
            
            
        }


        log.info("total de inventores " + listaInventores.size());
        if (insertar == false) {
            //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgAviso, msgAviso);
            //FacesContext.getCurrentInstance().addMessage(null, message);
            convierteListaErrores(msgAviso);
            verErrores = true;
        } else {
            verErrores = false;
        }

        if (banderaEditar == true) {
            log.info(posicionActualwe);

            subirPosicionInventorEnt(numElemto, posicionActualwe);
        }

       banderaEditar = false;
    }

    public void cancelarInventor() {
        domicilioInventor.setDomicilio(new DomicilioDto());
        domicilioInventor.paisCombo.setIdPais(new Long(0));
        domicilioInventor.entidadFederativaCombo.setIdEntidadFederativa(0);
        inicializarPersona(2);
        domicilioInventor.setDatosContacto(new Datoscontacto());
        verPrioridad = false;
        this.editarInventor = false;

    }

    public void reemplazarInventor(Persona inventor, boolean editar) {

        inventorDatos.setNombrecompleto("");

        for (int i = 0; i < listaInventores.size(); i++) {
            Persona elementoLista = (Persona) listaInventores.get(i);
            if (inventor.getIdSolicitante().longValue() == 0) {

                if (elementoLista.getNombrecompleto().equals(nombreInventor) == true) {
                    nombreInventor = "";
                    listaInventores.remove(i);
                    listaInventores.add(i, inventor);
                    break;
                }
            } else {

                if (elementoLista.getIdSolicitante().longValue() == inventor.getIdSolicitante().longValue()) {
                    listaInventores.remove(i);
                    if (editar == true) {
                        listaInventores.add(i, inventor);
                    }

                }
            }
        }
    }

    public void reemplazarSolicitante(Persona solicitante, boolean editar) {

        inventorDatos.setNombrecompleto("");

        for (int i = 0; i < listaSolicitantes.size(); i++) {
            Persona elementoLista = (Persona) listaSolicitantes.get(i);
            if (solicitante.getIdSolicitante().longValue() == 0) {

                if (elementoLista.getNombrecompleto().equals(nombreSolicitante) == true) {
                    nombreInventor = "";
                    listaSolicitantes.remove(i);
                    listaSolicitantes.add(i, solicitante);
                    break;
                }
            } else {

                if (elementoLista.getIdSolicitante().longValue() == solicitante.getIdSolicitante().longValue()) {
                    listaSolicitantes.remove(i);
                    if (editar == true) {
                        listaSolicitantes.add(solicitante);
                    }

                    if (solicitante.getPrincipal() != null && solicitante.getPrincipal().intValue() == 1) {
                        if (editar == true) {
                            selectedSolicitante = solicitante;
                        } else {
                            selectedSolicitante = new Persona();
                        }

                    }
                }
            }
        }
        reglaSolicitanteApoderado();
    }

    public void editarInventor(Persona inventor) {
         
        setPosicionActualwe(listaInventores.indexOf(inventor));

        posicionActualwe = listaInventores.indexOf(inventor);


        session.removeAttribute("posicion");
        session.setAttribute("posicion", posicionActualwe);
        
        boolean encontrado = false;
        for (int i = 0; i < listaInventores.size(); i++) {
            Persona elemento = (Persona) listaInventores.get(i);
            if (inventor.getIdSolicitante() == null) {
                inventor.setIdSolicitante(new Long(0));
            }

            if (inventor.getIdSolicitante() == 0) {
                if (elemento.getNombrecompleto().equals(inventor.getNombrecompleto())) {
                    encontrado = true;
                    nombreInventor = elemento.getNombrecompleto();
                }

            } else {

                if (inventor.getIdSolicitante() == elemento.getIdSolicitante()) {
                    encontrado = true;

                }
            }
            if (encontrado) {
                domicilioInventor.domicilio.setCalle(elemento.getDomicilioObj().getCalle());
                domicilioInventor.domicilio.setColonia(elemento.getDomicilioObj().getColonia());
                domicilioInventor.domicilio.setCodigopostal(elemento.getDomicilioObj().getCodigopostal());
                domicilioInventor.domicilio.setPoblacion(elemento.getDomicilioObj().getPoblacion());
                domicilioInventor.domicilio.setNumExt(elemento.getDomicilioObj().getNumExt());
                domicilioInventor.domicilio.setNumInt(elemento.getDomicilioObj().getNumInt());
                inicializarPersona(2);
                inventorDatos.setNombrecompleto(elemento.getNombrecompleto());
                inventorDatos.setRfc(elemento.getRfc());
                inventorDatos.getNacionalidad().setIdPais(elemento.getNacionalidad().getIdPais());
                inventorDatos.setIdTipopersona(elemento.getIdTipopersona());
                inventorDatos.setPrimerApellido(elemento.getPrimerApellido());
                inventorDatos.setSegundoApellido(elemento.getSegundoApellido());

                if (elemento.getInventor() != null) {
                    if (elemento.getInventor().intValue() == 1) {
                        inventorDatos.setInventor(elemento.getInventor());
                        inventorDatos.setTipoSolicitante(elemento.getTipoSolicitante());
                        inventorDatos.setDescuento(elemento.getDescuento());
                        inventorDatos.setPrincipal(elemento.getPrincipal());
                        inventorDatos.setPrimerApellido(elemento.getPrimerApellido());
                        inventorDatos.setSegundoApellido(elemento.getSegundoApellido());
                    }
                }



                domicilioInventor.paisCombo.setIdPais(elemento.getDomicilioObj().getPais().getIdPais());
                if (elemento.getDatosContacto() != null) {
                    domicilioInventor.datosContacto.setCorreoelectronico(elemento.getDatosContacto().getCorreoelectronico());
                    domicilioInventor.datosContacto.setTelefono(elemento.getDatosContacto().getTelefono());
                    domicilioInventor.datosContacto.setFax(elemento.getDatosContacto().getFax());
                    domicilioInventor.datosContacto.setTelefonoExt(elemento.getDatosContacto().getTelefonoExt());
                }

                inventorDatos.setIdSolicitante(elemento.getIdSolicitante());

                if (domicilioInventor.paisCombo.getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                    domicilioInventor.entidadFederativaCombo.setIdEntidadFederativa(elemento.getDomicilioObj().getEntidad().getIdEntidadFederativa());
                } else {
                    domicilioInventor.domicilio.setNombreEntidad(elemento.getDomicilioObj().getEntidad().getNombre());
                }
                editarInventor = true;

                break;

            }


        }
         banderaEditar = true;
    }

    public void eliminarInventor(Persona inventor) {

        String msg = "";
        boolean eliminar = true;

        if (eliminar == true) {

            verErrores = false;
            for (int i = 0; i < listaInventores.size(); i++) {
                Persona elemento = (Persona) listaInventores.get(i);
                if (inventor.getIdSolicitante() == null) {
                    inventor.setIdSolicitante(new Long(0));
                }

                if (inventor.getIdSolicitante() == 0) {
                    if (elemento.getNombrecompleto().equals(inventor.getNombrecompleto())) {
                        listaInventores.remove(i);
                    }

                } else {

                    if (inventor.getIdSolicitante() == elemento.getIdSolicitante()) {
                        listaInventores.remove(i);
                        //Es copia de un solicitante
                        if (inventor.getInventor() != null && inventor.getInventor().intValue() == 1) {
                            reemplazarSolicitante(inventor, false);
                            if (listaSolicitantes.size() == 1) {
                                listaSolicitantes.get(0).setPrincipal(new Short("1"));
                                selectedSolicitante = listaSolicitantes.get(0);
                            }
                        }
                    }
                }
            }
        } else {
            convierteListaErrores(msg);
            verErrores = true;
        }
        cancelarInventor();

    }

    public void overrideNombrePais() {
        if (log.isInfoEnabled()) {
            log.info("***********+overrideNombrePais************");
        }

        for (Pais pais : paisesPrioridad) {

            if (datosPrioridad.getIdPais().intValue() == pais.getIdPais().intValue()) {
                if (log.isInfoEnabled()) {
                    log.info("idPais prioridad: " + pais.getIdPais());
                    log.info("Nombre prioridAD: " + pais.getNombre());
                }
                datosPrioridad.setNombrePais(pais.getNombre());
                break;
            }

        }
    }

    public void borrarFecha(int origen) {
        if (origen == 1) {
            tramitePat.setFechaDivPrevia(null);
        } else {
            tramitePat.setFechaPresentacionExp(null);
        }
    }
    
    
    
       public void borrarFechaDivPrevia(int origen) {
        if (origen == 1) {
            tramitePat.setFechaDivPrevia(null);
            tramitePat.setFechaDivulgacion(null);
        } else {
            tramitePat.setFechaPresentacionExp(null);
        }
        
         docDivPrevia = false;
        
    }
    
    
    
    
    public void borrarArchivoDivPrevia(){    
        
       if(tramitePat.getIdTramitePatente() != null && tramitePat.getIdTramitePatente() > 0){
            patentesViewService.borrarPdfDivulgacionPrevia(this, idTramite);
        }
        
       forma.setRutaArcDivPre("");
    } 
    

    private String validaPrioridad() {
        String msg = "";

        datosPrioridad.setNumeroExpedienteTmp(Util.reemplazaAcentos(datosPrioridad.getNumeroExpediente().trim().replaceAll("[\u002F\u002C\u002E]", "")));
        //datosPrioridad.setNumeroExpedienteTmp(datosPrioridad.getNumeroExpediente().replaceAll("[/,.]",""));

        if (datosPrioridad.getIdPais() == null || datosPrioridad.getIdPais().intValue() == 0) {
            msg += bundleParametrosPatentes.getString("mensaje.pais.prioridad") + "||";
        }


        if (datosPrioridad.getFechaPresentacionExt() == null) {

            msg += bundleParametrosPatentes.getString("mensaje.sin.fecha.prioridad") + "||";
        }

        if (datosPrioridad.getNumeroExpediente() != null && datosPrioridad.getNumeroExpediente().length() != 0) {

            if (Pattern.matches(Constantes.pattern_numero_prioridad, datosPrioridad.getNumeroExpediente()) == false) {
                msg += bundleParametrosPatentes.getString("mensaje.error.numero.prioridad") + "||";
            }

        } else {
            msg += bundleParametrosPatentes.getString("mensaje.sin.numero.prioridad") + "||";
        }



        boolean isValid = true;
        if (this.tramitePat.getTipoSol().getIdTiposolicitud().longValue() == 9 & this.tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == 27) {
            if (this.tramitePat.getNumSolPCT() != null) {

                if (this.datosPrioridad.getNumeroExpediente().equals(this.tramitePat.getNumSolPCT())) {
                    isValid = false;
                }
            }

        }


        if (this.tramitePat.getTipoSol().getIdTiposolicitud().longValue() == 10 & this.tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == 11) {
            if (this.tramitePat.getNumSolPCT() != null) {

                if (this.datosPrioridad.getNumeroExpediente().equals(this.tramitePat.getNumSolPCT())) {
                    isValid = false;
                }

            }

        }

        if (!isValid) {
            msg += "Validacion PCT y prioridad invalida " + "||";
        }

        /*
         * Comparator<Prioridad> cmp = new Comparator<Prioridad>() {
         *
         * public int compare(Prioridad p1, Prioridad p2) {
         *
         * int pais = p1.getIdPais().compareTo(p2.getIdPais());
         *
         * return ((pais == 0) ?
         * p1.getNumeroExpedienteTmp().compareTo(p2.getNumeroExpedienteTmp()) :
         * pais);
         *
         * }
         * };
         *
         * Collections.sort(this.listaPrioridades, cmp); if
         * (Collections.binarySearch(this.listaPrioridades, this.datosPrioridad,
         * cmp) >= 0) { msg += "PRIORIDAD REPETIDA" + "||"; }
         *
         *
         */
        return msg;

    }
    
        public void cargarArchivos(FileUploadEvent event) {
        AnexosUtil anexosUtil = new AnexosUtil();
        forma.setFileFigura( event.getFile());
        if ( forma.getFileFigura() != null) {
            forma.setRutaImagen(forma.getFileFigura().getFileName());
            file = forma.getFileFigura();
            upload();
        }

    }
    

    public void cargarArchivo() {
        AnexosUtil anexosUtil = new AnexosUtil();
//        if (forma.getFileFigura() != null) {
//
//            forma.setRutaImagen(forma.getFileFigura().getFileName());
//            file = forma.getFileFigura();
//        }
//        upload();
        if (forma.getFileFiguras() != null) {
            for(UploadedFile Figura:forma.getFileFiguras()){
                forma.setRutaImagen(Figura.getFileName());
                file = Figura;
                upload();
            }    
        }

    }
    
    
      public void cargarArchivoDescripcion() {
        AnexosUtil anexosUtil = new AnexosUtil();
        log.info("Cargar archivo descripcion");
        System.out.println("cargando archivo");

        if (forma.getFileDesc() != null) {

            //Validacion tipo
            if (validasDesc(forma.getFileDesc().getContentType())) {

                UploadedFile fileDescripcion = forma.getFileDesc();

                forma.setRutaDesc(forma.getFileDesc().getFileName());
                file = forma.getFileDesc();

                //Validacio virus
//                    if (anexosUtil.deteccionVrus(fileDescripcion)) {

                anexoDescripcion = new AnexosViewDto();
                anexoDescripcion.setArchivoAnexo(fileDescripcion.getContents());
                String extension = FileServicesUtil.getExtension(fileDescripcion.getFileName());
                anexoDescripcion.setExtension(extension);
                anexoDescripcion.setNombreArchivo(fileDescripcion.getFileName());
                anexoDescripcion.setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);

                System.out.println("Se establecieron las propiedades");

//                    } else {
                //Mensaje de error virus
//                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", bundleParametrosPatentes.getString("mensaje.error.anexo.virus"));
//                        FacesContext.getCurrentInstance().addMessage(null, message);
//                    }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", bundleParametrosPatentes.getString("mensaje.error.tipo.archivo"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }

    }
    
    public void cargarFileDescMemoria() {
            AnexosUtil anexosUtil = new AnexosUtil();
        log.info("Cargar archivo descripcion");
        log.info("CHECAR:   "+tramitePat.getInvencion());
        log.info("Default Charset=" + Charset.defaultCharset());

        if (forma.getFileDesc() != null) {
        log.info("CHECAR:   "+tramitePat.getInvencion());
        log.info("Default Charset=" + Charset.defaultCharset());

            //Validacion tipo
            if (validasDesc(forma.getFileDesc().getFileName())) {

                UploadedFile fileDescripcion = forma.getFileDesc();

                forma.setRutaDesc(forma.getFileDesc().getFileName());
                file = forma.getFileDesc();

                forma.setAnexoDescripcionMe(new AnexosViewDto());
                forma.getAnexoDescripcionMe().setArchivoAnexo(fileDescripcion.getContents());
                String extension = FileServicesUtil.getExtension(fileDescripcion.getFileName());
                forma.getAnexoDescripcionMe().setExtension(extension);
                forma.getAnexoDescripcionMe().setNombreArchivo(fileDescripcion.getFileName());
                forma.getAnexoDescripcionMe().setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);
                
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", bundleParametrosPatentes.getString("mensaje.error.tipo.archivo"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
        log.info("CHECAR:   "+tramitePat.getInvencion());

    }
    
     public void cargarFilePrioridad() {
        AnexosUtil anexosUtil = new AnexosUtil();
        log.info("Cargar archivo descripcion");
        log.info("CHECAR:   "+tramitePat.getInvencion());
        log.info("Default Charset=" + Charset.defaultCharset());

        if (forma.getFileDesc() != null) {
        log.info("CHECAR:   "+tramitePat.getInvencion());
        log.info("Default Charset=" + Charset.defaultCharset());

            //Validacion tipo
            if (validasDesc(forma.getFileDesc().getFileName())) {

                UploadedFile fileDescripcion = forma.getFileDesc();

                forma.setRutaDesc(forma.getFileDesc().getFileName());
                file = forma.getFileDesc();

                forma.setAnexoDescripcionMe(new AnexosViewDto());
                forma.getAnexoDescripcionMe().setArchivoAnexo(fileDescripcion.getContents());
                String extension = FileServicesUtil.getExtension(fileDescripcion.getFileName());
                forma.getAnexoDescripcionMe().setExtension(extension);
                forma.getAnexoDescripcionMe().setNombreArchivo(fileDescripcion.getFileName());
                forma.getAnexoDescripcionMe().setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);
                
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", bundleParametrosPatentes.getString("mensaje.error.tipo.archivo"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
        log.info("CHECAR:   "+tramitePat.getInvencion());

    }
    
    
        public void handleFileUpload(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 

    public void cargarFileResMemoria() {
        AnexosUtil anexosUtil = new AnexosUtil();
        log.info("Cargar archivo descripcion");

        if (forma.getFileResumen() != null) {

            //Validacion tipo
            if (validasDesc(forma.getFileResumen().getContentType())) {

                UploadedFile fileResumen = forma.getFileResumen();

                forma.setRutaResum(forma.getFileResumen().getFileName());
                file = forma.getFileDesc();

                forma.setAnexoResumen(new AnexosViewDto());
                forma.getAnexoResumen().setArchivoAnexo(fileResumen.getContents());
                String extension = FileServicesUtil.getExtension(fileResumen.getFileName());
                forma.getAnexoResumen().setExtension(extension);
                forma.getAnexoResumen().setNombreArchivo(fileResumen.getFileName());
                forma.getAnexoResumen().setIdTipoanexo(Constantes.ANEXO_RESUMEN_MEMORIA);
                
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", bundleParametrosPatentes.getString("mensaje.error.tipo.archivo"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }

    }

    public void pruebaFechas(){
        
     Date fecha1 =  tramitePat.getFechaDivPrevia();
     
     if(fecha1 == null){
            docDivPrevia = false;
        
    }else{
            docDivPrevia = true;
     }
     
   }
    

    public void cargarArchivoDivulgacionPrevia() {
        AnexosUtil anexosUtil = new AnexosUtil();
        log.info("Cargar archivo divulgacion previa");
        System.out.println("cargando archivo");
        
        if (forma.getFileArcDivPre() != null) {

            //Validacion tipo
            if (validasDesc(forma.getFileArcDivPre().getContentType())) {

                UploadedFile fileArcDivulgacion = forma.getFileArcDivPre();

                forma.setRutaArcDivPre(forma.getFileArcDivPre().getFileName());
                file = forma.getFileArcDivPre();

                //Validacio virus
//                    if (anexosUtil.deteccionVrus(fileArcDivulgacion)) {

                anexoArcDivPre = new AnexosViewDto();
                anexoArcDivPre.setArchivoAnexo(fileArcDivulgacion.getContents());
                String extension = FileServicesUtil.getExtension(fileArcDivulgacion.getFileName());
                anexoArcDivPre.setExtension(extension);
                anexoArcDivPre.setNombreArchivo(fileArcDivulgacion.getFileName());
                anexoArcDivPre.setIdTipoanexo(Constantes.ANEXO_DIV_PREVIA_PAT);

                System.out.println("Se establecieron las propiedades");

//                    } else {
                //Mensaje de error virus
//                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", bundleParametrosPatentes.getString("mensaje.error.anexo.virus"));
//                        FacesContext.getCurrentInstance().addMessage(null, message);
//                    }
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", bundleParametrosPatentes.getString("mensaje.error.tipo.archivo"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }

    }
    

    public void cargarArchivoReivindicacion() {
        AnexosUtil anexosUtil = new AnexosUtil();
        log.info("Cargar archivo reivindicacion");
        System.out.println("cargando archivo");

        if (forma.getFileReivindicacion() != null) {

            //Validacion tipo
            if (validasDesc(forma.getFileReivindicacion().getContentType())) {

                UploadedFile fileReivindicacion = forma.getFileReivindicacion();

                forma.setRutaReiv(forma.getFileReivindicacion().getFileName());
                file = forma.getFileReivindicacion();

                //Validacio virus
//                    if (anexosUtil.deteccionVrus(fileDescripcion)) {

                anexoReivindicacion = new AnexosViewDto();
                anexoReivindicacion.setArchivoAnexo(fileReivindicacion.getContents());
                String extension = FileServicesUtil.getExtension(fileReivindicacion.getFileName());
                anexoReivindicacion.setExtension(extension);
                anexoReivindicacion.setNombreArchivo(fileReivindicacion.getFileName());
                anexoReivindicacion.setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);

                System.out.println("Se establecieron las propiedades");

//                    } else {
                //Mensaje de error virus
//                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", bundleParametrosPatentes.getString("mensaje.error.anexo.virus"));
//                        FacesContext.getCurrentInstance().addMessage(null, message);
//                    }
            } else {
                //MENSAJE DE ERROR TIPO
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", bundleParametrosPatentes.getString("mensaje.error.tipo.archivo"));
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }

    }

    public void agregarPrioridad() {
       
        try
        {
        Prioridad elemento = new Prioridad();
        boolean insertar = true;
        String msgAviso = "";

        msgAviso = validaPrioridad();
        if (msgAviso.length() != 0) {
            insertar = false;
        }

        if (insertar) {
            elemento.setIdPais(datosPrioridad.getIdPais());
            overrideNombrePais();
            elemento.setNombrePais(datosPrioridad.getNombrePais());
            elemento.setFechaPresentacionExt(datosPrioridad.getFechaPresentacionExt());
            elemento.setNumeroExpediente(datosPrioridad.getNumeroExpediente());
            elemento.setFechaPresentacion(Util.formatearFecha(datosPrioridad.getFechaPresentacionExt(), "dd/MM/yyyy"));
            elemento.setIdPrioridad(datosPrioridad.getIdPrioridad());


            if (!editarPrioridad) {

                Collections.sort(this.listaPrioridades, new ComparatorPrioridad());
                if (Collections.binarySearch(this.listaPrioridades, this.datosPrioridad, new ComparatorPrioridad()) >= 0) {
                    msgAviso += "PRIORIDAD REPETIDA" + "||";
                    insertar = false;

                } else {
                    idProvPrioridad++;
                    elemento.setIdProvisional(idProvPrioridad);
                    listaPrioridades.add(elemento);
                }

            } else {
                for (int i = 0; i < listaPrioridades.size(); i++) {
                    Prioridad elementoLista = (Prioridad) listaPrioridades.get(i);
                    if (elemento.getIdPrioridad() == null || elemento.getIdPrioridad().longValue() == 0) {

                        if (elementoLista.getIdProvisional() == replica.getIdProvisional()) {
                            elemento.setIdProvisional(replica.getIdProvisional());
                            replica = new Prioridad();
                            listaPrioridades.remove(i);



                            Collections.sort(this.listaPrioridades, new ComparatorPrioridad());
                            if (Collections.binarySearch(this.listaPrioridades, this.datosPrioridad, new ComparatorPrioridad()) >= 0) {
                                msgAviso += "PRIORIDAD REPETIDA" + "||";
                                insertar = false;
                                break;
                            } else {
                                listaPrioridades.add(i, elemento);

                                break;
                            }

                        }
                    } else {

                        if (elementoLista.getIdPrioridad().longValue() == elemento.getIdPrioridad().longValue()) {
                            listaPrioridades.remove(i);




                            Collections.sort(this.listaPrioridades, new ComparatorPrioridad());
                            if (Collections.binarySearch(this.listaPrioridades, this.datosPrioridad, new ComparatorPrioridad()) >= 0) {
                                msgAviso += "PRIORIDAD REPETIDA" + "||";
                                insertar = false;
                            } else {
                                listaPrioridades.add(elemento);
                            }

                            // listaPrioridades.add(elemento);
                        }
                    }
                }
                editarPrioridad = false;
             }


            if (msgAviso != null && msgAviso.length() == 0 ) {
                cancelarPrioridad();
            }
        }
        
        
        if (insertar == false) {
            //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgAviso, msgAviso);
            //FacesContext.getCurrentInstance().addMessage(null, message);
            convierteListaErrores(msgAviso);
            verErrores = true;
        } else {
            verErrores = false;
            this.agregarPrioridad=1;
            habilitarPrioridad(1);
        }
      } catch (Exception e) {
            log.error("Error:", e);
      }  
    }

    public void cancelarPrioridad() {
       try
       {
        datosPrioridad = new Prioridad();
        //agregarPrioridad = null;
//        datosPrioridad.setIdPais(elemento.getIdPais());
//        datosPrioridad.setFechaPresentacionExt(elemento.getFechaPresentacionExt());
//        datosPrioridad.setNumeroExpediente(elemento.getNumeroExpediente());
        verPrioridad = false;
        verPreguntaPrioridad = true;
        editarPrioridad=false;
       // forma.setRenderedPrioridades(true);
        this.disabledEditarPri = "false";
        this.disabledEliminar = "false";
        if (agregarPrioridad == 1)
        {   
            habilitarPrioridad(1);

        }
      } catch (Exception e) {
            log.error("Error:", e);
      }  
    }
    
    public void cancelaOpcionPriori() {
       try
       {    
            this.agregarPrioridad=1;
            habilitarPrioridad(1); 
       } catch (Exception e) {
            log.error("Error:", e);
         }  
    }
    
          
    public void editarPrioridad(Prioridad prior) {
        this.disabledEditarPri = "true";
        this.disabledEliminar = "true";
        boolean encontrado = false;
        replica = new Prioridad();
        this.forma.setRenderedPrioridades(false); 
        for (int i = 0; i < listaPrioridades.size(); i++) {
            Prioridad elemento = (Prioridad) listaPrioridades.get(i);
            if (prior.getIdPrioridad() == null) {
                prior.setIdPrioridad(new Long(0));
            }

            if (prior.getIdPrioridad() == 0) {
                elemento.getIdPrioridad().equals(i);
                if (elemento.getIdProvisional() == prior.getIdProvisional()) {
                    encontrado = true;
                    replica.setIdProvisional(prior.getIdProvisional());
                    verPrioridad = true;
                    agregarPrioridad = new Integer(1);
                    verPreguntaPrioridad = false;
                }

            } else {

                if (prior.getIdPrioridad().longValue() == elemento.getIdPrioridad().longValue()) {
                    encontrado = true;
                    agregarPrioridad = new Integer(1);
                    verPrioridad = true;
                    verPreguntaPrioridad = false;
                }
            }
            if (encontrado) {
                datosPrioridad.setIdPrioridad(elemento.getIdPrioridad());
                datosPrioridad.setIdPais(elemento.getIdPais());
                datosPrioridad.setFechaPresentacionExt(elemento.getFechaPresentacionExt());
                datosPrioridad.setNumeroExpediente(elemento.getNumeroExpediente());
                datosPrioridad.setIdProvisional(elemento.getIdProvisional());

                editarPrioridad = true;
                break;
            }


        }
    }
    
     public void eliminarPrioridad(Prioridad prior) {

        String msg = "";
        boolean eliminar = true;

        if (eliminar == true) {
            
           // listaPrioridades.remove(prior.getIdPrioridad().intValue());
            verErrores = false;
            for (int i = 0; i < listaPrioridades.size(); i++) {
                Prioridad elemento = (Prioridad) listaPrioridades.get(i);
                if (prior.getIdPrioridad() == null) {
                    prior.setIdPrioridad(new Long(0));
                }

                if (prior.getIdPrioridad() == 0) {
                    if (elemento.getIdProvisional() == prior.getIdProvisional()) {
                        log.info("PRIORIDAD:  " + listaPrioridades.get(i).getIdPrioridad());
//                        Prioridad prioridadDB = flujosgralesViewService.selectPrioridadByPrimaryKey(
//                                listaPrioridades.get(i).getIdPrioridad());
//
//                        if (prioridadDB.getIdAnexoPrioridad() != null && prioridadDB.getIdAnexoPrioridad() > 0) {
//                            flujosgralesViewService.deleteAnexosByIds(prioridadDB.getIdAnexoPrioridad());
//                        }
//                        if (prioridadDB.getIdAnexoTraduccion() != null && prioridadDB.getIdAnexoTraduccion() > 0) {
//                            flujosgralesViewService.deleteAnexosByIds(prioridadDB.getIdAnexoTraduccion());
//                        }
                        
                        listaPrioridades.remove(i);
                        
                    }
                } else {
                    if (prior.getIdPrioridad() == elemento.getIdPrioridad()) {
                        
                        Prioridad prioridadDB = flujosgralesViewService.selectPrioridadByPrimaryKey(
                                listaPrioridades.get(i).getIdPrioridad());

                        if (prioridadDB.getIdAnexoPrioridad() != null && prioridadDB.getIdAnexoPrioridad() > 0) {
                            flujosgralesViewService.deleteAnexosByIds(prioridadDB.getIdAnexoPrioridad());
                        }
                        if (prioridadDB.getIdAnexoTraduccion() != null && prioridadDB.getIdAnexoTraduccion() > 0) {
                            flujosgralesViewService.deleteAnexosByIds(prioridadDB.getIdAnexoTraduccion());
                        }

                        prioridadDB.setIdAnexoPrioridad(null);
                        prioridadDB.setIdAnexoTraduccion(null);
                        int resuls = flujosgralesViewService.updatePrioridadByPrimaryKey(prior);
                        log.info("Se actualizo el registro   " + resuls);
                        
                        listaPrioridades.remove(i);
                        
                        }
                    }
                }
        } else {
            convierteListaErrores(msg);
            verErrores = true;
        }

    } 
       
    public void eliminarPrioridades(Prioridad prior) {

        String msg = "";
        boolean eliminar = true;
        
        if (eliminar == true) {
            
            verErrores = false;
            int lista=listaPrioridades.size();
            for (int i = lista-1 ; i >= 0; i--) {
                Prioridad elemento = (Prioridad) listaPrioridades.get(i);
                if (elemento.getIdPrioridad() == null) {
                    elemento.setIdPrioridad(new Long(0));
                }

                if (elemento.getIdPrioridad() == 0) {
                        
                        listaPrioridades.remove(i);

                } else {
                  //  if (prior.getIdPrioridad() == elemento.getIdPrioridad()) {
                        
                        Prioridad prioridadDB = flujosgralesViewService.selectPrioridadByPrimaryKey(
                                listaPrioridades.get(i).getIdPrioridad());

                        if (prioridadDB.getIdAnexoPrioridad() != null && prioridadDB.getIdAnexoPrioridad() > 0) {
                            flujosgralesViewService.deleteAnexosByIds(prioridadDB.getIdAnexoPrioridad());
                        }
                        if (prioridadDB.getIdAnexoTraduccion() != null && prioridadDB.getIdAnexoTraduccion() > 0) {
                            flujosgralesViewService.deleteAnexosByIds(prioridadDB.getIdAnexoTraduccion());
                        }

                        prioridadDB.setIdAnexoPrioridad(null);
                        prioridadDB.setIdAnexoTraduccion(null);
                        int resuls = flujosgralesViewService.updatePrioridadByPrimaryKey(prior);
                        log.info("Se actulizo el registro   " + resuls);
                        
                        listaPrioridades.remove(i);
                        
                     //   }
                    }
                }
        } else {
            convierteListaErrores(msg);
            verErrores = true;
        }

    }

    public void convierteListaErrores(String error) {
        errores = new ArrayList<String>();
        if (error != null && error.length() != 0) {
            StringTokenizer st = new StringTokenizer(error, "||");

            while (st.hasMoreTokens()) {
                errores.add(st.nextToken());
            }

        }


    }

    public String buscarExpDivisional() {
        Solicitud solBuscar = new Solicitud();
        String msg = "";
        solicitudesSgapatTable = new ArrayList<Solicitud>();
        //validar el formato del expediente
        
        
        if (tramitePat.getExpDivisional() != null && tramitePat.getExpDivisional().length() != 0 && tramitePat.getExpDivisional().length() >= 10) {
            solBuscar.setCodOficina(tramitePat.getExpDivisional().substring(0, 2));

            if ((solBuscar.getCodOficina().equals("GT") || solBuscar.getCodOficina().equals("JL")
                    || solBuscar.getCodOficina().equals("NL") || solBuscar.getCodOficina().equals("PA")
                    || solBuscar.getCodOficina().equals("YU") || solBuscar.getCodOficina().equals("MX")) && tramitePat.getExpDivisional().substring(2, 3).equals("/")) {
                solBuscar.setTipExped(tramitePat.getExpDivisional().substring(3, 4));

                if ((solBuscar.getTipExped().equals("a") || solBuscar.getTipExped().equals("f")
                        || solBuscar.getTipExped().equals("t") || solBuscar.getTipExped().equals("u"))
                        && tramitePat.getExpDivisional().substring(4, 5).equals("/")) {

                    try {
                        solBuscar.setSerExped(new Integer(tramitePat.getExpDivisional().substring(5, 9)));
                        if (tramitePat.getExpDivisional().substring(9, 10).equals("/")) {
                            solBuscar.setNumExped(new Long(tramitePat.getExpDivisional().substring(10)));
                            if (tramitePat.getExpDivisional().substring(10).length() < 6) {
                                String leftPadding = "";
                                for (int i = tramitePat.getExpDivisional().substring(10).length(); i < 6; i++) {
                                    leftPadding += "0";
                                }
                                tramitePat.setExpDivisional(tramitePat.getExpDivisional().substring(0, 10) + leftPadding + solBuscar.getNumExped());
                            }
                            if (!solBuscar.getCodOficina().equals("MX") && solBuscar.getSerExped().intValue() > 2006) {
                                msg = bundleParametrosPatentes.getString("mensaje.error.validar.anio.oficina") + "||";
                            } else if (solBuscar.getCodOficina().equals("MX") && solBuscar.getSerExped().intValue() <= 2006) {
                                msg = bundleParametrosPatentes.getString("mensaje.error.validar.anio.oficina") + "||";
                            }else if(solBuscar.getSerExped().intValue()> (new Date().getYear()+1900)){
                                msg = bundleParametrosPatentes.getString("mensaje.error.validar.exp.divisional.año") + "||";
                            } 
                        }else {
                            msg = bundleParametrosPatentes.getString("mensaje.error.validar.exp.divisional") + "||";
                        }
                    } catch (Exception e) {
                        if (solBuscar.getSerExped() == null) {
                            msg = bundleParametrosPatentes.getString("mensaje.error.validar.sin.anio") + "||";
                        } else {
                            msg = bundleParametrosPatentes.getString("mensaje.error.validar.sin.num.exp") + "||";
                        }

                    }

                } else {
                    msg = bundleParametrosPatentes.getString("mensaje.error.validar.sin.tipo.exp") + "||";
                }

            } else {
                msg = bundleParametrosPatentes.getString("mensaje.error.validar.sin.cod.ofc") + "||";
            }
        } else {
            msg = bundleParametrosPatentes.getString("mensaje.error.validar.exp.divisional") + "||";
        }

        return msg;

    }

    public void asignarFechaDivisional() {
        String expDivisional = "";
        expDivisional = solSeleccionada.getCodOficina() + "/" + solSeleccionada.getTipExped() + "/" + solSeleccionada.getSerExped();
        int longitud = solSeleccionada.getNumExped().toString().length();
        switch (longitud) {
            case 1:
                expDivisional += "00000" + solSeleccionada.getNumExped();
                break;
            case 2:
                expDivisional += "0000" + solSeleccionada.getNumExped();
                break;
            case 3:
                expDivisional += "000" + solSeleccionada.getNumExped();
                break;
            case 4:
                expDivisional += "00" + solSeleccionada.getNumExped();
                break;
            case 5:
                expDivisional += "0" + solSeleccionada.getNumExped();
                break;
            case 6:
                expDivisional += solSeleccionada.getNumExped();
                break;

        }


        tramitePat.setExpDivisional(expDivisional);
        if (solSeleccionada.getFecPresentacion() != null) {
            tramitePat.setFechaPresentacionExp(solSeleccionada.getFecPresentacion());
            expDivisional2 = "";
            solicitudesSgapatTable = new ArrayList<Solicitud>();
        }

    }

    public void habilitarPrioridad(int cambioVista) throws Exception {
        
        verErrores = false;
        if (agregarPrioridad.intValue() == 1) {
            verPrioridad = true;
            if (cambioVista == 1) {
                datosPrioridad.setFechaPresentacionExt(null);
            }

        } else {
            verPrioridad = false;
            datosPrioridad = new Prioridad();
            patentesViewService.selectPrioridad(this);
        }

    }

public void inicializaSolPCT() {
        tramitePat.setFechaSolPCT(null);
        tramitePat.setFechaPubPCT(null);
        tramitePat.setNumPubPCT("");
        tramitePat.setNumSolPCT("");
        tramitePat.setTipoPubPCT("");
        tramitePat.setFaseSolPCT("");

    }

    public void inicializaTramite() {
        tramitePat = new TramitePatente();
        CatTiposolicitud tipo = new CatTiposolicitud();
        CatSubtiposolicitud subtipoS = new CatSubtiposolicitud();
        tramitePat.setTipoSol(tipo);
        tramitePat.setSubTipoSol(subtipoS);
        tramitePat.setIdTramitePatente(new Long(0));
        tramitePat.setFechaDivPrevia(null);
        tramitePat.setFechaDivulgacion(null);
        tramitePat.setInvencion(" de ");
        inicializaSolPCT();
        forma.setRenderedAviso(true);

        if (listTiposSol != null && listTiposSol.isEmpty() == false) {
            if (listTiposSol.size() == 1) {
                for (int u = 0; u < listTiposSol.size(); u++) {
                    tipo = (CatTiposolicitud) listTiposSol.get(u);

                    if (tipo.getIdTiposolicitud().longValue() == Constantes.TIPO_SOL_DISENO.longValue()) {
                        tramitePat.setTipoSol(tipo);
                        break;
                    }
                }
            }

        }
//        if (listTiposSol != null && listTiposSol.isEmpty() == false) {
//                for (int u = 0; u < listTiposSol.size(); u++) {
//                    tipo = (CatTiposolicitud) listTiposSol.get(u);
//                    switch (tipo.getIdTiposolicitud().intValue()){
//                        case Constantes.TIPO_SOL_DISENO.intValue(): 
//                            tramitePat.setTipoSol(tipo); 
//                            break;
//                        case Constantes.TIPO_SOL_MODELO_UTILIDAD.intValue(): break;
//                    }
//
//                    if (tipo.getIdTiposolicitud().longValue() == Constantes.TIPO_SOL_DISENO.longValue()) {
//                        tramitePat.setTipoSol(tipo);
//                        break;
//                    }
//                }
//        }
        if (tramitePat.getTipoSol().getIdTiposolicitud() == null) {
            tramitePat.getTipoSol().setIdTiposolicitud(listTiposSol.get(0).getIdTiposolicitud().longValue());
            if (tramitePat.getTipoSol().getIdTiposolicitud().intValue() == Constantes.TIPO_SOL_MODELO_UTILIDAD || tramitePat.getTipoSol().getIdTiposolicitud().intValue() == Constantes.TIPO_SOL_PATENTE) {
                habilitarResumen = true;
            }
            
        }
        verPreguntaPrioridad = true;

    }

    public void inicializarPersona(int tipo) {
        if (tipo == 1) {
            solicitanteDatos = new Persona();
            solicitanteDatos.setTipoPersona(new CatTipopersona());
            solicitanteDatos.setTipoSolicitante(new CatTipoSolicitante());
            solicitanteDatos.setPais(new Pais());
            solicitanteDatos.getPais().setIdPais(Constantes.ID_PAIS);
            solicitanteDatos.setNacionalidad(new Pais());
//            solicitanteDatos.setNacionalidad(solicitanteDatos.getPais());
            solicitanteDatos.setDatosContacto(new Datoscontacto());
            esinventor = false;
            inventor = false;
            aplicaDescuento = false;
        }
        if (tipo == 2) {
            inventorDatos = new Persona();
            inventorDatos.setTipoPersona(tiposPersonaInventor.get(0));
            inventorDatos.setPais(new Pais());
            inventorDatos.getPais().setIdPais(Constantes.ID_PAIS);
            inventorDatos.setNacionalidad(new Pais());
//            inventorDatos.setNacionalidad(inventorDatos.getPais());
            inventorDatos.setDatosContacto(new Datoscontacto());
        }

    }

    public String validarPCT() {
        String msg = "";

        if (!(tramitePat.getNumSolPCT() != null && tramitePat.getNumSolPCT().length() != 0)) {
            msg += bundleParametrosPatentes.getString("mensaje.error.sin.num.sol.pct") + "||";
        } else if (!Pattern.matches("PCT/[A-Z]{2}[0-9]{4}\\/[0-9]{6}", tramitePat.getNumSolPCT())) {
            msg += bundleParametrosPatentes.getString("mensaje.error.formato.num.sol.pct") + "||";
        }

        if (tramitePat.getFechaSolPCT() == null) {
            msg += bundleParametrosPatentes.getString("mensaje.error.sin.fec.sol.pct") + "||";
        }
//        if (!(tramitePat.getFaseSolPCT() != null && tramitePat.getFaseSolPCT().length() != 0)) {
//            msg += bundleParametrosPatentes.getString("mensaje.error.sin.fase.sol.pct") + "||";
//        }
        if (tramitePat.getNumPubPCT() != null && tramitePat.getNumPubPCT().length() != 0
                && (!Pattern.matches("WO [0-9]{4}\\/[0-9]{6}", tramitePat.getNumPubPCT()))) {
            msg += bundleParametrosPatentes.getString("mensaje.error.formato.num.pub.pct") + "||";
        }

        boolean isValid = true;
        if (this.tramitePat.getTipoSol().getIdTiposolicitud().longValue() == 10 & this.tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == 11) {
            if (this.tramitePat.getNumSolPCT() != null) {

                for (Prioridad pr : Util.checkListNull(this.listaPrioridades)) {
                    if (pr.getNumeroExpediente().equals(this.tramitePat.getNumSolPCT())) {
                        isValid = false;
                        this.listaPrioridades.remove(pr);
                        break;
                    }
                }
            }

        }


        if (this.tramitePat.getTipoSol().getIdTiposolicitud().longValue() == 9 & this.tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == 27) {
            if (this.tramitePat.getNumSolPCT() != null) {

                for (Prioridad pr : Util.checkListNull(this.listaPrioridades)) {
                    if (pr.getNumeroExpediente().equals(this.tramitePat.getNumSolPCT())) {
                        isValid = false;
                        this.listaPrioridades.remove(pr);
                        break;
                    }
                }
            }

        }

        if (!isValid) {
            msg += "Validacion PCT y prioridad invalida " + "||";
        }
        
        return msg;

    }

    public void guardar(int finalizar) {
        String msgAviso = "";
        String msgAvisoAct = "";
        boolean resul = false;
        boolean completa = true;

        try {
            
            if (!(tramitePat.getTipoSol().getIdTiposolicitud() != null && tramitePat.getTipoSol().getIdTiposolicitud().longValue() != 0)) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.tipo.solicitud") + "||";
                completa = false;
            }

            //Se valida que el expediente divisional de la solicitud exista
            if (!(tramitePat.getSubTipoSol().getIdSubtiposolicitud() != null && tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() != 0)) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.tipo.subsolicitud") + "||";
                completa = false;
            }
            
//            if (tramitePat.getExpDivisional()!="" && tramitePat.getExpDivisional()!=null)
//            {
//                if(tramitePat.getExpDivisional().length()==16)
//                {
//////                    if (this.buscarExpedienteDivisional()==false && contador<3)
//////                    {
//////                        contador+=1;
//                    if(tramitePat.getFechaPresentacionExp()== null)
//                    {
//                        msgAviso += "Debe indicar la Fecha de Presentación de la Solicitud Inicial" + "||";
//                        completa = false;  
//                    }
//////                    }
//                }
//            }
                
//            else {
//                if (tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_PATENTE.longValue()) {
//                    if (!(tramitePat.getFaseInternacional() != null && tramitePat.getFaseInternacional().intValue() != 0)) {
//                        msgAviso += bundleParametrosPatentes.getString("mensaje.error.fase.internacional") + "||";
//                        completa = false;
//                    }
//                }
//            }
            if (tramitePat.getExpDivisional() != null && tramitePat.getExpDivisional().length() != 0) {
                String msg = buscarExpDivisional();
                if (msg != null && msg.length() != 0) {
                    msgAviso += msg;
                    completa = false;
                }

            }
            
            //VALIDACION PESTANA SOLICITANTES
            if (listaSolicitantes != null && listaSolicitantes.isEmpty() != true && listaSolicitantes.size()>0)  {
                for (int j = 0; j < listaSolicitantes.size(); j++) {
                    Persona perSol = (Persona) listaSolicitantes.get(j);
                    if (perSol.getTipoPersona().getIdTipopersona().intValue() == 2) {
                        if (tramitePat.getApoderados() == null || tramitePat.getApoderados().isEmpty() == true) {
                            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.apoderados") + "||";
                            break;
                        }
                    }

                }

//                if (selectedSolicitante == null) {
//                    msgAviso += bundleParametrosPatentes.getString("mensaje.error.falta.principal") + "||";
//                } else if (msgAviso.length() == 0 && selectedSolicitante != null) {
//                    if (selectedSolicitante.getPrincipal() == null || selectedSolicitante.getPrincipal().intValue() == 0) {
//                        msgAviso += bundleParametrosPatentes.getString("mensaje.error.falta.principal") + "||";
//
//                    }
//                }

            }
        
        //VALIDACION PESTANA APODERADOS
//        if (tramitePat.getApoderados() != null && tramitePat.getApoderados().isEmpty() == false) {
//            if (selectedApoderado == null) {
//                msgAviso += bundleParametrosPatentes.getString("mensaje.error.falta.principal.apoderado") + "||";
//            } else {
//                if (selectedApoderado.getPrincipal() == null || selectedApoderado.getPrincipal().intValue() == 0) {
//                    msgAviso += bundleParametrosPatentes.getString("mensaje.error.falta.principal.apoderado") + "||";
//                    
//                }
//            }
//        }
            // VALIDACION PESTANA APODERADOS DOMICILIO NOTIFICACION
            if (tramitePat.getApoderados()!=null){
                if (tramitePat.getApoderados().size()>0){
                    if (this.domicilioContacto!=null){
                        msgAviso +=agregarDomicilioIni(this.domicilioContacto);
                    }else
                    {
                       msgAviso += "Se debe capturar el domicilio para oir y recibir notificaciones " + "||";
                    }
                    if (msgAviso.length()!=0){
                     completa=false;
                    }
                }
            }
//            else{
//                
//            }
                
            if (tramitePat.getTipoSol() != null && (tramitePat.getTipoSol().getIdTiposolicitud() == Constantes.SOL_BUSQUEDA_SIT || tramitePat.getTipoSol().getIdTiposolicitud() == Constantes.SOL_VIGILANCIA_SIT)) {
                forma.setRenderedResumen(false);
            } else {
                forma.setRenderedResumen(true);
            }
 /*IJZA se quita validacion del campo Titulo de la Invención ya que era obligatorio y no se utiliza para la 
        solicitu del SIT, este esta en la pestaña tipo solicitud 18/02/2015*/

    //        if (tramitePat.getInvencion() == null || tramitePat.getInvencion().length() == 0) {
    //            msgAviso += bundleParametrosPatentes.getString("mensaje.error.invento") + "||";
    //            completa = false;
    //        }

    /*IJZA se agrega validacion para el campo cobertura 18/02/2015 */
            
            
              if (tramitePat.getCobertura()== null || tramitePat.getCobertura().length() == 0) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.cobertura") + "||";
                completa = false;
            }

            if (verPCT == true) {
                String msg = "";
                msg = validarPCT();
                if (msg.length() != 0) {
                    msgAviso += msg;
                }
            }

            if (completa) {
                
                Anexos anexoHojaDes = new Anexos();
                anexoHojaDes.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
                anexoHojaDes.setIdTramitePatente(tramitePat.getIdTramitePatente());
                /*Se eliminan todos los anexos correspondientes a la hoja de descuento para no realizar
                 la comparacion de que existen o no la hoja de descuento contra solitantes*/
                log.info("Elimina el anexo");
                int res = this.flujosgralesViewService.deleteByTypeAnexo(anexoHojaDes);

                if (this.flujosgralesViewService.validarSolicitantes(listaSolicitantes)) {
                    for (int i = 0; i < listaSolicitantes.size(); i++) {
                        crearAnexoHojaDescuento(Constantes.FIRST, listaSolicitantes.get(i),firmante,sello);
                    }
                }
                forma.setRenderedResumen(false);

                if (idTramite == 0) {
                    resul = this.patentesViewService.guardar(this);
                    msgAvisoAct = bundleParametrosPatentes.getString("mensaje.solicitud.guardar");
                    idtramite=this.idTramite;

                } else {

                    //Se agrego validacion:el tramite solo podra ser actualizado cuando tenga estatus 1 y 2
                    TramitePatente TramitePatenteDb = this.patentesViewService.selectTramite(this.getIdTramite());

                    if (TramitePatenteDb != null && TramitePatenteDb.getIdEstatus() < 3) {
                        resul = this.patentesViewService.actualizaTramite(this);
                        if (finalizar != 1) {
                            //this.crearDocFile(tramitePat.getReivindicaciones());
                            msgAvisoAct = bundleParametrosPatentes.getString("mensaje.solicitud.actualizar");
                        }
                    } else {
                        msgAvisoAct = "La solicitud no puede ser actualizada, debido que ya se encuentra pagada";
                    }

                }

                if (idTramite != 0 && resul == true) {
                    this.patentesViewService.recuperarTramite(this);
                    actualizaAnexosMemoria();
                    actualizaAnexosApoderado();
//                    anexosViewDto.setIdSubtiposolicitud(this.tramitePat.getSubTipoSol().getIdSubtiposolicitud());
//                    anexosViewDto.stramite.setTipoPubPCT(forma.getTramitePat().getTipoPubPCT());
//            tretIdTramitePatente(this.tramitePat.getIdTramitePatente());
//                    anexosViewDto.setIdTipomarca(0);
//                    lstAnexosDto = catalogosViewService.ConsultaAnexosDtoPatentes(anexosViewDto);
//                    for(int j=0;j<lstAnexosDto.size();j++)
//                    {
//                       if (lstAnexosDto.get(j).getTxtAnexo()==null && lstAnexosDto.get(j).getNombreArchivo()==null){
//                           lstAnexosDto.remove(j);
//                           j--;
//                        }  
//                    }

                    if (finalizar == Constantes.EXISTE) {
                        msgAviso = validarSolicitudCompleta();
                        if (msgAviso.length() == 0) {
                            log.info("solicitud completa");
                            SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
                            if (null != obtSession) {
                                obtSession.setIdTramite(idTramite);
                                obtSession.setIdTipoTramite(TipoTramiteEnum.SOL_SIT.getIdTipoTramite());
                                ContextUtils.crearSesionRdu(obtSession);
                            }
//                      FacesContext.getCurrentInstance().getExternalContext().redirect("../include/redireccionar.faces");
                            FacesContext.getCurrentInstance().getExternalContext().redirect("../pago/Pagos.faces");
                            verErrores = false;

                        }
                    }
                }
            }


            if (msgAviso.length() != 0) {
                //FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgAviso, msgAviso);
                //FacesContext.getCurrentInstance().addMessage(null, message);
                convierteListaErrores(msgAviso);
                verErrores = true;
            } else {
                verErrores = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msgAvisoAct, msgAvisoAct);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }

        } catch (Exception e) {
            log.error("Error:", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundleParametrosPatentes.getString("mensaje.solicitud.guardar.error")));
        }

    }

    public void crearDocFile(List<Reivindicacion> reivindicaciones) {
        System.out.println(">>>>>>>>>>>>><Revindicaciones>>>>>>>>>>>>>>>>>>>>>>>>\n" + GenerarReporte.formatearReivindicaciones(reivindicaciones));
        String temp = GenerarReporte.formatearReivindicaciones(reivindicaciones).replace('\n', ' ');
        FileServicesUtil.createDocFile("REIVINDICACIONES", temp);
    }

    public boolean verificaExisteRGP() {
        boolean existeRGP = false;
        if (tramitePat.getApoderados() != null && tramitePat.getApoderados().isEmpty() == false) {
            for (int u = 0; u < tramitePat.getApoderados().size(); u++) {
                Persona apoderado = (Persona) tramitePat.getApoderados().get(u);
                if (apoderado.getRgp() != null && apoderado.getRgp().length() != 0) {
                    existeRGP = true;
                    break;
                }
            }
        }

        return existeRGP;
    }

    public boolean verificaExisteSolicitantesSinCopiaInventor() {
        boolean noInventor = false;
        if (listaSolicitantes != null && listaSolicitantes.isEmpty() == false) {
            for (int u = 0; u < listaSolicitantes.size(); u++) {
                Persona solicitante = (Persona) listaSolicitantes.get(u);
                if (solicitante.getInventor() != null && solicitante.getInventor().intValue() == Constantes.NO_EXISTE) {
                    noInventor = true;
                    break;
                }
            }
        }

        return noInventor;
    }

    public String validarSolicitudCompleta() {
        String msg = "";
        boolean anexoDesc=false;
        boolean anexoReiv=false;
        boolean anexoResu=false;

        Anexos anexoHojaDes = new Anexos();
        anexoHojaDes.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
        anexoHojaDes.setIdTramitePatente(tramitePat.getIdTramitePatente());
        /*Se eliminan todos los anexos correspondientes a la hoja de descuento para no realizar
         la comparacion de que existen o no la hoja de descuento contra solitantes*/
        log.info("Elimina el anexo");
        int res = this.flujosgralesViewService.deleteByTypeAnexo(anexoHojaDes);

        if (this.flujosgralesViewService.validarSolicitantes(listaSolicitantes)) {
            for (int i = 0; i < listaSolicitantes.size(); i++) {
                crearAnexoHojaDescuento(Constantes.FIRST, listaSolicitantes.get(i),firmante,sello);
            }
        }

        //Obtener la lista de anexos cargados y obligatorios
        AnexosViewDto anexosViewDto = new AnexosViewDto();
        anexosViewDto.setIdTramitePatente(idTramite);
        anexosViewDto.setIdSubtiposolicitud(tramitePat.getSubTipoSol().getIdSubtiposolicitud());
        anexosViewDto.setIdArea(tramitePat.getTipoSol().getIdArea());

        anexosViewDto.setIdTipomarca(new Long(0));
        if (listaPrioridades != null && listaPrioridades.isEmpty() == false) {
            anexosViewDto.setPrioridad(Constantes.ANEXO_PRIORIDAD_PAT.intValue());
        }

        if (tramitePat.getFechaDivPrevia() != null) {
            anexosViewDto.setComprobacionFechaDiv(Constantes.ANEXO_DIV_PREVIA_PAT.intValue());
        }

        if (tramitePat.getPersonasNot() != null && tramitePat.getPersonasNot().isEmpty() == false) {
            anexosViewDto.setNotificaciones(Constantes.ANEXO_NOTIFICACION_PAT.intValue());
        }

        if (verificaExisteRGP() == true) {
            anexosViewDto.setDoctoAcreditaPersonalidad(Constantes.ANEXO_ACREDITA_PERSONALIDAD.intValue());
        }

        if (verificaExisteSolicitantesSinCopiaInventor() == true) {
            anexosViewDto.setCesionDerechos(Constantes.ANEXO_CESION_DERECHOS.intValue());
        }
        boolean isValid = true;
        if (this.tramitePat.getTipoSol().getIdTiposolicitud().longValue() == 10 & this.tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == 11) {
            if (this.tramitePat.getNumSolPCT() != null) {

                for (Prioridad pr : Util.checkListNull(this.listaPrioridades)) {
                    if (pr.getNumeroExpediente().equals(this.tramitePat.getNumSolPCT())) {
                        isValid = false;
                        this.listaPrioridades.remove(pr);
                        break;
                    }
                }
            }

        }


        if (this.tramitePat.getTipoSol().getIdTiposolicitud().longValue() == 9 & this.tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() == 27) {
            if (this.tramitePat.getNumSolPCT() != null) {

                for (Prioridad pr : Util.checkListNull(this.listaPrioridades)) {
                    if (pr.getNumeroExpediente().equals(this.tramitePat.getNumSolPCT())) {
                        isValid = false;
                        this.listaPrioridades.remove(pr);
                        break;
                    }
                }
            }

        }

        if (!isValid) {
            msg += "Validacion PCT y prioridad invalida " + "||";
        }
        
        //VALIDACION PESTANA MEMORIA TECNICA
        if (tramitePat.getTipoSol() != null && tramitePat.getTipoSol().getIdTiposolicitud().intValue() == Constantes.SOL_DISENO_INDUSTRIAL) {
            forma.setRenderedResumen(false);
        } else {
            forma.setRenderedResumen(true);
        }
         if (tramitePat.getExpDivisional()!="" && tramitePat.getExpDivisional()!=null)
            {
              if(tramitePat.getExpDivisional().length()==16)
                {
                 if(tramitePat.getFechaPresentacionExp()== null)
                   {
                     msg += "Debe indicar la Fecha de Presentación de la Solicitud Inicial" + "||";
                    // completa = false;  
                   }
                }
             }
        
        for(int i=0;i<lstAnexosDto.size();i++){
            switch(lstAnexosDto.get(i).getIdTipoanexo().intValue()){
                case 15:    anexoDesc=true;
                            break;
                case 16:    anexoReiv=true;
                            break;
                case 43:    anexoResu=true;
                            break;    
            }
                
        }
        
        if (anexoReiv==false)
        {
            msg += bundleParametrosPatentes.getString("mensaje.error.reivindicaciones.sin.reivindicacion") + "||";   
        }
        
        if (anexoDesc==false)
        {
            msg += bundleParametrosPatentes.getString("mensaje.error.descripcion.sin.descripcion") + "||";    
        }
        
        if (anexoResu==false && this.tramitePat.getTipoSol().getIdTiposolicitud().longValue()== Constantes.SOL_MODELO_UTILIDAD.longValue())
        {
            msg += bundleParametrosPatentes.getString("mensaje.error.descripcion.sin.resumen") + "||";    
        }
        
        Anexos anexoMemoriaTec = new Anexos();

//        anexoMemoriaTec.setIdTipoanexo(Constantes.ANEXO_REIVINDICACIONES_PATENTE);
//        anexoMemoriaTec.setIdTramitePatente(idTramite);
//        anexoMemoriaTec = flujosgralesViewService.obtenerAnexosDynamic(anexoMemoriaTec);
//        if (anexoMemoriaTec==null && anexoReiv==false){
//            msg += bundleParametrosPatentes.getString("mensaje.error.reivindicaciones.sin.reivindicacion") + "||";   
//        } 
//        anexoMemoriaTec=new Anexos();
//        anexoMemoriaTec.setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);
//        anexoMemoriaTec.setIdTramitePatente(idTramite);
//        anexoMemoriaTec = flujosgralesViewService.obtenerAnexosDynamic(anexoMemoriaTec);
//        if (anexoMemoriaTec==null){
//            msg += bundleParametrosPatentes.getString("mensaje.error.descripcion.sin.descripcion") + "||";   
//        }  
             
                              
//        if (tramitePat.getReivindicaciones().isEmpty()) {
//            msg += bundleParametrosPatentes.getString("mensaje.error.reivindicaciones.sin.reivindicacion") + "||";
//        } else if (!tramitePat.getReivindicaciones().isEmpty() && (forma.getIdiomaReivindicacion() == null || forma.getIdiomaReivindicacion().equals(""))) {
//            msg += bundleParametrosPatentes.getString("mensaje.error.idiomaReivindicacion") + "||";
//        }
//
//        if ((forma.getDescPdf() == null || forma.getDescText() == null)
//                && (forma.getIdiomaDescripcion() == null || forma.getIdiomaDescripcion().equals(""))) {
//            msg += bundleParametrosPatentes.getString("mensaje.error.idiomaDescripcion") + "||";
//        }
//
//        if (forma.getDescPdf() != null && forma.getAnexoDescripcionMe() == null) {
//            msg += bundleParametrosPatentes.getString("mensaje.error.descripcion.pdf") + "||";
//        }
//
//        if (forma.getDescText() != null && tramitePat.getPreambulo() == null) {
//            msg += "Debe de ingresar la información correspondiente a la Descripción||";
//        }
//
          anexoDto = catalogosViewService.CosultaAnexosDtoSimplePatente(anexosViewDto);     
//        
//        Anexos anexoMemoriaTec = new Anexos();
//        
//        anexoMemoriaTec.setIdTipoanexo(Constantes.ANEXO_REIVINDICACIONES_PATENTE);
//        anexoMemoriaTec.setIdTramitePatente(idTramite);
//
//        anexoMemoriaTec = flujosgralesViewService.obtenerAnexosDynamic(anexoMemoriaTec);
//        if(anexoMemoriaTec != null && anexoMemoriaTec.getNombreArchivo() != null){
//            uno: for (int i = 0; i < anexoDto.size(); i++) {
//                AnexosViewDto anexo = (AnexosViewDto) anexoDto.get(i);
//                if (anexo.getIdTipoanexo() == 15) {
//                    anexo.setCargado(true);
//                    anexo.setObligatorio(Constantes.INIT_SHORT);
//                    break uno;
//                }
//            }
//        }

        if (anexosViewDto.getDoctoAcreditaPersonalidad() != null && anexosViewDto.getDoctoAcreditaPersonalidad().intValue() == Constantes.ANEXO_ACREDITA_PERSONALIDAD.intValue()) {
            ponerAnexoObligatorio(anexoDto, Constantes.ANEXO_ACREDITA_PERSONALIDAD);
        }
        if (anexosViewDto.getComprobacionFechaDiv() != null && anexosViewDto.getComprobacionFechaDiv().intValue() == Constantes.ANEXO_DIV_PREVIA_PAT.intValue()) {
            ponerAnexoObligatorio(anexoDto, Constantes.ANEXO_DIV_PREVIA_PAT);
        }
//        if (anexosViewDto.getNotificaciones() != null && anexosViewDto.getNotificaciones().intValue() == Constantes.ANEXO_NOTIFICACION_PAT.intValue()) {
//            ponerAnexoObligatorio(anexoDto, Constantes.ANEXO_NOTIFICACION_PAT);
//        }
        if (tramitePat.isMaterial_biologico()) {
            ponerAnexoObligatorio(anexoDto, Constantes.ANEXO_CONST_MAT_BIOL);
        }

//        for (int j = 0; j < anexoDto.size(); j++) {
//            AnexosViewDto archivo = (AnexosViewDto) anexoDto.get(j);
//
//            if (archivo.getCargado() == false && archivo.getObligatorio().intValue() == Constantes.EXISTE) {
//                    msg += "Falta el anexo: " + archivo.getDescripcion() + "||";
//            }
////            if (tramitePat.getPersonasNot() != null && tramitePat.getPersonasNot().isEmpty() == false) {
////                if (archivo.getIdTipoanexo().longValue() == Constantes.ANEXO_NOTIFICACION_PAT.longValue() && archivo.getCargado() == false && archivo.getObligatorio().intValue() == Constantes.NO_EXISTE) {
////                    msg += "El anexo de " + archivo.getDescripcion() + " es obligatorio||";
////                }
////            }
//        }
        msg += validarPersonas();
        msg += validarImagenesReinvindicaciones();
        return msg;
    }

    public void ponerAnexoObligatorio(List<AnexosViewDto> anexos, Long idAnexo) {
        for (AnexosViewDto anexo : anexos) {
            if (anexo.getIdTipoanexo().intValue() == idAnexo.intValue()) {
                anexo.setObligatorio((short) 1);
                break;
            }
        }
    }
    
    public String validarPersonas() {
        String msg = "";
        if (tramitePat.getObservaciones() != null && tramitePat.getObservaciones().length() != 0) {

//            if (Pattern.matches(Constantes.pattern_observacionesPatente, tramitePat.getObservaciones()) == false) {
            if (Pattern.matches(Constantes.pattern_nombre_personas, tramitePat.getObservaciones()) == false) {
                msg += bundleParametrosPatentes.getString("mensaje.error.obvs") + "||";
            }

        }


        if (tramitePat.getApoderados() != null && tramitePat.getApoderados().isEmpty() == false) {
            if (selectedApoderado == null) {
                msg += bundleParametrosPatentes.getString("mensaje.error.falta.principal.apoderado") + "||";
            } else {
                if (selectedApoderado.getPrincipal() == null || selectedApoderado.getPrincipal().intValue() == 0) {
                    msg += bundleParametrosPatentes.getString("mensaje.error.falta.principal.apoderado") + "||";

                }
            }
        }


        if (listaSolicitantes == null || listaSolicitantes.isEmpty() == true) {
            msg += bundleParametrosPatentes.getString("mensaje.error.sin.sol") + "||";
        } else {

            for (int j = 0; j < listaSolicitantes.size(); j++) {
                Persona perSol = (Persona) listaSolicitantes.get(j);
                if (perSol.getTipoPersona().getIdTipopersona().intValue() == 2) {
                    if (tramitePat.getApoderados() == null || tramitePat.getApoderados().isEmpty() == true) {
                        msg += bundleParametrosPatentes.getString("mensaje.error.sin.apoderados") + "||";
                        break;
                    }
                }

            }

            if (selectedSolicitante == null) {
                msg += bundleParametrosPatentes.getString("mensaje.error.falta.principal") + "||";
            } else {
                if (selectedSolicitante.getPrincipal() == null || selectedSolicitante.getPrincipal().intValue() == 0) {
                    msg += bundleParametrosPatentes.getString("mensaje.error.falta.principal") + "||";

                }
            }
        }

        if (listaInventores == null || listaInventores.isEmpty() == true) {
            msg += bundleParametrosPatentes.getString("mensaje.error.sin.inventor") + "||";
        }
        return msg;
    }
    
        public String validarImagenesReinvindicaciones() {
        String msg = "";
//        if (tramitePat.getReivindicaciones().isEmpty()) {
//            msg += bundleParametrosPatentes.getString("mensaje.error.reivindicaciones.sin.reivindicacion") + "||";
//        }
        if (tramitePat.getTipoSol() != null ? tramitePat.getTipoSol().getIdTiposolicitud().intValue() == Constantes.TIPO_SOL_DISENO : false) {
            if (tramitePat.getImagenes().isEmpty()) {
                msg += bundleParametrosPatentes.getString("mensaje.error.imagenes.sin.dibujo") + "||";
            }
        }
        return msg;
    }
//
//    public void validaFechaDivPrevia(DateSelectEvent event) {
//        RequestContext context = RequestContext.getCurrentInstance();
//
//        Util util = new Util();
//
//        if (event.getDate() != null) {
//            if (util.fueraRangoFechas(event.getDate(),
//                    Constantes.rango12Meses)) {
//                context.addCallbackParam("status", true);
//            }
//            tramitePat.setFechaDivPrevia(event.getDate());
//            return;
//        }
//    }

    public void validaFechaDivPresentacion(DateSelectEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();

        Util util = new Util();
        boolean validacion = Util.compareTimesByString(event.getDate(), "dd/MM/yyyy", tramitePat.getExpDivisional().substring(5, 9));

        if (validacion) {
            tramitePat.setFechaPresentacionExp(null);
            context.addCallbackParam("status", true);
        } else {
            tramitePat.setFechaPresentacionExp(event.getDate());
            return;
        }

//        if (event.getDate() != null) {
//            if (util.fueraRangoFechas(event.getDate(),
//                    Constantes.rango12Meses)) {
//                context.addCallbackParam("status", true);
//            }
//            tramitePat.setFechaPresentacionExp(event.getDate());
//            return;
//        }
    }

    public void MaxMinFechaDiv() {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

            String FechaFinal = "31/12/" + tramitePat.getExpDivisional().substring(5, 9);
            String FechaInicial = "01/01/" + tramitePat.getExpDivisional().substring(5, 9);
            forma.setMaxdatePresDiv(formatoFecha.parse(FechaFinal));
            forma.setFechaInicial(formatoFecha.parse(FechaInicial));
        } catch (ParseException ex) {
            log.fatal(ex.getMessage());
        }

    }

    public void validaFechaDivPrioridad(DateSelectEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        int numeroMeses = Constantes.rango12Meses;
        msgMostrarFchPrioridad = bundleParametrosPatentes.getString("advertencia.fecha.prioridad.invalida.doce");

        Util util = new Util();

        if (event.getDate() != null) {

            /**
             * VALIDACION SI ES DISEÑO INDUSTRIAL SON 6 MESES, EN CUALQUIER OTRO
             * CASO SON 12 MESES DE RANGO... CAMBIAR A LA CONSTANTE
             * "RANGO12MESES"
             */
            if (tramitePat.getTipoSol().getIdTiposolicitud().
                    compareTo(Constantes.SOL_DISENO_INDUSTRIAL) == 0) {
                numeroMeses = Constantes.rango6Meses;
                msgMostrarFchPrioridad = bundleParametrosPatentes.getString("advertencia.fecha.prioridad.invalida.seis");
            }


            if (util.fueraRangoFechas(event.getDate(),
                    numeroMeses)) {

                context.addCallbackParam("status", true);
            }
            datosPrioridad.setFechaPresentacionExt(event.getDate());
            return;
        }
        msgMostrarFchPrioridad = null;
    }

    public void crearCabeceroTramite() {
    try {

        if (tramitePat.getIdTramitePatente()>0)
        {
          this.setIdTramite(tramitePat.getIdTramitePatente());
        }
        if (this.idTramite > 0) {
            this.cabeceroTramite.setIdTramitePatente(idTramite);
            this.cabeceroTramite.setSubTipoSol(tramitePat.getSubTipoSol());

            if (listaPrioridades != null && listaPrioridades.isEmpty() == false) {
                this.cabeceroTramite.setPrioridades(listaPrioridades);
                this.agregarPrioridad=1;
                habilitarPrioridad(1);
            } else {
                this.cabeceroTramite.setPrioridades(null);
               this.agregarPrioridad=0;
            }
            if (tramitePat.getFechaDivPrevia() != null) {
                this.cabeceroTramite.setFechaDivPrevia(tramitePat.getFechaDivPrevia());
            } else {
                this.cabeceroTramite.setFechaDivPrevia(null);
            }
            if (tramitePat.getPersonasNot() != null && tramitePat.getPersonasNot().isEmpty() == false) {
                this.cabeceroTramite.setPersonasNot(tramitePat.getPersonasNot());
            } else {
                this.cabeceroTramite.setPersonasNot(null);
            }
            this.cabeceroTramite.setMaterial_biologico(tramitePat.isMaterial_biologico());

           // if (verificaExisteRGP() == true) {
                this.cabeceroTramite.setApoderados(tramitePat.getApoderados());
           // } else {
            //    this.cabeceroTramite.setApoderados(null);
                this.cabeceroTramite.setInventores(tramitePat.getInventores());
            //}

          //  if (verificaExisteSolicitantesSinCopiaInventor() == true) {
                this.cabeceroTramite.setSolicitantes(listaSolicitantes);
           // } else {
           //     this.cabeceroTramite.setSolicitantes(null);

           // }
            session.removeAttribute("cabecero");
            session.setAttribute("cabecero", cabeceroTramite);
            session.setAttribute("mostraHerramientas", new Integer(1));
            
        }else
        {
           //this.patentesViewService.recuperarTramite(this);
           this.agregarPrioridad=0;
//           session.removeAttribute("cabecero");
//           session.setAttribute("cabecero", cabeceroTramite);
//           session.setAttribute("mostraHerramientas", new Integer(1));
        }
    } catch (Exception e) 
        {
           e.printStackTrace();
        }
    }

    public void mostrarVistaPrevia() throws IOException {
         boolean resul = false;
        forma.setRenderedVistaPrevia(true);
        List<ReportesDto> listaTempReport = new ArrayList<ReportesDto>();
        List<ReportesDto> listaReportes = null;
        ByteArrayOutputStream reporteFinal = null;
        Util util = new Util();
        List<ReportesDto> listaTmp = new ArrayList<ReportesDto>();
        ReportesDto reportesDto = null;

       // this.guardar(0);
        
//        if (verErrores==false)
//        {
        tramitePat.setSolicitantes(listaSolicitantes);
        tramitePat.setInventores(listaInventores);
        tramitePat.setPrioridades(listaPrioridades);
        GenerarReporte generarReporte = new GenerarReporte();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                
        session.removeAttribute("reporteStream");

        boolean summaryBoolean = solicitanteDatos.isAplicarDescuento() ? true : false;

        Anexos anexoHojaDescuento = new Anexos();
        ReporteDisenoIndustrialDto reporteDisenoIndustrialDto = new ReporteDisenoIndustrialDto(tramitePat, request.getRealPath("") + "/content/imagenes/firma_impi.png", listaTmp);      
        firmante=reporteDisenoIndustrialDto.getNombreFirmante();
        sello=reporteDisenoIndustrialDto.getSelloSolicitante();
        anexoHojaDescuento.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
        anexoHojaDescuento.setIdTramitePatente(idTramite);
        /*Se eliminan todos los anexos correspondientes a la hoja de descuento para no realizar
         la comparacion de que existen o no la hoja de descuento contra solitantes*/
        log.info("Elimina el anexo");
        int res = this.flujosgralesViewService.deleteByTypeAnexo(anexoHojaDescuento);
        if (this.flujosgralesViewService.validarSolicitantes(listaSolicitantes)) {
            for (int i = 0; i < listaSolicitantes.size(); i++) {
//                crearAnexoHojaDescuento(Constantes.FIRST, listaSolicitantes.get(i).getTipoSolicitante().getIdTipoSolicitante().intValue());
                crearAnexoHojaDescuento(Constantes.FIRST, listaSolicitantes.get(i),firmante,sello);
            }
        }
        
        if(tramitePat.getIdTramitePatente() != null && tramitePat.getIdTramitePatente() > 0){
            patentesViewService.insertarPdfAnexosMemoriaTecnica(this, idTramite);
            patentesViewService.insertarPdfAnexosApoderado(this, idTramite);
            actualizaAnexosMemoria();
            actualizaAnexosApoderado();
//            anexosViewDto.setIdSubtiposolicitud(this.tramitePat.getSubTipoSol().getIdSubtiposolicitud());
//            anexosViewDto.setIdTramitePatente(this.tramitePat.getIdTramitePatente());
//            anexosViewDto.setIdTipomarca(0);
//            lstAnexosDto = catalogosViewService.ConsultaAnexosDtoPatentes(anexosViewDto);
//            for(int j=0;j<lstAnexosDto.size();j++)
//            {
//                if (lstAnexosDto.get(j).getTxtAnexo()==null && lstAnexosDto.get(j).getNombreArchivo()==null){
//                    lstAnexosDto.remove(j);
//                    j--;
//                }  
//            } 
            //patentesViewService.insertarPdfDescripcion(this, idTramite);
           // patentesViewService.insertarPdfDivulgacionPrevia(this, idTramite);
        }
       
//        if(this.getForma().getAnexoDescripcionMe()!=null && this.getForma().getAnexoDescripcionMe().getNombreArchivo()!=null){
//            ByteArrayInputStream Bya = new ByteArrayInputStream(this.getForma().getAnexoDescripcionMe().getArchivoAnexo());
//            InputStream PDF = Bya;
//            PdfReader pdfReader= new PdfReader(PDF);
//            this.setnPaginas(pdfReader.getNumberOfPages());
//            //tramitePat.setPaginasDescripcion(pdfReader.getNumberOfPages());
//            pdfReader.close();
//        }
//        }else
//        {
//            tramitePat.setPaginasDescripcion(this.anexoDescripcion.getNumeroHojas());
//        }
     

       // tramitePat.
        //CREAR figuras y reivindicaciones para ser mostrado en la solicitud
        /*Se comento ya que el reporte se unio con el descripcionTexto.jrxml en caso de querrer separarlo
        nuevamente descomentar este pedazo de codigo*/
//        if (tramitePat.getReivindicaciones() != null && tramitePat.getReivindicaciones().size() > 0) {
//            reportesDto = new ReportesDto();
//            reportesDto.setNombreBookMark("REIVINDICACION");
//            reportesDto.setNombreReporte("reivindicaciones.pdf");
//            reportesDto.setOrden(new Integer(9));
//
//            reportesDto.setReporteBytes(generarReporte.generarReivindicacionesEnPdf(
//                    request.getRealPath("") + "/content/reportes/template_reivindicaciones.jasper", tramitePat,this.getnPaginas())
//                    .toByteArray());
//            listaTempReport.add(reportesDto);
//        }
        if (tramitePat.getImagenes() != null && tramitePat.getImagenes().size() > 0) {
            reportesDto = new ReportesDto();
            reportesDto.setNombreBookMark("FIGURAS");
            reportesDto.setNombreReporte("Figuras.pdf");
            reportesDto.setOrden(new Integer(11));//10
            reportesDto.setReporteBytes(generarReporte.generarDibujosEnPdf(request.getRealPath("") + "/content/reportes/template_dibujos.jasper", tramitePat).toByteArray());
            listaTempReport.add(reportesDto);
        }

        //ReporteDisenoIndustrialDto reporteDisenoIndustrialDto = new  (tramitePat, request.getRealPath("") + "/content/imagenes/firma_impi.png", listaTmp);
         
        ByteArrayOutputStream byt = generarReporte.generaRepporte(request.getRealPath("") + "/content/reportes/reporte_diseno.jasper", reporteDisenoIndustrialDto);
        //se comenta ya que el reporte ya no cuenta con la pagina 1
        //byt = generarReporte.eliminarPagina(byt, 1);
                
        reportesDto = new ReportesDto();
        reportesDto.setNombreBookMark(util.recuperarNombreAnexo(Constantes.ANEXO_DISENO));
        reportesDto.setReporteBytes(byt.toByteArray());
        reportesDto.setOrden(new Integer(1));
        listaTempReport.add(reportesDto);
        
        

        tramitePat.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(tramitePat.getIdTramitePatente()));
        List<Anexos> allAnx = tramitePat.getAnexos();
        
//        if (allAnx != null && allAnx.size() > 0){
        //ordenar por numero de ID_anexo
//            Anexos anxAux;
//            for (int i=0;i<allAnx.size()-1;i++){            
//                for(int j=i+1;j<allAnx.size();j++)
//                {
//                    if (allAnx.get(j).getIdAnexo() < allAnx.get(i).getIdAnexo())
//                    {
//                        anxAux=allAnx.get(i);
//                        allAnx.set(i,allAnx.get(j));
//                        allAnx.set(j, anxAux);
//                    }    
//                }
//            }
//        }
        tramitePat.getPrioridades();
        if (allAnx != null && allAnx.size() > 0) {

            for (Anexos anx : allAnx) {
                
                if (util.recuperarNombreAnexo(anx.getIdTipoanexo())!= "PRIORIDAD" && util.recuperarNombreAnexo(anx.getIdTipoanexo())!= "TRADUCCION"   ){
                    reportesDto = new ReportesDto();
                    reportesDto.setNombreBookMark(util.recuperarNombreAnexo(anx.getIdTipoanexo()));
                    reportesDto.setReporteBytes(anx.getArchivoAnexo());
                    reportesDto.setOrden(patentesViewService.ordenarAnexos(anx));
                    listaTempReport.add(reportesDto);
                    if(anx.getOtroIdioma()==2 && anx.getIdTipoanexoTrad()!=null ){
                        reportesDto = new ReportesDto();
                        reportesDto.setNombreBookMark(util.recuperarNombreAnexo(anx.getIdTipoanexoTrad()));
                        reportesDto.setReporteBytes(anx.getArchivoTrad());
                        reportesDto.setOrden(patentesViewService.ordenarAnexosTraduccion(anx));
                        listaTempReport.add(reportesDto);
                    }   
                }
            }
        }
        ////////////////////////////////
        //ordenar anexos
        ReportesDto anexoAux;
        for (int i=0;i<listaTempReport.size()-1;i++){            
            for(int j=i+1;j<listaTempReport.size();j++)
            {
                if (listaTempReport.get(j).getOrden() < listaTempReport.get(i).getOrden())
                {
                    anexoAux=listaTempReport.get(i);
                    listaTempReport.set(i,listaTempReport.get(j));
                    listaTempReport.set(j, anexoAux);
                }    
                
            }
        }
        List<Anexos> prioridadAnx=new ArrayList<Anexos>();
        
        for (int i = 0; i < tramitePat.getPrioridades().size(); i++) {
                    String nombrePais = tramitePat.getPrioridades().get(i).getNombrePais();
                    Long idPrioridad = tramitePat.getPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbPrioridad = new AnexosViewDto();
                    Anexos anexoView= new Anexos();     
                    
                    anexoDbPrioridad.setIdPrioridad(idPrioridad);
                    anexoDbPrioridad.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);
                    anexoDbPrioridad = catalogosViewService.selectAnexoDynamic(anexoDbPrioridad);
                    
                    if(anexoDbPrioridad!=null && anexoDbPrioridad.getArchivoAnexo()!=null){
                        anexoView.setIdAnexo(anexoDbPrioridad.getIdAnexo());
                        anexoView.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);                        
                        anexoView.setIdTramitePatente(anexoDbPrioridad.getIdTramitePatente());
                        anexoView.setArchivoAnexo(anexoDbPrioridad.getArchivoAnexo());
                        anexoView.setNombreArchivo(anexoDbPrioridad.getNombreArchivo());
                        anexoView.setExtension("pdf");
                        prioridadAnx.add(anexoView);
                    }

        }
         for (int i = 0; i < tramitePat.getPrioridades().size(); i++) {
                    String nombrePais = tramitePat.getPrioridades().get(i).getNombrePais();
                    Long idPrioridad = tramitePat.getPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbTraduccion = new AnexosViewDto();
                    Anexos traduccionView = new Anexos();       
                                     
                    anexoDbTraduccion.setIdPrioridad(idPrioridad);
                    anexoDbTraduccion.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                    anexoDbTraduccion = catalogosViewService.selectAnexoDynamic(anexoDbTraduccion);
                                        
                    if(anexoDbTraduccion!=null && anexoDbTraduccion.getArchivoAnexo()!=null){
                        traduccionView.setIdAnexo(anexoDbTraduccion.getIdAnexo());
                        traduccionView.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                        traduccionView.setIdTramitePatente(anexoDbTraduccion.getIdTramitePatente());
                        traduccionView.setArchivoAnexo(anexoDbTraduccion.getArchivoAnexo());
                        traduccionView.setNombreArchivo(anexoDbTraduccion.getNombreArchivo());
                        traduccionView.setExtension("pdf");
                        prioridadAnx.add(traduccionView);
                    }   

        }
        
        if (prioridadAnx != null && prioridadAnx.size() > 0) {
            for (Anexos anx : prioridadAnx) {
                    reportesDto = new ReportesDto();
                    reportesDto.setNombreBookMark(util.recuperarNombreAnexo(anx.getIdTipoanexo()));
                    reportesDto.setReporteBytes(anx.getArchivoAnexo());
                    reportesDto.setOrden(patentesViewService.ordenarAnexos(anx));
                    listaTempReport.add(reportesDto);
            }
        }
        ////////////////////////////////

//        Coloca leyenda al costado de cada hoja con el numero de expediente
        for (int i = 0; i < listaTempReport.size(); i++) {
            if (Constantes.nombreSolicitudPDF.compareTo(listaTempReport.get(i).getNombreBookMark()) != 0) {
                byte[] temp = generarReporte.agregarNumSolicitudReporte(listaTempReport.get(i).getReporteBytes(),
                        reporteDisenoIndustrialDto.getExpediente());
                listaTempReport.get(i).setReporteBytes(temp);
            }
        }
        
        
        for (int i=0;i<listaTempReport.size()-1;i++){            
            for(int j=i+1;j<listaTempReport.size();j++)
            {
                if (listaTempReport.get(j).getOrden() < listaTempReport.get(i).getOrden())
                {
                    anexoAux=listaTempReport.get(i);
                    listaTempReport.set(i,listaTempReport.get(j));
                    listaTempReport.set(j, anexoAux);
                }    
                
            }
        }
        
        int orden =listaTempReport.get(0).getOrden();
        
        for (int i=0;i<listaTempReport.size();i++)
        {
            listaTempReport.get(i).setOrden(orden);
            orden += 1;
        }
        //    Se valida si existen mas de 1 anexo HOJA DE DESCUENTO, si existe se Reordenan los reporte
        listaReportes = patentesViewService.reordenarReporte(listaTempReport);

//        Ordena reporte
        listaReportes = patentesViewService.ordenarReporte(listaTempReport);
//        Concatena pdfs le implementa el bookmark y la marca de agua 
//        de vista previa en caso de que se requiera.
        reporteFinal = generarReporte.concatenarPdfsWithBookMarks(listaReportes, true);

        session.setAttribute("reporteStream", reporteFinal);

        vistaPrevia = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("reporteDialog.show();");
//        }
        
    }
       
    
    public void actualizaAnexosMemoria(){
        anexosViewDto.setIdSubtiposolicitud(this.tramitePat.getSubTipoSol().getIdSubtiposolicitud());
        anexosViewDto.setIdTramitePatente(this.tramitePat.getIdTramitePatente());
        anexosViewDto.setIdTipomarca(0);
        lstAnexosDto = catalogosViewService.ConsultaAnexosDtoPatentes(anexosViewDto);
        for(int j=0;j<lstAnexosDto.size();j++)
        {
            if (lstAnexosDto.get(j).getTxtAnexo()==null && lstAnexosDto.get(j).getNombreArchivo()==null){
                lstAnexosDto.remove(j);
                j--;
            }  
            if (j>=0){
                if (lstAnexosDto.get(j).getIdTipoanexo().longValue()== Constantes.ANEXO_OTROS.longValue()
                    ||lstAnexosDto.get(j).getIdTipoanexo().longValue()== Constantes.ANEXO_CARTA_PODER.longValue()
                    ||lstAnexosDto.get(j).getIdTipoanexo().longValue()== Constantes.ANEXO_CONSTANCIA_RGP.longValue()
                    ||lstAnexosDto.get(j).getIdTipoanexo().longValue()== Constantes.ANEXO_PODER_NOTARIAL.longValue()
                    ||lstAnexosDto.get(j).getIdTipoanexo().longValue()== Constantes.ANEXO_ACTA_CONSTITUTIVA.longValue()
                    ||lstAnexosDto.get(j).getIdTipoanexo().longValue()== Constantes.ANEXO_OTRO.longValue()){
                    lstAnexosDto.remove(j);
                    j--;
                }
            }
        } 
    }
    
        
    public void actualizaAnexosApoderado(){
        anexosViewDto.setIdSubtiposolicitud(this.tramitePat.getSubTipoSol().getIdSubtiposolicitud());
        anexosViewDto.setIdTramitePatente(this.tramitePat.getIdTramitePatente());
        anexosViewDto.setIdTipomarca(0);
        lstAnexosApoderado = catalogosViewService.ConsultaAnexosDtoPatentes(anexosViewDto);
        for(int j=0;j<lstAnexosApoderado.size();j++)
        {
//            if (lstAnexosDto.get(j).getTxtAnexo()==null && lstAnexosDto.get(j).getNombreArchivo()==null){
//                lstAnexosDto.remove(j);
//                j--;
//            }  
            if (j>=0){
                if (lstAnexosApoderado.get(j).getIdTipoanexo().longValue()== Constantes.ANEXO_DESCRIPCION_PATENTE
                 || lstAnexosApoderado.get(j).getIdTipoanexo().longValue()== Constantes.ANEXO_REIVINDICACIONES_PATENTE
                 || lstAnexosApoderado.get(j).getIdTipoanexo().longValue()== Constantes.ANEXO_RESUMEN_MEMORIA
                 || lstAnexosApoderado.get(j).getIdTipoanexo().longValue()== Constantes.ANEXO_OTROS        
                 || lstAnexosApoderado.get(j).getArchivoAnexo()== null){
                     lstAnexosApoderado.remove(j);
                    j--;
                }
            }
        } 
    }
    
    public void finalizarConValidacion() {
        
        String msgAviso = "";
        String msgAvisoAct = "";
        boolean resul = false;
        boolean completa = true;
        int finalizar = 1;

        try {
            // VALIDACION PESTANA APODERADOS DOMICILIO NOTIFICACION
            if (tramitePat.getApoderados()!=null){
                if (tramitePat.getApoderados().size()>0){
                    if (this.domicilioContacto!=null){
                        msgAviso +=agregarDomicilioContacto(this.domicilioContacto);
                    }else
                    {
                       msgAviso += "Se debe capturar el domicilio para oir y recibir notificaciones " + "||";
                    }
                    if (msgAviso.length()!=0){
                     completa=false;
                    }
                }
            }
            if (completa) {

               if (idTramite == 0) {
                   resul = this.patentesViewService.guardar(this);
                   // msgAvisoAct = bundleParametrosPatentes.getString("mensaje.solicitud.guardar");
                } else {

                    //Se agrego validacion:el tramite solo podra ser actualizado cuando tenga estatus 1 y 2
                    TramitePatente TramitePatenteDb = this.patentesViewService.selectTramite(this.getIdTramite());

                    if (TramitePatenteDb != null && TramitePatenteDb.getIdEstatus() < 3) {
                        resul = this.patentesViewService.actualizaTramite(this);
                        if (finalizar != 1) {
                        }
                    } else {
                       // msgAvisoAct = "La solicitud no puede ser actualizada, debido que ya se encuentra pagada";
                    }

                }

                if (idTramite != 0 && resul == true) {
                    this.patentesViewService.recuperarTramite(this);
                    actualizaAnexosMemoria();
                    actualizaAnexosApoderado();
                    if (finalizar == Constantes.EXISTE) {
                        msgAviso = validarSolicitudCompleta();

                    }
                    if (finalizar == Constantes.INIT && (forma.getIdiomaSelected() == null || forma.getIdiomaSelected().equals(""))) {
                        msgAviso += bundleParametrosPatentes.getString("mensaje.error.idiomaDescripcion") + "||";
                        completa = false;
                    }

                }
            }


            if (msgAviso.length() != 0) {
                convierteListaErrores(msgAviso);
                verErrores = true;
                
            } else {
                verErrores = false;
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msgAvisoAct, msgAvisoAct);
                FacesContext.getCurrentInstance().addMessage(null, message);
        
                forma.setRenderedVistaPrevia(true);
                List<ReportesDto> listaTempReport = new ArrayList<ReportesDto>();
                List<ReportesDto> listaReportes = null;
                ByteArrayOutputStream reporteFinal = null;
                Util util = new Util();
                List<ReportesDto> listaTmp = new ArrayList<ReportesDto>();
                ReportesDto reportesDto = null;


                tramitePat.setSolicitantes(listaSolicitantes);
                tramitePat.setInventores(listaInventores);
                tramitePat.setPrioridades(listaPrioridades);
                GenerarReporte generarReporte = new GenerarReporte();
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

                session.removeAttribute("reporteStream");

                boolean summaryBoolean = solicitanteDatos.isAplicarDescuento() ? true : false;

                Anexos anexoHojaDescuento = new Anexos();
                ReporteDisenoIndustrialDto reporteDisenoIndustrialDto = new ReporteDisenoIndustrialDto(tramitePat, request.getRealPath("") + "/content/imagenes/firma_impi.png", listaTmp);
                firmante=reporteDisenoIndustrialDto.getNombreFirmante();
                sello=reporteDisenoIndustrialDto.getSelloSolicitante();
                anexoHojaDescuento.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
                anexoHojaDescuento.setIdTramitePatente(idTramite);
                /*Se eliminan todos los anexos correspondientes a la hoja de descuento para no realizar
                 la comparacion de que existen o no la hoja de descuento contra solitantes*/
                log.info("Elimina el anexo");
                int res = this.flujosgralesViewService.deleteByTypeAnexo(anexoHojaDescuento);
                if (this.flujosgralesViewService.validarSolicitantes(listaSolicitantes)) {
                    for (int i = 0; i < listaSolicitantes.size(); i++) {
//                crearAnexoHojaDescuento(Constantes.FIRST, listaSolicitantes.get(i).getTipoSolicitante().getIdTipoSolicitante().intValue());
                        crearAnexoHojaDescuento(Constantes.FIRST, listaSolicitantes.get(i),firmante,sello);
                    }
                }

                if (tramitePat.getIdTramitePatente() != null && tramitePat.getIdTramitePatente() > 0) {
                    patentesViewService.insertarPdfAnexosMemoriaTecnica(this, idtramite);
                    patentesViewService.insertarPdfAnexosApoderado(this, idTramite);
                    actualizaAnexosMemoria();
                    actualizaAnexosApoderado();
                    //patentesViewService.insertarPdfDescripcion(this, idTramite);
                }
                
//                if(this.getForma().getAnexoDescripcionMe()!=null && this.getForma().getAnexoDescripcionMe().getNombreArchivo()!=null){
//                    ByteArrayInputStream Bya = new ByteArrayInputStream(this.getForma().getAnexoDescripcionMe().getArchivoAnexo());
//                    InputStream PDF = Bya;
//                    PdfReader pdfReader= new PdfReader(PDF);
//                    this.setnPaginas(pdfReader.getNumberOfPages());
//                    //tramitePat.setPaginasDescripcion(pdfReader.getNumberOfPages());
//                    pdfReader.close();
//                }
//                else{
//                    tramitePat.setPaginasDescripcion(this.anexoDescripcion.getNumeroHojas());
//                }
     

       // tramitePat.
        //CREAR figuras y reivindicaciones para ser mostrado en la solicitud
        /*Se comento ya que el reporte se unio con el descripcionTexto.jrxml en caso de querrer separarlo
        nuevamente descomentar este pedazo de codigo*/
//                if (tramitePat.getReivindicaciones() != null && tramitePat.getReivindicaciones().size() > 0) {
//                    reportesDto = new ReportesDto();
//                    reportesDto.setNombreBookMark("REIVINDICACION");
//                    reportesDto.setNombreReporte("reivindicaciones.pdf");
//                    reportesDto.setOrden(new Integer(9));
//                    reportesDto.setReporteBytes(generarReporte.generarReivindicacionesEnPdf(
//                            request.getRealPath("") + "/content/reportes/template_reivindicaciones.jasper", tramitePat,this.getnPaginas())
//                            .toByteArray());
//                    listaTempReport.add(reportesDto);
//                }

                
                if (tramitePat.getImagenes() != null && tramitePat.getImagenes().size() > 0) {
                    reportesDto = new ReportesDto();
                    reportesDto.setNombreBookMark("FIGURAS");
                    reportesDto.setNombreReporte("Figuras.pdf");
                    reportesDto.setOrden(new Integer(11));//10
                    reportesDto.setReporteBytes(generarReporte.generarDibujosEnPdf(request.getRealPath("") + "/content/reportes/template_dibujos.jasper", tramitePat).toByteArray());
                    listaTempReport.add(reportesDto);
                }

                //ReporteDisenoIndustrialDto reporteDisenoIndustrialDto = new ReporteDisenoIndustrialDto(tramitePat, request.getRealPath("") + "/content/imagenes/firma_impi.png", listaTmp);
                ByteArrayOutputStream byt = generarReporte.generaRepporte(request.getRealPath("") + "/content/reportes/reporte_diseno.jasper", reporteDisenoIndustrialDto);
               //se comenta ya que el reporte ya no cuenta con la pagina 1
                //byt = generarReporte.eliminarPagina(byt, 1);

                reportesDto = new ReportesDto();
                reportesDto.setNombreBookMark(util.recuperarNombreAnexo(Constantes.ANEXO_DISENO));
                reportesDto.setReporteBytes(byt.toByteArray());
                reportesDto.setOrden(new Integer(1));

                listaTempReport.add(reportesDto);


                tramitePat.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(tramitePat.getIdTramitePatente()));
                List<Anexos> allAnx = tramitePat.getAnexos();
                if (allAnx != null && allAnx.size() > 0) {

                    for (Anexos anx : allAnx) {
                        if (util.recuperarNombreAnexo(anx.getIdTipoanexo())!= "PRIORIDAD" && util.recuperarNombreAnexo(anx.getIdTipoanexo())!= "TRADUCCION"   ){
                            reportesDto = new ReportesDto();
                            reportesDto.setNombreBookMark(util.recuperarNombreAnexo(anx.getIdTipoanexo()));
                            reportesDto.setReporteBytes(anx.getArchivoAnexo());
                            reportesDto.setOrden(patentesViewService.ordenarAnexos(anx));
                            listaTempReport.add(reportesDto);
                            if(anx.getOtroIdioma()==2 && anx.getArchivoTrad()!=null){
                                reportesDto = new ReportesDto();
                                reportesDto.setNombreBookMark(util.recuperarNombreAnexo(anx.getIdTipoanexoTrad()));
                                reportesDto.setReporteBytes(anx.getArchivoTrad());
                                reportesDto.setOrden(patentesViewService.ordenarAnexosTraduccion(anx));
                                listaTempReport.add(reportesDto);
                            }  
                        }
                    }
                }
////////////////////////////////
        //ordenar anexos
        ReportesDto anexoAux;
        for (int i=0;i<listaTempReport.size()-1;i++){            
            for(int j=i+1;j<listaTempReport.size();j++)
            {
                if (listaTempReport.get(j).getOrden() < listaTempReport.get(i).getOrden())
                {
                    anexoAux=listaTempReport.get(i);
                    listaTempReport.set(i,listaTempReport.get(j));
                    listaTempReport.set(j, anexoAux);
                }    
                
            }
        }
        List<Anexos> prioridadAnx=new ArrayList<Anexos>();
        
        for (int i = 0; i < tramitePat.getPrioridades().size(); i++) {
                    String nombrePais = tramitePat.getPrioridades().get(i).getNombrePais();
                    Long idPrioridad = tramitePat.getPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbPrioridad = new AnexosViewDto();
                    Anexos anexoView= new Anexos();     
                    
                    anexoDbPrioridad.setIdPrioridad(idPrioridad);
                    anexoDbPrioridad.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);
                    anexoDbPrioridad = catalogosViewService.selectAnexoDynamic(anexoDbPrioridad);
                    
                    if(anexoDbPrioridad!=null && anexoDbPrioridad.getArchivoAnexo()!=null ){
                        anexoView.setIdAnexo(anexoDbPrioridad.getIdAnexo());
                        anexoView.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);                        
                        anexoView.setIdTramitePatente(anexoDbPrioridad.getIdTramitePatente());
                        anexoView.setArchivoAnexo(anexoDbPrioridad.getArchivoAnexo());
                        anexoView.setNombreArchivo(anexoDbPrioridad.getNombreArchivo());
                        anexoView.setExtension("pdf");
                        prioridadAnx.add(anexoView);
                    }
        }
        
         for (int i = 0; i < tramitePat.getPrioridades().size(); i++) {
                    String nombrePais = tramitePat.getPrioridades().get(i).getNombrePais();
                    Long idPrioridad = tramitePat.getPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbTraduccion = new AnexosViewDto();
                    Anexos traduccionView = new Anexos();       
                                     
                    anexoDbTraduccion.setIdPrioridad(idPrioridad);
                    anexoDbTraduccion.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                    anexoDbTraduccion = catalogosViewService.selectAnexoDynamic(anexoDbTraduccion);
                                        
                    if(anexoDbTraduccion!=null && anexoDbTraduccion.getArchivoAnexo()!=null ){
                        traduccionView.setIdAnexo(anexoDbTraduccion.getIdAnexo());
                        traduccionView.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                        traduccionView.setIdTramitePatente(anexoDbTraduccion.getIdTramitePatente());
                        traduccionView.setArchivoAnexo(anexoDbTraduccion.getArchivoAnexo());
                        traduccionView.setNombreArchivo(anexoDbTraduccion.getNombreArchivo());
                        traduccionView.setExtension("pdf");
                        prioridadAnx.add(traduccionView);
                    }   

        }
        
        if (prioridadAnx != null && prioridadAnx.size() > 0) {
            for (Anexos anx : prioridadAnx) {
                    reportesDto = new ReportesDto();
                    reportesDto.setNombreBookMark(util.recuperarNombreAnexo(anx.getIdTipoanexo()));
                    reportesDto.setReporteBytes(anx.getArchivoAnexo());
                    reportesDto.setOrden(patentesViewService.ordenarAnexos(anx));
                    listaTempReport.add(reportesDto);
            }
        }
        ////////////////////////////////                
//        Coloca leyenda al costado de cada hoja con el numero de expediente
                for (int i = 0; i < listaTempReport.size(); i++) {
                    if (Constantes.nombreSolicitudPDF.compareTo(listaTempReport.get(i).getNombreBookMark()) != 0) {
                        byte[] temp = generarReporte.agregarNumSolicitudReporte(listaTempReport.get(i).getReporteBytes(),
                                reporteDisenoIndustrialDto.getExpediente());
                        listaTempReport.get(i).setReporteBytes(temp);
                    }
                }
                
//                ReportesDto anexoAux;
                for (int i=0;i<listaTempReport.size()-1;i++){            
                    for(int j=i+1;j<listaTempReport.size();j++)
                    {
                        if (listaTempReport.get(j).getOrden() < listaTempReport.get(i).getOrden())
                            {
                                anexoAux=listaTempReport.get(i);
                                listaTempReport.set(i,listaTempReport.get(j));
                                listaTempReport.set(j, anexoAux);
                            }                   
                    }
                }
                
                int orden =listaTempReport.get(0).getOrden();
        
                for (int i=0;i<listaTempReport.size();i++)
                {
                    listaTempReport.get(i).setOrden(orden);
                    orden += 1;
                }
                //    Se valida si existen mas de 1 anexo HOJA DE DESCUENTO, si existe se Reordenan los reporte
                listaReportes = patentesViewService.reordenarReporte(listaTempReport);

//        Ordena reporte
                listaReportes = patentesViewService.ordenarReporte(listaTempReport);
//        Concatena pdfs le implementa el bookmark y la marca de agua 
//        de vista previa en caso de que se requiera.
                reporteFinal = generarReporte.concatenarPdfsWithBookMarks(listaReportes, true);

                session.setAttribute("reporteStream", reporteFinal);

                vistaPrevia = true;
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("reporteDialog.show();");

                RequestContext context1 = RequestContext.getCurrentInstance();
                context1.execute("reporteDialog.hide(); FinalizarDialog.show();");
            }
        } catch (Exception e) {
            log.error("Error:", e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundleParametrosPatentes.getString("mensaje.solicitud.guardar.error")));

            RequestContext context2 = RequestContext.getCurrentInstance();
            context2.execute("unlockWindow('divLockWindow');");
        }
     
    }     

    public boolean validasDesc(String contentType) {
        List<String> listaExtValidas = new ArrayList<String>();
        listaExtValidas.add("pdf");

        for (String objExt : listaExtValidas) {
            if (contentType.contains(objExt)) {
                return true;
            }
        }

        return false;
    }

    private boolean validaImagenesPermitidas() {
        boolean r = false;

        try {
            r = ContextUtils.checkMimeType(file.getInputstream(), file.getContentType(), "image/gif")
                    || ContextUtils.checkMimeType(file.getInputstream(), file.getContentType(), "image/jpeg")
                    || ContextUtils.checkMimeType(file.getInputstream(), file.getContentType(), "image/tiff");
        } catch (Exception ex) {
            log.fatal(ex.getMessage());
        }
        return r;
//        return r && FileServicesUtil.validaGifAnimado(file.getContents());
    }

    public boolean upload() {
        boolean archivoValido = true;
        if (file != null && file.getFileName() != null ? file.getFileName().length() > 0 ? true : false : false) {
            try {
                if (validaImagenesPermitidas()) {
                    if (FileServicesUtil.validaGifAnimado(file.getContents())) {
                        if (file.getContents().length > 2097152) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundleParametrosPatentes.getString("mensaje.error.imagenes.tamano.invalido")));
                        } else if (FileServicesUtil.checkSizeImagePat(file.getContents(), 1410, 2190)) {
                            if (FileServicesUtil.validaDPI(file.getContents())) { //VALIDACION DPI
                            }//VALIDACION DPI
                            else {
                                archivoValido = false;
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundleParametrosPatentes.getString("mensaje.error.imagenes.dpi.invalida")));
                            }
                        } else {
                            archivoValido = false;
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundleParametrosPatentes.getString("mensaje.error.imagenes.resolucion.invalida")));
                        }
                    } else { // VALIDACION GIF ANIMADO
                        archivoValido = false;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundleParametrosPatentes.getString("mensaje.error.imagenes.gif.animada")));
                    }
                } else {
                    archivoValido = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundleParametrosPatentes.getString("mensaje.error.imagenes.extencion.invalida")));
                }
            } catch (Exception ex) {
                log.fatal(ex.getMessage());
            }
            //PERMITE AGREGAR LA FIGURA
            if (!editarImagenDibujo) {
                ImagenDibujo imagenDiseno = new ImagenDibujo();
                if (file != null) {
                    imagenDiseno.setArchivo(file.getContents());
                    imagenDiseno.setNombreArchivo(file.getFileName());
                    imagenDiseno.setOrden((long) tramitePat.getImagenes().size() + 1);
                    imagenDiseno.setCorrecto(archivoValido ? 1 : 0);
                    tramitePat.getImagenes().add(imagenDiseno);
                    session.removeAttribute("imagenes");
                    session.setAttribute("mime", file.getContentType());
                    session.setAttribute("imagenes", tramitePat.getImagenes());
                }
                editarImagenDibujo = false;
                posicionImagenDibujo = null;
            } else {
                if (file != null) {
                    tramitePat.getImagenes().get(posicionImagenDibujo - 1).setArchivo(file.getContents());
                    tramitePat.getImagenes().get(posicionImagenDibujo - 1).setCorrecto(archivoValido ? 1 : 0);
                    //tramitePat.getImagenes().add(imagenDiseno);
                    session.removeAttribute("imagenes");
                    session.setAttribute("mime", file.getContentType());
                    session.setAttribute("imagenes", tramitePat.getImagenes());
                }
                editarImagenDibujo = false;
                posicionImagenDibujo = null;
            }


        } else {
            archivoValido = false;
            editarImagenDibujo = false;
            posicionImagenDibujo = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundleParametrosPatentes.getString("mensaje.error.imagenes.sin.archivo.cargardo")));
        }
        file = null;
        forma.setRutaImagen("");
        return archivoValido;

    }

    public void editarImagen(ImagenDibujo imagenDibujo) {
        log.info("Position: " + imagenDibujo.getOrden());
        editarImagenDibujo = true;
        posicionImagenDibujo = imagenDibujo.getOrden().intValue();
        this.forma.setFileFigura(file);
        file = null;
    }

    public void eliminarImagenes(ImagenDibujo imagen) {
        tramitePat.getImagenes().remove(imagen.getOrden().intValue() - 1);
        long i = 1L;
        for (ImagenDibujo tempImagen : tramitePat.getImagenes()) {
            tempImagen.setOrden(i);
            i++;
        }

        //Crear en sesion para que se muestre el cambio
        session.removeAttribute("imagenes");
        session.setAttribute("imagenes", tramitePat.getImagenes());

        editarImagenDibujo = false;
        posicionImagenDibujo = null;
        file = null;
        forma.setRutaImagen("");
    }

    public void agregarReivindicacion() {
        reivindicacionFlag = true;
        if (!editarReivindicacion) {
            Reivindicacion reinv = new Reivindicacion();
            if (reivindicacion != null ? reivindicacion.length() > 0 : false) {
                log.info("Reivindicacion agregada.");
                reinv.setDescripcion(reivindicacion);
                reinv.setOrden(tramitePat.getReivindicaciones().size() + 1);
                tramitePat.getReivindicaciones().add(reinv);
                reivindicacion = "";
                editarReivindicacion = false;
                posicionReivindicacion = null;
            }
        } else if (reivindicacion != null ? reivindicacion.length() > 0 : false) {
            tramitePat.getReivindicaciones().get(posicionReivindicacion - 1).setDescripcion(reivindicacion);
            reivindicacion = "";
            editarReivindicacion = false;
            posicionReivindicacion = null;
        }
    }

    public void editarReivindicacion(Reivindicacion pReivindicacion) {
        editarReivindicacion = true;
        reivindicacion = pReivindicacion.getDescripcion();
        posicionReivindicacion = pReivindicacion.getOrden();
        reivindicacionFlag = false;
    }

    public void cancelarReivindicaciones() {
        this.reivindicacion = null;
        reivindicacionFlag = true;
//        tramitePat.getReivindicaciones().clear();
    }

    public void cancelarImagenes() {
        tramitePat.getImagenes().clear();
        editarImagenDibujo = false;
        posicionImagenDibujo = null;
        file = null;
        forma.setRutaImagen("");
    }

    public void eliminarReivindicacion(Reivindicacion reivindicacion) {
        tramitePat.getReivindicaciones().remove(reivindicacion);
        int i = 1;
        for (Reivindicacion tempReiv : tramitePat.getReivindicaciones()) {
            tempReiv.setOrden(i);
            i++;
        }
        editarReivindicacion = false;
        posicionReivindicacion = null;
        reivindicacionFlag = false;
        sugerirReivindicacionMem();
    }

    public void consultarPct() {
        if (tramitePat.getNumSolPCT() != null) {
            //datosSolicitudPct = this.patentesViewService.consultarDatosPCt(tramitePat.getNumSolPCT());
            
            lstDatosPCTMU=this.patentesViewService.consultarDatosPCtMU(tramitePat.getNumSolPCT());
            if (lstDatosPCTMU != null && lstDatosPCTMU.size()>0) {
           // if (datosSolicitudPct != null && datosSolicitudPct.getIdSolicitudPct() != null) {
                
//                tramitePat.setFechaSolPCT(Util.parsearFecha(datosSolicitudPct.getFechaSolicitudPct(), "yyyyMMdd"));
//                tramitePat.setFechaPubPCT(Util.parsearFecha(datosSolicitudPct.getFechaPublicacionSolicitudPct(), "yyyyMMdd"));
                tramitePat.setFechaSolPCT(Util.parsearFecha(lstDatosPCTMU.get(0).getDatos(), "yyyyMMdd"));
                tramitePat.setFechaPubPCT(Util.parsearFecha(lstDatosPCTMU.get(2).getDatos(), "yyyyMMdd"));
                //tramitePat.setNumPubPCT(datosSolicitudPct.getNumeroSolicitudPct());
                String tipoPublicacion=lstDatosPCTMU.get(3).getDatos().substring(lstDatosPCTMU.get(3).getDatos().length()-2,lstDatosPCTMU.get(3).getDatos().length());
                String numeroSolicitud=lstDatosPCTMU.get(3).getDatos().replace("/", "").substring(0, 14);
                 if (numeroSolicitud!= null && numeroSolicitud.length() == 14) {
                    part1NumPubPct = numeroSolicitud.replace(" ", "").substring(2, 6);
                    part2NumPubPct = numeroSolicitud.replace(" ", "").substring(7);
                    for (int i = part2NumPubPct.length(); i < 6; i++) {
                        part2NumPubPct = "0" + part2NumPubPct;
                    }
                    //tramitePat.setNumPubPCT(numeroSolicitud);
                    tramitePat.setNumPubPCT("WO " + (part1NumPubPct != null ? part1NumPubPct : "") + "/" + (part2NumPubPct != null ? part2NumPubPct : ""));
                }
                tramitePat.setTipoPubPCT(tipoPublicacion);
                tipoPublicacionPct.setIdTipoPublicacionPct(new Integer(tipoPublicacion.substring(1)));
         
//                if (datosSolicitudPct.getNumeroSolicitudPct() != null && datosSolicitudPct.getNumeroSolicitudPct().length() == 14) {
//                    part1NumPubPct = datosSolicitudPct.getNumeroSolicitudPct().replace(" ", "").substring(2, 6);
//                    part2NumPubPct = datosSolicitudPct.getNumeroSolicitudPct().replace(" ", "").substring(7);
//                }
//                tramitePat.setTipoPubPCT(datosSolicitudPct.getTipoSolicitudPct());
//                tipoPublicacionPct.setIdTipoPublicacionPct(new Integer(datosSolicitudPct.getTipoSolicitudPct().substring(1)));
                habilitarPct = false;
            } else {
                limpiarPct();
                habilitarPct = true;
            }
        }
    }

    public void numeroSolicitud_onchange() {
        tramitePat.setNumSolPCT("PCT/" + (part1NumeroSolPct != null ? part1NumeroSolPct : "") + "/" + (part2NumeroSolPct != null ? part2NumeroSolPct : ""));
        tramitePat.setFechaSolPCT(null);
        tramitePat.setFechaPubPCT(null);
        tramitePat.setNumPubPCT(null);
        tipoPublicacionPct.setIdTipoPublicacionPct(null);
        part1NumPubPct = null;
        part2NumPubPct = null;

        if (tramitePat.getNumSolPCT() != null && tramitePat.getNumSolPCT().length() != 0 && Pattern.matches("PCT/[A-Z]{2}[0-9]{4}\\/[0-9]{6}", tramitePat.getNumSolPCT())) {
            habilitarBusquedaPct = true;
        } else {
            habilitarBusquedaPct = false;
        }
    }

    public void numeroPubPCT_onchange() {
        tramitePat.setNumPubPCT("WO " + (part1NumPubPct != null ? part1NumPubPct : "") + "/" + (part2NumPubPct != null ? part2NumPubPct : ""));
//        if (lstDatosPCTMU.size()>0){
//            habilitarPct=false;
//        }
    }

    public void tipoPublicacionPct_select() {
        if (tipoPublicacionPct.getIdTipoPublicacionPct() != null) {
            tramitePat.setTipoPubPCT("A" + tipoPublicacionPct.getIdTipoPublicacionPct());
        }
    }
    
    public void agregaAnexos(TabChangeEvent event) {
        //this.panel.setActiveIndex("-1");
     
        this.setAcordionIndex("-1");
        this.panel.setActiveIndex("-1");
        if(event.getTab().getId().equals("tabAnexo")){
           // this.guardar(0);
            anexosTramitePatente();
           this.setAcordionIndex("0");
           this.panel.setActiveIndex("0");
           crearCabeceroTramite();
            guardar(0);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (this.getAcordionIndex().equals("0")){
            context.addCallbackParam("status", true);
        }else{
            context.addCallbackParam("status", false);
        }
        return;
    }
    
    public void onTabChange(TabChangeEvent event) {
        FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
//    public void agregaAnexos(TabChangeEvent event){
//       
//       this.guardar(0);
//       //crearCabeceroTramite();
//       anexosTramitePatente();
////       this.getLstAnexos();
////       this.cabeceroTramite.getLstAnexosDocumento();
////       this.tramitePat.getLstAnexosDocumento();
//    }
    
    public void sugerirReivindicacionMem() {

        if (tramitePat.getReivindicaciones() != null && tramitePat.getReivindicaciones().size() > 0) {
            editarReivindicacion = true;
            posicionReivindicacion = Constantes.INIT;
            reivindicacionFlag = true;
            reivindicacion = null;
        } else {
            editarReivindicacion = false;
                    
            String tituloInvencion =  tramitePat.getSubTipoSol().getDescripcion() !=null ? tramitePat.getSubTipoSol().getDescripcion() : "";
                    
            reivindicacion = tituloInvencion + "  "+tramitePat.getInvencion() + ", tal como se ha referido e ilustrado.";
            reivindicacionFlag = false;
            forma.setReivText(Constantes.INIT.toString());
        }
        
        if(tramitePat.getTipoSol() != null && tramitePat.getTipoSol().getIdTiposolicitud().intValue() == Constantes.SOL_DISENO_INDUSTRIAL)
            forma.setRenderedResumen(false);
        else
            forma.setRenderedResumen(true);
        
    }    
    public void esReivindicacion(AjaxBehaviorEvent e) {
         if (this.getDatosAnexos().getIdTipoanexo()!=null){
            if (this.getDatosAnexos().getIdTipoanexo().longValue()== Constantes.ANEXO_REIVINDICACIONES_PATENTE && tramitePat.getTipoSol().getIdTiposolicitud().longValue()== Constantes.SOL_DISENO_INDUSTRIAL.longValue()){
                String tituloInvencion =  tramitePat.getSubTipoSol().getDescripcion() !=null ? tramitePat.getSubTipoSol().getDescripcion() : "";
                this.tramitePat.setPreambulo(tituloInvencion + "  "+tramitePat.getInvencion() + ", tal como se ha referido e ilustrado."); 
            }else{
                this.tramitePat.setPreambulo("");
            }
            forma.setEsDesabilitado(false);
        }else{
            forma.setEsDesabilitado(true);
        }
             
    }
    
    public void esOtroDocumento(AjaxBehaviorEvent e) {

         if (this.getDatosAnexosApoderado().getIdTipoanexo()!=null){
            if (this.getDatosAnexosApoderado().getIdTipoanexo().longValue()== Constantes.ANEXO_OTRO){
//                if (this.getOtroDocumento()!="")
//                this.setOtroDocumento("");
                
                forma.setRenderedOtroDoc(true);
            }else
            {
                forma.setRenderedOtroDoc(false);  
            }
            forma.setEsDesabilitadoPDF(false);
        }else{
            forma.setEsTxtOtroDocumento("");
            forma.setRenderedOtroDoc(false);
            forma.setEsDesabilitadoPDF(true);
        }
    }
    
    
    public void habilitaDocumentoApoderado(AjaxBehaviorEvent e) {

        if (this.getDatosAnexosApoderado().getIdTipoanexo() != null) {

            forma.setEsDesabilitadoPDF(false);
        } else {

            forma.setEsDesabilitadoPDF(true);
        }
    }
    
    
    
    
    
    
    public void sugReivindicacion(){
            if (this.getDatosAnexos().getIdTipoanexo()!=null){
            if (this.getDatosAnexos().getIdTipoanexo().longValue()== Constantes.ANEXO_REIVINDICACIONES_PATENTE){
                String tituloInvencion =  tramitePat.getSubTipoSol().getDescripcion() !=null ? tramitePat.getSubTipoSol().getDescripcion() : "";
                this.tramitePat.setPreambulo(tituloInvencion + "  "+tramitePat.getInvencion() + ", tal como se ha referido e ilustrado."); 
            }else{
                this.tramitePat.setPreambulo("");
            }
        }
    }
    public void aceptarTerminos() {
        if (forma.isChkAceptarTerminos()) {
            forma.setRenderedAceptarTerminos(false);
        } else {
            forma.setRenderedAceptarTerminos(true);
        }
    }

    public void redireccionarInicio() throws IOException {
        try {
            if (forma.getHideDlgTerminos() != Constantes.INIT) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.URL_INDEX_PATENTES);
            } else {
                forma.setHideDlgTerminos(Constantes.FIRST);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void onSelect_Descripcion(AjaxBehaviorEvent e) {
        if ((forma.getRutaDesc() != null && !forma.getRutaDesc().equals("")) || tramitePat.getPreambulo() != null
                || tramitePat.getDescripcionVistas() != null || tramitePat.getTextoAdicional() != null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambioDescripcionDlg.show();");
        } else {
            aceptarDescripcion();
        }

    }
    public void onSelect_Prio()
    {
      if (forma.getPrioPdf().equals("1"))
        {
          forma.setRenderedPrioridades(false);     
        }
      else
      {
       if(forma.getRutaPrioridad()!= null && forma.getRutaPrioridad()!="" )   
       {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambiPrioridadDlg.show();"); 
                
       }
      }
      forma.setRenderedPrioridades(true); 
    }
    
    public void onSelect_TradText(AjaxBehaviorEvent e) {
        patentesViewService.selectTradText(this);
    }

    public void onSelect_TradPdf(AjaxBehaviorEvent e) {
        patentesViewService.selectTradPdf(this);
    }
    
    public void onSelect_DescText(AjaxBehaviorEvent e) {
        patentesViewService.selectDescText(this);
    }

    public void onSelect_DescPdf(AjaxBehaviorEvent e) {
        patentesViewService.selectDescPdf(this);
    }

    public void onSelect_ResuText(AjaxBehaviorEvent e) {
        patentesViewService.selectResuText(this);
    }

    public void onSelect_ResuPdf(AjaxBehaviorEvent e) {
        patentesViewService.selectResuPdf(this);
    }

    public void onSelect_Reivindicacion(AjaxBehaviorEvent e) {
        if ((this.forma.getFileReivindicacion() != null && !new String("").equals(forma.getRutaReiv()))) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambioReivindicacionDlg.show();");
        } else {
            aceptarReivindicacion();
        }

    }
    
   public void onSelect_Prioridad(AjaxBehaviorEvent e) {
        patentesViewService.selectPrioridad(this);
    }

    public void onSelect_Resumen(AjaxBehaviorEvent e) {
        if ((forma.getRutaDesc() != null && !forma.getRutaDesc().equals("")) || tramitePat.getPreambulo() != null
                || tramitePat.getDescripcionVistas() != null || tramitePat.getTextoAdicional() != null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambioDescripcionDlg.show();");
        } else {
            aceptarDescripcion();
        }

    }

    public void aceptarDescripcion() {

        if (forma.getTipoDescripcion().equals(Constantes.INIT.toString())) {
            forma.setRenderedCapturatexto(false);
            forma.setRenderedCargarPdf(true);
            forma.setRutaDesc("");
            anexoDescripcion = new AnexosViewDto();
        } else if (forma.getTipoDescripcion().equals(Constantes.SECOND.toString())) {
            forma.setRenderedCargarPdf(false);
            forma.setRenderedCapturatexto(true);
            tramitePat.setPreambulo("");
            tramitePat.setDescripcionVistas("");
            tramitePat.setTextoAdicional("");
        }
    }

    public void aceptarDescripcionOk() {
            
        if (forma.getValorTipoDesc() == Constantes.INIT) {
            forma.setRenderedCargarPdf(true);
            forma.setRenderedDescText(false);
            forma.setDescPdf("");
            forma.setRutaDesc("");
            //tramitePat.setPreambulo("Agregue aqui la descripción");
            forma.setAnexoDescripcionMe(null);
        }else if (forma.getValorTipoDesc() == Constantes.SECOND) {
            forma.setRenderedDescText(true);
            forma.setRenderedCargarPdf(false);
            forma.setDescText("");
            tramitePat.setPreambulo("");

//            anexoDescripcion = new AnexosViewDto();
//            forma.setAnexoDescripcionMe(new AnexosViewDto());
        } 
        //sugReivindicacion();
        forma.setRenderedResumen(false);
    }
    
    public void aceptarDescripcionTradOk() {

        if (forma.getValorTipoTrad() == Constantes.INIT) {
            forma.setRenderedCargarPdfTrad(true);
            forma.setRenderedDescTextTrad(false);
            forma.setDescPdfTrad("");
            forma.setRutaAnexoTrad("");
            //tramitePat.setPreambulo("Agregue aqui la descripción");
            //forma.setAnexoDescripcionMe(null);
        }else if (forma.getValorTipoTrad() == Constantes.SECOND) {
            forma.setRenderedDescTextTrad(true);
            forma.setRenderedCargarPdfTrad(false);
            forma.setDescTextTrad("");
            forma.setTextoTraduccion("");
            //tramitePat.setPreambulo("");

//            anexoDescripcion = new AnexosViewDto();
//            forma.setAnexoDescripcionMe(new AnexosViewDto());
        } 
        forma.setRenderedResumen(false);
    }
    
        public void aceptarPrioridadOk() {

        if (forma.getValorTipoDesc() == Constantes.INIT) {
            forma.setRenderedCargarPdf(true);
            forma.setRenderedDescText(false);
            forma.setDescPdf("");
            forma.setRutaDesc("");
            forma.setAnexoDescripcionMe(null);
        }else if (forma.getValorTipoDesc() == Constantes.SECOND) {
            forma.setRenderedDescText(true);
            forma.setRenderedCargarPdf(false);
            forma.setDescText("");
            tramitePat.setPreambulo("");

//            anexoDescripcion = new AnexosViewDto();
//            forma.setAnexoDescripcionMe(new AnexosViewDto());
        } 
        forma.setRenderedResumen(false);
    }

    public void aceptarResumen() {

        if (forma.getValorTipoResu() == Constantes.INIT) {
            forma.setRenderedResuPdf(true);
            forma.setRenderedResuText(false);
            forma.setResuPdf("");
            forma.setRutaResum("");
        }else if (forma.getValorTipoResu() == Constantes.SECOND) {
            forma.setRenderedResuText(true);
            forma.setRenderedResuPdf(false);
            forma.setResuText("");
            tramitePat.setTextoAdicional("");
//            anexoDescripcion = new AnexosViewDto();
//            forma.setAnexoResumen(new AnexosViewDto());
        } 
    }

    public void aceptarReivindicacion() {

        if (forma.getTipoReivindicacion().equals(Constantes.INIT.toString())) {
            forma.setRenderedCapturatextoReiv(false);
            forma.setRenderedCargarPdfReiv(true);
            forma.setRutaReiv("");
            anexoReivindicacion = new AnexosViewDto();
        } else if (forma.getTipoReivindicacion().equals(Constantes.SECOND.toString())) {
            forma.setRenderedCargarPdfReiv(false);
            forma.setRenderedCapturatextoReiv(true);
        }
    }

    public void cancelarDescripcion() {
        if (forma.tipoDescripcion.equals(Constantes.INIT.toString())) {
            forma.tipoDescripcion = Constantes.SECOND.toString();
        } else if (forma.tipoDescripcion.equals(Constantes.SECOND.toString())) {
            forma.tipoDescripcion = Constantes.INIT.toString();
        }
    }

    public void cancelarDescripcionOk() {
        if (forma.getValorTipoDesc() == Constantes.INIT) {
            forma.setDescText("");
        } else if (forma.getValorTipoDesc() == Constantes.SECOND) {
            forma.setDescPdf("");
        }
    }
    
    public void cancelarDescripcionTradOk() {
        if (forma.getValorTipoTrad()== Constantes.INIT) {
            forma.setDescTextTrad("");
        } else if (forma.getValorTipoTrad() == Constantes.SECOND) {
            forma.setDescPdfTrad("");
        }
    }

    public void cancelarResumen() {
        if (forma.getValorTipoResu() == Constantes.INIT) {
            forma.setResuText("");
        } else if (forma.getValorTipoResu() == Constantes.SECOND) {
            forma.setResuPdf("");
        }
    }
    /*
     * Metodo que procesa una imagen y la rota segun se indique,
     * Si el indicadorGrados es 0 girara a la derecha, si es
     * 1 girada a la izquierda.
     */

    public void rotarImagen(ImagenDibujo dibujoObj, int indicadorGrados) {

        InputStream inputStream = new ByteArrayInputStream(dibujoObj.getArchivo());
        ImageUtil imageUtil = new ImageUtil();
        FileServicesUtil fileServicesUtil = new FileServicesUtil();
        BufferedImage bufferedImage = null;
        BufferedImage imagenRotada = null;
        String extension = fileServicesUtil.getExtension(dibujoObj.getNombreArchivo());
        try {
            bufferedImage = ImageIO.read(inputStream);

            if (indicadorGrados == 0) {
                imagenRotada = imageUtil.rotate90ToRight(bufferedImage);
            } else {
                imagenRotada = imageUtil.rotate90ToLeft(bufferedImage);
            }
            //Obtener arreglo de bytes del BufferedImage que regresa
            //el proceso de rotacion.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imagenRotada, extension, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();

            //Asignar al objeto el nuevo arreglo de bytes
            dibujoObj.setArchivo(imageInByte);

            //Agregar a sesion la nueva lista
            session.removeAttribute("imagenes");
            session.setAttribute("imagenes", tramitePat.getImagenes());

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(PatentesDisenoIndustrialMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
       public void subirPosicionInventorEnt(int pd ,int p) {
           
        int posicionUltimoEle = pd;
        int posicionActual = p;

        
        if (posicionUltimoEle == -1) {
            return;
        }

        if (listaInventores != null && !listaInventores.isEmpty()) {
            if (posicionUltimoEle > 0) {
                Persona personaMover = listaInventores.get(posicionUltimoEle);
                Persona personaPosicion = listaInventores.get(posicionActual);

                listaInventores.set(posicionUltimoEle, personaPosicion);
                listaInventores.set(posicionActual, personaMover);
            }
        }
        
        short orden = 0;
        for(int i = 0; i < listaInventores.size(); i++){
            Persona solicitante = new Persona();
            
            solicitante.setOrden(orden);
            solicitante.setIdSolicitante(listaInventores.get(i).getIdSolicitante());
            patentesViewService.actualizarPersona(solicitante);
            orden++;
        }
        
    }

    
        /**
     * Este metodo permite subir de lugar al inventor en la tabla de agregados.
     * Subir en la tabla es estar en la posicion mas b aja. *
     */
    public void subirPosicionInventor(Persona p) {
        int posicionActual = listaInventores.indexOf(p);

        if (posicionActual == -1) {
            return;
        }

        if (listaInventores != null && !listaInventores.isEmpty()) {
            if (posicionActual > 0) {
                Persona personaMover = listaInventores.get(posicionActual);
                Persona personaPosicion = listaInventores.get(posicionActual - 1);
                listaInventores.set(posicionActual, personaPosicion);
                listaInventores.set(posicionActual - 1, personaMover);
            }
        }
        
        short orden = 0;
        for(int i = 0; i < listaInventores.size(); i++){
            Persona solicitante = new Persona();
            
            solicitante.setOrden(orden);
            solicitante.setIdSolicitante(listaInventores.get(i).getIdSolicitante());
            patentesViewService.actualizarPersona(solicitante);
            orden++;
        }
        
    }

    /**
     * Este metodo permite bajar de lugar al inventor en la tabla en la cual
     * aparece. *
     */
    public void bajarPosicionInventor(Persona p) {
        int posicionActual = listaInventores.indexOf(p);

        if (posicionActual == -1) {
            return;
        }

        if (listaInventores != null && !listaInventores.isEmpty()) {
            int total = listaInventores.size();
            if ((total - 1) > posicionActual) {
                Persona personaMover = listaInventores.get(posicionActual);
                Persona personaPosicion = listaInventores.get(posicionActual + 1);

                listaInventores.set(posicionActual, personaPosicion);
                listaInventores.set(posicionActual + 1, personaMover);
            }
        }

    }
    
    public boolean buscarExpedienteDivisional(){
      boolean valor=false;
        String oficina=tramitePat.getExpDivisional().substring(0, 2);
        String tipoExp=tramitePat.getExpDivisional().substring(3, 4);
        Integer numExp=Integer.parseInt(tramitePat.getExpDivisional().substring(5, 9));
        Integer serExp= Integer.parseInt(tramitePat.getExpDivisional().substring(10, 16));
        Solicitud solicitud = flujosgralesViewService.selectByExpedienteDivisional(oficina,serExp,numExp,tipoExp);
        if(solicitud == null){
            valor= false;
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso de error expediende divisional de la solicitud", "El expediente divisional de la solicitud No existe o es incorrecto");
//            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else
        {
            valor= true;
        }
      
     
          
        return valor;
    }
            
            
            

    public void hideDlgTerminos() {
        forma.setHideDlgTerminos(Constantes.INIT);
    }

    public boolean isVerPrioridad() {
        return verPrioridad;
    }

    public void setVerPrioridad(boolean verPrioridad) {
        this.verPrioridad = verPrioridad;
    }

    public int getIdProvPrioridad() {
        return idProvPrioridad;
    }

    public void setIdProvPrioridad(int idProvPrioridad) {
        this.idProvPrioridad = idProvPrioridad;
    }

    public TramitePatente getTramitePat() {
        return tramitePat;
    }

    public void setTramitePat(TramitePatente tramitePat) {
        this.tramitePat = tramitePat;
    }

    public boolean isVerPCT() {
        return verPCT;
    }

    public void setVerPCT(boolean verPCT) {
        this.verPCT = verPCT;
    }

    public boolean isVerPreguntaPrioridad() {
        return verPreguntaPrioridad;
    }

    public void setVerPreguntaPrioridad(boolean verPreguntaPrioridad) {
        this.verPreguntaPrioridad = verPreguntaPrioridad;
    }

    public boolean isAplicaDescuento() {
        return aplicaDescuento;
    }

    public void setAplicaDescuento(boolean aplicaDescuento) {
        this.aplicaDescuento = aplicaDescuento;
    }

    public boolean isHabilitarResumen() {
        return habilitarResumen;
    }

    public void setHabilitarResumen(boolean habilitarResumen) {
        this.habilitarResumen = habilitarResumen;
    }

    public List<CatTipopersona> getTiposPersonaInventor() {
        return tiposPersonaInventor;
    }

    public void setTiposPersonaInventor(List<CatTipopersona> tiposPersonaInventor) {
        this.tiposPersonaInventor = tiposPersonaInventor;
    }

    public boolean isEditarInventor() {
        return editarInventor;
    }

    public void setEditarInventor(boolean editarInventor) {
        this.editarInventor = editarInventor;
    }

    public String getNombreInventor() {
        return nombreInventor;
    }

    public void setNombreInventor(String nombreInventor) {
        this.nombreInventor = nombreInventor;
    }

    public Integer getAgregarprioridad() {
        return agregarprioridad;
    }

    public void setAgregarprioridad(Integer agregarprioridad) {
        this.agregarprioridad = agregarprioridad;
    }

    public boolean isEditarPrioridad() {
        return editarPrioridad;
    }

    public void setEditarPrioridad(boolean editarPrioridad) {
        this.editarPrioridad = editarPrioridad;
    }

    public Prioridad getReplica() {
        return replica;
    }

    public void setReplica(Prioridad replica) {
        this.replica = replica;
    }

    public Integer getAgregarPrioridad() {
        return agregarPrioridad;
    }

    public void setAgregarPrioridad(Integer agregarPrioridad) {
        this.agregarPrioridad = agregarPrioridad;
    }

    public String getNombreSolicitante() {
        return nombreSolicitante;
    }

    public void setNombreSolicitante(String nombreSolicitante) {
        this.nombreSolicitante = nombreSolicitante;
    }

    public boolean isEditarSolicitante() {
        return editarSolicitante;
    }

    public void setEditarSolicitante(boolean editarSolicitante) {
        this.editarSolicitante = editarSolicitante;
    }

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    public boolean isEsinventor() {
        return esinventor;
    }

    public void setEsinventor(boolean esinventor) {
        this.esinventor = esinventor;
    }

    public List<EntidadFederativa> getEntidadesFederativas() {
        return entidadesFederativas;
    }

    public void setEntidadesFederativas(List<EntidadFederativa> entidadesFederativas) {
        this.entidadesFederativas = entidadesFederativas;
    }

    public boolean isInventor() {
        return inventor;
    }

    public void setInventor(boolean inventor) {
        this.inventor = inventor;
    }

    public long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(long idTramite) {
        this.idTramite = idTramite;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    public boolean isVerErrores() {
        return verErrores;
    }

    public void setVerErrores(boolean verErrores) {
        this.verErrores = verErrores;
    }

    public void setPatentesViewService(PatentesViewServiceImpl patentesViewService) {
        this.patentesViewService = patentesViewService;
    }

    public void setCatalogosViewService(CatalogosViewServiceImpl catalogosViewService) {
        this.catalogosViewService = catalogosViewService;
    }

    public Persona getApoderadoDatos() {
        return apoderadoDatos;
    }

    public void setApoderadoDatos(Persona apoderadoDatos) {
        this.apoderadoDatos = apoderadoDatos;
    }

    public List<CatSubtiposolicitud> getListSubtiposSol() {
        return listSubtiposSol;
    }

    public void setListSubtiposSol(List<CatSubtiposolicitud> listSubtiposSol) {
        this.listSubtiposSol = listSubtiposSol;
    }

    public List<CatTiposolicitud> getListTiposSol() {
        return listTiposSol;
    }

    public void setListTiposSol(List<CatTiposolicitud> listTiposSol) {
        this.listTiposSol = listTiposSol;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }
    

    public List<CatAnexos> getAnexosMemoria() {
        return anexosMemoria;
    }

    public void setAnexosMemoria(List<CatAnexos> anexosMemoria) {
        this.anexosMemoria = anexosMemoria;
    }

    public Persona getPersoNotDatos() {
        return persoNotDatos;
    }

    public void setPersoNotDatos(Persona persoNotDatos) {
        this.persoNotDatos = persoNotDatos;
    }

    public CatSubtiposolicitud getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(CatSubtiposolicitud subtipo) {
        this.subtipo = subtipo;
    }

    public CatTiposolicitud getTipoSol() {
        return tipoSol;
    }

    public void setTipoSol(CatTiposolicitud tipoSol) {
        this.tipoSol = tipoSol;
    }

    public Prioridad getDatosPrioridad() {
        return datosPrioridad;
    }

    public void setDatosPrioridad(Prioridad datosPrioridad) {
        this.datosPrioridad = datosPrioridad;
    }

    public IncludeDomicilio getDomicilioInventor() {
        return domicilioInventor;
    }

    public void setDomicilioInventor(IncludeDomicilio domicilioInventor) {
        this.domicilioInventor = domicilioInventor;
    }

    public IncludeDomicilio getDomicilioSolicitante() {
        return domicilioSolicitante;
    }

    public void setDomicilioSolicitante(IncludeDomicilio domicilioSolicitante) {
        this.domicilioSolicitante = domicilioSolicitante;
    }

    public Persona getInventorDatos() {
        return inventorDatos;
    }

    public void setInventorDatos(Persona inventorDatos) {
        this.inventorDatos = inventorDatos;
    }

    public List<Persona> getListaInventores() {
        return listaInventores;
    }

    public void setListaInventores(List<Persona> listaInventores) {
        this.listaInventores = listaInventores;
    }

    public List<Prioridad> getListaPrioridades() {
        return listaPrioridades;
    }

    public void setListaPrioridades(List<Prioridad> listaPrioridades) {
        this.listaPrioridades = listaPrioridades;
    }

    public List<Persona> getListaSolicitantes() {
        return listaSolicitantes;
    }

    public void setListaSolicitantes(List<Persona> listaSolicitantes) {
        this.listaSolicitantes = listaSolicitantes;
    }

    public Pais getNacionalidadInvCombo() {
        return nacionalidadInvCombo;
    }

    public void setNacionalidadInvCombo(Pais nacionalidadInvCombo) {
        this.nacionalidadInvCombo = nacionalidadInvCombo;
    }

    public Pais getNacionalidadSolCombo() {
        return nacionalidadSolCombo;
    }

    public void setNacionalidadSolCombo(Pais nacionalidadSolCombo) {
        this.nacionalidadSolCombo = nacionalidadSolCombo;
    }

    public List<Pais> getNacionalidades() {
        return nacionalidades;
    }

    public void setNacionalidades(List<Pais> nacionalidades) {
        this.nacionalidades = nacionalidades;
    }

    public List<Pais> getPaisesPrioridad() {
        return paisesPrioridad;
    }

    public void setPaisesPrioridad(List<Pais> paisesPrioridad) {
        this.paisesPrioridad = paisesPrioridad;
    }

    public Persona getSolicitanteDatos() {
        return solicitanteDatos;
    }

    public void setSolicitanteDatos(Persona solicitanteDatos) {
        this.solicitanteDatos = solicitanteDatos;
    }

    public List<CatTipopersona> getTiposPersona() {
        return tiposPersona;
    }

    public void setTiposPersona(List<CatTipopersona> tiposPersona) {
        this.tiposPersona = tiposPersona;
    }

    public List<CatTipoSolicitante> getTiposSolicitantes() {
        return tiposSolicitantes;
    }

    public void setTiposSolicitantes(List<CatTipoSolicitante> tiposSolicitantes) {
        this.tiposSolicitantes = tiposSolicitantes;
    }

    public Boolean getEditarApoderado() {
        return editarApoderado;
    }

    public void setEditarApoderado(Boolean editarApoderado) {
        this.editarApoderado = editarApoderado;
    }

    public IncludeFirmante getApoderadoInclude() {
        return apoderadoInclude;
    }

    public void setApoderadoInclude(IncludeFirmante apoderadoInclude) {
        this.apoderadoInclude = apoderadoInclude;
    }

    public Persona getPromovente() {
        return promovente;
    }

    public void setPromovente(Persona promovente) {
        this.promovente = promovente;
    }

    public IncludeFirmante getPersonaNotInclude() {
        return personaNotInclude;
    }

    public void setPersonaNotInclude(IncludeFirmante personaNotInclude) {
        this.personaNotInclude = personaNotInclude;
    }

    public Boolean getEditarPersonaNot() {
        return editarPersonaNot;
    }

    public void setEditarPersonaNot(Boolean editarPersonaNot) {
        this.editarPersonaNot = editarPersonaNot;
    }

    public TramitePatente getCabeceroTramite() {
        return cabeceroTramite;
    }

    public void setCabeceroTramite(TramitePatente cabeceroTramite) {
        this.cabeceroTramite = cabeceroTramite;
    }

    public List<AnexosViewDto> getAnexoDto() {
        return anexoDto;
    }

    public void setAnexoDto(List<AnexosViewDto> anexoDto) {
        this.anexoDto = anexoDto;
    }

    public PersonaDM getApoderadoModel() {
        return apoderadoModel;
    }

    public void setApoderadoModel(PersonaDM apoderadoModel) {
        this.apoderadoModel = apoderadoModel;
    }

    public PersonaDM getSolicitanteModel() {
        return solicitanteModel;
    }

    public void setSolicitanteModel(PersonaDM solicitanteModel) {
        this.solicitanteModel = solicitanteModel;
    }

    public Persona getSelectedApoderado() {
        return selectedApoderado;
    }

    public void setSelectedApoderado(Persona selectedApoderado) {
        this.selectedApoderado = selectedApoderado;
    }

    public Persona getSelectedPersonaNot() {
        return selectedPersonaNot;
    }

    public void setSelectedPersonaNot(Persona selectedPersonaNot) {
        this.selectedPersonaNot = selectedPersonaNot;
    }

    public Persona getSelectedSolicitante() {
        return selectedSolicitante;
    }

    public void setSelectedSolicitante(Persona selectedSolicitante) {
        this.selectedSolicitante = selectedSolicitante;
    }

    public boolean isCopiarInventorSolicitante() {
        return copiarInventorSolicitante;
    }

    public void setCopiarInventorSolicitante(boolean copiarInventorSolicitante) {
        this.copiarInventorSolicitante = copiarInventorSolicitante;
    }

    public String getExpDivisional2() {
        return expDivisional2;
    }

    public void setExpDivisional2(String expDivisional2) {
        this.expDivisional2 = expDivisional2;
    }

    public List<Solicitud> getSolicitudesSgapatTable() {
        return solicitudesSgapatTable;
    }

    public void setSolicitudesSgapatTable(List<Solicitud> solicitudesSgapatTable) {
        this.solicitudesSgapatTable = solicitudesSgapatTable;
    }

    public Solicitud getSolSeleccionada() {
        return solSeleccionada;
    }

    public void setSolSeleccionada(Solicitud solSeleccionada) {
        this.solSeleccionada = solSeleccionada;
    }

    public boolean isMarcarSolicitanteAutomatico() {
        return marcarSolicitanteAutomatico;
    }

    public void setMarcarSolicitanteAutomatico(boolean marcarSolicitanteAutomatico) {
        this.marcarSolicitanteAutomatico = marcarSolicitanteAutomatico;
    }

    public boolean isMateriaBiologico() {
        return materiaBiologico;
    }

    public void setMateriaBiologico(boolean materiaBiologico) {
        this.materiaBiologico = materiaBiologico;
    }

    public boolean isPubAnticipada() {
        return pubAnticipada;
    }

    public void setPubAnticipada(boolean pubAnticipada) {
        this.pubAnticipada = pubAnticipada;
    }

    public List<CatCapitulos> getListCapitulos() {
        return listCapitulos;
    }

    public void setListCapitulos(List<CatCapitulos> listCapitulos) {
        this.listCapitulos = listCapitulos;
    }

    public boolean isVerFaseInt() {
        return verFaseInt;
    }

    public void setVerFaseInt(boolean verFaseInt) {
        this.verFaseInt = verFaseInt;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public boolean isMarcarApoderadoAutomatico() {
        return marcarApoderadoAutomatico;
    }

    public void setMarcarApoderadoAutomatico(boolean marcarApoderadoAutomatico) {
        this.marcarApoderadoAutomatico = marcarApoderadoAutomatico;
    }

    public boolean isPersonasFisicasSol() {
        return personasFisicasSol;
    }

    public void setPersonasFisicasSol(boolean personasFisicasSol) {
        this.personasFisicasSol = personasFisicasSol;
    }

    public boolean isEditarImagenDibujo() {
        return editarImagenDibujo;
    }

    public void setEditarImagenDibujo(boolean editarImagenDibujo) {
        this.editarImagenDibujo = editarImagenDibujo;
    }

    public Integer getPosicionImagenDibujo() {
        return posicionImagenDibujo;
    }

    public void setPosicionImagenDibujo(Integer posicionImagenDibujo) {
        this.posicionImagenDibujo = posicionImagenDibujo;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

    public boolean isEditarReivindicacion() {
        return editarReivindicacion;
    }

    public void setEditarReivindicacion(boolean editarReivindicacion) {
        this.editarReivindicacion = editarReivindicacion;
    }

    public Integer getPosicionReivindicacion() {
        return posicionReivindicacion;
    }

    public void setPosicionReivindicacion(Integer posicionReivindicacion) {
        this.posicionReivindicacion = posicionReivindicacion;
    }

    public String getReivindicacion() {
        return reivindicacion;
    }

    public void setReivindicacion(String reivindicacion) {
        this.reivindicacion = reivindicacion;
    }

    public DatosSolicitudPct getDatosSolicitudPct() {
        return datosSolicitudPct;
    }

    public void setDatosSolicitudPct(DatosSolicitudPct datosSolicitudPct) {
        this.datosSolicitudPct = datosSolicitudPct;
    }

    public boolean isHabilitarPct() {
        return habilitarPct;
    }

    public void setHabilitarPct(boolean habilitarPct) {
        this.habilitarPct = habilitarPct;
    }

    public List<CatTipopersona> getTiposPersonaApoderados() {
        return tiposPersonaApoderados;
    }

    public void setTiposPersonaApoderados(List<CatTipopersona> tiposPersonaApoderados) {
        this.tiposPersonaApoderados = tiposPersonaApoderados;
    }

    public List<TipoPublicacionPct> getTiposPublicacionPct() {
        return tiposPublicacionPct;
    }

    public void setTiposPublicacionPct(List<TipoPublicacionPct> tiposPublicacionPct) {
        this.tiposPublicacionPct = tiposPublicacionPct;
    }

    public TipoPublicacionPct getTipoPublicacionPct() {
        return tipoPublicacionPct;
    }

    public void setTipoPublicacionPct(TipoPublicacionPct tipoPublicacionPct) {
        this.tipoPublicacionPct = tipoPublicacionPct;
    }

    public boolean isHabilitarBusquedaPct() {
        return habilitarBusquedaPct;
    }

    public void setHabilitarBusquedaPct(boolean habilitarBusquedaPct) {
        this.habilitarBusquedaPct = habilitarBusquedaPct;
    }

    public String getPart1NumeroSolPct() {
        return part1NumeroSolPct;
    }

    public void setPart1NumeroSolPct(String part1NumeroSolPct) {
        this.part1NumeroSolPct = part1NumeroSolPct;
    }

    public String getPart2NumeroSolPct() {
        return part2NumeroSolPct;
    }

    public void setPart2NumeroSolPct(String part2NumeroSolPct) {
        this.part2NumeroSolPct = part2NumeroSolPct;
    }

    public String getPart1NumPubPct() {
        return part1NumPubPct;
    }

    public void setPart1NumPubPct(String part1NumPubPct) {
        this.part1NumPubPct = part1NumPubPct;
    }

    public String getPart2NumPubPct() {
        return part2NumPubPct;
    }

    public void setPart2NumPubPct(String part2NumPubPct) {
        this.part2NumPubPct = part2NumPubPct;
    }

    public Boolean getVistaPrevia() {
        return vistaPrevia;
    }

    public void setVistaPrevia(Boolean vistaPrevia) {
        this.vistaPrevia = vistaPrevia;
    }

    public String getFlujoDescripcion() {
        return flujoDescripcion;
    }

    public void setFlujoDescripcion(String flujoDescripcion) {
        this.flujoDescripcion = flujoDescripcion;
    }

    /**
     * Indica si pertenece al flujo capturar descripcion o cargar archivo
     *
     * @return True si pertenece al flujo para cargar el archivo desde la
     * pestaña Descripcion
     */
    public boolean isFlujoDescripcionArchivo() {
        return "2".equals(flujoDescripcion);
    }

    public String getMensajeDescripcionVistas() {
        return mensajeDescripcionVistas;
    }

    public void setMensajeDescripcionVistas(String mensajeDescripcionVistas) {
        this.mensajeDescripcionVistas = mensajeDescripcionVistas;
    }

    public AnexosViewDto getAnexoDescripcion() {
        return anexoDescripcion;
    }

    public void setAnexoDescripcion(AnexosViewDto anexoDescripcion) {
        this.anexoDescripcion = anexoDescripcion;
    }

    public Boolean getReivindicacionFlag() {
        return reivindicacionFlag;
    }

    public void setReivindicacionFlag(Boolean reivindicacionFlag) {
        this.reivindicacionFlag = reivindicacionFlag;
    }

    public CapturaSolicitudForm getForma() {
        return forma;
    }

    public void setForma(CapturaSolicitudForm forma) {
        this.forma = forma;
    }

    public boolean isRevisionFinalizaCaptura() {
        return revisionFinalizaCaptura;
    }

    public void setRevisionFinalizaCaptura(boolean revisionFinalizaCaptura) {
        this.revisionFinalizaCaptura = revisionFinalizaCaptura;
        if (protestaFinalizaCaptura == true && revisionFinalizaCaptura == true)
        {
            this.completaFinalizaCaptura= true;
        }else
        {
            this.completaFinalizaCaptura= false;
        }
    }
    
    public boolean isProtestaFinalizaCaptura() {
        return protestaFinalizaCaptura;
    }

    public void setProtestaFinalizaCaptura(boolean protestaFinalizaCaptura) {
        this.protestaFinalizaCaptura = protestaFinalizaCaptura;
        if (protestaFinalizaCaptura == true && revisionFinalizaCaptura == true)
        {
            this.completaFinalizaCaptura= true;
        }else
        {
            this.completaFinalizaCaptura= false;
        }
   }
    
     public boolean isCompletaFinalizaCaptura() {
        return completaFinalizaCaptura;
    }

    public void setCompletaFinalizaCaptura(boolean completaFinalizaCaptura) {
        this.completaFinalizaCaptura = completaFinalizaCaptura;
    }

    public boolean isFechaValida() {
        return fechaValida;
    }

    public void setFechaValida(boolean fechaValida) {
        this.fechaValida = fechaValida;
    }

    public int getIndicadorFecha() {
        return indicadorFecha;
    }

    public void setIndicadorFecha(int indicadorFecha) {
        this.indicadorFecha = indicadorFecha;
    }

    public ImagenDibujo getDibujoSeleccionado() {
        return dibujoSeleccionado;
    }

    public void setDibujoSeleccionado(ImagenDibujo dibujoSeleccionado) {
        this.dibujoSeleccionado = dibujoSeleccionado;
    }

    public StreamedContent getImagenMostrar() {
        return imagenMostrar;
    }

    public void setImagenMostrar(StreamedContent imagenMostrar) {
        this.imagenMostrar = imagenMostrar;
    }

    public AnexosViewDto getAnexoReivindicacion() {
        return anexoReivindicacion;
    }

    public void setAnexoReivindicacion(AnexosViewDto anexoReivindicacion) {
        this.anexoReivindicacion = anexoReivindicacion;
    }

    public AnexosViewDto getAnexoResumen() {
        return anexoResumen;
    }

    public void setAnexoResumen(AnexosViewDto anexoResumen) {
        this.anexoResumen = anexoResumen;
    }

    public String getMsgMostrarFchPrioridad() {
        return msgMostrarFchPrioridad;
    }

    public void setMsgMostrarFchPrioridad(String msgMostrarFchPrioridad) {
        this.msgMostrarFchPrioridad = msgMostrarFchPrioridad;
    }

    /**
     * @return the anexoArcDivPre
     */
    public AnexosViewDto getAnexoArcDivPre() {
        return anexoArcDivPre;
    }

    /**
     * @param anexoArcDivPre the anexoArcDivPre to set
     */
    public void setAnexoArcDivPre(AnexosViewDto anexoArcDivPre) {
        this.anexoArcDivPre = anexoArcDivPre;
    }

    /**
     * @return the docDivPrevia
     */
    public boolean isDocDivPrevia() {
        return docDivPrevia;
    }

    /**
     * @param docDivPrevia the docDivPrevia to set
     */
    public void setDocDivPrevia(boolean docDivPrevia) {
        this.docDivPrevia = docDivPrevia;
    }

    /**
     * @return the numElemto
     */
    public int getNumElemto() {
        return numElemto;
    }

    /**
     * @param numElemto the numElemto to set
     */
    public void setNumElemto(int numElemto) {
        this.numElemto = numElemto;
    }

    /**
     * @return the banderaEditar
     */
    public boolean isBanderaEditar() {
        return banderaEditar;
    }

    /**
     * @param banderaEditar the banderaEditar to set
     */
    public void setBanderaEditar(boolean banderaEditar) {
        this.banderaEditar = banderaEditar;
    }

    /**
     * @return the posicionActualwe
     */
    public int getPosicionActualwe() {
        return posicionActualwe;
    }

    /**
     * @param posicionActualwe the posicionActualwe to set
     */
    public void setPosicionActualwe(int posicionActualwe) {
        this.posicionActualwe = posicionActualwe;
    }
    
    public String getDisabledEditarPri() {
        return disabledEditarPri;
    }

    public void setDisabledEditarPri(String disabledEditarPri) {
        this.disabledEditarPri = disabledEditarPri;
    }

    public String getDisabledEliminar() {
        return disabledEliminar;
    }

    public void setDisabledEliminar(String disabledEliminar) {
        this.disabledEliminar = disabledEliminar;
    }
    
    public String getFirmante() {
        return firmante;
    }

    public void setFirmante(String firmante) {
        this.firmante = firmante;
    }

    public String getSello() {
        return sello;
    }

    public void setSello(String sello) {
        this.sello = sello;
    }
    
    public int getnPaginas() {
        return nPaginas;
    }

    public void setnPaginas(int nPaginas) {
        this.nPaginas = nPaginas;
    }
    //////////////////////////////////////
     public boolean isEditarAnexoMT() {
        return editarAnexoMT;
    }

    public void setEditarAnexoMT(boolean editarAnexoMT) {
        this.editarAnexoMT = editarAnexoMT;
    }
    
    public int getIdAnexoMemoria() {
        return idAnexoMemoria;
    }

    public void setIdAnexoMemoria(int idAnexoMemoria) {
        this.idAnexoMemoria = idAnexoMemoria;
    }
   
    public CatAnexos getDatosAnexos() {
        return datosAnexos;
    }

    public void setDatosAnexos(CatAnexos datosAnexos) {
        this.datosAnexos = datosAnexos;
    }
    
    public List<AnexosViewDto> getLstAnexosDto() {
        return lstAnexosDto;
    }

    public void setLstAnexosDto(List<AnexosViewDto> lstAnexosDto) {
        this.lstAnexosDto = lstAnexosDto;
    }
    
    public AnexosViewDto getAnexoSelected() {
        return anexoSelected;
    }

    public void setAnexoSelected(AnexosViewDto anexoSelected) {
        this.anexoSelected = anexoSelected;
    }
    
        
    public boolean isEditarAnexo() {
        return editarAnexo;
    }

    public void setEditarAnexo(boolean editarAnexo) {
        this.editarAnexo = editarAnexo;
    }
    
    public void setFileDownload(StreamedContent fileDownload) {
        this.fileDownload = fileDownload;
    }

    public String getFileDownload() throws IOException {
        StreamedContent file = null;
        InputStream stream = null;
//      cambios voista previa        
        ByteArrayOutputStream outStream=null;
        forma.setRenderedVistaPrevia(true);
        session.removeAttribute("reporteStream");
        
        Util utilArch = new Util();

        if (null != anexoSelected) {
            if (null != anexoSelected.getArchivoAnexo()) {
                stream = new ByteArrayInputStream(anexoSelected.getArchivoAnexo());
//              Prueba Visualizar archivo  -----------
                //outStream = new ByteArrayOutputStream(anexoSelected.getArchivoAnexo().length);
                outStream = new ByteArrayOutputStream();
//                int read;
//                while((read = stream.read(anexoSelected.getArchivoAnexo())) >= 0)
//                {
//                    outStream.write(anexoSelected.getArchivoAnexo(), 0, read);
//                }
//                outStream.flush();
                outStream.write(anexoSelected.getArchivoAnexo());
                session.setAttribute("reporteStream", outStream);
                vistaPrevia = true;
//                RequestContext context = RequestContext.getCurrentInstance();
//                context.execute("reporteDialog.show();");
////                ----------------
//                MimeType mType = utilArch.extractMime(anexoSelected.getExtension());
//                String sMime = mType.getMime();
//                String sNmDownload = anexoSelected.getNombreArchivo();
//                file = new DefaultStreamedContent(stream, sMime, sNmDownload);
            }
        }
        return null;
    }
    
    public void setFileDownloadTrad(StreamedContent fileDownloadTrad) {
        this.fileDownloadTrad = fileDownloadTrad;
    }
     public String getFileDownloadTrad() throws IOException {      
        ByteArrayOutputStream outStream=null;
        forma.setRenderedVistaPrevia(true);
        session.removeAttribute("reporteStream");       
        Util utilArch = new Util();
        if (null != anexoSelected) {
            if (null != anexoSelected.getArchivoTrad()) {
                outStream = new ByteArrayOutputStream();
                outStream.write(anexoSelected.getArchivoTrad());
                session.setAttribute("reporteStream", outStream);
                vistaPrevia = true;
            }
        }
        return null;
    }
     
//     public StreamedContent getFileDownloadTrad() {
//        StreamedContent file = null;
//        InputStream stream = null;
//        Util utilArch = new Util();
//
//        if (null != anexoSelected) {
//            if (null != anexoSelected.getArchivoTrad()) {
//                stream = new ByteArrayInputStream(anexoSelected.getArchivoTrad());
//                MimeType mType = utilArch.extractMime(anexoSelected.getExtension());
//                String sMime = mType.getMime();
//                String sNmDownload = anexoSelected.getNombreTrad();
//                file = new DefaultStreamedContent(stream, sMime, sNmDownload);
//            }
//        }
//        return file;
//    }
    
//    public StreamedContent getFileDownloadApoderado() {
//        StreamedContent file = null;
//        InputStream stream = null;
//        Util utilArch = new Util();
//
//        if (null != anexoSelectedApoderado) {
////            if (null != anexoSelectedApoderado.getArchivoTrad()) {
//                stream = new ByteArrayInputStream(anexoSelectedApoderado.getArchivoAnexo());
//                MimeType mType = utilArch.extractMime(anexoSelectedApoderado.getExtension());
//                String sMime = mType.getMime();
//                String sNmDownload = anexoSelectedApoderado.getNombreArchivo();
//                file = new DefaultStreamedContent(stream, sMime, sNmDownload);
////            }
//        }
//        return file;
//    }
    public String getFileDownloadApoderado() throws IOException {      
        ByteArrayOutputStream outStream=null;
        forma.setRenderedVistaPrevia(true);
        session.removeAttribute("reporteStream");       
        Util utilArch = new Util();
        if (null != anexoSelectedApoderado) {
            if (null != anexoSelectedApoderado.getArchivoAnexo()) {
                outStream = new ByteArrayOutputStream();
                outStream.write(anexoSelectedApoderado.getArchivoAnexo());
                session.setAttribute("reporteStream", outStream);
                vistaPrevia = true;
            }
        }
        return null;
    }

    public void setFileDownloadApoderado(StreamedContent fileDownloadApoderado) {
        this.fileDownloadApoderado = fileDownloadApoderado;
    }
    
    public void agregarAnexoDto()throws IOException, SAXException, TikaException{
        AnexosViewDto elemento = new AnexosViewDto();
        boolean insertar = true;
        String msgAviso = "";
        String descripcion="";
        long idAnexoDto=0;
        elemento.setCargado(false);
        elemento.setTradCargada(false);
        msgAviso = validaAnexosMemoria();
        if (msgAviso.length() != 0) {
            insertar = false;
        }
        try{
        if (this.anexoSelected==null){
            this.anexoSelected= new AnexosViewDto();
        }

        if (insertar) {
            for (int i=0;i< this.anexosMemoria.size();i++)
            {
                if (this.anexosMemoria.get(i).getIdTipoanexo().longValue()== this.datosAnexos.getIdTipoanexo().longValue())
                {
                    descripcion=this.anexosMemoria.get(i).getDescripcion();
                    idAnexoDto=this.anexosMemoria.get(i).getIdTipoanexo().longValue();
                    break;
                }
            }
            if (this.forma.getIdiomaAnexo().equals("1")){
                elemento.setOtroIdioma((short)1);
            }else{
                elemento.setOtroIdioma((short)2);
            }
                    
            elemento.setIdTipoanexo(idAnexoDto);
            elemento.setDescripcion(descripcion);
            elemento.setIdSubtiposolicitud(tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue());
            elemento.setOrden(ordenaAnexos(idAnexoDto).shortValue());
            elemento.setVisiblexcarga(new Short("1"));
            if (this.anexoSelected.getCargado()!=null && this.tramitePat.getPreambulo().equals("")) {
                elemento.setCargado(true);
                elemento.setNombreArchivo(this.anexoSelected.getNombreArchivo());
                elemento.setExtension(this.anexoSelected.getExtension());
                elemento.setArchivoAnexo(this.anexoSelected.getArchivoAnexo());
            }else{
                elemento.setTxtAnexo(this.tramitePat.getPreambulo());
            }
            
            if (elemento.getOtroIdioma()==2){
                if (this.anexoSelected.getTradCargada()!=null && this.forma.getTextoTraduccion().equals("")) {
                    if (this.anexoSelected.getTradCargada()==true){
                        elemento.setTradCargada(true);
                        elemento.setNombreTrad(this.anexoSelected.getNombreTrad());
                        elemento.setArchivoTrad(this.anexoSelected.getArchivoTrad());
                        elemento.setExtension(this.anexoSelected.getExtension());
                    }
                }else{
                    if (!this.forma.getTextoTraduccion().equals("")){
                        elemento.setTxtAnexoTrad(this.forma.getTextoTraduccion());
                    }
                }
            }

            if (!editarAnexoMT) {              
                
                if (lstAnexosDto.size()>0){
                    for (int i=0;i<lstAnexosDto.size();i++){
                        if (lstAnexosDto.get(i).getIdTipoanexo().longValue()== elemento.getIdTipoanexo().longValue()){
                            msgAviso += "El anexo ya se encuentra agregado" + "||";
                            insertar = false;
                            break;
                        } 
                    }
                    if (insertar==true){
                        lstAnexosDto.add(elemento);
                    }
                }else{
                    lstAnexosDto.add(elemento);    
                }

            } else {
                for (int i = 0; i < lstAnexosDto.size(); i++) {
                    AnexosViewDto elementoLista = (AnexosViewDto) lstAnexosDto.get(i);
                        if (elementoLista.getIdTipoanexo().longValue() == elemento.getIdTipoanexo().longValue()) {
                            if(elementoLista.getIdAnexo()==0){ 
                                lstAnexosDto.remove(i);
                            }else{
                                flujosgralesViewService.deleteAnexosByIds(elementoLista.getIdAnexo());
                                lstAnexosDto.remove(i);
                            }
                           lstAnexosDto.add(elemento);
                        }
                }
//                AnexosViewDto anexoAux; 
//                for (int i=0;i<lstAnexosDto.size()-1;i++){            
//                    for(int j=i+1;j<lstAnexosDto.size();j++)
//                    {
//                        if (lstAnexosDto.get(j).getIdTipoanexo() < lstAnexosDto.get(i).getIdTipoanexo())
//                        {
//                            anexoAux=lstAnexosDto.get(i);
//                            lstAnexosDto.set(i,lstAnexosDto.get(j));
//                            lstAnexosDto.set(j, anexoAux);
//                        }    
//                
//                     }
//                }
                editarAnexo = false;
             }
            AnexosViewDto anexoAux;             
            for (int i=0;i<lstAnexosDto.size()-1;i++){            
                    for(int j=i+1;j<lstAnexosDto.size();j++)
                    {
                        if (lstAnexosDto.get(j).getIdTipoanexo() < lstAnexosDto.get(i).getIdTipoanexo())    
                        {
                            anexoAux=lstAnexosDto.get(i);
                            lstAnexosDto.set(i,lstAnexosDto.get(j));
                            lstAnexosDto.set(j, anexoAux);
                        }    
                
                     }
               }

            if (msgAviso != null && msgAviso.length() == 0 ) {
                cancelarAnexo();
            }
        }
        
        if (insertar == false) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Errores",msgAviso);
            FacesContext.getCurrentInstance().addMessage(null, message);
            convierteListaErrores(msgAviso);
            verErrores = true;
        } else {
            verErrores = false;
            cancelarAnexo();
        }
      } catch (Exception e) {
            log.error("Error:", e);
      } 

    }
    
       public void agregarAnexoApoderado()throws IOException, SAXException, TikaException{
        AnexosViewDto elemento = new AnexosViewDto();
        boolean insertar = true;
        String msgAviso = "";
        String descripcion="";
        long idAnexoDto=0;
        elemento.setCargado(false);
        //if (lstAnexosApoderado.isEmpty()||editarAnexoApoderado)
//        if (editarAnexoApoderado==false){
            try{
            
            if (this.anexoSelectedApoderado==null){
                this.anexoSelectedApoderado= new AnexosViewDto();
            }
            if(anexoSelectedApoderado !=null){
                if(anexoSelectedApoderado.getNombreArchivo()==null){
                    msgAviso += "Se debe cargar el archivo PDF" + "||";   
                }
            }else{
                  msgAviso += "Se debe cargar el archivo PDF" + "||";   
            }  
            if (msgAviso.length() != 0) {
                insertar = false;
            }
            if (insertar) {
                for (int i=0;i< this.anexosApoderado.size();i++)
                {
                    if (this.anexosApoderado.get(i).getIdTipoanexo().longValue()== this.datosAnexosApoderado.getIdTipoanexo().longValue())
                    {
                        descripcion=this.anexosApoderado.get(i).getDescripcion();
                        idAnexoDto=this.anexosApoderado.get(i).getIdTipoanexo().longValue();
                        break;
                    }
                }
                elemento.setOtroIdioma((short)1);
                elemento.setIdTipoanexo(idAnexoDto);
                elemento.setDescripcion(descripcion);
                elemento.setIdSubtiposolicitud(tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue());
                elemento.setOrden(ordenaAnexos(idAnexoDto).shortValue());
                elemento.setVisiblexcarga(new Short("1"));
                if (this.anexoSelectedApoderado.getCargado()!=null) {
                    elemento.setCargado(true);
                    elemento.setNombreArchivo(this.anexoSelectedApoderado.getNombreArchivo());
                    elemento.setExtension(this.anexoSelectedApoderado.getExtension());
                    elemento.setArchivoAnexo(this.anexoSelectedApoderado.getArchivoAnexo());
                    if (descripcion.equals("OTRO")){
                        elemento.setTxtAnexo(forma.getEsTxtOtroDocumento());
                    }
                }
                if (!editarAnexoApoderado) {              
                    if (lstAnexosApoderado.size()>0){
                        for (int i=0;i<lstAnexosApoderado.size();i++){
                            if (lstAnexosApoderado.get(i).getIdTipoanexo().longValue()== elemento.getIdTipoanexo().longValue()){
                                msgAviso += "El anexo ya se encuentra agregado" + "||";
                                insertar = false;
                                break;
                            } 
                        }
                        if (insertar==true){
                            lstAnexosApoderado.add(elemento);
                        }
                    }else{
                        lstAnexosApoderado.add(elemento);    
                    }
                } else {
                    for (int i = 0; i < lstAnexosApoderado.size(); i++) {
                        AnexosViewDto elementoLista = (AnexosViewDto) lstAnexosApoderado.get(i);
                        if (elementoLista.getIdTipoanexo().longValue() == elemento.getIdTipoanexo().longValue()) {
                            if(elementoLista.getIdAnexo()==0){ 
                                lstAnexosApoderado.remove(i);
                            }else{
                                flujosgralesViewService.deleteAnexosByIds(elementoLista.getIdAnexo());
                                lstAnexosApoderado.remove(i);
                            }
                            lstAnexosApoderado.add(elemento);
                        }
                    }
                    editarAnexoApoderado = false;
                }
                AnexosViewDto anexoAux;             
                for (int i=0;i<lstAnexosApoderado.size()-1;i++){            
                    for(int j=i+1;j<lstAnexosApoderado.size();j++)
                    {
                        if (lstAnexosApoderado.get(j).getIdTipoanexo() < lstAnexosApoderado.get(i).getIdTipoanexo())    
                        {
                            anexoAux=lstAnexosApoderado.get(i);
                            lstAnexosApoderado.set(i,lstAnexosApoderado.get(j));
                            lstAnexosApoderado.set(j, anexoAux);
                        }    
                
                     }
                }

                if (msgAviso != null && msgAviso.length() == 0 ) {
                    cancelarAnexoApoderado();
                }
            }
        
            if (insertar == false) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Errores",msgAviso);
                FacesContext.getCurrentInstance().addMessage(null, message);
                convierteListaErrores(msgAviso);
                verErrores = true;
            } else {
                verErrores = false;
                cancelarAnexoApoderado();
            }
          } catch (Exception e) {
            log.error("Error:", e);
          } 
//        }else{
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Errores","El anexo para acreditar la personalidad ya se encuentra agregado" + "||");
//            FacesContext.getCurrentInstance().addMessage(null, message);
//            convierteListaErrores("El anexo para acreditar la personalidad ya se encuentra agregado" + "||");
//            verErrores = true;  
//            cancelarAnexoApoderado();
//        }      

 }
       
    public void editarAnexoMT(AnexosViewDto elemento) {
     
     if (elemento != null){
        editarAnexoMT=true;
        this.anexoSelected=elemento;
        forma.setAnexoSeleccionado(this.anexoSelected);
        forma.setEsDesabilitado(false);
        this.forma.setIdiomaAnexo(elemento.getOtroIdioma().toString());
        if (elemento.getCargado()!=null && elemento.getTxtAnexo()==null){
            this.forma.setValorTipoDesc(Constantes.SECOND);                     
            aceptarDescripcionOk();
            this.forma.setDescText("");
            this.forma.setDescPdf("2");
            this.forma.setRutaDesc(elemento.getNombreArchivo());
            this.datosAnexos.setIdTipoanexo(elemento.getIdTipoanexo());
        }else{
            this.forma.setValorTipoDesc(Constantes.INIT);                     
            aceptarDescripcionOk();
            this.forma.setDescText("1");
            this.forma.setDescPdf("");
            this.tramitePat.setPreambulo(elemento.getTxtAnexo());
            this.datosAnexos.setIdTipoanexo(elemento.getIdTipoanexo());
        }
        if (elemento.getTradCargada()!=null && elemento.getTxtAnexoTrad()==null){
            if (elemento.getTradCargada()==true){
                this.forma.setValorTipoTrad(Constantes.SECOND);                     
                aceptarDescripcionTradOk();
                this.forma.setDescTextTrad("");
                this.forma.setDescPdfTrad("2");
                this.forma.setRutaAnexoTrad(elemento.getNombreTrad());
            }else{
                this.forma.setValorTipoTrad(Constantes.INIT);                     
                aceptarDescripcionTradOk();
                this.forma.setDescTextTrad("1");
                this.forma.setDescPdfTrad("");
            }    
        }else{
            this.forma.setValorTipoTrad(Constantes.INIT);                     
            aceptarDescripcionTradOk();
            this.forma.setDescTextTrad("1");
            this.forma.setDescPdfTrad("");
            this.forma.setTextoTraduccion(elemento.getTxtAnexoTrad());
        }
     }
         
        
    }
    
    public void eliminarAnexoApoderado(AnexosViewDto elemento) {
     
     if (elemento != null){
        for (int i = 0; i < lstAnexosApoderado.size(); i++) {
            AnexosViewDto elementoLista = (AnexosViewDto) lstAnexosApoderado.get(i);
            if (elementoLista.getIdTipoanexo() == elemento.getIdTipoanexo()) {
                if(elementoLista.getIdAnexo()==0){
                    lstAnexosApoderado.remove(i);
                }else{
                    flujosgralesViewService.deleteAnexosByIds(elementoLista.getIdAnexo());
                    lstAnexosApoderado.remove(i);
                }
                cancelarAnexo();
                break;
            }
        }
      }
     }
    
    
    public void editarAnexoApoderado(AnexosViewDto elemento) {
     
     if (elemento != null){
        editarAnexoApoderado=true;
        this.anexoSelectedApoderado=elemento;
        this.datosAnexosApoderado.setIdTipoanexo(elemento.getIdTipoanexo());
        this.forma.setRutaAnexoApoderado(elemento.getNombreArchivo());
        esOtroDocumento(null);
        if (elemento.getIdTipoanexo().longValue()== Constantes.ANEXO_OTRO){
            forma.setEsTxtOtroDocumento(elemento.getTxtAnexo());
        }
     }        
         
        
    }
    
     public void eliminarAnexoMT(AnexosViewDto elemento) {
     
     if (elemento != null){
        for (int i = 0; i < lstAnexosDto.size(); i++) {
            AnexosViewDto elementoLista = (AnexosViewDto) lstAnexosDto.get(i);
            if (elementoLista.getIdTipoanexo() == elemento.getIdTipoanexo()) {
                if(elementoLista.getIdAnexo()==0){
                    lstAnexosDto.remove(i);
                }else{
                    flujosgralesViewService.deleteAnexosByIds(elementoLista.getIdAnexo());
                    lstAnexosDto.remove(i);
                }
                cancelarAnexo();
                break;
            }
        }
      }
     }
    public void cancelarAnexo() {
       try
       {
        this.forma.setValorTipoDesc(Constantes.INIT );
        datosAnexos.setIdTipoanexo(null);
        this.anexoSelected=null;
        this.forma.setRenderedDescText(true);
        this.forma.setRenderedCargarPdf(false);
        this.forma.setRutaDesc("");
        this.tramitePat.setPreambulo("");
        this.forma.setDescText("1");
        this.forma.setIdiomaAnexo("1");
        this.forma.setDescPdf("");
        editarAnexoMT=false;
        aceptarDescripcionOk();
        this.forma.setValorTipoTrad(Constantes.INIT );
        this.forma.setRenderedDescTextTrad(true);
        this.forma.setRenderedCargarPdfTrad(false);
        this.forma.setRutaAnexoTrad("");
        this.forma.setTextoTraduccion("");
        this.forma.setDescTextTrad("1");
        this.forma.setDescPdfTrad("");
        aceptarDescripcionTradOk();
      } 
       catch (Exception e) {
            log.error("Error:", e);
      }  
    }
        public void cancelarAnexoApoderado() {
       try
       {
        this.datosAnexosApoderado.setIdTipoanexo(null);
        this.anexoSelectedApoderado=null;
        this.forma.setRutaAnexoApoderado("");
        this.forma.setEsTxtOtroDocumento("");
        editarAnexoApoderado=false;
        esOtroDocumento(null);
      } 
       catch (Exception e) {
            log.error("Error:", e);
      }  
    }
    private String validaAnexosMemoria() {
        String msg = "";

        if ( datosAnexos.getIdTipoanexo() == null || datosAnexos.getIdTipoanexo() == 0) {
                msg += "Se debe seleccionar un tipo de anexo" + "||";
        }
        if (tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue()==0){
            msg += "Se debe seleccionar el subtipo de la solicitud" + "||";
        }
        
        if (forma.getDescText()!=null){
            if (tramitePat.getPreambulo()==null ||tramitePat.getPreambulo().equals("")){
                msg += "Se debe capturar el texto de la descripción" + "||";
            }
        }else{
            if(anexoSelected !=null){
                if(anexoSelected.getNombreArchivo()==null){
                    msg += "Se debe cargar el archivo PDF" + "||";   
                }
            }else{
                  msg += "Se debe cargar el archivo PDF" + "||";   
            }  
        }
        
//        if (anexoSelected !=null){
//           if (this.forma.getIdiomaAnexo().equals("2")){
//                if (forma.getDescTextTrad()!=null){
//                    if (forma.getDescTextTrad().equals("1") && forma.getTextoTraduccion().equals("") ){
//                        msg += "Se debe capturar el texto de la traducción" + "||";
//                    }
//                }    
//                if (forma.getDescPdfTrad()!=null){
//                    if (forma.getDescPdfTrad().equals("2") && anexoSelected.getArchivoTrad()==null){
//                        msg += "Se debe cargar el archivo PDF de la traducción" + "||";
//                    }
//                } 
//           }
//        }
        return msg;
    }
    
    public Integer ordenaAnexos(long tipoAnexo) {

        Integer orden = null;
            switch ((int)tipoAnexo) {
                case 17://comprobante pago                        
                    orden = new Integer(2);
                    break;
                case 41://Documento descuento
                    orden = new Integer(3);
                    break;
                case 8://poder de acreditamiento del apoderado/RGP
                    orden = new Integer(4);
                    break;
                 case 19://personalidad
                    orden = new Integer(5);
                    break;
                case 20://cesión de derechos
                    orden = new Integer(6);
                    break;
//                 48 CARTA PODER SIMPLE
//                 49 CONSTANCIA RGP
//                 50 PODER NOTARIAL
//                 51 ACTA CONSTITUTIVA
//                 52 OTRO 
                case 48: case 49: case 50:
                case 51: case 52:    
                    orden = new Integer(6);
                    break;       
                case 21://DOCUMENTO COMPROBATORIO DE DIVULGACIÓN PREVIA
                    orden = new Integer(7);
                    break;
                case 15://descripcion
                    orden = new Integer(8);
                    break;
                case 16://Memoria tecnica
                    orden = new Integer(9);
                    break;
                case 22://prioridad
                    orden = new Integer(11);
                    break;
                case 27://traduccion prioridad
                    orden = new Integer(12);
                    break;
                default://otros
                    orden = new Integer(13);
                    break;
            }
        return orden;
    }

     public void uploadAnexo(FileUploadEvent event) throws IOException, SAXException, TikaException {
        //logger.info("==> DENTRO DE Anexo.uploadAnexo()");
        String sMsgUpload = "";
        Boolean pasaDeteccionVirus = true;
        
//        if (forma.getAnexoSeleccionado()==null)
//        {
//            forma.setAnexoSeleccionado(new AnexosViewDto());       
//        }
//        if (this.anexoSelected==null)
//        {
//            this.anexoSelected=new AnexosViewDto();       
//        }
        if (forma.getAnexoSeleccionado()==null )
        {
            this.anexoSelected=new AnexosViewDto(); 
            forma.setAnexoSeleccionado(this.anexoSelected);
        }else{
            this.anexoSelected=forma.getAnexoSeleccionado();
        }
//        forma.getAnexoSeleccionado().setExtension("pdf");
//        forma.getAnexoSeleccionado().setExtension("");
        this.anexoSelected.setExtension("pdf");
        this.anexoSelected.setDescripcion("");
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

            }
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
                            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.FIN" );
                            sMsgUpload = "Se cargo el archivo [" + fileName + "] asociado al anexo " + this.anexoSelected.getDescripcion();
                        } else if (!extArchivo.equals("gif") && !FileServicesUtil.validarArchivoProtegido(file.getContents())) {
                            this.anexoSelected.setNombreArchivo(fileName);
                            this.anexoSelected.setExtension(extArchivo);
                            this.anexoSelected.setArchivoAnexo(file.getContents());
                            this.anexoSelected.setCargado(Boolean.TRUE);
                            this.forma.setRutaDesc(fileName);
                            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.FIN" );
                            sMsgUpload = "Se cargo el archivo [" + fileName + "] asociado al anexo " + this.anexoSelected.getDescripcion();
                        } else {
                            sMsgUpload = "[" + fileName + "] no se cargó ya que está protegido o dañado";
                        }
                    } else {
                        sMsgUpload = "[" + fileName + "] no se cargó ya que el tipo de archivo no corresponde al anexo " + this.anexoSelected.getDescripcion();
                    }
                } else {
                    sMsgUpload = "[" + fileName + "] no se cargó ya que el archivo fue renombrado";
                }

            } else {
                sMsgUpload = "No se cargo el archivo. " + sMsgUpload;
            }

            RequestContext context = RequestContext.getCurrentInstance();
            forma.setAnexoSeleccionado(this.anexoSelected);
            context.execute("singlesubirDialog.hide();");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo:", sMsgUpload);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException ex) {
            logger.fatal("Informacion de Error: -- " + ex + " -- ubicado en: " + ex.getLocalizedMessage());
        }
     }
     
      public void uploadAnexoTrad(FileUploadEvent event) throws IOException, SAXException, TikaException {
        //logger.info("==> DENTRO DE Anexo.uploadAnexo()");
        String sMsgUpload = "";
        Boolean pasaDeteccionVirus = true;
        if (forma.getAnexoSeleccionado()==null )
        {
            this.anexoSelected=new AnexosViewDto(); 
            forma.setAnexoSeleccionado(this.anexoSelected);
        }else{
            this.anexoSelected=forma.getAnexoSeleccionado();
        }
            
        this.anexoSelected.setExtension("pdf");
        //this.anexoSelected.setDescripcion("");
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

            }
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
                    if (forma.getAnexoSeleccionado().getExtension().equals(extArchivo)) {
                        if (extArchivo.equals("gif")) {
                            this.anexoSelected.setNombreTrad(fileName);
                            this.anexoSelected.setArchivoTrad(file.getContents());
                            this.anexoSelected.setTradCargada(Boolean.TRUE);
                            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.FIN" );
                            sMsgUpload = "Se cargo el archivo [" + fileName + "] asociado al anexo " + this.anexoSelected.getDescripcion();
                        } else if (!extArchivo.equals("gif") && !FileServicesUtil.validarArchivoProtegido(file.getContents())) {
                            this.anexoSelected.setNombreTrad(fileName);
                            this.anexoSelected.setArchivoTrad(file.getContents());
                            this.anexoSelected.setTradCargada(Boolean.TRUE);
                            this.forma.setRutaAnexoTrad(fileName);
                            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.FIN" );
                            sMsgUpload = "Se cargo el archivo [" + fileName + "] asociado al anexo " + this.anexoSelected.getDescripcion();
                        } else {
                            sMsgUpload = "[" + fileName + "] no se cargó ya que está protegido o dañado";
                        }
                    } else {
                        sMsgUpload = "[" + fileName + "] no se cargó ya que el tipo de archivo no corresponde al anexo " + this.anexoSelected.getDescripcion();
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
      
    public void uploadAnexoApoderado(FileUploadEvent event) throws IOException, SAXException, TikaException {
        //logger.info("==> DENTRO DE Anexo.uploadAnexo()");
        String sMsgUpload = "";
//        Boolean pasaDeteccionVirus = true;
//
        if (this.anexoSelectedApoderado== null )
        {
            this.anexoSelectedApoderado=new AnexosViewDto(); 
//            forma.setAnexoSeleccionado(this.anexoSelected);
//        }else{
//            this.anexoSelected=forma.getAnexoSeleccionado();
        }
        this.anexoSelectedApoderado.setExtension("pdf");
        this.anexoSelectedApoderado.setDescripcion("");
        if (this.forma.getEsTxtOtroDocumento()!= "" && this.forma.getEsTxtOtroDocumento()!=null){
            this.anexoSelectedApoderado.setTxtAnexo(this.forma.getEsTxtOtroDocumento());
        }
        try {
            UploadedFile file = event.getFile();
            String fileName = file.getFileName();
            Util utilArch = new Util();
            String extArchivo = utilArch.extractExtension(fileName);
            extArchivo = extArchivo.toLowerCase();
            
            if (null != this.anexoSelectedApoderado) {

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
                    if (this.anexoSelectedApoderado.getExtension().equals(extArchivo)) {
                        if (extArchivo.equals("gif")) {
                            this.anexoSelectedApoderado.setNombreArchivo(fileName);
                            this.anexoSelectedApoderado.setExtension(extArchivo);
                            this.anexoSelectedApoderado.setArchivoAnexo(file.getContents());
                            this.anexoSelectedApoderado.setCargado(Boolean.TRUE);
                            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.FIN" );
                            sMsgUpload = "Se cargo el archivo [" + fileName + "] asociado al anexo " + this.anexoSelected.getDescripcion();
                        } else if (!extArchivo.equals("gif") && !FileServicesUtil.validarArchivoProtegido(file.getContents())) {
                            this.anexoSelectedApoderado.setNombreArchivo(fileName);
                            this.anexoSelectedApoderado.setExtension(extArchivo);
                            this.anexoSelectedApoderado.setArchivoAnexo(file.getContents());
                            this.anexoSelectedApoderado.setCargado(Boolean.TRUE);
                            this.forma.setRutaAnexoApoderado(fileName);

                            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.FIN" );
                            sMsgUpload = "Se cargo el archivo [" + fileName + "] asociado al anexo " + this.anexoSelected.getDescripcion();
                        } else {
                            sMsgUpload = "[" + fileName + "] no se cargó ya que está protegido o dañado";
                        }
                    } else {
                        sMsgUpload = "[" + fileName + "] no se cargó ya que el tipo de archivo no corresponde al anexo " + this.anexoSelected.getDescripcion();
                    }
                } else {
                    sMsgUpload = "[" + fileName + "] no se cargó ya que el archivo fue renombrado";
                }

            } else {
                sMsgUpload = "No se cargo el archivo. " + sMsgUpload;
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("pdfPersonalidadDlg.hide();");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo:", sMsgUpload);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (IOException ex) {
            logger.fatal("Informacion de Error: -- " + ex + " -- ubicado en: " + ex.getLocalizedMessage());
        }
     }
    
//      public void uploadAnexoTrad() throws IOException, SAXException, TikaException {
//        //logger.info("==> DENTRO DE Anexo.uploadAnexo()");
//        String sMsgUpload = "";
//        Boolean pasaDeteccionVirus = true;
//        if (forma.getAnexoSeleccionado()==null )
//        {
//            this.anexoSelected=new AnexosViewDto(); 
//            forma.setAnexoSeleccionado(this.anexoSelected);
//        }else{
//            this.anexoSelected=forma.getAnexoSeleccionado();
//        }
//            
//        this.anexoSelected.setExtension("pdf");
//        //this.anexoSelected.setDescripcion("");
//        if (forma.getFileArcTraduccion() != null){
//        try {
//            UploadedFile file = forma.getFileArcTraduccion();
//            String fileName = file.getFileName();
//            //parametros para deteccion de virus en los archivos
//            String aplicaDeteccionVirus = bundleParametros.getString("anexo.antivirus.aplica");
//            if (aplicaDeteccionVirus.equals("1")) {
//                pasaDeteccionVirus = this.deteccionVrus(file);
//                if (pasaDeteccionVirus == false) {
//                    sMsgUpload = "No se cargo su archivo, contiene virus.";
//                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", sMsgUpload);
//                    FacesContext.getCurrentInstance().addMessage(null, message);
//                    return;
//                }
//            }
//            logger.info(".....paso validacion de virus");
//            Util utilArch = new Util();
//            String extArchivo = utilArch.extractExtension(fileName);
//            extArchivo = extArchivo.toLowerCase();
//            if ( extArchivo.equals("gif") ){
//                if( file.getContents().length > 100000 ) {
//                    sMsgUpload = "Su archivo mide " + file.getContents().length + " bytes, esta permitido hasta 100kb.";
//                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo", sMsgUpload);
//                    FacesContext.getCurrentInstance().addMessage(null, message);
//                    return;
//                }
//
//            }
//            if (null != this.anexoSelected) {
//
//                //ini: Anexos, identificacion de archivos renombrados
//                TikaConfig config = TikaConfig.getDefaultConfig();
//                Detector detector = config.getDetector();
//                TikaInputStream stream = TikaInputStream.get(file.getInputstream());
//                Metadata metadata = new Metadata();
//                MediaType mediaType = null;
//                mediaType = detector.detect(stream, metadata);
//                String mimeTypeContenidoArchivo = mediaType.toString();
//                //fin: Anexos, identificacion de archivos renombrados
//
//                MimeType mimeTypeNombreArchivo = utilArch.extractMime(extArchivo);
//                //String mimeTypeContenidoArchivo=mimeTypeNombreArchivo.getMime();                
//                if (mimeTypeContenidoArchivo.equals(mimeTypeNombreArchivo.getMime())) {
//                    if (forma.getAnexoSeleccionado().getExtension().equals(extArchivo)) {
//                        if (extArchivo.equals("gif")) {
//                            this.anexoSelected.setNombreTrad(fileName);
//                            this.anexoSelected.setArchivoTrad(file.getContents());
//                            this.anexoSelected.setTradCargada(Boolean.TRUE);
//                            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.FIN" );
//                            sMsgUpload = "Se cargo el archivo [" + fileName + "] asociado al anexo " + this.anexoSelected.getDescripcion();
//                        } else if (!extArchivo.equals("gif") && !FileServicesUtil.validarArchivoProtegido(file.getContents())) {
//                            this.anexoSelected.setNombreTrad(fileName);
//                            this.anexoSelected.setArchivoTrad(file.getContents());
//                            this.anexoSelected.setTradCargada(Boolean.TRUE);
//                            this.forma.setRutaAnexoTrad(fileName);
//                            //System.out.println("  ==> DENTRO DE SUBIR ARCHIVO.FIN" );
//                            sMsgUpload = "Se cargo el archivo [" + fileName + "] asociado al anexo " + this.anexoSelected.getDescripcion();
//                        } else {
//                            sMsgUpload = "[" + fileName + "] no se cargó ya que está protegido o dañado";
//                        }
//                    } else {
//                        sMsgUpload = "[" + fileName + "] no se cargó ya que el tipo de archivo no corresponde al anexo " + this.anexoSelected.getDescripcion();
//                    }
//                } else {
//                    sMsgUpload = "[" + fileName + "] no se cargó ya que el archivo fue renombrado";
//                }
//
//            } else {
//                sMsgUpload = "No se cargo el archivo. " + sMsgUpload;
//            }
//
//            //RequestContext context = RequestContext.getCurrentInstance();
//           // context.execute("singlesubirDialog.hide();");
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso de carga de archivo:", sMsgUpload);
//            FacesContext.getCurrentInstance().addMessage(null, message);
//        } catch (IOException ex) {
//            logger.fatal("Informacion de Error: -- " + ex + " -- ubicado en: " + ex.getLocalizedMessage());
//        }
//        }
//     }

      
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
        } catch (FileNotFoundException ex) {
            logger.fatal("Informacion de Error: -- " + ex + " -- ubicado en: " + ex.getLocalizedMessage());
        }

        return resulDet;
    }

    public AnexosViewDto getAnexoSelectedTrad() {
        return anexoSelectedTrad;
    }

    public void setAnexoSelectedTrad(AnexosViewDto anexoSelectedTrad) {
        this.anexoSelectedTrad = anexoSelectedTrad;
    }
    
    public int getnPaginasTrad() {
        return nPaginasTrad;
    }

    public void setnPaginasTrad(int nPaginasTrad) {
        this.nPaginasTrad = nPaginasTrad;
    }
    
    //Tab Anexos
    public AnexosViewDto getAnexoSeleccionado() {
        return anexoSeleccionado;
    }

    public void setAnexoSeleccionado(AnexosViewDto anexoSeleccionado) {
        this.anexoSeleccionado = anexoSeleccionado;
    }

    public List<AnexosViewDto> getLstAnexos() {
        return lstAnexos;
    }

    public void setLstAnexos(List<AnexosViewDto> lstAnexos) {
        this.lstAnexos = lstAnexos;
    }

    public StreamedContent getFileDownloadAnexo() {
        return fileDownloadAnexo;
    }

    public void setFileDownloadAnexo(StreamedContent fileDownloadAnexo) {
        this.fileDownloadAnexo = fileDownloadAnexo;
    }
    
    public void anexosTramitePatente() {
        AnexosViewDto anexosViewDoc = new AnexosViewDto();
        try {
            if (tramitePat.getPrioridades() != null && tramitePat.getPrioridades().isEmpty() == false) {
                anexosViewDoc.setPrioridad(Constantes.ANEXO_PRIORIDAD_PAT.intValue());
            }

            if (tramitePat.getFechaDivPrevia() != null) {
                anexosViewDoc.setComprobacionFechaDiv(Constantes.ANEXO_DIV_PREVIA_PAT.intValue());
            }

            if (tramitePat.getPersonasNot() != null && tramitePat.getPersonasNot().isEmpty() == false) {
                anexosViewDoc.setNotificaciones(Constantes.ANEXO_NOTIFICACION_PAT.intValue());
            }

            if (tramitePat.getApoderados() != null && tramitePat.getApoderados().isEmpty() == false) {
                anexosViewDoc.setDoctoAcreditaPersonalidad(Constantes.ANEXO_ACREDITA_PERSONALIDAD.intValue());
            }

            if (tramitePat.getSolicitantes() != null && tramitePat.getSolicitantes().isEmpty() == false) {
                anexosViewDoc.setCesionDerechos(Constantes.ANEXO_CESION_DERECHOS.intValue());
            }


            anexosViewDoc.setIdSubtiposolicitud(this.tramitePat.getSubTipoSol().getIdSubtiposolicitud());
            anexosViewDoc.setIdTramitePatente(this.tramitePat.getIdTramitePatente());
            anexosViewDoc.setIdTipomarca(0);
            lstAnexos = catalogosViewService.ConsultaAnexosDtoPatentes(anexosViewDoc);

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
            
            int numPriorGuardadas = getNumeroAnexosPrioridad(lstAnexos);
            if (this.tramitePat.getPrioridades() != null && numPriorGuardadas < this.tramitePat.getPrioridades().size()) {
                for (int i = 0; i < (tramitePat.getPrioridades().size() - numPriorGuardadas); i++) {
                    String nombrePais = tramitePat.getPrioridades().get(i).getNombrePais();
                    Long idPrioridad = tramitePat.getPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbPrioridad = new AnexosViewDto();
                    AnexosViewDto anexoDbTraduccion = new AnexosViewDto();
                                        
                    anexoDbPrioridad.setIdPrioridad(idPrioridad);
                    anexoDbPrioridad.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);
                    anexoDbPrioridad = catalogosViewService.selectAnexoDynamic(anexoDbPrioridad);
                    
                    AnexosViewDto anexoView = new AnexosViewDto();                    
                    anexoView.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);
                    anexoView.setIdSubtiposolicitud(tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue());
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
                    lstAnexos.add(anexoView);
                                        
                    anexoDbTraduccion.setIdPrioridad(idPrioridad);
                    anexoDbTraduccion.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                    anexoDbTraduccion = catalogosViewService.selectAnexoDynamic(anexoDbTraduccion);
                    
                    AnexosViewDto traduccionView = new AnexosViewDto();                    
                    traduccionView.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                    traduccionView.setIdSubtiposolicitud(tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue());
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
                    lstAnexos.add(traduccionView);
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
            Collections.sort(lstAnexos, new AnexoComparator());  
//          Quitar anexos
            for (int i=0;i< lstAnexos.size();i++)
            {
               if (lstAnexos.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_DESCRIPCION_PATENTE.longValue())
               {
                   lstAnexos.remove(i);
                   i--;
               }else if(lstAnexos.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_DIV_PREVIA_PAT.longValue())
               {
                   lstAnexos.remove(i);
                   i--;
               }else if(lstAnexos.get(i).getIdTipoanexo().longValue()== Constantes.ANEXO_REIVINDICACIONES_PATENTE.longValue())
               {
                   lstAnexos.remove(i);
                   i--;
               }                           
            }
            
            indexacion(lstAnexos);
            Collections.sort(lstAnexos, new AnexoComparatorDos());
            
             
//            if (anexosViewDto.getDoctoAcreditaPersonalidad() != null && anexosViewDto.getDoctoAcreditaPersonalidad().intValue() == Constantes.ANEXO_ACREDITA_PERSONALIDAD.intValue()) {
//                ponerAnexoObligatorio(anexoDto, Constantes.ANEXO_ACREDITA_PERSONALIDAD);
//            }

            if (anexosViewDto.getComprobacionFechaDiv() != null && anexosViewDto.getComprobacionFechaDiv().intValue() == Constantes.ANEXO_DIV_PREVIA_PAT.intValue()) {
                ponerAnexoObligatorio(lstAnexos, Constantes.ANEXO_DIV_PREVIA_PAT);
            }
//            if (anexosViewDto.getNotificaciones() != null && anexosViewDto.getNotificaciones().intValue() == Constantes.ANEXO_NOTIFICACION_PAT.intValue()) {
//                ponerAnexoObligatorio(anexoDto, Constantes.ANEXO_NOTIFICACION_PAT);
//            }
            if (tramitePat.isMaterial_biologico()) {
                ponerAnexoObligatorio(lstAnexos, Constantes.ANEXO_CONST_MAT_BIOL);
            }
          //  tramitePat.setLstAnexosDocumento(lstAnexos);
        } catch (Exception e) {
            System.out.println("  ==> ERROR:  " + e.getMessage());
        }
       // session.removeAttribute("cabecero");
    }

    public void indexacion(List<AnexosViewDto> lstAnexos) {
        int i = 0;
        for (AnexosViewDto anexoViewDto : lstAnexos) {
            anexoViewDto.setIndex(i);
            i++;
        }
    }
    
    public int getNumeroAnexosPrioridad(List<AnexosViewDto> lstAnexos) {
        int i = 0;
        for (AnexosViewDto anexoViewDto : lstAnexos) {
            if (anexoViewDto.getIdTipoanexo().intValue() == Constantes.ANEXO_PRIORIDAD_PAT.intValue()) {
                i++;
            }
        }
        return i;
    }


    public AccordionPanel getPanel() {
        return panel;
    }

    public void setPanel(AccordionPanel panel) {
        this.panel = panel;
    }
    
    public String getAcordionIndex() {
        return acordionIndex;
    }

    public void setAcordionIndex(String acordionIndex) {
        this.acordionIndex = acordionIndex;
    }
    
    public String getSysDate() {
        return Util.formatearFecha(flujosgralesViewService.getSysDate(), "MM/dd/yyyy HH:mm:ss");
    }
    
    public int getnPagMemyReiv() {
        return nPagMemyReiv;
    }

    public void setnPagMemyReiv(int nPagMemyReiv) {
        this.nPagMemyReiv = nPagMemyReiv;
    }

    public int getnPagMemyReivTrad() {
        return nPagMemyReivTrad;
    }

    public void setnPagMemyReivTrad(int nPagMemyReivTrad) {
        this.nPagMemyReivTrad = nPagMemyReivTrad;
    }
    
    public String getTxtEditor() {
        return txtEditor;
    }

    public void setTxtEditor(String txtEditor) {
        this.txtEditor = txtEditor;
    }
    
    public String getTxtEditor2() {
        return txtEditor2;
    }

    public void setTxtEditor2(String txtEditor2) {
        this.txtEditor2 = txtEditor2;
    }

    
    public  void editor() throws DocumentException, com.itextpdf.text.DocumentException, UnsupportedEncodingException, IOException{
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Successful",  "Your message: " + txtEditor) );
        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
//        GenerarReporte generaReporte =new GenerarReporte();
//       // generaReporte.htmltoPDF(txtEditor);
//        GenerarReporte_html reporte=new GenerarReporte_html();
//        reporte.htmltoPDF(txtEditor);
//        reporte.xhtmltoPDF(txtEditor);
        txtEditor2="";
                //reporte.leePDF();
//        reporte.extractImages("/media/WindowsD/Resumen.pdf", "/media/WindowsD/Images");
        //ConvertPDFToXML convertir= new ConvertPDFToXML();
        String[] cad={""};
        ConvertPDFToXML.main(cad);
    }

    public List<CatAnexos> getAnexosApoderado() {
        return anexosApoderado;
    }

    public void setAnexosApoderado(List<CatAnexos> anexosApoderado) {
        this.anexosApoderado = anexosApoderado;
    }
    
    public List<AnexosViewDto> getLstAnexosApoderado() {
        return lstAnexosApoderado;
    }

    public void setLstAnexosApoderado(List<AnexosViewDto> lstAnexosApoderado) {
        this.lstAnexosApoderado = lstAnexosApoderado;
    }
    
    public boolean isEditarAnexoApoderado() {
        return editarAnexoApoderado;
    }

    public void setEditarAnexoApoderado(boolean editarAnexoApoderado) {
        this.editarAnexoApoderado = editarAnexoApoderado;
    }
    
    public String getOtroDocumento() {
        return otroDocumento;
    }

    public void setOtroDocumento(String otroDocumento) {
        this.otroDocumento = otroDocumento;
    }
    
    public AnexosViewDto getAnexoSelectedApoderado() {
        return anexoSelectedApoderado;
    }

    public void setAnexoSelectedApoderado(AnexosViewDto anexoSelectedApoderado) {
        this.anexoSelectedApoderado = anexoSelectedApoderado;
    }
    
    public CatAnexos getDatosAnexosApoderado() {
        return datosAnexosApoderado;
    }

    public void setDatosAnexosApoderado(CatAnexos datosAnexosApoderado) {
        this.datosAnexosApoderado = datosAnexosApoderado;
    }
    
    public IncludeDomicilio getDomicilioContacto() {
        return domicilioContacto;
    }

    public void setDomicilioContacto(IncludeDomicilio domicilioContacto) {
        this.domicilioContacto = domicilioContacto;
    }
    public void borrarPdfanexosApoderado(){
        for (CatAnexos anexo: anexosApoderado){
            Anexos anexoEliminar= new Anexos();
            anexoEliminar.setIdTipoanexo(anexo.getIdTipoanexo());
            anexoEliminar.setIdTramitePatente(this.idTramite);
            flujosgralesViewService.deleteByTypeAnexo(anexoEliminar);
        }
    }
    
    public boolean isModificaRadioBotonCoverNacioInter() {
        return modificaRadioBotonCoverNacioInter;
}

 
    public void setModificaRadioBotonCoverNacioInter(boolean modificaRadioBotonCoverNacioInter) {
        this.modificaRadioBotonCoverNacioInter = modificaRadioBotonCoverNacioInter;
    }

  
    public boolean isModificaRadioBotonCoverInter() {
        return modificaRadioBotonCoverInter;
    }

 
    public void setModificaRadioBotonCoverInter(boolean modificaRadioBotonCoverInter) {
        this.modificaRadioBotonCoverInter = modificaRadioBotonCoverInter;
    }

  
    public boolean isHabilitaDeshabilitaComboTipPer() {
        return habilitaDeshabilitaComboTipPer;
    }


    public void setHabilitaDeshabilitaComboTipPer(boolean habilitaDeshabilitaComboTipPer) {
        this.habilitaDeshabilitaComboTipPer = habilitaDeshabilitaComboTipPer;
    }

 
    
}
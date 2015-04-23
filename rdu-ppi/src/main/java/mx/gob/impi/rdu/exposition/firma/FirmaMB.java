package mx.gob.impi.rdu.exposition.firma;

import com.lowagie.text.pdf.PdfReader;
import java.io.*;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.pase.persistence.model.SisAlerta;
import mx.gob.impi.rdu.dto.*;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.exposition.flujosGenerales.reporte.GenerarReporte;
import mx.gob.impi.rdu.exposition.patentes.PatentesDisenoIndustrialMB;
import mx.gob.impi.rdu.firma.exception.BaseBusinessException;
import mx.gob.impi.rdu.firma.service.CertificateValidator;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.CatFirmahorarios;
import mx.gob.impi.rdu.persistence.model.CatTipopersona;
import mx.gob.impi.rdu.persistence.model.Datoscontacto;
import mx.gob.impi.rdu.persistence.model.Domicilio;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;
import mx.gob.impi.rdu.persistence.model.Pago;
import mx.gob.impi.rdu.persistence.model.Pais;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.Prioridad;
import mx.gob.impi.rdu.persistence.model.Reivindicacion;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;
import mx.gob.impi.rdu.service.*;
import mx.gob.impi.rdu.util.BundleUtils;
import mx.gob.impi.rdu.util.CipherEncript;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.EnumSubTipoPatente;
import mx.gob.impi.rdu.util.ReportesDtoComparator;
import mx.gob.impi.rdu.util.TipoTramiteEnum;
import mx.gob.impi.rdu.util.Util;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "firmaMB")
@ViewScoped
@SuppressWarnings("serial")
public class FirmaMB implements Serializable {

    private static final long serialVersionUID = 8423013812334112270L;
    private String cert;
    private String firmanteId;
    private String firmanteEncode;
    private String firmanteNombre = "";
    private String certificadora;
    private String firmaDigitalPromovente;
    private String codigoBarras;
    private String expediente;
    private String folio;
    private String firmanteBase = "";
    private String firmaImpi = "";
    private Long tramiteId;
    private String serieSolicitante;
    private String serieImpi;
    private String message;
    private String cadenaSolicitante;
    private String cadenaSolicitantePat;
    private String cadenaImpi;
    private String ocspResponse;
    private String fechaOcsp;
    private String fechaExpiracion;
    private long pages;
    private String verify;
    private String publicKey;
    private String serialNumberSolicitante;
    private String serialNumberImpi;
    private String verifyEnc;
    private Persona promovente = new Persona();
    Calendar cal = Calendar.getInstance();
    GregorianCalendar cl = new GregorianCalendar();
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    @ManagedProperty(value = "#{mailService}")
    private MailService mailService;
    @ManagedProperty(value = "#{catalogosViewService}")
    private CatalogosViewServiceImpl catalogosViewService;
    @ManagedProperty(value = "#{patentesViewService}")
    private PatentesViewServiceImpl patentesViewService;
    @ManagedProperty(value = "#{certificateValidatorService}")
    private CertificateValidator certificateValidatorService;
    private Logger lger = Logger.getLogger(this.getClass());
    private int numPaginas=0;
    private PatentesDisenoIndustrialMB patenteDI; 
    private TramitePatente pat;
    //   HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

    public void setPatentesViewService(PatentesViewServiceImpl patentesViewService) {
        this.patentesViewService = patentesViewService;
    }

    public void setCertificateValidatorService(CertificateValidator certificateValidatorService) {
        this.certificateValidatorService = certificateValidatorService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    public void setCatalogosViewService(CatalogosViewServiceImpl catalogosViewService) {
        this.catalogosViewService = catalogosViewService;
    }

    @PostConstruct
    public void init() throws Exception {

        if ("firma".equals(this.validaHorarioFirma())) {
            try {
                SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
                if (null != obtSession) {
                    
                    this.setTramiteId(obtSession.getIdTramite());
                    if (obtSession.getIdTipoTramite() == TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite()) {//patentes
                        //this.generaPdf(this.patentesViewService.obtenerTramitePatenteById(this.getTramiteId()), true);
                        //tramite = rduPatentesBeanRemote.obtenerTramitePatenteById(tramite.getIdTramitePatente());
                        pat = this.patentesViewService.obtenerTramitePatenteById(obtSession.getIdTramite());
                        patenteDI =new PatentesDisenoIndustrialMB();
                        patenteDI.setIdTramite(obtSession.getIdTramite());
                        patenteDI.setEntidadesFederativas(catalogosViewService.ConsultarEntidadesFederativas());
                        this.patentesViewService.recuperarTramite(patenteDI);
                        pat.getDomicilioObj().setPais(patenteDI.getTramitePat().getDomicilioObj().getPais());
                        pat.getDomicilioObj().setEntidad(patenteDI.getTramitePat().getDomicilioObj().getEntidad());
                        
                      // prueba ///////////////////////////
        List<Anexos> prioridadAnx=new ArrayList<Anexos>();
        
        for (int i = 0; i < patenteDI.getListaPrioridades().size(); i++) {
                    String nombrePais = patenteDI.getListaPrioridades().get(i).getNombrePais();
                    Long idPrioridad = patenteDI.getListaPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbPrioridad = new AnexosViewDto();
                    Anexos anexoView= new Anexos();     
                    
                    anexoDbPrioridad.setIdPrioridad(idPrioridad);
                    anexoDbPrioridad.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);
                    anexoDbPrioridad = catalogosViewService.selectAnexoDynamic(anexoDbPrioridad);
                    
                    if(anexoDbPrioridad!=null){
                        anexoView.setIdAnexo(anexoDbPrioridad.getIdAnexo());
                        anexoView.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);                        
                        anexoView.setIdTramitePatente(anexoDbPrioridad.getIdTramitePatente());
                        anexoView.setArchivoAnexo(anexoDbPrioridad.getArchivoAnexo());
                        anexoView.setNombreArchivo(anexoDbPrioridad.getNombreArchivo());
                        anexoView.setExtension("pdf");
                    }
                    prioridadAnx.add(anexoView);

        }
         for (int i = 0; i < patenteDI.getListaPrioridades().size(); i++) {
                    String nombrePais = patenteDI.getListaPrioridades().get(i).getNombrePais();
                    Long idPrioridad = patenteDI.getListaPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbTraduccion = new AnexosViewDto();
                    Anexos traduccionView = new Anexos();       
                                     
                    anexoDbTraduccion.setIdPrioridad(idPrioridad);
                    anexoDbTraduccion.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                    anexoDbTraduccion = catalogosViewService.selectAnexoDynamic(anexoDbTraduccion);
                                        
                    if(anexoDbTraduccion!=null){
                        traduccionView.setIdAnexo(anexoDbTraduccion.getIdAnexo());
                        traduccionView.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                        traduccionView.setIdTramitePatente(anexoDbTraduccion.getIdTramitePatente());
                        traduccionView.setArchivoAnexo(anexoDbTraduccion.getArchivoAnexo());
                        traduccionView.setNombreArchivo(anexoDbTraduccion.getNombreArchivo());
                        traduccionView.setExtension("pdf");
                    }   
                    prioridadAnx.add(traduccionView);
        }
        ///prioridades 
        pat.setLstPrio(prioridadAnx);
        
                        /////////////////////////////

//                        if (null != this.flujosgralesViewService.selectFepsByFolio(new Long(pat.getPago().getFeps()))) {                         
//                            lger.error("FIRMA: ERROR, EL FOLIO FEPS YA SE ENCUENTRA REGISTRADO EN SAGPAT >>> ");
//                            ContextUtils.getSession().setAttribute("errorFirm", "EL FOLIO FEPS YA SE ENCUENTRA REGISTRADO EN SAGPAT");
//                            FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firmaerror.faces");
//                        }
                        
                        
                        /*Se elimina el archivo hoja de descuento y se vuelve a construir colocandole la fecha en la cual 
                         * fue pagada la solicitud*/
                        
                        Anexos anexoHojaDescuento = new Anexos();
                        anexoHojaDescuento.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
                        anexoHojaDescuento.setIdTramitePatente(this.getTramiteId());
                        
                        lger.info("Elimina el anexo HOJA DE DESCUENTO PARA DESPUES CREARLA CON LA FECHA DE PAGO");
                        int res = this.flujosgralesViewService.deleteByTypeAnexo(anexoHojaDescuento);
                        
                        for (int w = 0; w < pat.getSolicitantes().size(); w++) {
                            Persona perSol = (Persona) pat.getSolicitantes().get(w);
                        
                            if (perSol.getDescuento() != null) {
                                if (perSol.getDescuento().intValue() == 1) {
                                    pat.getSolicitantes().get(w).setAplicarDescuento(true);
                                }
                            }
                            
                        }

//                        FirmaDto firma=new FirmaDto();
////                        
//                        if (this.flujosgralesViewService.validarSolicitantes(pat.getSolicitantes())) {
////                            List<Pago> pagos = this.flujosgralesViewService.selectPagoByTramiteId(pat.getIdTramitePatente());
//                            
//                            DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
//                            String convertido = "";
//                            
//                            if(pat.getPago() != null && pat.getPago().getFechapago() != null){
//                                convertido = fecha.format(pat.getPago().getFechapago());
//                            }
//                            
//                            
//                            for (int i = 0; i < pat.getSolicitantes().size(); i++) {
//                                lger.info("*** Crea Anexo hoja de descuento   "+pat.getSolicitantes().get(i).getNombrecompleto());
//                                this.flujosgralesViewService.crearAnexoHojaDescuento(Constantes.INIT, pat.getSolicitantes().get(i),
//                                        pat.getApoderados(), this.getTramiteId(), convertido,firma.getNombreFirmante(),firma.getFirmaSolicitante());
//                            }
//                        }                        
                        //FIN
   
                        //this.generaPdf(pat, true, new FirmaDto());
                        pat.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
                        this.generaPdf(pat, true, new FirmaDto());
                        this.setVerify(this.generaCadenaSolicitantePat(pat));
                        lger.info("CADENA ORIGINAL1 ." + this.getVerify() + ".");
                        byte[] utf8 = this.getVerify().getBytes("UTF8");
                        String chain = new String(utf8, "UTF8");
                        chain = this.flujosgralesViewService.validatePhrase(chain);
                        lger.info("chain:     " + chain);
                        utf8 = chain.getBytes("UTF8");
                        chain = new String(utf8, "UTF8");
                        this.setVerify(chain);
                        lger.info("FIRMA: CADENA ORIGINAL1 ." + chain + ".");
                        this.setVerify(chain);
                        this.setVerifyEnc(Util.encodeObject(this.getVerify()));
                        
                        //this.generaPdf(pat, true, new FirmaDto());
                        lger.info("getIdUsuarioFirmante:     " + pat.getIdUsuarioFirmante().toString());
                        this.setFirmanteId(pat.getIdUsuarioFirmante().toString());

                        this.setFirmanteBase(this.getFirmanteNombreTramite());
//                        this.setFirmanteBase("JUAN HERNANDEZ REYES");

                    }else if (obtSession.getIdTipoTramite() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite()) {//patentes
                        //this.generaPdf(this.patentesViewService.obtenerTramitePatenteById(this.getTramiteId()), true);
                        //tramite = rduPatentesBeanRemote.obtenerTramitePatenteById(tramite.getIdTramitePatente());
                        pat = this.patentesViewService.obtenerTramitePatenteById(obtSession.getIdTramite());
                        patenteDI =new PatentesDisenoIndustrialMB();
                        patenteDI.setIdTramite(obtSession.getIdTramite());
                        patenteDI.setEntidadesFederativas(catalogosViewService.ConsultarEntidadesFederativas());
                        this.patentesViewService.recuperarTramite(patenteDI);
                        pat.getDomicilioObj().setPais(patenteDI.getTramitePat().getDomicilioObj().getPais());
                        pat.getDomicilioObj().setEntidad(patenteDI.getTramitePat().getDomicilioObj().getEntidad());
                        
                      // prueba ///////////////////////////
        List<Anexos> prioridadAnx=new ArrayList<Anexos>();
        
        for (int i = 0; i < patenteDI.getListaPrioridades().size(); i++) {
                    String nombrePais = patenteDI.getListaPrioridades().get(i).getNombrePais();
                    Long idPrioridad = patenteDI.getListaPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbPrioridad = new AnexosViewDto();
                    Anexos anexoView= new Anexos();     
                    
                    anexoDbPrioridad.setIdPrioridad(idPrioridad);
                    anexoDbPrioridad.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);
                    anexoDbPrioridad = catalogosViewService.selectAnexoDynamic(anexoDbPrioridad);
                    
                    if(anexoDbPrioridad!=null){
                        anexoView.setIdAnexo(anexoDbPrioridad.getIdAnexo());
                        anexoView.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);                        
                        anexoView.setIdTramitePatente(anexoDbPrioridad.getIdTramitePatente());
                        anexoView.setArchivoAnexo(anexoDbPrioridad.getArchivoAnexo());
                        anexoView.setNombreArchivo(anexoDbPrioridad.getNombreArchivo());
                        anexoView.setExtension("pdf");
                    }
                    prioridadAnx.add(anexoView);

        }
         for (int i = 0; i < patenteDI.getListaPrioridades().size(); i++) {
                    String nombrePais = patenteDI.getListaPrioridades().get(i).getNombrePais();
                    Long idPrioridad = patenteDI.getListaPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbTraduccion = new AnexosViewDto();
                    Anexos traduccionView = new Anexos();       
                                     
                    anexoDbTraduccion.setIdPrioridad(idPrioridad);
                    anexoDbTraduccion.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                    anexoDbTraduccion = catalogosViewService.selectAnexoDynamic(anexoDbTraduccion);
                                        
                    if(anexoDbTraduccion!=null){
                        traduccionView.setIdAnexo(anexoDbTraduccion.getIdAnexo());
                        traduccionView.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                        traduccionView.setIdTramitePatente(anexoDbTraduccion.getIdTramitePatente());
                        traduccionView.setArchivoAnexo(anexoDbTraduccion.getArchivoAnexo());
                        traduccionView.setNombreArchivo(anexoDbTraduccion.getNombreArchivo());
                        traduccionView.setExtension("pdf");
                    }   
                    prioridadAnx.add(traduccionView);
        }
        ///prioridades 
        pat.setLstPrio(prioridadAnx);
        
                        /////////////////////////////

//                        if (null != this.flujosgralesViewService.selectFepsByFolio(new Long(pat.getPago().getFeps()))) {                         
//                            lger.error("FIRMA: ERROR, EL FOLIO FEPS YA SE ENCUENTRA REGISTRADO EN SAGPAT >>> ");
//                            ContextUtils.getSession().setAttribute("errorFirm", "EL FOLIO FEPS YA SE ENCUENTRA REGISTRADO EN SAGPAT");
//                            FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firmaerror.faces");
//                        }
                        
                        
                        /*Se elimina el archivo hoja de descuento y se vuelve a construir colocandole la fecha en la cual 
                         * fue pagada la solicitud*/
                        
                        Anexos anexoHojaDescuento = new Anexos();
                        anexoHojaDescuento.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
                        anexoHojaDescuento.setIdTramitePatente(this.getTramiteId());
                        
                        lger.info("Elimina el anexo HOJA DE DESCUENTO PARA DESPUES CREARLA CON LA FECHA DE PAGO");
                        int res = this.flujosgralesViewService.deleteByTypeAnexo(anexoHojaDescuento);
                        
                        for (int w = 0; w < pat.getSolicitantes().size(); w++) {
                            Persona perSol = (Persona) pat.getSolicitantes().get(w);
                        
                            if (perSol.getDescuento() != null) {
                                if (perSol.getDescuento().intValue() == 1) {
                                    pat.getSolicitantes().get(w).setAplicarDescuento(true);
                                }
                            }
                            
                        }

//                        FirmaDto firma=new FirmaDto();
////                        
//                        if (this.flujosgralesViewService.validarSolicitantes(pat.getSolicitantes())) {
////                            List<Pago> pagos = this.flujosgralesViewService.selectPagoByTramiteId(pat.getIdTramitePatente());
//                            
//                            DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
//                            String convertido = "";
//                            
//                            if(pat.getPago() != null && pat.getPago().getFechapago() != null){
//                                convertido = fecha.format(pat.getPago().getFechapago());
//                            }
//                            
//                            
//                            for (int i = 0; i < pat.getSolicitantes().size(); i++) {
//                                lger.info("*** Crea Anexo hoja de descuento   "+pat.getSolicitantes().get(i).getNombrecompleto());
//                                this.flujosgralesViewService.crearAnexoHojaDescuento(Constantes.INIT, pat.getSolicitantes().get(i),
//                                        pat.getApoderados(), this.getTramiteId(), convertido,firma.getNombreFirmante(),firma.getFirmaSolicitante());
//                            }
//                        }                        
                        //FIN
   
                        //this.generaPdf(pat, true, new FirmaDto());
                        pat.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
                        this.generaPdf(pat, true, new FirmaDto());
                        this.setVerify(this.generaCadenaSolicitantePat(pat));
                        lger.info("CADENA ORIGINAL1 ." + this.getVerify() + ".");
                        byte[] utf8 = this.getVerify().getBytes("UTF8");
                        String chain = new String(utf8, "UTF8");
                        chain = this.flujosgralesViewService.validatePhrase(chain);
                        lger.info("chain:     " + chain);
                        utf8 = chain.getBytes("UTF8");
                        chain = new String(utf8, "UTF8");
                        this.setVerify(chain);
                        lger.info("FIRMA: CADENA ORIGINAL1 ." + chain + ".");
                        this.setVerify(chain);
                        this.setVerifyEnc(Util.encodeObject(this.getVerify()));
                        
                        //this.generaPdf(pat, true, new FirmaDto());
                        lger.info("getIdUsuarioFirmante:     " + pat.getIdUsuarioFirmante().toString());
                        this.setFirmanteId(pat.getIdUsuarioFirmante().toString());

                        this.setFirmanteBase(this.getFirmanteNombreTramite());
//                        this.setFirmanteBase("JUAN HERNANDEZ REYES");

                    }else if (obtSession.getIdTipoTramite() == TipoTramiteEnum.SOL_PPI.getIdTipoTramite()) {//patentes
                        //this.generaPdf(this.patentesViewService.obtenerTramitePatenteById(this.getTramiteId()), true);
                        //tramite = rduPatentesBeanRemote.obtenerTramitePatenteById(tramite.getIdTramitePatente());
                        pat = this.patentesViewService.obtenerTramitePatenteById(obtSession.getIdTramite());
                        patenteDI =new PatentesDisenoIndustrialMB();
                        patenteDI.setIdTramite(obtSession.getIdTramite());
                        patenteDI.setEntidadesFederativas(catalogosViewService.ConsultarEntidadesFederativas());
                        this.patentesViewService.recuperarTramite(patenteDI);
                        pat.getDomicilioObj().setPais(patenteDI.getTramitePat().getDomicilioObj().getPais());
                        pat.getDomicilioObj().setEntidad(patenteDI.getTramitePat().getDomicilioObj().getEntidad());
                        
                      // prueba ///////////////////////////
        List<Anexos> prioridadAnx=new ArrayList<Anexos>();
        
        for (int i = 0; i < patenteDI.getListaPrioridades().size(); i++) {
                    String nombrePais = patenteDI.getListaPrioridades().get(i).getNombrePais();
                    Long idPrioridad = patenteDI.getListaPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbPrioridad = new AnexosViewDto();
                    Anexos anexoView= new Anexos();     
                    
                    anexoDbPrioridad.setIdPrioridad(idPrioridad);
                    anexoDbPrioridad.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);
                    anexoDbPrioridad = catalogosViewService.selectAnexoDynamic(anexoDbPrioridad);
                    
                    if(anexoDbPrioridad!=null){
                        anexoView.setIdAnexo(anexoDbPrioridad.getIdAnexo());
                        anexoView.setIdTipoanexo(Constantes.ANEXO_PRIORIDAD_PAT);                        
                        anexoView.setIdTramitePatente(anexoDbPrioridad.getIdTramitePatente());
                        anexoView.setArchivoAnexo(anexoDbPrioridad.getArchivoAnexo());
                        anexoView.setNombreArchivo(anexoDbPrioridad.getNombreArchivo());
                        anexoView.setExtension("pdf");
                    }
                    prioridadAnx.add(anexoView);

        }
         for (int i = 0; i < patenteDI.getListaPrioridades().size(); i++) {
                    String nombrePais = patenteDI.getListaPrioridades().get(i).getNombrePais();
                    Long idPrioridad = patenteDI.getListaPrioridades().get(i).getIdPrioridad();
                    AnexosViewDto anexoDbTraduccion = new AnexosViewDto();
                    Anexos traduccionView = new Anexos();       
                                     
                    anexoDbTraduccion.setIdPrioridad(idPrioridad);
                    anexoDbTraduccion.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                    anexoDbTraduccion = catalogosViewService.selectAnexoDynamic(anexoDbTraduccion);
                                        
                    if(anexoDbTraduccion!=null){
                        traduccionView.setIdAnexo(anexoDbTraduccion.getIdAnexo());
                        traduccionView.setIdTipoanexo(Constantes.ANEXO_TRADUCCION_PRIORIDAD);
                        traduccionView.setIdTramitePatente(anexoDbTraduccion.getIdTramitePatente());
                        traduccionView.setArchivoAnexo(anexoDbTraduccion.getArchivoAnexo());
                        traduccionView.setNombreArchivo(anexoDbTraduccion.getNombreArchivo());
                        traduccionView.setExtension("pdf");
                    }   
                    prioridadAnx.add(traduccionView);
        }
        ///prioridades 
        pat.setLstPrio(prioridadAnx);
        
                        /////////////////////////////

//                        if (null != this.flujosgralesViewService.selectFepsByFolio(new Long(pat.getPago().getFeps()))) {                         
//                            lger.error("FIRMA: ERROR, EL FOLIO FEPS YA SE ENCUENTRA REGISTRADO EN SAGPAT >>> ");
//                            ContextUtils.getSession().setAttribute("errorFirm", "EL FOLIO FEPS YA SE ENCUENTRA REGISTRADO EN SAGPAT");
//                            FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firmaerror.faces");
//                        }
                        
                        
                        /*Se elimina el archivo hoja de descuento y se vuelve a construir colocandole la fecha en la cual 
                         * fue pagada la solicitud*/
                        
                        Anexos anexoHojaDescuento = new Anexos();
                        anexoHojaDescuento.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
                        anexoHojaDescuento.setIdTramitePatente(this.getTramiteId());
                        
                        lger.info("Elimina el anexo HOJA DE DESCUENTO PARA DESPUES CREARLA CON LA FECHA DE PAGO");
                        int res = this.flujosgralesViewService.deleteByTypeAnexo(anexoHojaDescuento);
                        
                        for (int w = 0; w < pat.getSolicitantes().size(); w++) {
                            Persona perSol = (Persona) pat.getSolicitantes().get(w);
                        
                            if (perSol.getDescuento() != null) {
                                if (perSol.getDescuento().intValue() == 1) {
                                    pat.getSolicitantes().get(w).setAplicarDescuento(true);
                                }
                            }
                            
                        }

//                        FirmaDto firma=new FirmaDto();
////                        
//                        if (this.flujosgralesViewService.validarSolicitantes(pat.getSolicitantes())) {
////                            List<Pago> pagos = this.flujosgralesViewService.selectPagoByTramiteId(pat.getIdTramitePatente());
//                            
//                            DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
//                            String convertido = "";
//                            
//                            if(pat.getPago() != null && pat.getPago().getFechapago() != null){
//                                convertido = fecha.format(pat.getPago().getFechapago());
//                            }
//                            
//                            
//                            for (int i = 0; i < pat.getSolicitantes().size(); i++) {
//                                lger.info("*** Crea Anexo hoja de descuento   "+pat.getSolicitantes().get(i).getNombrecompleto());
//                                this.flujosgralesViewService.crearAnexoHojaDescuento(Constantes.INIT, pat.getSolicitantes().get(i),
//                                        pat.getApoderados(), this.getTramiteId(), convertido,firma.getNombreFirmante(),firma.getFirmaSolicitante());
//                            }
//                        }                        
                        //FIN
   
                        //this.generaPdf(pat, true, new FirmaDto());
                        pat.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
                        this.generaPdf(pat, true, new FirmaDto());
                        this.setVerify(this.generaCadenaSolicitantePat(pat));
                        lger.info("CADENA ORIGINAL1 ." + this.getVerify() + ".");
                        byte[] utf8 = this.getVerify().getBytes("UTF8");
                        String chain = new String(utf8, "UTF8");
                        chain = this.flujosgralesViewService.validatePhrase(chain);
                        lger.info("chain:     " + chain);
                        utf8 = chain.getBytes("UTF8");
                        chain = new String(utf8, "UTF8");
                        this.setVerify(chain);
                        lger.info("FIRMA: CADENA ORIGINAL1 ." + chain + ".");
                        this.setVerify(chain);
                        this.setVerifyEnc(Util.encodeObject(this.getVerify()));
                        
                        //this.generaPdf(pat, true, new FirmaDto());
                        lger.info("getIdUsuarioFirmante:     " + pat.getIdUsuarioFirmante().toString());
                        this.setFirmanteId(pat.getIdUsuarioFirmante().toString());

                        this.setFirmanteBase(this.getFirmanteNombreTramite());
//                        this.setFirmanteBase("JUAN HERNANDEZ REYES");

                    } else if (obtSession.getIdTipoTramite() == TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite()) {//promociones
                        SolicitudPreparacionDto tramitePromocion = this.flujosgralesViewService.selectPromoByPrimaryKey(this.getTramiteId());
                        tramitePromocion.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
                        this.setVerify(this.generaCadenaSolicitantePromo(tramitePromocion));
//                        this.setVerify("6JBHXGMNVORWID1IGW4N23FPPW=|12||BLANCA MARTINEZ GALICIA|DISENO INDUSTRIAL|4|PRUEBA PAGO|VWZA0R795ICKP2ONHPDESCPXV4I=|N6VCBBCHPXBRBZYC1YZHNZ9BQTG=");
                        lger.info("CADENA ORIGINAL1 ." + this.getVerify() + ".");
                        byte[] utf8 = this.getVerify().getBytes("UTF8");
                        String chain = new String(utf8, "UTF8");
//                        chain = this.flujosgralesViewService.validatePhrase(chain);
                        lger.info("chain:     " + chain);
                        utf8 = chain.getBytes("UTF8");
                        chain = new String(utf8, "UTF8");
                        this.setVerify(chain);
                        lger.info("FIRMA: CADENA ORIGINAL1 ." + chain + ".");
                        this.setVerify(chain);
                        this.setVerifyEnc(Util.encodeObject(this.getVerify()));

                        String folioFirma = "";
                        this.generaPdfPromocion(tramitePromocion, true, folioFirma);

                        promovente = obtenerPromovente(obtSession);

//                        this.setFirmanteId("30104");
                        this.setFirmanteId(promovente.getIdSolicitante().toString());

//                        this.setFirmanteBase("CESAR CASTAÑEDA REYES");
                        this.setFirmanteBase(promovente.getNombrecompleto());
                    }

                    //this.setFirmanteBase("SAUL RIOS RICO");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SIN CONEXION PASE", BundleUtils.getResource("firma.error")));
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firmaerror.faces");
                }

            } catch (Exception e) {
                e.printStackTrace();
                lger.error("FIRMA: ERROR AL OBTENER REPORTE >>> " + e.getLocalizedMessage() + "   >>>>  " + e.getMessage(), e);
                FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firmaerror.faces");
            }
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firmahorario.faces");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    public String getFirmanteNombreTramite() {

        ApoderadoDto obtApoderado = ContextUtils.obtenerApoderadoDeSesion(new Integer(this.getFirmanteId()));
        Promovente obtApoderado2 = this.flujosgralesViewService.buscaPromovente(new Long(this.getFirmanteId()));
        String tmp = obtApoderado2.getNombre().trim().toUpperCase() + " " + obtApoderado2.getApaterno().trim().toUpperCase() + " " + obtApoderado2.getAmaterno().trim().toUpperCase();
        lger.info("FirmanteNombreTramite:       " + tmp);
        return tmp;
    }

    public ByteArrayOutputStream generaPdfPromocion(SolicitudPreparacionDto tramitePromo, boolean isPreview, String folioFirma) throws Exception {
        System.out.println("En generaPdfPromocion...");

        Util util = new Util();
        ReportesDto reportesDto = new ReportesDto();
        List<ReportesDto> listaTempReport = new ArrayList<ReportesDto>();
        List<ReportesDto> listaReportes = new ArrayList<ReportesDto>();
        GenerarReporte generarReporte = new GenerarReporte();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String numExpediente = tramitePromo.getOficioCodOficina() + "/" + tramitePromo.getTipExped()
                + "/" + tramitePromo.getSerExped() + "/" + tramitePromo.getNumExped();

        ReportePromocionesDto reportePromociones = new ReportePromocionesDto();
        reportePromociones.setArea(tramitePromo.getDescripcionArea());
        reportePromociones.setDescripcionOficio(tramitePromo.getDescPromocion());
        reportePromociones.setTipoPromocion(tramitePromo.getDescOficio());
        reportePromociones.setNumExpediente(numExpediente);
        reportePromociones.setFecha(new Date());
        reportePromociones.setCadenaOriginal(this.getVerify());
        //SE DEBE DE CREAR LA FIRMA
        reportePromociones.setFolio(folioFirma);
        
        //ESTE SE DEBE DE UTILIZAR
        ReportePromocionesDto repPromo = new ReportePromocionesDto();
        
        ByteArrayOutputStream byt = generarReporte.generaRepporte(request.getRealPath("") + "/content/reportes/promo_con_contestacion.jasper",
                reportePromociones);

        reportesDto = new ReportesDto();
        reportesDto.setNombreBookMark(util.recuperarNombreAnexo(Constantes.ANEXO_DISENO));
        reportesDto.setReporteBytes(byt.toByteArray());
        reportesDto.setOrden(new Integer(1));
        listaTempReport.add(reportesDto);

        List<Anexos> allAnx = tramitePromo.getAnexos();

        for (Anexos anx : allAnx) {
            reportesDto = new ReportesDto();
            reportesDto.setNombreBookMark(util.recuperarNombreAnexo(anx.getIdTipoanexo()));
            reportesDto.setReporteBytes(anx.getArchivoAnexo());
            reportesDto.setOrden(patentesViewService.ordenarAnexos(anx));
            listaTempReport.add(reportesDto);
        }

        listaReportes = patentesViewService.ordenarReporte(listaTempReport);
        byt = generarReporte.concatenarPdfsWithBookMarks(listaReportes, isPreview);
        this.setPages(generarReporte.getNumberPages(byt));

        session.setAttribute("reporteStream", byt);

        return byt;

    }

  public  ByteArrayOutputStream generaPdf(Object tramiteDto, boolean isPreview, FirmaDto firma) throws Exception {
        Util util = new Util();
        if (tramiteDto instanceof TramitePatente) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

            List<ReportesDto> listaTempReport = new ArrayList<ReportesDto>();
            List<ReportesDto> listaReportes = new ArrayList<ReportesDto>();
            List<ReportesDto> listaTmp = new ArrayList<ReportesDto>();
            ReportesDto reportesDto = null;


            session.removeAttribute("reporteStream");
            TramitePatente patente = (TramitePatente) tramiteDto;
            GenerarReporte repps = new GenerarReporte();
            
            
            patente.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
             List<Anexos> allAnx = patente.getAnexos();
////             for (Anexos anx : allAnx) {
////             if (anx.getIdTipoanexo()==15)
////             {
////                    ByteArrayInputStream Bya = new ByteArrayInputStream(anx.getArchivoAnexo());
////                    InputStream PDF = Bya;
////                    PdfReader pdfReader= new PdfReader(PDF);
////                    numPaginas=pdfReader.getNumberOfPages();
////                    pdfReader.close();
////             }
////            }
////
////            if (patente.getReivindicaciones() != null && patente.getReivindicaciones().size() > 0) {
////                reportesDto = new ReportesDto();
////                reportesDto.setNombreBookMark("REIVINDICACION");
////                reportesDto.setNombreReporte("reivindicaciones.pdf");
////                reportesDto.setOrden(new Integer(9));               
////                reportesDto.setReporteBytes(repps.generarReivindicacionesEnPdf(request.getRealPath("") + "/content/reportes/template_reivindicaciones.jasper", patente,numPaginas)
////                        .toByteArray());
////                listaTmp.add(reportesDto);
////            }

//            //Se comento ya que el PDF Reivindicaciones se genera mas adelante
//            //CREAR figuras y reivindicaciones para ser mostrado en la solicitud
//             
//                    
//            if (patente.getReivindicaciones() != null && patente.getReivindicaciones().size() > 0) {
//                reportesDto = new ReportesDto();
//                reportesDto.setNombreBookMark("REIVINDICACION");
//                reportesDto.setNombreReporte("Reivindicacion.pdf");
//                reportesDto.setOrden(new Integer(9));               
//                reportesDto.setReporteBytes(repps.generarReivindicacionesEnPdf(request.getRealPath("") + "/content/reportes/template_reivindicaciones.jasper", patente)
//                        .toByteArray());
//                listaTmp.add(reportesDto);
//            }
            if (patente.getImagenes() != null && patente.getImagenes().size() > 0) {
                reportesDto = new ReportesDto();
                reportesDto.setNombreBookMark("FIGURAS");
                reportesDto.setNombreReporte("Figuras.pdf");
                reportesDto.setOrden(new Integer(11));//10
                reportesDto.setReporteBytes(repps.generarDibujosEnPdf(request.getRealPath("") + "/content/reportes/template_dibujos.jasper", patente).toByteArray());
                    listaTmp.add(reportesDto);
            }

            //Se crea el archivo temporal para obtener el # de paginas que contiene
            ReporteDisenoIndustrialDto repTmp = new ReporteDisenoIndustrialDto(patente, request.getRealPath("") + "/content/imagenes/firma_impi.png", firma, listaTmp);
            
            if(isPreview==false)
            {
            reportesDto = new ReportesDto();
            reportesDto.setNombreBookMark("ACUSE DE RECIBO");
            reportesDto.setNombreReporte("acuseSolicitud.pdf");
            reportesDto.setOrden(new Integer(0));
            reportesDto.setReporteBytes(
                    repps.generaRepporte(request.getRealPath("") + "/content/reportes/acuse_Solicitud.jasper", repTmp).toByteArray());
            listaTmp.add(reportesDto);               
            }   
            
            reportesDto = new ReportesDto();
            reportesDto.setNombreBookMark("SOLICITUD");
            reportesDto.setNombreReporte("formatoSolicitud.pdf");
            reportesDto.setOrden(new Integer(1));
            reportesDto.setReporteBytes(
                    repps.generaRepporte(request.getRealPath("") + "/content/reportes/reporte_diseno.jasper", repTmp).toByteArray());
            listaTmp.add(reportesDto);
                       
            Anexos anexoHojaDescuento = new Anexos();
            anexoHojaDescuento.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
            anexoHojaDescuento.setIdTramitePatente(this.getTramiteId());

            lger.info("Elimina el anexo HOJA DE DESCUENTO PARA DESPUES CREARLA CON LA FECHA DE PAGO");
            int res = this.flujosgralesViewService.deleteByTypeAnexo(anexoHojaDescuento);
            for (int w = 0; w < patente.getSolicitantes().size(); w++) {
                 Persona perSol = (Persona) patente.getSolicitantes().get(w);
                             if (perSol.getDescuento() != null) {
                                if (perSol.getDescuento().intValue() == 1) {
                                    patente.getSolicitantes().get(w).setAplicarDescuento(true);
                                }
                            }
                            
                        }
            //Crea documento de Descuento
            
                        if (this.flujosgralesViewService.validarSolicitantes(patente.getSolicitantes())) {
//                            List<Pago> pagos = this.flujosgralesViewService.selectPagoByTramiteId(pat.getIdTramitePatente());
                            
                            DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
                            String convertido = "";
                            
                            if(patente.getPago() != null && patente.getPago().getFechapago() != null){
                                convertido = fecha.format(patente.getPago().getFechapago());
                            }
                            
                            
                            for (int i = 0; i < patente.getSolicitantes().size(); i++) {
                                lger.info("*** Crea Anexo hoja de descuento   "+patente.getSolicitantes().get(i).getNombrecompleto());
                                this.flujosgralesViewService.crearAnexoHojaDescuento(Constantes.INIT, patente.getSolicitantes().get(i),
                                        patente.getApoderados(), this.getTramiteId(), convertido,firma.getNombreFirmante(),firma.getFirmaSolicitante(),
                                        patente.getTipoSol().getIdTiposolicitud().intValue());
                            }
                        }         
            //Se crea el archivo final de reporte diseno
            ReporteDisenoIndustrialDto rep = new ReporteDisenoIndustrialDto(patente, request.getRealPath("") + "/content/imagenes/firma_impi.png", firma, listaTmp);
            ByteArrayOutputStream byt = repps.generaRepporte(request.getRealPath("") + "/content/reportes/reporte_diseno.jasper", rep);
            
            //Se elimina el archivo temporal debido a que no se requiere que aparezca en el formato final
            listaTmp.remove(listaTmp.size() - 1);
//se comenta para quitar la validadion ya q el reporte ya no contiene la hoja 1
//            if (isPreview) {
//                byt = repps.eliminarPagina(byt, 1);
//            }
            reportesDto = new ReportesDto();
            reportesDto.setNombreBookMark(util.recuperarNombreAnexo(Constantes.ANEXO_DISENO));
            reportesDto.setReporteBytes(byt.toByteArray());
            reportesDto.setOrden(new Integer(1));

            //AGREGAR SOLICITUD
            listaTempReport.add(reportesDto);
           
            if(isPreview==false)
            {
            //Se agrega para generar el REPORTE DEL ACUSE DE RECIBO
            byt = repps.generaRepporte(request.getRealPath("") + "/content/reportes/acuse_Solicitud.jasper", rep);        
            //Se elimina el archivo temporal debido a que no se requiere que aparezca en el formato final
            listaTmp.remove(listaTmp.size() - 1);
            reportesDto = new ReportesDto();
            reportesDto.setNombreBookMark("ACUSE DE RECIBO");//util.recuperarNombreAnexo(44L));
            reportesDto.setReporteBytes(byt.toByteArray());
            reportesDto.setOrden(new Integer(0));
            //AGREGAR ACUSE
            listaTempReport.add(reportesDto);
            }

            if (listaTmp != null && listaTmp.size() > 0) {
                for (ReportesDto dto : listaTmp) {
                    listaTempReport.add(dto);
                }
            }
            patente.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
            //repps.generaRepporte(request.getRealPath("") + "/content/reportes/ixmpi_00_001.jasper", request.getRealPath("") + "/content/imagenes/firma_impi.png", patente);
              //Se descomenta para generar el PDF Reivindicaciones
            //CREA reivindicaciones para ser mostrado en la solicitud
             
                    

            //List<Anexos> 
            allAnx = patente.getAnexos();
//             for (Anexos anx : allAnx) {
//             if (anx.getIdTipoanexo()==15)
//             {
//                    ByteArrayInputStream Bya = new ByteArrayInputStream(anx.getArchivoAnexo());
//                    InputStream PDF = Bya;
//                    PdfReader pdfReader= new PdfReader(PDF);
//                    patente.setPaginasDescripcion(pdfReader.getNumberOfPages());
//                    pdfReader.close();
//             }
//            }
//
//            if (patente.getReivindicaciones() != null && patente.getReivindicaciones().size() > 0) {
//                reportesDto = new ReportesDto();
//                reportesDto.setNombreBookMark("REIVINDICACION");
//                reportesDto.setNombreReporte("Reivindicacion.pdf");
//                reportesDto.setOrden(new Integer(9));               
//                reportesDto.setReporteBytes(repps.generarReivindicacionesEnPdf(request.getRealPath("") + "/content/reportes/template_reivindicaciones.jasper", patente)
//                        .toByteArray());
//                listaTempReport.add(reportesDto);
//            }

            for (Anexos anx : allAnx) {
                if (util.recuperarNombreAnexo(anx.getIdTipoanexo())!= "PRIORIDAD" && util.recuperarNombreAnexo(anx.getIdTipoanexo())!= "TRADUCCION"   ){
                    reportesDto = new ReportesDto();
                    reportesDto.setNombreBookMark(util.recuperarNombreAnexo(anx.getIdTipoanexo()));
                    reportesDto.setReporteBytes(anx.getArchivoAnexo());
                    reportesDto.setOrden(patentesViewService.ordenarAnexos(anx));
                    listaTempReport.add(reportesDto);
                    if (anx.getOtroIdioma()==2 && anx.getArchivoTrad()!=null){
                        reportesDto = new ReportesDto();
                        reportesDto.setNombreBookMark(util.recuperarNombreAnexo(anx.getIdTipoanexoTrad()));
                        reportesDto.setReporteBytes(anx.getArchivoTrad());
                        reportesDto.setOrden(patentesViewService.ordenarAnexosTraduccion(anx));
                        listaTempReport.add(reportesDto);
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
        
        for (int i = 0; i < patenteDI.getListaPrioridades().size(); i++) {
                    String nombrePais = patenteDI.getListaPrioridades().get(i).getNombrePais();
                    Long idPrioridad = patenteDI.getListaPrioridades().get(i).getIdPrioridad();
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
         for (int i = 0; i < patenteDI.getListaPrioridades().size(); i++) {
                    String nombrePais = patenteDI.getListaPrioridades().get(i).getNombrePais();
                    Long idPrioridad = patenteDI.getListaPrioridades().get(i).getIdPrioridad();
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
        ///prioridades 
        patente.setLstPrio(prioridadAnx);
        /////
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
     
            for (int i = 0; i < listaTempReport.size(); i++) {
                if (Constantes.nombreSolicitudPDF.compareTo(listaTempReport.get(i).getNombreBookMark()) != 0) {
//                    byte[] temp = repps.agregarNumSolicitudReporte(listaPdf.get(i),
//                            rep.getFolio());
                    byte[] temp = repps.agregarNumSolicitudReporte(listaTempReport.get(i).getReporteBytes(),
                            rep.getExpediente());

                    listaTempReport.get(i).setReporteBytes(temp);



                }
            }
            //ordenar anexos
//            ReportesDto anexoAux;
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
 //****************************Reordenamiento Reporte nuevo
            int orden =listaTempReport.get(0).getOrden();
        
            for (int i=0;i<listaTempReport.size();i++)
            {
               listaTempReport.get(i).setOrden(orden);
                orden += 1;
            }
            
            int numHOjasDescuentos = 0;
            Collections.sort(listaTempReport, new ReportesDtoComparator());

            for (int j = 0; j < listaTempReport.size(); j++) {
                ReportesDto reporte = listaTempReport.get(j);

                if (reporte.getNombreBookMark()!=null && reporte.getNombreBookMark().equals("HOJA DE DESCUENTO"))
                        numHOjasDescuentos++;
            }
            
            /**
             * Reodenamiento por la anexacion de prioridades
             */ 
            
            if (numHOjasDescuentos > 1) {
                lger.info("Se reordena los archivo esto por que existe mas de 1 anexo de tipo HOJA DE DESCUENTO");
                for (int l=0; l < listaTempReport.size(); l++) {
                    listaTempReport.get(l).setOrden(l);
                }
            }

            listaReportes = patentesViewService.ordenarReporte(listaTempReport);

            byt = repps.concatenarPdfsWithBookMarks(listaReportes, isPreview);

            this.setPages(repps.getNumberPages(byt));


            session.setAttribute("reporteStream", byt);

            return byt;

        }

        return null;


    }

    public String getTipoFolio(long idTipoSolicitud) {

        if (idTipoSolicitud == 1L) {
            return BundleUtils.getResource("firma.folio.marca");
        } else if (idTipoSolicitud == 4L) {
            return BundleUtils.getResource("firma.folio.marca");
        } else if (idTipoSolicitud == 2L) {
            return BundleUtils.getResource("firma.folio.nombre");
        } else if (idTipoSolicitud == 3L) {
            return BundleUtils.getResource("firma.folio.aviso");
        }
        return "";
    }

    public String validaHorarioFirma() {
        Date d = this.flujosgralesViewService.getSysDate();
        Calendar hoy = GregorianCalendar.getInstance();
        hoy.setTime(d);
        //hoy.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.SATURDAY);
        //hoy.set(Calendar.DAY_OF_MONTH, 6);
        hoy.set(Calendar.SECOND, 0);
        hoy.set(Calendar.MILLISECOND, 0);
        String b = Util.formatearFecha(hoy.getTime(), Util.FORMATODDMMYYYY);
        //CatFirmahorarios horarioValido = this.catalogosViewService.getHorariosFirma(new CatFirmahorarios(hoy.get(GregorianCalendar.DAY_OF_WEEK), hoy.getTime()));
        CatFirmahorarios horarioValido = this.catalogosViewService.getHorariosFirma(new CatFirmahorarios(hoy.get(Calendar.DAY_OF_WEEK), hoy.getTime()));

        //if (horarioValido != null && horarioValido.getFechaInhabil() != null) {
        if (horarioValido == null) { // && horarioValido.getFechaInhabil() != null) {
            return "noLaborable";
        } else {
            String[] from = horarioValido.getHorarioDesde().split(":");
            String[] to = horarioValido.getHorarioHasta().split(":");
            Calendar apertura = (Calendar) hoy.clone();
            Calendar cierre = (Calendar) hoy.clone();
            apertura.set(Calendar.HOUR_OF_DAY, new Integer(from[0]));
            apertura.set(Calendar.MINUTE, new Integer(from[1]));
            cierre.set(Calendar.HOUR_OF_DAY, new Integer(to[0]));
            cierre.set(Calendar.MINUTE, new Integer(to[1]));

            if (hoy.after(apertura) && hoy.before(cierre)) {
                return "firma";
            } else {
                return "noLaborable";
            }
        }
    }

    public String generaCadenaSolicitantePromo(SolicitudPreparacionDto tramitePromocion) {
        StringBuilder chain = new StringBuilder();
        String chainHashesAnexos = "";

//        for (Anexos anx : Util.checkListNull(tramitePromocion.getAnexos())) {
//            chainHashesAnexos += Util.getDigest(anx.getArchivoAnexo());
//        }
//        if (chainHashesAnexos != null && chainHashesAnexos.length() > 0) {
//            chain.append(Util.getDigest(chainHashesAnexos.getBytes()));
//        }
        
        chain.append(Constantes.PIPE);
        chain.append(tramitePromocion.getIdTipoPromocion() == null ? "" : tramitePromocion.getIdTipoPromocion());
        chain.append(Constantes.PIPE);

        chain.append(Constantes.PIPE);
        chain.append(tramitePromocion.getDescripcionArea() == null ? "" : tramitePromocion.getDescripcionArea());
        chain.append(Constantes.PIPE);

        String numExpediente = tramitePromocion.getOficioCodOficina() + "/" + tramitePromocion.getTipExped()
                + "/" + tramitePromocion.getSerExped() + "/" + tramitePromocion.getNumExped();

        chain.append(numExpediente);
        
        chain.append(Constantes.PIPE);        
        chain.append(Util.getDigest(tramitePromocion.getDescPromocion().getBytes()));
        
        chain.append(Constantes.PIPE);
        chain.append(tramitePromocion.getDescOficio() == null ? "" : tramitePromocion.getDescOficio());

        this.setCadenaSolicitantePat(chain.toString());

        lger.info("CadenaSolicitantePat     " + this.getCadenaSolicitantePat());

        return chain.toString();
    }

    public String generaCadenaSolicitantePat(TramitePatente tramite) {
        StringBuilder chain = new StringBuilder();
        //chain.append(cert)
        String logo = "";
        String chainHashesAnexos = "";
        String chainHashesReivindicaciones = "";
        String chainHashesImagenes = "";

        for (Anexos anx : Util.checkListNull(tramite.getAnexos())) {
            chainHashesAnexos += Util.getDigest(anx.getArchivoAnexo());
            if (anx.getOtroIdioma()==2 && anx.getArchivoTrad()!=null){
                chainHashesAnexos += Util.getDigest(anx.getArchivoTrad());
            }
        }
        if (chainHashesAnexos != null && chainHashesAnexos.length() > 0) {
            chain.append(Util.getDigest(chainHashesAnexos.getBytes()));
        }
        chain.append(Constantes.PIPE);
        chain.append(tramite.getIdSubtipoSolicitud() == null ? "" : tramite.getIdSubtipoSolicitud());
        chain.append(Constantes.PIPE);
        //chain.append(tramite.getSolicitantes().get(0).getNombrecompleto());
        chain.append(Constantes.PIPE);
        //chain.append(tramite.getApoderados().get(0) == null ? "" : tramite.getApoderados().get(0).getNombrecompleto());
        chain.append(tramite.getApoderados().isEmpty() ? tramite.getSolicitantes().get(0).getNombrecompleto() : tramite.getApoderados().get(0).getNombrecompleto());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getTipoSol().getDescripcion() == null ? "" : tramite.getTipoSol().getDescripcion());//validar ***
        chain.append(Constantes.PIPE);
        chain.append(tramite.getTipoSol().getIdArea() == null ? "" : tramite.getTipoSol().getIdArea());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getInvencion() == null ? "" : tramite.getInvencion());
        chain.append(Constantes.PIPE);

        if (tramite.getImagenes() != null && tramite.getImagenes().size() > 0) {
            for (ImagenDibujo img : tramite.getImagenes()) {
                chainHashesImagenes += Util.getDigest(img.getArchivo());
            }
            if (chainHashesImagenes != null && chainHashesImagenes.length() > 0) {
                chain.append(Util.getDigest(chainHashesImagenes.getBytes()));
                chain.append(Constantes.PIPE);
            }
        }

//        if (tramite.getReivindicaciones() != null && tramite.getReivindicaciones().size() > 0) {
//            for (Reivindicacion reiv : tramite.getReivindicaciones()) {
//                chainHashesReivindicaciones += Util.getDigest(reiv.getDescripcion().getBytes());
//            }
//            if (chainHashesReivindicaciones != null && chainHashesReivindicaciones.length() > 0) {
//                chain.append(Util.getDigest(chainHashesReivindicaciones.getBytes()));
////                chain.append(Constantes.PIPE);
//            }
//        }

        this.setCadenaSolicitantePat(chain.toString());

        lger.info("CadenaSolicitantePat     " + this.getCadenaSolicitantePat());

        return chain.toString();
    }

    public String generaCadenaSolicitante(TramiteDto tramite) {
        StringBuilder chain = new StringBuilder();
        String logo = "";
        for (Anexos anx : Util.checkListNull(tramite.getAnexos())) {
            chain.append(Util.getDigest(anx.getArchivoAnexo()));
            if (anx.getIdTipoanexo().longValue() == Constantes.ANEXO_DISENO.longValue()) {
                logo = Util.getDigest(anx.getArchivoAnexo());  //LOGOTIPO
            }
        }
        chain.append(Constantes.PIPE);
        chain.append(tramite.getSubTipoSolicitudDto().getIdSubtiposolicitud().toString());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getSolicitantesDto().get(0).getNombrecompleto());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getNombreFirmante() == null ? "" : tramite.getNombreFirmante());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getDescripcionsignodistintivo() == null ? "" : tramite.getDescripcionsignodistintivo());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getIdTipomarca() == null ? "" : tramite.getIdTipomarca());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getFechaprimeruso() == null ? "" : new SimpleDateFormat(BundleUtils.getResource("ddMMyyyy")).format(tramite.getFechaprimeruso()));
        chain.append(Constantes.PIPE);
        chain.append(tramite.getTipoClaseSeleccionadaDto().getIdClase());
        chain.append(Constantes.PIPE);
        chain.append(Util.getDigest(tramite.getProductoServicio().getBytes()));
        chain.append(Constantes.PIPE);
        chain.append(tramite.getLeyendasfigurasreservables() == null ? "" : tramite.getLeyendasfigurasreservables());
        chain.append(Constantes.PIPE);
        chain.append(logo == null ? "" : logo);
        chain.append(Constantes.PIPE);
        chain.append(tramite.getPagoDto().getFoliopago());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getSerieSolicante() == null ? "" : tramite.getSerieSolicante());
        this.setCadenaSolicitante(chain.toString());
        System.out.println("<<<<<<<<<<<<<< " + chain.toString());
        return chain.toString();
    }

    public String generaCadenaSolicitantePromo(TramitePromocionMarca tramite) {
        StringBuilder chain = new StringBuilder();
        String logo = "";
        for (Anexos anx : Util.checkListNull(tramite.getAnexos())) {
            chain.append(Util.getDigest(anx.getArchivoAnexo()));
            if (anx.getIdTipoanexo().longValue() == Constantes.ANEXO_DISENO.longValue()) {
                logo = Util.getDigest(anx.getArchivoAnexo());  //LOGOTIPO
            }
        }
        chain.append(Constantes.PIPE);
        chain.append(tramite.getIdTramitePromocionMarca().toString());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getListaTramitePersona().get(0).getPersona().getNombrecompleto());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getNombreFirmante() == null ? "" : tramite.getNombreFirmante());
        chain.append(Constantes.PIPE);
        
        chain.append(tramite.getFechaCaptura() == null ? "" : new SimpleDateFormat(BundleUtils.getResource("ddMMyyyy")).format(tramite.getFechaCaptura()));
        chain.append(Constantes.PIPE);
        
        chain.append(logo == null ? "" : logo);
        chain.append(Constantes.PIPE);
        chain.append(tramite.getPago() == null ? "" : tramite.getPago().getFoliopago());
        chain.append(Constantes.PIPE);
        chain.append(tramite.getSerieSolicante() == null ? "" : tramite.getSerieSolicante());
        this.setCadenaSolicitante(chain.toString());
        System.out.println("<<<<<<<<<<<<<< " + chain.toString());
        return chain.toString();
    }

    public String generaCadenaImpiPat(TramitePatente tramite, FirmaDto firma) {

        StringBuilder chain = new StringBuilder();

        chain.append(tramite.getTipoSol().getIdTiposolicitud());
        chain.append(Constantes.PIPE);
//        chain.append(""); //SERIE************
//        chain.append(Constantes.PIPE);
        chain.append(firma.getExpedienteId());//EXPEDIENTE*************
        chain.append(Constantes.PIPE);
        chain.append(firma.getFolioId());//FOLIO ***************
        chain.append(Constantes.PIPE);
//        chain.append(tramite.getFechaRecepcion() == null ? "" : new SimpleDateFormat(BundleUtils.getResource("ddMMyyyy.hhmmss")).format(tramite.getFechaRecepcion()));
        chain.append(firma.getFechaRegistro() == null ? "" : new SimpleDateFormat(BundleUtils.getResource("ddMMyyyy.hhmmss")).format(firma.getFechaRegistro()));
        chain.append(Constantes.PIPE);
        chain.append(firma.getFolioId());//CODIGO DE BARRAS ********
//        chain.append(firma.getCodigoBarras());//CODIGO DE BARRAS ********
        chain.append(Constantes.PIPE);
        chain.append(Util.getDigest(this.getCadenaSolicitantePat().getBytes()));//CADENA SOLICITANTE
        chain.append(Constantes.PIPE);
        chain.append(this.getCertificadora());//certificadora *************
        chain.append(Constantes.PIPE);
        chain.append(Constantes.OCSP);
        chain.append(Constantes.PIPE);
        chain.append(this.getOcspResponse());
        chain.append(Constantes.PIPE);
        chain.append(this.getFechaOcsp());  //respuesta del ocsp fecha timestamp
        chain.append(Constantes.PIPE);
        chain.append(this.getSerialNumberImpi());// validar

        String cad = chain.toString();
        System.out.println(">>>>>>" + cad);
        return chain.toString();
    }

    public String generaCadenaImpiProm(TramitePromocionMarca tramite, FirmaDto firma) {

        StringBuilder chain = new StringBuilder();

        chain.append(tramite.getIdSubtiposolicitud());
        chain.append(Constantes.PIPE);
        chain.append(""); //SERIE************
        chain.append(Constantes.PIPE);
        chain.append(firma.getExpedienteId() == null ? "" : firma.getExpedienteId());//EXPEDIENTE*************
        chain.append(Constantes.PIPE);
        chain.append(firma.getFolio());//FOLIO ***************
        chain.append(Constantes.PIPE);
        chain.append(tramite.getFechaCaptura() == null ? "" : new SimpleDateFormat(BundleUtils.getResource("ddMMyyyy.hhmmss")).format(tramite.getFechaCaptura()));
        chain.append(Constantes.PIPE);
        chain.append(firma.getCodigoBarras());//CODIGO DE BARRAS ********
        chain.append(Constantes.PIPE);
        chain.append(Util.getDigest(this.getCadenaSolicitante().getBytes()));//CADENA SOLICITANTE
        chain.append(Constantes.PIPE);
        chain.append(this.getCertificadora());//certificadora *************
        chain.append(Constantes.PIPE);
        chain.append(Constantes.OCSP);
        chain.append(Constantes.PIPE);
        chain.append(this.getOcspResponse());
        chain.append(Constantes.PIPE);
        chain.append(this.getFechaOcsp());  //respuesta del ocsp fecha timestamp
        chain.append(Constantes.PIPE);
        chain.append(this.getSerialNumberImpi());// validar

        String cad = chain.toString();
        System.out.println(">>>>>>" + cad);
        return chain.toString();
    }

    public String generaCadenaImpi(TramiteDto tramite) {

        StringBuilder chain = new StringBuilder();

        chain.append(tramite.getSubTipoSolicitudDto().getIdSubtiposolicitud());
        chain.append(Constantes.PIPE);
        chain.append(Constantes.YEAR_FOLIO);
        chain.append(Constantes.PIPE);
        chain.append(this.getExpediente());
        chain.append(Constantes.PIPE);
        chain.append(this.getFolio() == null ? "" : this.getFolio().split("/")[3]);
        chain.append(Constantes.PIPE);
        chain.append(tramite.getFechaSysdate() == null ? "" : new SimpleDateFormat(BundleUtils.getResource("ddMMyyyy.hhmmss")).format(tramite.getFechaSysdate()));
        chain.append(Constantes.PIPE);
        chain.append(this.getCodigoBarras());
        chain.append(Constantes.PIPE);
        chain.append(Util.getDigest(this.getCadenaSolicitante().getBytes()));
        chain.append(Constantes.PIPE);
        chain.append(tramite.getCertificadora());
        chain.append(Constantes.PIPE);
        chain.append(Constantes.OCSP);
        chain.append(Constantes.PIPE);
        chain.append(this.getOcspResponse());
        chain.append(Constantes.PIPE);
        chain.append(this.getFechaOcsp());  //respuesta del ocsp fecha timestamp
        chain.append(Constantes.PIPE);
        chain.append(this.getSerialNumberImpi());// validar

        String cad = chain.toString();
        System.out.println(">>>>>>" + cad);
        return chain.toString();
    }

    public void validaCertificadoImpi(CertificadoDto cer) throws BaseBusinessException, Exception {

        this.certificateValidatorService.start(cer.getCerFile(), cer.getKeyFile(), CipherEncript.dec(cer.getPass()));
        String[] iss = this.certificateValidatorService.getIssuer();

        Pattern pattern = Pattern.compile(BundleUtils.getResource("firma.pattern.serial"));
        Matcher matcher = pattern.matcher(iss[0]);
        if (matcher.find()) {
            this.setSerieImpi(matcher.group(1));
            //certDto.setNumeroSerie(matcher.group(1));
        }
        //this.setFirmaImpi(iss[2]);
        this.setSerialNumberImpi(iss[4]);
    }

    public String generaSelloDigitalmpi(String cadanaImpiGen) {
        String sello;
        try {
            sello = this.certificateValidatorService.generaSelloDigital(cadanaImpiGen);
        } catch (Exception e) {
            sello = "****";
        }

        return sello;
    }

    public boolean esCadenaValida(String cadanaImpiGen) {
        try {
            Object obj = Util.decodeObject(this.publicKey);
            return this.certificateValidatorService.validaCadena(obj, this.firmanteEncode, cadanaImpiGen);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean esCadenaValidar(String cadanaImpiGen, String enc) {   //encpvk
        try {
            Object obj = Util.decodeObject(this.publicKey);
            //String obj2 =(String) Util.decodeObject(enc);

            //return   this.certificateValidatorService.validaCadena(obj,this.firmanteEncode,cadanaImpiGen);
            byte[] decodeChain = (byte[]) Util.decodeByteSigned(this.firmanteEncode);

            byte[] unsigned = this.certificateValidatorService.unsign(decodeChain, (PublicKey) obj);

            String roundTrip = (String) Util.decodeObject(new String(unsigned, "UTF8"));
            if (roundTrip != null) {
                lger.info("FIRMA: FIRMANTE ENCODE ." + roundTrip + ".");
                if (roundTrip.equals(cadanaImpiGen)) {
                    return true;
                }

                return false;
            } else {
                lger.info("FIRMA: FIRMANTE ENCODE (roundTrip de cadena decodificada) es null.");
                return false;
            }
        } catch (Exception e) {
            lger.error("FIRMA: ERROR AL VALIDAR CADENA >>> " + e.getLocalizedMessage() + "   >>>>  " + e.getMessage(), e);
            return false;
        }
    }

    public String generaSelloDigitalPromovente(String enc, String cadenaSolicitante) {

        try {
            Object obj = Util.decodeObject(enc);
            return this.certificateValidatorService.generaSelloDigitalPromovente(obj, cadenaSolicitante);
        } catch (Exception e) {
            return "++++";
        }
    }

    public void generaAlerta() {
        try {
            DateFormat formatter = new SimpleDateFormat(BundleUtils.getResource("ddMMyyyy.hhmmss"));
            Date fechaEx = formatter.parse(this.getFechaExpiracion());

            if ((((fechaEx.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24)) < new Long(BundleUtils.getResource("firma.dias.expiracion")).longValue())) {
                SisAlerta alerta = new SisAlerta();
                alerta.setFecha(new Date());
                alerta.setFechaExpira(new Date());
                alerta.setIdMotivoAlerta((short) 1);
                ApoderadoDto obtApoderado = ContextUtils.obtenerApoderadoDeSesion(new Integer(this.getFirmanteId()));
                alerta.setIdPromovente(new BigDecimal(obtApoderado.getId_promovente()));
//                alerta.setNumeroCertificado(report.getSolNumeroSerie());

                this.flujosgralesViewService.insertarAlertas(alerta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String guardaFirmaPromocion(ActionEvent event) throws Exception {
        lger.info("=================================================DENTRO DE guardaFirmaPromocion");
        CertificadoDto cerDto = this.flujosgralesViewService.getFirmaAdmin(new CertificadoDto(1, 4));
        if (null != cerDto && cerDto.getIdCertificado() != null) {

            try {
                lger.info("=================================================INICIO validaCertificadoImpi");
                this.validaCertificadoImpi(cerDto);
                lger.info("=================================================FIN validaCertificadoImpi");
            } catch (BaseBusinessException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AL VALIDAR CERTIFICADO IMPI", BundleUtils.getResource("firma.error")));
                return "firma";
            } catch (Exception ee) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AL VALIDAR CERTIFICADO IMPI", BundleUtils.getResource("firma.error")));
                lger.error("FIRMA: Informacion de Error: >>> " + ee.getLocalizedMessage() + "   >>>>  " + ee.getMessage(), ee);
                return "firma";
            }
            
            //INICIO
//            } catch (Exception ee) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AL VALIDAR CERTIFICADO IMPI", BundleUtils.getResource("firma.error")));
//                lger.error("FIRMA: Informacion de Error: >>> " + ee.getLocalizedMessage() + "   >>>>  " + ee.getMessage(), ee);
//                return "firma";
//            }
//            TramitePatente pat = this.patentesViewService.obtenerTramitePatenteById(this.getTramiteId());
//            pat.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
            //FIN
            
            
//                                  TramitePatente pat = this.patentesViewService.obtenerTramitePatenteById(this.getTramiteId());
            TramitePatente patPromocion = this.patentesViewService.obtenerTramitePromocioneById(this.getTramiteId());
            
            lger.info("PAGOS    "+patPromocion.getPago());
            SolicitudPreparacionDto tramitePromocion = this.flujosgralesViewService.selectPromoByPrimaryKey(this.getTramiteId());
            lger.info("this.getTramiteId():      " + this.getTramiteId());

//            pat.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
//            tramitePromocion.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
            
            patPromocion.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
            tramitePromocion.setAnexos(patPromocion.getAnexos());           

//            String tmpCadena = this.generaCadenaSolicitantePat(pat);
            String tmpCadena = this.generaCadenaSolicitantePromo(tramitePromocion);
            lger.info("CADENA ORIGINAL2 ." + tmpCadena + ".");
            byte[] utf8By = tmpCadena.getBytes("UTF8");
            String bbc = new String(utf8By, "UTF8");
//            bbc = this.flujosgralesViewService.validatePhrase(bbc);
            utf8By = bbc.getBytes("UTF8");
            bbc = new String(utf8By, "UTF8");
            lger.info("FIRMA: CADENA ORIGINAL2 ." + bbc + ".");
            
            if (!this.esCadenaValidar(bbc, this.getFirmaImpi())) {
                lger.info("FIRMA ERROR: NO CONCUERDAN CADENAS.");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO CONCUERDAN CADENAS", BundleUtils.getResource("firma.error")));
                return "firma";
            }


            FirmaDto firma = new FirmaDto();
            Pattern pattern = Pattern.compile(BundleUtils.getResource("firma.pattern.nombresol"));
            Matcher matcher = pattern.matcher(this.getFirmanteNombre());
            lger.info("matcher    " + matcher);
            String nombreFirmante = null;
            if (matcher.find()) {
                nombreFirmante = matcher.group(1);
                lger.info("nombreFirmante    " + nombreFirmante);
            }

            matcher = pattern.matcher(this.getCertificadora());
            if (matcher.find()) {
                this.setCertificadora(matcher.group(1));
                lger.info("Certificadora    " + this.getCertificadora());
            }

            pattern = Pattern.compile(BundleUtils.getResource("firma.pattern.serial"));
            matcher = pattern.matcher(this.getFirmanteNombre());
            if (matcher.find()) {
                String rfc = matcher.group(1);
                if (Character.isDigit(rfc.charAt(3))) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Solo personas fisicas pueden firmar solicitudes", BundleUtils.getResource("firma.error")));
                    lger.info("FIRMA ERROR>>> RFC PERTENECE A PERSONA MORAL " + rfc);
                    return "firma";
                }
                //  this.setSerieSolicitante(matcher.group(1));
            }
            this.setFirmanteNombre(nombreFirmante);
            if (nombreFirmante != null && nombreFirmante.toUpperCase().trim().equals(this.getFirmanteBase().trim())) {

                try {
                    Date sysDate = this.flujosgralesViewService.getSysDate();
                    patPromocion.setSysDate(sysDate);
//                    List<Pago> pagos = this.flujosgralesViewService.selectPagoByTramiteId(pat.getIdTramitePatente());
                    List<Pago> pagos = this.flujosgralesViewService.selectPagoByTramiteId(tramitePromocion.getIdPromocion());

                    PagoDto pago = new PagoDto();
                    pago.setFechapago(pagos.get(0).getFechapago());
                    pago.setFeps(pagos.get(0).getFeps());
                    pago.setFoliopago(pagos.get(0).getFoliopago());
                    pago.setIdPago(pagos.get(0).getIdPago());
                    pago.setIdTramite(pagos.get(0).getIdTramite());
                    pago.setTotal(pagos.get(0).getTotal());
                    firma.setPago(pago);
                    Calendar c = Calendar.getInstance();
                    c.setTime(sysDate);
                    ContextUtils.getSession().removeAttribute("foliox");
                    firma.setCadenaImpi(this.getCadenaImpi());
                    lger.info("CadenaImpi    " + this.getCadenaImpi());
                    firma.setFechaRegistro(sysDate);
                    firma.setAnexoXml(new byte[]{});

                    firma.setCodigoBarras(null);

                    firma.setFechaPresentacion(sysDate);


                    firma.setIdFirmaAdmin(cerDto.getIdCertificado());
                    lger.info("CadenaImpi    " + firma.getIdFirmaAdmin());
//                    firma.setIdPatente(pat.getIdTramitePatente());
                    firma.setIdPromocion(tramitePromocion.getIdPromocion());

                    firma.setCertificadora(this.getCertificadora());
                    lger.info("Certificadora    " + this.getCertificadora());
                    firma.setNombreFirmante(nombreFirmante);
                    lger.info("nombreFirmante    " + nombreFirmante);
                    //pat.setFoliosSeries(this.flujosgralesViewService.getFoliosSagpat(pat.getExpDivisional().split("/")[1]));
                    //pat.setExpDivisional("MX/f/2011/000025");

                    String tmpExp = "";
//                    if (pat.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.CIRCUITO_INTEGRADO.getIdSubTipoPatente()) {
//                        tmpExp = EnumSubTipoPatente.CIRCUITO_INTEGRADO.getCode();
//                    } else if (pat.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.DISENO_INDUSTRIAL.getIdSubTipoPatente()) {
//                        tmpExp = EnumSubTipoPatente.DISENO_INDUSTRIAL.getCode();
//                    } else if (pat.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.MODELO_UTILIDAD.getIdSubTipoPatente()) {
//                        tmpExp = EnumSubTipoPatente.MODELO_UTILIDAD.getCode();
//                    } else if (pat.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.PATENTE.getIdSubTipoPatente()) {
//                        tmpExp = EnumSubTipoPatente.PATENTE.getCode();
//                    }
                    tmpExp = EnumSubTipoPatente.PATENTE.getCode();
                    lger.info("tmpExp    " + tmpExp);


                    String folioSagpat[] = this.flujosgralesViewService.getFoliosSagpat(tmpExp, sysDate);

                    if (folioSagpat == null || folioSagpat.length < 4) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, BundleUtils.getResource("firma.sagpat.folio"), BundleUtils.getResource("firma.error")));
                        return "firma";

                    }
                    patPromocion.setFoliosSeries(folioSagpat); //folioSagpat);
                    lger.info("folioSagpat    " + folioSagpat);
                    firma.setExpediente(new Long(patPromocion.getFoliosSeries()[2])); //;
                    lger.info("Expediente    " + firma.getExpediente());
                    firma.setFolio(patPromocion.getFoliosSeries()[3]);
                    lger.info("Folio    " + firma.getFolio());
                    firma.setExpedienteId("MX/" + tmpExp + "/" + c.get(Calendar.YEAR) + "/" + Util.nPosiciones(firma.getExpediente().toString(), 6));
                    firma.setFolioId("MX/E/" + c.get(Calendar.YEAR) + "/" + Util.nPosiciones(firma.getFolio(), 6));
                    firma.setFolio(firma.getFolioId());

                    lger.info("ExpedienteId    " + firma.getExpedienteId());
                    lger.info("FolioId    " + firma.getFolioId());
                    lger.info("Folio    " + firma.getFolio());

                    patPromocion.setTipoExpediente(tmpExp);
                    lger.info("tmpExp    " + tmpExp);

//                    pat.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
                    tramitePromocion.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));

//                  firma.setCadenaSolicitante(this.generaCadenaSolicitantePat(pat));
                    firma.setCadenaSolicitante(this.generaCadenaSolicitantePromo(tramitePromocion));

                    lger.info("CadenaSolicitante    " + firma.getCadenaSolicitante());
                    
                    firma.setCadenaImpi(this.generaCadenaImpiPat(patPromocion, firma));
                    lger.info("CadenaImpi    " + firma.getCadenaImpi());
                    firma.setFirmaImpi(this.generaSelloDigitalmpi(firma.getCadenaImpi()));
                    lger.info("FirmaImpi    " + firma.getFirmaImpi());
                    firma.setFirmaSolicitante(this.generaSelloDigitalPromovente(this.getFirmaImpi(), firma.getCadenaSolicitante()));
                    lger.info("FirmaSolicitante    " + firma.getFirmaSolicitante());
                    //VALIDAR DE DONDE LLENA LOS SOLICITANTES
                    if(patPromocion.getSolicitantes() !=null)
                        firma.setDescuento(Util.aplicaDescuento(patPromocion.getSolicitantes()) ? 1 : null);

                    if(tramitePromocion.getMostrarDescuento() != null)
                        firma.setDescuento(tramitePromocion.getMostrarDescuento().intValue());

//                    firma.setAcusePdf(this.generaPdf(pat, false, firma).toByteArray());
                    firma.setAcusePdf(this.generaPdfPromocion(tramitePromocion, true, firma.getFolioId()).toByteArray());

                    firma.setPdfPaginas(this.getPages());
                    firma.setClaveExpediente(firma.getExpedienteId());
                    lger.info("ClaveExpediente    " + firma.getClaveExpediente());

                    //VALIDAR
//                    firma.setAnexoXml(new AnexoPatenteXml(pat, firma, ContextUtils.datosPromovente()).getDocumentoXml());
                    firma.setAnexoXml(new AnexoPatenteXml(patPromocion, firma, ContextUtils.datosPromovente()).getDocumentoXml());

//                    this.setFirmanteId(pat.getIdUsuarioFirmante().toString());
                    if(patPromocion.getIdUsuarioFirmante() != null){
                        lger.info("pat.getIdUsuarioFirmante().toString()     " + patPromocion.getIdUsuarioFirmante().toString());
                        this.setFirmanteId(patPromocion.getIdUsuarioFirmante().toString());
                    }

                    lger.info("FirmanteId    " + this.getFirmanteId());
                    ApoderadoDto ap = ContextUtils.obtenerApoderadoDeSesion(new Integer(this.getFirmanteId()));
                    lger.info("ap    " + ap);

//                    pat.setFolioFirma(firma.getFolioId());
                    patPromocion.setFolioFirma(firma.getFolioId());
                    lger.info("FolioFirma    " + patPromocion.getFolioFirma());

                    this.flujosgralesViewService.insertFepsSagpat(patPromocion);

//                    ContextUtils.getSession().setAttribute("foliox", pat.getFoliosSeries()[3]);
                    ContextUtils.getSession().setAttribute("foliox", patPromocion.getFoliosSeries()[3]);

                    Long idFirma = this.flujosgralesViewService.insertaFirma(firma);


                    try {
                        this.generaAlerta();

//                        this.patentesViewService.updatePatenteSigned(pat.getIdTramitePatente());
                        this.patentesViewService.updatePatenteSigned(patPromocion.getIdTramitePatente());
                        this.mailService.sendMail(BundleUtils.getResource("firma.email.rdu"), BundleUtils.getResource("firma.email.func.pat"), BundleUtils.getResource("firma.email.subject"), BundleUtils.getResource("firma.email.bodymesspat") + " " + patPromocion.getFoliosSeries()[3]);
                        if (ap != null && ap.getEmail() != null) {
                            this.mailService.sendMail(BundleUtils.getResource("firma.email.rdu"), ap.getEmail(), BundleUtils.getResource("firma.email.subject"), BundleUtils.getResource("firma.email.bodymesspat") + " " + patPromocion.getFoliosSeries()[3]);
                        }

                    } catch (Exception e) {
                        lger.error("FIRMA: Informacion de Error: >>> " + e.getLocalizedMessage() + "   >>>>  " + e.getMessage(), e);
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    lger.error("FIRMA: Informacion de Error: >>> " + e.getLocalizedMessage() + "   >>>>  " + e.getMessage(), e);
                    // FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firma.faces");  //firma.sigmar.error
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, BundleUtils.getResource("firma.sagpat.error"), BundleUtils.getResource("firma.error")));
                    return "firma";
                }

            } else {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, BundleUtils.getResource("firma.nombre.mistmatch"), BundleUtils.getResource("firma.error")));
                return "firma";
            }
        } else {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO EXISTE CERTIFICADO  IMPI", BundleUtils.getResource("firma.error")));
            return "firma";
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firmasuccess.faces");
        return "firmasuccess";
    }

    public String guardaFirmaPatente(ActionEvent event) throws Exception {
        lger.info("=================================================DENTRO DE guardaFirmaPatente");
        //this.setFirmanteBase(this.getFirmanteNombreTramite());
        CertificadoDto cerDto = this.flujosgralesViewService.getFirmaAdmin(new CertificadoDto(1, 4));
        if (null != cerDto && cerDto.getIdCertificado() != null) {

            try {
                this.validaCertificadoImpi(cerDto);
            } catch (BaseBusinessException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AL VALIDAR CERTIFICADO IMPI", BundleUtils.getResource("firma.error")));
                return "firma";
            } catch (Exception ee) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR AL VALIDAR CERTIFICADO IMPI", BundleUtils.getResource("firma.error")));
                lger.error("FIRMA: Informacion de Error: >>> " + ee.getLocalizedMessage() + "   >>>>  " + ee.getMessage(), ee);
                return "firma";
            }
            TramitePatente pat = this.patentesViewService.obtenerTramitePatenteById(this.getTramiteId());
            pat.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
            ///////////////////////////////
            patenteDI =new PatentesDisenoIndustrialMB();
            patenteDI.setIdTramite(this.getTramiteId());
            this.patentesViewService.recuperarTramite(patenteDI);
            pat.getDomicilioObj().setPais(patenteDI.getTramitePat().getDomicilioObj().getPais());
            pat.getDomicilioObj().setEntidad(patenteDI.getTramitePat().getDomicilioObj().getEntidad());
                      // prueba ///////////////////////////
            List<Anexos> prioridadAnx=new ArrayList<Anexos>();
        
            for (int i = 0; i < patenteDI.getListaPrioridades().size(); i++) {
                String nombrePais = patenteDI.getListaPrioridades().get(i).getNombrePais();
                Long idPrioridad = patenteDI.getListaPrioridades().get(i).getIdPrioridad();
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
            
            for (int i = 0; i < patenteDI.getListaPrioridades().size(); i++) {
                String nombrePais = patenteDI.getListaPrioridades().get(i).getNombrePais();
                Long idPrioridad = patenteDI.getListaPrioridades().get(i).getIdPrioridad();
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
        ///prioridades 
        pat.setLstPrio(prioridadAnx);
            //////////////////////////////
            String tmpCadena = this.generaCadenaSolicitantePat(pat);
            lger.info("CADENA ORIGINAL2 ." + tmpCadena + ".");
            byte[] utf8By = tmpCadena.getBytes("UTF8");
            String bbc = new String(utf8By, "UTF8");
            bbc = this.flujosgralesViewService.validatePhrase(bbc);
            utf8By = bbc.getBytes("UTF8");
            bbc = new String(utf8By, "UTF8");
            lger.info("FIRMA: CADENA ORIGINAL2 ." + bbc + ".");


            if (!this.esCadenaValidar(bbc, this.getFirmaImpi())) {
                lger.info("FIRMA ERROR: NO CONCUERDAN CADENAS.");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO CONCUERDAN CADENAS", BundleUtils.getResource("firma.error")));
                return "firma";
            }


            FirmaDto firma = new FirmaDto();
            Pattern pattern = Pattern.compile(BundleUtils.getResource("firma.pattern.nombresol"));
            Matcher matcher = pattern.matcher(this.getFirmanteNombre());
            lger.info("matcher    " + matcher);
            String nombreFirmante = null;
            if (matcher.find()) {
                nombreFirmante = matcher.group(1);
                lger.info("nombreFirmante    " + nombreFirmante);
            }

            matcher = pattern.matcher(this.getCertificadora());
            if (matcher.find()) {
                this.setCertificadora(matcher.group(1));
                lger.info("Certificadora    " + this.getCertificadora());
            }

            pattern = Pattern.compile(BundleUtils.getResource("firma.pattern.serial"));
            matcher = pattern.matcher(this.getFirmanteNombre());
            if (matcher.find()) {
                String rfc = matcher.group(1);
                if (Character.isDigit(rfc.charAt(3))) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Solo personas fisicas pueden firmar solicitudes", BundleUtils.getResource("firma.error")));
                    lger.info("FIRMA ERROR>>> RFC PERTENECE A PERSONA MORAL " + rfc);
                    return "firma";
                }
                //  this.setSerieSolicitante(matcher.group(1));
            }
            this.setFirmanteNombre(nombreFirmante);
            if (nombreFirmante != null && nombreFirmante.toUpperCase().trim().equals(this.getFirmanteBase().trim())) {

                try {
                    Date sysDate = this.flujosgralesViewService.getSysDate();
                    pat.setSysDate(sysDate);
                    List<Pago> pagos = this.flujosgralesViewService.selectPagoByTramiteId(pat.getIdTramitePatente());
                    PagoDto pago = new PagoDto();
                    pago.setFechapago(pagos.get(0).getFechapago());
                    pago.setFeps(pagos.get(0).getFeps());
                    pago.setFoliopago(pagos.get(0).getFoliopago());
                    pago.setIdPago(pagos.get(0).getIdPago());
                    pago.setIdTramite(pagos.get(0).getIdTramite());
                    pago.setTotal(pagos.get(0).getTotal());
                    firma.setPago(pago);
                    Calendar c = Calendar.getInstance();
                    c.setTime(sysDate);
                    ContextUtils.getSession().removeAttribute("foliox");
                    firma.setCadenaImpi(this.getCadenaImpi());
                    lger.info("CadenaImpi    " + this.getCadenaImpi());
                    firma.setFechaRegistro(sysDate);
                    firma.setAnexoXml(new byte[]{});

                    firma.setCodigoBarras(null);

                    firma.setFechaPresentacion(sysDate);


                    firma.setIdFirmaAdmin(cerDto.getIdCertificado());
                    lger.info("CadenaImpi    " + firma.getIdFirmaAdmin());
                    firma.setIdPatente(pat.getIdTramitePatente());
                    firma.setCertificadora(this.getCertificadora());
                    lger.info("Certificadora    " + this.getCertificadora());
                    firma.setNombreFirmante(nombreFirmante);
                    lger.info("nombreFirmante    " + nombreFirmante);
                    //pat.setFoliosSeries(this.flujosgralesViewService.getFoliosSagpat(pat.getExpDivisional().split("/")[1]));
                    //pat.setExpDivisional("MX/f/2011/000025");

                    String tmpExp = "";
                    if (pat.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.CIRCUITO_INTEGRADO.getIdSubTipoPatente()) {
                        tmpExp = EnumSubTipoPatente.CIRCUITO_INTEGRADO.getCode();
                    } else if (pat.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.DISENO_INDUSTRIAL.getIdSubTipoPatente()) {
                        tmpExp = EnumSubTipoPatente.DISENO_INDUSTRIAL.getCode();
                    } else if (pat.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.MODELO_UTILIDAD.getIdSubTipoPatente()) {
                        tmpExp = EnumSubTipoPatente.MODELO_UTILIDAD.getCode();
                    } else if (pat.getTipoSol().getIdTiposolicitud().longValue() == EnumSubTipoPatente.PATENTE.getIdSubTipoPatente()) {
                        tmpExp = EnumSubTipoPatente.PATENTE.getCode();
                    }
                    else {tmpExp ="f";}
                    lger.info("tmpExp    " + tmpExp);


                    String folioSagpat[] = this.flujosgralesViewService.getFoliosSagpat(tmpExp, sysDate);

                    if (folioSagpat == null || folioSagpat.length < 4) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, BundleUtils.getResource("firma.sagpat.folio"), BundleUtils.getResource("firma.error")));
                        return "firma";

                    }
                    pat.setFoliosSeries(folioSagpat); //folioSagpat);
                    lger.info("folioSagpat    " + folioSagpat);
                    firma.setExpediente(new Long(pat.getFoliosSeries()[2])); //;
                    lger.info("Expediente    " + firma.getExpediente());
                    firma.setFolio(pat.getFoliosSeries()[3]);
                    lger.info("Folio    " + firma.getFolio());
                    firma.setExpedienteId("MX/" + tmpExp + "/" + c.get(Calendar.YEAR) + "/" + Util.nPosiciones(firma.getExpediente().toString(), 6));
                    firma.setFolioId("MX/E/" + c.get(Calendar.YEAR) + "/" + Util.nPosiciones(firma.getFolio(), 6));
                    firma.setFolio(firma.getFolioId());

                    lger.info("ExpedienteId    " + firma.getExpedienteId());
                    lger.info("FolioId    " + firma.getFolioId());
                    lger.info("Folio    " + firma.getFolio());

                    pat.setTipoExpediente(tmpExp);
                    lger.info("tmpExp    " + tmpExp);
                    pat.setAnexos(this.flujosgralesViewService.obtenerAnexosByTramite(this.getTramiteId()));
                    firma.setCadenaSolicitante(this.generaCadenaSolicitantePat(pat));
                    lger.info("CadenaSolicitante    " + firma.getCadenaSolicitante());
                    firma.setCadenaImpi(this.generaCadenaImpiPat(pat, firma));
                    lger.info("CadenaImpi    " + firma.getCadenaImpi());
                    firma.setFirmaImpi(this.generaSelloDigitalmpi(firma.getCadenaImpi()));
                    lger.info("FirmaImpi    " + firma.getFirmaImpi());
                    firma.setFirmaSolicitante(this.generaSelloDigitalPromovente(this.getFirmaImpi(), firma.getCadenaSolicitante()));
                    lger.info("FirmaSolicitante    " + firma.getFirmaSolicitante());
                    firma.setDescuento(Util.aplicaDescuento(pat.getSolicitantes()) ? 1 : null);
                    
                    
                    
                    //AQUI
                    firma.setAcusePdf(this.generaPdf(pat, false, firma).toByteArray());
                    firma.setPdfPaginas(this.getPages());
                    firma.setClaveExpediente(firma.getExpedienteId());
                    lger.info("ClaveExpediente    " + firma.getClaveExpediente());

                    firma.setAnexoXml(new AnexoPatenteXml(pat, firma, ContextUtils.datosPromovente()).getDocumentoXml());
                    lger.info("pat.getIdUsuarioFirmante().toString()     " + pat.getIdUsuarioFirmante().toString());
                    this.setFirmanteId(pat.getIdUsuarioFirmante().toString());
                    lger.info("FirmanteId    " + this.getFirmanteId());
                    ApoderadoDto ap = ContextUtils.obtenerApoderadoDeSesion(new Integer(this.getFirmanteId()));
                    lger.info("ap    " + ap);
                    pat.setFolioFirma(firma.getFolioId());
                    lger.info("FolioFirma    " + pat.getFolioFirma());


                    //copiar solicitud en sagpat
//                    if( 0 ==  this.flujosgralesViewService.copiarSolicitudEnSagpat(pat, this.getPages())){
//                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, BundleUtils.getResource("firma.sagpat.error"), BundleUtils.getResource("firma.error")));
//                        return "firma";
//                    }
                    this.flujosgralesViewService.insertFepsSagpat(pat);
                    ContextUtils.getSession().setAttribute("foliox", pat.getFoliosSeries()[3]);
                    Long idFirma = this.flujosgralesViewService.insertaFirma(firma);


                    try {
                        this.generaAlerta();

                        this.patentesViewService.updatePatenteSigned(pat.getIdTramitePatente());
                        this.mailService.sendMail(BundleUtils.getResource("firma.email.rdu"), BundleUtils.getResource("firma.email.func.pat"), BundleUtils.getResource("firma.email.subject"), BundleUtils.getResource("firma.email.bodymesspat") + " " + pat.getFoliosSeries()[3]);
                        if (ap != null && ap.getEmail() != null) {
                            this.mailService.sendMail(BundleUtils.getResource("firma.email.rdu"), ap.getEmail(), BundleUtils.getResource("firma.email.subject"), BundleUtils.getResource("firma.email.bodymesspat") + " " + pat.getFoliosSeries()[3]);
                        }

                    } catch (Exception e) {
                        lger.error("FIRMA: Informacion de Error: >>> " + e.getLocalizedMessage() + "   >>>>  " + e.getMessage(), e);
                    }




                } catch (Exception e) {

                    e.printStackTrace();
                    lger.error("FIRMA: Informacion de Error: >>> " + e.getLocalizedMessage() + "   >>>>  " + e.getMessage(), e);
                    // FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firma.faces");  //firma.sigmar.error
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, BundleUtils.getResource("firma.sagpat.error"), BundleUtils.getResource("firma.error")));
                    return "firma";


                }



            } else {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, BundleUtils.getResource("firma.nombre.mistmatch"), BundleUtils.getResource("firma.error")));
                return "firma";
            }
        } else {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "NO EXISTE CERTIFICADO  IMPI", BundleUtils.getResource("firma.error")));
            return "firma";
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firmasuccess.faces");
        return "firmasuccess";
    }

    public String guardaFirma(ActionEvent event) throws Exception {
        lger.info("=================================================INICIO GUARDAR FIRMA");
        lger.info("ENTRO A guardaFirma");

        try {
            SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();

            if (null != obtSession) {
                this.setTramiteId(obtSession.getIdTramite());

                if (obtSession.getIdTipoTramite() == 11) {//patentes
                    lger.info("=================================================INICIO GUARDAR FIRMA PATENTES");
                    this.guardaFirmaPatente(event);
                }else if (obtSession.getIdTipoTramite() == 20) {//patentes
                    lger.info("=================================================INICIO GUARDAR FIRMA PATENTES");
                    this.guardaFirmaPatente(event);
                } else if (obtSession.getIdTipoTramite() == 4) {//promociones
                    lger.info("=================================================INICIO GUARDAR PROMOCIONES");
                    this.guardaFirmaPromocion(event);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "SIN CONEXION PASE", BundleUtils.getResource("firma.error")));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firmaerror.faces");
            }
            lger.info("=================================================FIN GUARDAR FIRMA");



        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/firmaerror.faces");
        }


        return null;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public void setFirmanteId(String firmanteId) {
        this.firmanteId = firmanteId;
    }

    public void setFirmanteNombre(String firmanteNombre) {
        this.firmanteNombre = firmanteNombre;
    }

    public String getCert() {
        return cert;
    }

    public String getFirmanteId() {
        return firmanteId;
    }

    public String getFirmanteNombre() {
        return firmanteNombre;
    }

    public String getCertificadora() {
        return certificadora;
    }

    public String getFirmaDigitalPromovente() {
        return firmaDigitalPromovente;
    }

    public void setCertificadora(String certificadora) {
        this.certificadora = certificadora;
    }

    public void setFirmaDigitalPromovente(String firmaDigitalPromovente) {
        this.firmaDigitalPromovente = firmaDigitalPromovente;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getExpediente() {
        return expediente;
    }

    public String getFolio() {
        return folio;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFirmanteBase() {
        return firmanteBase;
    }

    public void setFirmanteBase(String firmanteBase) {
        this.firmanteBase = firmanteBase;
    }

    public String getFirmaImpi() {
        return firmaImpi;
    }

    public void setFirmaImpi(String firmaImpi) {
        this.firmaImpi = firmaImpi;
    }

    public Long getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(Long tramiteId) {
        this.tramiteId = tramiteId;
    }

    public String getSerieSolicitante() {
        return serieSolicitante;
    }

    public void setSerieSolicitante(String serieSolicitante) {
        this.serieSolicitante = serieSolicitante;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCadenaImpi() {
        return cadenaImpi;
    }

    public String getCadenaSolicitante() {
        return cadenaSolicitante;
    }

    public void setCadenaImpi(String cadenaImpi) {
        this.cadenaImpi = cadenaImpi;
    }

    public void setCadenaSolicitante(String cadenaSolicitante) {
        this.cadenaSolicitante = cadenaSolicitante;
    }

    public String getOcspResponse() {
        return ocspResponse;
    }

    public void setOcspResponse(String ocspResponse) {
        this.ocspResponse = ocspResponse;
    }

    public String getFechaOcsp() {
        return fechaOcsp;
    }

    public void setFechaOcsp(String fechaOcsp) {
        this.fechaOcsp = fechaOcsp;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public String getSerieImpi() {
        return serieImpi;
    }

    public void setSerieImpi(String serieImpi) {
        this.serieImpi = serieImpi;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public long getPages() {
        return pages;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public void setFirmanteEncode(String firmanteEncode) {
        this.firmanteEncode = firmanteEncode;
    }

    public String getFirmanteEncode() {
        return firmanteEncode;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setCadenaSolicitantePat(String cadenaSolicitantePat) {
        this.cadenaSolicitantePat = cadenaSolicitantePat;
    }

    public String getCadenaSolicitantePat() {
        return cadenaSolicitantePat;
    }

    public String getSerialNumberSolicitante() {
        return serialNumberSolicitante;
    }

    public void setSerialNumberSolicitante(String serialNumberSolicitante) {
        this.serialNumberSolicitante = serialNumberSolicitante;
    }

    public String getSerialNumberImpi() {
        return serialNumberImpi;
    }

    public void setSerialNumberImpi(String serialNumberImpi) {
        this.serialNumberImpi = serialNumberImpi;
    }

    public String getVerifyEnc() {
        return verifyEnc;
    }

    public void setVerifyEnc(String verifyEnc) {
        this.verifyEnc = verifyEnc;
    }

    public Persona getPromovente() {
        return promovente;
    }

    public void setPromovente(Persona promovente) {
        this.promovente = promovente;
    }
}

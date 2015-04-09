/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.codec.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.impi.rdu.dataModel.PersonaDM;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.dto.ApoderadoDto;
import mx.gob.impi.rdu.dto.DomicilioDto;
import mx.gob.impi.rdu.dto.PromocionesConOficioDto;
import mx.gob.impi.rdu.dto.ReporteDescripcionDto;
import mx.gob.impi.rdu.dto.ReporteReivindicacion;
import mx.gob.impi.rdu.dto.ReporteResumen;
import mx.gob.impi.rdu.dto.ReportesDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.exposition.flujosGenerales.helper.IncludeDomicilio;
import mx.gob.impi.rdu.exposition.flujosGenerales.reporte.GenerarReporte;
import mx.gob.impi.rdu.exposition.form.CapturaSolicitudForm;
import mx.gob.impi.rdu.exposition.patentes.PatentesDisenoIndustrialMB;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.CatAnexos;
import mx.gob.impi.rdu.persistence.model.CatAreaPromPatentes;
import mx.gob.impi.rdu.persistence.model.CatSubtiposolicitud;
import mx.gob.impi.rdu.persistence.model.CatTipoPromPatentes;
import mx.gob.impi.rdu.persistence.model.CatTipoSolicitante;
import mx.gob.impi.rdu.persistence.model.CatTipopersona;
import mx.gob.impi.rdu.persistence.model.Datoscontacto;
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;
import mx.gob.impi.rdu.persistence.model.Pais;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.Prioridad;
import mx.gob.impi.rdu.persistence.model.PromocionesPatentes;
import mx.gob.impi.rdu.persistence.model.TipoPromPatByOficio;
import mx.gob.impi.rdu.persistence.model.TipoPublicacionPct;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.remote.RduCatalogosBeanRemote;
import mx.gob.impi.rdu.remote.RduFlujosGeneralesBeanRemote;
import mx.gob.impi.rdu.remote.RduPatentesBean;
import mx.gob.impi.rdu.remote.RduPatentesBeanRemote;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.Util;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPct;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPctMU;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author adriana
 */
public class PatentesViewServiceImpl implements PatentesViewService {

    private Logger log = Logger.getLogger(this.getClass());
    private RduPatentesBeanRemote rduPatentesBeanRemote;
    private RduCatalogosBeanRemote rduCatalogosBeanRemote;   
    private RduFlujosGeneralesBeanRemote rduFlujosGeneralesRemote;
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.CapturaSolPatentes";
    private final ResourceBundle bundleParametrosPatentes = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
    
    @EJB(mappedName="EJBRduPatentesBean")
    private RduPatentesBean rduPatentesBean;

    public void setRduCatalogosBeanRemote(RduCatalogosBeanRemote rduCatalogosBeanRemote) {
        this.rduCatalogosBeanRemote = rduCatalogosBeanRemote;
    }

    public void setRduPatentesBeanRemote(RduPatentesBeanRemote rduPatentesBeanRemote) {
        this.rduPatentesBeanRemote = rduPatentesBeanRemote;
    }

    public RduFlujosGeneralesBeanRemote getRduFlujosGeneralesRemote() {
        return rduFlujosGeneralesRemote;
    }

    public void setRduFlujosGeneralesRemote(RduFlujosGeneralesBeanRemote rduFlujosGeneralesRemote) {
        this.rduFlujosGeneralesRemote = rduFlujosGeneralesRemote;
    }

    public RduPatentesBean getRduPatentesBean() {
        return rduPatentesBean;
    }

    public void setRduPatentesBean(RduPatentesBean rduPatentesBean) {
        this.rduPatentesBean = rduPatentesBean;
    }

    public boolean guardar(PatentesDisenoIndustrialMB forma) {
        boolean resul = false;
        TramitePatente tramite = new TramitePatente();
        tramite = forma.getTramitePat();

        try {


            tramite.setIdSubtipoSolicitud(forma.getTramitePat().getSubTipoSol().getIdSubtiposolicitud());
            tramite.setIndActivo(new Short("1"));

            tramite.setIdEstatus(Constantes.INCOMPLETO);

            if (forma.getTramitePat().isMaterial_biologico() == true) {
                tramite.setHayMatBiologico(new Integer(1));
            } else {
                tramite.setHayMatBiologico(new Integer(0));
            }

            if (forma.getTramitePat().isPub_anticipada() == true) {
                tramite.setHayPubAnticipada(new Integer(1));
            } else {
                tramite.setHayPubAnticipada(new Integer(0));
            }


            tramite.setFechaCaptura(new Date());
            tramite.setFechaEstatusActual(tramite.getFechaCaptura());
            tramite.setFechaDivPrevia(forma.getTramitePat().getFechaDivPrevia());
            tramite.setFechaDivulgacion(forma.getTramitePat().getFechaDivulgacion());
            tramite.setExpDivisional(forma.getTramitePat().getExpDivisional());

            tramite.setNumSolPCT(forma.getTramitePat().getNumSolPCT());
            tramite.setFechaSolPCT(forma.getTramitePat().getFechaSolPCT());
            tramite.setFaseSolPCT(forma.getTramitePat().getFaseSolPCT());
            tramite.setFechaPubPCT(forma.getTramitePat().getFechaPubPCT());
            tramite.setNumPubPCT(forma.getTramitePat().getNumPubPCT());
            tramite.setTipoPubPCT(forma.getTramitePat().getTipoPubPCT());
            
            if (tramite.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_PATENTE.longValue() || 
                tramite.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_MODELO_UTILIDAD.longValue()) {
                if (forma.getForma().getCapPCT().equals("1")){
                    tramite.setFaseSolPCT("Capitulo 1");
                    tramite.setFaseInternacional(new Integer(1));
                }else{
                    tramite.setFaseSolPCT("Capitulo 2");
                    tramite.setFaseInternacional(new Integer(2));
                }
            }    
//            tramite.setTipoPubPCT(forma.getTramitePat().getTipoPubPCT());
//            tramite.setTipoPubPCT(forma.getTramitePat().getTipoPubPCT());

            tramite.setInvencion(forma.getTramitePat().getInvencion());
            if (forma.getTramitePat().getObservaciones() != null) {
                tramite.setObservaciones(forma.getTramitePat().getObservaciones());
            }

            if (forma.getTramitePat().getResumen() != null) {
                tramite.setResumen(forma.getTramitePat().getResumen());
            }

            //recupera de la sesion el usuario de captura
            SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
            tramite.setIdUsuarioCaptura(1L);
            if (null != obtSession) {
                if (obtSession.getUsuario().getIdUsuario() > 0) {
                    tramite.setIdUsuarioCaptura(obtSession.getUsuario().getIdUsuario());
                }
            }
            tramite.setIdUsuarioFirmante(forma.getTramitePat().getIdUsuarioFirmante());
            tramite.setSolicitantes(forma.getListaSolicitantes());
            tramite.setInventores(forma.getListaInventores());
            tramite.setPrioridades(forma.getListaPrioridades());

            tramite.setApoderados(forma.getTramitePat().getApoderados());
            tramite.setPersonasNot(forma.getTramitePat().getPersonasNot());
            
            if(!tramite.getReivindicaciones().isEmpty() && forma.getForma().getIdiomaReivindicacion() != null)
                tramite.getReivindicaciones().get(Constantes.FIRST).setIdIdiomas(
                    Integer.parseInt(forma.getForma().getIdiomaReivindicacion()));
            
            
            if(forma.getForma().getIdiomaDescripcion()!= null)
                tramite.setIdIdiomaDescripcion(forma.getForma().getIdiomaDescripcion());

            if(forma.getForma().getIdiomaReivindicacion() != null)
                tramite.setIdiomaReivindicacion(forma.getForma().getIdiomaReivindicacion());

            if(forma.getForma().getIdiomaResumen() != null)
                tramite.setIdiomaResumen(forma.getForma().getIdiomaResumen());

            long id_tramite = 0L;
            
            TramitePatente tramitePatente = rduPatentesBeanRemote.insertarTramitePatente(tramite);
            id_tramite = tramitePatente.getIdTramitePatente();
            tramite.setIdTramitePatente(id_tramite);
            forma.setIdTramite(tramite.getIdTramitePatente());
            forma.getTramitePat().setIdTramitePatente(id_tramite);
            //Tipo de Solicitud
            forma.setTipoSol(tramite.getTipoSol());
            //Si adjunto un archivo con una descripcion debera almacenarse.
//            insertarPdfDescripcion(forma, id_tramite);
              insertarPdfAnexosMemoriaTecnica(forma, tramite.getIdTramitePatente());
              insertarPdfAnexosApoderado(forma,tramite.getIdTramitePatente());
              insertarPdfDivulgacionPrevia(forma, tramite.getIdTramitePatente());
            resul=true;

            System.out.println("tramite insertado" + id_tramite);
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
        return resul;
    }
    
    public void insertarPdfDescripcion(PatentesDisenoIndustrialMB forma, Long id_tramite){
            AnexosViewDto anexoDescripcion = forma.getForma().getAnexoDescripcionMe();
            AnexosViewDto anexoMemoria = new AnexosViewDto();
            AnexosViewDto anexoResumen = forma.getForma().getAnexoResumen();
            Integer Paginas = 0;
            System.out.println("Anexo descripcion dif null ? " + (anexoDescripcion != null));
                
            //Si existen archivos previos de descripcion se eliminan para contener solo 1
            Anexos anexoEliminar = new Anexos();
            anexoEliminar.setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);
            anexoEliminar.setIdTramitePatente(id_tramite);
            
            rduFlujosGeneralesRemote.deleteByTypeAnexo(anexoEliminar);
            
            anexoEliminar.setIdTipoanexo(Constantes.ANEXO_RESUMEN_MEMORIA);
            anexoEliminar.setIdTramitePatente(id_tramite);
            rduFlujosGeneralesRemote.deleteByTypeAnexo(anexoEliminar);
            
            anexoEliminar.setIdTipoanexo(Constantes.ANEXO_REIVINDICACIONES_PATENTE);
            anexoEliminar.setIdTramitePatente(id_tramite);
            rduFlujosGeneralesRemote.deleteByTypeAnexo(anexoEliminar);
            
            if(forma.getForma().getAnexoResumen() != null && forma.getForma().getAnexoResumen().getNombreArchivo()!=null){
                anexoMemoria = forma.getForma().getAnexoResumen();
                anexoMemoria.setCargado(true);
                anexoMemoria.setIdTramitePatente(id_tramite);
                List<AnexosViewDto> anexos = new ArrayList<>();
                anexos.add(anexoMemoria);

                //Se inserta el anexo DEL RESUMEN
                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexos);
            }
            
            if(forma.getForma().getAnexoDescripcionMe()!=null && forma.getForma().getAnexoDescripcionMe().getNombreArchivo()!=null){
                anexoMemoria = forma.getForma().getAnexoDescripcionMe();
                anexoMemoria.setCargado(true);
                anexoMemoria.setIdTramitePatente(id_tramite);
                List<AnexosViewDto> anexos = new ArrayList<>();
                anexos.add(anexoMemoria);

                //Se inserta el anexo DE LA DESCRIPCION
                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexos);
                
            }else{
     
            if((forma.getTramitePat().getPreambulo() != null && !forma.getTramitePat().getPreambulo().equals(""))
                    || (forma.getTramitePat().getTextoAdicional() != null && !forma.getTramitePat().getTextoAdicional().equals("")) 
                    || (!forma.getTramitePat().getReivindicaciones().isEmpty())){
            try {
                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().
                                                            getExternalContext().getRequest();
                GenerarReporte generarReporte = new GenerarReporte();
                ReporteDescripcionDto descripcionDto = new ReporteDescripcionDto();
                
                descripcionDto.setPreambulo(forma.getTramitePat().getPreambulo());
                descripcionDto.setTextoAdicional(forma.getTramitePat().getTextoAdicional());
                
               // if(forma.getTramitePat().getReivindicaciones().isEmpty())
                    descripcionDto.setDesReivindicaciones("");
               // else
              //      descripcionDto.setDesReivindicaciones(forma.getTramitePat().getReivindicaciones().get(Constantes.FIRST).getDescripcion());

//                    for(CatSubtiposolicitud Subtiposolicitud : forma.getTramitePat().getSubTipoSol()){listSubtiposSol
                String subTipo = "";
                for(CatSubtiposolicitud Subtiposolicitud : forma.getListSubtiposSol()){                        
                    if(Subtiposolicitud.getIdSubtiposolicitud().intValue() == forma.getTramitePat().getSubTipoSol().getIdSubtiposolicitud().intValue())
                        subTipo = Subtiposolicitud.getDescripcion();
                }
                    
//                    String tituloDescripcion = forma.getTramitePat().getInvencion().replace("DE", "");
                if (forma.getTipoSol().getIdTiposolicitud().intValue()== Constantes.TIPO_SOL_MODELO_UTILIDAD.intValue()){
                    descripcionDto.setTituloDescripcion(forma.getTramitePat().getInvencion().toUpperCase());
                }else{
                    descripcionDto.setTituloDescripcion(subTipo.toUpperCase()+" "+forma.getTramitePat().getInvencion().toUpperCase());
                }
                //Leer el archivo y validar el numero de hojas que tiene 
                if (anexoDescripcion != null)
                {
                    ByteArrayInputStream Bya = new ByteArrayInputStream(forma.getForma().getAnexoDescripcionMe().getArchivoAnexo());
                    InputStream PDF = Bya;
                    PdfReader pdfReader= new PdfReader(PDF);
                    Paginas = pdfReader.getNumberOfPages();
                    pdfReader.close();
                    //System.out.println("Numero de paginas del PDF: " + Paginas);

                }else
                {
                    Paginas=0;
                }
                descripcionDto.setPaginas(Paginas);
               ///
                
                
                ByteArrayOutputStream byt = generarReporte.generarDescripcionEnPdf(request.getRealPath("")
                                + "/content/reportes/descripcionTexto.jasper", descripcionDto);
                byte[] archivoAnexo = byt.toByteArray();
                
                PdfReader pdfReader= new PdfReader(archivoAnexo);
                Paginas = pdfReader.getNumberOfPages();
                pdfReader.close();
                forma.setnPaginas(Paginas);
                //forma.getTramitePat().setPaginasDescripcion(Paginas);
                
                List<AnexosViewDto> anexoDescuentoTexto = new ArrayList<AnexosViewDto>();

                AnexosViewDto nvoAnexo = new AnexosViewDto();
                nvoAnexo.setArchivoAnexo(archivoAnexo);
                nvoAnexo.setExtension("pdf");
                nvoAnexo.setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);
                nvoAnexo.setIdTramitePatente(id_tramite);
                //nvoAnexo.setNombreArchivo("descripcion.pdf");
                nvoAnexo.setNombreArchivo("descripcion.pdf");
                nvoAnexo.setCargado(true);

                anexoDescuentoTexto.add(nvoAnexo);

                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoDescuentoTexto);
             }
            //else if (anexoDescripcion != null && anexoDescripcion.getNombreArchivo()!=null) {
            //                    anexoDescripcion.setCargado(true);
            //                    anexoDescripcion.setIdTramitePatente(id_tramite);
            //                    List<AnexosViewDto> anexos = new ArrayList<>();
            //                    anexos.add(anexoDescripcion);
            //
            //                    //Se inserta el anexo descripcion
            //                    rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexos);
            //                }
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
            
        }
//else if (anexoDescripcion != null && anexoDescripcion.getNombreArchivo()!=null) {
//                    anexoDescripcion.setCargado(true);
//                    anexoDescripcion.setIdTramitePatente(id_tramite);
//                    List<AnexosViewDto> anexos = new ArrayList<>();
//                    anexos.add(anexoDescripcion);
//                    
//                    //Se inserta el anexo descripcion
//                    rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexos);
//                }
        
    }
   public void insertarPdfAnexosMemoriaTecnica(PatentesDisenoIndustrialMB forma, Long id_tramite){
            AnexosViewDto anexoDescripcion = forma.getForma().getAnexoDescripcionMe();
            AnexosViewDto anexoMemoria = new AnexosViewDto();
            AnexosViewDto anexoResumen = forma.getForma().getAnexoResumen();
            AnexosViewDto anexoDto= new AnexosViewDto();
            Anexos anexoEliminar = new Anexos();
            short otroIdioma;
            Integer Paginas = 0;
            System.out.println("Anexo descripcion dif null ? " + (anexoDescripcion != null));
            
            for (int i=0; i< forma.getLstAnexosDto().size();i++){
            anexoDto=forma.getLstAnexosDto().get(i);
/////////// A N E X O      D E S C R I P C I O N            
            if (anexoDto.getIdTipoanexo().intValue()==Constantes.ANEXO_DESCRIPCION_PATENTE){
//                anexoEliminar.setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);
//                anexoEliminar.setIdTramitePatente(id_tramite);
//                rduFlujosGeneralesRemote.deleteByTypeAnexo(anexoEliminar);
                if (anexoDto.getOtroIdioma()==1 || (anexoDto.getOtroIdioma()==2 && anexoDto.getTradCargada()==false && anexoDto.getTxtAnexoTrad()==null) ) {    
                    if(anexoDto.getNombreArchivo()!= null && anexoDto.getTxtAnexo()== null){
                        try {
                            insertaPdfAnexos(anexoDto,id_tramite);   
                            ByteArrayInputStream Bya = new ByteArrayInputStream(anexoDto.getArchivoAnexo());
                            InputStream PDF = Bya;
                            PdfReader pdfReader= new PdfReader(PDF);
                            Paginas = pdfReader.getNumberOfPages();
                            pdfReader.close();
                            forma.setnPaginas(Paginas);
                        }
                            catch (IOException ex) {
                            java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        if(anexoDto.getTxtAnexo()!= null && !anexoDto.getTxtAnexo().equals("")){
                            try {
                                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().
                                                                getExternalContext().getRequest();
                                GenerarReporte generarReporte = new GenerarReporte();
                                ReporteDescripcionDto descripcionDto = new ReporteDescripcionDto();
                                descripcionDto.setPreambulo(enumeraParrafos(anexoDto.getTxtAnexo()));
                                descripcionDto.setTextoAdicional(forma.getTramitePat().getTextoAdicional());
                                descripcionDto.setDesReivindicaciones("");
                                String subTipo = "";
                                for(CatSubtiposolicitud Subtiposolicitud : forma.getListSubtiposSol()){                        
                                    if(Subtiposolicitud.getIdSubtiposolicitud().intValue() == forma.getTramitePat().getSubTipoSol().getIdSubtiposolicitud().intValue())
                                        subTipo = Subtiposolicitud.getDescripcion();
                                }
//                                 for(int k=0;k< forma.getAnexosMemoria().size();k++){
//                                    if (forma.getAne) 
//                                 }
                                if (forma.getTipoSol().getIdTiposolicitud().intValue()== Constantes.TIPO_SOL_MODELO_UTILIDAD.intValue()){
                                    descripcionDto.setTituloDescripcion(forma.getTramitePat().getInvencion().toUpperCase());
                                }else{
                                    descripcionDto.setTituloDescripcion(subTipo.toUpperCase()+" "+forma.getTramitePat().getInvencion().toUpperCase());
                                }
//                                descripcionDto.setTituloDescripcion(subTipo.toUpperCase()+" "+forma.getTramitePat().getInvencion().toUpperCase());
                                if (anexoDescripcion != null)
                                {
                                    ByteArrayInputStream Bya = new ByteArrayInputStream(forma.getForma().getAnexoDescripcionMe().getArchivoAnexo());
                                    InputStream PDF = Bya;
                                    PdfReader pdfReader= new PdfReader(PDF);
                                    Paginas = pdfReader.getNumberOfPages();
                                    pdfReader.close();
                                }else{
                                    Paginas=0;
                                }
                                descripcionDto.setPaginas(Paginas);
                                ByteArrayOutputStream byt = generarReporte.generarDescripcionEnPdf(request.getRealPath("")
                                                            + "/content/reportes/descripcionTexto.jasper", descripcionDto);

                                byte[] archivoAnexo = byt.toByteArray();

                                PdfReader pdfReader= new PdfReader(archivoAnexo);
                                Paginas = pdfReader.getNumberOfPages();
                                pdfReader.close();
                                forma.setnPaginas(Paginas);                                
                                List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                AnexosViewDto nvoAnexo = new AnexosViewDto();
                                nvoAnexo.setArchivoAnexo(archivoAnexo);
                                nvoAnexo.setExtension("pdf");
                                nvoAnexo.setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);
                                nvoAnexo.setIdTramitePatente(id_tramite);
                                nvoAnexo.setNombreArchivo("descripcion.pdf");
                                nvoAnexo.setTxtAnexo(anexoDto.getTxtAnexo());
                                nvoAnexo.setCargado(true);
                                nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                nvoAnexo.setIdAnexo(anexoDto.getIdAnexo());
                                anexoTexto.add(nvoAnexo);
                                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);
                            }
                                catch (IOException ex) {
                                java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                      }    
                    } 
                }else{                    
                    if(anexoDto.getNombreArchivo()!= null && anexoDto.getTxtAnexo()== null) {
                      //4 Tanto la Descripción como la traducción son archivos PDF                         
                      if(anexoDto.getNombreTrad()!=null && anexoDto.getTxtAnexoTrad()==null){
                        anexoDto.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_DESCRIPCION);
//                        insertaPdfAnexos(anexoDto,id_tramite);
                        try {
                            insertaPdfAnexos(anexoDto,id_tramite);   
                            ByteArrayInputStream Bya = new ByteArrayInputStream(anexoDto.getArchivoAnexo());
                            InputStream PDF = Bya;
                            PdfReader pdfReader= new PdfReader(PDF);
                            Paginas = pdfReader.getNumberOfPages();
                            pdfReader.close();
                            forma.setnPaginas(Paginas);
                            Bya = new ByteArrayInputStream(anexoDto.getArchivoTrad());
                            PDF = Bya;
                            pdfReader= new PdfReader(PDF);
                            Paginas = pdfReader.getNumberOfPages();
                            pdfReader.close();
                            forma.setnPaginasTrad(Paginas);
                        }
                            catch (IOException ex) {
                            java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                      }else{//3 La descripción es archivo PDF y la Traducción es Texto
                          try { //Descripción Original PDF
                                ByteArrayInputStream Bya = new ByteArrayInputStream(anexoDto.getArchivoAnexo());
                                InputStream PDF = Bya;
                                PdfReader pdfReader= new PdfReader(PDF);
                                Paginas = pdfReader.getNumberOfPages();
                                pdfReader.close();
                                forma.setnPaginas(Paginas);
                                //Traducción convertida a PDF
                                byte[] archivoAnexoTrad = generaDescripcionPDF(forma,anexoDto,true);
                                PdfReader pdfReaderTrad= new PdfReader(archivoAnexoTrad);
                                Paginas = pdfReaderTrad.getNumberOfPages();
                                pdfReaderTrad.close();
                                forma.setnPaginasTrad(Paginas);
                                
                                List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                AnexosViewDto nvoAnexo = new AnexosViewDto();
                                //Descripción Original PDF
                                nvoAnexo=anexoDto;
                                //Traducción convertida a PDF
                                nvoAnexo.setArchivoTrad(archivoAnexoTrad);                                
                                nvoAnexo.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_DESCRIPCION);
                                nvoAnexo.setNombreTrad("tradDescripcion.pdf");
                                nvoAnexo.setTxtAnexoTrad(anexoDto.getTxtAnexoTrad());
                                nvoAnexo.setTradCargada(true);
                                //
                                nvoAnexo.setExtension("pdf");
                                nvoAnexo.setIdTramitePatente(id_tramite);
                                nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                anexoTexto.add(nvoAnexo);
                                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);
                          } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                         }                         
                      }
                    }else{
                        //1 Tanto la Descripción como la traducción son textos
                        if(anexoDto.getTxtAnexo()!= null && !anexoDto.getTxtAnexo().equals("")){ 
                           if(anexoDto.getTxtAnexoTrad()!= null && !anexoDto.getTxtAnexoTrad().equals("")){
                            try {
                                //Descripción Original convertida a PDF
                                byte[] archivoAnexo = generaDescripcionPDF(forma,anexoDto,false);
                                PdfReader pdfReader= new PdfReader(archivoAnexo);
                                Paginas = pdfReader.getNumberOfPages();
                                pdfReader.close();
                                forma.setnPaginas(Paginas);
                                //Traducción convertida a PDF
                                byte[] archivoAnexoTrad = generaDescripcionPDF(forma,anexoDto,true);
                                PdfReader pdfReaderTrad= new PdfReader(archivoAnexoTrad);
                                Paginas = pdfReaderTrad.getNumberOfPages();
                                pdfReaderTrad.close();
                                forma.setnPaginasTrad(Paginas);
                               
                                List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                AnexosViewDto nvoAnexo = new AnexosViewDto();
                                //Descripción Original convertida a PDF
                                nvoAnexo.setArchivoAnexo(archivoAnexo);                                
                                nvoAnexo.setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);
                                nvoAnexo.setNombreArchivo("descripcion.pdf");
                                nvoAnexo.setTxtAnexo(anexoDto.getTxtAnexo());
                                nvoAnexo.setCargado(true);
                                //Traducción convertida a PDF
                                nvoAnexo.setArchivoTrad(archivoAnexoTrad);                                
                                nvoAnexo.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_DESCRIPCION);
                                nvoAnexo.setNombreTrad("tradDescripcion.pdf");
                                nvoAnexo.setTxtAnexoTrad(anexoDto.getTxtAnexoTrad());
                                //
                                nvoAnexo.setTradCargada(true);
                                nvoAnexo.setExtension("pdf");
                                nvoAnexo.setIdTramitePatente(id_tramite);
                                nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                nvoAnexo.setIdAnexo(anexoDto.getIdAnexo());
                                anexoTexto.add(nvoAnexo);
                                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);
                            }
                                catch (IOException ex) {
                                java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                          }
                        }else{//2 La descripción es Texto y la Traducción es archivo PDF
                            try{
                                //Traducción Original PDF
                                ByteArrayInputStream Bya = new ByteArrayInputStream(anexoDto.getArchivoTrad());
                                InputStream PDF = Bya;
                                PdfReader pdfReaderTrad= new PdfReader(PDF);
                                Paginas = pdfReaderTrad.getNumberOfPages();
                                pdfReaderTrad.close();
                                forma.setnPaginasTrad(Paginas);
                                //Descripción convertida a PDF
                                byte[] archivoAnexo = generaDescripcionPDF(forma,anexoDto,false);
                                PdfReader pdfReader= new PdfReader(archivoAnexo);
                                Paginas = pdfReader.getNumberOfPages();
                                pdfReader.close();
                                forma.setnPaginas(Paginas);

                                List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                AnexosViewDto nvoAnexo = new AnexosViewDto();
                                //Traducción Original PDF
                                
                                nvoAnexo=anexoDto;
                                //Descripcion convertida a PDF
                                nvoAnexo.setArchivoAnexo(archivoAnexo);                                
                                nvoAnexo.setIdTipoanexo(Constantes.ANEXO_DESCRIPCION_PATENTE);
                                nvoAnexo.setNombreArchivo("descripcion.pdf");
                                nvoAnexo.setTxtAnexo(anexoDto.getTxtAnexo());
                                nvoAnexo.setCargado(true);
                                //
                                nvoAnexo.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_DESCRIPCION);
                                nvoAnexo.setExtension("pdf");
                                nvoAnexo.setIdTramitePatente(id_tramite);
                                nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                anexoTexto.add(nvoAnexo);
                                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto); 
                            } catch (IOException ex) {
                                java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                           }        
                        }       
                      }    
                    } 
                    
                }
            }
/////////// A N E X O      R E I V I N D I C A C I O N
            if (anexoDto.getIdTipoanexo().intValue()==Constantes.ANEXO_REIVINDICACIONES_PATENTE){
//                anexoEliminar.setIdTipoanexo(Constantes.ANEXO_REIVINDICACIONES_PATENTE);
//                anexoEliminar.setIdTramitePatente(id_tramite);
//                rduFlujosGeneralesRemote.deleteByTypeAnexo(anexoEliminar);
                if (anexoDto.getOtroIdioma()==1 || (anexoDto.getOtroIdioma()==2 && anexoDto.getTradCargada()==false && anexoDto.getTxtAnexoTrad()==null) ){    
                    if(anexoDto.getNombreArchivo()!= null && anexoDto.getTxtAnexo()== null){
//                        insertaPdfAnexos(anexoDto,id_tramite);       
                        try {
                            insertaPdfAnexos(anexoDto,id_tramite);   
                            ByteArrayInputStream Bya = new ByteArrayInputStream(anexoDto.getArchivoAnexo());
                            InputStream PDF = Bya;
                            PdfReader pdfReader= new PdfReader(PDF);
                            Paginas = pdfReader.getNumberOfPages();
                            pdfReader.close();
                            forma.setnPagMemyReiv(Paginas);
                        }
                            catch (IOException ex) {
                            java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        if(anexoDto.getTxtAnexo()!= null && !anexoDto.getTxtAnexo().equals("")){
                            try {
                                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                                GenerarReporte generarReporte = new GenerarReporte();
                                ReporteReivindicacion reivindicacionDto = new ReporteReivindicacion();
                                reivindicacionDto.setDescripcion(anexoDto.getTxtAnexo());
                                ByteArrayOutputStream byt = generarReporte.generarReivindicacionEnPdf(request.getRealPath("")
                                               + "/content/reportes/template_reivindicaciones.jasper",reivindicacionDto ,forma.getnPaginas());
                                byte[] archivoAnexo = byt.toByteArray();     
                                PdfReader pdfReaderTrad= new PdfReader(archivoAnexo);
                                Paginas = pdfReaderTrad.getNumberOfPages();
                                pdfReaderTrad.close();
                                forma.setnPagMemyReiv(Paginas);
                                
                                List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                AnexosViewDto nvoAnexo = new AnexosViewDto();
                                nvoAnexo.setArchivoAnexo(archivoAnexo);
                                nvoAnexo.setExtension("pdf");
                                nvoAnexo.setIdTipoanexo(Constantes.ANEXO_REIVINDICACIONES_PATENTE);
                                nvoAnexo.setNombreArchivo("reivindicaciones.pdf");
                                nvoAnexo.setIdTramitePatente(id_tramite);
                                nvoAnexo.setTxtAnexo(anexoDto.getTxtAnexo());
                                nvoAnexo.setCargado(true);
                                nvoAnexo.setIdAnexo(anexoDto.getIdAnexo());
                                nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                anexoTexto.add(nvoAnexo);
                                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);
                            } catch (IOException ex) {
                                java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                           }  
                      }    
                    } 
                }else{                    
                    if(anexoDto.getNombreArchivo()!= null && anexoDto.getTxtAnexo()== null) {
                      //4 Tanto la Reivindicación como la traducción son archivos PDF                         
                      if(anexoDto.getNombreTrad()!=null && anexoDto.getTxtAnexoTrad()==null){
                        anexoDto.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_REIVINDICACION);
//                        insertaPdfAnexos(anexoDto,id_tramite);    
                        try {
                            insertaPdfAnexos(anexoDto,id_tramite);   
                            ByteArrayInputStream Bya = new ByteArrayInputStream(anexoDto.getArchivoAnexo());
                            InputStream PDF = Bya;
                            PdfReader pdfReader= new PdfReader(PDF);
                            Paginas = pdfReader.getNumberOfPages();
                            pdfReader.close();
                            forma.setnPagMemyReiv(Paginas);
                            Bya = new ByteArrayInputStream(anexoDto.getArchivoTrad());
                            PDF = Bya;
                            pdfReader= new PdfReader(PDF);
                            Paginas = pdfReader.getNumberOfPages();
                            pdfReader.close();
                            forma.setnPagMemyReivTrad(Paginas);
                        }
                            catch (IOException ex) {
                            java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                        }
                      }else{//3 La descripción es archivo PDF y la Traducción es Texto
                          try { //Reivindicación Original PDF
                                ByteArrayInputStream Bya = new ByteArrayInputStream(anexoDto.getArchivoAnexo());
                                InputStream PDF = Bya;
                                PdfReader pdfReader= new PdfReader(PDF);
                                Paginas = pdfReader.getNumberOfPages();
                                pdfReader.close();
                                forma.setnPagMemyReiv(Paginas);
                                //Traducción convertida a PDF
                                byte[] archivoAnexoTrad = generaReivindicacionPDF(forma,anexoDto,true);
                                PdfReader pdfReaderTrad= new PdfReader(archivoAnexoTrad);
                                Paginas = pdfReaderTrad.getNumberOfPages();
                                pdfReaderTrad.close();
                                forma.setnPagMemyReivTrad(Paginas);
                                
                                List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                AnexosViewDto nvoAnexo = new AnexosViewDto();
                                //Descripción Original PDF
                                nvoAnexo=anexoDto;
                                //Traducción convertida a PDF
                                nvoAnexo.setArchivoTrad(archivoAnexoTrad);                                
                                nvoAnexo.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_REIVINDICACION);
                                nvoAnexo.setNombreTrad("tradReivindicacion.pdf");
                                nvoAnexo.setTxtAnexoTrad(anexoDto.getTxtAnexoTrad());
                                nvoAnexo.setTradCargada(true);
                                //
                                nvoAnexo.setExtension("pdf");
                                nvoAnexo.setIdTramitePatente(id_tramite);
                                nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                anexoTexto.add(nvoAnexo);
                                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);
                          } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                         }                         
                      }
                    }else{
                        //1 Tanto la Descripción como la traducción son textos
                        if(anexoDto.getTxtAnexo()!= null && !anexoDto.getTxtAnexo().equals("")){ 
                           if(anexoDto.getTxtAnexoTrad()!= null && !anexoDto.getTxtAnexoTrad().equals("")){
                            try {
                                //Descripción Original convertida a PDF
                                byte[] archivoAnexo = generaReivindicacionPDF(forma,anexoDto,false);
                                PdfReader pdfReader= new PdfReader(archivoAnexo);
                                Paginas = pdfReader.getNumberOfPages();
                                pdfReader.close();
                                forma.setnPagMemyReiv(Paginas);
                                //Traducción convertida a PDF                                
                                byte[] archivoAnexoTrad = generaReivindicacionPDF(forma,anexoDto,true);
                                PdfReader pdfReaderTrad= new PdfReader(archivoAnexoTrad);
                                Paginas = pdfReaderTrad.getNumberOfPages();
                                pdfReaderTrad.close();
                                forma.setnPagMemyReivTrad(Paginas);
                               
                                List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                AnexosViewDto nvoAnexo = new AnexosViewDto();
                                //Descripción Original convertida a PDF
                                nvoAnexo.setArchivoAnexo(archivoAnexo);                                
                                nvoAnexo.setIdTipoanexo(Constantes.ANEXO_REIVINDICACIONES_PATENTE);
                                nvoAnexo.setNombreArchivo("reivindicaciones.pdf");
                                nvoAnexo.setTxtAnexo(anexoDto.getTxtAnexo());
                                nvoAnexo.setCargado(true);
                                //Traducción convertida a PDF
                                nvoAnexo.setArchivoTrad(archivoAnexoTrad);                                
                                nvoAnexo.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_REIVINDICACION);
                                nvoAnexo.setNombreTrad("tradReivindicacion.pdf");
                                nvoAnexo.setTxtAnexoTrad(anexoDto.getTxtAnexoTrad());
                                //
                                nvoAnexo.setTradCargada(true);
                                nvoAnexo.setExtension("pdf");
                                nvoAnexo.setIdTramitePatente(id_tramite);
                                nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                nvoAnexo.setIdAnexo(anexoDto.getIdAnexo());                                
                                anexoTexto.add(nvoAnexo);
                                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);
                            }
                                catch (IOException ex) {
                                java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                          }
                        }else{//2 La reivindicación es Texto y la Traducción es archivo PDF
                            try{
                                //Traducción PDF
                                ByteArrayInputStream Bya = new ByteArrayInputStream(anexoDto.getArchivoTrad());
                                InputStream PDF = Bya;
                                PdfReader pdfReaderTrad= new PdfReader(PDF);
                                Paginas = pdfReaderTrad.getNumberOfPages();
                                pdfReaderTrad.close();
                                forma.setnPagMemyReivTrad(Paginas);
                                //Reivindicación convertida a PDF
                                byte[] archivoAnexo = generaReivindicacionPDF(forma,anexoDto,false);
                                PdfReader pdfReader= new PdfReader(archivoAnexo);
                                Paginas = pdfReader.getNumberOfPages();
                                pdfReader.close();
                                forma.setnPagMemyReiv(Paginas);

                                List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                AnexosViewDto nvoAnexo = new AnexosViewDto();
                                //Traducción Original PDF
                                nvoAnexo=anexoDto;
                                //Reivindicación convertida a PDF
                                nvoAnexo.setArchivoAnexo(archivoAnexo);                                
                                nvoAnexo.setIdTipoanexo(Constantes.ANEXO_REIVINDICACIONES_PATENTE);
                                nvoAnexo.setNombreArchivo("reivindicaciones.pdf");
                                nvoAnexo.setTxtAnexo(anexoDto.getTxtAnexo());
                                nvoAnexo.setCargado(true);
                                //
                                nvoAnexo.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_REIVINDICACION);
                                nvoAnexo.setExtension("pdf");
                                nvoAnexo.setIdTramitePatente(id_tramite);
                                nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                anexoTexto.add(nvoAnexo);
                                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto); 
                            } catch (IOException ex) {
                                java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                           }        
                        }       
                      }    
                    } 
                    
                }
            }
            
//            if (anexoDto.getIdTipoanexo().intValue()==16){
//
//                    anexoEliminar.setIdTipoanexo(Constantes.ANEXO_REIVINDICACIONES_PATENTE);
//                    anexoEliminar.setIdTramitePatente(id_tramite);
//                    rduFlujosGeneralesRemote.deleteByTypeAnexo(anexoEliminar);
//                    if(anexoDto.getNombreArchivo()!= null && anexoDto.getTxtAnexo()== null){
//                        insertaPdfAnexos(anexoDto,id_tramite);                    
//                    }else{
//                        if(anexoDto.getTxtAnexo()!=null){
//                                HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().
//                                                                getExternalContext().getRequest();
//                                GenerarReporte generarReporte = new GenerarReporte();
//                                ReporteReivindicacion reivindicacionDto = new ReporteReivindicacion();
//                                reivindicacionDto.setDescripcion(anexoDto.getTxtAnexo());
//                                ByteArrayOutputStream byt = generarReporte.generarReivindicacionEnPdf(request.getRealPath("")
//                                                            + "/content/reportes/template_reivindicaciones.jasper",reivindicacionDto ,Paginas);
//                                byte[] archivoAnexo = byt.toByteArray();
//                                List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
//                                AnexosViewDto nvoAnexo = new AnexosViewDto();
//                                nvoAnexo.setArchivoAnexo(archivoAnexo);
//                                nvoAnexo.setExtension("pdf");
//                                nvoAnexo.setIdTipoanexo(Constantes.ANEXO_REIVINDICACIONES_PATENTE);
//                                nvoAnexo.setIdTramitePatente(id_tramite);
//                                nvoAnexo.setNombreArchivo("reivindicaciones.pdf");
//                                nvoAnexo.setCargado(true);
//                                nvoAnexo.setTxtAnexo(anexoDto.getTxtAnexo());
//                                nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
//                                anexoTexto.add(nvoAnexo);
//                                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);                       
//                        }
//                    }
//            }
/////////// A N E X O      R E S U M E N            
            if (anexoDto.getIdTipoanexo().intValue()== Constantes.ANEXO_RESUMEN_MEMORIA){
                if (anexoDto.getOtroIdioma() == 1 || (anexoDto.getOtroIdioma() == 2 && anexoDto.getTradCargada() == false && anexoDto.getTxtAnexoTrad() == null)) {
                    if (anexoDto.getNombreArchivo() != null && anexoDto.getTxtAnexo() == null) {
                        insertaPdfAnexos(anexoDto, id_tramite);
                    } else {
                        if (anexoDto.getTxtAnexo() != null && !anexoDto.getTxtAnexo().equals("")) {
                            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                            GenerarReporte generarReporte = new GenerarReporte();
                            ReporteResumen resumenDto = new ReporteResumen();
                            resumenDto.setDescripcion(enumeraParrafos(anexoDto.getTxtAnexo()));
                            ByteArrayOutputStream byt = generarReporte.generarResumenEnPdf(request.getRealPath("")
                                    + "/content/reportes/template_documentoResumen.jasper", resumenDto, forma.getnPaginas() + forma.getnPagMemyReiv());
                            byte[] archivoAnexo = byt.toByteArray();
                            
                            List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                            AnexosViewDto nvoAnexo = new AnexosViewDto();
                            nvoAnexo.setArchivoAnexo(archivoAnexo);
                            nvoAnexo.setExtension("pdf");
                            nvoAnexo.setIdTipoanexo(Constantes.ANEXO_RESUMEN_MEMORIA);
                            nvoAnexo.setNombreArchivo("resumen.pdf");
                            nvoAnexo.setIdTramitePatente(id_tramite);
                            nvoAnexo.setTxtAnexo(anexoDto.getTxtAnexo());
                            nvoAnexo.setCargado(true);
                            nvoAnexo.setIdAnexo(anexoDto.getIdAnexo());
                            nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                            anexoTexto.add(nvoAnexo);
                            rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);
                        }
                    }
                } else {
                    if (anexoDto.getNombreArchivo() != null && anexoDto.getTxtAnexo() == null) {
                        //4 Tanto la Reivindicación como la traducción son archivos PDF                         
                        if (anexoDto.getNombreTrad() != null && anexoDto.getTxtAnexoTrad() == null) {
                            anexoDto.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_RESUMEN);
                            insertaPdfAnexos(anexoDto, id_tramite);
                        } else {//3 La descripción es archivo PDF y la Traducción es Texto
                            try { 
                                //Traducción convertida a PDF
                                byte[] archivoAnexoTrad = generaResumenPDF(forma, anexoDto, true);
                                List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                AnexosViewDto nvoAnexo = new AnexosViewDto();
                                //Descripción Original PDF
                                nvoAnexo = anexoDto;
                                //Traducción convertida a PDF
                                nvoAnexo.setArchivoTrad(archivoAnexoTrad);
                                nvoAnexo.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_RESUMEN);
                                nvoAnexo.setNombreTrad("tradResumen.pdf");
                                nvoAnexo.setTxtAnexoTrad(anexoDto.getTxtAnexoTrad());
                                nvoAnexo.setTradCargada(true);
                                //
                                nvoAnexo.setExtension("pdf");
                                nvoAnexo.setIdTramitePatente(id_tramite);
                                nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                anexoTexto.add(nvoAnexo);
                                rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);
                            } catch (IOException ex) {
                                java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else {
                        //1 Tanto la Descripción como la traducción son textos
                        if (anexoDto.getTxtAnexo() != null && !anexoDto.getTxtAnexo().equals("")) {
                            if (anexoDto.getTxtAnexoTrad() != null && !anexoDto.getTxtAnexoTrad().equals("")) {
                                try {
                                    //Resumen Original convertido a PDF
                                    byte[] archivoAnexo = generaResumenPDF(forma, anexoDto, false);                            
                                    byte[] archivoAnexoTrad = generaResumenPDF(forma, anexoDto, true);

                                    List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                    AnexosViewDto nvoAnexo = new AnexosViewDto();
                                    //Descripción Original convertida a PDF
                                    nvoAnexo.setArchivoAnexo(archivoAnexo);
                                    nvoAnexo.setIdTipoanexo(Constantes.ANEXO_RESUMEN_MEMORIA);
                                    nvoAnexo.setNombreArchivo("resumen.pdf");
                                    nvoAnexo.setTxtAnexo(anexoDto.getTxtAnexo());
                                    nvoAnexo.setCargado(true);
                                    //Traducción convertida a PDF
                                    nvoAnexo.setArchivoTrad(archivoAnexoTrad);
                                    nvoAnexo.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_RESUMEN);
                                    nvoAnexo.setNombreTrad("tradResumen.pdf");
                                    nvoAnexo.setTxtAnexoTrad(anexoDto.getTxtAnexoTrad());
                                    //
                                    nvoAnexo.setTradCargada(true);
                                    nvoAnexo.setExtension("pdf");
                                    nvoAnexo.setIdTramitePatente(id_tramite);
                                    nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                    nvoAnexo.setIdAnexo(anexoDto.getIdAnexo());
                                    anexoTexto.add(nvoAnexo);
                                    rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);
                                } catch (IOException ex) {
                                    java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {//2 El resumen es Texto y la Traducción es archivo PDF
                                try {
                                    byte[] archivoAnexo = generaResumenPDF(forma, anexoDto, false);
                                    List<AnexosViewDto> anexoTexto = new ArrayList<AnexosViewDto>();
                                    AnexosViewDto nvoAnexo = new AnexosViewDto();
                                    //Traducción Original PDF
                                    nvoAnexo = anexoDto;
                                    //Reivindicación convertida a PDF
                                    nvoAnexo.setArchivoAnexo(archivoAnexo);
                                    nvoAnexo.setIdTipoanexo(Constantes.ANEXO_RESUMEN_MEMORIA);
                                    nvoAnexo.setNombreArchivo("Resumen.pdf");
                                    nvoAnexo.setTxtAnexo(anexoDto.getTxtAnexo());
                                    nvoAnexo.setCargado(true);
                                    //
                                    nvoAnexo.setIdTipoanexoTrad(Constantes.ANEXO_TRADUCCION_RESUMEN);
                                    nvoAnexo.setExtension("pdf");
                                    nvoAnexo.setIdTramitePatente(id_tramite);
                                    nvoAnexo.setOtroIdioma(anexoDto.getOtroIdioma());
                                    anexoTexto.add(nvoAnexo);
                                    rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexoTexto);
                                } catch (IOException ex) {
                                    java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }

                }
            }
        }
    }
   
      public void insertarPdfAnexosApoderado(PatentesDisenoIndustrialMB forma, Long id_tramite){
            AnexosViewDto anexoDto= new AnexosViewDto();
            Anexos anexoEliminar = new Anexos();
            System.out.println("Insertando los anexos de Apoderados"); 
//            }+
            for (int i=0; i< forma.getLstAnexosApoderado().size();i++){
                anexoDto=forma.getLstAnexosApoderado().get(i);
                if(anexoDto.getNombreArchivo()!= null){                                                    
                    System.out.println("Se inserta el anexo " + anexoDto.getDescripcion());       
                    insertaPdfAnexosApoderado(anexoDto,id_tramite);   
                    }
            }
      }
   public String enumeraParrafos(String Texto)
   {
    String[] parrafos =Texto.split("\n");

    String num;
    int nParrafo=0;
    StringBuilder textoEnumerado=new StringBuilder();
    for (int p=0;p<parrafos.length; p++)
    {       
        if (!parrafos[p].equals("\r")){
            nParrafo++;
            num = String.format("%03d", nParrafo);
            textoEnumerado.append("["+ num +"] " + parrafos[p]);
        }else
        {
            textoEnumerado.append(parrafos[p]);
        }
    }
    return textoEnumerado.toString();
   }
    
    public byte[] generaDescripcionPDF(PatentesDisenoIndustrialMB forma,AnexosViewDto anexoDto, Boolean esTraduccion) throws IOException{
        AnexosViewDto anexoDescripcion = forma.getForma().getAnexoDescripcionMe();
        int Paginas=0;
        
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        GenerarReporte generarReporte = new GenerarReporte();
        ReporteDescripcionDto descripcionDto = new ReporteDescripcionDto();
        if (esTraduccion){
            descripcionDto.setPreambulo(enumeraParrafos(anexoDto.getTxtAnexoTrad()));
        }else{
            descripcionDto.setPreambulo(enumeraParrafos(anexoDto.getTxtAnexo()));
        }
//        if (esTraduccion){
//            descripcionDto.setPreambulo(anexoDto.getTxtAnexoTrad());
//        }else{
//            descripcionDto.setPreambulo(anexoDto.getTxtAnexo());
//        }
        descripcionDto.setTextoAdicional(forma.getTramitePat().getTextoAdicional());
        descripcionDto.setDesReivindicaciones("");
        String subTipo = "";
        for(CatSubtiposolicitud Subtiposolicitud : forma.getListSubtiposSol()){                        
            if(Subtiposolicitud.getIdSubtiposolicitud().intValue() == forma.getTramitePat().getSubTipoSol().getIdSubtiposolicitud().intValue())
                subTipo = Subtiposolicitud.getDescripcion();
        }
//        if (esTraduccion){
//            descripcionDto.setTituloDescripcion(subTipo.toUpperCase()+" "+forma.getTramitePat().getInvencion().toUpperCase() + " (Traducción)" );
//        }else{
        if (forma.getTipoSol().getIdTiposolicitud().intValue()== Constantes.TIPO_SOL_MODELO_UTILIDAD.intValue()){
            descripcionDto.setTituloDescripcion(forma.getTramitePat().getInvencion().toUpperCase());
        }else{
            descripcionDto.setTituloDescripcion(subTipo.toUpperCase()+" "+forma.getTramitePat().getInvencion().toUpperCase());
        }
//            descripcionDto.setTituloDescripcion(subTipo.toUpperCase()+" "+forma.getTramitePat().getInvencion().toUpperCase());
//        }
        //Leer el archivo y validar el numero de hojas que tiene 
        if (anexoDescripcion != null){
        ByteArrayInputStream Bya = new ByteArrayInputStream(forma.getForma().getAnexoDescripcionMe().getArchivoAnexo());
        InputStream PDF = Bya;
        PdfReader pdfReader= new PdfReader(PDF);
        Paginas = pdfReader.getNumberOfPages();
        pdfReader.close();
        }else{
            Paginas=0;
        }
        descripcionDto.setPaginas(Paginas);
        ByteArrayOutputStream byt = generarReporte.generarDescripcionEnPdf(request.getRealPath("")
        + "/content/reportes/descripcionTexto.jasper", descripcionDto);
        byte[] archivoAnexo = byt.toByteArray();
        return archivoAnexo;                        
    }
    public byte[] generaReivindicacionPDF(PatentesDisenoIndustrialMB forma,AnexosViewDto anexoDto, Boolean esTraduccion) throws IOException{
        int Paginas=0;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        GenerarReporte generarReporte = new GenerarReporte();
        ReporteReivindicacion reivindicacionDto = new ReporteReivindicacion();
         if (esTraduccion){
            reivindicacionDto.setDescripcion(anexoDto.getTxtAnexoTrad());
            Paginas=forma.getnPaginasTrad();
        }else{
            reivindicacionDto.setDescripcion(anexoDto.getTxtAnexo());
            Paginas=forma.getnPaginas();
        }
        ByteArrayOutputStream byt = generarReporte.generarReivindicacionEnPdf(request.getRealPath("")
                    + "/content/reportes/template_reivindicaciones.jasper",reivindicacionDto ,Paginas);
        byte[] archivoAnexo = byt.toByteArray();
        return archivoAnexo;                        
    }
    
    public byte[] generaResumenPDF(PatentesDisenoIndustrialMB forma,AnexosViewDto anexoDto, Boolean esTraduccion) throws IOException{
        int Paginas=0;
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        GenerarReporte generarReporte = new GenerarReporte();
        ReporteResumen resumenDto = new ReporteResumen();
        
        if (esTraduccion){
            resumenDto.setDescripcion(enumeraParrafos(anexoDto.getTxtAnexoTrad()));
            Paginas=forma.getnPaginasTrad()+ forma.getnPagMemyReivTrad();
        }else{
            resumenDto.setDescripcion(enumeraParrafos(anexoDto.getTxtAnexo()));
            Paginas=forma.getnPaginas()+ forma.getnPagMemyReiv();
        }
//         if (esTraduccion){
//            resumenDto.setDescripcion(anexoDto.getTxtAnexoTrad());
//            Paginas=forma.getnPaginasTrad()+ forma.getnPagMemyReivTrad();
//        }else{
//            resumenDto.setDescripcion(anexoDto.getTxtAnexo());
//            Paginas=forma.getnPaginas()+ forma.getnPagMemyReiv();
//        }
        ByteArrayOutputStream byt = generarReporte.generarResumenEnPdf(request.getRealPath("")
                    + "/content/reportes/template_documentoResumen.jasper",resumenDto ,Paginas);
        byte[] archivoAnexo = byt.toByteArray();
        return archivoAnexo;                        
    }
    
    public void insertaPdfAnexos(AnexosViewDto anexoDto,Long id_tramite){
        AnexosViewDto anexoMemoria = new AnexosViewDto();
        anexoMemoria = anexoDto;
        anexoMemoria.setCargado(true);
        anexoMemoria.setIdTramitePatente(id_tramite);
        List<AnexosViewDto> anexos = new ArrayList<>();
        anexos.add(anexoMemoria);
        //Se inserta el anexo DEL RESUMEN
        rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexos);    
    }
    
    public void insertaPdfAnexosApoderado(AnexosViewDto anexoDto,Long id_tramite){
        AnexosViewDto anexoApoderado = new AnexosViewDto();
        anexoApoderado = anexoDto;
        anexoApoderado.setCargado(true);
        anexoApoderado.setIdTramitePatente(id_tramite);
        List<AnexosViewDto> anexos = new ArrayList<>();
        anexos.add(anexoApoderado);
        //Se inserta el anexo DEL RESUMEN
        rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexos);    
    }
    public void borrarPdfDivulgacionPrevia(PatentesDisenoIndustrialMB forma, Long id_tramite){
        
        
            Anexos anexoEliminar = new Anexos();
            anexoEliminar.setIdTipoanexo(Constantes.ANEXO_DIV_PREVIA_PAT);
            anexoEliminar.setIdTramitePatente(id_tramite);
            
            rduFlujosGeneralesRemote.deleteByTypeAnexo(anexoEliminar);
        
    }
    
       
    
    public void insertarPdfDivulgacionPrevia(PatentesDisenoIndustrialMB forma, Long id_tramite){
        
        
       
        
        
         AnexosViewDto anexoArcDivPre = forma.getAnexoArcDivPre();
         
         if (anexoArcDivPre != null && anexoArcDivPre.getNombreArchivo()!=null) { 
        Anexos anexoEliminar = new Anexos();
        anexoEliminar.setIdTipoanexo(Constantes.ANEXO_DIV_PREVIA_PAT);
        anexoEliminar.setIdTramitePatente(id_tramite);

        rduFlujosGeneralesRemote.deleteByTypeAnexo(anexoEliminar);
        
         }
        
        if (anexoArcDivPre != null && anexoArcDivPre.getNombreArchivo()!=null) {
                    //anexoDescripcion ya cuenta con las demas propiedades con valor, desde que se carga el archivo.
                    anexoArcDivPre.setCargado(true);
                    anexoArcDivPre.setIdTramitePatente(id_tramite);
                    List<AnexosViewDto> anexos = new ArrayList<>();
                    anexos.add(anexoArcDivPre);
                    
                    //Se inserta el anexo descripcion
                    rduFlujosGeneralesRemote.insertarAnexosDtoPatente(anexos);
                }
    
    }
    
    
    public TramitePatente selectTramite(Long idTramitePatente) {
        try {
            return this.rduPatentesBeanRemote.selectTramite(idTramitePatente);
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }
    }

    public boolean recuperarTramite(PatentesDisenoIndustrialMB forma) {
        boolean resul = false;

        try {

            TramitePatente tramite = new TramitePatente();

            tramite.setIdTramitePatente(forma.getIdTramite());

            //recupera el tramite
            tramite = rduPatentesBeanRemote.obtenerTramitePatenteById(tramite.getIdTramitePatente());
            //Para los tramites anteriores se quita el texto de preambulo
            tramite.setPreambulo("");
            if (tramite.getHayMatBiologico() != null && tramite.getHayMatBiologico().intValue() == 1) {
                tramite.setMaterial_biologico(true);
            }

            if (tramite.getHayPubAnticipada() != null && tramite.getHayPubAnticipada().intValue() == 1) {
                tramite.setPub_anticipada(true);
            }

            if (tramite.getFaseSolPCT() != null && tramite.getFaseSolPCT().length() != 0) {
                if (tramite.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_PATENTE.longValue() || tramite.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_MODELO_UTILIDAD.longValue()) {
                    if (tramite.getFaseSolPCT().endsWith("1") == true) {
                        tramite.setFaseInternacional(new Integer(1));
                        if (tramite.getNumPubPCT() != null && tramite.getNumPubPCT().length() == 14) {
                            forma.setPart1NumPubPct(tramite.getNumPubPCT().replace(" ", "").substring(2, 6));
                            forma.setPart2NumPubPct(tramite.getNumPubPCT().replace(" ", "").substring(7));
                        }
                        if (tramite.getNumSolPCT() != null && tramite.getNumSolPCT().length() == 17) {
                            forma.setPart1NumeroSolPct(tramite.getNumSolPCT().substring(4, 10));
                            forma.setPart2NumeroSolPct(tramite.getNumSolPCT().substring(11));
                        }

                        forma.setTipoPublicacionPct(new TipoPublicacionPct((tramite.getTipoPubPCT() != null && tramite.getTipoPubPCT().length() == 2) ? new Integer(tramite.getTipoPubPCT().substring(1)) : null, null));
                    } else {
                        tramite.setFaseInternacional(new Integer(2));
                        if (tramite.getNumPubPCT() != null && tramite.getNumPubPCT().length() == 14) {
                            forma.setPart1NumPubPct(tramite.getNumPubPCT().replace(" ", "").substring(2, 6));
                            forma.setPart2NumPubPct(tramite.getNumPubPCT().replace(" ", "").substring(7));
                        }
                        if (tramite.getNumSolPCT() != null && tramite.getNumSolPCT().length() == 17) {
                            forma.setPart1NumeroSolPct(tramite.getNumSolPCT().substring(4, 10));
                            forma.setPart2NumeroSolPct(tramite.getNumSolPCT().substring(11));
                        }
                        forma.setTipoPublicacionPct(new TipoPublicacionPct((tramite.getTipoPubPCT() != null && tramite.getTipoPubPCT().length() == 2) ? new Integer(tramite.getTipoPubPCT().substring(1)) : null, null));
                    }
                }
            }
            
            forma.setTramitePat(tramite);
            
           if (tramite.getDomicilioObj()!=null){
                IncludeDomicilio domicilioContacto= forma.getDomicilioContacto(); //new IncludeDomicilio();
                DomicilioDto domicilio = new DomicilioDto();
                Pais paisDomPersona = new Pais();
                paisDomPersona.setIdPais(tramite.getDomicilioObj().getIdPais());
                paisDomPersona.setNombre(Constantes.NOMBRE_PAIS);
                BeanUtils.copyProperties(tramite.getDomicilioObj(), domicilio);
                domicilioContacto.setDomicilio(domicilio);
                domicilioContacto.getDomicilio().setIdPais(tramite.getDomicilioObj().getIdPais());
                domicilioContacto.setPaisCombo(paisDomPersona);
                forma.getTramitePat().getDomicilioObj().setPais(paisDomPersona);
                //Buscar la descripcion de la entidad federativa
                for (int e = 0; e < forma.getEntidadesFederativas().size(); e++) {
                    EntidadFederativa eleLista = (EntidadFederativa) forma.getEntidadesFederativas().get(e);
                    if (eleLista.getIdEntidadFederativa().toString().equals(tramite.getDomicilioObj().getIdEntidad())) {
                        domicilioContacto.getDomicilio().setNombreEntidad(eleLista.getNombre());
                        domicilioContacto.setEntidadFederativaCombo(eleLista);
                        forma.getTramitePat().getDomicilioObj().setEntidad(eleLista);
                        break;
                    }
                }
                domicilioContacto.getDomicilio().setIdEntidad(tramite.getDomicilioObj().getIdEntidad());                
                domicilioContacto.setDatosContacto(tramite.getDatosContacto());
                forma.setDomicilioContacto(domicilioContacto);
                
                //forma.getDomicilioContacto().set
            }
           
            forma.getTramitePat().getDomicilioObj();
            
            if(tramite.getPreambulo()!= null && !tramite.getPreambulo().isEmpty() && tramite.getPreambulo().length()>0){
                forma.getForma().setDescText(Constantes.INIT.toString());
                forma.getForma().setRenderedDescText(false);
            }
            
            if(tramite.getTextoAdicional() != null && !tramite.getTextoAdicional().isEmpty() && tramite.getTextoAdicional().length()>0){
                forma.getForma().setResuText(Constantes.INIT.toString());
                forma.getForma().setRenderedResuText(false);
            }
            
            forma.setListaSolicitantes(tramite.getSolicitantes());
            forma.setListaInventores(tramite.getInventores());
            forma.setListaPrioridades(tramite.getPrioridades());


            recuperarSolicitantes(forma);
            recuperarInventores(forma);
            recuperarPrioridades(forma);


            forma.setApoderadoModel(new PersonaDM(tramite.getApoderados()));
            forma.setSolicitanteModel(new PersonaDM(forma.getListaSolicitantes()));
            for (Persona persona : tramite.getApoderados()) {
                if (persona.getPrincipal() != null) {
                    forma.setSelectedApoderado(persona);
                    break;
                }
            }
            
            forma.getForma().setIdiomaSelected(tramite.getIdIdiomaDescripcion());
            forma.getForma().setIdiomaDescripcion(tramite.getIdIdiomaDescripcion());
            forma.getForma().setIdiomaReivindicacion(tramite.getIdiomaReivindicacion());
            forma.getForma().setIdiomaResumen(tramite.getIdiomaResumen());
            
            forma.getForma().setRenderedAviso(false);
            //tipo de la solicitud
            forma.setTipoSol(tramite.getTipoSol());

            for (Persona persona : tramite.getSolicitantes()) {
                if (persona.getPrincipal() != null) {
                    forma.setSelectedSolicitante(persona);
                    break;
                }
            }
            
            if(!tramite.getReivindicaciones().isEmpty())
                forma.getForma().setIdiomaSelectReivin(tramite.getReivindicaciones().get(Constantes.FIRST).getIdIdiomas().toString());

            resul = true;

        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: recuperar tramite");
        }

        return resul;
    }

    public boolean actualizaTramite(PatentesDisenoIndustrialMB forma) throws Exception {
        System.out.println("actualizando");

        //copiar propiedades en la entidad de tramite
        TramitePatente tramite = new TramitePatente();
        tramite = forma.getTramitePat();
        boolean resul = false;
        tramite.setIdTramitePatente(forma.getTramitePat().getIdTramitePatente());

        tramite.setIdSubtipoSolicitud(forma.getTramitePat().getSubTipoSol().getIdSubtiposolicitud());
        tramite.setIndActivo(new Short("1"));

        tramite.setIdEstatus(Constantes.INCOMPLETO);


        tramite.setFechaEstatusActual(new Date());
        tramite.setFechaDivPrevia(forma.getTramitePat().getFechaDivPrevia());
        tramite.setFechaDivulgacion(forma.getTramitePat().getFechaDivulgacion());
        tramite.setExpDivisional(forma.getTramitePat().getExpDivisional());


        if (forma.getTramitePat().isMaterial_biologico() == true) {
            tramite.setHayMatBiologico(new Integer(1));
        } else {
            tramite.setHayMatBiologico(new Integer(0));
        }

        if (forma.getTramitePat().isPub_anticipada() == true) {
            tramite.setHayPubAnticipada(new Integer(1));
        } else {
            tramite.setHayPubAnticipada(new Integer(0));
        }

        tramite.setNumSolPCT(forma.getTramitePat().getNumSolPCT());
        tramite.setFechaSolPCT(forma.getTramitePat().getFechaSolPCT());
        tramite.setFaseSolPCT(forma.getTramitePat().getFaseSolPCT());
        tramite.setFechaPubPCT(forma.getTramitePat().getFechaPubPCT());
        tramite.setNumPubPCT(forma.getTramitePat().getNumPubPCT());
        tramite.setTipoPubPCT(forma.getTramitePat().getTipoPubPCT());
        if (tramite.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_PATENTE.longValue() || 
            tramite.getSubTipoSol().getIdSubtiposolicitud().longValue() == Constantes.SUBTIPO_PCT_MODELO_UTILIDAD.longValue()) {
            if (forma.getForma().getCapPCT().equals("1")){
                tramite.setFaseSolPCT("Capitulo 1");
                tramite.setFaseInternacional(new Integer(1));
            }else{
                tramite.setFaseSolPCT("Capitulo 2");
                tramite.setFaseInternacional(new Integer(2));
            }
        }
        tramite.setInvencion(forma.getTramitePat().getInvencion());
        if (forma.getTramitePat().getObservaciones() != null) {
            tramite.setObservaciones(forma.getTramitePat().getObservaciones());
        }

        if (forma.getTramitePat().getResumen() != null) {
            tramite.setResumen(forma.getTramitePat().getResumen());
        }

        tramite.setIdUsuarioCaptura(forma.getTramitePat().getIdUsuarioCaptura());
        tramite.setIdUsuarioFirmante(forma.getTramitePat().getIdUsuarioFirmante());

        tramite.setSolicitantes(forma.getListaSolicitantes());
        tramite.setInventores(forma.getListaInventores());
        tramite.setPrioridades(forma.getListaPrioridades());
        
        tramite.setPreambulo(forma.getTramitePat().getPreambulo());
        tramite.setDescripcionVistas(forma.getTramitePat().getDescripcionVistas());
        tramite.setTextoAdicional(forma.getTramitePat().getTextoAdicional());
        tramite.setIdIdiomaDescripcion(forma.getForma().getIdiomaDescripcion());
        
        tramite.setIdiomaReivindicacion(forma.getForma().getIdiomaReivindicacion());
        tramite.setIdiomaResumen(forma.getForma().getIdiomaResumen());
        
        //hasta aqui voy no guarda bien los datos
        if(!tramite.getReivindicaciones().isEmpty() && forma.getReivindicacion() != null 
                && forma.getForma().getIdiomaReivindicacion() != null)
            tramite.getReivindicaciones().get(Constantes.FIRST).setIdIdiomas(
                    Integer.parseInt(forma.getForma().getIdiomaReivindicacion()));
//        //Tipo de Solicitud
//        forma.setTipoSol(tramite.getTipoSol());
        resul = rduPatentesBeanRemote.updateTramitePatenteByPrimaryKey(tramite);
        
        //Si adjunto un archivo con una descripcion debera almacenarse.
        insertarPdfAnexosMemoriaTecnica(forma, tramite.getIdTramitePatente());
        insertarPdfAnexosApoderado(forma,tramite.getIdTramitePatente());
        //insertarPdfDescripcion(forma, tramite.getIdTramitePatente());
        insertarPdfDivulgacionPrevia(forma, tramite.getIdTramitePatente());

        resul = true;

        System.out.println("actualizado...................."
                + resul + "..." + tramite.getIdTramitePatente());
        return resul;


    }

    private void recuperarSolicitantes(PatentesDisenoIndustrialMB forma) {
        List<Persona> listaSolicitantes = new ArrayList<Persona>();
        listaSolicitantes = forma.getListaSolicitantes();

        if (listaSolicitantes != null) {
            for (int w = 0; w < listaSolicitantes.size(); w++) {

                Persona perSol = (Persona) listaSolicitantes.get(w);


                Pais paisDomPersona = new Pais();
                paisDomPersona.setIdPais(perSol.getDomicilioObj().getIdPais().longValue());
                EntidadFederativa entidadDomPersona = new EntidadFederativa();

                if (perSol.getDatosContacto() == null) {
                    perSol.setDatosContacto(new Datoscontacto());
                }


                if (perSol.getDomicilioObj().getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                    paisDomPersona.setNombre(Constantes.NOMBRE_PAIS);
                    if (perSol.getDomicilioObj().getIdEntidad() != null && !perSol.getDomicilioObj().getIdEntidad().equals("0")) {
                        entidadDomPersona.setIdEntidadFederativa(new Integer(perSol.getDomicilioObj().getIdEntidad()));
                        entidadDomPersona.setIdPais(perSol.getDomicilioObj().getIdPais().longValue());
                        //Buscar la descripcion de la entidad federativa
                        for (int e = 0; e < forma.getEntidadesFederativas().size(); e++) {
                            EntidadFederativa eleLista = (EntidadFederativa) forma.getEntidadesFederativas().get(e);
                            if (eleLista.getIdEntidadFederativa().intValue() == entidadDomPersona.getIdEntidadFederativa().intValue()) {
                                entidadDomPersona.setNombre(eleLista.getNombre());
                                break;
                            }
                        }

                    }

                } else {
                    for (int l = 0; l < forma.getPaises().size(); l++) {
                        Pais eleLista = (Pais) forma.getPaises().get(l);
                        if (eleLista.getIdPais().intValue() == perSol.getDomicilioObj().getIdPais().intValue()) {
                            paisDomPersona.setNombre(eleLista.getNombre());
                            break;
                        }
                    }
                    entidadDomPersona.setNombre(perSol.getDomicilioObj().getIdEntidad());
                }
                perSol.getDomicilioObj().setPais(paisDomPersona);
                perSol.getDomicilioObj().setEntidad(entidadDomPersona);

                CatTipoSolicitante tipoSol = new CatTipoSolicitante();
                tipoSol.setIdTipoSolicitante(perSol.getIdTipoSolicitante());
                for (int j = 0; j < forma.getTiposSolicitantes().size(); j++) {
                    CatTipoSolicitante elementoLista = (CatTipoSolicitante) forma.getTiposSolicitantes().get(j);
                    if (elementoLista.getIdTipoSolicitante().longValue() == tipoSol.getIdTipoSolicitante().longValue()) {
                        tipoSol = elementoLista;
                    }
                }
                perSol.setTipoSolicitante(tipoSol);

                Pais nacionalidad = new Pais();
                nacionalidad.setIdPais(perSol.getIdNacionalidad());
                for (int l = 0; l < forma.getPaises().size(); l++) {
                    Pais eleLista = (Pais) forma.getPaises().get(l);
                    if (eleLista.getIdPais().intValue() == nacionalidad.getIdPais().intValue()) {
                        perSol.setNacionalidad(eleLista);
                        break;
                    }
                }


                CatTipopersona tipoPersona = new CatTipopersona();
                tipoPersona.setIdTipopersona(perSol.getIdTipopersona());
                for (int j = 0; j < forma.getTiposPersona().size(); j++) {
                    CatTipopersona elementoLista = (CatTipopersona) forma.getTiposPersona().get(j);
                    if (elementoLista.getIdTipopersona().longValue() == tipoPersona.getIdTipopersona().longValue()) {
                        tipoPersona = elementoLista;
                    }
                }
                perSol.setTipoPersona(tipoPersona);

                if (perSol.getDescuento() != null) {
                    if (perSol.getDescuento().intValue() == 1) {
                        perSol.setAplicarDescuento(true);
                    }
                }

                if (perSol.getInventor() != null && perSol.getInventor().intValue() == 1) {
                    //buscar copia en los inventores
                    for (int l = 0; l < forma.getListaInventores().size(); l++) {
                        Persona eleInvetorLista = (Persona) forma.getListaInventores().get(l);
                        if (perSol.getNombrecompleto().equals(eleInvetorLista.getNombrecompleto())) {
                            if (perSol.getDomicilioObj().getCalle().equals(eleInvetorLista.getDomicilioObj().getCalle())) {
                                forma.getListaInventores().remove(eleInvetorLista);
                                forma.getListaInventores().add(l, perSol);
                                break;
                            }
                        }
                    }
                }


            }
        }

        forma.setListaSolicitantes(listaSolicitantes);
    }

    private void recuperarInventores(PatentesDisenoIndustrialMB forma) {
        List<Persona> listaInventores = new ArrayList<Persona>();
        listaInventores = forma.getListaInventores();
        if (listaInventores != null) {
            for (int w = 0; w < listaInventores.size(); w++) {

                Persona perSol = (Persona) listaInventores.get(w);


                Pais paisDomPersona = new Pais();
                paisDomPersona.setIdPais(perSol.getDomicilioObj().getIdPais().longValue());
                EntidadFederativa entidadDomPersona = new EntidadFederativa();

                if (perSol.getDatosContacto() == null) {
                    perSol.setDatosContacto(new Datoscontacto());
                }

                if (perSol.getDomicilioObj().getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                    paisDomPersona.setNombre(Constantes.NOMBRE_PAIS);
                    if (perSol.getDomicilioObj().getIdEntidad() != null && !perSol.getDomicilioObj().getIdEntidad().equals("0")) {
                        entidadDomPersona.setIdEntidadFederativa(new Integer(perSol.getDomicilioObj().getIdEntidad()));
                        entidadDomPersona.setIdPais(perSol.getDomicilioObj().getIdPais().longValue());
                        //Buscar la descripcion de la entidad federativa
                        for (int e = 0; e < forma.getEntidadesFederativas().size(); e++) {
                            EntidadFederativa eleLista = (EntidadFederativa) forma.getEntidadesFederativas().get(e);
                            if (eleLista.getIdEntidadFederativa().intValue() == entidadDomPersona.getIdEntidadFederativa().intValue()) {
                                entidadDomPersona.setNombre(eleLista.getNombre());
                                break;
                            }
                        }

                    }

                } else {
                    for (int l = 0; l < forma.getPaises().size(); l++) {
                        Pais eleLista = (Pais) forma.getPaises().get(l);
                        if (eleLista.getIdPais().intValue() == perSol.getDomicilioObj().getIdPais().intValue()) {
                            paisDomPersona.setNombre(eleLista.getNombre());
                            break;
                        }
                    }
                    entidadDomPersona.setNombre(perSol.getDomicilioObj().getIdEntidad());
                }
                perSol.getDomicilioObj().setPais(paisDomPersona);
                perSol.getDomicilioObj().setEntidad(entidadDomPersona);

                Pais nacionalidad = new Pais();
                nacionalidad.setIdPais(perSol.getIdNacionalidad());
                for (int l = 0; l < forma.getPaises().size(); l++) {
                    Pais eleLista = (Pais) forma.getPaises().get(l);
                    if (eleLista.getIdPais().intValue() == nacionalidad.getIdPais().intValue()) {
                        perSol.setNacionalidad(eleLista);
                        break;
                    }
                }

                CatTipopersona tipoPersona = new CatTipopersona();
                tipoPersona.setIdTipopersona(perSol.getIdTipopersona());
                for (int j = 0; j < forma.getTiposPersonaInventor().size(); j++) {
                    CatTipopersona elementoLista = (CatTipopersona) forma.getTiposPersonaInventor().get(j);
                    if (elementoLista.getIdTipopersona().longValue() == tipoPersona.getIdTipopersona().longValue()) {
                        tipoPersona = elementoLista;
                    }
                }
                perSol.setTipoPersona(tipoPersona);
            }
        }

        forma.setListaInventores(listaInventores);
    }

    private void recuperarPrioridades(PatentesDisenoIndustrialMB forma) {

        List<Prioridad> listaPrioridades = new ArrayList<Prioridad>();
        listaPrioridades = forma.getListaPrioridades();
        if (listaPrioridades != null) {
            for (int w = 0; w < listaPrioridades.size(); w++) {

                Prioridad prior = (Prioridad) listaPrioridades.get(w);
                prior.setFechaPresentacion(Util.formatearFecha(prior.getFechaPresentacionExt(), "dd/MM/yyyy"));


                for (int l = 0; l < forma.getPaisesPrioridad().size(); l++) {
                    Pais eleLista = (Pais) forma.getPaisesPrioridad().get(l);
                    if (eleLista.getIdPais().intValue() == prior.getIdPais().intValue()) {
                        prior.setNombrePais(eleLista.getNombre());
                        break;
                    }
                }
            }
        }

        forma.setListaPrioridades(listaPrioridades);
    }

    public TramitePatente obtenerTramitePatenteById(Long idTramite) throws Exception {
        return rduPatentesBeanRemote.obtenerTramitePatenteById(idTramite);
    }
    
    public TramitePatente obtenerTramitePromocioneById(Long idTramite) throws Exception {
        return rduPatentesBeanRemote.obtenerTramitePromocioneById(idTramite);
    }

    public int updatePatenteSigned(Long idTramite) {
        TramitePatente patente;
        try {
            patente = this.rduPatentesBeanRemote.obtenerTramitePatenteById(idTramite);
            if (patente.getIdEstatus().longValue() == Constantes.TRAMITE_ESTATUS_PAGADA.longValue()) {
                patente.setIdEstatus(Constantes.TRAMITE_ESTATUS_RECIBIDA);
                return this.rduPatentesBeanRemote.updateTramitePatenteSigned(patente);
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(PatentesViewServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }

    public List<Solicitud> obtenerExpedienteDivisional(Solicitud exp) {
        List<Solicitud> solSagpat = new ArrayList<Solicitud>();

        exp = rduPatentesBeanRemote.getExpDivisional(exp);
        if (exp != null) {
            solSagpat.add(exp);
        }
        return solSagpat;
    }

    public List<Persona> selectSolicitanteTramitePatente(Long idPatente) {
        return this.rduPatentesBeanRemote.selectSolicitanteTramitePatente(idPatente);

    }

    public DatosSolicitudPct consultarDatosPCt(String idSolicitudPct) {
        return this.rduPatentesBeanRemote.consultarDatosPCt(idSolicitudPct);
    }
    
    public List<DatosSolicitudPctMU> consultarDatosPCtMU(String idSolicitudPct) {
        return this.rduPatentesBeanRemote.consultarDatosPCtMU(idSolicitudPct);
    }

//    public String getCatalogoJms(String nombreCatalogo){
//     try {
//            return this.rduPatentesBeanRemote.getCatalogoJms(nombreCatalogo);
//        } catch (Exception e) {
//            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
//            return null;
//        }
//      }
//    public String getCatTipoPromocionJms(String nombreCatalogo){
//        try {
//            return this.rduPatentesBeanRemote.getCatTipoPromocionJms(nombreCatalogo);
//        } catch (Exception e) {
//            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
//            return null;
//        }
//    }
//    public String getTipoPromByOficioJms(String idOficio){
//        try {
//            return this.rduPatentesBeanRemote.getTipoPromByOficioJms(idOficio);
//        } catch (Exception e) {
//            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
//            return null;
//        }
//    }
//    
//    public List<CatAreaPromPatentes> getCatalogo(String cadenaXML){
//      try{  
//        return this.rduPatentesBeanRemote.getCatalogo(cadenaXML);
//        } catch (Exception e) {
//            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
//            return null;
//        }
//    }
//    public List<CatTipoPromPatentes> getCatTipoPromPat(String cadenaXML){
//        try{  
//          return this.rduPatentesBeanRemote.getCatTipoPromPat(cadenaXML);
//        } catch (Exception e) {
//            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
//            return null;
//        }
//    }
//    public List<TipoPromPatByOficio> getTipoPromByOficio(String cadenaXML){
//        try{  
//           return this.rduPatentesBeanRemote.getTipoPromByOficio(cadenaXML);
//        } catch (Exception e) {
//            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
//            return null;
//        }
//    }
//    public String setTramitePromoPatJms(PromocionesPatentes promocionesPatentes) {
//        try{  
//           return this.rduPatentesBeanRemote.setTramitePromoPatJms(promocionesPatentes);
//        } catch (Exception e) {
//            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
//            return null;
//        }
//    }
//
//    public String setTramitePromoPatJmsAdmin(String cadenaXML) {
//        try{  
//           return this.rduPatentesBeanRemote.setTramitePromoPatJmsAdmin(cadenaXML);
//        } catch (Exception e) {
//            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
//            return null;
//        }
//    }
    @Override
    public String getCatalogoJms(String nombreCatalogo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getCatTipoPromocionJms(String nombreCatalogo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTipoPromByOficioJms(String idOficio) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String setTramitePromoPatJms(PromocionesPatentes promocionesPatentes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CatAreaPromPatentes> getCatalogo(String cadenaXML) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<CatTipoPromPatentes> getCatTipoPromPat(String cadenaXML) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<TipoPromPatByOficio> getTipoPromByOficio(String cadenaXML) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String setTramitePromoPatJmsAdmin(String cadenaXML) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<CatTipoPromPatentes> selectTipoPromociones() {
        List<CatTipoPromPatentes> promociones = null;
        try {
            promociones = this.rduPatentesBeanRemote.selectTipoPromociones();
        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
        return promociones;
    }

    @Override
    public PromocionesConOficioDto getNumeroOficio(PromocionesConOficioDto promocionesConOficio) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Integer ordenarAnexos(Anexos anex) {
        int qty = 9;
        Integer orden = null;
        
        if (anex.getExtension().equals("pdf")) {
            switch (anex.getIdTipoanexo().intValue()) {
                case 17://comprobante pago                        
                    orden = new Integer(2);
                    break;
                case 41://Documento descuento
                    orden = new Integer(3);
                    break;
                case 19://poder de acreditamiento del apoderado/RGP
                    orden = new Integer(4);
                    break;
                case 20://cesión de derechos
                    orden = new Integer(6);
                    break;
//                    48 CARTA PODER SIMPLE
//                    49 CONSTANCIA RGP
//                    50 PODER NOTARIAL
//          //          51 ACTA CONSTITUTIVA
//                    52 OTRO 
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
                case 16://REIVINDICACION //Memoria tecnica
                    orden = new Integer(9);
                    break; 
                case 43://Resumen
                    orden = new Integer(10);
                    break;                      
                case 22://prioridad
                    orden = new Integer(15);//11//13//14
                    break;
                case 27://traduccion prioridad
                    orden = new Integer(16);//12//14//15
                    break;           
                default://otros
                    orden = new Integer(17);//13//15//16
                    break;                   
            }
        }
        return orden;
    }
    
    @Override
    public Integer ordenarAnexosTraduccion(Anexos anex) {
        Integer orden = null;
        if (anex.getExtension().equals("pdf")) {
            switch (anex.getIdTipoanexoTrad().intValue()) {
                case 45://Traducción descripción
                    orden = new Integer(12);//11
                    break; 
                case 46://Traducción reivindicación 
                    orden = new Integer(13);//12
                    break; 
                case 47://Traducción Resumen 1
                    orden = new Integer(14);//13
                    break;    
                default://otros
                    orden = new Integer(18);//13//16//17
                    break;                   
            }
        }
        return orden;
    }

    @Override
    public List<ReportesDto> ordenarReporte(List<ReportesDto> listReportes) {
        List<ReportesDto> listReportesOrdenados = null;
        HashMap<Integer, ReportesDto> mapaReportes = new HashMap<Integer, ReportesDto>();

        for (ReportesDto dto : listReportes) {
            //Validacion para los documentos OTROS
            if (dto.getOrden() == 13) {
                int checkExiste = dto.getOrden();
                while(mapaReportes.get(checkExiste) != null)
                {                    
                    checkExiste ++;
                }               
                mapaReportes.put(checkExiste, dto);
            } else {
                mapaReportes.put(dto.getOrden(), dto);
            }
        }
        
        return listReportesOrdenados = new ArrayList<ReportesDto>(mapaReportes.values());
    }

    @Override
    public List<ReportesDto> reordenarReporte(List<ReportesDto> listReportes) {
        List<ReportesDto> listReportesOrdenados = null;
        
        int numAnexoDesc = 0;
        int con = 0;
        int orden = 0;
        uno:
        for (ReportesDto dto : listReportes) {
            if (dto.getNombreBookMark().equals("HOJA DE DESCUENTO")) {
                numAnexoDesc++;
                orden = dto.getOrden();
            }

            if (numAnexoDesc > 1) {
                break uno;
            }
            con++;
        }

        if (numAnexoDesc > 1) {
            con -= 1;
            for (; con < listReportes.size(); con++) {
//              if(con+1 < listReportes.size())
//                {    
//                  if(listReportes.get(con+1).getNombreBookMark()=="DESCRIPCION" )
//                    {
//                        listReportes.get(con+1).setOrden(orden);
//                        orden += 1;
//                        listReportes.get(con).setOrden(orden);
//                        con++;
//                    }
//                else
//                    {    
//                        listReportes.get(con).setOrden(orden);
//                    }
//                }else
//                {    
                    listReportes.get(con).setOrden(orden);
//                }
                orden += 1;
            }
        }
        
        return listReportesOrdenados;
    }
    
    public void actualizarPersona(Persona persona) {
        rduPatentesBeanRemote.actualizarPersona(persona);
    }
    
    public void habilitarSolicitante(PatentesDisenoIndustrialMB forma) {
        if (forma.getSolicitanteDatos().getTipoPersona().getIdTipopersona().intValue() == Constantes.INIT) {
            forma.getForma().setPersonaFisica(false);
        } else {
            forma.getForma().setPersonaFisica(true);
            forma.getSolicitanteDatos().setPrimerApellido("");
            forma.getSolicitanteDatos().setSegundoApellido("");
        }

        forma.getForma().setPersonaMoral(false);
    }

    public void selectDescText(PatentesDisenoIndustrialMB forma) {
        if (forma.getForma().getRutaDesc() != null && !forma.getForma().getRutaDesc().equals("")) {
            forma.getForma().setValorTipoDesc(Constantes.INIT);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambiDescripcionDlg.show();");
        }else {
            forma.getForma().setRenderedCargarPdf(true);
            forma.getForma().setRenderedDescText(false);
            forma.getForma().setDescPdf("");
        }
    }
       
    public void selectDescPdf(PatentesDisenoIndustrialMB forma) {
        if (forma.getTramitePat().getPreambulo() != null && !forma.getTramitePat().getPreambulo().equals("")) {
            forma.getForma().setValorTipoDesc(Constantes.SECOND);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambiDescripcionDlg.show();");
        }else{
            forma.getForma().setRenderedDescText(true);
            forma.getForma().setRenderedCargarPdf(false);
            forma.getForma().setRutaDesc("");
            forma.getForma().setDescText("");
//          anexoDescripcion = new AnexosViewDto();
        }
    }
     public void selectTradText(PatentesDisenoIndustrialMB forma) {
        if (forma.getForma().getRutaAnexoTrad() != null && !forma.getForma().getRutaAnexoTrad().equals("")){
            forma.getForma().setValorTipoTrad(Constantes.INIT);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambiaDescripcionTradDlg.show();");
        }else {
            forma.getForma().setRenderedCargarPdfTrad(true);
            forma.getForma().setRenderedDescTextTrad(false);
            forma.getForma().setDescPdfTrad("");
        }
    }
       
    public void selectTradPdf(PatentesDisenoIndustrialMB forma) {
        if (forma.getForma().getTextoTraduccion() != null && !forma.getForma().getTextoTraduccion().equals("")) {
            forma.getForma().setValorTipoTrad(Constantes.SECOND);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambiaDescripcionTradDlg.show();");
        }else{
            forma.getForma().setRenderedDescTextTrad(true);
            forma.getForma().setRenderedCargarPdfTrad(false);
            forma.getForma().setRutaAnexoTrad("");
            forma.getForma().setDescTextTrad("");
//          anexoDescripcion = new AnexosViewDto();
        }
    }

    
    public void selectPrioridad(PatentesDisenoIndustrialMB forma) {
        try
        {
            if (forma.getListaPrioridades().size() > 0 && forma.getAgregarprioridad() == null) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("cambioPriodiradDlg.show();");
            }  
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    
    
    public void selectResuText(PatentesDisenoIndustrialMB forma) {
        if (forma.getForma().getRutaResum() != null && !forma.getForma().getRutaResum().equals("")) {
            forma.getForma().setValorTipoResu(Constantes.INIT);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambioResumenDlg.show();");
        } else {
            forma.getForma().setRenderedResuText(false);
            forma.getForma().setRenderedResuPdf(true);
            forma.getForma().setResuPdf("");
        }
    }
    
    public void selectResuPdf(PatentesDisenoIndustrialMB forma) {
        if (forma.getTramitePat().getTextoAdicional() != null) {
            forma.getForma().setValorTipoResu(Constantes.SECOND);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("cambioResumenDlg.show();");
        } else {
            forma.getForma().setRenderedResuText(true);
            forma.getForma().setRenderedResuPdf(false);

        }
    }
    
    //Depuracion de codigo para que todas las variables se encuentren en TramitePatente
    public String validarSolicitud(TramitePatente tramitePat, CapturaSolicitudForm forma, List<Persona> listaSolicitantes,
            Persona selectedSolicitante, List<Persona> listaInventores, Persona selectedApoderado) {
        String msgAviso = "";
        
        //VALIDACION PESTANA TIPO SOLICITUD
        if (!(tramitePat.getTipoSol().getIdTiposolicitud() != null && tramitePat.getTipoSol().getIdTiposolicitud().longValue() != 0)) {
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.tipo.solicitud") + "||";
        }

        if (!(tramitePat.getSubTipoSol().getIdSubtiposolicitud() != null && tramitePat.getSubTipoSol().getIdSubtiposolicitud().longValue() != 0)) {
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.tipo.subsolicitud") + "||";
        }

        if (tramitePat.getExpDivisional() != null && tramitePat.getExpDivisional().length() != 0) {
            String msg = buscarExpDivisional(tramitePat);
            if (msg != null && msg.length() != 0) {
                msgAviso += msg;
            }

        }

        /*IJZA se quita validacion del campo Titulo de la Invención ya que era obligatorio y no se utiliza para la 
        solicitu del SIT, este esta en la pestaña tipo solicitud 18/02/2015*/

//        if (tramitePat.getInvencion() == null || tramitePat.getInvencion().length() == 0) {
//            msgAviso += bundleParametrosPatentes.getString("mensaje.error.invento") + "||";
//        }

        /*IJZA se agrega validacion para el campo cobertura 18/02/2015 */
        
        
          if (tramitePat.getCobertura()== null || tramitePat.getCobertura().length() == 0) {
               msgAviso += bundleParametrosPatentes.getString("mensaje.error.cobertura") + "||";
        }
        if (tramitePat.getObservaciones() != null && tramitePat.getObservaciones().length() != 0) {

//            if (Pattern.matches(Constantes.pattern_observacionesPatente, tramitePat.getObservaciones()) == false) {
              if (Pattern.matches(Constantes.pattern_nombre_personas, tramitePat.getObservaciones()) == false) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.obvs") + "||";
            }

        }
        
        //VALIDACION PESTANA SOLICITANTES
        if (msgAviso.length() == 0 && (listaSolicitantes == null || listaSolicitantes.isEmpty() == true)  ) {
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.sol") + "||";
        } else {
            for (int j = 0; j < listaSolicitantes.size(); j++) {
                Persona perSol = (Persona) listaSolicitantes.get(j);
                if (perSol.getTipoPersona().getIdTipopersona().intValue() == 2) {
                    if (tramitePat.getApoderados() == null || tramitePat.getApoderados().isEmpty() == true) {
                        msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.apoderados") + "||";
                        break;
                    }
                }

            }

            if (msgAviso.length() == 0 && selectedSolicitante == null) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.falta.principal") + "||";
            } else if (msgAviso.length() == 0 && selectedSolicitante != null) {
                if (selectedSolicitante.getPrincipal() == null || selectedSolicitante.getPrincipal().intValue() == 0) {
                    msgAviso += bundleParametrosPatentes.getString("mensaje.error.falta.principal") + "||";

                }
            }

        }
        
        //VALIDACION PESTANA INVENTORES
        if (msgAviso.length() == 0 && (listaInventores == null || listaInventores.isEmpty() == true)  ) {
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.sin.inventor") + "||";
        }
        
        //VALIDACION PESTANA APODERADOS
        if (tramitePat.getApoderados() != null && tramitePat.getApoderados().isEmpty() == false) {
            if (selectedApoderado == null) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.falta.principal.apoderado") + "||";
            } else {
                if (selectedApoderado.getPrincipal() == null || selectedApoderado.getPrincipal().intValue() == 0) {
                    msgAviso += bundleParametrosPatentes.getString("mensaje.error.falta.principal.apoderado") + "||";
                    
                }
            }
        }
                
        //VALIDACION PESTANA PERSONAS AUTORIZADAS
        //personasNot
        if (tramitePat.getPersonasNot() == null || tramitePat.getPersonasNot().isEmpty() == true)
            msgAviso += bundleParametrosPatentes.getString("lista.persona.notificacion.vacio") + "||";        
        
        //VALIDACION PESTANA PRIORIDAD
        //VALIDACION PESTANA MEMORIA TECNICA
        if (msgAviso.length() == 0 && (tramitePat.getReivindicaciones().isEmpty())) {
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.reivindicaciones.sin.reivindicacion") + "||";
        }else if( !tramitePat.getReivindicaciones().isEmpty() &&  (forma.getIdiomaReivindicacion() == null || forma.getIdiomaReivindicacion().equals("")) ) {
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.idiomaReivindicacion") + "||";
        } 
        
        if (msgAviso.length() == 0 &&  (forma.getDescPdf() == null || forma.getDescText() == null )  && 
                (forma.getIdiomaDescripcion() == null || forma.getIdiomaDescripcion().equals("") ) ) {
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.idiomaDescripcion") + "||";
        }
        
        if(forma.getDescPdf()!=null && forma.getAnexoDescripcionMe() == null){
            msgAviso += bundleParametrosPatentes.getString("mensaje.error.descripcion.pdf") + "||";            
        }
        
        if(msgAviso.length() == 0 &&  forma.getDescText() != null && (tramitePat.getPreambulo() != null && tramitePat.getPreambulo().length() == 0))
            msgAviso += "Debe de ingresar la información correspondiente a la Descripción";
        
        if(tramitePat.getTipoSol() != null && tramitePat.getTipoSol().getIdTiposolicitud() == Constantes.SOL_DISENO_INDUSTRIAL)
            forma.setRenderedResumen(false);
        else
            forma.setRenderedResumen(true);

//        if ( (forma.g) &&  (forma.getIdiomaReivindicacion() == null || forma.getIdiomaReivindicacion().equals("")) ) {
//            msgAviso += bundleParametrosPatentes.getString("mensaje.error.idiomaReivindicacion") + "||";
//        } 
        
        //VALIDACION PESTANA FIGURAS
        if (msgAviso.length() == 0 && 
                (tramitePat.getTipoSol() != null ? tramitePat.getTipoSol().getIdTiposolicitud().intValue() == Constantes.TIPO_SOL_DISENO : false)) {
            if (tramitePat.getImagenes().isEmpty()) {
                msgAviso += bundleParametrosPatentes.getString("mensaje.error.imagenes.sin.dibujo") + "||";
            }
        }

                   log.info("RESUMEN   "+forma.isRenderedResumen());

        return msgAviso;
    }

    public String buscarExpDivisional(TramitePatente tramitePat) {
        Solicitud solBuscar = new Solicitud();
        String msg = "";
//        solicitudesSgapatTable = new ArrayList<Solicitud>();
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
                            }

                        } else {
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

//    @Override
//    public List<Solicitud> buscaExpedienteDivisional(String oficina, Integer numExp, Short serExp, String tipExp){ 
//        
//      
//    }


        
}


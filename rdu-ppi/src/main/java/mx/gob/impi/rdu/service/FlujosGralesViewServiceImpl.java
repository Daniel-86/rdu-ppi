/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.impi.ingresos.persistence.model.FepsRecibidos;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.pase.persistence.model.SisAlerta;
import mx.gob.impi.rdu.dto.*;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.dto.ApoderadoDto;
import mx.gob.impi.rdu.dto.DomicilioDto;
import mx.gob.impi.rdu.dto.ExpedientesDto;
import mx.gob.impi.rdu.dto.FirmaDto;
import mx.gob.impi.rdu.dto.FolioDto;

import mx.gob.impi.rdu.dto.RespuestaDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.exposition.flujosGenerales.Notification;
import mx.gob.impi.rdu.exposition.flujosGenerales.reporte.GenerarReporte;
import mx.gob.impi.rdu.exposition.form.CapturaSolicitudForm;
import mx.gob.impi.rdu.exposition.patentes.PatentesDisenoIndustrialMB;
import mx.gob.impi.rdu.persistence.model.*;
import mx.gob.impi.rdu.persistence.model.CodigosPostales;
import mx.gob.impi.rdu.persistence.model.Domicilio;
import mx.gob.impi.rdu.persistence.model.Firma;
import mx.gob.impi.rdu.persistence.model.Folio;
import mx.gob.impi.rdu.persistence.model.Tramite;
import mx.gob.impi.rdu.persistence.model.Usuario;
import mx.gob.impi.rdu.remote.RduFlujosGeneralesBeanRemote;
import mx.gob.impi.rdu.remote.RduPatentesBeanRemote;
import mx.gob.impi.rdu.util.ComparatorPersona;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import mx.gob.impi.sigappi.persistence.model.KfFolios;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.KfAlmacenar;
import mx.gob.impi.sigappi.persistence.model.SolicitudInteresados;
import mx.gob.impi.sigappi.persistence.model.TiposRelacion;
import mx.gob.impi.sigappi.persistence.model.UsuariosSigappi;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author usradmin
 */
public class FlujosGralesViewServiceImpl implements FlujosGralesViewService, Serializable {

    private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.Tableros";
    final ResourceBundle bundleFlujosGrls = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
    private Logger lger = Logger.getLogger(this.getClass());
    private RduFlujosGeneralesBeanRemote rduFlujosGeneralesRemot;
    private RduPatentesBeanRemote rduPatentesBeanRemote;
    

    public void setRduPatentesBeanRemote(RduPatentesBeanRemote rduPatentesBeanRemote) {
        this.rduPatentesBeanRemote = rduPatentesBeanRemote;
    }

    public void setRduFlujosGeneralesRemot(RduFlujosGeneralesBeanRemote rduFlujosGeneralesRemot) {
        this.rduFlujosGeneralesRemot = rduFlujosGeneralesRemot;
    }

    public List<SolicitudPreparacionDto> extraerSolicitudesPreparacion(List<Integer> usuarios, int idTipoTramite) {
        try {
            Usuario oUsrFirmante = new Usuario();
            Usuario oUsrCapturista = new Usuario();
            ApoderadoDto obtApoderadoFirmante = new ApoderadoDto();
            ApoderadoDto obtApoderadoCapturista = new ApoderadoDto();
            List<SolicitudPreparacionDto> retTramites = new ArrayList();
            List<SolicitudPreparacionDto> obtTramites = this.rduFlujosGeneralesRemot.extraerSolicitudesPreparacion(usuarios, idTipoTramite);
            //se recupera detalles del firmante y el capturista del XML que PASE proporciona a RDU
            if (null != obtTramites) {
                SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
                if (null != obtSession) {
                    if (obtSession.getUsuario().getIdUsuario() > 0) {
                        for (Iterator iter = obtTramites.iterator(); iter.hasNext();) {
                            SolicitudPreparacionDto oTramite = (SolicitudPreparacionDto) iter.next();
                            //capturista
                            if (oTramite.getIdUsuariocaptura() != null) {
                                if (oTramite.getIdUsuariocaptura() > 0) {
                                    obtApoderadoCapturista = ContextUtils.obtenerApoderadoDeSesion(oTramite.getIdUsuariocaptura().intValue());
                                    oUsrCapturista = new Usuario();
                                    oUsrCapturista.setNombre(obtApoderadoCapturista.getNombre());
                                    oUsrCapturista.setApellidoPaterno(obtApoderadoCapturista.getApaterno());
                                    oUsrCapturista.setApellidoMaterno(obtApoderadoCapturista.getAmaterno());
                                    oUsrCapturista.setIdUsuario(oTramite.getIdUsuariocaptura());
                                    oTramite.setUsuarioCaptura(oUsrCapturista);
                                }
                            }
                            //firmante
                            if (oTramite.getIdUsuariofirmante() != null) {
                                if (oTramite.getIdUsuariofirmante() > 0) {
                                    obtApoderadoFirmante = ContextUtils.obtenerApoderadoDeSesion(oTramite.getIdUsuariofirmante().intValue());
                                    oUsrFirmante = new Usuario();
                                    oUsrFirmante.setNombre(obtApoderadoFirmante.getNombre());
                                    oUsrFirmante.setApellidoPaterno(obtApoderadoFirmante.getApaterno());
                                    oUsrFirmante.setApellidoMaterno(obtApoderadoFirmante.getAmaterno());
                                    oUsrFirmante.setIdUsuario((long) oTramite.getIdUsuariofirmante());
                                    oTramite.setUsuarioFirmante(oUsrFirmante);
                                }
                            }
                            retTramites.add(oTramite);
                        }

                    }
                }
            }

            if (retTramites.size() > 0) {
                Collections.sort(retTramites, new Comparator<SolicitudPreparacionDto>() {
                    public int compare(SolicitudPreparacionDto s1, SolicitudPreparacionDto s2) {
                        return s2.getIdTramite().compareTo(s1.getIdTramite());
                    }
                });
            }

            return retTramites;

        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }

    }


    public RespuestaDto EliminarSolicitud(SolicitudPreparacionDto prmTramite) {
        String msgAviso = "";
        int respuestaEjb = 0;
        RespuestaDto respuestaVw = new RespuestaDto();
        try {
            if (null != prmTramite) {
                if (prmTramite.getIdEstatus().intValue() == 1) {
                    respuestaEjb = this.rduFlujosGeneralesRemot.eliminarSolicitud(new SolicitudPreparacionDto(prmTramite.getIdTramite(), prmTramite.getIdArea(), prmTramite.getIdSubtiposolicitud(), prmTramite.getIdTiposolicitud()));
                    if (respuestaEjb == 1) {
                        msgAviso = "La solicitud " + prmTramite.getIdTramite() + " fue eliminada";
                    } else {
                        msgAviso = "La solicitud " + prmTramite.getIdTramite() + " no fue eliminada de la BD";
                    }
                } else {

                    msgAviso = "Esta solicitud no puede ser eliminada porque tiene estatus de " + prmTramite.getDescripcionESTATUS() + " ";
                }

            } else {
                msgAviso = "Seleccione una solicitud para eliminarla";
            }
            respuestaVw.setRespuesta(respuestaEjb);
            respuestaVw.setMsgRespuesta(msgAviso);
            return respuestaVw;

        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return respuestaVw;
        }


    }
    
    public RespuestaDto eliminarPromocion(SolicitudPreparacionDto prmTramite) {
        String msgAviso = "";
        int respuestaEjb = 0;
        RespuestaDto respuestaVw = new RespuestaDto();
        try {
            if (null != prmTramite) {
                if (prmTramite.getIdEstatus().intValue() == 1) {
                    respuestaEjb = this.rduFlujosGeneralesRemot.eliminarPromocion(prmTramite);
                    if (respuestaEjb == 1) {
                        msgAviso = "La promoción " + prmTramite.getIdTramite() + " fue eliminada";
                    } else {
                        msgAviso = "La promoción " + prmTramite.getIdTramite() + " no fue eliminada de la BD";
                    }
                } else {

                    msgAviso = "Esta promoción no puede ser eliminada porque tiene estatus de " + prmTramite.getDescripcionESTATUS() + " ";
                }

            } else {
                msgAviso = "Seleccione una promoción para eliminarla";
            }
            respuestaVw.setRespuesta(respuestaEjb);
            respuestaVw.setMsgRespuesta(msgAviso);
            return respuestaVw;

        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return respuestaVw;
        }


    }

    public RespuestaDto copiarSolicitud(Tramite prmTramite) {
        String msgAviso = "";
        long respuestaEjb = 0;
        int retRespuesta = 0;
        RespuestaDto respuestaVw = new RespuestaDto();
        Tramite nvaSolicitud = new Tramite();
        try {

            if (null != prmTramite) {

                if (prmTramite.getIdEstatus() == 1) {
                    nvaSolicitud = this.clonarTramite(prmTramite);
                    respuestaEjb = this.rduFlujosGeneralesRemot.copiarSolicitud(nvaSolicitud);
                    nvaSolicitud.setIdTramite(respuestaEjb);

                    if (respuestaEjb > 0) {
                        msgAviso = "La solicitud " + prmTramite.getIdTramite() + " fue copiada en la solicitud " + nvaSolicitud.getIdTramite();
                        retRespuesta = 1;
                        //firmante
                        ApoderadoDto obtApoderado = new ApoderadoDto();
                        Usuario oUsrFirmante = new Usuario();
                        obtApoderado = ContextUtils.obtenerApoderadoDeSesion(nvaSolicitud.getIdUsuariofirmante().intValue());
                        oUsrFirmante = new Usuario();
                        oUsrFirmante.setNombre(obtApoderado.getNombre());
                        oUsrFirmante.setApellidoPaterno(obtApoderado.getApaterno());
                        oUsrFirmante.setApellidoMaterno(obtApoderado.getAmaterno());
                        oUsrFirmante.setIdUsuario((long) nvaSolicitud.getIdUsuariofirmante());
                        nvaSolicitud.setUsuarioFirmante(oUsrFirmante);
                        lger.info(".....tramite copiado en servicio view: " + respuestaEjb);

                    } else {
                        msgAviso = "La solicitud " + prmTramite.getIdTramite() + " no fue copiada en la BD";
                    }
                } else {

                    msgAviso = "Esta solicitud no puede ser copiada porque tiene estatus de " + prmTramite.getEstatus().getDescripcion() + " ";
                }

            } else {
                msgAviso = "Seleccione una solicitud para copiarla";
            }
            respuestaVw.setRespuesta(retRespuesta);
            respuestaVw.setMsgRespuesta(msgAviso);
            respuestaVw.setTramite(nvaSolicitud);
            return respuestaVw;

        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return respuestaVw;
        }

    }

    private Tramite clonarTramite(Tramite prmTramite) {
        Tramite nvaSolicitud = new Tramite();
        nvaSolicitud.setIdSubtiposolicitud(prmTramite.getIdSubtiposolicitud());
        nvaSolicitud.setIdTipomarca(prmTramite.getIdTipomarca());
        nvaSolicitud.setMarcacolectiva(prmTramite.getMarcacolectiva());
        nvaSolicitud.setDescripcionsignodistintivo(prmTramite.getDescripcionsignodistintivo());
        nvaSolicitud.setFechaprimeruso(prmTramite.getFechaprimeruso());
        nvaSolicitud.setNousadofecha(prmTramite.getNousadofecha());
        nvaSolicitud.setLeyendasfigurasreservables(prmTramite.getLeyendasfigurasreservables());
        nvaSolicitud.setIdDomicilionotificacion(prmTramite.getIdDomicilionotificacion());
        nvaSolicitud.setIdPaisprioridad(prmTramite.getIdPaisprioridad());
        nvaSolicitud.setFechapresentacionextranjero(prmTramite.getFechapresentacionextranjero());
        nvaSolicitud.setIdEstatus(prmTramite.getIdEstatus());
        nvaSolicitud.setIdUsuariofirmante(prmTramite.getIdUsuariofirmante());
        nvaSolicitud.setFechacaptura(new Date());
        nvaSolicitud.setIdUsuariocaptura(prmTramite.getIdUsuariocaptura());
        nvaSolicitud.setFechaEstatusActual(new Date());

        nvaSolicitud.setEstatus(prmTramite.getEstatus());
        nvaSolicitud.setSubTipoSolicitud(prmTramite.getSubTipoSolicitud());

        nvaSolicitud.setIndActivo(1);

        return nvaSolicitud;

    }

    public Long insertaFirma(FirmaDto firmaDto) {

        //  this.rduFlujosGeneralesRemot.insertFirma(firma);
        Firma firma = new Firma();
        BeanUtils.copyProperties(firmaDto, firma);

        return this.rduFlujosGeneralesRemot.insertFirma(firma);

    }

    public CertificadoDto getFirmaAdmin(CertificadoDto firma) {
        return this.rduFlujosGeneralesRemot.selectFirmaAdminEstatusArea(firma);
    }

    public int insertaFirmaAdmin(CertificadoDto certDto) {

        CertificadoDto fa = this.rduFlujosGeneralesRemot.selectFirmaAdminEstatusArea(new CertificadoDto(certDto.getIdEstatusCertificado(), certDto.getIdCatArea()));  //rduFlujosGeneralesRemot.selectFirmaAdminByInd(new CertificadoDto(certDto.getIdEstatusCertificado() )) ;

        if (fa != null && fa.getIdCertificado() != null) {
            fa.setIdEstatusCertificado(Constantes.ESTATUS_CERT_DESAHABILITADO);
            rduFlujosGeneralesRemot.updateFirmaAdmin(fa);
        }
        //firmaAdminDto.setLastVersionInd("Y");
        return this.rduFlujosGeneralesRemot.insertFirmaAdmin(certDto);
    }

    public int actualizaFirmaAdmin(CertificadoDto firma) {

        // this.rduFlujosGeneralesRemot.
        CertificadoDto cer = this.rduFlujosGeneralesRemot.selectFirmaAdminEstatusArea(new CertificadoDto(firma.getCatEstatusCertificadoDto().getIdEstatusCertificado(), firma.getIdCatArea()));
        if (cer != null && cer.getIdCertificado() != null) {
            cer.setIdEstatusCertificado(Constantes.ESTATUS_CERT_DESAHABILITADO);
            this.rduFlujosGeneralesRemot.updateFirmaAdmin(cer);
        }
        firma.setIdEstatusCertificado(firma.getCatEstatusCertificadoDto().getIdEstatusCertificado());
        return this.rduFlujosGeneralesRemot.updateFirmaAdmin(firma);

    }

    public Integer updateSelectiveFirma(Firma firma) {
        return this.rduFlujosGeneralesRemot.updateSelectiveFirma(firma);
    }

    public Domicilio obtenerDomicilio(DomicilioDto pDomicilio) {
        if (lger.isInfoEnabled()) {
            lger.info("<------------Entrando al metod CatalogosViewServiceImpl.obtenerDomicilio---------------------->");
        }
        Domicilio domicilio = new Domicilio();
        domicilio.setIdPais(pDomicilio.getIdPais());
        try {
            if (pDomicilio.getIdPais().intValue() == Constantes.ID_PAIS.intValue()) {
                domicilio.setIdEntidad(pDomicilio.getIdEntidad());
            } else {
                domicilio.setIdEntidad(pDomicilio.getNombreEntidad());
            }
            domicilio.setCodigopostal(pDomicilio.getCodigopostal());
            domicilio.setPoblacion(pDomicilio.getPoblacion());
            domicilio.setColonia(pDomicilio.getColonia());
            domicilio.setCalle(pDomicilio.getCalle());

        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
        return domicilio;
    }

    public int insertarAnexosDtos(List<AnexosViewDto> anexosDtos) {
        //lger.info("entra a insertar anexosDTO");
        return this.rduFlujosGeneralesRemot.insertarAnexosDto(anexosDtos);
    }

    public List<CodigosPostales> obtenerCodigosPostales(String codigoPostal) {
        try {
            return this.rduFlujosGeneralesRemot.selectByIdCodigosPostales(codigoPostal);
        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }
    }

    
    public List<ExpedientesDto> obtenerExpedientesPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer start, Integer pageSize) {
        List<ExpedientesDto> expedientesDt = new ArrayList();
        try {
           
            expedientesDt = this.rduFlujosGeneralesRemot.obtenerExpedientesPaginadosPorUsr(usuarios, idArea, idTipoSolicitud,
                    ultimaSemana, ultimoMes, fechaInicio, fechaFin,start,pageSize);
            return expedientesDt;
        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return expedientesDt;
        }

    }
    
     public List<ExpedientesDto> obtenerExpedientes(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        List<ExpedientesDto> expedientesDt = new ArrayList();
        try {            
            expedientesDt = this.rduFlujosGeneralesRemot.obtenerExpedientesPorUsr(usuarios, idArea, idTipoSolicitud,
                    ultimaSemana, ultimoMes, fechaInicio, fechaFin);
            return expedientesDt;
        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return expedientesDt;
        }

    }

    @Override
    public int obtenerTotalExpedientes(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return rduFlujosGeneralesRemot.obtenerTotalExpedientesPorUsr(usuarios, idArea, idTipoSolicitud, ultimaSemana,  ultimoMes,  fechaInicio,  fechaFin);                
    }
     
    public List<Pago> buscarPago(long pago) {
        List<Pago> listPago = new ArrayList();
        try {

            listPago = this.rduFlujosGeneralesRemot.buscarPago(pago);
            return listPago;
        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return listPago;
        }
    }

    
    public FirmaDto loadFirma(Long idFirma) {
        try {
            return this.rduFlujosGeneralesRemot.loadFirma(idFirma);
        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }
    }

    public Integer updateFirma(FirmaDto firma) {
        try {
            return this.rduFlujosGeneralesRemot.updateFirma(firma);
        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return 0;
        }
    }

    public int insertarAlertas(SisAlerta alerta) {
        return this.rduFlujosGeneralesRemot.insertarAlertas(alerta);
    }

    public Promovente buscaPromovente(Long id) {
        return this.rduFlujosGeneralesRemot.selectPromovente(id);
    }

    public List<CertificadoDto> loadAllActiveCerts(Integer idArea) {
        try {
            return this.rduFlujosGeneralesRemot.loadAllActiveCerts(idArea);
        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }
    }

    public Date getSysDate() {
        return this.rduFlujosGeneralesRemot.getSysDate();
    }

    public int insertFeps(TramiteDto prmTramite) {

        try {
            return this.rduFlujosGeneralesRemot.insertFeps(prmTramite);
        } catch (Exception e) {
            e.printStackTrace();
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return 0;
        }

    }

    public int insertFepsSagpat(TramitePatente tramite) {

        try {
            return this.rduFlujosGeneralesRemot.insertFepsSagpat(tramite);
        } catch (Exception e) {
            e.printStackTrace();
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return 0;
        }

    }

    public FepsRecibidos selectFepsByFolio(Long fepsFolio) {
        return this.rduFlujosGeneralesRemot.selectFepsByFolio(fepsFolio);
    }

    public int insertBitacoraErrores(BitacoraErrores bitacora) {
        return this.rduFlujosGeneralesRemot.insertBitacoraErrores(bitacora);
    }

    public List<BitacoraErrores> selectBitacoraErrores() {
        return this.rduFlujosGeneralesRemot.selectBitacoraErrores();
    }

    public int eliminarBitacoraErrores(Short idBitacora) {
        return this.rduFlujosGeneralesRemot.eliminarBitacoraErrores(idBitacora);
    }

    public int insertarAnexosDtosPatentes(List<AnexosViewDto> anexosDtos) {
        return this.rduFlujosGeneralesRemot.insertarAnexosDtoPatente(anexosDtos);
    }

    public String jobeliminarAcuse() {
        return this.rduFlujosGeneralesRemot.jobeliminarAcuse();
    }

    public int copiarSolicitudEnSagpat(TramitePatente patente, int pages) {
        return this.rduFlujosGeneralesRemot.copiarSolicitudEnSagpat(patente, pages);
    }

    
    public String[] getFoliosSagpat(String expediente, Date hoy) {
        return this.rduFlujosGeneralesRemot.getFoliosSagpat(expediente, hoy);

    }

    public String validatePhrase(String tmp) throws Exception {
        return this.rduFlujosGeneralesRemot.validatePhrase(tmp);
    }

    public void eliminarTramitesJob() {
        /*Proceso para eliminar acuses.pdf de la tabla FIRMA*/
        System.out.println(" **** eliminarTramite ==> ");
        int diasEliminacion = Integer.parseInt(bundleFlujosGrls.getString("dias.eliminacion"));
        int estatus = Integer.parseInt(bundleFlujosGrls.getString("valor.estatus"));

        try {
            List<Tramite> listTramite = new ArrayList<Tramite>();
            listTramite = this.rduFlujosGeneralesRemot.selectTramite();
            Date hoy = new Date(); //Fecha de hoy
            int cont = 0;
            int respuestaEjb = 0;

            for (Tramite tramite : listTramite) {
                long diferencia = (hoy.getTime() - tramite.getFechacaptura().getTime());
                // calcular la diferencia en dias
                long diffDays = diferencia / (24 * 60 * 60 * 1000);

                if (diffDays > diasEliminacion && tramite.getIdEstatus() == estatus) {
                    System.out.println(" **** IdTramite ==> " + tramite.getIdTramite() + "  idEstatus==> " + tramite.getIdEstatus()
                            + "  Fechacaptura==> " + tramite.getFechacaptura()
                            + "  fecha_actual==> " + hoy + "  DIFERENCIA DIAS==> " + diffDays);
                    //respuestaEjb =this.rduFlujosGeneralesRemot.eliminarSolicitud(tramite.getIdTramite(), 0);
                    if (respuestaEjb == 1) {
                        System.out.println("    ***La solicitud " + tramite.getIdTramite() + " fue eliminada");
                    } else {
                        System.out.println("    ***La solicitud " + tramite.getIdTramite() + " no fue eliminada de la BD");
                    }
                    cont++;
                }
            }
            System.out.println("    ***REGISTROS ELIMINADOS==>  " + cont);

        } catch (Exception e) {
            //bitacora.setDescripcion(e.toString());
            //this.rduFlujosGeneralesRemot.insertBitacoraErrores(bitacora);
            System.out.println("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
    }

    public List<Anexos> obtenerAnexosByTramite(Long idTramite) {
        return this.rduFlujosGeneralesRemot.obtenerAnexosByTramite(idTramite);
    }

    public List<Pago> selectPagoByTramiteId(Long idTramite) {
        return this.rduFlujosGeneralesRemot.selectPagoByTramiteId(idTramite);
    }

    public Firma obtenerFirmaByTramite(Long idTramite) {
        return rduFlujosGeneralesRemot.obternerFirmaByTramite(idTramite);
    }


    public List<Notificacion> consultarNotificaciones(Integer idUsuarioFirma) {
        return this.rduFlujosGeneralesRemot.consultarNotificaciones(idUsuarioFirma);
    }    
    
    public Notificacion selectNotificacionesById(Integer idNotificacion) {
        return this.rduFlujosGeneralesRemot.selectNotificacionesById(idNotificacion);
    }

    
    public List<Notificacion> getNotificacionesUserLoad(Integer idUserLoad, Integer coordinador) {
        return this.rduFlujosGeneralesRemot.getNotificacionesUserLoad(idUserLoad, coordinador);
    }

    public Long saveFirmaNotificacion(NotificacionFirma firma) {
        return this.rduFlujosGeneralesRemot.saveNotificacionFirma(firma);
    }

    public List<Notificacion> selectNotificacionesByIds(List<Integer> cadenaIds) {
        return this.rduFlujosGeneralesRemot.selectNotificacionesByIds(cadenaIds);
    }

    public int deleteNotificacionesByIds(List<Integer> cadenaIds) {
        return this.rduFlujosGeneralesRemot.deleteNotificacionesByIds(cadenaIds);
    }

    public List<Notificacion> selectNotificacionesByDates(String fechaInicio, String fechaFinal, Integer ultimaSemana, Integer ultimoMes, Integer idUsuarioFirmante) {
        return this.rduFlujosGeneralesRemot.getNotificacionesByDate(fechaInicio, fechaFinal, ultimaSemana, ultimoMes, idUsuarioFirmante);
    }

    public int updateNotificacionFirmada(Notificacion notificacion) {
        if (notificacion.getIdEstatus() == 3) {
            notificacion.setIdEstatus(5);
            return this.rduFlujosGeneralesRemot.updateNotificacion(notificacion);
        } else {
            return 0;
        }

    }

    public List<Promovente> selectPromoventeByPerfil(Integer idPerfil) {
        return this.rduFlujosGeneralesRemot.selectPromoventeByPerfil(idPerfil);
    }

    public int insertarNotificacion(Notificacion notificacion) {
        return this.rduFlujosGeneralesRemot.insertarNotificacion(notificacion);
    }
    
    public Firma getFirmaByExp(Long expediente, String expedienteSag) {    
        return this.rduFlujosGeneralesRemot.obternerFirmaByExpediente(expediente, expedienteSag);
    }

    public String getEmailByExp(Long id, String area) {
        try {
            //Firma firm = this.rduFlujosGeneralesRemot.obternerFirmaByExpediente(expediente, expedienteSag);
            Long idUser = null;

            TramitePatente pat = this.rduPatentesBeanRemote.selectTramite(id);
            idUser = pat.getIdUsuarioFirmante();

            Promovente prom = this.rduFlujosGeneralesRemot.selectPromovente(idUser);
            
            return prom.getEmail();


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Notificacion getNotificacionesByFolio(String folio) {
        return this.rduFlujosGeneralesRemot.getNotificacionesByFolio(folio);
    }

    public List<ExpedientesDto> obtenerExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return this.rduFlujosGeneralesRemot.obtenerExpedientesNot(usuarios, idArea, idTipoSolicitud,
                ultimaSemana, ultimoMes, fechaInicio, fechaFin);
    }
    
    public List<ExpedientesDto> obtenerExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return this.rduFlujosGeneralesRemot.obtenerExpedientesProm(usuarios, idArea, idTipoSolicitud,
                ultimaSemana, ultimoMes, fechaInicio, fechaFin);
    }

     public int deleteAnexosByIds(Long idAnexo) {
        return this.rduFlujosGeneralesRemot.deleteAnexosByIds(idAnexo);
    }

    @Override
    public ArchivoDigitalDto obtenerAcusePdf(Long idFirma) {        
        return rduFlujosGeneralesRemot.obtenerAcusePdf(idFirma);        
    }

    @Override
    public ArchivoDigitalDto obtenerAnexoXml(Long idFirma) {
        return rduFlujosGeneralesRemot.obtenerAnexoXml(idFirma);
    }

    @Override
    public ArchivoDigitalDto obtenerAcuseLogo(Long idTamite) {
        return rduFlujosGeneralesRemot.obtenerAcuseLogo(idTamite);
    }

    @Override
    public int obtenerTotalExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return rduFlujosGeneralesRemot.obtenerTotalExpedientesNot(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaFin, fechaFin);
    }

    @Override
    public int obtenerTotalExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return rduFlujosGeneralesRemot.obtenerTotalExpedientesProm(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaFin, fechaFin);
    }

    @Override
    public List<ExpedientesDto> obtenerExpedientesNotPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize) {                    
        return rduFlujosGeneralesRemot.obtenerExpedientesNotPaginados(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaFin, fechaFin,first,pageSize);
    }

    @Override
    public List<ExpedientesDto> obtenerExpedientesPromPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize) {
       StringBuilder sb = new StringBuilder();
        return rduFlujosGeneralesRemot.obtenerExpedientesPromPaginados(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaFin, fechaFin,first,pageSize);
    }  

     public List<NotificacionView> consultarNotificacionView(Long oficioSalida) {
        return this.rduFlujosGeneralesRemot.consultarNotificacionView(oficioSalida);
    }
    
    public List<NotificacionView> consultarNotificacionView(String codOficina, Long anio, Long numOficio) {
        return this.rduFlujosGeneralesRemot.consultarNotificacionView(codOficina, anio, numOficio);
    }
    
    public int deleteByTypeAnexo(Anexos anexo) {
        return this.rduFlujosGeneralesRemot.deleteByTypeAnexo(anexo);
    }
    
    public Prioridad selectPrioridadByPrimaryKey(Long idPrioridad) {
        return this.rduPatentesBeanRemote.selectPrioridadByPrimaryKey(idPrioridad);
    }
    
    public int updatePrioridadByPrimaryKey(Prioridad record) {
        return this.rduPatentesBeanRemote.updatePrioridadByPrimaryKey(record);
    }
//    //verificando funcionalidad borrado
//    public int deletePrioridadByPrimaryKey(Long idPrioridad) {
//        return this.rduPatentesBeanRemote.deletePrioridadByPrimaryKey(idPrioridad);
//    }
      
    
    public Anexos obtenerAnexosDynamic(Anexos anexo) {
        return this.rduFlujosGeneralesRemot.obtenerAnexosDynamic(anexo);
    }
    
    public boolean validarSolicitantes(List<Persona> listaSolicitantes) {
        boolean flag = true;
        
        uno : for(Persona solicitante : listaSolicitantes){
            System.out.println("Aplicar Descuento:   "+solicitante.isAplicarDescuento()+"  para el solicitante  "+solicitante.getNombrecompleto());
            if(!solicitante.isAplicarDescuento()){
                flag = false;
                break uno;
            }
        }
        
        return flag;
    }
    
    public void configfechaPresDiv(PatentesDisenoIndustrialMB formaPatentes) {
        if (formaPatentes.getTramitePat().getExpDivisional() != null && !formaPatentes.getTramitePat().getExpDivisional().equals("")) {
            try {
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(formaPatentes.getForma().getFechaActual());

                String fechaIni = "01/01/" + calendar.get(Calendar.YEAR);
                String FechaFinal = "31/12/" + formaPatentes.getTramitePat().getExpDivisional().substring(5, 9);

                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

                formaPatentes.getForma().setFechaInicial(formatoFecha.parse(fechaIni));
                formaPatentes.getForma().setMaxdatePresDiv(formatoFecha.parse(FechaFinal));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        } else {
            formaPatentes.getForma().setMaxdatePresDiv(new Date());
        }
    }
    public SolicitudPreparacionDto selectPromoByPrimaryKey(Long idTramitePromocionPatente) {
        return this.rduFlujosGeneralesRemot.selectPromoByPrimaryKey(idTramitePromocionPatente);
    }
    
    public void crearAnexoHojaDescuento(Integer flagFechas, Persona solicitante, List<Persona> apoderados, Long tramiteId, String convertido,String firmante, String selloFirmante,Integer tipoSolicitud) {
        GenerarReporte generarReporte = new GenerarReporte();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        List<Persona> personaApoderado = new ArrayList<Persona>();
        for (Persona perApoderado : apoderados) {
            if (perApoderado.getPrincipal() != null && perApoderado.getPrincipal() == 1) {
                personaApoderado.add(perApoderado);
                break;
            }
        }

 //       SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();

//        String usuarioPASE = obtSession.getUsuario().getNombre() + " "
//                + obtSession.getUsuario().getApellidoPaterno() + " "
//                + obtSession.getUsuario().getApellidoMaterno();
//
//        String primerApellido = "";
//        String segundoApellido = "";
//        
//        primerApellido = solicitante.getPrimerApellido() == null ? "" : solicitante.getPrimerApellido().trim();
//        segundoApellido = solicitante.getSegundoApellido() == null ? "" : solicitante.getSegundoApellido().trim();
//
//        String nombreSolicitante = solicitante.getNombrecompleto() + " " + primerApellido + " " + segundoApellido;
//
//        usuarioPASE += " por poder de\n" + nombreSolicitante;
//
//        hojaDescuento hojaDescuento = new hojaDescuento();
//        hojaDescuento.setTipo(solicitante.getTipoSolicitante().getIdTipoSolicitante().intValue());
//        hojaDescuento.setNombre(usuarioPASE);
//        hojaDescuento.setNombreApoderado("");
        //Se agrega codigo para la validadicion del firmante
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
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
        }
        
        hojaDescuento hojaDescuento = new hojaDescuento();
        hojaDescuento.setTipo(solicitante.getTipoSolicitante().getIdTipoSolicitante().intValue());
        hojaDescuento.setNombre(usuarioPASE);
        hojaDescuento.setNombreApoderado("");
        
        if  (tipoSolicitud == Constantes.TIPO_SOL_BUSQUEDA_SIT.intValue())
        {
            hojaDescuento.setTipoSolicitud(Constantes.TIPO_SOLICITUD_BU);
        }else if  (tipoSolicitud == Constantes.TIPO_SOL_VIGILANCIA_SIT.intValue())
        {
            hojaDescuento.setTipoSolicitud(Constantes.TIPO_SOLICITUD_VI);
        }else
        {
            hojaDescuento.setTipoSolicitud(Constantes.TIPO_SOLICITUD_BU);
        }
        hojaDescuento.setFlag(flagFechas);
        
        String[] result = convertido.split("/");
        hojaDescuento.setDia(result[2]);
        hojaDescuento.setMes(result[1]);
        hojaDescuento.setAnio(result[0]);
        hojaDescuento.setNombreFirmante(firmante);
        hojaDescuento.setSelloSolicitante(selloFirmante);

        hojaDescuento.setImgFirmaImpi(request.getRealPath("") + "/content/imagenes/firma_impi.png");

        ByteArrayOutputStream byt = generarReporte.generarHojaDescuentoPdf(request.getRealPath("")
                + "/content/reportes/impi_Hoja_descuento.jasper", hojaDescuento);

        byte[] archivoAnexo = byt.toByteArray();

        List<AnexosViewDto> anexoDescuento = new ArrayList<AnexosViewDto>();

        AnexosViewDto nvoAnexo = new AnexosViewDto();
        nvoAnexo.setArchivoAnexo(archivoAnexo);
        nvoAnexo.setExtension("pdf");
        nvoAnexo.setIdTipoanexo(Constantes.ANEXO_HOJA_DESCUENT0);
        nvoAnexo.setIdTramitePatente(tramiteId);
        nvoAnexo.setNombreArchivo("hojaDescuento.pdf");
        nvoAnexo.setCargado(true);

        anexoDescuento.add(nvoAnexo);

        int res = insertarAnexosDtosPatentes(anexoDescuento);
    }

    @Override
    public Solicitud selectByExpedienteDivisional(String oficina, Integer numExp, Integer serExp, String tipExp) {
       return this.rduFlujosGeneralesRemot.selectByExpedienteDivisional(oficina,numExp,serExp,tipExp);
    }

    @Override
    public int insert(KffoliosNotificacion kffoliosNotificacion) {
        return this.rduFlujosGeneralesRemot.insert(kffoliosNotificacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertSelective(KffoliosNotificacion kffoliosNotificacion) {
        return this.rduFlujosGeneralesRemot.insertSelective(kffoliosNotificacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<KffoliosNotificacion> selectByOficioSalida(String codbarras) {
        return this.rduFlujosGeneralesRemot.selectByOficioSalida(codbarras); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(KfFolios kfFolios) {
        return this.rduFlujosGeneralesRemot.insert(kfFolios); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<KfFolios> selectKfFoliosByCodbarras(String codbarras) {
        return this.rduFlujosGeneralesRemot.selectKfFoliosByCodbarras(codbarras); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(KfAlmacenar kfAlmacenar) {
        return this.rduFlujosGeneralesRemot.insert(kfAlmacenar); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<KfAlmacenar> selectKfAlmacenarByCodbarras(String codbarras) {
        return this.rduFlujosGeneralesRemot.selectKfAlmacenarByCodbarras(codbarras); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<KfAlmacenar> selectKfAlmacenarByTitle(String title) {
        return this.rduFlujosGeneralesRemot.selectKfAlmacenarByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(KfContenedores kfContenedores) {
        return this.rduFlujosGeneralesRemot.insert(kfContenedores); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<KfContenedores> selectKfContenedoresByTitle(String title) {
        return this.rduFlujosGeneralesRemot.selectKfContenedoresByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public List<KfContenedores> selectKfContenedoresByPC(String pc) {
        return this.rduFlujosGeneralesRemot.selectKfContenedoresByPC(pc); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(SolicitudInteresados solicitudInteresados) {
        return this.rduFlujosGeneralesRemot.insert(solicitudInteresados); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SolicitudInteresados> selectSolicitudInteresadosByTitle(String title) {
        return this.rduFlujosGeneralesRemot.selectSolicitudInteresadosByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresado(Integer codInteresado) {
        return this.rduFlujosGeneralesRemot.selectSolicitudInteresadosByCodInteresado(codInteresado); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(TiposRelacion tiposRelacion) {
        return this.rduFlujosGeneralesRemot.insert(tiposRelacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TiposRelacion> selectTiposRelacionByCodRelacion(Integer codRelacion) {
        return this.rduFlujosGeneralesRemot.selectTiposRelacionByCodRelacion(codRelacion); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insert(UsuariosSigappi usuariosSigappi) {
        return this.rduFlujosGeneralesRemot.insert(usuariosSigappi); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UsuariosSigappi> selectUsuariosSigappiByCveUsuario(String cveUsuario) {
        return this.rduFlujosGeneralesRemot.selectUsuariosSigappiByCveUsuario(cveUsuario); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TiposRelacion> listTiposRelacion() {
        return this.rduFlujosGeneralesRemot.listTiposRelacion();
    }
    
    @Override
    public List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresadoAndSecuencia(Integer codInteresado, Integer secuencia) {
        return this.rduFlujosGeneralesRemot.selectSolicitudInteresadosByCodInteresadoAndSecuencia(codInteresado, secuencia);
    }

    @Override
    public int updateNotificationSubscription(String title, Integer codInteresado, Integer secuencia) {
        return this.rduFlujosGeneralesRemot.updateNotificationSubscription(title, codInteresado, secuencia);
    }

}

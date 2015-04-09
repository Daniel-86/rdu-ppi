/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.dto.PromocionesConOficioDto;
import mx.gob.impi.rdu.dto.ReportesDto;
import mx.gob.impi.rdu.exposition.form.CapturaSolicitudForm;
import mx.gob.impi.rdu.exposition.patentes.PatentesDisenoIndustrialMB;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.CatAreaPromPatentes;
import mx.gob.impi.rdu.persistence.model.CatTipoPromPatentes;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.PromocionesPatentes;
import mx.gob.impi.rdu.persistence.model.TipoPromPatByOficio;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPct;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPctMU;
import mx.gob.impi.sagpat.persistence.model.Solicitud;

/**
 *
 * @author adriana
 */
public interface PatentesViewService {

    public boolean guardar(PatentesDisenoIndustrialMB forma);

    TramitePatente selectTramite(Long idTramitePatente);

    public boolean recuperarTramite(PatentesDisenoIndustrialMB forma);

    public boolean actualizaTramite(PatentesDisenoIndustrialMB forma) throws Exception;

    TramitePatente obtenerTramitePatenteById(Long idTramite) throws Exception;

    TramitePatente obtenerTramitePromocioneById(Long idTramite) throws Exception;

    int updatePatenteSigned(Long idTramite);

    public List<Persona> selectSolicitanteTramitePatente(Long idPatente);

    List<Solicitud> obtenerExpedienteDivisional(Solicitud exp);
    
    //public List<Solicitud> buscaExpedienteDivisional(String oficina, Integer numExp, Short serExp, String tipExp);

    public DatosSolicitudPct consultarDatosPCt(String idSolicitudPct);
    public List<DatosSolicitudPctMU> consultarDatosPCtMU(String idSolicitudPct);
    
    
    /// Metodos de MEnsajes Patentes
    public String getCatalogoJms(String nombreCatalogo);
    public String getCatTipoPromocionJms(String nombreCatalogo); 
    public String getTipoPromByOficioJms(String idOficio);
    public String setTramitePromoPatJms(PromocionesPatentes promocionesPatentes);
    
    public List<CatAreaPromPatentes> getCatalogo(String cadenaXML);
    public List<CatTipoPromPatentes> getCatTipoPromPat(String cadenaXML);
    public List<TipoPromPatByOficio> getTipoPromByOficio(String cadenaXML);
    public String setTramitePromoPatJmsAdmin(String cadenaXML);
    
    //*********************
    List<CatTipoPromPatentes> selectTipoPromociones();
    PromocionesConOficioDto getNumeroOficio(PromocionesConOficioDto promocionesConOficio);
    public Integer ordenarAnexos(Anexos anex);
    public Integer ordenarAnexosTraduccion(Anexos anex);
    public List<ReportesDto> ordenarReporte(List<ReportesDto> listReportes);
    public List<ReportesDto> reordenarReporte(List<ReportesDto> listReportes);
    public void habilitarSolicitante(PatentesDisenoIndustrialMB forma);
    void actualizarPersona(Persona persona);
    void selectDescText(PatentesDisenoIndustrialMB forma);
    void selectDescPdf(PatentesDisenoIndustrialMB forma);
    void selectResuText(PatentesDisenoIndustrialMB forma);
    void selectResuPdf(PatentesDisenoIndustrialMB forma);
    public String validarSolicitud (TramitePatente tramitePat, CapturaSolicitudForm forma, List<Persona> listaSolicitantes,
            Persona selectedSolicitante, List<Persona> listaInventores, Persona selectedApoderado);
      
}

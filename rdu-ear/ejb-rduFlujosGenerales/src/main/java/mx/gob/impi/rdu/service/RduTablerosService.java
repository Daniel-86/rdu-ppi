package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.dto.ArchivoDigitalDto;
import mx.gob.impi.rdu.dto.ExpedientesDto;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.dto.TramiteDto;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.Pago;
import mx.gob.impi.rdu.persistence.model.Tramite;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;

public interface RduTablerosService {

    public List<SolicitudPreparacionDto> extraerSolicitudesPreparacion(List<Integer> usuarios, int idTipoTramite);

    public List<Tramite> cargarSolicitudes(long idUsuario);

    public long copiarSolicitud(Tramite prmTramite);

    public TramiteDto obtenerSolicitudes(int idTramite);

    public int actualizarSolicitud(Tramite prmTramite);

    public int insertarpago(Pago pago);

    public List<Pago> buscarPago(long pago);

    public int actualizarPago(Pago pago);

    List<ExpedientesDto> obtenerExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);

    List<ExpedientesDto> obtenerExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);

    public List<ExpedientesDto> obtenerExpedientesPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);

    public Integer obtenerTotalExpedientesPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    public List<Tramite> selectTramite();

    public int actualizarPromocionMarca(TramitePromocionMarca tramite);
    
    public ArchivoDigitalDto obtenerAcusePdf(Long idFirma);
    
    ArchivoDigitalDto obtenerAnexoXml(Long idFirma);
    
    ArchivoDigitalDto obtenerAcuseLogo(Long idTamite);
    
    public List<ExpedientesDto> obtenerExpedientesPaginadosPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, int start, int maxPerPage);
    
    List<ExpedientesDto> obtenerExpedientesNotPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize);
    
    List<ExpedientesDto> obtenerExpedientesPromPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize);
    
    public int obtenerTotalExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    public int obtenerTotalExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    SolicitudPreparacionDto selectPromoByPrimaryKey(Long idTramitePromocionPatente);
    
}

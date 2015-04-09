package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.dto.ArchivoDigitalDto;
import mx.gob.impi.rdu.persistence.model.Tramite;
import mx.gob.impi.rdu.dto.ExpedientesDto;
import org.apache.ibatis.annotations.Param;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;


public interface TramiteMapper {

    int deleteByPrimaryKey(Integer idTramite);

    int insert(Tramite record);

    int insertSelective(Tramite record);

    Tramite selectByPrimaryKey(Long idTramite);

    Tramite selectDatosBasicos(Long idTramite);

    int updateByPrimaryKeySelective(Tramite record);

    int updateByPrimaryKey(Tramite record);

    List<Tramite> selectByUsuario(Integer idUsuariocaptura);

    int borradoLogico(@Param("idTramite") long idTramite, @Param("indActivo") int indActivo);

    List<Tramite> getTramiteByCriteria(Tramite tramite);

    List<ExpedientesDto> expedienteNotByIdUsuario(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);

    List<ExpedientesDto> expedientesPromByIdUsuario(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);

    List<ExpedientesDto> expedientesByUsuario(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);
    
    List<ExpedientesDto> expedientesPatentesByUsuario(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);
    
    List<ExpedientesDto> expedientesMarcasByUsuario(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);
    
    Integer obtenerTotalExpedientesPatentesByUsuario(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);
        
    public List<ExpedientesDto> expedientesPatentesPaginadosByUsuario(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin,
            @Param("start") int start, @Param("maxPerPage") int maxPerPage);    
    
    List<ExpedientesDto> obtenerExpedientesNotPaginadosByUsuario(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin,
            @Param("start") int start, @Param("maxPerPage") int maxPerPage);
    
    List<ExpedientesDto> obtenerExpedientesPromPaginadosByUsuario(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin,
            @Param("start") int start, @Param("maxPerPage") int maxPerPage);
    
    public int obtenerTotalExpedientesNot(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin);
    
    public int obtenerTotalExpedientesProm(@Param("usuarios") List<Integer> usuarios,
            @Param("idArea") Integer idArea, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("ultimaSemana") Integer ultimaSemana,
            @Param("ultimoMes") Integer ultimoMes, @Param("fechaInicio") String fechaInicio, @Param("fechaFin") String fechaFin );

    
    ArchivoDigitalDto obtenerAcusePdf(Long idFirma);
    
    ArchivoDigitalDto obtenerAnexoXml(Long idFirma);
    
    ArchivoDigitalDto obtenerAcuseLogo(Long idTamite);


    List<Tramite> selectTramite();

    public int selectsecuencia();

    List<SolicitudPreparacionDto> solicitudesByUsuarios(@Param("usuarios") List<Integer> usuarios, @Param("idTipoTramite") long idTipoTramite);
    
    
}

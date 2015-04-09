package mx.gob.impi.rdu.persistence.mappers;

import java.math.BigDecimal;
import java.util.List;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;
import mx.gob.impi.rdu.persistence.model.TramitePromocionPatente;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author JBMM
 */
public interface TramitePromocionPatenteMapper {
    int deleteByPrimaryKey(BigDecimal idTramitePromocionMarca);

    int insertTramitePromocion(TramitePromocionPatente tramitePromocionPatente);
    
    int insertTramitePromocionPatente(TramitePromocionPatente tramitePromocionPatente);
    
    int updateTramitePromocion(TramitePromocionPatente tramitePromocionPatente);
    
    int updateTramitePromocionPatente(TramitePromocionPatente tramitePromocionPatente);

    SolicitudPreparacionDto selectByPrimaryKey(Long idTramitePromocionPatente);

    int updateByPrimaryKeySelective(SolicitudPreparacionDto record);

    int updateByPrimaryKey(TramitePromocionMarca record);
    
    List<TramitePromocionMarca> selectByUsuarioTramites(Integer idUsuariocaptura);

    List<SolicitudPreparacionDto> solicitudesByUsuarios(@Param("usuarios") List<Integer> usuarios, @Param("idTipoTramite") long idTipoTramite);

    List<SolicitudPreparacionDto> promocionesByUsuarios(@Param("usuarios") List<Integer> usuarios, @Param("idTipoTramite") long idTipoTramite);

    int eliminacionLogica(SolicitudPreparacionDto solicitudPreparacionDto);
    
    int existePromocion(int idPromocion);
}
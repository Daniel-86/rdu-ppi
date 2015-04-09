package mx.gob.impi.rdu.persistence.mappers;

import java.math.BigDecimal;
import java.util.List;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author JBMM
 */
public interface TramitePromocionMarcaMapper {
    int deleteByPrimaryKey(BigDecimal idTramitePromocionMarca);

    int insert(TramitePromocionMarca tramitePromocionMarca);

    TramitePromocionMarca selectByPrimaryKey(BigDecimal idTramitePromocionMarca);

    int updateByPrimaryKeySelective(TramitePromocionMarca record);

    int updateByPrimaryKey(TramitePromocionMarca record);
    
    List<TramitePromocionMarca> selectByUsuarioTramites(Integer idUsuariocaptura);

    List<SolicitudPreparacionDto> solicitudesByUsuarios(@Param("usuarios") List<Integer> usuarios, @Param("idTipoTramite") long idTipoTramite);
}
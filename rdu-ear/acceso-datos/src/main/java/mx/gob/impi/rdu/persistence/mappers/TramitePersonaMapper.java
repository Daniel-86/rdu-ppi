package mx.gob.impi.rdu.persistence.mappers;

import java.math.BigDecimal;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.TramitePersona;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author JBMM
 */
public interface TramitePersonaMapper {

    int deleteByPrimaryKey(@Param("idTramite") Long idTramite, @Param("clasePersona") Long clasePersona);

    int insert(TramitePersona tramitePersona);

    List<TramitePersona> selectPatentes(@Param("idTramite") Long idTramite,
            @Param("idClasePersona") Long idClasePersona);

    List<TramitePersona> selectByPrimaryKey(@Param("idTramite") Long idTramite,
            @Param("idClasePersona") Long idClasePersona);

    List<TramitePersona> selectByTramite(BigDecimal idTramitePromocionMarca);

    int updateByPrimaryKeySelective(TramitePersona record);

    int updateByPrimaryKey(TramitePersona record);
}

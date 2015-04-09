package mx.gob.impi.rdu.persistence.mappers;

import java.math.BigDecimal;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.NumerosSolicitud;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author JBMM
 */
public interface NumerosSolicitudMapper {
    int deleteByPrimaryKey(@Param("idTramitePromocionMarca") BigDecimal idTramitePromocionMarca,
                           @Param("numero") Long numero);

    int insert(NumerosSolicitud numerosSolicitud);

    NumerosSolicitud selectByPrimaryKey(@Param("idTramitePromocionMarca") BigDecimal idTramitePromocionMarca,
                                        @Param("numero") Long numero);

    List<NumerosSolicitud> selectByTramite(BigDecimal idTramitePromocionMarca);

    int updateByPrimaryKeySelective(NumerosSolicitud record);

    int updateByPrimaryKey(NumerosSolicitud record);

    int eliminarNumerosByTramite(BigDecimal idTramitePromocionMarca);
}
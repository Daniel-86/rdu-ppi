package mx.gob.impi.rdu.persistence.mappers;

import java.math.BigDecimal;
import mx.gob.impi.rdu.persistence.model.EstablecimientoXPromomarca;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author JBMM
 */
public interface EstablecimientoXPromomarcaMapper {
    int deleteByPrimaryKey(@Param("idDomicilio") BigDecimal idDomicilio,
                    @Param("idTramitePromocionMarca") BigDecimal idTramitePromocionMarca);

    int insert(EstablecimientoXPromomarca establecimiento);

    EstablecimientoXPromomarca select(BigDecimal idDomicilio);
}
package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.EstablecimientoXTramite;
import org.apache.ibatis.annotations.Param;

public interface EstablecimientoXTramiteMapper {
    int deleteByPrimaryKey(EstablecimientoXTramite record);

    int insert(EstablecimientoXTramite record);

    int insertSelective(EstablecimientoXTramite record);

    EstablecimientoXTramite getEstablecimientoByTramite(Long idTramite);
}
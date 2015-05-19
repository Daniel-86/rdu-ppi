package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import org.apache.ibatis.annotations.Param;



public interface KffoliosNotificacionMapper {
    int insert(mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion record);

    int insertSelective(mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion record);
    
    List<KffoliosNotificacion> selectByOficioSalida(@Param("codbarras")String codbarras);
    
    List<KffoliosNotificacion>  selectANotificarByCodInteresado(@Param("codInteresado")Integer codInteresado);
}
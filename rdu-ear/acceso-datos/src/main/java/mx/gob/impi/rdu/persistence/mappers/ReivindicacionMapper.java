package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Reivindicacion;

public interface ReivindicacionMapper {

    int deleteByTramite(Long idReivindicacion);

    List<Reivindicacion> selectSelective(Reivindicacion record);

    int updateByPrimaryKeySelective(Reivindicacion record);

    int updateByPrimaryKey(Reivindicacion record);

    int insertSelective(Reivindicacion record);
}

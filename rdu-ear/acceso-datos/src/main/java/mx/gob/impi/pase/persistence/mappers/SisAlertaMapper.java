package mx.gob.impi.pase.persistence.mappers;

import java.math.BigDecimal;
import java.util.List;
import mx.gob.impi.pase.persistence.model.SisAlerta;

public interface SisAlertaMapper {
    int deleteByPrimaryKey(Short idAlerta);

    SisAlerta selectByPrimaryKey(Short idAlerta);

    int updateByPrimaryKeySelective(SisAlerta record);

    int updateByPrimaryKey(SisAlerta record);

    int insertarAlertas(SisAlerta alerta);
    List<SisAlerta> selectAlertas(SisAlerta alerta);
}
package mx.gob.impi.pase.persistence.mappers;

import mx.gob.impi.pase.persistence.model.CatMotivoAlerta;

public interface CatMotivoAlertaMapper {
    int deleteByPrimaryKey(Short idMotivoAlerta);

    CatMotivoAlerta selectByPrimaryKey(Short idMotivoAlerta);

    int updateByPrimaryKeySelective(CatMotivoAlerta record);

    int updateByPrimaryKey(CatMotivoAlerta record);
}

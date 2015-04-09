package mx.gob.impi.sagpat.persistence.mappers;

import mx.gob.impi.sagpat.persistence.model.ThisControl;

public interface ThisControlMapper {

    int insert(ThisControl record);

    ThisControl selectByPrimaryKey(ThisControl tableName);

    int updateByVariable(ThisControl control);

    int insertSelective(ThisControl record);
}
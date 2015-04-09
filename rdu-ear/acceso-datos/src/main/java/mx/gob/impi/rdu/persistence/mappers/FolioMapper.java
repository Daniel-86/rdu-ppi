package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.Folio;

public interface FolioMapper {
    int deleteByPrimaryKey(Long idFolio);

    int insert(Folio record);

    int insertSelective(Folio record);

    Folio selectByPrimaryKey(Long idFolio);

    int updateByPrimaryKeySelective(Folio record);

    Folio selectDynamicFolio(Folio folio);    
    
    int updateByPrimaryKey(Folio record);
}
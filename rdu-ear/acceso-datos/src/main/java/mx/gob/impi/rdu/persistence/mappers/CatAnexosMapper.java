package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.CatAnexos;
import java.util.List;


public interface CatAnexosMapper {
    int deleteByPrimaryKey(Integer idTipoanexo);

    int insert(CatAnexos record);

    int insertSelective(CatAnexos record);

    CatAnexos selectByPrimaryKey(Integer idTipoanexo);

    int updateByPrimaryKeySelective(CatAnexos record);

    int updateByPrimaryKey(CatAnexos record);
    
    List<CatAnexos> selectAnexos();
    
    List<CatAnexos> selectAnexosApoderado();
    
}
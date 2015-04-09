package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.CatArea;
import java.util.List;

public interface CatAreaMapper {
    int deleteByPrimaryKey(Integer idArea);

    int insert(CatArea record);

    int insertSelective(CatArea record);

    CatArea selectByPrimaryKey(Integer idArea);

    List<CatArea> selectAll();
    
    int updateByPrimaryKeySelective(CatArea record);

    int updateByPrimaryKey(CatArea record);
}
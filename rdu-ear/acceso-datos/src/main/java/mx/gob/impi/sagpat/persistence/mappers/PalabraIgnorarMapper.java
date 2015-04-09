package mx.gob.impi.sagpat.persistence.mappers;

import java.util.List;
import mx.gob.impi.sagpat.persistence.model.PalabraIgnorar;





public interface PalabraIgnorarMapper {
    
    int deleteByPrimaryKey(String palabra);

    PalabraIgnorar selectByPrimaryKey(String palabra);
    
    List<PalabraIgnorar>selectAll(String palabra);

    int updateByPrimaryKeySelective(PalabraIgnorar record);

    int updateByPrimaryKey(PalabraIgnorar record);
}
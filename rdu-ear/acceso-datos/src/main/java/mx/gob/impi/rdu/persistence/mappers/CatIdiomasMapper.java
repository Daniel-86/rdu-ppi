package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CatIdiomas;

/**
 *
 * @author JBMM
 */
public interface CatIdiomasMapper {
    
    List<CatIdiomas> selectAll();
    
}
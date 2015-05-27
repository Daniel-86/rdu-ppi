package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import org.apache.ibatis.annotations.Param;



public interface KfContenedoresMapper {
    int insert(mx.gob.impi.sigappi.persistence.model.KfContenedores record);

    
    List<KfContenedores> selectByTitle(@Param("title")String title);
    List<KfContenedores> selectByPC(@Param("pc")String pc);
    
    KfContenedores findByTitleOrPc(@Param("id")String id);
}
package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;

public interface ImagenDibujoMapper {

    int deleteByPrimaryKey(Long idTramite);

    List<ImagenDibujo> selectSelective(ImagenDibujo record);

    int updateByPrimaryKeySelective(ImagenDibujo record);

    int insertSelective(ImagenDibujo record);
}

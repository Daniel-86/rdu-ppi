package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.BitacoraErrores;

/**
 *
 * @author JBMM
 */
public interface BitacoraErroresMapper {
    int deleteByPrimaryKey(Short idError);

    BitacoraErrores selectByPrimaryKey(Short idError);

    int updateByPrimaryKeySelective(BitacoraErrores record);

    int updateByPrimaryKey(BitacoraErrores record);

    List<BitacoraErrores> selectBitacora();
    int insertBitacora(BitacoraErrores bitacora);

}
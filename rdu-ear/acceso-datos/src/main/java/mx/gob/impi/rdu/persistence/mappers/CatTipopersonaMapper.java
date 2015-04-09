package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CatTipopersona;

public interface CatTipopersonaMapper {
    int deleteByPrimaryKey(Short idTipopersona);

    int insert(CatTipopersona record);

    int insertSelective(CatTipopersona record);

    CatTipopersona selectByPrimaryKey(Short idTipopersona);

    int updateByPrimaryKeySelective(CatTipopersona record);

    int updateByPrimaryKey(CatTipopersona record);

    List<CatTipopersona> selectDynamic(CatTipopersona catTipopersona);
}
package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Firma;
import org.apache.ibatis.annotations.Param;

public interface FirmaMapper {

    int deleteByPrimaryKey(Integer idFirma);

    int insert(Firma record);

    int insertSelective(Firma record);

    Firma selectByPrimaryKey(Long idFirma);

    Firma selectByTramite(Long idTramite);

    int updateByPrimaryKeySelective(Firma record);

    int updateByPrimaryKeyWithBLOBs(Firma record);

    int updateByPrimaryKey(Firma record);

    List<Firma> selectFirma();
    
    Firma  selectByExpediente(@Param("expediente") Long expediente, @Param("expedienteSag") String expedienteSag);
}

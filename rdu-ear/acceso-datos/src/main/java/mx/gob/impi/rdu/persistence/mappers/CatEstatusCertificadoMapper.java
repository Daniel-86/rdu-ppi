package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CatEstatusCertificado;



public interface CatEstatusCertificadoMapper {
    int deleteByPrimaryKey(Integer idEstatusCertificado);

    int insert(CatEstatusCertificado record);

    int insertSelective(CatEstatusCertificado record);

    CatEstatusCertificado selectByPrimaryKey(Integer idEstatusCertificado);
    
    List<CatEstatusCertificado> loadAllEstatusCertificado();

    int updateByPrimaryKeySelective(CatEstatusCertificado record);

    int updateByPrimaryKey(CatEstatusCertificado record);
}
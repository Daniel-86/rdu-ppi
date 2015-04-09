package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Certificado;



public interface CertificadoMapper {
    int deleteByPrimaryKey(Short idFirmaAdmin);

    int insert(Certificado record);

    int insertSelective(Certificado record);

    Certificado selectByPrimaryKey(Long idFirmaAdmin);

    Certificado selectByEstatusArea(Certificado certificado);    
    
    List<Certificado> loadAllActiveCerts(Integer idArea);
    
    int updateByPrimaryKeySelective(Certificado record);

    int updateByPrimaryKeyWithBLOBs(Certificado record);

    int updateByPrimaryKey(Certificado record);
}
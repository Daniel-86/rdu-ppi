package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.dto.DomicilioDto;
import mx.gob.impi.rdu.persistence.model.Domicilio;

public interface DomicilioMapper {
    int deleteByPrimaryKey(Long idDomicilio);

    int insert(Domicilio record);

    int insertSelective(Domicilio record);

    Domicilio selectByPrimaryKey(Long idDomicilio);

    int updateByPrimaryKeySelective(Domicilio record);

    int updateByPrimaryKey(Domicilio record);   

    List<DomicilioDto> getDomiciliosDtoByCriteria(DomicilioDto domicilio);

}
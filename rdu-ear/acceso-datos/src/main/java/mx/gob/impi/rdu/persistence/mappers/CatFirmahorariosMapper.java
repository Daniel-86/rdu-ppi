package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CatFirmahorarios;
        


public interface CatFirmahorariosMapper {
    int deleteByPrimaryKey(Integer idFirmahorarios);

    int insert(CatFirmahorarios record);

    int insertSelective(CatFirmahorarios record);

    CatFirmahorarios selectByPrimaryKey(Integer idFirmahorarios);

    int updateByPrimaryKeySelective(CatFirmahorarios record);

    int updateByPrimaryKey(CatFirmahorarios record);
    
    List <CatFirmahorarios> selectByRule(CatFirmahorarios record);
    List <CatFirmahorarios> selectHorarios(CatFirmahorarios horario);
}
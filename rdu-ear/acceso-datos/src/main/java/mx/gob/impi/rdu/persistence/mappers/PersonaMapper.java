package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Persona;

public interface PersonaMapper {

    int deleteByPrimaryKey(Long idSolicitante);

    int insert(Persona record);

    int insertSelective(Persona record);

    Persona selectByPrimaryKey(Long idSolicitante);

    int updateByPrimaryKeySelective(Persona record);

    int updateByPrimaryKey(Persona record);

    List<Persona> selectDynamic(Persona solicitante);

    List<Persona> selectApoderados(Long idTramite);

    List<Persona> selectPersonasNot(Long idTramite);
    List<Persona> selectSolicitanteTramitePatente(Long idTramite);
    List<Persona> selectInventorTramitePatente(Long idTramite);
    List<Persona> selectSolicitanteTramiteMarcas(Long idTramite);
    
    int insertPersona(Persona record);

}

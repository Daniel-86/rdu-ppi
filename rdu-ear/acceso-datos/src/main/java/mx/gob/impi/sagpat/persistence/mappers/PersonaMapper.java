package mx.gob.impi.sagpat.persistence.mappers;

import mx.gob.impi.sagpat.persistence.model.Persona;



public interface PersonaMapper {
    int deleteByPrimaryKey(Integer codPersona);

    int insert(Persona record);

    int insertSelective(Persona record);

    Persona selectByPrimaryKey(Integer codPersona);
    Persona selectViewByPrimaryKey(Integer codPersona);
    
    String selectPersonaCodByDot(String nomPersona);
    
    String selectPersonaCod(String nomPersona);

    int updateByPrimaryKeySelective(Persona record);

    int updateByPrimaryKey(Persona record);
    
    String selectPersonaNombreCaracter(Persona record);
}
package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.Datoscontacto;

public interface DatoscontactoMapper {
    int deleteByPrimaryKey(Long idDatoscontacto);

    int insert(Datoscontacto record);

    int insertSelective(Datoscontacto record);

    Datoscontacto selectByPrimaryKey(Long idDatoscontacto);

    int updateByPrimaryKeySelective(Datoscontacto record);

    int updateByPrimaryKey(Datoscontacto record);
}
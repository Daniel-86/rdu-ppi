package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.Expediente;
import org.apache.ibatis.annotations.Param;

public interface ExpedienteMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int insert(Expediente record);

    int insertSelective(Expediente record);

    Expediente selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(Expediente record);

    int updateByPrimaryKey(Expediente record);
}
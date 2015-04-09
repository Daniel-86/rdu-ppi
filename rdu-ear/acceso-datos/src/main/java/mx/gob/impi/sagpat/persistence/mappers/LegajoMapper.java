package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.Legajo;
import org.apache.ibatis.annotations.Param;

public interface LegajoMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("secLegajo") Short secLegajo, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int insert(Legajo record);

    int insertSelective(Legajo record);

    Legajo selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("secLegajo") Short secLegajo, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(Legajo record);

    int updateByPrimaryKey(Legajo record);
}
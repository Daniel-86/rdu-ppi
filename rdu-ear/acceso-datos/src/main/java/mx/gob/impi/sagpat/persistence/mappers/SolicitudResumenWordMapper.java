package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.SolicitudResumenWord;
import org.apache.ibatis.annotations.Param;

public interface SolicitudResumenWordMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPais") String codPais, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int insert(SolicitudResumenWord record);

    int insertSelective(SolicitudResumenWord record);

    SolicitudResumenWord selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPais") String codPais, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudResumenWord record);

    int updateByPrimaryKeyWithBLOBs(SolicitudResumenWord record);

    int updateByPrimaryKey(SolicitudResumenWord record);
}
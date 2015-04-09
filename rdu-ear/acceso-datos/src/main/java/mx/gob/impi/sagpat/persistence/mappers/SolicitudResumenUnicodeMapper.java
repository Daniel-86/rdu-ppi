package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.SolicitudResumenUnicode;
import org.apache.ibatis.annotations.Param;

public interface SolicitudResumenUnicodeMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPais") String codPais, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    SolicitudResumenUnicode selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPais") String codPais, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudResumenUnicode record);
    
    int insert(SolicitudResumenUnicode record);

    int updateByPrimaryKeyWithBLOBs(SolicitudResumenUnicode record);

    int updateByPrimaryKey(SolicitudResumenUnicode record);
}
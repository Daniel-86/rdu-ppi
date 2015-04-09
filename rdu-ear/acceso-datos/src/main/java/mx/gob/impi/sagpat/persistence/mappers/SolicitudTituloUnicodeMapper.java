package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.SolicitudTituloUnicode;
import org.apache.ibatis.annotations.Param;

public interface SolicitudTituloUnicodeMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPais") String codPais, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    SolicitudTituloUnicode selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPais") String codPais, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudTituloUnicode record);
    
    int insert(SolicitudTituloUnicode record);

    int updateByPrimaryKeyWithBLOBs(SolicitudTituloUnicode record);

    int updateByPrimaryKey(SolicitudTituloUnicode record);
}
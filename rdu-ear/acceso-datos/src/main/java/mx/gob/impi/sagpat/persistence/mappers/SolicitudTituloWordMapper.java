package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.SolicitudTituloWord;
import org.apache.ibatis.annotations.Param;

public interface SolicitudTituloWordMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPais") String codPais, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int insert(SolicitudTituloWord record);

    int insertSelective(SolicitudTituloWord record);

    SolicitudTituloWord selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPais") String codPais, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudTituloWord record);

    int updateByPrimaryKeyWithBLOBs(SolicitudTituloWord record);

    int updateByPrimaryKey(SolicitudTituloWord record);
}
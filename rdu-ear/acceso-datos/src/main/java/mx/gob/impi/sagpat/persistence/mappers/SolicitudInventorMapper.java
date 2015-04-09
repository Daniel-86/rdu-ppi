package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.SolicitudInventor;
import org.apache.ibatis.annotations.Param;

public interface SolicitudInventorMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPersona") Integer codPersona, @Param("numExped") Integer numExped, @Param("secDireccion") Short secDireccion, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int insert(SolicitudInventor record);

    int insertSelective(SolicitudInventor record);

    SolicitudInventor selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPersona") Integer codPersona, @Param("numExped") Integer numExped, @Param("secDireccion") Short secDireccion, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudInventor record);

    int updateByPrimaryKey(SolicitudInventor record);
}
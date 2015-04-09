package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.SolicitudApoderado;
import org.apache.ibatis.annotations.Param;

public interface SolicitudApoderadoMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPersona") Integer codPersona, @Param("numExped") Integer numExped, @Param("secDireccion") Short secDireccion, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int insert(SolicitudApoderado record);

    int insertSelective(SolicitudApoderado record);

    SolicitudApoderado selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPersona") Integer codPersona, @Param("numExped") Integer numExped, @Param("secDireccion") Short secDireccion, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudApoderado record);

    int updateByPrimaryKey(SolicitudApoderado record);
}
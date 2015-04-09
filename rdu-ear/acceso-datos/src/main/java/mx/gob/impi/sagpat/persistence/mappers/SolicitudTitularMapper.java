package mx.gob.impi.sagpat.persistence.mappers;


import java.util.List;
import mx.gob.impi.sagpat.persistence.model.SolicitudTitular;
import mx.gob.impi.sagpat.persistence.model.SolicitudTitularDto;
import org.apache.ibatis.annotations.Param;

public interface SolicitudTitularMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPersona") Integer codPersona, @Param("numExped") Integer numExped, @Param("secDireccion") Short secDireccion, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int insert(SolicitudTitular record);

    int insertSelective(SolicitudTitular record);

    SolicitudTitular selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPersona") Integer codPersona, @Param("numExped") Integer numExped, @Param("secDireccion") Short secDireccion, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudTitular record);

    int updateByPrimaryKey(SolicitudTitular record);
    List<SolicitudTitularDto> selectTitularesByPromocion(SolicitudTitularDto solicitudTitular);
}
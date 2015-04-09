package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.SolicitudPrioridad;
import org.apache.ibatis.annotations.Param;

public interface SolicitudPrioridadMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPais") String codPais, @Param("idSolicitud") String idSolicitud, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int insert(SolicitudPrioridad record);

    int insertSelective(SolicitudPrioridad record);

    SolicitudPrioridad selectByPrimaryKey(@Param("codOficina") String codOficina, @Param("codPais") String codPais, @Param("idSolicitud") String idSolicitud, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int updateByPrimaryKeySelective(SolicitudPrioridad record);

    int updateByPrimaryKey(SolicitudPrioridad record);
}
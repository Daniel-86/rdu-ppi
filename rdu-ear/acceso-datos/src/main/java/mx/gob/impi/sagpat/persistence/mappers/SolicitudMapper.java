package mx.gob.impi.sagpat.persistence.mappers;


import java.util.List;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import mx.gob.impi.sagpat.persistence.model.SolicitudTitularDto;
import org.apache.ibatis.annotations.Param;

public interface SolicitudMapper {
    int deleteByPrimaryKey(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("serExped") Short serExped, @Param("tipExped") String tipExped);

    int insert(Solicitud record);

    int insertSelective(Solicitud record);

    Solicitud selectByPrimaryKey(Solicitud record);
    Solicitud selectByPrimaryKeyView(Solicitud record);
    Solicitud selectByExpedienteDivisional(@Param("codOficina") String codOficina, @Param("numExped") Integer numExped, @Param("serExped") Integer serExped, @Param("tipExped") String tipExped);


    int updateByPrimaryKeySelective(Solicitud record);

    int updateByPrimaryKey(Solicitud record);
}
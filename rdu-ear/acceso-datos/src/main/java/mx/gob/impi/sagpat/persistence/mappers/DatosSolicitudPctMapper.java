package mx.gob.impi.sagpat.persistence.mappers;

import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPct;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPctMU;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DatosSolicitudPctMapper {

    DatosSolicitudPct selectByPrimaryKey(@Param("idSolicitudPct") String idSolicitudPct);
    List<DatosSolicitudPctMU> selectPCT(@Param("idSolicitudPct") String idSolicitudPct);
}

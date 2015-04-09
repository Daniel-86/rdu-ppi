package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Pago;

public interface PagoMapper {
    int deleteByPrimaryKey(Integer idPago);

    int insert(Pago record);

    int insertSelective(Pago record);

    List<Pago> selectByTramiteId(Long idTramite);
    Pago selectByPrimaryKey(Integer idPago);

    int updateByPrimaryKeySelective(Pago record);

    int updatePASE(Pago record);

    int updateByPrimaryKey(Pago record);
}
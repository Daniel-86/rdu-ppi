package mx.gob.impi.sagpat.persistence.mappers;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author oracle
 */
public interface NotificacionViewMapper {

    List<NotificacionView> selectByOficioSalida(@Param("codOficina")String codOficina, @Param("anio")Long anio, @Param("numOficio")Long numOficio);
}

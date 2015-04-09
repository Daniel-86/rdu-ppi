package mx.gob.impi.sigmar.persistence.mappers;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;

/**
 *
 * @author oracle
 */
public interface NotificacionViewMapper {

    List<NotificacionView> selectByOficioSalida(Long oficioSalida);
}

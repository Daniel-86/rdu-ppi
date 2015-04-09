/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Notificacion;
import mx.gob.impi.rdu.persistence.model.NotificacionFirma;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;

/**
 *
 * @author oracle
 */
public interface RduNotificacionesService {

    List<NotificacionView> consultarNotificacionView(Long oficioSalida);

    List<NotificacionView> consultarNotificacionView(String codOficina, Long anio, Long numOficio);

    List<Notificacion> consultarNotificaciones(Integer idUsuario);
    
    List<Notificacion>getNotificacionesUserLoad(Integer usuariaCarga, Integer promovente);
        
    List<Notificacion> selectNotificacionesByIds(List<Integer> cadenaIds);
    
    int deleteNotificacionesByIds(List<Integer> cadenaIds);
    
    int insertarNotificacion(Notificacion notificacion);
    
    int updateNotificacion(Notificacion notificacion);
    
    Long saveNotificacionFirma(NotificacionFirma notifFirma);
    
    public List<Notificacion> getNotificacionesByDate(String fechaInicio, String fechaFin, Integer ultimaSemana,Integer ultimoMes, Integer idUsuarioFirmante);
    
    Notificacion getNotificacionesByFolio(String folio);
}

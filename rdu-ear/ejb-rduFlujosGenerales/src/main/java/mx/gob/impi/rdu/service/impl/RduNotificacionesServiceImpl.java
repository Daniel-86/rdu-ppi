/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service.impl;

import java.util.List;
import mx.gob.impi.rdu.persistence.mappers.NotificacionFirmaMapper;
import mx.gob.impi.rdu.persistence.mappers.NotificacionMapper;
import mx.gob.impi.rdu.persistence.model.Notificacion;
import mx.gob.impi.rdu.persistence.model.NotificacionFirma;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;
import mx.gob.impi.rdu.service.RduNotificacionesService;
import mx.gob.impi.sigappi.persistence.mappers.KffoliosNotificacionMapper;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import mx.gob.impi.sigmar.persistence.mappers.NotificacionViewMapper;

/**
 *
 * @author oracle
 */
public class RduNotificacionesServiceImpl implements RduNotificacionesService {

    private mx.gob.impi.sigmar.persistence.mappers.NotificacionViewMapper rduNotificacionViewMapper;
    private mx.gob.impi.sagpat.persistence.mappers.NotificacionViewMapper notificacionViewMapperSagpat;
    
    //private mx.gob.impi.sigappi.persistence.mappers.KffoliosNotificacionMapper kffoliosNotificacionMapper;
    
    private NotificacionMapper rduNotificacionMapper;
    private NotificacionFirmaMapper notificacionFirmaMapper;

    public void setNotificacionFirmaMapper(NotificacionFirmaMapper notificacionFirmaMapper) {
        this.notificacionFirmaMapper = notificacionFirmaMapper;
    }
    
    public void setRduNotificacionMapper(NotificacionMapper rduNotificacionMapper) {
        this.rduNotificacionMapper = rduNotificacionMapper;
    }

    public void setNotificacionViewMapperSagpat(mx.gob.impi.sagpat.persistence.mappers.NotificacionViewMapper notificacionViewMapperSagpat) {
        this.notificacionViewMapperSagpat = notificacionViewMapperSagpat;
    }

    public void setRduNotificacionViewMapper(NotificacionViewMapper rduNotificacionViewMapper) {
        this.rduNotificacionViewMapper = rduNotificacionViewMapper;
    }

    public List<NotificacionView> consultarNotificacionView(Long oficioSalida) {
        return rduNotificacionViewMapper.selectByOficioSalida(oficioSalida);
    }

    public List<NotificacionView> consultarNotificacionView(String codOficina, Long anio, Long numOficio) {
        return this.notificacionViewMapperSagpat.selectByOficioSalida(codOficina, anio, numOficio);
    }

    public List<Notificacion> consultarNotificaciones(Integer idUsuario) {
        return rduNotificacionMapper.selectByIdUsuarioFirma(idUsuario);
    }
    
    public List<Notificacion> getNotificacionesUserLoad(Integer idUsuario, Integer promovente) {
        return rduNotificacionMapper.selectByIdUsuarioCarga(idUsuario, promovente);
    }
    
    public List<Notificacion> getNotificacionesTitular(String titutar, Integer promovente) {
        return rduNotificacionMapper.selectByTitular(titutar, promovente);
    }

    public Notificacion selectNotificacionesById(Integer idNotificacion) {
        return rduNotificacionMapper.selectByPrimaryKey(idNotificacion);
    }
    
    
    
    
    public List<Notificacion> selectNotificacionesByIds(List<Integer> cadenaIds){
        return rduNotificacionMapper.selecttest(cadenaIds);
    }
    
        public int deleteNotificacionesByIds(List<Integer> cadenaIds){
                return rduNotificacionMapper.deleteByIds(cadenaIds);
    }

    public int insertarNotificacion(Notificacion notificacion) {
        return rduNotificacionMapper.insert(notificacion);
    }

    public int updateNotificacion(Notificacion notificacion) {
        return this.rduNotificacionMapper.updateByPrimaryKey(notificacion);
    }

    public Long saveNotificacionFirma(NotificacionFirma notifFirma) {
         this.notificacionFirmaMapper.insert(notifFirma);
         return notifFirma.getId();
    }

    public List<Notificacion> getNotificacionesByDate(String fechaInicio, String fechaFin, Integer ultimaSemana,Integer ultimoMes, Integer idUsuarioFirmante){
        return this.rduNotificacionMapper.selectByDates(fechaInicio, fechaFin, ultimaSemana, ultimoMes,idUsuarioFirmante );
    }

    public Notificacion getNotificacionesByFolio(String folio) {
        return this.rduNotificacionMapper.selectByFolio(folio);
}

//    public KffoliosNotificacionMapper getKffoliosNotificacionMapper() {
//        return kffoliosNotificacionMapper;
//    }
//
//    public void setKffoliosNotificacionMapper(KffoliosNotificacionMapper kffoliosNotificacionMapper) {
//        this.kffoliosNotificacionMapper = kffoliosNotificacionMapper;
//    }
//
//    
//    public List<KffoliosNotificacion> selectByOficioSalida(String codbarras) {
//        return this.kffoliosNotificacionMapper.selectByOficioSalida(codbarras); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public int insert(KffoliosNotificacion kffoliosNotificacion) {
//        return this.kffoliosNotificacionMapper.insert(kffoliosNotificacion); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    public int insertSelective(KffoliosNotificacion kffoliosNotificacion) {
//        return this.kffoliosNotificacionMapper.insertSelective(kffoliosNotificacion); //To change body of generated methods, choose Tools | Templates.
//    }
}

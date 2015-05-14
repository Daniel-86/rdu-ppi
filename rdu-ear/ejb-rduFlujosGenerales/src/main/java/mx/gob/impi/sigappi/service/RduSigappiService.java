/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigappi.service;

import java.util.List;
import mx.gob.impi.sigappi.persistence.model.KfAlmacenar;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.KfFolios;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import mx.gob.impi.sigappi.persistence.model.SolicitudInteresados;
import mx.gob.impi.sigappi.persistence.model.TiposRelacion;
import mx.gob.impi.sigappi.persistence.model.UsuariosSigappi;

/**
 *
 * @author oracle
 */
public interface RduSigappiService {

   
    List<KffoliosNotificacion> selectByOficioSalida(String codbarras);
    
    int insert(KffoliosNotificacion kffoliosNotificacion);
    
    int insertSelective(KffoliosNotificacion kffoliosNotificacion);
    
    List<KfFolios> selectKfFoliosByCodbarras(String codbarras);
    
    int insert(KfFolios kfFolios);
    
    List<KfAlmacenar> selectKfAlmacenarByCodbarras(String codbarras);
    
    List<KfAlmacenar> selectKfAlmacenarByTitle(String title);
    
    int insert(KfAlmacenar kfAlmacenar);
    
    List<KfContenedores> selectKfContenedoresByTitle(String title);
    List<KfContenedores> selectKfContenedoresByPC(String pc);
    
    int insert(KfContenedores kfContenedores);
    
    List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresado(Integer codInteresado);
    List<SolicitudInteresados> selectSolicitudInteresadosByTitle( String title);
    
    int insert(SolicitudInteresados solicitudInteresados);
    
    List<TiposRelacion> selectTiposRelacionByCodRelacion(Integer codRelacion);
    
    int insert(TiposRelacion  tiposRelacion);
    
    List<UsuariosSigappi> selectUsuariosSigappiByCveUsuario(String cveUsuario);
    
    int insert(UsuariosSigappi usuariosSigappi);
    
    List<TiposRelacion> listTiposRelacion();
    
    List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresadoAndSecuencia(Integer codInteresado, Integer secuencia);
    
    int updateNotificationSubscription(String title, Integer codInteresado, Integer secuencia);
    
}

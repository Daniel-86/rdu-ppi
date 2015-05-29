/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigappi.service;

import java.util.List;
import mx.gob.impi.sigappi.persistence.model.Anexo;
import mx.gob.impi.sigappi.persistence.model.Area;
import mx.gob.impi.sigappi.persistence.model.DerechosAsociados;
import mx.gob.impi.sigappi.persistence.model.FigurasJuridicas;
import mx.gob.impi.sigappi.persistence.model.Interesados;
import mx.gob.impi.sigappi.persistence.model.KfAlmacenar;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.KfFolios;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import mx.gob.impi.sigappi.persistence.model.SolicitudInteresados;
import mx.gob.impi.sigappi.persistence.model.SolicitudRevision;
import mx.gob.impi.sigappi.persistence.model.SolicitudWeb;
import mx.gob.impi.sigappi.persistence.model.TipoAnexo;
import mx.gob.impi.sigappi.persistence.model.TiposRelacion;
import mx.gob.impi.sigappi.persistence.model.UsuariosSigappi;

/**
 *
 * @author oracle
 */
public interface RduSigappiService {

   
    List<KffoliosNotificacion> selectByOficioSalida(String codbarras);
    
    List<KffoliosNotificacion>  selectANotificarByCodInteresado(Integer codInteresado);
    
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
    
    
    List<Anexo> selectAnexoByCodbarras(String codbarras);
    
    List<Area> selectAreaByCveArea(Integer cveArea);
    
    List<DerechosAsociados> selectDerechosAsociadosByTitle(String title);
    
    List<FigurasJuridicas> selectFigurasJuridicasByNumFigura(Integer numFigura);
    
    List<Interesados> selectInteresadosByCodInteresado(Integer codInteresado);
    
    List<TipoAnexo> selectTipoAnexoByCategoriaDivisionSeccion(Integer categoria,Integer division,Integer seccion);
    
    int insertSolicitudRevision(SolicitudRevision solicitudRevision);
    
    List<SolicitudRevision> findSolicitudRevisionByCodInteresadoAndSecuencia(Integer codInteresado, Integer secuencia);
    
    List<SolicitudRevision> findSolicitudRevisionByCodInteresadoAndSecuenciaAndSession(Integer codInteresado, Integer secuencia, Integer idSolicitud);
    
    int updateSolicitudRevision(SolicitudRevision solicitudRevision);
    
    KfContenedores findKfContenedoresByTitleOrPc(String id);
    
    void deleteSolicitudRevision(SolicitudRevision solicitudRevision);
    
    int appendSolicitudRevision(SolicitudRevision solicitudRevision);
    
    int insertSolicitudWeb(SolicitudWeb solicitudWeb);
    
    int updateSolicitudWeb(SolicitudWeb solicitudWeb);
    
    List<SolicitudWeb> findAllSolicitudWebByUserAndStatus(Integer idPromovente, Integer idStatus);
    
    List<SolicitudWeb> findAllSolicitudWebByUser(Integer idPromovente);
    
    SolicitudWeb findSolicitudWebBySession(Integer idSolicitud);
    
    Integer nextSequence();
    
    List<SolicitudRevision> findSolicitudRevisionByCodInteresadoAndSession(Integer codInteresado, Integer idSolicitud);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigappi.service.impl;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.AnexoMapper;
import mx.gob.impi.sigappi.persistence.mappers.AreaMapper;
import mx.gob.impi.sigappi.persistence.mappers.DerechosAsociadosMapper;
import mx.gob.impi.sigappi.persistence.mappers.FigurasJuridicasMapper;
import mx.gob.impi.sigappi.persistence.mappers.InteresadosMapper;
import mx.gob.impi.sigappi.persistence.mappers.KfAlmacenarMapper;
import mx.gob.impi.sigappi.persistence.mappers.KfContenedoresMapper;
import mx.gob.impi.sigappi.persistence.mappers.KfFoliosMapper;

import mx.gob.impi.sigappi.service.RduSigappiService;
import mx.gob.impi.sigappi.persistence.mappers.KffoliosNotificacionMapper;
import mx.gob.impi.sigappi.persistence.mappers.SolicitudInteresadosMapper;
import mx.gob.impi.sigappi.persistence.mappers.SolicitudRevisionMapper;
import mx.gob.impi.sigappi.persistence.mappers.SolicitudWebMapper;
import mx.gob.impi.sigappi.persistence.mappers.TipoAnexoMapper;
import mx.gob.impi.sigappi.persistence.mappers.TiposRelacionMapper;
import mx.gob.impi.sigappi.persistence.mappers.UsuariosSigappiMapper;
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
public class RduSigappiServiceImpl implements RduSigappiService {

    
    private mx.gob.impi.sigappi.persistence.mappers.KffoliosNotificacionMapper kffoliosNotificacionMapper;
    private mx.gob.impi.sigappi.persistence.mappers.KfFoliosMapper kfFoliosMapper;
    private mx.gob.impi.sigappi.persistence.mappers.KfAlmacenarMapper kfAlmacenarMapper;
    private mx.gob.impi.sigappi.persistence.mappers.KfContenedoresMapper kfContenedoresMapper;
    private mx.gob.impi.sigappi.persistence.mappers.SolicitudInteresadosMapper solicitudInteresadosMapper;
    private mx.gob.impi.sigappi.persistence.mappers.TiposRelacionMapper tiposRelacionMapper;
    private mx.gob.impi.sigappi.persistence.mappers.UsuariosSigappiMapper usuariosSigappiMapper;
    private mx.gob.impi.sigappi.persistence.mappers.AnexoMapper anexoMapper;
    private mx.gob.impi.sigappi.persistence.mappers.AreaMapper areaMapper;
    private mx.gob.impi.sigappi.persistence.mappers.DerechosAsociadosMapper derechosAsociadosMapper;
    private mx.gob.impi.sigappi.persistence.mappers.FigurasJuridicasMapper figurasJuridicasMapper;
    private mx.gob.impi.sigappi.persistence.mappers.InteresadosMapper interesadosMapper;
    private mx.gob.impi.sigappi.persistence.mappers.TipoAnexoMapper tipoAnexoMapper;
    private mx.gob.impi.sigappi.persistence.mappers.SolicitudRevisionMapper solicitudRevisionMapper;
    private mx.gob.impi.sigappi.persistence.mappers.SolicitudWebMapper solicitudWebMapper;

    public SolicitudWebMapper getSolicitudWebMapper() {
        return solicitudWebMapper;
    }

    public void setSolicitudWebMapper(SolicitudWebMapper solicitudWebMapper) {
        this.solicitudWebMapper = solicitudWebMapper;
    }
    
    public KffoliosNotificacionMapper getKffoliosNotificacionMapper() {
        return kffoliosNotificacionMapper;
    }

    public void setKffoliosNotificacionMapper(KffoliosNotificacionMapper kffoliosNotificacionMapper) {
        this.kffoliosNotificacionMapper = kffoliosNotificacionMapper;
    }

    public KfFoliosMapper getKfFoliosMapper() {
        return kfFoliosMapper;
    }

    public void setKfFoliosMapper(KfFoliosMapper kfFoliosMapper) {
        this.kfFoliosMapper = kfFoliosMapper;
    }

    public KfAlmacenarMapper getKfAlmacenarMapper() {
        return kfAlmacenarMapper;
    }

    public void setKfAlmacenarMapper(KfAlmacenarMapper kfAlmacenarMapper) {
        this.kfAlmacenarMapper = kfAlmacenarMapper;
    }

    public KfContenedoresMapper getKfContenedoresMapper() {
        return kfContenedoresMapper;
    }

    public void setKfContenedoresMapper(KfContenedoresMapper kfContenedoresMapper) {
        this.kfContenedoresMapper = kfContenedoresMapper;
    }

    public SolicitudInteresadosMapper getSolicitudInteresadosMapper() {
        return solicitudInteresadosMapper;
    }

    public void setSolicitudInteresadosMapper(SolicitudInteresadosMapper solicitudInteresadosMapper) {
        this.solicitudInteresadosMapper = solicitudInteresadosMapper;
    }

    public TiposRelacionMapper getTiposRelacionMapper() {
        return tiposRelacionMapper;
    }

    public void setTiposRelacionMapper(TiposRelacionMapper tiposRelacionMapper) {
        this.tiposRelacionMapper = tiposRelacionMapper;
    }

    public UsuariosSigappiMapper getUsuariosSigappiMapper() {
        return usuariosSigappiMapper;
    }

    public void setUsuariosSigappiMapper(UsuariosSigappiMapper usuariosSigappiMapper) {
        this.usuariosSigappiMapper = usuariosSigappiMapper;
    }

    public AnexoMapper getAnexoMapper() {
        return anexoMapper;
    }

    public void setAnexoMapper(AnexoMapper anexoMapper) {
        this.anexoMapper = anexoMapper;
    }

    public AreaMapper getAreaMapper() {
        return areaMapper;
    }

    public void setAreaMapper(AreaMapper areaMapper) {
        this.areaMapper = areaMapper;
    }

    public DerechosAsociadosMapper getDerechosAsociadosMapper() {
        return derechosAsociadosMapper;
    }

    public void setDerechosAsociadosMapper(DerechosAsociadosMapper derechosAsociadosMapper) {
        this.derechosAsociadosMapper = derechosAsociadosMapper;
    }

    public FigurasJuridicasMapper getFigurasJuridicasMapper() {
        return figurasJuridicasMapper;
    }

    public void setFigurasJuridicasMapper(FigurasJuridicasMapper figurasJuridicasMapper) {
        this.figurasJuridicasMapper = figurasJuridicasMapper;
    }

    public InteresadosMapper getInteresadosMapper() {
        return interesadosMapper;
    }

    public void setInteresadosMapper(InteresadosMapper interesadosMapper) {
        this.interesadosMapper = interesadosMapper;
    }

    public TipoAnexoMapper getTipoAnexoMapper() {
        return tipoAnexoMapper;
    }

    public void setTipoAnexoMapper(TipoAnexoMapper tipoAnexoMapper) {
        this.tipoAnexoMapper = tipoAnexoMapper;
    }
    
    public SolicitudRevisionMapper getSolicitudRevisionMapper() {
        return solicitudRevisionMapper;
    }
    
    public void setSolicitudRevisionMapper(SolicitudRevisionMapper solicitudRevisionMapper) {
        this.solicitudRevisionMapper = solicitudRevisionMapper;
    }

    
    public List<KffoliosNotificacion> selectByOficioSalida(String codbarras) {
        return this.kffoliosNotificacionMapper.selectByOficioSalida(codbarras); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<KffoliosNotificacion> selectANotificarByCodInteresado(Integer codInteresado) {
        return this.kffoliosNotificacionMapper.selectANotificarByCodInteresado(codInteresado); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(KffoliosNotificacion kffoliosNotificacion) {
        return this.kffoliosNotificacionMapper.insert(kffoliosNotificacion); //To change body of generated methods, choose Tools | Templates.
    }

    public int insertSelective(KffoliosNotificacion kffoliosNotificacion) {
        return this.kffoliosNotificacionMapper.insertSelective(kffoliosNotificacion); //To change body of generated methods, choose Tools | Templates.
    }

    public List<KfFolios> selectKfFoliosByCodbarras(String codbarras) {
        return this.kfFoliosMapper.selectByCodbarras(codbarras); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(KfFolios kfFolios) {
        return this.kfFoliosMapper.insert(kfFolios); //To change body of generated methods, choose Tools | Templates.
    }

    public List<KfAlmacenar> selectKfAlmacenarByCodbarras(String codbarras) {
        return this.kfAlmacenarMapper.selectByCodbarras(codbarras); //To change body of generated methods, choose Tools | Templates.
    }

    public List<KfAlmacenar> selectKfAlmacenarByTitle(String title) {
        return this.kfAlmacenarMapper.selectByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(KfAlmacenar kfAlmacenar) {
        return this.kfAlmacenarMapper.insert(kfAlmacenar); //To change body of generated methods, choose Tools | Templates.
    }

    public List<KfContenedores> selectKfContenedoresByTitle(String title) {
        return this.kfContenedoresMapper.selectByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<KfContenedores> selectKfContenedoresByPC(String pc) {
        return this.kfContenedoresMapper.selectByPC(pc); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(KfContenedores kfContenedores) {
        return this.kfContenedoresMapper.insert(kfContenedores); //To change body of generated methods, choose Tools | Templates.
    }

    public List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresado(Integer codInteresado) {
        return this.solicitudInteresadosMapper.selectByCodInteresado(codInteresado); //To change body of generated methods, choose Tools | Templates.
    }

    public List<SolicitudInteresados> selectSolicitudInteresadosByTitle(String title) {
        return this.solicitudInteresadosMapper.selectByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(SolicitudInteresados solicitudInteresados) {
        return this.solicitudInteresadosMapper.insert(solicitudInteresados); //To change body of generated methods, choose Tools | Templates.
    }

    public List<TiposRelacion> selectTiposRelacionByCodRelacion(Integer codRelacion) {
        return this.tiposRelacionMapper.selectByCodRelacion(codRelacion); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(TiposRelacion tiposRelacion) {
        return this.tiposRelacionMapper.insert(tiposRelacion); //To change body of generated methods, choose Tools | Templates.
    }

    public List<UsuariosSigappi> selectUsuariosSigappiByCveUsuario(String cveUsuario) {
        return this.usuariosSigappiMapper.selectByCveUsuario(cveUsuario); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(UsuariosSigappi usuariosSigappi) {
        return this.usuariosSigappiMapper.insert(usuariosSigappi); //To change body of generated methods, choose Tools | Templates.
    }

    public List<TiposRelacion> listTiposRelacion() {
        return this.tiposRelacionMapper.list();
    }
    
    public List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresadoAndSecuencia(Integer codInteresado, Integer secuencia) {
        return this.solicitudInteresadosMapper.findByUserAndSequence(codInteresado, secuencia);
    }
    
    public int updateNotificationSubscription(String title, Integer codInteresado, Integer secuencia) {
        return this.solicitudInteresadosMapper.updateSequence(title, codInteresado, secuencia);
    }

    public List<Anexo> selectAnexoByCodbarras(String codbarras) {
        return this.anexoMapper.selectByCodbarras(codbarras); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Area> selectAreaByCveArea(Integer cveArea) {
        return this.areaMapper.selectByCveArea(cveArea);//To change body of generated methods, choose Tools | Templates.
    }

    public List<DerechosAsociados> selectDerechosAsociadosByTitle(String title) {
        return this.derechosAsociadosMapper.selectByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }

    public List<FigurasJuridicas> selectFigurasJuridicasByNumFigura(Integer numFigura) {
        return this.figurasJuridicasMapper.selectByNumFigura(numFigura); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Interesados> selectInteresadosByCodInteresado(Integer codInteresado) {
        return this.interesadosMapper.selectByCodInteresado(codInteresado); //To change body of generated methods, choose Tools | Templates.
    }

    public List<TipoAnexo> selectTipoAnexoByCategoriaDivisionSeccion(Integer categoria, Integer division, Integer seccion) {
        return this.tipoAnexoMapper.selectByCategoriaDivisionSeccion(categoria, division, seccion); //To change body of generated methods, choose Tools | Templates.
    }

    public int insertSolicitudRevision(SolicitudRevision solicitudRevision) {
        return solicitudRevisionMapper.insert(solicitudRevision);
//        return 0;
    }

    public List<SolicitudRevision> findSolicitudRevisionByCodInteresadoAndSecuencia(Integer codInteresado, Integer secuencia) {
        return solicitudRevisionMapper.findByCodInteresadoAndSecuencia(codInteresado, secuencia);
    }
    
    public List<SolicitudRevision> findSolicitudRevisionByCodInteresadoAndSecuenciaAndSession(Integer codInteresado, Integer secuencia, Integer idSolicitud) {
        String asdf = "";
        return solicitudRevisionMapper.findByCodInteresadoAndSecuenciaAndSession(codInteresado, secuencia, idSolicitud);
    }

    public int updateSolicitudRevision(SolicitudRevision solicitudRevision) {
        return solicitudRevisionMapper.update(solicitudRevision);
//        return 0;
    }
    
    public KfContenedores findKfContenedoresByTitleOrPc(String id) {
        return kfContenedoresMapper.findByTitleOrPc(id);
    }
    
    public void deleteSolicitudRevision(SolicitudRevision solicitudRevision) {
        solicitudRevisionMapper.delete(solicitudRevision);
    }
    
    public int appendSolicitudRevision(SolicitudRevision solicitudRevision) {
        return solicitudRevisionMapper.append(solicitudRevision);
    }
    
    public int insertSolicitudWeb(SolicitudWeb solicitudWeb) {
        return solicitudWebMapper.insert(solicitudWeb);
    }
    
    public int updateSolicitudWeb(SolicitudWeb solicitudWeb) {
        return solicitudWebMapper.update(solicitudWeb);
    }
    
    public List<SolicitudWeb> findAllSolicitudWebByUserAndStatus(Integer idPromovente, Integer idStatus) {
        return solicitudWebMapper.findAllByUserAndStatus(idPromovente, idStatus);
    }
    
    public List<SolicitudWeb> findAllSolicitudWebByUser(Integer idPromovente) {
        return solicitudWebMapper.findAllByUser(idPromovente);
    }
    
    public SolicitudWeb findSolicitudWebBySession(Integer idSolicitud) {
        return solicitudWebMapper.findBySession(idSolicitud);
    }

    public Integer nextSequence() {
        return solicitudWebMapper.nextSequence();
    }
    
    public List<SolicitudRevision> findSolicitudRevisionByCodInteresadoAndSession(Integer codInteresado, Integer idSolicitud) {
        return solicitudRevisionMapper.findByCodInteresadoAndSession(codInteresado, idSolicitud);
    }
   
}

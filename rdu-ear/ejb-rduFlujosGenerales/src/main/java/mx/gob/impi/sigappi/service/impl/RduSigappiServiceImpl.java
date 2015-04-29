/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigappi.service.impl;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.KfAlmacenarMapper;
import mx.gob.impi.sigappi.persistence.mappers.KfContenedoresMapper;
import mx.gob.impi.sigappi.persistence.mappers.KfFoliosMapper;

import mx.gob.impi.sigappi.service.RduSigappiService;
import mx.gob.impi.sigappi.persistence.mappers.KffoliosNotificacionMapper;
import mx.gob.impi.sigappi.persistence.mappers.SolicitudInteresadosMapper;
import mx.gob.impi.sigappi.persistence.mappers.TiposRelacionMapper;
import mx.gob.impi.sigappi.persistence.mappers.UsuariosSigappiMapper;
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
public class RduSigappiServiceImpl implements RduSigappiService {

    
    private mx.gob.impi.sigappi.persistence.mappers.KffoliosNotificacionMapper kffoliosNotificacionMapper;
    private mx.gob.impi.sigappi.persistence.mappers.KfFoliosMapper kfFoliosMapper;
    private mx.gob.impi.sigappi.persistence.mappers.KfAlmacenarMapper kfAlmacenarMapper;
    private mx.gob.impi.sigappi.persistence.mappers.KfContenedoresMapper kfContenedoresMapper;
    private mx.gob.impi.sigappi.persistence.mappers.SolicitudInteresadosMapper solicitudInteresadosMapper;
    private mx.gob.impi.sigappi.persistence.mappers.TiposRelacionMapper tiposRelacionMapper;
    private mx.gob.impi.sigappi.persistence.mappers.UsuariosSigappiMapper usuariosSigappiMapper;
    
    

   

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

    
    public List<KffoliosNotificacion> selectByOficioSalida(String codbarras) {
        return this.kffoliosNotificacionMapper.selectByOficioSalida(codbarras); //To change body of generated methods, choose Tools | Templates.
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
}

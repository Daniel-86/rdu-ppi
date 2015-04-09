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
import mx.gob.impi.sigappi.persistence.model.KfAlmacenar;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.KfFolios;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;

/**
 *
 * @author oracle
 */
public class RduSigappiServiceImpl implements RduSigappiService {

    
    private mx.gob.impi.sigappi.persistence.mappers.KffoliosNotificacionMapper kffoliosNotificacionMapper;
    private mx.gob.impi.sigappi.persistence.mappers.KfFoliosMapper kfFoliosMapper;
    private mx.gob.impi.sigappi.persistence.mappers.KfAlmacenarMapper kfAlmacenarMapper;
    private mx.gob.impi.sigappi.persistence.mappers.KfContenedoresMapper kfContenedoresMapper;
    
    

   

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

    public int insert(KfContenedores kfContenedores) {
        return this.kfContenedoresMapper.insert(kfContenedores); //To change body of generated methods, choose Tools | Templates.
    }
}

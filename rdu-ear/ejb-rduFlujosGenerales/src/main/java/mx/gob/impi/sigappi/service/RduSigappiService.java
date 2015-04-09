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
    
    int insert(KfContenedores kfContenedores);
    
}

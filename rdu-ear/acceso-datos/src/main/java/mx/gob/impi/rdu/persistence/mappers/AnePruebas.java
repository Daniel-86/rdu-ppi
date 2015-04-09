/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Anexos;

/**
 *
 * @author oracle
 */
public interface AnePruebas {
    int deleteByPrimaryKey(Integer idAnexo);

    int insert(Anexos record);

    int insertSelective(Anexos record);

    Anexos selectByPrimaryKey(Integer idAnexo);

    int updateByPrimaryKeySelective(Anexos record);

    int updateByPrimaryKeyWithBLOBs(Anexos record);
    
    List<Anexos>selectByTramiteId(Long tramiteId);

    int updateByPrimaryKey(Anexos record);
    int insertPatente(Anexos record);
    int insertPromoMarcas(Anexos record);
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CatCapitulos;

/**
 *
 * @author adriana
 */
public interface CatCapitulosMapper {

    int deleteByPrimaryKey(Integer idCapitulo);

    int insert(CatCapitulos record);

    int insertSelective(CatCapitulos record);

    CatCapitulos selectByPrimaryKey(Integer idCapitulo);

    List<CatCapitulos> selectAll();

    int updateByPrimaryKeySelective(CatCapitulos record);

    int updateByPrimaryKey(CatCapitulos record);
}

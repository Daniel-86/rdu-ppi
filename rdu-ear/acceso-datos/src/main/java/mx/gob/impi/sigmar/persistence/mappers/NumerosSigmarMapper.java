/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.sigmar.persistence.mappers;

import mx.gob.impi.sigmar.persistence.params.ProductosParameters;
import java.util.List;
import mx.gob.impi.rdu.dto.NumerosSigmarDTO;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author Wolf_ito
 */
public interface NumerosSigmarMapper {

    List<NumerosSigmarDTO> recuperarRegistros(
            @Param("tipos") List<Integer> tipos, @Param("numero") List<Integer> numero);

    List<NumerosSigmarDTO> recuperarSolicitudes(
            @Param("tipos") List<Integer> tipos, @Param("numero") List<Integer> numero);

    void selectProductos(ProductosParameters params);
}

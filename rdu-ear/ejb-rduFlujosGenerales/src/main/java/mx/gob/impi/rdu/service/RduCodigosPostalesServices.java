/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CodigosPostales;

/**
 *
 * @author cesar
 */
public interface RduCodigosPostalesServices {

    List<CodigosPostales> selectByIdCodigosPostales(String codigoPostal);
}

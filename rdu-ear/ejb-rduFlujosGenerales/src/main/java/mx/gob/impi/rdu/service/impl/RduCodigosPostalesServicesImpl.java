/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service.impl;

import java.util.List;
import mx.gob.impi.rdu.persistence.mappers.CodigosPostalesMapper;
import mx.gob.impi.rdu.persistence.model.CodigosPostales;
import mx.gob.impi.rdu.service.RduCodigosPostalesServices;

/**
 *
 * @author CESAR CASTAÑEDA REYES
 */
public class RduCodigosPostalesServicesImpl implements RduCodigosPostalesServices {

    private CodigosPostalesMapper rduCodigosPostalesMapper;

    public void setRduCodigosPostalesMapper(CodigosPostalesMapper rduCodigosPostalesMapper) {
        this.rduCodigosPostalesMapper = rduCodigosPostalesMapper;
    }

    public List<CodigosPostales> selectByIdCodigosPostales(String codigoPostal) {
        try {
            return rduCodigosPostalesMapper.selectById(codigoPostal);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service.impl;

import java.util.Date;
import mx.gob.impi.rdu.persistence.mappers.FuncionesSimplesMapper;
import mx.gob.impi.rdu.service.RduFuncionesSimplesService;

/**
 *
 * @author cesar
 */
public class RduFuncionesSimplesServiceImpl implements RduFuncionesSimplesService {

    FuncionesSimplesMapper rduFuncionesSimplesMapper;

    public FuncionesSimplesMapper getRduFuncionesSimplesMapper() {
        return rduFuncionesSimplesMapper;
    }

    public void setRduFuncionesSimplesMapper(FuncionesSimplesMapper rduFuncionesSimplesMapper) {
        this.rduFuncionesSimplesMapper = rduFuncionesSimplesMapper;
    }

    public Date getSysDate() {
        try {
            return rduFuncionesSimplesMapper.getSysDate();
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
    }
}

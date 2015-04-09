/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.List;
import javax.ejb.EJB;
import mx.gob.impi.rdu.persistence.model.TramitePromocionPatente;
import mx.gob.impi.rdu.remote.RduPatentesBean;
import mx.gob.impi.rdu.util.PromocionNoExisteException;
import mx.gob.impi.rdu.util.SolicitudNoExisteException;
import mx.gob.impi.sagpat.persistence.model.ResultadoOficioPromocion;
import mx.gob.impi.sagpat.persistence.model.SolicitudTitularDto;
import mx.gob.impi.sagpat.persistence.model.TramiteOficio;

/**
 *
 * @author User
 */
public class PromocionesServiceImpl implements PromocionesService {

    @EJB(mappedName = "EJBRduPatentesBean")
    private RduPatentesBean rduPatentesBean;

    @Override
    public ResultadoOficioPromocion obtenerDatosOficioPromocion(TramiteOficio tramiteOficio) {
        return rduPatentesBean.obtenerDatosOficioPromocion(tramiteOficio);
    }

    @Override
    public int guardarPromocionPatente(TramitePromocionPatente promocion) throws SolicitudNoExisteException, PromocionNoExisteException{
        return rduPatentesBean.guardarPromocionPatente(promocion);
    }

    @Override
    public List<SolicitudTitularDto> selectTitularesByPromocion(SolicitudTitularDto solicitudTitular) {
        return rduPatentesBean.selectTitularesByPromocion(solicitudTitular);
    }
}

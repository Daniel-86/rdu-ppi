/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.ws.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import mx.gob.impi.rdu.dto.TramiteDto;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.Pago;
import mx.gob.impi.rdu.persistence.model.Tramite;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.remote.RduFlujosGeneralesBeanRemote;
import mx.gob.impi.rdu.remote.RduPatentesBeanRemote;
import mx.gob.impi.rdu.util.Constantes;

/**
 *
 * @author winter
 */
public class TramiteWService {

    Properties props = new Properties();
    InitialContext ctx;
    //RduFlujosGeneralesBean ejbFlujosGeneralesRemote;
    RduFlujosGeneralesBeanRemote ejbFlujosGeneralesRemote;
    RduPatentesBeanRemote ejbPatentesBeanRemote;

    public TramiteWService() {
        try {
            props.setProperty("java.naming.factory.initial",
                    "com.sun.enterprise.naming.SerialInitContextFactory");
            props.setProperty("java.naming.factory.url.pkgs",
                    "com.sun.enterprise.naming");
            props.setProperty("java.naming.factory.state",
                    "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            /*glassfish - Cuando el EJB se encuentra en otro servidor
             No es necesario si está en el mismo servidor (war y ear)
             <prop key="org.omg.CORBA.ORBInitialHost">192.168.0.92</prop>
             <prop key="org.omg.CORBA.ORBInitialPort">3718</prop>*/

            ctx = new InitialContext(props);
            //ejbFlujosGeneralesRemote = (RduFlujosGeneralesBean) ctx.lookup("EjbRduFlujosGenerales");
            ejbFlujosGeneralesRemote = (RduFlujosGeneralesBeanRemote) ctx.lookup("EjbRduFlujosGenerales");
            ejbPatentesBeanRemote = (RduPatentesBeanRemote) ctx.lookup("EJBRduPatentesBean");
        } catch (NamingException e) {
            System.out.println(e.getMessage());
        }

    }

    public int actualizarTramite(TramiteDto tramiteDto) {
        Tramite tramite = new Tramite();
        tramite.setIdEstatus(tramiteDto.getIdEstatus());
        tramite.setFechaEstatusActual(tramiteDto.getFechaEstatusActual());
        tramite.setIdTramite(tramiteDto.getIdTramite());
        tramite.setIdDomicilionotificacion(Constantes.FIRST_LONG);

        return this.ejbFlujosGeneralesRemote.actualizarTramite(tramite);
    }

    public int actualizarTramitePatentes(TramiteDto tramiteDto) {
        TramitePatente tramite = new TramitePatente();
        tramite.setIdEstatus(tramiteDto.getIdEstatus());
        tramite.setFechaEstatusActual(tramiteDto.getFechaEstatusActual());
        tramite.setIdTramitePatente(tramiteDto.getIdTramite());

        return this.ejbPatentesBeanRemote.updateTramitePatente(tramite); //PATENTES
    }

    public String consultarTramiteByid(Long idTramite) {
        //Consulta para figuras juridicas de patentes
        TramitePatente tramitePatente = ejbPatentesBeanRemote.getDatosTramitePatente(idTramite);
        return tramitePatente.getIdTramitePatente() + "|" + tramitePatente.getInvencion() + "|" + tramitePatente.getTipoSol().getDescripcion();

        //Falta implementar la logica para las promociones de marcas y patentes
    }

    public int insertaPago(Pago pago) {
        return this.ejbFlujosGeneralesRemote.insertarpago(pago);
    }

    public List<Tramite> consultarTramite(long tramite) {
        return this.ejbFlujosGeneralesRemote.cargarSolicitudes(tramite);
    }

    public TramiteDto obtenerTramite(int idTramite) {
        return this.ejbFlujosGeneralesRemote.obtenerSolicitudes(idTramite);
    }

    public int actualizarPagos(Pago pago) {
        return this.ejbFlujosGeneralesRemote.actualizarPago(pago);
    }

    public int insertarAnexo(Anexos anexo) {
        return this.ejbFlujosGeneralesRemote.insertarAnexos(anexo);
    }

    public int insertarAnexoPatentes(Anexos anexo) {
        return this.ejbFlujosGeneralesRemote.insertarAnexosPago(anexo);
    }

    public Date getSysdate() {
        return this.ejbFlujosGeneralesRemote.getSysDate();
    }
}

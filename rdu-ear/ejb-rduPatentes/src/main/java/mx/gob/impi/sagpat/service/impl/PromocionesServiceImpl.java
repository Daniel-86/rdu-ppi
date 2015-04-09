/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sagpat.service.impl;

import java.util.List;
import mx.gob.impi.rdu.persistence.mappers.TramitePromocionPatenteMapper;
import mx.gob.impi.rdu.persistence.model.TramitePromocionPatente;
import mx.gob.impi.rdu.util.PromocionNoExisteException;
import mx.gob.impi.sagpat.persistence.mappers.PromocionesMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudTitularMapper;
import mx.gob.impi.sagpat.persistence.model.Documento;
import mx.gob.impi.sagpat.persistence.model.ResultadoOficioPromocion;
import mx.gob.impi.sagpat.persistence.model.Solicitante;
import mx.gob.impi.sagpat.persistence.model.SolicitudTitularDto;
import mx.gob.impi.sagpat.persistence.model.TipoOficio;
import mx.gob.impi.sagpat.persistence.model.TipoPromocion;
import mx.gob.impi.sagpat.persistence.model.TramiteOficio;
import mx.gob.impi.sagpat.service.PromocionesService;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author oracle
 */
public class PromocionesServiceImpl implements PromocionesService {

    private PromocionesMapper promocionesMapper;
    private TramitePromocionPatenteMapper promocionPatenteMapper;
    private SolicitudTitularMapper solicitudTitularMapper;

    @Transactional
    public int guardarPromocionPatente(TramitePromocionPatente promocion) throws PromocionNoExisteException {
        System.out.println("---------------> en guardarPromocionPatente()...");
        if (promocion.getIdPromocion() != 0) {
            System.out.println("-----------------> idPromocion no es cero: "+promocion.getIdPromocion()+"... verificando si existe la promoción...");
            int existePromocion = promocionPatenteMapper.existePromocion(promocion.getIdPromocion());
            System.out.println("-----------------> existePromocion: " + existePromocion);
            if (existePromocion > 0) {
                System.out.println("----------------> actualizando tramite promoción...");
                promocionPatenteMapper.updateTramitePromocion(promocion);
                System.out.println("----------------> actualizando tramite promoción patente...");
                promocionPatenteMapper.updateTramitePromocionPatente(promocion);
                System.out.println("----------------> promoción actualizada!!!");
            } else {
                System.out.println("--------------> no existe la promoción... lanzando excepción...");
                throw new PromocionNoExisteException();
            }
        } else {
            System.out.println("------------> insertando trámite promoción...");
            promocionPatenteMapper.insertTramitePromocion(promocion);
            System.out.println("------------> se insertó el trámite promoción... idPromocion: " + promocion.getIdPromocion());
            System.out.println("----------> insertando trámite promoción patente...");
            promocionPatenteMapper.insertTramitePromocionPatente(promocion);
            System.out.println("------------> se insertó el trámite promoción patente... idPromocion: " + promocion.getIdPromocion());
        }

        return promocion.getIdPromocion();
    }

    public boolean solicitudValida(TramitePromocionPatente promocion) {

        TramiteOficio tramite = new TramiteOficio(
                promocion.getCodOficinaExped(),
                promocion.getTipExped(),
                promocion.getSerExped(),
                promocion.getNumExped());

        TramiteOficio resultado = promocionesMapper.consultarExisteSolicitud(tramite);

        System.out.println("-----------> resultado en solicitudValida: " + resultado);
        return resultado != null;
    }

    public ResultadoOficioPromocion obtenerDatosOficioPromocion(TramiteOficio tramiteOficio) {

        ResultadoOficioPromocion resultado = new ResultadoOficioPromocion();

        // SQL1: Buscar el oficio
        TramiteOficio oficio = promocionesMapper.buscarOficioAResponder(tramiteOficio);

        // Si no obtuvo resultado... despliega mensaje M1
        if (oficio == null) {
            System.out.println("-------> no se encontró el oficio... regresando mensaje M1...");
            resultado.setCodigoError("M1");
            return resultado;
        }

        System.out.println("---------> tipoOficio: " + oficio.getTipoOficio());

        // Hay resultado...
        // SQL2: Consultar valores para tipo promoción
        TipoOficio tipoOficio = promocionesMapper.consultarValoresPromocion(oficio.getTipoOficio());

        String tipoPromocion = null;

        // Si no hubo resultados...
        if (tipoOficio == null) {
            // TIPO_PROMOCION = NULL
            obtenerDatosDePromocionYSolicitud(tipoPromocion, oficio, resultado);

        } else {// Si hay resultados...

            resultado.setDescOficio(tipoOficio.getDescOficio());

            System.out.println("-----------> tipoPromocion: " + tipoOficio.getTipoPromocion());
            System.out.println("-----------> tipoPromocionWnot: " + tipoOficio.getTipoPromocionWnot());
            System.out.println("-----------> descOficio: " + tipoOficio.getDescOficio());

            // Checar si el estatus es WRES
            String estatus = oficio.getCodStatus();
            System.out.println("-----------> estatus: " + estatus);

            if (estatus != null && estatus.equals("WRES")) {
                // Estatus es WRES
                tipoPromocion = verificarNotificacion(tramiteOficio, tipoOficio);
                obtenerDatosDePromocionYSolicitud(tipoPromocion, oficio, resultado);

            } else {
                // Estatus es diferente a WRES
                // TIPO_PROMOCION = TIP_PROMOCION_WNOT
                tipoPromocion = tipoOficio.getTipoPromocionWnot();
                obtenerDatosDePromocionYSolicitud(tipoPromocion, oficio, resultado);
            }
        }

        resultado.setTipoPromocion(tipoPromocion);

        return resultado;
    }

    private void obtenerDatosDePromocionYSolicitud(String tipoPromocion, TramiteOficio tramiteOficio, ResultadoOficioPromocion resultado) {

        System.out.println("--------------> en obtenerDatosDePromocionYSolicitud():  tipoPromocion: " + tipoPromocion);

        // Tipo promoción es igual a NULL?
        if (tipoPromocion == null) {
            System.out.println("-------> tipo promoción es nulo... regresando mensaje M2...");
            resultado.setCodigoError("M2");
            return;
        }

        // Tipo promoción no es NULL
        // SQL3: Consultar descripción del tipo de promoción y tarifa
        TipoPromocion promocion = promocionesMapper.consultarTipoPromocion(tipoPromocion);
        if (promocion == null) {
            System.out.println("-------> promoción es nulo... regresando mensaje M2...");
            resultado.setCodigoError("M2");
            return;
        }

        resultado.setDescPromocion(promocion.getDesTipoRdu());
        resultado.setArea(promocion.getArea());
        resultado.setPlazoAdicional(promocion.getPlazoAdicional());

        System.out.println("-----------> desTipoRdu: " + promocion.getDesTipoRdu());
        System.out.println("-----------> articulo: " + promocion.getArticulo());

        // SQL6: Consultar número de solicitud
        TramiteOficio numSolicitud = promocionesMapper.consultarNumSolicitud(tramiteOficio);

        if (numSolicitud != null) {
            System.out.println("-----------> Datos de num solicitud obtenidos. Consultando número de concesión...");
            System.out.println("-----------> codOficinaExped: " + numSolicitud.getCodOficinaExped());
            System.out.println("-----------> tipoExped: " + numSolicitud.getTipoExped());
            System.out.println("-----------> serExped: " + numSolicitud.getSerExped());
            System.out.println("-----------> numExped: " + numSolicitud.getNumExped());

            // SQL7: Consultar número de concesión
            Double numConcesion = promocionesMapper.consultarNumConcesion(numSolicitud);
            System.out.println("---------> numConcesion: " + numConcesion);
            if (numConcesion != null) {
                resultado.setNumConcesion(numConcesion.intValue() + "");
            }

            // SQL8: Consultar tipo de solicitante
            Solicitante solicitante = promocionesMapper.consultarTipoSolicitante(numSolicitud);
            System.out.println("----------> nomPersona: " + solicitante.getNomPersona());
            System.out.println("----------> descTipo: " + solicitante.getDescTipo());
            System.out.println("----------> tipoPersona: " + solicitante.getTipoPersona());
            resultado.setAplicaDescuento(solicitante.getAplicaDescuento());
            resultado.setTipoSolicitante((int)solicitante.getTipoSolicitante());

        } else {
            System.out.println("----------> No se obtuvieron datos de num solicitud. Ejecutando consulta jerárquica...");
            System.out.println("----------> tipoTramite: " + tramiteOficio.getTipoTramite());
            System.out.println("----------> numTramite: " + tramiteOficio.getNumTramite());
            // SQL6a: Ejecutar consulta jerárquica
            numSolicitud = promocionesMapper.ejecutarConsultaJerarquica(tramiteOficio);

            if (numSolicitud == null) {
                System.out.println("-------> numSolicitud es nulo... regresando mensaje M3...");
                resultado.setCodigoError("M3");
            } else {
                // Hubo resultados para la consulta jerárquica
                if (numSolicitud.getCodOficinaExped() == null) {
                    numSolicitud.setCodOficinaExped(numSolicitud.getCodOficinaExpPro());
                }
            }
        }

        if (numSolicitud != null) {
            resultado.setCodOficinaExped(numSolicitud.getCodOficinaExped());
            resultado.setTipoExped(numSolicitud.getTipoExped());
            resultado.setSerExped((int) numSolicitud.getSerExped() + "");
            resultado.setNumExped((int) numSolicitud.getNumExped() + "");
        }
    }

    private String verificarNotificacion(TramiteOficio tramiteOficio, TipoOficio tipoOficio) {

        System.out.println("---------> en verificarNotificacion()...");
        System.out.println("--------> codOficinaOficio: " + tramiteOficio.getCodOficinaOficio());
        System.out.println("--------> serOficio: " + tramiteOficio.getSerOficio());
        System.out.println("---------> numOficio: " + tramiteOficio.getNumOficio());

        String tipoPromocion;

        // SQL4: Consultar Documento
        Documento documento = promocionesMapper.consultarDocumento(tramiteOficio);

        boolean notificado = false;

        if (documento != null) {
            System.out.println("---------> documento encontrado!!");
            notificado = true;
        } else {
            System.out.println("--------> no se encontró documento... consultando notificación...");
            // SQL5: Consultar si se notificó por gaceta
            Integer resultado = promocionesMapper.consultarNotificacion(tramiteOficio);
            if (resultado != null) {
                System.out.println("------> notificación encontrada!!!");
                notificado = true;
            }
        }

        System.out.println("--------> notificado: " + notificado);
        // Establecer el valor de TIPO_PROMOCION
        if (notificado) {
            tipoPromocion = tipoOficio.getTipoPromocion();
        } else {
            tipoPromocion = tipoOficio.getTipoPromocionWnot();
        }

        System.out.println("------> verificarNotificacion regresa: tipoPromocion = " + tipoPromocion);

        return tipoPromocion;
    }

    /**
     * @param promocionesMapper the promocionesMapper to set
     */
    public void setPromocionesMapper(PromocionesMapper promocionesMapper) {
        this.promocionesMapper = promocionesMapper;
    }

    /**
     * @param promocionPatenteMapper the promocionPatenteMapper to set
     */
    public void setPromocionPatenteMapper(TramitePromocionPatenteMapper promocionPatenteMapper) {
        this.promocionPatenteMapper = promocionPatenteMapper;
    }

    public void setSolicitudTitularMapper(SolicitudTitularMapper solicitudTitularMapper) {
        this.solicitudTitularMapper = solicitudTitularMapper;
    }

    public List<SolicitudTitularDto> selectTitularesByPromocion(SolicitudTitularDto solicitudTitular) {
        return this.solicitudTitularMapper.selectTitularesByPromocion(solicitudTitular);
    }
}

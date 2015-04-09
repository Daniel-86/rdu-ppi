package mx.gob.impi.rdu.ws.exposition;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import mx.gob.impi.rdu.ws.vo.CatClaseVO;
import javax.jws.WebService;
import mx.gob.impi.rdu.ws.service.TramiteWService;
import java.math.BigDecimal;
import java.util.Date;
import mx.gob.impi.rdu.dto.TramiteDto;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.Pago;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.TipoTramiteEnum;
import mx.gob.impi.rdu.util.Util;
import mx.gob.impi.rdu.ws.exception.BusinessException;
import mx.gob.impi.rdu.ws.exception.TechnicalException;
import mx.gob.impi.rdu.ws.util.Utils;
import mx.gob.impi.rdu.ws.vo.EstadoPASEVO;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author usradmin
 */
@WebService
public class RduOperacionesWeb {

    /**
     * Clave del estado de la solicitud COMPLETA
     */
    public static final String CVE_ESTADO_COMPLETA = "C";
    /**
     * Clave del estado de la solicitud PAGADA
     */
    public static final String CVE_ESTADO_PAGADA = "P";
    /**
     * Clave del estado de la solicitud INCOMPLETA
     */
    public static final String CVE_ESTADO_INCOMPLETA = "I";
    private Date fecha = new Date();

    public String metodoTests() {
        String vMsg = "RDU-WS funciona correctamente";
        return vMsg;

    }

    public CatClaseVO nadaClase() {
        CatClaseVO oClase = new CatClaseVO();
        oClase.setIdclase(1);
        oClase.setDescripcion("TEST 12");
        oClase.setTipoClase("10");
        return oClase;
    } 

    /**
     * Actualiza el estado del Documento electronico segun procesos del PASE
     *
     * @param estado instancia de EstadoPASEVO con parametros del documento y el
     * estado a asignar
     * @return indicador de afectacion a registros
     * @throws BusinessException Lanzada en caso de error en las reglas de
     * negocio
     * @throws TechnicalException Lanzada en caso de error de infraestructura
     */
    public int actualizaSolicitud(EstadoPASEVO paseVo) throws BusinessException, TechnicalException {
        TramiteWService objServicio = new TramiteWService();
        int toReturn = 0;

        System.out.println("--------------------------------Entrando en actualizaEstadoPASE---------------------------------- ");
        System.out.println("  ==> estado  " + paseVo.getEstado());

        // Valida que se hayan proporcionado los datos minimos
        if (paseVo.getEstado().isEmpty()) {
            throw new BusinessException("El indicador de ESTADO es requerido.");
        }
        if (paseVo.getIdDocumento() <= 0) {
            throw new BusinessException("El identificador del documento es requerido.");
        }

        if (CVE_ESTADO_COMPLETA.equals(paseVo.getEstado()) || CVE_ESTADO_PAGADA.equals(paseVo.getEstado())) {
            if (paseVo.getFolio() == null || paseVo.getFolio() <= 0) {
                throw new BusinessException("El FOLIO del documento es requerido.");
            }
        }

        // Valida que el estado sea correcto
        if (!paseVo.getEstado().equals(CVE_ESTADO_COMPLETA) && !paseVo.getEstado().equals(CVE_ESTADO_PAGADA)
                && !paseVo.getEstado().equals(CVE_ESTADO_INCOMPLETA)) {
            throw new BusinessException("El estado recibido es incorrecto.");
        }

        System.out.println("  ==> Se actualiza el ESTADO (" + paseVo.getEstado() + ") del documento (" + paseVo.getIdDocumento() + ")");

        try {
            System.out.println("  ==> Busca un documento existente en RDU  " + paseVo.getIdDocumento());
            TramiteDto listTramiteVo = objServicio.obtenerTramite(paseVo.getIdDocumento());

            if (listTramiteVo == null) {
                throw new BusinessException("El documento electronico no existe.");
            }

            // Si el estado es COMPLETO
            if (paseVo.getEstado().equals(CVE_ESTADO_COMPLETA)) {
                System.out.println("  ==> Si el estado es COMPLETO");
                // Se actualiza el estado del documento a COMPLETA.
                listTramiteVo.setIdEstatus(Long.valueOf(Constantes.TRAMITE_ESTATUS_COMPLETA));//2
                listTramiteVo.setFechaEstatusActual(fecha);
//                listTramiteVo.setIdTramite(Long.valueOf(paseVo.getIdDocumento()));

                // Se crea un registro en RDU en la tabla PAGO para la solicitud.
                Pago folio = new Pago();

                folio.setFoliopago(String.valueOf(paseVo.getFolio()));
                folio.setFeps(String.valueOf(paseVo.getFolio()));
                folio.setTotal(BigDecimal.valueOf(paseVo.getImporte()));

             if (listTramiteVo.getTipoTramite() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite())//2
                {
                    folio.setIdTramitePatentes(Long.valueOf(paseVo.getIdDocumento()));
                } 

                System.out.println("  ==> Se crea un registro en RDU en la tabla PAGO para el TRAMITE");
                Integer fepsElectronicoActualizado = objServicio.insertaPago(folio);

                System.out.println("  ==> Se actualiza el estado del documento a COMPLETA  " + listTramiteVo.getTipoTramite());

                Integer updateTramiteAfectado = -1;

                if (listTramiteVo.getTipoTramite() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite())//2
                {
                    updateTramiteAfectado = objServicio.actualizarTramitePatentes(listTramiteVo);
                } 
                toReturn = fepsElectronicoActualizado + updateTramiteAfectado;

                System.out.println("  ==> FIN DE ESTADO COMPLETO");
                System.out.println("  ==> toReturn   " + toReturn);
            }

            // Si el estado es INCOMPLETA
            if (paseVo.getEstado().equals(CVE_ESTADO_INCOMPLETA)) {
                // Se actualiza el estado del documento a INCOMPLETA
                listTramiteVo.setIdEstatus(Constantes.TRAMITE_ESTATUS_INCOMPLETA);//1
                listTramiteVo.setIdTramite(Long.valueOf(paseVo.getIdDocumento()));

                if (listTramiteVo.getTipoTramite() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite())//2
                {
                    toReturn = objServicio.actualizarTramitePatentes(listTramiteVo);
                }

            }

            // Si el estado es PAGADO
            if (paseVo.getEstado().equals(CVE_ESTADO_PAGADA)) {
                System.out.println("  ==> Si el estado es PAGADO");
                // Valida que se hayan proporcionado los datos requeridos
                if (paseVo.getEstado().isEmpty() || paseVo.getIdDocumento() <= 0 || paseVo.getFolio() <= 0) {
                    throw new BusinessException("No se proporcionaron los datos requeridos.");
                }

                // Se actualiza el estado del documento a PAGADA en la tabla TRAMITE
                listTramiteVo.setIdTramite(Long.valueOf(paseVo.getIdDocumento()));
                listTramiteVo.setIdEstatus(Constantes.TRAMITE_ESTATUS_PAGADA);//3
                listTramiteVo.setFechaEstatusActual(fecha);

                // Se crea un registro en RDU en la tabla PAGO para el TRAMITE.
                Pago pago = new Pago();

                pago.setFoliopago(String.valueOf(paseVo.getFolio()));
                pago.setFeps(String.valueOf(paseVo.getFolio()));
                pago.setTotal(BigDecimal.valueOf(paseVo.getImporte()));
                pago.setFechapago(paseVo.getFechaPago());

                if (listTramiteVo.getTipoTramite() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite())//2
                {
                    pago.setIdTramitePatentes(Long.valueOf(paseVo.getIdDocumento()));
                } 

                Anexos anex = new Anexos(); // nuestra entidad
                File anexoComprobante = null;

                if (paseVo.getArchivoAnexo() != null) {
                    System.out.println("  ==>Se recupera el Anexo enviado por el PASE, SE ENCUENTRA CODIFICADO EN BASE 64");
                    //Se crea un nuevo registro en la tabla ANEXO para el documento.

                    byte[] archivoAnexoBytes = Base64.decodeBase64(paseVo.getArchivoAnexo().getBytes());
                    anexoComprobante = Utils.writeTempFile(new ByteArrayInputStream(archivoAnexoBytes), "anexoComprobanteTempFile", ".pdf");

                    InputStream is = new FileInputStream(anexoComprobante); //lo abrimos. Lo importante es que sea un InputStream
                    byte[] buffer = new byte[(int) anexoComprobante.length()]; //creamos el buffer
                    int readers = is.read(buffer); //leemos el archivo al buffer

                   if (listTramiteVo.getTipoTramite() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite())//2
                    {
                        anex.setIdTramitePatente(Long.valueOf(paseVo.getIdDocumento()));
                    } 

                    anex.setIdTipoanexo(Long.valueOf(17));
                    anex.setArchivoAnexo(buffer);
//                    System.out.println("*****************Cargando el comprobante de pago desde la url******************");
//                    anex.setArchivoAnexo(Utils.getFile(new URL("https://eservicios.impi.gob.mx/seimpi/GenPagoFEPSPdf?recuperacion=true&folios_feps=" + paseVo.getFolio())));
                    anex.setExtension("pdf");
                    anex.setNombreArchivo("anexoComprobante" + paseVo.getFolio());

                } else {
                    System.out.println("No se pudo obtener el archivo del Anexo para el Comprobante de Pago.");
                }

                System.out.println("  ==> ACTUALIZA LOS DATOS DEL FEPS PAGADO");
                System.out.println("********************************************+");
                System.out.println("********************************************+");
                System.out.println("Fecha servidor de rdu:" + Util.formatearFecha(fecha, Util.FORMATODDMMYYYYHHMMSS));
                System.out.println("Fecha servidor de pase:" + Util.formatearFecha(paseVo.getFechaPago(), Util.FORMATODDMMYYYYHHMMSS));
                Integer fepsElectronicoUpdate = objServicio.actualizarPagos(pago);
                System.out.println("********************************************+");
                System.out.println("********************************************+");
                System.out.println("********************************************+");
                System.out.println("  **** fepsElectronicoUpdate:   " + fepsElectronicoUpdate);

                //En caso de no haber localizado el registro del FEPS_ELECTRONICO se inserta el registro
                if (fepsElectronicoUpdate != null && fepsElectronicoUpdate.intValue() == 0) {
                    System.out.println("  ==> En caso de no haber localizado el registro del PAGO se inserta el registro");
                    fepsElectronicoUpdate = objServicio.insertaPago(pago);
                }

                Integer anexoElectronicoActualizado = -1;
                if (listTramiteVo.getTipoTramite() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite())//2
                {
                    anexoElectronicoActualizado = objServicio.insertarAnexoPatentes(anex);
                } 

                Integer updateAfectado = -1;
               if (listTramiteVo.getTipoTramite() == TipoTramiteEnum.SOL_SIT.getIdTipoTramite())//2
                {
                    updateAfectado = objServicio.actualizarTramitePatentes(listTramiteVo);
                } 

                toReturn = fepsElectronicoUpdate + anexoElectronicoActualizado + updateAfectado;
                anexoComprobante.delete();
                System.out.println("  ==> FIN DE ESTADO PAGADO");
                System.out.println("  ==> toReturn   " + toReturn);
            }

        } catch (Exception e) {
            toReturn = 0;
            throw new TechnicalException("***error:  " + e.getMessage());
        }

        return toReturn;
    }

//    public Date getSysdate(){
//        return new TramiteWService().getSysdate();
//    }
}

/*Clase VO que contiene los parametros de entrada para la actualizacion de los estados del PASE*/
package mx.gob.impi.rdu.ws.vo;

import java.util.Date;

/**
 *
 * @author winter
 */
public class EstadoPASEVO {

    /**
     * Identificador del documento electronico
     */
    private int idDocumento;
    private String estado;

    /**
     * Elementos del pago (FEPS)
     */
    private Long folio;
    private Date fechaPago;
    private Double importe;

    /**
     * Elementos del Anexo (archivo binario)
     */
    private String archivoAnexo;

    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getArchivoAnexo() {
        return archivoAnexo;
    }

    public void setArchivoAnexo(String archivoAnexo) {
        this.archivoAnexo = archivoAnexo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

}
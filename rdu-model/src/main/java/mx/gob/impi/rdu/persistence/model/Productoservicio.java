package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class Productoservicio implements Serializable {
    private Long idProdserv;

    private Long idTipoclaseseleccionada;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    

    public Long getIdProdserv() {
        return idProdserv;
    }

    public void setIdProdserv(Long idProdserv) {
        this.idProdserv = idProdserv;
    }

    public Long getIdTipoclaseseleccionada() {
        return idTipoclaseseleccionada;
    }

    public void setIdTipoclaseseleccionada(Long idTipoclaseseleccionada) {
        this.idTipoclaseseleccionada = idTipoclaseseleccionada;
    }


}
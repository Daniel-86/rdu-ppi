package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class CatEstatus  implements Serializable{
    private Integer idEstatus;

    private String descripcion;

    private Integer indActivo;

    private String sigaccion;

    private String urlsigaccion;

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public Integer getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Integer indActivo) {
        this.indActivo = indActivo;
    }

    public String getSigaccion() {
        return sigaccion;
    }

    public void setSigaccion(String sigaccion) {
        this.sigaccion = sigaccion == null ? null : sigaccion.trim();
    }

    public String getUrlsigaccion() {
        return urlsigaccion;
    }

    public void setUrlsigaccion(String urlsigaccion) {
        this.urlsigaccion = urlsigaccion == null ? null : urlsigaccion.trim();
    }
}
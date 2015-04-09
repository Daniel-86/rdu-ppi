package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class CatEstatusCertificado implements  Serializable{
    private Integer idEstatusCertificado;

    private String codigo;

    private Integer indActivo;

    private String descripcion;

    public Integer getIdEstatusCertificado() {
        return idEstatusCertificado;
    }

    public void setIdEstatusCertificado(Integer idEstatusCertificado) {
        this.idEstatusCertificado = idEstatusCertificado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo == null ? null : codigo.trim();
    }

    public Integer getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Integer indActivo) {
        this.indActivo = indActivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }
}
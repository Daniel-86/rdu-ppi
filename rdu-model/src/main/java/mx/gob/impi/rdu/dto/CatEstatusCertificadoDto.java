/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;

/**
 *
 * @author
 */
public class CatEstatusCertificadoDto implements Serializable {

    public CatEstatusCertificadoDto() {
    }

    public CatEstatusCertificadoDto(Integer idEstatusCertificado) {
        this.idEstatusCertificado = idEstatusCertificado;
    }

    public CatEstatusCertificadoDto(Integer idEstatusCertificado, String descripcion) {
        this.idEstatusCertificado = idEstatusCertificado;
        this.descripcion = descripcion;
    }
    
    
    private Integer idEstatusCertificado;

    private String codigo;

    private Integer indActivo;

    private String descripcion;

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getIdEstatusCertificado() {
        return idEstatusCertificado;
    }

    public Integer getIndActivo() {
        return indActivo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdEstatusCertificado(Integer idEstatusCertificado) {
        this.idEstatusCertificado = idEstatusCertificado;
    }

    public void setIndActivo(Integer indActivo) {
        this.indActivo = indActivo;
    }
    
    
}

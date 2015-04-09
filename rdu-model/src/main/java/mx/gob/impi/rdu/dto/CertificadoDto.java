/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import java.util.Date;
import mx.gob.impi.rdu.persistence.model.CatEstatusCertificado;

/**
 *
 * @author
 */
public class CertificadoDto implements  Serializable{

    public CertificadoDto() {
    }

    public CertificadoDto(Long idCertificado) {
        this.idCertificado = idCertificado;
    }

    public CertificadoDto(Integer idEstatusCertificado, Integer idCatArea) {
        this.idEstatusCertificado = idEstatusCertificado;
        this.idCatArea = idCatArea;
    }

    public CertificadoDto(CatEstatusCertificadoDto estatus,  Long idCertificado, String nombreAdmin, String numeroSerie, Integer idEstatusCertificado, String pass, Date fechaCarga, String usuario, Long prioridad) {
        this.idCertificado = idCertificado;
        this.nombreAdmin = nombreAdmin;
        this.numeroSerie = numeroSerie;
        this.idEstatusCertificado = idEstatusCertificado;
        this.catEstatusCertificadoDto = estatus;
        this.pass = pass;
        this.fechaCarga = fechaCarga;
        this.usuario = usuario;
        this.prioridad = prioridad;
    }
    
    
    
    
    private Long idCertificado;

    private String nombreAdmin;

    private String numeroSerie;

    private Integer idEstatusCertificado;
    
    private CatEstatusCertificadoDto catEstatusCertificadoDto = new CatEstatusCertificadoDto();

    private String pass;

    private Date fechaCarga;

    private String usuario;

    private Long prioridad;

    private byte[] keyFile;

    private byte[] cerFile;
    
    private Integer idCatArea;

    private CatAreaDto catAreaDto= new CatAreaDto();

    public CatAreaDto getCatAreaDto() {
        return catAreaDto;
    }

    public void setCatAreaDto(CatAreaDto catAreaDto) {
        this.catAreaDto = catAreaDto;
    }
    
    public Integer getIdCatArea() {
        return idCatArea;
    }

    public void setIdCatArea(Integer idCatArea) {
        this.idCatArea = idCatArea;
    }
    
    public CatEstatusCertificadoDto getCatEstatusCertificadoDto() {
        return catEstatusCertificadoDto;
    }

    public byte[] getCerFile() {
        return cerFile;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public Integer getIdEstatusCertificado() {
        return idEstatusCertificado;
    }



    public byte[] getKeyFile() {
        return keyFile;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getPass() {
        return pass;
    }

    public Long getPrioridad() {
        return prioridad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setCatEstatusCertificadoDto(CatEstatusCertificadoDto catEstatusCertificado) {
        this.catEstatusCertificadoDto = catEstatusCertificado;
    }

    public void setCerFile(byte[] cerFile) {
        this.cerFile = cerFile;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public void setIdEstatusCertificado(Integer idEstatusCertificado) {
        this.idEstatusCertificado = idEstatusCertificado;
    }

    public Long getIdCertificado() {
        return idCertificado;
    }

    public void setIdCertificado(Long idCertificado) {
        this.idCertificado = idCertificado;
    }

  

    public void setKeyFile(byte[] keyFile) {
        this.keyFile = keyFile;
    }

    public void setNombreAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setPrioridad(Long prioridad) {
        this.prioridad = prioridad;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    
    
}

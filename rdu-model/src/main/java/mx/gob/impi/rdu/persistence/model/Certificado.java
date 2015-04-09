package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.util.Date;

public class Certificado implements  Serializable{

    public Certificado() {
    }

    public Certificado(Integer idEstatusCertificado, Integer idCatArea) {
        this.idEstatusCertificado = idEstatusCertificado;
        this.idCatArea = idCatArea;
    }
    
    
    
    private Long idCertificado;

    private String nombreAdmin;

    private String numeroSerie;

    private Integer idEstatusCertificado;
    
    private Integer idCatArea;
    
    private CatArea catArea;
    
    private CatEstatusCertificado catEstatusCertificado;

    private String pass;

    private Date fechaCarga;

    private String usuario;

    private Long prioridad;

    private byte[] keyFile;

    private byte[] cerFile;

    public CatArea getCatArea() {
        return catArea;
    }

    public Integer getIdCatArea() {
        return idCatArea;
    }

    public void setCatArea(CatArea catArea) {
        this.catArea = catArea;
    }

    public void setIdCatArea(Integer idCatArea) {
        this.idCatArea = idCatArea;
    }
    
    public CatEstatusCertificado getCatEstatusCertificado() {
        return catEstatusCertificado;
    }

    public void setCatEstatusCertificado(CatEstatusCertificado catEstatusCertificado) {
        this.catEstatusCertificado = catEstatusCertificado;
    }

    public Long getIdCertificado() {
        return idCertificado;
    }

    public void setIdCertificado(Long idCertificado) {
        this.idCertificado = idCertificado;
    }
    
    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin == null ? null : nombreAdmin.trim();
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie == null ? null : numeroSerie.trim();
    }

    public Integer getIdEstatusCertificado() {
        return idEstatusCertificado;
    }

    public void setIdEstatusCertificado(Integer idEstatusCertificado) {
        this.idEstatusCertificado = idEstatusCertificado;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass == null ? null : pass.trim();
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario == null ? null : usuario.trim();
    }

    public Long getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Long prioridad) {
        this.prioridad = prioridad;
    }

    public byte[] getKeyFile() {
        return keyFile;
    }

    public void setKeyFile(byte[] keyFile) {
        this.keyFile = keyFile;
    }

    public byte[] getCerFile() {
        return cerFile;
    }

    public void setCerFile(byte[] cerFile) {
        this.cerFile = cerFile;
    }
}
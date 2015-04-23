package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class UsuariosSigappi implements Serializable {

    private String cveUsuario;
    private String password;
    private String nombre;
    private Integer indAdministrador;
    private String area;
    private Integer indActivo;
    private Integer cveArea;
    private Integer indAnalista;
    private String impresora;
    private Integer cvePerfil;

    public String getCveUsuario() {
        return cveUsuario;
    }

    public void setCveUsuario(String cveUsuario) {
        this.cveUsuario = cveUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIndAdministrador() {
        return indAdministrador;
    }

    public void setIndAdministrador(Integer indAdministrador) {
        this.indAdministrador = indAdministrador;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Integer indActivo) {
        this.indActivo = indActivo;
    }

    public Integer getCveArea() {
        return cveArea;
    }

    public void setCveArea(Integer cveArea) {
        this.cveArea = cveArea;
    }

    public Integer getIndAnalista() {
        return indAnalista;
    }

    public void setIndAnalista(Integer indAnalista) {
        this.indAnalista = indAnalista;
    }

    public String getImpresora() {
        return impresora;
    }

    public void setImpresora(String impresora) {
        this.impresora = impresora;
    }

    public Integer getCvePerfil() {
        return cvePerfil;
    }

    public void setCvePerfil(Integer cvePerfil) {
        this.cvePerfil = cvePerfil;
    }

}

package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

/**
 *
 * @author CESAR CASTAÑEDA REYES
 */
public class CodigosPostales implements Serializable {

    private String codigoPostal;
    private String asentamiento;
    private String tipoAsentamiento;
    private String municipio;
    private String estado;
    private Integer cveCodigoPostal;
    private Long cveEstado;
    private Integer cveOficina;
    private Integer cveCp;
    private Long cveTipoAsentamiento;
    private Integer cveMunicipio;
    private String ciudad;

    public String getAsentamiento() {
        return asentamiento;
    }

    public void setAsentamiento(String asentamiento) {
        this.asentamiento = asentamiento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Integer getCveCodigoPostal() {
        return cveCodigoPostal;
    }

    public void setCveCodigoPostal(Integer cveCodigoPostal) {
        this.cveCodigoPostal = cveCodigoPostal;
    }

    public Integer getCveCp() {
        return cveCp;
    }

    public void setCveCp(Integer cveCp) {
        this.cveCp = cveCp;
    }

    public Long getCveEstado() {
        return cveEstado;
    }

    public void setCveEstado(Long cveEstado) {
        this.cveEstado = cveEstado;
    }

    public Integer getCveMunicipio() {
        return cveMunicipio;
    }

    public void setCveMunicipio(Integer cveMunicipio) {
        this.cveMunicipio = cveMunicipio;
    }

    public Integer getCveOficina() {
        return cveOficina;
    }

    public void setCveOficina(Integer cveOficina) {
        this.cveOficina = cveOficina;
    }

    public Long getCveTipoAsentamiento() {
        return cveTipoAsentamiento;
    }

    public void setCveTipoAsentamiento(Long cveTipoAsentamiento) {
        this.cveTipoAsentamiento = cveTipoAsentamiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getTipoAsentamiento() {
        return tipoAsentamiento;
    }

    public void setTipoAsentamiento(String tipoAsentamiento) {
        this.tipoAsentamiento = tipoAsentamiento;
    }
}

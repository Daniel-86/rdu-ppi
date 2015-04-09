package mx.gob.impi.pase.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Promovente implements Serializable{

    public Promovente() {
    }

    public Promovente(BigDecimal idPromovente, String nombre) {
        this.idPromovente = idPromovente;
        this.nombre = nombre;
    }
    
    
    private BigDecimal idPromovente;

    private String login;

    private String password;

    private String nombre;

    private String apaterno;

    private String amaterno;

    private String rfc;

    private String calleNumero;

    private String colonia;

    private String telefono;

    private String fax;

    private String email;

    private String razonSocial;

    private Short habilitado;

    private Date fechaRegistro;

    private Date fechaActivacion;

    private BigDecimal idPromoventePadre;

    private BigDecimal idPerfil;

    private Short idEstado;

    private Long idMunicipio;

    private Short tipoPersona;

    private BigDecimal idTipoPromovente;

    private String codigoPostal;

    private String cargo;

    private String numeroExterior;

    private String numeroInterior;

    private Short habilitaMarcanet;

    public BigDecimal getIdPromovente() {
        return idPromovente;
    }

    public void setIdPromovente(BigDecimal idPromovente) {
        this.idPromovente = idPromovente;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login == null ? null : login.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre == null ? null : nombre.trim();
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno == null ? null : apaterno.trim();
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno == null ? null : amaterno.trim();
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc == null ? null : rfc.trim();
    }

    public String getCalleNumero() {
        return calleNumero;
    }

    public void setCalleNumero(String calleNumero) {
        this.calleNumero = calleNumero == null ? null : calleNumero.trim();
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia == null ? null : colonia.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono == null ? null : telefono.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial == null ? null : razonSocial.trim();
    }

    public Short getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Short habilitado) {
        this.habilitado = habilitado;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(Date fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public BigDecimal getIdPromoventePadre() {
        return idPromoventePadre;
    }

    public void setIdPromoventePadre(BigDecimal idPromoventePadre) {
        this.idPromoventePadre = idPromoventePadre;
    }

    public BigDecimal getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(BigDecimal idPerfil) {
        this.idPerfil = idPerfil;
    }

    public Short getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Short idEstado) {
        this.idEstado = idEstado;
    }

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public Short getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(Short tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public BigDecimal getIdTipoPromovente() {
        return idTipoPromovente;
    }

    public void setIdTipoPromovente(BigDecimal idTipoPromovente) {
        this.idTipoPromovente = idTipoPromovente;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal == null ? null : codigoPostal.trim();
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo == null ? null : cargo.trim();
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior == null ? null : numeroExterior.trim();
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior == null ? null : numeroInterior.trim();
    }

    public Short getHabilitaMarcanet() {
        return habilitaMarcanet;
    }

    public void setHabilitaMarcanet(Short habilitaMarcanet) {
        this.habilitaMarcanet = habilitaMarcanet;
    }
}
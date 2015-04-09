/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import java.util.List;
/**
 *
 * @author usradmin
 */
public class PromoventeDto implements Serializable{
    private int id_promovente;
    private String nombre;
    private String apaterno;
    private String amaterno;
    private String login;
    private String password;
    private String email;
    private String rfc;

    private String calle_numero;
    private String numero_exterior;
    private String numero_interior;
    private String colonia;
    private String codigo_postal;
    private String telefono;
    private String fax;

    private String razon_social;
    private String fecha_registro;
    private String fecha_activacion;
    private int habilitado;

    private int id_perfil;
    private String descPerfil;
    private int id_promovente_padre;

    private int id_estado;
    private String descEstado;
    private int id_municipio;
    private String descMunicipio;

    private int tipo_persona;
    private String descTipoPromovente;

    private String cargo;
    private int habilita_marcanet;
    private int id_tramite;

    private List<ApoderadoDto> apoderados;

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public List<ApoderadoDto> getApoderados() {
        return apoderados;
    }

    public void setApoderados(List<ApoderadoDto> apoderados) {
        this.apoderados = apoderados;
    }


    public String getCalle_numero() {
        return calle_numero;
    }

    public void setCalle_numero(String calle_numero) {
        this.calle_numero = calle_numero;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getDescEstado() {
        return descEstado;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

    public String getDescMunicipio() {
        return descMunicipio;
    }

    public void setDescMunicipio(String descMunicipio) {
        this.descMunicipio = descMunicipio;
    }

    public String getDescPerfil() {
        return descPerfil;
    }

    public void setDescPerfil(String descPerfil) {
        this.descPerfil = descPerfil;
    }

    public String getDescTipoPromovente() {
        return descTipoPromovente;
    }

    public void setDescTipoPromovente(String descTipoPromovente) {
        this.descTipoPromovente = descTipoPromovente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFecha_activacion() {
        return fecha_activacion;
    }

    public void setFecha_activacion(String fecha_activacion) {
        this.fecha_activacion = fecha_activacion;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getHabilita_marcanet() {
        return habilita_marcanet;
    }

    public void setHabilita_marcanet(int habilita_marcanet) {
        this.habilita_marcanet = habilita_marcanet;
    }

    public int getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(int habilitado) {
        this.habilitado = habilitado;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public int getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(int id_municipio) {
        this.id_municipio = id_municipio;
    }

    public int getId_perfil() {
        return id_perfil;
    }

    public void setId_perfil(int id_perfil) {
        this.id_perfil = id_perfil;
    }

    public int getId_promovente() {
        return id_promovente;
    }

    public void setId_promovente(int id_promovente) {
        this.id_promovente = id_promovente;
    }

    public int getId_promovente_padre() {
        return id_promovente_padre;
    }

    public void setId_promovente_padre(int id_promovente_padre) {
        this.id_promovente_padre = id_promovente_padre;
    }

    public int getId_tramite() {
        return id_tramite;
    }

    public void setId_tramite(int id_tramite) {
        this.id_tramite = id_tramite;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero_exterior() {
        return numero_exterior;
    }

    public void setNumero_exterior(String numero_exterior) {
        this.numero_exterior = numero_exterior;
    }

    public String getNumero_interior() {
        return numero_interior;
    }

    public void setNumero_interior(String numero_interior) {
        this.numero_interior = numero_interior;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getTipo_persona() {
        return tipo_persona;
    }

    public void setTipo_persona(int tipo_persona) {
        this.tipo_persona = tipo_persona;
    }

}

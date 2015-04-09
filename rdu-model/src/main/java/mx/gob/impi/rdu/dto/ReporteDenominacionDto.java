/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import mx.gob.impi.rdu.persistence.model.Datoscontacto;
import mx.gob.impi.rdu.persistence.model.Domicilio;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;

/**
 *
 * @author
 */
public class ReporteDenominacionDto {

    public ReporteDenominacionDto(TramitePromocionMarca promoMarca) {
        this.promo = promoMarca;
    }

    public ReporteDenominacionDto(TramitePromocionMarca promoMarca, String rutaImagen, FirmaDto firma) {
        this.promo = promoMarca;
        this.rutaFirmaImpi = rutaImagen;
        this.firma = firma;
        this.convertirTramite();
    }
    private FirmaDto firma;
    private TramitePromocionMarca promo;
    private String denominacion;
    private String fechaPresentacion;
    private String folio;
    private String codigoBarras;
    private String solNombre;
    private String solNacionalidad;
    private String solCalle;
    private String solColonia;
    private String solPoblacion;
    private String solEstado;
    private String solCodigoPostal;
    private String solPais;
    private String solTelefono;
    private String solEmail;
    private String estCalle;
    private String estColonia;
    private String estPoblacion;
    private String estEstado;
    private String estCodigoPostal;
    private String estTelefono;
    private String estEmail;
    private String notCalle;
    private String notColonia;
    private String notPoblacion;
    private String notEstado;
    private String notCodigoPostal;
    private String notTelefono;
    private String notEmail;
    private String cadenaImpi;
    private String cadenaSolicitante;
    private String selloImpi;
    private String selloSolicitante;
    private String certificadora;
    private String rutaFirmaImpi;
    private String expediente;
    private String fecha;

    public void convertirTramite() {
        this.setDenominacion(this.promo.getIdDenominacion().toString());
        this.setFolio(this.promo.getFolio());
        // this.setCodigoBarras(promo.getCodBarras()==null ? "":promo.getCodBarras());
        Persona perSol = promo.getListaTramitePersona().get(0).getPersona();
        this.setSolCalle(perSol.getDomicilioObj().getCalle());
        this.setSolCodigoPostal(perSol.getDomicilioObj().getCodigopostal().toString());
        this.setSolColonia(perSol.getDomicilioObj().getColonia());
        Datoscontacto contacto = new Datoscontacto();
        contacto = perSol.getDatosContacto();
        if (contacto != null) {
            this.setSolEmail(contacto.getCorreoelectronico());
            this.setSolTelefono(contacto.getTelefono());
        }
        this.setCadenaImpi(this.firma.getCadenaImpi());
        this.setCadenaSolicitante(firma.getCadenaSolicitante());
        this.setCodigoBarras(this.firma.getCodigoBarras() == null ? "123" : this.firma.getCodigoBarras().toString());
        this.setSelloImpi(this.firma.getFirmaImpi());
        this.setSelloSolicitante(this.firma.getFirmaSolicitante());

        this.setSolEstado(perSol.getDomicilioObj().getPoblacion());
        this.setSolNacionalidad(perSol.getNacionalidad()==null?"": perSol.getNacionalidad().getNacionalidad()==null?"":perSol.getNacionalidad().getNacionalidad());
        this.setSolNombre(perSol.getNombrecompleto());
        this.setSolPais(perSol.getPais() == null ? "" : perSol.getPais().getNombre());
        this.setSolPoblacion(perSol.getDomicilioObj().getPoblacion());

        if (this.promo.getEstablecimiento() != null && this.promo.getEstablecimiento().getDomicilio() != null) {
            Domicilio est = this.promo.getEstablecimiento().getDomicilio();
            this.setEstCalle(est.getCalle());
            this.setEstCodigoPostal(est.getCodigopostal());
            this.setEstColonia(est.getColonia());
            this.setEstEstado(est.getPoblacion());
            this.setEstPoblacion(est.getPoblacion());

        }

        if (this.promo.getNotificacion() != null && this.promo.getNotificacion().getDomicilio() != null) {
            Domicilio dom = this.promo.getNotificacion().getDomicilio();
            this.setNotCalle(dom.getCalle());
            this.setNotCodigoPostal(dom.getCodigopostal());
            this.setNotColonia(dom.getColonia());
            this.setNotEstado(dom.getPoblacion());
            this.setNotPoblacion(dom.getPoblacion());


        }

        if (this.promo.getNotificacion() != null && this.promo.getNotificacion().getDatosContacto() != null) {
            Datoscontacto cont = this.promo.getNotificacion().getDatosContacto();
            this.setNotEmail(cont.getCorreoelectronico());
            this.setNotTelefono(cont.getTelefono());
        }


    }

    public String getCadenaImpi() {
        return cadenaImpi;
    }

    public void setCadenaImpi(String cadenaImpi) {
        this.cadenaImpi = cadenaImpi;
    }

    public String getCadenaSolicitante() {
        return cadenaSolicitante;
    }

    public void setCadenaSolicitante(String cadenaSolicitante) {
        this.cadenaSolicitante = cadenaSolicitante;
    }

    public String getCertificadora() {
        return certificadora;
    }

    public void setCertificadora(String certificadora) {
        this.certificadora = certificadora;
    }

    public String getEstEmail() {
        return estEmail;
    }

    public FirmaDto getFirma() {
        return firma;
    }

    public void setFirma(FirmaDto firma) {
        this.firma = firma;
    }

    public void setEstEmail(String estEmail) {
        this.estEmail = estEmail;
    }

    public String getEstTelefono() {
        return estTelefono;
    }

    public void setEstTelefono(String estTelefono) {
        this.estTelefono = estTelefono;
    }

    public String getSelloImpi() {
        return selloImpi;
    }

    public void setSelloImpi(String selloImpi) {
        this.selloImpi = selloImpi;
    }

    public String getSelloSolicitante() {
        return selloSolicitante;
    }

    public void setSelloSolicitante(String selloSolicitante) {
        this.selloSolicitante = selloSolicitante;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getEstCalle() {
        return estCalle;
    }

    public void setEstCalle(String estCalle) {
        this.estCalle = estCalle;
    }

    public String getEstCodigoPostal() {
        return estCodigoPostal;
    }

    public void setEstCodigoPostal(String estCodigoPostal) {
        this.estCodigoPostal = estCodigoPostal;
    }

    public String getEstColonia() {
        return estColonia;
    }

    public void setEstColonia(String estColonia) {
        this.estColonia = estColonia;
    }

    public String getEstEstado() {
        return estEstado;
    }

    public void setEstEstado(String estEstado) {
        this.estEstado = estEstado;
    }

    public TramitePromocionMarca getPromo() {
        return promo;
    }

    public void setPromo(TramitePromocionMarca promo) {
        this.promo = promo;
    }

    public String getEstPoblacion() {
        return estPoblacion;
    }

    public void setEstPoblacion(String estPoblacion) {
        this.estPoblacion = estPoblacion;
    }

    public String getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(String fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNotCalle() {
        return notCalle;
    }

    public void setNotCalle(String notCalle) {
        this.notCalle = notCalle;
    }

    public String getNotCodigoPostal() {
        return notCodigoPostal;
    }

    public void setNotCodigoPostal(String notCodigoPostal) {
        this.notCodigoPostal = notCodigoPostal;
    }

    public String getNotColonia() {
        return notColonia;
    }

    public void setNotColonia(String notColonia) {
        this.notColonia = notColonia;
    }

    public String getNotEmail() {
        return notEmail;
    }

    public void setNotEmail(String notEmail) {
        this.notEmail = notEmail;
    }

    public String getNotEstado() {
        return notEstado;
    }

    public void setNotEstado(String notEstado) {
        this.notEstado = notEstado;
    }

    public String getNotPoblacion() {
        return notPoblacion;
    }

    public void setNotPoblacion(String notPoblacion) {
        this.notPoblacion = notPoblacion;
    }

    public String getNotTelefono() {
        return notTelefono;
    }

    public void setNotTelefono(String notTelefono) {
        this.notTelefono = notTelefono;
    }

    public String getSolCalle() {
        return solCalle;
    }

    public void setSolCalle(String solCalle) {
        this.solCalle = solCalle;
    }

    public String getSolCodigoPostal() {
        return solCodigoPostal;
    }

    public void setSolCodigoPostal(String solCodigoPostal) {
        this.solCodigoPostal = solCodigoPostal;
    }

    public String getSolColonia() {
        return solColonia;
    }

    public void setSolColonia(String solColonia) {
        this.solColonia = solColonia;
    }

    public String getSolEmail() {
        return solEmail;
    }

    public void setSolEmail(String solEmail) {
        this.solEmail = solEmail;
    }

    public String getSolEstado() {
        return solEstado;
    }

    public void setSolEstado(String solEstado) {
        this.solEstado = solEstado;
    }

    public String getSolNacionalidad() {
        return solNacionalidad;
    }

    public void setSolNacionalidad(String solNacionalidad) {
        this.solNacionalidad = solNacionalidad;
    }

    public String getSolNombre() {
        return solNombre;
    }

    public void setSolNombre(String solNombre) {
        this.solNombre = solNombre;
    }

    public String getSolPais() {
        return solPais;
    }

    public void setSolPais(String solPais) {
        this.solPais = solPais;
    }

    public String getSolPoblacion() {
        return solPoblacion;
    }

    public void setSolPoblacion(String solPoblacion) {
        this.solPoblacion = solPoblacion;
    }

    public String getSolTelefono() {
        return solTelefono;
    }

    public void setSolTelefono(String solTelefono) {
        this.solTelefono = solTelefono;
    }

    public String getRutaFirmaImpi() {
        return rutaFirmaImpi;
    }

    public void setRutaFirmaImpi(String rutaFirmaImpi) {
        this.rutaFirmaImpi = rutaFirmaImpi;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

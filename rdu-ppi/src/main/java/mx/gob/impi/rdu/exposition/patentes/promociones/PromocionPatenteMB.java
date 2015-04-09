/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.exposition.patentes.promociones;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import mx.gob.impi.rdu.persistence.model.CatTiposolicitud;

/**
 *
 * @author marisol
 */

@ManagedBean(name = "promocionPatenteMB")
@ViewScoped
public class PromocionPatenteMB implements Serializable{


    private String oficinaOficio;
    private String anioOficio;
    private int folioOficio;
    private String descripcionOficio;

    private String tipoPromocion;
    private String areaPromocion;

    private String numExpediente;
    private String registroConcedido;

    private String descripcionPromocion;

    private String plazoAdicional;
    private boolean aplicaDescuento;
    private String descuento;

    private List<String> errores;
    private boolean verErrores;

    private List<CatTiposolicitud> listTiposSol = Collections.emptyList();
    private List<String> listAnio = new ArrayList();
    private List<String> listPlazo = new ArrayList();
    
    @PostConstruct
    public void init() {
        
        //listAnio = CatExpedientAnioWebService.getAnio(expediente);
        getListAnio().add("2010");
        getListAnio().add("2011");
        getListAnio().add("2012");
        getListPlazo().add("1");
        getListPlazo().add("2");
    
    }
    
    
    public void mostrar(){
        System.out.println("Oficina: "+ this.oficinaOficio);
        System.out.println("Año: "+ this.anioOficio);
        System.out.println("Folio: "+ this.folioOficio);
        
    }
    
    
    public void guardar(int finalizar) {
        String msgAviso = "";
        String msgAvisoAct = "";
        boolean resul = false;
        boolean completa = true;
    
    }

    /**
     * @return the oficinaOficio
     */
    public String getOficinaOficio() {
        return oficinaOficio;
    }

    /**
     * @param oficinaOficio the oficinaOficio to set
     */
    public void setOficinaOficio(String oficinaOficio) {
        this.oficinaOficio = oficinaOficio;
    }

    /**
     * @return the anioOficio
     */
    public String getAnioOficio() {
        return anioOficio;
    }

    /**
     * @param anioOficio the anioOficio to set
     */
    public void setAnioOficio(String anioOficio) {
        this.anioOficio = anioOficio;
    }

    /**
     * @return the folioOficio
     */
    public int getFolioOficio() {
        return folioOficio;
    }

    /**
     * @param folioOficio the folioOficio to set
     */
    public void setFolioOficio(int folioOficio) {
        this.folioOficio = folioOficio;
    }

    /**
     * @return the descripcionOficio
     */
    public String getDescripcionOficio() {
        return descripcionOficio;
    }

    /**
     * @param descripcionOficio the descripcionOficio to set
     */
    public void setDescripcionOficio(String descripcionOficio) {
        this.descripcionOficio = descripcionOficio;
    }

    /**
     * @return the tipoPromocion
     */
    public String getTipoPromocion() {
        return tipoPromocion;
    }

    /**
     * @param tipoPromocion the tipoPromocion to set
     */
    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    /**
     * @return the areaPromocion
     */
    public String getAreaPromocion() {
        return areaPromocion;
    }

    /**
     * @param areaPromocion the areaPromocion to set
     */
    public void setAreaPromocion(String areaPromocion) {
        this.areaPromocion = areaPromocion;
    }

    /**
     * @return the numExpediente
     */
    public String getNumExpediente() {
        return numExpediente;
    }

    /**
     * @param numExpediente the numExpediente to set
     */
    public void setNumExpediente(String numExpediente) {
        this.numExpediente = numExpediente;
    }

    /**
     * @return the registroConcedido
     */
    public String getRegistroConcedido() {
        return registroConcedido;
    }

    /**
     * @param registroConcedido the registroConcedido to set
     */
    public void setRegistroConcedido(String registroConcedido) {
        this.registroConcedido = registroConcedido;
    }

    /**
     * @return the descripcionPromocion
     */
    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    /**
     * @param descripcionPromocion the descripcionPromocion to set
     */
    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    /**
     * @return the plazoAdicional
     */
    public String getPlazoAdicional() {
        return plazoAdicional;
    }

    /**
     * @param plazoAdicional the plazoAdicional to set
     */
    public void setPlazoAdicional(String plazoAdicional) {
        this.plazoAdicional = plazoAdicional;
    }

    /**
     * @return the aplicaDescuento
     */
    public boolean isAplicaDescuento() {
        return aplicaDescuento;
    }

    /**
     * @param aplicaDescuento the aplicaDescuento to set
     */
    public void setAplicaDescuento(boolean aplicaDescuento) {
        this.aplicaDescuento = aplicaDescuento;
    }

    /**
     * @return the errores
     */
    public List<String> getErrores() {
        return errores;
    }

    /**
     * @param errores the errores to set
     */
    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    /**
     * @return the verErrores
     */
    public boolean isVerErrores() {
        return verErrores;
    }

    /**
     * @param verErrores the verErrores to set
     */
    public void setVerErrores(boolean verErrores) {
        this.verErrores = verErrores;
    }

    /**
     * @return the listTiposSol
     */
    public List<CatTiposolicitud> getListTiposSol() {
        return listTiposSol;
    }

    /**
     * @param listTiposSol the listTiposSol to set
     */
    public void setListTiposSol(List<CatTiposolicitud> listTiposSol) {
        this.listTiposSol = listTiposSol;
    }

    /**
     * @return the listAnio
     */
    public List<String> getListAnio() {
        return listAnio;
    }

    /**
     * @param listAnio the listAnio to set
     */
    public void setListAnio(List<String> listAnio) {
        this.listAnio = listAnio;
    }

    /**
     * @return the listPlazo
     */
    public List<String> getListPlazo() {
        return listPlazo;
    }

    /**
     * @param listPlazo the listPlazo to set
     */
    public void setListPlazo(List<String> listPlazo) {
        this.listPlazo = listPlazo;
    }

    /**
     * @return the descuento
     */
    public String getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

   

    

}

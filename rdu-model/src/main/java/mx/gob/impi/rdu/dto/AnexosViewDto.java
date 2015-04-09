/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;

/**
 *
 * @author winter
 */
@SuppressWarnings("serial")
public class AnexosViewDto implements Serializable {

    private Long idTipoanexo;
    private String descripcion;
    private Short obligatorio;
    private Long idSubtiposolicitud;
    private Short visiblexcarga;
    private Boolean cargado;
    private Short orden;
    private Integer idArea;
    private long idTipomarca;
    private long idAnexo;
    private long idTramite;
    private long idTramitePatente;
    private Long idTramitePromocionMarcas;
    private byte[] archivoAnexo;
    private String extension;
    private String nombreArchivo;
    private Integer prioridad;
    private Integer reglasUso;
    private Integer comprobacionFechaDiv;
    private Integer notificaciones;
    private Integer doctoAcreditaPersonalidad;
    private Integer cesionDerechos;
    private String textoAyuda;
    private int idTipoTramite;
    private Integer numeroHojas;
    private Integer index;
    private Long idPrioridad;
    private Long idSolicitante;
    private String txtAnexo;
    private Short otroIdioma=1;
    private Long idTipoanexoTrad;
    private byte[] archivoTrad;
    private String nombreTrad;
    private String txtAnexoTrad;
    private Boolean tradCargada;


    public Short getOtroIdioma() {
        return otroIdioma;
    }

    public void setOtroIdioma(Short otroIdioma) {
        this.otroIdioma = otroIdioma;
    }
    
    public String getTxtAnexo() {
        return txtAnexo;
    }

    public void setTxtAnexo(String txtAnexo) {
        this.txtAnexo = txtAnexo;
    }

    public Long getIdTramitePromocionMarcas() {
        return idTramitePromocionMarcas;
    }

    public void setIdTramitePromocionMarcas(Long idTramitePromocionMarcas) {
        this.idTramitePromocionMarcas = idTramitePromocionMarcas;
    }

    public Integer getCesionDerechos() {
        return cesionDerechos;
    }

    public void setCesionDerechos(Integer cesionDerechos) {
        this.cesionDerechos = cesionDerechos;
    }

    public Integer getDoctoAcreditaPersonalidad() {
        return doctoAcreditaPersonalidad;
    }

    public void setDoctoAcreditaPersonalidad(Integer doctoAcreditaPersonalidad) {
        this.doctoAcreditaPersonalidad = doctoAcreditaPersonalidad;
    }

    public Integer getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Integer notificaciones) {
        this.notificaciones = notificaciones;
    }

    public Integer getComprobacionFechaDiv() {
        return comprobacionFechaDiv;
    }

    public void setComprobacionFechaDiv(Integer comprobacionFechaDiv) {
        this.comprobacionFechaDiv = comprobacionFechaDiv;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public long getIdTramitePatente() {
        return idTramitePatente;
    }

    public void setIdTramitePatente(long idTramitePatente) {
        this.idTramitePatente = idTramitePatente;
    }

    public byte[] getArchivoAnexo() {
        return archivoAnexo;
    }

    public void setArchivoAnexo(byte[] archivoAnexo) {
        this.archivoAnexo = archivoAnexo;
    }

    public Boolean getCargado() {
        return cargado;
    }

    public void setCargado(Boolean cargado) {
        this.cargado = cargado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getIdAnexo() {
        return idAnexo;
    }

    public void setIdAnexo(long idAnexo) {
        this.idAnexo = idAnexo;
    }

    public Long getIdSubtiposolicitud() {
        return idSubtiposolicitud;
    }

    public void setIdSubtiposolicitud(Long idSubtiposolicitud) {
        this.idSubtiposolicitud = idSubtiposolicitud;
    }

    public Long getIdTipoanexo() {
        return idTipoanexo;
    }

    public void setIdTipoanexo(Long idTipoanexo) {
        this.idTipoanexo = idTipoanexo;
    }

    public long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(long idTramite) {
        this.idTramite = idTramite;
    }

    public Short getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Short obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Short getVisiblexcarga() {
        return visiblexcarga;
    }

    public void setVisiblexcarga(Short visiblexcarga) {
        this.visiblexcarga = visiblexcarga;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Short getOrden() {
        return orden;
    }

    public void setOrden(Short orden) {
        this.orden = orden;
    }

    public long getIdTipomarca() {
        return idTipomarca;
    }

    public void setIdTipomarca(long idTipomarca) {
        this.idTipomarca = idTipomarca;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Integer getReglasUso() {
        return reglasUso;
    }

    public void setReglasUso(Integer reglasUso) {
        this.reglasUso = reglasUso;
    }

    public String getTextoAyuda() {
        return textoAyuda;
    }

    public void setTextoAyuda(String textoAyuda) {
        this.textoAyuda = textoAyuda;
    }

    public Integer getNumeroHojas() {
        return numeroHojas;
    }

    public void setNumeroHojas(Integer numeroHojas) {
        this.numeroHojas = numeroHojas;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Long getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public Long getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Long idSolicitante) {
        this.idSolicitante = idSolicitante;
    }
    
    public Long getIdTipoanexoTrad() {
        return idTipoanexoTrad;
    }

    public void setIdTipoanexoTrad(Long idTipoanexoTrad) {
        this.idTipoanexoTrad = idTipoanexoTrad;
    }

    public byte[] getArchivoTrad() {
        return archivoTrad;
    }

    public void setArchivoTrad(byte[] archivoTrad) {
        this.archivoTrad = archivoTrad;
    }

    public String getNombreTrad() {
        return nombreTrad;
    }

    public void setNombreTrad(String nombreTrad) {
        this.nombreTrad = nombreTrad;
    }
    
    public String getTxtAnexoTrad() {
        return txtAnexoTrad;
    }

    public void setTxtAnexoTrad(String txtAnexoTrad) {
        this.txtAnexoTrad = txtAnexoTrad;
    }
    
    
    public Boolean getTradCargada() {
        return tradCargada;
    }

    public void setTradCargada(Boolean tradCargada) {
        this.tradCargada = tradCargada;
    }
    


}

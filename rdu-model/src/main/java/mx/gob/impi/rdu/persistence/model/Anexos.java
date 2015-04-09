package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class Anexos implements  Comparable<Anexos> , Serializable{
    private Long idAnexo;

    private Long idTipoanexo;

    private CatAnexos catAnexo;
    
    private Long idTramite;

    private byte[] archivoAnexo;

    private String extension;
    
    private int orden;

    private String nombreArchivo;
    private Long idTramitePatente;
    private Long idTramitePromocionMarcas;
    private String txtAnexo;
    private short otroIdioma;
    private Long idTipoanexoTrad;
    private byte[] archivoTrad;
    private String nombreTrad;
    private String txtAnexoTrad; 

    public String getTxtAnexoTrad() {
        return txtAnexoTrad;
    }

    public void setTxtAnexoTrad(String txtAnexoTrad) {
        this.txtAnexoTrad = txtAnexoTrad;
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

    public short getOtroIdioma() {
        return otroIdioma;
    }

    public void setOtroIdioma(short otroIdioma) {
        this.otroIdioma = otroIdioma;
    }

    public String getTxtAnexo() {
        return txtAnexo;
    }

    public void setTxtAnexo(String txtAnexo) {
        this.txtAnexo = txtAnexo;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    

    public Long getIdTramitePatente() {
        return idTramitePatente;
    }

    public void setIdTramitePatente(Long idTramitePatente) {
        this.idTramitePatente = idTramitePatente;
    }



    public CatAnexos getCatAnexo() {
        return catAnexo;
    }

    public void setCatAnexo(CatAnexos catAnexo) {
        this.catAnexo = catAnexo;
    }

    public Long getIdAnexo() {
        return idAnexo;
    }

    public void setIdAnexo(Long idAnexo) {
        this.idAnexo = idAnexo;
    }

    public Long getIdTipoanexo() {
        return idTipoanexo;
    }

    public void setIdTipoanexo(Long idTipoanexo) {
        this.idTipoanexo = idTipoanexo;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public byte[] getArchivoAnexo() {
        return archivoAnexo;
    }

    public void setArchivoAnexo(byte[] archivoAnexo) {
        this.archivoAnexo = archivoAnexo;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Long getIdTramitePromocionMarcas() {
        return idTramitePromocionMarcas;
    }

    public void setIdTramitePromocionMarcas(Long idTramitePromocionMarcas) {
        this.idTramitePromocionMarcas = idTramitePromocionMarcas;
    }

    public int compareTo(Anexos o) {        
        return this.orden -o.orden;        
    }
    

}
package mx.gob.impi.ucm.persistence.model;

import mx.gob.impi.ucm.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class Documentos  implements Serializable{
    
    private String codbarras;
    private String documento;
    private String cveArea;
    private String sentido;
    private Integer serie;
    private Integer folio;
    private String tipoDocumento;
    private String descripcion;
    private Date fecha;
    private String security;
    private String objId;
    private String contentId;
    private Date ucmMigrationDate;
    private Integer fullTextIndexedFlg;
    private Date fullTextIndexedDate;
    private String codHash;
    private Integer indModificado;
    private Integer numPaginas;
    private Integer indDesplegable;
    private Integer indLucene;

    public String getCodbarras() {
        return codbarras;
    }

    public void setCodbarras(String codbarras) {
        this.codbarras = codbarras;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCveArea() {
        return cveArea;
    }

    public void setCveArea(String cveArea) {
        this.cveArea = cveArea;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public Integer getFolio() {
        return folio;
    }

    public void setFolio(Integer folio) {
        this.folio = folio;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Date getUcmMigrationDate() {
        return ucmMigrationDate;
    }

    public void setUcmMigrationDate(Date ucmMigrationDate) {
        this.ucmMigrationDate = ucmMigrationDate;
    }

    public Integer getFullTextIndexedFlg() {
        return fullTextIndexedFlg;
    }

    public void setFullTextIndexedFlg(Integer fullTextIndexedFlg) {
        this.fullTextIndexedFlg = fullTextIndexedFlg;
    }

    public Date getFullTextIndexedDate() {
        return fullTextIndexedDate;
    }

    public void setFullTextIndexedDate(Date fullTextIndexedDate) {
        this.fullTextIndexedDate = fullTextIndexedDate;
    }

    public String getCodHash() {
        return codHash;
    }

    public void setCodHash(String codHash) {
        this.codHash = codHash;
    }

    public Integer getIndModificado() {
        return indModificado;
    }

    public void setIndModificado(Integer indModificado) {
        this.indModificado = indModificado;
    }

    public Integer getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(Integer numPaginas) {
        this.numPaginas = numPaginas;
    }

    public Integer getIndDesplegable() {
        return indDesplegable;
    }

    public void setIndDesplegable(Integer indDesplegable) {
        this.indDesplegable = indDesplegable;
    }

    public Integer getIndLucene() {
        return indLucene;
    }

    public void setIndLucene(Integer indLucene) {
        this.indLucene = indLucene;
    }



}
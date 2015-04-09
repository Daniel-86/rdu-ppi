package mx.gob.impi.rdu.persistence.model;

/**
 *
 * @author JBMM
 */
@SuppressWarnings("serial")
public class CatIdiomas implements java.io.Serializable{
    private Integer idIdiomas;
    private String descripcion;
    private Integer indActivo;

    public Integer getIdIdiomas() {
        return idIdiomas;
    }

    public void setIdIdiomas(Integer idIdiomas) {
        this.idIdiomas = idIdiomas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Integer indActivo) {
        this.indActivo = indActivo;
    }
    
}
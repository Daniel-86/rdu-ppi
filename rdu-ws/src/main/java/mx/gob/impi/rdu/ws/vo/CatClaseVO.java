package mx.gob.impi.rdu.ws.vo;
import java.io.Serializable;


/**
 *
 * @author usradmin
 */
public class CatClaseVO implements Serializable{
    private long idclase;
    private String descripcion;
    private String tipoClase;

    public CatClaseVO() {
        super();
    }

    public CatClaseVO(long idclase, String descripcion, String tipoClase) {
        super();
        this.idclase = idclase;
        this.descripcion = descripcion;
        this.tipoClase = tipoClase;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getIdclase() {
        return idclase;
    }

    public void setIdclase(long idclase) {
        this.idclase = idclase;
    }

    public String getTipoClase() {
        return tipoClase;
    }

    public void setTipoClase(String tipoClase) {
        this.tipoClase = tipoClase;
    }



}

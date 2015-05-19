package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class KfAlmacenar  implements Serializable{
    
    private String codbarras;
    private String title;
    private Integer yaimportado;
    private String title_ant;

    public String getCodbarras() {
        return codbarras;
    }

    public void setCodbarras(String codbarras) {
        this.codbarras = codbarras;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYaimportado() {
        return yaimportado;
    }

    public void setYaimportado(Integer yaimportado) {
        this.yaimportado = yaimportado;
    }

    public String getTitle_ant() {
        return title_ant;
    }

    public void setTitle_ant(String title_ant) {
        this.title_ant = title_ant;
    }

}
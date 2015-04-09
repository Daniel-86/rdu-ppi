package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class CatAreaPromPatentes  implements Serializable{
    private String idAreaPromPat;

    private String descripcionPromPat;

    public String getIdAreaPromPat() {
        return idAreaPromPat;
    }

    public void setIdAreaPromPat(String idAreaPromPat) {
        this.idAreaPromPat = idAreaPromPat;
    }

    public String getDescripcionPromPat() {
        return descripcionPromPat;
    }

    public void setDescripcionPromPat(String descripcionPromPat) {
        this.descripcionPromPat = descripcionPromPat;
    }
    
}
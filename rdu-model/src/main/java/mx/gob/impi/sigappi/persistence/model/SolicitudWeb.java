/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigappi.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author daniel
 */
public class SolicitudWeb implements Serializable{
    
    public static enum Status {
        SAVED(0),
        FINISHED(1),
        PRESENTED(2);
        
        private static final Map<Integer, Status> lookup
                = new HashMap<Integer, Status>();

        static {
            for (Status s : EnumSet.allOf(Status.class)) {
                lookup.put(s.getCode(), s);
            }
        }

        private int code;

        private Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static Status get(Integer code) {
            return lookup.get(code);
        }
        
        public static boolean isMember(Integer value) {
            return lookup.containsKey(value);
        }
    }
    
    Integer idSolicitud;
    Date fecha;
    String codBarras;
    Integer idPromovente;
    Integer idStatus;
    Integer tipoDocumento;

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public Integer getIdPromovente() {
        return idPromovente;
    }

    public void setIdPromovente(Integer idPromovente) {
        this.idPromovente = idPromovente;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public String niceStatus() {
        Status status = Status.isMember(idStatus)? Status.get(idStatus): null;
        return status != null? status.name(): "UNKNOWN";
    }
}

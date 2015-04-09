package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;

public class ImagenDibujo implements Serializable {

    private Long idImagenDibujo;
    private Long idTramite;
    private String nombreArchivo;
    private Long orden;
    private byte[] archivo;
    private Integer correcto;   

    public ImagenDibujo(Long idImagenDibujo, Long idTramite, Long orden, byte[] archivo, Integer correcto) {
        this.idImagenDibujo = idImagenDibujo;
        this.idTramite = idTramite;
        this.orden = orden;
        this.archivo = archivo;
        this.correcto = correcto;
    }
    
    public ImagenDibujo(ImagenDibujo imagenDibujo)
    {
        try {
            BeanUtils.copyProperties(this, imagenDibujo);
            
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ImagenDibujo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ImagenDibujo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ImagenDibujo() {
    }

    public Long getIdImagenDibujo() {
        return idImagenDibujo;
    }

    public void setIdImagenDibujo(Long idImagenDibujo) {
        this.idImagenDibujo = idImagenDibujo;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Integer getCorrecto() {
        return correcto;
    }

    public void setCorrecto(Integer correcto) {
        this.correcto = correcto;  
                
    }    
}

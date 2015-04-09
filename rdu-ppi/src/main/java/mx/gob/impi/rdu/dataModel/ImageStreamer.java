/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dataModel;

import java.io.ByteArrayInputStream;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * Clase que permite manejar dinamicamente un StreamedContent 
 * 
 * @author Guillermo Garcia
 * @version 1.0
 * @since 01/11/13
 */
@ManagedBean
@RequestScoped
public class ImageStreamer {

    private static StreamedContent defaultFileContent;
    private StreamedContent fileContent;
    private boolean mostrarImagen;

    @PostConstruct
    void init(){
        System.out.println("Inicializando la clase ImageStreamer...");
        setMostrarImagen(true);
    }
    
    public StreamedContent getFileContent() {
        ImagenDibujo dibujo;
        Map<String,Object> map = null;
        
       map= FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
            dibujo = (ImagenDibujo) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("imagen");
        
        if(dibujo != null && dibujo.getArchivo().length > 0 )
        {
             fileContent   = fileContent = new DefaultStreamedContent(new ByteArrayInputStream(dibujo.getArchivo()), "image/gif");
        }
        return fileContent;
    }

    public void setFileContent(StreamedContent fileContent) {
        this.fileContent = fileContent;
    }

    static {
        defaultFileContent = new DefaultStreamedContent();
    }

    public static StreamedContent getDefaultFileContent() {
        return defaultFileContent;
    }

    public static void setDefaultFileContent(StreamedContent defaultFileContent) {
        ImageStreamer.defaultFileContent = defaultFileContent;
    }

    public boolean isMostrarImagen() {
        return mostrarImagen;
    }

    public void setMostrarImagen(boolean mostrarImagen) {
        this.mostrarImagen = mostrarImagen;
    }
    
    
}
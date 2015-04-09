/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.exposition.anexos;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.service.AnexosServiceImpl;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author winter
 */
@ManagedBean(name = "UploadMB")
@ViewScoped
@SuppressWarnings("serial")
public class UploadMB implements Serializable{
    private String fileName = null;
    private long size = 0;
    private List<Anexos> tmpList;

    @ManagedProperty(value="#{anexosService}")
    private AnexosServiceImpl anexosService;

    public void setAnexosService(AnexosServiceImpl anexosService) {
        this.anexosService = anexosService;
    }
    
    public void uploadFile(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        fileName = file.getFileName();
        size = file.getSize();

        Anexos image = new Anexos();
        image.setIdAnexo(1L);
        image.setIdTipoanexo(1L);
        image.setIdTramite(1L);
        image.setArchivoAnexo(file.getContents());

        anexosService.insert(image);

        System.out.println("  ==> DENTRO DE SUBIR ARCHIVO BD");
        FacesMessage msg = new FacesMessage("Archivo cargado exitosamente", "ok");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
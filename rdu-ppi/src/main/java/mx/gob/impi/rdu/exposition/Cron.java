/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition;

import com.mx.impi.vidoc.contenedor.webservice.DocFile;
import com.mx.impi.vidoc.contenedor.webservice.InputDocumentDocInfo;
import com.mx.impi.vidoc.contenedor.webservice.WsImpiDocContainer;
import com.mx.impi.vidoc.contenedor.webservice.WsImpiDocContainer_Service;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.xml.ws.WebServiceRef;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.rdu.persistence.model.Notificacion;
import mx.gob.impi.rdu.service.FlujosGralesViewService;
import mx.gob.impi.rdu.util.JndiPropertiesUtils;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import mx.gob.impi.sigappi.persistence.model.KfAlmacenar;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.KfFolios;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author juan
 */
public class Cron implements Serializable {

    private static int contador = 0;
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private FlujosGralesViewService flujosgralesViewService;

    public Cron() {
    }

    public void getUsuariosInfo() {

        List<KffoliosNotificacion> kn = this.flujosgralesViewService.selectANotificarByCodInteresado(Integer.parseInt("23016"));
        if (kn != null) {
            for (KffoliosNotificacion kfNot : kn) {

//                    verificar que no exista notificacion
//                    Notificacion notifi = null;
//                    notifi = flujosgralesViewService.getNotificacionesByFolio(not.getFolio());  
                try {
                    Notificacion notificacion = new Notificacion();
                    notificacion.setFolio(kfNot.getCodbarras().toString());

                    DocFile docFile = null;

                    try { // Call Web Service Operation

                        com.mx.impi.vidoc.contenedor.webservice.WsImpiDocContainer_Service service
                                = new com.mx.impi.vidoc.contenedor.webservice.WsImpiDocContainer_Service();
                        com.mx.impi.vidoc.contenedor.webservice.WsImpiDocContainer port
                                = service.getWsImpiDocContainerPort();

                        InputDocumentDocInfo inputDocument = new InputDocumentDocInfo();
                        inputDocument.setUser("webmaster_vidoc@impi.gob.mx");
                        inputDocument.setPassword("portal123");
                        inputDocument.setCodbarras(kfNot.getCodbarras());
                        inputDocument.setIdArea(7);
                        docFile = port.getDocumentFile(inputDocument);
                        System.out.println("Result = " + docFile.getMensaje());
                    } catch (Exception ex) {
                        System.out.println("El Acuerdo " + kfNot.getCodbarras() + " no se puede recuperar de UCM (ERROR)....|");
                        System.out.println("UCM (ERROR):" + ex.getMessage());
                    }

                    if (docFile != null && docFile.getFile() != null) {
                        notificacion.setArchivoNombre(kfNot.getCodbarras().replace("/", "_") + ".pdf");
                        notificacion.setArchivo(docFile.getFile().getFileContent());

                        KfAlmacenar kfAlmacenar = !flujosgralesViewService.selectKfAlmacenarByCodbarras(kfNot.getCodbarras()).isEmpty() ? flujosgralesViewService.selectKfAlmacenarByCodbarras(kfNot.getCodbarras()).get(0) : null;

                        if (kfAlmacenar != null) {
                            KfContenedores kfContenedores = !flujosgralesViewService.selectKfContenedoresByTitle(kfAlmacenar.getTitle()).isEmpty() ? flujosgralesViewService.selectKfContenedoresByTitle(kfAlmacenar.getTitle()).get(0) : null;
                            if (kfAlmacenar != null) {
                                notificacion.setDenominacion(kfContenedores.getPc());
                            }

                            notificacion.setExpediente(kfAlmacenar.getTitle());
                        }

                        notificacion.setIdUsuarioCarga(kfNot.getCodInteresado());
                        //buscar al coordinador modificar usuarios
                        notificacion.setIdUsuarioFirma(kfNot.getCodInteresado());
                        notificacion.setTitular(kfNot.getCveUsuario());
                        notificacion.setIdArea(Integer.parseInt("40"));
                        int num=flujosgralesViewService.insertarNotificacion(notificacion);
                        //if(num==1) flujosgralesViewService.updateKffoliosNotificacion();
                    } else {
                        System.out.println("El Acuerdo " + kfNot.getCodbarras() + " no se puede recuperar de UCM o no esta migrado....|");

                    }

                } catch (Exception ex) {
                    System.out.println("Error:" + ex.getMessage());
                }

            }

        } else {
            System.out.println("No se ejecuto el Cron");
        }

    }
}

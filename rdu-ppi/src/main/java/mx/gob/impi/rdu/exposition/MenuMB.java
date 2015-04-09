/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition;

import java.io.IOException;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.JndiPropertiesUtils;
import mx.gob.impi.rdu.util.Translate;
import org.apache.log4j.Logger;

/**
 *
 * @author usradmin
 */
@ManagedBean(name = "menuMB")
@ViewScoped
@SuppressWarnings("serial")
public class MenuMB implements Serializable {

    private Logger logger = Logger.getLogger(this.getClass());
    private int idMenu = 0;
    private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.parametros";
    final ResourceBundle bundleParametros = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
    
    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;       
    }   

    public void actualizar() throws IOException {
        //logger.info("entra");
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        if (null != obtSession) {
            if (this.idMenu > 0) {
                obtSession.setIdMenu(this.idMenu);
            }
            ContextUtils.crearSesionRdu(obtSession);

        }
        if (this.idMenu == 5 || this.idMenu == 58) {
//            FacesContext.getCurrentInstance().getExternalContext().redirect(bundleParametros.getString("action.salirPase.url"));            
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(
                        (String) JndiPropertiesUtils.getProperty("action.salirPase.url"));
            } catch (NamingException ne) {
                ne.printStackTrace();
            }
        }
        
         httpSession.setAttribute("ocultarPanel", Boolean.FALSE);
        
        
    }
    
    public void actualizar2() throws IOException{
        //logger.info("entra");
        
        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        if (null != obtSession) {
            if (this.idMenu>0)
                obtSession.setIdMenu(this.idMenu);
                ContextUtils.crearSesionRdu(obtSession);

        }
        if (this.idMenu==5){                    
            FacesContext.getCurrentInstance().getExternalContext().redirect(bundleParametros.getString("action.salirPase.url"));            
        }
        
        httpSession.setAttribute("ocultarPanel", Boolean.TRUE);
    }

}

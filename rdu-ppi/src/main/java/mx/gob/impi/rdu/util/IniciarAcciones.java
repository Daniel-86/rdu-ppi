/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.util;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import mx.gob.impi.rdu.persistence.model.Usuario;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;


/**
 *
 * @author usradmin
 */
public class IniciarAcciones extends HttpServlet{
    private Logger log = Logger.getLogger(this.getClass());

    @Override
    public void init(){
        Usuario usr = new Usuario();
        try
        {

            ContextUtils.leerParametro();
            /*
            String encXmlPromovente="";
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest rqs=(HttpServletRequest) context.getExternalContext().getRequest();  
            if (rqs==null){
                encXmlPromovente="HttpServletRequest vacio";
            }
            else{
                encXmlPromovente = rqs.getParameter("PORTAL_XMLPromovente");
                if (encXmlPromovente==null)
                    encXmlPromovente="PORTAL_XMLPromovente vacio";
            }
            */
            /*
            usr.setNombre("USUARIO-INI");
            ContextUtils.crearSesionUsuario(usr);
            */
             log.info("(***) 'IniciarAcciones' successfully completed (***): ");
        }catch (Exception e)
        {
            log.error("Ocurrio un error en el metodo IniciarAcciones.init", e);
        }
    }



    /*
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String encXmlPromovente="";
            if (request==null){
                encXmlPromovente="HttpServletRequest vacio";
            }
            else{
                encXmlPromovente = request.getParameter("PORTAL_XMLPromovente");
                if (encXmlPromovente==null)
                    encXmlPromovente="PORTAL_XMLPromovente vacio";

            }
            log.info("(***) 'IniciarAcciones.doPost' successfully completed (***): "+ encXmlPromovente);
    }
    */
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.indice;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.util.BundleUtils;
import mx.gob.impi.rdu.util.ContextUtils;
import org.apache.log4j.Logger;

@ManagedBean(name = "indiceMB")
@ViewScoped
@SuppressWarnings("serial")
public class IndiceMB implements Serializable{
    private Logger log = Logger.getLogger(this.getClass());
    List <AreaVO> areas;
    List <GestionVO> tableros;
    AreaVO areaSelected;
   
    public IndiceMB() {
        areaSelected=new AreaVO();
    }

    @PostConstruct
    public void init() throws Exception {
        
              
        /*
        try {
            this.areas=this.ObtenerAreas();
            this.tableros=this.ObtenerTablerosMarca();
        } catch (Exception e) {
            log.info(e);
        }

         */
    }
    
    public void actualizarValores() throws IOException{
        //logger.info("entra");
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        
        if (null != obtSession) {
            obtSession.setIdMenu(0);
            obtSession.setIdTramite(0);
            ContextUtils.crearSesionRdu(obtSession);
        }
    }


    public AreaVO getAreaSelected() {
        return areaSelected;
    }

    public void setAreaSelected(AreaVO areaSelected) {
        this.areaSelected = areaSelected;
    }

    private List<AreaVO> ObtenerAreas(){
        List<AreaVO> retAreas=new ArrayList();
        
        //marcas
        AreaVO areaMarca=new AreaVO();
        areaMarca.setIdArea(1);
        areaMarca.setNombreArea("Marcas");
        areaMarca.setSolicitudes(this.ObtenerSolicitudesMarca());
        areaMarca.setTableros(this.ObtenerTablerosMarca());

        retAreas.add(areaMarca);
        
        //patentes
        AreaVO areaPatente=new AreaVO();
        areaPatente.setIdArea(2);
        areaPatente.setNombreArea("Patentes");
        areaPatente.setSolicitudes(this.ObtenerSolicitudesPatente());
        areaPatente.setTableros(this.ObtenerTablerosMarca());
        retAreas.add(areaPatente);
        
        return retAreas;
    }

    private List <SolicitudRduVO> ObtenerSolicitudesMarca(){
        List <SolicitudRduVO> retSolicitudesMarca= new ArrayList();
        retSolicitudesMarca.add(new SolicitudRduVO("Registro de signos distintivos","content/common/authentication/pagina-test.faces"));
        retSolicitudesMarca.add(new SolicitudRduVO("Licencia de uso","#"));
        retSolicitudesMarca.add(new SolicitudRduVO("Renovación","#"));
        retSolicitudesMarca.add(new SolicitudRduVO("Licencia de uso","#"));
        retSolicitudesMarca.add(new SolicitudRduVO("Cambio de nombre","#"));
        retSolicitudesMarca.add(new SolicitudRduVO("Cambio de domicilio","#"));
        retSolicitudesMarca.add(new SolicitudRduVO("Autorización de uso","#"));
        return retSolicitudesMarca;
    }
    private List <GestionVO> ObtenerTablerosMarca(){
        List <GestionVO> retTableros= new ArrayList();
        retTableros.add(new GestionVO("Documentos en Preparación","#"));
        retTableros.add(new GestionVO("Tablero de Control","#"));
        return retTableros;

    }

    private List <SolicitudRduVO> ObtenerSolicitudesPatente(){
        List <SolicitudRduVO> retSolicitudesPatente= new ArrayList();
        retSolicitudesPatente.add(new SolicitudRduVO("Patentes 1","#"));
        retSolicitudesPatente.add(new SolicitudRduVO("Patentes 2","#"));
        retSolicitudesPatente.add(new SolicitudRduVO("Patentes 3","#"));
        retSolicitudesPatente.add(new SolicitudRduVO("Patentes 4","#"));
        return retSolicitudesPatente;
    }


    public List<AreaVO> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaVO> areas) {
        this.areas = areas;
    }

    public List<GestionVO> getTableros() {
        return tableros;
    }

    public void setTableros(List<GestionVO> tableros) {
        this.tableros = tableros;
    }


}

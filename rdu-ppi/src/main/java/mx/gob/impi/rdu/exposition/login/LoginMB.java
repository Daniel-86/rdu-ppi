package mx.gob.impi.rdu.exposition.login;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.persistence.model.Usuario;
import mx.gob.impi.rdu.persistence.model.UsuarioSeguridad;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.JndiPropertiesUtils;
import mx.gob.impi.rdu.util.Util;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

@ManagedBean(name = "loginMB")
@RequestScoped
public class LoginMB implements Serializable {

    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    private Logger log = Logger.getLogger(this.getClass());
    private Usuario usuario = new Usuario();
    private Boolean editMod;
    private Date Fecha;
    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    /**
     * Direccionamiento hacia el contexto de spring
     * @return 
     */
    public String login() throws IOException, ServletException {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            ServletRequest servletRequest = (ServletRequest) context.getRequest();
            ServletResponse servletResponse = (ServletResponse) context.getResponse();

            RequestDispatcher dispatcher = servletRequest.getRequestDispatcher("/j_spring_security_check");
            dispatcher.forward(servletRequest, servletResponse);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            log.error("Ocurrio un error en el metodo LoginMB.login", e);
        }
        return null;
    }

    public Boolean getEditMod() {
        return editMod;
    }

    public void setEditMod(Boolean editMod) {
        this.editMod = editMod;
    }

    public Usuario getUsuario() {
        SesionRDU session = ContextUtils.obtenerSesionUsuario();
        if (null != session) {



            Promovente obtApoderado2 = this.flujosgralesViewService.buscaPromovente(new Long(session.getPromovente().getId_promovente()));

            if (obtApoderado2 != null) {
                crearUsuarioDesarrollo(obtApoderado2);
            }

            PromoventeDto promo = session.getPromovente();
            Usuario user = session.getUsuario();
            user.setNombre(obtApoderado2.getNombre());
            user.setApellidoMaterno(obtApoderado2.getAmaterno());
            user.setApellidoPaterno(obtApoderado2.getApaterno());
            promo.setNombre(obtApoderado2.getNombre());
            promo.setApaterno(obtApoderado2.getApaterno());
            promo.setAmaterno(obtApoderado2.getAmaterno());
            promo.setCalle_numero(obtApoderado2.getCalleNumero());
            promo.setColonia(obtApoderado2.getColonia());
            session.setUsuario(user);
            session.setPromovente(promo);
            HttpSession ss = ContextUtils.getSession();
            ss.setAttribute(SesionRDU.class.getName(), session);

            if (session.getUsuario() != null) {
                usuario = session.getUsuario();
                usuario.setNombre(usuario.getNombre().toUpperCase());
                usuario.setApellidoPaterno(usuario.getApellidoPaterno().toUpperCase());
                usuario.setApellidoMaterno(usuario.getApellidoMaterno().toUpperCase());
            }
        }

        return usuario;
    }

    public void crearUsuarioDesarrollo(Promovente promovente) {
        String ambienteProduccion;
        try {
            ambienteProduccion = (String) JndiPropertiesUtils.getProperty("action.ambienteProduccion");
            if (ambienteProduccion.equals("0")) {
                promovente.setLogin((String) JndiPropertiesUtils.getProperty("nombrePromovente"));
                promovente.setNombre((String) JndiPropertiesUtils.getProperty("nombrePromovente"));
                promovente.setApaterno((String) JndiPropertiesUtils.getProperty("aPaternoPromovente"));
                promovente.setAmaterno((String) JndiPropertiesUtils.getProperty("aMaternoPromovente"));
                promovente.setIdPerfil(new BigDecimal((String) JndiPropertiesUtils.getProperty("idPerfilPromovente")));
                promovente.setIdPromovente(new BigDecimal((String) JndiPropertiesUtils.getProperty("idPromovente")));
                promovente.setTipoPersona(Constantes.INIT_SHORT);
            }
        } catch (NamingException ex) {
            log.info("Propiedad action.ambienteProduccion no declarada en el glassfish");
        }
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void guardar() {
        addOkToCallback();

    }

    private void addOkToCallback() {
        RequestContext.getCurrentInstance().addCallbackParam("ok", true);
    }
    
    public String getSysDate() {
        return Util.formatearFecha(flujosgralesViewService.getSysDate(), "MM/dd/yyyy HH:mm:ss");
    }
    public String getDateSistema() {
//        Date Fecha= flujosgralesViewService.getSysDate();
//        Integer dia= Fecha.getDay();
//        Integer mes= Fecha.getMonth();
//        Integer año= Fecha.getYear();
//        String hora= String.format("%02d", Fecha.getHours());
//        String min=String.format("%02d", Fecha.getMinutes());
//        String seg=String.format("%02d", Fecha.getSeconds());
//        String[] MesLetra={"Enero","Febrero","Marzo","Abril","Mayo","Junio", "Julio","Agosto","Septiembre","Octobre","Noviembre","Diciembre"};
//        String FechaSistema= dia.toString()+ "de" + MesLetra[mes]+"," + año.toString() + " " + hora+":"+min+":"+seg;
        return  Util.formatearFecha(flujosgralesViewService.getSysDate(), "MM/dd/yyyy HH:mm:ss");
    }
}

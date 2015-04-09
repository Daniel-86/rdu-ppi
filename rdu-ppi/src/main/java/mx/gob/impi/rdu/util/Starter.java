/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;

/**
 *
 * @author miguel.meza
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import org.apache.log4j.Logger;
import org.quartz.impl.StdScheduler;

/**
 * <p>Descripci�n:</p>
 * Clase que se emplea para iniciar variables y servicios antes que se
 * cargue cualquier otra cosa y de que cualquier usuario entre al sistema.
 * Tambi�n es encargada (en su m�todo 'destoy') de terminar con aquellos 
 * procesos que requieran de una terminaci�n expl�cita.
 *
 * @author GAA
 * @version 1.1.56
 */
public class Starter extends HttpServlet {
    // CONSTANTES: PRIVADAS Y P�BLICAS

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(Starter.class);
    private static final String MAIL_RESOURCES_PATH_PROPERTY_NAME = "mail.resources";
    private static final String MAIL_TEMPLATE_PROPERTY_NAME = "mail.template";
    private static final String LOGIN_BLOKED_WINDOW_TIME = "login.blokedWindowTime";
    private static final String LOGIN_MAX_INVALID_TRIES = "login.maxInvalidTries";
    private static final String RESTORE_PASSWORD_TICKET_LIVE_TIME = "restore.password.ticketLiveTime";
    private static final String PASSWORD_MIN_LENGTH = "password.minLength";
    /**
     * Archivo principal de propiedades
     */
    public static final String APPLICATION_PROPERTIES_FILE = "application.properties";
    /**
     * Ruta en la que se ubicar� el directorio de recursos
     */
    public static final String RESOURCES_PATH = "WEB-INF/classes/";

    // VARIABLES: PRUVADAS (Y LUEGO P�BLICAS, PERO NO HAY)
    private static String realPath = "";
    private static String fullContext = "";
    private static String contextName = "";
    //private static PropertiesAgent pa = null;

    /**
     * Inicia servicios y establece las rutas b�sicas para el aplicativo.
     */
    @Override
    public void init() {
        ServletContext sistema = getServletContext();
        //String s = sistema.getServletContextName(); //retorna s=null

        // realPath Regresa: \home\Users\Administrador\Workspaces\MyEclipse-8.6\.metadata\.me_tcat\webapps\buscador\
        realPath = normalizePath(sistema.getRealPath(""));
        logger.info("Init in progress. Real path:  (" + realPath + ")");

        // fullContext Regresa: /localhost/TestIbatis2/   (con / al final)
        fullContext = getNombreCtx(sistema);

        String[] caja = fullContext.split("/");
        // contextName Regresa: /TestiBatis2   (sin / al final)
        contextName = "/" + caja[caja.length - 1];
        logger.info("Single Context Name:      --->" + contextName + "<---");

        fullContext = "/www.se.sniim.gob.mx:8080" + contextName + "/";
        logger.info("Full Context:      --->" + fullContext + "<---");

        // *** OJO *** Cargar S�LO hasta que las variables anteriores ya fueron seteadas !!!!!
        //logger.info("Cargando archivo de propiedades...");
       // pa = new PropertiesAgent(APPLICATION_PROPERTIES_FILE);

        // Avisa que ya concluy� el init de Starter.
        logger.info("(***) 'Starter' successfully completed (***)");
    }

    /**
     * Regresa la trayectoria absoluta (acorde al sistema de archivos usado) en donde 
     * est� montado el aplicativo. Por ejemplo:<br/><br/>
     * '\home\Users\Administrador\Workspaces\MyEclipse-8.6\.metadata\.me_tcat\webapps\skeleton\'<br/><br/>
     * En todo caso la trayactoria termina con un separador compatible con el sistema 
     * opertaivo en uso.
     * 
     * @return String : Trayectoria absoluta del aplicativo
     */
    public static String getRealPath() {
        return realPath;
    }

    /**
     * Regresa el nombre del contexto del aplicativo con un separador al inicio y sin 
     * separador al final. Por ejemplo, '/skeleton'
     * 
     * @return String : Contexto del aplicativo
     */
    public static String getContextName() {
        return contextName;
    }

    /**
     * Regresa el contexto completo del aplicativo.
     * Por ejemplo, /localhost/skeleton/ incluyendo el
     * primer y �ltimo seperador.
     * 
     * @return String : Contexto completo del aplicativo
     */
    public static String getFullContextName() {
        return fullContext;
    }

    /**
     * Regresa la trayectoria en donde se ubican los recursos asociados al servicio de correo. 
     * La trayectoria es relativa a WEB_INF/clases/ y tiene un patr�n del tipo: 'foo/', por 
     * ejemplo: 'mail/', que deber� existir en 'WEB_INF/clases/'. Los recursos de soporte al
     * servicio de correo incluyen, mas no se limitan a, plantillas para env�o de correo con
     * formato espec�fico (que puede ser HTML), im�genes y documentos a anexar en un correo.
     * 
     * @return String : Ruta del archivo de recursos de correo
     */
//    public static String getMailResourcesPath() {
//        return pa.getString(MAIL_RESOURCES_PATH_PROPERTY_NAME);
//    }

    /**
     * Regresa el nombre de un archivo que contiene una plantilla para env�o
     * de correos con cierto formato. El nombre de tal archivo (definido por 
     * una propiedad de alg�n archivo de recursos) deber� estar en formato relativo
     * al directorio de recursos asociados al servicio de correo.
     * 
     * @see mx.gob.se.upci.expedientes.support.Starter getMailResourcesPath
     * @return String : Nombre del archivo-plantilla para env�o de correos
     */
//    public static String getMailTemplateRelativeFileName() {
//        return pa.getString(MAIL_TEMPLATE_PROPERTY_NAME);
//    }
        
    /**
     * Regresa el m�ximo n�mero de intentos fallidos al momento de login
     * @return m�ximo n�mero de intentos antes de ser bloqueado
     */
//    public static int getIntentosPermitidos() {
//        return pa.getInt(LOGIN_MAX_INVALID_TRIES);
//    }

    /**
     * Regresa la cantidad (en milisegundos) que el usuario permanecer� bloquedao
     * @return milisegundos que el usuario estar� bloqueado
     */
//    public static int getTiempoBloqueado() {
//        return pa.getInt(LOGIN_BLOKED_WINDOW_TIME);
//    }

    /**
     * Regresa el tiempo de vida del ticket para recuperar el password
     * @return Tiempo, en horas, que el ticket ser� v�lido
     */
//    public static int getTiempoVidaTicket() {
//        return pa.getInt(RESTORE_PASSWORD_TICKET_LIVE_TIME);
//    }
    
    /**
     * Regresa la longitud m�nima que debe tener un password
     * @return La longitud m�nima que debe tener un password
     */
//    public static int getLongitudMinimaPassword() {
//        return pa.getInt(PASSWORD_MIN_LENGTH);
//    }
    
    /**
     * Obtiene el nombre del contexto del aplicativo.
     * Por ejemplo, /localhost/skeleton/ incluyendo el
     * primer y �ltimo seperador.
     *
     * @param app ServletContext
     * @return String : Contexto completo del aplicativo
     */
    private String getNombreCtx(ServletContext app) {
        String nombreCtx = "";
        try {
            nombreCtx = app.getResource("/").getPath();
        } catch (Exception e) {
            logger.error("-->" + e.toString() + "<--");
        }
        return nombreCtx;
    }

    /**
     * Manda un mensaje de error en caso de que Starter sea
     * invocado accidentalmente via el m�todo 'doGet'.
     *
     * @param request HttpServletRequest Atributo por default de doGet
     * @param response HttpServletResponse Atributo por default de doGet
     * @throws IOException No ser� usada
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(msg());
    }

    /**
     * Manda un mensaje de error en caso de que Starter sea invocado
     * accidentalmente via el m�todo 'doPost'.
     *
     * @param request HttpServletRequest Atributo por default de doPost
     * @param response HttpServletResponse Atributo por default de doPost
     * @throws IOException No ser� usada
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(msg());
    }

    /**
     * Notifica del proceso de 'shutdown' del aplicativo y termina los
     * procesos que deben de ser concluidos para que el 'shutdown' sea efectivo.
     */
    @Override
    public void destroy() {
        logger.info("Destructor in progress....");
        logger.info("Stopping mail engine....");

//        StdScheduler x = (StdScheduler) BeanLocator.getBean("schedulerFactoryBean");
//        x.shutdown();

        logger.info("Mail engine stopped");
        logger.info("(***) Destructor concluded successfully (***)");
    }

    /**
     * Regresa el mensaje que env�a el servlet en caso de que �ste sea
     * invocado v�a el m�todo 'get' o por el m�todo 'post'.
     *
     * @return String : Mensaje en formato HTML
     */
    private String msg() {
        StringBuffer str = new StringBuffer();
        str.append("<html>\n<body>\n<form>\n");
        str.append("<H1>Este servlet no debe ser invocado mas que por el container!</H1>\n");
        str.append("</form>\n</body>\n</html>\n");
        return str.toString();
    }

    /**
     * Regresa una cadena que contiene la trayectoria de un directorio dado
     * garantizando que incluir� el separador de archivo adecuado al final de este.
     *
     * @param dirName : Ruta a normailzar
     * @return String : Cadena original terminada con el separador adecuado
     */
    public String normalizePath(String dirName) {
        String fs = getFileSeparator();
        int tam = dirName.length();
        if (dirName.substring(tam - 1, tam).equals(fs)) {
            return dirName;
        }
        return dirName + fs;
    }

    /**
     * Regresa el s�mbolo '/' para linux y '\' para windows
     * 
     * @return String : Separador de directorios con base en la plataforma usada
     */
    public static String getFileSeparator() {
        return java.io.File.separator;
    }
}// Fin de clase Starter ***


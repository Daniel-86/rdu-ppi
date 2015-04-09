/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;

import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ccastaneda
 */
public class JndiPropertiesUtils {
    
     /*
     * Obtiene el contexto de jndi de un servidor de aplicaciones local.
     */
    public static InitialContext getInitialContext() throws NamingException {
        InitialContext ctx;
        Properties props;
        props = new Properties();
        props.setProperty("java.naming.factory.initial",
                "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs",
                "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state",
                "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        /*glassfish - Cuando el EJB se encuentra en otro servidor
         No es necesario si está en el mismo servidor (war y ear)
         <prop key="org.omg.CORBA.ORBInitialHost">192.168.0.92</prop>
         <prop key="org.omg.CORBA.ORBInitialPort">3718</prop>*/

        ctx = new InitialContext(props);
        return ctx;
    }

    /*
     * Buscar la propiedad en jndi
     */
    public static Object getProperty(String name) throws NamingException {
        InitialContext ctx = getInitialContext();
        return ctx.lookup(name);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
/**
 *
 * @author miguel.meza
 */


//import mx.gob.se.upci.expedientes.support.Starter;

@ManagedBean
@ApplicationScoped
/**
 * <p>Descripci�n:</p>
 * Clase auxiliar que contiene m�todos utilitarios para su uso desde el frontend.
 *
 * @author GAA
 * @version 1.1.56
 */
public class Translate {
	/**
	 * Regresa la ruta del contexto del aplicativo
	 * 
	 * @return String : ruta del contexto del aplicativo
	 */
	public String getContextPath() {
		return Starter.getContextName();
	}
}

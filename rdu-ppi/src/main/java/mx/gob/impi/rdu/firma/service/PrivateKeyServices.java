package mx.gob.impi.rdu.firma.service;

import java.security.PrivateKey;
import mx.gob.impi.rdu.firma.exception.PrivateKeyException;





/**
 * Interfaz con servicios para lectura de llave privada.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
public interface PrivateKeyServices {

	/**
	 * Obtiene el objeto PrivateKey del arreglo de bytes y contrasena para
	 * abrirlo, lanza una excepci�n PrivateKeyException si no pudo obtener el
	 * objeto.
	 * 
	 * @param privateKeyBytes
	 *            Arreglo de bytes que representa el archivo de la llave
	 *            privada.
	 * @param password
	 *            Contrasena para poder abrir la llave privada.
	 * @return Objeto PrivateKey.
	 * @throws PrivateKeyException
	 *             Si no ha sido posible obtener la llave privada.
	 */
	PrivateKey createPrivateKey(byte[] privateKeyBytes, char[] password)
			throws PrivateKeyException;

}

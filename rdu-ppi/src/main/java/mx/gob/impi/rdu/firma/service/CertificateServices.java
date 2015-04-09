package mx.gob.impi.rdu.firma.service;


import java.security.cert.X509Certificate;
import java.util.Date;
import mx.gob.impi.rdu.firma.exception.CertificateException;




/**
 * Interfaz con servicios para lectura, validaci�n y manipulaci�n de
 * certificados.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
/**
 * @author topiltzin
 * 
 */
public interface CertificateServices {

	/**
	 * Obtiene el objeto X509Certificate del arreglo de bytes, lanza una
	 * excepci�n CertificateException si no pudo obtener el objeto.
	 * 
	 * @param certBytes
	 *            Arreglo de bytes que representa el archivo del certificado.
	 * @return Objeto X509Certificate.
	 * @throws CertificateException
	 *             Si no ha sido posible obtener el certificado.
	 */
	X509Certificate getX509Certificate(byte[] certBytes)
			throws CertificateException;

	/**
	 * Obtiene la fecha de expiraci�n del certificado.
	 * 
	 * @param certBytes
	 *            Arreglo de bytes que representa el archivo del certificado.
	 * @return Fecha de expiraci�n del certificado.
	 * @throws CertificateException
	 *             Si no ha sido posible obtener la fecha de expiraci�n.
	 */
	Date getExpirationDate(byte[] certBytes) throws CertificateException;

	/**
	 * Verifica si la fecha de expiraci�n es v�lida comparandola contra la fecha
	 * del servidor.
	 * 
	 * @param serverDate
	 *            Fecha del servidor.
	 * @param expirationDate
	 *            Fecha de expiraci�n del certificado.
	 * @return Regresa true si el certificado ha expirado, false en otro caso.
	 * @throws CertificateException
	 *             Si no ha podido verificar la fecha de expiraci�n.
	 */
	boolean checkExpirationDate(Date serverDate, Date expirationDate)
			throws CertificateException;

	/**
	 * Verifica si el certificado y llave privada son pares uno del otro, es
	 * decir, hayan sido generados a la par.
	 * 
	 * @param certBytes
	 *            Arreglo de bytes que representa el archivo del certificado.
	 * @param privateKeyBytes
	 *            Arreglo de bytes que representa el archivo de la llave
	 *            privada.
	 * @param password
	 *            Contrase�a de la llave privada.
	 * @return Regresa true si son pares, false en otro caso.
	 * @throws CertificateException
	 *             Si no ha podido verificar la paridad del certificado y llave
	 *             privada.
	 */
	boolean checkParity(byte[] certBytes, byte[] privateKeyBytes,
			char[] password) throws CertificateException;

	/**
	 * Verifica si el certificado es una FIEL.
	 * 
	 * @param certBytes
	 *            Arreglo de bytes que representa el archivo del certificado.
	 * @return Regresa true si el certificado es una FIEL, false en otro caso.
	 * @throws CertificateException
	 *             Si no ha podido verificar que el certificado es una FIEL.
	 */
	boolean isFiel(byte[] certBytes) throws CertificateException;

	/**
	 * Obtiene el objeto X509Certificate de la cadena pasada como atributo,
	 * lanza una excepci�n CertificateException si no pudo obtener el objeto.
	 * 
	 * @param pem
	 *            Cadena que representa el archivo del certificado.
	 * @return Objeto X509Certificate.
	 * @throws CertificateException
	 *             Si no ha sido posible obtener el certificado.
	 */
	X509Certificate getX509Certificate(String pem) throws CertificateException;
        
                String getFirmaDigital(byte[] certBytes, byte[] privateKeyBytes,
			char[] password) throws CertificateException;

}

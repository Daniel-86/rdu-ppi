package mx.gob.impi.rdu.firma.service;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import mx.gob.impi.rdu.firma.exception.OcspException;
import org.bouncycastle.ocsp.OCSPReq;
import org.bouncycastle.ocsp.OCSPResp;

/**
 * Interfaz con servicios para validacion contra el OCSP indicado.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
public interface OcspServices {

	/**
	 * Genera una solicitud OCSP, indicando el certificado y el numero de serie
	 * para su posterior validacion.
	 * 
	 * @param issuerCert
	 *            Certificado a validar.
	 * @param serialNumber
	 *            Numero de serie del certificado.
	 * @return Objeto OCSPReq listo para validar contra un OCSP.
	 * @throws OcspException
	 *             Si no pudo generar la peticion al OCSP.
	 */
	OCSPReq generateOCSPRequest(X509Certificate issuerCert,
			BigInteger serialNumber) throws OcspException;

	/**
	 * Obtiene el mensaje de la respuesta del OCSP, es decir, el estado de
	 * revocaci�n.
	 * 
	 * Posibles valores: unspecified (0): "Certificado Activo." keyCompromise
	 * (1): "Estado desconocido." cACompromise (2): "Certificado Revocado."
	 * affiliationChanged (3):
	 * "El Certificado solicitado no es el mismo que el verificado" superseded
	 * (4): "La respuesta no est� firmada por la CA solicitada."
	 * cessationOfOperation (5): "El Request OCSP est� mal generado."
	 * certificateHold (6): "Error interno del servidor." (value 7 is not used):
	 * "Servidor ocupado, intentar m�s tarde." removeFromCRL (8):
	 * "El Request OCSP debe estar firmado." privilegeWithdrawn (9):
	 * "No est� autorizado para utilizar este servicio." aACompromise (10):
	 * "Error de solicitud. (desconocido)" invalid (11):
	 * "El Certificado no est� firmado por el SAT."
	 * 
	 * 
	 * @param ocspResp
	 *            Respuesta del OCSP.
	 * @return Cadena con el mensaje representando el estado de revocaci�n del
	 *         certificado.
	 * @throws OcspException
	 *             Si no pudo obtener la respuesta.
	 */
	String getStatusString(int status) throws OcspException;

	/**
	 * Verifica si el certificado es valido mediante una petici�n al servidor de
	 * OCSP en la direcci�n indicada.
	 * 
	 * @param ocspReq
	 *            Peticion OCSP.
	 * @param ocspAddress
	 *            Ubicacion del servidor de OCSP.
	 * @param serialNumber
	 *            Numero de serie del certificado que deseamos validar.
	 * @return Regresa true si la peticion OCSP contesto exitosamente y si el
	 *         estado de revocacion es cero, es decir, si el certificado es
	 *         valido y esta activo.
	 * @throws OcspException
	 *             Si no pudo verificar el estatus del certificado.
	 */
	boolean isValid(OCSPReq ocspReq, String ocspAddress, BigInteger serialNumber)
			throws OcspException;

                 OCSPResp getOcspResponse()throws OcspException;
}

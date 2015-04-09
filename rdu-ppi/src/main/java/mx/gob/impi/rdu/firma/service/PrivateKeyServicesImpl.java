package mx.gob.impi.rdu.firma.service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import mx.gob.impi.rdu.firma.exception.PrivateKeyException;

import org.apache.commons.ssl.PKCS8Key;



/**
 * Clase que implementa los servicios para lectura de llave privada.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
public class PrivateKeyServicesImpl extends AbstractBaseService implements
		PrivateKeyServices {

	private final static String ERROR_PASSWORD = "Given final block not properly padded";

	public PrivateKey createPrivateKey(byte[] privateKeyBytes, char[] password)
			throws PrivateKeyException {

		try {
			PKCS8Key pk8 = new PKCS8Key(privateKeyBytes, password);
			return KeyFactory.getInstance("RSA").generatePrivate(
					new PKCS8EncodedKeySpec(pk8.getDecryptedBytes()));
		} catch (Exception e) {

			if (ERROR_PASSWORD.equals(e.getMessage())) {
				throw new PrivateKeyException("La contraseña es incorrecta.", e);
			}

			throw new PrivateKeyException(
					"No ha sido posible obtener la llave privada.", e);
		}
	}

}

package mx.gob.impi.rdu.firma.service;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import mx.gob.impi.rdu.firma.exception.CertificateException;
import org.apache.commons.ssl.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

/**
 * Clase que implementa los servicios para lectura, validacion y manipulacion de
 * certificados.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
@Service("privateKeyServices")
public class CertificateServicesImpl extends AbstractBaseService implements
		CertificateServices {

	@Autowired
	private PrivateKeyServices privateKeyServices;

	public void setPrivateKeyServices(PrivateKeyServices privateKeyServices) {
		this.privateKeyServices = privateKeyServices;
	}

	public X509Certificate getX509Certificate(byte[] certBytes)
			throws CertificateException {
		try {
                            CertificateFactory cf;
			cf = CertificateFactory.getInstance("X.509");
			return (X509Certificate) cf
					.generateCertificate(new ByteArrayInputStream(certBytes));
		} catch (java.security.cert.CertificateException e) {
			throw new CertificateException(
					"No ha sido posible obtener el certificado.", e);
		}
	}

	public Date getExpirationDate(byte[] certBytes) throws CertificateException {
		X509Certificate x509Certificate = getX509Certificate(certBytes);
		System.out.println("expirationDate (NotAfter): "
				+ x509Certificate.getNotAfter());
		System.out.println("expirationCreacion (NotBefore): "
				+ x509Certificate.getNotBefore());
		return x509Certificate.getNotAfter();
	}

	public boolean checkParity(byte[] certBytes, byte[] privateKeyBytes,
			char[] password) throws CertificateException {

		try {
			X509Certificate x509 = getX509Certificate(certBytes);
			PrivateKey pk = privateKeyServices.createPrivateKey(
					privateKeyBytes, password);
			RSAPublicKey publicKey = (RSAPublicKey) x509.getPublicKey();
			byte[] proof = { 21, 21, 22, 87 };
			Signature signature = Signature.getInstance("MD5WithRSA", "SunRsaSign");			
                                                signature.initSign(pk);                                               
			signature.update(proof);
			byte[] sign = signature.sign();                                                                                         
			signature.initVerify(publicKey);
			signature.update(proof);
			return signature.verify(sign);
		} catch (Exception e) {
			throw new CertificateException(
					"No ha sido posible verificar la paridad del certificado y llave privada.",
					e);
		}

	}
                
        public String getFirmaDigital(byte[] certBytes, byte[] privateKeyBytes, char[] password) throws CertificateException
        {
                		try {
			X509Certificate x509 = getX509Certificate(certBytes);
			PrivateKey pk = privateKeyServices.createPrivateKey(
					privateKeyBytes, password);
			RSAPublicKey publicKey = (RSAPublicKey) x509.getPublicKey();
			byte[] proof = { 21, 21, 22, 87 };
			Signature signature = Signature.getInstance("MD5WithRSA", "SunRsaSign");
			signature.initSign(pk);
			signature.update(proof);
			byte[] sign = signature.sign();
                                                String resultado = new BASE64Encoder().encode(sign);
                                                return resultado;
                                               	
                                }catch (Exception e) {
			throw new CertificateException(
					"No se pudo obtener la firma del certificado",	e);
		}

        
        }
     
	public boolean isFiel(byte[] certBytes) throws CertificateException {
		boolean isFiel = false;

		X509Certificate x509 = getX509Certificate(certBytes);

		try {
			if (x509 != null) {
				boolean[] keyUsage = x509.getKeyUsage();
				if ((keyUsage[0] != false) && (keyUsage[1] != false)) {
					int i = 2;
					for (i = 2; i < keyUsage.length; i++) {
						if (keyUsage[i] == false)
							continue;
						isFiel = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			throw new CertificateException(
					"No ha sido posible verificar si el certificado es FIEL.",
					e);
		}
		return isFiel;
	}

	public X509Certificate getX509Certificate(String pem)
			throws CertificateException {

		try {
			String PEM_HEADER = "-----BEGIN CERTIFICATE-----";
			String PEM_FOOTER = "-----END CERTIFICATE-----";
			pem = pem.substring(PEM_HEADER.length(), pem.length());
			int offset = pem.lastIndexOf(PEM_FOOTER);
			pem = pem.substring(0, offset);
			byte[] certificado = new Base64().decode(pem.getBytes());
			return (X509Certificate) CertificateFactory.getInstance("X.509")
					.generateCertificate(new ByteArrayInputStream(certificado));

		} catch (java.security.cert.CertificateException e) {
			throw new CertificateException(
					"No ha sido posible obtener el certificado del PEM.", e);
		}

	}

	public boolean checkExpirationDate(Date serverDate, Date expirationDate)
			throws CertificateException {
		
		System.out.println("Checando fecha de expiracion.");
		System.out.println("Fecha del servidor: " + serverDate);
		System.out.println("Fecha de expiracion: " + expirationDate);
		try {
			boolean expirado = serverDate.after(expirationDate);
			return expirado;
		} catch (Exception e) {
			throw new CertificateException(
					"No ha sido posible verificar la fecha de expiracion del cerificado.",
					e);
		}

	}

  
        

}

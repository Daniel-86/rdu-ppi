package mx.gob.impi.rdu.firma.service;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Vector;



import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.ocsp.OCSPObjectIdentifiers;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.ocsp.BasicOCSPResp;
import org.bouncycastle.ocsp.CertificateID;
import org.bouncycastle.ocsp.CertificateStatus;
import org.bouncycastle.ocsp.OCSPException;
import org.bouncycastle.ocsp.OCSPReq;
import org.bouncycastle.ocsp.OCSPReqGenerator;
import org.bouncycastle.ocsp.OCSPResp;
import org.bouncycastle.ocsp.RevokedStatus;
import org.bouncycastle.ocsp.SingleResp;
import org.bouncycastle.ocsp.UnknownStatus;


import java.util.Date;
import mx.gob.impi.rdu.firma.exception.OcspException;

/**
 * Clase que implementa los servicios para validacion contra el OCSP indicado.
 * 
 * @version 0.1
 * @author INFOTEC
 * 
 */
public class OcspServicesImpl extends AbstractBaseService implements
		OcspServices {

    
                    private OCSPResp response;
                    
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.sct.utic.signer.crypto.service.OcspServices#generateOCSPRequest
	 * (java.security.cert.X509Certificate, java.math.BigInteger)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OCSPReq generateOCSPRequest(X509Certificate issuerCert,
			BigInteger serialNumber) throws OcspException {

		logger.info("Generando solicitud al OCSP...");

		try {

			// Add provider BC
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			// Generate the id for the certificate we are looking for
			CertificateID id = new CertificateID(CertificateID.HASH_SHA1,
					issuerCert, serialNumber);

			// basic request generation with nonce
			OCSPReqGenerator gen = new OCSPReqGenerator();

			gen.addRequest(id);

			// create details for nonce extension
			BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
			Vector oids = new Vector();
			Vector values = new Vector();

			oids.add(OCSPObjectIdentifiers.id_pkix_ocsp_nonce);
			values.add(new X509Extension(false, new DEROctetString(nonce
					.toByteArray())));

			gen.setRequestExtensions(new X509Extensions(oids, values));
			return gen.generate();

		} catch (OCSPException e) {
			throw new OcspException(
					"No ha sido posible generar una solicitud al OCSP.", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.sct.utic.signer.crypto.service.OcspServices#getStatusString(org
	 * .bouncycastle.ocsp.OCSPResp)
	 */
	public String getStatusString(int status) throws OcspException {

		try {
			String statusString = null;
			switch (status) {
			case 0:
				statusString = "Certificado Activo.";
				break;
			case 1:
				statusString = "Estado desconocido.";
				break;
			case 2:
				statusString = "Certificado Revocado.";
				break;
			case 3:
				statusString = "El Certificado solicitado no es el mismo que el verificado";
				break;
			case 4:
				statusString = "La respuesta no esta firmada por la CA solicitada.";
				break;
			case 5:
				statusString = "El Request OCSP esta mal generado.";
				break;
			case 6:
				statusString = "Error interno del servidor.";
				break;
			case 7:
				statusString = "Servidor ocupado, intentar mas tarde.";
				break;
			case 8:
				statusString = "El Request OCSP debe estar firmado.";
				break;
			case 9:
				statusString = "No esta autorizado para utilizar este servicio.";
				break;
			case 10:
				statusString = "Error de solicitud. (desconocido)";
				break;
			case 11:
				statusString = "El Certificado no esta firmado por el SAT.";
			}
			return statusString;
		} catch (Exception e) {
			throw new OcspException(
					"No ha sido posible determinar el estatus de la solicitud al OCSP.",
					e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.gob.sct.utic.signer.crypto.service.OcspServices#isValid(org.bouncycastle
	 * .ocsp.OCSPReq, java.lang.String)
	 */
	public boolean isValid(OCSPReq ocspReq, String ocspAddress,
			BigInteger serialNumber) throws OcspException {

		boolean isValid = false;
		byte[] array = null;

		try {
			array = ocspReq.getEncoded();
		} catch (IOException e) {
			throw new OcspException(
					"No ha sido posible leer la solicitud al OCSP.", e);
		}

		// Send request:
		// serviceAddr URL OCSP service
		String serviceAddr = ocspAddress;

		if (serviceAddr != null) {

			int status = 0;

			try {
				if (serviceAddr.startsWith("http")) {
					HttpURLConnection con = null;
					URL url = new URL((String) serviceAddr);
					con = (HttpURLConnection) url.openConnection();
					con.setRequestProperty("Content-Type",
							"application/ocsp-request");
					con.setRequestProperty("Accept",
							"application/ocsp-response");
					con.setDoOutput(true);
					OutputStream out = con.getOutputStream();
					DataOutputStream dataOut = new DataOutputStream(
							new BufferedOutputStream(out));
					// Escribo el request
					dataOut.write(array);

					dataOut.flush();
					dataOut.close();

					// Check errors in response:
					if (con.getResponseCode() / 100 != 2) {
						throw new OcspException(
								"Ocurrio un error en la respuesta del OCSP.");
					}

					// Get Response
					InputStream in = (InputStream) con.getContent();
					OCSPResp ocspResponse = new OCSPResp(in);
                                                                                response = ocspResponse;
                                                                                status = processOcspResponse(ocspResponse, serialNumber);
				}
			} catch (Exception e) {
				throw new OcspException(
						"Ocurrio un error al validar la respuesta del OCSP.", e);
			}

			// Decodificando la respuesta
			System.out.println("Status convertido: " + status);

			isValid = (status == 0);
			if (!isValid) {
				System.err.println("No es valido");
				String stringError = getStatusString(status);
				throw new OcspException(stringError);
			}

			System.out.println("Si es valido");

		}
		return isValid;
	}

	private int processOcspResponse(OCSPResp ocspResponse,
			BigInteger serialNumber) throws OcspException {
		if (ocspResponse == null) {
			throw new OcspException("No se cargo la respuesta del OCSP.");
		}

		try {
                              
			int status = ocspResponse.getStatus();

			if (status == 0) {

				// Aunque la respuesta es activo, verificamos el estatus del
				// certificado.

				BasicOCSPResp basicOCSPResp;

				basicOCSPResp = (BasicOCSPResp) ocspResponse
						.getResponseObject();
				X509Certificate[] certificateChainCA = basicOCSPResp
						.getCerts("SUN");

				if (basicOCSPResp.verify(certificateChainCA[0].getPublicKey(),
						"SunRsaSign")) {

					SingleResp[] singleResp = basicOCSPResp.getResponses();
					CertificateID _cid = singleResp[0].getCertID();
					String toCheck1 = new String(_cid.getSerialNumber()
							.toByteArray());
					String toCkeck = new String(serialNumber.toByteArray());

					if (toCheck1.compareTo(toCkeck) == 0) {

						CertificateStatus certificateStatus = (CertificateStatus) singleResp[0]
								.getCertStatus();
						if (certificateStatus == null) {
							return 0;
						}
						if (certificateStatus.getClass() == UnknownStatus.class) {
							return 1;
						}
						if (certificateStatus.getClass() == RevokedStatus.class) {
							return 2;
						}
					} else {
						return 3;
					}
				} else {
					return 4;
				}
			} else {
				if (status == 1) {
					return 5;
				}
				if (status == 2) {
					return 6;
				}
				if (status == 3) {
					return 7;
				}
				if (status == 4) {
					return 8;
				}
				if (status == 5) {
					return 9;
				}
				return 10;
			}
			return -1;

		} catch (OCSPException e) {
			e.printStackTrace();
			throw new OcspException(
					"No ha sido posible verificar la respuesta del OCSP.", e);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			throw new OcspException(
					"No ha sido posible verificar la respuesta del OCSP.", e);
		}catch(Exception ee){
                                        ee.printStackTrace();
                                        throw new OcspException(
					"No ha sido posible verificar la respuesta del OCSP.", ee);
                                }
	}
      
        
    @Override
    public OCSPResp getOcspResponse() throws OcspException {
    
        
        return response;
    }
        

}

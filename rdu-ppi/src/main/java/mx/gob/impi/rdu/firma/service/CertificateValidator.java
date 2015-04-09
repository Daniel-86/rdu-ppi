/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.firma.service;

import java.security.PrivateKey;
import java.security.PublicKey;
import mx.gob.impi.rdu.firma.exception.BaseBusinessException;
import mx.gob.impi.rdu.firma.exception.PrivateKeyException;
import org.bouncycastle.ocsp.OCSPResp;

/**
 *
 * @author
 */
public interface CertificateValidator {
    public void start(byte[] fileItemCertificate, byte[] fileItemPrivateKey, String password) throws BaseBusinessException;
    public String[] getIssuer() throws Exception;
    public OCSPResp obtenerResponse()throws Exception;
    public String generaSelloDigital(String cadena) throws Exception;
    public String generaSelloDigitalPromovente(Object pKey, String cadena) throws Exception;
    public Boolean validaCadena(Object pKey, String cadenaEncode, String cadenaOriginal) throws Exception;
    public byte[] unsign(byte[] signedMessage, PublicKey publicKey);
    public byte[] decrypt(byte[] encryptedMessage, PrivateKey privateKey);
  
}

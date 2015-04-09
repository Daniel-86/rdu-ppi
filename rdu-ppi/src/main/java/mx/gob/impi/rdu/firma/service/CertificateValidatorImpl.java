package mx.gob.impi.rdu.firma.service;

import java.io.IOException;
import mx.gob.impi.rdu.util.FileServicesUtil;
import mx.gob.impi.rdu.firma.exception.FileException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.crypto.Cipher;
import mx.gob.impi.rdu.firma.exception.BaseBusinessException;
import mx.gob.impi.rdu.firma.exception.CertificateException;
import mx.gob.impi.rdu.firma.exception.OcspException;
import mx.gob.impi.rdu.firma.exception.PrivateKeyException;
import mx.gob.impi.rdu.util.BundleUtils;
import mx.gob.impi.rdu.util.Util;
import org.apache.log4j.Logger;
import org.bouncycastle.ocsp.BasicOCSPResp;
import org.bouncycastle.ocsp.OCSPReq;
import org.bouncycastle.ocsp.OCSPResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Valida la FIEL, cargando el certificado, la llave privada y el password. Se
 * valida la fecha de expiracion, la paridad entre certificado y llave privada,
 * se valida si es FIEL y el estatus del que indica el OCSP (SAT).
 *
 *
 * @version 0.1
 * @author INFOTEC
 *
 */
@Service("certificateValidatorService")
public class CertificateValidatorImpl implements CertificateValidator {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // parametros de la forma
    private String password;
    private byte[] certficate;
    private byte[] key;
    // Objetos para firma
    private byte[] certBytes;
    private byte[] privateKeyBytes;
    private X509Certificate cert;
    private X509Certificate certAC;
    private PrivateKey privateKey;
    private boolean expirado;
    private boolean paridad;
    private boolean esFIEL;
    private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final String FIRMA_IMPI = "hcwOt4/ipZ+T7tBPnrXlgsvbiKcuWZtT1yWpZvP8iYFxWTw44ze30QO8InoXNiCY9WqsNrSsq33k O9WMGbtLxrQalxc7WlW5pNZxbSSYnh2zAiIJktQZ7UILaLD33Gudax3W48G9fV68yf9WLq4lLXuo K2oPm/6vVtcH0i7A7aayw2X0qCpW9Pq6kslZYIUcgaq1FqN6lqT2ur+F+HFxiJgmKtuN3RQplch6 5LVGlYcIHeWlKGnD846Hx7B5oNAID7iodayk17ObRhMx1mRKZ+h0u6okBNyi78hbITKJ6l/Rf8Ed hPDEWYLcSaYwMv4HN//pOm8atp+JwdRMEa8snQ==";
    //private final String CERT_STORE_2011 = "-----BEGIN CERTIFICATE-----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-----END CERTIFICATE----------BEGIN CERTIFICATE-----MIIFuDCCBKCgAwIBAgIUMDAwMDAwMDAwMDAwMDAwMDEwMjIwDQYJKoZIhvcNAQEEBQAwggF4MQswCQYDVQQGEwJNWDEVMBMGA1UECBMMTWV4aWNvLCBELkYuMRkwFwYDVQQHExBDaXVkYWQgZGUgTWV4aWNvMRgwFgYDVQQKEw9CYW5jbyBkZSBNZXhpY28xIDAeBgNVBAsTF0dlcmVuY2lhIGRlIEluZm9ybWF0aWNhMRQwEgYDVQQDEwtBUkMgQmFueGljbzEVMBMGA1UELRMMQk1FODIxMTMwU1hBMSgwJgYDVQQMEx9BZG1pbmlzdHJhZG9yIENlbnRyYWwgZGUgbGEgSUVTMREwDwYGdYhdjzUREwUwNjA1OTEpMCcGBnWIXY81ExYdQXYuIDUgZGUgTWF5byAjMiwgQ29sLiBDZW50cm8xHjAcBgZ1iF2PNRcTEkFCQ0RFRjEyMzQ1Njc4SDAwMDEeMBwGBnWIXY81HRMSQUJDRDk4MDcwNlpZWFdWVTU0MSYwJAYJKoZIhvcNAQkBFhdjY29yb25hZEBiYW54aWNvLm9yZy5teDAeFw0wNDEwMjcwNTAwMDBaFw0wODEwMjcwNjAwMDBaMIIBijEvMC0GA1UECgwmU2VydmljaW8gZGUgQWRtaW5pc3RyYWNpw7NuIFRyaWJ1dGFyaWExODA2BgNVBAsML0FkbWluaXN0cmFjacOzbiBkZSBTZWd1cmlkYWQgZGUgbGEgSW5mb3JtYWNpw7NuMTgwNgYDVQQDDC9BLkMuIGRlbCBTZXJ2aWNpbyBkZSBBZG1pbmlzdHJhY2nDs24gVHJpYnV0YXJpYTEmMCQGA1UEEBMdQXYuIEhpZGFsZ28gNzcsIENvbC4gR3VlcnJlcm8xDjAMBgNVBBETBTA2MzAwMRIwEAYDVQQHDAlDb3lvYWPDoW4xGTAXBgNVBAgTEERpc3RyaXRvIEZlZGVyYWwxCzAJBgNVBAYTAk1YMRUwEwYDVQQtEwxTQVQ5NzA3MDFOTjMxITAfBgkqhkiG9w0BCQEWEmFzaXNuZXRAc2F0LmdvYi5teDE1MDMGCSqGSIb3DQEJAhMmUmVzcG9uc2FibGU6IENlc2FyIEx1aXMgUGVyYWxlcyBUZWxsZXowggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDkULY6icoRfNXtvQdPAGlTC2IwXcblxeBWxqeI0JZLZwbx117eJGV2SG9ej/4QxSsWKxF/C1X8HJVMvpj8P/Zty79I9poAPjzknq0k/1vTj4iwhOtOx08bEilXOJFYyjZQEkW1txB+v5mIVHPEGEmhZ5J/lOQP7v3r/jwwy1eB/yclMKqHuNipKPg7xQKp2nY84T/0plk5OHKHUJsQEwnlMBk00OuBKk1lrNyuCXnuCqKs7AboK15DvMTLdzS9n8SzN31N5fO5z4LCpRyROISZhJxjyHwfKeaPTnkqW2y6S0wWoa6qMJyUeqx6v9ZyrJl21bSc/OblpWirKBahDUHbAgMBAAGjJDAiMBIGA1UdEwEB/wQIMAYBAf8CAQAwDAYDVR0PBAUDAwfmADANBgkqhkiG9w0BAQQFAAOCAQEAWDLnTC+EObvXn7hzJmWrl9L3/AQBK6l3sJjPEqZSyWUQCW9z7Hv0r6ChKA4znj9merq/+SHbC04YiK5E0qLm9SgE0XgEdETjBNH4nSVQ7h5/m0EIxANPnYFj5YhSpPMIwFMvSGYjGxAUIANdlTELhusZck5XH1O3NT3ugiczKks9EI4aUGBVzJfHzz+bj+Js84LP0vCvW6vfH9o6WozY41HV0B0MIbPeFGQpMmntwsWhHjh9mTHxHb3ggvUlUvJ5z2aafS6msF6BsR58uI8bdiufFBc+gD+5aebjau7rGPVqCy3vJ0oTn8MiQRuBMUosqIisXzQfOzu+EGiEcYxuRQ==-----END CERTIFICATE-----";
    //private final String CERT_STORE_2012  ="-----BEGIN CERTIFICATE-----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-----END CERTIFICATE-----";
    //private final String OCSP_SERVER = "http://www.sat.gob.mx/OCSP";
    //private final String OCSP_SERVER = "https://cfdit.sat.gob.mx/edofiel";
    private OCSPReq ocspReq;
    private String status;
    // Servicios
    @Autowired
    private CertificateServices certificateServices;
    @Autowired
    private PrivateKeyServices privateKeyServices;
    @Autowired
    private OcspServices ocspServices;
    // Lista de errores
    private List<String> errors;
    private String certKey = null;
    public static String firmaPr = "";
    private Logger lger = Logger.getLogger(this.getClass());

    public CertificateValidatorImpl() {
        super();
//		certificateServices = new CertificateServicesImpl();
//		privateKeyServices = new PrivateKeyServicesImpl();
        //ocspServices = new OcspServicesImpl();

    }

    public void start(byte[] fileItemCertificate, byte[] fileItemPrivateKey, String password) throws BaseBusinessException {
        this.certficate = fileItemCertificate;
        this.key = fileItemPrivateKey;
        errors = new ArrayList<String>();
        this.password = password;
        // Lectura de objetos para firma
        try {
            //	SwingUtils.addTextDebugMessage(this.textArea, "Leyendo Archivos...");
            read();
        } catch (BaseBusinessException e) {
            //	SwingUtils.addTextDebugMessage(this.textArea, "****ERROR: "+e.getBusinessMessage());
            System.err.println("Error: " + e.getBusinessMessage());
            e.printStackTrace();
            errors.add(e.getBusinessMessage());
            throw new BaseBusinessException(e.getBusinessMessage(), e);

        }

        // Validar certificado
        try {
            //SwingUtils.addTextDebugMessage(this.textArea, "Validando Archivos...");
            validate();

        } catch (BaseBusinessException e) {
            //SwingUtils.addTextDebugMessage(this.textArea, "**** ERROR: " + e.getBusinessMessage());
            e.printStackTrace();
            errors.add(e.getBusinessMessage());
            throw new BaseBusinessException(e.getBusinessMessage(), e);

        }

        //Si es correcto obtener cert del firmante
        try {

            obtenerCertFirmante();

        } catch (Exception e) {
            //    SwingUtils.addTextDebugMessage(this.textArea, "**** ERROR: " + e.getMessage());    
            e.printStackTrace();
            errors.add(e.getMessage());
            throw new BaseBusinessException(e.getMessage(), e);

        }

        //return certKey;

    }

    public String[] getIssuer() throws Exception {

        String[] issuer = new String[5];
        issuer[0] = cert.getSubjectDN().getName();
        issuer[1] = cert.getIssuerX500Principal().getName();
        issuer[2] = certificateServices.getFirmaDigital(certBytes, privateKeyBytes, password.toCharArray());
        // issuer[3]=Utils.getDigest(ocspServices.getOcspResponse().getEncoded());                    
        Date or = ((BasicOCSPResp) ocspServices.getOcspResponse().getResponseObject()).getProducedAt();
        issuer[3] = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(or));
        issuer[4] = cert.getSerialNumber().toString();
        return issuer;

    }

    public OCSPResp obtenerResponse() throws Exception {

        return ocspServices.getOcspResponse();
    }

    public void obtenerCertFirmante() throws Exception {
        certKey = certificateServices.getFirmaDigital(certBytes, privateKeyBytes, password.toCharArray());

    }

    /**
     * Realiza la lectura de los archivos y genera el tipo de objeto
     * X509Certificate y PrivateKey.
     *
     * @throws CertificateException Si no pudo obtener el certificado.
     * @throws PrivateKeyException Si no pudo obtener la llave privada.
     */
    private void read() throws CertificateException, PrivateKeyException {
        readCertificate();
        readPrivateKey();

    }

    /**
     * Realiza la lectura del archivo y genera el tipo de objeto
     * X509Certificate.
     *
     * @throws CertificateException Si no pudo obtener el certificado.
     */
    private void readCertificate() throws CertificateException {
        System.out.println("-- readCertificate --");

        try {

            //certBytes =FileServicesUtil.getDataByteArray(certficate) ;                                                
            certBytes = (byte[]) certficate.clone();

        } catch (Exception fe) {
            //	SwingUtils.addTextDebugMessage(this.textArea, "**** ERROR No se encontro el archivo en la ruta seleccionada: " + fe.getBusinessMessage());
            throw new CertificateException(
                    "No se encontro el archivo en la ruta seleccionada.", fe);
        }

        this.cert = certificateServices.getX509Certificate(certBytes);
        BigInteger g = cert.getSerialNumber();
        String aas = g.toString();
        String sr = cert.getSubjectDN().getName();
        this.cert.getPublicKey();
    }

    /**
     * Realiza la lectura del archivo y genera el tipo de objeto PrivateKey.
     *
     * @throws PrivateKeyException Si no pudo obtener la llave privada.
     */
    private void readPrivateKey() throws PrivateKeyException {
        System.out.println("-- readCertificate --");
        try {
            //privateKeyBytes = FileServicesUtil.getDataByteArray(key);
            privateKeyBytes = (byte[]) key.clone();
        } catch (Exception e) {
            throw new PrivateKeyException(
                    "No ha sido posible extraer la llave privada.", e);
        }

        this.privateKey = privateKeyServices.createPrivateKey(privateKeyBytes, password.toCharArray());

    }

    /**
     * Obtiene el sello digital a partir de la cadena original
     *
     * @throws PrivateKeyException Si no pudo obtener la llave privada.
     */
    public String generaSelloDigital(String cadena) throws Exception {

        // Compute signature
        Signature instance = Signature.getInstance("SHA1withRSA");
        BASE64Encoder enc = new BASE64Encoder();
        instance.initSign(privateKey);
        instance.update((cadena).getBytes());
        byte[] signature = instance.sign();

        return enc.encode(signature);

    }

    /**
     * Obtiene el sello digital a partir de la cadena original y private key del
     * promovente
     *
     * @throws Exception Si no pudo obtener la llave privada.
     */
    public String generaSelloDigitalPromovente(Object pKey, String cadena) throws Exception {

        // Compute signature
        PrivateKey pKeyNueva = (PrivateKey) pKey;
        BASE64Encoder enc = new BASE64Encoder();
        Signature instance = Signature.getInstance("SHA1withRSA");
        instance.initSign(pKeyNueva);
        instance.update((cadena).getBytes());
        byte[] signature = instance.sign();

        return enc.encode(signature);

    }

    /**
     * Valida cadena sellada con la cadena en base de datos
     *
     * @throws Exception
     */
    public Boolean validaCadena(Object pKey, String cadenaEncode, String cadenaOriginal) throws Exception {

        PublicKey publicKey = (PublicKey) pKey;
        BASE64Decoder dec = new BASE64Decoder();
        // byte[] btye = dec.decodeBuffer();
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initVerify(publicKey);
        sign.update(cadenaOriginal.getBytes());//WORKING...
        return sign.verify(dec.decodeBuffer(cadenaEncode));

    }

    /**
     * Realiza las validaciones del certificado.
     *
     * @throws CertificateException Si el certificado no es valido, indicando el
     * motivo por el cual no es valido.
     * @throws OcspException Si no se pudo realizar la validacion con el OCSP.
     */
    private void validate() throws CertificateException, OcspException {
        validateExpirationDate();
        validateParity();
        validateIsFIEL();
        validateOcsp();
    }

    /**
     * Valida la fecha de expiracion del certificado.
     *
     * @throws CertificateException Si no se ha podido validar la expiracion del
     * certificado.
     */
    private void validateExpirationDate() throws CertificateException {

        // Valida fecha de expiracion
        Date expirationDate = certificateServices.getExpirationDate(certBytes);

        // Si se valida en el server, obtenemos la fecha
        Date serverDate = new Date();
        // Si no, obtenemos la fecha como cadena y la convertimos
        // Date serverDate = getDateFromString(fechaServidor);

        expirado = certificateServices.checkExpirationDate(serverDate,
                expirationDate);

        //System.out.println("El certificado " + (expirado == true ? "SI" : "NO") + " ha expirado.");
    }

    /**
     * Valida la paridad entre el certificado y la llave privada.
     *
     * @throws CertificateException Si no ha podido validar la paridad del
     * certificado y llave privada.
     */
    private void validateParity() throws CertificateException {

        // Valida paridad
        paridad = certificateServices.checkParity(certBytes, privateKeyBytes,
                password.toCharArray());
        //System.out.println("El certificado y la llave privada "+ (paridad == true ? "SI" : "NO") + " tienen paridad.");
    }

    /**
     * Valida que el certificado se trate de una FIEL.
     *
     * @throws CertificateException Si no ha podido validar que el certificado
     * sea una FIEL.
     */
    private void validateIsFIEL() throws CertificateException {
        // Valida que sea una FIEL
        esFIEL = certificateServices.isFiel(certBytes);
        //System.out.println("El certificado " + (esFIEL == true ? "SI" : "NO")	+ " es FIEL.");
    }

    /**
     * Valida contra el OCSP.
     *
     * @throws CertificateException Si no pudo obtener el certificado.
     * @throws OcspException Si no ha podido validar contra el OCSP.
     */
    private void validateOcsp() throws CertificateException, OcspException {
        getSATCertificate();
        getOcspRequest();
        getOcspResponse();
    }

    /**
     * Obtiene el certificado del SAT, en sus presentaciones para los generados
     * antes de 2012 y posteriores a 2012.
     *
     * @throws CertificateException Si no ha sido posible obtener el
     * certificado.
     */
    private void getSATCertificate() throws CertificateException {
        // Valida contra OCSP
        int anioGenerado = getAnioCreacion(cert);
        // primero verifica la fecha en que se genero el certificado
        if (anioGenerado >= 2012) {
            certAC = certificateServices.getX509Certificate(BundleUtils.getResource("firma.cert.2012"));
        } else {
            certAC = certificateServices.getX509Certificate(BundleUtils.getResource("firma.cert.2011"));
        }
    }

    /**
     * Obtiene el anioo de creacion del certificado.
     *
     * @param certificate Certificado a validar.
     * @return El numero del anio en que se genero el certificado.
     */
    private int getAnioCreacion(X509Certificate certificate) {
        Date before = certificate.getNotBefore();
        Calendar beforeCalendar = Calendar.getInstance();
        beforeCalendar.setTime(before);
        return beforeCalendar.get(Calendar.YEAR);
    }

    /**
     * Genera la peticion al OCSP.
     *
     * @throws OcspException Si no ha podido generar la peticion al OCSP.
     */
    private void getOcspRequest() throws OcspException {
        // genera objeto OCSPReq
        ocspReq = ocspServices.generateOCSPRequest(certAC,
                cert.getSerialNumber());
    }

    /**
     * Obtiene la respuesta del OCSP.
     *
     * @throws OcspException Si no ha podido obtener la respuesta del OCSP.
     */
    private void getOcspResponse() throws OcspException {
//		// obtiene la respuesta OCSP
//		OCSPResp ocspResp = ocspServices.getOCSPResp(ocspReq, OCSP_SERVER);
//		status = ocspServices.getStatusString(ocspResp);
//		System.out.println("Status: " + status);

        boolean isValid = ocspServices.isValid(ocspReq, BundleUtils.getResource("firma.ocsp.server"), cert.getSerialNumber());
        System.out.println("isValid: " + isValid);

        status = "Certificado activo";
    }

    public byte[] unsign(byte[] signedMessage, PublicKey publicKey) {
        try {
            // Read the signature from the signedMessage (and its length)
            int length = Util.byteArrayToInt(Arrays.copyOf(signedMessage, 4));
            byte[] sentSignature = Arrays.copyOfRange(signedMessage, 4, 4 + length);

            // Determine the signed hash sum of the message
            byte[] message = Arrays.copyOfRange(signedMessage, 4 + length, signedMessage.length);
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(publicKey);
            sig.update(message);

            // Verify the signature
            if (!sig.verify(sentSignature)) {
                throw new SignatureException("Signature invalid");
            }

            return message;
        } catch (InvalidKeyException exception) {
            exception.printStackTrace();
            lger.error("VALIDACION DEL CERTIFICADO: ERROR EN LA LLAVE >>> " + exception.getLocalizedMessage() + "   >>>>  " + exception.getMessage(), exception);
            return null;
        } catch (GeneralSecurityException exception) {
            // new String(signedMessage) 
            exception.printStackTrace();
            lger.error("VALIDACION DEL CERTIFICADO: ERROR EN EL MENSAJE >>> " + exception.getLocalizedMessage() + "   >>>>  " + exception.getMessage(), exception);
            lger.error("    <<<<---->>>>    El mensaje fue: " + new String(signedMessage));
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            lger.error("VALIDACION DEL CERTIFICADO: ERROR >>> " + exception.getLocalizedMessage() + "   >>>>  " + exception.getMessage(), exception);
            return null;
        }
    }

    public byte[] decrypt(byte[] encryptedMessage, PrivateKey privateKey) {
        try {
            // Read the symmetric key from the encrypted message (and its length)
            int length = Util.byteArrayToInt(Arrays.copyOf(encryptedMessage, 4));
            byte[] wrappedKey = Arrays.copyOfRange(encryptedMessage, 4, 4 + length);

            // Decrypt the symmetric key
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.UNWRAP_MODE, privateKey);
            Key symmetricKey = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);

            // Decrypt the message and return it
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, symmetricKey);

            return cipher.doFinal(Arrays.copyOfRange(encryptedMessage, 4 + length, encryptedMessage.length));
        } catch (GeneralSecurityException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    
}

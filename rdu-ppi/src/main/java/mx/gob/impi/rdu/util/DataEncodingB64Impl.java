package mx.gob.impi.rdu.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;

public class DataEncodingB64Impl implements DataEncoding{

	private static final DataEncoding instance = new DataEncodingB64Impl();

	public static DataEncoding getInstance(){
		return instance;
	}

	public String decodeData(String valueEncoded) throws EncodingException {
		List<String> valuesFlat = decodeData(new String[]{valueEncoded});

		if(valuesFlat!=null && !valuesFlat.isEmpty()){
			return valuesFlat.get(0);
		}

		return null;
	}

	public List<String> decodeData(String... valuesEncoded) throws EncodingException {
		List<String> valuesFlat = new ArrayList<String>();

		try{
			for (String value : valuesEncoded){
				if (value!=null && value.length()>0){
					byte[] dataDec = new Base64().decode(value.getBytes());

					String valueFlat = new String(dataDec);
					valuesFlat.add(valueFlat);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new EncodingException(e);
		}

		return valuesFlat;
	}

	public byte[] decodeDataToBytes(String valueEncoded) throws EncodingException {
		byte[] dataDecoded = null;

		if (valueEncoded==null) throw new IllegalArgumentException("El dato a decodificar es requerido");

		try{
			dataDecoded = new Base64().decode(valueEncoded.getBytes());

		}catch(Exception e){
			e.printStackTrace();
			throw new EncodingException(e);
		}

		return dataDecoded;
	}

	public String encodeData(String valuesFlat) throws EncodingException {

		List<String> valuesEncoded = encodeData(new String[]{valuesFlat});

		if (valuesEncoded!=null && !valuesEncoded.isEmpty()){
			return valuesEncoded.get(0); // Primer elemento
		}

		return null;
	}

	public List<String> encodeData(String... valuesFlat) throws EncodingException {
		List<String> valuesEncoded = new ArrayList<String>();

		try{
			for (String valueFlat : valuesFlat){
				if (valueFlat!=null && valueFlat.length()>0){
					byte[] encData = valueFlat.getBytes();

					String valueEncoded = new String(new Base64().encode(encData));

					valuesEncoded.add(valueEncoded);
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			throw new EncodingException(e);
		}

		return valuesEncoded;
	}

	public String encodeData(byte[] valueFlat) throws EncodingException {

		if (valueFlat==null) throw new IllegalArgumentException("Los datos a codificar son requeridos");

		String valueEncodedB64 = null;

		try{
			valueEncodedB64 = new String(new Base64().encode(valueFlat));

		}catch(Exception e){
			e.printStackTrace();
			throw new EncodingException(e);
		}

		return valueEncodedB64;
	}

    public InputStream getDecodeCipher(InputStream in) throws EncodingException {
        throw new EncodingException("Metodo no soportado");
    }

}
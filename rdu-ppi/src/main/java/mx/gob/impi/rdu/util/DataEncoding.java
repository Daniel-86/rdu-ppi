package mx.gob.impi.rdu.util;

import java.io.InputStream;
import java.util.List;

public interface DataEncoding {

	/**
	 * Codifica el parametro recibido
	 * Regresa el valor codificado
	 *
	 * @param valuesFlat Valor a codificar
	 * @return Valor codificado
	 * @throws EncodingException Lanzada en caso de error durante la codificacion
	 */
	public String encodeData(String valuesFlat) throws EncodingException;

	/**
	 * Codifica los parametros recibidos
	 * Regresa los valores codificados en el mismo orden en el cual se enviaron
	 *
	 * @param valuesFlat Valores a codificar
	 * @return Valores codificados en el mismo orden en el cual se enviaron
	 * @throws EncodingException Lanzada en caso de error durante la codificacion
	 */
	public List<String> encodeData(String... valuesFlat) throws EncodingException;

	/**
	 * Codifica el dato recibidos
	 *
	 * @param valueFlat Valor a codificar
	 * @return Valor codificado
	 * @throws EncodingException Lanzada en caso de error durante la codificacion
	 */
	public String encodeData(byte[] valueFlat) throws EncodingException;

	/**
	 * Decodifica el parametro
	 * Regresa el valor encriptado
	 *
	 * @param valuesEncoded Valor a decodificar
	 * @return Valor decodificado
	 * @throws EncodingException Lanzada en caso de error durante la decodificacion
	 */
	public String decodeData(String valueEncoded) throws EncodingException;

	/**
	 * Decodifica los parametros
	 * Regresa los valores decodificados en el mismo orden en el cual se enviaron
	 *
	 * @param valuesEncoded Valores a decodificar
	 * @return Valores decodificados en el mismo orden en el cual se enviaron
	 * @throws EncodingException Lanzada en caso de error durante la decodificacion
	 */
	public List<String> decodeData(String... valuesEncoded) throws EncodingException;

	/**
	 * Decodifica el parametro
	 *
	 * @param valuesEncoded Valor a decodificar
	 * @return Valor decodificado
	 * @throws EncodingException Lanzada en caso de error durante la decodificacion
	 */
	public byte[] decodeDataToBytes(String valueEncoded) throws EncodingException;

	/**
	 * Genera un envoltorio decodificador para el flujo indicado
	 * @param in flujo de datos
	 * @return InputStream para decodificacion
	 * @throws EncodingException Lanzada en caso de error al crear el componente
	 */
	public InputStream getDecodeCipher(InputStream in) throws EncodingException;
}
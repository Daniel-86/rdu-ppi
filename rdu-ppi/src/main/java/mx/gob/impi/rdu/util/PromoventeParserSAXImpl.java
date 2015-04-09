/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//import mx.gob.impi.rdu.modelo.negocio.intercambio.fachada.util.Utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.dto.ApoderadoDto;

/**
 * @author Fabian Guerra Soto
 *
 */
public class PromoventeParserSAXImpl extends DefaultHandler {
	private PromoventeDto promovente = null;
	private StringBuilder valor = null;
	private boolean promoventePrincipalPoblado = false;
	private ArrayList<ApoderadoDto> apoderados = null;
	private ApoderadoDto current = null;
	private boolean tieneApoderados = false;
	// Log logger = LogFactory.getLog(getClass());

	private InputStream inputStream;

	public PromoventeParserSAXImpl(byte[] in) {
		this.inputStream = new ByteArrayInputStream(in);
	}

	public PromoventeParserSAXImpl(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public PromoventeDto getPromovente() throws SAXException,
			ParserConfigurationException, IOException {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser parser;

		parser = parserFactory.newSAXParser();
		parser.parse(inputStream, this);

		promovente.setApoderados(apoderados);

		return promovente;
	}

	public void startDocument() throws SAXException {
		promovente = new PromoventeDto();
		valor = new StringBuilder(); // Se utiliza un StringBuilder ya que el
										// valor puede ser muy grande
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if ("apoderados".equalsIgnoreCase(qName)) {
			apoderados = new ArrayList<ApoderadoDto>();
			tieneApoderados = true;
		} else if (qName.equalsIgnoreCase("apoderado")) {
			current = new ApoderadoDto();
			apoderados.add(current);
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		// Se obtiene el valor contenido en el elemento
		String valorstr = valor.toString();

		if (!tieneApoderados) {
			// Pobla las propiedades del promovente
			if (qName.equalsIgnoreCase("promovente")) {
				if (!promoventePrincipalPoblado) {
					promoventePrincipalPoblado = true;
				} else {
					// PromoventeDto apoderado = new PromoventeDto();
					// promovente.addApoderadoAdicional(apoderado);
					// promovente = apoderado;
				}
			} else if (qName.equalsIgnoreCase("id_promovente")) {
				promovente.setId_promovente(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("nombre")) {
				promovente.setNombre(valorstr);
			} else if (qName.equalsIgnoreCase("apaterno")) {
				promovente.setApaterno(valorstr);
			} else if (qName.equalsIgnoreCase("amaterno")) {
				promovente.setAmaterno(valorstr);
			} else if (qName.equalsIgnoreCase("login")) {
				promovente.setLogin(valorstr);
			} else if (qName.equalsIgnoreCase("password")) {
				promovente.setPassword(valorstr);
			} else if (qName.equalsIgnoreCase("email")) {
				//promovente.setMail(valorstr);
                                promovente.setEmail(valorstr);
			} else if (qName.equalsIgnoreCase("rfc")) {
				promovente.setRfc(valorstr);
			} else if (qName.equalsIgnoreCase("calle_numero")) {
				//promovente.setCalle(valorstr);
                                promovente.setCalle_numero(valorstr);
			} else if (qName.equalsIgnoreCase("numero_exterior")) {
				promovente.setNumero_exterior(valorstr);
			} else if (qName.equalsIgnoreCase("numero_interior")) {
				promovente.setNumero_interior(valorstr);
			} else if (qName.equalsIgnoreCase("colonia")) {
				promovente.setColonia(valorstr);
			} else if (qName.equalsIgnoreCase("codigo_postal")) {
				promovente.setCodigo_postal(valorstr);
			} else if (qName.equalsIgnoreCase("telefono")) {
				promovente.setTelefono(valorstr);
			} else if (qName.equalsIgnoreCase("fax")) {
				promovente.setFax(valorstr);
			} else if (qName.equalsIgnoreCase("razon_social")) {
				promovente.setRazon_social(valorstr);
			} else if (qName.equalsIgnoreCase("fecha_registro")) {
				promovente.setFecha_registro(valorstr);
			} else if (qName.equalsIgnoreCase("fecha_activacion")) {
				promovente.setFecha_activacion(valorstr);
			} else if (qName.equalsIgnoreCase("habilitado")) {
				promovente.setHabilitado(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("id_perfil")) {
				promovente.setId_perfil(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("descPerfil")) {
				promovente.setDescPerfil(valorstr);
			} else if (qName.equalsIgnoreCase("id_promovente_padre")) {
				promovente.setId_promovente_padre(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("id_estado")) {
				promovente.setId_estado(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("descEstado")) {
				promovente.setDescEstado(valorstr);
			} else if (qName.equalsIgnoreCase("id_municipio")) {
				promovente.setId_municipio(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("descMunicipio")) {
				promovente.setDescMunicipio(valorstr);
			} else if (qName.equalsIgnoreCase("tipo_persona")) {
				promovente.setTipo_persona(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("descTipoPromovente")) {
				promovente.setDescTipoPromovente(valorstr);
			} else if (qName.equalsIgnoreCase("cargo")) {
				promovente.setCargo(valorstr);
			} else if (qName.equalsIgnoreCase("habilita_marcanet")) {
				promovente.setHabilita_marcanet(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("id_documento")) {
				//promovente.setIdDocumento(Utils.valueInt(valorstr));
			} else if (qName.equalsIgnoreCase("id_tramite")) {
				promovente.setId_tramite(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("apoderados")) {
				// logger.debug("valorstr:" + valorstr);
			}
		} else {
			if (qName.equalsIgnoreCase("id_promovente")) {
				current.setId_promovente(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("nombre")) {
				current.setNombre(valorstr);
			} else if (qName.equalsIgnoreCase("apaterno")) {
				current.setApaterno(valorstr);
			} else if (qName.equalsIgnoreCase("amaterno")) {
				current.setAmaterno(valorstr);
			} else if (qName.equalsIgnoreCase("login")) {
				current.setLogin(valorstr);
			} else if (qName.equalsIgnoreCase("password")) {
				current.setPassword(valorstr);
			} else if (qName.equalsIgnoreCase("email")) {
				current.setEmail(valorstr);
			} else if (qName.equalsIgnoreCase("rfc")) {
				current.setRfc(valorstr);
			} else if (qName.equalsIgnoreCase("calle_numero")) {
				//promovente.setCalle(valorstr);
                                current.setCalle_numero(valorstr);
			} else if (qName.equalsIgnoreCase("numero_exterior")) {
				current.setNumero_exterior(valorstr);
			} else if (qName.equalsIgnoreCase("numero_interior")) {
				current.setNumero_interior(valorstr);
			} else if (qName.equalsIgnoreCase("colonia")) {
				current.setColonia(valorstr);
			} else if (qName.equalsIgnoreCase("codigo_postal")) {
				current.setCodigo_postal(valorstr);
			} else if (qName.equalsIgnoreCase("telefono")) {
				current.setTelefono(valorstr);
			} else if (qName.equalsIgnoreCase("fax")) {
				current.setFax(valorstr);
			} else if (qName.equalsIgnoreCase("razon_social")) {
				current.setRazon_social(valorstr);
			} else if (qName.equalsIgnoreCase("fecha_registro")) {
				current.setFecha_registro(valorstr);
			} else if (qName.equalsIgnoreCase("fecha_activacion")) {
				current.setFecha_activacion(valorstr);
			} else if (qName.equalsIgnoreCase("habilitado")) {
				current.setHabilitado(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("id_perfil")) {
				current.setId_perfil(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("descPerfil")) {
				current.setDescPerfil(valorstr);
			} else if (qName.equalsIgnoreCase("id_promovente_padre")) {
				current.setId_promovente_padre(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("id_estado")) {
				current.setId_estado(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("descEstado")) {
				current.setDescEstado(valorstr);
			} else if (qName.equalsIgnoreCase("id_municipio")) {
				current.setId_municipio(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("descMunicipio")) {
				current.setDescMunicipio(valorstr);
			} else if (qName.equalsIgnoreCase("tipo_persona")) {
				current.setTipo_persona(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("descTipoPromovente")) {
				current.setDescTipoPromovente(valorstr);
			} else if (qName.equalsIgnoreCase("cargo")) {
				current.setCargo(valorstr);
			} else if (qName.equalsIgnoreCase("habilita_marcanet")) {
				current.setHabilita_marcanet(Integer.parseInt(valorstr.trim()));
			} else if (qName.equalsIgnoreCase("id_documento")) {
				//promovente.setIdDocumento(Utils.valueInt(valorstr));
			} else if (qName.equalsIgnoreCase("id_tramite")) {
				//current.setId_tramite(Integer.parseInt(valorstr.trim()));
                        }
		}

		// Se inicializa el contenedor del valor, para la nueva etiqueta
		valor = new StringBuilder();
	}

	public void characters(char buf[], int offset, int len) throws SAXException {
		String valAux = new String(buf, offset, len);
		valor.append(valAux.trim());
	}

	public void endDocument() throws SAXException {
	}

}

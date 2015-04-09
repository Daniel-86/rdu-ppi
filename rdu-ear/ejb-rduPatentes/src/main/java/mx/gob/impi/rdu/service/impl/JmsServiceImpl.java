package mx.gob.impi.rdu.service.impl;


import java.io.ByteArrayOutputStream;

import javax.jms.*;
import org.springframework.stereotype.Service;

/// Import de xml
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

// Fin de Imports de Ejemplos
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import mx.gob.impi.rdu.persistence.model.ListaExpedientePromPat;
import mx.gob.impi.rdu.persistence.model.PromocionesPatentes;

import mx.gob.impi.rdu.service.JmsService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;




//@Service
public class JmsServiceImpl implements JmsService{

	
	/**
	 * Envia una mensaje a la cola de consulta de catalogos
	 * @param String catalogo -Nombre del catalogo
	 * @return 	
	 */
	public String getCatalogoJms(String nombreCatalogo) {
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination  = null;
		MessageProducer msgProducer = null;
		TextMessage msg = null;
		QueueConnectionFactory factoryResponse = null;
		QueueConnection connectionResponse = null;
		QueueSession sessionResponse = null;
		TextMessage messageResponse = null;
		String respuesta = null;
		int ackMode = Session.AUTO_ACKNOWLEDGE;

		try {
			
			// Genera la conexion, la sesion y define la cola a la que se va a enviar el mensaje
			factory = new com.tibco.tibjms.TibjmsConnectionFactory("tcp://192.168.10.189:7222");
			connection = factory.createConnection();
			session = connection.createSession(false,ackMode);
			//destination = session.createQueue("IMPI.SOA.SIGA.CONSULTACATALOGOS");
                        destination = session.createQueue("IMPI.SOA.RDU.GETCATDEPARTAMENTO");//Cola de Jessica
			//******************
			
			// Genera una cola temporal para recibir la respuesta del mensaje
			factoryResponse  = new com.tibco.tibjms.TibjmsQueueConnectionFactory("tcp://192.168.10.189:7222");
			connectionResponse = factoryResponse.createQueueConnection();
			sessionResponse = connectionResponse.createQueueSession(false, ackMode);
			TemporaryQueue responseQueue = sessionResponse.createTemporaryQueue();
			QueueReceiver receiver = sessionResponse.createReceiver(responseQueue);
			connectionResponse.start();
			//*******************

			// *******Envia el mensaje
			msgProducer = session.createProducer(null);
			msg = session.createTextMessage();
			msg.setJMSType("XML Text");	//Define el tipo a enviar
			msg.setText(generaXml(nombreCatalogo,"input","idArea")); //Envia un string con el formato xml
			msg.setJMSReplyTo(responseQueue);	//Define la cola que va a recibir la respuesta del mensaje
			msgProducer.send(destination, msg);
//			System.err.println("Published message: "+msg);
			
			//*********************************
			
			//*******Recibe respuesta del mensaje enviado
			
			messageResponse = (TextMessage) receiver.receive();       
//			System.err.println("Received response message: "+messageResponse);
			
			connectionResponse.close();

			//*************************

			connection.close();
		} 
		catch (JMSException e) 
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			respuesta = messageResponse.getText();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respuesta;
	
	}
	
	public String getCatTipoPromocionJms(String nombreCatalogo) {
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination  = null;
		MessageProducer msgProducer = null;
		TextMessage msg = null;
		QueueConnectionFactory factoryResponse = null;
		QueueConnection connectionResponse = null;
		QueueSession sessionResponse = null;
		TextMessage messageResponse = null;
		String respuesta = null;
		int ackMode = Session.AUTO_ACKNOWLEDGE;

		try {
			
			// Genera la conexion, la sesion y define la cola a la que se va a enviar el mensaje
			factory = new com.tibco.tibjms.TibjmsConnectionFactory("tcp://192.168.10.189:7222");
			connection = factory.createConnection();
			session = connection.createSession(false,ackMode);
			//destination = session.createQueue("IMPI.SOA.SIGA.CONSULTACATALOGOS");
                        destination = session.createQueue("IMPI.SOA.RDU.GETCATTIPOPROMOCION");//Cola de Muñeco
			//******************
			
			// Genera una cola temporal para recibir la respuesta del mensaje
			factoryResponse  = new com.tibco.tibjms.TibjmsQueueConnectionFactory("tcp://192.168.10.189:7222");
			connectionResponse = factoryResponse.createQueueConnection();
			sessionResponse = connectionResponse.createQueueSession(false, ackMode);
			TemporaryQueue responseQueue = sessionResponse.createTemporaryQueue();
			QueueReceiver receiver = sessionResponse.createReceiver(responseQueue);
			connectionResponse.start();
			//*******************

			// *******Envia el mensaje
			msgProducer = session.createProducer(null);
			msg = session.createTextMessage();
			msg.setJMSType("XML Text");	//Define el tipo a enviar
			msg.setText(generaXml(nombreCatalogo,"input","idDepartamento")); //Envia un string con el formato xml
			msg.setJMSReplyTo(responseQueue);	//Define la cola que va a recibir la respuesta del mensaje
			msgProducer.send(destination, msg);
//			System.err.println("Published message: "+msg);
			
			//*********************************
			
			//*******Recibe respuesta del mensaje enviado
			
			messageResponse = (TextMessage) receiver.receive();       
//			System.err.println("Received response message: "+messageResponse);
			
			connectionResponse.close();

			//*************************

			connection.close();
		} 
		catch (JMSException e) 
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			respuesta = messageResponse.getText();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respuesta;
	
	}
	/**
	 * Genera una documento en formato XML
	 * @param String nombreCatalogo
	 * @return String en formato xml	
	 */
        private String generaXml(String nombreCatalogo,String rootXml,String ElementXml){
          
          String xmlAsString = null;  
          try{
            //Creamos primero los constructores, factory’s y demás…  
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation(); 
            //Ahora creamos el objeto Document, lo que vendría a ser nuestra raíz del arbol XML  
            //y también le seteamos la versión de XML.
           // Document document = implementation.createDocument(null, "Body", null);
          //   Document document = implementation.createDocument(null, "input", null);
               Document document = implementation.createDocument(null, rootXml, null);
            //document.setXmlVersion("1.0");
            //Obtenemos la raíz
            Element raiz = document.getDocumentElement();
            //Y luego creamos un nodo para agregarle a la raíz, el cual va a contener algún texto.
            //Element nodoNombreCampo = document.createElement("CatalogName"); //creamos un nuevo elemento
            //Element nodoNombreCampo = document.createElement("idArea"); //creamos un nuevo elemento
            Element nodoNombreCampo = document.createElement(ElementXml); //creamos un nuevo elemento
            Text nodoValorCampo = document.createTextNode(nombreCatalogo); //Ingresamos la info				
            //Text nodoValorCampo = document.createTextNode("4"); //Ingresamos la info				
            nodoNombreCampo.appendChild(nodoValorCampo); 						
            raiz.appendChild(nodoNombreCampo); //pegamos el elemento a la raiz "Documento"
            //Para terminar podemos guardar ese XML creado en un archivo, array de byte o lo que queramos…
            Source source = new DOMSource(document);
            Writer wr = new StringWriter();
            StreamResult result = new StreamResult(wr);
            //Result result = new StreamResult(new java.io.File("resultado.xml")); //nombre del archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            xmlAsString = result.getWriter().toString();
            
          } catch (ParserConfigurationException e) {
            System.out.println("Error al crear el XML ParserConfigurationException: " + e.getClass() + " - " + e.getMessage()+"");
          } catch (TransformerConfigurationException e) {
             System.out.println("Error al crear el XML TransformerConfigurationException: " + e.getClass() + " - " + e.getMessage()+"");
          } catch (TransformerException e) {
             System.out.println("Error al crear el XML TransformerException: " + e.getClass() + " - " + e.getMessage()+"");
          }  

          
          System.out.println("xmlAsString------"+xmlAsString);
          return xmlAsString;
        }

    public String getTipoPromByOficioJms(String idOficio) {
        ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination  = null;
		MessageProducer msgProducer = null;
		TextMessage msg = null;
		QueueConnectionFactory factoryResponse = null;
		QueueConnection connectionResponse = null;
		QueueSession sessionResponse = null;
		TextMessage messageResponse = null;
		String respuesta = null;
		int ackMode = Session.AUTO_ACKNOWLEDGE;

		try {
			
			// Genera la conexion, la sesion y define la cola a la que se va a enviar el mensaje
			factory = new com.tibco.tibjms.TibjmsConnectionFactory("tcp://192.168.10.189:7222");
			connection = factory.createConnection();
			session = connection.createSession(false,ackMode);
			//destination = session.createQueue("IMPI.SOA.SIGA.CONSULTACATALOGOS");
                        destination = session.createQueue("IMPI.SOA.RDU.GETTIPOPROMOCIONBYOFICIO");//Cola de Muñeco
			//******************
			
			// Genera una cola temporal para recibir la respuesta del mensaje
			factoryResponse  = new com.tibco.tibjms.TibjmsQueueConnectionFactory("tcp://192.168.10.189:7222");
			connectionResponse = factoryResponse.createQueueConnection();
			sessionResponse = connectionResponse.createQueueSession(false, ackMode);
			TemporaryQueue responseQueue = sessionResponse.createTemporaryQueue();
			QueueReceiver receiver = sessionResponse.createReceiver(responseQueue);
			connectionResponse.start();
			//*******************

			// *******Envia el mensaje
			msgProducer = session.createProducer(null);
			msg = session.createTextMessage();
			msg.setJMSType("XML Text");	//Define el tipo a enviar
			msg.setText(generaXml(idOficio,"input","idOficio")); //Envia un string con el formato xml
			msg.setJMSReplyTo(responseQueue);	//Define la cola que va a recibir la respuesta del mensaje
			msgProducer.send(destination, msg);
//			System.err.println("Published message: "+msg);
			
			//*********************************
			
			//*******Recibe respuesta del mensaje enviado
			
			messageResponse = (TextMessage) receiver.receive();       
//			System.err.println("Received response message: "+messageResponse);
			
			connectionResponse.close();

			//*************************

			connection.close();
		} 
		catch (JMSException e) 
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			respuesta = messageResponse.getText();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respuesta;
    }
    
    public String setTramitePromoPatJms(PromocionesPatentes promocionesPatentes) {
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination  = null;
		MessageProducer msgProducer = null;
		TextMessage msg = null;
		QueueConnectionFactory factoryResponse = null;
		QueueConnection connectionResponse = null;
		QueueSession sessionResponse = null;
		TextMessage messageResponse = null;
		String respuesta = null;
		int ackMode = Session.AUTO_ACKNOWLEDGE;

		try {
			
			// Genera la conexion, la sesion y define la cola a la que se va a enviar el mensaje
			factory = new com.tibco.tibjms.TibjmsConnectionFactory("tcp://192.168.10.189:7222");
			connection = factory.createConnection();
			session = connection.createSession(false,ackMode);
			//destination = session.createQueue("IMPI.SOA.SIGA.CONSULTACATALOGOS");
                        destination = session.createQueue("IMPI.SOA.RDU.SETPROMOPATTRANSACCIONAL");//
			//******************
			
			// Genera una cola temporal para recibir la respuesta del mensaje
			factoryResponse  = new com.tibco.tibjms.TibjmsQueueConnectionFactory("tcp://192.168.10.189:7222");
			connectionResponse = factoryResponse.createQueueConnection();
			sessionResponse = connectionResponse.createQueueSession(false, ackMode);
			TemporaryQueue responseQueue = sessionResponse.createTemporaryQueue();
			QueueReceiver receiver = sessionResponse.createReceiver(responseQueue);
			connectionResponse.start();
			//*******************

			// *******Envia el mensaje
			msgProducer = session.createProducer(null);
			msg = session.createTextMessage();
			msg.setJMSType("XML Text");	//Define el tipo a enviar
			msg.setText(generaXmlTramitePromPat(promocionesPatentes)); //Envia un string con el formato xml
			msg.setJMSReplyTo(responseQueue);	//Define la cola que va a recibir la respuesta del mensaje
			msgProducer.send(destination, msg);
//			System.err.println("Published message: "+msg);
			
			//*********************************
			
			//*******Recibe respuesta del mensaje enviado
			
			messageResponse = (TextMessage) receiver.receive();       
//			System.err.println("Received response message: "+messageResponse);
			
			connectionResponse.close();

			//*************************

			connection.close();
		} 
		catch (JMSException e) 
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			respuesta = messageResponse.getText();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respuesta;
	
	}
    
    //private String generaXmlTramitePromPat(String nombreCatalogo,String rootXml,String ElementXml){
    private String generaXmlTramitePromPat(PromocionesPatentes promocionesPatentes){
          
          String xmlAsString = null;  
          try{
            //Creamos primero los constructores, factory’s y demás…  
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation(); 
            // ************* DOCUMENTO RAIZ
            Document document = implementation.createDocument(null,"input", null);
            Element raiz = document.getDocumentElement();
            // *********** TRAMITE PROMOMOCION 
            Element tramitePromocion = document.createElement("tramitePromocion"); //creamos un nuevo elemento
            Element idPromocion =  document.createElement("idPromocion"); //creamos un nuevo elemento
            Text valorIdPromocion = document.createTextNode(promocionesPatentes.getIdPromocion()); //Ingresamos la info	
            idPromocion.appendChild(valorIdPromocion);
            tramitePromocion.appendChild(idPromocion);
            Element idUsuario =  document.createElement("idUsuario"); //creamos un nuevo elemento
            Text valorIdUsuario = document.createTextNode(promocionesPatentes.getIdUsuario()); //Ingresamos la info	
            idUsuario.appendChild(valorIdUsuario);
            tramitePromocion.appendChild(idUsuario);
            raiz.appendChild(tramitePromocion);
            /// ********* TRAMITE PROMOCION PATENTE
            Element tramitePromocionPatente = document.createElement("tramitePromocionPatente");
            Element idPromocion2 =  document.createElement("idPromocion"); //creamos un nuevo elemento
            Text valorIdPromocion2 = document.createTextNode(promocionesPatentes.getIdPromocion()); //Ingresamos la info
            idPromocion2.appendChild(valorIdPromocion2);
            tramitePromocionPatente.appendChild(idPromocion2);
            Element idTipoPromPat = document.createElement("idTipoPromPat");
            Text valorIdTipoPromPat = document.createTextNode(promocionesPatentes.getIdTipoPromPat());
            idTipoPromPat.appendChild(valorIdTipoPromPat);
            tramitePromocionPatente.appendChild(idTipoPromPat);
            Element oficioCodOficina = document.createElement("oficioCodOficina");
            Text valorOficioCodOficina = document.createTextNode(promocionesPatentes.getOficioCodOficina());
            oficioCodOficina.appendChild(valorOficioCodOficina);
            tramitePromocionPatente.appendChild(oficioCodOficina);
            Element oficioSerie = document.createElement("oficioSerie");
            Text valorOficioSerie = document.createTextNode(promocionesPatentes.getOficioSerie());
            oficioSerie.appendChild(valorOficioSerie);
            tramitePromocionPatente.appendChild(oficioSerie);
            Element oficioFolio = document.createElement("oficioFolio");
            Text valorOficioFolio = document.createTextNode(promocionesPatentes.getOficioFolio());
            oficioFolio.appendChild(valorOficioFolio);
            tramitePromocionPatente.appendChild(oficioFolio);
            Element plazoAdicional = document.createElement("plazoAdicional");
            Text valorPlazoAdicional = document.createTextNode(promocionesPatentes.getPlazoAdicional());
            plazoAdicional.appendChild(valorPlazoAdicional);
            tramitePromocionPatente.appendChild(plazoAdicional);
            Element indDescuento = document.createElement("indDescuento");
            Text valorIndDescuento = document.createTextNode(promocionesPatentes.getIndDescuento());
            indDescuento.appendChild(valorIndDescuento);
            tramitePromocionPatente.appendChild(indDescuento);
            Element descripcionProm = document.createElement("descripcionProm");
            Text valorDescripcionProm = document.createTextNode(promocionesPatentes.getDescripcionProm());
            descripcionProm.appendChild(valorDescripcionProm);
            tramitePromocionPatente.appendChild(descripcionProm);
            raiz.appendChild(tramitePromocionPatente);
            // ********* PROMOCION PATENTE 
            Element promExpediente = document.createElement("promExpediente");
            
            for (Iterator iter = promocionesPatentes.getListaExpedientePromPat().iterator(); iter.hasNext();) {
                
                ListaExpedientePromPat listaExpedientePromPat = (ListaExpedientePromPat) iter.next();
                
                Element listaExpediente = document.createElement("listaExpediente");
                Element idPromocion3 = document.createElement("idPromocion"); //creamos un nuevo elemento
                Text valorIdPromocion3 = document.createTextNode(listaExpedientePromPat.getIdPromocion()); //Ingresamos la info	
                idPromocion3.appendChild(valorIdPromocion3);
                listaExpediente.appendChild(idPromocion3);
                Element expCodOficina = document.createElement("expCodOficina");
                Text valorExpCodOficina = document.createTextNode(listaExpedientePromPat.getExpCodOficina());
                expCodOficina.appendChild(valorExpCodOficina);
                listaExpediente.appendChild(expCodOficina);
                Element expTipo = document.createElement("expTipo");
                Text valorExpTipo = document.createTextNode(listaExpedientePromPat.getExpTipo());
                expTipo.appendChild(valorExpTipo);
                listaExpediente.appendChild(expTipo);
                Element expSerie = document.createElement("expSerie");
                Text valorExpSerie = document.createTextNode(listaExpedientePromPat.getExpSerie());
                expSerie.appendChild(valorExpSerie);
                listaExpediente.appendChild(expSerie);
                Element expNumero = document.createElement("expNumero");
                Text valorExpNumero = document.createTextNode(listaExpedientePromPat.getExpNumero());
                expNumero.appendChild(valorExpNumero);
                listaExpediente.appendChild(expNumero);
                Element registro = document.createElement("registro");
                Text valorRegistro = document.createTextNode(listaExpedientePromPat.getRegistro());
                registro.appendChild(valorRegistro);
                listaExpediente.appendChild(registro);
                promExpediente.appendChild(listaExpediente);
                  
              }
            raiz.appendChild(promExpediente);
            // ************ ACTUALIZA
            Element actualiza =  document.createElement("actualiza"); //creamos un nuevo elemento
            Text valorActualiza = document.createTextNode("1"); //Ingresamos la info	
            actualiza.appendChild(valorActualiza);
            raiz.appendChild(actualiza);
            

            //Para terminar podemos guardar ese XML creado en un archivo, array de byte o lo que queramos…
            Source source = new DOMSource(document);
            Writer wr = new StringWriter();
            StreamResult result = new StreamResult(wr);
            //Result result = new StreamResult(new java.io.File("resultado.xml")); //nombre del archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            xmlAsString = result.getWriter().toString();
            
          } catch (ParserConfigurationException e) {
            System.out.println("Error al crear el XML ParserConfigurationException: " + e.getClass() + " - " + e.getMessage()+"");
          } catch (TransformerConfigurationException e) {
             System.out.println("Error al crear el XML TransformerConfigurationException: " + e.getClass() + " - " + e.getMessage()+"");
          } catch (TransformerException e) {
             System.out.println("Error al crear el XML TransformerException: " + e.getClass() + " - " + e.getMessage()+"");
          }  

          
          System.out.println("xmlAsString------"+xmlAsString);
          return xmlAsString;
        }
       
}

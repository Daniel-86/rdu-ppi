package mx.gob.impi.rdu.service.impl;

import java.util.ArrayList;
import java.util.List;

//import mx.gob.impi.gacmed.model.Gaceta;
//import mx.gob.impi.gacmed.model.GacetaSiga;
//import mx.gob.impi.gacmed.model.Seccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import mx.gob.impi.rdu.service.AdminJmsService;
import mx.gob.impi.rdu.persistence.model.CatAreaPromPatentes;
import mx.gob.impi.rdu.persistence.model.CatTipoPromPatentes;
import mx.gob.impi.rdu.persistence.model.TipoPromPatByOficio;


//@Service
public class AdminJmsServiceImpl implements AdminJmsService{
	
//	@Autowired
//	private Gaceta gaceta;
	
	/**
     * Regresa una lista con los elementos del catalogo obtenido de la respuesta del mensaje
     *@param String cadenaXml con la respuesta del mensaje
     *@param Object Objeto del cual se va conformar la lista de regreso
     *@return List Lista de objetos que contiene el catalogo
     */
	@Override
	public List<CatAreaPromPatentes> getCatalogo(String cadenaXML) {
		

		List<CatAreaPromPatentes> elementosCatalogo = new ArrayList<CatAreaPromPatentes>();
                
		DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new java.io.StringReader(cadenaXML)));
	
			Document doc = parser.getDocument();
			NodeList nodeList = doc.getElementsByTagName("ns0:catDepartamento");
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Element elementoBody = (Element) nodeList.item(i);
				
					NodeList nlsId = elementoBody.getElementsByTagName("ns0:idDepartamento");
					Element elementoId = (Element) nlsId.item(0);
					String id = elementoId.getFirstChild().getNodeValue();
		
					NodeList nlsDescripcion = elementoBody.getElementsByTagName("ns0:descripcion");
					Element elementoDescripcion = (Element) nlsDescripcion.item(0);
					String descripcion = elementoDescripcion.getFirstChild().getNodeValue();
                                        
                                        CatAreaPromPatentes areaprompat = new CatAreaPromPatentes();
                                        areaprompat.setIdAreaPromPat(id);
                                        areaprompat.setDescripcionPromPat(descripcion);
                                        
                                        elementosCatalogo.add(areaprompat);

					//elementosCatalogo.add("<----- ID: " + id + "  Descripcion: " + descripcion + " ---->");
				
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return elementosCatalogo;
	}
    @Override
    public List<CatTipoPromPatentes> getCatTipoPromPat(String cadenaXML) {
        
               List<CatTipoPromPatentes> elementosCatalogo = new ArrayList<CatTipoPromPatentes>();
                
		DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new java.io.StringReader(cadenaXML)));
	
			Document doc = parser.getDocument();
			NodeList nodeList = doc.getElementsByTagName("ns0:catTipoPromocionPatente");
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Element elementoBody = (Element) nodeList.item(i);
				        
                                        CatTipoPromPatentes tipoPromPat = new CatTipoPromPatentes();
                                         
					NodeList nlsId = elementoBody.getElementsByTagName("ns0:idTipoPromocion");
					Element elementoId = (Element) nlsId.item(0);
					String id = elementoId.getFirstChild().getNodeValue();
                                        tipoPromPat.setIdTipoPromocion(id);
		
					NodeList nlsDescripcion = elementoBody.getElementsByTagName("ns0:descripcion");
					Element elementoDescripcion = (Element) nlsDescripcion.item(0);
					String descripcion = elementoDescripcion.getFirstChild().getNodeValue();
                                        tipoPromPat.setDescripcion(descripcion);
                                        
                                        NodeList nlsindDescuento = elementoBody.getElementsByTagName("ns0:indDescuento");
					Element elementoindDescuento = (Element) nlsindDescuento.item(0);
					String indDescuento = elementoindDescuento.getFirstChild().getNodeValue();
                                        tipoPromPat.setIndDescuento(indDescuento);
                                        
                                        NodeList nlsindPlazoAdicional = elementoBody.getElementsByTagName("ns0:indPlazoAdicional");
					Element elementoindPlazoAdicional = (Element) nlsindPlazoAdicional.item(0);
					String indPlazoAdicional = elementoindPlazoAdicional.getFirstChild().getNodeValue();
                                        tipoPromPat.setIndPlazoAdicional(indPlazoAdicional);
                                        
                                        NodeList nlsindExpVinculado = elementoBody.getElementsByTagName("ns0:indExpVinculado");
					Element elementoindExpVinculado = (Element) nlsindExpVinculado.item(0);
					String indExpVinculado = elementoindExpVinculado.getFirstChild().getNodeValue();
                                        tipoPromPat.setIndExpVinculado(indExpVinculado);
                                        
                                        NodeList nlsindExpMultiple = elementoBody.getElementsByTagName("ns0:indExpMultiple");
					Element elementoindExpMultiple = (Element) nlsindExpMultiple.item(0);
					String indExpMultiple = elementoindExpMultiple.getFirstChild().getNodeValue();
                                        tipoPromPat.setIndExpMultiple(indExpMultiple);
                                        
                                        NodeList nlstarifaArticulo = elementoBody.getElementsByTagName("ns0:tarifaArticulo");
					Element elementotarifaArticulo = (Element) nlstarifaArticulo.item(0);
					String tarifaArticulo = elementotarifaArticulo.getFirstChild().getNodeValue();
                                        tipoPromPat.setTarifaArticulo(tarifaArticulo);
                                        
                                        NodeList nlstarifaInciso = elementoBody.getElementsByTagName("ns0:tarifaInciso");
					Element elementotarifaInciso = (Element) nlstarifaInciso.item(0);
					String tarifaInciso = elementotarifaInciso.getFirstChild().getNodeValue();
                                        tipoPromPat.setTarifaInciso(tarifaInciso);
                                        
                                        elementosCatalogo.add(tipoPromPat);

					//elementosCatalogo.add("<----- ID: " + id + "  Descripcion: " + descripcion + " ---->");
				
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return elementosCatalogo;
    }
    @Override
    public List<TipoPromPatByOficio> getTipoPromByOficio(String cadenaXML) {
         
                List<TipoPromPatByOficio> elementosCatalogo = new ArrayList<TipoPromPatByOficio>();
                
		DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new java.io.StringReader(cadenaXML)));
	
			Document doc = parser.getDocument();
			NodeList nodeList = doc.getElementsByTagName("ns0:tipoPromocionPatente");
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Element elementoBody = (Element) nodeList.item(i);
				        
                                        TipoPromPatByOficio tipoPromPat = new TipoPromPatByOficio();
                                         
					NodeList nlsTipoProm = elementoBody.getElementsByTagName("ns0:tipoPromocion");
					Element elementoTipoProm = (Element) nlsTipoProm.item(0);
					String tipoPromocion = elementoTipoProm.getFirstChild().getNodeValue();
                                        tipoPromPat.setTipoPromocion(tipoPromocion);
		
					NodeList nlsExpediente = elementoBody.getElementsByTagName("ns0:expediente");
					Element elementoExpediente = (Element) nlsExpediente.item(0);
					String expediente = elementoExpediente.getFirstChild().getNodeValue();
                                        tipoPromPat.setExpediente(expediente);
                                        
                                        NodeList nlsConcesion = elementoBody.getElementsByTagName("ns0:concesion");
					Element elementoConcesion = (Element) nlsConcesion.item(0);
                                        if (elementoConcesion.hasChildNodes()) {
                                            String concesion = elementoConcesion.getFirstChild().getNodeValue();
                                            tipoPromPat.setConcesion(concesion);
                                        }
                                        
                                        NodeList nlsDesOficio = elementoBody.getElementsByTagName("ns0:desOficio");
					Element elementoDesOficio= (Element) nlsDesOficio.item(0);
					String desOficio = elementoDesOficio.getFirstChild().getNodeValue();
                                        tipoPromPat.setDesOficio(desOficio);
                                        
                                        NodeList nlsDesTipoPromocion = elementoBody.getElementsByTagName("ns0:desTipoPromocion");
					Element elementoDesTipoPromocion = (Element) nlsDesTipoPromocion.item(0);
                                        if (elementoDesTipoPromocion.hasChildNodes()) {
                                            String desTipoPromocion = elementoDesTipoPromocion.getFirstChild().getNodeValue();
                                            tipoPromPat.setDesTipoPromocion(desTipoPromocion);
                                        }
                                        
                                        NodeList nlsIdDepartamento = elementoBody.getElementsByTagName("ns0:idDepartamento");
					Element elementoIdDepartamento = (Element) nlsIdDepartamento.item(0);
                                        if(elementoIdDepartamento.hasChildNodes()){
                                            String idDepartamento = elementoIdDepartamento.getFirstChild().getNodeValue();
                                            tipoPromPat.setIdDepartamento(idDepartamento);
                                        }
                                        
                                        NodeList nlsDesDepartamento = elementoBody.getElementsByTagName("ns0:desDepartamento");
					Element elementoDesDepartamento = (Element) nlsDesDepartamento.item(0);
                                        if(elementoDesDepartamento.hasChildNodes()){
                                            String desDepartamento = elementoDesDepartamento.getFirstChild().getNodeValue();
                                            tipoPromPat.setDesDepartamento(desDepartamento);
                                        }

                                        
                                        elementosCatalogo.add(tipoPromPat);

					//elementosCatalogo.add("<----- ID: " + id + "  Descripcion: " + descripcion + " ---->");
				
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return elementosCatalogo;
    }

    public String getTramitePromocionPatente(String cadenaXML) {
        String descripcion = null;
        DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new java.io.StringReader(cadenaXML)));
	
			Document doc = parser.getDocument();
			NodeList nodeList = doc.getElementsByTagName("control");
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Element elementoBody = (Element) nodeList.item(i);
				                                          
                                        NodeList nlsStatus = elementoBody.getElementsByTagName("status");
					Element elementoStatus = (Element) nlsStatus.item(0);
					String status = elementoStatus.getFirstChild().getNodeValue();
                                        //tipoPromPat.setTipoPromocion(tipoPromocion);
		
					NodeList nlsDescripcion = elementoBody.getElementsByTagName("descripcion");
					Element elementoDescripcion = (Element) nlsDescripcion.item(0);
					descripcion = elementoDescripcion.getFirstChild().getNodeValue();
                                        //tipoPromPat.setExpediente(expediente);
                                        

			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return descripcion;
    }

    public String setTramitePromoPatJms(String cadenaXML) {
        String descripcion = null;
        DOMParser parser = new DOMParser();
		try {
			parser.parse(new InputSource(new java.io.StringReader(cadenaXML)));
	
			Document doc = parser.getDocument();
			NodeList nodeList = doc.getElementsByTagName("control");
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				
				Element elementoBody = (Element) nodeList.item(i);
				                                          
                                        NodeList nlsStatus = elementoBody.getElementsByTagName("status");
					Element elementoStatus = (Element) nlsStatus.item(0);
					String status = elementoStatus.getFirstChild().getNodeValue();
                                        //tipoPromPat.setTipoPromocion(tipoPromocion);
		
					NodeList nlsDescripcion = elementoBody.getElementsByTagName("descripcion");
					Element elementoDescripcion = (Element) nlsDescripcion.item(0);
					descripcion = elementoDescripcion.getFirstChild().getNodeValue();
                                        //tipoPromPat.setExpediente(expediente);
                                        

			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return descripcion;
    }
	
}

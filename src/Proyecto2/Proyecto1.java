package Proyecto2;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import org.apache.commons.lang3.StringEscapeUtils;
import org.htmlcleaner.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;


public class Proyecto1 {

	   private static Document abrirSitio(String ruta){
			try {
				// Creamos una instancia de HtmlCleaner y le pedimos que parsee la página
				TagNode tagNode;
				HtmlCleaner cleaner=new HtmlCleaner();
				
				CleanerProperties props = cleaner.getProperties();
				 
				props.setCharset("UTF-8");
				props.setTranslateSpecialEntities(false);
				props.setRecognizeUnicodeChars(true);
				props.setTransResCharsToNCR(false);
				
				URL url=new URL(ruta);
				HttpURLConnection httpcon=(HttpURLConnection)url.openConnection();
				httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:29.0) Gecko/20100101 Firefox/29.0");
				tagNode=cleaner.clean(httpcon.getInputStream(),"UTF-8");

				//for(TagNode s:tagNode.getAllElements(true))
				//System.out.println(s.getText());
				
				// Para trabajar con DOM, necesitamos serializarlo y generar el arbol DOM
				Document document = new DomSerializer(props).createDOM(tagNode);
				
		      	return document;				
				
			} catch (IOException | ParserConfigurationException e) {
				System.out.println("No se ha podido leer HTML");
				e.printStackTrace();
			    return null;
			}
	   }
	   
	   private static Document abrirArchivo(String ruta){
				try {
					// Creamos una instancia de HtmlCleaner y le pedimos que parsee la página
					TagNode tagNode;
					HtmlCleaner cleaner=new HtmlCleaner();
					tagNode=cleaner.clean(new File(ruta));

					// Para trabajar con DOM, necesitamos serializarlo y generar el arbol DOM
					Document document = new DomSerializer(new CleanerProperties()).createDOM(tagNode);
					
			      	return document;				
					
				} catch (IOException | ParserConfigurationException e) {
				    return null;
				}
	   }
	   
	   private static boolean guardarArchivoUsandoXSLT(Document doc, String rutaXsl, String ruta) throws UnsupportedEncodingException, FileNotFoundException{
			try {
				
		        File xsltFile = new File(rutaXsl);

		        // JAXP lee utilizando una instancia Source
		        Source xsltSource = new StreamSource(xsltFile);
		        Source xmlSource = new DOMSource(doc);
		        Result htmlResult = new StreamResult(ruta);

		        // Transformer soporta procesado XSLT
		        TransformerFactory transFact =
		                TransformerFactory.newInstance();
		        Transformer trans = transFact.newTransformer(xsltSource);

		        
		        // Aplicar XSLT y generar html
		        trans.transform(xmlSource, htmlResult);
		    	
		    	return true;
			
			   } catch (TransformerException | TransformerFactoryConfigurationError e) {
				  e.printStackTrace(); 
			      return false;
			   }
	   }
	   
	   private static NodeList evaluarXPath(Document doc, String xpath){
					XPathFactory xpFactory = XPathFactory.newInstance();
					XPath xPath = xpFactory.newXPath();
					NodeList nodos;
					try {
						 nodos = (NodeList) xPath.evaluate(
								xpath, doc, XPathConstants.NODESET);
					} catch (XPathExpressionException e) {
						
						nodos=null;
					}
							
				    return nodos;
	   }
	
	   private static String limpiarEntidades(String origen)
	   {
          return StringEscapeUtils.unescapeHtml4(origen);
	   }
	   
	   public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException, InterruptedException{
		   String archivoPrecioXsl="archivos/htmlproyecto/indexPrecio2.xsl",
				   archivoResultado="archivos/htmlproyecto/Resultado3.html";
		  
		   Document docBMW =  abrirSitio("https://www.bmw.es/vc/ncc/xhtml/start/startWithModelSelection.faces?productType=1&brand=BM&market=ESPT&country=ES&locale=es_ES");
		   Document docVolvo =  abrirSitio("http://www.volvocars.com/es/explore/pages/model-line-up-new.aspx");
		   
		   NodeList cocheNombre=evaluarXPath(docBMW,".//*[@class='vicVehicle']/div/span[1]");
		   NodeList cochePrecio=evaluarXPath(docBMW,".//*[@class='vicVehicle']/div/span[3]");
		   NodeList cocheImagen=evaluarXPath(docBMW,".//*[@id='vicStage']");
		   NodeList cocheWeb=evaluarXPath(docBMW,".//*[@id='topNavi_homeLink']");
		   
		   NodeList cocheNombres2=evaluarXPath(docVolvo,"//div[@class='product9Description']/a/text()");
		   NodeList cocheURL2 = evaluarXPath(docVolvo,"//div[@class='product9Description']/a/@href");
		   NodeList cochePrecio2=evaluarXPath(docVolvo,"//div[@class='productPrices']//@alt");
		   NodeList cocheImagene2=evaluarXPath(docVolvo,"//div[@class='product9ImageProduct']/img/@src");
		   NodeList cocheWeb2=evaluarXPath(docVolvo,"//div[@id='navigation']//li[1]//@href");
		   
		   Document docXML=null;
		   try {
			   DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			   DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			   docXML = docBuilder.newDocument();
		   } catch (ParserConfigurationException e) {
			   e.printStackTrace();
		   }
		   
		   Element raiz = (Element)docXML.createElement("coches"); 
		   docXML.appendChild(raiz);
			     
		   
		   for(int i=0;i<cocheNombre.getLength();i++){
			   
			   Node coche=docXML.createElement("coche");
			   Node imagen=docXML.createElement("imagen_coche");
			   Node nombre=docXML.createElement("nombre_coche");
			   Node precio=docXML.createElement("precio_coche");
			   Node pagreferencia=docXML.createElement("pagreferencia_coche");
			   
		
			   pagreferencia.setTextContent(cocheWeb.item(0).getTextContent());
			   imagen.setTextContent(limpiarEntidades(cocheImagen.item(i).getTextContent()));
			   nombre.setTextContent(limpiarEntidades(cocheNombre.item(i).getTextContent()));
			   String numero = limpiarEntidades(cochePrecio.item(i).getTextContent()).trim();
			   precio.setTextContent(numero.substring(0, numero.length()-1));

			   coche.appendChild(pagreferencia);
			   coche.appendChild(imagen);
			   coche.appendChild(nombre);
			   coche.appendChild(precio);
		
			   raiz.appendChild(coche);
		   }     
		   
		   for(int i=0;i<cocheNombres2.getLength();i++){
			   
			   Node coche2=docXML.createElement("coche");
			   
			   Node pagreferencia=docXML.createElement("pagreferencia");
			   Node imagen=docXML.createElement("imagen");
			   Node nombre=docXML.createElement("nombre");
			   Node precio=docXML.createElement("precio");
			   
			   pagreferencia.setTextContent(cocheWeb2.item(0).getTextContent());
			   imagen.setTextContent(limpiarEntidades(cocheImagene2.item(i).getTextContent()));
			   nombre.setTextContent(limpiarEntidades(cocheNombres2.item(i).getTextContent()));
			   String numero = limpiarEntidades(cochePrecio2.item(i).getTextContent());
			   try{
				   precio.setTextContent(numero.substring(0, numero.length()-3));
			   }catch(Exception e){
				   System.out.println("Se ha registrado un problema al transformar a número " + numero);
			   }

			   coche2.appendChild(pagreferencia);
			   coche2.appendChild(imagen);
			   coche2.appendChild(nombre);
			   coche2.appendChild(precio);
			   
			   raiz.appendChild(coche2);
		   }
		  
		   guardarArchivoUsandoXSLT(docXML,archivoPrecioXsl,archivoResultado);
			   
	   }
}

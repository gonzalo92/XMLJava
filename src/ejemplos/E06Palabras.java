package ejemplos;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class E06Palabras {

	public static void main(String[] args) throws IOException, XPatherException {
		String urlPalabraAleatoria = "http://www.palabrasque.com/palabra-aleatoria.php";
		
		// Clean Builder
		CleanerProperties props = new CleanerProperties();
		// set some properties to non-default values
		props.setTranslateSpecialEntities(true);
		props.setTransResCharsToNCR(true);
		props.setOmitComments(true);
		
		URL url=new URL(urlPalabraAleatoria);
		HttpURLConnection httpcon=(HttpURLConnection)url.openConnection();
		httpcon.addRequestProperty("User-Agent", "Mozilla/4.76");
		TagNode tagNode = new HtmlCleaner(props).clean(
		    httpcon.getInputStream()
		);

		// guardar una copia
		new PrettyXmlSerializer(props).writeToFile(
			    tagNode, "archivos/palabra.xml", "utf-8");
		
		Object[] palabra=tagNode.evaluateXPath("/html/body/center/font[1]/strong");
		
		//System.out.println(palabra[0].getText());
		
	}

}

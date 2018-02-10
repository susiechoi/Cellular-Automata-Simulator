package cellsociety_team17;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class XMLSaver {
	public XMLSaver(Grid g, HashMap<String, Object> myAttributes){
		Document dom;
		Element elem = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();
			
			//Create all the meta data
			Element myRoot = dom.createElement("data");
			elem = dom.createElement("meta");
			myRoot.appendChild(elem);
			Iterator it = myAttributes.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				Element tempAttrElement = dom.createElement((String) pair.getKey());
				tempAttrElement.setTextContent(pair.getValue().toString());
			}
			
		} catch(Exception e) {
			System.out.println("Error trying to create XML document");
		}
	}
}

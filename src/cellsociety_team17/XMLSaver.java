package cellsociety_team17;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;


public class XMLSaver {
	Document dom;
	public XMLSaver(Grid g, HashMap<String, Object> myAttributes){
		Element elem = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();
			
			//Create all the meta data
			Element myRoot = dom.createElement("data");
			Element metaElem = dom.createElement("meta");
			
			Iterator it = myAttributes.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				Element tempAttrElement = dom.createElement((String) pair.getKey());
				tempAttrElement.setTextContent(pair.getValue().toString());
				metaElem.appendChild(tempAttrElement);
			}
			myRoot.appendChild(metaElem);
			
			Element gridElem = dom.createElement("grid");
			Cell[][] myGrid = g.getMyCells();
			for(int i = 0; i < myGrid.length; i++) {
				Element tempRow = dom.createElement("row");
					for(int k = 0; k < myGrid[i].length; k++) {
						Element tempCell = dom.createElement("cell");
						tempCell.setAttribute("column", Integer.toString(k));
						tempCell.setAttribute("row", Integer.toString(i));
						tempCell.setTextContent(Integer.toString(myGrid[i][k].getMyState()));
						tempRow.appendChild(tempCell);
					}
				
				gridElem.appendChild(tempRow);
			}
			myRoot.appendChild(gridElem);
			dom.appendChild(myRoot);
			
		} catch(Exception e) {
			System.out.println("Error trying to create XML document");
		}
	}

	public void save() {
		try {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(dom);
		//StreamResult result = new StreamResult(System.out);
		StreamResult result = new StreamResult(new File("saved_simulation.xml"));
		
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
		System.out.println("File saved");
		} catch(Exception e) {
			System.out.println("File could not be saved.");
			e.printStackTrace();
		}
		
	}
}

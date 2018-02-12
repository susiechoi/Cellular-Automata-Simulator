package cellsociety_team17;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	/**
	 * The class that handles saving a previously run simulation
	 * @param g the grid of the simulation to be saved
	 * @param myAttributes the attributes of the simulation to be saved
	 */
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
	/**
	 * Handles all the operations to save a simulation and output the file to the root of this programs directory
	 */
	public void save() {
		try {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(dom);
		//StreamResult result = new StreamResult(System.out);
		
		DateFormat df = new SimpleDateFormat("dd_MM_yy_HH_mm_ss");
		Date dateobj = new Date();
		StreamResult result = new StreamResult(new File("saved_simulation_" + df.format(dateobj) + ".xml"));
		
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.transform(source, result);
		System.out.println("File saved");
		} catch(Exception e) {
			System.out.println("File could not be saved.");
			e.printStackTrace();
		}
		
	}
}

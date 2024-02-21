import com.altova.Application;
import com.altova.Core;
import com.altova.IO;
import com.altova.xml.JsonNode;
import com.altova.xml.XmlNode;

public class JsonToXmlConverter {
    public static void main(String[] args) {
        // Initialize the XMLSpy application
        Application app = new Application();

        try {
            // Load JSON data from file
            JsonNode jsonNode = app.loadJsonFile("input.json");

            // Convert JSON to XML
            XmlNode xmlNode = app.jsonToXml(jsonNode);

            // Save XML to file
            app.save(xmlNode, "output.xml");
            
            // Optionally, you can also get the XML as a string
            // String xmlString = xmlNode.saveToString();
            
            System.out.println("Conversion successful.");
        } catch (Core.Exception e) {
            e.printStackTrace();
            System.err.println("Error converting JSON to XML: " + e.getMessage());
        } finally {
            // Close the XMLSpy application when done
            app.quit();
        }
    }
}

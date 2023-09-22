import javax.xml.stream.*;
import java.io.*;

public class XMLPruner {
    public static void main(String[] args) {
        // Input and output file paths
        String inputFile = "input.xml";
        String outputFile = "output.xml";
        String prunedFile = "pruned.xml";

        // Element to prune (container name)
        String elementToPrune = "containerToPrune";

        XMLStreamReader reader = null;
        XMLStreamWriter outputWriterXML = null;
        XMLStreamWriter prunedWriterXML = null;

        try {
            // Create XML input factory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();

            // Create readers and writers within try-with-resources to ensure proper resource cleanup
            reader = inputFactory.createXMLStreamReader(new FileReader(inputFile));
            outputWriterXML = XMLOutputFactory.newInstance().createXMLStreamWriter(new FileWriter(outputFile));
            prunedWriterXML = XMLOutputFactory.newInstance().createXMLStreamWriter(new FileWriter(prunedFile));

            // Flags to track whether to write to output or pruned file
            boolean writeToOutput = true;

            while (reader.hasNext()) {
                int event = reader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        String elementName = reader.getLocalName();
                        if (elementName.equals(elementToPrune)) {
                            // Found the element to prune, set flag to write to the pruned file
                            writeToOutput = false;
                        }
                        
                        // Handle attributes
                        for (int i = 0; i < reader.getAttributeCount(); i++) {
                            String attributeName = reader.getAttributeLocalName(i);
                            String attributeValue = reader.getAttributeValue(i);
                            
                            // Process attribute as needed
                            // Here, you can choose to write attributes to either the output or pruned file
                            if (writeToOutput) {
                                outputWriterXML.writeAttribute(attributeName, attributeValue);
                            } else {
                                prunedWriterXML.writeAttribute(attributeName, attributeValue);
                            }
                        }
                        
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        elementName = reader.getLocalName();
                        if (elementName.equals(elementToPrune)) {
                            // Finished pruning the element, reset flag to write to the output file
                            writeToOutput = true;
                        }
                        break;

                    case XMLStreamConstants.START_DOCUMENT:
                    case XMLStreamConstants.END_DOCUMENT:
                        // Do nothing for document start and end
                        break;

                    default:
                        // Write to the appropriate file based on the flag
                        if (writeToOutput) {
                            outputWriterXML.writeCharacters(reader.getText());
                        } else {
                            prunedWriterXML.writeCharacters(reader.getText());
                        }
                }
            }

            System.out.println("XML pruning complete.");
        } catch (XMLStreamException | IOException e) {
            // Handle exceptions related to XML processing or file I/O
            e.printStackTrace();
        } finally {
            // Close readers and writers in the finally block to ensure resource cleanup
            try {
                if (reader != null) reader.close();
                if (outputWriterXML != null) outputWriterXML.close();
                if (prunedWriterXML != null) prunedWriterXML.close();
            } catch (XMLStreamException | IOException e) {
                // Handle any exceptions that may occur during closing
                e.printStackTrace();
            }
        }
    }
}

package net.osandman.util.parsing.xml_example;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// Jakarta EE packages
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class XmlParse {
    private Document document;
    private static final String[] escapeSymbols = {"<", ">", "'", "\"", "&"};

    public String addComments(Object obj, String tagName, String comment) {
        String xmlStr = convertObjectToXml(obj, false);
        document = getDocument(xmlStr);

        addCdataBlocks(document.getDocumentElement());

        NodeList nodeList = document.getElementsByTagName(tagName);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Comment documentComment = document.createComment(comment);
            documentComment.normalize();
            Node item = nodeList.item(i);
            item.getParentNode().insertBefore(documentComment, item);
        }
        return transformDocToXml();
    }

    public String convertObjectToXml(Object obj, boolean formatted) {
        StringWriter writer = new StringWriter();
        try {
            Marshaller marshaller = JAXBContext.newInstance(obj.getClass()).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
            marshaller.marshal(obj, writer);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

    private void addCdataBlocks(Node rootElement) {
        if (rootElement.hasChildNodes()) {
            NodeList childNodes = rootElement.getChildNodes();
            int length = childNodes.getLength();
            for (int i = 0; i < length; i++) {
                addCdataBlocks(childNodes.item(i));
            }
        } else {
            String textContent = rootElement.getTextContent();
            if (containsEscapeSymbols(textContent)) {
                rootElement.setTextContent("");
                rootElement.getParentNode().appendChild(document.createCDATASection(textContent));
            }
        }
    }

    private static boolean containsEscapeSymbols(String inputStr) {
        if (inputStr != null && !inputStr.isEmpty()) {
            for (String character : escapeSymbols) {
                if (inputStr.contains(character))
                    return true;
            }
        }
        return false;
    }
    private Document getDocument(String xmlStr) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        try {
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            return documentBuilder.parse(new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8)));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String transformDocToXml() {
        StringWriter writer = new StringWriter();
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            transformer.transform(new DOMSource(document), new StreamResult(writer));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return writer.toString();
    }

}

package net.osandman.util.parsing.xml_example;

public class Main {
    public static void main(String[] args) {
        TestForXml testForXml = new TestForXml();
        testForXml.list.add("HEllo1");
        testForXml.list.add("HEllo2");
        testForXml.list.add("<![CDATA[this is CDATA string '<&>']]>");
        testForXml.list.add("HEllo3");
        testForXml.list1.add("!!!!");
        testForXml.list1.add("????");

        XmlParse xmlParse = new XmlParse();

        System.out.println(xmlParse.convertObjectToXml(testForXml, true));
        System.out.println(xmlParse.addComments(testForXml, "test", "its comment"));

    }

}

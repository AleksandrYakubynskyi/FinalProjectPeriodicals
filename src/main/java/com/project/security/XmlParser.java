package com.project.security;

import com.project.entity.enums.Role;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class XmlParser {
    private static final Logger LOGGER = Logger.getLogger(XmlParser.class);

    private static final String FILE_NAME = "webSecurityConstraints.xml";
    private static final String CONSTRAINT = "constraint";
    private static final String URL_PATTERN = "url-pattern";
    private static final String ROLE = "role";

    public static ArrayList<Constraint> parse() {
        ArrayList<Constraint> constraints = new ArrayList<>();

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(XmlParser.class.getClassLoader().getResourceAsStream(FILE_NAME));

            Node root = document.getDocumentElement();

            NodeList xmlConstraints = root.getChildNodes();
            for (int i = NumberUtils.INTEGER_ZERO; i < xmlConstraints.getLength(); i++) {
                Node xmlConstraint = xmlConstraints.item(i);

                if (xmlConstraint.getNodeType() != Node.TEXT_NODE && xmlConstraint.getNodeName().equals(CONSTRAINT)) {
                    NodeList properties = xmlConstraint.getChildNodes();
                    Constraint constraint = new Constraint();

                    for (int j = NumberUtils.INTEGER_ZERO; j < properties.getLength(); j++) {
                        Node property = properties.item(j);

                        if (property.getNodeType() != Node.TEXT_NODE) {
                            String name = property.getNodeName();
                            String value = property.getChildNodes().item(NumberUtils.INTEGER_ZERO).getTextContent();

                            if (URL_PATTERN.equals(name)) {
                                constraint.setUrlPattern(value);
                            } else if (ROLE.equals(name)) {
                                Role role = Role.valueOf(value);
                                constraint.addRole(role);
                            }
                        }
                    }
                    constraints.add(constraint);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            LOGGER.error(ExceptionUtils.getMessage(e));
            System.out.println(e.getMessage());
        }
        return constraints;
    }
}

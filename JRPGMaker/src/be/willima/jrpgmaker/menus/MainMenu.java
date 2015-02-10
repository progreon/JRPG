/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.menus;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author marco
 */
public class MainMenu extends JMenuBar {
    
    /**
     * Creates the main menu from an XML-file.
     *
     * @param mainMenuXML The XML-file which describes the menu
     */
    public MainMenu(String mainMenuXML) {
        try {
            parseXMLToMenu(mainMenuXML);
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parseXMLToMenu(String mainMenuXML) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(mainMenuXML));

        Node menuBarNode = document.getElementsByTagName("menubar").item(0);
        NodeList menus = menuBarNode.getChildNodes();
        int l = menus.getLength();
        for (int i = 0; i < l; i++) {
            Node menu = menus.item(i);
            if (menu.getNodeName().equals("menu")) {
                this.add(createMenuFromNode(menu));
            }
        }
    }

    private JMenu createMenuFromNode(Node menuNode) {
        String menuName = menuNode.getAttributes().getNamedItem("name").getTextContent();
        System.out.println("creating menu from node: " + menuNode.getNodeName() + ": " + menuName);
        JMenu menu = new JMenu(menuName);
        NodeList menuItems = menuNode.getChildNodes();
        int l = menuItems.getLength();
        for (int i = 0; i < l; i++) {
            Node menuItem = menuItems.item(i);
            if (menuItem.getNodeName().equals("menu")) {
                menu.add(createMenuFromNode(menuItem));
            } else if (menuItem.getNodeName().equals("action")) {
                String actionClass = menuItem.getTextContent();
                System.out.println("action class: " + actionClass);
                try {
                    Action action = (Action) ClassLoader.getSystemClassLoader().loadClass(actionClass).newInstance();
                    menu.add(action);
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
                    Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (menuItem.getNodeName().equals("separator")) {
                menu.add(new JSeparator());
            }
        }

        return menu;
    }

}

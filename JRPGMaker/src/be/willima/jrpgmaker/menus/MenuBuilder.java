/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.willima.jrpgmaker.menus;

import be.willima.jrpgdatabase.JRPGDao;
import be.willima.jrpgmaker.JRPGMakerS;
import be.willima.jrpgmaker.actions.JRPGAction;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public final class MenuBuilder {
    
    private final JRPGMakerS makerFrame;

    public MenuBuilder(JRPGMakerS makerFrame) {
        this.makerFrame = makerFrame;
    }
    
    /**
     * TODO
     *
     * @param XmlFileURI
     * @return
     */
    public JMenuBar createMenuBar(String XmlFileURI) {
        JMenuBar menuBar = null;
        try {
            menuBar = parseXMLToMenuBar(getClass().getResourceAsStream(XmlFileURI));
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            Logger.getLogger(MenuBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return menuBar;
    }
    
    /**
     * TODO
     *
     * @param XmlFileURI
     * @return
     */
    public JMenu createMenu(String XmlFileURI) {
        JMenu menu = null;
        try {
            menu =  parseXMLToMenu(getClass().getResourceAsStream(XmlFileURI));
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            Logger.getLogger(MenuBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return menu;
    }

    private JMenuBar parseXMLToMenuBar(InputStream menuBarXML) throws ParserConfigurationException, IOException, SAXException {
        JMenuBar menuBar = new JMenuBar();
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(menuBarXML);

        Node menuBarNode = document.getElementsByTagName("menubar").item(0);
        NodeList menus = menuBarNode.getChildNodes();
        int l = menus.getLength();
        for (int i = 0; i < l; i++) {
            Node menu = menus.item(i);
            if (menu.getNodeName().equals("menu")) {
                menuBar.add(createMenuFromNode(menu));
            }
        }
        
        return menuBar;
    }
    
    private JMenu parseXMLToMenu(InputStream menuXML) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(menuXML);
        
        Node menuNode = document.getElementsByTagName("menu").item(0);
        JMenu menu = createMenuFromNode(menuNode);
        return menu;
    }

    private JMenu createMenuFromNode(Node menuNode) {
        String menuName = menuNode.getAttributes().getNamedItem("name").getTextContent();
//        System.out.println("creating menu from node: " + menuNode.getNodeName() + ": " + menuName);
        JMenu menu = new JMenu(menuName);
        NodeList menuItems = menuNode.getChildNodes();
        int l = menuItems.getLength();
        for (int i = 0; i < l; i++) {
            Node menuItem = menuItems.item(i);
            if (menuItem.getNodeName().equals("menu")) {
                menu.add(createMenuFromNode(menuItem));
            } else if (menuItem.getNodeName().equals("action")) {
                String actionClass = menuItem.getTextContent();
//                System.out.println("action class: " + actionClass);
                try {
                    ClassLoader cl = ClassLoader.getSystemClassLoader();
                    Class c = cl.loadClass(actionClass);
                    Constructor constr = c.getConstructor(JRPGMakerS.class);
                    JRPGAction action = (JRPGAction) constr.newInstance(makerFrame);
                    menu.add(action);
                } catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(MenuBuilder.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (menuItem.getNodeName().equals("separator")) {
                menu.add(new JSeparator());
            }
        }

        return menu;
    }
}

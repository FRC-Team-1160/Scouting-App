/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.scouting.frontend.panels;

import com.team1160.scouting.frontend.elements.JumpMenuItem;
import com.team1160.scouting.frontend.elements.MultiLineInputElement;
import com.team1160.scouting.frontend.elements.NumberDropDownElement;
import com.team1160.scouting.frontend.elements.ScoutingElement;
import com.team1160.scouting.frontend.elements.SingleLineInputElement;
import com.team1160.scouting.frontend.elements.WeightingSliderElement;
import com.team1160.scouting.frontend.resourcePackets.CardLayoutPacket;
import com.team1160.scouting.h2.CommentTable;
import com.team1160.scouting.h2.DictTable;
import com.team1160.scouting.h2.MatchScoutingTable;
import com.team1160.scouting.h2.WeightingTable;
import com.team1160.scouting.xml.XMLParser;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author sakekasi
 */
public class MatchScoutingPanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5439413188323739057L;

	protected JPanel bottomPanel;
    
    protected ArrayList<ScoutingElement> elements;
    protected JPanel elementPanel;
    protected JPanel elementInputPanel;
    protected JPanel elementButtonPanel;
    protected Border elementBorder;


    protected ArrayList<WeightingSliderElement> weightingElements;
    protected JPanel weightingPanel;
    protected JPanel weightingInputPanel;
    protected JPanel weightingButtonPanel;
    protected Border weightingBorder;


    protected JMenuBar menubar;

    protected JMenu go;

    protected CardLayoutPacket layout;

    protected MatchScoutingTable scoutingTable;
    protected WeightingTable weightingTable;
    protected DictTable dictTable;
    protected CommentTable commentTable;


    public MatchScoutingPanel(CardLayoutPacket layout, MatchScoutingTable scouting,
        WeightingTable weighting, DictTable dict,
        CommentTable comment) throws ParserConfigurationException, SAXException, IOException, Exception {
        this.layout = layout;

        this.elements = new ArrayList<ScoutingElement>();
        this.weightingElements = new ArrayList<WeightingSliderElement>();

        this.setLayout(new BorderLayout());

        XML xml = this.new XML();
        this.scoutingTable = scouting;
        this.weightingTable = weighting;
        this.dictTable = dict;
        this.commentTable = comment;
        

        this.menubar = new JMenuBar();
        this.menubar.setLayout(new BorderLayout());

        this.go = new JMenu("Go");
        this.go.setMnemonic(KeyEvent.VK_G);
        this.go.add(new JumpMenuItem(layout,"Graph","graph"));
        this.go.add(new JumpMenuItem(layout,"Comments","comment"));
//        this.go.add(new JumpMenuItem(layout,"Pit Scouting","pit"));
        
        this.menubar.add(this.go, BorderLayout.WEST);
//        this.toolbar.add(this.graph, BorderLayout.EAST);

        this.bottomPanel = new JPanel();
        this.bottomPanel.setLayout(new GridLayout(1,2,0,0));

        xml.parse();

        this.elementPanel = new JPanel();
        this.elementInputPanel = new JPanel();
        this.elementBorder = BorderFactory.createTitledBorder("Values");
        this.elementInputPanel.setLayout(new GridLayout(this.elements.size(),1,0,0));
        this.elementPanel.setBorder(this.elementBorder);
        this.elementPanel.setLayout(new BorderLayout());

        this.weightingPanel = new JPanel();
        this.weightingInputPanel = new JPanel();
        this.weightingBorder = BorderFactory.createTitledBorder("Weighting");
        this.weightingPanel.setBorder(this.weightingBorder);
        this.weightingInputPanel.setLayout(new GridLayout(this.weightingElements.size(),1,0,0));
        this.weightingPanel.setLayout(new BorderLayout());

        

        for(ScoutingElement se: this.elements){
            this.elementInputPanel.add(se);
        }

        for(WeightingSliderElement w: this.weightingElements){
            this.weightingInputPanel.add(w);
        }

        JButton elementSubmit=new JButton("submit"),elementClear = new JButton("reset");
        elementSubmit.addActionListener(this.new ElementSubmit());
        elementClear.addActionListener(this.new ElementClear());
        JButton weightingSubmit = new JButton("submit"), weightingClear = new JButton("reset");
        weightingSubmit.addActionListener(this.new WeightingSubmit());
        weightingClear.addActionListener(this.new WeightingClear());

        this.elementButtonPanel = new JPanel();
//        this.elementButtonPanel.setBackground(Color.red);

        this.weightingButtonPanel = new JPanel();
//        this.weightingButtonPanel.setLayout(new GridLayout(1,2));
//        this.weightingButtonPanel.setBackground(Color.red);

        this.elementButtonPanel.add(elementSubmit);
        this.elementButtonPanel.add(elementClear);
        this.weightingButtonPanel.add(weightingSubmit);
        this.weightingButtonPanel.add(weightingClear);

        this.elementPanel.add(new JScrollPane(this.elementInputPanel), BorderLayout.CENTER);
        this.elementPanel.add(this.elementButtonPanel, BorderLayout.SOUTH);

        this.weightingPanel.add(new JScrollPane(this.weightingInputPanel), BorderLayout.CENTER);
        this.weightingPanel.add(this.weightingButtonPanel, BorderLayout.SOUTH);

        this.bottomPanel.add(this.elementPanel);
        this.bottomPanel.add(this.weightingPanel);
        
        this.add(this.menubar, BorderLayout.NORTH);
        this.add(this.bottomPanel, BorderLayout.CENTER);

        

    }


    class XML{
        XMLParser xml;

        protected XML() throws ParserConfigurationException, SAXException, IOException{
            xml = new XMLParser("config.xml");
        }

        protected void parse() throws Exception{
            Element e = xml.getMatch();
            elements.add(new SingleLineInputElement("Team No:", SingleLineInputElement.INTEGER));
            SingleLineInputElement match = new SingleLineInputElement("Match No:", SingleLineInputElement.INTEGER);
            match.setInComments(true);
            elements.add(match);
            NodeList element = e.getElementsByTagName("field");
            MatchScoutingPanel.this.dictTable.reset();
            for(int i=0;i<element.getLength();i++){
                Element el = (Element) element.item(i);
                String type=el.getAttribute("type");
                String name=el.getAttribute("name");
                @SuppressWarnings("unused")
				String inWeight = el.getAttribute("inWeight");
                if(type.equalsIgnoreCase("sIntInput")){
                    SingleLineInputElement se;
                    if(  (el.hasAttribute("isNegative")) &&
                            el.getAttribute("isNegative").equals("true")){
                         se = new SingleLineInputElement(name,
                            SingleLineInputElement.INTEGER,
                            true);
                         
                    } else if(  ((el.hasAttribute("isTime"))) &&
                            el.getAttribute("isTime").equals("true")){
                        int maxTime = Integer.parseInt(el.getAttribute("maxTime"));
                        se = new SingleLineInputElement(name,
                                SingleLineInputElement.INTEGER,
                                false,
                                true,
                                maxTime);
                    }else {
                         se = new SingleLineInputElement(name,
                            SingleLineInputElement.INTEGER);
                    }

                    if((!(el.hasAttribute("inWeight"))) ||
                            (el.getAttribute("inWeight")).equalsIgnoreCase("true")){
                        weightingElements.add(new WeightingSliderElement(name, se.hashCode()));
                        weightingElements.get(weightingElements.size()-1).setValue(
                                MatchScoutingPanel.this.weightingTable.getValue(
                                weightingElements.get(weightingElements.size()-1).hashCode()));
                        MatchScoutingPanel.this.dictTable.insert(se.hashCode(), se.getText());

                    }
                    
                    se.setAlignmentX(Component.LEFT_ALIGNMENT);
                    elements.add(se);
                } else if(type.equalsIgnoreCase("numDropDown")){
                    NumberDropDownElement ne=new NumberDropDownElement(name,
                        Integer.parseInt(el.getAttribute("bottom")),
                        Integer.parseInt(el.getAttribute("top")));
                    if((!(el.hasAttribute("inWeight"))) ||
                            (el.getAttribute("inWeight")).equalsIgnoreCase("true")){
                        weightingElements.add(new WeightingSliderElement(name, ne.hashCode()));
                        weightingElements.get(weightingElements.size()-1).setValue(
                                MatchScoutingPanel.this.weightingTable.getValue(
                                weightingElements.get(weightingElements.size()-1).hashCode()));
                        MatchScoutingPanel.this.dictTable.insert(ne.hashCode(), ne.getText());
                    }
                    ne.setAlignmentX(Component.LEFT_ALIGNMENT);
                    elements.add(ne);
                } else {
                    throw new Exception("wrong type");
                }

            }

            MultiLineInputElement comments = new MultiLineInputElement("Comments:");
            comments.setInComments(true);
            elements.add(comments);
            

        }

        
    }

    class ElementSubmit implements ActionListener{

        public void actionPerformed(ActionEvent ae) {
            ArrayList<String> values = new ArrayList<String>();
            boolean error=false;
            for(ScoutingElement e: MatchScoutingPanel.this.elements){
                if(e.getClass()== SingleLineInputElement.class){
                    SingleLineInputElement slie = (SingleLineInputElement) e;
                    if(!slie.isNegative()){
                        error = slie.getInput().startsWith("-");
                    }
                    if(slie.isTime()){
                        try{
                            int i = Integer.parseInt(slie.getInput());
                            error = (i>slie.getMaxTime())||(i<=0);

                        } catch(NumberFormatException nfe){
                            error = true;
                        }
                    }
                }
                values.add(e.getInput());
            }
            for(String s:values){
                if(!s.contains("\n")){
                    try{
                        Integer.parseInt(s);
                    }catch(NumberFormatException nfe){
                        error = true;
                    }
                }
            }
            if(error){

                JOptionPane.showMessageDialog(MatchScoutingPanel.this,
                         "Wrong Input",
                         "Input Error",
                         JOptionPane.ERROR_MESSAGE);
            } else {
                for(int i=2; i<(MatchScoutingPanel.this.elements.size()-1);i++){
                    try {
                        MatchScoutingPanel.this.scoutingTable.insert(Integer.parseInt(values.get(0)), MatchScoutingPanel.this.elements.get(i).hashCode(), Integer.parseInt(values.get(i)));
                    } catch (SQLException ex) {
                        Logger.getLogger(MatchScoutingPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                	if(values.get(values.size()-1).matches(".*\\w.*"))
                		MatchScoutingPanel.this.commentTable.insert(Integer.parseInt(values.get(0)),
                				Integer.parseInt(values.get(1)),
                				values.get(values.size()-1));
                } catch (SQLException ex) {
                    Logger.getLogger(MatchScoutingPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                for(ScoutingElement s: MatchScoutingPanel.this.elements){
                    s.clear();
                }

            }
        }

    }

        class ElementClear implements ActionListener{

            public void actionPerformed(ActionEvent ae) {
                for(ScoutingElement s: MatchScoutingPanel.this.elements){
                    s.clear();
                }
            }

        }

        class WeightingSubmit implements ActionListener{

            public void actionPerformed(ActionEvent ae) {
            try {
                MatchScoutingPanel.this.weightingTable.reset();
            } catch (SQLException ex) {
                Logger.getLogger(MatchScoutingPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
                for(WeightingSliderElement e:MatchScoutingPanel.this.weightingElements){
                    try {
                        MatchScoutingPanel.this.weightingTable.insert(e.hashCode(), Integer.parseInt(e.getInput()));
                    } catch (SQLException ex) {
                        Logger.getLogger(MatchScoutingPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

        class WeightingClear implements ActionListener{

            public void actionPerformed(ActionEvent ae) {
                
                for(WeightingSliderElement e:MatchScoutingPanel.this.weightingElements){
                try {
                    e.setValue(MatchScoutingPanel.this.weightingTable.getValue(e.hashCode()));
                } catch (SQLException ex) {
                    Logger.getLogger(MatchScoutingPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            }
           
        }


}



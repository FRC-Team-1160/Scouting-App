

package com.team1160.scouting.frontend.elements;

import java.awt.Component;
import javax.swing.JPanel;

/**
 * abstract superclass for all xml-generated elements in the app.
 * @author Saketh Kasibatla
 */
public abstract class ScoutingElement extends JPanel{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6010761707838944507L;
	/**
     * whether or not the element is weighted
     */
    public boolean isWeighted;

    /**
     * default superconstructor for all scouting elements
     */
    public ScoutingElement(){
        this.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    /**
     * gets the input value of the element
     * @return the string value of the input
     */
    public abstract String getInput();

    /**
     * gets the name of the element
     * @return the string name of the element
     */
    public abstract String getText();
    
    /**
     * resets the element to its default state
     */
    public abstract void clear();
}

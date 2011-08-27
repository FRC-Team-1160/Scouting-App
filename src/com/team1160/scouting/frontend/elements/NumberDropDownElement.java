package com.team1160.scouting.frontend.elements;

import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JLabel;

/**
 * A scouting element with a dropdown box which contains numbers for the user to choose from.
 * @author Saketh Kasibatla
 */
public class NumberDropDownElement extends ScoutingElement{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3243698735620782166L;

	/**
     * the label that displays the name of the scouting element
     */
    private JLabel label;
    
    /**
     * the input dropdown box.
     */
    private JComboBox dropDown;

    /**
     * the lowest and highest numbers in the dropdown box.
     */
    private int bottom,top;

    /**
     * the name that is displayed in the jlabel
     */
    private String name;

    /**
     * Creates a new NumberDropDownElement.
     * @param name the name of the new element
     * @param bottom the lowest number in the dropdown box
     * @param top the highest element in the dropdown box.
     */
    public NumberDropDownElement(String name, int bottom, int top) {
        super();
        if(!(name.endsWith(":"))){
            label=new JLabel(name+":");
            this.name=name+":";
        }
        else{
            label=new JLabel(name);
            this.name=name;
        }
        this.bottom=bottom;
        this.top=top;
        Vector<Integer> items = new Vector<Integer>();
        for(int i=bottom;i<=top;i++){
            items.add(i);
        }
        this.dropDown=new JComboBox(items);
        
        this.add(this.label);
        this.add(this.dropDown);
        
    }

    /**
     *
     * @return the text of the label
     */
    public String getText(){
        return this.label.getText().substring(0,this.label.getText().length());
    }

    /**
     *
     * @return the input in the input field
     */
    public String getInput(){
        return this.dropDown.getSelectedItem().toString();
    }

    /**
     * tests if this element is equal to another object.
     * @param obj the object to test equality with
     * @return wether or not this equals obj
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NumberDropDownElement other = (NumberDropDownElement) obj;
        if (this.bottom != other.bottom) {
            return false;
        }
        if (this.top != other.top) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * hash code method. used for identification purposes.
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.bottom;
        hash = 97 * hash + this.top;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    /**
     * resets this element.
     */
    @Override
    public void clear() {
        this.dropDown.setSelectedItem(new Integer(1));
    }

    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.scouting.frontend.elements;

/**
 *
 * @author Saketh Kasibatla
 */



import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Saketh Kasibatla
 */
public class MultiLineInputElement extends ScoutingElement{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1672197929657009689L;
	private JLabel label;
    private JScrollPane scrolling;
    private JTextArea input;
    String name;
    protected boolean inComments;


    public MultiLineInputElement(String name) {
        super();
        this.inComments=false;
        if(!(name.endsWith(":"))){
            label=new JLabel(name+":");
            this.name=name+":";
        }
        else{
            label=new JLabel(name);
            this.name=name;
        }
        this.input = new JTextArea();
        this.scrolling = new JScrollPane(this.input);
        this.setLayout(new BorderLayout());

        this.label.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.input.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(label,BorderLayout.WEST);
        this.add(scrolling,BorderLayout.CENTER);
        
    }

    public String getText(){
        return this.label.getText();
    }

    public String getInput(){
        return this.input.getText()+"\n";
    }

    public boolean isInComments() {
        return inComments;
    }

    public void setInComments(boolean inComments) {
        this.inComments = inComments;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MultiLineInputElement other = (MultiLineInputElement) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public void clear() {
        this.input.setText("");
    }


}

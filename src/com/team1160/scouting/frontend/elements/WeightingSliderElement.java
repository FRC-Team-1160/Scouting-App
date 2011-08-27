/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.scouting.frontend.elements;

import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 *
 * @author Saketh Kasibatla
 */
public class WeightingSliderElement extends ScoutingElement{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5453773753985194913L;

	/**
     * label with slider's name
     */
    private JLabel label;

    /**
     * actual slider
     */
    private JSlider slider;

    /**
     * the name that is displayed in the jlabel
     */
    private String name;

    private int hashCode;



    public WeightingSliderElement(String name, int hashCode) {
        super();

        if(!(name.endsWith(":"))){
            label=new JLabel(name+":");
            this.name=name+":";
        }
        else{
            label=new JLabel(name);
            this.name=name;
        }
        this.slider=new JSlider();
        this.slider.setMaximum(100);
        this.slider.setMinimum(0);
        this.slider.setMajorTickSpacing(10);
        this.slider.setMinorTickSpacing(2);
        this.slider.setPaintTicks(true);
        this.slider.setSnapToTicks(true);
        this.slider.setPaintLabels(true);
        
        this.add(label);
        this.add(slider);
        this.hashCode = hashCode;
    }



    @Override
    public String getInput() {
        int value=this.slider.getValue();
        return Integer.toString(value);
    }

    @Override
    public String getText() {
        return this.label.getText();
    }

    @Override
    public void clear() {
        this.slider.setValue(50);
    }

    public void setValue(int n) {
        slider.setValue(n);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WeightingSliderElement other = (WeightingSliderElement) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }



}

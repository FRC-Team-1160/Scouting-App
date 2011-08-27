

package com.team1160.scouting.frontend.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.team1160.scouting.frontend.resourcePackets.CardLayoutPacket;
import javax.swing.JMenuItem;

/**
 * This class is used with a card layout to go to the previous card in the layout.
 * @author Saketh Kasibatla
 */
public class PrevMenuItem extends JMenuItem implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2554005647915486553L;
	/**
     * Contains the card layout and its parent.
     */
    CardLayoutPacket layout;

    /**
     * creates a PrevButton with text name.
     * @param layout a CardLayoutPacket whose cards the button will flip thru.
     * @param name the text displayed on the button.
     */
    public PrevMenuItem(CardLayoutPacket layout, String name) {
        super(name);
        this.layout = layout;
        this.addActionListener(this);

    }

    /**
     * creates a generic PrevButton with the name "prev"
     * @param layout a CardLayoutPacket whose cards the button will flip thru.
     */
    public PrevMenuItem(CardLayoutPacket layout) {
        super("prev");
        this.layout = layout;
        this.addActionListener(this);
    }

    /**
     * when the button is clicked, will go to the previous card.
     * @param e the actionevent
     */
    public void actionPerformed(ActionEvent e) {
        //cardlayout.previous(parent);
        this.layout.getLayout().previous(this.layout.getParent());
    }

}



package com.team1160.scouting.frontend.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import com.team1160.scouting.frontend.resourcePackets.CardLayoutPacket;

/**
 * This class is used with a card layout to go to the previous card in the layout.
 * @author Saketh Kasibatla
 */
public class PrevButton extends JButton implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7947534100341629833L;
	/**
     * Contains the card layout and its parent.
     */
    CardLayoutPacket layout;

    /**
     * creates a PrevButton with text name.
     * @param layout a CardLayoutPacket whose cards the button will flip thru.
     * @param name the text displayed on the button.
     */
    public PrevButton(CardLayoutPacket layout, String name) {
        super(name);
        this.layout = layout;
        this.addActionListener(this);

    }

    /**
     * creates a generic PrevButton with the name "prev"
     * @param layout a CardLayoutPacket whose cards the button will flip thru.
     */
    public PrevButton(CardLayoutPacket layout) {
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

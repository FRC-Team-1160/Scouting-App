
package com.team1160.scouting.frontend.elements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import com.team1160.scouting.frontend.resourcePackets.CardLayoutPacket;

/**
 * A button like NextButton or PrevButton , but to go to a specific slide.
 * @author Saketh Kasibatla
 */
public class JumpButton extends JButton implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3520384163398358578L;

	/**
     * Contains the card layout and its parent.
     */
    CardLayoutPacket layout;

    /**
     * the name of the slide that the JumpButton goes to.
     */
    String slideName;

    /**
     * creates a JumpButton with text name.
     * @param layout a CardLayoutPacket whose cards the button will flip thru.
     * @param text the text displayed on the button.
     * @param slideName the name of the slide that the button will jump to
     */
    public JumpButton(CardLayoutPacket layout,String text,  String slideName) {
        super(text);
        this.layout = layout;
        this.slideName = slideName;
        this.addActionListener(this);
    }
    /**
     * when the button is clicked, will go to the indicated card.
     * @param e the actionevent
     */
    public void actionPerformed(ActionEvent e) {
        this.layout.getLayout().show(this.layout.getParent(),slideName);
    }



}

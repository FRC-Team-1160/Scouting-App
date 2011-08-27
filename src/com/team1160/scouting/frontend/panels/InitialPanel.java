package com.team1160.scouting.frontend.panels;

import com.team1160.scouting.frontend.elements.JumpMenuItem;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.team1160.scouting.frontend.resourcePackets.CardLayoutPacket;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 * the splash panel of the app.
 * only has an image and a next button.<br><br><br>
 * 
 *
 *
 * @author Saketh Kasibatla
 */
public class InitialPanel extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1628999285286377291L;

	/**
     * the toolbar displayed at the top.
     */
    JMenuBar toolbar;

    /**
     * the panel containing the pictures under the toolbar
     */
    JPanel picturepanel;

    /**
     * the layout packet containing the cardlayout for the next button.
     */
    CardLayoutPacket layout;

    JMenu go;


    /**
     * creates a new splash panel.
     * @param layout the layout packet to add to the button.
     */
    public InitialPanel(CardLayoutPacket layout) {
        this.layout = layout;
        this.setLayout(new BorderLayout());

        toolbar = new JMenuBar();
        toolbar.setLayout(new BorderLayout());
        this.go = new JMenu("Go");
        this.go.setMnemonic(KeyEvent.VK_G);
        this.go.add(new JumpMenuItem(layout,"Match Scouting","match"));
        this.go.add(new JumpMenuItem(layout,"Graph","graph"));
        this.go.add(new JumpMenuItem(layout,"Comments","comment"));
//        this.go.add(new JumpMenuItem(layout,"Pit Scouting","pit"));
        
        toolbar.add(this.go,BorderLayout.WEST);
        this.add(toolbar,BorderLayout.NORTH);

        picturepanel=new JPanel();
        ImageIcon image = new ImageIcon("image.png");
        
        picturepanel.add(new JLabel(image/*"1160 Scouting App"*/));
        picturepanel.setOpaque(true);
        this.add(picturepanel,BorderLayout.CENTER);

        this.setPreferredSize(new Dimension(800,600));
    }


}

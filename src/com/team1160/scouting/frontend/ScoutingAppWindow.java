package com.team1160.scouting.frontend;

import com.team1160.scouting.frontend.panels.CommentPanel;
import com.team1160.scouting.frontend.panels.GraphPanel;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.team1160.scouting.frontend.panels.InitialPanel;
import com.team1160.scouting.frontend.panels.MatchScoutingPanel;
import com.team1160.scouting.frontend.resourcePackets.CardLayoutPacket;
import com.team1160.scouting.h2.CommentTable;
import com.team1160.scouting.h2.DictTable;
import com.team1160.scouting.h2.MatchScoutingTable;
import com.team1160.scouting.h2.WeightingTable;
import java.io.File;

/**
 * The actual JFrame that contains the application
 * @author Saketh Kasibatla
 */
public class ScoutingAppWindow extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6088160965155260964L;


	/**
     * the splash pane which displays the FIRST logo and 1160 logo
     */
    private InitialPanel splash;


    private MatchScoutingPanel match;


    /**
     * Contains the card layout and its parent.
     */
    private CardLayoutPacket layout;

    /**
     * the panel upon which the cardlayout will change panels.
     */
    private JPanel cards;

    private GraphPanel graph;

    private CommentPanel comment;

    /**
     * makes a new window
     * @param title the title of the window
     * @throws HeadlessException
     */

    private MatchScoutingTable scoutingTable;
    private WeightingTable weightingTable;
    private DictTable dictTable;
    private CommentTable commentTable;


    public ScoutingAppWindow(String title) throws Exception{
        super(title);

        this.scoutingTable = new MatchScoutingTable(
                "."+File.separator+"data"+File.separator+"database");
        this.weightingTable = new WeightingTable(
                "."+File.separator+"data"+File.separator+"database");
        this.dictTable = new DictTable(
                "."+File.separator+"data"+File.separator+"database");
        this.commentTable = new CommentTable(
                "."+File.separator+"data"+File.separator+"database");


        this.cards=new JPanel();
        layout = new CardLayoutPacket(cards);
        cards.setLayout(this.layout.getLayout());
        splash = new InitialPanel(layout);
        splash.setOpaque(true);
        match = new MatchScoutingPanel(layout, this.scoutingTable, this.weightingTable, this.dictTable, this.commentTable);
        graph = new GraphPanel(layout, this.scoutingTable, this.weightingTable, this.dictTable);
        comment = new CommentPanel(layout, this.commentTable);
        cards.add(splash,"splashscreen");
        cards.add(match, "match");
        cards.add(graph, "graph");
        cards.add(comment, "comment");
        this.add(cards);
        


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(820,710);
        this.setVisible(true);
    }

    /**
     * creates a new window with the default title of 1160 Scouting App
     * @throws HeadlessException
     */
    public ScoutingAppWindow() throws Exception{
        this("1160 Scouting App");
    }


}

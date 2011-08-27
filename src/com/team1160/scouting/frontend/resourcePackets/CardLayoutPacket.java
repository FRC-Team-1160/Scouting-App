package com.team1160.scouting.frontend.resourcePackets;

import java.awt.CardLayout;
import java.awt.Container;

/**
 * contains a cardlayout and its container.
 * @author Saketh Kasibatla
 */
public class CardLayoutPacket {
    protected CardLayout layout;
    protected Container parent;

    public CardLayoutPacket(Container parent){
        layout=new CardLayout();
        this.parent=parent;
        

    }

    public CardLayout getLayout() {
        return layout;
    }

    public void setLayout(CardLayout layout) {
        this.layout = layout;
    }

    public Container getParent() {
        return parent;
    }

    public void setParent(Container parent) {
        this.parent = parent;
    }

}

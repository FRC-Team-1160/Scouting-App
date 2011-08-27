/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.scouting.frontend.panels;

import com.team1160.scouting.frontend.elements.JumpMenuItem;
import com.team1160.scouting.frontend.elements.MultiLineTableCellRenderer;
import com.team1160.scouting.frontend.resourcePackets.CardLayoutPacket;
import com.team1160.scouting.h2.CommentTable;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author sakekasi
 */
public class CommentPanel extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8884065626598316707L;
	JMenuBar menubar;
    JToolBar toolbar;
    JMenu go;

    private final JButton refresh;
    private final JButton deleteData;

    CommentTable commentTable;

    JTable table;
    JScrollPane scroll;

    CardLayoutPacket layout;

    public CommentPanel(CardLayoutPacket layout, CommentTable commentTable) throws SQLException {
        this.commentTable = commentTable;
        this.layout = layout;

        this.menubar = new JMenuBar();
        this.go = new JMenu("Go");
        this.go.setMnemonic(KeyEvent.VK_G);
        this.go.add(new JumpMenuItem(layout,"Match Scouting","match"));
        this.go.add(new JumpMenuItem(layout,"Graph","graph"));

        this.toolbar = new JToolBar();
        this.toolbar.setFloatable(false);

        this.deleteData = new JButton("Delete Data");
        this.deleteData.setMnemonic(KeyEvent.VK_D);
        this.deleteData.addActionListener(this.new DeleteData());
        this.deleteData.setSize(100, this.menubar.getHeight());
        this.toolbar.add(this.deleteData);

        this.refresh = new JButton("Refresh");
        this.refresh.setMnemonic(KeyEvent.VK_R);
        this.refresh.addActionListener(this.new Refresh());
        this.refresh.setSize(100, this.menubar.getHeight());
        this.toolbar.add(this.refresh);

        this.table = new JTable(new CommentTableModel(this.getData()));
        //this.table.setDefaultRenderer(Object.class, new MultiLineTableCellRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new MultiLineTableCellRenderer());
        this.scroll = new JScrollPane(this.table);

        this.setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        top.add(this.menubar);
        this.menubar.add(this.go, BorderLayout.NORTH);
        top.add(this.toolbar, BorderLayout.SOUTH);
        this.add(top, BorderLayout.NORTH);

        this.add(this.scroll, BorderLayout.CENTER);


    }


    public void refresh() throws SQLException{
        this.remove(this.scroll);
        this.table=null;
        this.scroll = null;
        this.table = new JTable(new CommentTableModel(this.getData()));
        table.getColumnModel().getColumn(2).setCellRenderer(new MultiLineTableCellRenderer());
        //this.table.setDefaultRenderer(Object.class, new MultiLineTableCellRenderer());
        this.scroll = new JScrollPane(this.table);
        this.add(this.scroll, BorderLayout.CENTER);
        this.validate();

    }

    protected ArrayList<ArrayList<String>> getData() throws SQLException{
        List<Integer> teams = this.commentTable.getTeams();
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        for(Integer t:teams){
            Map<Integer, String> comments = this.commentTable.getComments(t);
            for(Integer m:comments.keySet()){
                ArrayList<String> row = new ArrayList<String>();
                row.add(t.toString());
                row.add(m.toString());
                row.add(comments.get(m));
                data.add(row);
            }
        }

        return data;
    }

    class Refresh implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            try {
                CommentPanel.this.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    class DeleteData implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            Object[] options = {"Continue", "Gancel"};
            boolean contin = JOptionPane.showOptionDialog(
                         CommentPanel.this,
                         "Pressing \'Continue\' will permanently delete your data.",
                         "Warning",
                         JOptionPane.YES_NO_OPTION,
                         JOptionPane.ERROR_MESSAGE,
                         null,
                         options,
                         options[1])==0;

            if(contin){
                try {
                    CommentPanel.this.commentTable.reset();
                } catch (SQLException ex) {
                    Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    class CommentTableModel extends AbstractTableModel{
        /**
		 * 
		 */
		private static final long serialVersionUID = -8131695332102264265L;
		String columnNames[]={"Team #","Match #","Comments"};
        ArrayList<ArrayList<String>> data;

        public CommentTableModel(ArrayList<ArrayList<String>> data) {
            this.data = data;
        }



        public int getRowCount() {
            return this.data.size();
        }

        public int getColumnCount() {
            return this.columnNames.length;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            return this.data.get(rowIndex).get(columnIndex);
        }

        @Override
        public boolean isCellEditable(int row, int col){
            return false;
        }

        @Override
        public String getColumnName (int col) {
            return this.columnNames[col];
        }

        

    }

    
}

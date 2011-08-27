/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.scouting.frontend.elements;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer{

        /**
	 * 
	 */
	private static final long serialVersionUID = 913219999734482766L;



		public MultiLineTableCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            
        }



        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            }
            else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setFont(table.getFont());
            if (hasFocus) {
                setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
                if (table.isCellEditable(row, column)) {
                    setForeground(UIManager.getColor("Table.focusCellForeground"));
                    setBackground(UIManager.getColor("Table.focusCellBackground"));
                }
            }
            else {
                setBorder(new EmptyBorder(1, 2, 1, 2));
            }

            setText((value == null) ? "" : value.toString());

            int count=((String) value).split("\n").length;
            this.setPreferredSize(new Dimension(267,16*count));
            int height_wanted = (int)getPreferredSize().getHeight();
            if (height_wanted != table.getRowHeight(row))
                table.setRowHeight(row, height_wanted);
            return this;
        }

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.scouting.frontend.panels;

import com.team1160.scouting.frontend.elements.JumpMenuItem;
import com.team1160.scouting.frontend.resourcePackets.CardLayoutPacket;
import com.team1160.scouting.h2.DictTable;
import com.team1160.scouting.h2.MatchScoutingTable;
import com.team1160.scouting.h2.WeightingTable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.GroupedStackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author saketh
 */
public class GraphPanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8322774647630395987L;

	CardLayoutPacket layout;

    JMenuBar menubar;
    JToolBar toolbar;
    JMenu go;

    private final JButton refresh;
    private final JButton deleteData;

    MatchScoutingTable matchTable;
    WeightingTable weightingTable;
    DictTable dictTable;

    JFreeChart chart;
    ChartPanel chartPanel;
    JScrollPane scroll;
    @SuppressWarnings("unused")
	private final GroupedStackedBarRenderer renderer;
    final int preferredHeight = 600;
    Dimension preferredSize;
    

    public GraphPanel(CardLayoutPacket layout, MatchScoutingTable match, WeightingTable weight, DictTable dict) throws SQLException{
        this.matchTable = match;
        this.weightingTable = weight;
        this.dictTable = dict;

        this.menubar = new JMenuBar();
        this.go = new JMenu("Go");
        this.go.setMnemonic(KeyEvent.VK_G);
        this.go.add(new JumpMenuItem(layout,"Match Scouting","match"));
        //this.go.add(new JumpMenuItem(layout,"Pit Scouting","pit"));
        this.go.add(new JumpMenuItem(layout,"Comments","comment"));

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

        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        top.add(this.menubar);
        this.menubar.add(this.go, BorderLayout.NORTH);
        top.add(this.toolbar, BorderLayout.SOUTH);
        
        this.chart = ChartFactory.createStackedBarChart(
                "Team Rankings",
                "Team Number",
                "Score",
                this.createDataSet(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        this.renderer = new GroupedStackedBarRenderer();
        this.chartPanel = new ChartPanel(this.chart,
                    this.preferredSize.width,
                    this.preferredSize.height,
                    50,
                    0,
                    2000000,
                    2000000,
                    true,
                    true,
                    true,
                    false,
                    false,
                    true);
        this.scroll = new JScrollPane(this.chartPanel);
        this.setLayout(new BorderLayout());
        this.add(top, BorderLayout.NORTH);
        this.add(this.scroll, BorderLayout.CENTER);
        
        




    }

    protected CategoryDataset createDataSet() throws SQLException {
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        Map<String,Integer> dict;
        Map<Integer, String> reverseDict;
        List<Integer> teams;
        Map<Integer, Integer> weights;

        teams = this.matchTable.getTeams();
        dict = this.dictTable.getValuesName();
        reverseDict = this.dictTable.getValuesID();
        weights = this.weightingTable.getValues();
        if(weights.keySet().isEmpty()){
            weights = new LinkedHashMap<Integer, Integer>();
            for(Integer i:reverseDict.keySet()){
                weights.put(i,50);
            }
        }
        
        
        Map<Integer, Double> teamToOverallScore = new HashMap<Integer, Double>();
        Map<Integer, LinkedHashMap<String, Double>> teamToValues = new HashMap<Integer, LinkedHashMap<String, Double>>();
        ValueComparator vc = new ValueComparator(teamToOverallScore);
        @SuppressWarnings("unchecked")
		TreeMap<Integer, Double> teamSorted = new TreeMap<Integer, Double>(vc);

        for(Integer t:teams){
            LinkedHashMap<String, Double> values = new LinkedHashMap<String, Double>();
            for(String n:dict.keySet()){
                int value = this.matchTable.getAverageValue(t, dict.get(n));
                double weight = (weights.get(dict.get(n)));
                weight /=100;
                double weightedValue = value * weight;
                values.put(n, weightedValue);
//
            }
            double overallScore = 0;
            for(String s:values.keySet()){
                overallScore+=values.get(s);
            }
            teamToOverallScore.put(t, overallScore);
            teamToValues.put(t, values);
        }

        teamSorted.putAll(teamToOverallScore);
        for(Integer t:teamSorted.keySet()){
            for(String s:teamToValues.get(t).keySet()){
//                System.out.println(s);
//                System.out.println(t);
//                System.out.println(teamToValues.get(t).get(s));
                data.addValue(teamToValues.get(t).get(s), s.substring(0, s.length()-1), t.toString());
            }
        }
        this.preferredSize = new Dimension(this.getPreferredWidth(teams.size()), this.preferredHeight);
        return data;


    }

    public void refresh() throws SQLException{
        this.remove(this.scroll);
        this.chart = null;
        this.chartPanel = null;
        this.scroll = null;
        this.chart = ChartFactory.createStackedBarChart(
                "Team Rankings",
                "Team Number",
                "Score",
                this.createDataSet(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        this.chartPanel = new ChartPanel(this.chart,
                    this.preferredSize.width,
                    this.preferredSize.height,
                    50,
                    0,
                    2000000,
                    2000000,
                    true,
                    true,
                    true,
                    false,
                    false,
                    true);

        this.scroll = new JScrollPane(this.chartPanel);
        this.add(this.scroll, BorderLayout.CENTER);
        this.validate();
        this.scroll.validate();

    }

    

    @SuppressWarnings("rawtypes")
	class ValueComparator implements Comparator {

      Map<Integer, Double> base;
      public ValueComparator(Map<Integer, Double> base) {
          this.base = base;
      }

      public int compare(Object a, Object b) {

        if(base.get(a) < base.get(b)) {
          return 1;
        } else if(base.get(a) == base.get(b)) {
          return 0;
        } else {
          return -1;
        }
      }
    }

    private int getPreferredWidth(int n){
        if((n<=10)&&(n>0)){
            return 805;
        }else{
            return 69+74*n+12;
        }
    }

    class Refresh implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            try {
                GraphPanel.this.refresh();
            } catch (SQLException ex) {
                Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    class DeleteData implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            Object[] options = {"Continue", "Gancel"};
            boolean contin = JOptionPane.showOptionDialog(
                         GraphPanel.this,
                         "Pressing \'Continue\' will permanently delete your data.",
                         "Warning",
                         JOptionPane.YES_NO_OPTION,
                         JOptionPane.ERROR_MESSAGE,
                         null,
                         options,
                         options[1])==0;

            if(contin){
                try {
                    GraphPanel.this.matchTable.reset();
                    GraphPanel.this.weightingTable.reset();
//                    GraphPanel.this.dictTable.reset();
                } catch (SQLException ex) {
                    Logger.getLogger(GraphPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}

package com.team1160.scouting.h2;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Represents the h2 table that takes match scouting data input.
 *
 * ____________________________________________
 * | team # (int)  | type (int) | value (int) |
 * --------------------------------------------
 * | 1. ...        | 2.  ...    | 3.  ...     |
 * --------------------------------------------
 *
 * 1. the team number of the inputted data
 * 2. the type (the hashcode of the type input) i.e: # points scored
 * 3. the value of the input
 *
 * @author Saketh Kasibatla
 */
public class MatchScoutingTable extends H2Table{
    
    public MatchScoutingTable(String database) throws ClassNotFoundException, SQLException{
        super(database);
        statement.executeUpdate("create table if not exists match(team int,ID int,value int);");
    }

        /**
     * inserts a row into the database.
     * @param team the team number of the data
     * @param ID the ID (hashcode) of the data
     * @param value the data
     */
    public void insert(int team,int ID,int value) throws SQLException{
        PreparedStatement prep= connection.prepareStatement(
                "insert into match values(?,?,?);");
        prep.setInt(1, team);
        prep.setInt(2, ID);
        prep.setInt(3, value);

        prep.addBatch();

        connection.setAutoCommit(false);
        prep.executeBatch();
        connection.setAutoCommit(true);


    }

        /**
     * gets the values in the table.
     * @param team the team name whose values will be retrieved
     * @return a map containing a type and value for each data entry under the team.
     */
    public Map<Integer,Integer> getValues(int team) throws SQLException{
        ResultSet rs=statement.executeQuery("select * from match where team="+Integer.toString(team));
        Map<Integer,Integer> values=new HashMap<Integer,Integer>();
        while(rs.next()){
            Integer key,value;
            key=rs.getInt("value");
            value=rs.getInt("ID");
            values.put(key, value);
        }

        return values;

    }

    /**
     * gets all values with the inputted team and ID.
     * @param team the team number which the values are under.
     * @param ID the id (hashcode) which the values are under.
     * @return a list of all the values.
     */
    public List<Integer> getValues(int team,int ID) throws SQLException{
        ResultSet rs=statement.executeQuery("select * from match where team="+Integer.toString(team)+"and ID="+Integer.toString(ID));
        List<Integer> values=new ArrayList<Integer>();
        while(rs.next()){
            Integer value;
            value=rs.getInt("value");
            values.add(value);
        }

        return values;

    }

    public int getAverageValue(int team, int ID) throws SQLException{
        List<Integer> values = this.getValues(team, ID);
        int sum = 0;
        for(Integer i:values){
            sum += i;
        }
//        System.out.println(team);
//        System.out.println(values.size());
//        System.out.println(sum/(values.size()));
//        System.out.println("\n");

        int average = (values.size()==0)?0:sum/(values.size());
        return average;

    }

    public List<Integer> getTeams() throws SQLException{
        ResultSet rs = statement.executeQuery("select distinct team from match");
        List<Integer> teams = new ArrayList<Integer>();
        while(rs.next()){
            Integer team;
            team=rs.getInt("team");
            teams.add(team);
        }
        Collections.sort(teams);
        return teams;
    }

    public void reset() throws SQLException{
        statement.executeUpdate("Drop table match;");
        statement.executeUpdate("create table if not exists match(team int,ID int,value int);");
    }
}

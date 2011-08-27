/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.scouting.h2;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Saketh Kasibatla
 */
public class WeightingTable extends H2Table{


    public WeightingTable(String database) throws ClassNotFoundException, SQLException{
        super(database);
        statement.executeUpdate("create table if not exists weight(ID int,value int);");
        
    }

    public void insert(int ID, int value) throws SQLException{
        PreparedStatement prep= connection.prepareStatement(
                "insert into weight values(?,?);");
        prep.setInt(1, ID);
        prep.setInt(2, value);

        prep.addBatch();

        connection.setAutoCommit(false);
        prep.executeBatch();
        connection.setAutoCommit(true);


    }

    public  int getValue(int ID) throws SQLException{
        ResultSet rs;
        try {
            rs = statement.executeQuery("select * from weight where id=" + Integer.toString(ID));
            rs.next();
            return rs.getInt("value");
        } catch (SQLException ex) {
            this.insert(ID, 100);
            return 100;
        }
        
        
    }


    public Map<Integer,Integer> getValues() throws SQLException{
        ResultSet rs=statement.executeQuery("select * from weight");
        Map<Integer,Integer> values=new HashMap<Integer,Integer>();
        while(rs.next()){
            Integer key;
            Integer value;
            key=rs.getInt("ID");
            value=rs.getInt("value");
            values.put(key, value);
        }

        return values;

    }

    public void reset() throws SQLException{
        statement.executeUpdate("Drop table weight;");
        statement.executeUpdate("create table if not exists weight(ID int,value int);");
    }
}

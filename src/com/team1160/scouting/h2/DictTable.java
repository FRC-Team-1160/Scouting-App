/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.scouting.h2;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Saketh Kasibatla
 */
public class DictTable extends H2Table{

    public DictTable(String database) throws SQLException, ClassNotFoundException{
        super(database);
        statement.executeUpdate("create table if not exists dictweight(ID int,value varchar);");
    }

     public void insert(int ID, String value) throws SQLException{
        PreparedStatement prep= connection.prepareStatement(
                "insert into dictweight values(?,?);");
        prep.setInt(1, ID);
        prep.setString(2, value);

        prep.addBatch();

        connection.setAutoCommit(false);
        prep.executeBatch();
        connection.setAutoCommit(true);


    }

    public  String getString(int ID) throws SQLException{
        ResultSet rs=statement.executeQuery("select * from dictweight where id="
                +Integer.toString(ID));
        return rs.getString("value");
    }

    public  String getID(String value) throws SQLException{
        ResultSet rs=statement.executeQuery("select * from dictweight where value="
                +value);
        return rs.getString("ID");
    }

    public Map<Integer,String> getValuesID() throws SQLException{
        ResultSet rs=statement.executeQuery("select * from dictweight");
        Map<Integer,String> values=new LinkedHashMap<Integer,String>();
        while(rs.next()){
            Integer key;
            String value;
            key=rs.getInt("ID");
            value=rs.getString("value");
            values.put(key, value);
        }

        return values;

    }

    public Map<String,Integer> getValuesName() throws SQLException{
        ResultSet rs=statement.executeQuery("select * from dictweight");
        Map<String,Integer> values=new LinkedHashMap<String,Integer>();
        while(rs.next()){
            Integer key;
            String value;
            key=rs.getInt("ID");
            value=rs.getString("value");
            values.put(value, key);
        }

        return values;

    }

    public void reset() throws SQLException{
        statement.executeUpdate("Drop table dictweight;");
        statement.executeUpdate("create table if not exists dictweight(ID int,value varchar);");
    }

}

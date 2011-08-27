/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.scouting.h2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sakekasi
 */
public class CommentTable extends H2Table{

    public CommentTable(String database) throws ClassNotFoundException, SQLException {
        super(database);
        statement.executeUpdate("create table if not exists comment(team int,match int,comment varchar);");
    }

    public void insert(int team, int match, String comment) throws SQLException{
        PreparedStatement prep= connection.prepareStatement(
                "insert into comment values(?,?,?);");
        prep.setInt(1, team);
        prep.setInt(2, match);
        prep.setString(3, comment);

        prep.addBatch();

        connection.setAutoCommit(false);
        prep.executeBatch();
        connection.setAutoCommit(true);
    }

    public String getComment(int team, int match) throws SQLException{
        ResultSet rs;
        rs = statement.executeQuery("select * from comment where team=" + Integer.toString(team)
                +"and match="+Integer.toString(match));
        rs.next();
        return rs.getString("comment");
    }

    public Map<Integer, String> getComments(int team) throws SQLException{
        ResultSet rs;
        rs = statement.executeQuery("select * from comment where team="+ Integer.toString(team));
        Map<Integer, String> comments = new LinkedHashMap<Integer, String>();
        while(rs.next()){
            Integer key;
            String value;
            key=rs.getInt("match");
            value=rs.getString("comment");
            comments.put(key, value);
        }

        return comments;

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
        statement.executeUpdate("Drop table comment;");
        statement.executeUpdate("create table if not exists comment(team int,match int,comment varchar);");
    }



}

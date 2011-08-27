/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.scouting.h2;

import java.sql.*;
import org.h2.Driver;

/**
 *
 * @author Saketh Kasibatla
 */
@SuppressWarnings("unused")
public abstract class H2Table {
        /**
     * the location of the database.
     */
    protected String database;

    /**
     * the Connection object provided (java.sql)
     * created from the database string.
     */
    protected Connection connection;

    /**
     * the Statement object used to send commands
     * to the database.
     */
    protected Statement statement;

    public H2Table(String database) throws ClassNotFoundException, SQLException{
        this.database = database;
        Class.forName("org.h2.Driver");
        connection= DriverManager.getConnection("jdbc:h2:"+database);
        statement=connection.createStatement();
    }

    
}

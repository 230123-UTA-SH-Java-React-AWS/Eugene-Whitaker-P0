package com.revature.repository;

import com.revature.repository.dao.DAOgetObjectsWhere;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the interactions with the managerIDs table of 
 *         the database. It implements the generic <code>DOA</code> 
 *         interface(s) {@link Repository}.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeRepository}</li>
 *         <li>{@link ManagerRepository}</li>
 *         <li>{@link TicketRepository}</li>
 *         </ul>
 *         for more information on other repositories.
 */
public class ManagerIDRepository implements DAOgetObjectsWhere<Integer> {
    /**
     * <p>
     * This method gets an unique item from the database specified by the 
     * clause parameter
     * </p>
     * 
     * @param clause the WHERE clause meant to select a unique item
     * @return The unique item selected
     */
    @Override
    public Integer getObjectsWhere(String clause) {
        String sql = "SELECT * FROM managerids WHERE "+clause;
        int managerID = 0;

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                managerID = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return managerID;
    }
}

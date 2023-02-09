package com.revature.repository;

import com.revature.App;
import com.revature.model.Manager;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the interactions with the manager table of the
 *         database. It implements the generic <code>DOA</code> interface
 *         {@link Repository}.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeRepository}</li>
 *         <li>{@link ManagerIDRepository}</li>
 *         </ul>
 *         for more information on other repositories.
 */
public class ManagerRepository {
    /**
     * <p>
     * This method saves an object to the database
     * </p>
     * 
     * @param manager the object to be saved
     */
    public void saveToRepository(Manager manager) {
        String sql = "UPDATE manager SET (email, pass) = (?, ?) WHERE ID = (?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = connection.prepareStatement(sql);
            
            prstmt.setString(1, manager.getEmail());
            prstmt.setString(2, manager.getPassword());
            prstmt.setInt(3, manager.getManagerID());

            prstmt.execute();

            App.logger.info("Number of rows updated: "+prstmt.getUpdateCount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method gets an unique item from the database specified by the 
     * clause parameter
     * </p>
     * 
     * @param clause the WHERE clause meant to select a unique item
     * @return The unique item selected
     */
    public Manager getObjectsWhere(String clause) {
        String sql = "SELECT * FROM manager WHERE "+clause;
        Manager manager = new Manager();

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                manager.setManagerID(rs.getInt(1));
                manager.setEmail(rs.getString(2));
                manager.setPassword(rs.getString(3));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return manager;
    }
}

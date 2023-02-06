package com.revature.repository;

import com.revature.model.Manager;
import com.revature.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
     * This method adds a new {@link Manager} object to the database.
     * </p>
     * 
     * @param manager the object to be added
     */
    public void saveToRepository(Manager manager) {
        String sql = "INSERT INTO manager (ID, email, pass) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = connection.prepareStatement(sql);

            prstmt.setInt(1, manager.getManagerID());
            prstmt.setString(2, manager.getEmail());
            prstmt.setString(3, manager.getPassword());

            prstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method gets all managers in the database and returns them as a
     * {@link List} of {@link Manager} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    public List<Manager> getAllObjects() {
        String sql = "SELECT * FROM manager";
        List<Manager> listManagers = new ArrayList<Manager>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Manager newManager = new Manager();
                newManager.setManagerID(rs.getInt(1));
                newManager.setEmail(rs.getString(2));
                newManager.setPassword(rs.getString(3));

                listManagers.add(newManager);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listManagers;
    }

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

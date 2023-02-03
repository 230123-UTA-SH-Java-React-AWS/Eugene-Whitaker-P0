package com.revature.repository;

import com.revature.utils.ConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the interactions with the managerIDs table of the
 *         database. It implements the generic <code>DOA</code> interface
 *         {@link Repository}.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeRepository}</li>
 *         <li>{@link ManagerRepository}</li>
 *         </ul>
 *         for more information on other repositories.
 */
public class ManagerIDRepository implements Repository<Integer> {

    /**
     * <p>
     * This method saves a {@link Employee} object to a file at FILEPATH.
     * </p>
     * 
     * @param employee the object to be saved
     */
    @Override
    public void saveToFile(Integer employee) {
    }

    /**
     * <p>
     * This method adds a new {@link Employee} object to the database.
     * </p>
     * 
     * @param employee the object to be added
     */
    @Override
    public void saveToRepository(Integer employee) {
    }
    /**
     * <p>
     * This method gets all managerIDs in the database and returns them as a
     * {@link List} of {@link Integer} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    @Override
    public List<Integer> getAllObjects() {
        String sql = "SELECT * FROM managerIDs";
        List<Integer> listManagerIDs = new ArrayList<Integer>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next())
                listManagerIDs.add(rs.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listManagerIDs;
    }

    /**
     * <p>
     * This method gets all employee emails in the database and 
     * returns them as a {@link List} of {@link String} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    @Override
    public List<String> getAllColumnString(String column) {
        return null;
    }

    /**
     * <p>
     * This method gets all employee emails in the database and 
     * returns them as a {@link List} of {@link Integer} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    @Override
    public List<Integer> getAllColumnInteger(String column) {
        return null;
    }
}

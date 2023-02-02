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
 *         database.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeRepository}</li>
 *         <li>{@link ManagerRepository}</li>
 *         </ul>
 *         for more information on other repositories.
 */
public class ManagerIDRepository {

    /**
     * <p>
     * This method gets all managerIDs in the database and returns them as a
     * {@link List} of {@link Integer} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    public List<Integer> getAllManagerIDs() {
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
}

package com.revature.repository;

import com.revature.model.Manager;
import com.revature.utils.ConnectionUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

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
public class ManagerRepository implements Repository<Manager>{
    private static final String FILEPATH = "ersproject/src/main/java/com/revature/repository/manager.json";

    /**
     * <p>
     * This method saves a {@link Manager} object to a file at FILEPATH.
     * </p>
     * 
     * @param manager the object to be saved
     */
    @Override
    public void saveToFile(Manager manager) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = "";

        try {
            jsonObject = mapper.writeValueAsString(manager);
            File managerFile = new File(FILEPATH);
            FileWriter writer;
            // If the file doesn't exist then create it and write to it
            // Otherwise append it and write to it
            if (managerFile.createNewFile())
                writer = new FileWriter(FILEPATH);
            else
                writer = new FileWriter(FILEPATH, true);

            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(jsonObject);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method adds a new {@link Manager} object to the database.
     * </p>
     * 
     * @param manager the object to be added
     */
    @Override
    public void saveToRepository(Manager manager) {
        String sql = "INSERT INTO manager (ID, email, pass) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = connection.prepareStatement(sql);

            prstmt.setInt(1, manager.getManagerID());
            prstmt.setString(2, manager.getEmail());
            prstmt.setString(3, manager.getPassword());

            prstmt.execute();
            System.out.println("Number of Rows updated: " + prstmt.getUpdateCount());

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
    @Override
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

    /**
     * <p>
     * This method gets all entries of a column where the entry is a
     * <code>String</code> in the database and returns them as a
     * {@link List} of {@link String} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    @Override
    public List<String> getAllColumnString(String column) {
        String sql = "SELECT "+column+" FROM manager";
        List<String> listEmails = new ArrayList<String>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String newEntry = new String();
                newEntry = rs.getString(1);

                listEmails.add(newEntry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listEmails;
    }

    /**
     * <p>
     * This method gets all entries of a column where the entry is a
     * <code>Integer</code> in the database and returns them as a
     * {@link List} of {@link Integer} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    @Override
    public List<Integer> getAllColumnInteger(String column) {
        String sql = "SELECT "+column+" FROM manager";
        List<Integer> listEmails = new ArrayList<Integer>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Integer newEntry;
                newEntry = rs.getInt(1);

                listEmails.add(newEntry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listEmails;
    }
}

package com.revature.repository;

import com.revature.model.Employee;
import com.revature.model.Ticket;
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
 *         This class handles the interactions with the employee table of the
 *         database. It implements the generic <code>DOA</code> interface
 *         {@link Repository}.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link ManagerIDRepository}</li>
 *         <li>{@link ManagerRepository}</li>
 *         </ul>
 *         for more information on other repositories.
 */
public class TicketRepository implements Repository<Ticket> {
    private static final String FILEPATH = "ersproject/src/main/java/com/revature/repository/employee.json";

    /**
     * <p>
     * This method saves a {@link Employee} object to a file at FILEPATH.
     * </p>
     * 
     * @param ticket the object to be saved
     */
    @Override
    public void saveToFile(Ticket ticket) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonObject = "";

        try {
            jsonObject = mapper.writeValueAsString(ticket);
            File employeeFile = new File(FILEPATH);
            FileWriter writer;
            // If the file doesn't exist then create it and write to it
            // Otherwise append it and write to it
            if (employeeFile.createNewFile())
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
     * This method adds a new {@link Ticket} object to the database.
     * </p>
     * 
     * @param employee the object to be added
     */
    @Override
    public void saveToRepository(Ticket ticket) {
        // String sql = "INSERT INTO employee (amount, description) VALUES (?, ?)";

        // try (Connection connection = ConnectionUtil.getConnection()) {
        //     PreparedStatement prstmt = connection.prepareStatement(sql);
        //     prstmt.setFloat(1, ticket.getAmount());
        //     prstmt.setString(2, ticket.getDescription());

        //     prstmt.execute();
        //     System.out.println("Number of Rows updated: " + prstmt.getUpdateCount());

        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }
    }

    /**
     * <p>
     * This method adds a new {@link Ticket} object to the database.
     * </p>
     * 
     * @param employee the object to be added
     */
    public void saveToRepository(Ticket ticket, Employee employee) {
        String sql = "INSERT INTO ticket (employeeID, amount, description, pending, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement prstmt = connection.prepareStatement(sql);
            System.out.println("EmployeeID: "+employee.getEmployeeID());
            System.out.println("Amount: "+ticket.getAmount());
            System.out.println("Desc: "+ticket.getDescription());
            System.out.println("Pending: "+ticket.getPending());
            System.out.println("Status: "+ticket.getStatus());
            prstmt.setInt(1, employee.getEmployeeID());
            prstmt.setFloat(2, ticket.getAmount());
            prstmt.setString(3, ticket.getDescription());
            prstmt.setBoolean(4, ticket.getPending());
            prstmt.setString(5, ticket.getStatus());

            prstmt.execute();
            System.out.println("Number of Rows updated: " + prstmt.getUpdateCount());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * This method gets all employees in the database and returns them as a
     * {@link List} of {@link Ticket} objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    @Override
    public List<Ticket> getAllObjects() {
        String sql = "SELECT * FROM ticket";
        List<Ticket> listTickets = new ArrayList<Ticket>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Ticket newTicket = new Ticket();
                newTicket.setTicketID(rs.getInt(1));
                newTicket.setAmount(rs.getFloat(2));
                newTicket.setDescription(rs.getString(3));

                listTickets.add(newTicket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listTickets;
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
        // String sql = "SELECT "+column+" FROM employee";
        // List<String> listEmails = new ArrayList<String>();

        // try (Connection connection = ConnectionUtil.getConnection()) {
        //     Statement stmt = connection.createStatement();

        //     ResultSet rs = stmt.executeQuery(sql);

        //     while (rs.next()) {
        //         String newEmail = new String();
        //         newEmail = rs.getString(1);

        //         listEmails.add(newEmail);
        //     }

        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }

        // return listEmails;
        return null;
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
        // String sql = "SELECT "+column+" FROM employee";
        // List<Integer> listEmails = new ArrayList<Integer>();

        // try (Connection connection = ConnectionUtil.getConnection()) {
        //     Statement stmt = connection.createStatement();

        //     ResultSet rs = stmt.executeQuery(sql);

        //     while (rs.next()) {
        //         Integer newEmail;
        //         newEmail = rs.getInt(1);

        //         listEmails.add(newEmail);
        //     }

        // } catch (SQLException e) {
        //     e.printStackTrace();
        // }

        // return listEmails;
        return null;
    }
}

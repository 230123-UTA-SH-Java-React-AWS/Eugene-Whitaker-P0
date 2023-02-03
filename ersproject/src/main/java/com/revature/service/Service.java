package com.revature.service;

import java.util.List;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class handles the intermediary behaviors between the controller
 *         and the database for the managers.
 *         </p>
 *         See Also:
 *         <ul>
 *         <li>{@link EmployeeService}</li>
 *         <li>{@link ManagerIDService}</li>
 *         <li>{@link ManagerService}</li>
 *         </ul>
 *         for more information on its implementions.
 */
public interface Service<T> {
     /**
     * <p>
     * This generic method save an object formated in <code>JSON</code> to
     * the database.
     * </p>
     * 
     * @param objectJson the <code>JSON</code> object to be added to the database
     */
    public abstract void saveToRepository(String objectJson);

    /**
     * <p>
     * This generic method gets all objects in the database and returns them as a
     * {@link List} of objects.
     * </p>
     * 
     * @return the {@link List} of objects
     */
    public List<T> getAllObjects();

    /**
     * <p>
     * This method gets all entries of a column where the entry is a
     * <code>String</code> in the database and returns them as a 
     * {@link List} of {@link String} objects.
     * </p>
     * 
     * @return the {@link List} of {@link String} objects
     */
    public List<String> getAllColumnString(String column);

    /**
     * <p>
     * This method gets all entries of a column where the entry is a
     * <code>String</code> in the database and returns them as a 
     * {@link List} of {@link Integer} objects.
     * </p>
     * 
     * @return the {@link List} of {@link String} objects
     */
    public List<Integer> getAllColumnInteger(String column);

    /**
     * <p>
     * This generic method converts a {@link List} of objects to a
     * <code>JSON</code> formated string.
     * </p>
     * 
     * @param the {@link List} of objects
     * @return the <code>JSON</code> formated string
     */
    public String listToJSON(List<T> listEmployees);
}

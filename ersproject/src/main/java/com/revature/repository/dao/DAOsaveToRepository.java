package com.revature.repository.dao;

public interface DAOsaveToRepository<T> {
    /**
     * <p></p>
     * 
     * @param object
     */
    public abstract void saveToRepository(T object);
}
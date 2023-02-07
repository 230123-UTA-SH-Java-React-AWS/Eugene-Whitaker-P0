package com.revature.repository.dao;

public interface DAOsaveToRepositoryFK<T, K> {
    /**
     * <p></p>
     * 
     * @param ticket
     * @param employee
     */
    public abstract void saveToRepositoryFK(T ticket, K employee);
}

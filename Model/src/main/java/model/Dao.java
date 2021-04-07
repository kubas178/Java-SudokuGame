package model;

import model.exceptions.FileException;

import java.sql.SQLException;

public interface Dao<T> {

    T read() throws FileException, SQLException, ClassNotFoundException;

    void write(T t) throws FileException, SQLException;

}

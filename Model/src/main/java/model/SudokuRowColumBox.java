package model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.exceptions.RowColumnBoxSizeException;


public abstract class SudokuRowColumBox implements Observable,Cloneable {

    public static final int SIZE = 9;
    private List<SudokuField> fields;
    private Set<Observer> observers = new HashSet<>();

    public SudokuRowColumBox(final List<SudokuField> fields) {
        if(fields.size() != SIZE) {
            throw new RowColumnBoxSizeException("Wrong size of row,coulmn,or box ");
        }
        this.fields = fields;
    }

    public boolean verify() {
        for (int i = 0; i < SIZE; i++) {
            for (int i2 = i + 1; i2 < SIZE; i2++) {
                if (fields.get(i).getFieldValue() == fields.get(i2).getFieldValue()) {
                    notifyObserver();
                    return false;
                }
            }
        }
        return true;
    }

    public List<SudokuField> getSudokuFieldList() {
        return Collections.unmodifiableList(fields);
    }


    @Override
    public void notifyObserver() {
        observers.forEach(Observer::update1);
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }


    @Override
    public  String toString() {
        return MoreObjects.toStringHelper(this).add("fields", fields).toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final SudokuRowColumBox other  = (SudokuRowColumBox) o;
        return Objects.equal(this.fields, other.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fields);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        List<SudokuField> fields = new ArrayList<>(getSudokuFieldList());
        return new SudokuColumn(fields);
    }

}

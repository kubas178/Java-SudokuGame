package model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.io.Serializable;
import model.exceptions.FieldValueException;


public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private int value;
    private boolean isModified;

    public SudokuField(){
    }

    public SudokuField(int value) {
        this.value = value;
    }

    public int getFieldValue() {
        return this.value;
    }

    public void setFieldValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }



    public void setValue(int value) {
        if(value > 9 || value < 0) {
            throw new FieldValueException("Wrong field value");
        }
        this.value = value;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    @Override
    public  String toString() {
        return MoreObjects.toStringHelper(this).add("fieldValue", value).toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final SudokuField other  = (SudokuField) o;
        return Objects.equal(this.value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public int compareTo(SudokuField o) {
        if (this.getFieldValue() == o.getFieldValue()) {
            return 0;
        } else {
            return 1;
        }
    }

    public Object clone() {
        SudokuField field = new SudokuField();
        field.setFieldValue(this.value);
        return field;
    }

}

package model.exceptions;

public class DataBaseException  extends RuntimeException {

    public DataBaseException(Throwable cause) {
        super(cause);
    }

    public DataBaseException() {
        super();
    }
}

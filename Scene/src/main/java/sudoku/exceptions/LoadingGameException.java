package sudoku.exceptions;

import model.exceptions.FileException;

import java.io.IOException;

public class LoadingGameException extends FileException
{ public LoadingGameException(Throwable cause) { super(cause); }

}

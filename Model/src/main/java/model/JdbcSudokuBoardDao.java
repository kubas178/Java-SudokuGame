package model;

import model.exceptions.DataBaseException;
import model.exceptions.FileException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    private String boardName;

    public JdbcSudokuBoardDao(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public SudokuBoard read() throws DataBaseException {
        try (Connection connection = prepareConnection()) {
            Statement statement = connection.createStatement();
            return readBoard(statement);
        } catch (Exception e) {
            System.out.println(e);
            throw new DataBaseException(e);
        }
    }

    private SudokuBoard readBoard(Statement statement) throws SQLException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        try(ResultSet boardRS = statement.executeQuery("select id from board where name ='" + boardName + "';");) {
            return readFields(boardRS, statement, board);
        } catch (Exception e) {
            throw new DataBaseException(e);
        }
    }

    private SudokuBoard readFields(ResultSet boardRS, Statement statement, SudokuBoard board) throws SQLException {
        if (boardRS.next()) {
            int boardId = boardRS.getInt("id");
            ResultSet fieldsRS = statement.executeQuery("select * from field where board_id = " + boardId + ";");
            while (fieldsRS.next()) {
                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                        board.set(fieldsRS.getInt("x"), fieldsRS.getInt("y"), fieldsRS.getInt("value"));
                        if (fieldsRS.getBoolean("is_modified"))
                            board.setGap(fieldsRS.getInt("x"), fieldsRS.getInt("y"), fieldsRS.getInt("value"));
                    }
                }
            }
            return board;
        }
        throw new DataBaseException();
    }

    @Override
    public void write(SudokuBoard sudokuBoard){
        try (Connection connection = prepareConnection()){
            Statement statement = connection.createStatement();
            removeOldBoards(connection);
            statement.executeUpdate("insert into board (name) values ('" + boardName + "')", Statement.RETURN_GENERATED_KEYS);
            try (ResultSet boardResult = statement.getGeneratedKeys()) {
                boardResult.next();
                var boardId = boardResult.getInt(1);
                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++)
                        statement.executeUpdate("insert into field (x,y,value,board_id,is_modified) values ('" + x + "', '" + y
                                + "', '" + sudokuBoard.get(x, y) + "', '" + boardId + "', '" + sudokuBoard.isModificable(x, y) + "')");
                }
            }
        } catch(Exception e){
            System.out.println(e);
            throw new DataBaseException(e);
        }
    }

    private void removeOldBoards(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        try (ResultSet boardRS = statement.executeQuery("select id from board where name ='" + boardName + "';")) {
            while (boardRS.next()) {
                int boardId = boardRS.getInt("id");
                Statement deleteFieldsStatement = connection.createStatement();
                Statement deleteBoardStatement = connection.createStatement();
                deleteFieldsStatement.executeUpdate("DELETE from field where board_id =" + boardId);
                deleteBoardStatement.executeUpdate("DELETE from board where id =" + boardId);
            }
        } catch(Exception e) {
            throw new DataBaseException(e);
        }
    }

    public Connection prepareConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/sudoku","postgres", "postgres");
    }
}

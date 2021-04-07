import model.SudokuBoardDaoFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class SudokuBoardDaoFactoryTest {

    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();

    @Test
    public void getFileDaoTest() {
        assertNotNull(factory.getFileDao("lol"));
    }

}
package us.mattgreen.Test;

import us.mattgreen.FileInput;
import us.mattgreen.FileOutput;
import org.junit.*;

public class FileOutputTest extends junit.framework.TestCase {
    public FileOutputTest(){}

    FileOutput testFile, newFile;
    FileInput testRead, readNew;

    @Before
    public void setUp() throws Exception {
        // file already exists
        testFile = new FileOutput("testFile.txt");
        testRead = new FileInput("testFile.txt");

        // file does not yet exist
        // FileOutput can create a new file to output to, but FileInput must read from a file that already exists
        newFile = new FileOutput("newFile.txt");
        readNew = new FileInput("newFile.txt");
    }

    @After
    public void tearDown() throws Exception {
        testFile.fileClose();
        testRead.fileClose();
        newFile.fileClose();
        readNew.fileClose();
    }

    @Test
    public void testFileWrite() {
        testFile.fileWrite("Will it work?");
        testFile.fileClose();
        assertEquals("Will it work?", testRead.fileReadLine());
        newFile.fileWrite("this should not work");
        newFile.fileClose();
        assertEquals("this should not work", readNew.fileReadLine());
    }

    @Test
    public void testFileClose() {
        testFile.fileClose();
        testFile.fileWrite("This should not work");
        assertNotSame("This should not work", testRead.fileReadLine());
    }
}

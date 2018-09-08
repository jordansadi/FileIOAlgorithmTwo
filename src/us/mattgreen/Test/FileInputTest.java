package us.mattgreen.Test;

import us.mattgreen.FileInput;
import us.mattgreen.FileOutput;
import org.junit.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class FileInputTest extends junit.framework.TestCase {
    public FileInputTest() {}

    FileInput testFile;
    BufferedReader in;
    FileOutput outFile;
    String line;
    int counter;

    @Before
    public void setUp() throws Exception {
        testFile = new FileInput("testFile.txt");

        in = null;
        try {
            in = new BufferedReader(new FileReader("testFile.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File Open Error: " + "testFile.txt" + " " + e);
        }

        outFile = new FileOutput("testFile.txt");

        counter = 0;
    }

    @After
    public void tearDown() throws Exception {
        outFile.fileClose();
        testFile.fileClose();
    }

    @Test
    public void testFileRead() {
        outFile.fileWrite("testing...");
        outFile.fileClose();
        try {
            while ((line = in.readLine()) != null) {
                counter++;
            }
        } catch (Exception e) {
            System.out.println("File Write Error: " + "testFile.txt" + " " + e);
        }
        assertEquals(1, counter);
    }

    @Test
    public void testFileReadLine() {
        outFile.fileWrite("testing...");
        outFile.fileClose();
        assertEquals("testing...", testFile.fileReadLine());
    }

    @Test
    public void testFileClose() {
        outFile.fileWrite("testing...");
        testFile.fileClose();
        assertNotSame("testing...", testFile.fileReadLine());
    }
}
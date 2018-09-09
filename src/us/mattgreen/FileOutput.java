package us.mattgreen;

import java.io.*;

/**
 * This class creates a framework for writing to a specified file
 * Created by mgreen14 on 12/27/17.
 * @author Matt Green
 * @version 2018 0822 .3
 */
public class FileOutput {

    Writer out = null;
    private String fileName;

    /**
     * This constructor creates an instance of the FileOutput class
     * @param fileName the name of the specified file
     * @return instance of the FileOutput class
     */
    public FileOutput(String fileName) {
        this.fileName = fileName;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
        }
        catch(FileNotFoundException e) {
            System.out.println("File Open Error: " + fileName + " "  + e);
        }
    }

    /**
     * This method attempts to write a line to the specified file
     * @param line the line that is meant to be written to the file
     * @throws Exception if the file cannot be written to
     */
    public void fileWrite(String line) {
        try {
            out.write(line+"\n");
        }
        catch(Exception e) {
            System.out.println("File Write Error: " + fileName + " "  + e);
        }
    }

    /**
     * This method attempts to close the specified file
     * @throws IOException if the file cannot be closed
     */
    public void fileClose() {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
package core;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class that provides a content for the newsletter from a specified text file.
 */
public class Resources {
    private final String filename;

    /**
     * Constructs a core.Resources object.
     * @param filename a filename from which the content is sourced
     */
    public Resources(String filename) {
        this.filename = filename;
    }

    /**
     * Returns the newsletter's content based on the current date.
     * @return content of a newsletter
     * @throws IOException if it's unable to read the file
     */
    public String getResource() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String currentDateStr = new SimpleDateFormat("dd/MM/yy").format(new Date());

        boolean foundDate = false;

        try (LineNumberReader reader = new LineNumberReader(new FileReader(filename))) {
            String curLine;

            while ((curLine = reader.readLine()) != null) {
                curLine = curLine.trim();

                if (curLine.equals(currentDateStr)) {
                    foundDate = true;
                    continue;
                }

                if (foundDate && curLine.isEmpty()) {
                    String nextLine = reader.readLine();
                    if (nextLine != null && nextLine.isEmpty()) {
                        break;
                    }
                }

                if (foundDate) {
                    stringBuilder.append(curLine).append('\n');
                }
            }
        }

        return stringBuilder.toString();
    }
}

package ticketbooking.filesystem;

import java.io.*;
import java.util.StringTokenizer;

public class FileIO {

    /**
     * Reads a file and returns a 2D array of strings.
     * Each row in the file is a line, and each column is separated by a comma.
     * The first row is the header row. The header row is ignored while reading the file.
     * The first column is the name of the customer.
     * The second column is the number of tickets the customer has bought.
     *
     * @param filename the name of the file to read
     * @return a 2D array of strings
     */
    public static String[][] read(String filename) {
        File file = new File(filename);
        String[][] result = new String[1][2];
        int index = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                if (tokenizer.countTokens() != 2) {
                    System.out.println("Error: Invalid data in " + filename + "'s " + index + 1 + " row.");
                    System.out.println("Expected 2 columns, but found " + tokenizer.countTokens() + " columns.");
                    System.exit(1);
                }
                String[] parts = new String[tokenizer.countTokens()];
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = tokenizer.nextToken();
                }
                if (index >= result.length) {
                    String[][] newResult = new String[result.length + 1][2];
                    System.arraycopy(result, 0, newResult, 0, result.length);
                    result = newResult;
                }
                result[index] = parts;
                index++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + filename + " not found.");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error: Could not read " + filename + ".");
            System.exit(1);
        }
        return result;
    }

}

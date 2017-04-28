
import java.io.*;
import java.net.URL;
import java.util.Hashtable;

/**
 * Created by kunze on 1/12/2017.
 * Utility class to convert txt files to hashtables, and a function that helps $
 */

public class fileHasher {
    /**
     * Checks to see if the given table contains the given word using the defau$
     * @param table the Hashtable to search through
     * @param word the word to search for
     * @return
     */
    public boolean searchTable(Hashtable table, String word)
    {
        int wordKey = word.hashCode();
        return table.containsKey(wordKey);
    }
    /**
     * Takes a file path, reads the file, hashes it, and returns the hash table.
     * @param filePath line-delimted, txt file, ideally with only 1 word per st$
     * @return A hashtable, where the key is the hashed value of the line, and $
     * @throws IOException File not existant? Whoops.
     */

    public static Hashtable fileToHashTable(String filePath) throws IOException {
        Hashtable<Integer, Double> table = new Hashtable<Integer, Double>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = reader.readLine();

        while(line != null)
        {
            String[] split = line.split("\t");
            String word = split[0].substring(0,split[0].length() - 2);
            Double score = Double.parseDouble(split[1]);

            int hashCode = word.hashCode();

            table.put(hashCode, score);
            line = reader.readLine();
        }
        return table;
    }
}




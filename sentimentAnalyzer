import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class sentimentAnalyzer
{
    private Hashtable wordList;

    /**
     * languageProcessing.sentimentAnalyzer constructor that hashes
     * @throws IOException File is missing
     */
    public sentimentAnalyzer() throws IOException {
        String filePath = "sentiwords.txt";
        wordList = fileHasher.fileToHashTable(filePath);
        System.out.println("Tables done hashing");
    }

    /**
     *Takes a string, takes outz
     * @param str String to analyze
     * @return
     */
    public double analyzeString(String str)
    {
        double stringScore = 0;
        String[] strArr = str.replaceAll("[^a-zA-Z ]", "").toLowerCase().split($
        for(String segment : strArr)
        {
            int hashedSeg = segment.hashCode();

            if(wordList.containsKey(hashedSeg))
            {
                double value = (double) wordList.get(hashedSeg);
                stringScore += value;
            }

        }
                            //TODO: Implement quantifier for precision
        return stringScore;
    }}










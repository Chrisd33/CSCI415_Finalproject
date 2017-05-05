import org.apache.hadoop.conf.Configuration;
import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapreduce.Reducer;
import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.util.Hashtable;
import java.io.BufferedReader;
import java.io.*;


public class sentimentReducer extends Reducer < Text, Text, Text, Text > {
    double runningTotal = 0.0;
    int numTweets = 0;
    Hashtable sentiTable = new Hashtable();

   @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Path pt = new Path("/user/jkunze/sentiwords.txt");
        FileSystem fs = FileSystem.get(new Configuration());
        BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
	String line = br.readLine();
        while (line!=null) {
            String[] split =  line.split("\t");
            String word = split[0].substring(0,split[0].length()-2);
            double score = Double.parseDouble(split[1]);
            int hashCode = word.hashCode();
            sentiTable.put(hashCode, score);
            line = br.readLine();
        }
    }

    @Override
    public void reduce(Text key, Iterable < Text > values, Context context) throws IOException,
    InterruptedException {
        for (Text value: values) { //Finds total value of bytes us$
//	    String valueArr[] = (value.toString()).split("\t");
//	    String tweet = valueArr[1];
//          double tempScore = analyzeString(tweet);
//          runningTotal += tempScore;
//          numTweets++;
	    context.write(value, new Text(""));
        }
 }

    public double analyzeString(String str) {
        double stringScore = 0.0;
        String[] strArr = str.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        for (String segment: strArr) {
            int hashedSeg = segment.hashCode();

            if (sentiTable.containsKey(hashedSeg)) {
                double value = (double) sentiTable.get(hashedSeg);
                stringScore += value;
            }
        }
        return stringScore;
    }

    @Override                                                    //Only prints$
     protected void cleanup(Context context) throws IOException, InterruptedException{
	double avg = (runningTotal/(double)numTweets);
	//BigDecimal avg = runningTotal.divide(new BigDecimal(numTweets));
	Text avgText = new Text(String.valueOf(avg));
	Text runningTotalText = new Text(String.valueOf(runningTotal));
	Text numTweetsText = new Text(Integer.toString(numTweets));
	String outputString = "There were " + numTweets + " strings parsed, with a total score of " + runningTotal + "\nAverage: " + avg;
	Text outputText = new Text(outputString);
	context.write(outputText, outputText);
     }
}

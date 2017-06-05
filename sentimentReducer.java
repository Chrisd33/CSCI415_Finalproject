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
    int numPositive = 0;
    int numNegative = 0;
    int numTweets = 0;
    int numTweetsOverall = 0;
    double lowestTweetScore = 0.0;
    double highestTweetScore = 0.0;
    String highestTweet;
    String lowestTweet;
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
	double timeScore = 0.0;
	int numScore = 0;
        for (Text value: values) {
          double tempScore = analyzeString(value.toString());
	  if(tempScore != 0.0){
          runningTotal += tempScore;
          numTweets++;
	  timeScore += tempScore;
	  numScore++;
	  setScorePosition(value, tempScore);
	  if(tempScore > 0)
	    numPositive++;
	  else if(tempScore < 0)
	    numNegative++;
	}
	numTweetsOverall++;
        }
	double timeScoreAvg = timeScore/numScore;
	context.write(new Text(key), new Text(Double.toString(timeScoreAvg)));

 }

    private void setScorePosition(Text textAnalyzed, double scoreAssigned) {
	    if(scoreAssigned > highestTweetScore)
	      {
		highestTweetScore = scoreAssigned;
		highestTweet = textAnalyzed.toString();
	      }
	    else if(scoreAssigned < lowestTweetScore)
	    {
		lowestTweetScore = scoreAssigned;
		lowestTweet = textAnalyzed.toString();
	    }
}
    public double analyzeString(String str) {
        double stringScore = 0.0;
	int numWordsInString = 0;
        String[] strArr = str.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        for (String segment: strArr) {
            int hashedSeg = segment.hashCode();
            if (sentiTable.containsKey(hashedSeg)) {
                double value = (double) sentiTable.get(hashedSeg);
                stringScore += value;
            }
	    numWordsInString++;
        }
	double avg = stringScore / (double) numWordsInString;
        return avg;
    }

    @Override
     protected void cleanup(Context context) throws IOException, InterruptedException{
	double avg = (runningTotal/(double)numTweets);
	Text avgText = new Text(String.valueOf(avg));
	Text runningTotalText = new Text(String.valueOf(runningTotal));
	Text numTweetsText = new Text(Integer.toString(numTweets));
	String outputString = "\nThere were " + numTweets + " strings parsed, with a total score of " + runningTotal + "\nAverage: " + avg;
	double percentPositive = 100* ((double)numPositive/(double)numTweets);
	double percentNegative = 100.0 - percentPositive;
	String posNegString = "\nOf the " + numTweets + " strings, " + numPositive + " were overall positive, and " + numNegative + " were overall negative\nThis means\n" + Math.floor(percentNegative) + "% were overall negative\n" + Math.floor(percentPositive) + "% were overall positive";
	Text outputText = new Text(outputString);
	Text posNegText = new Text(posNegString);
	String highestOutput = "Highest tweet was: " + highestTweet + "\nwith a score of: " + highestTweetScore;
	String lowestOutput = "\nLowest Tweet was: " + lowestTweet + "\n with a score of: " + lowestTweetScore;
	Text highestOutputText = new Text(highestOutput);
	Text lowestOutputText = new Text(lowestOutput);
	context.write(highestOutputText, lowestOutputText);
	context.write(outputText, posNegText);
     }
}

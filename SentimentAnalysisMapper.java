import java.io.IOException;
import java.math.BigDecimal;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SentimentAnalysisMapper extends Mapper<LongWritable, Text, Text, Text> {

   public sentimentAnalyzer() throws IOException {
	this.negWords = fileHasher.fileToHashTable("assets/strongNegWords.txt");
	this.posWords = fileHasher.fileToHashTable("assets/strongPosWords.txt");
	system.out.println("Tables done hashing");
   }

   public int analyzeString(String str)
   {
	int  stringScore = 0;
	String[] strArr = str.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
	for(String segment : strArr)
	{
		if(posWords.containsKey(segment.hashCode()))
			stringScore++;
		else if(negWords.containsKey(segment.hashCode()))
			stringScore--;
	}
	return listScore;
   }
   public int runCycle(int numberCycles, int tweetsPerCycle) throws InterruptedException, IOException {
	int totalScore = 0;
	for(int i = 0; i < numberCycles; i++){
		list<String> tweetlist = twitterResources.gatherTweets(tweetsPerCycle);
		TimeUnit.SECONDS.sleep(15);
		int score = this.analyzeList(tweetList);
		totalScore += score;
		System.out.println("Analyzer done analyzing test #" + i+1);
		System.out.println("Score " + (i+1) + ": " + score);
		System.out.println("Running Average " + i + ": " + totalScore/(i+1));
		tweetList = null;
		TimeUnit.SECONDS.sleep(15);
	}
	return totalScore;
   }

/*
   @Override
   public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      String line = value.toString();
      String[] fields = line.split("\t");
      if (fields.length > 2) {
         String store = fields[3];
         if (store.equals("Toys") || store.equals("Consumer Electronics")) {
            String sales = fields[4];
            context.write(new Text(store), new Text(sales));
         }
      }
   }
*/
}
//testing push

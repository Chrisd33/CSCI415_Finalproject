

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.util.Hashtable;

public class sentimentReducer extends Reducer<Text, Text, Text, Text>  {
        BigDecimal runningTotal = new BigDecimal(0.0);
        double numTweets = 0.0;
	Hashtable sentiTable = new Hashtable();
   @Override
   public void setup(Context context) throws IOException
	{
		Path pt = new Path("hdfs:sentiwords.txt");
		FileSystem fs = FileSystem.get(new Configuration());
		BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));
		String line;
		line = br.readLine();
		while(line !null)
		{
			String[] split("\t");
			String word = split[0].substring(0,split[0]-length()-2);
			Double score = Double.parseDouble(split[1]);

			int hashCode = word.hashCode();
			sentiTable.put(hashCode, score);
			line = br.readLine();
		}
	}

   @Override
   public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
      BigDecimal totalScoreUser = BigDecimal.ZERO;
      double numTweetsUser = 0.0;

      for (Text value : values) {               //Finds total value of bytes us$
         BigDecimal tempScore = new BigDecimal(analyzeString(value.toString()));
         totalScoreUser = totalScoreUser.add(tempScore);
         numTweetsUser++;
      }
        runningTotal.add(totalScoreUser);
        numTweets += numTweetsUser;
        Text t = new Text(totalScoreUser.toString());
        context.write(key,t);
}
	public double analyzeString(String str, Hashtable wordList)
	{
	double stringScore = 0;
        String[] strArr = str.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
        for(String segment : strArr)
        {
            int hashedSeg = segment.hashCode();

            if(wordList.containsKey(hashedSeg))
            {
                double value = (double) wordList.get(hashedSeg);
                stringScore += value;
            }
        }
        return stringScore;
	}

  // @Override                                                    //Only prints$
  // protected void cleanup(Context context) throws IOException, InterruptedExc$
//Text t = new Text(topDataTransfer.toString());
 //       context.write(topIpAddress,t);
  // }
}


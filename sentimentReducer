
import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.text.DecimalFormat;
import java.math.BigDecimal;

public class BiggestUserReducer extends Reducer<Text, Text, Text, Text> {
        BigDecimal runningTotal = 0.0;
        double numTweets = 0.0;
        sentimentAnalyzer analyzer = new sentimentAnalyzer();

   @Override
   public void reduce(Text key, Iterable<Text> values, Context context) throws $
      BigDecimal totalScoreUser = BigDecimal.ZERO;
      double numTweetsUser = 0.0;
      for (Text value : values) {               //Finds total value of bytes us$
         BigDecimal tempScore = new BigDecimal(analyzer.analyzeString(value.toS$
         totalScoreUser = totalScoreUser.add(tempScore);
         numTweetsUser++;
      }
        runningTotal.add(totalScoreUser);
        numTweets += numTweetsUser;
        Text t = new Text(totalScoreUser.toString());
        context.write(key,t);
}
  // @Override                                                    //Only prints$
  // protected void cleanup(Context context) throws IOException, InterruptedExc$
//Text t = new Text(topDataTransfer.toString());
 //       context.write(topIpAddress,t);
  // }
}


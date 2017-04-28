
import java.io.IOException;
import java.math.BigDecimal;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class sentimentMapper extends Mapper<LongWritable, Text, Text, Text> {

   @Override
   public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      String line = value.toString();
      String[] fields = line.split("\t");
      String twitterId = fields[0];                     //IP is  first in array
      String tweet = fields[1];

      context.write(new Text(twitterId), new Text(tweet));
   }
}




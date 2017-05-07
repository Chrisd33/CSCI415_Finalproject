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
      if(fields.length > 1){
      	String timeString = fields[0];
      	String tweet = fields[1];
      	long time = Integer.parseInt(timeString);
      	Text timeText = new Text("failure");
                if(time > 060000 && time < 120000)
                {
                    timeText = new Text("6 AM to 12 pm");
                }
                else if(time > 120000 && time < 180000)
                {
                    timeText = new Text("12 PM to 6 PM");
                }
                else if(time > 180000 && time < 240000)
                {
                    timeText = new Text("6 PM to 12 AM");
                }
		else
		{
		     timeText = new Text("12 AM to 6 AM");
		}
      context.write(new Text(timeText), new Text(tweet));
   }
}
}




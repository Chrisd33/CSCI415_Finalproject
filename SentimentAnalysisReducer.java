import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.text.DecimalFormat;
import java.math.BigDecimal;

public class SalesTestReducer extends Reducer<Text, Text, Text, Text> {

   @Override
   public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
      BigDecimal totalValue = BigDecimal.ZERO;
      for (Text value : values) {
         BigDecimal bigDecimalValue = new BigDecimal(value.toString());
         totalValue = totalValue.add(bigDecimalValue);
      }
      Text t = new Text(totalValue.toString());
      context.write(key, t);
   }
}

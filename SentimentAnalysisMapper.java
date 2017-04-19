import java.io.IOException;
import java.math.BigDecimal;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SalesTestMapper extends Mapper<LongWritable, Text, Text, Text> {

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
}
//testing push

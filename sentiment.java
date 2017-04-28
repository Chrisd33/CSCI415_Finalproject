
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class sentiment {
   public static void main(String[] args) throws Exception {
      if (args.length != 2) {
         System.err.println("Usage: sentiment analysis <input path> <output pat$
         System.exit(-1);
      }

      Job job = new Job();
      job.setJarByClass(sentiment.class);
      job.setJobName("sentimentAnalysis");

      FileInputFormat.addInputPath(job, new Path(args[0]));
      FileOutputFormat.setOutputPath(job, new Path(args[1]));

      job.setMapperClass(sentimentMapper.class);
      job.setReducerClass(sentimentReducer.class);

      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(Text.class);

      System.exit(job.waitForCompletion(true) ? 0 : 1);
   }
}





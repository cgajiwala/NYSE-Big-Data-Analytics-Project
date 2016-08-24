package com.packetnode;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;     



public class StockVolumeAggregate extends Configured implements Tool {

//Note: Every mapper always has first two classes in this form (LongWritable, Text)

	//Rule Input (K,V)
	//Key - Should always be LongWritable
	//Value- Should always be Text


	public static class Map extends
			Mapper<LongWritable, Text, Text, DoubleWritable> {

//Rule Input (K,V)
//Key - Should always be LongWritable
//Value- Should always be Text

		
           
		
		public void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			                                  
			// Date,Open,High,Low,Close,Volume,Adj Close
			//2010-10-18,40.66,41.74,40.44,41.49,10620000,41.49
            //2010-10-15,40.92,41.11,40.40,40.62,9023100,40.62
            //2010-10-14,41.15,41.37,40.96,41.02,6750300,41.02
		    // 2010-10-18,40.66,41.74,40.44,41.49,10620000,41.49 
		
		   try {
               String[] tokens = value.toString().split(",");  
				//System.out.println(value);
				
				
               Text text = new Text(tokens[0]);
				
			   double v = Double.valueOf(tokens[6]);
			   
			   //long is converted to hadoop datatype i.e LongWritable
			   DoubleWritable volume = new DoubleWritable(v);
			 
			   context.write(text, volume);  
                //Stock_symbol as key and volume as the value for each day			   
			   
		   }
		   
		   catch(NumberFormatException e) {
			   System.out.println(e);
		   }  
		}
		
	
}


public static class Reduce extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
	
	
	public void reduce(Text key, Iterable<DoubleWritable> values,
			Context context) throws IOException, InterruptedException {

		int sum = 0;
		for (DoubleWritable val : values) {
			sum += val.get();
		}
		context.write(key, new DoubleWritable(sum));
	}
	
}


       public static void main(String[] args) throws Exception {
		int ret = ToolRunner.run(new StockVolumeAggregate(), args);
		System.exit(ret);
	   }
	
	
	//@Override
	public int run(String[] args) throws Exception {
		Job job = new Job();
		job.setJarByClass(StockVolumeAggregate.class);
		job.setJobName("stockvolumeagg");

		
		//NB: Setting output key class as LongWritable and value also as LongWritable
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);

		job.setMapperClass(Map.class);
		job.setCombinerClass(Reduce.class);
		job.setReducerClass(Reduce.class);

		
		//This step gives the output and input format of the files.
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		//FileInputFormat.setInputPaths(job, new Path("C:/cygwin64/usr/local/hadoop-0.20.2/input_stock/table.csv_s=BP"));
		//FileOutputFormat.setOutputPath(job, new Path("C:/cygwin64/usr/local/hadoop-0.20.2/out_stock1"));

		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}

	}

	

	

	
	

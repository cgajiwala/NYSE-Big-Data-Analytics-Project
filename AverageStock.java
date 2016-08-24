package com.packetnode;

import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;

import java.text.DecimalFormat;

public class AverageStock extends Configured implements Tool {


public static class Map extends
		Mapper<LongWritable, Text, Text, FloatWritable> {


	
       
	
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
	
		//key is something we don't know.
		
		// Date,Open,High,Low,Close,Volume,Adj Close
		//2010-10-18,40.66,41.74,40.44,41.49,10620000,41.49
        //2010-10-15,40.92,41.11,40.40,40.62,9023100,40.62
        //2010-10-14,41.15,41.37,40.96,41.02,6750300,41.02
	    // 2010-10-18,40.66,41.74,40.44,41.49,10620000,41.49 
	
	   try {
           String[] tokens = value.toString().split(",");  
	
           

			
             Text date = new Text(tokens[0]);
             
           Text word = new Text();
          
			//LongWritable date = new LongWritable(d);
           
			
             float open = Float.valueOf(tokens[1]);
				float close = Float.valueOf(tokens[4]);
		   		
				float a = (open+close)/2;
             
				FloatWritable average = new FloatWritable(a);
		   		//word.set(new DecimalFormat("0.##").format((double) change) + "%");
		   		
		   		
		   //long volume = Long.valueOf(tokens[5]);
		  
            	
		   //LongWritable turnover = new LongWritable(var);
		 
		   context.write(date, average);  
            //Date as key and turnover as the value for each day			   
		   
	   }
	   
	   catch(NumberFormatException e) {
		   System.out.println(e);
	   }  
	}
	
	 
	
	


}




public static void main(String[] args) throws Exception {
	int ret = ToolRunner.run(new AverageStock(), args);
	System.exit(ret);
}

//@Override
public int run(String[] args) throws Exception {
	Job job = new Job();
	job.setJarByClass(AverageStock.class);
	job.setJobName("AverageStock");

	//job.setOutputKeyClass(Text.class);
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(FloatWritable.class);

	job.setMapperClass(Map.class);
	//job.setCombinerClass(Reduce.class);
	//job.setReducerClass(Reduce.class);

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


//Map_Reduce : Word Count in Java
//Don't import Lib files for File Formats(import from mapred lib).

//Note: Convert this java file into a jar file and run it into Hadoop using the following command: 
// $ hadoop jar converted_MapReduce_jar_file input_file_location output_file_location_HDFS

//Combiner comes before partitioner because it acts like a mini reduce.
//Libraries.

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Partitioner;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;


//WordCount using MapReduce.

//Main Class

public class MapReduceWordCountPartitioner {
	
	//Mapper Class
	
	//The mapper takes input from the data(i.e., the Text file in this case.)
	
	//The key and value for the mapper class in TextInputFormat is key: LongWritable(buffer offset) 
	//value: Text(Lines). 
	
	// Buffer Offset   Lines
	// 0              hi there.
	// 9              How have you been?
	//27              hi there.I have been good.
	
	//The output of the mapper is key: Text(tokens), values: IntWritable( new intwritable(1)).
	
	//The Reporters could be used by Mappers and Reducers to report the progress.
	
	//Parameters for the mapper interface depends on the business logic.
	
	public static class Map extends MapReduceBase implements Mapper<LongWritable,Text,Text,IntWritable>{
		
		//Mapper Method
		
		public void map(LongWritable key, Text value,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			String Line = value.toString(); //Creates a string representation of the object.
			StringTokenizer tokenizer = new StringTokenizer(Line); 
			while(tokenizer.hasMoreTokens()){
				value.set(tokenizer.nextToken());//Divides the line into token(Creates a record 
				                                 //for each word) and sets the value accordingly.
				output.collect(value, new IntWritable(1));  // Collecting Output from the Mapper.
			}	
		}	
	}
	
   // The output of mapper :
	
	//hi,1 
	//there,1
	//.,1
	//How,1 and so on.

	//Reducer Class
	
	//The o/p of mapper will undergo a sort and shuffle phase. So the o/p will be value,list[new intwritable]. 
	//It Depends on the no. of times the words has been repeated. That is:
	
	//hi,[1,1]
	//there,[1,1]
	//.,[1,1,1] and so on.
	
	//This o/p is then passed to the reducer. Therefore the i/p of reducer is Text(token),list(IntWritables):
	
	public static class Partition implements Partitioner<Text, IntWritable>{

	@Override
	public void configure(JobConf arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPartition(Text key, IntWritable value, int numPartitions) {
		String key_lower =key.toString().toLowerCase();
		if(key_lower.equals("gautham")){
			return 0;
		}
		else if(key_lower.equals("tell")){
			return 1;
		}
	else if (key_lower.equals("to")){
		return 2;
	}
	else 
	{
		return 3;
		}
	}}
	
	public static class Reduce extends MapReduceBase implements Reducer<Text,IntWritable,Text,IntWritable>{

		//Reducer Method
		
		public void reduce(Text key, Iterator<IntWritable> values,
				OutputCollector<Text, IntWritable> output, Reporter reporter)
				throws IOException {
			int sum = 0;
			while(values.hasNext()){
				sum+=values.next().get(); //Getting Values in the list and summing it.
			}
			output.collect(key, new IntWritable(sum)); // Collecting Output from the Reducer
		}
	}
	
	//Main Method in MapReduce(also known as driver) used for configuration and running MapReduce.
	
	public static void main(String args[]) throws Exception{
		
		JobConf conf = new JobConf(MapReduceWordCountPartitioner.class); //JobConf will tell the Hadoop Framework 
		                                                                 //to execute MR Job.
		conf.setJobName("WordCount"); //Set the job's new name.
		
		conf.setNumReduceTasks(6);
		
		conf.setOutputKeyClass(Text.class); //Set the format of the Output "key" (o/p of Reducer)
		conf.setOutputValueClass(IntWritable.class); // Set the format of the Output "value" (o/p of Reducer)
		
		conf.setMapperClass(Map.class); // Assign the Class used for Mapper
		conf.setReducerClass(Reduce.class); // Assign the Class used for Reducer
		
		conf.setPartitionerClass(Partition.class);
		
		conf.setInputFormat(TextInputFormat.class); //Assign the format of the input file(Text in this program)
		conf.setOutputFormat(TextOutputFormat.class); //Assign the format you want to store the 
		                                              //output file(Text in this program)
		FileInputFormat.setInputPaths(conf, new Path(args[0])); //The file on which you want to run this MapReduce Job
		                                                     // Hadoop will not know, specify input.
		FileOutputFormat.setOutputPath(conf, new Path(args[1])); //After running MapReduce in Hadoop, where 
		                                                         //do you want to store the reducer output in HDFS ?		
		JobClient.runJob(conf); //Run MapReduce Job.
		
	}
}

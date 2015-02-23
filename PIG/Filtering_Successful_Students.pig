student = LOAD '/PIG_TESTS_ALL/PIG_Dataset/student' as (name:chararray,roll:int);
results = LOAD '/PIG_TESTS_ALL/PIG_Dataset/results/' as (id:int,grade:chararray);
join_raw_data = JOIN results by id,student by roll; 
filtering = FILTER join_raw_data by grade == 'pass'; 
displaying = FOREACH filtering generate $2,$1; 
dump displaying;

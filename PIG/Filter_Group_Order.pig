load_data = LOAD '/PIG_TEST1/Test1_PIG/' using PigStorage(' ') as (language:chararray,website:chararray,pages:int,roll:int);
filtered_data = FILTER load_data by language == 'en';
group_data = GROUP filtered_data by website;   
results = FOREACH group_data generate group,SUM(filtered_data.pages);
ordered_data = ORDER results by $1 DESC;
dump ordered_data; 

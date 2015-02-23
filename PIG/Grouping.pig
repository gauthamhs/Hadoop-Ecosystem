load_data = load '/PIG_TESTS_ALL/PIG_TEST1/Test1_PIG' using PigStorage(' ') as (f1:int,f2:int,f3:int,f4:int);
group_data = group load_data by f1;
dump group_data;

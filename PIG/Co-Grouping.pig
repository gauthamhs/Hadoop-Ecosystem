a = load '/PIG_TEST/Test1_PIG' as (name:chararray,roll_no:int,gpa:float);
dump a;
b = load '/PIG_TEST/Test2_PIG' as (name:chararray,id:int);
dump b;
c = cogroup a by name, b by name;
dump c;

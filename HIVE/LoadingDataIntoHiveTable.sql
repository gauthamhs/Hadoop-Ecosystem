show databases;
use batch19;
show tables;
create table employee_data1(name string, salary float, address string) 
row format delimited
fields terminated by ',';
load data local inpath '/home/cloudera/Desktop/HIVE/Hive_Files/emp1.txt' into table employee_data1;

show tables;
select * from employee_data1;

Note: If the file is already in HDFS, you dont need to use the local keyword. 
Also when you load a file, the file gets moved from the hdfs to the table that is using the file. So, be careful.
When you want to use the data in the file without moving the file to the HIVE table, use an external HIVE table.


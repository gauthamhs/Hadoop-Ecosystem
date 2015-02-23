show databases;
use batch2;
create table employee(name string, salary float, place string)
partitioned by (country string, state string)
row format delimited
fields terminated by ',';

load data local inpath '/home/cloudera/Desktop/HIVE/Hive_Files/emp2.txt' into table employee
partition(country='India',state='Karnataka');

select * from employee where country = 'India' and state = 'Karnataka';



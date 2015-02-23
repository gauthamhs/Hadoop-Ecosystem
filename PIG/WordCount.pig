load_data = LOAD '/PIG_TESTS_ALL/WordCount' as (line);
tokenizing_data = FOREACH load_data generate flatten(TOKENIZE(line)) as word;
group_data = GROUP tokenizing_data by word;
Result = FOREACH group_data generate group,COUNT(tokenizing_data);
dump Result;

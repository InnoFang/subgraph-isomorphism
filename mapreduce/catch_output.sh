echo '=== list all of the folder with the output content ==='
hadoop fs -ls hdfs://localhost:9000/user/innofang/output/
echo '\n'
read -p 'Which folder have the content you want to catch?' folder
echo '=== list all of the file with the output content ==='
hadoop fs -ls hdfs://localhost:9000/user/innofang/output/$folder
echo '\n'
read -p 'Which file have the content you want to catch?' file
hadoop fs -text hdfs://localhost:9000/user/innofang/output/$folder/$file
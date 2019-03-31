echo '=== Step one: Generate the jar file ==='
mvn clean package -DskipTests
echo '\n'

echo '=== Step two: Upload the jar file to the HDFS ==='
scp target/mapreduce-1.0.jar innofang@localhost:~/lib
echo '\n'

echo '=== Step three: Run the specify class/program ==='
hadoop jar /home/innofang/lib/mapreduce-1.0.jar io.github.innofang.UllmannDriver hdfs://localhost:9000/resources/graphDB/Q4.my hdfs://localhost:9000/output/subgraph-isomorphism/ullmann

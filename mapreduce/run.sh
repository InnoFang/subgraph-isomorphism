#!/usr/bin/env bash
echo '=== Step one: Generate the jar file ==='
mvn clean package -DskipTests
echo '\n'

echo '=== Step two: Upload the jar file to the HDFS ==='
scp target/mapreduce-1.0.jar innofang@localhost:~/lib
echo '\n'

echo '=== Step three: Run the specify class/program ==='
hadoop jar /home/innofang/lib/mapreduce-1.0.jar io.github.innofang.VF2Driver \
-s hdfs:////localhost:9000/datasets/ermil-Eu-core/email-Eu-core-department-labels.txt \
-t hdfs:////localhost:9000/datasets/emil-Eu-core/Q4-10-unweighted.my \
-o hdfs:////localhost:9000/output/subgraph-isomorphism/vf2

#!/usr/bin/env bash
echo '=== Step one: Generate the jar file ==='
mvn clean package -DskipTests
echo '\n'

echo '=== Step two: Upload the jar file to the HDFS ==='
scp target/mapreduce-1.0.jar innofang@localhost:~/lib
echo '\n'

echo '=== Step three: Run the specify class/program ==='
hadoop jar /home/innofang/lib/mapreduce-1.0.jar io.github.innofang.VF2Driver \
-t hdfs://localhost:9000/user/innofang/datasets/email-Eu-core/email-Eu-core.txt \
-s hdfs://localhost:9000/user/innofang/datasets/email-Eu-core/V3E3.txt \
-o hdfs://localhost:9000/user/innofang/output/vf2

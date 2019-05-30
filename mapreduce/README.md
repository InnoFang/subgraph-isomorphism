## How to run on Hadoop

**PS** You should put the datasets on HDFS first.

 + **Step one: Generate the jar file**
    
    run the command `mvn clean package -DskipTests` 
 and it can generate a jar file (`mapreduce-1.0.jar` for this project) under the `/target` folder
 
 + **Step two: Upload the jar file to the HDFS**
 
    run the command `scp target/mapreduce-1.0.jar innofang@localhost:~/lib`
    
    the jar file is under the target folder, and the lib folder must be created on HDFS before you run this command,
    `innofang@localhost:` is my username, you should change into yours.
 
 + **Step three: Run the specify class/program**
 
    Open terminator and run the command `hadoop jar <your jar file location> <the class with main method> <input file path on HDFS> <output file path on HDFS>`
    
    For example, for me, if I want to run the `UllmannDriver.java` (which is to run Ullmann Algorithm), I would run the command as follow:
    
    `hadoop jar /home/innofang/lib/mapreduce-1.0.jar io.github.innofang.UllmannDriver hdfs://localhost:9000/resources/graphDB/Q4.my hdfs://localhost:9000/output/subgraph-isomorphism/ullmann`

## Make it run easily

Use shell to make it easily, see [run.sh](run.sh), also you can catch the output content by [catch-output.sh](catch_output.sh).

Please modify it according to your truly situation.
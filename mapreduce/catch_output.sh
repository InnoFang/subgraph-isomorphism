echo '=== list all of the folder with the output content ==='
hadoop fs -ls /output/subgraph-isomorphism/
echo '\n'
read -p 'Which folder have the content you want to catch?' folder
echo '=== list all of the file with the output content ==='
hadoop fs -ls /output/subgraph-isomorphism/$folder
echo '\n'
read -p 'Which file have the content you want to catch?' file
hadoop fs -text /output/subgraph-isomorphism/$folder/$file
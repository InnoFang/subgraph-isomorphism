package io.github.innofang.util;


import org.apache.commons.cli.*;

import java.util.HashMap;

public class ArgsParser {

    private ArgsParser(){}
    private static HashMap<String, String> arguments = new HashMap<>();

    public static final String SOURCE = "source";
    public static final String TARGET = "target";
    public static final String OUTPUT = "output";

    public static ArgsParser parse(String[] args) {

        Options options = new Options();
        options.addRequiredOption("s", "source", true,
                "the source graph file path on HDFS (Required)");
        options.addRequiredOption("t", "target", true,
                "the target graph file path on HDFS (Required)");
        options.addRequiredOption("o", "output", true,
                "the output folder path of MapReduce result on HDFS (Required)");
        options.addOption("h", "help", false,
                "show this help message and exit program");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
            arguments.put(SOURCE, cmd.getParsedOptionValue("s").toString());
            arguments.put(TARGET, cmd.getParsedOptionValue("t").toString());
            arguments.put(OUTPUT, cmd.getParsedOptionValue("o").toString());
        } catch (ParseException e) {
            e.printStackTrace();
            formatter.printHelp("sub-Graph Isomorphism", options, true);
            System.exit(1);
        }

        if (cmd.hasOption("h")) {
            formatter.printHelp("sub-Graph Isomorphism", options, true);
            System.exit(0);
        }

        return new ArgsParser();
    }

    public String getSourceGraphFilePath() {
        return arguments.get(SOURCE);
    }

    public String getTargetGraphFilePath() {
        return arguments.get(TARGET);
    }

    public String getOutputFolderPath() {
        return arguments.get(OUTPUT);
    }
}

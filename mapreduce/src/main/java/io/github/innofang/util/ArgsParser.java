package io.github.innofang.util;


import org.apache.commons.cli.*;

import java.util.HashMap;

public class ArgsParser {
    public static HashMap<String, String> parse(String[] args) {
        HashMap<String, String> arguments = new HashMap<>();

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
            arguments.put("source", cmd.getParsedOptionValue("s").toString());
            arguments.put("target", cmd.getParsedOptionValue("t").toString());
            arguments.put("output", cmd.getParsedOptionValue("o").toString());
        } catch (ParseException e) {
            e.printStackTrace();
            formatter.printHelp("sub-Graph Isomorphism", options, true);
            System.exit(1);
        }

        if (cmd.hasOption("h")) {
            formatter.printHelp("sub-Graph Isomorphism", options, true);
            System.exit(0);
        }

        return arguments;
    }
}

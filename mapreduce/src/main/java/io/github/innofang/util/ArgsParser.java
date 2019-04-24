package io.github.innofang.util;


import org.apache.commons.cli.*;

import java.util.HashMap;

public class ArgsParser {

    private ArgsParser() { }

    private static HashMap<String, String> arguments = new HashMap<>();

    private static final String SOURCE = "source";
    private static final String TARGET = "target";
    private static final String OUTPUT = "output";

    public static ArgsParser parse(String[] args) {

        // commons-cli 1.2

        Options options = new Options();

        Option source = new Option("s", "source", true,
                "the source graph file path on HDFS (Required)");
        source.setRequired(true);

        Option target = new Option("t", "target", true,
                "the target graph file path on HDFS (Required)");
        target.setRequired(true);

        Option output = new Option("o", "output", true,
                "the output folder path of MapReduce result on HDFS (Required)");
        output.setRequired(true);

        Option help = new Option("h", "help", false,
                "show this help message and exit program");

        options.addOption(source)
                .addOption(target)
                .addOption(output)
                .addOption(help);

        CommandLineParser parser = new BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
            arguments.put(SOURCE, cmd.getOptionValue("s"));
            arguments.put(TARGET, cmd.getOptionValue("t"));
            arguments.put(OUTPUT, cmd.getOptionValue("o"));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
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

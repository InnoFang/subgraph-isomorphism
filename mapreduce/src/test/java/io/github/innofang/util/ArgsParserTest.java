package io.github.innofang.util;

import org.junit.Test;

import java.util.ArrayList;

public class ArgsParserTest {

    @Test
    public void parse() {
        ArrayList<String[]> args = new ArrayList<>();
        args.add(new String[]{"-h"});
        args.add(new String[]{"-s/home/source"});
        args.add(new String[]{"-s/home/source", "-t/home/innofang/target", "-o/output"});
        args.add(new String[]{"-s=/home/source", "-t=/home/innofang/target", "-o=/output"});

        System.out.println(ArgsParser.parse(args.get(3)));
    }

}
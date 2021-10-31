package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public  class Utils {
    public static Pattern integerConstant = Pattern.compile("0|(-?[1-9][0-9]*)");
    public static Pattern stringConstant = Pattern.compile("\"([0-9A-Za-z])+\"");
    public static Pattern booleanConstant = Pattern.compile("true|false");
    public static Pattern identifier = Pattern.compile("[a-zA-z][0-9a-zA-Z]*");
    public static List<String> separatorsRegex = initializeSeparatorsRegex();
    public static List<String> separators = initializeSeparators();


    private static List<String> initializeSeparators()
    {
        List<String> sep = new ArrayList<>();
        sep.add("/*");
        sep.add("\\*");
        sep.add("*");
        sep.add("==");
        sep.add(">=");
        sep.add("<=");
        sep.add(";");
        sep.add("=");
        sep.add("(");
        sep.add(")");
        sep.add("+");
        sep.add("-");
        sep.add("/");
        sep.add("%");
        sep.add(">");
        sep.add("{");
        sep.add("}");
        sep.add("let");
        sep.add("char");
        sep.add("int");
        sep.add("and");
        sep.add("or");
        sep.add("check");
        sep.add("if");
        sep.add("else");
        sep.add("array");
        sep.add("go");
        sep.add("else");
        sep.add("print");
        sep.add("read");
        sep.add("exit");
        sep.add(":");
        return  sep;
    }
    private static List<String> initializeSeparatorsRegex()
    {
        List<String> sep = new ArrayList<>();
        sep.add("/\\*");
        sep.add("\\*\\");
        sep.add("\\*");
        sep.add("==");
        sep.add(">=");
        sep.add("<=");
        sep.add(";");
        sep.add("=");
        sep.add("\\(");
        sep.add("\\)");
        sep.add("\\+");
        sep.add("-");
        sep.add("/");
        sep.add("%");
        sep.add(">");
        sep.add("\\{");
        sep.add("\\}");
        sep.add("let");
        sep.add("char");
        sep.add("int");
        sep.add("and");
        sep.add("or");
        sep.add("check");
        sep.add("if");
        sep.add("else");
        sep.add("array");
        sep.add("go");
        sep.add("else");
        sep.add("print");
        sep.add("read");
        sep.add("exit");
        sep.add(":");
        return  sep;
    }
}

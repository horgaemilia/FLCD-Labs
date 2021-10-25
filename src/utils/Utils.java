package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public  class Utils {
    public static Pattern integerConstant = Pattern.compile("0|(-?[1-9][0-9]*)");
    public static Pattern stringConstant = Pattern.compile("('[0-9A-Za-z]')+");
    public static Pattern booleanConstant = Pattern.compile("true|false");
    public static Pattern identifier = Pattern.compile("[a-zA-z][0-9a-zA-Z]*");
    public static List<String> separators = Arrays.asList("\\/\\*","\\*\\/","#",";",",","\\(","\\)","\\{","\\}",":"," ");
}

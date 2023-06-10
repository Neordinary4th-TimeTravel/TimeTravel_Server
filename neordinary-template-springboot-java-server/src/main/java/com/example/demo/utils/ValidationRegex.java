package com.example.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    public static boolean isRegexTime(String target) {
        String[] tar = target.split("T",1);
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        try{
            format.parse(tar[0]);
            return true;
        }
        catch(ParseException e)
        {
            return false;
        }
    }
}


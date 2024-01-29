package org.john.course;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CourseType {
    COMP_100("0", "COMP 100"),
    COMP_110("1", "COMP 110"),
    COMP_110L("2", "COMP 110L"),
    COMP_111B("3", "COMP 110B"),
    COMP_111BL("4", "COMP 111BL"),
    COMP_122("5", "COMP 122"),
    COMP_122L("6", "COMP 122L"),
    COMP_182("7", "COMP 182"),
    COMP_182L("8", "COMP 182L"),
    COMP_222("9", "COMP 222"),
    COMP_256("10", "COMP 256"),
    COMP_256L("11", "COMP 256L"),
    COMP_282("12", "COMP 282"),
    COMP_310("13", "COMP 310"),
    COMP_322("14", "COMP 322"),
    COMP_322L("15", "COMP 322L"),
    COMP_324("16", "COMP 324"),
    COMP_333("17", "COMP 333"),
    COMP_380("18", "COMP 380"),
    COMP_380L("19", "COMP 380L"),
    COMP_424("20", "COMP 424"),
    COMP_429("21", "COMP 429"),
    COMP_430("22", "COMP 430"),
    COMP_440("23", "COMP 440"),
    COMP_467("24", "COMP 467"),
    COMP_469("25", "COMP 469"),
    COMP_482("26", "COMP 482"),
    COMP_484("27", "COMP 484"),
    COMP_484L("28", "COMP 484L"),
    COMP_485("29", "COMP 485"),
    COMP_488("30", "COMP 488"),
    COMP_491("31", "COMP 491"),
    COMP_491L("32", "COMP 491L"),
    COMP_492("33", "COMP 492"),
    COMP_522("34", "COMP 522"),
    COMP_529("35", "COMP 529"),
    COMP_529L("36", "COMP 529L"),
    COMP_541("37", "COMP 541"),
    COMP_542("38", "COMP 542"),
    COMP_565("39", "COMP 565"),
    COMP_583("40", "COMP 583"),
    COMP_584("41", "COMP 584"),
    COMP_585("42", "COMP 585"),
    COMP_589("43", "COMP 589"),
    COMP_610("44", "COMP 610"),
    COMP_620("45", "COMP 620"),
    COMP_680("46", "COMP 680"),
    COMP_696C("47", "COMP 696C"),
    COMP_698C("48", "COMP 698C")
    ;

    private final String PositionKey;
    private final String cleanName;

    CourseType(String positionKey, String cleanName) {
        this.PositionKey = positionKey;
        this.cleanName = cleanName;
    }

    public String getPositionKey() {
        return PositionKey;
    }

    public String getCleanName() {
        return cleanName;
    }

    public static final HashMap<String, CourseType> VALUES = (HashMap<String, CourseType>)
            Arrays.stream(values()).collect(Collectors.toMap(CourseType::getCleanName, Function.identity()));

    public static CourseType getCourse(String cleanName) {
        return VALUES.get(cleanName.toUpperCase());
    }
}

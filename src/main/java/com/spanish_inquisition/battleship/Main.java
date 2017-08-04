package com.spanish_inquisition.battleship;

import java.util.Arrays;

/**
 * Created by Patka on 2017-08-04.
 */
public class Main {
    public static void main(String[] args) {
        String msg = "[10, 20]";
        String destroyedFieldsString = msg.substring(msg.indexOf('[') + 1, msg.indexOf(']'));
        String[] destroyedFieldsToParse = destroyedFieldsString.split(",");
        Arrays.stream(destroyedFieldsToParse).map(field -> Integer.parseInt(field.trim())).forEach(field -> System.out.println(field));


    }
}

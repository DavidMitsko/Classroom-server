package by.mitsko.classroom.service.util;

import by.mitsko.classroom.entity.Action;
import by.mitsko.classroom.exception.InvalidDataException;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Converter {

    public static List<Action> convertStringToActionsList(String actionsString) {
        String[] strings = actionsString.split(",");
        List<Action> actions = new LinkedList<>();
        for (String str: strings) {
            actions.add(Action.valueOf(str));
        }
        return actions;
    }

    public static List<LocalDateTime> convertStringToDate(String time) {
        String[] strings = time.split(":");

        if(strings.length > 2) {
            throw new InvalidDataException("Invalid time period");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//"dd.MM.yyyy");

        List<LocalDateTime> dates = new LinkedList<>();

        dates.add(LocalDate.parse(strings[0], formatter).atStartOfDay());
        dates.add(LocalDate.parse(strings[1], formatter).atTime(23, 59, 59));

//        for (String date : strings) {
//            dates.add(LocalDate.parse(date, formatter).atStartOfDay());
//        }

        return dates;
    }
}

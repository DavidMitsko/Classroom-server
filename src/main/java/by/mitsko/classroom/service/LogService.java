package by.mitsko.classroom.service;

import by.mitsko.classroom.entity.Action;
import by.mitsko.classroom.entity.Log;
import by.mitsko.classroom.entity.User;

import java.util.List;

public interface LogService {
    void add(Action action, User user);

    List<Log> getAllStudentsLogs(Long studentId, String time);

    List<Log> getFilteredStudentsLogs(Long studentId, String actionsString, String time);
}

package by.mitsko.classroom.service.impl;

import by.mitsko.classroom.entity.Action;
import by.mitsko.classroom.entity.Log;
import by.mitsko.classroom.entity.User;
import by.mitsko.classroom.repository.LogRepository;
import by.mitsko.classroom.repository.UserRepository;
import by.mitsko.classroom.service.LogService;
import by.mitsko.classroom.service.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {
    private final UserRepository userRepository;
    private final LogRepository logRepository;

    @Autowired
    public LogServiceImpl(UserRepository userRepository, LogRepository logRepository) {
        this.userRepository = userRepository;
        this.logRepository = logRepository;
    }

    @Override
    public void add(Action action, User user) {
        saveLog(new Log(action, user));
    }

    @Override
    public List<Log> getAllStudentsLogs(Long studentId, String time) {
        User student = userRepository.getById(studentId);

        List<Log> logs = logRepository.getAllByUser(student);
        return getLogs(time, logs);
    }

    @Override
    public List<Log> getFilteredStudentsLogs(Long studentId, String actionsString, String time) {
        User student = userRepository.getById(studentId);

        List<Log> logs = logRepository.getAllByActionIsInAndUser
                (Converter.convertStringToActionsList(actionsString), student);
        return getLogs(time, logs);
    }

    private List<Log> getLogs(String time, List<Log> logs) {
        if (!time.isEmpty()) {
            List<LocalDateTime> dates = Converter.convertStringToDate(time);
            logs = logs.stream().filter(log -> log.getDate().compareTo(dates.get(0)) > 0
                    && log.getDate().compareTo(dates.get(1)) < 0).collect(Collectors.toList());
        }
        return logs;
    }

    private void saveLog(Log log) {
        logRepository.save(log);
    }
}

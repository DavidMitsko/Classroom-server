package by.mitsko.classroom.scheduler;

import by.mitsko.classroom.entity.*;
import by.mitsko.classroom.repository.LogRepository;
import by.mitsko.classroom.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@EnableScheduling
@Component
public class ScheduledReport {
    private final ReportRepository reportRepository;
    private final LogRepository logRepository;

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender mailSender;

    private final String subject = "Report";

    @Autowired
    public ScheduledReport(ReportRepository reportRepository, LogRepository logRepository,
                           JavaMailSender mailSender) {
        this.reportRepository = reportRepository;
        this.logRepository = logRepository;
        this.mailSender = mailSender;
    }

    @Scheduled(cron = "0 0 15 ? * *")
    public void prepareReports() {
        List<Report> reports = reportRepository.findAll();

        LocalDateTime today = LocalDateTime.now(ZoneId.of("UTC+3"));
        if (today.getDayOfWeek() != DayOfWeek.SUNDAY) {
            reports = reports.stream().filter((report) -> report.getFrequency() == Frequency.DAY)
                    .collect(Collectors.toList());
        }
        for (Report report : reports) {
            sendReport(report, today);
        }
    }

    private void sendReport(Report report, LocalDateTime today) {
        List<Log> logs = logRepository.getAllByDateAfter
                (today.minusDays(report.getFrequency().getAmountOfDays()));

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(report.getRecipient().getEmail());
        mailMessage.setSubject(subject);

        Set<User> users = logs.stream().map((Log::getUser)).collect(Collectors.toSet());

        StringBuilder message = new StringBuilder("You received report a period in " +
                report.getFrequency().getAmountOfDays() + " day(s)\n");
        for (User user : users) {
            List<Log> usersLogs = logs.stream().filter((log) -> log.getUser().equals(user))
                    .collect(Collectors.toList());
            message.append(user.getUsername())
                    .append(": ");
            for (Action action : Action.values()) {
                message.append(action)
                        .append(" - ")
                        .append(count(usersLogs, action))
                        .append(" ");
            }
            message.append("\n");
        }

        mailMessage.setText(message.toString());

        mailSender.send(mailMessage);
    }

    private long count(List<Log> logs, Action action) {
        return logs.stream().filter((log) -> log.getAction() == action).count();
    }
}

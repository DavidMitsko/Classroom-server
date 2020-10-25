package by.mitsko.classroom.service;

import by.mitsko.classroom.entity.Report;

public interface ReportService {
    void setUpReports(Long userId, String frequency);

    void turnOnReports(Long userId, String frequency);

    void turnOffReports(Long userId);

    Report getReportInfo(Long userId);
}

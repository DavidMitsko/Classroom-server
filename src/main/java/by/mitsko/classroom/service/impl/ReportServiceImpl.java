package by.mitsko.classroom.service.impl;

import by.mitsko.classroom.entity.Frequency;
import by.mitsko.classroom.entity.Report;
import by.mitsko.classroom.repository.ReportRepository;
import by.mitsko.classroom.repository.UserRepository;
import by.mitsko.classroom.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void setUpReports(Long userId, String frequency) {
        Report report = reportRepository.getByRecipient(userRepository.getById(userId));
        report.setFrequency(Frequency.valueOf(frequency));
        reportRepository.save(report);
    }

    @Override
    public void turnOnReports(Long userId, String frequency) {
        reportRepository.save(new Report(userRepository.getById(userId), Frequency.valueOf(frequency)));
    }

    @Override
    @Transactional
    public void turnOffReports(Long userId) {
        reportRepository.deleteByRecipient(userRepository.getById(userId));
    }

    @Override
    public Report getReportInfo(Long userId) {
        return reportRepository.getByRecipient(userRepository.getById(userId));
    }
}

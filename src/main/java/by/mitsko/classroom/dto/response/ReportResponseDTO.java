package by.mitsko.classroom.dto.response;

import by.mitsko.classroom.entity.Report;
import lombok.Data;

@Data
public class ReportResponseDTO {
    private String frequency;
    private Boolean enable;

    public ReportResponseDTO(Report report) {
        if(report != null) {
            this.frequency = report.getFrequency().name();
            enable = true;
        } else {
            enable = false;
        }
    }
}

package by.mitsko.classroom.controller;

import by.mitsko.classroom.controller.utils.Converter;
import by.mitsko.classroom.dto.request.ReportRequestDTO;
import by.mitsko.classroom.dto.request.SettingsRequestDTO;
import by.mitsko.classroom.dto.request.UserAuthenticationRequestDTO;
import by.mitsko.classroom.dto.response.LogResponseDTO;
import by.mitsko.classroom.dto.response.ReportResponseDTO;
import by.mitsko.classroom.dto.response.UserResponseDTO;
import by.mitsko.classroom.entity.Report;
import by.mitsko.classroom.entity.Role;
import by.mitsko.classroom.entity.User;
import by.mitsko.classroom.service.LogService;
import by.mitsko.classroom.service.ReportService;
import by.mitsko.classroom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final SimpMessagingTemplate template;
    private final UserService userService;
    private final LogService logService;
    private final ReportService reportService;

    @Autowired
    public UserController(UserService userService, SimpMessagingTemplate template, LogService logService,
                          ReportService reportService) {
        this.userService = userService;
        this.template = template;
        this.logService = logService;
        this.reportService = reportService;
    }

    @PostMapping("signIn")
    public ResponseEntity<?> signIn(@RequestBody UserAuthenticationRequestDTO requestDTO) {
        User user = userService.signIn(requestDTO.getUsername(), Role.valueOf(requestDTO.getRole()), requestDTO.getEmail());
        UserResponseDTO response = new UserResponseDTO(user);

        sendMessage();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("signOut")
    public ResponseEntity<?> signOut(@RequestParam Long userId) {
        userService.signOut(userId);

        sendMessage();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("raiseHand")
    public ResponseEntity<?> raiseHand(@RequestParam Long userId) {
        User user = userService.raiseHand(userId);
        UserResponseDTO response = new UserResponseDTO(user);

        sendMessage();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getAllStudents")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDTO> users = Converter.convertUserToUserResponse(userService.getAllStudents());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("getAuthorizedStudents")
    public ResponseEntity<?> getAuthorizedUsers() {
        List<UserResponseDTO> users = Converter.convertUserToUserResponse(userService.getAuthorizedStudents());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("getAllStudentsLogs")
    public ResponseEntity<?> getAllLogs(@RequestParam Long studentId, @RequestParam String time) {
        List<LogResponseDTO> logs = Converter.convertLogToLogResponse
                (logService.getAllStudentsLogs(studentId, time));
        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @GetMapping("getStudentsLogs")
    public ResponseEntity<?> getLogs(@RequestParam String actions, @RequestParam Long studentId,
                                     @RequestParam String time) {
        List<LogResponseDTO> logs = Converter.convertLogToLogResponse
                (logService.getFilteredStudentsLogs(studentId, actions, time));

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    @PostMapping("changeEmail")
    public ResponseEntity<?> changeEmail(@RequestBody SettingsRequestDTO requestDTO) {
        userService.changeEmail(requestDTO.getUserId(), requestDTO.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("setUpReports")
    public ResponseEntity<?> setUpReports(@RequestBody ReportRequestDTO requestDTO) {
        reportService.setUpReports(requestDTO.getUserId(), requestDTO.getFrequency());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("turnOnReports")
    public ResponseEntity<?> turnOnReports(@RequestBody ReportRequestDTO requestDTO) {
        reportService.turnOnReports(requestDTO.getUserId(), requestDTO.getFrequency());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("turnOffReports")
    public ResponseEntity<?> turnOffReports(@RequestParam Long userId) {
        reportService.turnOffReports(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("getReportInfo")
    public ResponseEntity<?> getReportInfo(@RequestParam Long userId) {
        ReportResponseDTO responseDTO = new ReportResponseDTO(reportService.getReportInfo(userId));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @MessageMapping("/updateAllUsers")
    @SendTo("/topic/members")
    public List<UserResponseDTO> updateAllUsers() {
        return Converter.convertUserToUserResponse(userService.getAuthorizedStudents());
    }

    private void sendMessage() {
        template.convertAndSend("/topic/members",
                Converter.convertUserToUserResponse(userService.getAuthorizedStudents()));
    }
}

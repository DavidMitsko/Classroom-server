package by.mitsko.classroom.controller;

import by.mitsko.classroom.controller.utils.Converter;
import by.mitsko.classroom.dto.request.UserAuthenticationRequestDTO;
import by.mitsko.classroom.dto.response.UserResponseDTO;
import by.mitsko.classroom.entity.User;
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

    @Autowired
    public UserController(UserService userService, SimpMessagingTemplate template ) {
        this.userService = userService;
        this.template = template;
    }

    @PostMapping("signIn")
    public ResponseEntity<?> signIn(@RequestBody UserAuthenticationRequestDTO requestDTO) {
        User user = userService.signIn(requestDTO.getUsername());
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

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers() {
        List<UserResponseDTO> users = Converter.convertUserToUserResponse(userService.getAllUsers());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @MessageMapping("/updateAllUsers")
    @SendTo("/topic/members")
    public List<UserResponseDTO> updateAllUsers() {
        return Converter.convertUserToUserResponse(userService.getAllUsers());
    }

    private void sendMessage() {
        template.convertAndSend("/topic/members", Converter.convertUserToUserResponse(userService.getAllUsers()));
    }
}

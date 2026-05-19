package net.engineeringdigest.project.journalApp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import net.engineeringdigest.project.journalApp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    @Operation(summary = "Chat with AI Assistant")
    public String chat(@RequestBody Map<String, String> request) throws Exception {

        String question = request.get("question");

        return chatService.chatWithEvents(question);
    }
}
package com.example.booking.Controller.Customer;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.FlightRequestPackage.ContactMessageRequest;
import com.example.booking.Entity.Airlines;
import com.example.booking.Entity.ContactMessage;
import com.example.booking.Repository.ContactMessageRepository;
import com.example.booking.Service.ContactMessageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/contact")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactMessageService contactMessageService;

    @PostMapping
    public ResponseEntity<ResponseDto<ContactMessage>> receiveMessage(@RequestBody ContactMessageRequest message) {
        ContactMessage contactMessage=contactMessageService.addContactMessage(message);
        return ResponseConfig.success(contactMessage);
    }
    @GetMapping("/list")
    public  ResponseEntity<ResponseDto<Page<ContactMessage>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size, @RequestParam String search) {
        return ResponseConfig.success(contactMessageService.getAll(page, size,search));
    }
    @PutMapping("/{id}/mark-read")
    public  ResponseEntity<ResponseDto<ContactMessage>> markContactAsRead(@PathVariable Long id) {
        return ResponseConfig.success(contactMessageService.markContactAsRead(id));
    }
    @PostMapping("/reply")
    public ResponseEntity<ResponseDto<String>> reply(@RequestBody ContactMessageRequest message) {
        return ResponseConfig.success((contactMessageService.reply(message)));
    }
}

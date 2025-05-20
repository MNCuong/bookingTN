package com.example.booking.Controller.Flight;

import com.example.booking.Entity.Contact;
import com.example.booking.Entity.ContactRep;
import com.example.booking.Repository.ContactReplyRepository;
import com.example.booking.Repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {
    private final ContactRepository contactRepository;
    private final ContactReplyRepository contactReplyRepository;
    // Tạo ticket mới
    @PostMapping("/contact")
    public Contact createTicket(@RequestBody Contact contact) {
        contact.setStatus("Pending");
        contact.setCreatedAt(LocalDateTime.now());
        return contactRepository.save(contact);
    }

    @GetMapping("/contact")
    public List<Contact> getTickets(@RequestParam Long customerId) {
        return contactRepository.findAll().stream()
                .filter(t -> t.getUserId().equals(customerId))
                .toList();
    }
    @GetMapping("/list")
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    // Lấy chi tiết ticket + danh sách reply
    @GetMapping("/tickets/{id}")
    public Map<String, Object> getTicketDetail(@PathVariable Long id) {
        Contact contact = contactRepository.findById(id).orElseThrow();
        List<ContactRep> replies = contactReplyRepository.findByContactIdOrderByCreatedAtAsc(id);
        return Map.of("contact", contact, "replies", replies);
    }

    @PostMapping("/tickets/{id}/replies")
    public ContactRep postReply(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Contact ticket = contactRepository.findById(id).orElseThrow();
        ContactRep reply = new ContactRep();
        reply.setContact(ticket);
        reply.setSender(body.get("sender"));
        reply.setMessage(body.get("message"));
        reply.setCreatedAt(LocalDateTime.now());

        if ("admin".equals(reply.getSender())) {
            ticket.setStatus("Replied");
        } else {
            ticket.setStatus("Pending");
        }
        contactRepository.save(ticket);
        return contactReplyRepository.save(reply);
    }
}

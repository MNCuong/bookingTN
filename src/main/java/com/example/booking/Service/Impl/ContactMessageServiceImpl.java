package com.example.booking.Service.Impl;

import com.example.booking.DTO.Request.FlightRequestPackage.ContactMessageRequest;
import com.example.booking.Entity.ContactMessage;
import com.example.booking.Exception.BookingException;
import com.example.booking.Repository.ContactMessageRepository;
import com.example.booking.Service.ContactMessageService;
import com.example.booking.Service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ContactMessageServiceImpl implements ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;
    private final EmailService emailService;

    @Override
    public ContactMessage addContactMessage(ContactMessageRequest request) {
        return contactMessageRepository.save(ContactMessage.builder()
                .email(request.getEmail())
                .message(request.getMessage())
                .name(request.getName())
                .status("NEW")
                .object("Customer")
                .create_at(LocalDateTime.now())
                .subject(request.getSubject())
                .build());
    }

    @Override
    public Page<ContactMessage> getAll(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<ContactMessage> contactMessagePage;
        if (search == null || search.isEmpty()) {
            contactMessagePage = contactMessageRepository.findAll(pageable);
        } else {
            contactMessagePage = contactMessageRepository.findByEmailOrderByCustomStatus(search, pageable);
        }
        return contactMessagePage;
    }

    @Override
    public ContactMessage markContactAsRead(long id) {
        Optional<ContactMessage> optionalContact = contactMessageRepository.findById(id);
        ContactMessage contact = optionalContact.get();
        if (contact.getStatus().equals("NEW")) {
            contact.setStatus("READ");
        }
        return contactMessageRepository.save(contact);
    }

    @Override
    public String reply(ContactMessageRequest request) {
        try {
            emailService.replyEmail(request);
            ContactMessage contact = contactMessageRepository.findById(request.getId()).get();
            contact.setStatus("REPLIED");
            contactMessageRepository.save(contact);
        } catch (Exception e) {
            throw new BookingException("Error", "Lỗi trong quá trình phản hồi");
        }
        return "success";
    }

}

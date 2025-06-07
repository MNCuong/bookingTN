package com.example.booking.Service;

import com.example.booking.DTO.Request.FlightRequestPackage.ContactMessageRequest;
import com.example.booking.Entity.ContactMessage;
import org.springframework.data.domain.Page;

public interface ContactMessageService {
    ContactMessage addContactMessage(ContactMessageRequest request);
    Page<ContactMessage> getAll(int page, int size, String search);
    ContactMessage markContactAsRead(long id);
    String reply(ContactMessageRequest request);

}

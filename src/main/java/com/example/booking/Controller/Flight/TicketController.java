package com.example.booking.Controller.Flight;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Response.FlightResponse;
import com.example.booking.DTO.Response.FlightResponsePackage.TicketResponse;
import com.example.booking.Entity.Ticket;
import com.example.booking.Entity.User;
import com.example.booking.Service.TicketService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<Page<TicketResponse>>> getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String search) {
        return ResponseConfig.success(ticketService.getList(page, size, search));
    }

    @PostMapping
    public ResponseEntity<ResponseDto<Ticket>> createTicket(@RequestBody Ticket dto) {
        return ResponseConfig.success(ticketService.createTicket(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<Ticket>> updateTicket(@PathVariable Long id, @RequestBody Ticket dto) {
        return ResponseConfig.success(ticketService.updateTicket(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/passenger/{id}")
    public ResponseEntity<ResponseDto<List<TicketResponse>>> getTicketsByPassenger(@PathVariable Long id) {
        List<TicketResponse> tickets = ticketService.getTicketsByPassengerId(id);
        return ResponseConfig.success(tickets);
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<ResponseDto<List<TicketResponse>>> getTicketsFlight(@PathVariable Long id) {
//        List<TicketResponse> tickets = ticketService.getTicketsFlight(id);
//        return ResponseConfig.success(tickets);
//    }
    @GetMapping("/flight/{id}")
    public ResponseEntity<ResponseDto<List<TicketResponse>>> getTicketsFlight(@PathVariable Long id) {
        return ResponseConfig.success(ticketService.getTicketsFlight(id));
    }

    @GetMapping("/grouped-by-transaction")
    public ResponseEntity<ResponseDto<List<Map<String, Object>>>> getTicketsGroupedByTransaction(HttpServletRequest request) {
        String token = JwtUtil.getTokenFromRequest(request);
        User user=userService.findUserByEmail(jwtUtil.extractUsername(token));
        return ResponseConfig.success(ticketService.getTicketsGroupedByTransaction(user.getId()));
    }
    @PutMapping("/checkin/{id}")
    public ResponseEntity<ResponseDto<String>> checkin(@PathVariable Long id) {
        return  ResponseConfig.success(ticketService.checkin(id));
    }
}


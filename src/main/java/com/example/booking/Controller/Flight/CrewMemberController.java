package com.example.booking.Controller.Flight;


import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.Entity.CrewMember;
import com.example.booking.Service.CrewMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/crew-member")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDto<Page<CrewMember>>> getList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String search) {
        return ResponseConfig.success(crewMemberService.getList(page, size, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<CrewMember>> getCrewMember(@PathVariable Long id) {
        CrewMember savedCrewMember = crewMemberService.getCrewMember(id);
        return ResponseConfig.success(savedCrewMember);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto<CrewMember>> createCrewMember(@RequestBody CrewMember crewMember) {
        CrewMember savedCrewMember = crewMemberService.createCrewMember(crewMember);
        return ResponseConfig.success(savedCrewMember);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto<CrewMember>> updateCrewMember(@PathVariable Long id, @RequestBody CrewMember crewMember) {
        CrewMember updatedCrewMember = crewMemberService.updateCrewMember(id, crewMember);
        return ResponseConfig.success(updatedCrewMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<String>> deleteCrewMember(@PathVariable Long id) {
        return ResponseConfig.success(crewMemberService.deleteCrewMember(id));
    }
}


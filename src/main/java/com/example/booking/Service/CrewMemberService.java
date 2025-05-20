package com.example.booking.Service;

import com.example.booking.Entity.CrewMember;
import com.example.booking.Entity.Flight;
import org.springframework.data.domain.Page;

public interface CrewMemberService {
    CrewMember createCrewMember(CrewMember crewMember);
    CrewMember updateCrewMember(Long id, CrewMember crewMember );
    CrewMember getCrewMember(Long id);
    String deleteCrewMember(Long id);
    Page<CrewMember> getList(int page, int size, String search);

}

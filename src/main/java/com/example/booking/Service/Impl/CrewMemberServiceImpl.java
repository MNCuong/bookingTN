package com.example.booking.Service.Impl;

import com.example.booking.Entity.CrewMember;
import com.example.booking.Repository.CrewMemberRepository;
import com.example.booking.Service.CrewMemberService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class CrewMemberServiceImpl implements CrewMemberService {
    private final CrewMemberRepository crewMemberRepo;

    // Tạo mới thành viên phi hành đoàn
    @Override
    public CrewMember createCrewMember(CrewMember crewMember) {
        CrewMember crewMemberSave = new CrewMember();
        crewMemberSave.setName(crewMember.getName());
        crewMemberSave.setPosition(crewMember.getPosition());
        crewMemberSave.setLicenseExpiry(crewMember.getLicenseExpiry());
        crewMemberSave.setCreateAt(LocalDateTime.now());
        crewMemberSave.setStatus("ACTIVE");
        crewMemberSave.setIsDelete(false);
        return crewMemberRepo.save(crewMemberSave);
    }

    @Override
    public CrewMember updateCrewMember(Long id, CrewMember crewMember) {
        CrewMember existingCrewMember = crewMemberRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Crew Member not found"));

        existingCrewMember.setName(crewMember.getName());
        existingCrewMember.setPosition(crewMember.getPosition());
        existingCrewMember.setLicenseExpiry(crewMember.getLicenseExpiry());
        existingCrewMember.setStatus(crewMember.getStatus());
        existingCrewMember.setUpdateAt(LocalDateTime.now());
        return crewMemberRepo.save(existingCrewMember);
    }

    @Override
    public CrewMember getCrewMember(Long id) {
        return crewMemberRepo.findById(id).get();
    }

    @Override
    public String deleteCrewMember(Long id) {
        try {
            CrewMember crewMember = crewMemberRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Crew Member not found"));
            crewMember.setIsDelete(true);
            crewMemberRepo.save(crewMember);
            return "Success";
        }catch (Exception e){

            return e.getMessage();
        }

//        crewMemberRepo.delete(crewMember);
    }

    @Override
    public Page<CrewMember> getList(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if(search== null|| search.isEmpty()){
            return crewMemberRepo.findAllByIsDelete(false,pageable);
        }else{
            return crewMemberRepo.searchCrewMembers(search, pageable);

        }
    }
}

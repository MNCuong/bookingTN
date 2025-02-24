package com.example.booking.Controller.Hotel;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.HotelRequest;
import com.example.booking.DTO.Response.HotelResponse;
import com.example.booking.Service.HotelService;
import com.example.booking.Service.MinIOService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/hotel")
public class HotelController {
    private final HotelService hotelService;
    private final MinIOService minIOService;

    @PostMapping("/add-hotel")
    public ResponseEntity<ResponseDto<HotelResponse>> addHotel(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("city") String city,
            @RequestParam("country") String country,
            @RequestParam(value = "imgs", required = false) List<MultipartFile> imgs) {
        HotelRequest hotelRequest = HotelRequest.builder()
                .name(name)
                .phone(phone)
                .address(address)
                .description(description)
                .city(city)
                .country(country)
                .build();

        return ResponseConfig.success(hotelService.addHotel(hotelRequest, imgs));
    }

//    @GetMapping("/view-hotel")
//    public ResponseEntity<List<InputStreamResource>> viewFiles(@RequestParam("hotelId") String hotelId) {
//        try {
//            // Lấy tất cả ảnh của khách sạn từ MinIO
//            List<InputStream> fileStreams = minIOService.downloadFileViewHotel(hotelId);
//            List<InputStreamResource> resources = new ArrayList<>();
//
//            // Chuyển InputStream thành InputStreamResource
//            for (InputStream fileStream : fileStreams) {
//                InputStreamResource resource = new InputStreamResource(fileStream);
//                resources.add(resource);
//            }
//
//            // Trả về danh sách ảnh với content-type là image/jpeg
//            return ResponseEntity.ok()
//                    .contentType(MediaType.IMAGE_JPEG) // Hoặc MediaType.IMAGE_PNG nếu ảnh là PNG
//                    .body(resources);  // Trả về danh sách các ảnh
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
    @GetMapping("/Img-hotel")
    public ResponseEntity<ResponseDto<List<String>>> getImg(@RequestParam("hotelId") String hotelId) {
        return ResponseConfig.success(hotelService.getImgHotel(hotelId));
    }
}


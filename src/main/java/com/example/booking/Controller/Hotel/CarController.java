package com.example.booking.Controller.Hotel;

import com.example.booking.Config.ResponseConfig;
import com.example.booking.Config.ResponseDto;
import com.example.booking.DTO.Request.CarRequest;
import com.example.booking.DTO.Response.CarResponse;
import com.example.booking.DTO.Response.ListCarResponse;
import com.example.booking.Enum.CarStatus;
import com.example.booking.Enum.CarType;
import com.example.booking.Service.CarService;
import io.minio.errors.MinioException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/car")
public class CarController {
    private final CarService carService;

    @PostMapping("/add-car")
    public ResponseEntity<ResponseDto<CarResponse>> addCar(@RequestParam("brand") String brand,
                                                           @RequestParam("model") String model,
                                                           @RequestParam("year") int year,
                                                           @RequestParam("pricePerDay") BigDecimal pricePerDay,
                                                           @RequestParam("description") String description,
                                                           @RequestParam("licensePlate") String licensePlate,
                                                           @RequestParam("seatCapacity") int seatCapacity,
                                                           @RequestParam("type") CarType type,
                                                           @RequestParam("fuelType") String fuelType,
                                                           @RequestParam("status") CarStatus status,
                                                           @RequestParam(value = "files", required = false) List<MultipartFile> files, HttpServletRequest request) throws MinioException, IOException {
        CarRequest carRequest = CarRequest.builder()
                .fuelType(fuelType)
                .status(status)
                .type(type)
                .seatCapacity(seatCapacity)
                .brand(brand)
                .description(description)
                .licensePlate(licensePlate)
                .model(model)
                .pricePerDay(pricePerDay)
                .year(year)
                .build();
        return ResponseConfig.success(carService.addCar(carRequest, files, request));
    }

    @GetMapping("/list-car")
    public ResponseEntity<ResponseDto<List<ListCarResponse>>> listCar() {
        return ResponseConfig.success(carService.getCars());
    }

    @GetMapping("/car-detail")
    public ResponseEntity<ResponseDto<CarResponse>> carDetail(@RequestParam("id") long id) {
        return ResponseConfig.success(carService.carDetail(id));
    }

    @GetMapping("/car-of-hotel")
    public ResponseEntity<ResponseDto<List<ListCarResponse>>> carOfHotel(@RequestParam("hotelId") long id) {
        return ResponseConfig.success(carService.carOfHotel(id));
    }

    @GetMapping("/get-list-img-car")
    public ResponseEntity<ResponseDto<List<String>>> getListImgCar() {
        return ResponseConfig.success(carService.getImgCar());
    }
    @GetMapping("/get-img-car-by-id")
    public ResponseEntity<ResponseDto<List<String>>> getImgCarById(@RequestParam long id) {
        return ResponseConfig.success(carService.getImgCarById(id));
    }
}

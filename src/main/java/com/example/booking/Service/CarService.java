package com.example.booking.Service;

import com.example.booking.DTO.Request.CarRequest;
import com.example.booking.DTO.Response.CarResponse;
import com.example.booking.DTO.Response.ListCarResponse;
import com.example.booking.Entity.CarRental;
import io.minio.errors.MinioException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarService {
    CarResponse addCar(CarRequest carRequest, List<MultipartFile> files, HttpServletRequest request) throws IOException, MinioException;

    CarResponse updateCar(CarRequest carRequest);

    CarResponse deleteCar(CarRequest carRequest);

    CarRental findById(long id);

    List<ListCarResponse> getCars();

    List<ListCarResponse> carOfHotel(long id);

    CarResponse carDetail(long id);

    List<String> getImgCar();

    List<String> getImgCarById(long id);

    void save(CarRental carRental);
}

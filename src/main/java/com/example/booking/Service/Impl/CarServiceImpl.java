package com.example.booking.Service.Impl;

import com.example.booking.Common.MessageCommon;
import com.example.booking.Common.ServiceMessageConstants;
import com.example.booking.DTO.Request.CarRequest;
import com.example.booking.DTO.Response.CarResponse;
import com.example.booking.DTO.Response.ListCarResponse;
import com.example.booking.Entity.CarRental;
import com.example.booking.Entity.Hotel;
import com.example.booking.Entity.User;
import com.example.booking.Exception.BookingException;
import com.example.booking.Mapper.CarMapper;
import com.example.booking.Repository.CarRepository;
import com.example.booking.Service.CarService;
import com.example.booking.Service.HotelService;
import com.example.booking.Service.MinIOService;
import com.example.booking.Service.UserService;
import com.example.booking.Utils.JwtUtil;
import io.minio.errors.MinioException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final MinIOService minIOService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final HotelService hotelService;
    private final MessageCommon messageCommon;

    @Override
    public CarResponse addCar(CarRequest carRequest, List<MultipartFile> files, HttpServletRequest request) throws IOException, MinioException {
        String token = JwtUtil.getTokenFromRequest(request);
        User user = userService.findUserByEmail(jwtUtil.extractUsername(token));
        if (user == null) {
            throw new BookingException(ServiceMessageConstants.USER_NOT_FOUND, messageCommon.getMessage(ServiceMessageConstants.USER_NOT_FOUND));
        }
        Hotel hotel = hotelService.findByUser(user);
        if (hotel == null) {
            throw new BookingException(ServiceMessageConstants.HOTEL_NOT_FOUND, messageCommon.getMessage(ServiceMessageConstants.HOTEL_NOT_FOUND));
        }
        CarRental car = carRepository.save(CarRental.builder().brand(carRequest.getBrand()).model(carRequest.getModel()).year(carRequest.getYear()).fuelType(carRequest.getFuelType()).description(carRequest.getDescription()).seatCapacity(carRequest.getSeatCapacity()).hotel(hotel).licensePlate(carRequest.getLicensePlate()).pricePerDay(carRequest.getPricePerDay()).status(carRequest.getStatus()).build());
        if (files != null) {
            for (MultipartFile file : files) {
                minIOService.uploadFileCar(file.getInputStream(), file.getName(), file.getContentType(), hotel.getId().toString(), car.getId());
            }
        }
        return carMapper.toCarResponse(car);
    }

    @Override
    public CarResponse updateCar(CarRequest carRequest) {
        return null;
    }

    @Override
    public CarResponse deleteCar(CarRequest carRequest) {
        return null;
    }

    @Override
    public CarRental findById(long id) {
        return carRepository.findById(id).get();
    }


    @Override
    public List<ListCarResponse> getCars() {
        List<ListCarResponse> list = new ArrayList<>();

        for (CarRental rental : carRepository.findAll()) {
            list.add(ListCarResponse.builder().carStatus(rental.getStatus()).id(rental.getId()).model(rental.getModel()).brand(rental.getBrand()).seatCapacity(rental.getSeatCapacity()).pricePerDay(rental.getPricePerDay()).build());
        }
        return list;
    }

    @Override
    public List<ListCarResponse> carOfHotel(long hotelId) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        List<ListCarResponse> list = new ArrayList<>();

        for (CarRental rental : carRepository.findByHotel(hotel)) {
            list.add(ListCarResponse.builder().carStatus(rental.getStatus()).id(rental.getId()).model(rental.getModel()).brand(rental.getBrand()).seatCapacity(rental.getSeatCapacity()).pricePerDay(rental.getPricePerDay()).build());
        }
        return list;
    }

    @Override
    public CarResponse carDetail(long id) {
        return carMapper.toCarResponse(carRepository.findById(id).get());
    }

    @Override
    public List<String> getImgCar() {
        return new ArrayList<>(minIOService.getImagesByPrefix("Car/"));
    }

    @Override
    public List<String> getImgCarById(long id) {
        return minIOService.getImagesByCarId(id + "");
    }

    @Override
    public void save(CarRental carRental) {
        carRepository.save(carRental);
    }

    public List<CarResponse> getCarsByHotel() {
        return List.of();
    }
}

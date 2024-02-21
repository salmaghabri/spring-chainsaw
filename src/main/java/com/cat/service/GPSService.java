package com.cat.service;

import com.cat.model.dao.GPS;
import com.cat.model.dto.SessionReview.CreateSessionReviewDTO;
import com.cat.model.dto.gps.CreateGPSDTO;
import com.cat.model.dto.gps.GPSDTO;
import com.cat.repository.GPSRepository;
import com.cat.repository.SupervisorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.beans.PropertyDescriptor;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GPSService {
    private final GPSRepository gpsRepository;
    private final SupervisorRepository supervisorRepository;
    private final EmployeeService employeeService;
    private final GoalService goalService;
    private final SessionReviewService sessionReviewService;



    public Page<GPS> getAllGPS(Pageable pageable) {
        return gpsRepository.findAll(pageable);
    }

    public GPS getGPSById(UUID id) {
        return gpsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GPS not found with ID: " + id));
    }


    public GPSDTO createGPS(CreateGPSDTO gpsDTO) {

        //create gps dto
        GPS gps=new GPS();
        gps.setYear(gpsDTO.getYear());
        gps.setEmployee(this.supervisorRepository.findById(gpsDTO.getEmployee()).orElseThrow());
        gps.setMinGoalsNumber(gpsDTO.getMinGoalsNumber());
        gps.setSessionNumber(gpsDTO.getSessionNumber());
        gps=gpsRepository.save(gps);
        // add session reviews
        for(CreateSessionReviewDTO s: gpsDTO.getSessionReviews()){
            s.setGps(gps.getId());
            s.setGpsOwner(gpsDTO.getEmployee());
            this.sessionReviewService.createSessionReview(s);
        }
        return toDto(gps);
    }
    public CreateGPSDTO createdebug(CreateGPSDTO dto){
        return dto;

    }

    public GPS create(GPS gps) {
        return gpsRepository.save(gps);
    }
    public GPS updateGPS(UUID id, GPS gpsDetails) {
        GPS gps = getGPSById(id);
        BeanUtils.copyProperties(gpsDetails, gps, getNullPropertyNames(gpsDetails));
        return gpsRepository.save(gps);
    }


    private String[] getNullPropertyNames(GPS gps) {
        final BeanWrapper srcWrapper = new BeanWrapperImpl(gps);
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : srcWrapper.getPropertyDescriptors()) {
            Object srcValue = srcWrapper.getPropertyValue(propertyDescriptor.getName());
            if (srcValue == null) {
                emptyNames.add(propertyDescriptor.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
    public void deleteGPSById(UUID id) {
        GPS gps = gpsRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GPS not found with ID: " + id));
        gpsRepository.delete(gps);
    }
    public List<GPS> getGPSByEmployee(UUID id){
        return gpsRepository.findAllByEmployee_Id(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GPS not found with Employee id: " + id));
    }

    public List<GPSDTO> getGPSDTOByEmployee(UUID id) {
        List<GPS> gpsList=gpsRepository.findAllByEmployee_Id(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GPS not found with Employee id: " + id));
        List<GPSDTO> gpsdtoList=new ArrayList<>();
        for(GPS g: gpsList){
            gpsdtoList.add(this.toDto(g));
        }
        return gpsdtoList;
    }

    private GPSDTO toDto(GPS g) {
        GPSDTO dto=GPSDTO
                .builder()
                .id(g.getId())
                .year(g.getYear())
                .minGoalsNumber(g.getMinGoalsNumber())
                .sessionNumber(g.getSessionNumber())
                .employee(employeeService.toDTO(g.getEmployee()))
                .goals(this.goalService.getAllPreviewGoalDTOsByGPS(g.getId()))
                .sessionReviews(this.sessionReviewService.getAllSessionReviewPreviewsByGPS(g.getId()))
                .build();
        return dto;
    }
    public List<UUID> getAllGPSIdsByEmployee(UUID employeeId){
        return this.gpsRepository.getAllGPSIdsByEmployee(employeeId);

    }

}


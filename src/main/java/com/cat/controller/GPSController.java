package com.cat.controller;

import com.cat.model.dao.GPS;
import com.cat.model.dto.SessionReview.CreateSessionReviewDTO;
import com.cat.model.dto.gps.CreateGPSDTO;
import com.cat.model.dao.SessionReview;
import com.cat.model.dto.SessionReview.SessionReviewDTO;
import com.cat.model.dto.gps.GPSDTO;
import com.cat.service.GPSService;
import com.cat.service.SessionReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gps")
public class GPSController {

        private final GPSService gpsService;
        private final SessionReviewService sessionReviewService;

        @GetMapping
        public Page<GPS> getAllGPS(Pageable pageable) {
            return gpsService.getAllGPS(pageable);
        }

        @GetMapping("/{id}")
            public ResponseEntity<GPS> getGPSById(@PathVariable(value = "id") UUID gpsId) {
            GPS gps = gpsService.getGPSById(gpsId);
            return ResponseEntity.ok().body(gps);
        }
        @GetMapping("/sr/{id}")
            public ResponseEntity<SessionReviewDTO> getRSyId(@PathVariable(value = "id") UUID gpsId) {
            SessionReviewDTO gps = sessionReviewService.getSessionReviewById(gpsId);
            return ResponseEntity.ok().body(gps);
        }
        @GetMapping("/sr")
            public Page<SessionReviewDTO> getAllRS(Pageable pageable) {
            return sessionReviewService.getAllSessionReviews(pageable);

        }


//        @PostMapping("/add")
//        public GPS createGPS(@RequestBody GPS createGPSDTO) {
//            return gpsService.create(createGPSDTO);
//        }
        @PostMapping
        public GPSDTO createGPSFromDTO(@RequestBody CreateGPSDTO createGPSDTO) {
            return gpsService.createGPS(createGPSDTO);
        }

        @PostMapping("/reviews")
        public SessionReview cretetsr(@RequestBody CreateSessionReviewDTO createGPSDTO){
            return sessionReviewService.createSessionReview(createGPSDTO);
        }
        @PatchMapping("/{id}")
            public ResponseEntity<GPS> updateGPS(@PathVariable(value = "id") UUID gpsId,
                                             @RequestBody GPS gpsDetails) {
            final GPS updatedGPS = gpsService.updateGPS(gpsId, gpsDetails);
            return ResponseEntity.ok(updatedGPS);
        }






    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGPS(@PathVariable(value = "id") UUID gpsId) {
        gpsService.deleteGPSById(gpsId);
        return ResponseEntity.noContent().build();
    }




    @GetMapping("/employee/goals/{employeeId}")
        public ResponseEntity<List<GPS>> getGoalsByEmployeeId(@PathVariable(value = "employeeId") UUID employeeId) {
        List<GPS> gps = gpsService.getGPSByEmployee(employeeId);
        return ResponseEntity.ok().body(gps);
    }
    @GetMapping("/employee/dto/{employeeId}")
        public ResponseEntity<List<GPSDTO>> getGPSDTOsByEmployeeId(@PathVariable(value = "employeeId") UUID employeeId) {
        List<GPSDTO> gps = gpsService.getGPSDTOByEmployee(employeeId);
        return ResponseEntity.ok().body(gps);
    }
    @GetMapping("/reviews/{id}")
    public List<SessionReviewDTO> getSRByGPS(@PathVariable(value = "id") UUID gpsId ){
            return sessionReviewService.getAllSessionReviewsByGPS(gpsId);

    }
    @GetMapping("ids/{id}")
    public List<UUID> getGPSIdsByEmployee(@PathVariable(value = "id") UUID employeeId ){
            return gpsService.getAllGPSIdsByEmployee(employeeId);
    }


    }



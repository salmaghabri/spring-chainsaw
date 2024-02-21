package com.cat.service;

import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.model.dto.SessionReview.CreateSessionReviewDTO;
import com.cat.model.dto.SessionReview.SessionReviewPreviewDTO;
import com.cat.enums.Status;
import com.cat.model.dao.SessionReview;
import com.cat.model.dto.SessionReview.SessionReviewDTO;
import com.cat.model.dto.validation.CreateReviewSessionValidationDTO;
import com.cat.model.dto.validation.ValidationDTO;
import com.cat.repository.GPSRepository;
import com.cat.repository.SessionReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionReviewService {
    private final SessionReviewRepository sessionReviewRepository;
    private final GPSRepository gpsRepository;
    private final EmployeeService employeeService;
    private final ValidationService validationService;
    private final GoalService goalService;
    private final GoalReviewService goalReviewService;


    public Page<SessionReviewDTO> getAllSessionReviews(Pageable pageable) {
        Page<SessionReview> sessionReviewsPage = sessionReviewRepository.findAll(pageable);
        List<SessionReviewDTO> sessionReviewDTOList = sessionReviewsPage.getContent()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(sessionReviewDTOList, pageable, sessionReviewsPage.getTotalElements());
    }

    public SessionReviewDTO getSessionReviewById(UUID id) {
        return toDTO(sessionReviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SessionReview not found with ID: " + id)));
    }

    public SessionReview createSessionReview(CreateSessionReviewDTO createSessionReviewDTO) {
        // create Review Session from dto
        SessionReview sessionReview = new SessionReview();
        sessionReview.setDate(createSessionReviewDTO.getDate());
        sessionReview.setType(createSessionReviewDTO.getType());
        sessionReview.setGps(this.gpsRepository.findById(createSessionReviewDTO.getGps()).orElseThrow());
        sessionReview = sessionReviewRepository.save(sessionReview);
        // add validations of the reviewers to the Review Session
        List<EmployeeDTO> reviewers = this.employeeService.getAllSupervisors(createSessionReviewDTO.getGpsOwner());
        List<ValidationDTO> validationDTOS = new ArrayList<>();
        for (EmployeeDTO r : reviewers) {
            CreateReviewSessionValidationDTO validationDTO = CreateReviewSessionValidationDTO
                    .builder()
                    .validator(r.getId())
                    .status(Status.PENDING)
                    .SessionReview(sessionReview.getId())
                    .build();
            validationDTOS.add(this.validationService.createReviewSessionValidation(validationDTO));


        }
        SessionReviewDTO sessionReviewDto = toDTO(sessionReview);

        sessionReviewDto.setValidations(validationDTOS);

        return sessionReview;
    }

    public SessionReviewDTO toDTO(SessionReview sessionReview) {
        SessionReviewDTO sessionReviewDTO = SessionReviewDTO
                .builder()
                .date(sessionReview.getDate())

                .id(sessionReview.getId())
                .ranking(sessionReview.getRanking())
                .type(sessionReview.getType())
                .validations(this.validationService.getAllValidationsByReviewSession(sessionReview.getId()))
                .goals(this.goalService.getAllGoalAndReviewDTOsByGPS(sessionReview.getGps().getId(),sessionReview.getId()))
                .build();
        return sessionReviewDTO;

    }

    SessionReviewPreviewDTO toPreviewDTO(SessionReview sessionReview) {
        SessionReviewPreviewDTO sessionReviewPreviewDTO = SessionReviewPreviewDTO
                .builder()
                .id(sessionReview.getId())
                .date(sessionReview.getDate())
                .ranking(sessionReview.getRanking())
                .type(sessionReview.getType())
                .build();
        return sessionReviewPreviewDTO;
    }

    List<SessionReviewPreviewDTO> getAllSessionReviewPreviewsByGPS(UUID gpsId) {
        List<SessionReview> sessionReviewList = sessionReviewRepository.findAllByGps_Id(gpsId);
        List<SessionReviewPreviewDTO> previewDTOS = new ArrayList<>();
        for (SessionReview s : sessionReviewList) {
            previewDTOS.add(toPreviewDTO(s));
        }
        return previewDTOS;
    }
    public List<SessionReviewDTO> getAllSessionReviewsByGPS(UUID gpsId) {
        List<SessionReview> sessionReviewList = sessionReviewRepository.findAllByGps_Id(gpsId);
        List<SessionReviewDTO> previewDTOS = new ArrayList<>();
        for (SessionReview s : sessionReviewList) {
            previewDTOS.add(toDTO(s));
        }
        return previewDTOS;
    }

//    ReviewSessionWithGoalReviews toReviewSessionWithGoalReviews(SessionReview sessionReview){
//        ReviewSessionWithGoalReviews reviewSessionWithGoalReviews=ReviewSessionWithGoalReviews.builder()
//                .id(sessionReview.getId())
//                .date(sessionReview.getDate())
//                .type(sessionReview.getType())
//                .goalReviews(this.goalReviewService.getAllReviewsByGoalAndSession())
//                .build();
//    }

}

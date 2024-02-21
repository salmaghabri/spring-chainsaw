package com.cat.service;

import com.cat.model.dao.Goal;
import com.cat.enums.Status;
import com.cat.model.dao.SessionReview;
import com.cat.model.dao.Validation;
import com.cat.model.dto.validation.*;
import com.elyadata.stub.model.dto.validation.*;
import com.cat.repository.GoalRepository;
import com.cat.repository.SessionReviewRepository;
import com.cat.repository.ValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidationService {
    private final ValidationRepository validationRepository;
    private final SessionReviewRepository sessionReviewRepository;
    private final GoalRepository goalRepository;
    private final EmployeeService employeeService;


    public Page<Validation> getAllValidations(Pageable pageable) {
        return validationRepository.findAll(pageable);
    }
    public ValidationDTO getValidationById(UUID id) {
        return this.validationToDTO(validationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Validation not found with ID: " + id)));
    }
    public ValidationDTO createGoalValidation(CreateValidationDTO validationDTO) {
        Validation validation = this.createDTOToValidation(validationDTO);
        Validation savedValidation = validationRepository.save(validation);
        return this.validationToDTO(savedValidation);
    }
    public ValidationDTO createReviewSessionValidation(CreateReviewSessionValidationDTO createReviewSessionValidationDTO){
        Validation validation=new Validation();
        validation.setStatus(createReviewSessionValidationDTO.getStatus());
        validation.setValidator(createReviewSessionValidationDTO.getValidator());
        validation.setSessionReview(this.sessionReviewRepository.findById(createReviewSessionValidationDTO.getSessionReview()).orElseThrow());
        return validationToDTO(validationRepository.save(validation));

    }


    public ValidationDTO updateValidation(UUID goalId,UUID validator, UpdateValidationDTO validationDTO){
        Validation validation=this.validationRepository.findByGoalIdAndValidatorAndSessionReviewIsNull(goalId, validator);
        validation.setStatus(validationDTO.getStatus());
        ValidationDTO dto= validationToDTO(validationRepository.save(validation));
        int validatedGoals=this.validationRepository.countValidatedValidationsByGoalId(goalId, Status.VALIDATED);
        int DeclinedGoals=this.validationRepository.countValidatedValidationsByGoalId(goalId, Status.DECLINED);
        int total=this.validationRepository.countValidationByGoal_IdAndSessionReviewIsNull(goalId);
            Goal goal=this.goalRepository.findById(goalId).orElseThrow();
        if(total==validatedGoals){
            goal.setStatus(Status.VALIDATED);
        }else if(total==DeclinedGoals){
            goal.setStatus(Status.DECLINED);
        }else{
            goal.setStatus(Status.PENDING);
        }
        goalRepository.save(goal);

        dto.setGoalStatus(goal.getStatus());

        return dto;
    }


    public SessionReview updateSessionValidation(UUID reviewSessionId, UUID validator, UpdateReviewSessionValidation valdiationDTO){
        Validation validation=this.validationRepository.findBySessionReview_IdAndValidator(reviewSessionId,validator);
        validation.setRanking(valdiationDTO.getRanking());
        if(valdiationDTO.getRanking()==20){
        validation.setStatus(Status.DECLINED);

        }else{
            validation.setStatus(Status.VALIDATED);

        }
        validationRepository.save(validation);

        int total=validationRepository.countValidationBySessionReview_Id(reviewSessionId);
        int pendingValidations= validationRepository.countValidatedValidationsBySessionReviewId(reviewSessionId,Status.PENDING);
            SessionReview sessionReview=this.sessionReviewRepository.findById(reviewSessionId).orElse(null);
        if(pendingValidations ==0){
            int avg= Double.valueOf(this.validationRepository.getAverageRankingBySessionReviewId(reviewSessionId)).intValue();
            sessionReview.setRanking(avg);
            this.sessionReviewRepository.save(sessionReview);
        }
        return this.sessionReviewRepository.save(sessionReview);
    }
    public ValidationDTO updateSessionValidationById(UUID validationId,UpdateReviewSessionValidation valdiationDTO){
        Validation validation=this.validationRepository.findById(validationId).orElse(null);
        validation.setRanking(valdiationDTO.getRanking());
        return validationToDTO(validationRepository.save(validation));
    }




    public void deleteValidationById(UUID id) {
        Validation validation = validationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Validation not found with ID: " + id));
        validationRepository.delete(validation);
    }
    public List<ValidationDTO>  getAllValidationsByGoal(UUID goalId){
            List<Validation>  validations = this.validationRepository.findAllByGoal_IdAndSessionReviewIsNull(goalId);
        List<ValidationDTO>  validationsDto=new ArrayList<>();
        for(Validation v: validations){
            validationsDto.add(this.validationToDTO(v));

        }
        return validationsDto;

    }
    public List<ValidationDTO> getAllValidationsByReviewSession(UUID reviewSessionId){
        List<Validation>  validations=this.validationRepository.findAllBySessionReview_Id(reviewSessionId);
        List<ValidationDTO>  validationsDto=new ArrayList<>();
        for(Validation v: validations){
            validationsDto.add(this.validationToDTO(v));

        }
        return validationsDto;


    }
    public Validation createDTOToValidation(CreateValidationDTO validationDTO) {
        Validation validation=new Validation();
        validation.setValidator(validationDTO.getValidator());
        validation.setStatus(validationDTO.getStatus());
        validation.setGoal(this.goalRepository.findById(validationDTO.getGoal()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "goal not found with ")));
        return validation;
    }

    public ValidationDTO validationToDTO(Validation validation) {
        ValidationDTO dto= ValidationDTO
                .builder()
                .id(validation.getId())
                .validator(this.employeeService.toDTO(this.employeeService.getEmployeeById(validation.getValidator())))
                .status(validation.getStatus())
                .ranking(validation.getRanking())
                .build();
        return dto;

    }
    public ReviewSessionValidation toReviewSessionValidation(Validation validation){
        ReviewSessionValidation dto= ReviewSessionValidation
                .builder()
                .id(validation.getId())
                .validator(this.employeeService.toDTO(this.employeeService.getEmployeeById(validation.getValidator())))
                .status(validation.getStatus())
                .ranking(validation.getRanking())
                .build();
        return dto;
    }

}

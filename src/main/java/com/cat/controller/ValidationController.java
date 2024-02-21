package com.cat.controller;

import com.cat.model.dto.SessionReview.SessionReviewDTO;
import com.cat.model.dto.validation.CreateValidationDTO;
import com.cat.model.dto.validation.UpdateReviewSessionValidation;
import com.cat.model.dto.validation.UpdateValidationDTO;
import com.cat.model.dto.validation.ValidationDTO;
import com.cat.service.SessionReviewService;
import com.cat.model.dao.Validation;
import com.elyadata.stub.model.dto.validation.*;
import com.cat.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validations")
public class ValidationController {
    private final ValidationService validationService;
    private final SessionReviewService sessionReviewService;

    @GetMapping
    public ResponseEntity<Page<Validation>> getAllValidations(Pageable pageable) {
        return ResponseEntity.ok().body(validationService.getAllValidations(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValidationDTO> getValidationById(@PathVariable(value = "id") UUID validationId) {
        return ResponseEntity.ok().body(validationService.getValidationById(validationId));
    }
    @GetMapping("/goal/{id}")
    public ResponseEntity<List<ValidationDTO>> getAllValidationsByGoal(@PathVariable(value = "id") UUID goalId) {
        return ResponseEntity.ok().body(validationService.getAllValidationsByGoal(goalId));
    }

    @PostMapping
    public ResponseEntity<ValidationDTO> createValidation(@RequestBody CreateValidationDTO createValidationDTO) {
        return ResponseEntity.ok().body(validationService.createGoalValidation(createValidationDTO));
    }

    @PutMapping("/goal/{goalId}/{validator}")
    public ResponseEntity<ValidationDTO> updateValidation(@PathVariable(value = "goalId") UUID goalId,
                                                          @PathVariable(value = "validator") UUID validator,
                                                          @RequestBody UpdateValidationDTO validationDetails) {
        return ResponseEntity.ok(validationService. updateValidation(goalId, validator,validationDetails));
    }
    @PutMapping("/session/{sessionId}/{validator}")
    public ResponseEntity<
            SessionReviewDTO> updateValidationSession(@PathVariable(value = "sessionId") UUID sessionId,
                                                      @PathVariable(value = "validator") UUID validator,
                                                      @RequestBody UpdateReviewSessionValidation validationDetails) {
        return ResponseEntity.ok(this.sessionReviewService.toDTO(validationService. updateSessionValidation(sessionId, validator,validationDetails)));
    }
    @PutMapping("/session/{id}")
    public ResponseEntity<ValidationDTO > updateValidationSessionById(@PathVariable(value = "id") UUID id,
                                                                    @RequestBody UpdateReviewSessionValidation  validationDetails) {
        return ResponseEntity.ok(validationService. updateSessionValidationById(id,validationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValidation(@PathVariable(value = "id") UUID validationId) {
        validationService.deleteValidationById(validationId);
        return ResponseEntity.noContent().build();
    }
}

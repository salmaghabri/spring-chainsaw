package com.cat.service;

import com.cat.model.dao.GoalReview;
import com.cat.model.dto.GoalReview.CreateGoalReview;
import com.cat.model.dto.GoalReview.GoalReviewDTO;
import com.cat.model.dto.GoalReview.UpdateFeedbackDTO;
import com.cat.model.dto.GoalReview.UpdateProgressDTO;
import com.cat.repository.GoalRepository;
import com.cat.repository.GoalReviewRepository;
import com.cat.repository.SessionReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoalReviewService {

    private final GoalReviewRepository goalReviewRepository;
    private final SessionReviewRepository  sessionReviewRepository;
    private final GoalRepository goalRepository;
    private final EmployeeService employeeService;



    public Page<GoalReview> getAllGoalReviews(Pageable pageable) {
        return goalReviewRepository.findAll(pageable);
    }

    public GoalReview getGoalReviewById(UUID id) {
        return goalReviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "GoalReview not found with ID: " + id));
    }

    public GoalReviewDTO createGoalReview(CreateGoalReview goalReview) {
        GoalReview gr=new GoalReview();
        gr.setCreatedBy(goalReview.getCreatedBy());
        gr.setGoal(this.goalRepository.findById(goalReview.getGoal()).orElseThrow());
        gr.setProgress(goalReview.getProgress());
        gr.setSessionReview(this.sessionReviewRepository.findById(goalReview.getReviewSession()).orElseThrow());
//        gr.setValidation(this.validationService.createGoalReviewValidation(validationDTO));


        return toDTO(goalReviewRepository.save(gr));
    }
    public GoalReviewDTO toDTO(GoalReview goalReview){
        GoalReviewDTO goalReviewDTO=GoalReviewDTO.builder()
                .id(goalReview.getId())
                .createdAt(goalReview.getCreatedAt())
                .progress(goalReview.getProgress())
                .feedback(goalReview.getFeedback())
                .createdBy(this.employeeService.getEmployeeDTOById(goalReview.getCreatedBy()))
                .goal(goalReview.getGoal().getId())
                .reviewSession(goalReview.getSessionReview().getId())
                .build();
return goalReviewDTO;
    }
    public List<GoalReviewDTO> getAllReviewsByGoalAndSession(UUID goalId,UUID sessionId){
        List<GoalReview> goalReviewsList= this.goalReviewRepository.findAllByGoal_IdAndSessionReview_Id(goalId,sessionId);
        List<GoalReviewDTO> dtos=new ArrayList<>();
        for(GoalReview r: goalReviewsList){
            dtos.add(toDTO(r));
        }
        return  dtos;
     }
    public List<GoalReviewDTO> getAllReviewsByGoal(UUID goalId){
        List<GoalReview> goalReviewsList= this.goalReviewRepository.findAllByGoal_Id(goalId);
        List<GoalReviewDTO> dtos=new ArrayList<>();
        for(GoalReview r: goalReviewsList){
            dtos.add(toDTO(r));
        }
        return  dtos;
    }



    public GoalReviewDTO updateGoalReviewFeedback(UpdateFeedbackDTO dto, UUID goalId, UUID supervisorId) {
        GoalReview goalReview=this.goalReviewRepository.findByGoal_IdAndCreatedByAndSessionReview_Id(goalId,supervisorId,dto.getSessionId());

        goalReview.setFeedback(dto.getFeedback());
        return toDTO(this.goalReviewRepository.save(goalReview));
    }

    public GoalReviewDTO updateGoalReviewProgress(UpdateProgressDTO dto, UUID goalId, UUID supervisorId) {
        GoalReview goalReview=this.goalReviewRepository.findByGoal_IdAndCreatedByAndSessionReview_Id(goalId,supervisorId,dto.getSessionId());
        System.out.println(goalReview.getProgress());
        goalReview.setProgress(dto.getProgress());
        System.out.println(dto.getProgress());
        return toDTO(this.goalReviewRepository.save(goalReview));
    }
    public Map<String, List<GoalReviewDTO>> getGRGroupedBySession(UUID id){
        List<GoalReview> goalReviews = this.goalReviewRepository.findAllByGoal_Id( id);
        return goalReviews.stream()
                .collect(Collectors.groupingBy(g -> g.getSessionReview().getType(),
                        Collectors.mapping(this::toDTO, Collectors.toList())));


    }
}

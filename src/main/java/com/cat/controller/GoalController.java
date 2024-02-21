package com.cat.controller;

import com.cat.model.dao.Goal;
import com.cat.model.dto.Goal.CreateGoalDTO;
import com.cat.model.dto.Goal.GoalDTO;
import com.cat.model.dto.Goal.UpdateGoalDescriptionAndCategoryDTO;
import com.cat.model.dto.Goal.UpdateTitleDTO;
import com.cat.model.dto.GoalReview.CreateGoalReview;
import com.cat.model.dto.GoalReview.GoalReviewDTO;
import com.cat.model.dto.GoalReview.UpdateFeedbackDTO;
import com.cat.model.dto.GoalReview.UpdateProgressDTO;
import com.cat.service.GPSService;
import com.cat.service.GoalReviewService;
import com.cat.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class GoalController {
    private final GoalService goalService;
    private final GPSService gpsService;
    private final GoalReviewService goalReviewService;
    @GetMapping
    public ResponseEntity<Page<Goal>> getAllGoals(Pageable pageable) {
        Page<Goal> p=goalService.getAllGoals(pageable);
        return ResponseEntity.ok().body(p);}

        @GetMapping("/{id}")
        public ResponseEntity<Goal> getGoalById (@PathVariable(value = "id") UUID goalId){
            Goal goal = goalService.getGoalById(goalId);
            return ResponseEntity.ok().body(goal);
        }
    @GetMapping("/dto/{id}")
    public ResponseEntity<GoalDTO> getGoalDTOById (@PathVariable(value = "id") UUID goalId){
        return ResponseEntity.ok().body(goalService.getGoalDTO(goalId));
    }


        @PostMapping
        public GoalDTO createGoal (@RequestBody CreateGoalDTO goalDTO){
            return goalService.createGoal(goalDTO);
        }

        @PatchMapping("/{id}")
        public ResponseEntity<Goal> updateGoal (@PathVariable(value = "id") UUID goalId,
                @RequestBody Goal goalDetails){
            final Goal updatedGoal = goalService.updateGoal(goalId, goalDetails);
            return ResponseEntity.ok(updatedGoal);
        }
        @PutMapping("/title/{id}")
        public ResponseEntity<GoalDTO> updateTitleGoal (@PathVariable(value = "id") UUID goalId,
                                                @RequestBody UpdateTitleDTO goalDetails){
            final GoalDTO  updatedGoal = goalService.updateGoalTitle(goalId, goalDetails);
            return ResponseEntity.ok(updatedGoal);
        }
        @PutMapping("/description/{id}")
        public ResponseEntity<GoalDTO> updateDescriptionGoal (@PathVariable(value = "id") UUID goalId,
                                                              @RequestBody UpdateGoalDescriptionAndCategoryDTO goalDetails){
            final GoalDTO  updatedGoal = goalService.updateGoalDescriptionAndCategory(goalId, goalDetails);
            return ResponseEntity.ok(updatedGoal);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Goal> deleteGoal (@PathVariable(value = "id") UUID goalId){
            goalService.deleteGoalById(goalId);
            return ResponseEntity.noContent().build();

        }
        @GetMapping("/gps/{id}")
        public ResponseEntity<List<Goal>> getGPSByEmployeeId(@PathVariable(value = "id") UUID gpsId) {
            List<Goal> goals = goalService.getAllGoalsByGPS(gpsId);
            return ResponseEntity.ok().body(goals);
        }
        @GetMapping("/gps/dto/{id}")
        public ResponseEntity<List<GoalDTO>> getGPSByGPsId(@PathVariable(value = "id") UUID gpsId) {
            List<GoalDTO> goals = goalService.getAllGoalsDTOByGPS(gpsId);
            return ResponseEntity.ok().body(goals);
        }
        @GetMapping("/reviews/sessions/{id}")
        public ResponseEntity<Map<String, List<GoalReviewDTO>>> getGoalReviewsByGoalIdGroupedBySession(@PathVariable(value = "id") UUID goalId) {

            return ResponseEntity.ok().body(this.goalReviewService.getGRGroupedBySession(goalId));
        }

@PostMapping("/goalreview")
public GoalReviewDTO createGoalReview (@RequestBody CreateGoalReview dto){

        return goalReviewService.createGoalReview(dto);
}
@PutMapping("/goalReview/feedback/{goalId}/{supervisorId}")
public GoalReviewDTO updateGoalReviewFeedback (@RequestBody UpdateFeedbackDTO dto, @PathVariable(value = "goalId") UUID goalId, @PathVariable(value = "supervisorId") UUID supervisorId){


    return goalReviewService.updateGoalReviewFeedback(dto,goalId,supervisorId);
}
@PutMapping("/goalReview/progress/{goalId}/{supervisorId}")
public GoalReviewDTO updateGoalReviewProgress (@RequestBody UpdateProgressDTO dto, @PathVariable(value = "goalId") UUID goalId, @PathVariable(value = "supervisorId") UUID supervisorId){


    return goalReviewService.updateGoalReviewProgress(dto,goalId,supervisorId);
}

    @GetMapping("/gps/{goalId}/allIds")
    public ResponseEntity<List<UUID>> getAllGoalIdsByGpsId(@PathVariable UUID goalId) {
        List<UUID> goalIds = goalService.getAllGoalIdsByGpsId(goalId);
        return ResponseEntity.ok(goalIds);
    }


    }

package com.cat.service;

import com.cat.model.dao.GPS;
import com.cat.model.dao.Goal;
import com.cat.model.dto.Employee.EmployeeDTO;
import com.cat.model.dto.Goal.*;
import com.cat.model.dto.validation.CreateValidationDTO;
import com.cat.model.dto.validation.ValidationDTO;
import com.cat.repository.*;
import com.cat.enums.Status;
import com.cat.mapper.CategoryMapper;
import com.elyadata.stub.model.dto.Goal.*;
import com.elyadata.stub.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
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

@RequiredArgsConstructor
@Service
public class GoalService {

    private final GoalRepository goalRepository;
    private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    private final GPSRepository gpsRepository;
    private final CategoryRepository categoryRepository;
    private final EmployeeService employeeService;
    private final CommentRepository commentRepository;
    private final ValidationService  validationService;
    private final GoalReviewService goalReviewService;
    private final GoalReviewRepository goalReviewRepository;
    private final ValidationRepository validationRepository;


    public Page<Goal> getAllGoals(Pageable pageable) {
        return goalRepository.findAll(pageable);

    }

    public Goal getGoalById(UUID id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found "));
        return goal;

    }

    public GoalDTO getGoalDTO(UUID id) {
        Goal goal = goalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Goal not found "));
        return toDTO(goal);


    }

    public GoalDTO createGoal(CreateGoalDTO goalDTO) {
//create goal from dto
        Goal goal = new Goal();
        goal.setCreatedBy(goalDTO.getCreatedBy());
        goal.setUpdatedBy(goalDTO.getCreatedBy());
        goal.setDescription(goalDTO.getDescription());
        goal.setTitle(goalDTO.getTitle());
        goal.setStatus(Status.PENDING);
        goal.setGps(this.gpsRepository.findById(goalDTO.getGps()).orElseThrow());
        goal.setCategory(this.categoryRepository.findById(goalDTO.getCategory()).orElseThrow());
        GoalDTO goalDto= toDTO(goalRepository.save(goal));
// add reviewers validations with the default status "pending"
        GPS gps=gpsRepository.findById(goalDTO.getGps()).orElseThrow();
        List<EmployeeDTO> reviewers=this.employeeService.getAllSupervisors(gps.getEmployee().getId());
        //TODO: add the managers to the list
        List<ValidationDTO> validationDTOS=new ArrayList<>();
        for(EmployeeDTO r: reviewers){
            CreateValidationDTO validationDTO=CreateValidationDTO
                    .builder()
                    .validator(r.getId())
                    .status(Status.PENDING)
                    .goal(goalDto.getId())
                    .build();
            validationDTOS.add(  this.validationService.createGoalValidation(validationDTO));


        }
        goalDto.setValidations(validationDTOS);


        return goalDto;
    }

    public Goal create(Goal goal) {
        return this.goalRepository.save(goal);
    }

    public Goal updateGoal(UUID id, Goal goalDetails) {
        Goal goal = getGoalById(id);
        BeanUtils.copyProperties(goalDetails, goal, getNullPropertyNames(goalDetails));

        return goalRepository.save(goal);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper srcWrapper = new BeanWrapperImpl(source);
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : srcWrapper.getPropertyDescriptors()) {
            Object srcValue = srcWrapper.getPropertyValue(propertyDescriptor.getName());
            if (srcValue == null) {
                emptyNames.add(propertyDescriptor.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
    @Transactional
    public void deleteGoalById(UUID id) {
//        commentRepository.deleteCommentsByGoalId(id);
        Goal goal = getGoalById(id);
        commentRepository.deleteAllByGoal(goal);
        goalReviewRepository.deleteAllByGoal(goal);
        validationRepository.deleteAllByGoal(goal);
        goalRepository.delete(goal);
    }

    public List<Goal> getAllGoalsByGPS(UUID id) {
        return goalRepository.findAllByGps_Id(id);
        //TODO: return bad request 404 if not found

    }

    public List<GoalDTO> getAllGoalsDTOByGPS(UUID id) {
        List<Goal> goals = goalRepository.findAllByGps_Id(id);
        List<GoalDTO> goalDTOs = new ArrayList<>();
        for (Goal g : goals) {
            goalDTOs.add(toDTO(g));
        }
        return goalDTOs;


    }

    public GoalDTO toDTO(Goal goal) {
        GoalDTO dto = GoalDTO.
                builder()
                .category(categoryMapper.categoryToDTO(goal.getCategory()))
                .title(goal.getTitle())
                .description(goal.getDescription())
                .createdAt(goal.getCreatedAt())
                .updatedAt(goal.getUpdatedAt())
                .status(goal.getStatus())
                .id(goal.getId())
//                .comments(this.commentRepository.findCommentsByGoal_Id(goal.getId()))
                .validations(this.validationService.getAllValidationsByGoal(goal.getId()))
                .owner(this.employeeService.toDTO(goal.getGps().getEmployee()))
                .createdBy(this.employeeService.toDTO(this.employeeService.getEmployeeById(goal.getCreatedBy())))
                .updatedBy(this.employeeService.toDTO(this.employeeService.getEmployeeById(goal.getUpdatedBy())))
                .gps(goal.getGps().getId())
                .build();
        return dto;
    }
    public GoalPreviewDTO toPreviewDTO(Goal goal) {
        GoalPreviewDTO dto = GoalPreviewDTO.
                builder()
                .category(categoryMapper.categoryToDTO(goal.getCategory()))
                .title(goal.getTitle())
                .status(goal.getStatus())
                .id(goal.getId())
                .createdBy(this.employeeService.getEmployeeById(goal.getCreatedBy()).getUsername())
                .updatedBy(this.employeeService.getEmployeeById(goal.getUpdatedBy()).getUsername())
                .comments(this.commentRepository.countCommentByGoal_Id(goal.getId()))
                .validations(this.validationService.getAllValidationsByGoal(goal.getId()))
                .build();
        return dto;
    }

    public List<GoalPreviewDTO> getAllPreviewGoalDTOsByGPS(UUID id) {
            List<Goal> goals = goalRepository.findAllByGps_Id(id);
            List<GoalPreviewDTO> goalDTOs = new ArrayList<>();
            for (Goal g : goals) {
                goalDTOs.add(toPreviewDTO(g));
            }
            return goalDTOs;




    }
    public List<UUID> getAllGoalIdsByGpsId(UUID goalId) {
        Goal goal=this.goalRepository.findById(goalId).orElse(null);

        return goalRepository.findAllGoalIdsByGpsId(goal.getGps().getId());
    }

    public GoalDTO updateGoalTitle(UUID goalId, UpdateTitleDTO goalDetails) {
        Goal goal= this.goalRepository.findById(goalId).orElseThrow();
        goal.setTitle(goalDetails.getTitle());
        goal.setUpdatedBy(goalDetails.getUpdatedBy());
        return this.toDTO(goalRepository.save(goal));

    }

    public GoalDTO updateGoalDescriptionAndCategory(UUID goalId, UpdateGoalDescriptionAndCategoryDTO goalDetails) {
        Goal goal= this.goalRepository.findById(goalId).orElseThrow();
        goal.setDescription(goalDetails.getDescription());
        goal.setUpdatedBy(goalDetails.getUpdatedBy());
        goal.setCategory(this.categoryRepository.findById(goalDetails.getCategory()).orElseThrow());
        return this.toDTO(goalRepository.save(goal));

    }
    public GoalAndReviewDTO toGoalAndReviewDTO(Goal goal,UUID sessionId){
        GoalAndReviewDTO dto=GoalAndReviewDTO.builder()
                .id(goal.getId())
                .title(goal.getTitle())
                .description(goal.getDescription())
                .owner(this.employeeService.toDTO(goal.getGps().getEmployee()))
                .createdBy(this.employeeService.toDTO(this.employeeService.getEmployeeById(goal.getCreatedBy())))
                .goalReviews(this.goalReviewService.getAllReviewsByGoalAndSession(goal.getId(),sessionId))
                .build();
        return dto;
    }
    public List<GoalAndReviewDTO> getAllGoalAndReviewDTOsByGPS(UUID id,UUID sessionID){
        List<Goal> goals = goalRepository.findAllByGps_Id(id);
        List<GoalAndReviewDTO> goalDTOs = new ArrayList<>();
        for (Goal g : goals) {
            goalDTOs.add(toGoalAndReviewDTO(g,sessionID));
        }
        return goalDTOs;

    }
}


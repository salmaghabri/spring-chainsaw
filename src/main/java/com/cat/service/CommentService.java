package com.cat.service;

import com.cat.model.dao.Comment;
import com.cat.model.dao.Employee;
import com.cat.model.dto.Comment.ResponseComment;
import com.cat.repository.CommentRepository;
import com.cat.model.dto.Comment.CreateCommentDTO;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final EmployeeService employeeService;
    private final GoalService goalService;

    public Page<Comment> getAllComments(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    public Comment getCommentById(UUID id) {
        return this.commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with ID: " + id));


    }

    public ResponseComment createComment(CreateCommentDTO createCommentDTO) {
        Comment comment= new Comment();
        comment.setCommentedBy(createCommentDTO.getCommentedBy());
        comment.setGoal(this.goalService.getGoalById(createCommentDTO.getGoal()));
        comment.setContent(createCommentDTO.getContent());

        return this.toResponseComment( commentRepository.save(comment));
    }

    public Comment updateComment(UUID id, Comment commentDetails) {
        Comment comment = getCommentById(id);
        comment.setContent(commentDetails.getContent());

        return commentRepository.save(comment);
    }


    public void deleteCommentById(UUID id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with ID: " + id));
        commentRepository.delete(comment);
    }

    public ResponseComment toResponseComment(Comment comment){
        Employee e = employeeService.getEmployeeById(comment.getCommentedBy());
        ResponseComment responseComment = ResponseComment.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .employeeDTO(this.employeeService.toDTO(e))
                .build();
        return responseComment;
    }
    public List<ResponseComment> getAllCommentsByGoal(UUID goalId) {
        List<Comment> comments = this.commentRepository.findCommentsByGoal_Id(goalId);
        List<ResponseComment> responseComments = new ArrayList<>();
        for (Comment c : comments) {
            ResponseComment responseComment = toResponseComment(c);
            responseComments.add(responseComment);
        }
        return responseComments;
        //TODO return 404 if not found
    }
    public int getCommentsCount(UUID id){
        return this.commentRepository.countCommentByGoal_Id(id);

    }
}

package com.cat.mapper;

import com.cat.model.dao.Comment;
import com.cat.model.dto.Comment.CreateCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE= Mappers.getMapper(CommentMapper.class);
    @Mapping(target = "goal", source = "goal.id")
    CreateCommentDTO commentTocreateDTO(Comment comment);
    @Mapping(target = "goal.id", source = "goal")
    Comment createDTOToComment(CreateCommentDTO commentDTO);


}

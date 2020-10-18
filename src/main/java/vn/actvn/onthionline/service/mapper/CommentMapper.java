package vn.actvn.onthionline.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.actvn.onthionline.common.Constant;
import vn.actvn.onthionline.domain.Comment;
import vn.actvn.onthionline.service.ImageService;
import vn.actvn.onthionline.service.dto.CommentDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class CommentMapper {
    @Autowired
    private ImageService imageService;

    public CommentDto toDto(Comment comment, String username) throws IOException {
        if (null ==  comment)
            return null;
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        //commentDto.setUserLiked(getUserLikedToList(comment.getUserLiked()));
        //Check isLiked;
        commentDto.setCreatedDate(comment.getCreatedDate());
        commentDto.setUpdatedDate(comment.getUpdatedDate());
        commentDto.setFullNameUserCreated(comment.getUserCreated().getFullname());
        commentDto.setUsername(comment.getUserCreated().getUsername());
        if (null != comment.getUserCreated().getAvatar())
            commentDto.setAvatarBase64(imageService.getFile(comment.getUserCreated().getAvatar()));
        return commentDto;
    }

    public List<String> getUserLikedToList(String userLiked) {
        return Arrays.asList(userLiked.split(Constant.SEPARATOR).clone());
    }
}

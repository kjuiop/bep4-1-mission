package com.back.shared.post.dto;

import com.back.boundedcontext.post.domain.Post;
import com.back.standard.modeltype.CanGetModelTypeCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostDto implements CanGetModelTypeCode {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private long authorId;
    private String authorName;
    private String title;
    private String content;

    @Override
    public String getModelTypeCode() {
        return "Post";
    }
}

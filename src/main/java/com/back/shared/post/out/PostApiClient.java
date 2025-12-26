package com.back.shared.post.out;

import com.back.shared.post.dto.PostDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * @author : JAKE
 * @date : 25. 12. 26.
 */
@Service
public class PostApiClient {

    private final RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:8080/post/api/v1")
            .build();

    public List<PostDto> getItems() {
        return restClient.get()
                .uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public PostDto getItem(long id) {
        return restClient.get()
                .uri("/posts/%d".formatted(id))
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}

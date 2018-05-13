package com.yesdoing.webservice.service;

import com.yesdoing.webservice.domain.posts.Posts;
import com.yesdoing.webservice.domain.posts.PostsRepository;
import com.yesdoing.webservice.dto.posts.PostsMainResponseDto;
import com.yesdoing.webservice.dto.posts.PostsSaveRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsServiceTest {

    @Autowired
    private PostsService postsService;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void Dto데이터가_posts테이블에_저장된다() {
        //given
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder()
                .author("yesdoing@gmail.com")
                .content("테스트")
                .title("테스트 타이틀")
                .build();

        //when
        postsService.save(dto);

        //then
        Posts posts = postsRepository.findAll().get(0);
        assertThat(posts.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(posts.getContent()).isEqualTo(dto.getContent());
        assertThat(posts.getTitle()).isEqualTo(dto.getTitle());

    }

    @Test
    public void 목록의_데이터가_역순으로_출력된다() {
        //given

        //when
        List<PostsMainResponseDto> posts = postsService.findAllDesc();

        //then
        PostsMainResponseDto dtoIndexZero = posts.get(0);
        PostsMainResponseDto dtoIndexOne = posts.get(1);

        assertThat(dtoIndexZero.getTitle()).isEqualTo("테스트2");
        assertThat(dtoIndexOne.getTitle()).isEqualTo("테스트1");
    }
}
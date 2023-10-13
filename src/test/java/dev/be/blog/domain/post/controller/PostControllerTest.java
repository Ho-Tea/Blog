package dev.be.blog.domain.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.be.blog.domain.board.controller.BoardController;
import dev.be.blog.domain.board.dto.BoardRequest;
import dev.be.blog.domain.board.dto.BoardResponse;
import dev.be.blog.domain.board.service.BoardServiceImpl;
import dev.be.blog.domain.post.dto.PostRequest;
import dev.be.blog.domain.post.dto.PostResponse;
import dev.be.blog.domain.post.entity.Post;
import dev.be.blog.domain.post.service.PostServiceImpl;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostServiceImpl postService;

    @Autowired
    private ObjectMapper objectMapper;
    private static PostRequest validObject;
    private static PostRequest inValidObject;

    @BeforeAll
    private static void setUp() {
        BoardRequest boardRequest = new BoardRequest("새로운 보드");
        validObject = new PostRequest(boardRequest, null, "Title", "Content");
        inValidObject = new PostRequest(boardRequest, null, null, null);
    }

    @Test
    @DisplayName("Valid 조건에 맞는 파라미터를 넘기면 게시물 생성에 성공한다 - DTO 검증")
    @WithMockUser(username = "testUser", roles = "USER")
    void createPost() throws Exception {
        //given
        //when
        //then
        mvc.perform(post("/api/posts")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(validObject))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Invalid 조건에 맞는 파라미터를 넘기면 게시물 생성에 실패한다 - DTO 검증")
    @WithMockUser(username = "testUser", roles = "USER")
    void failCreatePost() throws Exception {
        //given
        //when
        //then
        mvc.perform(post("/api/posts")
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(inValidObject))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    @DisplayName("Valid 조건에 맞는 파라미터를 넘기면 모든 게시물 조회에 성공한다 - DTO 검증")
    @WithMockUser(username = "testUser", roles = "USER")
    void findAll() throws Exception {
        //given
        List<PostResponse> postResponse = new ArrayList<>();
        for(int i = 1 ; i <= 3; i++){
            PostResponse boardResponse = new PostResponse(Long.valueOf(i), String.valueOf(i));
            postResponse.add(boardResponse);
        }
        Page<PostResponse> page = new PageImpl<>(postResponse);
        //when
        when(postService.findAll(anyInt(), anyInt())).thenReturn(page);
        //then
        mvc.perform(get("/api/posts/all")
                        .param("size", String.valueOf(1))
                        .param("page", String.valueOf(3)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    @DisplayName("Valid 조건에 맞는 파라미터를 넘기면 게시물 상세 조회에 성공한다 - DTO 검증")
    @WithMockUser(username = "testUser", roles = "USER")
    void findDetail(Long postId) throws Exception {
        mvc.perform(get("/api/posts/{postId}", postId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    @DisplayName("InValid 조건에 맞는 파라미터를 넘기면 게시물 상세 조회에 실패한다 - DTO 검증")
    @WithMockUser(username = "testUser", roles = "USER")
    void failFindDetail(Long postId) throws Exception {
        mvc.perform(get("/api/posts/{postId}", postId))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    @DisplayName("Valid 조건에 맞는 파라미터를 넘기면 게시물 수정에 성공한다 - DTO 검증")
    @WithMockUser(username = "testUser", roles = "USER")
    void update(Long postId) throws Exception {
        mvc.perform(put("/api/posts/{postId}", postId)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(validObject))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    @DisplayName("InValid 조건에 맞는 파라미터를 넘기면 게시물 수정에 실패한다 - DTO 검증")
    @WithMockUser(username = "testUser", roles = "USER")
    void failUpdate(Long postId) throws Exception {
        mvc.perform(put("/api/posts/{postId}", postId)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(inValidObject))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 2L})
    @DisplayName("Valid 조건에 맞는 파라미터를 넘기면 보드 삭제에 성공한다 - DTO 검증")
    @WithMockUser(username = "testUser", roles = "USER")
    void delete(Long postId) throws Exception {
        mvc.perform(get("/api/posts/{postId}", postId))
                .andExpect(status().isOk())
                .andDo(print());
    }
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    @DisplayName("InValid 조건에 맞는 파라미터를 넘기면 보드 삭제에 실패한다 - DTO 검증")
    @WithMockUser(username = "testUser", roles = "USER")
    void failDelete(Long postId) throws Exception {
        mvc.perform(get("/api/posts/{postId}", postId))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }



}
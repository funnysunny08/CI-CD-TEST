package sopt.org.springPractice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sopt.org.springPractice.common.dto.ApiResponse;
import sopt.org.springPractice.config.jwt.JwtService;
import sopt.org.springPractice.config.resolver.UserId;
import sopt.org.springPractice.controller.dto.BoardImageListRequestDto;
import sopt.org.springPractice.controller.dto.BoardRequestDto;
import sopt.org.springPractice.exception.Success;
import sopt.org.springPractice.external.client.aws.S3Service;
import sopt.org.springPractice.service.BoardService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
//@SecurityRequirement(name = "JWT Auth")
public class BoardController {

    private final S3Service s3Service;
    private final BoardService boardService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse create(
            @UserId Long userId,
            @ModelAttribute @Valid final BoardImageListRequestDto request) {
        List<String> boardThumbnailImageUrlList = s3Service.uploadImages(request.getBoardImages(), "board");
//        String boardThumbnailImageUrl = s3Service.uploadImage(request.getThumbnail(), "board");
        boardService.create(userId, boardThumbnailImageUrlList, request);
        return ApiResponse.success(Success.CREATE_BOARD_SUCCESS);
    }
}

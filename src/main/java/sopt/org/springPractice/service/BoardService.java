package sopt.org.springPractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sopt.org.springPractice.controller.dto.BoardImageListRequestDto;
import sopt.org.springPractice.controller.dto.BoardRequestDto;
import sopt.org.springPractice.domain.Board;
import sopt.org.springPractice.domain.Image;
import sopt.org.springPractice.domain.User;
import sopt.org.springPractice.exception.Error;
import sopt.org.springPractice.exception.model.NotFoundException;
import sopt.org.springPractice.infrastructure.BoardRepository;
import sopt.org.springPractice.infrastructure.ImageRepository;
import sopt.org.springPractice.infrastructure.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public void create(Long userId, List<String> boardImageUrlList, BoardImageListRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(Error.NOT_FOUND_USER_EXCEPTION, Error.NOT_FOUND_USER_EXCEPTION.getMessage()));

        Board newBoard = Board.newInstance(
                user,
                request.getTitle(),
                request.getContent(),
                request.getIsPublic()
        );

        boardRepository.save(newBoard);

        // 이미지 생성
        for (String boardImageUrl: boardImageUrlList) {
            imageRepository.save(Image.newInstance(newBoard, boardImageUrl));
        }
    }
}

package com.sparta.travel.domain.service;

import com.sparta.travel.domain.dto.BookmarkRequestDto;
import com.sparta.travel.domain.dto.BookmarkResponseDto;
import com.sparta.travel.domain.dto.MsgResponseDto;
import com.sparta.travel.domain.entity.Bookmark;
import com.sparta.travel.domain.entity.User;
import com.sparta.travel.domain.repository.BookmarkRepository;
import com.sparta.travel.domain.repository.UserRepository;
import com.sparta.travel.global.CustomException;
import com.sparta.travel.global.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
   private final UserRepository userRepository;

    public List<BookmarkResponseDto> getAllBookmark(User user) {
        // 사용자 확인
        checkUser(user);

        List<Bookmark> bookmarkList = bookmarkRepository.findByUser(user);
        List<BookmarkResponseDto> dtoList = new ArrayList<>();
        for(Bookmark bookmark:bookmarkList){
            BookmarkResponseDto bookmarkResponseDto =new BookmarkResponseDto(bookmark);
            dtoList.add(bookmarkResponseDto);
        }

        return dtoList;
    }

    public List<BookmarkResponseDto> getCityBookMark(String city, User user) {
        // 사용자 확인
        checkUser(user);

        List<Bookmark> bookmarkList = bookmarkRepository.findByUserAndCity(user,city);
        List<BookmarkResponseDto> cityDtoList = new ArrayList<>();
        for(Bookmark bookmark:bookmarkList){
            BookmarkResponseDto bookmarkResponseDto =new BookmarkResponseDto(bookmark);
            cityDtoList.add(bookmarkResponseDto);
        }

        return cityDtoList;

    }

    public MsgResponseDto createBookMark(BookmarkRequestDto bookmarkRequestDto, User user) {
        Bookmark bookmark = new Bookmark(bookmarkRequestDto,user);
        bookmarkRepository.save(bookmark);

        return new MsgResponseDto(HttpServletResponse.SC_OK,"저장에 성공했습니다.");
    }
    public MsgResponseDto deleteBookMark(Long id, User user) {
        Bookmark bookmark = bookmarkRepository.findById(id)
                .orElseThrow(() ->  new CustomException(ErrorCode.BOOKMARK_NOT_FOUND));
        if(!user.getUserId().equals(bookmark.getUser().getUserId())){
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
        bookmarkRepository.deleteById(id);

        return new MsgResponseDto(HttpServletResponse.SC_OK,"삭제 성공했습니다.");
    }

    public void checkUser(User user){
        Optional<User> checkUser = userRepository.findByUserId(user.getUserId());
        if (checkUser.isEmpty()) {
            throw new CustomException(ErrorCode.ID_NOT_FOUND);
        }
    }
}

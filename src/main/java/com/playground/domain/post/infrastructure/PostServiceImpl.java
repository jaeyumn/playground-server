package com.playground.domain.post.infrastructure;

import com.playground.domain.member.domain.Member;
import com.playground.domain.member.domain.MemberRepository;
import com.playground.domain.post.domain.Category;
import com.playground.domain.post.domain.CategoryRepository;
import com.playground.domain.post.domain.Post;
import com.playground.domain.post.dto.request.CreatePostRequestDto;
import com.playground.domain.post.service.PostService;
import com.playground.global.exception.CustomApiException;
import com.playground.global.exception.ErrorCode;
import com.playground.global.security.SessionMemberProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final SessionMemberProvider sessionMemberProvider;

    @Transactional
    public void createPost(CreatePostRequestDto requestDto) {
        String memberId = sessionMemberProvider.getMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.MEMBER_NOT_FOUND));
        Category category = categoryRepository.findCategoryById(requestDto.getCategoryId())
                .orElseThrow(() -> new CustomApiException(ErrorCode.CATEGORY_NOT_FOUND));
        Post post = new Post(requestDto.getTitle(), requestDto.getContent(), member, category);

        member.addPost(post);
        category.addPost(post);
    }
}

package com.WAT.BEJURYU.controller;

import com.WAT.BEJURYU.dto.*;
import com.WAT.BEJURYU.entity.DrinkType;
import com.WAT.BEJURYU.service.DrinkService;
import com.WAT.BEJURYU.service.MemberService;
import com.WAT.BEJURYU.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberApiController {
    private final MemberService memberService;
    @PutMapping("/nickname/{new_nickname}")
    public ResponseEntity<MemberResponse> updateNickname(@PathVariable(value = "new_nickname") String newNickname,
                                                       @RequestBody MemberChangeNicknameRequest memberChangeNicknameRequest) {
        final MemberResponse member = memberService.updateNickname(memberChangeNicknameRequest);

        return ResponseEntity.ok(member);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long userId) {
        final MemberResponse member = memberService.getMember(userId);
        return ResponseEntity.ok(member);
    }

}

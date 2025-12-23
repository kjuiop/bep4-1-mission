package com.back.boundedcontext.member.in.api.v1;

import com.back.boundedcontext.member.app.MemberFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : JAKE
 * @date : 25. 12. 23.
 */
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberFacade memberFacade;

    @GetMapping("random-secure-tip")
    @Transactional(readOnly = true)
    public String getRandomSecureTip() {
        return memberFacade.getRandomSecureTip();
    }
}

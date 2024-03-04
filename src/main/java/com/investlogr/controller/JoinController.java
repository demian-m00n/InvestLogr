package com.investlogr.controller;

import com.investlogr.domain.dto.JoinDTO;
import com.investlogr.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO){
        log.info("join ! : " + joinDTO.getUsername());
        joinService.joinProcess(joinDTO);

        return "ok";
    }
}

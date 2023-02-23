package com.digitopia.caseStudy.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class InvitationControlService {
    @Scheduled(fixedRate = 2000L)
    public void invitationExpireManager() throws InterruptedException {

        

    }

}

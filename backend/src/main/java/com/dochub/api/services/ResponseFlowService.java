package com.dochub.api.services;

import com.dochub.api.repositories.ResponseFlowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseFlowService {
    private final ResponseFlowRepository responseFlowRepository;


}
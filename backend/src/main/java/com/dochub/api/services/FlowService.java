package com.dochub.api.services;

import com.dochub.api.repositories.FlowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlowService {
    private FlowRepository flowRepository;


}
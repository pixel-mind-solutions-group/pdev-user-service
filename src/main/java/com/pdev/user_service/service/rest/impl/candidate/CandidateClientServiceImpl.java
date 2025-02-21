package com.pdev.user_service.service.rest.impl.candidate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pdev.user_service.client.CandidateServiceClient;
import com.pdev.user_service.constant.CommonConstants;
import com.pdev.user_service.dto.candidate.CandidateDTO;
import com.pdev.user_service.exception.BaseException;
import com.pdev.user_service.service.rest.candidate.CandidateClientService;
import com.pdev.user_service.util.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CandidateClientServiceImpl implements CandidateClientService {

    private final CandidateServiceClient candidateServiceClient;
    private final ObjectMapper objectMapper;

    @Override
    public CandidateDTO saveCandidate(CandidateDTO dto) {
        log.info("CandidateClientServiceImpl -> saveCandidate() => started!");

        try {
            ResponseEntity<CommonResponse> response = candidateServiceClient.saveCandidate(dto, CommonConstants.OUT_SERVICE);
            if (response.getBody() != null && response.getBody().getStatus().equals(HttpStatus.CREATED)) {
                return objectMapper.convertValue(response.getBody().getData(), CandidateDTO.class);
            } else {
                throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while saving candidate.");
            }

        } catch (Exception e) {
            log.error("CandidateClientServiceImpl -> saveCandidate() => Exception: {}", e.getMessage());
            throw new BaseException(500, "Error occurred while calling user service to save candidate. Error: " + e.getMessage());
        }
    }
}

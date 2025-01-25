package com.pdev.user_service.client;

import com.pdev.user_service.constant.CommonConstants;
import com.pdev.user_service.dto.candidate.CandidateDTO;
import com.pdev.user_service.util.CommonResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "rempms-candidate-service")
@Headers("Content-Type: application/json")
public interface CandidateServiceClient {

    @PostMapping(value = "/api/candidate/v1/save")
    ResponseEntity<CommonResponse> saveCandidate(@RequestBody CandidateDTO dto, @RequestHeader(value = CommonConstants.OUT_SERVICE) String header);
}

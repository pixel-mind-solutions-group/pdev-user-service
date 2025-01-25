package com.pdev.user_service.service.rest;

import com.pdev.user_service.dto.candidate.CandidateDTO;

public interface CandidateClientService {

    CandidateDTO saveCandidate(CandidateDTO dto);
}

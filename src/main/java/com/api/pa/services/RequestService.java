package com.api.pa.services;

import com.api.pa.models.RequestModel;
import com.api.pa.repositories.RequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestService {

    final RequestRepository requestRepository;


    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    public RequestModel save(RequestModel requestModel) {
        return requestRepository.save(requestModel);
    }
    public Page<RequestModel> findAll(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }
    public Optional<RequestModel> findById(Integer requestId) {
        return requestRepository.findById(requestId);
    }

    public void delete(RequestModel requestModel) {
        requestRepository.delete(requestModel);
    }
}

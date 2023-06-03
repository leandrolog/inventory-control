package com.api.pa.services;

import com.api.pa.models.Request;
import com.api.pa.repositories.RequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RequestService {

    final RequestRepository requestRepository;


    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    @Transactional
    public Request save(Request request) {
        return requestRepository.save(request);
    }
    public Page<Request> findAll(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }
    public Optional<Request> findById(Integer requestId) {
        return requestRepository.findById(requestId);
    }
    @Transactional
    public void delete(Request request) {
        requestRepository.delete(request);
    }
}

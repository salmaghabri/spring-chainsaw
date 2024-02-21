package com.cat.service;

import com.cat.model.dao.Job;
import com.cat.repository.JobRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.beans.PropertyDescriptor;
import java.util.*;

@Service
public class JobService {
    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Page<Job> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    public Job getJobById(UUID id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found with ID: " + id));
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public Job updateJob(UUID id, Job jobDetails) {
        Job job = getJobById(id);
        BeanUtils.copyProperties(jobDetails, job, getNullPropertyNames(jobDetails));
        return jobRepository.save(job);
    }

    public void deleteJobById(UUID id) {
        Job job = getJobById(id);
        jobRepository.delete(job);
    }

    private String[] getNullPropertyNames(Job job) {
        final BeanWrapper srcWrapper = new BeanWrapperImpl(job);
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : srcWrapper.getPropertyDescriptors()) {
            Object srcValue = srcWrapper.getPropertyValue(propertyDescriptor.getName());
            if (srcValue == null) {
                emptyNames.add(propertyDescriptor.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
    public Job getJobByName(String name) {

        return jobRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found with name: " + name));
    }
}
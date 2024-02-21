package com.cat.controller;

import com.cat.model.dao.Job;
import com.cat.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/jobs")
public class JobController {
    private final JobService jobService;

    @GetMapping
    public ResponseEntity<Page<Job>> getAllJobs(Pageable pageable) {
        return ResponseEntity.ok().body(jobService.getAllJobs(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable(value = "id") UUID jobId) {
        Job job = jobService.getJobById(jobId);
        return ResponseEntity.ok().body(job);
    }

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobService.createJob(job);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable(value = "id") UUID jobId,
                                         @RequestBody Job jobDetails) {
        final Job updatedJob = jobService.updateJob(jobId, jobDetails);
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable(value = "id") UUID jobId) {
        jobService.deleteJobById(jobId);
        return ResponseEntity.noContent().build();
    }
}

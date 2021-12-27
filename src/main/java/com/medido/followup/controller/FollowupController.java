package com.medido.followup.controller;

import com.medido.followup.controller.dto.HealthDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class FollowupController {

    @GetMapping("/health_check")
    public ResponseEntity<HealthDto> healthCheck() {
        return new ResponseEntity<>(new HealthDto(), HttpStatus.OK);
    }
}
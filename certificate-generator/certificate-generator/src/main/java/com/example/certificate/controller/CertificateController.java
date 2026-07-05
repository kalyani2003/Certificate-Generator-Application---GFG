package com.example.certificate.controller;

import com.example.certificate.model.CertificateRequest;
import com.example.certificate.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @ModelAttribute("certificate")
    public CertificateRequest certificateRequest() {
        return new CertificateRequest();
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/generate")
    public ResponseEntity<ByteArrayResource> generateCertificate(
            @ModelAttribute("certificate") CertificateRequest request) throws Exception {

        byte[] pdf = certificateService.generateCertificate(request);

        ByteArrayResource resource = new ByteArrayResource(pdf);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + request.getName() + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdf.length)
                .body(resource);
    }

}
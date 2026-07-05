package com.example.certificate.service;

import com.example.certificate.model.CertificateRequest;
import com.example.certificate.util.PdfGenerator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class CertificateService {

    public byte[] generateCertificate(CertificateRequest request) throws Exception {

        String templatePath;

        switch (request.getCertificateType()) {

            case "MERN":
                templatePath = "certificate/MERN.pdf";
                break;

            case "DSA":
                templatePath = "certificate/DSA.pdf";
                break;

            case "DA":
            default:
                templatePath = "certificate/DA.pdf";
                break;
        }

        ClassPathResource resource = new ClassPathResource(templatePath);

        return PdfGenerator.generateCertificate(
                resource.getFile().getAbsolutePath(),
                request
        );

    }

}
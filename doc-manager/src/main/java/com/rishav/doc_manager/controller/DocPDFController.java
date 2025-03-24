package com.rishav.doc_manager.controller;

import com.rishav.doc_manager.service.DocPDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/docs")
public class DocPDFController {

    private DocPDFService docPDFService;

    @Autowired
    public DocPDFController(DocPDFService docPDFService) {
        this.docPDFService = docPDFService;
    }

    @GetMapping("/status/check")
    public String statusCheck() {
        return "DocManager is running on port ";
    }

    @PostMapping("/uploadTemplate")
    public ResponseEntity<String> uploadTemplate(@RequestParam("file") MultipartFile file) {
        return this.docPDFService.uploadTemplate(file);
    }

    @PostMapping(value = "/generatePdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generatePdf(@RequestBody List<String> data,
                                              @RequestParam(value = "template", required = false) String templateName) {
        return this.docPDFService.generatePdf(data, templateName);
    }
}

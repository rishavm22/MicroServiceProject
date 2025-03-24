package com.rishav.doc_manager.service;

import com.rishav.doc_manager.DocxPlaceholderReplacer;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.docx4j.Docx4J;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class DocPDFService {

    public ResponseEntity<String> uploadTemplate(MultipartFile file) {
        try {
            // Get the original filename
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//            fileName = fileName.split(".")[0];
            // Check if the file is empty
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload.");
            }

            // Define the directory to save the uploaded files (e.g., "uploads")
            String uploadDir = "uploads";

            // Create the directory if it doesn't exist
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save the file
            Path filePath = Paths.get(uploadDir, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("File uploaded and saved successfully: " + fileName + " File Path " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    public ResponseEntity<byte[]> generatePdf(List<String> data, String templateName) {
        try {
            // If no templateName is provided, use the default template
            var templateFile = (templateName == null || templateName.isEmpty()) ? "template.docx" : "uploads/" + templateName;

            File docxFile = new File("C:\\Users\\Rishav Mishra\\Documents\\Projects\\MicroServiceProject\\doc-manager\\uploads\\Sample Doc.docx"); // Replace with your file path
            var bytefileContent = Files.readAllBytes(docxFile.toPath());
            // Load the template from the specified path
            InputStream docxInputStream = new ByteArrayInputStream(bytefileContent);

            WordprocessingMLPackage wordMLPackage = Docx4J.load(docxInputStream);

            // Replace placeholders
            File modifiedDocxFile = File.createTempFile("modified", ".docx");
            DocxPlaceholderReplacer.replacePlaceholders(new FileInputStream(templateFile), modifiedDocxFile, data);
            wordMLPackage = Docx4J.load(modifiedDocxFile);
            modifiedDocxFile.delete();

            // Convert to XSL-FO
            File xslFoFile = File.createTempFile("temp", ".fo");

            var foSettings = new FOSettings();
            foSettings.setWmlPackage(wordMLPackage);
            Docx4J.toFO(foSettings, new FileOutputStream(xslFoFile), 0);

            // Convert to PDF
            File pdfFile = File.createTempFile("temp", ".pdf");
            convertToPdfUsingFop(xslFoFile, pdfFile);

            // Read the PDF into a byte array
            byte[] pdfBytes = readPdfBytes(pdfFile);

            // Clean up temporary files
            xslFoFile.delete();
            pdfFile.delete();

            // Set headers for PDF download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "generated.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void convertToPdfUsingFop(File xslFoFile, File pdfFile) throws Exception {
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, new FileOutputStream(pdfFile));

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();

        StreamSource src = new StreamSource(xslFoFile);
        SAXResult res = new SAXResult(fop.getDefaultHandler());

        transformer.transform(src, res);
    }

    private byte[] readPdfBytes(File pdfFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(pdfFile);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }
}

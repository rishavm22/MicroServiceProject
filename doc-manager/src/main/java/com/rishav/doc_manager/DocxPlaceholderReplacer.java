package com.rishav.doc_manager;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.Marshaller;
import org.docx4j.Docx4J;
import org.docx4j.jaxb.NamespacePrefixMapperUtils;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@Service
public class DocxPlaceholderReplacer {

    public static void replacePlaceholders(InputStream docxInputStream, File outputFile, List<String> data) throws Exception {
        // Load the Word document
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(docxInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

        // Get the document body
        Body body = documentPart.getJaxbElement().getBody();

        // Replace placeholders in paragraphs and tables
        replacePlaceholdersInContent(body.getContent(), data);

        // Save the modified document
        wordMLPackage.save(outputFile);
        System.out.println("Document modified and saved to " + outputFile.getAbsolutePath());
    }

    private static void replacePlaceholdersInContent(List<Object> content, List<String> data) {
        int placeholderCount = 0;
        for (Object obj : content) {
            if (placeholderCount >= data.size()) break; // Stop if all data is used

            if (obj instanceof P) { // Paragraph
                P paragraph = (P) obj;
                placeholderCount = replacePlaceholdersInRuns(paragraph.getContent(), data, placeholderCount);
            } else if (obj instanceof Tbl) { // Table
                Tbl table = (Tbl) obj;
                for (Object rowObj : table.getContent()) {
                    if (rowObj instanceof Tr) { // Table Row
                        Tr row = (Tr) rowObj;
                        for (Object cellObj : row.getContent()) {
                            if (cellObj instanceof Tc) { // Table Cell
                                Tc cell = (Tc) cellObj;
                                placeholderCount = replacePlaceholdersInRuns(cell.getContent(), data, placeholderCount);
                            }
                        }
                    }
                }
            }
        }
    }

    private static int replacePlaceholdersInRuns(List<Object> content, List<String> data, int placeholderCount) {
        for (Object obj : content) {
            if (placeholderCount >= data.size()) break; // Stop if all data is used

            if (obj instanceof R) { // Run (text)
                R run = (R) obj;
                List<Object> runContent = run.getContent();
                for (int i = 0; i < runContent.size(); i++) {
                    Object rObj = runContent.get(i);
                    if (rObj instanceof JAXBElement) {
                        JAXBElement<?> jaxbElement = (JAXBElement<?>) rObj;
                        if (jaxbElement.getValue() instanceof Text) {
                            Text text = (Text) jaxbElement.getValue();
                            String textValue = text.getValue();
                            while (textValue.contains("************") && placeholderCount < data.size()) {
                                textValue = textValue.replaceFirst("\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*", data.get(placeholderCount));
                                placeholderCount++;
                            }
                            text.setValue(textValue);
                        }
                    }
                }
            }
        }
        return placeholderCount;
    }

    public static void main(String[] args) {
        try (InputStream docxInputStream = DocxPlaceholderReplacer.class.getResourceAsStream("/template.docx")) {
            if (docxInputStream == null) {
                System.err.println("Template file not found!");
                return;
            }

            File outputFile = new File("modified.docx");
            List<String> data = List.of("Value 1", "Value 2", "Value 3"); // Example data
            replacePlaceholders(docxInputStream, outputFile, data);
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

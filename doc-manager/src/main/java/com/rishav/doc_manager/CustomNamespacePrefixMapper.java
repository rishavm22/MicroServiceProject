package com.rishav.doc_manager;

import org.docx4j.jaxb.NamespacePrefixMapperInterface;


public class CustomNamespacePrefixMapper implements NamespacePrefixMapperInterface {

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if ("http://schemas.openxmlformats.org/wordprocessingml/2006/main".equals(namespaceUri)) {
            return "w"; // Or any prefix you prefer
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] { "http://schemas.openxmlformats.org/wordprocessingml/2006/main" };
    }

    @Override
    public String[] getPreDeclaredNamespaceUris2() {
        return new String[0];
    }

    @Override
    public String[] getContextualNamespaceDecls() {
        return new String[0];
    }

}

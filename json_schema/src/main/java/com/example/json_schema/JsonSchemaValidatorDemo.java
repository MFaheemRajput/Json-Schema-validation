package com.example.json_schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import com.networknt.schema.SpecVersion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

public class JsonSchemaValidatorDemo {
    // Helper method to load JSON from resources
    private JsonNode loadJsonFromResources(String fileName) throws IOException {
        // Load the file as an InputStream from the resources folder
        InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(fileName);

        if (resourceStream == null) {
            throw new IOException("File not found in resources: " + fileName);
        }

        // Read the JSON content
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(resourceStream);
    }

    // Method to load JSON and Schema, and validate it
    public void validateJson(String schemaFilePath, String jsonFilePath) throws IOException {
        // Load schema and JSON from files

        JsonNode schemaNode = loadJsonFromResources(schemaFilePath);
        JsonNode rootNode = loadJsonFromResources(jsonFilePath);

        // Create schema factory and validate
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        JsonSchema schema = schemaFactory.getSchema(schemaNode);

        if (rootNode.isArray()) {
            // Loop through each object in the array and validate it
            Iterator<JsonNode> elements = rootNode.elements();
            while (elements.hasNext()) {
                JsonNode element = elements.next();
                Set<ValidationMessage> errors = schema.validate(element);
                if (errors.isEmpty()) {
                    System.out.println("Validation successful!");
                } else {
                    System.out.println("Validation errors:");
                    for (ValidationMessage error : errors) {
                        System.out.println(error.getMessage());
                    }
                }
            }
        }
    }
}
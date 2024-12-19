package com.example.json_schema;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // Example usage
        JsonSchemaValidatorDemo validator = new JsonSchemaValidatorDemo();

        // Pass the paths of the JSON Schema and JSON files to validate
        String schemaFilePath = "person-schema.json";
        String jsonFilePath = "persons.json";

        try {
            validator.validateJson(schemaFilePath, jsonFilePath);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
package com.assignment.name_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private JsonManipulationService service;

    @PostMapping("/manipulate")
    public ResponseEntity<String> manipulateJson(@RequestParam String replacements) {
        try {
            String jsonModel = "{\"menu\": {\"id\": \"file\", \"value\": \"File\", \"popup\": {\"menuitem\": [{\"value\": \"New\", \"onclick\": \"CreateDoc()\"}, {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"}, {\"value\": \"Save\", \"onclick\": \"SaveDoc()\"}]}}}";

            String[] pairs = replacements.split(",");
            Map<String, String> replacementMap = new HashMap<>();

            for (String pair : pairs) {
                String[] keyValue = pair.split(":::");
                if (keyValue.length == 2) {
                    replacementMap.put(keyValue[0], keyValue[1]);
                } else {
                    throw new IllegalArgumentException("Invalid replacement format: " + pair);
                }
            }

            String result = service.manipulateJson(jsonModel, replacementMap);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}
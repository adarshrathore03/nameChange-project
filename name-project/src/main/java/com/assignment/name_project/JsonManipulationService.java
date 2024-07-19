package com.assignment.name_project;


import com.assignment.name_project.model.JsonModelEntity;
import com.assignment.name_project.repository.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class JsonManipulationService {

    @Autowired
    private Repository repository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String manipulateJson(String jsonInput, Map<String, String> replacements) throws IOException {
        ObjectNode root = (ObjectNode) objectMapper.readTree(jsonInput);

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String oldValue = entry.getKey();
            String newValue = entry.getValue();
            replaceValue(root, oldValue, newValue);
        }

        String modifiedJson = objectMapper.writeValueAsString(root);

        JsonModelEntity entity = new JsonModelEntity();
        entity.setJsonModel(modifiedJson);
        repository.save(entity);

        return modifiedJson;
    }

    private void replaceValue(ObjectNode node, String oldValue, String newValue) {
        node.fieldNames().forEachRemaining(fieldName -> {
            if (node.get(fieldName).isValueNode() && node.get(fieldName).asText().equals(oldValue)) {
                ((ObjectNode) node).put(fieldName, newValue);
            } else if (node.get(fieldName).isObject()) {
                replaceValue((ObjectNode) node.get(fieldName), oldValue, newValue);
            } else if (node.get(fieldName).isArray()) {
                node.get(fieldName).forEach(item -> {
                    if (item.isObject()) {
                        replaceValue((ObjectNode) item, oldValue, newValue);
                    }
                });
            }
        });
    }
}
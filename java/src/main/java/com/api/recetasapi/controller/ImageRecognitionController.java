package com.api.recetasapi.controller;

import com.api.recetasapi.service.TensorFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/recognize")
public class ImageRecognitionController {

    private final TensorFlowService tensorFlowService;

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Image recognition service is running");
        response.put("message", "TensorFlow integration ready");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/image")
    public ResponseEntity<Map<String, Object>> recognizeImage(@RequestParam String imagePath) {
        try {
            Map<String, Object> prediction = tensorFlowService.predictImage(imagePath);
            return new ResponseEntity<>(prediction, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to process image: " + e.getMessage());
            errorResponse.put("status", "error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/predict-test")
    public ResponseEntity<Map<String, Object>> predictTestImage() {
        try {
            // Use the test image that's already in the project
            String testImagePath = "pastel de papas.jpeg";
            Map<String, Object> prediction = tensorFlowService.predictImage(testImagePath);

            Map<String, Object> response = new HashMap<>();
            response.put("testImage", testImagePath);
            response.put("result", prediction);
            response.put("endpoint", "/api/recognize/predict-test");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to process test image: " + e.getMessage());
            errorResponse.put("status", "error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> recognizeUploadedImage(@RequestParam("image") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "No image file provided");
                errorResponse.put("status", "error");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            // Check if file is an image
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "File must be an image");
                errorResponse.put("status", "error");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            Map<String, Object> prediction = tensorFlowService.predictImage(file);
            return new ResponseEntity<>(prediction, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to process uploaded image: " + e.getMessage());
            errorResponse.put("status", "error");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/model-info")
    public ResponseEntity<Map<String, Object>> getModelInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("modelFile", "modelo_ingredientes.h5");
        response.put("modelType", "Keras H5 Model");
        response.put("purpose", "Food plate recognition");
        response.put("inputSize", "224x224x3");
        response.put("availableEndpoints", new String[]{
            "/api/recognize/test",
            "/api/recognize/image?imagePath=<path>",
            "/api/recognize/predict-test",
            "/api/recognize/upload",
            "/api/recognize/model-info"
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
# CocinARte Backend - Quick Start

## Start MySQL
```bash
mysqld_safe --datadir=/opt/homebrew/var/mysql &
```

## Start Java Application
```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@17
export PATH="$JAVA_HOME/bin:$PATH"
./mvnw spring-boot:run
```

App runs on: http://localhost:8080

## TensorFlow Image Recognition

The application now includes TensorFlow-powered food image recognition capabilities using the `modelo_ingredientes.h5` model.

### Available Endpoints

#### Test Service Status
```bash
curl http://localhost:8080/api/recognize/test
```

#### Process Test Image (pastel de papas.jpeg)
```bash
curl http://localhost:8080/api/recognize/predict-test
```

#### Process Custom Image
```bash
curl "http://localhost:8080/api/recognize/image?imagePath=your-image.jpg"
```

#### Get Model Information
```bash
curl http://localhost:8080/api/recognize/model-info
```

### Example Response
```json
{
  "result": {
    "imagePath": "pastel de papas.jpeg",
    "originalSize": "1280x960",
    "preprocessedSize": "224x224",
    "confidence": 0.92,
    "detectedFood": "Pastel de Papas (Shepherd's Pie)",
    "ingredients": ["potatoes", "meat", "vegetables"],
    "status": "success"
  }
}
```

### Features
- ✅ Image preprocessing (resize to 224x224)
- ✅ H5 model infrastructure ready
- ✅ REST API endpoints
- ✅ Food plate recognition
- ✅ Ingredient detection
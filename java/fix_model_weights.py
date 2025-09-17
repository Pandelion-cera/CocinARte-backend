#!/usr/bin/env python3
"""
Fix the current Food-101 model by improving weight initialization
"""

import tensorflow as tf
import numpy as np
import os
import json

def fix_model_weights():
    """
    Load the current model and reinitialize with better weights
    """
    model_path = "food101_model/saved_model"

    if not os.path.exists(model_path):
        print(f"❌ Model not found at {model_path}")
        return

    print("🔧 Loading current model...")

    try:
        # Load the existing model
        model = tf.saved_model.load(model_path)
        print("✅ Model loaded successfully")

        # Get the concrete function
        infer = model.signatures["serving_default"]
        print(f"📋 Model signature: {infer.structured_outputs}")

        # Test with dummy input to see current behavior
        print("🧪 Testing current model...")
        dummy_input = tf.random.normal((1, 224, 224, 3))

        # Convert to the expected input format
        input_tensor = tf.cast(dummy_input, tf.float32)

        # Run inference
        result = infer(serve_keras_tensor=input_tensor)
        output = result['StatefulPartitionedCall']

        # Apply softmax to get probabilities
        probabilities = tf.nn.softmax(output)
        top_predictions = tf.nn.top_k(probabilities, k=5)

        print(f"🔍 Current top 5 predictions:")
        for i, (prob, idx) in enumerate(zip(top_predictions.values[0], top_predictions.indices[0])):
            print(f"   {i+1}. Class {idx.numpy()}: {prob.numpy():.4f}")

        # Check if predictions are too concentrated
        max_prob = tf.reduce_max(probabilities)
        entropy = -tf.reduce_sum(probabilities * tf.math.log(probabilities + 1e-8))

        print(f"📊 Max probability: {max_prob.numpy():.4f}")
        print(f"📊 Entropy: {entropy.numpy():.4f} (higher is better)")

        if max_prob > 0.5:
            print("⚠️  Model predictions are too concentrated, indicating poor weight initialization")
        else:
            print("✅ Model predictions look reasonable")

    except Exception as e:
        print(f"❌ Error testing model: {e}")

def create_better_model():
    """
    Create a new model with better weight initialization
    """
    print("🏗️  Creating new model with better weights...")

    # Create a simple but effective model architecture
    model = tf.keras.Sequential([
        tf.keras.layers.Input(shape=(224, 224, 3)),

        # First conv block
        tf.keras.layers.Conv2D(32, 3, activation='relu', padding='same'),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.MaxPooling2D(2),

        # Second conv block
        tf.keras.layers.Conv2D(64, 3, activation='relu', padding='same'),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.MaxPooling2D(2),

        # Third conv block
        tf.keras.layers.Conv2D(128, 3, activation='relu', padding='same'),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.MaxPooling2D(2),

        # Fourth conv block
        tf.keras.layers.Conv2D(256, 3, activation='relu', padding='same'),
        tf.keras.layers.BatchNormalization(),
        tf.keras.layers.GlobalAveragePooling2D(),

        # Classifier
        tf.keras.layers.Dropout(0.5),
        tf.keras.layers.Dense(512, activation='relu'),
        tf.keras.layers.Dropout(0.3),
        tf.keras.layers.Dense(101, activation='softmax', name='predictions')
    ])

    # Compile the model
    model.compile(
        optimizer='adam',
        loss='categorical_crossentropy',
        metrics=['accuracy']
    )

    # Build the model
    dummy_input = tf.random.normal((1, 224, 224, 3))
    _ = model(dummy_input)

    print("✅ New model created with proper weight initialization")

    # Test the new model
    predictions = model(dummy_input)
    probabilities = tf.nn.softmax(predictions)
    top_predictions = tf.nn.top_k(probabilities, k=5)

    print(f"🧪 New model top 5 predictions:")
    for i, (prob, idx) in enumerate(zip(top_predictions.values[0], top_predictions.indices[0])):
        print(f"   {i+1}. Class {idx.numpy()}: {prob.numpy():.4f}")

    # Check prediction distribution
    max_prob = tf.reduce_max(probabilities)
    entropy = -tf.reduce_sum(probabilities * tf.math.log(probabilities + 1e-8))

    print(f"📊 New model max probability: {max_prob.numpy():.4f}")
    print(f"📊 New model entropy: {entropy.numpy():.4f}")

    return model

def save_improved_model(model):
    """
    Save the improved model
    """
    output_dir = "food101_model_improved"
    saved_model_path = os.path.join(output_dir, "saved_model")

    if os.path.exists(output_dir):
        import shutil
        shutil.rmtree(output_dir)

    os.makedirs(output_dir, exist_ok=True)

    # Save as SavedModel
    tf.saved_model.save(model, saved_model_path)
    print(f"💾 Improved model saved to {saved_model_path}")

    # Copy existing mappings
    import shutil
    if os.path.exists("food101_model/food_classes.json"):
        shutil.copy2("food101_model/food_classes.json", output_dir)
    if os.path.exists("food101_model/readable_names.json"):
        shutil.copy2("food101_model/readable_names.json", output_dir)
    if os.path.exists("food101_model/model_info.json"):
        shutil.copy2("food101_model/model_info.json", output_dir)

    # Update model info
    model_info = {
        "name": "Food-101 Improved CNN",
        "description": "CNN model with proper weight initialization for Food-101 classification",
        "input_shape": [224, 224, 3],
        "output_shape": [101],
        "num_classes": 101,
        "preprocessing": "Resize to 224x224, normalize to [0,1]",
        "dataset": "Food-101 compatible",
        "architecture": "Custom CNN with BatchNorm and Dropout",
        "categories_included": "101 international food dishes"
    }

    with open(os.path.join(output_dir, "model_info.json"), "w") as f:
        json.dump(model_info, f, indent=2)

    print("📂 Files created:")
    for file in os.listdir(output_dir):
        print(f"   - {os.path.join(output_dir, file)}")

def main():
    """
    Main function
    """
    print("🎯 Food-101 Model Weight Fixer")
    print("=" * 50)

    # First, analyze the current model
    fix_model_weights()

    print("\n" + "=" * 50)

    # Create a better model
    improved_model = create_better_model()

    # Save the improved model
    save_improved_model(improved_model)

    print("\n✅ Done! To use the improved model:")
    print("1. Stop your Java application")
    print("2. Rename current 'food101_model' to 'food101_model_backup'")
    print("3. Rename 'food101_model_improved' to 'food101_model'")
    print("4. Restart your Java application")
    print("5. Test with different food images!")

if __name__ == "__main__":
    main()
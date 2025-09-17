"use client";

import { useState, useRef } from "react";
import { Camera, Upload, Sparkles, ChefHat } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import { CameraCapture } from "@/components/CameraCapture";
import { IngredientAnalysis } from "@/components/IngredientAnalysis";
import { RecipeResults } from "@/components/RecipeResults";

export default function HomePage() {
  const [imageData, setImageData] = useState<string | null>(null);
  const [isAnalyzing, setIsAnalyzing] = useState(false);
  const [ingredients, setIngredients] = useState<string[]>([]);
  const [showResults, setShowResults] = useState(false);
  const { toast } = useToast();
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleImageCapture = (imageSrc: string) => {
    setImageData(imageSrc);
    setShowResults(false);
    setIngredients([]);
  };

  const handleFileUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        const result = e.target?.result as string;
        handleImageCapture(result);
      };
      reader.readAsDataURL(file);
    }
  };

  const analyzeImage = async () => {
    if (!imageData) return;

    setIsAnalyzing(true);

    try {
      // Step 1: Convert base64 image to blob and send to Java for food recognition
      const base64Response = await fetch(imageData);
      const blob = await base64Response.blob();

      const formData = new FormData();
      formData.append('image', blob, 'image.jpg');

      const recognitionResponse = await fetch('/api/recognize-food', {
        method: 'POST',
        body: formData,
      });

      if (!recognitionResponse.ok) {
        throw new Error('Failed to recognize food in image');
      }

      const recognitionData = await recognitionResponse.json();
      const detectedFood = recognitionData.detectedFood;

      toast({
        title: "Food detected!",
        description: `Detected: ${detectedFood}`,
      });

      // Step 2: Get ingredients for the detected food using OpenAI
      const ingredientsResponse = await fetch('/api/analyze-ingredients', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ foodName: detectedFood }),
      });

      if (!ingredientsResponse.ok) {
        throw new Error('Failed to analyze ingredients');
      }

      const ingredientsData = await ingredientsResponse.json();

      setIngredients(ingredientsData.ingredients);
      setIsAnalyzing(false);
      setShowResults(true);

      toast({
        title: "Ingredients analyzed!",
        description: `Found ${ingredientsData.ingredients.length} ingredients for ${detectedFood}.`,
      });
    } catch (error) {
      console.error('Error in analysis process:', error);
      setIsAnalyzing(false);

      toast({
        title: "Error",
        description: "Failed to analyze image. Please try again.",
        variant: "destructive",
      });
    }
  };

  const resetApp = () => {
    setImageData(null);
    setIngredients([]);
    setShowResults(false);
    setIsAnalyzing(false);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-cream via-background to-brown-light">
      <div className="container mx-auto px-4 py-8">
        {/* Header */}
        <div className="text-center mb-8">
          <div className="flex items-center justify-center gap-2 mb-4">
            <ChefHat className="h-8 w-8 text-primary" />
            <h1 className="text-4xl font-bold bg-gradient-primary bg-clip-text text-transparent">
              RecipeSnap
            </h1>
          </div>
          <p className="text-muted-foreground text-lg">
            Snap a photo of your ingredients and discover amazing recipes
          </p>
        </div>

        {!imageData ? (
          /* Welcome Screen */
          <Card className="max-w-md mx-auto p-8 text-center shadow-warm border-0 bg-card/80 backdrop-blur-sm">
            <div className="space-y-6">
              <div className="w-24 h-24 mx-auto bg-gradient-primary rounded-full flex items-center justify-center">
                <Camera className="h-12 w-12 text-white" />
              </div>

              <div>
                <h2 className="text-2xl font-semibold mb-2">Start Cooking!</h2>
                <p className="text-muted-foreground">
                  Take a photo of your ingredients or upload an existing image
                </p>
              </div>

              <div className="space-y-3">
                <CameraCapture onCapture={handleImageCapture} />

                <div className="flex items-center gap-3">
                  <div className="flex-1 h-px bg-border"></div>
                  <span className="text-muted-foreground text-sm">or</span>
                  <div className="flex-1 h-px bg-border"></div>
                </div>

                <Button
                  variant="outline"
                  className="w-full"
                  onClick={() => fileInputRef.current?.click()}
                >
                  <Upload className="w-4 h-4 mr-2" />
                  Upload Image
                </Button>

                <input
                  ref={fileInputRef}
                  type="file"
                  accept="image/*"
                  onChange={handleFileUpload}
                  className="hidden"
                />
              </div>
            </div>
          </Card>
        ) : (
          /* Analysis Screen */
          <div className="max-w-4xl mx-auto space-y-6">
            <Card className="p-6 shadow-warm border-0 bg-card/80 backdrop-blur-sm">
              <div className="grid md:grid-cols-2 gap-6">
                {/* Image Preview */}
                <div className="space-y-4">
                  <h3 className="text-xl font-semibold">Your Photo</h3>
                  <div className="relative overflow-hidden rounded-lg shadow-soft">
                    <img
                      src={imageData}
                      alt="Captured food"
                      className="w-full h-64 object-cover"
                    />
                  </div>

                  <div className="flex gap-2">
                    <Button onClick={resetApp} variant="outline" className="flex-1">
                      Take New Photo
                    </Button>

                    {!isAnalyzing && !showResults && (
                      <Button
                        onClick={analyzeImage}
                        className="flex-1 bg-gradient-primary hover:opacity-90 transition-all"
                      >
                        <Sparkles className="w-4 h-4 mr-2" />
                        Analyze Ingredients
                      </Button>
                    )}
                  </div>
                </div>

                {/* Analysis Results */}
                <div className="space-y-4">
                  <h3 className="text-xl font-semibold">AI Analysis</h3>

                  <IngredientAnalysis
                    isAnalyzing={isAnalyzing}
                    ingredients={ingredients}
                  />
                </div>
              </div>
            </Card>

            {showResults && ingredients.length > 0 && (
              <RecipeResults ingredients={ingredients} />
            )}
          </div>
        )}
      </div>
    </div>
  );
}
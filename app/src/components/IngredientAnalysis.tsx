import { Loader2, CheckCircle } from "lucide-react";
import { Badge } from "@/components/ui/badge";

interface IngredientAnalysisProps {
  isAnalyzing: boolean;
  ingredients: string[];
}

export const IngredientAnalysis = ({ isAnalyzing, ingredients }: IngredientAnalysisProps) => {
  if (isAnalyzing) {
    return (
      <div className="flex flex-col items-center justify-center p-8 text-center">
        <Loader2 className="h-8 w-8 animate-spin text-primary mb-4" />
        <h4 className="text-lg font-semibold mb-2">Analyzing your ingredients...</h4>
        <p className="text-muted-foreground text-sm">
          Our AI is identifying the ingredients in your photo
        </p>
      </div>
    );
  }

  if (ingredients.length === 0) {
    return (
      <div className="p-8 text-center text-muted-foreground">
        <p>Click "Analyze Ingredients" to start!</p>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <div className="flex items-center gap-2 text-green-600">
        <CheckCircle className="h-5 w-5" />
        <span className="font-semibold">Ingredients Detected</span>
      </div>
      
      <div className="flex flex-wrap gap-2">
        {ingredients.map((ingredient, index) => (
          <Badge
            key={index}
            variant="secondary"
            className="bg-gradient-primary text-white hover:opacity-90 transition-all"
          >
            {ingredient}
          </Badge>
        ))}
      </div>
      
      <p className="text-sm text-muted-foreground">
        Found {ingredients.length} ingredients. Ready to find recipes!
      </p>
    </div>
  );
};
import { Clock, Users, ChefHat } from "lucide-react";
import { Card } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";

interface Recipe {
  id: number;
  title: string;
  description: string;
  cookTime: string;
  servings: number;
  difficulty: string;
  ingredients: string[];
  instructions: string[];
  image: string;
}

interface RecipeResultsProps {
  ingredients: string[];
}

// Mock recipe data - in real app, this would come from an API
const getMockRecipes = (ingredients: string[]): Recipe[] => [
  {
    id: 1,
    title: "Classic Tomato Basil Pasta",
    description: "A simple yet delicious pasta dish with fresh tomatoes and aromatic basil",
    cookTime: "25 min",
    servings: 4,
    difficulty: "Easy",
    ingredients: [
      "400g pasta",
      "4 large tomatoes, diced",
      "1 large onion, sliced",
      "4 cloves garlic, minced",
      "Fresh basil leaves",
      "3 tbsp olive oil",
      "Salt and pepper to taste",
      "Parmesan cheese (optional)"
    ],
    instructions: [
      "Cook pasta according to package instructions until al dente",
      "Heat olive oil in a large pan over medium heat",
      "Add onions and cook until translucent, about 5 minutes",
      "Add garlic and cook for another minute",
      "Add diced tomatoes and simmer for 10 minutes",
      "Season with salt and pepper",
      "Toss with cooked pasta and fresh basil",
      "Serve with grated Parmesan if desired"
    ],
    image: "🍝"
  },
  {
    id: 2,
    title: "Mediterranean Bruschetta",
    description: "Fresh and vibrant appetizer perfect for sharing",
    cookTime: "15 min",
    servings: 6,
    difficulty: "Easy",
    ingredients: [
      "1 baguette, sliced",
      "4 large tomatoes, diced",
      "1 small onion, finely chopped",
      "3 cloves garlic, minced",
      "Fresh basil leaves, chopped",
      "3 tbsp olive oil",
      "Salt and pepper to taste",
      "Balsamic vinegar (optional)"
    ],
    instructions: [
      "Toast baguette slices until golden",
      "Mix diced tomatoes, onion, and garlic in a bowl",
      "Add chopped basil and olive oil",
      "Season with salt and pepper",
      "Top each toast with the mixture",
      "Drizzle with balsamic vinegar if desired",
      "Serve immediately"
    ],
    image: "🍞"
  }
];

export const RecipeResults = ({ ingredients }: RecipeResultsProps) => {
  const recipes = getMockRecipes(ingredients);

  return (
    <div className="space-y-6">
      <div className="text-center">
        <h2 className="text-2xl font-bold mb-2">Recipe Suggestions</h2>
        <p className="text-muted-foreground">
          Here are some delicious recipes you can make with your ingredients
        </p>
      </div>

      <div className="grid md:grid-cols-2 gap-6">
        {recipes.map((recipe) => (
          <Card key={recipe.id} className="overflow-hidden shadow-warm border-0 bg-card/80 backdrop-blur-sm hover:shadow-lg transition-all duration-300">
            <div className="p-6">
              {/* Recipe Header */}
              <div className="flex items-start gap-4 mb-4">
                <div className="text-4xl">{recipe.image}</div>
                <div className="flex-1">
                  <h3 className="text-xl font-semibold mb-1">{recipe.title}</h3>
                  <p className="text-muted-foreground text-sm mb-3">
                    {recipe.description}
                  </p>
                  
                  {/* Recipe Meta */}
                  <div className="flex items-center gap-4 text-sm text-muted-foreground">
                    <div className="flex items-center gap-1">
                      <Clock className="h-4 w-4" />
                      {recipe.cookTime}
                    </div>
                    <div className="flex items-center gap-1">
                      <Users className="h-4 w-4" />
                      {recipe.servings} servings
                    </div>
                    <Badge variant="secondary" className="bg-accent/20 text-accent-foreground">
                      {recipe.difficulty}
                    </Badge>
                  </div>
                </div>
              </div>

              {/* Ingredients */}
              <div className="mb-4">
                <h4 className="font-semibold mb-2 flex items-center gap-2">
                  <ChefHat className="h-4 w-4" />
                  Ingredients
                </h4>
                <div className="grid grid-cols-1 gap-1 text-sm">
                  {recipe.ingredients.map((ingredient, index) => (
                    <div key={index} className="flex items-center gap-2">
                      <div className="w-1 h-1 bg-primary rounded-full"></div>
                      {ingredient}
                    </div>
                  ))}
                </div>
              </div>

              {/* Instructions */}
              <div>
                <h4 className="font-semibold mb-2">Instructions</h4>
                <div className="space-y-2 text-sm">
                  {recipe.instructions.map((instruction, index) => (
                    <div key={index} className="flex gap-3">
                      <div className="flex-shrink-0 w-6 h-6 bg-primary/10 text-primary rounded-full flex items-center justify-center text-xs font-semibold">
                        {index + 1}
                      </div>
                      <p className="text-muted-foreground leading-relaxed">
                        {instruction}
                      </p>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
};
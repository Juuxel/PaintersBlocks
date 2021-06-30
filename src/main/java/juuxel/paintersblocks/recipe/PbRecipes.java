package juuxel.paintersblocks.recipe;

import juuxel.paintersblocks.PaintersBlocks;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;

public final class PbRecipes {
    public static final SpecialRecipeSerializer<?> SWATCHING = register("crafting_special_swatching", new SpecialRecipeSerializer<>(SwatchingRecipe::new));

    private static <T extends RecipeSerializer<?>> T register(String id, T value) {
        return Registry.register(Registry.RECIPE_SERIALIZER, PaintersBlocks.id(id), value);
    }

    public static void init() {
    }
}

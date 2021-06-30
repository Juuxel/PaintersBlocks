package juuxel.paintersblocks.recipe;

import juuxel.paintersblocks.item.PbDyeableItem;
import juuxel.paintersblocks.item.PbItems;
import juuxel.paintersblocks.item.SwatchItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SwatchingRecipe extends SpecialCraftingRecipe {
    public SwatchingRecipe(Identifier id) {
        super(id);
    }

    @Nullable
    private Inputs findInputs(CraftingInventory inventory) {
        ItemStack clean = ItemStack.EMPTY;
        ItemStack dyed = ItemStack.EMPTY;

        for (int slot = 0; slot < inventory.size(); slot++) {
            ItemStack stack = inventory.getStack(slot);
            if (stack.isEmpty()) continue;

            Item item = stack.getItem();
            if (item != PbItems.SWATCH) return null; // invalid item
            SwatchItem swatch = (SwatchItem) item;

            if (swatch.hasColor(stack)) {
                if (!dyed.isEmpty()) return null; // duplicate dyed swatch
                dyed = stack;
            } else {
                if (!clean.isEmpty()) return null; // duplicate clean swatch
                clean = stack;
            }
        }

        return !clean.isEmpty() && !dyed.isEmpty() ? new Inputs(clean, dyed) : null;
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        return findInputs(inventory) != null;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        @Nullable Inputs inputs = findInputs(inventory);
        if (inputs == null) return ItemStack.EMPTY;

        ItemStack result = new ItemStack(PbItems.SWATCH, 2);
        PbDyeableItem.copyColor(inputs.dyed(), result);
        return result;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PbRecipes.SWATCHING;
    }

    private record Inputs(ItemStack clean, ItemStack dyed) {}
}
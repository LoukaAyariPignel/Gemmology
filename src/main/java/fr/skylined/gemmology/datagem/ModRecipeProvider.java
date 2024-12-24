package fr.skylined.gemmology.datagem;

import fr.skylined.gemmology.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        //offerSmelting();

        //offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.GEM, RecipeCategory.DECORATIONS, Blocks.AMETHYST_BLOCK); //exemple

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GEM)
                .pattern("FFF")
                .pattern("FHF")
                .pattern("FFF")
                .input('F', Items.DIAMOND)
                .input('H', Items.EMERALD)
                .criterion(hasItem(ModItems.GEM), conditionsFromItem(ModItems.GEM))
                .offerTo(exporter);
    }
}

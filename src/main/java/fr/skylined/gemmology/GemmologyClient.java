package fr.skylined.gemmology;

import fr.skylined.gemmology.component.ModComponents;
import fr.skylined.gemmology.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.ItemStack;

public class GemmologyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register(new ItemColorProvider() {
            @Override
            public int getColor(ItemStack stack, int layer)
            {
                return layer == 0 ? (int) stack.getOrDefault(ModComponents.WAVE_LENGTH, 380).floatValue() : 0xFFFFFF;
            }
        }, ModItems.GEM);
    }
}

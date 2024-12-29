package fr.skylined.gemmology.item.custom;


import fr.skylined.gemmology.component.ModComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GemItem extends Item {



    public GemItem(Settings settings) {
        super(settings);
    }

    // Initialisation de la longueur d'onde lors du craft
    @Override
    public void onCraft(ItemStack stack, World world) {
        super.onCraft(stack, world);
        if(!world.isClient()){
            initializeWavelength(stack);
        }
    }

    // Initialiser la longueur d'onde de manière aléatoire entre 380 nm et 750 nm
    private void initializeWavelength(ItemStack stack) {
        if(!stack.contains(ModComponents.WAVE_LENGTH)){
            float randomWavelength = 380 + (750 - 380) * new Random().nextFloat(); // Plage de longueurs d'onde visible (380 nm à 750 nm)
            stack.set(ModComponents.WAVE_LENGTH, randomWavelength);
        }

    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        if (stack.contains(ModComponents.WAVE_LENGTH)){
            //Récuperer les données de la gemme
            float waveLenght = stack.getOrDefault(ModComponents.WAVE_LENGTH, 0f);

            tooltip.add(Text.literal("Longueur d'onde: " + String.format("%.1f nm", waveLenght)));
            tooltip.add(Text.literal("Couleur").styled(style -> style.withColor(getColorFromWavelength(waveLenght))));
        }
    }

    // Calculer la couleur en fonction de la longueur d'onde (hue)
    private TextColor getColorFromWavelength(double wavelength) {
        float hue = (float) ((wavelength - 380) / (750 - 380));  // Plage de 0 à 1
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);  // Convertir en RGB

        return TextColor.fromRgb(rgb);
    }

}
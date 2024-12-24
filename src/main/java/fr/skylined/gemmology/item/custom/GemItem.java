package fr.skylined.gemmology.item.custom;


import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GemItem extends Item {

    private static final String WAVELENGTH_KEY = "Wavelength";

    private double wavelength;  // Longueur d'onde de la gemme

    public GemItem(Settings settings) {
        super(settings);
    }

    // Initialisation de la longueur d'onde et de la lux lors du craft
    @Override
    public void onCraft(ItemStack stack, World world) {
        super.onCraft(stack, world);
        initializeWavelength(stack);
    }

    // Initialiser la longueur d'onde de manière aléatoire entre 380 nm et 750 nm
    private void initializeWavelength(ItemStack stack) {
        stack.apply(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT, comp -> comp.apply(currentNbt -> {
            // Vérifier si l'NBT contient déjà la longueur d'onde
            if (!currentNbt.contains(WAVELENGTH_KEY)) {
                double randomWavelength = 380 + (750 - 380) * new Random().nextDouble(); // Plage de longueurs d'onde visible (380 nm à 750 nm)
                setWavelength(randomWavelength);
            }
            // Sauvegarder la longueur d'onde dans l'NBT
            currentNbt.putDouble(WAVELENGTH_KEY, this.wavelength);
        }));
    }

    public void setWavelength(double wavelength) {
        this.wavelength = wavelength;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        // Récupérer les données NBT de la gemme
        NbtComponent nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);

        if (nbtComponent != null) {
            NbtCompound nbt = nbtComponent.copyNbt();

            double wavelength = nbt.getDouble(WAVELENGTH_KEY);

            tooltip.add(Text.literal("Longueur d'onde: " + String.format("%.1f nm", wavelength)));

            tooltip.add(Text.literal("Couleur").styled(style -> style.withColor(getColorFromWavelength(wavelength))));
        }
    }

    // Calculer la couleur en fonction de la longueur d'onde (hue)
    private TextColor getColorFromWavelength(double wavelength) {
        float hue = (float) ((wavelength - 380) / (750 - 380));  // Plage de 0 à 1
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);  // Convertir en RGB

        return TextColor.fromRgb(rgb);
    }

}

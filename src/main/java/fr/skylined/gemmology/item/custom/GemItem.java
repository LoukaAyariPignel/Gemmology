package fr.skylined.gemmology.item.custom;

import fr.skylined.gemmology.energy.LightStorage;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
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

public class GemItem extends Item implements LightStorage {

    private static final String WAVELENGTH_KEY = "Wavelength";
    private static final String LUX_KEY = "Lux";

    private double wavelength;  // Longueur d'onde de la gemme
    private int lux;  // Quantité de lux générée par la gemme

    public GemItem(Settings settings) {
        super(settings);
    }

    // Initialisation de la longueur d'onde et de la lux lors du craft
    @Override
    public void onCraft(ItemStack stack, World world) {
        super.onCraft(stack, world);
        initializeWavelength(stack);
        initializeLux();
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

    // Initialiser la quantité de lux en fonction de la longueur d'onde
    private void initializeLux() {
        this.lux = calculateLux(this.wavelength); // Calculer les lux en fonction de la longueur d'onde
    }

    // Calculer les lux en fonction de la longueur d'onde
    private int calculateLux(double wavelength) {
        // Simuler la relation entre la longueur d'onde et la lux (exemple arbitraire)
        if (wavelength < 450) {
            return 200; // Bleu (longueur d'onde courte)
        } else if (wavelength < 500) {
            return 300; // Vert
        } else if (wavelength < 600) {
            return 250; // Jaune
        } else {
            return 100; // Rouge (longueur d'onde longue)
        }
    }

    @Override
    public int getLux() {
        return this.lux;
    }

    @Override
    public void setLux(int lux) {
        this.lux = lux;
    }

    @Override
    public double getWavelength() {
        return this.wavelength;
    }

    @Override
    public void setWavelength(double wavelength) {
        this.wavelength = wavelength;
    }

    // Sauvegarder les données NBT de la gemme
    @Override
    public void saveToNbt(NbtCompound nbt) {
        nbt.putDouble(WAVELENGTH_KEY, this.wavelength);
        nbt.putInt(LUX_KEY, this.lux);
    }

    // Charger les données NBT de la gemme
    @Override
    public void loadFromNbt(NbtCompound nbt) {
        this.wavelength = nbt.getDouble(WAVELENGTH_KEY);
        this.lux = nbt.getInt(LUX_KEY);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        // Récupérer les données NBT de la gemme
        NbtComponent nbtComponent = stack.get(DataComponentTypes.CUSTOM_DATA);  // Utiliser DataComponentTypes pour récupérer les données

        if (nbtComponent != null) {
            NbtCompound nbt = nbtComponent.copyNbt();

            double wavelength = nbt.getDouble(WAVELENGTH_KEY);
            int lux = nbt.getInt(LUX_KEY);

            // Afficher la longueur d'onde dans le tooltip
            tooltip.add(Text.literal("Longueur d'onde: " + String.format("%.1f nm", wavelength)));

            // Affichage de la couleur en fonction de la longueur d'onde
            tooltip.add(Text.literal("Couleur").styled(style -> style.withColor(getColorFromWavelength(wavelength))));
        }
    }

    // Calculer la couleur en fonction de la longueur d'onde (hue)
    private TextColor getColorFromWavelength(double wavelength) {
        // Convertir la longueur d'onde en une valeur de teinte (hue)
        float hue = (float) ((wavelength - 380) / (750 - 380));  // Plage de 0 à 1
        int rgb = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);  // Convertir en RGB

        // Convertir l'entier RGB en un TextColor compatible avec Minecraft
        return TextColor.fromRgb(rgb);
    }
}

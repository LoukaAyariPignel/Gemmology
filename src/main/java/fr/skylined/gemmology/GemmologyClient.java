package fr.skylined.gemmology;

import fr.skylined.gemmology.component.ModComponents;
import fr.skylined.gemmology.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;

import java.awt.*;

public class GemmologyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.ITEM.register((stack, layer) -> {
            if(stack.contains(ModComponents.WAVE_LENGTH)) {
                if (!(stack.get(ModComponents.WAVE_LENGTH) < 380 || stack.get(ModComponents.WAVE_LENGTH) > 780)){
                    if (layer == 0){
                        return getColorFromWavelength(stack.getOrDefault(ModComponents.WAVE_LENGTH, 380).floatValue())/*(int) stack.getOrDefault(ModComponents.WAVE_LENGTH, 380).floatValue()*/;
                    }
                }
            }
            return 0;

        }, ModItems.GEM);
    }

    private int getColorFromWavelength(float wavelength) {
        final float Gamma = 0.80f;
        final int IntensityMax = 255;
        float red = 0, green = 0, blue = 0;
        float factor;

        if (wavelength >= 380 && wavelength < 440) {
            red = -(wavelength - 440) / (440 - 380);
            green = 0.0f;
            blue = 1.0f;
        } else if (wavelength >= 440 && wavelength < 490) {
            red = 0.0f;
            green = (wavelength - 440) / (490 - 440);
            blue = 1.0f;
        } else if (wavelength >= 490 && wavelength < 510) {
            red = 0.0f;
            green = 1.0f;
            blue = -(wavelength - 510) / (510 - 490);
        } else if (wavelength >= 510 && wavelength < 580) {
            red = (wavelength - 510) / (580 - 510);
            green = 1.0f;
            blue = 0.0f;
        } else if (wavelength >= 580 && wavelength < 645) {
            red = 1.0f;
            green = -(wavelength - 645) / (645 - 580);
            blue = 0.0f;
        } else if (wavelength >= 645 && wavelength <= 780) {
            red = 1.0f;
            green = 0.0f;
            blue = 0.0f;
        }

        if (wavelength >= 380 && wavelength < 420) {
            factor = 0.3f + 0.7f * (wavelength - 380) / (420 - 380);
        } else if (wavelength >= 420 && wavelength < 701) {
            factor = 1.0f;
        } else if (wavelength >= 701 && wavelength < 781) {
            factor = 0.3f + 0.7f * (780 - wavelength) / (780 - 700);
        } else {
            factor = 0.0f;
        }

        if (red != 0) {
            red = Math.round(IntensityMax * Math.pow(red * factor, Gamma));
        }
        if (green != 0) {
            green = Math.round(IntensityMax * Math.pow(green * factor, Gamma));
        }
        if (blue != 0) {
            blue = Math.round(IntensityMax * Math.pow(blue * factor, Gamma));
        }

        // Retourner la couleur en format RGB (int)
        return new Color((int) red, (int) green, (int) blue).getRGB();
    }

}

package fr.skylined.gemmology.component;

import com.mojang.serialization.Codec;
import fr.skylined.gemmology.Gemmology;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    public static void initialize(){
        Gemmology.LOGGER.info("Registering {} components", Gemmology.MOD_ID);
    }

    public static final ComponentType<Float> WAVE_LENGHT = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Gemmology.MOD_ID, "wave_lenght"),
            ComponentType.<Float>builder().codec(Codec.FLOAT).build()
    );
}

package fr.skylined.gemmology.energy;

import net.minecraft.nbt.NbtCompound;

public interface LightStorage {

    int getLux();

    void setLux(int lux);

    double getWavelength();

    void setWavelength(double wavelength);

    void saveToNbt(NbtCompound nbt);

    void loadFromNbt(NbtCompound nbt);
}

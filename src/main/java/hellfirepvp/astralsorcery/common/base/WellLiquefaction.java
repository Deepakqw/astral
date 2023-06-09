/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.base;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.block.network.BlockCollectorCrystalBase;
import hellfirepvp.astralsorcery.common.item.ItemCraftingComponent;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: WellLiquefaction
 * Created by HellFirePvP
 * Date: 27.02.2017 / 17:41
 */
public class WellLiquefaction {

    public static Map<ItemStack, LiquefactionEntry> mtLiquefactions = new HashMap<>();
    private static Map<ItemStack, LiquefactionEntry> registeredLiquefactions = new HashMap<>();

    private static Map<ItemStack, LiquefactionEntry> localFallback = new HashMap<>();

    public static void init() {
        registerLiquefaction(ItemCraftingComponent.MetaType.AQUAMARINE.asStack(), BlocksAS.fluidLiquidStarlight, 0.3F, 6, new Color(0x00, 0x88, 0xDD));
        registerLiquefaction(ItemCraftingComponent.MetaType.RESO_GEM.asStack(), BlocksAS.fluidLiquidStarlight, 0.8F, 12, new Color(0x00, 0x88, 0xDD));
        registerLiquefaction(new ItemStack(ItemsAS.tunedCelestialCrystal), BlocksAS.fluidLiquidStarlight, 1.4F, 280, BlockCollectorCrystalBase.CollectorCrystalType.CELESTIAL_CRYSTAL.displayColor);
        registerLiquefaction(new ItemStack(ItemsAS.celestialCrystal), BlocksAS.fluidLiquidStarlight, 1.2F, 90, BlockCollectorCrystalBase.CollectorCrystalType.CELESTIAL_CRYSTAL.displayColor);
        registerLiquefaction(new ItemStack(ItemsAS.tunedRockCrystal), BlocksAS.fluidLiquidStarlight, 1.1F, 200, BlockCollectorCrystalBase.CollectorCrystalType.ROCK_CRYSTAL.displayColor);
        registerLiquefaction(new ItemStack(ItemsAS.rockCrystal), BlocksAS.fluidLiquidStarlight, 1F, 70, BlockCollectorCrystalBase.CollectorCrystalType.ROCK_CRYSTAL.displayColor);

        cacheLocalFallback();
    }

    private static void cacheLocalFallback() {
        if(localFallback.isEmpty()) {
            localFallback.putAll(registeredLiquefactions);
        }
    }

    public static void loadFromFallback() {
        registeredLiquefactions.clear();
        registeredLiquefactions.putAll(localFallback);
    }

    public static void registerLiquefaction(ItemStack catalystIn, Fluid producedIn, float productionMultiplier, float shatterMultiplier, Color color) {
        for (ItemStack i : registeredLiquefactions.keySet()) {
            if(i.isItemEqual(catalystIn)) {
                AstralSorcery.log.warn("Tried to register Lightwell Liquefaction that has the same input as an already existing one.");
                return;
            }
        }

        registeredLiquefactions.put(catalystIn, new LiquefactionEntry(catalystIn, producedIn, productionMultiplier, shatterMultiplier, color));
    }

    @Nullable
    public static LiquefactionEntry getLiquefactionEntry(ItemStack suggestedCatalyst) {
        for (ItemStack i : registeredLiquefactions.keySet()) {
            if(i.isItemEqual(suggestedCatalyst)) {
                return registeredLiquefactions.get(i);
            }
        }
        for (ItemStack i : mtLiquefactions.keySet()) {
            if(i.isItemEqual(suggestedCatalyst)) {
                return mtLiquefactions.get(i);
            }
        }
        return null;
    }

    @Nullable
    public static LiquefactionEntry tryRemoveLiquefaction(ItemStack stack, @Nullable Fluid fluid) {
        for (ItemStack i : registeredLiquefactions.keySet()) {
            if(i.isItemEqual(stack)) {
                LiquefactionEntry le = registeredLiquefactions.get(i);
                if(fluid == null || le.producing.equals(fluid)) {
                    registeredLiquefactions.remove(i);
                    return le;
                }
            }
        }
        return null;
    }

    public static List<LiquefactionEntry> getRegisteredLiquefactions() {
        return new ArrayList<>(registeredLiquefactions.values());
    }

    public static class LiquefactionEntry {

        public final ItemStack catalyst;
        public final Fluid producing;
        public final float productionMultiplier, shatterMultiplier;
        @Nullable
        public final Color catalystColor;

        public LiquefactionEntry(ItemStack catalyst, Fluid producing, float productionMultiplier, float shatterMultiplier, @Nullable Color catalystColor) {
            this.catalyst = catalyst;
            this.producing = producing;
            this.productionMultiplier = productionMultiplier;
            this.shatterMultiplier = Math.max(0, shatterMultiplier);
            this.catalystColor = catalystColor;
        }

    }

}

/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.item.crystal;

import hellfirepvp.astralsorcery.common.block.network.BlockCollectorCrystalBase;
import hellfirepvp.astralsorcery.common.data.research.ProgressionTier;
import hellfirepvp.astralsorcery.common.item.ItemGatedVisibility;
import hellfirepvp.astralsorcery.common.item.crystal.base.ItemRockCrystalBase;
import hellfirepvp.astralsorcery.common.item.crystal.base.ItemTunedCrystalBase;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.registry.RegistryItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ItemCelestialCrystal
 * Created by HellFirePvP
 * Date: 15.09.2016 / 14:13
 */
public class ItemCelestialCrystal extends ItemRockCrystalBase implements ItemGatedVisibility {

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        ItemStack stack = new ItemStack(this);
        CrystalProperties.applyCrystalProperties(stack, new CrystalProperties(CrystalProperties.MAX_SIZE_CELESTIAL, 100, 100));
        subItems.add(stack);
    }

    @Override
    public Color getHightlightColor(ItemStack stack) {
        return BlockCollectorCrystalBase.CollectorCrystalType.CELESTIAL_CRYSTAL.displayColor;
    }

    @Override
    public ItemTunedCrystalBase getTunedItemVariant() {
        return ItemsAS.tunedCelestialCrystal;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return RegistryItems.rarityCelestial;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isSupposedToSeeInRender(ItemStack stack) {
        return getClientProgress().getTierReached().isThisLaterOrEqual(ProgressionTier.CONSTELLATION_CRAFT);
    }

}

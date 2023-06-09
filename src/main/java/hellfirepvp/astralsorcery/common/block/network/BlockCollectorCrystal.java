/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.block.network;

import hellfirepvp.astralsorcery.common.constellation.ConstellationRegistry;
import hellfirepvp.astralsorcery.common.constellation.IWeakConstellation;
import hellfirepvp.astralsorcery.common.item.block.ItemCollectorCrystal;
import hellfirepvp.astralsorcery.common.item.crystal.CrystalProperties;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: BlockCollectorCrystal
 * Created by HellFirePvP
 * Date: 01.08.2016 / 12:58
 */
public class BlockCollectorCrystal extends BlockCollectorCrystalBase {

    public BlockCollectorCrystal() {
        super(Material.GLASS, MapColor.GRAY);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (IWeakConstellation major : ConstellationRegistry.getWeakConstellations()) {
            ItemStack stack = new ItemStack(itemIn);
            ItemCollectorCrystal.setConstellation(stack, major);
            ItemCollectorCrystal.setType(stack, CollectorCrystalType.ROCK_CRYSTAL);
            CrystalProperties.applyCrystalProperties(stack, new CrystalProperties(CrystalProperties.MAX_SIZE_ROCK, 100, 100));
            list.add(stack);
        }
    }

    @Nonnull
    @Override
    public ItemStack getDecriptor(IBlockState state) {
        return new ItemStack(BlocksAS.collectorCrystal);
    }

}

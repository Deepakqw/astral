package hellfirepvp.astralsorcery.common.base;

import hellfirepvp.astralsorcery.common.util.ItemUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: MeltInteraction
 * Created by HellFirePvP
 * Date: 22.04.2017 / 16:40
 */
public interface MeltInteraction {

    public boolean isMeltable(World world, BlockPos pos, IBlockState state);

    default public int getMeltTickDuration() {
        return 100; //Def. Furance duration halved
    }

    //Result can be a blockstate and/or an itemstack.
    //The BlockState result, if present, takes precedence.
    @Nullable
    public IBlockState getMeltResultState();

    @Nullable
    public ItemStack getMeltResultStack();

    default public void placeResultAt(World world, BlockPos pos) {
        IBlockState result = getMeltResultState();
        if(result != null) {
            world.setBlockState(pos, result, 3);
        } else {
            world.setBlockToAir(pos);
            ItemStack resultStack = getMeltResultStack();
            if(resultStack != null && resultStack.getItem() != null) {
                ItemUtils.dropItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, resultStack);
            }
        }
    }

}

/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.integrations.mods.crafttweaker;

import hellfirepvp.astralsorcery.common.crafting.ItemHandle;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: BaseTweaker
 * Created by HellFirePvP
 * Date: 27.02.2017 / 00:57
 */
public abstract class BaseTweaker {

    @Nullable
    public static ItemStack convertToItemStack(IItemStack stack) {
        if(stack == null) {
            return null;
        }
        Object objStack = stack.getInternal();
        if (!(objStack instanceof ItemStack)) {
            MineTweakerAPI.logError("Invalid ItemStack: " + objStack);
            return null;
        } else {
            return (ItemStack) objStack;
        }
    }

    @Nullable
    public static FluidStack convertToFluidStack(ILiquidStack stack, boolean capAndLimitToBuckets) {
        if(stack == null) {
            return null;
        }
        Object objStack = stack.getInternal();
        if (!(objStack instanceof FluidStack)) {
            MineTweakerAPI.logError("Invalid FluidStack: " + objStack);
            return null;
        } else {
            FluidStack flStack = (FluidStack) objStack;
            if(capAndLimitToBuckets) {
                flStack.amount = Fluid.BUCKET_VOLUME; //Only full buckets please...
            }
            return flStack;
        }
    }

    @Nullable
    public static ItemHandle convertToHandle(IIngredient obj) {
        if(obj == null) {
            return null;
        }
        if(obj instanceof IItemStack) {
            ItemStack ret = convertToItemStack((IItemStack) obj);
            if(ret == null) return null;
            return new ItemHandle(ret);
        } else if(obj instanceof ILiquidStack) {
            FluidStack ret = convertToFluidStack((ILiquidStack) obj, true);
            if (ret == null) return null;
            return new ItemHandle(ret);
        } else if(obj instanceof IOreDictEntry) {
            return new ItemHandle(((IOreDictEntry) obj).getName());
        }
        return null;
    }

}

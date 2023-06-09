/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.integrations.mods.jei.base;

import com.google.common.collect.Lists;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: JEIBaseWrapper
 * Created by HellFirePvP
 * Date: 15.02.2017 / 16:59
 */
public abstract class JEIBaseWrapper implements IRecipeWrapper {

    @Override
    @Deprecated
    public List getInputs() {
        return Lists.newArrayList();
    }

    @Override
    @Deprecated
    public List getOutputs() {
        return Lists.newArrayList();
    }

    @Override
    @Deprecated
    public List<FluidStack> getFluidInputs() {
        return Lists.newArrayList();
    }

    @Override
    @Deprecated
    public List<FluidStack> getFluidOutputs() {
        return Lists.newArrayList();
    }
}

/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.integrations.mods.jei.base;

import mezz.jei.api.recipe.IRecipeHandler;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: JEIBaseHandler
 * Created by HellFirePvP
 * Date: 15.02.2017 / 16:58
 */
public abstract class JEIBaseHandler<T> implements IRecipeHandler<T> {

    @Override
    @Deprecated
    public String getRecipeCategoryUid() {
        return null;
    }

}

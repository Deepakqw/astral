/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.integrations.mods.jei;

import hellfirepvp.astralsorcery.common.integrations.ModIntegrationJEI;
import hellfirepvp.astralsorcery.common.integrations.mods.jei.base.JEIBaseCategory;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: CategoryInfuser
 * Created by HellFirePvP
 * Date: 11.01.2017 / 00:10
 */
public class CategoryInfuser extends JEIBaseCategory<InfuserRecipeWrapper> {

    private final IDrawable background;

    public CategoryInfuser(IGuiHelper guiHelper) {
        super("jei.category.infuser", ModIntegrationJEI.idInfuser);
        ResourceLocation location = new ResourceLocation("astralsorcery", "textures/gui/jei/recipeTemplateInfusion.png");
        background = guiHelper.createDrawable(location, 0, 0, 116, 54);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {}

    @Override
    public void drawAnimations(Minecraft minecraft) {}

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, InfuserRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup group = recipeLayout.getItemStacks();
        group.init(0, true, 18, 18);
        group.init(1, false, 94, 18);

        group.set(ingredients);
    }
}

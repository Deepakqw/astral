/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.integrations.mods.crafttweaker.tweaks;

import hellfirepvp.astralsorcery.common.integrations.ModIntegrationCrafttweaker;
import hellfirepvp.astralsorcery.common.integrations.mods.crafttweaker.BaseTweaker;
import hellfirepvp.astralsorcery.common.integrations.mods.crafttweaker.network.InfusionRecipeAdd;
import hellfirepvp.astralsorcery.common.integrations.mods.crafttweaker.network.InfusionRecipeRemove;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: InfusionRecipe
 * Created by HellFirePvP
 * Date: 27.02.2017 / 00:46
 */
@ZenClass("mods.astralsorcery.StarlightInfusion")
public class InfusionRecipe extends BaseTweaker {

    protected static final String name = "AstralSorcery Starlight Infusion";

    @ZenMethod
    public static void addInfusion(IItemStack input, IItemStack output, boolean consumeMultiple, float consumptionChance, int craftingTickTime) {
        ItemStack in = convertToItemStack(input);
        ItemStack out = convertToItemStack(output);
        if(in == null || out == null) {
            MineTweakerAPI.logError("[" + name + "] Skipping recipe due to invalid input/output.");
            return;
        }

        consumptionChance = MathHelper.clamp(consumptionChance, 0F, 1F);
        craftingTickTime = Math.max(1, craftingTickTime);

        ModIntegrationCrafttweaker.recipeModifications.add(new InfusionRecipeAdd(in, out, consumeMultiple, consumptionChance, craftingTickTime));
    }

    @ZenMethod
    public static void removeInfusion(IItemStack output) {
        ItemStack out = convertToItemStack(output);
        if(out == null) {
            MineTweakerAPI.logError("[" + name + "] Skipping recipe-remoal due to invalid output.");
            return;
        }

        ModIntegrationCrafttweaker.recipeModifications.add(new InfusionRecipeRemove(out));
    }

}

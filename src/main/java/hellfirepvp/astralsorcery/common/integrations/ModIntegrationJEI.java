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

package hellfirepvp.astralsorcery.common.integrations;

import com.google.common.collect.Lists;
import hellfirepvp.astralsorcery.common.base.LightOreTransmutations;
import hellfirepvp.astralsorcery.common.base.WellLiquefaction;
import hellfirepvp.astralsorcery.common.block.network.BlockAltar;
import hellfirepvp.astralsorcery.common.crafting.altar.AltarRecipeRegistry;
import hellfirepvp.astralsorcery.common.crafting.infusion.InfusionRecipeRegistry;
import hellfirepvp.astralsorcery.common.integrations.mods.jei.*;
import hellfirepvp.astralsorcery.common.integrations.mods.jei.altar.AltarAttunementRecipeHandler;
import hellfirepvp.astralsorcery.common.integrations.mods.jei.altar.AltarConstellationRecipeHandler;
import hellfirepvp.astralsorcery.common.integrations.mods.jei.altar.AltarDiscoveryRecipeHandler;
import hellfirepvp.astralsorcery.common.integrations.mods.jei.altar.CategoryAltarAttunement;
import hellfirepvp.astralsorcery.common.integrations.mods.jei.altar.CategoryAltarConstellation;
import hellfirepvp.astralsorcery.common.integrations.mods.jei.altar.CategoryAltarDiscovery;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.registry.RegistryRecipes;
import hellfirepvp.astralsorcery.common.tile.TileAltar;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ModIntegrationJEI
 * Created by HellFirePvP
 * Date: 10.01.2017 / 23:21
 */
@JEIPlugin
public class ModIntegrationJEI implements IModPlugin {

    public static boolean jeiRegistrationPhase = true;

    public static final String idWell = "astralsorcery.lightwell";
    public static final String idInfuser = "astralsorcery.infuser";
    public static final String idTransmutation = "astralsorcery.lightTransmutation";

    public static final String idAltarDiscovery = "astralsorcery.altar.discovery";
    public static final String idAltarAttunement = "astralsorcery.altar.attunement";
    public static final String idAltarConstellation = "astralsorcery.altar.constellation";

    public static IStackHelper stackHelper;
    public static IJeiHelpers jeiHelpers;
    public static IRecipeRegistry recipeRegistry;

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {}

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {}

    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        stackHelper = jeiHelpers.getStackHelper();
        hideItems(registry.getJeiHelpers().getIngredientBlacklist());
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        //REMINDER: Higher tiers *must* come before lower tiers in this list.
        registry.addRecipeCategories(
                new CategoryWell(guiHelper),
                new CategoryInfuser(guiHelper),
                new CategoryTransmutation(guiHelper),
                new CategoryAltarConstellation(guiHelper),
                new CategoryAltarAttunement(guiHelper),
                new CategoryAltarDiscovery(guiHelper));

        registry.addRecipeHandlers(
                new WellRecipeHandler(),
                new InfuserRecipeHandler(),
                new TransmutationRecipeHandler(),
                new AltarConstellationRecipeHandler(),
                new AltarAttunementRecipeHandler(),
                new AltarDiscoveryRecipeHandler());

        registry.addRecipeCategoryCraftingItem(new ItemStack(BlocksAS.blockWell), idWell);
        registry.addRecipeCategoryCraftingItem(new ItemStack(BlocksAS.starlightInfuser), idInfuser);
        registry.addRecipeCategoryCraftingItem(new ItemStack(BlocksAS.lens), idTransmutation);
        registry.addRecipeCategoryCraftingItem(new ItemStack(BlocksAS.lensPrism), idTransmutation);
        registry.addRecipeCategoryCraftingItem(new ItemStack(BlocksAS.blockAltar, 1, BlockAltar.AltarType.ALTAR_1.ordinal()), idAltarDiscovery);
        registry.addRecipeCategoryCraftingItem(new ItemStack(BlocksAS.blockAltar, 1, BlockAltar.AltarType.ALTAR_2.ordinal()), idAltarAttunement);
        registry.addRecipeCategoryCraftingItem(new ItemStack(BlocksAS.blockAltar, 1, BlockAltar.AltarType.ALTAR_3.ordinal()), idAltarConstellation);

        registry.addRecipes(InfusionRecipeRegistry.recipes);
        registry.addRecipes(InfusionRecipeRegistry.mtRecipes);
        registry.addRecipes(LightOreTransmutations.getRegisteredTransmutations());
        registry.addRecipes(WellLiquefaction.getRegisteredLiquefactions());

        registry.addRecipes(AltarRecipeRegistry.recipes.get(TileAltar.AltarLevel.DISCOVERY));
        registry.addRecipes(AltarRecipeRegistry.recipes.get(TileAltar.AltarLevel.ATTUNEMENT));
        registry.addRecipes(AltarRecipeRegistry.recipes.get(TileAltar.AltarLevel.CONSTELLATION_CRAFT));

        registry.addRecipes(Lists.newArrayList(
                RegistryRecipes.rCCParchment   .makeNative(),
                RegistryRecipes.rRJournal      .makeNative(),
                RegistryRecipes.rBlackMarbleRaw.makeNative(),
                RegistryRecipes.rMarbleArch    .makeNative(),
                RegistryRecipes.rMarbleBricks  .makeNative(),
                RegistryRecipes.rMarbleChiseled.makeNative(),
                RegistryRecipes.rMarbleEngraved.makeNative(),
                RegistryRecipes.rMarblePillar  .makeNative(),
                RegistryRecipes.rMarbleRuned   .makeNative(),
                RegistryRecipes.rMarbleStairs  .makeNative()));

        jeiRegistrationPhase = false;
    }

    private void hideItems(IIngredientBlacklist blacklist) {
        blacklist.addIngredientToBlacklist(new ItemStack(BlocksAS.blockFakeTree));
        blacklist.addIngredientToBlacklist(new ItemStack(BlocksAS.translucentBlock));
        blacklist.addIngredientToBlacklist(new ItemStack(BlocksAS.blockStructural));
        blacklist.addIngredientToBlacklist(new ItemStack(BlocksAS.blockAltar, 1, 3));
        blacklist.addIngredientToBlacklist(new ItemStack(BlocksAS.blockAltar, 1, 4));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        recipeRegistry = jeiRuntime.getRecipeRegistry();
    }

}

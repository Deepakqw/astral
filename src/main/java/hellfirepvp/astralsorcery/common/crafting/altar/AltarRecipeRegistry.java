/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.crafting.altar;

import com.google.common.collect.Lists;
import hellfirepvp.astralsorcery.common.crafting.altar.recipes.AttunementRecipe;
import hellfirepvp.astralsorcery.common.crafting.altar.recipes.ConstellationRecipe;
import hellfirepvp.astralsorcery.common.crafting.altar.recipes.DiscoveryRecipe;
import hellfirepvp.astralsorcery.common.crafting.altar.recipes.TraitRecipe;
import hellfirepvp.astralsorcery.common.crafting.helper.AbstractCacheableRecipe;
import hellfirepvp.astralsorcery.common.crafting.helper.CraftingAccessManager;
import hellfirepvp.astralsorcery.common.tile.TileAltar;
import hellfirepvp.astralsorcery.common.util.ItemUtils;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: AltarRecipeRegistry
 * Created by HellFirePvP
 * Date: 22.09.2016 / 13:13
 */
public class AltarRecipeRegistry {

    public static Map<TileAltar.AltarLevel, List<AbstractAltarRecipe>> mtRecipes = new HashMap<>();
    public static Map<TileAltar.AltarLevel, List<AbstractAltarRecipe>> recipes = new HashMap<>();
    private static AbstractAltarRecipe[] compiledRecipeArray = null;

    private static Map<TileAltar.AltarLevel, List<AbstractAltarRecipe>> localFallbackCache = new HashMap<>();

    //NEVER call this. this should only get called once at post init to compile all recipes for fast access.
    //After this is called, changes to recipe registry might break stuff.
    public static void compileRecipes() {
        compiledRecipeArray = null;

        int totalNeeded = 0;
        for (TileAltar.AltarLevel level : recipes.keySet()) {
            totalNeeded += recipes.get(level).size();
        }
        for (TileAltar.AltarLevel level : mtRecipes.keySet()) {
            totalNeeded += mtRecipes.get(level).size();
        }

        int i = 0;
        compiledRecipeArray = new AbstractAltarRecipe[totalNeeded];
        for (TileAltar.AltarLevel l : TileAltar.AltarLevel.values()) {
            List<AbstractAltarRecipe> recipeList = recipes.get(l);
            for (AbstractAltarRecipe rec : recipeList) {
                compiledRecipeArray[i] = rec;
                rec.updateUniqueId(i);
                i++;
            }
            recipeList = mtRecipes.get(l);
            for (AbstractAltarRecipe rec : recipeList) {
                compiledRecipeArray[i] = rec;
                rec.updateUniqueId(i);
                i++;
            }
        }
    }

    public static void cacheLocalRecipes() {
        if(localFallbackCache.isEmpty()) {
            for (TileAltar.AltarLevel al : TileAltar.AltarLevel.values()) {
                localFallbackCache.put(al, new LinkedList<>());
                localFallbackCache.get(al).addAll(recipes.get(al));
            }
        }
    }

    public static void loadFromFallback() {
        if(!localFallbackCache.isEmpty()) {
            for (TileAltar.AltarLevel al : TileAltar.AltarLevel.values()) {
                recipes.get(al).addAll(localFallbackCache.get(al));
            }
        }
    }

    @Nullable
    public static AbstractAltarRecipe getRecipe(int id) {
        if(id < 0 || id >= compiledRecipeArray.length) return null;
        return compiledRecipeArray[id];
    }

    /*
     * Returns the Recipe that was removed if successful.
     */
    @Nullable
    public static AbstractAltarRecipe removeFindRecipeByOutputAndLevel(ItemStack output, TileAltar.AltarLevel altarLevel) {
        Iterator<AbstractAltarRecipe> iterator = recipes.get(altarLevel).iterator();
        while (iterator.hasNext()) {
            AbstractAltarRecipe rec = iterator.next();
            ItemStack out = rec.getOutputForMatching();
            if (out != null && ItemUtils.matchStackLoosely(rec.getOutputForMatching(), output)) {
                iterator.remove();
                return rec;
            }
        }
        return null;
    }

    public static TraitRecipe registerTraitRecipe(AbstractCacheableRecipe recipe) {
        TraitRecipe tr = new TraitRecipe(recipe);
        registerAltarRecipe(tr);
        return tr;
    }

    public static ConstellationRecipe registerConstellationRecipe(AbstractCacheableRecipe recipe) {
        ConstellationRecipe dr = new ConstellationRecipe(recipe);
        registerAltarRecipe(dr);
        return dr;
    }

    public static AttunementRecipe registerAttenuationRecipe(AbstractCacheableRecipe recipe) {
        AttunementRecipe dr = new AttunementRecipe(recipe);
        registerAltarRecipe(dr);
        return dr;
    }

    public static DiscoveryRecipe registerDiscoveryRecipe(AbstractCacheableRecipe recipe) {
        DiscoveryRecipe dr = new DiscoveryRecipe(recipe);
        registerAltarRecipe(dr);
        return dr;
    }

    public static <T extends AbstractAltarRecipe> T registerAltarRecipe(T recipe) {
        TileAltar.AltarLevel level = recipe.getNeededLevel();
        recipes.get(level).add(recipe);
        if(CraftingAccessManager.hasCompletedSetup()) {
            CraftingAccessManager.compile();
        }
        return recipe;
    }

    public static Collection<AbstractAltarRecipe> getRecipesForLevel(TileAltar.AltarLevel al) {
        List<AbstractAltarRecipe> cache = Lists.newLinkedList();
        cache.addAll(recipes.get(al));
        cache.addAll(mtRecipes.get(al));
        return cache;
    }


    @Nullable
    public static AbstractAltarRecipe findMatchingRecipe(TileAltar ta, boolean ignoreStarlightRequirement) {
        TileAltar.AltarLevel lowestAllowed = TileAltar.AltarLevel.DISCOVERY;
        for (int i = ta.getAltarLevel().ordinal(); i >= 0; i--) {
            TileAltar.AltarLevel lvl = TileAltar.AltarLevel.values()[i];
            if(lvl.getMatcher().mbAllowsForCrafting(ta)) {
                lowestAllowed = lvl;
                break;
            }
        }
        for (int i = lowestAllowed.ordinal(); i >= 0; i--) {
            TileAltar.AltarLevel lvl = TileAltar.AltarLevel.values()[i];
            List<AbstractAltarRecipe> validRecipes = recipes.get(lvl);
            if(validRecipes != null) {
                for (AbstractAltarRecipe rec : validRecipes) {
                    if(rec.matches(ta, ta.getInventoryHandler(), ignoreStarlightRequirement)) {
                        return rec;
                    }
                }
            }
            validRecipes = mtRecipes.get(lvl);
            if(validRecipes != null) {
                for (AbstractAltarRecipe rec : validRecipes) {
                    if(rec.matches(ta, ta.getInventoryHandler(), ignoreStarlightRequirement)) {
                        return rec;
                    }
                }
            }
        }
        return null;
        /*List<TileAltar.AltarLevel> levels = new ArrayList<>();
        List<AbstractAltarRecipe> validRecipes = new LinkedList<>();
        for (int i = 0; i < level.ordinal() + 1; i++) {
            levels.add(TileAltar.AltarLevel.values()[i]);
        }
        for (TileAltar.AltarLevel valid : levels) {
            validRecipes.addAll(recipes.get(valid));
        }
        for (AbstractAltarRecipe recipe : validRecipes) {
            if(recipe.matches(ta)) {
                return recipe;
            }
        }*/
    }

    static {
        for (TileAltar.AltarLevel al : TileAltar.AltarLevel.values()) {
            recipes.put(al, new LinkedList<>());
            mtRecipes.put(al, new LinkedList<>());
        }
    }

}

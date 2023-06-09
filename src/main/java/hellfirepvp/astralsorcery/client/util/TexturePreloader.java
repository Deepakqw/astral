/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.util;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.util.resource.AssetLibrary;
import hellfirepvp.astralsorcery.client.util.resource.AssetLoader;
import hellfirepvp.astralsorcery.common.data.config.Config;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: TexturePreloader
 * Created by HellFirePvP
 * Date: 21.09.2016 / 15:44
 */
public class TexturePreloader {

    public static void doPreloadRoutine() {
        //Needs to happen...
        AstralSorcery.log.info("[AssetLibrary] Preload mandatory textures");
        TexturePreloader.preloadMandatoryTextures();

        if(Config.clientPreloadTextures) {
            long startMs = System.currentTimeMillis();
            AstralSorcery.log.info("[AssetLibrary] Preload textures");
            TexturePreloader.preloadTextures();
            AstralSorcery.log.info("[AssetLibrary] Initializing sprite library");
            SpriteLibrary.init();
            AstralSorcery.log.info("[AssetLibrary] Texture Preloading took " + (System.currentTimeMillis() - startMs) + "ms!");
        } else {
            AstralSorcery.log.info("[AssetLibrary] Skipping preloading textures (configured).");
        }
    }

    private static void preloadMandatoryTextures() {
        //AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "arrow_left")        .allocateGlId();
        //AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "arrow_right")       .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiJBlankBook")        .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiJSpaceBook")        .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiJBookmark")         .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiJBookmarkStretched").allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiJArrow")            .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "underline")           .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiResBG")             .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiJResOverlay")       .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "hud_charge_frame")     .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "hud_charge_charge")    .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "hud_item_frame")       .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.ENVIRONMENT, "star1")        .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.ENVIRONMENT, "star2")        .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.ENVIRONMENT, "connection")   .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.ENVIRONMENT, "solarEclipse") .allocateGlId();
    }

    private static void preloadTextures() {
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "cloud1")                  .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "cloud2")                  .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "cloud3")                  .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "cloud4")                  .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "cloud5")                  .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiConPaper")             .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiAltar1")               .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiAltar2")               .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiAltar3")               .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "gridDisc")                .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "gridAtt")                 .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "gridCst")                 .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guiJStorageBook")         .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guidrawing")              .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.GUI, "guidrawing_empty")        .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "lightbeam")            .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "burst1")               .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "burst2")               .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "ceffect1")             .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "ceffect2")             .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "ceffect3")             .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "connectionPerks")      .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "starlight_store")      .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "halo1")                .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "halo2")                .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "star1")                .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "star2")                .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "flareStar")            .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "flareStatic")          .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "flarePerkInactive")    .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "flarePerkActive")      .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "flarePerkActivateable").allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "unlock_perk")          .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "flare1")               .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.EFFECT, "charge")               .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "potion_cheatdeath")      .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "potion_bleed")           .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "moon_full")              .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "moon_waning1")           .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "moon_waning2")           .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "moon_waning3")           .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "moon_new")               .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "moon_waxing1")           .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "moon_waxing2")           .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "moon_waxing3")           .allocateGlId();
        AssetLibrary.loadTexture(AssetLoader.TextureLocation.MISC, "smoke")                  .allocateGlId();

        SpriteLibrary.init(); //Loads all spritesheets
    }

}

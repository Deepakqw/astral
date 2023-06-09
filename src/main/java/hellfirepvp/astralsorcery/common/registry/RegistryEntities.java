/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.entities.*;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryEntities
 * Created by HellFirePvP
 * Date: 08.05.2016 / 23:19
 */
public class RegistryEntities {

    public static void init() {
        registerEntities();
    }

    private static void registerEntities() {
        int modEid = 0;

        registerEntity(EntityItemHighlighted.class, "EntityHighlighted", modEid++, 64, 20, true);
        registerEntity(EntityItemStardust.class, "EntityStardust", modEid++, 64, 20, true);
        registerEntity(EntityCrystal.class, "EntityCrystal", modEid++, 64, 20, true);
        registerEntity(EntityFlare.class, "EntityFlare", modEid++, 64, 2, true);
        registerEntity(EntityStarburst.class, "EntityStarBurst", modEid++, 32, 1, true);
        registerEntity(EntityIlluminationSpark.class, "EntityIlluminationSpark", modEid++, 32, 1, true);
        registerEntity(EntityCrystalTool.class, "EntityCrystalTool", modEid++, 64, 20, true);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int id, int trackingRange, int updateFreq, boolean sendVelUpdates) {
        EntityRegistry.registerModEntity(entityClass, name, id, AstralSorcery.instance, trackingRange, updateFreq, sendVelUpdates);
    }

}

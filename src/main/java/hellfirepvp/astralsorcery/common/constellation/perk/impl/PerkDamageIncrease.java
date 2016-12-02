package hellfirepvp.astralsorcery.common.constellation.perk.impl;

import hellfirepvp.astralsorcery.common.constellation.perk.ConstellationPerk;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.Configuration;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: PerkDamageIncrease
 * Created by HellFirePvP
 * Date: 02.12.2016 / 19:53
 */
public class PerkDamageIncrease extends ConstellationPerk {

    private static float dmgMultiplier = 1.05F;

    public PerkDamageIncrease() {
        super("DMG_PERM", Target.ENTITY_ATTACK);
    }

    @Override
    public float onEntityAttack(EntityPlayer attacker, EntityLivingBase attacked, float dmgIn) {
        return dmgIn * dmgMultiplier;
    }

    @Override
    public boolean hasConfigEntries() {
        return true;
    }

    @Override
    public void loadFromConfig(Configuration cfg) {
        dmgMultiplier = cfg.getFloat(getKey() + "DamageIncrease", getConfigurationSection(), 1.05F, 1F, 2F, "Sets the damage multiplier that is applied to entity damage if the player has this perk.");
    }

}

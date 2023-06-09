/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client.render.tile;

import hellfirepvp.astralsorcery.common.tile.TileAttunementRelay;
import hellfirepvp.astralsorcery.common.tile.base.TileInventoryBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: TESRAttunementRelay
 * Created by HellFirePvP
 * Date: 27.03.2017 / 18:07
 */
public class TESRAttunementRelay extends TileEntitySpecialRenderer<TileAttunementRelay> {

    @Override
    public void renderTileEntityAt(TileAttunementRelay te, double x, double y, double z, float partialTicks, int destroyStage) {
        TileInventoryBase.ItemHandlerTile iht = te.getInventoryHandler();
        if (iht == null) return;
        ItemStack in = iht.getStackInSlot(0);
        if (in == null) return;
        EntityItem ei = new EntityItem(Minecraft.getMinecraft().world, 0, 0, 0, in);
        ei.age = te.getTicksExisted();
        ei.hoverStart = 0;
        Minecraft.getMinecraft().getRenderManager().doRenderEntity(ei, x + 0.5, y, z + 0.5, 0, partialTicks, true);
    }

}
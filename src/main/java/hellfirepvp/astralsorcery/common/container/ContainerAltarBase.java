/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.container;

import hellfirepvp.astralsorcery.common.item.ItemConstellationFocus;
import hellfirepvp.astralsorcery.common.tile.TileAltar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ContainerAltarBase
 * Created by HellFirePvP
 * Date: 16.10.2016 / 19:26
 */
public abstract class ContainerAltarBase extends Container {

    public final InventoryPlayer playerInv;
    public final TileAltar tileAltar;
    public final ItemStackHandler invHandler;
    private final int plSize;

    public ContainerAltarBase(InventoryPlayer playerInv, TileAltar tileAltar) {
        this.playerInv = playerInv;
        this.tileAltar = tileAltar;
        this.invHandler = tileAltar.getInventoryHandler();

        bindPlayerInventory();
        bindAltarInventory();

        this.plSize = playerInv.mainInventory.length;
    }

    abstract void bindPlayerInventory();

    abstract void bindAltarInventory();

    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (this instanceof ContainerAltarTrait && index >= 0 && index < 36 &&
                    itemstack1.getItem() instanceof ItemConstellationFocus &&
                    ((ItemConstellationFocus) itemstack1.getItem()).getFocusConstellation(itemstack1) != null) {
                if (this.mergeItemStack(itemstack1, ((ContainerAltarTrait) this).focusSlot.slotNumber, ((ContainerAltarTrait) this).focusSlot.slotNumber + 1, false)) {
                    return itemstack;
                }
            }
            if (index >= 0 && index < 27) {
                if (!this.mergeItemStack(itemstack1, 27, 36, false)) {
                    return null;
                }
            } else if (index >= 27 && index < 36) {
                if (!this.mergeItemStack(itemstack1, 0, 27, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}

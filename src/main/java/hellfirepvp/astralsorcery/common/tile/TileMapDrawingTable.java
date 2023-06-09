package hellfirepvp.astralsorcery.common.tile;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.client.effect.EffectHandler;
import hellfirepvp.astralsorcery.client.effect.EffectHelper;
import hellfirepvp.astralsorcery.client.effect.EntityComplexFX;
import hellfirepvp.astralsorcery.client.effect.fx.EntityFXFacingParticle;
import hellfirepvp.astralsorcery.common.constellation.DrawnConstellation;
import hellfirepvp.astralsorcery.common.constellation.distribution.ConstellationSkyHandler;
import hellfirepvp.astralsorcery.common.constellation.starmap.ActiveStarMap;
import hellfirepvp.astralsorcery.common.item.ItemCraftingComponent;
import hellfirepvp.astralsorcery.common.item.ItemInfusedGlass;
import hellfirepvp.astralsorcery.common.network.PacketChannel;
import hellfirepvp.astralsorcery.common.network.packet.server.PktParticleEvent;
import hellfirepvp.astralsorcery.common.tile.base.TileSkybound;
import hellfirepvp.astralsorcery.common.util.ItemUtils;
import hellfirepvp.astralsorcery.common.util.data.Vector3;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: TileMapDrawingTable
 * Created by HellFirePvP
 * Date: 14.03.2017 / 22:18
 */
public class TileMapDrawingTable extends TileSkybound {

    public static int RUN_TIME = 10 * 20;

    private ItemStack slotIn = null;
    private ItemStack slotGlassLens = null;

    private int runTick = 0;

    @Override
    protected void onFirstTick() {}

    @Override
    public void update() {
        super.update();

        if(world.isRemote) {
            playWorkEffects();
        } else {
            if(ConstellationSkyHandler.getInstance().isNight(getWorld()) && doesSeeSky() &&
                    slotGlassLens != null && slotGlassLens.getItem() != null && slotGlassLens.getItem() instanceof ItemInfusedGlass) {
                ActiveStarMap map = ItemInfusedGlass.getMapEngravingInformations(slotGlassLens);
                if(map != null && slotIn != null && slotIn.getItem() != null && !hasParchment() &&
                        ((slotIn.isItemEnchantable() && map.tryApplyEnchantments(ItemUtils.copyStackWithSize(slotIn, slotIn.stackSize)))
                                || (slotIn.getItem() instanceof ItemPotion && PotionUtils.getEffectsFromStack(slotIn).isEmpty()))) {
                    runTick++;
                    if(runTick > RUN_TIME) {
                        if(slotIn.isItemEnchantable()) {
                            if(slotIn.getItem() instanceof ItemBook && map.tryApplyEnchantments(ItemUtils.copyStackWithSize(slotIn, slotIn.stackSize))) {
                                slotIn = new ItemStack(Items.ENCHANTED_BOOK);
                            }
                            map.tryApplyEnchantments(slotIn);
                            if(slotGlassLens.attemptDamageItem(1, rand)) {
                                slotGlassLens.stackSize--;
                                if(slotGlassLens.stackSize <= 0) {
                                    slotGlassLens = null;
                                }
                                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, rand.nextFloat() * 0.5F + 1F, rand.nextFloat() * 0.2F + 0.8F);
                            }
                        } else if(PotionUtils.getEffectsFromStack(slotIn).isEmpty()) {
                            map.tryApplyPotionEffects(slotIn);

                            if(rand.nextInt(3) == 0 && slotGlassLens.attemptDamageItem(1, rand)) {
                                slotGlassLens.stackSize--;
                                if(slotGlassLens.stackSize <= 0) {
                                    slotGlassLens = null;
                                }
                                world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, rand.nextFloat() * 0.5F + 1F, rand.nextFloat() * 0.2F + 0.8F);
                            }
                        }
                        runTick = 0;
                    }
                    markForUpdate();
                } else {
                    if(runTick > 0) {
                        runTick = 0;
                        markForUpdate();
                    }
                }
            } else {
                if(runTick > 0) {
                    runTick = 0;
                    markForUpdate();
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void playWorkEffects() {
        if(getPercRunning() <= 1E-4) return;
        Vector3 offset = new Vector3(-6.0 / 16.0, 1.505, -6.0 / 16.0);
        if(rand.nextBoolean()) {
            offset.addX(26.0 / 16.0);
        }
        int random = rand.nextInt(6);
        offset.addZ(random * (5.0 / 16.0));
        if(random > 2) {
            offset.addZ(1.0 / 16.0D); //Gap in the middle..
        }
        offset.add(rand.nextFloat() * 0.1, 0, rand.nextFloat() * 0.1).add(pos);

        Color c;
        switch (random) {
            case 0:
                c = new Color(0xFF0800);
                break;
            case 1:
                c = new Color(0xFFCC00);
                break;
            case 2:
                c = new Color(0x6FFF00);
                break;
            case 3:
                c = new Color(0x00FCFF);
                break;
            case 4:
                c = new Color(0x0028FF);
                break;
            case 5:
            default:
                c = new Color(0xFF00FE);
                break;
        }

        EntityFXFacingParticle p = EffectHelper.genericFlareParticle(offset.getX(), offset.getY(), offset.getZ());
        p.scale(rand.nextFloat() * 0.1F + 0.15F).enableAlphaFade(EntityComplexFX.AlphaFunction.FADE_OUT);
        p.gravity(0.006F).setMaxAge(rand.nextInt(30) + 35);
        p.setColor(c);

        if(rand.nextFloat() < getPercRunning()) {
            Vector3 center = new Vector3(this).add(0.5, 1, 0.5);

            AstralSorcery.proxy.fireLightning(world, offset, center, c);
            p = EffectHelper.genericFlareParticle(offset.getX(), offset.getY(), offset.getZ());
            p.scale(rand.nextFloat() * 0.1F + 0.15F).enableAlphaFade(EntityComplexFX.AlphaFunction.FADE_OUT);
            p.gravity(0.004F).setMaxAge(rand.nextInt(30) + 35);
            p.setColor(c);
            Vector3 mov = center.clone().subtract(offset).normalize().multiply(0.05 * rand.nextFloat());
            p.motion(mov.getX(), mov.getY(), mov.getZ());
        }

        if(getPercRunning() > 0.1F) {
            if(rand.nextInt(3) == 0) {
                EffectHandler.getInstance().lightbeam(offset.clone().addY(0.4 + rand.nextFloat() * 0.3), offset, 0.2F).setColorOverlay(c);
            }
            if(rand.nextInt(4) == 0) {
                switch (rand.nextInt(3)) {
                    case 0:
                        c = new Color(0x0054C4);
                        break;
                    case 1:
                        c = new Color(0x7729CA);
                        break;
                    case 2:
                        c = new Color(0x0028FF);
                        break;
                }
                offset = new Vector3(this).add(rand.nextFloat(), 1, rand.nextFloat());
                EffectHandler.getInstance().lightbeam(offset.clone().addY(1 + rand.nextFloat() * 0.4), offset, 0.5F).setColorOverlay(c);
            }
        }

    }

    public int addParchment(int amt) {
        if(slotIn != null && slotIn.getItem() != null) {
            if(slotIn.getItem() instanceof ItemCraftingComponent &&
                    slotIn.getItemDamage() == ItemCraftingComponent.MetaType.PARCHMENT.getMeta()) {
                int current = slotIn.stackSize;
                if(current + amt <= 64) {
                    current += amt;
                    slotIn.stackSize = current;
                    markForUpdate();
                    return 0;
                } else {
                    int ret = (current + amt) - 64;
                    slotIn.stackSize = 64;
                    markForUpdate();
                    return ret;
                }
            }
            return amt;
        } else {
            slotIn = ItemCraftingComponent.MetaType.PARCHMENT.asStack();
            slotIn.stackSize = amt;
            markForUpdate();
            return 0;
        }
    }

    @Nullable
    public ItemStack getSlotGlassLens() {
        return slotGlassLens;
    }

    @Nullable
    public ItemStack getSlotIn() {
        return slotIn;
    }

    public float getPercRunning() {
        return ((float) runTick) / ((float) RUN_TIME);
    }

    public void putSlotIn(ItemStack stack) {
        this.slotIn = ItemUtils.copyStackWithSize(stack, 1);
        markForUpdate();
    }

    public void putGlassLens(ItemStack glassLens) {
        if(glassLens != null) {
            this.slotGlassLens = ItemUtils.copyStackWithSize(glassLens, Math.min(glassLens.stackSize, 1));
        } else {
            this.slotGlassLens = null;
        }
        markForUpdate();
    }

    public boolean hasParchment() {
        return slotIn != null && slotIn.getItem() != null && slotIn.getItem() instanceof ItemCraftingComponent &&
                slotIn.getItemDamage() == ItemCraftingComponent.MetaType.PARCHMENT.getMeta() &&
                slotIn.stackSize > 0;
    }

    public boolean hasUnengravedGlass() {
        return slotGlassLens != null && slotGlassLens.getItem() != null && slotGlassLens.getItem() instanceof ItemInfusedGlass &&
                ItemInfusedGlass.getMapEngravingInformations(slotGlassLens) == null;
    }

    public void dropContents() {
        if(slotIn != null && slotIn.getItem() != null) {
            ItemUtils.dropItemNaturally(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, slotIn);
            slotIn = null;
        }
        if(slotGlassLens != null && slotGlassLens.getItem() != null) {
            ItemUtils.dropItemNaturally(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, slotGlassLens);
            slotGlassLens = null;
        }
        markForUpdate();
    }

    @Override
    public void readCustomNBT(NBTTagCompound compound) {
        super.readCustomNBT(compound);
        this.runTick = compound.getInteger("runTick");

        if(compound.hasKey("slotIn")) {
            this.slotIn = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("slotIn"));
        } else {
            this.slotIn = null;
        }
        if(compound.hasKey("slotGlassLens")) {
            this.slotGlassLens = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("slotGlassLens"));
        } else {
            this.slotGlassLens = null;
        }
    }

    @Override
    public void writeNetNBT(NBTTagCompound compound) {
        super.writeNetNBT(compound);
        compound.setInteger("runTick", this.runTick);

        if(this.slotIn != null) {
            NBTTagCompound tag = new NBTTagCompound();
            this.slotIn.writeToNBT(tag);
            compound.setTag("slotIn", tag);
        }

        if(this.slotGlassLens != null) {
            NBTTagCompound tag = new NBTTagCompound();
            this.slotGlassLens.writeToNBT(tag);
            compound.setTag("slotGlassLens", tag);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox().expand(0.5, 0.5, 0.5);
    }

    public void tryEngraveGlass(java.util.List<DrawnConstellation> constellations) {
        if(hasParchment() && hasUnengravedGlass()) {
            slotIn.stackSize--;
            ItemInfusedGlass.setMapEngravingInformations(getSlotGlassLens(), ActiveStarMap.compile(constellations));
            markForUpdate();
            PktParticleEvent ev = new PktParticleEvent(PktParticleEvent.ParticleEventType.ENGRAVE_LENS, new Vector3(this));
            PacketChannel.CHANNEL.sendToAllAround(ev, PacketChannel.pointFromPos(world, getPos(), 16));
        }
    }

    public boolean burnParchment() {
        if(hasParchment() && hasUnengravedGlass()) {
            slotIn.stackSize--;
            markForUpdate();
            PktParticleEvent ev = new PktParticleEvent(PktParticleEvent.ParticleEventType.BURN_PARCHMENT, new Vector3(this));
            PacketChannel.CHANNEL.sendToAllAround(ev, PacketChannel.pointFromPos(world, getPos(), 16));
            return true;
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public static void burnParchmentEffects(PktParticleEvent pktParticleEvent) {
        if(Minecraft.getMinecraft().world == null) return;

        Vector3 offset = pktParticleEvent.getVec();
        Minecraft.getMinecraft().world.playSound(offset.getX(), offset.getY(), offset.getZ(),
                SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS,
                rand.nextFloat() * 0.5F + 1F, rand.nextFloat() * 0.1F + 0.9F, true);

        offset.add(-0.2, 1.1, -0.2);
        for (int i = 0; i < 50; i++) {
            Vector3 at = offset.clone().add(rand.nextFloat() * 1.4, 0, rand.nextFloat() * 1.4);
            Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.FLAME,
                    at.getX(), at.getY(), at.getZ(),
                    rand.nextFloat() * 0.2 * (rand.nextBoolean() ? 1 : -1),
                    rand.nextFloat() * 0.05 * (rand.nextBoolean() ? 1 : -1),
                    rand.nextFloat() * 0.2 * (rand.nextBoolean() ? 1 : -1));
        }

        for (int i = 0; i < 70; i++) {
            Vector3 at = offset.clone().add(rand.nextFloat() * 1.4, 0, rand.nextFloat() * 1.4);
            EntityFXFacingParticle p = EffectHelper.genericFlareParticle(at.getX(), at.getY(), at.getZ());
            p.gravity(0.004).scale(rand.nextFloat() * 0.1F + 0.2F).setMaxAge(rand.nextInt(20) + 20);
            p.motion(rand.nextFloat() * 0.15 * (rand.nextBoolean() ? 1 : -1),
                    rand.nextFloat() * 0.05 * (rand.nextBoolean() ? 1 : -1),
                    rand.nextFloat() * 0.15 * (rand.nextBoolean() ? 1 : -1));
            p.setColor(new Color(Color.HSBtoRGB(rand.nextFloat() * 360, 1F, 1F)));
        }

    }

    @SideOnly(Side.CLIENT)
    public static void engraveLensEffects(PktParticleEvent pktParticleEvent) {

    }
}

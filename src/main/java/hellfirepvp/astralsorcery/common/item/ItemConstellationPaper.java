/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.item;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.CommonProxy;
import hellfirepvp.astralsorcery.common.constellation.ConstellationRegistry;
import hellfirepvp.astralsorcery.common.constellation.IConstellation;
import hellfirepvp.astralsorcery.common.constellation.IMinorConstellation;
import hellfirepvp.astralsorcery.common.data.research.PlayerProgress;
import hellfirepvp.astralsorcery.common.data.research.ResearchManager;
import hellfirepvp.astralsorcery.common.entities.EntityItemHighlighted;
import hellfirepvp.astralsorcery.common.item.base.ItemHighlighted;
import hellfirepvp.astralsorcery.common.lib.Sounds;
import hellfirepvp.astralsorcery.common.registry.RegistryItems;
import hellfirepvp.astralsorcery.common.util.SoundHelper;
import hellfirepvp.astralsorcery.common.util.WRItemObject;
import hellfirepvp.astralsorcery.common.util.nbt.NBTHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ItemConstellationPaper
 * Created by HellFirePvP
 * Date: 07.05.2016 / 15:16
 */
public class ItemConstellationPaper extends Item implements ItemHighlighted {

    public ItemConstellationPaper() {
        setMaxDamage(0);
        setMaxStackSize(1);
        setHasSubtypes(true);
        setCreativeTab(RegistryItems.creativeTabAstralSorceryPapers);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        subItems.add(new ItemStack(this, 1));

        for (IConstellation c : ConstellationRegistry.getAllConstellations()) {
            if (c instanceof IMinorConstellation) continue;

            ItemStack cPaper = new ItemStack(this, 1);
            setConstellation(cPaper, c);
            subItems.add(cPaper);
        }
    }

    @Override
    public boolean hasCustomEntity(ItemStack stack) {
        return true;
    }

    @Override
    public Entity createEntity(World world, Entity entity, ItemStack itemstack) {
        EntityItemHighlighted ei = new EntityItemHighlighted(world, entity.posX, entity.posY, entity.posZ, itemstack);
        ei.setDefaultPickupDelay();
        ei.motionX = entity.motionX;
        ei.motionY = entity.motionY;
        ei.motionZ = entity.motionZ;
        return ei;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        IConstellation c = getConstellation(stack);
        if (c != null) {
            tooltip.add(TextFormatting.BLUE + I18n.format(c.getUnlocalizedName()));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("constellation.noInformation"));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if(worldIn.isRemote && getConstellation(itemStackIn) != null) {
            SoundHelper.playSoundClient(Sounds.bookFlip, 1F, 1F);
            AstralSorcery.proxy.openGui(CommonProxy.EnumGuiId.CONSTELLATION_PAPER, playerIn, worldIn, ConstellationRegistry.getConstellationId(getConstellation(itemStackIn)), 0, 0);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (worldIn.isRemote || entityIn == null || !(entityIn instanceof EntityPlayer)) return;

        IConstellation cst = getConstellation(stack);

        if(cst != null) {
            PlayerProgress progress = ResearchManager.getProgress((EntityPlayer) entityIn);
            if(progress != null) {
                boolean has = false;
                for (String strConstellation : progress.getSeenConstellations()) {
                    IConstellation c = ConstellationRegistry.getConstellationByName(strConstellation);
                    if(c != null && c.equals(cst)) {
                        has = true;
                        break;
                    }
                }
                if(!has) {
                    if(ResearchManager.memorizeConstellation(cst, (EntityPlayer) entityIn)) {
                        entityIn.addChatMessage(
                                new TextComponentTranslation("progress.seen.constellation.chat",
                                        new TextComponentTranslation(cst.getUnlocalizedName())
                                                .setStyle(new Style().setColor(TextFormatting.GRAY)))
                                        .setStyle(new Style().setColor(TextFormatting.BLUE)));
                        if(ResearchManager.clientProgress.getSeenConstellations().size() == 1) {
                            entityIn.addChatMessage(
                                    new TextComponentTranslation("progress.seen.constellation.first.chat")
                                            .setStyle(new Style().setColor(TextFormatting.BLUE)));
                        }
                    }
                }
            }
            return;
        }

        if ((worldIn.getTotalWorldTime() & 7)  == 0) {
            PlayerProgress progress = ResearchManager.getProgress((EntityPlayer) entityIn);
            if (progress != null) {
                List<IConstellation> constellations = new ArrayList<>();
                for (IConstellation c : ConstellationRegistry.getAllConstellations()) {
                    if(c.canDiscover(progress)) {
                        constellations.add(c);
                    }
                }

                for (String strConstellation : progress.getKnownConstellations()) {
                    IConstellation c = ConstellationRegistry.getConstellationByName(strConstellation);
                    if(c != null) {
                        constellations.remove(c);
                    }
                }
                for (String strConstellation : progress.getSeenConstellations()) {
                    IConstellation c = ConstellationRegistry.getConstellationByName(strConstellation);
                    if(c != null) {
                        constellations.remove(c);
                    }
                }

                if (constellations.isEmpty()) {
                    return;
                }

                List<WRItemObject<IConstellation>> wrp = buildWeightedRandomList(constellations);
                WRItemObject<IConstellation> result = WeightedRandom.getRandomItem(worldIn.rand, wrp);
                setConstellation(stack, result.getValue());
            }
        }
    }

    private List<WRItemObject<IConstellation>> buildWeightedRandomList(List<IConstellation> constellations) {
        List<WRItemObject<IConstellation>> wrc = new ArrayList<>();
        for (IConstellation c : constellations) {
            WRItemObject<IConstellation> i = new WRItemObject<>(1, c);//(int) (tier.getShowupChance() * 100), c);
            wrc.add(i);
        }
        return wrc;
    }

    @Override
    public Color getHightlightColor(ItemStack stack) {
        IConstellation c = getConstellation(stack);
        return c == null ? Color.GRAY : c.getRenderColor();
    }

    public static IConstellation getConstellation(ItemStack stack) {
        Item i = stack.getItem();
        if (!(i instanceof ItemConstellationPaper)) return null;
        return IConstellation.readFromNBT(NBTHelper.getPersistentData(stack));
    }

    public static void setConstellation(ItemStack stack, IConstellation constellation) {
        Item i = stack.getItem();
        if (!(i instanceof ItemConstellationPaper)) return;
        NBTTagCompound tag = NBTHelper.getPersistentData(stack);
        constellation.writeToNBT(tag);
    }

}

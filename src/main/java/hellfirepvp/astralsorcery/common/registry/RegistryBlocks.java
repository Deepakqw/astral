/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.registry;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.block.*;
import hellfirepvp.astralsorcery.common.block.fluid.FluidBlockLiquidStarlight;
import hellfirepvp.astralsorcery.common.block.fluid.FluidLiquidStarlight;
import hellfirepvp.astralsorcery.common.block.network.BlockAltar;
import hellfirepvp.astralsorcery.common.block.network.BlockAttunementAltar;
import hellfirepvp.astralsorcery.common.block.network.BlockCelestialCollectorCrystal;
import hellfirepvp.astralsorcery.common.block.network.BlockCollectorCrystal;
import hellfirepvp.astralsorcery.common.block.network.BlockLens;
import hellfirepvp.astralsorcery.common.block.network.BlockPrism;
import hellfirepvp.astralsorcery.common.block.network.BlockRitualPedestal;
import hellfirepvp.astralsorcery.common.block.network.BlockWell;
import hellfirepvp.astralsorcery.common.lib.BlocksAS;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import hellfirepvp.astralsorcery.common.tile.*;
import hellfirepvp.astralsorcery.common.tile.network.TileCollectorCrystal;
import hellfirepvp.astralsorcery.common.tile.network.TileCrystalLens;
import hellfirepvp.astralsorcery.common.tile.network.TileCrystalPrismLens;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.LinkedList;
import java.util.List;

import static hellfirepvp.astralsorcery.common.lib.BlocksAS.*;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: RegistryBlocks
 * Created by HellFirePvP
 * Date: 07.05.2016 / 18:16
 */
public class RegistryBlocks {

    public static List<Block> defaultItemBlocksToRegister = new LinkedList<>();
    public static List<Block> customNameItemBlocksToRegister = new LinkedList<>();
    public static List<BlockDynamicColor> pendingIBlockColorBlocks = new LinkedList<>();

    public static void init() {
        registerFluids();

        registerBlocks();

        registerTileEntities();
    }

    private static void registerFluids() {
        FluidLiquidStarlight f = new FluidLiquidStarlight();
        FluidRegistry.registerFluid(f);
        fluidLiquidStarlight = FluidRegistry.getFluid(f.getName());
        blockLiquidStarlight = new FluidBlockLiquidStarlight();
        GameRegistry.register(blockLiquidStarlight.setUnlocalizedName(blockLiquidStarlight.getClass().getSimpleName()).setRegistryName(blockLiquidStarlight.getClass().getSimpleName()));
        fluidLiquidStarlight.setBlock(blockLiquidStarlight);

        FluidRegistry.addBucketForFluid(BlocksAS.fluidLiquidStarlight);
        ItemsAS.itemBucketLiquidStarlight = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluidLiquidStarlight);
    }

    //Blocks
    private static void registerBlocks() {
        //WorldGen&Related
        customOre = registerBlock(new BlockCustomOre());
        queueCustomNameItemBlock(customOre);
        customSandOre = registerBlock(new BlockCustomSandOre());
        queueCustomNameItemBlock(customSandOre);
        customFlower = registerBlock(new BlockCustomFlower());
        queueCustomNameItemBlock(customFlower);
        blockMarble = registerBlock(new BlockMarble());
        queueCustomNameItemBlock(blockMarble);
        blockMarbleStairs = registerBlock(new BlockMarbleStairs());
        queueDefaultItemBlock(blockMarbleStairs);
        blockBlackMarble = registerBlock(new BlockBlackMarble());
        queueCustomNameItemBlock(blockBlackMarble);
        blockVolatileLight = registerBlock(new BlockFlareLight());
        queueDefaultItemBlock(blockVolatileLight);

        //Mechanics
        blockAltar = registerBlock(new BlockAltar());
        attunementAltar = registerBlock(new BlockAttunementAltar());
        queueDefaultItemBlock(attunementAltar);
        attunementRelay = registerBlock(new BlockAttunementRelay());
        queueDefaultItemBlock(attunementRelay);
        ritualPedestal = registerBlock(new BlockRitualPedestal());
        blockWell = registerBlock(new BlockWell());
        queueDefaultItemBlock(blockWell);
        blockIlluminator = registerBlock(new BlockWorldIlluminator());
        queueDefaultItemBlock(blockIlluminator);
        blockMachine = registerBlock(new BlockMachine());
        queueCustomNameItemBlock(blockMachine);
        blockFakeTree = registerBlock(new BlockFakeTree());
        queueDefaultItemBlock(blockFakeTree);
        starlightInfuser = registerBlock(new BlockStarlightInfuser());
        queueDefaultItemBlock(starlightInfuser);
        ritualLink = registerBlock(new BlockRitualLink());
        queueDefaultItemBlock(ritualLink);

        treeBeacon = registerBlock(new BlockTreeBeacon());
        queueDefaultItemBlock(treeBeacon);
        translucentBlock = registerBlock(new BlockTranslucentBlock());
        queueDefaultItemBlock(translucentBlock);
        drawingTable = registerBlock(new BlockMapDrawingTable());
        queueCustomNameItemBlock(drawingTable);
        //celestialOrrery = registerBlock(new BlockCelestialOrrery());
        //queueDefaultItemBlock(celestialOrrery);
        celestialGateway = registerBlock(new BlockCelestialGateway());
        queueDefaultItemBlock(celestialGateway);

        lens = registerBlock(new BlockLens());
        lensPrism = registerBlock(new BlockPrism());
        queueDefaultItemBlock(lens);
        queueDefaultItemBlock(lensPrism);

        celestialCrystals = registerBlock(new BlockCelestialCrystals());
        queueCustomNameItemBlock(celestialCrystals);

        //Machines&Related
        //stoneMachine = registerBlock(new BlockStoneMachine());
        collectorCrystal = registerBlock(new BlockCollectorCrystal());
        celestialCollectorCrystal = registerBlock(new BlockCelestialCollectorCrystal());

        blockStructural = registerBlock(new BlockStructural());
        queueCustomNameItemBlock(blockStructural);
    }

    //Called after items are registered.
    //Necessary for blocks that require different models/renders for different metadata values
    public static void initRenderRegistry() {
        registerBlockRender(blockMarble);
        registerBlockRender(blockBlackMarble);
        registerBlockRender(blockAltar);
        registerBlockRender(customOre);
        registerBlockRender(customSandOre);
        registerBlockRender(customFlower);
        registerBlockRender(blockStructural);
        registerBlockRender(blockMachine);
    }

    //Tiles
    private static void registerTileEntities() {
        registerTile(TileAltar.class);
        registerTile(TileRitualPedestal.class);
        registerTile(TileCollectorCrystal.class);
        registerTile(TileCelestialCrystals.class);
        registerTile(TileWell.class);
        registerTile(TileIlluminator.class);
        registerTile(TileTelescope.class);
        registerTile(TileGrindstone.class);
        registerTile(TileStructuralConnector.class);
        registerTile(TileFakeTree.class);
        registerTile(TileAttunementAltar.class);
        registerTile(TileStarlightInfuser.class);
        registerTile(TileTreeBeacon.class);
        registerTile(TileRitualLink.class);
        registerTile(TileTranslucent.class);
        registerTile(TileAttunementRelay.class);
        registerTile(TileMapDrawingTable.class);
        //registerTile(TileCelestialOrrery.class);
        registerTile(TileCelestialGateway.class);

        registerTile(TileCrystalLens.class);
        registerTile(TileCrystalPrismLens.class);
    }

    private static void queueCustomNameItemBlock(Block block) {
        customNameItemBlocksToRegister.add(block);
    }

    private static void queueDefaultItemBlock(Block block) {
        defaultItemBlocksToRegister.add(block);
    }

    private static <T extends Block> T registerBlock(T block, String name) {
        GameRegistry.register(block.setUnlocalizedName(name).setRegistryName(name));
        if(block instanceof BlockDynamicColor) {
            pendingIBlockColorBlocks.add((BlockDynamicColor) block);
        }
        return block;
    }

    private static <T extends Block> T registerBlock(T block) {
        return registerBlock(block, block.getClass().getSimpleName());
    }

    private static void registerBlockRender(Block block) {
        if(block instanceof BlockVariants) {
            for (IBlockState state : ((BlockVariants) block).getValidStates()) {
                String unlocName = ((BlockVariants) block).getBlockName(state);
                String name = unlocName + "_" + ((BlockVariants) block).getStateName(state);
                AstralSorcery.proxy.registerVariantName(Item.getItemFromBlock(block), name);
                AstralSorcery.proxy.registerBlockRender(block, block.getMetaFromState(state), name);
            }
        } else {
            AstralSorcery.proxy.registerVariantName(Item.getItemFromBlock(block), block.getUnlocalizedName());
            AstralSorcery.proxy.registerBlockRender(block, 0, block.getUnlocalizedName());
        }
    }

    private static void registerTile(Class<? extends TileEntity> tile, String name) {
        GameRegistry.registerTileEntity(tile, name);
    }

    private static void registerTile(Class<? extends TileEntity> tile) {
        registerTile(tile, tile.getSimpleName());
    }

    public static class FluidCustomModelMapper extends StateMapperBase implements ItemMeshDefinition {

        private final ModelResourceLocation res;

        public FluidCustomModelMapper(Fluid f) {
            this.res = new ModelResourceLocation(AstralSorcery.MODID.toLowerCase() + ":BlockFluids", f.getName());
        }

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {
            return res;
        }

        @Override
        public ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return res;
        }

    }

}

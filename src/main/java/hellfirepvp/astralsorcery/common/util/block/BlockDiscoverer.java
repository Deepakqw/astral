/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2019
 *
 * All rights reserved.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.common.util.block;

import hellfirepvp.astralsorcery.common.util.MiscUtils;
import hellfirepvp.observerlib.api.util.BlockArray;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: BlockDiscoverer
 * Created by HellFirePvP
 * Date: 25.08.2019 / 21:15
 */
public class BlockDiscoverer {

    public static BlockArray discoverBlocksWithSameStateAroundChain(World world, BlockPos origin, BlockState match, int length, @Nullable Direction originalBreakDirection, BlockPredicate addCheck) {
        BlockArray out = new BlockArray();

        BlockPos offset = new BlockPos(origin);
        lbl: while (length > 0) {
            List<Direction> faces = new ArrayList<>();
            Collections.addAll(faces, Direction.values());
            if (originalBreakDirection != null && out.getContents().isEmpty()) {
                faces.remove(originalBreakDirection);
                faces.remove(originalBreakDirection.getOpposite());
            }
            Collections.shuffle(faces);
            for (Direction face : faces) {
                BlockPos at = offset.offset(face);
                if (out.getContents().containsKey(at)) {
                    continue;
                }
                BlockState test = world.getBlockState(at);
                if (BlockUtils.matchStateExact(match, test) && addCheck.test(world, at, test)) {
                    out.addBlock(test, at.getX(), at.getY(), at.getZ());
                    length--;
                    offset = at;
                    continue lbl;
                }
            }
            break;
        }

        return out;
    }

    public static BlockArray searchForBlocksAround(World world, BlockPos origin, int cubeSize, BlockPredicate match) {
        BlockArray out = new BlockArray();

        try (BlockPos.PooledMutableBlockPos offset = BlockPos.PooledMutableBlockPos.retain()) {
            for (int xx = -cubeSize; xx <= cubeSize; xx++) {
                for (int zz = -cubeSize; zz <= cubeSize; zz++) {
                    for (int yy = -cubeSize; yy <= cubeSize; yy++) {
                        offset.setPos(origin.getX() + xx, origin.getY() + yy, origin.getZ() + zz);
                        if (MiscUtils.isChunkLoaded(world, offset)) {
                            BlockState atState = world.getBlockState(offset);
                            if(match.test(world, offset, atState)) {
                                out.addBlock(atState, offset.getX(), offset.getY(), offset.getZ());
                            }
                        }
                    }
                }
            }
        }
        return out;
    }
}
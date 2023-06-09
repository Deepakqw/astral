package hellfirepvp.astralsorcery.common.network.packet.client;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.tile.TileCelestialGateway;
import hellfirepvp.astralsorcery.common.util.ByteBufUtils;
import hellfirepvp.astralsorcery.common.util.MiscUtils;
import hellfirepvp.astralsorcery.common.util.data.Vector3;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: PktRequestTeleport
 * Created by HellFirePvP
 * Date: 22.04.2017 / 15:43
 */
public class PktRequestTeleport implements IMessage, IMessageHandler<PktRequestTeleport, IMessage> {

    private int dimId;
    private BlockPos pos;

    public PktRequestTeleport() {}

    public PktRequestTeleport(int dimId, BlockPos pos) {
        this.dimId = dimId;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.dimId = buf.readInt();
        this.pos = ByteBufUtils.readPos(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.dimId);
        ByteBufUtils.writePos(buf, this.pos);
    }

    @Override
    public IMessage onMessage(PktRequestTeleport message, MessageContext ctx) {
        EntityPlayer request = ctx.getServerHandler().playerEntity;
        TileCelestialGateway gate = MiscUtils.getTileAt(request.world, new Vector3(request, true).toBlockPos(), TileCelestialGateway.class, false);
        if(gate != null && gate.hasMultiblock() && gate.doesSeeSky()) {
            AstralSorcery.proxy.scheduleDelayed(() -> MiscUtils.transferEntityTo(request, message.dimId, message.pos));
        }
        return null;
    }
}

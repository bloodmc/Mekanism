package mekanism.common.network;

import io.netty.buffer.ByteBuf;

import java.util.Random;

import mekanism.api.Coord4D;
import mekanism.common.PacketHandler;
import mekanism.common.network.PacketPortalFX.PortalFXMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketPortalFX implements IMessageHandler<PortalFXMessage, IMessage>
{
	@Override
	public IMessage onMessage(PortalFXMessage message, MessageContext context) 
	{
		EntityPlayer player = PacketHandler.getPlayer(context);
		
		PacketHandler.handlePacket(new Thread() {
			@Override
			public void run()
			{
				Random random = new Random();
		
				for(int i = 0; i < 50; i++)
				{
					player.worldObj.spawnParticle(EnumParticleTypes.PORTAL, message.coord4D.xCoord + random.nextFloat(), message.coord4D.yCoord + random.nextFloat(), message.coord4D.zCoord + random.nextFloat(), 0.0F, 0.0F, 0.0F);
					player.worldObj.spawnParticle(EnumParticleTypes.PORTAL, message.coord4D.xCoord + random.nextFloat(), message.coord4D.yCoord + 1 + random.nextFloat(), message.coord4D.zCoord + random.nextFloat(), 0.0F, 0.0F, 0.0F);
				}
			}
		}, player);
		
		return null;
	}
	
	public static class PortalFXMessage implements IMessage
	{
		public Coord4D coord4D;
	
		public PortalFXMessage() {}
		
		public PortalFXMessage(Coord4D coord)
		{
			coord4D = coord;
		}
	
		@Override
		public void toBytes(ByteBuf dataStream)
		{
			coord4D.write(dataStream);
		}
	
		@Override
		public void fromBytes(ByteBuf dataStream)
		{
			coord4D = Coord4D.read(dataStream);
		}
	}
}

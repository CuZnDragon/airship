package net.wyrms.airship.client;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiScreen;
import net.wyrms.airship.EntityAirShip;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class AirShipTickHandler implements ITickHandler
	{
		@Override
		public void tickStart(EnumSet<TickType> type, Object... tickData)
			{
			}

		@Override
		public void tickEnd(EnumSet<TickType> type, Object... tickData)
			{
				if (type.equals(EnumSet.of(TickType.RENDER)))
					{
						onRenderTick();
					}
				else if (type.equals(EnumSet.of(TickType.CLIENT)))
					{
						GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
						if (guiscreen != null)
							{
								onTickInGUI(guiscreen);
							}
						else
							{
								onTickInGame(Minecraft.getMinecraft());
							}
					}
			}

		@Override
		public EnumSet<TickType> ticks()
			{
				return EnumSet.of(TickType.RENDER, TickType.CLIENT);
				// In my testing only RENDER, CLIENT, & PLAYER did anything on the
				// client side.
				// Read 'cpw.mods.fml.common.TickType.java' for a full list and
				// description of available types
			}

		@Override
		public String getLabel()
			{
				return null;
			}

		public void onRenderTick()
			{
				// System.out.println("onRenderTick");
				// TODO: Your Code Here
			}

		public void onTickInGUI(GuiScreen guiscreen)
			{
				// System.out.println("onTickInGUI");
				// TODO: Your Code Here
				Minecraft game = Minecraft.getMinecraft();
				
				if (guiscreen instanceof GuiInventory && game.thePlayer.ridingEntity instanceof EntityAirShip && game.thePlayer.ridingEntity != null)
					{
						game.displayGuiScreen(new GuiAirShip(game.thePlayer.inventory, (EntityAirShip) game.thePlayer.ridingEntity));
					}
			}

		public void onTickInGame(Minecraft game)
			{
				// System.out.println("onTickInGame");
				// TODO: Your Code Here
			}
	}

package net.wyrms.airship.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import net.minecraft.src.World;

import net.minecraftforge.client.MinecraftForgeClient;

import net.wyrms.airship.CommonProxy;
import net.wyrms.airship.AirShip;
import net.wyrms.airship.EntityAirShip;
import net.wyrms.airship.RenderAirShip;
import net.wyrms.airship.ModelAirShip;
import net.wyrms.airship.ModelBalloon;

public class ClientProxy extends CommonProxy
	{

		/* INSTANCES */
		public Object getClient()
			{
				return FMLClientHandler.instance().getClient();
			}

		public World getClientWorld()
			{
				return FMLClientHandler.instance().getClient().theWorld;
			}

		@Override
		public void registerRenderer()
			{
				MinecraftForgeClient.preloadTexture(AIRSHIP_ITEMS_PNG);
				RenderingRegistry.registerEntityRenderingHandler(EntityAirShip.class,
				    new RenderAirShip(new ModelAirShip(), new ModelBalloon(), 3.0f));
			}
	}

// ModLoader.registerEntityID(EntityAirship.class, "Airship", ModLoader
// .getUniqueEntityId());

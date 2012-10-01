package net.wyrms.airship;

//import java.util.logging.Level;

//import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;

import net.minecraftforge.common.Configuration;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;

import net.wyrms.airship.PropertyConfig;
import net.wyrms.airship.ItemAirShip;
import net.wyrms.airship.ItemBalloon;
import net.wyrms.airship.AirShipRecipes;
import net.wyrms.airship.ItemSteamBoat;

@Mod(name = "Airship", modid = "Airship", version = "1.7.4-alpha")
@NetworkMod(channels = { "Airship" }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class, versionBounds = "[4.1.1]")
public class AirShip
	{
		public static boolean ENABLE_AIRSHIP;
		public static boolean ENABLE_STEAMBOAT;
		// public static boolean ENABLE_PIRATE;

		public static int KEY_UP;
		public static int KEY_DOWN;
		public static int KEY_CHEST;
		public static int KEY_FIRE;
		public static boolean SHOW_BOILER;

		public static int airShipId;
		public static int engineId;
		public static int balloonId;
		public static int steamBoatId;

		public static Item airShip;
		public static Item engine;
		public static Item balloon;
		public static Item steamBoat;

		// public static int airShipRenderId;
		// public static int steamBoatRenderId;

//		public static Item balloonRaw;
//		public static Item balloonWhite;
//		public static Item balloonOrange;
//		public static Item balloonMagenta;
//		public static Item balloonLtBlue;
//		public static Item balloonYellow;
//		public static Item balloonLtGreen;
//		public static Item balloonPink;
//		public static Item balloonGray;
//		public static Item balloonLtGray;
//		public static Item balloonCyan;
//		public static Item balloonPurple;
//		public static Item balloonBlue;
//		public static Item balloonGreen;
//		public static Item balloonRed;
//		public static Item balloonBlack;

		@Instance
		public static AirShip instance;

		@SidedProxy(clientSide = "net.wyrms.airship.client.ClientProxy", serverSide = "net.wyrms.airship.CommonProxy")
		public static CommonProxy proxy;

		@PreInit
		public void preInit(FMLPreInitializationEvent event)
			{
				PropertyConfig.initProperties(event);
			}

		@Init
		public void load(FMLInitializationEvent event)
			{
				proxy.registerRenderer();

				if (ENABLE_AIRSHIP)
					{
						balloon = new ItemBalloon(balloonId);
						airShip = new ItemAirShip(airShipId);
						AirShipRecipes.addAirShipCraftingRecipes();
						AirShipRecipes.addAirShipNames();
						EntityRegistry.registerGlobalEntityID(EntityAirShip.class, "AirShip", ModLoader.getUniqueEntityId());
					}

				if (ENABLE_STEAMBOAT)
					{
						steamBoat = new ItemSteamBoat(steamBoatId);
						AirShipRecipes.addSteamBoatCraftingRecipes();
						AirShipRecipes.addSteamBoatNames();
					}

				// if (ENABLE_PIRATE) {
				//
				// }
			}

		@PostInit
		public void postInit(FMLPostInitializationEvent evt)
			{
				// Stub Method
			}

	}

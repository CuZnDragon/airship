package net.wyrms.airship;

//import java.util.logging.Level;

//import org.lwjgl.input.Keyboard;

import java.util.Map;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.TickRegistry;

import net.minecraftforge.common.Configuration;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ModLoader;

import net.wyrms.airship.PropertyConfig;
import net.wyrms.airship.ItemAirShip;
import net.wyrms.airship.ItemBalloon;
import net.wyrms.airship.AirShipRecipes;
import net.wyrms.airship.ItemSteamBoat;
import net.wyrms.airship.client.AirShipTickHandler;
import net.wyrms.airship.client.RenderSteamBoat;

@Mod(name = "Airship", modid = "Airship", version = "4.1.1")
@NetworkMod(channels =
	{ "Airship" }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class, versionBounds = "[4.1.1]")
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
		public static Item engine1;
		public static Item engine2;
		public static Item balloon;
		public static Item steamBoat;

		// public static Item balloonRaw;
		// public static Item balloonWhite;
		// public static Item balloonOrange;
		// public static Item balloonMagenta;
		// public static Item balloonLtBlue;
		// public static Item balloonYellow;
		// public static Item balloonLtGreen;
		// public static Item balloonPink;
		// public static Item balloonGray;
		// public static Item balloonLtGray;
		// public static Item balloonCyan;
		// public static Item balloonPurple;
		// public static Item balloonBlue;
		// public static Item balloonGreen;
		// public static Item balloonRed;
		// public static Item balloonBlack;

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
						engine = new ItemEngine(engineId);
						AirShipRecipes.addAirShipCraftingRecipes();
						AirShipRecipes.addAirShipNames();
						EntityRegistry.registerModEntity(EntityAirShip.class, "AirShip",
						    120, this, 250, 5, true);
						// EntityRegistry.registerModEntity(EntityAirShip.class, "AirShip",
						// ModLoader.getUniqueEntityId(), this, 250, 5, true);
						// EntityRegistry.registerGlobalEntityID(EntityAirShip.class,
						// "AirShip", ModLoader.getUniqueEntityId());
						TickRegistry.registerTickHandler(new AirShipTickHandler(),
						    Side.CLIENT);
					}

				if (ENABLE_STEAMBOAT)
					{
						steamBoat = new ItemSteamBoat(steamBoatId);
						AirShipRecipes.addSteamBoatCraftingRecipes();
						AirShipRecipes.addSteamBoatNames();
						EntityRegistry.registerModEntity(EntitySteamBoat.class,
						    "SteamBoat", 121, this, 250, 5, true);
						// EntityRegistry.registerModEntity(EntitySteamBoat.class,
						// "SteamBoat", ModLoader.getUniqueEntityId(), this, 250, 5, true);
						// EntityRegistry.registerGlobalEntityID(EntitySteamBoat.class,
						// "SteamBoat", ModLoader.getUniqueEntityId());
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

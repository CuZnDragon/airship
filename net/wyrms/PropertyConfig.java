package net.wyrms.airship;

import java.util.logging.Level;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.common.Configuration;

import net.wyrms.airship.AirShip;

public class PropertyConfig
	{
		public static void initProperties(FMLPreInitializationEvent event)
			{
				Configuration config = new Configuration(
				    event.getSuggestedConfigurationFile());
				try
					{
						config.load();

						AirShip.ENABLE_AIRSHIP = config.getOrCreateBooleanProperty(
						    "EnableAirShip", Configuration.CATEGORY_GENERAL, true)
						    .getBoolean(true);
						AirShip.ENABLE_STEAMBOAT = config.getOrCreateBooleanProperty(
						    "EnableSteamBoat", Configuration.CATEGORY_GENERAL, true)
						    .getBoolean(true);
						// AirShip.ENABLE_PIRATE =
						// config.getOrCreateBooleanProperty("EnablePirate",
						// Configuration.CATEGORY_GENERAL, false).getBoolean(false);

						AirShip.KEY_UP = Keyboard.getKeyIndex(config.getOrCreateProperty(
						    "Key Up", Configuration.CATEGORY_GENERAL,
						    Keyboard.getKeyName(Keyboard.KEY_SPACE)).value);
						AirShip.KEY_DOWN = Keyboard.getKeyIndex(config.getOrCreateProperty(
						    "Key Down", Configuration.CATEGORY_GENERAL,
						    Keyboard.getKeyName(Keyboard.KEY_LSHIFT)).value);
						AirShip.KEY_CHEST = Keyboard.getKeyIndex(config
						    .getOrCreateProperty("Key Chest",
						        Configuration.CATEGORY_GENERAL,
						        Keyboard.getKeyName(Keyboard.KEY_RSHIFT)).value);
						AirShip.KEY_FIRE = Keyboard.getKeyIndex(config.getOrCreateProperty(
						    "Key Fire", Configuration.CATEGORY_GENERAL,
						    Keyboard.getKeyName(Keyboard.KEY_LCONTROL)).value);
						AirShip.SHOW_BOILER = config.getOrCreateBooleanProperty(
						    "ShowBoiler", Configuration.CATEGORY_GENERAL, true).getBoolean(
						    true);

						AirShip.airShipId = config.getOrCreateIntProperty("AirShip",
						    Configuration.CATEGORY_ITEM, 4242).getInt();
						AirShip.engineId = config.getOrCreateIntProperty("Engine",
						    Configuration.CATEGORY_ITEM, 4243).getInt();
						AirShip.balloonId = config.getOrCreateIntProperty("Balloon",
						    Configuration.CATEGORY_ITEM, 4244).getInt();
						AirShip.steamBoatId = config.getOrCreateIntProperty("SteamBoat",
						    Configuration.CATEGORY_ITEM, 5154).getInt();

					} catch (Exception ex)
					{
						FMLLog.log(Level.SEVERE, ex,
						    "Airship has had a problem loading it's configuration file");
					} finally
					{
						config.save();
					}
			}
	}

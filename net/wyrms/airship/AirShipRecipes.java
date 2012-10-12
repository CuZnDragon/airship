package net.wyrms.airship;

import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.wyrms.airship.AirShip;

public class AirShipRecipes
	{

		public static void addAirShipCraftingRecipes()
			{
				GameRegistry.addRecipe(new ItemStack(AirShip.balloon, 1, 0), new Object[] { "###", "# #", "###", '#', new ItemStack(Item.leather) });

				GameRegistry.addRecipe(new ItemStack(AirShip.engine, 1), new Object[] { "###", "#X#", "###", '#', new ItemStack(Item.ingotIron), 'X', new ItemStack(Block.pistonBase) });

				GameRegistry.addRecipe(new ItemStack(AirShip.airShip, 1), new Object[] { "XBX", "ECE", "XDX", 'X', new ItemStack(Item.silk), 'B', new ItemStack(AirShip.balloon), 'C', new ItemStack(Block.chest), 'E', new ItemStack(AirShip.engine), 'D', new ItemStack(Item.boat)});
/*
				GameRegistry.addRecipe(new ItemStack(AirShip.airShip, 1), new Object[] { "XBX", "ECE", "XDX", "X", new ItemStack(Item.silk), 'B',
				    new ItemStack(AirShip.balloon), 'C', new ItemStack(Block.chest),
				    'E', new ItemStack(AirShip.engine), 'D',
				    new ItemStack(Item.boat), 'L', new ItemStack(Block.dispenser),
				    'F', new ItemStack(Block.stoneOvenIdle) });
*/
				
//			GameRegistry.addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 1),
//	    new ItemStack(AirShip.balloon),
//	    new ItemStack(Item.dyePowder, 1, 15));
//	GameRegistry.addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 2),
//	    new ItemStack(AirShip.balloon),
//	    new ItemStack(Item.dyePowder, 1, 14));
//	GameRegistry.addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 3),
//	    new ItemStack(AirShip.balloon),
//	    new ItemStack(Item.dyePowder, 1, 13));
//	GameRegistry.addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 4),
//	    new ItemStack(AirShip.balloon),
//	    new ItemStack(Item.dyePowder, 1, 12));
//	GameRegistry.addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 5),
//	    new ItemStack(AirShip.balloon),
//	    new ItemStack(Item.dyePowder, 1, 11));
//	GameRegistry.addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 6),
//	    new ItemStack(AirShip.balloon),
//	    new ItemStack(Item.dyePowder, 1, 10));
//	GameRegistry
//	    .addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 7),
//	        new ItemStack(AirShip.balloon), new ItemStack(Item.dyePowder,
//	            1, 9));
//	GameRegistry
//	    .addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 8),
//	        new ItemStack(AirShip.balloon), new ItemStack(Item.dyePowder,
//	            1, 8));
//	GameRegistry
//	    .addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 9),
//	        new ItemStack(AirShip.balloon), new ItemStack(Item.dyePowder,
//	            1, 7));
//	GameRegistry
//	    .addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 10),
//	        new ItemStack(AirShip.balloon), new ItemStack(Item.dyePowder,
//	            1, 6));
//	GameRegistry
//	    .addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 11),
//	        new ItemStack(AirShip.balloon), new ItemStack(Item.dyePowder,
//	            1, 5));
//	GameRegistry
//	    .addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 12),
//	        new ItemStack(AirShip.balloon), new ItemStack(Item.dyePowder,
//	            1, 4));
//	GameRegistry
//	    .addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 13),
//	        new ItemStack(AirShip.balloon), new ItemStack(Item.dyePowder,
//	            1, 2));
//	GameRegistry
//	    .addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 14),
//	        new ItemStack(AirShip.balloon), new ItemStack(Item.dyePowder,
//	            1, 1));
//	GameRegistry
//	    .addShapelessRecipe(new ItemStack(AirShip.balloon, 1, 15),
//	        new ItemStack(AirShip.balloon), new ItemStack(Item.dyePowder,
//	            1, 0));
			}

		public static void addSteamBoatCraftingRecipes()
			{
				GameRegistry.addRecipe(new ItemStack(AirShip.steamBoat, 1),
				    new Object[]
					    { "#X#", "###", '#', new ItemStack(Block.planks), 'X',
					        new ItemStack(Item.ingotIron) });

			}

		public static void addAirShipNames()
			{
				LanguageRegistry.addName(AirShip.airShip, "Air Ship");
				LanguageRegistry.addName(AirShip.balloon, "Ballon");
				LanguageRegistry.addName(AirShip.engine, "Engine");
			}

		public static void addSteamBoatNames()
			{
				LanguageRegistry.addName(AirShip.steamBoat, "Steam Boat");
			}
	}

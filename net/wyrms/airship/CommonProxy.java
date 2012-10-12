package net.wyrms.airship;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.wyrms.airship.client.GuiAirShip;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.network.IGuiHandler;


//public class CommonProxy implements IGuiHandler
public class CommonProxy
	{
		public static String AIRSHIP_ITEMS_PNG = "/net/wyrms/airship/graphics/airship-items.png";
		public static String AIRSHIP_PNG = "/net/wyrms/airship/graphics/airship.png";
		public static String BALLOON_PNG = "/net/wyrms/airship/graphics/balloon.png";
		public static String AIRSHIP_GUI_PNG = "/net/wyrms/airship/graphics/airshipgui.png";
		public static String STEAMBOAT_PNG = "/net/wyrms/airship/graphics/steamboat.png";

		// Client stuff
		public void registerRenderer()
			{
				// Nothing here as this is the server side proxy
			}

/*
		@Override
		public Object getServerGuiElement(int ID, EntityPlayer player, World world,
		    int x, int y, int z)
			{
				// TODO Auto-generated method stub
				return null;
			}

		@Override
		public Object getClientGuiElement(int ID, EntityPlayer player, World world,
		    int x, int y, int z)
			{
				// TODO Auto-generated method stub
				return null;
			}
*/
	}

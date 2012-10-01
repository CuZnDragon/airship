package net.wyrms.airship;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.network.IGuiHandler;

//public class CommonProxy implements IGuiHandler
public class CommonProxy
	{
		public static String AIRSHIP_ITEMS_PNG = "/airship/graphics/airship-items.png";

		// public static String AC_ITEMS_PNG =
		// "/net/wyrms/airship/graphics/ac-items.png";

		// Client stuff
		public void registerRenderer()
			{
				// Nothing here as this is the server side proxy
			}

//		@Override
//		public Object getServerGuiElement(int ID, EntityPlayer player, World world,
//		    int x, int y, int z)
//			{
//				// TODO Auto-generated method stub
//				return null;
//			}

//		@Override
//		public Object getClientGuiElement(int ID, EntityPlayer player, World world,
//		    int x, int y, int z)
//			{
//				// TODO Auto-generated method stub
//				return null;
//			}

	}

package net.wyrms.airship;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiInventory;

@SideOnly(Side.CLIENT)
public class GuiInventoryMoreSlots extends GuiInventory
	{
		public GuiInventoryMoreSlots(EntityPlayer entityplayer)
			{
				super(entityplayer);
				// TODO Auto-generated constructor stub
			}
	}

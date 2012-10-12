package net.wyrms.airship;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;

public class ItemBalloon extends Item
	{
//		private final static String[] ballonColors =
//			{ "raw", "white", "orange", "magenta", "lightBlue", "yellow",
//			    "lightGreen", "pink", "darkGrey", "lightGrey", "cyan", "purple",
//			    "blue", "green", "red", "black" };

		public ItemBalloon(int itemIndex)
			{
				super(itemIndex);
				this.maxStackSize = 1;
				this.setTabToDisplayOn(CreativeTabs.tabTransport);
//				this.setHasSubtypes(true);
				this.setMaxDamage(0);
				this.setIconIndex(0);
				this.setItemName("itemBalloon");
			}

//		@SideOnly(Side.CLIENT)
		// @Override
//		public int getIconFromDamage(int damageValue)
//			{
//				int var1 = MathHelper.clamp_int(damageValue, 0, 15);
//				return this.iconIndex + var1;
//			}

		// @Override
//		public String getItemNameIS(ItemStack itemstack)
//			{
//				return getItemName() + "." + ballonColors[itemstack.getItemDamage()];
//			}

//		@SideOnly(Side.CLIENT)
		// @Override
//		public void getSubItems(int pi1, CreativeTabs pCT2,
//		    List pLi3)
//			{
//				for (int i = 0; i < 16; ++i)
//					{
//						pLi3.add(new ItemStack(pi1, 1, i));
//					}
//			}

		public String getTextureFile()
			{
				return CommonProxy.AIRSHIP_ITEMS_PNG;
			}

	}

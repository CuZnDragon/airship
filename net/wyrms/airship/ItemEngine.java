package net.wyrms.airship;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class ItemEngine extends Item
	{
		public ItemEngine(int itemIndex)
			{
				super(itemIndex);
				this.maxStackSize = 1;
				this.setCreativeTab(CreativeTabs.tabTransport);
				this.setIconIndex(16);
				this.setItemName("itemEngine");
			}

		public String getTextureFile()
			{
				return CommonProxy.AIRSHIP_ITEMS_PNG;
			}
	}

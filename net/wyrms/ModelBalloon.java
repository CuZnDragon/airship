package net.wyrms.airship;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.ModelBase;

@SideOnly(Side.CLIENT)
public class ModelBalloon extends ModelBase
	{
		public CustomModelRenderer boxes;

		public ModelBalloon()
			{
				this.boxes = new CustomModelRenderer(0, 0, 256, 256);
				// Balloon
				this.boxes.addBox(0, 0, 0, 64, 64, 64, -8.0F);
				this.boxes.setPosition(-30, -15, -30);
				this.boxes.rotateAngleX = 1.570796F;
			}

		public void render(float f, float f1, float f2, float f3, float f4, float f5)
			{
				this.boxes.render(f5);
			}
	}

package net.wyrms.airship;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.ModelBase;

import net.wyrms.airship.client.CustomModelRenderer;

@SideOnly(Side.CLIENT)
public class ModelAirShip extends ModelBase

	{
		private float bladespin;
		private long lastframe;
		public CustomModelRenderer boxes[];

		public ModelAirShip()
			{
				this.lastframe = System.nanoTime();
				this.bladespin = 0.0F;

				this.boxes = new CustomModelRenderer[27];

				this.boxes[0] = new CustomModelRenderer(0, 8, 64, 64);
				this.boxes[1] = new CustomModelRenderer(0, 0, 64, 64);
				this.boxes[2] = new CustomModelRenderer(0, 0, 64, 64);
				this.boxes[3] = new CustomModelRenderer(0, 0, 64, 64);
				this.boxes[4] = new CustomModelRenderer(0, 0, 64, 64);
				this.boxes[5] = new CustomModelRenderer(56, 0, 64, 64);
				this.boxes[6] = new CustomModelRenderer(56, 0, 64, 64);
				this.boxes[7] = new CustomModelRenderer(56, 0, 64, 64);
				this.boxes[8] = new CustomModelRenderer(56, 0, 64, 64);
				this.boxes[9] = new CustomModelRenderer(36, 39, 64, 64);
				this.boxes[14] = new CustomModelRenderer(36, 39, 64, 64);

				// Propeller Blades
				this.boxes[10] = new CustomModelRenderer(0, 55, 64, 64);
				this.boxes[11] = new CustomModelRenderer(0, 55, 64, 64);
				this.boxes[12] = new CustomModelRenderer(0, 55, 64, 64);
				this.boxes[13] = new CustomModelRenderer(0, 55, 64, 64);
				this.boxes[15] = new CustomModelRenderer(0, 55, 64, 64);
				this.boxes[16] = new CustomModelRenderer(0, 55, 64, 64);
				this.boxes[17] = new CustomModelRenderer(0, 55, 64, 64);
				this.boxes[18] = new CustomModelRenderer(0, 55, 64, 64);
				// ///////
				// / Ballloooooonz
				// ///////
				this.boxes[19] = new CustomModelRenderer(0, 0, 64, 64);
				this.boxes[20] = new CustomModelRenderer(0, 43, 64, 64);
				this.boxes[21] = new CustomModelRenderer(28, 44, 64, 64);

				// Rudder
				this.boxes[22] = new CustomModelRenderer(0, 32, 64, 64);
				this.boxes[23] = new CustomModelRenderer(56, 0, 64, 64);
				this.boxes[24] = new CustomModelRenderer(56, 0, 64, 64);
				this.boxes[25] = new CustomModelRenderer(56, 0, 64, 64);
				this.boxes[26] = new CustomModelRenderer(56, 0, 64, 64);

				byte byte0 = 24;
				byte byte1 = 6;
				byte byte2 = 20;
				byte byte3 = 4;

				// Bottom Panel
				this.boxes[0].addBox(-12, -8, -3, 24, 16, 4, 0.0F);
				this.boxes[0].setPosition(0.0F, 0 + byte3, 0.0F);
				this.boxes[0].rotateAngleX = 1.570796F;

				// Front Panel
				this.boxes[1].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2,
				    0.0F);
				this.boxes[1].setPosition(-byte0 / 2 + 1, 0 + byte3, 0.0F);
				this.boxes[1].rotateAngleY = 4.712389F;

				// Rear Panel
				this.boxes[2].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2,
				    0.0F);
				this.boxes[2].setPosition(byte0 / 2 - 1, 0 + byte3, 0.0F);
				this.boxes[2].rotateAngleY = 1.570796F;
				// Left Panel
				this.boxes[3].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2,
				    0.0F);
				this.boxes[3].setPosition(0.0F, 0 + byte3, -byte2 / 2 + 1);
				this.boxes[3].rotateAngleY = 3.141593F;
				// Right Panel
				this.boxes[4].addBox(-byte0 / 2 + 2, -byte1 - 1, -1F, byte0 - 4, byte1, 2,
				    0.0F);
				this.boxes[4].setPosition(0.0F, 0 + byte3, byte2 / 2 - 1);

				// TopLeft Connector
				this.boxes[5].addBox(-2, -13, -1f, 2, byte0, 2, 0.0F);
				this.boxes[5].setPosition(-12.0F, -12.5f, -byte2 / 2 - 3.5f);
				this.boxes[5].rotateAngleY = 0.392699F;
				this.boxes[5].rotateAngleX = 0.392699F;
				// BottomLeft Connector
				this.boxes[7].addBox(-2, -13, -1f, 2, byte0, 2, 0.0F);
				this.boxes[7].setPosition(13.5F, -12.5f, -byte2 / 2 - 2);
				this.boxes[7].rotateAngleY = -0.392699F;
				this.boxes[7].rotateAngleX = 0.392699F;
				// TopRight Connector
				this.boxes[6].addBox(-2, -13, -1f, 2, byte0, 2, 0.0F);
				this.boxes[6].setPosition(-12.0F, -12.5f, +byte2 / 2 + 3.5f);
				this.boxes[6].rotateAngleY = -0.392699F;
				this.boxes[6].rotateAngleX = -0.392699F;
				// BottomRight Connector
				this.boxes[8].addBox(-2, -13, -1f, 2, byte0, 2, 0.0F);
				this.boxes[8].setPosition(13.5F, -12.5f, +byte2 / 2 + 2);
				this.boxes[8].rotateAngleY = 0.392699F;
				this.boxes[8].rotateAngleX = -0.392699F;

				// Left Engine Supports
				this.boxes[23].addBox(-2, -2, -1f, 2, 13, 2, 0.0F);
				this.boxes[23].setPosition(0, 3, -byte0 / 2 - 7f);
				this.boxes[23].rotateAngleX = 1.570796F;
				this.boxes[24].addBox(-2, -2, -1f, 2, 13, 2, 0.0F);
				this.boxes[24].setPosition(6, 3, -byte0 / 2 - 7f);
				this.boxes[24].rotateAngleX = 1.570796F;

				// Right Engine Supports
				this.boxes[25].addBox(-2, -2, -1f, 2, 13, 2, 0.0F);
				this.boxes[25].setPosition(0, 3, byte0 / 2 - 2f);
				this.boxes[25].rotateAngleX = 1.570796F;
				this.boxes[26].addBox(-2, -2, -1f, 2, 13, 2, 0.0F);
				this.boxes[26].setPosition(6, 3, byte0 / 2 - 2f);
				this.boxes[26].rotateAngleX = 1.570796F;

				// Right Engine
				this.boxes[9].addBox(-8, -4, 0, 10, 8, 4, 1f);
				this.boxes[9].setPosition(0, 3, byte0 / 2 + 9f);
				this.boxes[9].rotateAngleY = (float) Math.PI;
				// Left Engine
				this.boxes[14].addBox(-8, -4, 0, 10, 8, 4, 1F);
				this.boxes[14].setPosition(0, 3, -byte0 / 2 - 5f);
				this.boxes[14].rotateAngleY = (float) Math.PI;
				// Left Propeller
				this.boxes[10].addBox(-6, -0.5f, -0.5F, 12, 1, 2, 0.0F);
				this.boxes[10].setPosition(9.5F, 0.0F, byte0 / 2 + 7f);
				this.boxes[10].rotateAngleX = 1.570796F;
				this.boxes[10].rotateAngleZ = 1.570796F;
				this.boxes[11].addBox(-byte3 - 2, -0.5f, -0.5F, byte0 / 2, 1, 2, 0.0F);
				this.boxes[11].setPosition(9.5F, 0.0F, byte0 / 2 + 7f);
				this.boxes[11].rotateAngleX = 1.570796F;
				this.boxes[11].rotateAngleZ = 1.570796F;
				this.boxes[12].addBox(-byte3 - 2, -0.5f, -0.5F, byte0 / 2, 1, 2, 0.0F);
				this.boxes[12].setPosition(9.5F, 0.0F, byte0 / 2 + 7f);
				this.boxes[12].rotateAngleX = 1.570796F;
				this.boxes[12].rotateAngleZ = 1.570796F;
				this.boxes[13].addBox(-byte3 - 2, -0.5f, -0.5F, byte0 / 2, 1, 2, 0.0F);
				this.boxes[13].setPosition(9.5F, 0.0F, byte0 / 2 + 7f);
				this.boxes[13].rotateAngleX = 1.570796F;
				this.boxes[13].rotateAngleZ = 1.570796F;

				// Right Propeller

				this.boxes[15].addBox(-byte3 - 2, -0.5f, -0.5F, byte0 / 2, 1, 2, 0.0F);
				this.boxes[15].setPosition(9.5F, 0.0F, -byte0 / 2 - 7f);
				this.boxes[15].rotateAngleX = 1.570796F;
				this.boxes[15].rotateAngleZ = 1.570796F;
				this.boxes[16].addBox(-byte3 - 2, -0.5f, -0.5F, byte0 / 2, 1, 2, 0.0F);
				this.boxes[16].setPosition(9.5F, 0.0F, -byte0 / 2 - 7f);
				this.boxes[16].rotateAngleX = 1.570796F;
				this.boxes[16].rotateAngleZ = 1.570796F;
				this.boxes[17].addBox(-byte3 - 2, -0.5f, -0.5F, byte0 / 2, 1, 2, 0.0F);
				this.boxes[17].setPosition(9.5F, 0.0F, -byte0 / 2 - 7f);
				this.boxes[17].rotateAngleX = 1.570796F;
				this.boxes[17].rotateAngleZ = 1.570796F;
				this.boxes[18].addBox(-byte3 - 2, -0.5f, -0.5F, byte0 / 2, 1, 2, 0.0F);
				this.boxes[18].setPosition(9.5F, 0.0F, -byte0 / 2 - 7f);
				this.boxes[18].rotateAngleX = 1.570796F;
				this.boxes[18].rotateAngleZ = 1.570796F;

				this.boxes[19].addBox(0, 0, 0.0f, 0, 0, 0, 0.0F);

				// Boiler
				// boiler
				// -8, -4, 0, 10, 8, 4
				if (AirShip.SHOW_BOILER)
					{
						this.boxes[20].addBox(-8, -4, 0.0f, 10, 8, 4, 0.0F);
						this.boxes[21].addBox(-2, -13, -1f, 2, 14, 2, 0.0F);
					} else
					{
						this.boxes[20].addBox(0, 0, 0.0f, 0, 0, 0, 0.0F);
						this.boxes[21].addBox(0, 0, 0.0f, 0, 0, 0, 0.0F);
					}
				this.boxes[20].setPosition(0.0F, 3.0F, 0.0F);
				this.boxes[20].rotateAngleX = 1.570796F;
				// Chimney

				this.boxes[21].setPosition(0.0F, 0.0F, 0.0F);

				// Rudder
				this.boxes[22].addBox(0, 0, 0, 0, 0, 0, 0.0F);

			}

		public void render(float f, float f1, float f2, float f3, float f4, float f5)
			{
				// figure out elapsed time since last frame to get a steady spin on the
				// paddles
				long now = System.nanoTime();
				int elapsed = (int) ((now - this.lastframe) / (1000 * 1000));
				this.bladespin -= (float) elapsed / 300.0f;
				this.lastframe = now;

				// give each of the paddles a quarter spin to make them fan out
				this.boxes[10].rotateAngleY = this.bladespin;
				this.boxes[11].rotateAngleY = this.bladespin + 0.78539825F;
				this.boxes[12].rotateAngleY = this.bladespin + 1.570796F;
				this.boxes[13].rotateAngleY = this.bladespin + 2.35619475F;

				this.boxes[15].rotateAngleY = this.bladespin;
				this.boxes[16].rotateAngleY = this.bladespin + 0.78539825F;
				this.boxes[17].rotateAngleY = this.bladespin + 1.570796F;
				this.boxes[18].rotateAngleY = this.bladespin + 2.35619475F;
				for (int i = 0; i < 27; i++)
					{
						this.boxes[i].render(f5);
					}
			}
	}

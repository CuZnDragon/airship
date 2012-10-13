package net.wyrms.airship.client;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityFX;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.ModelBase;
import net.minecraft.src.Render;
import net.minecraft.src.WorldClient;
import net.wyrms.airship.AirShip;
import net.wyrms.airship.EntityAirShip;
import net.wyrms.airship.client.EntitySteamFX;
import net.wyrms.airship.client.EntitySteamExplode;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAirShip extends Render
	{
		// protected ModelBase field_20920_e;
		// protected ModelBase field_77045_g;
		protected ModelBase modelAirShip;
		// protected ModelBase renderPassModel;
		protected ModelBase modelBalloon;
		private Minecraft mc;
		private WorldClient worldObj;
		private EntityAirShip en1;

		public RenderAirShip(ModelBase modelbase, ModelBase modelbase1, float f)
			{
				this(modelbase, f);
				setRenderPassModel(modelbase1);
				this.mc = FMLClientHandler.instance().getClient();
				this.worldObj = FMLClientHandler.instance().getClient().theWorld;
			}

		public RenderAirShip(ModelBase modelbase, float f)
			{
				// field_20920_e = modelbase;
				// field_77045_g = modelbase;
				modelAirShip = modelbase;
				shadowSize = f;
			}

		public void setRenderPassModel(ModelBase modelbase)
			{
				// renderPassModel = modelbase;
				modelBalloon = modelbase;
			}

		// public void airShipSpawnParticle(String sPType, double posX, double posY,
		// double posZ, double velX, double velY, double velZ)
		public void spawnParticle(String sPType, double posX, double posY,
		    double posZ, double velX, double velY, double velZ)
			{
				this.airShipParticle(sPType, posX, posY, posZ, velX, velY, velZ);
			}

		// this is from RenderGlobal public EntityFX func_72726_b(String par1Str,
		// double par2, double par4, double par6, double par8, double par10, double
		// par12)
		// public void airShipParticle(String sPType, double posX, double posY,
		// double posZ, double velX, double velY, double velZ)
		public EntityFX airShipParticle(String sPType, double posX, double posY,
		    double posZ, double velX, double velY, double velZ)
			{
				if (this.mc != null && this.mc.renderViewEntity != null
				    && this.mc.effectRenderer != null)
					{
						EntityFX returnEntity = null;
						Random random = new Random();

						if (sPType.equals("steamexplode"))
							{
								this.mc.effectRenderer
								    .addEffect(returnEntity = new EntitySteamExplode(
								        this.worldObj, posX, posY, posZ, velX, velY, velZ));
							}

						if (sPType.equals("steam"))
							{
								this.mc.effectRenderer
								    .addEffect(returnEntity = new EntitySteamFX(this.worldObj,
								        posX, posY, posZ, velX, velY, velZ));
							}

						if (returnEntity != null)
							{
								return returnEntity;
							}
						else
							{
								return null;
							}
					}
				else
					{
						return null;
					}
			}

		//	public void func_157_a(EntityAirShip entityairship, double d, double d1,
		//	public void func_76997_a(EntityAirShip entityairship, double d, double d1,
		public void renderAirShip(EntityAirShip entityairship, double d, double d1,
		    double d2, float f, float f1)
			{
				en1 = entityairship;
				GL11.glPushMatrix();
				GL11.glTranslatef((float) d, (float) d1, (float) d2);
				GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
				// float f2 = (float) entityairship.field_806_b - f1;
				// float f3 = (float) entityairship.field_807_a - f1;
				float f2 = (float) entityairship.getTimeSinceHit() - f1;
				float f3 = (float) entityairship.getDamageTaken() - f1;
				if (f3 < 0.0F)
					{
						f3 = 0.0F;
					}
				if (f2 > 0.0F)
					{
						// GL11.glRotatef(((MathHelper.sin(f2) * f2 * f3) / 10F) * (float)
						// entityairship.field_808_c, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(((MathHelper.sin(f2) * f2 * f3) / 10F)
						    * (float) entityairship.getForwardDirection(), 1.0F, 0.0F, 0.0F);
					}
				loadTexture("/terrain.png");
				float f4 = 0.75F;
				GL11.glScalef(f4, f4, f4);
				GL11.glScalef(1.0F / f4, 1.0F / f4, 1.0F / f4);

				loadTexture("/net/wyrms/airship/graphics/airship.png");

				GL11.glScalef(-1F, -1F, 1.0F);

				modelAirShip.render(entityairship, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F,
				    0.0625F);
				if (true)
					{
						// This is where the balloon color would need to be set.
						loadTexture("/net/wyrms/airship/graphics/balloon.png");
						modelBalloon.render(entityairship, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F,
						    0.0625F);
					}
				GL11.glPopMatrix();
			}

		public void doRender(Entity entity, double d, double d1, double d2,
		    float f, float f1)
			{
				// func_157_a((EntityAirShip) entity, d, d1, d2, f, f1);
				// this.func_76997_a((EntityAirShip) entity, d, d1, d2, f, f1);
				this.renderAirShip((EntityAirShip) entity, d, d1, d2, f, f1);
			}
	}

package net.wyrms.airship;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

@SideOnly(Side.CLIENT)
public class EntitySteamFX extends EntityFX
	{
//	float field_671_a;
		float steamParticleScale;
		
		public EntitySteamFX(World world, double d, double d1, double d2,
		    double d3, double d4, double d5)
			{
				this(world, d, d1, d2, d3, d4, d5, 1.0F);
			}

		public EntitySteamFX(World world, double d, double d1, double d2,
		    double d3, double d4, double d5, float f)
			{
				super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
				this.motionX *= 0.10000000149011612D;
				this.motionY *= 0.10000000149011612D;
				this.motionZ *= 0.10000000149011612D;
				this.motionX += d3;
				this.motionY += d4;
				this.motionZ += d5;
				this.particleRed = 230;
				this.particleGreen = 230;
				this.particleBlue = 230;
				this.particleScale *= 0.75F;
				this.particleScale *= f;
//				field_671_a = particleScale;
				this.steamParticleScale = particleScale;
				this.particleMaxAge = (int) (8D / (Math.random() * 0.80000000000000004D + 0.20000000000000001D));
				this.particleMaxAge *= f;
				noClip = false;
			}

		public void renderParticle(Tessellator tessellator, float f, float f1,
		    float f2, float f3, float f4, float f5)
			{
				float f6 = (((float)this.particleAge + f) / (float)this.particleMaxAge) * 32F;
				if (f6 < 0.0F)
					{
						f6 = 0.0F;
					}
				if (f6 > 1.0F)
					{
						f6 = 1.0F;
					}
//				particleScale = field_671_a * f6;
				this.particleScale = this.steamParticleScale * f6;
				super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);
			}

		public void onUpdate()
			{
				this.prevPosX = this.posX;
				this.prevPosY = this.posY;
				this.prevPosZ = this.posZ;
				if (this.particleAge++ >= this.particleMaxAge)
					{
						this.setDead();
					}
				this.setParticleTextureIndex(7 - (this.particleAge * 8) / this.particleMaxAge);
				this.motionY += 0.0040000000000000001D;
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				if (this.posY == this.prevPosY)
					{
						this.motionX *= 1.1000000000000001D;
						this.motionZ *= 1.1000000000000001D;
					}
				this.motionX *= 0.95999997854232788D;
				this.motionY *= 0.95999997854232788D;
				this.motionZ *= 0.95999997854232788D;
				if (onGround)
					{
						this.motionX *= 0.69999998807907104D;
						this.motionZ *= 0.69999998807907104D;
					}
			}
	}

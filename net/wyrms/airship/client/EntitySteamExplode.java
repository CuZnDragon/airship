package net.wyrms.airship.client;

import java.util.Random;

import net.minecraft.src.EntityFX;
import net.minecraft.src.Tessellator;
import net.minecraft.src.World;

public class EntitySteamExplode extends EntityFX
	{

		public EntitySteamExplode(World world, double d, double d1, double d2,
		    double d3, double d4, double d5)
			{
				super(world, d, d1, d2, d3, d4, d5);
				this.motionX = d3 + (double) ((float) (Math.random() * 2D - 1.0D) * 0.05F);
				this.motionY = d4 + (double) ((float) (Math.random() * 2D - 1.0D) * 0.05F);
				this.motionZ = d5 + (double) ((float) (Math.random() * 2D - 1.0D) * 0.05F);
				this.particleRed = 230;
				this.particleGreen = 230;
				this.particleBlue = 230;
				this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6F + 1.0F;
				this.particleMaxAge = (int) (16D / ((double) this.rand.nextFloat() * 0.80000000000000004D + 0.20000000000000001D)) + 2;
			}

		public void renderParticle(Tessellator tessellator, float f, float f1,
		    float f2, float f3, float f4, float f5)
			{
				super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);
			}

		public void onUpdate()
			{
				this.prevPosX = this.posX;
				this.prevPosY = this.posY;
				this.prevPosZ = this.posZ;
				if (this.particleAge++ >= this.particleMaxAge)
					{
						setDead();
					}
				this.setParticleTextureIndex(7 - (this.particleAge * 8) / this.particleMaxAge);

				this.motionY += 0.0080000000000000001D;
				this.moveEntity(this.motionX, this.motionY, this.motionZ);
				this.motionX *= 0.89999997615814209D;
				this.motionY *= 0.89999997615814209D;
				this.motionZ *= 0.89999997615814209D;
				if (onGround)
					{
						this.motionX *= 0.69999998807907104D;
						this.motionZ *= 0.69999998807907104D;
					}
			}
	}

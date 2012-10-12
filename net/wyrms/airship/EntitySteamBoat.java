package net.wyrms.airship;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;
import net.wyrms.airship.client.ModelSteamBoat;
import net.wyrms.airship.client.RenderAirShip;
import net.wyrms.airship.client.RenderSteamBoat;

public class EntitySteamBoat extends Entity
	{
		// These are the datawatch variables below that have the get and set
		// functions
		// public int field_807_a;
		// public int steamBoatCurrentDamage;
		// public int field_806_b;
		// public int steamBoatTimeSinceHit;
		// public int field_808_c;
		// public int steamBoatRockDirection;
		private boolean field_70279_a;
		private double field_70276_b;
		// private int field_9394_d;
		private int steamBoatPosRotationIncrements;
		// private double field_9393_e;
		private double steamBoatX;
		// private double field_9392_f;
		private double steamBoatY;
		// private double field_9391_g;
		private double steamBoatZ;
		// private double field_9390_h;
		private double steamBoatYaw;
		// private double field_9389_i;
		private double steamBoatPitch;
		// private double field_9388_j;
		@SideOnly(Side.CLIENT)
		private double velocityX;
		// private double field_9387_k;
		@SideOnly(Side.CLIENT)
		private double velocityY;
		// private double field_9386_l;
		@SideOnly(Side.CLIENT)
		private double velocityZ;

		public EntitySteamBoat(World world)
			{
				super(world);
				// these are the datawatchers below...
				// this.steamBoatCurrentDamage = 0; //field_807_a
				// this.steamBoatTimeSinceHit = 0; //field_806_b
				// this.steamBoatRockDirection = 1; //field_808_c
				this.field_70279_a = true;
				this.field_70276_b = 0.07D;
				preventEntitySpawning = true;
				setSize(1.5F, 0.6F);
				yOffset = height / 2.0F;

			}

		/**
		 * returns if this entity triggers Block.onEntityWalking on the blocks they
		 * walk on. used for spiders and wolves to prevent them from trampling crops
		 */
		protected boolean canTriggerWalking()
			{
				return false;
			}

		@Override
		protected void entityInit()
			{
				this.dataWatcher.addObject(17, new Integer(0)); // steamBoatTimeSinceHit
				this.dataWatcher.addObject(18, new Integer(1)); // steamBoatRockDirection
				this.dataWatcher.addObject(19, new Integer(0)); // steamBoatCurrentDamage
			}

		// public AxisAlignedBB func_383_b_(Entity entity)
		public AxisAlignedBB getCollisionBox(Entity entity)
			{
				return entity.boundingBox;
			}

		// public AxisAlignedBB func_372_f_()
		public AxisAlignedBB getBoundingBox()
			{
				return this.boundingBox;
			}

		public boolean canBePushed()
			{
				return true;
			}

		public EntitySteamBoat(World world, double d, double d1, double d2)
			{
				this(world);
				this.setPosition(d, d1 + (double) this.yOffset, d2);
				this.motionX = 0.0D;
				this.motionY = 0.0D;
				this.motionZ = 0.0D;
				this.prevPosX = d;
				this.prevPosY = d1;
				this.prevPosZ = d2;
			}

		public double getMountedYOffset()
			{
				return (double) height * 0.0D - 0.30000001192092896D;
			}

		// public boolean attackEntityFrom(Entity entity, int i)
		public boolean attackEntityFrom(DamageSource damageSource, int i)
			{
				if (damageSource.getSourceOfDamage() == null)
					{
						return true;
					}

				if (this.worldObj.isRemote || this.isDead)
					{
						return true;
					}

				// field_808_c = -field_808_c;
				// field_806_b = 10;
				// field_807_a += i * 10;
				// this.steamBoatRockDirection = -this.steamBoatRockDirection;
				this.setForwardDirection(-this.getForwardDirection());
				// this.steamBoatTimeSinceHit = 10;
				this.setTimeSinceHit(10);
				// this.steamBoatCurrentDamage += i * 10;
				this.setDamageTaken(this.getDamageTaken() + i * 10);
				this.setBeenAttacked();

				if (damageSource.getEntity() instanceof EntityPlayer
				    && ((EntityPlayer) damageSource.getEntity()).capabilities.isCreativeMode)
					{
						this.setDamageTaken(100);
					}

				// if (field_807_a > 80)
				if (this.getDamageTaken() > 80)
					{
//						for (int j = 0; j < 5; j++)
//							{
//								dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
//							}
//
//						for (int k = 0; k < 1; k++)
//							{
//								dropItemWithOffset(Item.ingotIron.shiftedIndex, 1, 0.0f);
//							}
//						setDead();

						if (this.riddenByEntity != null)
							{
								this.riddenByEntity.mountEntity(this);
							}

						this.dropItemWithOffset(AirShip.steamBoat.shiftedIndex, 1, 0.0F);
						this.setDead();
					}
				return true;
			}

		@SideOnly(Side.CLIENT)
		public void performHurtAnimation()
			{
				// field_808_c = -field_808_c;
				// field_806_b = 10;
				// field_807_a += field_807_a * 10;
				// steamBoatRockDirection = -steamBoatRockDirection;
				// steamBoatTimeSinceHit = 10;
				// steamBoatCurrentDamage += steamBoatCurrentDamage * 2;
				this.setForwardDirection(-this.getForwardDirection());
				this.setTimeSinceHit(10);
				this.setDamageTaken(this.getDamageTaken() * 10);
			}

		public boolean canBeCollidedWith()
			{
				return !isDead;
			}

		@SideOnly(Side.CLIENT)
		public void setPositionAndRotation2(double d, double d1, double d2,
		    float f, float f1, int i)
			{
				// field_9393_e = d;
				// field_9392_f = d1;
				// field_9391_g = d2;
				// field_9390_h = f;
				// field_9389_i = f1;
				// field_9394_d = i + 4;
				// motionX = field_9388_j;
				// motionY = field_9387_k;
				// motionZ = field_9386_l;
				// steamBoatPosRotationIncrements = i + 4;
				if (this.field_70279_a)
					{
						this.steamBoatPosRotationIncrements = i + 4;
					}
				else
					{
						double var10 = d - this.posX;
						double var12 = d1 - this.posY;
						double var14 = d2 - this.posZ;
						double var16 = var10 * var10 + var12 * var12 + var14 * var14;

						if (var16 <= 1.0D)
							{
								return;
							}

						this.steamBoatPosRotationIncrements = 3;
					}

				this.steamBoatX = d;
				this.steamBoatY = d1;
				this.steamBoatZ = d2;
				this.steamBoatYaw = (double) f;
				this.steamBoatPitch = (double) f1;
				this.motionX = this.velocityX;
				this.motionY = this.velocityY;
				this.motionZ = this.velocityZ;
			}

		@SideOnly(Side.CLIENT)
		public void setVelocity(double d, double d1, double d2)
			{
				// field_9388_j = motionX = d;
				// field_9387_k = motionY = d1;
				// field_9386_l = motionZ = d2;
				this.velocityX = this.motionX = d;
				this.velocityY = this.motionY = d1;
				this.velocityZ = this.motionZ = d2;
			}

		public void onUpdate()
			{
				super.onUpdate();
				// if (field_806_b > 0)
				// {
				// field_806_b--;
				// }
				// if (field_807_a > 0)
				// {
				// field_807_a--;
				// }
				if (this.getTimeSinceHit() > 0)
					{
						this.setTimeSinceHit(this.getTimeSinceHit() - 1);
					}

				if (this.getDamageTaken() > 0)
					{
						this.setDamageTaken(this.getDamageTaken() - 1);
					}

				this.prevPosX = this.posX;
				this.prevPosY = this.posY;
				this.prevPosZ = this.posZ;
				int i = 5;
				double d = 0.0D;
				for (int j = 0; j < i; j++)
					{
						double d4 = (this.boundingBox.minY + ((this.boundingBox.maxY - this.boundingBox.minY) * (double) (j + 0))
						    / (double) i) - 0.125D;
						double d8 = (this.boundingBox.minY + ((this.boundingBox.maxY - this.boundingBox.minY) * (double) (j + 1))
						    / (double) i) - 0.125D;
						// AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(
						AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool()
						    .addOrModifyAABBInPool(this.boundingBox.minX, d4,
						        this.boundingBox.minZ, this.boundingBox.maxX, d8,
						        boundingBox.maxZ);
						if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water))
							{
								d += 1.0D / (double) i;
							}
					}

				double d11 = Math.sqrt(motionX * motionX + motionZ * motionZ);

				double d9;
				double d12;
				double d13;
				double d15;

				// if (d11 > 0.14999999999999999D)
				if (d11 > 0.26249999999999996D)
					{
						// double d13 = Math.cos(((double) rotationYaw *
						// 3.1415926535897931D) / 180D);
						// double d15 = Math.sin(((double) rotationYaw *
						// 3.1415926535897931D) / 180D);
						d13 = Math.cos(((double) this.rotationYaw * Math.PI) / 180D);
						d15 = Math.sin(((double) this.rotationYaw * Math.PI) / 180D);

						for (int i1 = 0; (double) i1 < 1.0D + d11 * 60D; i1++)
							{
								double d18 = this.rand.nextFloat() * 2.0F - 1.0F;
								// double d20 = (double) (rand.nextInt(2) * 2 - 1) *
								// 0.69999999999999996D;
								double d20 = (double) (rand.nextInt(2) * 2 - 1) * 0.7D;
								if (rand.nextBoolean())
									{
										// double d21 = (posX - d13 * d18 * 0.80000000000000004D)
										// + d15 * d20;
										double d21 = (this.posX - d13 * d18 * 0.8D) + d15 * d20;
										// double d23 = posZ - d15 * d18 * 0.80000000000000004D -
										// d13
										// * d20;
										double d23 = this.posZ - d15 * d18 * 0.8D - d13 * d20;
										this.worldObj.spawnParticle("splash", d21,
										    this.posY - 0.125D, d23, this.motionX, this.motionY,
										    this.motionZ);
									}
								else
									{
										// double d22 = posX + d13 + d15 * d18 *
										// 0.69999999999999996D;
										double d22 = this.posX + d13 + d15 * d18 * 0.7D;
										// double d24 = (posZ + d15) - d13 * d18
										// * 0.69999999999999996D;
										double d24 = (this.posZ + d15) - d13 * d18 * 0.7D;
										this.worldObj.spawnParticle("splash", d22,
										    this.posY - 0.125D, d24, this.motionX, this.motionY,
										    this.motionZ);
									}
							}

					}

				// if (worldObj.isRemote)
				if (this.worldObj.isRemote && this.field_70279_a) // This is in
																													// EntityBoat
					{
						// if (field_9394_d > 0)
						if (this.steamBoatPosRotationIncrements > 0)
							{
								// double d1 = posX + (field_9393_e - posX)
								// / (double) field_9394_d;
								d13 = this.posX + (this.steamBoatX - this.posX)
								    / (double) this.steamBoatPosRotationIncrements;
								// double d5 = posY + (field_9392_f - posY)
								// / (double) field_9394_d;
								d15 = this.posY + (this.steamBoatY - this.posY)
								    / (double) this.steamBoatPosRotationIncrements;
								// double d9 = posZ + (field_9391_g - posZ)
								// / (double) field_9394_d;
								d9 = this.posZ + (this.steamBoatZ - this.posZ)
								    / (double) this.steamBoatPosRotationIncrements;
								// double d12;
								// for (d12 = field_9390_h - (double) rotationYaw; d12 < -180D;
								// d12 += 360D)
								// {
								// }
								// for (; d12 >= 180D; d12 -= 360D)
								// {
								// }
								d12 = MathHelper.wrapAngleTo180_double(this.steamBoatYaw
								    - (double) this.rotationYaw);
								// rotationYaw += d12 / (double) field_9394_d;
								this.rotationYaw = (float) ((double) this.rotationYaw + d12
								    / (double) this.steamBoatPosRotationIncrements);
								// rotationPitch += (field_9389_i - (double) rotationPitch)
								// / (double) field_9394_d;
								this.rotationPitch = (float) ((double) this.rotationPitch + (this.steamBoatPitch - (double) this.rotationPitch)
								    / (double) this.steamBoatPosRotationIncrements);
								// field_9394_d--;
								--this.steamBoatPosRotationIncrements;
								// setPosition(d1, d5, d9);
								setPosition(d13, d15, d9);
								setRotation(this.rotationYaw, this.rotationPitch);
							}
						else
							{
								// double d2 = posX + motionX;
								// double d6 = posY + motionY;
								// double d10 = posZ + motionZ;
								d13 = this.posX + this.motionX;
								d15 = this.posY + this.motionY;
								d9 = this.posZ + this.motionZ;
								// setPosition(d2, d6, d10);
								this.setPosition(d13, d15, d9);
								if (this.onGround)
									{
										this.motionX *= 0.5D;
										this.motionY *= 0.5D;
										this.motionZ *= 0.5D;
									}
								// motionX *= 0.99000000953674316D;
								// motionY *= 0.94999998807907104D;
								// motionZ *= 0.99000000953674316D;
								this.motionX *= 0.9900000095367432D;
								this.motionY *= 0.949999988079071D;
								this.motionZ *= 0.9900000095367432D;
							}
						// return;
					}
				/*
				 * double d3 = d * 2D - 1.0D; motionY += 0.039999999105930328D * d3; if
				 * (riddenByEntity != null) { motionX += riddenByEntity.motionX *
				 * 0.40000000000000001D; motionZ += riddenByEntity.motionZ *
				 * 0.40000000000000001D; } double d7 = 1D; if (motionX < -d7) { motionX
				 * = -d7; } if (motionX > d7) { motionX = d7; } if (motionZ < -d7) {
				 * motionZ = -d7; } if (motionZ > d7) { motionZ = d7; } if (onGround) {
				 * motionX *= 0.5D; motionY *= 0.5D; motionZ *= 0.5D; }
				 * moveEntity(motionX, motionY, motionZ);
				 * 
				 * double smoke = rand.nextFloat() * 2.0f - 1.0f; if (smoke > 0.65f) {
				 * worldObj.spawnParticle("smoke", posX, posY + 0.9D, posZ, 0.0D, 0.0D,
				 * 0.0D); }
				 * 
				 * if (isCollidedHorizontally && d11 > 0.14999999999999999D) { if
				 * (!worldObj.isRemote) { setDead();
				 * 
				 * for (int k = 0; k < 5; k++) {
				 * dropItemWithOffset(Block.planks.blockID, 1, 0.0F); }
				 * 
				 * for (int l = 0; l < 1; l++) {
				 * dropItemWithOffset(Item.ingotIron.shiftedIndex, 1, 0.0F); }
				 * 
				 * } } else { motionX *= 0.99000000953674316D; motionY *=
				 * 0.94999998807907104D; motionZ *= 0.99000000953674316D; }
				 * rotationPitch = 0.0F; double d14 = rotationYaw; double d16 = prevPosX
				 * - posX; double d17 = prevPosZ - posZ; if (d16 * d16 + d17 * d17 >
				 * 0.001D) { d14 = (float) ((Math.atan2(d17, d16) * 180D) /
				 * 3.1415926535897931D); } double d19; for (d19 = d14 - (double)
				 * rotationYaw; d19 >= 180D; d19 -= 360D) { } for (; d19 < -180D; d19 +=
				 * 360D) { } if (d19 > 30D) { d19 = 30D; } if (d19 < -30D) { d19 = -30D;
				 * } rotationYaw += d19; setRotation(rotationYaw, rotationPitch); List
				 * list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
				 * boundingBox .expand(0.20000000298023224D, 0.0D,
				 * 0.20000000298023224D)); if (list != null && list.size() > 0) { for
				 * (int j1 = 0; j1 < list.size(); j1++) { Entity entity = (Entity)
				 * list.get(j1); if (entity != riddenByEntity && entity.canBePushed() &&
				 * (entity instanceof EntitySteamBoat)) {
				 * entity.applyEntityCollision(this); } }
				 * 
				 * } if (riddenByEntity != null && riddenByEntity.isDead) {
				 * riddenByEntity = null; } }
				 */
				else
					// new else section that was added to EntityBoat
					{
						if (d < 1.0D)
							{
								d13 = d * 2.0D - 1.0D;
								this.motionY += 0.03999999910593033D * d13;
							}
						else
							{
								if (this.motionY < 0.0D)
									{
										this.motionY /= 2.0D;
									}

								this.motionY += 0.007000000216066837D;
							}

						if (this.riddenByEntity != null)
							{
								// this.motionX += this.riddenByEntity.motionX *
								// this.field_70276_b; //How fast the boat moves
								// this.motionZ += this.riddenByEntity.motionZ *
								// this.field_70276_b; // 0.07D to 0.35D
								this.motionX += this.riddenByEntity.motionX * 0.40000000000000001D;
								this.motionZ += this.riddenByEntity.motionZ * 0.40000000000000001D;
							}

						d13 = Math.sqrt(this.motionX * this.motionX + this.motionZ
						    * this.motionZ);
						// if (d13 > 0.35D)
						if (d13 > 0.40000000000000001D)
							{
								// d15 = 0.35D / d13;
								d15 = 0.40000000000000001D / d13;
								this.motionX *= d15;
								this.motionZ *= d15;
								// d13 = 0.35D;
								d13 = 0.40000000000000001D;
							}

						// if (d13 > d11 && this.field_70276_b < 0.35D)
						if (d13 > d11 && this.field_70276_b < 0.40000000000000001D)
							{
								// this.field_70276_b += (0.35D - this.field_70276_b) / 35.0D;
								this.field_70276_b += (0.40000000000000001D - this.field_70276_b) / 0.40000000000000001D;

								// if (this.field_70276_b > 0.35D)
								if (this.field_70276_b > 0.40000000000000001D)
									{
										// this.field_70276_b = 0.35D;
										this.field_70276_b = 0.40000000000000001D;
									}
							}
						else
							{
								// this.field_70276_b -= (this.field_70276_b - 0.07D) / 35.0D;
								this.field_70276_b -= (this.field_70276_b - 0.07D) / 0.40000000000000001D;

								if (this.field_70276_b < 0.07D)
									{
										this.field_70276_b = 0.07D;
									}
							}

						double d7 = 1D;
						if (this.motionX < -d7)
							{
								this.motionX = -d7;
							}
						if (this.motionX > d7)
							{
								this.motionX = d7;
							}
						if (this.motionZ < -d7)
							{
								this.motionZ = -d7;
							}
						if (this.motionZ > d7)
							{
								this.motionZ = d7;
							}
						if (this.onGround)
							{
								this.motionX *= 0.5D;
								this.motionY *= 0.5D;
								this.motionZ *= 0.5D;
							}

						this.moveEntity(this.motionX, this.motionY, this.motionZ);

						Side side = FMLCommonHandler.instance().getEffectiveSide();
						if (side == Side.CLIENT)
							{
								// We are on the client side.
								//Minecraft mc = FMLClientHandler.instance().getClient();
								double smoke = this.rand.nextFloat() * 2.0f - 1.0f;
								if (smoke > 0.65f)
									{
										this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.9D, this.posZ, 0.0D, 0.0D, 0.0D);
									}
							}
						
						// if (isCollidedHorizontally && d11 > 0.14999999999999999D)
						if (this.isCollidedHorizontally && d11 > 0.2D)
							{
								if (!this.worldObj.isRemote)
									{
										this.setDead();
										int i25;
										for (i25 = 0; i25 < 3; ++i25)
											{
												this.dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
											}

										for (i25 = 0; i25 < 2; ++i25)
											{
												this.dropItemWithOffset(Item.stick.shiftedIndex, 1,
												    0.0F);
											}

										this.dropItemWithOffset(Item.ingotIron.shiftedIndex, 1,
										    0.0F);
									}
							}
						else
							{
								// motionX *= 0.99000000953674316D;
								// motionY *= 0.94999998807907104D;
								// motionZ *= 0.99000000953674316D;
								this.motionX *= 0.9900000095367432D;
								this.motionY *= 0.949999988079071D;
								this.motionZ *= 0.9900000095367432D;
							}

						this.rotationPitch = 0.0F;
						d15 = (double) this.rotationYaw;
						d9 = this.prevPosX - this.posX;
						d12 = this.prevPosZ - this.posZ;

						if (d9 * d9 + d12 * d12 > 0.001D)
							{
								d15 = (double) ((float) (Math.atan2(d12, d9) * 180.0D / Math.PI));
							}

						double d14 = MathHelper.wrapAngleTo180_double(d15
						    - (double) this.rotationYaw);

						// if (d14 > 30.0D)
						// {
						// d14 = 30.0D;
						// }
						//
						// if (d14 < -30.0D)
						// {
						// d14 = -30.0D;
						// }
						if (d14 > 20.0D)
							{
								d14 = 20.0D;
							}

						if (d14 < -20.0D)
							{
								d14 = -20.0D;
							}

						this.rotationYaw = (float) ((double) this.rotationYaw + d14);
						this.setRotation(this.rotationYaw, this.rotationPitch);

						if (!this.worldObj.isRemote)
							{
								List li16 = this.worldObj.getEntitiesWithinAABBExcludingEntity(
								    this, this.boundingBox.expand(0.20000000298023224D, 0.0D,
								        0.20000000298023224D));

								if (li16 != null && !li16.isEmpty())
									{
										Iterator it28 = li16.iterator();

										while (it28.hasNext())
											{
												Entity en18 = (Entity) it28.next();

												if (en18 != this.riddenByEntity && en18.canBePushed()
												    && en18 instanceof EntityAirShip)
													{
														en18.applyEntityCollision(this);
													}
											}
									}

								for (int i27 = 0; i27 < 4; ++i27)
									{
										int i29 = MathHelper.floor_double(this.posX
										    + ((double) (i27 % 2) - 0.5D) * 0.8D);
										int i19 = MathHelper.floor_double(this.posZ
										    + ((double) (i27 / 2) - 0.5D) * 0.8D);

										for (int i20 = 0; i20 < 2; ++i20)
											{
												int i21 = MathHelper.floor_double(this.posY) + i20;
												int i22 = this.worldObj.getBlockId(i29, i21, i19);
												int i23 = this.worldObj.getBlockMetadata(i29, i21, i19);

												if (i22 == Block.snow.blockID)
													{
														this.worldObj.setBlockWithNotify(i29, i21, i19, 0);
													}
												else if (i22 == Block.waterlily.blockID)
													{
														Block.waterlily.dropBlockAsItemWithChance(
														    this.worldObj, i29, i21, i19, i23, 0.3F, 0);
														this.worldObj.setBlockWithNotify(i29, i21, i19, 0);
													}
											}
									}

								if (this.riddenByEntity != null && this.riddenByEntity.isDead)
									{
										this.riddenByEntity = null;
									}
							}
					}
				// End of new else section
			}

		public void updateRiderPosition()
			{
				if (riddenByEntity == null)
					{
						return;
					}
				else
					{
						// double d = Math.cos(((double) rotationYaw * 3.1415926535897931D)
						// / 180D) * 0.40000000000000002D;
						// double d1 = Math.sin(((double) rotationYaw * 3.1415926535897931D)
						// / 180D) * 0.40000000000000002D;
						double d = Math.cos(((double) this.rotationYaw * Math.PI) / 180D) * 0.40000000000000002D;
						double d1 = Math.sin(((double) this.rotationYaw * Math.PI) / 180D) * 0.40000000000000002D;
						this.riddenByEntity.setPosition(this.posX + d, this.posY
						    + getMountedYOffset() + this.riddenByEntity.getYOffset(),
						    this.posZ + d1);
						return;
					}
			}

		protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
			{
			}

		protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
			{
			}

		// public float func_392_h_()
		@SideOnly(Side.CLIENT)
		public float getShadowSize()
			{
				return 0.0F;
			}

		public boolean interact(EntityPlayer entityplayer)
			{
				if (this.riddenByEntity != null
				    && (this.riddenByEntity instanceof EntityPlayer)
				    && this.riddenByEntity != entityplayer)
					{
						return true;
					}
				if (!this.worldObj.isRemote)
					{
						entityplayer.mountEntity(this);
					}
				return true;
			}

		public void setDamageTaken(int par1)
			{
				this.dataWatcher.updateObject(19, Integer.valueOf(par1));
			}

		/**
		 * Gets the damage taken from the last hit.
		 */
		public int getDamageTaken()
			{
				return this.dataWatcher.getWatchableObjectInt(19);
			}

		/**
		 * Sets the time to count down from since the last time entity was hit.
		 */
		public void setTimeSinceHit(int par1)
			{
				this.dataWatcher.updateObject(17, Integer.valueOf(par1));
			}

		/**
		 * Gets the time since the last hit.
		 */
		public int getTimeSinceHit()
			{
				return this.dataWatcher.getWatchableObjectInt(17);
			}

		/**
		 * Sets the forward direction of the entity.
		 */
		public void setForwardDirection(int par1)
			{
				this.dataWatcher.updateObject(18, Integer.valueOf(par1));
			}

		/**
		 * Gets the forward direction of the entity.
		 */
		public int getForwardDirection()
			{
				return this.dataWatcher.getWatchableObjectInt(18);
			}

		@SideOnly(Side.CLIENT)
		public void func_70270_d(boolean p1)
			{
				this.field_70279_a = p1;
			}
		
	}

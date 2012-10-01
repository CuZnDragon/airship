package net.wyrms.airship;

import java.util.Iterator;
import java.util.Random;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.geom.Point2D;
import java.lang.reflect.*;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;

//import net.wyrms.airship.EntitySteamExplode;
//import net.wyrms.airship.EntitySteamFX;
//import net.wyrms.airship.RenderAirShip;

import org.lwjgl.input.Keyboard;

public class EntityAirShip extends Entity implements IInventory
	{
		private boolean field_70279_a;
		private double field_70276_b;
		private int airShipPosRotationIncrements;
		private double airShipX;
		private double airShipY;
		private double airShipZ;
		private double airShipYaw;
		private double airShipPitch;
		@SideOnly(Side.CLIENT)
		private double velocityX;
		@SideOnly(Side.CLIENT)
		private double velocityY;
		@SideOnly(Side.CLIENT)
		private double velocityZ;
//		public int airShipCurrentDamage;
//		public int airShipTimeSinceHit;
//		public int airShipRockDirection;
		public int PetrolFuel = 0;
		boolean hasOpened = false;
		boolean checked = false;
		boolean hasFired = false;
		boolean canBePushed = true;
		int fuelTime = 0;
		int maxProxies = 1024;
		int currentFuelTime;

		public GuiIngame chat;
		private ItemStack cargoItems[];

		private Minecraft mc;
		// private static IMinecartCollisionHandler collisionHandler = null;

		public EntityAirShip(World par1World)
			{
				super(par1World);
				this.field_70279_a = true;
				this.field_70276_b = 0.07D;
				this.preventEntitySpawning = true;
				this.setSize(1.5F, 1.7F);
				this.yOffset = height / 2.0F;

				//these are the datawatchers below...
				// this.airShipCurrentDamage = 0; //field_807_a 
				// this.airShipTimeSinceHit = 0;  //field_806_b
				// this.airShipRockDirection = 1; //field_808_c
				cargoItems = new ItemStack[36];

				// AirShip.airShipRenderID = RenderingRegistry.instance()
				// .getNextAvailableRenderId();

				mc = FMLClientHandler.instance().getClient();
			}

		/**
		 * returns if this entity triggers Block.onEntityWalking on the blocks they
		 * walk on. used for spiders and wolves to prevent them from trampling crops
		 */
		protected boolean canTriggerWalking()
			{
				return false;
			}

		protected void fall(float f)
			{

			}

		protected void entityInit()
			{
				this.dataWatcher.addObject(17, new Integer(0)); // airShipTimeSinceHit
				this.dataWatcher.addObject(18, new Integer(1)); // airShipRockDirection
				this.dataWatcher.addObject(19, new Integer(0)); // airShipCurrentDamage
			}

		/**
		 * Returns a boundingBox used to collide the entity with other entities and
		 * blocks. This enables the entity to be pushable on contact, like boats or
		 * minecarts.
		 */
		public AxisAlignedBB getCollisionBox(Entity par1Entity)
			{
				return par1Entity.boundingBox;
			}

		/**
		 * returns the bounding box for this entity
		 */
		public AxisAlignedBB getBoundingBox()
			{
				return this.boundingBox;
			}

		public boolean canBePushed()
			{
				return canBePushed;
			}

		public EntityAirShip(World world, double d, double d1, double d2)
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

		public boolean attackEntityFrom(DamageSource par1DamageSource, int i)
			{

				if (par1DamageSource.getSourceOfDamage() == null)
					{
						return true;
					}

				if (this.worldObj.isRemote || this.isDead)
					{
						return true;
					}

				double d1 = par1DamageSource.getSourceOfDamage().posX - this.posX;
				double d2 = par1DamageSource.getSourceOfDamage().posY - this.posY;
				double d3 = par1DamageSource.getSourceOfDamage().posZ - this.posZ;
				double d4 = par1DamageSource.getSourceOfDamage().posX - d1 / 2;
				double d5 = par1DamageSource.getSourceOfDamage().posY - d2 / 2;
				double d6 = par1DamageSource.getSourceOfDamage().posZ - d3 / 2;

				// mc.effectRenderer.addEffect(new EntitySteamExplode(worldObj, d4, d5,
				// d6, 0.0D, 0.0D, 0.0D));

				// this.airShipRockDirection = -this.airShipRockDirection;
				this.setForwardDirection(-this.getForwardDirection());
				// this.airShipTimeSinceHit = 1;
				this.setTimeSinceHit(10);
				// this.airShipCurrentDamage += i * 10;
				this.setDamageTaken(this.getDamageTaken() + i * 10);
				this.setBeenAttacked();

				if (par1DamageSource.getEntity() instanceof EntityPlayer
				    && ((EntityPlayer) par1DamageSource.getEntity()).capabilities.isCreativeMode)
					{
						this.setDamageTaken(600);
					}

				// if (this.airShipCurrentDamage > 300)
				if (this.getDamageTaken() > 300)
					{
						if (this.riddenByEntity != null)
							{
								this.riddenByEntity.mountEntity(this);
							}
						dropItemWithOffset(AirShip.airShip.shiftedIndex, 1, 0.0F);
						setDead();
					}
				return true;
			}

		@SideOnly(Side.CLIENT)
		public void performHurtAnimation()
			{
				// airShipRockDirection = -airShipRockDirection;
				// airShipTimeSinceHit = 1;
				// airShipCurrentDamage += airShipCurrentDamage * 2;
				this.setForwardDirection(-this.getForwardDirection());
				this.setTimeSinceHit(10);
				this.setDamageTaken(this.getDamageTaken() * 11);
			}

		public boolean canBeCollidedWith()
			{
				return !isDead;
			}

		@SideOnly(Side.CLIENT)
		public void setPositionAndRotation2(double d, double d1, double d2,
		    float f, float f1, int i)
			{
				// airShipPosRotationIncrements = i + 4;
				if (this.field_70279_a)
					{
						this.airShipPosRotationIncrements = i + 5;
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

						this.airShipPosRotationIncrements = 3;
					}

				this.airShipX = d;
				this.airShipY = d1;
				this.airShipZ = d2;
				this.airShipYaw = (double) f;
				this.airShipPitch = (double) f1;
				this.motionX = this.velocityX;
				this.motionY = this.velocityY;
				this.motionZ = this.velocityZ;
			}

		@SideOnly(Side.CLIENT)
		public void setVelocity(double d, double d1, double d2)
			{
				this.velocityX = this.motionX = d;
				this.velocityY = this.motionY = d1;
				this.velocityZ = this.motionZ = d2;
			}

		public void onUpdate()
			{
				super.onUpdate();
				boolean boost = false;

				if (fuelTime > 0)
					{
						fuelTime--;
					}

				if (!worldObj.isRemote)
					{
						if (fuelTime == 0)
							{
								if (cargoItems[13] != null)
									{
										if (cargoItems[13].getItem().shiftedIndex == Item.gunpowder.shiftedIndex)
											boost = true;
										else
											boost = false;
									}

								currentFuelTime = fuelTime = getFuelTime(cargoItems[13]);
								if (fuelTime > 0)
									{

										if (cargoItems[13] != null)
											{
												if (cargoItems[13].getItem().hasContainerItem())
													{
														cargoItems[13] = new ItemStack(cargoItems[13]
														    .getItem().getContainerItem());
													}
												else
													{
														cargoItems[13].stackSize--;
													}

												if (cargoItems[13].stackSize == 0)
													{
														cargoItems[13] = null;
													}
											}
									}
							}

					}

				if (boost)
					{
						motionX += riddenByEntity.motionX * 18D;
						motionZ += riddenByEntity.motionZ * 18D;
					}

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
						double d5 = (this.boundingBox.minY + ((this.boundingBox.maxY - this.boundingBox.minY) * (double) (j + 0))
						    / (double) i) - 0.125D;
						double d9 = (this.boundingBox.minY + ((this.boundingBox.maxY - this.boundingBox.minY) * (double) (j + 1))
						    / (double) i) - 0.125D;

						AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool()
						    .addOrModifyAABBInPool(this.boundingBox.minX, d5,
						        this.boundingBox.minZ, this.boundingBox.maxX, d9,
						        this.boundingBox.maxZ);

						if (this.worldObj.isAABBInMaterial(axisalignedbb, Material.water))
							{
								d += 1.0D / (double) i;
							}
					}

				double d11 = Math.sqrt(this.motionX * this.motionX + this.motionZ
				    * this.motionZ);

				double d9;
				double d12;
				double d13;
				double d15;

				// if (d11 > 0.14999999999999999D)
				if (d11 > 0.26249999999999996D)
					{
						d13 = Math.cos(((double) this.rotationYaw * Math.PI) / 180D);
						d15 = Math.sin(((double) this.rotationYaw * Math.PI) / 180D);

						// for (int i1 = 0; (double)i1 < 1.0D + d11 * 60D; i1++)
						for (int i1 = 0; (double) i1 < 1.0D + d11 * 60D; ++i1)
							{
								double d18 = (double) this.rand.nextFloat() * 2.0F - 1.0F;
								// double d20 = (double)(rand.nextInt(2) * 2 - 1) *
								// 0.69999999999999996D;
								double d20 = (double) (rand.nextInt(2) * 2 - 1) * 0.7D;

								double d4 = (this.boundingBox.minY + ((this.boundingBox.maxY - this.boundingBox.minY) * (double) (i1 + 0))
								    / (double) i) - 0.125D;
								double d8 = (this.boundingBox.minY + ((this.boundingBox.maxY - this.boundingBox.minY) * (double) (i1 + 1))
								    / (double) i) - 0.125D;
								AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool()
								    .addOrModifyAABBInPool(this.boundingBox.minX, d4,
								        this.boundingBox.minZ, this.boundingBox.maxX, d8,
								        this.boundingBox.maxZ);

								if (this.rand.nextBoolean())
									{
										// double d21 = (this.posX - d13 * d18 *
										// 0.80000000000000004D) + d15 * d20;
										double d21 = this.posX - d13 * d18 * 0.8D + d15 * d20;
										// double d23 = this.posZ - d15 * d18 * 0.80000000000000004D
										// - d13
										double d23 = this.posZ - d15 * d18 * 0.8D - d13 * d20;

										if (this.worldObj.isAABBInMaterial(axisalignedbb,
										    Material.water))
											{
												this.worldObj.spawnParticle("splash", d21,
												    this.posY - 0.125D, d23, this.motionX,
												    this.motionY, this.motionZ);
											}
									}
								else
									{
										// double d22 = this.posX + d13 + d15 * d18 *
										// 0.69999999999999996D;
										double d22 = this.posX + d13 + d15 * d18 * 0.7D;
										// double d24 = (this.posZ + d15) - d13 * d18 *
										// 0.69999999999999996D;
										double d24 = (this.posZ + d15) - d13 * d18 * 0.7D;
										if (this.worldObj.isAABBInMaterial(axisalignedbb,
										    Material.water))
											{
												this.worldObj.spawnParticle("splash", d22,
												    this.posY - 0.125D, d24, this.motionX,
												    this.motionY, this.motionZ);
											}
									}
							}
					}

				// if (worldObj.isRemote)
				if (this.worldObj.isRemote && this.field_70279_a) // This is in EntityBoat
					{
						if (this.airShipPosRotationIncrements > 0)
							{
								d13 = this.posX + (this.airShipX - this.posX)
								    / (double) this.airShipPosRotationIncrements;
								d15 = this.posY + (this.airShipY - this.posY)
								    / (double) this.airShipPosRotationIncrements;
								d9 = this.posZ + (this.airShipZ - this.posZ)
								    / (double) this.airShipPosRotationIncrements;
								// for (d12 = airShipYaw - (double) rotationYaw; d12 < -180D;
								// d12 += 360D)
								// {
								// }
								// for (; d12 >= 180D; d12 -= 360D)
								// {
								// }
								d12 = MathHelper.wrapAngleTo180_double(this.airShipYaw
								    - (double) this.rotationYaw);
								// rotationYaw += d12 / (double) airShipPosRotationIncrements;
								this.rotationYaw = (float) ((double) this.rotationYaw + d12
								    / (double) this.airShipPosRotationIncrements);
								// rotationPitch += (airShipPitch - (double) rotationPitch) /
								// (double) airShipPosRotationIncrements;
								this.rotationPitch = (float) ((double) this.rotationPitch + (this.airShipPitch - (double) this.rotationPitch)
								    / (double) this.airShipPosRotationIncrements);
								// airShipPosRotationIncrements--;
								--this.airShipPosRotationIncrements;
								this.setPosition(d13, d15, d9);
								this.setRotation(rotationYaw, rotationPitch);
							}
						else
							{
								d13 = this.posX + this.motionX;
								d15 = this.posY + this.motionY;
								d9 = this.posZ + this.motionZ;
								this.setPosition(d13, d15, d9);

								if (onGround)
									{
										this.motionX *= 0.5D;
										this.motionY *= 0.5D;
										this.motionZ *= 0.5D;
										this.posY += 3D;
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
				// Beginning of new else section
				else
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
//								this.motionX += this.riddenByEntity.motionX * this.field_70276_b;  //How fast the boat moves
//								this.motionZ += this.riddenByEntity.motionZ * this.field_70276_b;  // 0.07D to 0.35D
								this.motionX += this.riddenByEntity.motionX * 0.25000000000000001D;
								this.motionZ += this.riddenByEntity.motionZ * 0.25000000000000001D;

// Need to figure out server/client side of this
								if (Keyboard.isKeyDown(AirShip.KEY_UP))
									{
										this.motionY -= this.riddenByEntity.motionY * 0.04000000000000001D;
									}
// Need to figure out server/client side of this
								if (Keyboard.isKeyDown(AirShip.KEY_DOWN))
									{
										for (int j = 0; j < i; j++)
											{
												double d4 = (this.boundingBox.minY + ((this.boundingBox.maxY - this.boundingBox.minY) * (double) (j - 2))
												    / (double) i) - 0.125D;
												double d8 = (this.boundingBox.minY + ((this.boundingBox.maxY - this.boundingBox.minY) * (double) (j - 4))
												    / (double) i) - 0.125D;
												AxisAlignedBB axisalignedbb = AxisAlignedBB.getAABBPool()
												    .addOrModifyAABBInPool(this.boundingBox.minX, d4,
												        this.boundingBox.minZ, this.boundingBox.maxX, d8,
												        this.boundingBox.maxZ);
												if (!this.worldObj.isAABBInMaterial(axisalignedbb,
												    Material.water))
													{
														this.motionY += this.riddenByEntity.motionY * 0.01000000000000001D;
													}
												else
													{
														this.posY += 5D;
														this.motionY = 0;
													}
											}

									}

							}
						
						if (this.riddenByEntity == null || this.fuelTime == 0)
							{
								this.motionY -= (0.01D * 10) / 15; // Gravity :P
							}

						d13 = Math.sqrt(this.motionX * this.motionX + this.motionZ
						    * this.motionZ);

						if (d13 > 0.35D)
							{
								d15 = 0.35D / d13;
								this.motionX *= d15;
								this.motionZ *= d15;
								d13 = 0.35D;
							}

						if (d13 > d11 && this.field_70276_b < 0.35D)
							{
								this.field_70276_b += (0.35D - this.field_70276_b) / 35.0D;

								if (this.field_70276_b > 0.35D)
									{
										this.field_70276_b = 0.35D;
									}
							}
						else
							{
								this.field_70276_b -= (this.field_70276_b - 0.07D) / 35.0D;

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

//						this.worldObj.spawnParticle("steam", var15, this.posY - 0.125D, var17, this.motionX, this.motionY, this.motionZ);
						// Look at how the splash stuff was done....
						// if (AirShip.SHOW_BOILER)
						// {
						// double smoke = rand.nextFloat() * 2.0f - 1.0f;
						// if (smoke > 0.65f)
						// {
						// mc.effectRenderer.addEffect(new EntitySteamFX(worldObj, posX, posY + 0.9D, posZ, 0.0D, 0.0D, 0.0D));
						// }
						// }

//						if (isCollidedHorizontally && d11 > 0.14999999999999999D)
//						if (this.isCollidedHorizontally && d11 > 0.2D)
						if (this.isCollidedHorizontally && d11 > 0.26249999999999996D)
							{
// No damage on collision
//								if (!this.worldObj.isRemote)
//									{
//										this.setDead();
//										int i25;
//										// figure out what to drop if collision....
//										for (i25 = 0; i25 < 3; ++i25)
//											{
//												this.dropItemWithOffset(Block.planks.blockID, 1, 0.0F);
//											}
//
//										for (i25 = 0; i25 < 2; ++i25)
//											{
//												this.dropItemWithOffset(Item.stick.shiftedIndex, 1,
//												    0.0F);
//											}
//									}
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
								d15 = (double)((float) (Math.atan2(d12, d9) * 180.0D / Math.PI));
							}

						double d14 = MathHelper.wrapAngleTo180_double(d15
						    - (double) this.rotationYaw);

//						if (d14 > 30.0D)
//							{
//								d14 = 30.0D;
//							}
//
//						if (d14 < -30.0D)
//							{
//								d14 = -30.0D;
//							}
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
								// Arrow Firing?? not in EntityBoat
								if (count == 20)
									hasFired = false;

								count++;

								if (Keyboard.isKeyDown(AirShip.KEY_FIRE) && riddenByEntity != null
								    && !hasFired)
									{
										EntityPlayer player = (EntityPlayer) riddenByEntity;
										this.FireArrow(player);
										count = 0;
									}
							}
					}
				// End of new else section
			}
		// end of update

    public void updateRiderPosition()
    {
        if (this.riddenByEntity != null)
        {
// 					double d = Math.cos(((double) rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
//					double d1 = Math.sin(((double) rotationYaw * 3.1415926535897931D) / 180D) * 0.40000000000000002D;
            double d = Math.cos((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
            double d1 = Math.sin((double)this.rotationYaw * Math.PI / 180.0D) * 0.4D;
//					riddenByEntity.setPosition(posX + d, posY + getMountedYOffset() + riddenByEntity.getYOffset(), posZ + d1);
            this.riddenByEntity.setPosition(this.posX + d, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + d1);
        }
    }

		protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
			{
				// par1NBTTagCompound.setTag("Type", "Airship");
				NBTTagList var2 = new NBTTagList();

				for (int var3 = 0; var3 < this.cargoItems.length; ++var3)
					{
						if (this.cargoItems[var3] != null)
							{
								NBTTagCompound var4 = new NBTTagCompound();
								var4.setByte("Slot", (byte) var3);
								this.cargoItems[var3].writeToNBT(var4);
								var2.appendTag(var4);
							}
					}
				par1NBTTagCompound.setTag("Items", var2);
			}

		protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
			{
				NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
				this.cargoItems = new ItemStack[this.getSizeInventory()];

				for (int var3 = 0; var3 < var2.tagCount(); ++var3)
					{
						NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
						int var5 = var4.getByte("Slot") & 255;

						if (var5 >= 0 && var5 < this.cargoItems.length)
							{
								this.cargoItems[var5] = ItemStack.loadItemStackFromNBT(var4);
							}
					}
				// PetrolFuel = nbttagcompound.getShort("Fuel");
			}

		@SideOnly(Side.CLIENT)
		public float getShadowSize()
			{
				return 0.0F;
			}

		public boolean interact(EntityPlayer entityplayer)
			{
				if (riddenByEntity != null && (riddenByEntity instanceof EntityPlayer)
				    && riddenByEntity != entityplayer)
					{
						return true;
					}
				if (!worldObj.isRemote)
					{
						ItemStack itemstack = entityplayer.inventory.getCurrentItem();
						if (itemstack != null && itemstack.itemID == Item.bow.shiftedIndex)
							{
								return false;
							}
						else
							{
								entityplayer.mountEntity(this);
							}
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
		public void func_70270_d(boolean par1)
			{
				this.field_70279_a = par1;
			}
		
		public void setDead()
			{
				label0: for (int i = 0; i < getSizeInventory(); i++)
					{
						ItemStack itemstack = getStackInSlot(i);
						if (itemstack == null)
							{
								continue;
							}
						float f = rand.nextFloat() * 0.8F + 0.1F;
						float f1 = rand.nextFloat() * 0.8F + 0.1F;
						float f2 = rand.nextFloat() * 0.8F + 0.1F;
						do
							{
								if (itemstack.stackSize <= 0)
									{
										continue label0;
									}
								int j = rand.nextInt(21) + 10;
								if (j > itemstack.stackSize)
									{
										j = itemstack.stackSize;
									}
								itemstack.stackSize -= j;
								EntityItem entityitem = new EntityItem(worldObj, posX
								    + (double) f, posY + (double) f1, posZ + (double) f2,
								    new ItemStack(itemstack.itemID, j,
								        itemstack.getItemDamage()));
								float f3 = 0.05F;
								entityitem.motionX = (float) rand.nextGaussian() * f3;
								entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
								entityitem.motionZ = (float) rand.nextGaussian() * f3;
								worldObj.spawnEntityInWorld(entityitem);
							}
						while (true);
					}
/* 
 * See how the smoke particles are done so can just do the spawnparticle command
 */
				// Random random = new Random();
				// for (int i = 1; i < 30; i++)
				// {
				// if (i % 2 == 0)
				// {
				// mc.effectRenderer.addEffect(new EntitySteamExplode(worldObj,
				// posX + (random.nextInt(i) / 8), posY, posZ
				// - (random.nextInt(i) / 8), 0D, 0D, 0D));
				// mc.effectRenderer.addEffect(new EntitySteamExplode(worldObj,
				// posX + (random.nextInt(i) / 8), posY, posZ
				// + (random.nextInt(i) / 8), 0D, 0D, 0D));
				// } else
				// {
				// mc.effectRenderer.addEffect(new EntitySteamExplode(worldObj,
				// posX - (random.nextInt(i) / 8), posY, posZ
				// + (random.nextInt(i) / 8), 0D, 0D, 0D));
				// mc.effectRenderer.addEffect(new EntitySteamExplode(worldObj,
				// posX - (random.nextInt(i) / 8), posY, posZ
				// - (random.nextInt(i) / 8), 0D, 0D, 0D));
				// }
				// }

				super.setDead();
			}

		public String getInvName()
			{
				return "Airship";
			}

		public void onInventoryChanged()
			{
			}

		private int getFuelTime(ItemStack itemstack)
			{
				if (itemstack == null)
					{
						return 0;
					}
				int i = itemstack.getItem().shiftedIndex;
				if (i < 256 && Block.blocksList[i].blockMaterial == Material.wood)
					{
						return 300;
					}
				if (i == Item.stick.shiftedIndex)
					{
						return 100;
					}
				if (i == Item.coal.shiftedIndex)
					{
						return 1600;
					}
				if (i == Item.bucketLava.shiftedIndex)
					{
						return 20000;
					}
				if (i == Block.sapling.blockID)
					{
						return 100;
					}
				if (i == Item.gunpowder.shiftedIndex)
					{
						return 200;
					}
				else
					{
						return GameRegistry.getFuelValue(itemstack);
					}
			}

		public int getFuelScaled(int i)
			{
				return (fuelTime * i) / 600;
			}

		int count = 0;



		public int getSizeInventory()
			{
				return 14;
			}

		public ItemStack getStackInSlot(int i)
			{
				return cargoItems[i];
			}

		public ItemStack decrStackSize(int i, int j)
			{
				if (cargoItems[i] != null)
					{
						if (cargoItems[i].stackSize <= j)
							{
								ItemStack itemstack = cargoItems[i];
								cargoItems[i] = null;
								return itemstack;
							}
						ItemStack itemstack1 = cargoItems[i].splitStack(j);
						if (cargoItems[i].stackSize == 0)
							{
								cargoItems[i] = null;
							}
						return itemstack1;
					}
				else
					{
						return null;
					}
			}

		public void setInventorySlotContents(int i, ItemStack itemstack)
			{
				cargoItems[i] = itemstack;
				if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
					{
						itemstack.stackSize = getInventoryStackLimit();
					}
			}

		public int getInventoryStackLimit()
			{
				return 64;
			}

		public boolean canInteractWith(EntityPlayer entityplayer)
			{
				if (isDead)
					{
						return false;
					}
				return entityplayer.getDistanceSqToEntity(this) <= 64D;
			}

/*
 * Need to find a way to trigger bow sound from event and on client side only		
 */
		private void FireArrow(EntityPlayer entityplayer)
			{
				World world = this.worldObj;

				ItemStack itemstack = getStackInSlot(12);

				if (itemstack != null && hasFired == false)
					{
						if (itemstack.getItem().getItemName() == Item.arrow.getItemName())
							{
								Vec3 vec = entityplayer.getLook(1.0F);
								double d8 = 4D;
								double d1 = posX + vec.xCoord * d8;
								double d2 = posY + (double) (height / 4.0F);
								double d3 = posZ + vec.zCoord * d8;
								EntityArrow round = new EntityArrow(world, d1, d2, d3);
								this.decrStackSize(12, 1);
								// world.playSoundAtEntity(entityplayer, "random.bow", 1.0F,
								// 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
								round.setArrowHeading(vec.xCoord, vec.yCoord, vec.zCoord, 2.6F,
								    6F);
								world.spawnEntityInWorld(round);
								hasFired = true;
							}
					}

				if (itemstack != null && hasFired == false
				    && Loader.isModLoaded("mod_Arrows"))
					{
						if (itemstack.getItem().shiftedIndex == 384)
							{
								try
									{
										Class<?> Arrow = Class.forName("EntityArrowExplosive");
										Class[] args2 = new Class[]
											{ World.class, double.class, double.class, double.class };
										Class[] args3 = new Class[]
											{ double.class, double.class, double.class, float.class,
											    float.class };
										Constructor<?> constructor = Arrow.getConstructor(args2);
										Vec3 vec = entityplayer.getLook(1.0F);
										double d8 = 4D;
										double d1 = posX + vec.xCoord * d8;
										double d2 = posY + (double) (height / 4.0F);
										double d3 = posZ + vec.zCoord * d8;
										Object[] args = new Object[]
											{ world, d1, d2, d3 };
										Object arrow = constructor.newInstance(args);
										this.decrStackSize(12, 1);
										// world.playSoundAtEntity(entityplayer, "random.bow", 1.0F,
										// 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
										Object[] argshead = new Object[]
											{ vec.xCoord, vec.yCoord, vec.zCoord, 2.6F, 6F };
										Method setHeading = Arrow.getMethod("a", args3);
										setHeading.invoke(arrow, argshead);
										world.spawnEntityInWorld((Entity) arrow);
										hasFired = true;
									}
								catch (Throwable e)
									{
										System.out.println(e);
									}
							}

						if (itemstack.getItem().shiftedIndex == 385)
							{
								try
									{
										Class<?> Arrow = Class.forName("EntityArrowFire");
										Class[] args2 = new Class[]
											{ World.class, double.class, double.class, double.class };
										Class[] args3 = new Class[]
											{ double.class, double.class, double.class, float.class,
											    float.class };
										Constructor<?> constructor = Arrow.getConstructor(args2);
										Vec3 vec = entityplayer.getLook(1.0F);
										double d8 = 4D;
										double d1 = posX + vec.xCoord * d8;
										double d2 = posY + (double) (height / 4.0F);
										double d3 = posZ + vec.zCoord * d8;
										Object[] args = new Object[]
											{ world, d1, d2, d3 };
										Object arrow = constructor.newInstance(args);
										this.decrStackSize(12, 1);
										// world.playSoundAtEntity(entityplayer, "random.bow", 1.0F,
										// 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
										Object[] argshead = new Object[]
											{ vec.xCoord, vec.yCoord, vec.zCoord, 2.6F, 6F };
										Method setHeading = Arrow.getMethod("a", args3);
										setHeading.invoke(arrow, argshead);
										world.spawnEntityInWorld((Entity) arrow);
										hasFired = true;
									}
								catch (Throwable e)
									{
										System.out.println(e);
									}
							}

						if (itemstack.getItem().shiftedIndex == 386)
							{
								try
									{
										Class<?> Arrow = Class.forName("EntityArrowIce");
										Class[] args2 = new Class[]
											{ World.class, double.class, double.class, double.class };
										Class[] args3 = new Class[]
											{ double.class, double.class, double.class, float.class,
											    float.class };
										Constructor<?> constructor = Arrow.getConstructor(args2);
										Vec3 vec = entityplayer.getLook(1.0F);
										double d8 = 4D;
										double d1 = posX + vec.xCoord * d8;
										double d2 = posY + (double) (height / 4.0F);
										double d3 = posZ + vec.zCoord * d8;
										Object[] args = new Object[]
											{ world, d1, d2, d3 };
										Object arrow = constructor.newInstance(args);
										this.decrStackSize(12, 1);
										// world.playSoundAtEntity(entityplayer, "random.bow", 1.0F,
										// 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
										Object[] argshead = new Object[]
											{ vec.xCoord, vec.yCoord, vec.zCoord, 2.6F, 6F };
										Method setHeading = Arrow.getMethod("a", args3);
										setHeading.invoke(arrow, argshead);
										world.spawnEntityInWorld((Entity) arrow);
										hasFired = true;
									}
								catch (Throwable e)
									{
										System.out.println(e);
									}
							}

						if (itemstack.getItem().shiftedIndex == 387)
							{
								try
									{
										Class<?> Arrow = Class.forName("EntityArrowEgg");
										Class[] args2 = new Class[]
											{ World.class, double.class, double.class, double.class };
										Class[] args3 = new Class[]
											{ double.class, double.class, double.class, float.class,
											    float.class };
										Constructor<?> constructor = Arrow.getConstructor(args2);
										Vec3 vec = entityplayer.getLook(1.0F);
										double d8 = 4D;
										double d1 = posX + vec.xCoord * d8;
										double d2 = posY + (double) (height / 4.0F);
										double d3 = posZ + vec.zCoord * d8;
										Object[] args = new Object[]
											{ world, d1, d2, d3 };
										Object arrow = constructor.newInstance(args);
										this.decrStackSize(12, 1);
										// world.playSoundAtEntity(entityplayer, "random.bow", 1.0F,
										// 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
										Object[] argshead = new Object[]
											{ vec.xCoord, vec.yCoord, vec.zCoord, 2.6F, 6F };
										Method setHeading = Arrow.getMethod("a", args3);
										setHeading.invoke(arrow, argshead);
										world.spawnEntityInWorld((Entity) arrow);
										hasFired = true;
									}
								catch (Throwable e)
									{
										System.out.println(e);
									}
							}

						if (itemstack.getItem().shiftedIndex == 388)
							{
								try
									{
										Class<?> Arrow = Class.forName("EntityArrowLightning");
										Class[] args2 = new Class[]
											{ World.class, double.class, double.class, double.class };
										Class[] args3 = new Class[]
											{ double.class, double.class, double.class, float.class,
											    float.class };
										Constructor<?> constructor = Arrow.getConstructor(args2);
										Vec3 vec = entityplayer.getLook(1.0F);
										double d8 = 4D;
										double d1 = posX + vec.xCoord * d8;
										double d2 = posY + (double) (height / 4.0F);
										double d3 = posZ + vec.zCoord * d8;
										Object[] args = new Object[]
											{ world, d1, d2, d3 };
										Object arrow = constructor.newInstance(args);
										this.decrStackSize(12, 1);
										// world.playSoundAtEntity(entityplayer, "random.bow", 1.0F,
										// 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
										Object[] argshead = new Object[]
											{ vec.xCoord, vec.yCoord, vec.zCoord, 2.6F, 6F };
										Method setHeading = Arrow.getMethod("a", args3);
										setHeading.invoke(arrow, argshead);
										world.spawnEntityInWorld((Entity) arrow);
										hasFired = true;
									}
								catch (Throwable e)
									{
										System.out.println(e);
									}
							}
					}
			}

/*
 * Not sure if this is the correct way to do this or not for the particles
 
		
		@SideOnly(Side.CLIENT)
		public void addSteamParticles()
			{
				this.mc.effectRenderer.addEffect(new EntitySteamFX(this.worldObj, this.posX, this.posY + 0.9D, this.posZ, 0.0D, 0.0D, 0.0D));
			}
		
		@SideOnly(Side.CLIENT)
		public void addSteamExplode()
			{
			  this.mc.effectRenderer.addEffect(new EntitySteamExplode(worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D));
			}

*/		
		
		@Override
		public ItemStack getStackInSlotOnClosing(int var1)
			{
				// TODO Auto-generated method stub
				return null;
			}

		@Override
		public boolean isUseableByPlayer(EntityPlayer var1)
			{
				// TODO Auto-generated method stub
				return false;
			}

		@Override
		public void openChest()
			{
				// TODO Auto-generated method stub

			}

		@Override
		public void closeChest()
			{
				// TODO Auto-generated method stub
			}
	}

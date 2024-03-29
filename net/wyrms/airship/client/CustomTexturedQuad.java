package net.wyrms.airship.client;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.PositionTextureVertex;
import net.minecraft.src.Vec3;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
import net.minecraft.src.Tessellator;

@SideOnly(Side.CLIENT)
public class CustomTexturedQuad
	{
//		public PositionTextureVertex field_1195_a[];
		public PositionTextureVertex vertexPositions[];
//		public int field_1194_b;
		public int nVertices;
//		private boolean field_1196_c;
		private boolean invertNormal;
		private int texWidth;
		private int texHeight;

		public CustomTexturedQuad(PositionTextureVertex aPositionTextureVertex[])
			{
//				field_1194_b = 0;
				this.nVertices = 0;
//				field_1196_c = false;
				this.invertNormal = false;
//				field_1195_a = aPositionTextureVertex;
				this.vertexPositions = aPositionTextureVertex;
//				field_1194_b = aPositionTextureVertex.length;
				this.nVertices = aPositionTextureVertex.length;
			}

		public CustomTexturedQuad(PositionTextureVertex aPositionTextureVertex[],
		    int i, int j, int k, int l)
			{
				this(aPositionTextureVertex);
				float f = 0.0015625F;
				float f1 = 0.003125F;
				aPositionTextureVertex[0] = aPositionTextureVertex[0]
				    .setTexturePosition((float) k / 64F - f, (float) j / 32F + f1);
				aPositionTextureVertex[1] = aPositionTextureVertex[1]
				    .setTexturePosition((float) i / 64F + f, (float) j / 32F + f1);
				aPositionTextureVertex[2] = aPositionTextureVertex[2]
				    .setTexturePosition((float) i / 64F + f, (float) l / 32F - f1);
				aPositionTextureVertex[3] = aPositionTextureVertex[3]
				    .setTexturePosition((float) k / 64F - f, (float) l / 32F - f1);
			}

		public CustomTexturedQuad(PositionTextureVertex aPositionTextureVertex[],
		    int i, int j, int k, int l, int texWidth, int texHeight)
			{
				this(aPositionTextureVertex);
				float f = 0.0015625F;
				float f1 = 0.003125F;
				float w = (float) texWidth;
				float h = (float) texHeight;
				aPositionTextureVertex[0] = aPositionTextureVertex[0]
				    .setTexturePosition((float) k / w - f, (float) j / h + f1);
				aPositionTextureVertex[1] = aPositionTextureVertex[1]
				    .setTexturePosition((float) i / w + f, (float) j / h + f1);
				aPositionTextureVertex[2] = aPositionTextureVertex[2]
				    .setTexturePosition((float) i / w + f, (float) l / h - f1);
				aPositionTextureVertex[3] = aPositionTextureVertex[3]
				    .setTexturePosition((float) k / w - f, (float) l / h - f1);
			}

		public void func_809_a()
			{
				PositionTextureVertex aPositionTextureVertex[] = new PositionTextureVertex[this.vertexPositions.length];
				for (int i = 0; i < this.vertexPositions.length; i++)
					{
						aPositionTextureVertex[i] = this.vertexPositions[this.vertexPositions.length - i
						    - 1];
					}

				this.vertexPositions = aPositionTextureVertex;
			}

//		public void func_808_a(Tessellator tessellator, float f)
		public void draw(Tessellator tessellator, float f)
			{
				Vec3 vec3d = this.vertexPositions[1].vector3D
				    .subtract(this.vertexPositions[0].vector3D);
				Vec3 vec3d1 = this.vertexPositions[1].vector3D
				    .subtract(this.vertexPositions[2].vector3D);
				Vec3 vec3d2 = vec3d1.crossProduct(vec3d).normalize();
				tessellator.startDrawingQuads();
				if (this.invertNormal)
					{
						tessellator.setNormal(-(float) vec3d2.xCoord,
						    -(float) vec3d2.yCoord, -(float) vec3d2.zCoord);
					} else
					{
						tessellator.setNormal((float) vec3d2.xCoord, (float) vec3d2.yCoord,
						    (float) vec3d2.zCoord);
					}
				for (int i = 0; i < 4; i++)
					{
						PositionTextureVertex PositionTextureVertex = vertexPositions[i];
						tessellator.addVertexWithUV(
						    (float) PositionTextureVertex.vector3D.xCoord * f,
						    (float) PositionTextureVertex.vector3D.yCoord * f,
						    (float) PositionTextureVertex.vector3D.zCoord * f,
						    PositionTextureVertex.texturePositionX,
						    PositionTextureVertex.texturePositionY);
					}
				tessellator.draw();
			}
	}

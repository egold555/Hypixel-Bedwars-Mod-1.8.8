package org.golde.hypixelmod.feature.beds;

import org.golde.hypixelmod.HypixelMod;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityBedRenderer2 extends TileEntitySpecialRenderer<TileEntityBed2>
{
    private static final ResourceLocation[] TEXTURES;
    private ModelBed2 model = new ModelBed2();
    private int version;

    public TileEntityBedRenderer2()
    {
        this.version = this.model.getModelVersion();
    }

    @Override
    public void renderTileEntityAt(TileEntityBed2 te, double x, double y, double z, float partialTicks, int destroyStage)
    {
    	final float alpha = 1f; //fuck if i know
    	
        if (this.version != this.model.getModelVersion())
        {
            this.model = new ModelBed2();
            this.version = this.model.getModelVersion();
        }

        boolean flag = te.getWorld() != null;
        boolean flag1 = flag ? te.isHeadPiece() : true;
        
        
        EnumDyeColor enumdyecolor = te != null ? te.getColor() : EnumDyeColor.BLUE;
        int i = flag ? te.getBlockMetadata() & 3 : 0;
        
//        System.out.println("color=" + te.getColor() + " x=" + (int)x + " y=" + (int)y + " z=" + (int)z);

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            ResourceLocation resourcelocation = TEXTURES[enumdyecolor.getMetadata()];

            if (resourcelocation != null)
            {
                this.bindTexture(resourcelocation);
            }
        }

        if (flag)
        {
            this.renderPiece(flag1, x, y, z, i, alpha);
        }
        else
        {
            GlStateManager.pushMatrix();
            this.renderPiece(true, x, y, z, i, alpha);
            this.renderPiece(false, x, y, z - 1.0D, i, alpha);
            GlStateManager.popMatrix();
        }

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    private void renderPiece(boolean p_193847_1_, double x, double y, double z, int p_193847_8_, float alpha)
    {
        this.model.preparePiece(p_193847_1_);
        GlStateManager.pushMatrix();
        float f = 0.0F;
        float f1 = 0.0F;
        float f2 = 0.0F;

        if (p_193847_8_ == EnumFacing.NORTH.getHorizontalIndex())
        {
            f = 0.0F;
        }
        else if (p_193847_8_ == EnumFacing.SOUTH.getHorizontalIndex())
        {
            f = 180.0F;
            f1 = 1.0F;
            f2 = 1.0F;
        }
        else if (p_193847_8_ == EnumFacing.WEST.getHorizontalIndex())
        {
            f = -90.0F;
            f2 = 1.0F;
        }
        else if (p_193847_8_ == EnumFacing.EAST.getHorizontalIndex())
        {
            f = 90.0F;
            f1 = 1.0F;
        }

        GlStateManager.translate((float)x + f1, (float)y + 0.5625F, (float)z + f2);
        GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        this.model.render();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        GlStateManager.popMatrix();
    }

    static
    {
        EnumDyeColor[] aenumdyecolor = EnumDyeColor.values();
        TEXTURES = new ResourceLocation[aenumdyecolor.length];

        for (EnumDyeColor enumdyecolor : aenumdyecolor)
        {
            TEXTURES[enumdyecolor.getMetadata()] = new ResourceLocation(HypixelMod.MOD_ID, "textures/blocks/bed/" + enumdyecolor.getName() + ".png");
        }
    }

}

package blusunrize.immersiveengineering.client.render;

import org.lwjgl.opengl.GL11;

import blusunrize.immersiveengineering.api.IEProperties;
import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityCrusher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.model.ISmartBlockModel;

public class TileRenderCrusher extends TileEntitySpecialRenderer<TileEntityCrusher>
{
	@Override
	public void renderTileEntityAt(TileEntityCrusher te, double x, double y, double z, float partialTicks, int destroyStage)
	{
		if(te.isDummy()||!te.getWorld().isBlockLoaded(te.getPos()))
			return;
		
		final BlockRendererDispatcher blockRenderer = Minecraft.getMinecraft().getBlockRendererDispatcher();
		BlockPos blockPos = te.getPos();
		IBlockState state = getWorld().getBlockState(blockPos);
		state = state.getBlock().getActualState(state, getWorld(), blockPos);
		state = state.withProperty(IEProperties.DYNAMICRENDER, true);
		IBakedModel model = blockRenderer.getBlockModelShapes().getModelForState(state);

		boolean b = te.getEnergyStored(null)>0 && !te.isRSDisabled() && !te.processQueue.isEmpty();
		float angle = te.animation_barrelRotation+(b?18*partialTicks:0);
		
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		
		ClientUtils.bindAtlas();
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(.5, 1.5, .5);
		

		RenderHelper.disableStandardItemLighting();
		GlStateManager.blendFunc(770, 771);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		if(Minecraft.isAmbientOcclusionEnabled())
			GlStateManager.shadeModel(7425);
		else
			GlStateManager.shadeModel(7424);
		GlStateManager.translate(te.facing.getFrontOffsetX()*.5, 0, te.facing.getFrontOffsetZ()*.5);
		GlStateManager.rotate(angle, -te.facing.getFrontOffsetZ(), 0, te.facing.getFrontOffsetX());
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		worldRenderer.setTranslation( -.5-blockPos.getX(), -.5- blockPos.getY(),  -.5-blockPos.getZ());
		worldRenderer.color(255, 255, 255, 255);
		if(model instanceof ISmartBlockModel)
			model = ((ISmartBlockModel) model).handleBlockState(state);
		blockRenderer.getBlockModelRenderer().renderModel(te.getWorld(), model, state, blockPos, worldRenderer);
		worldRenderer.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		GlStateManager.rotate(-angle, -te.facing.getFrontOffsetZ(), 0, te.facing.getFrontOffsetX());
		GlStateManager.translate(te.facing.getFrontOffsetX()*-1, 0, te.facing.getFrontOffsetZ()*-1);
		GlStateManager.rotate(-angle, -te.facing.getFrontOffsetZ(), 0, te.facing.getFrontOffsetX());
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
		worldRenderer.setTranslation( -.5-blockPos.getX(), -.5- blockPos.getY(),  -.5-blockPos.getZ());
		worldRenderer.color(255, 255, 255, 255);
		if(model instanceof ISmartBlockModel)
			model = ((ISmartBlockModel) model).handleBlockState(state);
		blockRenderer.getBlockModelRenderer().renderModel(te.getWorld(), model, state, blockPos, worldRenderer);
		worldRenderer.setTranslation(0.0D, 0.0D, 0.0D);
		tessellator.draw();
		GlStateManager.rotate(angle, -te.facing.getFrontOffsetZ(), 0, te.facing.getFrontOffsetX());
		
		RenderHelper.enableStandardItemLighting();
		
		GlStateManager.popMatrix();
	}
	
}
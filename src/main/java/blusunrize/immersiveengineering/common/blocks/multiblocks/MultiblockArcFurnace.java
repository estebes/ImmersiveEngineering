package blusunrize.immersiveengineering.common.blocks.multiblocks;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.MultiblockHandler.IMultiblock;
import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.BlockTypes_MetalsAll;
import blusunrize.immersiveengineering.common.blocks.BlockTypes_MetalsIE;
import blusunrize.immersiveengineering.common.blocks.metal.BlockTypes_MetalDecoration0;
import blusunrize.immersiveengineering.common.blocks.metal.BlockTypes_MetalDecoration1;
import blusunrize.immersiveengineering.common.blocks.metal.BlockTypes_MetalMultiblock;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityArcFurnace;
import blusunrize.immersiveengineering.common.blocks.stone.BlockTypes_StoneDecoration;
import blusunrize.immersiveengineering.common.util.IEAchievements;
import blusunrize.immersiveengineering.common.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class MultiblockArcFurnace implements IMultiblock
{
	public static MultiblockArcFurnace instance = new MultiblockArcFurnace();
	static ItemStack[][][] structure = new ItemStack[5][5][5];
	static{
		for(int h=0;h<5;h++)
			for(int l=0;l<5;l++)
				for(int w=0;w<5;w++)
				{
					if(h==0)
					{
						if(l==0&&w==2)
							structure[h][l][w] = new ItemStack(Items.cauldron);
						else if(l==2&&(w==0||w==4))
							structure[h][l][w] = new ItemStack(IEContent.blockStorage,1,BlockTypes_MetalsIE.STEEL.getMeta());
						else if(l==0&&w==0)
							structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration1,1,BlockTypes_MetalDecoration1.STEEL_SCAFFOLDING_0.getMeta());
						else if(l==4&& w==2)
							structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0,1,BlockTypes_MetalDecoration0.HEAVY_ENGINEERING.getMeta());
						else if(l==4||(l>2 &&(w==0||w==4)))
							structure[h][l][w] = new ItemStack(IEContent.blockSheetmetal,1,BlockTypes_MetalsAll.STEEL.getMeta());
						else
							structure[h][l][w] = new ItemStack(IEContent.blockSheetmetalSlabs,1,BlockTypes_MetalsAll.STEEL.getMeta());
					}
					else if(h==1)
					{
						if(l==2&&(w==0||w==4))
							structure[h][l][w] = new ItemStack(IEContent.blockStorage,1,BlockTypes_MetalsIE.STEEL.getMeta());
						else if(l==0&&w==0)
							structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0,1,BlockTypes_MetalDecoration0.RS_ENGINEERING.getMeta());
						else if(l==4&&w>0&&w<4)
							structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0,1,BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta());
						else if((w==0||w==4)&&l>2)
							structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0,1,BlockTypes_MetalDecoration0.HEAVY_ENGINEERING.getMeta());
						else if(l>=2 && w>0&&w<4)
							structure[h][l][w] = new ItemStack(IEContent.blockStoneDecoration,1,BlockTypes_StoneDecoration.BLASTBRICK_REINFORCED.getMeta());
					}
					else if(h==2)
					{
						if(l==2&&(w==0||w==4))
							structure[h][l][w] = new ItemStack(IEContent.blockStorage,1,BlockTypes_MetalsIE.STEEL.getMeta());
						else if(l==4&&w>0&&w<4)
							structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0,1,BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta());
						else if(l==4)
							structure[h][l][w] = new ItemStack(IEContent.blockSheetmetal,1,BlockTypes_MetalsAll.STEEL.getMeta());
						else if(w>0&&w<4)
							structure[h][l][w] = new ItemStack(IEContent.blockStoneDecoration,1,BlockTypes_StoneDecoration.BLASTBRICK_REINFORCED.getMeta());
					}
					else if(h==3)
					{
						if(l==4&&w==2)
							structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0,1,BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta());
						else if(l==4&&(w==1||w==3))
							structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration1,1,BlockTypes_MetalDecoration1.STEEL_SCAFFOLDING_0.getMeta());
						else if(l>0&&w>0&&w<4)
							structure[h][l][w] = new ItemStack(IEContent.blockStoneDecoration,1,BlockTypes_StoneDecoration.BLASTBRICK_REINFORCED.getMeta());
					}
					else if(h==4)
					{
						if(l>1&&w==2)
							structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration0,1,BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta());
						else if(l==4&&(w==1||w==3))
							structure[h][l][w] = new ItemStack(IEContent.blockMetalDecoration1,1,BlockTypes_MetalDecoration1.STEEL_SCAFFOLDING_0.getMeta());
					}
				}
	}
	@Override
	public ItemStack[][][] getStructureManual()
	{
		return structure;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public boolean overwriteBlockRender(ItemStack stack, int iterator)
	{
		if(iterator==2)
		{
			ImmersiveEngineering.proxy.draw3DBlockCauldron();
			return true;
		}
		return false;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderFormedStructure()
	{
		return true;
	}
	@SideOnly(Side.CLIENT)
	static ItemStack renderStack;
	@Override
	@SideOnly(Side.CLIENT)
	public void renderFormedStructure()
	{
		if(renderStack==null)
			renderStack = new ItemStack(IEContent.blockMetalMultiblock,1,BlockTypes_MetalMultiblock.ARC_FURNACE.getMeta());
		GlStateManager.scale(-9,9,9);
		GlStateManager.rotate(90, 0, 1, 0);
		GlStateManager.translate(-.0625, 0, 0);
		GlStateManager.disableCull();
		ClientUtils.mc().getRenderItem().renderItem(renderStack, ItemCameraTransforms.TransformType.GUI);
		GlStateManager.enableCull();
	}
	@Override
	public float getManualScale()
	{
		return 12;
	}

	@Override
	public String getUniqueName()
	{
		return "IE:ArcFurnace";
	}

	@Override
	public boolean isBlockTrigger(IBlockState state)
	{
		return state.getBlock()==Blocks.cauldron;
	}

	@Override
	public boolean createStructure(World world, BlockPos pos, EnumFacing side, EntityPlayer player)
	{
		if(side==EnumFacing.UP||side==EnumFacing.DOWN)
			side = EnumFacing.fromAngle(player.rotationYaw);
		BlockPos startPos = pos;
		side = side.getOpposite();

		if(Utils.isOreBlockAt(world, startPos.add(0,-1,0), "scaffoldingSteel")
				&& Utils.isBlockAt(world, startPos.offset(side,2).add(0,-1,0), IEContent.blockMetalDecoration0, BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta()))
		{
			startPos = startPos.offset(side,2);
			side = side.getOpposite();
		}

		boolean mirrored = false;
		boolean b = structureCheck(world,startPos, side, mirrored);
		if(!b)
		{
			mirrored = true;
			b = structureCheck(world,startPos, side, mirrored);
		}

		if(b)
		{
			for(int l=0;l<5;l++)
				for(int w=-2;w<=2;w++)
					for(int h=0;h<5;h++)
						if(structure[h][l][w+2]!=null)
						{
							int ww = mirrored?-w:w;
							BlockPos pos2 = startPos.offset(side, l).offset(side.rotateY(), ww).add(0, h, 0);

							world.setBlockState(pos2, IEContent.blockMetalMultiblock.getStateFromMeta(BlockTypes_MetalMultiblock.ARC_FURNACE.getMeta()));
							TileEntity curr = world.getTileEntity(pos2);
							if(curr instanceof TileEntityArcFurnace)
							{
								TileEntityArcFurnace tile = (TileEntityArcFurnace)curr;
								tile.facing=side;
								tile.formed=true;
								tile.pos = h*25 + l*5 + (w+2);
								tile.offset = new int[]{(side==EnumFacing.WEST?-l+2: side==EnumFacing.EAST?l-2: side==EnumFacing.NORTH?ww: -ww),h-1,(side==EnumFacing.NORTH?-l+2: side==EnumFacing.SOUTH?l-2: side==EnumFacing.EAST?ww : -ww)};
								tile.mirrored = mirrored;
								tile.markDirty();
								world.addBlockEvent(pos2, IEContent.blockMetalMultiblock, 255, 0);
							}
						}
			player.triggerAchievement(IEAchievements.mbArcFurnace);
		}
		return b;
	}

	boolean structureCheck(World world, BlockPos startPos, EnumFacing dir, boolean mirror)
	{
		for(int l=0;l<5;l++)
			for(int w=-2;w<=2;w++)
				for(int h=0;h<5;h++)
					if(structure[h][l][w+2]!=null)
					{
						int ww = mirror?-w:w;
						BlockPos pos = startPos.offset(dir, l).offset(dir.rotateY(), ww).add(0, h, 0);

						if(world.isAirBlock(pos))
							return false;
						if(OreDictionary.itemMatches(structure[h][l][w+2], new ItemStack(Items.cauldron), true))
						{
							if(!Utils.isBlockAt(world, pos, Blocks.cauldron, OreDictionary.WILDCARD_VALUE))
								return false;
						}
						else if(OreDictionary.itemMatches(structure[h][l][w+2], new ItemStack(IEContent.blockMetalDecoration1,1,BlockTypes_MetalDecoration1.STEEL_SCAFFOLDING_0.getMeta()), true))
						{
							if(!Utils.isOreBlockAt(world, pos, "scaffoldingSteel"))
								return false;
						}
						else if(OreDictionary.itemMatches(structure[h][l][w+2], new ItemStack(IEContent.blockSheetmetal,1,BlockTypes_MetalsAll.STEEL.getMeta()), true))
						{
							if(!Utils.isOreBlockAt(world, pos, "blockSheetmetalSteel"))
								return false;
						}
						else if(OreDictionary.itemMatches(structure[h][l][w+2], new ItemStack(IEContent.blockSheetmetalSlabs,1,BlockTypes_MetalsAll.STEEL.getMeta()), true))
						{
							if(!Utils.isOreBlockAt(world, pos, "slabSheetmetalSteel"))
								return false;
						}
						else if(OreDictionary.itemMatches(structure[h][l][w+2], new ItemStack(IEContent.blockStorage,1,BlockTypes_MetalsIE.STEEL.getMeta()), true))
						{
							if(!Utils.isOreBlockAt(world, pos, "blockSteel"))
								return false;
						}
						else
						{
							Block b = Block.getBlockFromItem(structure[h][l][w+2].getItem());
							if(b!=null)
								if(!Utils.isBlockAt(world, pos, b,structure[h][l][w+2].getItemDamage()))
									return false;
						}
					}
		return true;
	}

	static final ItemStack[] materials = new ItemStack[]{
			new ItemStack(Items.cauldron),
			new ItemStack(IEContent.blockSheetmetalSlabs,14,BlockTypes_MetalsAll.STEEL.getMeta()),
			new ItemStack(IEContent.blockSheetmetal,8,BlockTypes_MetalsAll.STEEL.getMeta()),
			new ItemStack(IEContent.blockStorage,6,BlockTypes_MetalsIE.STEEL.getMeta()),
			new ItemStack(IEContent.blockMetalDecoration1,5,BlockTypes_MetalDecoration1.STEEL_SCAFFOLDING_0.getMeta()),
			new ItemStack(IEContent.blockMetalDecoration0,1,BlockTypes_MetalDecoration0.RS_ENGINEERING.getMeta()),
			new ItemStack(IEContent.blockMetalDecoration0,10,BlockTypes_MetalDecoration0.LIGHT_ENGINEERING.getMeta()),
			new ItemStack(IEContent.blockMetalDecoration0,5,BlockTypes_MetalDecoration0.HEAVY_ENGINEERING.getMeta()),
			new ItemStack(IEContent.blockStoneDecoration,27,BlockTypes_StoneDecoration.BLASTBRICK_REINFORCED.getMeta())};
	@Override
	public ItemStack[] getTotalMaterials()
	{
		return materials;
	}
}
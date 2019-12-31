package gwpp.larger_workbenches.block;

import gwpp.larger_workbenches.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import gwpp.larger_workbenches.LargerWorkbenches;

public class BlockAutoLargeWorkbench extends BlockLargeWorkbench {

	public BlockAutoLargeWorkbench() {
		super();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;
		player.openGui(LargerWorkbenches.instance, world.getBlockMetadata(x, y, z) + 16, world, x, y, z);
		return true;
	}

	public TileEntity createNewTileEntity(World world, int meta) {
		switch (meta) {
		case 0:
			return new TileEntityAutoLargeWorkbench4x4();
		case 1:
			return new TileEntityAutoLargeWorkbench5x5();
		case 2:
			return new TileEntityAutoLargeWorkbench6x6();
		case 3:
			return new TileEntityAutoLargeWorkbench7x7();
		case 4:
			return new TileEntityAutoLargeWorkbench8x8();
		case 5:
			return new TileEntityAutoLargeWorkbench9x9();
		}
		return new TileEntityAutoLargeWorkbench4x4();
	}

	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ) {
		super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
		TileEntity te = world.getTileEntity(x,y,z);
		if(te instanceof TileEntityAutoLargeWorkbench){
			((TileEntityAutoLargeWorkbench)te).onSideUpdated(tileX, tileY, tileZ);
		}
	}
}
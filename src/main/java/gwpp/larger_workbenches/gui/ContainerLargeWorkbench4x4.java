package gwpp.larger_workbenches.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import gwpp.larger_workbenches.crafting.LargeCraftingManager;
import gwpp.larger_workbenches.gui.inventory.InventoryCraftResultBase;
import gwpp.larger_workbenches.gui.inventory.InventoryCraftingBase;
import gwpp.larger_workbenches.tileentity.TileEntityLargeWorkbench4x4;

public class ContainerLargeWorkbench4x4  extends Container {

	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;

	public ContainerLargeWorkbench4x4(InventoryPlayer inventoryPlayer, World world, int x, int y, int z, TileEntityLargeWorkbench4x4 tileEntity) {
		worldObj = world;
		posX = x;
		posY = y;
		posZ = z;
		craftMatrix = new InventoryCraftingBase(this, 4, 4, tileEntity);
		craftResult = new InventoryCraftResultBase(tileEntity);
		addSlotToContainer(new SlotCrafting(inventoryPlayer.player, craftMatrix, craftResult, 0, 133, 44));
		for (int l = 0; l < 4; l++) {
			for (int i1 = 0; i1 < 4; i1++) {
				addSlotToContainer(new Slot(craftMatrix, i1 + l * 4, 21 + i1 * 18, 17 + l * 18));
			}
		}
		for (int l = 0; l < 3; l++) {
			for (int i1 = 0; i1 < 9; i1++) {
				addSlotToContainer(new Slot(inventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 102 + l * 18));
			}
		}
		for (int l = 0; l < 9; l++) {
			addSlotToContainer(new Slot(inventoryPlayer, l, 8 + l * 18, 160));
		}
		onCraftMatrixChanged(craftMatrix);
	}

	public void onCraftMatrixChanged(IInventory inventory) {
		ItemStack itemStack = LargeCraftingManager.instance.findMatchingRecipe(craftMatrix, worldObj);
		craftResult.setInventorySlotContents(0, itemStack);
	}

	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
	}

	public boolean canInteractWith(EntityPlayer player) {
		return player.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		ItemStack itemStack = null;
		Slot slot = (Slot)inventorySlots.get(slotNumber);
		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemStack1 = slot.getStack();
			itemStack = itemStack1.copy();
			if (slotNumber == 0) {
				if (!mergeItemStack(itemStack1, 17, 53, true))
					return null;
				slot.onSlotChange(itemStack1, itemStack);
			}
			else if ((slotNumber >= 17) && (slotNumber < 44)) {
				if (!mergeItemStack(itemStack1, 44, 53, false))
					return null;
			}
			else if ((slotNumber >= 44) && (slotNumber < 53)) {
				if (!mergeItemStack(itemStack1, 17, 44, false))
					return null;
			}
			else if (!mergeItemStack(itemStack1, 17, 53, false))
				return null;
			if (itemStack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();
			if (itemStack1.stackSize == itemStack.stackSize)
				return null;
			slot.onPickupFromSlot(player, itemStack1);
		}
		return itemStack;
	}
}
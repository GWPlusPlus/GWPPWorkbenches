package gwpp.larger_workbenches.plugin.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;
import gwpp.larger_workbenches.crafting.LargeCraftingManager;
import gwpp.larger_workbenches.crafting.LargeShapelessOreRecipe;
import gwpp.larger_workbenches.crafting.LargeShapelessRecipe;
import gwpp.larger_workbenches.gui.GuiAutoLargeWorkbench5x5;
import gwpp.larger_workbenches.gui.GuiLargeWorkbench5x5;

public class LargeShapelessRecipeHandler5x5 extends LargeShapelessRecipeHandlerBase {

	public class CachedLargeShapelessRecipe5x5 extends CachedLargeShapelessRecipeBase {

		public CachedLargeShapelessRecipe5x5(ItemStack output) {
			super();
			result = new PositionedStack(output, 137, 42);
		}

		public CachedLargeShapelessRecipe5x5(Object[] input, ItemStack output) {
			this(Arrays.asList(input), output);
		}

		public CachedLargeShapelessRecipe5x5(List<?> input, ItemStack output) {
			this(output);
			setIngredients(input);
		}

		public void setIngredients(List<?> items) {
			ingredients.clear();
			for (int i = 0; i < items.size(); i++) {
				PositionedStack positionedStack = new PositionedStack(items.get(i), 7 + (i % 5) * 18, 6 + (i / 5) * 18);
				positionedStack.setMaxSize(1);
				ingredients.add(positionedStack);
			}
		}
	}

	public void loadTransferRects() {
		transferRects.add(new RecipeTransferRect(new Rectangle(102, 41, 24, 18), "large_crafting.tier1"));
	}

	public List<Class<? extends GuiContainer>> getGuiClasses() {
		List<Class<? extends GuiContainer>> classes = new ArrayList();
		classes.add(GuiLargeWorkbench5x5.class);
		classes.add(GuiAutoLargeWorkbench5x5.class);
		return classes;
	}

	public String getRecipeName() {
		return StatCollector.translateToLocal("crafting.large.shapeless.tier1");
	}

	public void loadCraftingRecipes(String outputId, Object... results) {
		if (outputId.equals("large_crafting.tier1") && getClass() == LargeShapelessRecipeHandler5x5.class) {
			for (IRecipe iRecipe : LargeCraftingManager.instance.recipes) {
				CachedLargeShapelessRecipe5x5 recipe = null;
				if (iRecipe instanceof LargeShapelessRecipe && (((LargeShapelessRecipe)iRecipe).tier == -1 || ((LargeShapelessRecipe)iRecipe).tier == 1) && ((LargeShapelessRecipe)iRecipe).getRecipeSize() <= 25)
					recipe = largeShapelessRecipe((LargeShapelessRecipe)iRecipe);
				else if (iRecipe instanceof LargeShapelessOreRecipe && (((LargeShapelessOreRecipe)iRecipe).tier == -1 || ((LargeShapelessOreRecipe)iRecipe).tier == 1) && ((LargeShapelessOreRecipe)iRecipe).getRecipeSize() <= 25)
					recipe = forgeLargeShapelessRecipe((LargeShapelessOreRecipe)iRecipe);
				if (recipe == null)
					continue;
				arecipes.add(recipe);
			}
		} else
			super.loadCraftingRecipes(outputId, results);
	}

	public void loadCraftingRecipes(ItemStack result) {
		for (IRecipe iRecipe : LargeCraftingManager.instance.recipes) {
			if (NEIServerUtils.areStacksSameTypeCrafting(iRecipe.getRecipeOutput(), result)) {
				CachedLargeShapelessRecipe5x5 recipe = null;
				if (iRecipe instanceof LargeShapelessRecipe && (((LargeShapelessRecipe)iRecipe).tier == -1 || ((LargeShapelessRecipe)iRecipe).tier == 1) && ((LargeShapelessRecipe)iRecipe).getRecipeSize() <= 25)
					recipe = largeShapelessRecipe((LargeShapelessRecipe)iRecipe);
				else if (iRecipe instanceof LargeShapelessOreRecipe && (((LargeShapelessOreRecipe)iRecipe).tier == -1 || ((LargeShapelessOreRecipe)iRecipe).tier == 1) && ((LargeShapelessOreRecipe)iRecipe).getRecipeSize() <= 25)
					recipe = forgeLargeShapelessRecipe((LargeShapelessOreRecipe)iRecipe);
				if (recipe == null)
					continue;
				arecipes.add(recipe);
			}
		}
	}

	public void loadUsageRecipes(ItemStack ingredient) {
		for (IRecipe iRecipe : LargeCraftingManager.instance.recipes) {
			CachedLargeShapelessRecipe5x5 recipe = null;
			if (iRecipe instanceof LargeShapelessRecipe && (((LargeShapelessRecipe)iRecipe).tier == -1 || ((LargeShapelessRecipe)iRecipe).tier == 1) && ((LargeShapelessRecipe)iRecipe).getRecipeSize() <= 25)
				recipe = largeShapelessRecipe((LargeShapelessRecipe)iRecipe);
			else if (iRecipe instanceof LargeShapelessOreRecipe && (((LargeShapelessOreRecipe)iRecipe).tier == -1 || ((LargeShapelessOreRecipe)iRecipe).tier == 1) && ((LargeShapelessOreRecipe)iRecipe).getRecipeSize() <= 25)
				recipe = forgeLargeShapelessRecipe((LargeShapelessOreRecipe)iRecipe);
			if (recipe == null)
				continue;
			if (recipe.contains(recipe.ingredients, ingredient)) {
				recipe.setIngredientPermutation(recipe.ingredients, ingredient);
				arecipes.add(recipe);
			}
		}
	}

	private CachedLargeShapelessRecipe5x5 largeShapelessRecipe(LargeShapelessRecipe recipe) {
		if(recipe.recipeItems == null)
			return null;
		return new CachedLargeShapelessRecipe5x5(recipe.recipeItems, recipe.getRecipeOutput());
	}

	public CachedLargeShapelessRecipe5x5 forgeLargeShapelessRecipe(LargeShapelessOreRecipe recipe) {
		ArrayList<Object> items = recipe.getInput();
		for (Object item : items)
			if (item instanceof List && ((List<?>)item).isEmpty())
				return null;
		return new CachedLargeShapelessRecipe5x5(items, recipe.getRecipeOutput());
	}

	public String getOverlayIdentifier() {
		return "large_crafting.tier1";
	}

	public String getGuiTexture() {
		return "largerworkbenches:textures/gui/workbench5x5_gui.png";
	}

	public boolean hasOverlay(GuiContainer gui, Container container, int recipe) {
		return RecipeInfo.hasDefaultOverlay(gui, "large_crafting.tier1");
	}

	public void drawBackground(int recipe) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GuiDraw.changeTexture(getGuiTexture());
		GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 176, 106);
	}
}
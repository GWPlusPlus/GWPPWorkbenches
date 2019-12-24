# LargerWorkbenches
This mod adds some workbenches which can do more than 3x3 crafting.

----
## MineTweaker, CraftTweaker
### AddRecipe
**ShapedRecipe**<br>
`mods.larger_workbenches.LargeCrafting.addShaped(<output>, [[<input>, <input>...], [<input>, <input>...], [<input>, <input>...]...]);`

**only tier ShapedRecipe**<br>
**tier -1 = any tables, 0 = 4x4 table, 1 = 5x5 table, 2 = 6x6 table, 3 = 7x7 table, 4 = 8x8 table, 5 = 9x9 table**<br>
`mods.larger_workbenches.LargeCrafting.addShaped(tier, <output>, [[<input>, <input>...], [<input>, <input>...], [<input>, <input>...]...]);`

**ShapelessRecipe**<br>
`mods.larger_workbenches.LargeCrafting.addShapeless(<output>, [<input>, <input>...]);`

**only tier ShapelessRecipe**<br>
`mods.larger_workbenches.LargeCrafting.addShapeless(tier, <output>, [<input>, <input>...]);`

### RemoveRecipe
`mods.larger_workbenches.LargeCrafting.remove(<output>);`
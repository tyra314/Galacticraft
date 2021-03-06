package micdoodle8.mods.galacticraft.core.items;

import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemCheese extends ItemFood implements ISortableItem
{
    public ItemCheese(int par1, float par2, boolean par3)
    {
        super(par1, par2, par3);
        this.setUnlocalizedName("cheese_curd");
    }

    public ItemCheese(int par1, boolean par2)
    {
        this(par1, 0.6F, par2);
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        this.itemIcon = iconRegister.registerIcon("galacticraftmoon:cheese_curd");
    }*/

    @Override
    public CreativeTabs getCreativeTab()
    {
        return GalacticraftCore.galacticraftItemsTab;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return ClientProxyCore.galacticraftItem;
    }

    @Override
    public EnumSortCategoryItem getCategory(int meta)
    {
        return EnumSortCategoryItem.GENERAL;
    }
}

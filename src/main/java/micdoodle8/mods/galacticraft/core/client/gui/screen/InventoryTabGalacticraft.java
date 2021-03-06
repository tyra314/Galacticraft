package micdoodle8.mods.galacticraft.core.client.gui.screen;

import micdoodle8.mods.galacticraft.api.client.tabs.AbstractTab;
import net.minecraftforge.fml.client.FMLClientHandler;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.items.GCItems;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.proxy.ClientProxyCore;
import net.minecraft.item.ItemStack;

public class InventoryTabGalacticraft extends AbstractTab
{
    public InventoryTabGalacticraft()
    {
        super(0, 0, 0, new ItemStack(GCItems.oxMask));
    }

    @Override
    public void onTabClicked()
    {
        GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_OPEN_EXTENDED_INVENTORY, FMLClientHandler.instance().getClient().theWorld.provider.getDimensionId(), new Object[] { }));
        ClientProxyCore.playerClientHandler.onBuild(0, FMLClientHandler.instance().getClientPlayerEntity());
    }

    @Override
    public boolean shouldAddToList()
    {
        return true;
    }
}

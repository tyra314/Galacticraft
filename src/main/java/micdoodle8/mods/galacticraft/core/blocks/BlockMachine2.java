package micdoodle8.mods.galacticraft.core.blocks;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.energy.tile.TileBaseUniversalElectrical;
import micdoodle8.mods.galacticraft.core.items.IShiftDescription;
import micdoodle8.mods.galacticraft.core.items.ItemBlockDesc;
import micdoodle8.mods.galacticraft.core.tile.TileEntityCircuitFabricator;
import micdoodle8.mods.galacticraft.core.tile.TileEntityCoalGenerator;
import micdoodle8.mods.galacticraft.core.tile.TileEntityElectricIngotCompressor;
import micdoodle8.mods.galacticraft.core.tile.TileEntityOxygenStorageModule;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryBlock;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockMachine2 extends BlockTileGC implements IShiftDescription, ISortableBlock
{
    public static final int ELECTRIC_COMPRESSOR_METADATA = 0;
    public static final int CIRCUIT_FABRICATOR_METADATA = 4;
    public static final int OXYGEN_STORAGE_MODULE_METADATA = 8;

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyEnum TYPE = PropertyEnum.create("type", EnumMachineExtendedType.class);

    public enum EnumMachineExtendedType implements IStringSerializable
    {
        ELECTRIC_COMPRESSOR(0, "electric_compressor"),
        CIRCUIT_FABRICATOR(1, "circuit_fabricator"),
        OXYGEN_STORAGE(2, "oxygen_storage");

        private final int meta;
        private final String name;

        private EnumMachineExtendedType(int meta, String name)
        {
            this.meta = meta;
            this.name = name;
        }

        public int getMeta()
        {
            return this.meta;
        }

        public static EnumMachineExtendedType byMetadata(int meta)
        {
            return values()[meta];
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    /*private IIcon iconMachineSide;
    private IIcon iconInput;
    private IIcon iconOxygenInput;
    private IIcon iconOxygenOutput;

    private IIcon iconElectricCompressor;
    private IIcon iconCircuitFabricator;
    private IIcon[] iconOxygenStorageModule;*/

    public BlockMachine2(String assetName)
    {
        super(GCBlocks.machine);
        this.setHardness(1.0F);
        this.setStepSound(Block.soundTypeMetal);
        //this.setBlockTextureName(GalacticraftCore.TEXTURE_PREFIX + assetName);
        this.setUnlocalizedName(assetName);
    }

    @Override
    public CreativeTabs getCreativeTabToDisplayOn()
    {
        return GalacticraftCore.galacticraftBlocksTab;
    }

    /*@Override
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        this.blockIcon = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine");
        this.iconInput = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_input");
        this.iconOxygenInput = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_oxygen_input");
        this.iconOxygenOutput = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_oxygen_output");

        this.iconMachineSide = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "machine_side");
        this.iconElectricCompressor = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "electric_compressor");
        this.iconCircuitFabricator = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "circuit_fabricator");
        this.iconOxygenStorageModule = new IIcon[17];

        for (int i = 0; i < this.iconOxygenStorageModule.length; i++)
        {
            this.iconOxygenStorageModule[i] = iconRegister.registerIcon(GalacticraftCore.TEXTURE_PREFIX + "oxygenStorageModule_" + i);
        }
    }*/

    @Override
    public int getRenderType()
    {
//        return GalacticraftCore.proxy.getBlockRender(this);
        return 3;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        TileEntity tile = worldIn.getTileEntity(pos);

        if (tile instanceof TileEntityCoalGenerator)
        {
            TileEntityCoalGenerator tileEntity = (TileEntityCoalGenerator) tile;
            if (tileEntity.heatGJperTick > 0)
            {
                int metadata = getMetaFromState(state);
                float particlePosX = pos.getX() + 0.5F;
                float particlePosY = pos.getY() + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
                float particlePosZ = pos.getZ() + 0.5F;
                float particleSize0 = 0.52F;
                float particleSize1 = rand.nextFloat() * 0.6F - 0.3F;

                if (metadata == 3)
                {
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, particlePosX - particleSize0, particlePosY, particlePosZ + particleSize1, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, particlePosX - particleSize0, particlePosY, particlePosZ + particleSize1, 0.0D, 0.0D, 0.0D);
                }
                else if (metadata == 2)
                {
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, particlePosX + particleSize0, particlePosY, particlePosZ + particleSize1, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, particlePosX + particleSize0, particlePosY, particlePosZ + particleSize1, 0.0D, 0.0D, 0.0D);
                }
                else if (metadata == 1)
                {
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, particlePosX + particleSize1, particlePosY, particlePosZ - particleSize0, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, particlePosX + particleSize1, particlePosY, particlePosZ - particleSize0, 0.0D, 0.0D, 0.0D);
                }
                else if (metadata == 0)
                {
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, particlePosX + particleSize1, particlePosY, particlePosZ + particleSize0, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, particlePosX + particleSize1, particlePosY, particlePosZ + particleSize0, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    /*@Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata >= BlockMachine2.OXYGEN_STORAGE_MODULE_METADATA)
        {
            metadata -= BlockMachine2.OXYGEN_STORAGE_MODULE_METADATA;

            if (side == 0 || side == 1)
            {
                return this.blockIcon;
            }

            // If it is the front side
            if (side == metadata + 2)
            {
                return this.iconOxygenInput;
            }
            // If it is the back side
            else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal())
            {
                return this.iconOxygenOutput;
            }

            int oxygenLevel = 0;
            TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileEntityOxygenStorageModule)
            {
                oxygenLevel = Math.min(((TileEntityOxygenStorageModule) tile).scaledOxygenLevel, 16);
            }

            return this.iconOxygenStorageModule[oxygenLevel];
        }

        return super.getIcon(world, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 0 || side == 1)
        {
            return this.blockIcon;
        }

        if (metadata >= BlockMachine2.OXYGEN_STORAGE_MODULE_METADATA)
        {
            metadata -= BlockMachine2.OXYGEN_STORAGE_MODULE_METADATA;

            if (side == 0 || side == 1)
            {
                return this.blockIcon;
            }

            // If it is the front side
            if (side == metadata + 2)
            {
                return this.iconOxygenInput;
            }
            // If it is the back side
            else if (side == ForgeDirection.getOrientation(metadata + 2).getOpposite().ordinal())
            {
                return this.iconOxygenOutput;
            }

            return this.iconOxygenStorageModule[16];
        }
        else if (metadata >= BlockMachine2.CIRCUIT_FABRICATOR_METADATA)
        {
            metadata -= BlockMachine2.CIRCUIT_FABRICATOR_METADATA;

            if (metadata == 0 && side == 4 || metadata == 1 && side == 5 || metadata == 2 && side == 3 || metadata == 3 && side == 2)
            {
                return this.iconCircuitFabricator;
            }

            if (side == ForgeDirection.getOrientation(metadata + 2).ordinal())
            {
                return this.iconInput;
            }
        }
        else if (metadata >= BlockMachine2.ELECTRIC_COMPRESSOR_METADATA)
        {
            metadata -= BlockMachine2.ELECTRIC_COMPRESSOR_METADATA;

            if (metadata == 0 && side == 4 || metadata == 1 && side == 5 || metadata == 2 && side == 3 || metadata == 3 && side == 2)
            {
                return this.iconElectricCompressor;
            }

            if (side == ForgeDirection.getOrientation(metadata + 2).ordinal())
            {
                return this.iconInput;
            }
        }

        return this.iconMachineSide;
    }*/

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        int metadata = getMetaFromState(state);

        final int angle = MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int change = EnumFacing.getHorizontal(angle).getOpposite().getHorizontalIndex();

        if (metadata >= BlockMachine2.OXYGEN_STORAGE_MODULE_METADATA)
        {
            worldIn.setBlockState(pos, getStateFromMeta(BlockMachine2.OXYGEN_STORAGE_MODULE_METADATA + change), 3);
        }
        else if (metadata >= BlockMachine2.CIRCUIT_FABRICATOR_METADATA)
        {
            worldIn.setBlockState(pos, getStateFromMeta(BlockMachine2.CIRCUIT_FABRICATOR_METADATA + change), 3);
        }
        else if (metadata >= BlockMachine2.ELECTRIC_COMPRESSOR_METADATA)
        {
            worldIn.setBlockState(pos, getStateFromMeta(BlockMachine2.ELECTRIC_COMPRESSOR_METADATA + change), 3);
        }
    }


    @Override
    public boolean onUseWrench(World world, BlockPos pos, EntityPlayer entityPlayer, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        int metadata = getMetaFromState(world.getBlockState(pos));
        int change = world.getBlockState(pos).getValue(FACING).rotateY().getHorizontalIndex();

        world.setBlockState(pos, this.getStateFromMeta(metadata - (metadata % 4) + change), 3);

        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileBaseUniversalElectrical)
        {
            ((TileBaseUniversalElectrical) te).updateFacing();
        }

        return true;
    }

    /**
     * Called when the block is right clicked by the player
     */
    @Override
    public boolean onMachineActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entityPlayer, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            entityPlayer.openGui(GalacticraftCore.instance, -1, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        int metadata = getMetaFromState(state);
        if (metadata >= BlockMachine2.OXYGEN_STORAGE_MODULE_METADATA)
        {
            return new TileEntityOxygenStorageModule();
        }
        else if (metadata >= BlockMachine2.CIRCUIT_FABRICATOR_METADATA)
        {
            return new TileEntityCircuitFabricator();
        }
        else if (metadata >= BlockMachine2.ELECTRIC_COMPRESSOR_METADATA)
        {
            return new TileEntityElectricIngotCompressor();
        }
        else
        {
            return null;
        }
    }

    public ItemStack getElectricCompressor()
    {
        return new ItemStack(this, 1, BlockMachine2.ELECTRIC_COMPRESSOR_METADATA);
    }

    public ItemStack getCircuitFabricator()
    {
        return new ItemStack(this, 1, BlockMachine2.CIRCUIT_FABRICATOR_METADATA);
    }

    public ItemStack getOxygenStorageModule()
    {
        return new ItemStack(this, 1, BlockMachine2.OXYGEN_STORAGE_MODULE_METADATA);
    }

    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List<ItemStack> par3List)
    {
        par3List.add(this.getElectricCompressor());
        par3List.add(this.getCircuitFabricator());
        par3List.add(this.getOxygenStorageModule());
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        int metadata = getMetaFromState(state);
        if (metadata >= BlockMachine2.OXYGEN_STORAGE_MODULE_METADATA)
        {
            return BlockMachine2.OXYGEN_STORAGE_MODULE_METADATA;
        }
        else if (metadata >= BlockMachine2.CIRCUIT_FABRICATOR_METADATA)
        {
            return BlockMachine2.CIRCUIT_FABRICATOR_METADATA;
        }
        else if (metadata >= BlockMachine2.ELECTRIC_COMPRESSOR_METADATA)
        {
            return BlockMachine2.ELECTRIC_COMPRESSOR_METADATA;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos, EntityPlayer player)
    {
        int metadata = this.getDamageValue(world, pos);

        return new ItemStack(this, 1, metadata);
    }

    @Override
    public String getShiftDescription(int meta)
    {
        switch (meta)
        {
        case CIRCUIT_FABRICATOR_METADATA:
            return GCCoreUtil.translate("tile.circuit_fabricator.description");
        case ELECTRIC_COMPRESSOR_METADATA:
            return GCCoreUtil.translate("tile.compressor_electric.description");
        case OXYGEN_STORAGE_MODULE_METADATA:
            return GCCoreUtil.translate("tile.oxygen_storage_module.description");
        }
        return "";
    }

    @Override
    public boolean showDescription(int meta)
    {
        return true;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getHorizontal(meta % 4);
        EnumMachineExtendedType type = EnumMachineExtendedType.byMetadata((int)Math.floor(meta / 4.0));
        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(TYPE, type);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getHorizontalIndex() + ((EnumMachineExtendedType)state.getValue(TYPE)).getMeta() * 4;
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, FACING, TYPE);
    }

    @Override
    public EnumSortCategoryBlock getCategory(int meta)
    {
        return EnumSortCategoryBlock.MACHINE;
    }
}

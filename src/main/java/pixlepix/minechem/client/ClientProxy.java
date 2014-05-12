package pixlepix.minechem.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import pixlepix.minechem.client.render.item.*;
import pixlepix.minechem.client.render.tileentity.*;
import pixlepix.minechem.client.sound.MinechemSoundEvent;
import pixlepix.minechem.common.CommonProxy;
import pixlepix.minechem.common.MinechemBlocks;
import pixlepix.minechem.common.MinechemItems;
import pixlepix.minechem.common.tileentity.*;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        CUSTOM_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();

        MinecraftForgeClient.registerItemRenderer(MinechemItems.element.itemID, new ItemElementRenderer());
        MinecraftForgeClient.registerItemRenderer(MinechemItems.molecule.itemID, new ItemMoleculeRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.microscope.blockID].itemID, new ItemMicroscopeRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.decomposer.blockID].itemID, new ItemDecomposerRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.synthesis.blockID].itemID, new ItemSynthesisRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.blueprintProjector.blockID].itemID, new ItemBlueprintProjectorRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.chemicalStorage.blockID].itemID, new ItemChemicalStorageRenderer());
        MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.leadedChest.blockID].itemID, new ItemLeadedChestRenderer());
        //MinecraftForgeClient.registerItemRenderer(Item.itemsList[MinechemBlocks.printer.blockID].itemID, new ItemBlueprintPrinterRenderer());

        TickRegistry.registerTickHandler(new TickHandler(), Side.CLIENT);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMicroscope.class, new TileEntityMicroscopeRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDecomposer.class, new TileEntityDecomposerRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySynthesis.class, new TileEntitySynthesisRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlueprintProjector.class, new TileEntityBlueprintProjectorRenderer());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGhostBlock.class, new TileEntityGhostBlockRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChemicalStorage.class, new TileEntityChemicalStorageRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLeadedChest.class, new TileEntityLeadedChestRenderer());
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBluePrintPrinter.class, new TileEntityBluePrintPrinterRenderer());
    }

    @Override
    public void registerHooks() {
        MinecraftForge.EVENT_BUS.register(new MinechemSoundEvent());
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }

}
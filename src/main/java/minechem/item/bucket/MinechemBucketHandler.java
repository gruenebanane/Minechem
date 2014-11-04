package minechem.item.bucket;

import cpw.mods.fml.common.registry.GameRegistry;
import minechem.Minechem;
import minechem.fluid.MinechemFluid;
import minechem.fluid.MinechemFluidBlock;
import minechem.item.MinechemChemicalType;
import minechem.item.element.Element;
import minechem.item.element.ElementEnum;
import minechem.item.molecule.Molecule;
import minechem.item.molecule.MoleculeEnum;
import minechem.potion.PotionChemical;
import minechem.reference.Reference;
import minechem.tileentity.decomposer.DecomposerRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MinechemBucketHandler
{
    private static MinechemBucketHandler instance;
    public Map<MinechemFluidBlock, MinechemBucketItem> buckets = new HashMap<MinechemFluidBlock, MinechemBucketItem>();

    public static MinechemBucketHandler getInstance()
    {
        if (instance == null)
        {
            instance = new MinechemBucketHandler();
        }
        return instance;
    }

    private MinechemBucketHandler()
    {

    }

    public MinechemBucketItem getBucket(MinechemChemicalType type)
    {
        if (type != null)
        {
            for (MinechemFluidBlock block : buckets.keySet())
            {
                if (block.getFluid() instanceof MinechemFluid)
                {
                    MinechemChemicalType blockType = ((MinechemFluid) block.getFluid()).getChemical();
                    if (type == blockType) return buckets.get(block);
                }
            }
        }
        return null;
    }

    public void registerCustomMinechemBucket(MinechemFluidBlock block, MinechemChemicalType type, String prefix)
    {
        if (buckets.get(block) != null) return;

        MinechemBucketItem bucket = new MinechemBucketItem(block, block.getFluid(), type);
        GameRegistry.registerItem(bucket, Reference.ID + "Bucket." + prefix + block.getFluid().getName());
        FluidContainerRegistry.registerFluidContainer(block.getFluid(), new ItemStack(bucket), new ItemStack(Items.bucket));
        buckets.put(block, bucket);
        Minechem.PROXY.onAddBucket(bucket);
    }

    public void registerBucketRecipes()
    {
        GameRegistry.addRecipe(new MinechemBucketRecipe());
        GameRegistry.addRecipe(new MinechemBucketReverseRecipe());

        for (MinechemBucketItem bucket : buckets.values())
        {
            registerBucketDecomposerRecipe(new ItemStack(bucket), bucket.chemical);
        }
    }

    private void registerBucketDecomposerRecipe(ItemStack itemStack, MinechemChemicalType type)
    {
        ArrayList<PotionChemical> tubes = new ArrayList<PotionChemical>();
        tubes.add(new Element(ElementEnum.Fe, 48));
        if (type instanceof ElementEnum) tubes.add(new Element((ElementEnum) type, 8));
        else if (type instanceof MoleculeEnum) tubes.add(new Molecule((MoleculeEnum) type, 8));
        DecomposerRecipe.add(new DecomposerRecipe(itemStack, tubes));
    }
}

package com.chyzman.ctft.classes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class CustomFishingRodItem extends FishingRodItem {
    public boolean isfoil;
    public String texturetype;

    public CustomFishingRodItem(boolean isFoil, String textureType, Settings settings) {
        super(settings);
        isfoil = isFoil;
        texturetype = textureType;
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return isfoil || super.hasGlint(stack);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isSneaking() && this.isFood()) {
            ItemStack itemStack = user.getStackInHand(hand);
            if (user.canConsume(this.getFoodComponent().isAlwaysEdible())) {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(itemStack);
            } else {
                return TypedActionResult.fail(itemStack);
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public Text getName() {
        var baseitemname = (Registry.ITEM.getId(this.asItem())).getPath();
        return (new TranslatableText("ctft.item.fishing_rod_preffix")
                .append(new TranslatableText(this.texturetype + ".minecraft." + baseitemname
                        .substring(0, baseitemname
                                .lastIndexOf('_')).substring(0, baseitemname
                                .substring(0, baseitemname
                                        .lastIndexOf('_'))
                                .lastIndexOf('_'))))
                .append(new TranslatableText("ctft.item.fishing_rod_suffix")));
    }

    @Override
    public Text getName(ItemStack stack) {
        return this.getName();
    }
}

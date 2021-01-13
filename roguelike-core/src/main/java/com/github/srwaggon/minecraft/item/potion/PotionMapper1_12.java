package com.github.srwaggon.minecraft.item.potion;

import com.github.srwaggon.minecraft.item.RldItem;
import com.github.srwaggon.minecraft.item.RldItemStack;
import com.github.srwaggon.minecraft.tag.TagMapper;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionUtils;

import java.util.Optional;

import greymerk.roguelike.treasure.loot.PotionForm;
import greymerk.roguelike.treasure.loot.PotionType;

public class PotionMapper1_12 {

  public static ItemStack map(RldItemStack rldItemStack) {
    RldItem item = rldItemStack.getItem();
    ItemStack itemStack = map((Potion) item);

    mergeTags(rldItemStack, itemStack);

    return itemStack;
  }

  public static void mergeTags(RldItemStack rldItemStack, ItemStack itemStack) {
    Optional.ofNullable(rldItemStack.getTags())
        .ifPresent(compoundTag ->
            Optional.ofNullable(itemStack.getTagCompound()).orElseGet(() -> ensureNbtTags(itemStack))
                .merge(TagMapper.map(compoundTag)));
  }

  public static NBTTagCompound ensureNbtTags(ItemStack itemStack) {
    itemStack.setTagCompound(new NBTTagCompound());
    return itemStack.getTagCompound();
  }

  public static ItemStack map(Potion potion) {
    ItemPotion itemPotion = map(potion.getForm());
    ItemStack itemStack = new ItemStack(itemPotion);
    net.minecraft.potion.PotionType data = map(potion.getType(), potion.isAmplified(), potion.isExtended());
    ItemStack potionWithData = PotionUtils.addPotionToItemStack(itemStack, data);
    potion.getEffects()
        .forEach(effect ->
            addCustomEffect(potionWithData, effect.getType(), effect.getAmplification(), effect.getDuration()));
    return potionWithData;
  }

  private static void addCustomEffect(ItemStack itemStack, EffectType type, int amplifier, int duration) {
    final int ticksPerSecond = 20;
    final String CUSTOM = "CustomPotionEffects";

    NBTTagCompound tag = itemStack.getTagCompound();
    if (tag == null) {
      tag = new NBTTagCompound();
      itemStack.setTagCompound(tag);
    }


    NBTTagList effects = tag.getTagList(CUSTOM, 10);
    if (effects == null) {
      effects = new NBTTagList();
      tag.setTag(CUSTOM, effects);
    }

    NBTTagCompound toAdd = new NBTTagCompound();

    toAdd.setByte("Id", (byte) type.getEffectID());
    toAdd.setByte("Amplifier", (byte) (amplifier));
    toAdd.setInteger("Duration", duration * ticksPerSecond);
    toAdd.setBoolean("Ambient", true);

    effects.appendTag(toAdd);
    tag.setTag(CUSTOM, effects);
    itemStack.setTagCompound(tag);
  }


  public static ItemPotion map(PotionForm potionForm) {
    if (potionForm == PotionForm.REGULAR) {
      return Items.POTIONITEM;
    } else if (potionForm == PotionForm.SPLASH) {
      return Items.SPLASH_POTION;
    } else {
      return Items.LINGERING_POTION;
    }
  }

  public static net.minecraft.potion.PotionType map(PotionType effect, boolean upgrade,
      boolean extend) {

    if (effect == null) {
      return PotionTypes.AWKWARD;
    }

    switch (effect) {
      case HEALING:
        return upgrade
            ? PotionTypes.STRONG_HEALING
            : PotionTypes.HEALING;
      case HARM:
        return upgrade
            ? PotionTypes.STRONG_HARMING
            : PotionTypes.HARMING;
      case REGEN:
        return extend
            ? PotionTypes.LONG_REGENERATION
            : upgrade
                ? PotionTypes.STRONG_REGENERATION
                : PotionTypes.REGENERATION;
      case POISON:
        return extend
            ? PotionTypes.LONG_POISON
            : upgrade
                ? PotionTypes.STRONG_POISON
                : PotionTypes.POISON;
      case STRENGTH:
        return extend
            ? PotionTypes.LONG_STRENGTH
            : upgrade
                ? PotionTypes.STRONG_STRENGTH
                : PotionTypes.STRENGTH;
      case WEAKNESS:
        return extend
            ? PotionTypes.LONG_WEAKNESS
            : PotionTypes.WEAKNESS;
      case SLOWNESS:
        return extend
            ? PotionTypes.LONG_SLOWNESS
            : PotionTypes.SLOWNESS;
      case SWIFTNESS:
        return extend
            ? PotionTypes.LONG_SWIFTNESS
            : upgrade
                ? PotionTypes.STRONG_SWIFTNESS
                : PotionTypes.SWIFTNESS;
      case FIRERESIST:
        return extend
            ? PotionTypes.LONG_FIRE_RESISTANCE
            : PotionTypes.FIRE_RESISTANCE;
      default:
        return PotionTypes.AWKWARD;
    }
  }
}

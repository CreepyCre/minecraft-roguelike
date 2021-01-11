package com.github.srwaggon.roguelike.minecraft.item;

public interface RldItem {

  default RldItemStack asItemStack() {
    return new RldItemStack(this);
  }

  ItemType getItemType();

}

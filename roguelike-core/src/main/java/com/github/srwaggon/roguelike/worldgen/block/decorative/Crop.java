package com.github.srwaggon.roguelike.worldgen.block.decorative;

public enum Crop {

  CARROTS,
  COCOA,
  MELON_STEM,
  NETHER_WART,
  POTATOES,
  PUMPKIN_STEM,
  WHEAT,
  ;

  public CropBlock getBrush() {
    return CropBlock.crop().setCrop(this);
  }

}
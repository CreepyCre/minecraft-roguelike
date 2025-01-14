package com.github.srwaggon.roguelike.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import greymerk.roguelike.command.CommandContext;
import greymerk.roguelike.command.ICommandRouter;
import greymerk.roguelike.command.routes.CommandRouteRoguelike;


public class CommandRoguelike extends CommandBase {

  private ICommandRouter router;

  public CommandRoguelike() {
    this.router = new CommandRouteRoguelike();
  }

  @Nonnull
  @Override
  public String getName() {
    return "roguelike";
  }

  @Override
  public String getUsage(ICommandSender sender) {
    return null;
  }

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
    CommandContext context = new CommandContext(new CommandSender1_12(sender));
    router.execute(context, Arrays.asList(args));
  }

  @Override
  public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
    return router.getTabCompletion(Arrays.asList(args));
  }
}

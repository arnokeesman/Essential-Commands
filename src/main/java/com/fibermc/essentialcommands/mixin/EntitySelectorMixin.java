package com.fibermc.essentialcommands.mixin;

import java.util.List;

import com.fibermc.essentialcommands.EssentialCommands;
import com.fibermc.essentialcommands.playerdata.PlayerData;
import com.fibermc.essentialcommands.playerdata.PlayerDataManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.command.EntitySelector;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(EntitySelector.class)
public class EntitySelectorMixin {
    @Shadow
    @Final
    @Nullable
    private String playerName;

    @Inject(
        method = "getPlayers",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/PlayerManager;getPlayer(Ljava/lang/String;)Lnet/minecraft/server/network/ServerPlayerEntity;"),
        locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    void checkNicknames(ServerCommandSource source, CallbackInfoReturnable<List<ServerPlayerEntity>> cir) {
        if (!EssentialCommands.CONFIG.NICKNAME_ABOVE_HEAD) return;
        List<PlayerData> nicknamePlayers = PlayerDataManager.getInstance().getPlayerDataMatchingNickname(this.playerName);
        if (nicknamePlayers.size() == 1) cir.setReturnValue(nicknamePlayers.stream().map(PlayerData::getPlayer).toList());
    }
}

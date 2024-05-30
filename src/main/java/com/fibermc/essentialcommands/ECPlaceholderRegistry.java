package com.fibermc.essentialcommands;

import com.fibermc.essentialcommands.access.ServerPlayerEntityAccess;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;

import net.minecraft.util.Identifier;

final class ECPlaceholderRegistry {
    private ECPlaceholderRegistry() {}

    public static void register() {
        var namespace = EssentialCommands.MOD_ID;
        Placeholders.register(
            Identifier.of(namespace, "nickname"),
            (ctx, arg) -> {
                if (ctx.hasPlayer()) {
                    return PlaceholderResult.value(
                        ((ServerPlayerEntityAccess)ctx.player())
                            .ec$getPlayerData()
                            .getFullNickname());
                }
                return PlaceholderResult.invalid("No player!");
            }
        );

    }
}

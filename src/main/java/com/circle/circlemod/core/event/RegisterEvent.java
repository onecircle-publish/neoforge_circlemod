package com.circle.circlemod.core.event;

import com.circle.circlemod.core.CircleMod;
import com.circle.circlemod.core.payload.SyncMovementPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = CircleMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterEvent {


    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(CircleMod.MODID);
        registrar.playBidirectional(SyncMovementPayload.TYPE, SyncMovementPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(SyncMovementPayload::handle, null));
    }

}

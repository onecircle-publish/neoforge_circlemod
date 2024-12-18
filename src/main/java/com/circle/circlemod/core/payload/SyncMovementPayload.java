package com.circle.circlemod.core.payload;

import com.circle.circlemod.core.CircleMod;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.ParticleUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.joml.Vector3f;

public record SyncMovementPayload(int entityId, Vector3f center, float radius,
                                  double angle) implements CustomPacketPayload {


    public static final CustomPacketPayload.Type<SyncMovementPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CircleMod.MODID, "sync_movement"));

    public static final StreamCodec<ByteBuf, SyncMovementPayload> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, SyncMovementPayload::entityId, ByteBufCodecs.VECTOR3F, SyncMovementPayload::center, ByteBufCodecs.FLOAT, SyncMovementPayload::radius, ByteBufCodecs.DOUBLE, SyncMovementPayload::angle, SyncMovementPayload::new);


    public static void recieve(SyncMovementPayload data) {
        double angle = data.angle;
        Vector3f center = data.center;
        float radius = data.radius;

        Minecraft mc = Minecraft.getInstance();

        Entity entity = mc.level.getEntity(data.entityId);
        if (entity != null) {
            Vec3 position = entity.getPosition(1);
            float radians = (float) Math.toRadians(angle);
            float sin = Mth.sin(radians);
            float cos = Mth.cos(radians);
            Vector3f normalize = new Vector3f(sin, 0, cos).normalize(radius);

            ParticleUtils.spawnParticles(mc.level, new BlockPos((int) position.x, (int) position.y, (int) position.z), 5, 1, 1, true, ParticleTypes.WAX_ON);
            Vector3f target = center.add(normalize);
            entity.setPos(new Vec3(target));
        }
    }

    public static void handle(final SyncMovementPayload data, final IPayloadContext context) {
        // 处理网络包的方法，它将接收到的数据在服务器端的逻辑线程上w处理
        context.enqueueWork(() -> {
            recieve(data);
        });
    }


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

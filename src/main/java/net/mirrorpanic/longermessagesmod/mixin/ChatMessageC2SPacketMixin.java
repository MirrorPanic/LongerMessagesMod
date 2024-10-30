package net.mirrorpanic.longermessagesmod.mixin;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.message.LastSeenMessageList;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.time.Instant;

@Mixin(ChatMessageC2SPacket.class)
public class ChatMessageC2SPacketMixin {

    @Final
    @Shadow
    private String chatMessage;

    @Final
    @Shadow
    private Instant timestamp;

    @Final
    @Shadow
    private long salt;

    @Final
    @Shadow
    private MessageSignatureData signature;

    @Final
    @Shadow
    private LastSeenMessageList.Acknowledgment acknowledgment;

    @Inject(method = "write(Lnet/minecraft/network/PacketByteBuf;)V", at = @At("HEAD"))
    private void onWrite(PacketByteBuf buf, CallbackInfo info) {
        buf.writeString(this.chatMessage, 1024);

        buf.writeInstant(this.timestamp);
        buf.writeLong(this.salt);
        buf.writeNullable(this.signature, MessageSignatureData::write);
        this.acknowledgment.write(buf);
    }
    
    @Inject(method = "<init>(Lnet/minecraft/network/PacketByteBuf;)V", at = @At("RETURN"))
    private void onConstruct(PacketByteBuf buf, CallbackInfo info) {
        ((ChatMessageC2SPacketAccessor) this).setChatMessage(buf.readString(1024));
    }
}

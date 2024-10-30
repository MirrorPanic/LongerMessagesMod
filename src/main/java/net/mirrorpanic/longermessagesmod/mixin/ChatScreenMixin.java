package net.mirrorpanic.longermessagesmod.mixin;

import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private void increaseChatCharacterLimit(CallbackInfo info) {
        ChatScreen chatScreen = (ChatScreen) (Object) this;
        TextFieldWidget chatField = ((ChatScreenAccessor) chatScreen).getChatField();
        if (chatField != null) {
            chatField.setMaxLength(1024); // Set character limit to 1024
        }
    }
}

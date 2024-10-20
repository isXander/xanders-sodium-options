package dev.isxander.xso.utils;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DonationPrompt extends Screen {
    private static final List<Text> DONATION_PROMPT_MESSAGE = List.of(
            Text.literal("Hello!"),
            Text.empty(),
            Text.literal("It seems that you've been enjoying ")
                    .append(Text.literal("Sodium").setStyle(Style.EMPTY.withColor(2616210)))
                    .append(Text.empty().formatted(Formatting.RESET))
                    .append(Text.literal(", the powerful and open rendering optimization mod for Minecraft.")),
            Text.empty(),
            Text.literal("Mods like these are complex. They require ")
                    .append(Text.literal("thousands of hours").setStyle(Style.EMPTY.withColor(16739840)))
                    .append(Text.empty().formatted(Formatting.RESET))
                    .append(Text.literal(" of development, debugging, and tuning to create the experience that players have come to expect.")),
            Text.empty(),
            Text.literal("If you'd like to show your token of appreciation, and support the development of our mod in the process, then consider ")
                    .append(Text.literal("buying us a coffee").setStyle(Style.EMPTY.withColor(15550926)))
                    .append(Text.literal(".").formatted(Formatting.RESET)),
            Text.empty(),
            Text.literal("And thanks again for using our mod! We hope it helps you (and your computer.)"));

    private final Screen target;

    public DonationPrompt(Screen target) {
        super(Text.empty());

        this.target = target;
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 150;
        int buttonHeight = 20;
        int spacing = 10; // Space between the two buttons

        // Calculate total width of both buttons plus spacing
        int totalWidth = (buttonWidth * 2) + spacing;

        // Compute the starting x position to center both buttons
        int startX = (this.width - totalWidth) / 2;
        int yPosition = this.height - 30;

        // "Donate" button
        ButtonWidget donateButton = ButtonWidget.builder(Text.literal("Donate"), button -> {
                    // Open the donation URL in the default browser
                    client.setScreen(new ConfirmLinkScreen(confirmed -> {
                        if (confirmed) {
                            Util.getOperatingSystem().open("https://ko-fi.com/jellysquid_");
                        }
                        client.setScreen(this);
                    }, "https://ko-fi.com/jellysquid_", true));
                })
                .dimensions(startX, yPosition, buttonWidth, buttonHeight)
                .build();

        // "OK" button
        ButtonWidget okButton = ButtonWidget.builder(ScreenTexts.OK, button -> client.setScreen(target))
                .dimensions(startX + buttonWidth + spacing, yPosition, buttonWidth, buttonHeight)
                .build();

        // Add both buttons to the screen
        this.addDrawableChild(donateButton);
        this.addDrawableChild(okButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Set the maximum width for text wrapping (e.g., 80% of the screen width)
        int maxTextWidth = (int)(this.width * 0.8);

        // Wrap the text lines to fit within the maximum width
        List<OrderedText> wrappedLines = new ArrayList<>();
        for (Text line : DONATION_PROMPT_MESSAGE) {
            List<OrderedText> wrappedLine = textRenderer.wrapLines(line, maxTextWidth);
            wrappedLines.addAll(wrappedLine);
        }

        // Calculate the total height of all wrapped lines
        int lineHeight = textRenderer.fontHeight + 5; // Adjust spacing as needed
        int totalHeight = lineHeight * wrappedLines.size();

        // Compute starting x and y positions to center the text block
        int x = (this.width - maxTextWidth) / 2;
        int y = (this.height - totalHeight) / 2;

        // Render each wrapped line
        for (OrderedText line : wrappedLines) {
            context.drawText(textRenderer, line, x, y, 0xFFFFFF, false);
            y += lineHeight; // Adjust spacing as needed
        }
    }
}

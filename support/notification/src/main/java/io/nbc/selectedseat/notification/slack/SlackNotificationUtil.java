package io.nbc.selectedseat.notification.slack;

import static com.slack.api.webhook.WebhookPayloads.payload;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class SlackNotificationUtil {

    private static final Map<NotificationType, String> colors = Map.of(
        NotificationType.SUCCESS, "good",
        NotificationType.FAIL, "danger"
    );

    public static void sendMessage(
        final String url,
        final String notificationTitle,
        final String messageTitle,
        final String message,
        final NotificationType type
    ) {
        final Slack slack = Slack.getInstance();
        final Field field = generateField(messageTitle, message);
        final List<Attachment> attachments = generateAttachment(field, type);

        try {
            slack.send(url, payload(payload -> payload
                    .text(notificationTitle)
                    .attachments(attachments)
                )
            );
        } catch (IOException e) {
            // TODO: replace to custom job exception
            throw new RuntimeException(e);
        }
    }

    private static Field generateField(
        final String messageTitle,
        final String message
    ) {
        return Field.builder()
            .title(messageTitle)
            .value(message)
            .build();
    }

    @NotNull
    private static List<Attachment> generateAttachment(
        final Field field,
        final NotificationType type
    ) {
        return List.of(Attachment.builder()
            .color(colors.get(type))
            .fields(List.of(field))
            .build()
        );
    }
}

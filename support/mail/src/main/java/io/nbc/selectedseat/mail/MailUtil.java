package io.nbc.selectedseat.mail;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailUtil {

    private final AmazonSimpleEmailService amazonSimpleEmailService;
    private final TemplateEngine templateEngine;

    @Value("${email.host}")
    private String hostEmail;

    public void send(
        final String title,
        final String content,
        final String... to
    ) {
        // TODO: WIP
        String htmlContent = setContent(
            "test invitor",
            "test url",
            "test workspace",
            "test code"
        );

        SendEmailRequest emailRequest = createSendEmailRequest(title,
            htmlContent, to);

        amazonSimpleEmailService.sendEmail(emailRequest);
    }

    private String setContent(
        final String invitor,
        final String urls,
        final String workspace,
        final String code
    ) {
        Context context = new Context();
        context.setVariable("urls", urls);
        context.setVariable("invitor", invitor);
        context.setVariable("workspace", workspace);
        context.setVariable("code", code);

        return templateEngine.process("mail", context);
    }

    private SendEmailRequest createSendEmailRequest(
        final String subject,
        final String content,
        final String... to
    ) {
        return new SendEmailRequest()
            .withDestination(new Destination().withToAddresses(to))
            .withSource(hostEmail) // TODO: will change
            .withMessage(
                new Message()
                    .withSubject(
                        new Content().withCharset(StandardCharsets.UTF_8.name())
                            .withData(subject)).withBody(new Body().withHtml(
                        new Content().withCharset(StandardCharsets.UTF_8.name())
                            .withData(content)))
            );
    }
}

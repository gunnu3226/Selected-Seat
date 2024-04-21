package io.nbc.selectedseat.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@RequiredArgsConstructor
public class MailTemplateFactory {

    private final TemplateEngine templateEngine;

    public String generateAdvanceNotificationTemplate(
        final String concertName,
        final String concertDate,
        final Long reservationId,
        final String ticketNumber,
        final Integer dayBefore
    ) {

        Context context = new Context();
        context.setVariable("concertName", concertName);
        context.setVariable("concertDate", concertDate);
        context.setVariable("reservationId", reservationId);
        context.setVariable("ticketNumber", ticketNumber);
        context.setVariable("dayBefore", dayBefore);

        return templateEngine.process("concert-advance-notification.html", context);
    }
}

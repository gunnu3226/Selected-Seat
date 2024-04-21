package io.nbc.selectedseat.batch.task.reservation;

import io.nbc.selectedseat.db.core.domain.reservation.entity.ReservationDocument;
import io.nbc.selectedseat.mail.MailTemplateFactory;
import io.nbc.selectedseat.mail.MailUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationDocumentItemWriter implements
    ItemWriter<ReservationDocument> {

    private final MailUtil mailUtil;
    private final MailTemplateFactory templateFactory;

    @Override
    public void write(final Chunk<? extends ReservationDocument> chunk)
        throws Exception {
        final AtomicInteger count = new AtomicInteger();

        chunk.forEach(reservationDocument -> {
            String ticketNumber = reFormatTicketNumber(
                reservationDocument.getTicketNumber(),
                reservationDocument.getTicketRating()
            );

            int dayBefore = calculateBeforeDay(reservationDocument);

            String concertDate = reFormatConcertDate(
                reservationDocument.getConcertDate()
            );

            String template = templateFactory.generateAdvanceNotificationTemplate(
                reservationDocument.getConcertName(),
                concertDate,
                reservationDocument.getReservationId(),
                ticketNumber,
                dayBefore
            );

            mailUtil.send(
                "사전 알림 메일",
                template,
                reservationDocument.getMemberEmail()
            );

            int countValue = count.addAndGet(1);

            if (countValue % 100 == 0) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private int calculateBeforeDay(
        final ReservationDocument reservationDocument) {
        int dayOfConcertStart = reservationDocument.getConcertDate().getDayOfMonth();
        int dayOfToday = LocalDateTime.now().getDayOfMonth();
        return dayOfConcertStart - dayOfToday;
    }

    private String reFormatTicketNumber(
        final String ticketNumber,
        final String ticketRating) {
        String[] split = ticketNumber.split(":");

        return "%s석 %s행 %s열".formatted(ticketRating, split[0], split[1]);
    }

    private String reFormatConcertDate(
        final LocalDateTime concertDate
    ) {
        return concertDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

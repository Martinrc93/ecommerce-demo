package com.demo.ecommerce.application.port.in.sale.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record GetSaleByDatesCommand(
        LocalDateTime startDate,
        LocalDateTime endDate
) {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public GetSaleByDatesCommand(String startDate, String endDate) {
        this(
                LocalDateTime.parse(startDate, FORMATTER),
                LocalDateTime.parse(endDate, FORMATTER)
        );
    }
}

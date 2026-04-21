package com.demo.ecommerce.application.port.in.sale.command;

import java.util.List;
import java.util.UUID;

public record CreateSaleCommand(UUID userId, List<Item> items) {
}

package org.example.shop.cli.view;

import org.example.shop.domain.model.Order;
import org.example.shop.domain.model.OrderItem;

import java.time.format.DateTimeFormatter;

public final class OrderPrinter {
    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private OrderPrinter() {

    }

    public static void print(Order o) {
        System.out.printf("Заказ: id = %d | %s | Клиент = %d | Статус = %s | Способ оплаты = %s%n",
                o.getId(), TS.format(o.getOrderDate()),
                o.getCustomerId(), o.getStatus(), o.getPaymentMethod());

        if (o.getItems().isEmpty()) {
            System.out.println("  (нет товаров)");
            return;
        }

        double subtotal = 0.f;
        for (OrderItem it : o.getItems()) {
            double lineTotal = it.getUnitPrice() * it.getQuantity();
            subtotal = subtotal + lineTotal;
            System.out.printf("  * ID = %d | ID товара = %d | \"%s\" | Количество = %d | Стоимость = %s | Подытог = %.3f%n",
                    it.getId(), it.getProductId(), it.getProductNameSnapshot(),
                    it.getQuantity(), it.getUnitPrice(), it.getUnitPrice() * it.getQuantity());
        }
        System.out.printf("  Стоимость без доставки = %.3f\n", subtotal);
        System.out.printf("  Стоимость доставки = %.3f\n", o.getDeliveryCost());
        System.out.printf("  Итог = %.3f\n", (subtotal + o.getDeliveryCost()));
    }

    public static void printBrief(Order o) {
        System.out.printf("- ID заказа = %d ID клиента = %d Товаров = %d Статус = %s%n",
                o.getId(), o.getCustomerId(),
                o.getItems().size(), o.getStatus());
    }
}

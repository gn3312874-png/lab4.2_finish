

/**
 * Класс, представляющий онлайн-заказ с доставкой.
 *
 * @author Студент
 * @version 2.0
 */
public class OnlineOrder extends Order {
    private String zone; // "Зеленая", "Желтая", "Красная"
    private boolean hasPromoCode;
    private String paymentMethod; // "Картой", "Наличные"

    /**
     * Конструктор онлайн-заказа.
     */
    public OnlineOrder(String number, double amount, boolean isUrgent,
                       String zone, boolean hasPromoCode, String paymentMethod) {
        super(number, amount, isUrgent);
        this.zone = zone;
        this.hasPromoCode = hasPromoCode;
        this.paymentMethod = paymentMethod;
    }

    /**
     * Переопределение расчета стоимости.
     * Коэффициенты зон: Зеленая = 1.0, Желтая = 1.05 (+5%), Красная = 1.15 (+15%).
     * Промокод дает скидку 8%.
     * Оплата картой добавляет комиссию 0.5% от текущей накопленной суммы.
     */
    @Override
    public double calculateTotalCost() {
        double cost = super.calculateTotalCost();

        // Коэффициенты зон доставки
        switch (zone.toLowerCase()) {
            case "желтая": cost *= 1.05; break;
            case "красная": cost *= 1.15; break;
            case "зеленая": default: cost *= 1.00; break;
        }

        // Скидка по промокоду
        if (hasPromoCode) {
            cost *= 0.92; // Скидка 8%
        }

        // Комиссия за оплату картой
        if ("картой".equalsIgnoreCase(paymentMethod)) {
            cost *= 1.005; // Комиссия 0.5%
        }

        return cost;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder(super.getInfo());
        sb.append(String.format("Тип: Онлайн-заказ\nЗона доставки: %s\nПромокод: %s\nСпособ оплаты: %s\n",
                zone, hasPromoCode ? "Применен (Скидка 8%)" : "Нет", paymentMethod));
        return sb.toString();
    }
}

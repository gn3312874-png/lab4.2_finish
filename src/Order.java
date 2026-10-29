

/**
 * Базовый класс, представляющий общий заказ.
 *
 * @author Студент
 * @version 2.0
 */
public class Order {
    private String number;
    private double amount;
    private boolean isUrgent;

    /**
     * Конструктор класса Заказ.
     *
     * @param number   номер заказа
     * @param amount   базовая сумма заказа
     * @param isUrgent признак срочности
     */
    public Order(String number, double amount, boolean isUrgent) {
        this.number = number;
        this.amount = amount;
        this.isUrgent = isUrgent;
    }

    /**
     * Расчет итоговой стоимости с учетом срочности.
     * За срочность взимается доплата 10 рублей.
     *
     * @return итоговая стоимость заказа
     */
    public double calculateTotalCost() {
        return amount + (isUrgent ? 10.0 : 0.0);
    }

    /**
     * Формирует информацию о заказе в виде строки.
     *
     * @return строковое представление информации о заказе
     */
    public String getInfo() {
        return String.format("Заказ №%s\nБазовая сумма: %.2f руб.\nСрочный: %s\nИтоговая стоимость: %.2f руб.\n",
                number, amount, isUrgent ? "Да (+10 руб.)" : "Нет", calculateTotalCost());
    }

    // Геттеры на случай необходимости доступа к полям
    public String getNumber() { return number; }
    public double getAmount() { return amount; }
    public boolean isUrgent() { return isUrgent; }
}

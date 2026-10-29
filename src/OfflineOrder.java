

/**
 * Класс, представляющий оффлайн-заказ в магазине.
 *
 * @author Студент
 * @version 2.0
 */
public class OfflineOrder extends Order {
    private String purchaseLocation;
    private boolean hasDiscountCard;
    private boolean needsPackaging;

    /**
     * Конструктор оффлайн-заказа.
     */
    public OfflineOrder(String number, double amount, boolean isUrgent,
                        String purchaseLocation, boolean hasDiscountCard, boolean needsPackaging) {
        super(number, amount, isUrgent);
        this.purchaseLocation = purchaseLocation;
        this.hasDiscountCard = hasDiscountCard;
        this.needsPackaging = needsPackaging;
    }

    /**
     * Переопределение расчета стоимости.
     * Сначала считается базовая стоимость со срочностью.
     * При наличии дисконтной карты — скидка 3%.
     * При необходимости упаковки — стоимость увеличивается на 0.2%.
     */
    @Override
    public double calculateTotalCost() {
        double cost = super.calculateTotalCost();
        if (hasDiscountCard) {
            cost *= 0.97; // Скидка 3%
        }
        if (needsPackaging) {
            cost *= 1.002; // Наценка 0.2% за упаковку
        }
        return cost;
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder(super.getInfo());
        sb.append(String.format("Тип: Оффлайн-заказ\nМесто покупки: %s\nДисконтная карта: %s\nУпаковка: %s\n",
                purchaseLocation, hasDiscountCard ? "Есть (Скидка 3%)" : "Нет", needsPackaging ? "Нужна (+0.2%)" : "Не нужна"));
        return sb.toString();
    }
}

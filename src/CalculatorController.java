

import javafx.application.Platform;
import javafx.scene.control.*;

/**
 * Класс-контроллер для обработки событий интерфейса системы заказов.
 *
 * @author Студент
 * @version 2.0
 */
public class CalculatorController {

    // Общие элементы
    private final ComboBox<String> typeBox;
    private final TextField numField;
    private final TextField amountField;
    private final CheckBox urgentCheck;

    // Оффлайн элементы
    private final TextField txtLocation;
    private final CheckBox chkCard;
    private final CheckBox chkPack;

    // Онлайн элементы
    private final ComboBox<String> zoneBox;
    private final CheckBox chkPromo;
    private final ComboBox<String> paymentBox;

    // Вывод
    private final TextArea resultArea;

    /**
     * Конструктор со всеми элементами управления формы.
     */
    public CalculatorController(ComboBox<String> typeBox, TextField numField, TextField amountField, CheckBox urgentCheck,
                                TextField txtLocation, CheckBox chkCard, CheckBox chkPack,
                                ComboBox<String> zoneBox, CheckBox chkPromo, ComboBox<String> paymentBox,
                                TextArea resultArea) {
        this.typeBox = typeBox;
        this.numField = numField;
        this.amountField = amountField;
        this.urgentCheck = urgentCheck;
        this.txtLocation = txtLocation;
        this.chkCard = chkCard;
        this.chkPack = chkPack;
        this.zoneBox = zoneBox;
        this.chkPromo = chkPromo;
        this.paymentBox = paymentBox;
        this.resultArea = resultArea;
    }

    /**
     * Основной метод сбора данных, полиморфного создания объектов и вычисления.
     */
    public void calculate() {
        try {
            String number = numField.getText().trim();
            if (number.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Ошибка валидации", "Поле 'Номер заказа' не должно быть пустым.");
                return;
            }

            double amount = Double.parseDouble(amountField.getText().trim());
            if (amount < 0) {
                showAlert(Alert.AlertType.ERROR, "Ошибка валидации", "Сумма заказа не может быть отрицательной.");
                return;
            }

            boolean isUrgent = urgentCheck.isSelected();
            Order order;

            // Определяем, какой объект создавать на основе выбора пользователя
            if ("Оффлайн-заказ".equals(typeBox.getValue())) {
                String location = txtLocation.getText().trim();
                boolean hasCard = chkCard.isSelected();
                boolean needsPack = chkPack.isSelected();

                order = new OfflineOrder(number, amount, isUrgent, location, hasCard, needsPack);
            } else {
                String zone = zoneBox.getValue();
                boolean hasPromo = chkPromo.isSelected();
                String payment = paymentBox.getValue();

                order = new OnlineOrder(number, amount, isUrgent, zone, hasPromo, payment);
            }

            // Вывод информации с помощью переопределенного метода getInfo()
            resultArea.appendText(order.getInfo());
            resultArea.appendText("========================================\n");

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Ошибка ввода", "Пожалуйста, введите корректное числовое значение в поле 'Сумма'.");
        }
    }

    /**
     * Очищает форму для выполнения нового расчета.
     */
    public void clearFields() {
        numField.clear();
        amountField.clear();
        urgentCheck.setSelected(false);
        txtLocation.setText("Главный филиал");
        chkCard.setSelected(false);
        chkPack.setSelected(false);
        zoneBox.setValue("Зеленая");
        chkPromo.setSelected(false);
        paymentBox.setValue("Картой");
        resultArea.clear();
    }

    /**
     * Показывает окно "Об авторе".
     */
    public void showAuthor() {
        showAlert(Alert.AlertType.INFORMATION, "Об авторе",
                "Лабораторная работа №3 (на базе ЛР №2)\n" +
                        "Тема: Наследование и полиморфизм в JavaFX\n" +
                        "Вариант индивидуального задания: №9 (Заказы)\n" +
                        "Разработчик: Студент [Ваше Имя]");
    }

    /**
     * Завершает работу приложения.
     */
    public void exitApplication() {
        Platform.exit();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

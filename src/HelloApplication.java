

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // --- Главное меню ---
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Файл");
        MenuItem newItem = new MenuItem("Новый расчет");
        MenuItem exitItem = new MenuItem("Выход");
        fileMenu.getItems().addAll(newItem, new SeparatorMenuItem(), exitItem);

        Menu helpMenu = new Menu("Справка");
        MenuItem authorItem = new MenuItem("Об авторе");
        helpMenu.getItems().addAll(authorItem);
        menuBar.getMenus().addAll(fileMenu, helpMenu);
        root.setTop(menuBar);

        // --- Основная форма ввода (GridPane для выравнивания) ---
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 15;");

        // Общие поля для всех заказов
        grid.add(new Label("Тип заказа:"), 0, 0);
        ComboBox<String> typeBox = new ComboBox<>(FXCollections.observableArrayList("Оффлайн-заказ", "Онлайн-заказ"));
        typeBox.setValue("Оффлайн-заказ");
        grid.add(typeBox, 1, 0);

        grid.add(new Label("Номер заказа:"), 0, 1);
        TextField numField = new TextField();
        grid.add(numField, 1, 1);

        grid.add(new Label("Сумма (руб):"), 0, 2);
        TextField amountField = new TextField();
        grid.add(amountField, 1, 2);

        CheckBox urgentCheck = new CheckBox("Срочный заказ (+10 руб)");
        grid.add(urgentCheck, 1, 3);

        // --- Блок уникальных полей для ОФФЛАЙН-заказа ---
        Label lblLocation = new Label("Место покупки:");
        TextField txtLocation = new TextField("Главный филиал");
        CheckBox chkCard = new CheckBox("Дисконтная карта (Скидка 3%)");
        CheckBox chkPack = new CheckBox("Нужна упаковка (+0.2%)");

        VBox offlineFields = new VBox(10, lblLocation, txtLocation, chkCard, chkPack);
        grid.add(offlineFields, 0, 4, 2, 1);

        // --- Блок уникальных полей для ОНЛАЙН-заказа ---
        Label lblZone = new Label("Зона доставки:");
        ComboBox<String> zoneBox = new ComboBox<>(FXCollections.observableArrayList("Зеленая", "Желтая", "Красная"));
        zoneBox.setValue("Зеленая");
        CheckBox chkPromo = new CheckBox("Применить промокод (Скидка 8%)");
        Label lblPayment = new Label("Способ оплаты:");
        ComboBox<String> paymentBox = new ComboBox<>(FXCollections.observableArrayList("Картой", "Наличные"));
        paymentBox.setValue("Картой");

        VBox onlineFields = new VBox(10, lblZone, zoneBox, chkPromo, lblPayment, paymentBox);
        grid.add(onlineFields, 0, 5, 2, 1);

        // По умолчанию показываем оффлайн, скрываем онлайн
        onlineFields.setVisible(false);
        onlineFields.setManaged(false);

        // Логика переключения видимости полей в зависимости от ComboBox
        typeBox.setOnAction(e -> {
            boolean isOffline = "Оффлайн-заказ".equals(typeBox.getValue());
            offlineFields.setVisible(isOffline);
            offlineFields.setManaged(isOffline);
            onlineFields.setVisible(!isOffline);
            onlineFields.setManaged(!isOffline);
        });

        // --- Нижняя часть (Кнопка и Вывод) ---
        Button calcButton = new Button("Рассчитать и вывести информацию");
        calcButton.setMaxWidth(Double.MAX_VALUE);
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPromptText("Результаты расчетов отобразятся здесь...");

        VBox centerBox = new VBox(15, grid, calcButton, resultArea);
        centerBox.setStyle("-fx-padding: 10;");
        root.setCenter(centerBox);

        // --- Подключение обновленного контроллера ---
        CalculatorController controller = new CalculatorController(
                typeBox, numField, amountField, urgentCheck,
                txtLocation, chkCard, chkPack,
                zoneBox, chkPromo, paymentBox, resultArea
        );

        newItem.setOnAction(e -> controller.clearFields());
        exitItem.setOnAction(e -> controller.exitApplication());
        authorItem.setOnAction(e -> controller.showAuthor());
        calcButton.setOnAction(e -> controller.calculate());

        // --- Сцена ---
        Scene scene = new Scene(root, 480, 580);
        stage.setTitle("Учет заказов (Вариант 9) — JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

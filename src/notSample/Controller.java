/**
 * This controller class responds to what SceneBuilder creates. Ties the two together.
 *
 * @author Fernando Ramirez
 */
package notSample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

    // Added database code

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProductDatabase"; // line

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn = null;
    Statement stmt = null;

    public void connectDatabase() {
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 3: Execute a query
            stmt = conn.createStatement(); // possible bug here (soon to be used)

            // STEP 4: Clean-up environment

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function will initiate SQL to manipulate and interact with database
     *
     * @param sql SQL string argument.
     */
    public void executeSql(String sql) {
        try {
            Statement stmt = null; // initialize statement
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Everything that the user will interact with
    @FXML
    private ChoiceBox<ItemType> choiceBoxItemType;

    @FXML
    private ComboBox<Integer> comboBoxQuantitySelection;

    @FXML
    private TextArea textArea_ProductionLog;

    @FXML
    private TextField textFieldManufacturer;

    @FXML
    private TextField textFieldProductName;

    @FXML
    private ListView<Product> listView;

    @FXML
    private TableColumn<?, ?> Column1;

    @FXML
    private TableColumn<?, ?> Column2;

    @FXML
    private TableColumn<?, ?> Column3;

    @FXML
    private TableColumn<?, ?> Column4;

    @FXML
    private TableView<Product> prodTableView;

    private ObservableList<Product> productLine;
    private String name;
    private String manufacturer;
    private ItemType type;
    private int productIndex;

    /**
     * This function appends product data to the database, productDatabase. Populates database into
     * scene when mouse is clicked
     *
     * @param actionEvent This event happens when clicked by "mouseclick".
     */
    @FXML
    public void addProduct(ActionEvent actionEvent) {
        name = textFieldProductName.getText();
        manufacturer = textFieldManufacturer.getText();

        //Values of my choiceBox will be turned into an "ItemType" Value
        ItemType type = choiceBoxItemType.getValue();

        //Declaring ObservableList
        productLine = FXCollections.observableArrayList();

        //What eah column will hold
        Column1.setCellValueFactory(new PropertyValueFactory("id"));
        Column2.setCellValueFactory(new PropertyValueFactory("name"));
        Column3.setCellValueFactory(new PropertyValueFactory("manufacturer"));
        Column4.setCellValueFactory(new PropertyValueFactory("Type"));

        //My observable list will be displayed on my prodTableView
        prodTableView.setItems(productLine);

        productLine.add(new Widget(name, manufacturer, type));

        listView.getItems().addAll(productLine);

        System.out.println("Product Added");
    }

    /**
     * This function records the product and enters it into the production log.
     *
     * @param event This event happens when clicked by user.
     */
    @FXML
    void recordProduction(MouseEvent event) {

        //Selecting the quantity and displaying it that amount of times
        int amount = Integer.parseInt(String.valueOf(comboBoxQuantitySelection.getSelectionModel().getSelectedItem()));
        for (int i = 0; i < amount; i++) {
            textArea_ProductionLog.appendText(String.valueOf(listView.getSelectionModel().getSelectedItem()) + "\n");
        }
    }

    /**
     * This function responds to the click on the comboBox_chooseQuantity button to let the user
     * choose the quantity.
     */
    @FXML
    public void initialize() {

        // populates comboBox
        ObservableList<Integer> data = comboBoxQuantitySelection.getItems();
        for (int i = 1; i <= 10; i++) {
            data.add(i);
        }
        comboBoxQuantitySelection.getSelectionModel().selectFirst();
        comboBoxQuantitySelection.setEditable(true);

        choiceBoxItemType.getItems().addAll(ItemType.values());

        /**
         * Driver function will declare database and start the program
         *
         * @param args array for the driver class
         */
        String productName = textFieldProductName.getText(); // assign input to string
        String manufacturer = textFieldManufacturer.getText(); // assign input to string
        String sql =
                "INSERT INTO Product(type, manufacturer, name) VALUES ('AUDIO' , '"
                        + manufacturer
                        + "','"
                        + productName
                        + "')";
    }

}

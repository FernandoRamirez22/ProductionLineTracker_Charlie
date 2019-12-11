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

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Fernando Ramirez
 * Date 9/15/2019
 * <p>
 * This class will create managed most of the graphic interface funtions and dictate
 * how the user will interact with the software
 */

public class Controller {
    //Everything that the user will interact with
    @FXML
    private ChoiceBox<ItemType> choiceBoxItemType;
    @FXML
    private ComboBox<String> comboBoxQuantitySelection;
    @FXML
    private TextArea textArea_ProductionLog;
    @FXML
    private TextField textFieldManufacturer;
    @FXML
    private TextField textFieldProductName;
    @FXML
    private ListView<Product> listView;
    @FXML
    private TableColumn<?, ?> Column2;
    @FXML
    private TableColumn<?, ?> Column3;
    @FXML
    private TableColumn<?, ?> Column4;
    @FXML
    private TableView<Product> prodTableView;
    @FXML
    private TextField employeeName;
    @FXML
    private TextField employeePassword;
    @FXML
    private TextArea textAreaEmployee;
    @FXML
    private TextArea recordProduction;

    //fields
    private ObservableList<Product> productLine;
    private String name;
    private String manufacturer;
    private ItemType type;
    private int productIndex = 0;
    final ObservableList<ProductionRecord> productionRecordList = FXCollections.observableArrayList();

    // Added database code
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/ProdDB";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    private Connection conn;
    private Statement stmt;

    /**
     * This method will call all methods from the
     * controller to initialize the program display
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        initializingComboBox();
        initializingChoiceBox();
        connectDatabase();
        ProductionLineTable();
        LoadProductionLog();
        loadProductList();
        showProduction();
    }

    /**
     * LoadProductLog method will display production record to
     * production record database
     *
     * @throws SQLException
     */
    @FXML
    private void LoadProductionLog() throws SQLException {
        String prodSql = "SELECT * FROM PRODUCTIONRECORD";
        ResultSet rs = stmt.executeQuery(prodSql);
        while (rs.next()) {
            int productionNum = rs.getInt(1);
            String productName = rs.getString(2);
            String serialNum = rs.getString(3);
            Date dateProduced = rs.getDate(4);

            ProductionRecord tempRecord =
                    new ProductionRecord(productionNum, productName, serialNum, dateProduced);
            productionRecordList.add(tempRecord);
        }
    }

    /**
     * connectDatabase method connects program to my H2 Database
     */
    public void connectDatabase() {
        try {
            // STEP 1: Register JDBC driver
            String JDBC_Driver = "org.h2.Driver";
            Class.forName(JDBC_DRIVER);
            String DB_URL = "jdbc:h2:./res/ProdDB";
            //database credentials
            String USER = "";
            String PASS = "DBPW";
            System.out.println("Connecting to database");

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // STEP 3: Execute a query
            stmt = conn.createStatement(); // possible bug here (soon to be used)
            System.out.println("Successfully connected to database");

            // STEP 4: Clean-up environment

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function appends product data to the database, productDatabase. Populates database into
     * scene when mouse is clicked
     *
     * @throws SQLException
     */
    @FXML
    public void addProduct() throws SQLException {

        String name = textFieldProductName.getText();
        String manufacturer = textFieldManufacturer.getText();
        ItemType type = choiceBoxItemType.getValue();

        if (name != null && manufacturer != null && type != null) {
            String query = "INSERT INTO PRODUCT (NAME, TYPE, MANUFACTURER) VALUES(?, ?, ?)";
            PreparedStatement prodToDB = conn.prepareStatement(query);
            prodToDB.setString(1, name);
            prodToDB.setString(2, type.toString());
            prodToDB.setString(3, manufacturer);
            prodToDB.executeUpdate();
            textFieldProductName.clear();
            textFieldManufacturer.clear();
            choiceBoxItemType.getSelectionModel().clearSelection();
            loadProductList();
        } else {
            System.out.println("Invalid Input");
        }
    }

    /**
     * ProductionLineTable method will set up table columns for my productionLine
     * observable list
     *
     * @throws SQLException if sql stateent is valid
     */
    private void ProductionLineTable() {
        productLine = FXCollections.observableArrayList();
        Column2.setCellValueFactory(new PropertyValueFactory("name"));
        Column3.setCellValueFactory(new PropertyValueFactory("manufacturer"));
        Column4.setCellValueFactory(new PropertyValueFactory("type"));
        prodTableView.setItems(productLine);
    }

    /**
     * loadProductList method will take information from
     * the database and displays it on the ProductList observable list
     *
     * @throws SQLException if sql stateent is valid
     */
    private void loadProductList() throws SQLException {
        String sql = "SELECT * FROM PRODUCT";
        if (stmt == null) {
            System.out.println("stmt is null");
        }
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            String name = rs.getString(2);
            ItemType typeFromDB = ItemType.valueOf(rs.getString(3));
            String manufacturer = rs.getString(4);


            Product productDB = new Widget(name, manufacturer, typeFromDB);
            productLine.add(productDB);
            listView.getItems().clear();
            for (Product products : productLine) {
                listView.getItems().add(products);
            }
        }
    }

    /**
     * This function records the product and enters it into the production log.
     */
    @FXML
    void recordProduction() throws SQLException {
        Product productProduced = listView.getSelectionModel().getSelectedItem();
        String quantity = String.valueOf(comboBoxQuantitySelection.getValue());
        int numProduced = Integer.parseInt(quantity);
        ArrayList<ProductionRecord> pr = new ArrayList();
        for (int productionRunProduct = 0; productionRunProduct < numProduced; productionRunProduct++) {
            ProductionRecord prodRecord = new ProductionRecord(productProduced, productionRunProduct);
            // using the iterator as the product id for testing
            pr.add(prodRecord);
        }
        productionRecordList.clear();
        addToProductionDB(pr);
        LoadProductionLog();
        showProduction();

    }

    /**
     * The addToProdcutionDB method adds record details into the production record table.
     *
     * @param prodArray receives an array list that carries record production type objects.
     * @throws SQLException Checks if the sql statement is valid.
     */
    private void addToProductionDB(ArrayList<ProductionRecord> prodArray) throws SQLException {
        String prodQuery =
                "INSERT INTO PRODUCTIONRECORD(productName, serialNum, prodDate) VALUES (?, ?, ?)";
        PreparedStatement addRecord = conn.prepareStatement(prodQuery);
        for (int i = 0; i < prodArray.size(); i++) {
            addRecord.setString(1, prodArray.get(i).getProductName());
            addRecord.setString(2, prodArray.get(i).getSerialNum());
            addRecord.setTimestamp(3, new Timestamp(prodArray.get(i).getProdDate().getTime()));
            //     addRecord.setString(4, employeeDetails.getUsername());
            addRecord.executeUpdate();
        }
    }

    /**
     * createEmployeeAccount method will creat an employee account in the database while
     * with the compiled attributes which are the employee objects
     *
     * @throws SQLException confirms that SQL statement is valid
     **/
    @FXML
    void createEmployeeAccount() throws SQLException {
        String employeeFullName = employeeName.getText();
        String employeePass = employeePassword.getText();
        Employee employeeInfo = new Employee(employeeFullName, employeePass);

        String prodQuery = "INSERT INTO EMPLOYEE (NAME, PASSWORD, USERNAME, EMAIL) VALUES (?, ?, ?, ?)";
        PreparedStatement addEmployee = conn.prepareStatement(prodQuery);
        addEmployee.setString(1, employeeFullName);
        addEmployee.setString(2, employeePass);
        addEmployee.setString(3, employeeInfo.getUsername());
        addEmployee.setString(4, employeeInfo.getEmail());
        addEmployee.executeUpdate();

        employeeName.clear();
        employeePassword.clear();
        textAreaEmployee.appendText(employeeInfo.toString() + "\n");
    }

    @FXML
    void createEmployeeBtn(ActionEvent event) throws SQLException {
        createEmployeeAccount();
    }

    /**
     * showProduction method outputs record showProduction items from the database to the production log
     * text area.
     */
    private void showProduction() {
        textArea_ProductionLog.clear();

        for (int i = 0; i < productionRecordList.size(); i++) {
            textArea_ProductionLog.appendText(productionRecordList.get(i).toString() + "\n");
        }
    }

    /**
     * This function responds to the click on the comboBox_chooseQuantity button to let the user
     * choose the quantity.
     */
    @FXML
    private void initializingComboBox() {
        comboBoxQuantitySelection.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        comboBoxQuantitySelection.getSelectionModel().selectFirst();
        comboBoxQuantitySelection.setEditable(true);

        choiceBoxItemType.getItems().addAll(ItemType.values());
    }

    /**
     * This method will populate choiceBox
     */
    @FXML
    private void initializingChoiceBox() {
        choiceBoxItemType.getItems().addAll(ItemType.values());
        choiceBoxItemType.getSelectionModel().selectFirst(); //Displays the first selection chosen by user
    }
}
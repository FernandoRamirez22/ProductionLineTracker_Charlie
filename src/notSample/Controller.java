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
 * @author Fernando Ramirez, Date 9/15/2019, This class will create managed most of the graphic interface funtions and dictate
 * how the user will interact with the software
 */

public class Controller {
    //Everything that the user will interact with
    @FXML
    private ChoiceBox<ItemType> choiceBoxItemType;
    @FXML
    private ComboBox<String> comboBoxQuantitySelection;
    @FXML
    private TextArea textAreaProductionLog;
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
    @FXML
    private TextField employeeName;
    @FXML
    private TextField employeePassword;
    @FXML
    private TextArea textAreaEmployee;
    @FXML
    private TextArea recordProduction;

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

    public void initialize() throws SQLException {
        initializingChoiceBox();
        initializingComboBox();
        connectDatabase();
        loadProductList();
        ProductionLineTable();
        showProduction();
        textArea_ProductionLog();

    }

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
     * This function will initiate SQL to manipulate and interact with database
     *
     * @param sql SQL string argument.
     */
//    public void executeSql(String sql) {
//        try {
//            Statement stmt = null; // initialize statement
//            stmt.executeUpdate(sql);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * This function appends product data to the database, productDatabase. Populates database into
     * scene when mouse is clicked
     */
    @FXML
    public void addProduct() throws SQLException {

        String name = textFieldProductName.getText();
        String manufacturer = textFieldManufacturer.getText();
        ItemType type = choiceBoxItemType.getValue();

        if (name != null && manufacturer != null && type != null) {
            String query = "INSERT PRODUCT INFO (NAME, TYPE, MANUFACTURER) VALUES(?, ?, ?)";
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
     * loadProductList method will take information from
     * the database and displays it on the ProductList observable list
     *
     * @throws SQLException if sql stateent is valid
     */
    private void ProductionLineTable() {
        productLine = FXCollections.observableArrayList();
        Column1.setCellValueFactory(new PropertyValueFactory("ID"));
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
        String sql = "SELECT * FROM PRODUCTIONRECORD";
        if (stmt == null){
            System.out.println("stmt is null");
        }
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int productNum = rs.getInt(1);
            String name = rs.getString(2);
            String serialNum = rs.getString(3);
            Date date = rs.getDate(4);


            Product productFromDB = new Widget(name, manufacturer, type);
            productFromDB.setId(productNum);
            productLine.add(productFromDB);

            listView.getItems().add(productLine.get(productIndex++));
        }
    }

    //
//        //What each column will hold
//        Column1.setCellValueFactory(new PropertyValueFactory("id"));
//        Column2.setCellValueFactory(new PropertyValueFactory("name"));
//        Column3.setCellValueFactory(new PropertyValueFactory("manufacturer"));
//        Column4.setCellValueFactory(new PropertyValueFactory("Type"));
//
//        //My observable list will be displayed on my prodTableView
//        prodTableView.setItems(productLine);
//
//        productLine.add(new Widget(name, manufacturer, type));
//
//        listView.getItems().addAll(productLine);
//
//        System.out.println("Product Added");
    @FXML
    public void textArea_ProductionLog() throws SQLException {
        Product productProduced = listView.getSelectionModel().getSelectedItem();
        //String quantity = String.valueOf(comboBoxQuantitySelection.getValue());
        int numProduced = Integer.parseInt(comboBoxQuantitySelection.getValue());
        ArrayList<ProductionRecord> pr = new ArrayList();
        for (int productionRunProduct = 0; productionRunProduct < numProduced; productionRunProduct++) {
            ProductionRecord prodRecord = new ProductionRecord(productProduced, productionRunProduct);
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
        textArea_ProductionLog();
        showProduction();

    }

    /**
     * The addToProdcutionDB adds record details into the production record table.
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
        //textAreaProductionLog.clear();

        for (int i = 0; i < productionRecordList.size(); i++) {
            textAreaProductionLog.appendText(productionRecordList.get(i).toString() + "\n");
        }
    }

    /**
     * This function responds to the click on the comboBox_chooseQuantity button to let the user
     * choose the quantity.
     */
    @FXML
    private void initializingComboBox() {

        // populates comboBox
//        ObservableList<String> data = FXCollections.observableArrayList();
//        for (int i = 1; i <= 10; i++) {
//            data.add(Integer.toString(i));
//        }


        comboBoxQuantitySelection.getItems().addAll("1","2");
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
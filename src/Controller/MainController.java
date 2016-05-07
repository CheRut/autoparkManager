package controllers;

import Interfaces.impls.CollectionServiceDataList;
import Interfaces.impls.CollectionsCarList;
import Interfaces.impls.CollectionsPartsTable;
import Objects.Automobile;
import connectionDB.DbConnection;
import controllers.*;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.ControlsFXSample;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.samples.Utils;


import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MainController {

    GregorianCalendar cal = new GregorianCalendar();
    Connection conn=null;
    PreparedStatement pst=null;
    ResultSet rs = null;
    private CollectionsCarList tabServiceListImpl = new CollectionsCarList();
    private ServInfBaseCreating servInfBaseCreating =  new ServInfBaseCreating();
    BrandAutoComplete helloAutoComplete = new BrandAutoComplete();
    VinAutoComplete modelAutoComplete = new VinAutoComplete();
    public Stage mainStage= new Stage();
    Exception evtt = new Exception();
    @FXML private Button btnAdd;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;
    @FXML private Button btnService;
    @FXML private Button btnAddServInfo;
    @FXML private VBox mainBox;

    @FXML private TableView tabServiceList;

    @FXML private DatePicker txtDPicker;
    @FXML private TextField txtGarNumb;
    @FXML private TextField txtRegNumb;
    @FXML private TextField txtEngVol;
    @FXML private TextField txtServInterval;
    @FXML private TextField txtBrand;
    @FXML private TextField txtVin;
    @FXML private TextField txtTransmNumb;
    @FXML private TextField txtSpeedometer;
    @FXML private TextField txtModel;
    @FXML private TextField txtEngNumb;
    @FXML private TextField txtYear;
    @FXML private TextField txtServLast;
    @FXML private TextField txtNextServ;
    @FXML private TextField txtSearch;
    @FXML private TextField txtDaysLast;
    @FXML private TextField txtServDate;

    @FXML private Label labelCount;
    @FXML private MenuItem storeMI;
    @FXML private MenuItem servInfoMI;
    @FXML private MenuItem storeMIAddStore;
    @FXML private MenuItem AddAutoMI;
    @FXML private MenuItem tableEditMI;

    @FXML private Button btnSearch;
    @FXML private TableColumn<Automobile, Integer> columnId;
    @FXML private TableColumn<Automobile, String> columnBrand;
    @FXML private TableColumn<Automobile, String> columnModel;
    @FXML private TableColumn<Automobile, String> columnGarNumb;
    @FXML private TableColumn<Automobile, String> columnRegNumb;
    @FXML private TableColumn<Automobile, String> columnVinCode;
    @FXML private TableColumn<Automobile, String> columnEngNumb;
    @FXML private TableColumn<Automobile, String> columnEngVol;
    @FXML private TableColumn<Automobile, String> columnTransmNumb;
    @FXML private TableColumn<Automobile, String> columnYearsOfRel;
    @FXML private TableColumn<Automobile, String> columnSpdVolume;
    @FXML private TableColumn<Automobile, String> columnServInt;
    @FXML private TableColumn<Automobile, String> columnServLast;
    @FXML private TableColumn<Automobile, String> columnServDate;
    @FXML private TableColumn<Automobile, String> columnDaysLast;

    private Parent fxmlAddDB;
    private Parent fxmlStore;
    private Parent fxmlServInfo;
    private Parent fxmlCurrentStore;
    CollectionsPartsTable collectionsPartsTable = new CollectionsPartsTable();
    CollectionServiceDataList collectionServiceDataList = new CollectionServiceDataList();
    private FXMLLoader fxmlStoreLoader = new FXMLLoader();
    private FXMLLoader fxmlAddDBLoader = new FXMLLoader();
    private FXMLLoader fxmlServInfLoader = new FXMLLoader();
    private FXMLLoader fxmlCurrentStoreLoader = new FXMLLoader();
    private AddDBDialogController addDBDialogController;
    private CurrentWorkDialogController currentWorkDialogController;
    private StoreController storeController;
    private ServiceController serviceController;
    private Stage addDbStage;
    private Stage storeStage;
    public Stage serviceInfoStage;
    private Stage currentStoreStage;

    private Automobile automobile;
    public   void setMainStage(Stage mainStage){this.mainStage = mainStage;}



    @FXML
    private void initialize() {

        columnId.setCellValueFactory(new PropertyValueFactory<Automobile, Integer>("id"));
        columnBrand.setCellValueFactory(new PropertyValueFactory<Automobile, String>("brand"));
        columnModel.setCellValueFactory(new PropertyValueFactory<Automobile, String>("model"));
        columnGarNumb.setCellValueFactory(new PropertyValueFactory<Automobile, String>("garNumb"));
        columnRegNumb.setCellValueFactory(new PropertyValueFactory<Automobile, String>("regNumb"));
        columnVinCode.setCellValueFactory(new PropertyValueFactory<Automobile, String>("vinCode"));
        columnEngNumb.setCellValueFactory(new PropertyValueFactory<Automobile, String>("engNumb"));
        columnEngVol.setCellValueFactory(new PropertyValueFactory<Automobile, String>("engVol"));
        columnTransmNumb.setCellValueFactory(new PropertyValueFactory<Automobile, String>("transmNumb"));
        columnYearsOfRel.setCellValueFactory(new PropertyValueFactory<Automobile, String>("yearsOfRel"));
        columnSpdVolume.setCellValueFactory(new PropertyValueFactory<Automobile, String>("servInt"));
        columnServInt.setCellValueFactory(new PropertyValueFactory<Automobile, String>("spdVol"));
        columnServLast.setCellValueFactory(new PropertyValueFactory<Automobile, String>("servLast"));
        columnDaysLast.setCellValueFactory(new PropertyValueFactory<Automobile, String>("daysLast"));
        columnServDate.setCellValueFactory(new PropertyValueFactory<Automobile, String>("serviceDate"));
        initListeners();
        tabServiceListImpl.fillTestData();

        tabServiceList.setItems(tabServiceListImpl.getAutomobileList());
       
        initStoreLoader();
        initServInfLoader();
        initAddDbLoader();
        initCurrentStoreLoader();

        btnAdd.setDisable(true);
        txtDPicker.setDisable(true);
        txtServDate.setDisable(true);
        helloAutoComplete.txtAutocompl(txtBrand);
        getModelList(txtVin);

        blinkingDist();


    }
    private int varSIValue(){
        int spd = Integer.parseInt(txtSpeedometer.getText());
        int sInt = Integer.parseInt(txtServInterval.getText());
        int sl = Integer.parseInt(txtServLast.getText());
        int varSI=0;

        if(spd<sInt){

            varSI=sInt;
        }
        else if (spd>sInt || sl!=0)
        {
            varSI=spd+sl;
        }


        return varSI;
    }
    //метод для получения списка моделей
    private ArrayList<String> getModels(){
    ArrayList<String> name = new ArrayList<>();
        Connection conn = DbConnection.ConnectDb();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
        String c = "select*from modelsList";
            pst = conn.prepareStatement(c);
            rs = pst.executeQuery();
            while(rs.next()){
                name.add(rs.getString("vmmodels"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);}
              }
        return name;
    }
    //Обновляем тблицу
    private void tabUpdate(){
    tabServiceList.getItems().clear();
    tabServiceListImpl.fillTestData();
}
    //инициализируем склад
    private void initStoreLoader(){
        try{
            fxmlStoreLoader.setLocation(getClass().getResource("/fxml/store.fxml"));
            fxmlStore = fxmlStoreLoader.load();
            storeController = fxmlStoreLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //инициализируем склад при проведении текущих работ
    private void initCurrentStoreLoader(){
        try{
            fxmlCurrentStoreLoader.setLocation(getClass().getResource("/fxml/CurrentWorkDialog.fxml"));
            fxmlCurrentStore = fxmlCurrentStoreLoader.load();
            currentWorkDialogController = fxmlCurrentStoreLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //инициализируем окно сервисной информации
    private void initServInfLoader(){
        try{
            fxmlServInfLoader.setLocation(getClass().getResource("/fxml/Service.fxml"));
            fxmlServInfo = fxmlServInfLoader.load();
            serviceController = fxmlServInfLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //добавляем в базу новый склад
    private void initAddDbLoader(){
        try{
            fxmlAddDBLoader.setLocation(getClass().getResource("/fxml/addStore.fxml"));
            fxmlAddDB = fxmlAddDBLoader.load();
            addDBDialogController = fxmlAddDBLoader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //при выборе строки в таблице,заполняем поля
    private void fieldText(Automobile automobile){
        if (automobile==null){
            return;
        }
        this.automobile = automobile;
        txtBrand.setText(automobile.getBrand());
        txtModel.setText(automobile.getModel());
        txtGarNumb.setText(automobile.getGarNumb());
        txtRegNumb.setText(automobile.getRegNumb());
        txtVin.setText(automobile.getVinCode());
        txtEngNumb.setText(automobile.getEngNumb());
        txtEngVol.setText(automobile.getEngVol());
        txtTransmNumb.setText(automobile.getTransmNumb());
        txtYear.setText(automobile.getYearsOfRel());
        txtSpeedometer.setText(automobile.getSpdVol());
        txtServInterval.setText(automobile.getServInt());
        txtServLast.setText(automobile.getServLast());
        txtDaysLast.setText(automobile.getDaysLast());
        txtServDate.setText(automobile.getServiceDate());
        txtNextServ.setText(automobile.getServSpeedValue());
        //dateCatcher();



    }
    //вставляем в таблицу БД данные о новом автомобиле.
    private void addToTab(){
        // TODO add your handling code here:
        Connection conn = DbConnection.ConnectDb();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            String sql ="Insert into carsList(garageNumb,Brand,Model,regNumb,VinCode,EngineNumb,EngineVol,TransmissionNumb,YearsOfRelease,ServiceInterval,Speedometer,ServiceLast,daysLast,serviceDate,varSI)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, txtGarNumb.getText());
                pst.setString(2, txtBrand.getText());
                pst.setString(3, txtModel.getText());
                pst.setString(4, txtRegNumb.getText());
                pst.setString(5, txtVin.getText());
                pst.setString(6, txtEngNumb.getText());
                pst.setString(7, txtEngVol.getText());
                pst.setString(8, txtTransmNumb.getText());
                pst.setString(9, txtYear.getText());
                pst.setString(10, txtServInterval.getText());
                pst.setString(11, txtSpeedometer.getText());
                varSIValue();
                String addVarSI = "" + varSIValue();
                txtNextServ.setText(addVarSI);
                int spd = Integer.parseInt(txtSpeedometer.getText());
                int varSI = Integer.parseInt(txtNextServ.getText());
                if (varSI != 0) {
                    int sl = varSI - spd;
                    String s = "";
                    txtServLast.setText(s + sl);
                }
                pst.setString(12, txtServLast.getText());
                datePick();
                txtDaysLast.setText(dayCounting()); ;
                pst.setString(13, txtDaysLast.getText());
                txtServDate.setText(txtDPicker.getValue().toString());
                pst.setString(14, txtServDate.getText());
                pst.setString(15, txtNextServ.getText());
                pst.execute();
                pst.close();
                JOptionPane.showMessageDialog(null, "Автомобиль добавлен");
                tabUpdate(); addNewAuto();updateByModel();dataAdding();clearFields();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e/*"При добавление нового автомобиля,\nзначение поля Остаток до ТО \nявляется обязательным для заполнения"*/);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}

    private GregorianCalendar datePick() {
        LocalDate localDate = txtDPicker.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
       Date date = Date.from(instant);
    cal.setTime(date);
        System.out.println(cal);
    return cal;


}
    //расчитываем остаток дней до проведения ТО 
    public String dayCounting(){
        String s="";
        GregorianCalendar today = new GregorianCalendar();
        int days = today.get(Calendar.DAY_OF_YEAR)-cal.get(Calendar.DAY_OF_YEAR);
days = 365-days;
        // корректируем, если текущий месяц в дате проверки меньше месяца
        int todayMonth = today.get(GregorianCalendar.MONTH);
        int calMonth = cal.get(GregorianCalendar.MONTH);
        int todayYear = today.get(GregorianCalendar.YEAR);
        int calYear = cal.get(GregorianCalendar.YEAR);
        int yearRes = todayYear-calYear;
        if ( todayYear > calYear ) {
            days = 365-days;
        }/*else if(yearRes>1){
            int n = yearRes;
            days = days+365*n;
        }*/

        System.out.println(days);
        s=Integer.toString(days);
        return s;
    }
    //поиск автомобиля по гаражному номеру
    private void searchTab() {
        Connection conn = DbConnection.ConnectDb();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String tmp = txtSearch.getText();
        String sql = "select * from carsList where garageNumb =?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, tmp);
            rs = pst.executeQuery();
            if (rs.next()) {
                String add1 = rs.getString("garageNumb");
                txtGarNumb.setText(add1);
                String add2 = rs.getString("Brand");
                txtBrand.setText(add2);
                String add3 = rs.getString("Model");
                txtModel.setText(add3);
                String add4 = rs.getString("regNumb");
                txtRegNumb.setText(add4);
                String add5 = rs.getString("VinCode");
                txtVin.setText(add5);
                String add6 = rs.getString("EngineNumb");
                txtEngNumb.setText(add6);
                String add7 = rs.getString("EngineVol");
                txtEngVol.setText(add7);
                String add8 = rs.getString("TransmissionNumb");
                txtTransmNumb.setText(add8);
                String add9 = rs.getString("YearsOfRelease");
                txtYear.setText(add9);
                String add10 = rs.getString("ServiceInterval");
                txtServInterval.setText(add10);
                String add11 = rs.getString("Speedometer");
                txtSpeedometer.setText(add11);
                String add12 = rs.getString("ServiceLast");
                txtServLast.setText(add12);
                String add13 = rs.getString("VarSi");
                txtNextServ.setText(add13);
                String add14 = rs.getString("daysLast");
                txtDaysLast.setText(add14);
                String add15 = rs.getString("serviceDate");
                txtServDate.setText(add15);
            }
            tabUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ошибка!\nПроверте правильность введения данных.");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //обновление БД
    private void updateTab(){
        Connection conn = DbConnection.ConnectDb();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String value1 = txtBrand.getText();
            String value2 = txtModel.getText();
            String value3 = txtGarNumb.getText();
            String value4 = txtRegNumb.getText();
            String value5 = txtVin.getText();
            String value6 = txtEngNumb.getText();
            String value7 = txtEngVol.getText() ;
            String value8 = txtTransmNumb.getText();
            String value9 = txtYear.getText();
            String value10 = txtSpeedometer.getText();
            String value11 = txtServInterval.getText();
            //String value12 = txtServLast.getText();
            //String value13 = txtNextServ.getText();
            int spd = Integer.parseInt(txtSpeedometer.getText());
            int varSI = Integer.parseInt(txtNextServ.getText());
            int sl=varSI-spd;
            String s ="";
            txtServLast.setText(s+sl);
            String value12 = txtServLast.getText();
            String value13 = ""+varSIValue();
            txtNextServ.setText(value13);
            //String value15 = dateCatcher();

            if(txtDPicker.getValue()!=null) {
                txtServDate.setText(txtDPicker.getValue().toString());
            }
            else{
                String t = txtServDate.getText();
                txtServDate.setText(t);
                }
             String value14 = dateSetter(txtServDate.getText());
            txtDaysLast.setText(value14);

            String value15 = txtServDate.getText();
                //String sql = "select * from carsList where garageNumb = ?";

            String upd = "UPDATE carsList set Brand='"+value1+"', Model= '" +value2 + "',garageNumb='" +value3+ "',regNumb='" + value4 + "',VinCode='" + value5 + "',EngineNumb='" + value6 + "'," +
                    "EngineVol = '" +value7+ "',TransmissionNumb='" +value8+ "',YearsOfRelease ='" +value9+ "',Speedometer='" +value10  + "',ServiceInterval='" + value11 + "',ServiceLast='" + value12 + "',varSI='"+value13+"',serviceDate ='"+value15+"' ,daysLast='"+value14+"' WHERE garageNumb='"+value3+"'";
            pst=conn.prepareStatement(upd);
            pst.execute();
            pst.close();
            JOptionPane.showMessageDialog(null, "Данные успешно обновлены");
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //инициализация слушателей
    /*обновляем счетчик
    по значению остатка до ТО,определяем подходящие авто,выделяем их красным цветом
    */
    private void  initListeners(){
        tabServiceListImpl.getAutomobileList().addListener(new ListChangeListener<Automobile>() {
            @Override
            public void onChanged(Change<? extends Automobile> c) {
                updateCountLabel();
            }
        });
        columnServLast.setCellFactory(column -> {
            return new TableCell<Automobile, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<Automobile> currentRow = getTableRow();

                    if (!isEmpty()) {
                        if(Integer.parseInt(item)<1000)

                            currentRow.setStyle("-fx-background-color:red");
                       else{

                        }
                    }
                }
            };
        });
        tabServiceList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==1){
                    Automobile selectAutomobile = (Automobile) tabServiceList.getSelectionModel().getSelectedItem();

                    fieldText(selectAutomobile);
                }
            }
        });

    }
    //конвертируем строку в дату
    public void stringConverter(String date){
    txtDPicker.setConverter(new StringConverter<LocalDate>() {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        {
            txtDPicker.setPromptText(pattern.toLowerCase());
        }

        @Override public String toString(LocalDate date) {
            if (date != null) {
                return dateFormatter.format(date);
            } else {
                return "";
            }
        }

        @Override public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string, dateFormatter);
            } else {
                return null;
            }
        }
    });
}
    /*Метод  преобразует строку в формат даты*
    / Затем получаем количество дней - разницу между датами(указанной и текущей)
    если дата взята с прошлого,позапрошлого и т.д года
    используем условие
     */
    private String dateSetter(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s="";
        Date date = new Date();
        try {
            date = format.parse(dateString);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            Date today = new Date();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(today);
            int z = c.get(Calendar.DAY_OF_YEAR)-c2.get(Calendar.DAY_OF_YEAR);

            int cYear = c.get(GregorianCalendar.YEAR);
            int c2Year = c2.get(GregorianCalendar.YEAR);
            if(c2Year>cYear){
                int res = c2Year-cYear;
                z= res*(c.get(Calendar.DAY_OF_YEAR)-c2.get(Calendar.DAY_OF_YEAR));
            }
            else if (c2Year==cYear){
                z=(c2.get(Calendar.DAY_OF_YEAR)-c.get(Calendar.DAY_OF_YEAR));
                z=365-z;
            }
            System.out.println(z);
            s=Integer.toString(z);
        } catch (Exception evtt) {
            JOptionPane.showMessageDialog(null, evtt);
        }
        return s;
    }
    private void updateCountLabel() {
        labelCount.setText("Количество записей: " + tabServiceListImpl.getAutomobileList().size());
    }
    //очистка полей
    private void clearFields(){
    txtGarNumb.setText("");
    txtBrand.setText("");
    txtModel.setText("");
    txtRegNumb.setText("");
    txtVin.setText("");
    txtEngNumb.setText("");
    txtEngVol.setText("");
    txtTransmNumb.setText("");
    txtYear.setText("");
    txtServInterval.setText("");
    txtSpeedometer.setText("");
    txtServLast.setText("");
    //txtSearch.setText("");
    txtNextServ.setText("");
    txtServDate.setText("");
    txtDaysLast.setText("");
}
    //события
    public void actionButtonPressed(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();//определения источника,что вызвал наш метод

        // если нажата не кнопка-выходим из метода
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;//нисходящее приведение

        Automobile selectAutomobile = (Automobile) tabServiceList.getSelectionModel().getSelectedItem();

        Window parentWindow = ((Node) actionEvent.getSource()).getScene().getWindow();

        //editDialogController.setAutomobile(selectAutomobile);

        switch (clickedButton.getId()) {
            case "btnAdd":
                 addToTab();
                addNewAuto();
                dataAdding();
                updateByModel();
                break;
            case "btnEdit":
                updateTab();
                tabUpdate();
                break;
            case "btnDelete":
                UIManager.put("OptionPane.yesButtonText", "Да");
                UIManager.put("OptionPane.noButtonText", "Нет");
                int p = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить автомобиль?", "Удалить", JOptionPane.YES_NO_OPTION);
                if (p == 0) {
                    tabServiceListImpl.delete((Automobile) tabServiceList.getSelectionModel().getSelectedItem());
                    actionDelete();
                    break;
                }
            case "btnClear":
               clearFields();
                break;
            case "btnService":
                int spd = Integer.parseInt(txtSpeedometer.getText());
                int sInt = Integer.parseInt(txtServInterval.getText());
                int varSI=spd+sInt;
                int sl = varSI - spd;
                txtNextServ.setText(""+varSI);
                txtServLast.setText(""+sl);

                break;
            case "btnSearch":
                searchTab();
                break;
            case "btnAddServInfo":
                addNewAuto();
                dataAdding();
                updateByModel();

        }
    }
    //удаление авто из БД
    public void actionDelete(){
        Connection conn = DbConnection.ConnectDb();
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            String del = "DELETE FROM carsList WHERE garageNumb =?";
            pst = conn.prepareStatement(del);
            pst.setString(1, txtGarNumb.getText());
            pst.execute();
            pst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //события меню
    public void actionMenuItemPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();//определения источника,что вызвал наш метод
        // если нажата не кнопка-выходим из метода
        if (!(source instanceof MenuItem)) {
            return;
        }
        MenuItem clickMenuItem = (MenuItem) source;//нисходящее приведение



        switch (clickMenuItem.getId()) {
            case "storeMI":
                showStore();
                break;
            case "servInfoMI":
                addNewAuto();
                serviceController.setAutomobile((Automobile) tabServiceList.getSelectionModel().getSelectedItem());
                serviceController.TabServiceData.getItems().clear();
                serviceController.fillServiceData(txtGarNumb.getText(),collectionServiceDataList);
                serviceController.TabServiceData.setItems(collectionServiceDataList.getServiceDatList());
                serviceController.init();
                //serviceController.serviceUpdate(txtGarNumb.getText());
                serviceController.servDataCorrector(txtGarNumb.getText());
                showServiceInfo();



                //serviceController.AreaContent();
                //showDialog();
                //editDialogController.actionEdit();


                break;
            case "storeMIAddStore":
                showAddDbDialog();
                break;
            case "AddAutoMI":
                btnAdd.setDisable(false);
                txtDPicker.setDisable(false);
                btnEdit.setDisable(true);
                clearFields();
                break;
            case "tableEditMI":

                    btnEdit.setDisable(false);
                    btnAdd.setDisable(true);
                    txtDPicker.setDisable(false);


                break;
            case "currentWorksMI":
                currentWorkDialogController.getLblName().setText(txtModel.getText());
                currentWorkDialogController.tabViewTable.getItems().clear();
                currentWorkDialogController.fillPartsWorkStoreData(txtModel.getText()+"Store",collectionsPartsTable);
                currentWorkDialogController.tabViewTable.setItems(collectionsPartsTable.getPartsList());
                currentWorkDialogController.init();
                showCurrentWork();

                break;
        }
    }
    //Отображаем склад
    public void showStore(){
    if (storeStage==null){
        storeStage = new Stage();
        storeStage.setTitle("Склад запчастей");
        storeStage.setMinHeight(600);
        storeStage.setMinWidth(400);
        storeStage.setResizable(false);
        storeStage.setScene(new Scene(fxmlStore));
        storeStage.initModality(Modality.WINDOW_MODAL);
        storeStage.initOwner(mainStage);
    }
    storeStage.showAndWait(); //ожидание закрытия окна
}
    public void showCurrentWork(){
        if (currentStoreStage==null){
            currentStoreStage = new Stage();
            currentStoreStage.setTitle("Склад запчастей");
            //currentStoreStage.setMinHeight(600);
           // currentStoreStage.setMinWidth(400);
            currentStoreStage.setResizable(false);
            currentStoreStage.setScene(new Scene(fxmlCurrentStore));
            currentStoreStage.initModality(Modality.WINDOW_MODAL);
            currentStoreStage.initOwner(mainStage);
        }
        currentStoreStage.showAndWait(); //ожидание закрытия окна
    }
    public void showServiceInfo(){
        if (serviceInfoStage==null){
            serviceInfoStage= new Stage();
            serviceInfoStage.setTitle("Сервисная информация");
            serviceInfoStage.setMinHeight(600);
            serviceInfoStage.setMinWidth(400);
            serviceInfoStage.setResizable(true);
            serviceInfoStage.setScene(new Scene(fxmlServInfo));

            serviceInfoStage.initModality(Modality.WINDOW_MODAL);
            serviceInfoStage.initOwner(mainStage);



        }
        serviceInfoStage.show(); //ожидание закрытия окна
    }
    public void showAddDbDialog(){


            if (addDbStage==null) {

                addDbStage = new Stage();
                addDbStage.setTitle("Редактирование");
               // addDbStage.setMinHeight(600);
               // addDbStage.setMinWidth(400);
                addDbStage.setResizable(false);
                addDbStage.setScene(new Scene(fxmlAddDB));
                addDbStage.initModality(Modality.WINDOW_MODAL);
                addDbStage.initOwner(mainStage);


            }
        addDbStage.showAndWait(); //ожидание закрытия окна

    }
     /*добавляем сервисную информации о вновь добавленном в БД авто
     создаем таблицу определенного типа
    */
    private void addNewAuto() {
        conn = DbConnection.ConnectDb(txtBrand.getText());
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            String tabName = txtGarNumb.getText();
            String newDbTable = "CREATE TABLE IF NOT EXISTS '" + tabName + "' "
                    + "("
                    + "TypesOfWorks CHAR,"
                    + "speedCount INTEGER, "
                    + "dayCount CHAR, "
                    + "serviceLast INTEGER, "
                    + "servIn INTEGER,"
                    + "dateIn CHAR,"
                    + "serviceDaysLast INTEGER,"
                    + "toolName CHAR,"
                    + "quantity INTEGER,"
                    + "measurement CHAR)" ;
            pst = conn.prepareStatement(newDbTable);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Создана новая таблица \nдля автомобиля с гаражным номером \n" + txtGarNumb.getText());
            pst.close();
            //addToTab();
        } catch (Exception e) {JOptionPane.showMessageDialog(null,e);}

        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*заполняем новую таблицу данными*/
    private void dataAdding(){
        Connection conn = DbConnection.ConnectDb(txtBrand.getText());
        PreparedStatement pst = null;
        ResultSet rs = null;
        String dataFrom = "select * from '"+txtVin.getText()+"'";
        String garNumbInfo = txtGarNumb.getText();
        try{
            String add1="";
            int add2;
            String add3="";
            int add4;
            int add5;
            String add6="";
            String add7 = "";
            int add8;
            String add9="";
            pst=conn.prepareStatement(dataFrom);
            rs = pst.executeQuery();
            while (rs.next() ) {
                add1 = rs.getString("TypesOfWorks");
                add2 = rs.getInt("speedCount");
                add3 = rs.getString("dayCount");
                add4 = rs.getInt("serviceLast");
                add5 = rs.getInt("servIn");
                add6 = rs.getString("dateIn");
                add7=  rs.getString("toolName");
                add8 = rs.getInt("quantity");
                add9 = rs.getString("measurement");
                String dataTo ="Insert into '"+garNumbInfo+"' (TypesOfWorks,speedCount,dayCount,serviceLast,servIn,dateIn,toolName,quantity,measurement)values('"+add1+"','"+add2+"','"+add3+"','"+add4+"','"+add5+"','"+add6+"','"+add7+"','"+add8+"','"+add9+"') ";
                pst=conn.prepareStatement(dataTo);
                pst.execute();
            }
pst.close();
        }catch(Exception e){JOptionPane.showMessageDialog(null,"here error");}
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*проводим расчеты,обновляем таблицу*/
    private void updateByModel() {
        Connection conn = DbConnection.ConnectDb(txtBrand.getText());
        PreparedStatement pst = null;
        ResultSet rs = null;
        int sl = 0;
        int servInt = 0;
        ResultSet rss = null;
        PreparedStatement pstt = null;

        String dataFrom = "select * from carsList where garageNumb = '" + txtGarNumb.getText() + "'";
        try {
            pst = conn.prepareStatement(dataFrom);
            rs = pst.executeQuery();
            while (rs.next()) {
                sl = rs.getInt("ServiceLast");
                servInt = Integer.parseInt(rs.getString("ServiceInterval"));
                String dataUpd = "UPDATE '" + txtGarNumb.getText() + "' set serviceLast='" + sl + "'where speedCount ='" + servInt + "'";
//String dataIns = "insert into '" + txtGarNumb.getText() + "'(serviceLast)values('"+sl+"')where speedCount ='" + servInt + "'";
                try {
                    pst = conn.prepareStatement(dataUpd);
                    //pst = conn.prepareStatement(dataIns);
                    pst.execute();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "fuck");
                }
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void blinkingDist(){
        columnServLast.setCellFactory(column -> {
            return new TableCell<Automobile, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<Automobile> currentRow = getTableRow();

                    if (!isEmpty()) {

                        if(Integer.parseInt(item) < 1000)
                            currentRow.setStyle("-fx-background-color:#f06d4c;-fx-text-fill: darkgreen");
                        else
                            currentRow.setStyle("-fx-background-color:#1aeea1");
                    }
                }
            };
        });
    }
    /*автозаполнение поля "марка автомобиля"*/
    public  class BrandAutoComplete extends ControlsFXSample {
        MainController mainController;
        Connection conn =null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        private AutoCompletionBinding<String> autoCompletionBinding;
        private String[] _possibleSuggestions =

                {


        @Override
        public String getSampleName() {
            return null;
        }

        @Override
        public Node getPanel(Stage stage) {
            return null;
        }

        @Override
        public String getJavaDocURL() {
            return Utils.JAVADOC_BASE + "org/controlsfx/control/textfield/TextFields.html";
        }

        public void txtAutocompl(TextField textField) {

            TextFields.bindAutoCompletion
                    (textField, "MERCEDES-BENZ", "TOYOTA","AUDI", "VOLKSWAGEN",
                            "BMW", "MITSUBISHI", "SKODA", "HYUNDAI","OPEL","HONDA","КАМАЗ","УАЗ","ПАЗ",
                            "ГАЗ","ВАЗ","FORD","NISSAN");

        }

    }
    }
     /*автозаполнение поля "номер кузова"*/
    public  class VinAutoComplete extends ControlsFXSample {


        PreparedStatement pst = null;
        ResultSet rs = null;
        private AutoCompletionBinding<String> autoCompletionBinding;

        private ArrayList<String> _possibleSuggestions = new ArrayList<String>();
        //private Set<String> possibleSuggestions = new HashSet<>(Arrays.asList(_possibleSuggestions));

        @Override
        public String getSampleName() {
            return null;
        }

        @Override
        public Node getPanel(Stage stage) {
            return null;
        }

        @Override
        public String getJavaDocURL() {
            return Utils.JAVADOC_BASE + "org/controlsfx/control/textfield/TextFields.html";
        }
        public void txtVinAutocompl(TextField textVin) {
        String modName = txtBrand.getText();
        Connection conn =DbConnection.ConnectDb();
                   try {
                _possibleSuggestions.clear();
                String t = "SELECT * FROM '" + txtBrand.getText() + "' ";
                pst = conn.prepareStatement(t);
                rs = pst.executeQuery();
                while (rs.next()) {
                    _possibleSuggestions.add(rs.getString("VinCode"));
                    // System.out.println(rs.getString("model"));
                }
                pst.close();
                rs.close();
                        } catch (Exception evtt) {
                evtt.printStackTrace();
            }

            TextFields.bindAutoCompletion(textVin,_possibleSuggestions);

        }

        public ArrayList<String> get_possibleSuggestions() {
            return _possibleSuggestions;
        }

        public void set_possibleSuggestions(ArrayList<String> _possibleSuggestions) {
            this._possibleSuggestions = _possibleSuggestions;

        }
    }
    /*исходя из значения номера кузова заполняем остальные поля*/
    public void setTextToFields(){
        String modName = txtBrand.getText();
        String data = txtVin.getText();
        PreparedStatement setFieldsSt = null;
        Connection conn = DbConnection.ConnectDb();
        ResultSet setFieldsRs = null;
        try{
            String setFields = "Select*from '"+txtBrand.getText()+"' where vinCode = '"+data+"'";
            setFieldsSt = conn.prepareStatement(setFields);
            setFieldsRs = setFieldsSt.executeQuery();
            while(setFieldsRs.next()){
                txtModel.setText(setFieldsRs.getString("model"));
                txtEngNumb.setText(setFieldsRs.getString("engineNumb"));
                txtEngVol.setText(setFieldsRs.getString("engineVolume"));
                System.out.println("Oops...");
            }
            setFieldsSt.close();
            setFieldsRs.close();
           

        }catch(Exception eMb){
            eMb.printStackTrace();}
    }
    public void getModelList(TextField tfV){
        tfV.setOnMouseClicked(new EventHandler<MouseEvent>() {
            VinAutoComplete mac = new VinAutoComplete();
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount()==1) {
                    mac.txtVinAutocompl(txtVin);

                }
            }
        });
        tfV.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode()== KeyCode.ENTER){
                    setTextToFields();
                }
            }
        });


    }
    public TextField getTxtVin() {
        return txtVin;
    }
    public void setTxtVin(TextField txtVin) {
        this.txtVin = txtVin;
    }

}

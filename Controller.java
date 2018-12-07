package StudentManagementSystem;

/**
 * @author      Edson Zhu <efkzhu @ myseneca.ca>
 * @version     1.0
 * @since       1.0
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class used to control the view (FXML)
 */
public class Controller {
    @FXML private TextField fileName;
    @FXML private TextField name;
    @FXML private TextField course;
    @FXML private TextField grade;
    @FXML private TextArea outputText;
    @FXML private TableView<Student> tabView;
    @FXML private TableColumn<Student, Integer> stdId;
    @FXML private TableColumn<Student, String> stdGrade;
    @FXML private TableColumn<Student, String> stdName;
    @FXML private TableColumn<Student, String> stdCourse;
    private ObservableList<Student> stdCollection;

    /**
     * Constructor
     */
    public Controller() {}

    /**
     * Initialize the view disabling the name, course and grade field
     */
    @FXML void initialize(){
        disableFieldTrue();
        stdCollection = FXCollections.observableArrayList();

        stdCourse.setCellValueFactory(new PropertyValueFactory<Student, String>("course"));
        stdGrade.setCellValueFactory(new PropertyValueFactory<Student, String>("grade"));
        stdId.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        stdName.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));

        tabView.setEditable(true);
        stdCourse.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        stdGrade.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
        stdName.setCellFactory(TextFieldTableCell.<Student>forTableColumn());
    }

    /**
     * Load a student collection from the disk
     */
    @FXML void loadStudent(){
        try {
            FileInputStream in = new FileInputStream(fileName.getText());
            ObjectInputStream i = new ObjectInputStream(in);
            List<Student> list = (ArrayList<Student>) i.readObject() ;
            i.close();
            in.close();
            stdCollection = FXCollections.observableList(list);
            putStudentTab();
            cleanFields();
            outputText.setText("Success! Student collection " + fileName.getText() + " was loaded.");
        }catch (IOException e){
            outputText.setText("Fail to find or open the following filename: " + fileName.getText());
        }catch (ClassNotFoundException e) {
            outputText.setText("Student collection not found.");
        }
    }

    /**
     * Load all the student files that exist in the system
     * @return A collection of the student on the list
     */
    public void putStudentTab () {
        tabView.setItems(stdCollection);
        tabView.getColumns();
        tabView.sort();
        tabView.layout();
        disableFieldTrue();
    }

    /**
     * Change course in table
     * @param stdColEvent
     */
    @FXML void editColCourse(TableColumn.CellEditEvent<Student, String> stdColEvent) {
        Student stdTable = tabView.getSelectionModel().getSelectedItem();
        stdCollection.forEach(student -> {
            if (student.getId() == stdTable.getId()) {
                student.setCourse(stdColEvent.getNewValue());
            }
        });
        outputText.setText("Student's course successfully changed! ");
    }

    /**
     * Change name in table
     * @param stdColEvent
     */
    @FXML void editColName(TableColumn.CellEditEvent<Student, String> stdColEvent) {
        Student stdTable = tabView.getSelectionModel().getSelectedItem();
        stdCollection.forEach(student -> {
            if (student.getId() == stdTable.getId()) {
                student.setName(stdColEvent.getNewValue());
            }
        });
        outputText.setText("Student's name successfully changed! ");
    }

    @FXML void getInfo() {
        Student stdTable = tabView.getSelectionModel().getSelectedItem();
        name.setText(stdTable.getName());
        course.setText(stdTable.getCourse());
        grade.setText(stdTable.getGrade());
    }

    /**
     * Change grade in table
     * @param stdColEvent
     */
    @FXML void editColGrade(TableColumn.CellEditEvent<Student, String> stdColEvent) {
        Student stdTable = tabView.getSelectionModel().getSelectedItem();
        stdCollection.forEach(student -> {
            if (student.getId() == stdTable.getId()) {
                student.setGrade(Integer.parseInt(stdColEvent.getNewValue()));
            }
        });
        outputText.setText("Student's grade successfully changed! ");
    }

    /**
     * Enable the name, course and grade field for input
     */
    @FXML void newStudent(){
        disableFieldFalse();
        cleanFields();
        outputText.setText("");
    }

    /**
     * Return a unique integer number
     * @param list list of integer to check
     * @return a unique integer
     */
    int checkId(List<Integer> list) {
        int holder = 0;
        List<Integer> arryList;
        while (list.size() != 0) {
                final int holderFinal = holder;
            arryList = list.stream()
                    .filter(x -> x != holderFinal)
                    .collect(Collectors.toList());

            if (arryList.size() == list.size()) break;

            list = arryList.stream()
                    .collect(Collectors.toList());
            arryList.clear();
            holder++;
        }
        return holder;
    }

    /**
     * Add a new student to the collection
     */
    @FXML void addStudent(){
        List<Integer> idHolder = stdCollection.stream()
                .map(std -> std.getId())
                .collect(Collectors.toList());
        stdCollection.add(new Student(name.getText(), course.getText(), Integer.parseInt(grade.getText()), checkId(idHolder)));
        putStudentTab();
    }

    /**
     * Save the Student collection to the disk
     */
    @FXML void saveFile(){
        try {
            FileOutputStream out = new FileOutputStream(fileName.getText());
            ObjectOutputStream o = new ObjectOutputStream(out);
            o.writeObject(new ArrayList<Student>(stdCollection));
            o.close();
            out.close();
            outputText.setText("Success! Student file colletion " + fileName.getText() + " was saved.");

        } catch (IOException e){
            outputText.setText("Fail to save collection file");
        }
    }

    /**
     * Allow the user to edit student information
     */
    @FXML void editStudent() {
        disableFieldFalse();
        outputText.setText("");
    }

    /**
     * Delete the student object file from the disk
     */
    @FXML void deleteStudent() {
        Student stdTable = tabView.getSelectionModel().getSelectedItem();
        stdCollection.removeIf(p -> p.getId() == stdTable.getId());
        putStudentTab();
    }

    /**
     * Open a windows to allow the user to select a file on the system
     */
    @FXML void chooseFile() {
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if(selectedFile != null) {
            fileName.setText(selectedFile.getName());
        }
    }
    /**
     * Clean all the GUI fields
     */
    void cleanFields() {
        //fileName.setText("");
        name.setText("");
        course.setText("");
        grade.setText("");
    }

    /**
     * Enable name, course and grade field
     */
    void disableFieldFalse() {
        name.setDisable(false);
        course.setDisable(false);
        grade.setDisable(false);
    }

    /**
     * Disable name, course and grade field
     */
    void disableFieldTrue() {
        name.setDisable(true);
        course.setDisable(true);
        grade.setDisable(true);
    }

}

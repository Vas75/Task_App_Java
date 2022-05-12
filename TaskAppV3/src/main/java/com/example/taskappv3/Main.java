package com.example.taskappv3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;

public class Main extends Application {
    private static final String SAVE_FILE = "SaveFile.dat";
    private static final Binder BINDER = Save.getBinder(SAVE_FILE); //On program start, BINDER list retrieved from save file
    private static VBox vboxGroups; //Public to access throughout class, needed to populate vbox with group elements


    @Override
    public void start(Stage primaryStage) {
        HomePane homePane = new HomePane();

        //Getting a reference to the vbox in scroll pane so can populate with groups
        ScrollPane scrGroups = (ScrollPane) homePane.getChildren().get(1);
        vboxGroups = (VBox) scrGroups.getContent();

        //Populate the scrolls vbox with group titles, delete, and view btns
        populateWithGroups(vboxGroups);

        Scene scene = new Scene(homePane);
        primaryStage.setTitle("Task App v3");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Class method creates a VBox pane that holds contents of the start page, controls for adding, viewing, and deleting groups
     */
    static class HomePane extends VBox {
        public HomePane() {
            super(5);
            //btn to add group and text for group name
            HBox addGroupCtrls = new HBox(5);
            addGroupCtrls.setPadding(new Insets(10));

            TextField tfAddGroup = new TextField();
            tfAddGroup.setPrefWidth(350);

            Button btnAddGroup = new Button("Add Group");

            //Scroll pane with the groups
            ScrollPane scrollGroups = new ScrollPane();
            scrollGroups.setPrefHeight(400);
            scrollGroups.setPadding(new Insets(5)); //Is this doing anything?

            //Vbox to hold groups in scroll pane, poplulate with labels of groups
            VBox vboxGroups = new VBox();
            vboxGroups.setSpacing(5);
            vboxGroups.setPadding(new Insets(5));
            scrollGroups.setContent(vboxGroups);

            //Add button and text field to hbox
            addGroupCtrls.getChildren().addAll(btnAddGroup, tfAddGroup);


            //Add the ctrls groups to the vbox
            this.getChildren().addAll(addGroupCtrls, scrollGroups);

            //EVENT HANDLER FOR ADDING GROUP////////////////////////////////////////////////////////////////////////////////////////////
            btnAddGroup.setOnAction(e -> {
                if (!tfAddGroup.getText().isEmpty()) {
                    addNewGroup(tfAddGroup.getText());
                }
            });
        }
    }

    /**
     * This class method will use the arraylist of groups (Binder) to display hbox for each group in list.
     * Event handlers added to group view and delete buttons.
     */
    public static void populateWithGroups(VBox vboxGroups) {
        vboxGroups.getChildren().clear(); //Clear groups from the vbox before adding groups to prevent duplication

        for(Group group: BINDER) {
            //Get name and id of group off the object
            String groupName = group.getGroupName();
            String groupID = String.valueOf(group.getGroupID());

            //hbox for each group
            HBox hBox = new HBox();
            //hBox.setPrefWidth(400);
            hBox.setPrefWidth(400);
            hBox.setPadding(new Insets(5));
            hBox.setAlignment(Pos.CENTER);
            hBox.setStyle("-fx-background-color: #FFD580;");

            //hbox to create seperation between title and buttons, expands to fill space between evenly
            HBox divider = new HBox();
            HBox.setHgrow(divider, Priority.ALWAYS);


            HBox btnsGroup = new HBox();
            btnsGroup.setSpacing(5);

            Label lbl = new Label(groupName);
            Button btnDel = new Button("X");

            Button btnView = new Button("view");
            btnView.setId(groupID);

            btnsGroup.getChildren().addAll(btnView, btnDel);
            hBox.getChildren().addAll(lbl, divider, btnsGroup);
            vboxGroups.getChildren().add(hBox);

            //EVENT HANDLERS ///////////////////////////////////////////////////////////////////////////////////
            btnView.setOnAction(e -> viewGroup(groupID));
            btnDel.setOnAction(e -> delGroup(groupID));
        }
    }

    /**
     * Event handler for button to view group, needs to get task object to display details, open second stage
     */
    public static void viewGroup(String id) {
        int groupID = Integer.parseInt(id);
        Group group = BINDER.getGroupByID(groupID); //Get matching group list off Binder using the ID obtained from the button clicked

        Stage stage = new Stage(); //New window to display tasks in group

        //Btns to add general and school tasks, buttons have ids to indicate if school or general task is being created
        HBox hBoxAddTaskCtrls = new HBox();
        Button btnAddGeneralTask = new Button("Add General Task");
        btnAddGeneralTask.setId("general");
        Button btnAddSchoolTask = new Button("Add School Task");
        btnAddSchoolTask.setId("school");
        hBoxAddTaskCtrls.getChildren().addAll(btnAddGeneralTask, btnAddSchoolTask);
        hBoxAddTaskCtrls.setAlignment(Pos.CENTER);
        hBoxAddTaskCtrls.setPadding(new Insets(5));
        hBoxAddTaskCtrls.setSpacing(100);

        ScrollPane scrollTasks = new ScrollPane();
        scrollTasks.setPrefWidth(420);
        scrollTasks.setPrefHeight(400);
        scrollTasks.setPadding(new Insets(5));

        //Vbox to hold tasks in scroll pane
        VBox vboxTasks = new VBox();
        vboxTasks.setSpacing(30);
        scrollTasks.setContent(vboxTasks); //Add vbox of task to scrollable pane

        //vbox, textarea, and delete btn created for each task object, added to vPane/scroll pane.
        for (Task task: group) {
            String taskID =  String.valueOf(task.getTaskID());

            VBox vPane = new VBox(); //vbox holds task text and delete button for task
            vPane.setPadding(new Insets(5));
            vPane.setSpacing(5);

            if(task instanceof SchoolTask) {
                vPane.setStyle("-fx-background-color: #90EE90;");
            } else {
                vPane.setStyle("-fx-background-color: #ADD8E6;");
            }


            TextArea ta = new TextArea();
            ta.setText(task.toString()); //Get toString of task object
            ta.setPrefColumnCount(10);
            ta.setPrefWidth(375);
            ta.setWrapText(true);

            Button btnDelete = new Button("Delete");

            vPane.getChildren().addAll(ta, btnDelete);
            vboxTasks.getChildren().add(vPane);

            //EVENT HANDLERS/////////////////////////////////////////////////////////////////////////////////
            btnDelete.setOnAction(e -> {
                stage.close(); //After task delete btn is clicked, the stage for the group is closed, since it will be opened again to re-render group tasks
                delTask(taskID, String.valueOf(groupID)); //delTask gets ref to both task to delete and group it lives on
            });
        }

        //EVENT HANDLERS/////////////////////////////////////////////////////////////////////////
        btnAddGeneralTask.setOnAction(e -> {
            stage.close(); //Close the stage, so can reopen when updated with task
            openNewTaskForm(groupID, btnAddGeneralTask.getId());
        });


        btnAddSchoolTask.setOnAction(e -> {
            stage.close();
            openNewTaskForm(groupID, btnAddSchoolTask.getId());
        });


        VBox wrapper = new VBox();
        wrapper.setAlignment(Pos.CENTER);
        wrapper.getChildren().addAll(hBoxAddTaskCtrls, scrollTasks);
        Scene scene = new Scene(wrapper);
        stage.setTitle(group.getGroupName());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handler to add group, group made with arg groupName, group added to BINDER, BINDER saved, vbox containing groups repopulated
     */
    public static void addNewGroup(String groupName) {
        Group newGroup = new Group(groupName);
        BINDER.add(newGroup);
        Save.saveBinder(SAVE_FILE, BINDER);
        populateWithGroups(vboxGroups);
    }

    /**Handler to delete group, deletes group via id, Binder is saved to file, vbox repopulated with updated groups*/
    public static void delGroup(String groupID) {
        int ID = Integer.parseInt(groupID);
        BINDER.deleteGroupByID(ID);
        Save.saveBinder(SAVE_FILE, BINDER);
        populateWithGroups(vboxGroups);
    }

    /**Handler to delete task, takes both the task id and group id of group holding the task*/
    public static void delTask(String taskID, String groupID) {
        Group foundGroup = BINDER.getGroupByID(Integer.parseInt(groupID));
        foundGroup.deleteTaskByID(Integer.parseInt(taskID));
        Save.saveBinder(SAVE_FILE, BINDER); //After deletion of task, save changes to the Binder
        viewGroup(groupID);
    }

    /**Handler to add task to specific group*/
    public static void addTask(String title, String body, String due, String teacher, String phone, String className, int groupID, String btnID) {
        Task newTask;

        //Create general or school task
        if (btnID == "general") {
            newTask = new GeneralTask(title, body, due);
        } else {
            newTask = new SchoolTask(title, body, due, className, teacher, phone);
        }

        //Find correct group by id, add task object to the array, save the binder
        Group foundGroup = BINDER.getGroupByID(groupID);
        foundGroup.add(newTask);
        Save.saveBinder(SAVE_FILE, BINDER);

        viewGroup(String.valueOf(groupID)); //Re-render the group of updated tasks
    }

    /**Handler to open window to create new task, takes id of group to add to, and id of button clicked (general or school)*/
    public static void openNewTaskForm(int groupID, String buttonID) {
        //Make window/form to get task info   Date will need year, month, day fields
        Stage stage = new Stage();

        //Holds labels and fields of form
        VBox form = new VBox();
        form.setPadding(new Insets(10));
        form.setSpacing(10);

        HBox titleRow = new HBox();
        titleRow.setSpacing(5);
        titleRow.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("Task Name");
        TextField tfTitle = new TextField();
        titleRow.getChildren().addAll(title, tfTitle);

        VBox bodyRow = new VBox();
        bodyRow.setSpacing(5);
        Label body = new Label("Task Details");
        TextArea taBody = new TextArea();
        bodyRow.getChildren().addAll(body, taBody);

        //Row for due date has 3 nested HBox///////////
        HBox dueDateRow = new HBox();
        dueDateRow.setSpacing(5);
        dueDateRow.setAlignment(Pos.CENTER_LEFT);
        Label lblDueDate = new Label("Task Due");
        TextField tfDueDate = new TextField();
        dueDateRow.getChildren().addAll(lblDueDate, tfDueDate);

        //Fields for the  additional StudentTask information, only added if buttonID == student
        HBox teacherNameRow = new HBox();
        teacherNameRow.setAlignment(Pos.CENTER_LEFT);
        teacherNameRow.setSpacing(5);
        Label lblName = new Label("Instructor Name");
        TextField tfName = new TextField();
        teacherNameRow.getChildren().addAll(lblName, tfName);

        HBox teacherTelRow = new HBox();
        teacherTelRow.setSpacing(5);
        teacherTelRow.setAlignment(Pos.CENTER_LEFT);
        Label lblTel = new Label("Instructor Phone");
        TextField tfTel = new TextField();
        teacherTelRow.getChildren().addAll(lblTel, tfTel);

        HBox classNameRow = new HBox();
        classNameRow.setAlignment(Pos.CENTER_LEFT);
        classNameRow.setSpacing(5);
        Label lblClassName = new Label("Class Name");
        TextField tfClassName = new TextField();
        classNameRow.getChildren().addAll(lblClassName, tfClassName);

        form.getChildren().addAll(titleRow, bodyRow, dueDateRow); //Add fields general fields to form

        //Check if user wants to create student task, if so, add student data fields
        if(buttonID == "school") {
            form.getChildren().addAll(teacherNameRow, teacherTelRow, classNameRow);
        }

        //Submit button for the form
        Button btnSubmit = new Button("SUBMIT");
        form.getChildren().add(btnSubmit);

        Scene scene = new Scene(form);
        stage.setTitle("NEW " + buttonID.toUpperCase() + " TASK" );
        stage.setScene(scene);
        stage.show();

        //EVENT HANDLER///////////////////////////////////////////
        btnSubmit.setOnAction(e -> {
            final String DEFAULT = "undefined";

            //Get and sanitize input
            String taskTitle, taskBody, taskDueDate, teacherName, teacherTel, className;
            taskTitle = tfTitle.getText().trim().length() > 0 ? tfTitle.getText().trim() : DEFAULT;
            taskBody = taBody.getText().trim().length() > 0 ? taBody.getText().trim() : DEFAULT;
            taskDueDate = tfDueDate.getText().trim().length() > 0 ? tfDueDate.getText().trim() : DEFAULT;
            teacherName = tfName.getText().trim().length() > 0 ? tfName.getText().trim() : DEFAULT;
            teacherTel = tfTel.getText().trim().length() > 0 ? tfTel.getText().trim() : DEFAULT;
            className = tfClassName.getText().trim().length() > 0 ? tfClassName.getText().trim() : DEFAULT;

            addTask(taskTitle, taskBody, taskDueDate, teacherName, teacherTel, className, groupID, buttonID);
            stage.close(); //Close new task form after task submitted
        });
    }


    public static void main(String[] args) {
        launch();
    }
}

package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import application.Main.Dog;

public class Main extends Application {

    private ArrayList<Dog> dogList = new ArrayList<>();
    private ListView<String> dogListView;
    private ListView<String> handlerListView;
    private ListView<String> searchDogListView;
    
    private static final String APP_TITLE = "Final Project";
    private static final int SCENE_WIDTH = 400;
    private static final int SCENE_HEIGHT = 200;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(APP_TITLE);
        buildMainMenu(primaryStage);
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    // Build the main menu UI
    private void buildMainMenu(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("Bria's DogStay menu"), 1, 0);

        // Buttons for the main menu
        Button btEnter = createMenuButton("Enter Dog");
        Button btListAll = createMenuButton("List Dogs");
        Button btMedications = createMenuButton("Medications");
        Button btHandlers = createMenuButton("Handlers");
        Button btReleaseDog = createMenuButton("Release Dog");
        Button btSearchDog = createMenuButton("Search Dogs");

        // Add buttons to the grid pane
        gridPane.add(btEnter, 0, 1);
        gridPane.add(btListAll, 1, 1);
        gridPane.add(btMedications, 0, 2);
        gridPane.add(btHandlers, 1, 2);
        gridPane.add(btReleaseDog, 0, 3);
        gridPane.add(btSearchDog, 1, 3);

        setupMainMenuButtonActions(primaryStage, btEnter, btListAll, btMedications, btHandlers, btReleaseDog, btSearchDog);

        Scene mainMenuScene = new Scene(gridPane, SCENE_WIDTH, SCENE_HEIGHT);
    }

    // Setup actions for main menu buttons
    private void setupMainMenuButtonActions(Stage primaryStage, Button btEnter, Button btListAll, Button btMedications, Button btHandlers, Button btReleaseDog, Button btSearchDog) {
        btEnter.setOnAction(e -> enterDogScene(primaryStage));
        btListAll.setOnAction(e -> listDogsScene(primaryStage));
        btMedications.setOnAction(e -> medicationsScene(primaryStage));
        btHandlers.setOnAction(e -> handlersScene(primaryStage));
        btReleaseDog.setOnAction(e -> releaseDogScene(primaryStage));
        btSearchDog.setOnAction(e -> searchDogScene(primaryStage));
    }

    // Create a menu button with styling
    private Button createMenuButton(String buttonText) {
        Button button = new Button(buttonText);
        button.setPrefWidth(150);
        return button;
    }
    	// Handle the Enter Dog scene
    private void enterDogScene(Stage primaryStage) {
    	
    	// UI elements
    	TextField firstNameField = new TextField();
    	TextField lastNameField = new TextField();
    	TextField ageField = new TextField();
    	TextField weightField = new TextField();
    	TextField breedField = new TextField();
    	RadioButton btPaid = new RadioButton("Paid");
    	RadioButton btUnpaid = new RadioButton("Unpaid");
    	ToggleGroup paymentGroup = new ToggleGroup();
    	Button btEnterDog = new Button("Enter");
    	Button btBack = new Button("Back");
    	
    	GridPane enterGridPane = new GridPane();
		enterGridPane.add(new Label("First Name: "), 0, 0);
		enterGridPane.add(firstNameField, 1, 0);
		enterGridPane.add(new Label("Last Name: "), 0, 1);
		enterGridPane.add(lastNameField, 1, 1);
		enterGridPane.add(new Label("Age: "), 0, 2);
		enterGridPane.add(ageField, 1, 2);
		enterGridPane.add(new Label("Weight: "), 0, 3);
		enterGridPane.add(weightField, 1, 3);
		enterGridPane.add(new Label("Breed: "), 0, 4);
		enterGridPane.add(breedField, 1, 4);
		enterGridPane.add(btPaid, 0, 5);
		enterGridPane.add(btUnpaid, 1, 5);
		enterGridPane.add(btBack, 0, 6);
		enterGridPane.add(btEnterDog, 1, 6);
		 btPaid.setToggleGroup(paymentGroup);
		 btUnpaid.setToggleGroup(paymentGroup);
    	
    	// Action Handling for Enter button	 
			btEnterDog.setOnAction(e -> {
				String lastName = lastNameField.getText();
			    String firstName = firstNameField.getText();		    
			    // validate name fields
			    if (!validateNameFields(lastName, firstName) ){
			        showErrorAlert("Missing Information", "Please fill in all required fields.");
			        return;
			    }			    
			    System.out.println("Name Entered");			    		        
			    String textWeight = weightField.getText();
			    double newWeight = 0.0;		    
			    	// validate weight
			    try {
			        newWeight = Double.parseDouble(textWeight);
			    } catch (NumberFormatException ex) {			        
			        weightField.clear(); // Clear the weight field
			        showErrorAlert("Invalid Weight Input", "Please enter a valid number for weight.");
			        return;    	
			    }
			    	// validate age
			    String textAge = ageField.getText();	    
			    int newAge = 0; 
			    try {
			        newAge = Integer.parseInt(textAge);			
			    } catch (NumberFormatException ex) {		        
			        ageField.clear(); // Clear the age field
			        showErrorAlert("Invalid Age Input", "Please enter a whole number");
			        return; 
			    }
			    	// get Breed
			    String newBreed = breedField.getText();			    		    		    
			    boolean isPaid = false;			  
			    if (btPaid.isSelected() && btUnpaid.isSelected()) {
			    	if (btPaid.isSelected()) {
			    	isPaid = true;
			    	}
			    	else {
			    	isPaid = false;
			    	}
			    }
			    else {
			    	showErrorAlert("Payment Status", "Please select payment status ");
			        return;    	
			    }			    			   			    			 				    
			    Dog newDog = new Dog(lastName, firstName, newWeight, newAge, newBreed, isPaid);	// send the entered information to the functions to creat dog obj
			    dogList.add(newDog);	// add the new Dog obj to the ArrayList
			    updateDogListView();	// update the dog list for display			       
			    // clear the field
			   clearEnterFields(lastNameField, firstNameField, weightField, ageField, breedField,btPaid, btUnpaid );		    
			});		
			
			// Building the Scene
			
			Scene sceneEnterDog = new Scene(enterGridPane, 400, 200);
			
    }
    
   private void clearEnterFields(TextField lastNameField, TextField firstNameField,TextField weightField, TextField ageField, TextField breedField, RadioButton btPaid, RadioButton btUnpaid) {
    	
   	 	lastNameField.clear();
   	    firstNameField.clear();
   	    weightField.clear();
   	    ageField.clear();
   	    breedField.clear();
   	    btPaid.setSelected(false);
   	    btUnpaid.setSelected(false);
   	
   }
   private boolean validateNameFields(String lastName, String firstName) {
	   // test if the fields are empty
	   if (firstName.isEmpty() || lastName.isEmpty()) {      
	        return false;
	    }	
	   return true;  // return true if not empty
   } 
   private void showErrorAlert(String title, String message) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}

    
// Classes Section
public static class Dog {
		
		private String lastName;
		private String firstName;
		private Double weight;
		private int age;
		private String breed;
		private Boolean paid;
		// constructor
		public Dog(String ln,String fn,  Double w, int a, String b, boolean p) {
			this.lastName = ln;
			this.firstName = fn;
			this.weight = w;
			this.age = a;
			this.breed = b;
			this.paid = p;
		}
		
		String getLastName(String lastName) {
			String last = this.lastName;
			return last;
		
		}
		
		String getFirstName(String firstName) {
			String first = this.firstName;
			return first;	
		}
		
		Double getWeight(double w) {
			w = this.weight;
			return w;		
		}
		
		int getAge(int a) {
			a = this.age;
			return a;	
		}
		
		String getBreed(String b) {
			b = this.breed;
			return b;
		}
		
		Boolean isPaid() {
			Boolean p = this.paid;
			return p;
		}
		
	}    

public static class Handler {
	// handler info
	private String lastName;
	private String firstName;
	private int employeeID;
	
	// constructor
	public Handler(String ln, String fn, int eID){
		this.lastName = ln;
		this.firstName = fn;
		this.employeeID = eID;
		
	}
	
	String getLast(String lastName) {
		String last = this.lastName;
		return last;
	}
	
	String getFirst() {
		return this.firstName;
	}
		
	int getID(){
		return this.employeeID;
	}
		
}
    

    

    
    
}
    
    
    

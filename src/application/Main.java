package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class Main extends Application {

	int[] arr;
	int[][] cost;
	int[][] direction;

	@Override
	public void start(Stage stage) {
		try {
			// Table screen
			Group group = new Group();
			Scene scene = new Scene(group, 1000, 800);
			stage.setResizable(false);
			stage.setTitle("Lama 1st Project");

			// background
			Rectangle background = new Rectangle(1000, 800);
			group.getChildren().add(background);
			background.setFill(Color.rgb(183, 182, 182));

			// Load screen
			Stage stage1 = new Stage();
			Group group1 = new Group();
			Scene scene1 = new Scene(group1, 550, 180);
			stage1.setTitle("Lama 1st Project");

			// Input file area
			Label qText = new Label("Please upload the file");
			qText.setFont(Font.font("serif", 20));

			Image backGround = new Image(getClass().getResourceAsStream("images.png"));
			ImageView backGround1 = new ImageView(backGround);
			backGround1.setFitHeight(22);
			backGround1.setFitWidth(22);

			Button inputButton = new Button("Choose File");
			inputButton.setGraphic(backGround1);

			Line line = new Line(5, 150, 545, 150);

			Label output = new Label();
			output.setFont(Font.font(13));

			HBox inputBox = new HBox(6);
			inputBox.getChildren().addAll(qText, inputButton, output);
			inputBox.setLayoutX(20);
			inputBox.setLayoutY(50);

			FileChooser choose = new FileChooser();

			inputButton.setOnAction(e -> {
				try {
					File file = choose.showOpenDialog(new Stage());
					arr = readFile(file);
					if (!file.getPath().substring(file.getPath().indexOf(".") + 1).equals("txt")) {
						throw new Exception();
					}

					output.setText("It has been read");
					output.setTextFill(Color.GREEN);

					String str = result(arr);

					HBox resultH = new HBox(20);
					resultH.setLayoutX(50);
					resultH.setLayoutY(80);

					VBox result = new VBox(10);
					Label inputNumber = new Label();
					// Label inputNumber = new Label(" read file " + "\n" +print(arr));
					// inputNumber.setFont(Font.font(20));
					// inputNumber.setStyle("-fx-background-color: #78909C;
					// -fx-border-color:gray;");
					Label in = new Label(" The DP table: ");
					VBox vbox = buildTable(arr);

					int[] result1 = finalReselt(arr.length, arr.length, new ArrayList<>());

					Label l1 = new Label("Number of LED's is ON: ");
					TextField t1 = new TextField("...");
					// HBox hb1 = new HBox(l1, t1);

					// Label l1 = new Label("Number of LED's is ON: ");
					// TextField t1 = new TextField("...");
					t1.clear();
					t1.setText(result1.length + "");
					t1.setStyle("-fx-border-color:#000;");
					t1.setMaxWidth(50);
					// t1.setDisable(true);

					Label l2 = new Label("LEDs that give the expected result");
					TextField t2 = new TextField("...");
					// HBox hb2 = new HBox(l2, t2);

					// Label l2 = new Label("LEDs that give the expected result ");
					// TextField t2 = new TextField("...");

					t2.clear();
					t2.setText(str);
					t2.setStyle("-fx-border-color:#000;");
					t2.setMaxWidth(900);

					VBox color = new VBox(5);

					result.getChildren().addAll(in, vbox, l1, t1, l2, t2, inputNumber);
					resultH.getChildren().addAll(result, color);
					group.getChildren().add(resultH);

					stage.setScene(scene);
					stage1.close();
					stage.show();

				} catch (Exception ee) {

				}
			});

			Button back = new Button("Back");
			back.setLayoutX(20);
			back.setLayoutY(20);

			back.setOnAction(e -> {
				group.getChildren().remove(group.getChildren().size() - 1);
				output.setText("");
				stage.close();
				stage1.show();
			});

			Button close = new Button("Close");
			close.setLayoutX(940);
			close.setLayoutY(750);

			close.setOnAction(e -> {
				stage.close();
			});

			Button artB = new Button("Graph");
			artB.setLayoutX(800);
			artB.setLayoutY(200);
			artB.setStyle("-fx-border-color: black;");
			artB.setOnAction(e -> {
				art();

			});

			// Existing code...

			group.getChildren().addAll(back, close, artB);
			// Existing code...

			stage1.setScene(scene1);
			group1.getChildren().addAll(inputBox, line); // Add the button to the layout
			stage1.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public int[] readFile(File file) {
		ArrayList<Integer> arr = new ArrayList<>();

		try {
			Scanner scan = new Scanner(file);

			while (scan.hasNext()) {
				if (scan.hasNextInt()) {
					arr.add(scan.nextInt());
				} else {
					scan.next(); // Consume the non-integer token
				}
			}

			int[] arr1 = new int[arr.size()];
			for (int i = 0; i < arr1.length; i++)
				arr1[i] = arr.get(i);

			scan.close();
			for (int i = 0; i < arr1.length; i++) {
				System.out.println(arr1[i]);
			}
			return arr1;

		} catch (FileNotFoundException e) {
			System.out.println("File not found exception: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception occurred: " + e.getMessage());
			e.printStackTrace();
		}

		return new int[0];
	}

	public VBox buildTable(int[] arr) {
		int length = arr.length + 1;
		if (arr.length > 100)
			length = 100;

		VBox v = new VBox(5);
		for (int i = 0; i < length + 1; i++) // for row
		{
			HBox h = new HBox(5);
			if (i == 0)
				h.getChildren().add(new Rectangle(20, 20));

			for (int j = 0; j < length + 1; j++) // for column
			{
				Text text = new Text();
				text.setFont(Font.font(15));

				Rectangle r = new Rectangle(20, 20);
				if (i == 0) {
					if (j == length)
						continue;
					text.setText(j + "");
					r.setFill(Color.WHEAT);
					r.setStroke(Color.BLACK);
				} else if (j == 0) {
					if (i == 1)
						text.setText(0 + "");
					else
						text.setText(arr[i - 2] + "");
					r.setFill(Color.WHEAT);
					r.setStroke(Color.BLACK);
				} else {
					String symbol = getSymbol(direction[i - 1][j - 1]);
					text.setText(cost[i - 1][j - 1] + symbol);
					r.setFill(Color.WHEAT);
					r.setStroke(Color.BLACK);

				}

				StackPane stack = new StackPane();
				stack.getChildren().addAll(r, text);

				h.getChildren().add(stack);
			}
			v.getChildren().add(h);
		}

		return v;
	}

	private String getSymbol(int direction) {
		if (direction == 1) {
			return "↑"; // Up
		} else if (direction == 2) {
			return "↖"; // Diagonal
		} else {
			return "←"; // Left
		}
	}

	// _________________
	// _______LCS

	public String result(int[] arr) throws IOException {
		int[] boardL = arr;
		int[] boardS = new int[arr.length];

		for (int i = 0; i < boardS.length; i++)
			boardS[i] = i + 1;

		int value = arr.length;
		cost = new int[value + 1][value + 1];
		direction = new int[value + 1][value + 1];

		for (int i = 0; i < value; i++) {
			cost[i][0] = 0;
			cost[0][i] = 0;
		}

		/*
		 * 1 -> up 2 -> diagonal 3 -> left
		 */
		for (int i = 1; i < value + 1; i++) {
			for (int j = 1; j < value + 1; j++) {
				if (boardL[i - 1] == boardS[j - 1]) {
					cost[i][j] = cost[i - 1][j - 1] + 1;
					direction[i][j] = 2;

				} else {
					if (cost[i][j - 1] >= cost[i - 1][j]) {
						cost[i][j] = cost[i][j - 1];
						direction[i][j] = 3;
					} else {
						cost[i][j] = cost[i - 1][j];
						direction[i][j] = 1;
					} // end else
				} // end big else
			} // end for
		} // end big for

		// Similarity extraction:

		int[] array = finalReselt(value, value, new ArrayList<>());
		String finalResult = Arrays.toString(array);
		return finalResult;
	}// end method

	// _______________________

	// .........................................................................................

	public void art() {
	    Stage stage = new Stage();
	    Group group = new Group();
	    Scene scene = new Scene(group, 1000, 800);

	    scene.setFill(Color.LIGHTBLUE);

	    stage.setResizable(false);

	    int[] arr = this.arr;
	    int[] result = finalReselt(arr.length, arr.length, new ArrayList<>());

	    VBox vbox1 = new VBox(10);
	    vbox1.setLayoutX(260);
	    vbox1.setLayoutY(50);

	    VBox vbox2 = new VBox(10);
	    vbox2.setLayoutX(700);
	    vbox2.setLayoutY(50);

	    ImageView[] imageViews1 = new ImageView[arr.length];
	    ImageView[] imageViews2 = new ImageView[arr.length];

	    // Draw lines when bulbs are on
	    for (int j = 0; j < result.length; j++) {
	        int index = indexOf1(arr, result[j]);
	        if (index != -1) {
	            Line line = new Line();
	            line.setStartX(260 + 25); // Adjust X position based on your layout
	            line.setStartY(50 + (index * 35) + 12.5); // Adjust Y position based on your layout
	            line.setEndX(700); // Adjust X position based on your layout
	            line.setEndY(50 + (result[j] - 1) * 35 + 12.5); // Adjust Y position based on your layout
	            line.setStroke(Color.BLACK);
	            group.getChildren().add(line);
	        }
	    }

	    for (int i = 0; i < arr.length; i++) {
	        Image bulbOffImage = new Image("C:\\Users\\user\\Desktop\\ProjectAlgo\\lama\\src\\application\\ledoff.PNG");
	        Image bulbOnImage = new Image("C:\\Users\\user\\Desktop\\ProjectAlgo\\lama\\src\\application\\ledon.PNG");

	        imageViews1[i] = new ImageView(bulbOffImage);
	        imageViews1[i].setFitWidth(25);
	        imageViews1[i].setFitHeight(25);

	        for (int j = 0; j < result.length; j++) {
	            if (arr[i] == result[j]) {
	                imageViews1[i].setImage(bulbOnImage);
	                break; // Break out of the inner loop once the match is found
	            }
	        }

	        Text text = new Text(arr[i] + "");
	        text.setFont(Font.font("Arial", 12));

	        StackPane stack = new StackPane();
	        stack.getChildren().addAll(imageViews1[i], text);
	        StackPane.setAlignment(text, Pos.CENTER_RIGHT); // Adjust alignment

	        vbox1.getChildren().add(stack);

	        imageViews2[i] = new ImageView(bulbOffImage);
	        imageViews2[i].setFitWidth(25);
	        imageViews2[i].setFitHeight(25);

	        for (int j = 0; j < result.length; j++) {
	            if (i + 1 == result[j]) {
	                imageViews2[i].setImage(bulbOnImage);
	                break; 
	            }
	        }

	        Text text2 = new Text(i + 1 + "");
	        text2.setFont(Font.font("Arial", 12));

	        StackPane stack2 = new StackPane();
	        stack2.getChildren().addAll(imageViews2[i], text2);
	        StackPane.setAlignment(text2, Pos.CENTER_RIGHT); // Adjust alignment

	        vbox2.getChildren().add(stack2);
	    }
	    Button back1 = new Button("Back");
	    back1.setLayoutX(20);
	    back1.setLayoutY(20);

	    back1.setOnAction(e -> {
	        stage.close();
	    });

	    group.getChildren().addAll(back1);
	    group.getChildren().addAll(vbox1, vbox2);

	    stage.setScene(scene);
	    stage.show();
	}

	// find the index of an element in an array
	private int indexOf1(int[] arr, int value) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == value) {
				return i;
			}
		}
		return -1;
	}

	// Final result
	public int[] finalReselt(int i, int j, ArrayList<Integer> result) {
		if (i == 0 || j == 0) {
			Collections.reverse(result); // Reverse the result list
			int[] array = new int[result.size()];
			for (int k = 0; k < result.size(); k++) {
				array[k] = result.get(k);
			}
			return array;
		} else {
			if (direction[i][j] == 2) {
				result.add(arr[i - 1]); // Reverse order
				return finalReselt(i - 1, j - 1, result);
				
			} else if (direction[i][j] == 3) {
				return finalReselt(i, j - 1, result);
			} else {
				return finalReselt(i - 1, j, result);
			}
		}
	}

	public String print(int[] arr) {
		String str = "";
		String str1 = "";

		int l = arr.length;
		if (arr.length > 15)
			l = 15;
		for (int i = 0; i < l; i++) {
			str1 += i + 1 + " ";
			str += arr[i] + " ";
		}
		return str1 + "\n" + str;
	}

}

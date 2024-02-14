package Controllers;

import Application.Context;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class welcomeController {

    @FXML
    private TextField name1;

    @FXML
    private TextField name2;
    @FXML
    private TextField name3;
    @FXML
    private TextField name4;
    @FXML
    private TextField name5;

    public TextField[] name;

    @FXML
    public void initialize(){
        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue.intValue()) {
                case 2:
                    name1.setVisible(false);
                    name2.setVisible(true);
                    name3.setVisible(false);
                    name4.setVisible(true);
                    name5.setVisible(false);
                    break;
                case 3:
                    name1.setVisible(true);
                    name2.setVisible(false);
                    name3.setVisible(true);
                    name4.setVisible(false);
                    name5.setVisible(true);
                    break;
                case 4:
                    name1.setVisible(true);
                    name2.setVisible(true);
                    name3.setVisible(false);
                    name4.setVisible(true);
                    name5.setVisible(true);
                    break;
                case 5:
                    name1.setVisible(true);
                    name2.setVisible(true);
                    name3.setVisible(true);
                    name4.setVisible(true);
                    name5.setVisible(true);
                    break;
            }
        });
        initializeNameArray();

    }
    public void initializeNameArray() {
        name = new TextField[5];
        name[0] = name1;
        name[1] = name2;
        name[2] = name3;
        name[3] = name4;
        name[4] = name5;
    }

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button restartButton;

    @FXML
    private Slider slider;

    @FXML
    private TextField startCash;

    @FXML
    void Begin(MouseEvent event) throws IOException{
        try {
            int i = Integer.parseInt(startCash.getText());
            Context.getInstance().currentGame().setCash(i);
        }
        catch (NumberFormatException nfe)
        {

        }
        for (int i = 0; i < 5; i++){
            if (name[i].getText() != "") {
                Context.getInstance().currentGame().setPlayerName(name[i].getText(), i);
                if (name[i].isVisible()) {
                    Context.getInstance().currentGame().setPlayerActive(i);
                }
            }
        }
        Parent gameParent = FXMLLoader.load(getClass().getResource("../resources/gameTable.fxml"));
        Scene gameScene = new Scene(gameParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(gameScene);
        window.show();
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    void restart(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../resources/welcome.fxml"));
        Scene rootScene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(rootScene);
        window.show();
    }

}

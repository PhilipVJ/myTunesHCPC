/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mytunes.be.User;
import mytunes.gui.model.MTModel;

/**
 * FXML Controller class
 *
 * @author Philip
 */
public class LoginScreenController implements Initializable
{

    @FXML
    private ListView<User> userView;
    @FXML
    private Button newUser;
    @FXML
    private TextField userName;
    @FXML
    private AnchorPane rootPane2;
    @FXML
    private Label loginInfo;
    
    private MTModel mtmodel;
    

    /**
     * Initializes the controller class. 
     */
    
    public LoginScreenController()
    {
        mtmodel = new MTModel();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
              userView.setItems(mtmodel.getUsers());
    }    

    @FXML
    private void userLogin(ActionEvent event) 
    {
        User user = userView.getSelectionModel().getSelectedItem();
        
        if (user==null)
        {
            loginInfo.setText("Please select a user");
        }
        
        else 
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/MyTunes.fxml"));
                Parent root = (Parent)loader.load();
                MyTunesController mController = loader.getController();
                mController.setUser(user);
                mController.setListViews();
                Stage stage = (Stage) rootPane2.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex)
            {
                Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void deleteUser(ActionEvent event) 
    {
        User userToDelete = userView.getSelectionModel().getSelectedItem();
        mtmodel.deleteUser(userToDelete);
      
    }

    @FXML
    private void createUser(ActionEvent event)
    {
        String username = userName.getText();
        if (username.length()==0)
        {
            userName.setText("Please type in a username");
            return;
        }    
        
        mtmodel.addUser(username);
        userName.clear();
       
    }    
}

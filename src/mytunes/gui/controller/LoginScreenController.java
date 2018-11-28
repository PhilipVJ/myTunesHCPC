/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import mytunes.bll.MTManager;
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
    
    private MTModel mtmodel;
    @FXML
    private AnchorPane rootPane2;
    @FXML
    private Label loginInfo;

    /**
     * Initializes the controller class.
     * 
     
     */
    
    public LoginScreenController() throws IOException, SQLException
    {
    mtmodel = new MTModel();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
     
        try
        {
            userView.setItems(mtmodel.getUsers());
        } catch (IOException ex)
        {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

              
      
    }    

    @FXML
    private void userLogin(ActionEvent event) throws IOException, SQLException
    {
        User user = userView.getSelectionModel().getSelectedItem();
        
        if (user==null)
        {
            loginInfo.setText("Please select a user");
        }
        
        else {
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/myTunes.fxml"));
        Parent root = (Parent)loader.load();
        
        MyTunesController mController = loader.getController();
        mController.setUser(user);
        Stage stage = (Stage) rootPane2.getScene().getWindow();   // skriv new stage hvis det skal være i et nyt vindue
        stage.setScene(new Scene(root));
        stage.show();
        
        }
    }

    @FXML
    private void deleteUser(ActionEvent event) throws IOException, SQLException
    {
    User userToDelete = userView.getSelectionModel().getSelectedItem();
    mtmodel.deleteUser(userToDelete);
    userView.setItems(mtmodel.getUsers());// istedet for dette, så prøv at addUser til observablelist inde i modelklassen. Burde virke.
    }

    @FXML
    private void createUser(ActionEvent event) throws IOException, SQLException
    {
    String username = userName.getText();
    mtmodel.addUser(username);
    userName.clear();
    userView.setItems(mtmodel.getUsers()); // istedet for dette, så prøv at addUser til observablelist inde i modelklassen. Burde virke.
    }
    
    
    
}

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            mtmodel = new MTModel();
            ObservableList<User> allUsers = FXCollections.observableArrayList(mtmodel.getUsers());
            userView.setItems(allUsers);
            
        } catch (IOException ex)
        {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(LoginScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
              
      
    }    

    @FXML
    private void userLogin(ActionEvent event)
    {
    }

    @FXML
    private void deleteUser(ActionEvent event)
    {
    }
    
}

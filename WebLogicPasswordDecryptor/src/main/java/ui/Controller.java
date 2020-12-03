package ui;

import core.Decryptor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.security.Security;


public class Controller {

    @FXML
    private TextField text_encryptcode;

    @FXML
    private Button btn_decrypt;

    @FXML
    private TextArea msg_textarea;

    @FXML
    private TextField text_datpath;

    @FXML
    private Button btn_select;

    @FXML
    void selectdatfile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Dat Files", "*.dat"), new ExtensionFilter("All Files", "*.*"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            text_datpath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void decode(ActionEvent event) {
        Security.addProvider(new BouncyCastleProvider());
        String serializedSystemIniPath = text_datpath.getText();
        String ciphertext = text_encryptcode.getText();
        String cleartext = "";
        if (ciphertext.startsWith("{AES}")) {
            ciphertext = ciphertext.replaceAll("^[{AES}]+", "");
            try {
                cleartext = Decryptor.decryptAES(serializedSystemIniPath, ciphertext);
                msg_textarea.clear();
                msg_textarea.setText(cleartext);
            } catch (Exception e) {
                msg_textarea.clear();
                msg_textarea.setText(e.toString());
            }
        }else if (ciphertext.startsWith("{3DES}")) {
            ciphertext = ciphertext.replaceAll("^[{3DES}]+", "");
            try {
                cleartext = Decryptor.decrypt3DES(serializedSystemIniPath, ciphertext);
                msg_textarea.clear();
                msg_textarea.setText(cleartext);
            } catch (Exception e) {
                msg_textarea.clear();
                msg_textarea.setText(e.toString());
            }
        }else {
            msg_textarea.clear();
            msg_textarea.setText("密文输入错误！");
        }

    }
}


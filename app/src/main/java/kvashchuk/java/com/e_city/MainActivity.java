package kvashchuk.java.com.e_city;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

    EditText inputValue = null;
    TextView displayMessage = null;
    TextView inputValueId = null;

    Button toMakeMove;
    Button newGame;
    String newString = null;
    String urlNewGame = "http://mytomcatapp-dergachovda.rhcloud.com/NewGame";
    String urlplayingGame = "http://mytomcatapp-dergachovda.rhcloud.com/String";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputValue = (EditText) findViewById(R.id.enterCity);
        inputValueId = (TextView) findViewById(R.id.gameID);
        displayMessage = (TextView) findViewById(R.id.displayMessage);

        toMakeMove = (Button) findViewById(R.id.toMakeMove);
        newGame = (Button) findViewById(R.id.newGame);

        toMakeMove.setOnClickListener(this);
        newGame.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toMakeMove:

                new Thread(new Runnable() {
                    public void run() {

                        try {

                            final String inputString = inputValue.getText().toString();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    if (inputString.equals("")) {
                                        displayMessage.setText("You don't make move ");
                                    } else {
                                        displayMessage.setText("");
                                    }
                                }
                            });



                            BufferedReader in = theConnection(urlplayingGame, inputString);
                            String returnString = "";
                            while ((returnString = in.readLine()) != null) {
                                newString = returnString;
                            }
                            in.close();

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    if (inputString.equals("")) {
                                        inputValue.setText("");
                                    } else {
                                        inputValue.setText(newString);
                                    }

                                }
                            });

                        } catch (Exception e) {
                            Log.d("Exception", e.toString());
                        }

                    }
                }).start();

                break;
            case R.id.newGame:
                new Thread(new Runnable() {
                    public void run() {

                        try {


                            BufferedReader in = theConnection(urlNewGame, "new Game");
                            String returnString = "";
                            while ((returnString = in.readLine()) != null) {
                                newString = returnString;
                            }
                            in.close();

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    inputValueId.setText(newString);
                                }
                            });

                        } catch (Exception e) {
                            Log.d("Exception", e.toString());
                        }

                    }
                }).start();

                break;

        }
    }

    private BufferedReader theConnection(String urleGame, String inputString) throws IOException {
        URL url = new URL(urleGame);
        URLConnection connection = url.openConnection();
        Log.d("inputString", inputString);
        connection.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
        out.write(inputString);
        out.close();
        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
        //return connection;


    }

}
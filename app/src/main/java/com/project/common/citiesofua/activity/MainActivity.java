package com.project.common.citiesofua.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.project.common.citiesofua.R;
import com.project.common.citiesofua.core.*;

public class MainActivity extends Activity implements View.OnClickListener {

    private Game game;

    private TextView tvLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = Game.getInstance();
        game.setId(loadID());

        Button btnResume = (Button) findViewById(R.id.btnResume);
        Button btnNewGame = (Button) findViewById(R.id.btnNewGame);
        Button btnExit = (Button) findViewById(R.id.btnExit);

        tvLog = (TextView) findViewById(R.id.tvLog);
        tvLog.setText(game.toString());

        btnResume.setOnClickListener(this);
        btnNewGame.setOnClickListener(this);
        btnExit.setOnClickListener(this);

        if (game.getId() > 0) {
            btnResume.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnResume:
                break;

            case R.id.btnNewGame:
                game.erase();

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Connection connection = new Connection(Connection.NEW_GAME_URL);
                            Parameter param = connection.execute("by Dima");
                            game.setId(param.getGameID());

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    saveID();
                                    tvLog.setText(game.toString());
                                    Log.d("OK", game.toString());
                                }
                            });
                        } catch (Exception e) {
                            Log.d(this.getClass().getName() + "/onClick/NewGame:", e.toString());
                        }
                    }
                }).start();

                break;

            case R.id.btnExit:
                finish();
                break;
        }
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveID() {
        SharedPreferences sPref = getSharedPreferences("GAME_ID", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(Game.ID, String.valueOf(game.getId()));
        ed.apply();
    }

    private int loadID() {
        SharedPreferences sPref = getSharedPreferences("GAME_ID", MODE_PRIVATE);
        String savedText = sPref.getString(Game.ID, "");
        if (savedText.isEmpty()) {
            return -1;
        }
        return Integer.valueOf(savedText);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveID();
    }

}

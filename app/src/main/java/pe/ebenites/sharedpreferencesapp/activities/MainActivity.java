package pe.ebenites.sharedpreferencesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import pe.ebenites.sharedpreferencesapp.R;
import pe.ebenites.sharedpreferencesapp.models.User;
import pe.ebenites.sharedpreferencesapp.repositories.UserRepository;

public class MainActivity extends AppCompatActivity {

    private TextView fullnameText, Textfullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullnameText = findViewById(R.id.fullname_text);
        Textfullname = findViewById(R.id.text_fullname);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sp.getString("username", null);
        User user = UserRepository.findByUsername(username);

        if(user != null) {
            fullnameText.setText( user.getFullname() );
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout_item:
                callLogout();
                break;
            case R.id.encuesta_item:
                callEncuesta();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void callLogout() {
        // Eliminar el estado islogged de la SP
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().remove("islogged").commit();

        // Finalizamos
        finish();

        // y si se desea redireccionamos al LoginActivity
        ///startActivity(...);
    }

    private void callEncuesta() {
        Intent intent=new Intent(MainActivity.this, EncuestaActivity.class);
        startActivity(intent);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String fullname = sp.getString("fullname", null);

        if(fullname != null) {
            Textfullname.setText(fullname);
        }
    }
}

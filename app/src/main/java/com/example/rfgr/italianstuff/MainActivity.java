package com.example.rfgr.italianstuff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ShareActionProvider shareActionProvider;

    @Override
    //zaimplementowana w ten sposób metoda spowoduje dodanie do paska akcji wszystkich elementów z podanego pliku zasobów menu
    public boolean onCreateOptionsMenu(Menu menu) {
        //poniżej przygotowuję menu, dodajemy elementy menu do paska akcji
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("Tekst");
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override//zaimplementowana metoda wykona określony kod po kliknięciu wybranego elementu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order: //wywołuję aktywność OrderActivity
                Toast.makeText(this, "Złóż zamówienie", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

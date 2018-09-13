package com.example.rfgr.italianstuff;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

public class MainActivity extends Activity {
    private ShareActionProvider shareActionProvider;
    private String[] titles; //zapisuję tu prywatne zmienne ponieważ będę ich potrzebował w różnych metodach tej klasy
    private ListView drawerList;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles); //odnajduję listę główną szuflady
        drawerList = (ListView) findViewById(R.id.drawer); //odnajduję sam widok szuflady
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //odnajduję layout w którym znajduje się szuflada
        drawerList.setAdapter(new ArrayAdapter<String>(this, //do odnalezionego widoku szuflady wciskam odnalezioną listę główną szuflady,
                android.R.layout.simple_list_item_activated_1, titles)); //a używając ...activated list item, elementy wybrane zostaną podświetlone

        drawerList.setOnItemClickListener(new DrawerItemClickListner()); //dodaję utworzon nasłuchiwacz do widoku listy

        if (savedInstanceState == null) { //jeżeli zapisany stan aktywności wynosił nic to późniejsze metody też zostaną wykonane od niczego
            selectItem(0);
        }
    }

    //poniżej element nasłuchujący klknięcia w listę z szuflady
    private class DrawerItemClickListner implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position); //po wybraniu elementu z listy zostanie wywołany kod metody selectItem
        }
    }

    //poniższa metoda wykorzystuje swich by okreśłić który fragment ma zostać wciśnięty do ramki w ekranie główny by nie tworzyć dodatkowych widoków
    private void selectItem(int position) {
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new PizzaFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction(); //wywołany manager fragmentów
        ft.replace(R.id.content_frame, fragment); //określam gdzie i który fragment ma być wciśnięty
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE); //określam sposób przejścia
        ft.commit(); //wykonaj
        setActionBarTitle(position); //wykorzystam inną metodę by podmienić nazwę na pasku akcji
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //na zakończenie odszukuję layout szuflady
        drawerLayout.closeDrawer(drawerList); //i go zamykam ustępując miejsca nowo wstawionemu fragmentowi
    }

    private void setActionBarTitle(int position) { //metoda umieszczająca nazwę fragmentu w pasku akcji
        String title;
        if (position == 0) { //jeżeli kliknięta pozycja to nic to nazwa pozostaje ta sama
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position]; //w przeciwnym wypadku nazwa równa jest klikniętej pozycji
        }
        getActionBar().setTitle(title); //ustawiamy wybraną nazwę na pasku akcji
    }

    @Override
    //zaimplementowana w ten sposób metoda spowoduje dodanie do paska akcji wszystkich elementów z podanego pliku zasobów menu
    public boolean onCreateOptionsMenu(Menu menu) {
        //poniżej przygotowuję menu, dodajemy elementy menu do paska akcji
        getMenuInflater().inflate(R.menu.menu_main, menu); //określam czym napompować widok
        MenuItem menuItem = menu.findItem(R.id.action_share); //określam że znajdzie się tam również odnośnik do nie moich aktywności (aplikacji)
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("Tekst"); //wykorzystuję inną metodę by przekazać info jakich aktywności (aplikacji) oczekuję
        return super.onCreateOptionsMenu(menu);
    }

    //ta metoda określa czego oczekuję względem obcych aktywności(aplikacji)
    //dzięki temu zostaną wyświetlone jedynie aplikacje mogące spełnić moje zachcianki
    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    //zaimplementowana metoda wykona określony kod po kliknięciu wybranego elementu
    @Override
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

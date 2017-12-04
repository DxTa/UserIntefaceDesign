package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


//TODO build the screen
//- Filling report screen
//        - there is option to take the picture
//        - and fill other details
//        - info:
//        - site name: dropdown list
//        - date the case happened: dropdown list showing relative date (yesterday, 1 week ago, etc)
//        - what was the cause?: dropdown list including predefined options and "other" option showing text area
public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    ArrayList<String> selection = new ArrayList<String>();
    TextView final_text;

    //upload image
    private static final int CAMERA_REQUEST = 1888;
    ImageView mimageView;

    //dropdown
    Spinner mySpiner;
    ArrayAdapter<CharSequence>  myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initNavigationDrawer();

        mimageView = (ImageView) this.findViewById(R.id.image_from_camera);
        ImageButton button = (ImageButton) this.findViewById(R.id.take_image_from_camera);

        final_text =(TextView)findViewById(R.id.final_result);
        final_text.setEnabled(false);

        mySpiner = (Spinner) findViewById(R.id.spinner1);
        myAdapter= ArrayAdapter.createFromResource(this, R.array.names, R.layout.spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpiner.setAdapter(myAdapter);
        mySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+ " selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void selectLeakage(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()){
            case R.id.leakage_blended:
                if(checked){
                    selection.add("Pipe Blended");
                }
                else{
                    selection.remove("Pipe Blended");
                }
                break;
            case R.id.leakage_broken:
                if(checked){
                    selection.add("Pipe Broken");
                }
                else{
                    selection.remove("Pipe Broken");
                }

        }
    }

    public void finalSelection(View view){
        String final_leakage_selection ="";
        for(String Selections: selection){
            final_leakage_selection=final_leakage_selection+Selections + "\n";
        }
        final_text.setText(final_leakage_selection);
        final_text.setEnabled(true);
    }



    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(), "date");
    }
    private void setDate(final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.showDate)).setText(dateFormat.format(calendar.getTime()));
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day){
        Calendar cal = new GregorianCalendar(year, month,day);
        setDate(cal);
    }
    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);

        }
    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.settings:
                        Toast.makeText(getApplicationContext(),"Select site name ",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Select date from calender",Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Check leakage type",Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Or write leakage type ",Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Take image of the pipe  ",Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Finally, send the report ",Toast.LENGTH_LONG).show();
                        break;
                    case R.id.logout:
                        finish();

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    //upload image
    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            mimageView.setImageBitmap(mphoto);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}

package com.iteso.tarea01;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMain extends AppCompatActivity {

    EditText name, phone;
    TextView escolaridad, genero, libro;
    Spinner spinner;
    RadioGroup radioGroup;
    RadioButton radioFem, radioMasc;
    AutoCompleteTextView autoCompleteTextView;
    CheckBox checkBox;
    Button limpiar;

    public class Alumno {
        String nom = "";
        String tel = "";
        String esc = "";
        String gen = "";
        String lib = "";
        String dep = "";


        public Alumno(String nom, String tel, String esc, String gen, String lib, String dep) {
            this.nom = nom;
            this.tel = tel;
            this.esc = esc;
            this.gen = gen;
            this.lib = lib;
            this.dep = dep;
        }

        @Override
        public String toString() {
            return ("Nombre: " + nom + "\nTel: " + tel + "\nEscolaridad: " + esc + "\nGenero: " + gen + "\nLibro Fav: " + lib + "\nPractica Deporte: " + dep);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("MyFirstApp", "onCreate()");

        setContentView(R.layout.activity_main);

        name = findViewById(R.id.activity_linear_nombre);
        phone = findViewById(R.id.activity_linear_telefono);


        escolaridad = findViewById(R.id.activity_linear_escolaridad);
        genero = findViewById(R.id.activity_linear_genero);
        libro = findViewById(R.id.activity_linear_librofav);

        spinner = (Spinner) findViewById(R.id.activity_linear_spinner);
        ArrayAdapter<CharSequence> adapterEscolaridad = ArrayAdapter.createFromResource(this,
                R.array.escolaridad, android.R.layout.simple_spinner_item);
        adapterEscolaridad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterEscolaridad);

        radioGroup = findViewById(R.id.activity_linear_radioGroup);
        radioFem = findViewById(R.id.activity_linear_femenino);
        radioMasc = findViewById(R.id.activity_linear_masculino);


        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.activity_linear_AutoCompleteTextView);
        String[] libros = getResources().getStringArray(R.array.libros);
        ArrayAdapter<String> adapterLibros =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, libros);
        autoCompleteTextView.setAdapter(adapterLibros);


        checkBox = findViewById(R.id.activity_linear_deporteBox);
        limpiar = (Button) findViewById(R.id.activity_relative_limpiar);

        limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);
                builder.setMessage("Seguro deseas limpiar?");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        limpiar();
                        Toast.makeText(ActivityMain.this, "Aceptaste, no hay vuelta atrás", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ActivityMain.this, "Cancelaste", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //This is a comment
        name.setText(savedInstanceState.getString("NAME"));
        phone.setText(savedInstanceState.getString("PHONE"));
    }

    public void save(View v){
        switch (v.getId()){
            case R.id.ic_menu_save:
                String sName = name.getText().toString().trim();
                if(sName.length() == 0){
                    name.setError("Name cannot be left blank!");
                    break;
                }

                Toast.makeText(this,
                        name.getText().toString(),
                        Toast.LENGTH_LONG).show();

                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Alumno alumno;
        name = findViewById(R.id.activity_linear_nombre);
        phone = findViewById(R.id.activity_linear_telefono);
        spinner = findViewById(R.id.activity_linear_spinner);
        autoCompleteTextView = findViewById(R.id.activity_linear_AutoCompleteTextView);
        radioGroup = findViewById(R.id.activity_linear_radioGroup);
        Button gender = findViewById(radioGroup.getCheckedRadioButtonId());
        checkBox = findViewById(R.id.activity_linear_deporteBox);
        String practicaDeporte;

        if(checkBox.isChecked())
                practicaDeporte = "Sí practica";
            else
                practicaDeporte = "No practica";


        switch (item.getItemId()){
            case R.id.ic_menu_save:
                alumno = new Alumno(name.getText().toString(), phone.getText().toString(), spinner.getSelectedItem().toString(),
                        gender.getText().toString(), autoCompleteTextView.getText().toString(), practicaDeporte);
                Toast toastAlumno = Toast.makeText(this,
                        alumno.toString(),
                        Toast.LENGTH_LONG);
                toastAlumno.show();
                limpiar();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void limpiar (){
        spinner = findViewById(R.id.activity_linear_spinner);

        name.setText("");
        phone.setText("");
        spinner.setSelection(0);
        autoCompleteTextView.setText("");
        radioFem.setChecked(true);
        radioMasc.setChecked(false);
        checkBox.setChecked(false);
    }

}

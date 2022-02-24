package com.example.libri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLHelper;
import helpers.Login;

//gerencia todas as activities
public class MainActivity extends AppCompatActivity {

    private EditText txtLogin;
    private EditText txtSenha;
    private Button btnLogar;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);
        btnLogar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrarUsuario);

        // Direcionamento para a tela de cadastro
        btnCadastrar.setOnClickListener(view->{
            Intent telaCadastro = new Intent(
                    MainActivity.this,
                    CadastroUsuario.class
            );
            startActivity(telaCadastro);
        });

        // ou: startActivity(new Intent(
        //                    MainActivity.this,
        //                    CadastroUsuario.class
        //            ));

        btnLogar.setOnClickListener(view->{

            String login = txtLogin.getText().toString();
            String senha = txtSenha.getText().toString();

            int cod_usuario = SQLHelper.getInstance(this).login(login, senha);

            if (cod_usuario > 0) {
                Log.d("TESTE-", "IF");
                Login.setCod_usuario(cod_usuario);

                startActivity(new Intent(MainActivity.this,
                        FeedLivros.class));

            } else {
                Toast.makeText(MainActivity.this, "Dados de login incorretos.", Toast.LENGTH_LONG).show();
            }

        });

    }
}
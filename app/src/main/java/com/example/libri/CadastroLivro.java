package com.example.libri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLHelper;
import helpers.DateFormat;

public class CadastroLivro extends AppCompatActivity {

    private EditText txtTitulo;
    private EditText txtDescricao;
    private EditText txtFoto;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        txtTitulo = findViewById(R.id.txtTituloLivro);
        txtDescricao = findViewById(R.id.txtLivroDescricao);
        txtFoto = findViewById(R.id.txtLivroFoto);
        btnCadastrar = findViewById(R.id.btnCadastrarLivro);

        btnCadastrar.setOnClickListener(view->{

            /** PROCESSO DE GRAVAÇÃO DO USUÁRIO **/      //cria um "modal de aviso"
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.titulo_cadastro_livro))
                    .setMessage(getString(R.string.mensagem_cadastro_livro))
                    .setPositiveButton(R.string.salvar, (dialog1, which)->{
                        /** AÇÃO DO POSITIVE BUTTON **/
                        String titulo = txtTitulo.getText().toString();
                        String descricao = txtDescricao.getText().toString();
                        String foto = txtFoto.getText().toString();

                        /** DATA DE INSERÇÃO DO LIVRO **/
                        DateFormat dataInsercao = new DateFormat();
//                        String created_date = dataInsercao.getDateFormat();

                        //cria a conexão e cadastra um novo usuário, inserindo seus dados
                        boolean cadastroLivro = SQLHelper.getInstance(CadastroLivro.this)
                                .addBook(1, titulo, descricao, foto,
                                        dataInsercao.getDateFormat());

                        if (cadastroLivro) {

                            Toast.makeText(this, "Cadastro realizado com sucesso!",
                                    Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(this, "Houve um erro ao realizar o cadastro do livro.",
                                    Toast.LENGTH_LONG).show();

                        }
                    })
                    .setNegativeButton(R.string.cancelar, (dialog1, which)->{}).create();

            dialog.show();

        });

    }

    /** INFLATE DO MENU **/
    // A classe mãe cria um menu vazio e nós passamos o que criamos e o inflamos, expandimos
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu); //pega o menu e infla-lo, exibe-o
        return super.onCreateOptionsMenu(menu);
    }

    /** AÇÕES DO MENU **/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Log.d("MENUITEM-", String.valueOf(item.getItemId()));

        switch (item.getItemId()) {

            case R.id.menu_cadastrar_livro:
                startActivity(new Intent(CadastroLivro.this,
                        CadastroLivro.class));
                break;

            case R.id.menu_feed_livro:
                startActivity(new Intent(CadastroLivro.this,
                        FeedLivros.class));
                break;

            case R.id.menu_sair:
                startActivity(new Intent(CadastroLivro.this,
                        MainActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    private boolean validate() {

        return(
                txtTitulo.getText().toString().isEmpty() ||
                        txtDescricao.getText().toString().isEmpty() ||
                        txtFoto.getText().toString().isEmpty()
        );

    }

}
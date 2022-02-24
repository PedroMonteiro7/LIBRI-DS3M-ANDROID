package com.example.libri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLHelper;
import helpers.DateFormat;

public class CadastroUsuario extends AppCompatActivity {

    /** REPRESENTAÇÃO DOS CAMPOS DA ACTIVITY **/
    private EditText txtNome;
    private EditText txtSobrenome;
    private EditText txtEmail;
    private EditText txtLogin;
    private EditText txtSenha;
    private Button btnCadastrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        /** CAPTURA DOS COMPONENTES GRÁFICOS DA ACTIVITY **/
        txtNome = findViewById(R.id.txtNome);
        txtSobrenome = findViewById(R.id.txtSobrenome);
        txtEmail = findViewById(R.id.txtEmail);
        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);
        btnCadastrarUsuario = findViewById(R.id.btnCadastrarUsuario);

        /** TRATAMENTO DO EVENTO DE CLIQUE DO BOTÃO **/
        btnCadastrarUsuario.setOnClickListener(view->{ //recebe um objeto de view com função lambda (anônima que não recebe nome porque é chamada automaticamente)
            if (validate()) {
                Toast.makeText(this, "PREENCHA TODOS OS CAMPOS.", Toast.LENGTH_SHORT).show();
                //onde            mensagem        duração
                return;
            }

            /** PROCESSO DE GRAVAÇÃO DO USUÁRIO **/      //cria um "modal de aviso"
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.titulo_cadastro_usuario))
                    .setMessage(getString(R.string.mensagem_cadastro_usuario))
                    .setPositiveButton(R.string.salvar, (dialog1, which)->{
                        /** AÇÃO DO POSITIVE BUTTON **/
                        String nome = txtNome.getText().toString();
                        String sobrenome = txtSobrenome.getText().toString();
                        String email = txtEmail.getText().toString();
                        String login = txtLogin.getText().toString();
                        String senha = txtSenha.getText().toString();

                        /** DATA DE INSERÇÃO DO USUÁRIO **/
                        DateFormat dataInsercao = new DateFormat();
//                        String created_date = dataInsercao.getDateFormat();

                        //cria a conexão e cadastra um novo usuário, inserindo seus dados
                        boolean cadastroUsuario = SQLHelper.getInstance(this)
                                .addUser(nome, sobrenome, email, login, senha,
                                        dataInsercao.getDateFormat());

                        if (cadastroUsuario) {

                            Toast.makeText(this, "Cadastro realizado com sucesso!",
                                    Toast.LENGTH_LONG).show();

                        } else {

                            Toast.makeText(this, "Houve um erro ao realizar o cadastro de usuário.",
                                    Toast.LENGTH_LONG).show();

                        }
                    })
                    .setNegativeButton(R.string.cancelar, (dialog1, which)->{}).create();

            dialog.show();

        });

    }

    private boolean validate() {

        return(
                txtNome.getText().toString().isEmpty() ||
                txtSobrenome.getText().toString().isEmpty() ||
                txtEmail.getText().toString().isEmpty() ||
                txtLogin.getText().toString().isEmpty() ||
                txtSenha.getText().toString().isEmpty()
        );

    }

}
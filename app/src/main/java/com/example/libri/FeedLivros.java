package com.example.libri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class FeedLivros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_livros);
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
                startActivity(new Intent(FeedLivros.this,
                        CadastroLivro.class));
                break;

            case R.id.menu_feed_livro:
                startActivity(new Intent(FeedLivros.this,
                        FeedLivros.class));
                break;

            case R.id.menu_sair:
                startActivity(new Intent(FeedLivros.this,
                        MainActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);

    }

}
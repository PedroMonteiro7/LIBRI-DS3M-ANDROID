package com.example.libri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import database.SQLHelper;
import model.Item;
import model.Livro;

public class FeedLivros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_livros);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<Item> item = SQLHelper.getInstance(this).listBook();

        recyclerView.setAdapter(new LivroAdapter(item));

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

    /** ADAPTER DO RECYCLER VIEW **/
    class LivroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        //ATRIBUTO QUE RECEBE OS OBJETOS DE "ITEMS"
        public List<Item> item;

        //CONSTRUTOR DA CLASSE LivroAdapter
        public LivroAdapter(List<Item> item) {

            this.item = item;

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //chama a classe de criação do ViewHolder
            if (viewType == 0) {

                return new LivroAdapter.LivroViewHolder(
                        LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.item_container_livro,
                                        parent,
                                        false));

            }

            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            if (getItemViewType(position) == 0) {
                //TIPO LIVRO

                //cria um objeto de livro, pegando seu conteúdo através de sua posição
                Livro livro = (Livro) item.get(position).getObject();
                ((LivroAdapter.LivroViewHolder) holder).setLivroData(livro);

            }

        }

        /** MÉTODO AUXILIAR DE MANIPULAÇÃO DE POSITION PARA O MÉTODO onBindViewHolder **/
        public int getItemViewType(int position) {
            return item.get(position).getType();
        }

        @Override
        public int getItemCount() {
            return item.size();
        }

        /** VIEWHOLDER **/
        private class LivroViewHolder extends RecyclerView.ViewHolder {

            private TextView textLivroTitulo, textLivroDescricao;
            private int cod_livro;

            public LivroViewHolder(@NonNull View itemView) {
                super(itemView);

                textLivroTitulo = itemView.findViewById(R.id.textLivroTitulo);
                textLivroDescricao = itemView.findViewById(R.id.textLivroDescricao);

            } // fim do construtor

            /** MÉTODO DE SET DE DADOS NAS TEXTVIWES **/
            public void setLivroData(Livro livro){

                textLivroTitulo.setText(livro.getTitulo());
                textLivroDescricao.setText(livro.getDescricao());

            }// fim da classe viewholder

        }

    }

}
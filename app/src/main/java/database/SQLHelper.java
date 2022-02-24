package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

// Manipula o BD
public class SQLHelper extends SQLiteOpenHelper {

    /** ATRIBUTOS DA CLASSE DE CONNECTION **/

    // atributo visível só dentro da classe (private), importante e sempre vai ser usado e não pode ter o seu valor alterado
    private static final String DB_NAME = "libri";
//    Eu preciso disso toda hora? Se sim, torne o atributo static (define a disponibilidade) para chamá-lo a qualquer momento para não precisar criar objeto

    private static final int DB_VERSION = 2;
    private static SQLHelper INSTANCE; //objeto da própria classe para garantir somente uma conexão para cada usuário

    // Método para verificar se a conexão está aberta
    public static SQLHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SQLHelper(context); //cria uma conexão, caso não haja
        }
        return INSTANCE; //retorna essa instância
    }

    // Método construtor: Recebe os valores iniciais de abertura da conexão
    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
//        super referencia o construtor da classe herdada (nesse caso, a SQLiteOpenHelper)
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE tbl_usuario" +
                                    "(cod_usuario INTEGER PRIMARY KEY," +
                                    "nome TEXT," +
                                    "sobrenome TEXT," +
                                    "email TEXT," +
                                    "login TEXT," +
                                    "senha TEXT," +
                                    "created_date DATETIME)"); // created_date guardará dd/mm/aaaa hh:ss da criação da conexão

        Log.d("SQLITE-", "BANCO DE DADOS CRIADO! - " + DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("CREATE TABLE tbl_livro" +
                "(cod_livro INTEGER PRIMARY KEY," +
                "cod_usuario INTEGER," +
                "titulo TEXT," +
                "descricao TEXT," +
                "foto TEXT," +
                "created_date DATETIME," +
                "FOREIGN KEY (cod_usuario) REFERENCES tbl_usuario(cod_usuario))"); // created_date guardará dd/mm/aaaa hh:ss da criação da conexão

        Log.d("SQLITE-", "BANCO DE DADOS CRIADO! - " + DB_VERSION);

    }

    /** INSERÇÃO DE USUÁRIOS **/
    public boolean addUser(String nome, String sobrenome, String email, String login, String senha, String created_date) {

        // Configura o SQLite para escrita:
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
                                            //método que habilita a escrita

        try { //tentar

            sqLiteDatabase.beginTransaction(); //inicia a "conversa" com o B.D.

            ContentValues values = new ContentValues();

            // chave (mesmo nome do atributo criado) e valor
            values.put("nome", nome);
            values.put("sobrenome", sobrenome);
            values.put("email", email);
            values.put("login", login);
            values.put("senha", senha);
            values.put("created_date", created_date);

            // insere ou gera um erro                       //se não fosse inserir em todas as colunas
            sqLiteDatabase.insertOrThrow("tbl_usuario", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return true;

                //objeto da classe Exception
        } catch (Exception e){ //se tiver, captura e trata o erro

            Log.d("SQLITE-", e.getMessage());
            return false;

        } finally {  //finaliza a conexão (sempre é executado, passando ou não pelo catch)

            //o B.D está aberto? Se sim, finalize
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }

        }

    }

    /** INSERÇÃO DE LIVROS **/
    public boolean addBook(int cod_usuario, String titulo, String descricao, String foto, String created_date) {

        // Configura o SQLite para escrita:
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //método que habilita a escrita

        int cod_livro = 0;

        try { //tentar

            sqLiteDatabase.beginTransaction(); //inicia a "conversa" com o B.D.

            ContentValues values = new ContentValues();

            // chave (mesmo nome do atributo criado) e valor
            values.put("cod_usuario", cod_usuario);
            values.put("titulo", titulo);
            values.put("descricao", descricao);
            values.put("foto", foto);
            values.put("created_date", created_date);

            // insere ou gera um erro                       //se não fosse inserir em todas as colunas
            sqLiteDatabase.insertOrThrow("tbl_livro", null, values);
            sqLiteDatabase.setTransactionSuccessful();

            return true;

            //objeto da classe Exception
        } catch (Exception e){ //se tiver, captura e trata o erro

            Log.d("SQLITE-", e.getMessage());
            return false;

        } finally {  //finaliza a conexão (sempre é executado, passando ou não pelo catch)

            //o B.D está aberto? Se sim, finalize
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.endTransaction();
            }

        }

    }

    /** REALIZAR LOGIN **/
    public int login(String login, String senha) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

                                     //permite escrever o comando SQL
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM tbl_usuario WHERE login = ? AND senha = ?",
                new String[]{login, senha}
        );

//
        try {

            if (cursor.moveToFirst()) {
                int cod_usuario = cursor.getInt(cursor.getColumnIndex("cod_usuario"));
                return cod_usuario;
            }
            return 0;

        } catch (Exception e) {

            Log.d("SQLITE-", e.getMessage());

        } finally {

            if (cursor != null && !cursor.isClosed()) {

                cursor.close();

            }

        }
        return 0;

    }

}

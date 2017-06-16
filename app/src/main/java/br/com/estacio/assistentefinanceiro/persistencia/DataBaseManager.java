package br.com.estacio.assistentefinanceiro.persistencia;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseManager extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "teste.db";
    public static final String TABELA = "lancamentos";

    public static final String ID = "_id";
    public static final String CATEGORIA = "categoria";
    public static final String SUBCATEGORIA = "subcategoria";
    public static final String DESCRICAO = "descricao";
    public static final String VALOR = "valor";
    public static final String DATA = "data";
    public static final int VERSAO = 1;

    public DataBaseManager(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    public DataBaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DataBaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABELA
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CATEGORIA + " TEXT, "
                + SUBCATEGORIA + " TEXT, "
                + DESCRICAO + " TEXT, "
                + VALOR + " FLOAT, "
                + DATA + " DATE"
                + ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        onCreate(db);
    }

}

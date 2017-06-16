package br.com.estacio.assistentefinanceiro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.estacio.assistentefinanceiro.entity.Lancamento;
import br.com.estacio.assistentefinanceiro.persistencia.DataBaseManager;

public class LancamentoDAO {

    private SQLiteDatabase db;
    private DataBaseManager banco;

    public LancamentoDAO(Context context) {
        banco = new DataBaseManager(context);
    }

    public boolean insert(Lancamento lancamento) {
        db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(banco.CATEGORIA, lancamento.getCategoria());
        valores.put(banco.SUBCATEGORIA, lancamento.getSubcategoria());
        valores.put(banco.DESCRICAO, lancamento.getDescricao());
        valores.put(banco.VALOR, lancamento.getValor());
        valores.put(banco.DATA, lancamento.getData());

        long result = db.insert(banco.TABELA, null, valores);
        db.close();

        return result == -1? false : true;
    }

    public List<Lancamento> selectAll() {
        Cursor cursor;

        String[] campos = {banco.ID, banco.CATEGORIA, banco.SUBCATEGORIA,
                banco.DESCRICAO, banco.VALOR, banco.DATA};

        db = banco.getReadableDatabase();

        cursor = db.query(banco.TABELA, campos, null, null, null, null, "data DESC");

        List<Lancamento> listaLancamentos = new ArrayList<>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Lancamento lancamento = new Lancamento();
                lancamento.setCategoria(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseManager.CATEGORIA)));
                lancamento.setSubcategoria(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseManager.SUBCATEGORIA)));
                lancamento.setDescricao(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseManager.DESCRICAO)));
                lancamento.setData(cursor.getString(cursor.getColumnIndexOrThrow(DataBaseManager.DATA)));
                lancamento.setValor(cursor.getFloat(cursor.getColumnIndexOrThrow(DataBaseManager.VALOR)));
                listaLancamentos.add(lancamento);
            } while (cursor.moveToNext());
        }

        return listaLancamentos;
    }

    public float selectTotalLancamentos(String categoria) {
        String query = "SELECT SUM(valor) FROM lancamentos WHERE categoria = ?;";
        db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{categoria});

        float total = 0;

        if (cursor.moveToFirst())
            total = cursor.getFloat(0);

        return total;
    }
}

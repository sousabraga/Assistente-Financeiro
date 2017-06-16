package br.com.estacio.assistentefinanceiro.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.com.estacio.assistentefinanceiro.R;
import br.com.estacio.assistentefinanceiro.dao.LancamentoDAO;
import br.com.estacio.assistentefinanceiro.entity.Lancamento;

public class LancamentosActivity extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamentos);

        LancamentoDAO lancamentoDAO = new LancamentoDAO(getBaseContext());
        List<Lancamento> listaLancamentos = lancamentoDAO.selectAll();

        ArrayAdapter<Lancamento> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listaLancamentos);

        lista = (ListView) findViewById(R.id.lista);
        lista.setAdapter(adapter);
    }

    public void novoLancamento(View view) {
        startActivity(new Intent(this, CadastroLancamentoActivity.class));
    }
}

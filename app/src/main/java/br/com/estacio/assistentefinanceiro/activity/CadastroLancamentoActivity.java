package br.com.estacio.assistentefinanceiro.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import br.com.estacio.assistentefinanceiro.R;
import br.com.estacio.assistentefinanceiro.dao.LancamentoDAO;
import br.com.estacio.assistentefinanceiro.entity.Lancamento;

public class CadastroLancamentoActivity extends AppCompatActivity {

    private int ano, mes, dia;
    private Button dataNovoLancamento;
    private Spinner subcategoria;
    private RadioGroup radioGroup;
    private EditText descricao;
    private EditText valor;

    private LancamentoDAO lancamentoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_lancamento);
        lancamentoDAO = new LancamentoDAO(this.getBaseContext());


        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        dataNovoLancamento = (Button) findViewById(R.id.dataLancamento);
        dataNovoLancamento.setText(dia + "/" + (mes + 1) + "/" + ano);

        subcategoria = (Spinner) findViewById(R.id.categoria);
        subcategoria.setEnabled(false);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        descricao = (EditText) findViewById(R.id.descricao);
        valor = (EditText) findViewById(R.id.valor);
    }

    public void salvarLancamento(View view) {
        Lancamento lancamento = new Lancamento();

        RadioButton radioButton = null;

        for (int i = 0;i < radioGroup.getChildCount(); i++) {
            radioButton = ((RadioButton)radioGroup.getChildAt(i));
            if (radioButton.isChecked())
                break;
        }


        lancamento.setCategoria(radioButton.getText().toString());
        lancamento.setSubcategoria(subcategoria.getSelectedItem().toString());
        lancamento.setDescricao(descricao.getText().toString());
        lancamento.setValor(Float.parseFloat(valor.getText().toString()));
        lancamento.setData(dataNovoLancamento.getText().toString());

        boolean result = lancamentoDAO.insert(lancamento);

        if (result) {
            Toast.makeText(this, "Lançamento cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LancamentosActivity.class));
        } else {
            Toast.makeText(this, "Cadastro falhou!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void selecionarCategoria(View view) {
        RadioButton radio = (RadioButton) view;
        ArrayAdapter<CharSequence> adapter = null;

        if ("Receitas".equals(radio.getText()))
            adapter = ArrayAdapter.createFromResource(this, R.array.categoria_receita, android.R.layout.simple_spinner_item);
        else if ("Despesas".equals(radio.getText()))
            adapter = ArrayAdapter.createFromResource(this, R.array.categoria_despesa, android.R.layout.simple_spinner_item);
        if ("Investimentos".equals(radio.getText()))
            adapter = ArrayAdapter.createFromResource(this, R.array.categoria_investimento, android.R.layout.simple_spinner_item);

        subcategoria.setAdapter(adapter);
        subcategoria.setEnabled(true);
    }

    public void selecionarData(View view) {
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (R.id.dataLancamento == id)
            return new DatePickerDialog(this, listener, ano, mes, dia);
        return null;
    }

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;

            dataNovoLancamento.setText(dia + "/" + (mes + 1) + "/" + ano);
        }
    };

}

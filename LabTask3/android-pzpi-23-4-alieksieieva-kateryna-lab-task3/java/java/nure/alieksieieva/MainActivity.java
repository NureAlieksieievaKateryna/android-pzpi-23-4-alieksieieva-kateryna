package java.nure.alieksieieva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView resultTextView;
    private StringBuilder input = new StringBuilder();
    private double firstOperand = 0;
    private String operator = "";
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.resultTextView);


        setButtonListeners();
    }
    private void clearInput() {

        input.setLength(0);
        firstOperand = 0;
        operator = "";
        isNewOperation = true;
        resultTextView.setText("0");
    }
    private void setButtonListeners() {
        int[] numberButtons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9
        };

        View.OnClickListener numberClickListener = view -> {
            if (isNewOperation) {
                input.setLength(0);
                isNewOperation = false;
            }
            Button button = (Button) view;
            input.append(button.getText());
            resultTextView.setText(input.toString());
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(numberClickListener);
        }

        findViewById(R.id.btnPlus).setOnClickListener(view -> setOperator("+"));
        findViewById(R.id.btnMinus).setOnClickListener(view -> setOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(view -> setOperator("×"));
        findViewById(R.id.btnDivide).setOnClickListener(view -> setOperator("÷"));

        findViewById(R.id.btnEqual).setOnClickListener(view -> calculateResult());

        findViewById(R.id.btnClear).setOnClickListener(view -> {
            input.setLength(0);
            firstOperand = 0;
            operator = "";
            isNewOperation = true;
            resultTextView.setText("0");
        });
    }

    private void setOperator(String op) {
        if (!input.toString().isEmpty()) {
            firstOperand = Double.parseDouble(input.toString());
            operator = op;
            isNewOperation = true;
        }
    }

    private void calculateResult() {
        if (!input.toString().isEmpty() && !operator.isEmpty()) {
            double secondOperand = Double.parseDouble(input.toString());
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "×":
                    result = firstOperand * secondOperand;
                    break;
                case "÷":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        resultTextView.setText("Error");
                        return;
                    }
                    break;
            }
            resultTextView.setText(String.valueOf(result));
            input.setLength(0);
            input.append(result);
            operator = "";
            isNewOperation = true;
        }
    }
}
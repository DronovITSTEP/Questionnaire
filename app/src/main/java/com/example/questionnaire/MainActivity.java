package com.example.questionnaire;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int totlaScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SeekBar salary = findViewById(R.id.seek_salary);
        TextView textSalary = findViewById(R.id.textView);
        TextView result = findViewById(R.id.result_test);

        salary.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
         {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textSalary.setText(progress + " руб.");

                /*int seekBarWidth = seekBar.getWidth();

                float progressPercent = (float) progress / seekBarWidth;
                int x = (int) (seekBarWidth * progressPercent);

                textSalary.setX(seekBar.getX() + x - textSalary.getWidth()/2);*/
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        setRandomRadioButton();

        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(v -> {

            result.setVisibility(View.VISIBLE);

            EditText fio = findViewById(R.id.input_fio);
            String fio_text = fio.getText().toString();
            if (fio_text.isEmpty()) {
                result.setText("Укажите ФИО");
                return;
            }

            EditText age = findViewById(R.id.age_input);
            if (age.getText().toString().isEmpty()) {
                result.setText("Укажите возраст");
                return;
            }
            int age_number = Integer.parseInt(age.getText().toString());
            if (age_number < 18 || age_number >= 60) {
                result.setText("Не подходите по возрасту");
                return;
            }

            totlaScore = 0;

            calculateScore();

            if (totlaScore >= 10) {
                result.setText("Вы успешно прошли");
                result.setTextColor(Color.GREEN);
            }
            else  {
                result.setText("Вы не сдали тест");
                result.setTextColor(Color.RED);
            }

        });
    }

    private  void calculateScore() {
        RadioGroup question = findViewById(R.id.question_1);
        setTotalScore(question);

        question = findViewById(R.id.question_2);
        setTotalScore(question);

        question = findViewById(R.id.question_3);
        setTotalScore(question);

        question = findViewById(R.id.question_4);
        setTotalScore(question);

        question = findViewById(R.id.question_5);
        setTotalScore(question);

        CheckBox checkBox = findViewById(R.id.experience_check);
        if (checkBox.isChecked()) {
            totlaScore += 2;
        }
        checkBox = findViewById(R.id.businessTrip_check);
        if (checkBox.isChecked()) {
            totlaScore += 1;
        }

        checkBox = findViewById(R.id.teamDev_check);
        if (checkBox.isChecked()) {
            totlaScore += 1;
        }

    }
    private void setTotalScore(RadioGroup question) {
        int selectedRadio = question.getCheckedRadioButtonId();
        RadioButton answer = findViewById(selectedRadio);
        if (answer != null
                && answer.getTag() != null
                && (boolean)answer.getTag()) {
            totlaScore += 2;
        }
    }

    private void setRandomRadioButton() {
        RadioGroup question = findViewById(R.id.question_1);
        setTagForRadioButton(question, 0);

        question = findViewById(R.id.question_2);
        setTagForRadioButton(question, 1);

        question = findViewById(R.id.question_3);
        setTagForRadioButton(question, 2);

        question = findViewById(R.id.question_4);
        setTagForRadioButton(question, 3);

        question = findViewById(R.id.question_5);
        setTagForRadioButton(question, 0);
    }

    private void setTagForRadioButton(RadioGroup question, int rand) {
        RadioButton[] radioButtons = new RadioButton[question.getChildCount()];

        for (int i = 0; i < question.getChildCount(); i++) {
            var child = question.getChildAt(i);
            if (child instanceof RadioButton) {
                radioButtons[i] = (RadioButton) child;
            }
        }

        RadioButton radioButton = radioButtons[rand];
        radioButton.setTag(true);
    }
}
package com.purutal.dateconverteractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.purutal.dateconverteractivity.DateConverter.DateConverter;
import com.purutal.dateconverteractivity.DateConverter.Model;
import com.purutal.dateconverteractivity.JsonUtils.JsonConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Context context;
    EditText etYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        btn = findViewById(R.id.b_create);
        etYear = findViewById(R.id.et_date);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = etYear.getText().toString();
                if (date.isEmpty()) {
                    etYear.setError("Cannot be empty");
                    return;
                }
                int dateInt;

                try {
                    dateInt = Integer.parseInt(date);
                    if (dateInt > 2090 || dateInt < 1971) {
                        Toast.makeText(context, "Can only be between 1971 and 2090", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "Enter Valid Year", Toast.LENGTH_SHORT).show();
                    return;
                }


                new CreateDateTask(btn).execute(dateInt);
            }
        });

    }

    private class CreateDateTask extends AsyncTask<Integer, Void, String> {
        Button btn;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


        public CreateDateTask(Button btn) {
            this.btn = btn;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btn.setEnabled(false);
            btn.setText("Generating files...");
        }

        @Override
        protected String doInBackground(Integer... ints) {
            List<Model> dateModels = new ArrayList<>();
            DateConverter dc = new DateConverter();
            Model model = new Model(ints[0], 1, 1);
            Calendar calendar = dc.getEnglishDate(model);

            Model datemodel = dc.getNepaliDate(calendar);
            datemodel.setEngDate(formatter.format(calendar.getTime()));
            while (datemodel.getYear() == ints[0]) {
                dateModels.add(datemodel);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                datemodel = dc.getNepaliDate(calendar);
                datemodel.setEngDate(formatter.format(calendar.getTime()));
            }

            String jsonString = JsonConverter.toJSon(dateModels);
            Log.i("string", "doInBackground: " + jsonString);

            return create(String.valueOf(ints[0]) + ".json", jsonString);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            btn.setEnabled(true);
            btn.setText("Generate");
            etYear.setText("");
            if (s != null)
                Toast.makeText(MainActivity.this, "File generated at " + s, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

        }
    }

    private String create(String fileName, String jsonString) {

        try {
            FileOutputStream fos = new FileOutputStream(new File(context.getExternalFilesDir(null).getAbsolutePath() + "/" + fileName));
            if (jsonString != null) {
                fos.write(jsonString.getBytes());
            }
            fos.close();
            return context.getExternalFilesDir(null).getAbsolutePath() + "/" + fileName;
        } catch (FileNotFoundException fileNotFound) {
            return null;
        } catch (IOException ioException) {
            return null;
        }

    }

}

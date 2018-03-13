package com.example.anna.justcoffee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int quantity;
    int price = 5;
    int whippedCream = 1;
    int chocolate = 2;
    int pricetopay;

    TextView quantityTextView;
    TextView orderSummaryTextView;
    Button b1;
    ImageView iw;

    EditText nameSpace;
    String name;
    boolean choiceWhippedCream;
    boolean choiceChocolate;
    CheckBox choiceBoxWhippedCream;
    CheckBox choiceBoxChocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantityTextView = findViewById(R.id.quantity_text_view);


        choiceBoxWhippedCream = findViewById(R.id.notify_me_checkbox);
        choiceBoxChocolate = findViewById(R.id.notify_me_checkbox_chocolate);

        Button startBtn = findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();


            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("quan", quantity);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        quantity = savedInstanceState.getInt("quan");
        quantityTextView.setText(String.valueOf(quantity));

    }

    /**
     * This method is called when the order button is clicked.
     */

    public void increment(View view) {
        quantity++;
        if (quantity >= 100) {
            Toast.makeText(getApplicationContext(), R.string.checkyourorder, Toast.LENGTH_LONG).show();
        }
        quantityTextView.setText(String.valueOf(quantity));

        // priceTextView.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(price1)));
    }

    public void decriment(View view) {

        quantity--;
        if (quantity <= 0) {
            quantity = 0;
            Toast.makeText(getApplicationContext(), R.string.checkyourorderagain, Toast.LENGTH_LONG).show();
        }
        quantityTextView.setText(String.valueOf(quantity));

    }


    protected void sendEmail() {
        EditText nameSpace = findViewById(R.id.name_view);
        String name = nameSpace.getText().toString();
        String emailSubject = getString(R.string.coffeeorder) + name;
        String summaryMessage = createOrderSummary(pricetopay);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(getString(R.string.mailto)));
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, summaryMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    private void submitOrder() {
        int payment = calculatePrice(quantity, price);

    }

    //method calculates final price
    private int calculatePrice(int quantity, int price) {
        if (choiceBoxWhippedCream.isChecked()) {
            price++;
        }
        if (choiceBoxChocolate.isChecked()) {
            price += 2;
        }
        pricetopay = quantity * price;
        return pricetopay;

    }

    private String createOrderSummary(int finalPrice) {

        //finding checkboxes and checking if they are checked

        choiceWhippedCream = choiceBoxWhippedCream.isChecked();
        choiceChocolate = choiceBoxChocolate.isChecked();

        // finding name space and getting the name
        EditText nameSpace = findViewById(R.id.name_view);
        String name = nameSpace.getText().toString();
      /* if (choiceWhippedCream == true) {
            Log.v("Main Activity", "Add Whipped Cream" + finalPrice);

    }
    else {Log.v("Main Activity", "Don't Add Whipped Cream"+finalPrice);}*/
        //returning the order summary

        return (getString(R.string.name1) + name + getString(R.string.whippedcream) + choiceWhippedCream + getString(R.string.chocolatetopping) + choiceChocolate + "\nQuantity: " + quantity + "\nTotal: $" + finalPrice + "\nThank You");
    }

    @Override
    public void onClick(View view) {

    }
}

/**
 * This method displays the given quantity value on the screen.
 */
//    private void display(int number) {
//        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
//        quantityTextView.setText("" + number);
//    }
//
//    private void displayprice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        priceTextView.setText("" + number);
//    }
//
//
//
//}
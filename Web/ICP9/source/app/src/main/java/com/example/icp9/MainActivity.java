package com.example.icp9;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";

    EditText userName;
    EditText userPhone;
    EditText userEmail;
    CheckBox Cheese;
    CheckBox Mushrooms;
    CheckBox Chicken;
    Button OrderButton;
    final int PIZZA_PRICE = 12;
    final int CHEESE_PRICE = 2;
    final int MUSHROOMS_PRICE = 2;
    final int CHICKEN_PRICE = 4;
    String size;
    String crust;
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.et_user);
        userPhone = findViewById(R.id.et_phone);
        userEmail =  findViewById(R.id.et_email);

        Cheese = findViewById(R.id.cb_cheese);
        Mushrooms = findViewById(R.id.cb_mushrooms);
        Chicken =  findViewById(R.id.cb_chicken);
        OrderButton = findViewById(R.id.btn_order);

        OrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

    }

    public void onCrustSelected(View view){
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button option was clicked from Radio Buttons
        switch(view.getId()) {
            case R.id.rb_handTossed:
                if (checked)
                    crust = "Hand Tossed";
                break;
            case R.id.rb_handmadePan:
                if (checked)
                    crust = "Hand Made";
                break;
            case R.id.rb_thinCrust:
                if (checked)
                    crust = "Thin Crust";
                break;
        }
    }

    public void onSizeSelected(View view){
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb_small:
                if (checked)
                    size = "Small";
                break;
            case R.id.rb_medium:
                if (checked)
                    size = "Medium";
                break;
            case R.id.rb_large:
                if (checked)
                    size = "Large";
                break;
        }
    }


    public void sendEmail() {

        String userName = this.userName.getText().toString();
        String userPhone = this.userPhone.getText().toString();
        String userEmail = this.userEmail.getText().toString();
        String[] userEmails = userEmail.split(",");

        // check if any of the below is selected
        boolean hasCheese = Cheese.isChecked();
        boolean hasMushrooms = Mushrooms.isChecked();
        boolean hasChicken = Chicken.isChecked();


        // calculate the total price
        float totalPrice = calculatePrice(hasCheese, hasMushrooms, hasChicken);

        // create and store the order summary details
        String orderSummaryMessage = createOrderSummary(userName, hasCheese, hasMushrooms, hasChicken, totalPrice);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        Log.i("Send email", "");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, userEmails);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pizza Delivery Details");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummaryMessage);
        try {
            startActivity(Intent.createChooser(emailIntent, "Choose any email client"));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


     // When the Order button is clicked.

    public void submitOrder(View view) {

        String userName = this.userName.getText().toString();

        boolean hasCheese = Cheese.isChecked();

        boolean hasMushrooms = Mushrooms.isChecked();

        boolean hasChicken = Chicken.isChecked();

        float totalPrice = calculatePrice(hasCheese, hasMushrooms, hasChicken);

        String orderSummaryMessage = createOrderSummary(userName, hasCheese, hasMushrooms,hasChicken, totalPrice);

        Intent redirect = new Intent(MainActivity.this, OrderSummery.class);
        redirect.putExtra("MESSAGE", orderSummaryMessage);
        MainActivity.this.startActivity(redirect);
    }


    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }
    // Order Summary Details
    private String createOrderSummary(String userName, boolean hasCheese, boolean hasMushrooms, boolean hasChicken, float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name, userName) + ",\n" +
                getString(R.string.order_details) + "\n" +
                getString(R.string.order_summary_cheese, boolToString(hasCheese)) + "\n" +
                getString(R.string.order_summary_mushrooms, boolToString(hasMushrooms)) + "\n" +
                getString(R.string.order_summary_chicken, boolToString(hasChicken)) + "\n" +
                "Crust?" + crust + "\n"+
                "Size?" + size + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    // Total price calculation
    private float calculatePrice(boolean hasCheese, boolean hasMushrooms, boolean hasChicken) {
        int basePrice = PIZZA_PRICE;
        if (hasCheese) {
            basePrice += CHEESE_PRICE;
        }
        if (hasMushrooms) {
            basePrice += MUSHROOMS_PRICE;
        }
        if (hasChicken) {
            basePrice += CHICKEN_PRICE;
        }
        return quantity * basePrice;
    }

    // Quantity
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.tv_quantity);
        quantityTextView.setText("" + number);
    }


// Increment of Quantity
    public void increment(View view) {
        if (quantity < 50) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than 50 pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_quantity);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    // Decrement of Quantity
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select atleast one pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_quantity);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}
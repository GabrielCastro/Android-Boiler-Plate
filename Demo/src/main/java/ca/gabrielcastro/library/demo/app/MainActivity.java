package ca.gabrielcastro.library.demo.app;


import android.content.Intent;
import android.os.Bundle;

import ca.gabrielcastro.library.demo.widget.SquareImageActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.startActivity(new Intent(this, SquareImageActivity.class));
        finish();
    }
}

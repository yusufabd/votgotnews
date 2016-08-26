package uz.androidclub.votgotnews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.davemorrissey.labs.subscaleview.ImageSource;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MapActivity extends AppCompatActivity {

    PinView imageView;
    Bitmap map, marker;
    PhotoViewAttacher photoView;
    PinView pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        imageView = (PinView)findViewById(R.id.image_view);
        imageView.setImage(ImageSource.resource(R.drawable.map));
        imageView.setPin(new PointF(1718f, 581f));
        imageView.setPin(new PointF(5423f, 881f));
        imageView.setPin(new PointF(2718f, 981f));
    }

    class DrawView extends View {

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.GREEN);
        }
    }
}

package com.justin.mysightsecurity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class RecordviewAcitvity extends AppCompatActivity {
    private VideoView recordedViedo ;
    private ImageView recordViewExit;

    SQLiteDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recordview);

        Bundle bundle = getIntent().getExtras();
        String stringValue = bundle.getString("historyinfo");

        try {
            db= RecordviewAcitvity.this.openOrCreateDatabase("sight.db", Context.MODE_PRIVATE,null);
        }catch (Exception e) {
            Toast.makeText(RecordviewAcitvity.this, "Can not access database: "+ e.toString(), Toast.LENGTH_SHORT).show();
            db.close();
            return;
        }

        Cursor c = db.rawQuery("SELECT * FROM History",null);
        //Log.d("My Test", "All is Ok right here!");
        c.moveToPosition(Integer.parseInt(stringValue));

        String strTime = c.getString(2);

       // Toast.makeText(this, c.getString(4), Toast.LENGTH_SHORT).show();

        recordedViedo = (VideoView)findViewById(R.id.recordView);
            recordViewExit = (ImageView)findViewById(R.id.recordViewExit);
            Uri uri = Uri.parse(c.getString(4));
            recordedViedo.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(recordedViedo);

        // sets the media player to the videoView
        mediaController.setMediaPlayer(recordedViedo);

        // sets the media controller to the videoView
        recordedViedo.setMediaController(mediaController);

        recordedViedo.start();
            recordViewExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
    }


}

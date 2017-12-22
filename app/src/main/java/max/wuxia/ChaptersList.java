package max.wuxia;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ChaptersList extends AppCompatActivity {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Log.d("onCreate", "chaptersListOnCreateCalled");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters_list);
        context = this; //getApplicationContext();

        if (getIntent().hasExtra("novelName")){
            String novelName = getIntent().getExtras().getString("novelName");
            TextView novelNameText = (TextView) findViewById(R.id.novelName);
            novelNameText.setText(novelName);

            ChapterListTask chapterListTask = new ChapterListTask(context, this.findViewById(android.R.id.content), novelName);
            chapterListTask.execute(novelName);
        }

    }


}


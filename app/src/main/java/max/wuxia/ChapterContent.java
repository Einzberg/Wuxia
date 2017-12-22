package max.wuxia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChapterContent extends AppCompatActivity {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate", "onCreateCalled");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_content);
        context = this;


        if (getIntent().hasExtra("chapterUrl")){
            String chapterUrl = getIntent().getExtras().getString("chapterUrl");
            String novelName = getIntent().getExtras().getString("novelName");
//            TextView novelNameText = (TextView) findViewById(R.id.novelContentId);
//            novelNameText.setText(chapterUrl);

            ChapterContentTask chapterContentTask = new ChapterContentTask(context, this.findViewById(android.R.id.content));
            chapterContentTask.execute(chapterUrl);

        }


    }
}

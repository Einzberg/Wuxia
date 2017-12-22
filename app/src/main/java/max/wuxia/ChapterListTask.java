package max.wuxia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * Created by MaxBlue on 12/21/2017.
 */

    public class ChapterListTask extends AsyncTask<String, Integer, ArrayList> {
        public Context context;
        public View view;
        public String novelName;
        public ChapterListExtractor chapterExtractor;

        public ChapterListTask(Context context, View view, String novelName){
            this.context = context;
            this.view = view;
            this.novelName = novelName;
        }

    @Override
    protected ArrayList doInBackground(String... strings) {
        chapterExtractor = new ChapterListExtractor();
        ArrayList chapters = chapterExtractor.GetChaptersList(strings[0]);
//        Arrays.sort(chapters);
        return chapters;
    }

    @Override
    protected void onPostExecute(ArrayList chapters){
        ChaptersList chapterList = new ChaptersList();
        int randomid = 6666;
        for (Object chapter : chapters){
            CreateChapterButton(randomid, chapter);
            randomid++;
        }
    }

    public void CreateChapterButton(int id, final Object chapter) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        Button btn = new Button(context);
        btn.setId(id);
        final int id_ = btn.getId();
        btn.setText(chapter.toString());
        btn.setBackgroundColor(Color.rgb(198, 226, 255));
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.scrollLinearLayourId);
        linearLayout.addView(btn, layoutParams);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String chapterUrl = chapterExtractor.GetUrlFromChapters(novelName, chapter.toString());
//                String chapterContent = chapterExtractor.GetArticleFromHtml(chapterUrl);
                Intent startIntent = new Intent(context, ChapterContent.class);
                startIntent.putExtra("chapterUrl", chapterUrl);
                context.startActivity(startIntent);
            }
        });
    }
}

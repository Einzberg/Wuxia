package max.wuxia;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by MaxBlue on 12/21/2017.
 */

public class ChapterContentTask extends AsyncTask<String, Integer, String> {
    public ChapterListExtractor chapterExtractor;
    public Context context;
    public View view;

    public ChapterContentTask(Context context, View view){
        this.context = context;
        this.view = view;
    }
    @Override
    protected String doInBackground(String... strings) {
        chapterExtractor = new ChapterListExtractor();
        String content = chapterExtractor.GetArticleFromHtml(strings[0]);
        return content;
    }

    @Override
    protected void onPostExecute(String chapterContent){
        TextView novelNameText = (TextView) view.findViewById(R.id.novelContentId);
        chapterContent = chapterContent.replaceAll("Previous Chapter|Next Chapter", "");
        Log.d("content", chapterContent);
        novelNameText.setText(chapterContent);
    }
}

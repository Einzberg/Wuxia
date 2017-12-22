package max.wuxia;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;


public class ChapterContentTask extends AsyncTask<String, Integer, String> {
    public ChapterListExtractor chapterExtractor;
    public Context context;
    public View view;
//    final public String chapterContent;

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
        for (int i = 0; i < 4; i++){
            chapterContent = chapterContent.replaceFirst("\n", "");
        }
        final String content = chapterContent;
        novelNameText.setText(chapterContent);

        final TextToSpeechCoverter ttsc = new TextToSpeechCoverter(context);
        ToggleButton readingButton = (ToggleButton) view.findViewById(R.id.readButton);
        readingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton toggleButton, boolean b) {

                if (b){
                    Log.d("content", "calling on changed");
                    for (int i = 0; i < 4; i++){
                        int j = i * 100;
                        String content2 = content.substring(j, j + 100);
                        ttsc.convertTextToSpeech(content2);

                    }
                } else {
                    ttsc.onPause();
                }
            }
        });
    }
}

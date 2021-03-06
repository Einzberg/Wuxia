package max.wuxia;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;


public class ChapterContentTask extends AsyncTask<String, Integer, String> {
    private Context context;
    private View view;
    private TextToSpeechConverter ttsc;

    ChapterContentTask(Context context, View view){
        Log.d("onCreate", "chapterContentTaskCalled");
        this.context = context;
        this.view = view;
    }
    @Override
    protected String doInBackground(String... strings) {
        ChapterListExtractor chapterExtractor = new ChapterListExtractor();
        return chapterExtractor.GetArticleFromHtml(strings[0]);
    }

    @Override
    protected void onPostExecute(String chapterContent){
        if (chapterContent == null){
            TextView novelNameText = (TextView) view.findViewById(R.id.novelContentId);
            novelNameText.setText(R.string.noContentTextValue);
        } else {
            TextView novelNameText = (TextView) view.findViewById(R.id.novelContentId);
            chapterContent = chapterContent.replaceAll("Previous Chapter|Next Chapter", "");
            for (int i = 0; i < 4; i++){
                chapterContent = chapterContent.replaceFirst("\n", "");
            }

            final String[] paragraphs = chapterContent.split("\n");
            novelNameText.setText(chapterContent);

            ttsc = new TextToSpeechConverter(context);
            ToggleButton readingButton = (ToggleButton) view.findViewById(R.id.readButton);
            readingButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton toggleButton, boolean b) {
                    int paragraphIndex = 0;

                    if (b){
                        while(b){
                            if (paragraphIndex > paragraphs.length - 1){
                                break;
                            }
                            String notReadParagraphs = paragraphs[paragraphIndex];
                            ttsc.convertTextToSpeech(notReadParagraphs);
                            paragraphIndex++;
                        }

                    } else {
                        ttsc.onPause();
                    }
                }
            });
        }
    }

    void terminate(){
        if (ttsc != null){
            ttsc.terminate();
        }
    }
}

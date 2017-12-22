package max.wuxia;

import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.jsoup.Jsoup;
        import org.jsoup.nodes.Document;
        import org.jsoup.nodes.Element;
        import org.jsoup.select.Elements;

public class ChapterListExtractor {

    public static HashMap<String, HashMap> baseULRs;
    public static HashMap<String, String> chaptersDictionary;
    public HashMap<String, String> novelUrlDictionary;

    private String spritVesselIndex = "http://www.wuxiaworld.com/spiritvessel-index/";
    private String againstTheGodsIndex = "http://www.wuxiaworld.com/atg-index/";

    public ChapterListExtractor (){
        setUpDictionary();
    }

    private void setUpDictionary(){
        novelUrlDictionary = new HashMap<String, String>(){
            {
                put("Against The Gods", againstTheGodsIndex);
                put("Spirit Realm", spritVesselIndex);
            }
        };

        chaptersDictionary = new HashMap<String, String>();
        baseULRs = new HashMap<String, HashMap>();
    }


    public String GetArticleFromHtml(String html) {
        Document chapterDocument = null;

        try {
            chapterDocument = Jsoup.connect(html).get();
            chapterDocument.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
            chapterDocument.select("br").append("\\n");
            chapterDocument.select("p").prepend("\\n\\n");
//            String s = chapterDocument.html().replaceAll("\\\\n", "\n");
        } catch (Exception e){
            Log.d("onCreate", e.toString());
        }
        Elements articleBodys = chapterDocument.select("[itemprop=articleBody]");
        String article = articleBodys.first().text();
        article = article.replaceAll("\\\\n", "\n");
        return article;
    }

    public ArrayList GetChaptersList(String novelName) {
        Document chapterDocument = null;
        ArrayList chapterList = new ArrayList();
        try {
            chapterDocument = Jsoup.connect(novelUrlDictionary.get(novelName)).get();
        } catch (Exception e) {
            Log.d("onCreate", e.toString());
        }

        Elements articleBodys = chapterDocument.select("[itemprop=articleBody]");
        Element article = articleBodys.first();

        Elements classes = article.getElementsByTag("a");
        for (Element classe : classes) {
            String webpageUrl = classe.attr("abs:href");
            if (!(classe.text().length() > 15)){
                chaptersDictionary.put(classe.text(), webpageUrl);
                chapterList.add(classe.text());
            }
        }

        baseULRs.put(novelName, chaptersDictionary);
        return chapterList;
    }

    public String GetUrlFromChapters(String novelName, String chapter){
        HashMap<String, String> chapters = baseULRs.get(novelName);
        return chapters.get(chapter);
    }
}

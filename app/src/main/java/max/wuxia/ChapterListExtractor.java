package max.wuxia;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class ChapterListExtractor {

    private static HashMap<String, HashMap> baseULRs;
    private static HashMap<String, String> chaptersDictionary;
    private HashMap<String, String> novelUrlDictionary;

    private String spritVesselIndex = "http://www.wuxiaworld.com/spiritvessel-index/";
    private String againstTheGodsIndex = "http://www.wuxiaworld.com/atg-index/";

    ChapterListExtractor(){
        setUpDictionary();
    }

    private void setUpDictionary(){
        novelUrlDictionary = new HashMap<String, String>(){
            {
                put("Against The Gods", againstTheGodsIndex);
                put("Spirit Realm", spritVesselIndex);
            }
        };

        chaptersDictionary = new HashMap<>();
        baseULRs = new HashMap<>();
    }


    String GetArticleFromHtml(String html) {
        Document chapterDocument;
        try {
            chapterDocument = Jsoup.connect(html).get();
            chapterDocument.outputSettings(new Document.OutputSettings().prettyPrint(false));
            chapterDocument.select("br").append("\\n");
            chapterDocument.select("p").prepend("\\n\\n");
            Elements articleBodys = chapterDocument.select("[itemprop=articleBody]");
            String article = articleBodys.first().text();
            article = article.replaceAll("\\\\n", "\n");
            return article;
        } catch (Exception e){
            Log.d("GetArticleError: ", e.toString());
            return null;
        }
    }

    ArrayList GetChaptersList(String novelName) {
        Document chapterDocument;
        ArrayList chapterList = new ArrayList();
        Elements articleBodies;
        try {
            chapterDocument = Jsoup.connect(novelUrlDictionary.get(novelName)).get();
            articleBodies = chapterDocument.select("[itemprop=articleBody]");
        } catch (Exception e) {
            Log.d("GetChaptersError: ", e.toString());
            return null;
        }

        Element article = articleBodies.first();

        Elements tags = article.getElementsByTag("a");
        for (Element tagElement : tags) {
            String webpageUrl = tagElement.attr("abs:href");
            chaptersDictionary.put(tagElement.text(), webpageUrl);
            chapterList.add(tagElement.text());
        }

        baseULRs.put(novelName, chaptersDictionary);
        return chapterList;
    }

    String GetUrlFromChapters(String novelName, String chapter){
        HashMap<String, String> chapters = baseULRs.get(novelName);
        return chapters.get(chapter);
    }

    HashMap GetChaptersAndUrls(String novelName){
        return baseULRs.get(novelName);
    }
}

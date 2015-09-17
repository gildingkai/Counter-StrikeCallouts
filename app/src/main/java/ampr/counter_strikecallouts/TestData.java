package ampr.counter_strikecallouts;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TwitchRat on 9/2/2015.
 */
public class TestData {

    public class Question{
        public String answer;
        public String[] incorrect;
        public int top;
        public int bottom;
        public int left;
        public int right;

        private Question(String ans,String inc,String lef,String top,String rig,String bot){
            this.answer = ans;
            this.incorrect = inc.split(",");
            this.top = Integer.parseInt(top);
            this.bottom = Integer.parseInt(bot);
            this.left = Integer.parseInt(lef);
            this.right = Integer.parseInt(rig);


        }

    }

    public List parse(InputStream inStream) throws XmlPullParserException, IOException {
        try{
            XmlPullParser mapParse = Xml.newPullParser();
            mapParse.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            mapParse.setInput(inStream, null);
            mapParse.nextTag();
            return readFeed(mapParse);


        }finally {
            inStream.close();
        }


    }

    private static final String ns = null;

    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List questions = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "map");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("question")) {
                questions.add(readEntry(parser));
            }
        }
        return questions;
    }


    private Question readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "question");
        String answer = null;
        String incorrect = null;
        String position = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("answer")) {
                answer = readAnswer(parser);
            } else if (name.equals("incorrect")) {
                incorrect = readIncorrect(parser);
            } else if (name.equals("position")) {
                position = readPosition(parser);
            }
        }
        String[] pos = position.split(",");


        return new Question(answer, incorrect, pos[0],pos[1],pos[2],pos[3]);
    }



    private String readAnswer(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "answer");
        String answer = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "answer");
        return answer;
    }
    private String readIncorrect(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "incorrect");
        String incorrect = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "incorrect");
        return incorrect;
    }

    private String readPosition(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "position");
        String strNumber = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "position");

        return strNumber;
    }


    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}

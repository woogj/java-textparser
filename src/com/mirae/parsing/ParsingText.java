package com.mirae.parsing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ParsingText {
    private static Logger log = LoggerFactory.getLogger(ParsingExcel.class);
    private static long startTm = 0L;
    private static long endTm = 0L;

    public static void parsingText(String filename){
        Date date = new Date();

        SimpleDateFormat forLog = new SimpleDateFormat("mm:ss:S");
        startTm = System.currentTimeMillis();
        log.debug("@@ construction parsing starting");
        ArrayList<String> rsSetences = new ArrayList<>();
        if(checkString(filename)) return null;
        ArrayList<String> setences = new ArrayList<>();

        BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.KOREA);
        setences = GetRefined.cropLine(filename);

        rsSetences= GetRefined.Reattach(GetRefined.confirmSentence(GetBreakIterator.printEachForward(boundary, setences)));
        endTm = System.currentTimeMillis();
        log.debug(String.format("@@ TextParsing Terminated- total sentences: %s (%s)", rsSetences.size(), forLog.format(endTm-startTm)));

        return rsSetences;
    }

    private static boolean checkString(String str){
        return str == null || str.length() == 0;
    }
}

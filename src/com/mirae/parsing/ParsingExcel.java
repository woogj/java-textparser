package com.mirae.parsing;


import com.mirae.parsing.utils.ReadExcel;
import com.mirae.parsing.utils.WriteExcel;
import com.mirae.parsing.utils.getObjectSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ParsingExcel {
    private static Logger log = LoggerFactory.getLogger(ParsingExcel.class);
    private static long startTm = 0L;
    private static long endTm = 0L;

    public static void parsingExcel(String filename){

        Date date = new Date();

        SimpleDateFormat forExcel = new SimpleDateFormat("yyMMddHHmmss");
        SimpleDateFormat forLog = new SimpleDateFormat("mm:ss:S");
        startTm = System.currentTimeMillis();
        log.debug("Excel file parsing starting");

        ArrayList<String[]> excel = ReadExcel.getExcelList(filename);
        String[] no = excel.get(0);
        String[] excelFile = excel.get(1);
        ArrayList<ArrayList<String>> setences = new ArrayList<>();
        ArrayList<ArrayList<String>> rsSetences = new ArrayList<>();
        BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.KOREA);
        setences = GetRefined.cropLine(excelFile);

        for(ArrayList<String> Index: setences) {
            rsSetences.add(GetRefined.Reattach(GetRefined.confirmSentence(GetBreakIterator.printEachForward(boundary, Index))));
        }


        WriteExcel.exportExcel(no, excelFile, rsSetences, filename+"_parsed"+forExcel.format(date));
        endTm = System.currentTimeMillis();
        log.debug("TextParsing terminated- total text: "+rsSetences.size()+", total sentences: "+ getObjectSize.getSizeObj(rsSetences)+"- %s",forLog.format(endTm-startTm));
    }
}

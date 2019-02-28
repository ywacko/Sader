package com.unidt.helper.common;

import com.unidt.mybatis.dto.SurveysChapterDto;

import java.util.Comparator;

public class SortBySeq implements Comparator {
    public int compare(Object o1, Object o2){
        SurveysChapterDto sCD1 = (SurveysChapterDto) o1;
        SurveysChapterDto sCD2 = (SurveysChapterDto) o2;
        return Integer.valueOf(sCD1.surveys_chapter_seq) > Integer.valueOf(sCD2.surveys_chapter_seq) ?
                1 : -1;
    }
}

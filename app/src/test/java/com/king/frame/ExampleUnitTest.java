package com.king.frame;

import com.king.frame.utils.FileUtil;
import com.king.frame.utils.TimeUtil;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void time_isCorrect() throws Exception {
        String dateBegin = "2017-03-02 12:00:00";
        Date begin = TimeUtil.INSTANCE.secondStrToDate(dateBegin);
        System.out.print(TimeUtil.INSTANCE.timeMillisToStr(System.currentTimeMillis()));
        System.out.print(new Date(System.currentTimeMillis()));
    }

    @Test
    public void testFileSize() throws Exception {
        long size = 202624164122L;
        System.out.println(FileUtil.Companion.formatFileSize(size));

    }

    @Test
    public void testLoadBook(){

    }
}
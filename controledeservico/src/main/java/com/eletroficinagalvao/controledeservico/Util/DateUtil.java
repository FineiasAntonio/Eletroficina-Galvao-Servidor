package com.eletroficinagalvao.controledeservico.Util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtil {

    public static Date getDateForSQL(LocalDate date){
        return Date.valueOf(date);
    }

    public static LocalDate getDate(Date date){
        return date.toLocalDate();
    }

}

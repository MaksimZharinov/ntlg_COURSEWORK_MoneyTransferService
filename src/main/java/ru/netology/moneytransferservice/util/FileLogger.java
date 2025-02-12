package ru.netology.moneytransferservice.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileLogger {

    private static final Logger logger = LogManager.getLogger(FileLogger.class);

    public void logTransfer(String cardFrom, String cardTo, double amount, double commission, String result) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String formattedDate = formatter.format(date);

        StringBuilder sb = new StringBuilder()
                .append(formattedDate).append(" | ")
                .append(cardFrom).append(" | ")
                .append(cardTo).append(" | ")
                .append(amount).append(" | ")
                .append(commission).append(" | ")
                .append(result);

        logger.info(sb.toString());
    }
}

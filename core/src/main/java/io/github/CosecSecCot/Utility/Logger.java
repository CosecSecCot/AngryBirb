package io.github.CosecSecCot.Utility;

import com.badlogic.gdx.Gdx;

import java.time.LocalDateTime;

public class Logger {
    boolean canLog;

    public Logger(boolean canLog) {
        this.canLog = canLog;
    }

    public void info(String... message) {
        StringBuilder builder = new StringBuilder();
        for (String s : message) {
            builder.append(s);
            builder.append(" ");
        }
        if (canLog) {
            Gdx.app.log("INFO", LocalDateTime.now()+ ": " + builder);
        }
    }

    public void warning(String... message) {
        StringBuilder builder = new StringBuilder();
        for (String s : message) {
            builder.append(s);
            builder.append(" ");
        }
        if (canLog) {
            Gdx.app.log("WARNING", LocalDateTime.now()+ ": " + builder);
        }
    }

    public void error(String... message) {
        StringBuilder builder = new StringBuilder();
        for (String s : message) {
            builder.append(s);
            builder.append(" ");
        }
        if (canLog) {
            Gdx.app.log("ERROR", LocalDateTime.now()+ ": " + builder);
        }
    }
}

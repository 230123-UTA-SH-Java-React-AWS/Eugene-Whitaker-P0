package com.revature.utils;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Treyvon Whitaker
 *         <p>
 *         This class utilizes the {@link InputStream} and
 *         {@link StringBuilder} to read in the request body of an exchange 
 *         and convert it to a String like object
 *         </p>
 */
public class StringBuilderUtil {
    /**
     * <p>
     * This method construst a StringBuilder from and InputStream object
     * </p>
     * 
     * @param exchange the object containing the request body
     * @return the StrinBuilder of the request body
     * @throws IOException
     */
    public static StringBuilder buildString(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        StringBuilder textBuilder = new StringBuilder();
        Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())));

        int c = 0;
        while((c = reader.read()) != -1) {
            textBuilder.append((char) c);
        }
        reader.close();

        return textBuilder;
    }
}

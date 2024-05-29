package dbhelper;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * stores cookies for authentication
 * @author James Dai, chatgpt
 * @version 5/29/24
 */
public class SimpleCookieJar implements CookieJar {

    private final Map<String, List<Cookie>> cookieStore = new HashMap<>();
    /**
     * saves cookies
     */
    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.put(url.host(), cookies);
    }

    /**
     * loads cookies
     */
    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url.host());
        return cookies != null ? cookies : new ArrayList<>();
    }
}

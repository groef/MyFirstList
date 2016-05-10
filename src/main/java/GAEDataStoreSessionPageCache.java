import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GAEDataStoreSessionPageCache {
	
	private static final Map<String, Map<String, Object>> CACHE = Collections.synchronizedMap(new HashMap<String, Map<String, Object>>());

    public static Object getAttribute(String sessionId, String attribName) {
        Map<String, Object> attribs = CACHE.get(sessionId);
        if (attribs != null) {
            synchronized(attribs) {
                return attribs.get(attribName);
            }
        }

        return null;
    }

    public static void setAttribute(String sessionId, String attribName, Object attribValue) {
        Map<String, Object> attribs = CACHE.get(sessionId);
        if (attribs == null) {
            attribs = new HashMap<String, Object>();
            CACHE.put(sessionId, attribs);
        }

        synchronized(attribs) {
            attribs.put(attribName, attribValue);
        }
    }

    public static void destroySession(String sessionId) {
        CACHE.remove(sessionId);
    }

    public static void createSession(String sessionId, boolean force) {
        if (force || ! CACHE.containsKey(sessionId)) {
            CACHE.put(sessionId, new HashMap<String, Object>());
        }
    }
}

package com.hubeleon.wickets.cache;

import org.apache.wicket.Application;
import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.page.IManageablePage;
import org.apache.wicket.page.IPageManagerContext;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.IPageStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class disables Wicket's serialization behavior, while still retaining session and page data in memory (so back button will work).
 * This will run out of memory under heavy load; but it's very convenient for low volume web applications.
 * To disable serialization in your application, call this code:
 * <pre>
 *     setPageManagerProvider( new NoSerializePageManagerProvider( this, getPageManagerContext() ) );
 * </pre>
 */
public class NoSerializePageManagerProvider extends DefaultPageManagerProvider {
    private IPageManagerContext pageManagerContext;

    public NoSerializePageManagerProvider( Application application, IPageManagerContext pageManagerContext ) {
        super( application );
        this.pageManagerContext = pageManagerContext;
    }

    @Override
    protected IDataStore newDataStore() {
        return new HttpSessionDataStore( pageManagerContext, new PageNumberEvictionStrategy( 20 ) );
    }

    @Override
    protected IPageStore newPageStore( IDataStore dataStore ) {
        return new IPageStore() {
            Map<String,Map<Integer,IManageablePage>> cache = new HashMap<String, Map<Integer, IManageablePage>>();

            public void destroy() {
                cache = null;
            }

            public IManageablePage getPage( String sessionId, int pageId ) {
                Map<Integer, IManageablePage> sessionCache = getSessionCache( sessionId, false );
                IManageablePage page = sessionCache.get( pageId );
                if( page == null ) {
                    throw new IllegalArgumentException( "Found this session, but there is no page with id " + pageId );
                }
                return page;
            }

            public void removePage( String sessionId, int pageId ) {
                getSessionCache( sessionId, false ).remove( pageId );
            }

            public void storePage( String sessionId, IManageablePage page ) {
                getSessionCache( sessionId, true ).put( page.getPageId(), page );
            }

            public void unbind( String sessionId ) {
                cache.remove( sessionId );
            }

            public Serializable prepareForSerialization( String sessionId, Object page ) {
                return null;
            }

            public Object restoreAfterSerialization( Serializable serializable ) {
                return null;
            }

            public IManageablePage convertToPage( Object page ) {
                return (IManageablePage)page;
            }

            private Map<Integer, IManageablePage> getSessionCache( String sessionId, boolean create ) {
                Map<Integer, IManageablePage> sessionCache = cache.get( sessionId );
                if( sessionCache == null ) {
                    if( create ) {
                        sessionCache = new HashMap<Integer, IManageablePage>();
                        cache.put( sessionId, sessionCache );
                    } else {
                        throw new IllegalArgumentException( "There are no pages stored for session id " + sessionId );
                    }
                }
                return sessionCache;
            }
        };
    }
}
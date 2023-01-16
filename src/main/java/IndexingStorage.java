import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexingStorage {
    protected static IndexingStorage storage;
    protected Map<String, List<PageEntry>> wordIndexing = new HashMap<>();

    private IndexingStorage() {
    }

    public static synchronized IndexingStorage getIndexedStorage() {
        if (storage == null) {
            storage = new IndexingStorage();
        }
        return storage;
    }

    public Map<String, List<PageEntry>> getStorage() {
        return wordIndexing;
    }
}
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    protected Map<String, List<PageEntry>> wordIndex;

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        wordIndex = IndexingStorage.getIndexedStorage().getStorage();
        var doc = new PdfDocument(new PdfReader(pdfsDir));
        for (int i = 0; i < doc.getNumberOfPages(); i++) {
            var text = PdfTextExtractor.getTextFromPage(doc.getPage(i + 1));
            var words = text.split("\\P{IsAlphabetic}+");
            Map<String, Integer> freqs = new HashMap<>();
            for (var word : words) {
                if (word.isEmpty()) {
                    continue;
                }
                word = word.toLowerCase();
                freqs.put(word, freqs.getOrDefault(word, 0) + 1);
            }
            for (Map.Entry<String, Integer> entry : freqs.entrySet()) {
                String wordKey = entry.getKey();
                int countWord = entry.getValue();
                List<PageEntry> pageEntry = new ArrayList<>();
                pageEntry.add(new PageEntry(pdfsDir.getName(), i + 1, countWord));

                if (!wordIndex.containsKey(wordKey)) {
                    wordIndex.put(wordKey, pageEntry);
                } else {
                    wordIndex.get(wordKey).add(new PageEntry(pdfsDir.getName(), i + 1, countWord));
                }
                Collections.sort(wordIndex.get(wordKey));
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) throws IOException {
        return wordIndex.getOrDefault(word, Collections.emptyList());
    }
}

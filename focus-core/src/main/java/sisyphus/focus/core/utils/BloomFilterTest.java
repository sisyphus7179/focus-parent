package sisyphus.focus.core.utils;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BloomFilterTest {

    public static void main(String[] args) {
        // 1.create bloom filter
        double expectedInsertions = 1_000_000;
        double fpp = 0.0003;// 5 hash functions
        // Funnel<String> funnel = Funnels.stringFunnel(StandardCharsets.UTF_8);
        Funnel<String> funnel = (x, y) -> y.putString(x, StandardCharsets.UTF_8);
        BloomFilter<String> bloomFilter = BloomFilter.create(funnel, (long)expectedInsertions, fpp);

        // 2.initial bloom filter
        List<String> insertUUIDs = new ArrayList<>();
        List<String> unInsertUUIDs = new ArrayList<>();
        int insertSuccess = 0;
        for (int i = 0; i < expectedInsertions; i++) {
            String uuid = UUID.randomUUID().toString();
            insertUUIDs.add(uuid);
            unInsertUUIDs.add(UUID.randomUUID().toString());
            if (bloomFilter.put(uuid)) {
                insertSuccess++;
            }
        }

        // 3.validate
        int queryInsertSuccess = 0;
        int queryUnInsertSuccess = 0;
        for (int i = 0; i < expectedInsertions; i++) {
            if (bloomFilter.mightContain(insertUUIDs.get(i))) {
                queryInsertSuccess++;
            }
            if (bloomFilter.mightContain(unInsertUUIDs.get(i))) {
                queryUnInsertSuccess++;
            }
        }

        System.out.println("Insert success Percentage: " + NumberUtils.roundToPer(insertSuccess / expectedInsertions));
        System.out.println("Query Success Percentage: " + NumberUtils.roundToPer(queryInsertSuccess / expectedInsertions));
        System.out.println("Query False Percentage: " + NumberUtils.roundToPer(queryUnInsertSuccess / expectedInsertions));
    }

}

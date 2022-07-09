package sisyphus.focus.core.utils;

import lombok.extern.slf4j.Slf4j;
import sisyphus.focus.core.entity.BatchFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

@Slf4j
public class FileUtils {

    public static final String BATCH_FILE_SUFFIX = ".txt";
    public static final int BATCH_FILE_ALLOW_MAX_BYTES = 1024 * 1024;
    public static final int EOL_LEN = System.getProperty("line.separator", "\n").getBytes(StandardCharsets.UTF_8).length;

    public static <T> void batchGenerateFiles(BatchFile<T> batchFile) throws IOException {
        String line;
        int lineBytes;
        BigDecimal total;
        List<T> objs = batchFile.getObjs();
        while (!objs.isEmpty()) {
            T obj = objs.remove(0);
            line = batchFile.getObjToStr().apply(obj);
            lineBytes = line.getBytes(StandardCharsets.UTF_8).length;
            if (batchFile.getReminderBytes() >= lineBytes) {
                batchFile.getLines().add(line);
                batchFile.setReminderBytes(batchFile.getReminderBytes() - lineBytes - EOL_LEN);
                if (batchFile.isNeedSummary()) {
                    total = batchFile.getTotal();
                    batchFile.setTotal(total.add(batchFile.getQueryAmount().apply(obj)));
                }
            } else {
                generateFile(batchFile);
                restoreBatchFile(batchFile, obj);
            }
        }
        if (!batchFile.isHasMoreElement() && !batchFile.getLines().isEmpty()) {
            generateFile(batchFile);
            restoreBatchFile(batchFile);
        }
    }

    private static <T> void generateFile(BatchFile<T> batchFile) throws IOException {
        if (batchFile.isNeedSummary()) {// set summary
            BigDecimal bigDecimal = batchFile.getTotal().setScale(2, RoundingMode.HALF_UP);
            System.out.println("total: " + bigDecimal);
            batchFile.getLines().add(0, String.format(batchFile.getSummaryLine(), bigDecimal));
        }
        // fixme generate file name
        int batchNo = batchFile.getBatchNo();
        batchFile.setBatchNo(++batchNo);
        Path path = Paths.get(batchFile.getPath(), batchFile.getFileName() + "_" + batchNo + BATCH_FILE_SUFFIX);
        log.info("Generate file[{}] start....................", path.getFileName());
        if (!path.toFile().exists()) {
            path.toFile().createNewFile();
        }
        Files.write(path, batchFile.getLines(), StandardOpenOption.APPEND);
        System.out.println(1024 * 1024);
        System.out.println(path.toFile().length());
        log.info("Generate file[{}] success....................", path.getFileName());
    }

    private static <T> void restoreBatchFile(BatchFile<T> batchFile) {
        restoreBatchFile(batchFile, null);
        batchFile.getObjs().clear();
    }

    private static <T> void restoreBatchFile(BatchFile<T> batchFile, T t) {
        if (Objects.nonNull(t)) batchFile.getObjs().add(0, t);
        batchFile.setTotal(BigDecimal.ZERO);
        batchFile.setReminderBytes(BATCH_FILE_ALLOW_MAX_BYTES);
        if (batchFile.isNeedSummary()) {
            batchFile.setReminderBytes(BATCH_FILE_ALLOW_MAX_BYTES - batchFile.getSummaryLineBytes());
        }
        batchFile.getLines().clear();
    }

}


package sisyphus.focus.core.utils;

import lombok.extern.slf4j.Slf4j;
import sisyphus.focus.core.entity.BatchFile;
import sisyphus.focus.core.entity.Employee;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
public class FileUtils {

    public static final String BATCH_FILE_SUFFIX = ".txt";
    public static final int BATCH_FILE_ALLOW_MAX_BYTES = 1024 * 1024;
    public static final int EOL_LEN = System.getProperty("line.separator", "\n").getBytes(StandardCharsets.UTF_8).length;

    public static void main(String[] args) throws IOException {
        String summaryLine = "1|%f";
        Function<Employee, BigDecimal> queryAmount = x -> BigDecimal.valueOf(x.getSalary());
        int batchObjNum = 50;
        Function<Employee, String> objToStr = x -> {
            return "2|" + x.getId() + "|" + x.getName() + "|" + x.getAge() + "|" + x.getSalary();
        };
        String path = "d:\\";
        String fileName = "20220709";
        BatchFile<Employee> batchFile = new BatchFile<Employee>(summaryLine, queryAmount, batchObjNum, objToStr, path, fileName);
        List<Employee> employees = new ArrayList<>();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 1; i < 49_998; i++) {
            Employee employee = new Employee(i, UUID.randomUUID().toString(), secureRandom.nextInt(100), secureRandom.nextDouble() * 10000);
            employees.add(employee);
            if (i % 50 == 0) {
                batchFile.setObjs(employees);// sync
                FileUtils.batchGenerateFiles(batchFile);
            }
        }
        if (!employees.isEmpty()) {
            batchFile.setObjs(employees);
            FileUtils.batchGenerateFiles(batchFile);
        }
    }

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
            // don't need to invoke restoreBatchFile(batchFile, obj);
        }
    }

    private static <T> void generateFile(BatchFile<T> batchFile) throws IOException {
        if (batchFile.isNeedSummary()) {
            batchFile.getLines().add(0, String.format(batchFile.getSummaryLine(), batchFile.getTotal()));
        }
        int batchNo = batchFile.getBatchNo();
        batchFile.setBatchNo(++batchNo);
        Path path = Paths.get(batchFile.getPath(), batchFile.getFileName() + "_" + batchNo + BATCH_FILE_SUFFIX);
        log.info("Generate file[{}] start....................", path.getFileName());
        if (!path.toFile().exists()) {
            path.toFile().createNewFile();
        }
        Files.write(path, batchFile.getLines(), StandardOpenOption.APPEND);
        log.info("Generate file[{}] success....................", path.getFileName());
    }

    private static <T> void restoreBatchFile(BatchFile<T> batchFile, T t) {
        batchFile.getObjs().add(0, t);
        batchFile.setTotal(BigDecimal.ZERO);
        batchFile.setReminderBytes(BATCH_FILE_ALLOW_MAX_BYTES);
        if (batchFile.isNeedSummary()) {
            batchFile.setReminderBytes(BATCH_FILE_ALLOW_MAX_BYTES - batchFile.getSummaryLineBytes());
        }
        batchFile.getLines().clear();
    }

}


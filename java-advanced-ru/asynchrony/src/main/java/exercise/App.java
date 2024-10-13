package exercise;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.concurrent.ExecutionException;

class App {

    // BEGIN
    public static CompletableFuture<String> unionFiles(String pathFile1, String pathFile2, String pathFile3) {

        Path path1 = Paths.get(pathFile1);
        Path path2 = Paths.get(pathFile2);
        Path path3 = Paths.get(pathFile3);

        CompletableFuture<String> futureFile1 = CompletableFuture.supplyAsync(() -> {

            String allContentFile1 = null;
            try {
                allContentFile1 = Files.readString(path1);
            } catch (NoSuchFileException e) {
                System.out.println("NoSuchFileException");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            return allContentFile1;
        });

        CompletableFuture<String> futureFile2 = CompletableFuture.supplyAsync(() -> {

            String allContentFile2 = null;
            try {
                allContentFile2 = Files.readString(path2);
            } catch (NoSuchFileException e) {
                System.out.println("NoSuchFileException");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            return allContentFile2;
        });

        CompletableFuture<String> futureFile3 = futureFile1.thenCombine(futureFile2, (contentFile1, contentFile2) -> {
            String generalContent = contentFile1 + " " + contentFile2;
            try {
                Files.writeString(path3, generalContent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return generalContent;
        });

        return futureFile3;
    }
    // END

    public static Path main(String[] args) throws Exception {
        // BEGIN

        Path result = null;
        try {
            String unionContent = unionFiles("src/main/resources/file1.txt", "src/main/resources/file2.txt", "src/main/resources/result.txt").get();
            result = Files.writeString(Paths.get("src/main/resources/result.txt"), unionContent);
            System.out.println("File writing successfully");

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Ошибка при получении результата из CompletableFuture: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
        return result;
        // END
    }
}
// resources/file.txt

/*
try {
                return Files.writeString(path3, generalContent);
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
 */

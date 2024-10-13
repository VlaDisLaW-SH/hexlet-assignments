package exercise;

import java.nio.file.*;
import java.util.concurrent.CompletableFuture;

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
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return allContentFile1;
        });

        CompletableFuture<String> futureFile2 = CompletableFuture.supplyAsync(() -> {

            String allContentFile2 = null;

            try {
                allContentFile2 = Files.readString(path2);
            } catch (NoSuchFileException e) {
                System.out.println("NoSuchFileException");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return allContentFile2;
        });

        CompletableFuture<String> futureFile3 = futureFile1.thenCombine(futureFile2, (contentFile1, contentFile2) -> {
            String generalContent = contentFile1 + contentFile2;
            try {
                Files.writeString(path3, generalContent, StandardOpenOption.CREATE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return "File writing successfully";
        });

        return futureFile3;
    }
    // END

    public static void main(String[] args) throws Exception {
        // BEGIN
        CompletableFuture<String> resultFile = unionFiles(
            "src/main/resources/file1.txt",
            "src/main/resources/file2.txt",
            "src/main/resources/dest.txt"
        );
        resultFile.get();
        // END
    }
}

/* Solution of teacher
private static Path getFullPath(String filePath) {
        return Paths.get(filePath).toAbsolutePath().normalize();
    }

    public static CompletableFuture<String> unionFiles(String source1, String source2, String dest) {

        CompletableFuture<String> content1 = CompletableFuture.supplyAsync(() -> {
            String content = "";

            try {
                content = Files.readString(getFullPath(source1));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return content;
        });

        CompletableFuture<String> content2 = CompletableFuture.supplyAsync(() -> {

            String content = "";
            try {
                content = Files.readString(getFullPath(source2));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return content;
        });

        return content1.thenCombine(content2, (cont1, cont2) -> {
            String union = cont1 + cont2;
            try {
                Files.writeString(getFullPath(dest), union, StandardOpenOption.CREATE);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return "ok!";

        }).exceptionally(ex -> {
            System.out.println("Oops! We have an exception - " + ex.getMessage());
            return "Unknown!";
        });
    }

    public static CompletableFuture<Long> getDirectorySize(String path) {

        var directory = new File(path);

        if (!directory.isDirectory()) {
            return CompletableFuture.completedFuture(0L);
        }

        File[] files = directory.listFiles();

        CompletableFuture<Long>[] fileSizes = Arrays.stream(files)
            .filter(File::isFile)
            .map(file -> CompletableFuture.supplyAsync(() -> file.length()))
            .toArray(CompletableFuture[]::new);

        return CompletableFuture.allOf(fileSizes)
            .thenApply(v -> Arrays.stream(fileSizes)
                .mapToLong(CompletableFuture::join)
                .sum());
    }

///
CompletableFuture<String> result = unionFiles(
            "src/main/resources/file1.txt",
            "src/main/resources/file2.txt",
            "src/main/resources/dest.txt"
        );
        CompletableFuture<Long> size = getDirectorySize("src/main/resources");
        result.get();
        System.out.println("done!");
        System.out.println(size.get());
 */

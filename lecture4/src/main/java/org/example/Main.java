package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String EXIT_DECISION = "終了";
    private static final String EMPTY_INPUT_MESSAGE = "入力が空です。再度入力してください。";
    private static final String SEARCH_CONTINUATION_PROMPT = "検索をつづけますか？（続ける場合：Enter,終了する場合：'終了'と入力する）";
    private static final String KEYWORD_PROMPT = "キーワードを入力してください：";
    private static final String SEARCH_NOT_FOUND_MESSAGE = "関連書籍は見つかりませんでした。";
    private static final String PROGRAM_EXIT_MESSAGE = "プログラムを終了します。";


    public static void main(String[] args) {
        List<Books> booksList = initializeBooks();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String keyword = getUserInput(br);
                displaySearchResults(booksList, keyword);

                System.out.print(SEARCH_CONTINUATION_PROMPT);
                String decision = br.readLine();

                if (EXIT_DECISION.equals(decision)) {
                    System.out.println(PROGRAM_EXIT_MESSAGE);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Books> initializeBooks() {
        List<Books> booksList = new ArrayList<>();
        booksList.add(new Books("ひと目でわかるAzure Active Directory", 3960));
        booksList.add(new Books("ひと目でわかるIntune", 3960));
        booksList.add(new Books("ひと目でわかるMicrosoft Defender for Endpoint", 3300));
        return booksList;
    }

    private static String getUserInput(BufferedReader br) throws IOException {
        while (true) {
            System.out.print(KEYWORD_PROMPT);
            String input = br.readLine();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println(EMPTY_INPUT_MESSAGE);
                System.out.println();
            }
        }
    }

    private static void displaySearchResults(List<Books> books, String keyword) {
        List<Books> matchedBooks = books.stream()
                .filter(book -> book.getName().contains(keyword))
                .toList();

        if (matchedBooks.isEmpty()) {
            System.out.println(SEARCH_NOT_FOUND_MESSAGE);
        } else {
            matchedBooks.forEach(book -> {
                System.out.printf("検索結果\n商品名：%s\n価格：%d円%n", book.getName(), book.getPrice());
                System.out.println();
            });
        }
    }
}

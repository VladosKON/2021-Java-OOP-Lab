package com.company.bin;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("src/com/company/data/data_books.csv"));
            Scanner reader = new Scanner(System.in);
            String line;
            int numberLine = 1;
            Scanner scanner;

            int index = 0, count = 0, countTop = 0;
            double averageRating = 0, topAverageRating = 0;
            String topName = "", topRatingName = "";

            // Список со всеми издательствами
            List<Publish> publishList = new ArrayList<>();

            //Список названий издательств без повторений
            List<String> publishNameList = new ArrayList<>();


            // Чтение из файла по строкам. Запись в классы и списки.
            // index = 0 : Название книги
            // index = 1 : Автор
            // index = 2 : Рейтинг (от 0.00 до 5.00)
            // index = 3 : Код языка
            // index = 4 : Количество страниц (от 0)
            // index = 5 : Год (от 0 до 9999)
            // index = 6 : Название издательства
            while ((line = fileReader.readLine()) != null) {
                Book book = new Book();
                scanner = new Scanner(line);
                scanner.useDelimiter(",\\s*");
                try {
                    while (scanner.hasNext()) {
                        String data = scanner.next();
                        if (index == 0) {
                            book.setBookName(data);
                        } else if (index == 1) {
                            book.setAuthor(data);
                        } else if (index == 2) {
                            if (Double.parseDouble(data) < 0.0 || Double.parseDouble(data) > 5.0) {
                                System.out.println("Ошибка! Рейтинг должен быть от 0 до 5!\n" + "Рейтинг '" + data + "' в строке " + numberLine + "\n'" + line + "'");
                                index = 0;
                                scanner.nextLine();
                            } else {
                                book.setRating(Double.parseDouble(data));
                            }
                        } else if (index == 3) {
                            book.setLangCode(data);
                        } else if (index == 4) {
                            if (Integer.parseInt(data) < 0) {
                                System.out.println("Ошибка! Количество страниц должно быть > 0! \n" + "Страниц '" + data + "' в строке " + numberLine + "\n'" + line + "'");
                                index = 0;
                                scanner.nextLine();
                            } else {
                                book.setPages(Integer.parseInt(data));
                            }
                        } else if (index == 5) {
                            book.setYearPublish(data);
                        } else if (index == 6) {
                            // если в списке названий нет еще названия из файла, то мы создаем новый
                            // экземпляр издательства и заносим туда книгу
                            // иначе мы просто закидываем в нужное издательство книгу
                            if (!publishNameList.contains(data)) {
                                publishNameList.add(data);
                                Publish publish = new Publish(data);
                                publish.setBookPublish(book);
                                publishList.add(publish);
                            } else {
                                for (Publish publish : publishList) {
                                    if (publish.getPublishName().equals(data)) {
                                        publish.setBookPublish(book);
                                    }
                                }
                            }
                        } else
                            System.out.println("Неккоректные данные " + data);
                        index++;
                    }
                    //catch для отлавливания когда неверно введено что-то в строке
                } catch (NumberFormatException e) {
                    scanner.nextLine();
                }
                index = 0;
                numberLine++;
            }
            fileReader.close();

            // Вывод списка издательств с книгами
//            System.out.println("\n" + publishList);

            // Вывод списка оригинальных названий издательств
//            System.out.println("\nСписок издательств: " + publishNameList + "\n");

            // Подсчет количества книг у каждого издательства и нахождение топа по кол-ву книг.
            Collections.sort(publishList);
            System.out.println("\nБольше всего книг у издателя: '" + publishList.get(0).getPublishName() + "' (" + publishList.get(0).getLenght() + " книг)");
//            for (Publish publish : publishList) {
//                count = publish.getLenght();
//                if (count > countTop) {
//                    countTop = count;
//                    topName = publish.getPublishName();
//                }
//                // Вывод всех издательств и кол-во их книг
////                System.out.println(publish.getPublishName() + " книг: " + count);
//            }
//            System.out.println("\nБольше всего книг у издателя: '" + topName + "' (" + countTop + " книг)");

            // Поиск издательства с самым большим средним рейтингом по введенному году
            System.out.println("\nВведите год: ");
            int inputYear = reader.nextInt();
            if (inputYear < 0 || inputYear > 9999) {
                System.out.println("Введено неверное число");
                return;
            } else {
                for (Publish publish : publishList) {
                    count = 0;
                    averageRating = 0;
                    for (Book book : publish.getBookPublish()) {
                        if (book.getYearPublish() == inputYear) {
                            averageRating += book.getRating();
                            count++;
                        }
                    }
                    if (count != 0) {
                        averageRating /= count;
                        if (averageRating > topAverageRating) {
                            topAverageRating = averageRating;
                            topRatingName = publish.getPublishName();
                        }
                    }

                }
                if (topAverageRating == 0) {
                    System.out.println("За данный год не найдено издательств");
                } else {
                    System.out.println("За " + inputYear + "г.: '" + topRatingName + "' с наибольшим средним рейтингом: " + (String.format("%(.2f", topAverageRating)));
                }
            }

            // Запись в файл
            try (FileWriter writer = new FileWriter("src/com/company/output/output.txt", false)) {
                writer.write("Больше всего книг у издателя: " + publishList.get(0).getPublishName() + "' (" + publishList.get(0).getLenght() + " книг)");
                // Вывод отсортированного по году списка книг
                // sort предложен по дефолту
                for (Publish publish : publishList) {
                    if (publish.getPublishName().equals(publishList.get(0).getPublishName())) {
                        Collections.sort(publish.getBookPublish());
                        writer.write("\n" + String.valueOf(publish.getBookPublish()));
                    }
                }

                writer.flush();
            } catch (IOException e) {
                System.out.println("Не удалось записать файл!");
            }


        } catch (IOException e) {
            System.out.println("Файл не найден!");
        } catch (NullPointerException e) {
            System.out.println("В файле ошибка!" + e);
        } catch (InputMismatchException e) {
            System.out.println("Ошибка, введите целочисленное число!");
        }
    }
}
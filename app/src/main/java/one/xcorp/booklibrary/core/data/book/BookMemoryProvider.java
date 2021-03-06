package one.xcorp.booklibrary.core.data.book;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Single;
import one.xcorp.booklibrary.core.data.IDataProvider;

public class BookMemoryProvider implements IDataProvider<Book> {

    private static BookMemoryProvider instance;

    static synchronized BookMemoryProvider getInstance() {
        if (instance == null) {
            instance = new BookMemoryProvider();
        }
        return instance;
    }

    private final Set<Book> books = new HashSet<>();

    private BookMemoryProvider() {
        books.add(new Book(
                "Михаил Зыгарь",
                "Империя должна умереть. История русских революций в лицах. 1900-1917",
                2017, 910)
                .setIllustration("http://ozon-st.cdn.ngenix.net/multimedia/1021426197.jpg")
                .setExcerpt("Книга Михаила Зыгаря необыкновенно увлекательна, оторваться от нее невозможно. Важнейший момент в истории России становится понятным благодаря тому, что люди, творившие эту историю, показаны совершенно живыми; порой хотелось в голос говорить им: Нет, не надо, это ошибка, вы губите Россию! Не могу вспомнить ни одной книги - ни российского, ни зарубежного автора, - которая бы столь полно, точно и мощно представила читателю суть ключевого исторического события. Тем, кто стремится понять, почему произошло то, что произошло, чтение этой книги обязательно. Владимир Познер журналист и телеведущий, первый президент Академии российского телевидения. Это именно такое изложение истории, которое лично мне больше всего нравится: безэмоционально-беспартийное, взвешенное, аналитическое - и при этом не скучное. Довольно редкое сочетание. Григорий Чхартишвили (Борис Акунин) Странно, что такая книга не была написана раньше. Спокойное, внятное, но при этом крайне увлекательное изложение того, что случилось 100 лет назад. Эту книгу надо читать сегодня, потому что написана она для человека, живущего в 2017 году, и апеллирует к нашим нынешним представлениям о том, как крутятся механизмы власти, как делается история страны и как ее можно потерять."));
        books.add(new Book(
                "Дэн Браун",
                "Происхождение",
                2017, 576)
                .setIllustration("http://ozon-st.cdn.ngenix.net/multimedia/1021441153.jpg")
                .setExcerpt("Новый роман от автора абсолютного бестселлера \"Код да Винчи\"! Продолжение романов \"Ангелы и демоны\", \"Код Да Винчи\", \"Утраченный символ\" и \"Инферно\". Профессор Роберт Лэнгдон начинает новое расследование! Во всем мире продано свыше 200 000 000 книг Дэна Брауна! Роберт Лэнгдон прибывает в музей Гуггенхайма в Бильбао по приглашению друга и бывшего студента Эдмонда Кирша. Миллиардер и компьютерный гуру, он известен своими удивительными открытиями и предсказаниями. И этим вечером Кирш собирается \"перевернуть все современные научные представления о мире\", дав ответ на два главных вопроса, волнующих человечество на протяжении всей истории: Откуда мы? Что нас ждет? Однако прежде чем Эдмонд успевает сделать заявление, роскошный прием превращается в хаос. Лэнгдону и директору музея, красавице Амбре Видаль, чудом удается бежать. Теперь их путь лежит в Барселону, где Кирш оставил для своего учителя закодированный ключ к тайне, способной потрясти сами основы представлений человечества о себе. Тайне, которая была веками похоронена во тьме забвения. Тайне, которой, возможно, лучше бы никогда не увидеть света, - по крайней мере, так считают те, кто преследует Лэнгдона и Видаль и готов на все, чтобы помешать им раскрыть истину."));
        books.add(new Book(
                "Денис Савельев, Евгения Крюкова",
                "100+ хаков для интернет-маркетологов. Как получить трафик и конвертировать его в продажи",
                2018, 304)
                .setIllustration("http://ozon-st.cdn.ngenix.net/multimedia/1021598602.jpg")
                .setExcerpt("Цитата \"Эта книга должна/обязана лежать на столе у руководителя проекта, которому важно понимать происходящее, ибо конкуренция на рынке острая и без знания того, что необходимо и что достаточно делать, выживаемость интернет-компании резко снижается\". Герман Клименко, советник президента Российской Федерации по вопросам развития интернета. О чем книга Универсальное руководство \"100+ хаков для интернет-маркетологов\" - настоящий кладезь полезной информации с пошаговой инструкцией к действию. В этой книге не будет банальных советов из серии «создавая полезный контент, думайте о своей аудитории\". Авторы - руководитель отдела маркетинга компании \"Текстерра\" Евгения Крюкова и генеральный директор Денис Савельев - приводят конкретные рекомендации, как увеличить трафик и повысить конверсию. Все предложенные в книге методы и инструменты опробованы авторами на практике."));
        books.add(new Book(
                "Олеся Куприн",
                "Вкусные истории. Душевные рецепты для теплой компании",
                2016, 192)
                .setIllustration("http://ozon-st.cdn.ngenix.net/multimedia/1016015486.jpg")
                .setExcerpt("Хочется приготовить что-то быстрое, вкусное и чтобы домашние ахнули и расцеловали в обе щеки, а идей нет. Вам это знакомо? В этой книге вы найдете простые, несложные рецепты, которые помогут сделать разнообразным ужин вашей семьи и не при этом не требуют часами стоять за плитой. Тут и секретный семейный рецепт абрикосового пирога, и бабушкин рецепт засолки рыбы и даже \"суп с котом\". Кроме рецептов, вы найдете здесь теплые семейные истории Олеси Куприн и потрясающие фотографии, которые так любят ее подписчики в Инстаграм (А их уже больше 100 тыс!). Уверены, что они понравятся и вам."));
        books.add(new Book(
                "Э. Лофтус",
                "Маленькая книга. Мечта гурмана",
                2017, 288)
                .setIllustration("http://ozon-st.cdn.ngenix.net/multimedia/1020908148.jpg")
                .setExcerpt("В этой книге собраны знаковые рецепты мировой кухни – французской, британской, итальянской, китайской, арабской, индийской. Они представлены в форме четких пошаговых инструкций с фотографиями, иллюстрирующими каждый этап приготовления таким образом, что любое блюдо станет настоящей мечтой гурмана!"));
        books.add(new Book(
                "Kerryann Dunlop",
                "The Family Cookbook",
                2014, 128)
                .setIllustration("http://ozon-st.cdn.ngenix.net/multimedia/1010616875.jpg")
                .setExcerpt("The Family Cookbook, a selection of 50 hearty, everyday recipes, from Food Tube's own Kerryann Dunlop. \"Kerryann is a big character with a tone and style that's warm, motherly and gentle - with these recipes and her thrifty family tips and tricks, you'll have a bunch of recipes that'll serve you well for years to come\". (Jamie Oliver). Kerryann's no-nonsense approach to creating comforting family meals show that it's not hard to eat delicious food on a tight budget. She also has plenty of tips and tricks to get the most out of fresh, simple ingredients. Her simple twists on meat, fish and veg classics include: Homemade fish fingers and minty smashed peas, Potato, chickpea and cauliflower curry, and Lamb chops with aubergine salad and cucumber and mint yoghurt. Kerryann came into the public eye as one of Jamie Oliver's 'Fifteen' apprentices on Channel 4. Originally from Hackney, East London, she qualified as a silver-service waitress before her life was changed by being selected to train with Jamie. Her expertise as both a mother and a chef makes The Family Cookbook the perfect guide to delicious, fuss-free family eating."));
        books.add(new Book(
                "Стивен Р. Кови",
                "Семь навыков высокоэффективных людей. Мощные инструменты развития личности",
                2018, 398)
                .setIllustration("http://ozon-st.cdn.ngenix.net/multimedia/1018960911.jpg")
                .setExcerpt("Во-первых, эта книга излагает системный подход к определению жизненных целей, приоритетов человека. Эти цели у всех разные, но книга помогает понять себя и четко сформулировать жизненные цели. Во-вторых, книга показывает, как достигать этих целей. И в-третьих, книга показывает, как каждый человек может стать лучше. Причем речь идет не об изменении имиджа, а о настоящих изменениях, самосовершенствовании. Книга не дает простых решений и не обещает мгновенных чудес. Любые позитивные изменения требуют времени, работы и упорства. Но для людей, стремящихся максимально реализовать потенциал, заложенный в них природой, эта книга - дорожная карта."));
    }

    @Override
    public @NonNull Single<List<Book>> list() {
        return Single.just(new ArrayList<>(books));
    }

    @Override
    public Completable add(@NonNull Book book) {
        return Completable.fromAction(() -> books.add(book));
    }

    @Override
    public Completable delete(@NonNull Book book) {
        return Completable.fromAction(() -> books.remove(book));
    }

    @Override
    public Completable update(@NonNull Book book) {
        return Completable.fromAction(() -> {
            if (!books.contains(book)) {
                throw new IllegalStateException("Book does not exist.");
            }

            books.remove(book);
            books.add(book);
        });
    }
}